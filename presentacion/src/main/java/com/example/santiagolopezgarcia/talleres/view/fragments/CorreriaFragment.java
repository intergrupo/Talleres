package com.example.santiagolopezgarcia.talleres.view.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dominio.bussinesslogic.ordentrabajo.BuscadorOrdenTrabajo;
import com.example.dominio.bussinesslogic.ordentrabajo.OrdenTrabajoBL;
import com.example.dominio.bussinesslogic.tarea.TareaXOrdenTrabajoBL;
import com.example.dominio.modelonegocio.ClasificacionVista;
import com.example.dominio.modelonegocio.Correria;
import com.example.dominio.modelonegocio.ListaOrdenTrabajo;
import com.example.dominio.modelonegocio.ListaTareaXOrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;
import com.example.dominio.modelonegocio.TareaXOrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.presenters.CorreriaPresenter;
import com.example.santiagolopezgarcia.talleres.util.DatosCache;
import com.example.santiagolopezgarcia.talleres.view.adapters.OrdenTrabajoAdapter;
import com.example.santiagolopezgarcia.talleres.view.adapters.TareaXTrabajoOrdenTrabajoAdapter;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IBusquedaOrdenTrabajoPopup;
import com.example.santiagolopezgarcia.talleres.view.interfaces.ICorreriaView;
import com.example.santiagolopezgarcia.talleres.view.interfaces.InvocarActivity;
import com.example.santiagolopezgarcia.talleres.view.interfaces.Item;
import com.example.santiagolopezgarcia.talleres.view.interfaces.ItemSeleccionado;
import com.example.santiagolopezgarcia.talleres.view.popups.BusquedaOrdenTrabajoPopup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class CorreriaFragment extends BaseFragment<CorreriaPresenter> implements ItemSeleccionado, Item,
        IBusquedaOrdenTrabajoPopup, InvocarActivity, ICorreriaView {

    private static final String TIENE_FILTRO_ACTIVO = "tienefiltroactivobundle";
    private static final String POSICION_ORDEN_TRABAJO_ACTIVA = "posicionordentrabajoactivabundle";
    private static final String ORDEN_TRABAJO_BUSQUEDA = "ordentrabajobusquedabundle";

    Correria correria;
    @BindView(R.id.lvOrdenesTrabajo)
    RecyclerView lvOrdenesTrabajo;
    @Inject
    BuscadorOrdenTrabajo buscadorOrdenTrabajo;
    @Inject
    OrdenTrabajoBL ordenTrabajoBL;
    @Inject
    TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL;
    public boolean tieneFiltroActivo;
    private ProgressDialog pd;

    public int posicionOrdenTrabajoActiva;
    private CorreriaFragmentView correriaFragmentView;
    private int contadorLimpiarCache = 0;
    private boolean habilitarFiltro = false;
    private int posicionMarcar = -1;

    public OrdenTrabajo getOrdenTrabajoActiva() {
        return presentador.getOrdenTrabajoActiva();
    }

    public void setPosicionOrdenTrabajoActiva(int posicion) {
        presentador.setPosicionActiva(posicion);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vistaCorreria = inflater.inflate(R.layout.fragment_correria, container, false);
        ButterKnife.bind(this, vistaCorreria);
        dependencia.getContenedor().build().inject(this);
        obtenerParametros();
        recuperarSavedInstanceState(savedInstanceState);

        cargarDatosOnCreateView();

        return vistaCorreria;
    }

    public void cargarDatosOnCreateView() {
        if (!tieneFiltroActivo) {
            if (presentador.getClasificacionVista() == ClasificacionVista.ORDENTRABAJO) {
                cargarRevisiones("");
            } else {
                cargarLecturas("");
            }
            posicionar(presentador.getPosicionActiva());
        } else {
            buscar(presentador.getOrdenTrabajoBusqueda());
        }
    }

    private void recuperarSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(TIENE_FILTRO_ACTIVO)) {
                tieneFiltroActivo = savedInstanceState.getBoolean(TIENE_FILTRO_ACTIVO);
            }
            if (savedInstanceState.containsKey(POSICION_ORDEN_TRABAJO_ACTIVA)) {
                posicionOrdenTrabajoActiva = savedInstanceState.getInt(POSICION_ORDEN_TRABAJO_ACTIVA);
            }
            if (savedInstanceState.containsKey(ORDEN_TRABAJO_BUSQUEDA)) {
                presentador.setOrdenTrabajoBusqueda((OrdenTrabajoBusqueda) savedInstanceState.getSerializable(ORDEN_TRABAJO_BUSQUEDA));
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TIENE_FILTRO_ACTIVO, tieneFiltroActivo);
        outState.putInt(POSICION_ORDEN_TRABAJO_ACTIVA, posicionOrdenTrabajoActiva);
        outState.putSerializable(ORDEN_TRABAJO_BUSQUEDA, presentador.getOrdenTrabajoBusqueda());
    }


    private void obtenerParametros() {
        Bundle bundle = getArguments();
        if (bundle.containsKey(CorreriaFragment.class.getName())) {
            correria = (Correria) bundle.getSerializable(Correria.class.getName());
            if (bundle.getBoolean(CorreriaFragment.class.getName())) {
                presentador.setClasificacionVista(ClasificacionVista.ORDENTRABAJO);
            } else {
                presentador.setClasificacionVista(ClasificacionVista.TAREA);
            }
        }
        presentador.setPosicionActiva(bundle.containsKey("Posicion") ? bundle.getInt("Posicion") : 0);
    }

    public void cargarLecturas(String codigoOT) {
        mostrarBarraProgreso("Cargando tareas...");
//        if(presentador == null){
//            Intent intent = new Intent(AppContext.getContextoGlobal(), PrincipalActivity.class);
//            intent.putExtra(Correria.class.getName(), correria);
//            startActivity(intent);
//        }
        presentador.setClasificacionVista(ClasificacionVista.TAREA);
        CargadorDeDatos cargadorDeDatos = new CargadorDeDatos();
        cargadorDeDatos.setCodigoOT(codigoOT);
        cargadorDeDatos.execute();
        ocultarBarraProgreso();
        posicionMarcar = -1;
    }


    private void asignarLecturasALista(String codigoOT) {
        posicionOrdenTrabajoActiva = presentador.getPosicionActiva();
        if (correria != null) {
            if (!codigoOT.isEmpty()) {
                posicionOrdenTrabajoActiva = presentador.obtenerPosicionTareaXOT(tieneFiltroActivo, codigoOT);
                itemSeleccionado(posicionOrdenTrabajoActiva);
            }
            if (DatosCache.getListaOrdenTrabajoFiltrado().isEmpty()) {
                TareaXTrabajoOrdenTrabajoAdapter trabajoOrdenTrabajoAdapter = obtenerTareaXTrabajoOrdenTrabajoAdapter(DatosCache.getListaTareaXOrdenTrabajo());
                if (!DatosCache.getListaTareaXOrdenTrabajo().isEmpty()) {
                    presentador.setTareaXOrdenTrabajoActiva(DatosCache.getListaTareaXOrdenTrabajo().get(posicionOrdenTrabajoActiva));
                    itemSeleccionado(posicionOrdenTrabajoActiva);
                } else {
                    itemSeleccionado(posicionOrdenTrabajoActiva);
                }

                lvOrdenesTrabajo.setAdapter(trabajoOrdenTrabajoAdapter);
                lvOrdenesTrabajo.setLayoutManager(new LinearLayoutManager(getActivity()));
            } else {
                if (tieneFiltroActivo) {
                    posicionarTareaXOrdenTrabajoActiva(iniciarFiltroLecturas(presentador.getOrdenTrabajoBusqueda()));
                    return;
                }

                quitarFiltroLecturas();
                itemSeleccionado(posicionOrdenTrabajoActiva);
            }
            lvOrdenesTrabajo.scrollToPosition(posicionOrdenTrabajoActiva - 1);
        }
    }

    public void posicionar(int posicion) {
        lvOrdenesTrabajo.scrollToPosition(posicion - 1);
    }

    public void cargarRevisiones(String codigoOT) {
        mostrarBarraProgreso("Cargando ordenes de trabajo...");
        presentador.setClasificacionVista(ClasificacionVista.ORDENTRABAJO);
        CargadorDeDatos cargadorDeDatos = new CargadorDeDatos();
        cargadorDeDatos.setCodigoOT(codigoOT);
        cargadorDeDatos.execute();
        posicionMarcar = -1;
    }

    private void asignarRevisionesALista(String codigoOT) {
        posicionOrdenTrabajoActiva = presentador.getPosicionActiva();
        if (correria != null) {
            if (!codigoOT.isEmpty()) {
                posicionOrdenTrabajoActiva = presentador.obtenerPosicionOT(tieneFiltroActivo, codigoOT);
                itemSeleccionado(posicionOrdenTrabajoActiva);
            }
            if (DatosCache.getListaTareaXOrdenTrabajoFiltrado().isEmpty()) {
                OrdenTrabajoAdapter ordenesTrabajoAdapter = obtenerOrdenTrabajoAdapter(DatosCache.getListaOrdenTrabajo());
                if (!DatosCache.getListaOrdenTrabajo().isEmpty()) {
                    presentador.setOrdenTrabajoActiva(DatosCache.getListaOrdenTrabajo().get(posicionOrdenTrabajoActiva));
                    itemSeleccionado(posicionOrdenTrabajoActiva);
                } else {
                    itemSeleccionado(posicionOrdenTrabajoActiva);
                }
                lvOrdenesTrabajo.setAdapter(ordenesTrabajoAdapter);
                lvOrdenesTrabajo.setLayoutManager(new LinearLayoutManager(getActivity()));
            } else {
                if (tieneFiltroActivo) {
                    posicionarOrdenTrabajoActiva(iniciarFiltroRevisiones(presentador.getOrdenTrabajoBusqueda()));
                    return;
                }
                quitarFiltroRevisiones();
                itemSeleccionado(posicionOrdenTrabajoActiva);
            }
            lvOrdenesTrabajo.scrollToPosition(posicionOrdenTrabajoActiva - 1);
        }
    }

    @NonNull
    public OrdenTrabajoAdapter obtenerOrdenTrabajoAdapter(ListaOrdenTrabajo listaOrdenTrabajo) {
        OrdenTrabajoAdapter ordenesTrabajoAdapter = new OrdenTrabajoAdapter(this, listaOrdenTrabajo,
                posicionOrdenTrabajoActiva, (SiriusApp) getActivity().getApplication());
        ordenesTrabajoAdapter.setItemSeleccionado(this);
        ordenesTrabajoAdapter.setItemActivo(this);
        return ordenesTrabajoAdapter;
    }

    @NonNull
    public TareaXTrabajoOrdenTrabajoAdapter obtenerTareaXTrabajoOrdenTrabajoAdapter(ListaTareaXOrdenTrabajo listaTareaXOrdenTrabajo) {
        TareaXTrabajoOrdenTrabajoAdapter ordenesTrabajoAdapter = new TareaXTrabajoOrdenTrabajoAdapter(
                getContext(), this, (SiriusApp) getActivity().getApplication(),
                listaTareaXOrdenTrabajo, posicionOrdenTrabajoActiva);
        ordenesTrabajoAdapter.setItemSeleccionado(this);
        ordenesTrabajoAdapter.setItemActivo(this);
        return ordenesTrabajoAdapter;
    }

    public void iniciarActiva(View view) {
        if (presentador.getClasificacionVista() == ClasificacionVista.ORDENTRABAJO) {
            if ((DatosCache.getListaOrdenTrabajoFiltrado().size() > 0) || (DatosCache.getListaOrdenTrabajo().size() > 0)) {
                ((OrdenTrabajoAdapter) lvOrdenesTrabajo.getAdapter()).iniciarOrdenTrabajoActiva(presentador.getOrdenTrabajoActiva());
            } else {
                mostrarMensajeError(getActivity().getString(R.string.mensaje_no_tiene_ninguna_orden_activa));
            }
        } else {
            if ((DatosCache.getListaTareaXOrdenTrabajoFiltrado().size() > 0) || (DatosCache.getListaTareaXOrdenTrabajo().size() > 0)) {
                ((TareaXTrabajoOrdenTrabajoAdapter) lvOrdenesTrabajo.getAdapter()).iniciarTareaXOrdenTrabajoActiva(presentador.getTareaXOrdenTrabajoActiva());
            } else {
                mostrarMensajeError(getActivity().getString(R.string.mensaje_no_tiene_ninguna_orden_activa));
            }
        }
    }

    @Override
    public void itemSeleccionado(int posicion) {
        posicionOrdenTrabajoActiva = posicion;
        refrescarTotalesPaginador(posicion + 1);
    }

    private void aplicarFiltroRevisiones() {
        tieneFiltroActivo = true;
        correriaFragmentView.habilitarFiltro(tieneFiltroActivo);
        OrdenTrabajoAdapter ordenesTrabajoAdapter = obtenerOrdenTrabajoAdapter(DatosCache.getListaOrdenTrabajoFiltrado());
        lvOrdenesTrabajo.setAdapter(ordenesTrabajoAdapter);
        lvOrdenesTrabajo.setLayoutManager(new LinearLayoutManager(getActivity()));
        correriaFragmentView.cambiarColorBotonConFiltroActivo();
        itemSeleccionado(0);
    }

    private void aplicarFiltroLecturas() {
        tieneFiltroActivo = true;
        correriaFragmentView.habilitarFiltro(tieneFiltroActivo);
        TareaXTrabajoOrdenTrabajoAdapter tareaXTrabajoOrdenTrabajoAdapter =
                obtenerTareaXTrabajoOrdenTrabajoAdapter(DatosCache.getListaTareaXOrdenTrabajoFiltrado());
        lvOrdenesTrabajo.setAdapter(tareaXTrabajoOrdenTrabajoAdapter);
        lvOrdenesTrabajo.setLayoutManager(new LinearLayoutManager(getActivity()));
        correriaFragmentView.cambiarColorBotonConFiltroActivo();
        itemSeleccionado(0);
    }

    public boolean filtrarRevisiones(OrdenTrabajoBusqueda ordenTrabajoBusqueda) {

        DatosCache.getListaOrdenTrabajoFiltrado().clear();
        if (!ordenTrabajoBusqueda.isLimpiar()) {
            if (ordenTrabajoBusqueda.isTodos()) {
                DatosCache.setListaOrdenTrabajoFiltrado(DatosCache.getListaOrdenTrabajo().clonar());
            } else {
                DatosCache.setListaOrdenTrabajoFiltrado(buscadorOrdenTrabajo.filtrarPorOrdenTrabajo(
                        DatosCache.getListaOrdenTrabajo(), ordenTrabajoBusqueda));
            }
        }
        return !DatosCache.getListaOrdenTrabajoFiltrado().isEmpty();
    }

    private boolean filtrarLectura(OrdenTrabajoBusqueda ordenTrabajoBusqueda) {
        DatosCache.getListaTareaXOrdenTrabajoFiltrado().clear();
        if (!ordenTrabajoBusqueda.isLimpiar()) {
            if (ordenTrabajoBusqueda.isTodos()) {
                DatosCache.setListaTareaXOrdenTrabajoFiltrado(DatosCache.getListaTareaXOrdenTrabajo().clonar());
            } else {
                DatosCache.setListaTareaXOrdenTrabajoFiltrado(buscadorOrdenTrabajo.filtrarPorTarea(
                        DatosCache.getListaTareaXOrdenTrabajo(), ordenTrabajoBusqueda));
            }
        }
        return !DatosCache.getListaTareaXOrdenTrabajoFiltrado().isEmpty();
    }

    public boolean iniciarFiltroLecturas(OrdenTrabajoBusqueda ordenTrabajoBusqueda) {
        int cambiar = 0;
        if (filtrarLectura(ordenTrabajoBusqueda)) {
            getActivity().runOnUiThread(() -> {
                aplicarFiltroLecturas();
            });
            cambiar = 1;
            return true;
        } else {
            TareaXTrabajoOrdenTrabajoAdapter ordenesTrabajoAdapter = obtenerTareaXTrabajoOrdenTrabajoAdapter(
                    DatosCache.getListaTareaXOrdenTrabajo());
            getActivity().runOnUiThread(() -> {
                lvOrdenesTrabajo.setAdapter(ordenesTrabajoAdapter);
                lvOrdenesTrabajo.setLayoutManager(new LinearLayoutManager(getActivity()));
            });
        }
        tieneFiltroActivo = false;
        if (cambiar == 1) {
            getActivity().runOnUiThread(() -> {
                itemSeleccionado(0);
                correriaFragmentView.cambiarColorBotonSinFiltro();
            });
        } else {
            getActivity().runOnUiThread(() -> {
                itemSeleccionado(posicionOrdenTrabajoActiva);
            });
        }
        return false;
    }

    public boolean iniciarFiltroRevisiones(OrdenTrabajoBusqueda ordenTrabajoBusqueda) {
        int cambiar = 0;
        if (filtrarRevisiones(ordenTrabajoBusqueda)) {
            getActivity().runOnUiThread(() -> {
                aplicarFiltroRevisiones();
            });
            cambiar = 1;
            return true;
        } else {
            OrdenTrabajoAdapter ordenesTrabajoAdapter = obtenerOrdenTrabajoAdapter(
                    DatosCache.getListaOrdenTrabajo());
            getActivity().runOnUiThread(() -> {
                lvOrdenesTrabajo.setAdapter(ordenesTrabajoAdapter);
                lvOrdenesTrabajo.setLayoutManager(new LinearLayoutManager(getActivity()));
            });
        }
        tieneFiltroActivo = false;
        if (cambiar == 1) {
            getActivity().runOnUiThread(() -> {
                itemSeleccionado(0);
                correriaFragmentView.cambiarColorBotonSinFiltro();
            });
        } else {
            getActivity().runOnUiThread(() -> {
                itemSeleccionado(posicionOrdenTrabajoActiva);
            });
        }
        return false;
    }

    private void quitarFiltroRevisiones() {
        OrdenTrabajoAdapter ordenesTrabajoAdapter = obtenerOrdenTrabajoAdapter(DatosCache.getListaOrdenTrabajo());
        correriaFragmentView.cambiarColorBotonConFiltroInactivo();
        lvOrdenesTrabajo.setAdapter(ordenesTrabajoAdapter);
        lvOrdenesTrabajo.setLayoutManager(new LinearLayoutManager(getActivity()));
        presentador.setOrdenTrabajoActiva(DatosCache.getListaOrdenTrabajo().get(posicionOrdenTrabajoActiva));
    }

    private void mostrarFiltroRevisiones() {
        OrdenTrabajoAdapter ordenesTrabajoAdapter = obtenerOrdenTrabajoAdapter(DatosCache.getListaOrdenTrabajoFiltrado());
        correriaFragmentView.cambiarColorBotonConFiltroActivo();
        lvOrdenesTrabajo.setAdapter(ordenesTrabajoAdapter);
        lvOrdenesTrabajo.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (posicionOrdenTrabajoActiva == 1) {
            posicionOrdenTrabajoActiva = 0;
        }
        if (DatosCache.getListaOrdenTrabajoFiltrado().size() > 0) {
            presentador.setOrdenTrabajoActiva(DatosCache.getListaOrdenTrabajoFiltrado().get(posicionOrdenTrabajoActiva));
        } else {
            quitarFiltroRevisiones();
            tieneFiltroActivo = false;
            refrescarTotalesPaginador(posicionOrdenTrabajoActiva);
        }
    }

    private void mostrarFiltroLecturas() {
        TareaXTrabajoOrdenTrabajoAdapter tareaXTrabajoOrdenTrabajoAdapter =
                obtenerTareaXTrabajoOrdenTrabajoAdapter(DatosCache.getListaTareaXOrdenTrabajoFiltrado());
        correriaFragmentView.cambiarColorBotonConFiltroActivo();
        lvOrdenesTrabajo.setAdapter(tareaXTrabajoOrdenTrabajoAdapter);
        lvOrdenesTrabajo.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (posicionOrdenTrabajoActiva == 1) {
            posicionOrdenTrabajoActiva = 0;
        }
        if (DatosCache.getListaTareaXOrdenTrabajoFiltrado().size() > 0) {
            presentador.setTareaXOrdenTrabajoActiva(DatosCache.getListaTareaXOrdenTrabajoFiltrado().get(posicionOrdenTrabajoActiva));
        } else {
            quitarFiltroLecturas();
            tieneFiltroActivo = false;
            refrescarTotalesPaginador(posicionOrdenTrabajoActiva);
        }

    }

    private void quitarFiltroLecturas() {
        TareaXTrabajoOrdenTrabajoAdapter tareaXTrabajoOrdenTrabajoAdapter = obtenerTareaXTrabajoOrdenTrabajoAdapter(DatosCache.getListaTareaXOrdenTrabajo());
        correriaFragmentView.cambiarColorBotonConFiltroInactivo();
        lvOrdenesTrabajo.setAdapter(tareaXTrabajoOrdenTrabajoAdapter);
        lvOrdenesTrabajo.setLayoutManager(new LinearLayoutManager(getActivity()));
        presentador.setTareaXOrdenTrabajoActiva(DatosCache.getListaTareaXOrdenTrabajo().get(posicionOrdenTrabajoActiva));
    }

    private void iniciarFiltroRevisiones() {

        if (presentador.getOrdenTrabajoBusqueda() != null) {
            if (!presentador.getOrdenTrabajoBusqueda().esVacio()) {
                if (contadorLimpiarCache == 0) {
                    if (DatosCache.getListaOrdenTrabajoFiltrado().size() > 0 && !tieneFiltroActivo) {
                        DatosCache.getListaOrdenTrabajoFiltrado().clear();
                    }
                }
            }
        }

        if (DatosCache.getListaOrdenTrabajoFiltrado().size() > 0) {
            contadorLimpiarCache = 0;
            if (tieneFiltroActivo) {
                tieneFiltroActivo = false;
                posicionOrdenTrabajoActiva = presentador.obtenerPosicionOT(tieneFiltroActivo,
                        presentador.getOrdenTrabajoActiva().getCodigoOrdenTrabajo());
                quitarFiltroRevisiones();
                itemSeleccionado(posicionOrdenTrabajoActiva);
                lvOrdenesTrabajo.scrollToPosition(posicionOrdenTrabajoActiva);
            } else {
                mostrarFiltroRevisiones();
                tieneFiltroActivo = true;
                itemSeleccionado(0);
            }
        } else if (presentador.getOrdenTrabajoBusqueda() != null) {
            if (!presentador.getOrdenTrabajoBusqueda().esVacio()) {
                agregarCacheRevisiones(presentador.getOrdenTrabajoBusqueda());
                if (filtrarRevisiones(presentador.getOrdenTrabajoBusqueda())) {
                    iniciarFiltroRevisiones();
                } else {
                    contadorLimpiarCache = 0;
                    mostrarMensajeError(getResources().getString(R.string.mensaje_no_obtuvo_resultados_filtro));

                }
            } else {
                mostrarMensajeError(getResources().getString(R.string.mensaje_no_tiene_parametros_busqueda));
            }
        }
    }


    private void iniciarFiltroLecturas() {

        if (presentador.getOrdenTrabajoBusqueda() != null) {
            if (!presentador.getOrdenTrabajoBusqueda().esVacio()) {
                if (contadorLimpiarCache == 0) {
                    if (DatosCache.getListaTareaXOrdenTrabajoFiltrado().size() > 0 && !tieneFiltroActivo) {
                        DatosCache.getListaTareaXOrdenTrabajoFiltrado().clear();
                    }
                }
            }
        }

        if (DatosCache.getListaTareaXOrdenTrabajoFiltrado().size() > 0) {
            contadorLimpiarCache = 0;
            if (tieneFiltroActivo) {
                tieneFiltroActivo = false;
                posicionOrdenTrabajoActiva = presentador.obtenerPosicionTarea(tieneFiltroActivo,
                        presentador.getTareaXOrdenTrabajoActiva());
                quitarFiltroLecturas();
                itemSeleccionado(posicionOrdenTrabajoActiva);
                lvOrdenesTrabajo.scrollToPosition(posicionOrdenTrabajoActiva);
            } else {
                mostrarFiltroLecturas();
                tieneFiltroActivo = true;
                itemSeleccionado(0);
            }
        } else if (presentador.getOrdenTrabajoBusqueda() != null) {
            if (!presentador.getOrdenTrabajoBusqueda().esVacio()) {
                agregarCacheLecturas(presentador.getOrdenTrabajoBusqueda());
                if (filtrarLectura(presentador.getOrdenTrabajoBusqueda())) {
                    iniciarFiltroLecturas();
                } else {
                    contadorLimpiarCache = 0;
                    mostrarMensajeError(getResources().getString(R.string.mensaje_no_obtuvo_resultados_filtro));
                }
            } else {
                mostrarMensajeError(getResources().getString(R.string.mensaje_no_tiene_parametros_busqueda));
            }
        }
    }

    private void agregarCacheRevisiones(OrdenTrabajoBusqueda ordenTrabajoBusqueda) {
        DatosCache.setListaOrdenTrabajoFiltrado(buscadorOrdenTrabajo.filtrarPorOrdenTrabajo(
                DatosCache.getListaOrdenTrabajo(), ordenTrabajoBusqueda));
        contadorLimpiarCache += 1;
    }

    private void agregarCacheLecturas(OrdenTrabajoBusqueda ordenTrabajoBusqueda) {
        DatosCache.setListaTareaXOrdenTrabajoFiltrado(buscadorOrdenTrabajo.filtrarPorTarea(
                DatosCache.getListaTareaXOrdenTrabajo(), ordenTrabajoBusqueda));
        contadorLimpiarCache += 1;
    }

    public void filtrar() {
        posicionOrdenTrabajoActiva = 0;
        if (presentador.getClasificacionVista() == ClasificacionVista.ORDENTRABAJO) {
            iniciarFiltroRevisiones();
        } else {
            iniciarFiltroLecturas();
        }
    }

    private void refrescarTotalesPaginador(int posicion) {
        if (tieneFiltroActivo) {
            if (presentador.getClasificacionVista() == ClasificacionVista.ORDENTRABAJO) {
                if (DatosCache.getListaOrdenTrabajoFiltrado().size() != 0)
                    correriaFragmentView.refrescarFiltro(posicion + "/" + DatosCache.getListaOrdenTrabajoFiltrado().size());
                else
                    correriaFragmentView.refrescarFiltro(posicion + "/" + DatosCache.getListaOrdenTrabajo().size());
                if (posicion < DatosCache.getListaOrdenTrabajoFiltrado().size()) {
                    OrdenTrabajo ordenTrabajo = DatosCache.getListaOrdenTrabajoFiltrado().get(posicion);
                    ordenTrabajo = ordenTrabajoBL.cargarOrdenTrabajo(ordenTrabajo.getCodigoCorreria(), ordenTrabajo.getCodigoOrdenTrabajo());
                    DatosCache.getListaOrdenTrabajoFiltrado().set(posicion, ordenTrabajo);
                }
            } else {
                if (DatosCache.getListaTareaXOrdenTrabajoFiltrado().size() != 0)
                    correriaFragmentView.refrescarFiltro(posicion + "/" + DatosCache.getListaTareaXOrdenTrabajoFiltrado().size());
                else
                    correriaFragmentView.refrescarFiltro(posicion + "/" + DatosCache.getListaTareaXOrdenTrabajo().size());
            }
        } else {
            if (presentador.getClasificacionVista() == ClasificacionVista.ORDENTRABAJO) {
                correriaFragmentView.refrescarFiltro(posicion + "/" + DatosCache.getListaOrdenTrabajo().size());
                if (posicion < DatosCache.getListaOrdenTrabajo().size()) {
                    OrdenTrabajo ordenTrabajo = DatosCache.getListaOrdenTrabajo().get(posicion);
                    ordenTrabajo = ordenTrabajoBL.cargarOrdenTrabajo(ordenTrabajo.getCodigoCorreria(), ordenTrabajo.getCodigoOrdenTrabajo());
                    DatosCache.getListaOrdenTrabajo().set(posicion, ordenTrabajo);
                }
            } else {
                correriaFragmentView.refrescarFiltro(posicion + "/" + DatosCache.getListaTareaXOrdenTrabajo().size());
            }
        }
    }

    @Override
    public void itemProcesado(Object entidad) {
        if (entidad instanceof OrdenTrabajo) {
            presentador.setOrdenTrabajoActiva((OrdenTrabajo) entidad);
        } else {
            presentador.setTareaXOrdenTrabajoActiva((TareaXOrdenTrabajo) entidad);
        }
    }

    @Override
    public boolean buscar(OrdenTrabajoBusqueda ordenTrabajoBusqueda) {
        presentador.setOrdenTrabajoBusqueda(ordenTrabajoBusqueda);
        boolean resultado;
        if (presentador.getClasificacionVista() == ClasificacionVista.ORDENTRABAJO) {
            if (ordenTrabajoBusqueda.esVacio()) {
                quitarFiltroRevisiones();
                DatosCache.setListaOrdenTrabajoFiltrado(new ListaOrdenTrabajo());
                DatosCache.setListaTareaXOrdenTrabajoFiltrado(new ListaTareaXOrdenTrabajo());
                correriaFragmentView.cambiarColorBotonSinFiltro();
            }
            resultado = iniciarFiltroRevisiones(ordenTrabajoBusqueda);
            posicionarOrdenTrabajoActiva(resultado);
        } else {
            if (ordenTrabajoBusqueda.esVacio()) {
                quitarFiltroLecturas();
                DatosCache.setListaOrdenTrabajoFiltrado(new ListaOrdenTrabajo());
                DatosCache.setListaTareaXOrdenTrabajoFiltrado(new ListaTareaXOrdenTrabajo());
                correriaFragmentView.cambiarColorBotonSinFiltro();
            }
            resultado = iniciarFiltroLecturas(presentador.getOrdenTrabajoBusqueda());
            posicionarTareaXOrdenTrabajoActiva(resultado);
        }
        return resultado;
    }

    private void posicionarTareaXOrdenTrabajoActiva(boolean resultado) {
        getActivity().runOnUiThread(() -> {
            if (resultado) {
//            DatosCache.setListaTareaXOrdenTrabajoFiltrado(buscadorOrdenTrabajo.filtrarPorTarea(DatosCache.getListaTareaXOrdenTrabajo(),
//                    presentador.getOrdenTrabajoBusqueda()));
                presentador.setTareaXOrdenTrabajoActiva(DatosCache.getListaTareaXOrdenTrabajoFiltrado().get(posicionOrdenTrabajoActiva));
            } else {
                presentador.setTareaXOrdenTrabajoActiva(DatosCache.getListaTareaXOrdenTrabajo().get(posicionOrdenTrabajoActiva));
            }
        });
    }

    private void posicionarOrdenTrabajoActiva(boolean resultado) {
        getActivity().runOnUiThread(() -> {
            if (resultado) {
//            DatosCache.setListaOrdenTrabajoFiltrado(buscadorOrdenTrabajo.filtrarPorOrdenTrabajo(DatosCache.getListaOrdenTrabajo(),
//                    presentador.getOrdenTrabajoBusqueda()));
                presentador.setOrdenTrabajoActiva(DatosCache.getListaOrdenTrabajoFiltrado().get(posicionOrdenTrabajoActiva));
            } else {
                presentador.setOrdenTrabajoActiva(DatosCache.getListaOrdenTrabajo().get(posicionOrdenTrabajoActiva));
            }
        });
    }

    public void mostrarBusqueda() {
        BusquedaOrdenTrabajoPopup busquedaOrdenTrabajoPopup = new BusquedaOrdenTrabajoPopup();
        busquedaOrdenTrabajoPopup.setOrdenTrabajoABuscar(presentador.getOrdenTrabajoBusqueda());
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        busquedaOrdenTrabajoPopup.setBusquedaOrdenTrabajoPopup(this);
        busquedaOrdenTrabajoPopup.show(fragmentManager, "");
    }


    //Recibir parametros
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                int posicionOrdenTrabajoActualActiva = 0;
                boolean refrescarAdapter = false;

                if (bundle.containsKey("positionCurrentItem")) {
                    posicionOrdenTrabajoActualActiva = bundle.getInt("positionCurrentItem");
                }
                tieneFiltroActivo = false;
                if (bundle.containsKey("FiltroActivo")) {
                    tieneFiltroActivo = bundle.getBoolean("FiltroActivo");
                }
                if (bundle.containsKey("refrescarAdapter")) {
                    refrescarAdapter = bundle.getBoolean("refrescarAdapter");
                }
                if (bundle.containsKey("posicionMarcar")) {
                    posicionMarcar = bundle.getInt("posicionMarcar");
                }
                presentador.setOrdenTrabajoBusqueda(new OrdenTrabajoBusqueda());
                if (bundle.containsKey(OrdenTrabajoBusqueda.class.getName())) {
                    presentador.setOrdenTrabajoBusqueda((OrdenTrabajoBusqueda) bundle.getSerializable(OrdenTrabajoBusqueda.class.getName()));
                }

                if (bundle.containsKey("HabilitarFiltro")) {
                    habilitarFiltro = bundle.getBoolean("HabilitarFiltro");
                }

                if (presentador.getClasificacionVista() == ClasificacionVista.ORDENTRABAJO) {
                    cargarDatos();
                    resultadoInvocarDetalleRevisiones(bundle, posicionOrdenTrabajoActualActiva);
                    lvOrdenesTrabajo.scrollToPosition(posicionOrdenTrabajoActualActiva - 1);
                } else {
                    mostrarBarraProgreso("Cargando tareas...");
                    if (bundle.containsKey(TareaXOrdenTrabajo.class.getName())) {
                        posicionOrdenTrabajoActualActiva = presentador.obtenerPosicionTarea(tieneFiltroActivo,
                                (TareaXOrdenTrabajo) bundle.getSerializable(TareaXOrdenTrabajo.class.getName()));
                    }
                    final int finalPosicionOrdenTrabajoActualActiva = posicionOrdenTrabajoActualActiva;
                    boolean finalRefrescarAdapter = refrescarAdapter;
                    pd = ProgressDialog.show(getContext(), "Datos", "Refrescando...");
                    Thread thread = new Thread(() -> {
                        DatosCache.setListaTareaXOrdenTrabajo(tareaXOrdenTrabajoBL.cargarTareasXOrdenTrabajo(correria.getCodigoCorreria()));
                        resultadoInvocarDetalleLecturas(finalPosicionOrdenTrabajoActualActiva);
                        if (finalRefrescarAdapter) {
                            Intent intentInvocar = new Intent();
                            intentInvocar.putExtra("positionCurrentItem", finalPosicionOrdenTrabajoActualActiva);
                            intentInvocar.putExtra(OrdenTrabajoBusqueda.class.getName(), presentador.getOrdenTrabajoBusqueda());
                            invocar(intentInvocar, ClasificacionVista.TAREA);
                        }
                        handler.sendEmptyMessage(0);
                    });
                    thread.setPriority(Thread.MIN_PRIORITY);
                    thread.start();
                }
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            pd.dismiss();
        }
    };


    private void resultadoInvocarDetalleLecturas(int posicionOrdenTrabajoActualActiva) {
        getActivity().runOnUiThread(() -> {
            if (!DatosCache.getListaTareaXOrdenTrabajoFiltrado().isEmpty()) {
                posicionOrdenTrabajoActiva = posicionOrdenTrabajoActualActiva;
                filtrarLectura(presentador.getOrdenTrabajoBusqueda());
//                if (tieneFiltroActivo && posicionOrdenTrabajoActualActiva != 0) {
                if (tieneFiltroActivo) {
                    mostrarFiltroLecturas();
                } else {
                    quitarFiltroLecturas();
                }
            } else {
                TareaXTrabajoOrdenTrabajoAdapter tareaXTrabajoOrdenTrabajoAdapter = obtenerTareaXTrabajoOrdenTrabajoAdapter(DatosCache.getListaTareaXOrdenTrabajo());
                lvOrdenesTrabajo.setAdapter(tareaXTrabajoOrdenTrabajoAdapter);
                if (posicionOrdenTrabajoActualActiva != posicionOrdenTrabajoActiva) {
                    ((TareaXTrabajoOrdenTrabajoAdapter) lvOrdenesTrabajo.getAdapter()).setPosicionOrdenTrabajoActiva(posicionOrdenTrabajoActualActiva);
                    lvOrdenesTrabajo.getAdapter().notifyItemChanged(posicionOrdenTrabajoActiva);
                    lvOrdenesTrabajo.getAdapter().notifyItemChanged(posicionOrdenTrabajoActualActiva);
                }
                correriaFragmentView.cambiarColorBotonSinFiltro();
                posicionOrdenTrabajoActiva = posicionOrdenTrabajoActualActiva;
                presentador.setTareaXOrdenTrabajoActiva(DatosCache.getListaTareaXOrdenTrabajo().get(posicionOrdenTrabajoActualActiva));
            }
            refrescarTotalesPaginador(posicionOrdenTrabajoActiva + 1);
            ocultarBarraProgreso();
            lvOrdenesTrabajo.scrollToPosition(posicionOrdenTrabajoActualActiva);
            if (habilitarFiltro) {
                correriaFragmentView.habilitarFiltro(habilitarFiltro);
                correriaFragmentView.cambiarColorBotonConFiltroInactivo();
            }
        });
    }

    private void resultadoInvocarDetalleRevisiones(Bundle bundle, int posicionOrdenTrabajoActualActiva) {
        if (!DatosCache.getListaOrdenTrabajoFiltrado().isEmpty()) {
            posicionOrdenTrabajoActiva = posicionOrdenTrabajoActualActiva;
            if (tieneFiltroActivo) {
                mostrarFiltroRevisiones();
                presentador.setOrdenTrabajoActiva(DatosCache.getListaOrdenTrabajoFiltrado().get(posicionOrdenTrabajoActualActiva));
            } else {
                quitarFiltroRevisiones();
                presentador.setOrdenTrabajoActiva(DatosCache.getListaOrdenTrabajo().get(posicionOrdenTrabajoActualActiva));
            }
        } else {
            if (presentador.getOrdenTrabajoBusqueda() == null)
                presentador.setOrdenTrabajoBusqueda(new OrdenTrabajoBusqueda());
            OrdenTrabajoAdapter ordenesTrabajoAdapter = obtenerOrdenTrabajoAdapter(DatosCache.getListaOrdenTrabajo());
            lvOrdenesTrabajo.setAdapter(ordenesTrabajoAdapter);
            tieneFiltroActivo = false;
            if (posicionOrdenTrabajoActualActiva != posicionOrdenTrabajoActiva) {
                ((OrdenTrabajoAdapter) lvOrdenesTrabajo.getAdapter()).setPosicionOrdenTrabajoActiva(posicionOrdenTrabajoActualActiva);
                lvOrdenesTrabajo.getAdapter().notifyItemChanged(posicionOrdenTrabajoActiva);
                lvOrdenesTrabajo.getAdapter().notifyItemChanged(posicionOrdenTrabajoActualActiva);
            }
            correriaFragmentView.cambiarColorBotonSinFiltro();
            posicionOrdenTrabajoActiva = posicionOrdenTrabajoActualActiva;
            presentador.setOrdenTrabajoActiva(DatosCache.getListaOrdenTrabajo().get(posicionOrdenTrabajoActiva));
        }
        refrescarTotalesPaginador(posicionOrdenTrabajoActiva + 1);
        if (habilitarFiltro) {
            correriaFragmentView.habilitarFiltro(habilitarFiltro);
            correriaFragmentView.cambiarColorBotonConFiltroInactivo();
        }
    }

    //Enviar parametros
    @Override
    public void invocar(Intent intent, ClasificacionVista clasificacionVista) {
//        if (clasificacionVista == ClasificacionVista.ORDENTRABAJO) {
//            intent.setClass(getActivity(), DetalleRevisionesActivity.class);
//            if (DatosCache.getListaOrdenTrabajoFiltrado().size() > 0) {
//                intent.putExtra(OrdenTrabajoBusqueda.class.getName(), presentador.getOrdenTrabajoBusqueda());
//                intent.putExtra("FiltroActivo", tieneFiltroActivo);
//            }
//            if (presentador.getOrdenTrabajoBusqueda() != null) {
//                if (!presentador.getOrdenTrabajoBusqueda().esVacio())
//                    intent.putExtra(OrdenTrabajoBusqueda.class.getName(), presentador.getOrdenTrabajoBusqueda());
//            }
//        } else {
//            intent.setClass(getActivity(), DetalleLecturasActivity.class);
//            if (DatosCache.getListaTareaXOrdenTrabajoFiltrado().size() > 0) {
//                intent.putExtra(OrdenTrabajoBusqueda.class.getName(), presentador.getOrdenTrabajoBusqueda());
//                intent.putExtra("FiltroActivo", tieneFiltroActivo);
//            }
//            if (presentador.getOrdenTrabajoBusqueda() != null) {
//                if (!presentador.getOrdenTrabajoBusqueda().esVacio())
//                    intent.putExtra(OrdenTrabajoBusqueda.class.getName(), presentador.getOrdenTrabajoBusqueda());
//            }
//
//        }
//        intent.putExtra("posicionMarcar", posicionMarcar);
//        startActivityForResult(intent, 5);
    }

    @Override
    public Context obtenerContexto() {
        return getActivity();
    }

    private void cargarDatos() {
        if (presentador.getClasificacionVista() == ClasificacionVista.TAREA) {
            DatosCache.setListaTareaXOrdenTrabajo(tareaXOrdenTrabajoBL.cargarTareasXOrdenTrabajo(
                    correria.getCodigoCorreria()));
        } else {
            DatosCache.setListaOrdenTrabajo(ordenTrabajoBL.cargarOrdenesTrabajo(correria.getCodigoCorreria()));
        }
    }

    @Override
    public void mostrarBarraProgreso() {

    }

    private class CargadorDeDatos extends AsyncTask<Void, Void, Boolean> {

        String codigoOT;

        public void setCodigoOT(String codigoOT) {
            this.codigoOT = codigoOT;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            cargarDatos();
            return true;
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (presentador.getClasificacionVista() == ClasificacionVista.TAREA) {
                asignarLecturasALista(codigoOT);
            } else {
                asignarRevisionesALista(codigoOT);
            }
            ocultarBarraProgreso();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CorreriaFragmentView) {
            correriaFragmentView = (CorreriaFragmentView) context;
        }
    }

    public interface CorreriaFragmentView {
        void refrescarFiltro(String mensaje);

        void cambiarColorBotonConFiltroActivo();

        void cambiarColorBotonConFiltroInactivo();

        void cambiarColorBotonSinFiltro();

        void habilitarFiltro(boolean valor);
    }

    public OrdenTrabajoBusqueda getFiltroOrdenTrabajoBusqueda() {
        return presentador.getOrdenTrabajoBusqueda();
    }
}