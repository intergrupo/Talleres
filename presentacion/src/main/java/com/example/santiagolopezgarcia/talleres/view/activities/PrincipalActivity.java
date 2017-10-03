package com.example.santiagolopezgarcia.talleres.view.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dominio.modelonegocio.ClasificacionVista;
import com.example.dominio.modelonegocio.Correria;
import com.example.dominio.modelonegocio.ListaOpcion;
import com.example.dominio.modelonegocio.Opcion;
import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;
import com.example.dominio.modelonegocio.TareaXTrabajo;
import com.example.dominio.modelonegocio.Usuario;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.presenters.PrincipalPresenter;
import com.example.santiagolopezgarcia.talleres.util.DatosCache;
import com.example.santiagolopezgarcia.talleres.view.Rutina;
import com.example.santiagolopezgarcia.talleres.view.adapters.TabVistaPaginasCorreriaAdapter;
import com.example.santiagolopezgarcia.talleres.view.fragments.CambioCorreriaFragment;
import com.example.santiagolopezgarcia.talleres.view.fragments.CorreriaFragment;
import com.example.santiagolopezgarcia.talleres.view.fragments.InformacionCorreriaFragment;
import com.example.santiagolopezgarcia.talleres.view.fragments.TotalesFragment;
import com.example.santiagolopezgarcia.talleres.view.interfaces.INuevaTareaPopup;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IPrincipalView;
import com.example.santiagolopezgarcia.talleres.view.interfaces.ISincronizacionView;
import com.example.santiagolopezgarcia.talleres.view.interfaces.Item;
import com.example.santiagolopezgarcia.talleres.view.popups.CambioClavePopup;
import com.example.santiagolopezgarcia.talleres.view.popups.SincronizacionPopUp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class PrincipalActivity extends BaseActivity<PrincipalPresenter> implements IPrincipalView,
        DrawerLayout.DrawerListener, CambioCorreriaFragment.OnCambioCorreria,
        CorreriaFragment.CorreriaFragmentView, INuevaTareaPopup, Item<Usuario>, ISincronizacionView {

    @BindView(R.id.view_pager)
    ViewPager vistaPagina;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    private TabVistaPaginasCorreriaAdapter tabVistaPaginasCorreriaAdapter;
    private TextView tvTituloActionBar;
    private TextView tvSubTituloActionBar;
    private ActionBar actionBar;
    private ImageView ivGPS;
    private String titulo;
    private Menu menu;
    private int fragmentoActal;
    private int CREACIONORDENTRABAJO = 1;
    private int CANCELARORDENTRABAJO = 2;
    @BindView(R.id.ContenedorEncabezado)
    RelativeLayout contenedorControles;
    @BindView(R.id.btnFiltrar)
    Button btnFiltrar;
    @BindView(R.id.ivBuscar)
    ImageView ivBuscar;
    @BindView(R.id.ivDerecha)
    ImageView ivDerecha;
    @BindView(R.id.ivIzquierda)
    ImageView ivIzquierda;
    @BindView(R.id.btnOrdenTrabajoActiva)
    ImageButton btnOrdenTrabajoActiva;
    private Usuario usuario;
    private static final int LISTAOT = 3;
    private static final String INSTANCEUSUARIO = "usuario";
    private static final String INSTANCETITULO = "titulo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            refrescarReferencias(savedInstanceState);
        setContentView(R.layout.activity_principal);
        mostrarBarraProgreso();
        ButterKnife.bind(this);
        dependencia.getContenedor().build().inject(this);
        usuario = ((SiriusApp) getApplication()).getUsuario();
        presentador.adicionarVista(this);
        drawer.setDrawerListener(this);
        configurarActionBar();
        obtenerParametros();
        asignarEventos();
        presentador.iniciar();
        fragmentoActal = TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_INFORMACION_CORRERIA;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(INSTANCEUSUARIO, usuario);
        outState.putString(INSTANCETITULO, titulo);
    }

    private void refrescarReferencias(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(INSTANCEUSUARIO))
            usuario = (Usuario) savedInstanceState.getSerializable(INSTANCEUSUARIO);
        if (savedInstanceState.containsKey(INSTANCETITULO))
            titulo = savedInstanceState.getString(INSTANCETITULO);
    }



    private void asignarEventos() {
        vistaPagina.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_LOGIN:
                        tvTituloActionBar.setText(getString(R.string.titulo_cambio_de_correria_mayuscula));
                        ivGPS.setVisibility(View.GONE);
                        guardarDatosCorreria();
                        ivDerecha.setVisibility(View.VISIBLE);
                        ivIzquierda.setVisibility(View.GONE);
                        ivBuscar.setVisibility(View.GONE);
                        btnFiltrar.setVisibility(View.GONE);
                        btnOrdenTrabajoActiva.setVisibility(View.GONE);
                        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.verde)));
                        contenedorControles.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.verde)));
                        borrarMenuDinamico();
                        break;
                    case TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_INFORMACION_CORRERIA:
                        tvTituloActionBar.setText(getString(R.string.titulo_informacion_correria_mayuscula));
                        ivGPS.setVisibility(View.GONE);
                        ivDerecha.setVisibility(View.VISIBLE);
                        ivIzquierda.setVisibility(View.VISIBLE);
                        ivBuscar.setVisibility(View.GONE);
                        btnFiltrar.setVisibility(View.GONE);
                        btnOrdenTrabajoActiva.setVisibility(View.GONE);
                        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.verde)));
                        contenedorControles.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.verde)));
                        borrarMenuDinamico();
                        crearMenudinamico(Opcion.OpcionMenu.INFORMACIONCORRERIA);
                        break;
                    case TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_TOTALES:
                        tvTituloActionBar.setText(getString(R.string.titulo_totales_mayuscula));
                        ivGPS.setVisibility(View.GONE);
                        refrescarInformacionTotales();
                        if (fragmentoActal == TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_INFORMACION_CORRERIA) {
                            guardarDatosCorreria();
                        }
                        ivDerecha.setVisibility(View.VISIBLE);
                        ivIzquierda.setVisibility(View.VISIBLE);
                        ivBuscar.setVisibility(View.GONE);
                        btnFiltrar.setVisibility(View.GONE);
                        btnOrdenTrabajoActiva.setVisibility(View.GONE);
                        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.verde)));
                        contenedorControles.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.verde)));
                        borrarMenuDinamico();
                        break;
                    case TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_LECTURAREVISION:
                        tvTituloActionBar.setText(titulo);
                        ivGPS.setVisibility(View.VISIBLE);
                        ivDerecha.setVisibility(View.GONE);
                        ivIzquierda.setVisibility(View.VISIBLE);
                        ivBuscar.setVisibility(View.VISIBLE);
                        btnFiltrar.setVisibility(View.VISIBLE);
                        btnOrdenTrabajoActiva.setVisibility(View.VISIBLE);
                        presentador.validarVistaCorreria();
                        crearMenudinamico();
                        break;
                    default:
                        break;
                }
                fragmentoActal = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void borrarMenuDinamico() {
        if (menu != null) {
            ListaOpcion listaOpciones = usuario.getPerfil().getListaOpciones();
            for (Opcion opcion : listaOpciones) {
                menu.removeItem(opcion.getIdentificador());
            }
        }
    }

    private void refrescarInformacionTotales() {
        try {
            Fragment fragment = tabVistaPaginasCorreriaAdapter.getItem(TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_TOTALES);
            ((TotalesFragment) fragment).refrescarDatos();
        } catch (Exception e) {}
    }

    private void guardarDatosCorreria() {
        Fragment fragment = tabVistaPaginasCorreriaAdapter.getItem(TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_INFORMACION_CORRERIA);
        String observacion = ((InformacionCorreriaFragment) fragment).obtenerObservacion();
        presentador.guardarObservacion(observacion);
    }

    private void obtenerParametros() {
        Bundle bundle = getIntent().getExtras();
        if ((bundle != null) && bundle.containsKey(Correria.class.getName())) {
            presentador.setCorreria((Correria) bundle.getSerializable(Correria.class.getName()));
        }
        presentador.setPosicionActiva(getIntent().getIntExtra("Posicion", 0));
        presentador.setSINCRONIZACION(getIntent().getBooleanExtra("Sincronizacion", false));
    }

    private void configurarActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.verdeoscuro)));
        LayoutInflater mInflater = LayoutInflater.from(this);
        View vistaActionBar = mInflater.inflate(R.layout.actionbar_ordenes_trabajo, null);
        tvTituloActionBar = (TextView) vistaActionBar.findViewById(R.id.tvTitulo);
        tvTituloActionBar.setText(getString(R.string.titulo_informacion_correria_mayuscula));
        titulo = getString(R.string.titulo_orden_trabajo_mayuscula);
        ivGPS = (ImageView) vistaActionBar.findViewById(R.id.ivGPS);
        ivGPS.setOnClickListener(v -> {
            if (presentador.getClasificacionVista().equals(ClasificacionVista.ORDENTRABAJO)) {
                startActivity(new Intent(getContext(), MapaOrdenesTrabajo.class));
            } else if (presentador.getClasificacionVista().equals(ClasificacionVista.TAREA)) {
                startActivity(new Intent(getContext(), MapaTareas.class));
            }
        });
        tvSubTituloActionBar = (TextView) vistaActionBar.findViewById(R.id.tvSubtitulo);
        actionBar.setCustomView(vistaActionBar);
        actionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void mostrarCorreria(final Correria correria) {
        runOnUiThread(() -> {
            tvSubTituloActionBar.setText(correria.getDescripcion());
            tabVistaPaginasCorreriaAdapter = new TabVistaPaginasCorreriaAdapter(getSupportFragmentManager(),
                    correria, presentador.isSINCRONIZACION() || presentador.getClasificacionVista() == ClasificacionVista.ORDENTRABAJO,
                    presentador.getPosicionActiva());
            vistaPagina.setAdapter(tabVistaPaginasCorreriaAdapter);
            if (presentador.isSINCRONIZACION()) {
                mostrarControlesOrdenTrabajo();
                tabVistaPaginasCorreriaAdapter.setEsVistaXOrdenTrabajo(true);
                vistaPagina.setCurrentItem(TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_LECTURAREVISION);
            } else {
                vistaPagina.setCurrentItem(TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_INFORMACION_CORRERIA);
            }
        });
    }

    @Override
    public void mostrarBarraProgreso() {
        mostrarBarraProgreso(getString(R.string.mensaje_cargando_datos));
    }

    @Override
    public void ocultarBarraProgreso() {
        super.ocultarBarraProgreso();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_correria, menu);
        if (presentador.isSINCRONIZACION()) {
            crearMenudinamico(Opcion.OpcionMenu.CORRERIAOT);
        } else {
            crearMenudinamico(Opcion.OpcionMenu.INFORMACIONCORRERIA);
        }
        return true;
    }

    private void crearMenudinamico() {
        if (presentador.getClasificacionVista() == ClasificacionVista.TAREA) {
            crearMenudinamico(Opcion.OpcionMenu.CORRERIATAREA);
        } else {
            crearMenudinamico(Opcion.OpcionMenu.CORRERIAOT);
        }
    }

    private void crearMenudinamico(Opcion.OpcionMenu opcionMenu) {
        if (menu != null) {
            ListaOpcion listaOpciones;
            listaOpciones = usuario.getPerfil().getListaOpciones().filtrar(opcion ->
                    opcion.getMenu().contains(opcionMenu.getCodigo()));
            for (Opcion opcion : listaOpciones) {
                menu.add(0, opcion.getIdentificador(), opcion.getIdentificador(), opcion.getDescripcion());
            }
        }
    }

    @Override
    public void mostrarControlesTarea() {
        presentador.setClasificacionVista(ClasificacionVista.TAREA);
        titulo = getString(R.string.titulo_tareas_mayuscula);
        runOnUiThread(() -> {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.naranjadoclaro)));
            contenedorControles.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.naranjadoclaro)));
            tvTituloActionBar.setText(titulo);
        });
    }

    public void mostrarVistaTarea() {
        mostrarControlesTarea();
        borrarMenuDinamico();
        crearMenudinamico();
        tabVistaPaginasCorreriaAdapter.setEsVistaXOrdenTrabajo(false);
        Fragment fragment = tabVistaPaginasCorreriaAdapter.getItem(
                TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_LECTURAREVISION);
        String codigoOT;
        if (((CorreriaFragment) fragment).tieneFiltroActivo) {
            codigoOT = DatosCache.getListaOrdenTrabajoFiltrado().get(
                    ((CorreriaFragment) fragment).posicionOrdenTrabajoActiva).getCodigoOrdenTrabajo();
        } else {
            codigoOT = DatosCache.getListaOrdenTrabajo().get(
                    ((CorreriaFragment) fragment).posicionOrdenTrabajoActiva).getCodigoOrdenTrabajo();
        }
        ((CorreriaFragment) fragment).cargarLecturas(codigoOT);
    }

    @Override
    public void mostrarControlesOrdenTrabajo() {
        presentador.setClasificacionVista(ClasificacionVista.ORDENTRABAJO);
        titulo = getString(R.string.titulo_ordenes_trabajo_mayusculas);
        runOnUiThread(() -> {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.verdeoscuro)));
            contenedorControles.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.verdeoscuro)));
            tvTituloActionBar.setText(titulo);
        });
    }

    public void mostrarVistaOrdenTrabajo() {
        mostrarControlesOrdenTrabajo();
        borrarMenuDinamico();
        crearMenudinamico();
        tabVistaPaginasCorreriaAdapter.setEsVistaXOrdenTrabajo(true);
        Fragment fragment = tabVistaPaginasCorreriaAdapter.getItem(
                TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_LECTURAREVISION);
        String codigoOT;
        if (((CorreriaFragment) fragment).tieneFiltroActivo) {
            codigoOT = DatosCache.getListaTareaXOrdenTrabajoFiltrado().get(
                    ((CorreriaFragment) fragment).posicionOrdenTrabajoActiva).getCodigoOrdenTrabajo();
        } else {
            if (!DatosCache.getListaTareaXOrdenTrabajo().isEmpty()) {
                codigoOT = DatosCache.getListaTareaXOrdenTrabajo().get(
                        ((CorreriaFragment) fragment).posicionOrdenTrabajoActiva).getCodigoOrdenTrabajo();
            } else {
                codigoOT = DatosCache.getListaOrdenTrabajo().get(
                        ((CorreriaFragment) fragment).posicionOrdenTrabajoActiva).getCodigoOrdenTrabajo();
            }
        }
        ((CorreriaFragment) fragment).cargarRevisiones(codigoOT);
    }

    public void imprimirDetalleTareas(Opcion opcion) {
//        Intent intent = new Intent(this, ImpresionActivity.class);
//        CorreriaFragment fragment = (CorreriaFragment) tabVistaPaginasCorreriaAdapter.getItem(TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_LECTURAREVISION);
//        intent.putExtra(Opcion.class.getName(), opcion);
//        intent.putExtra("tieneFiltroActivo", fragment.tieneFiltroActivo);
//        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Opcion opcion = usuario.getPerfil().getListaOpciones().get(id);
        switch (Rutina.getRutina(opcion.getRutina())) {
            case NUEVAORDENTRABAJO:
                mostrarCreacionOrdenesTrabajo(opcion);
                break;
            case NUEVATAREA:
                mostrarCreacionTareas(opcion);
                break;
            case ADMINISTRACION:
                mostrarAdministracion(opcion);
                break;
            case CANCELARORDENTRABAJO:
                cancelarOrdenTrabajo(opcion);
                break;
            case CAMBIOCLAVE:
                mostrarCambioClave();
                break;
            case CORRERIAORDENTRABAJO:
                mostrarVistaOrdenTrabajo();
                break;
            case CORRERIATAREA:
                mostrarVistaTarea();
                break;
            case IMPRIMIRDLLETAREAS:
                presentador.mostrarAdvertenciaListado("TAREA", opcion);
                break;
            case DESCARGA_PARCIAL:
                descargaPacial();
                break;
            case ENVIODIRECTOPARCIAL:
                mostrarEnvioDirectoParcial();
                break;
            case ENVIOWEBPARCIAL:
                mostrarEnvioWebParcial();
                break;
            case SINCRONIZACION:
                mostrarSincronizacion();
                break;
            case IMPRIMIRDLLEORDENTRABAJO:
                presentador.mostrarAdvertenciaListado("OT", opcion);
                break;
            default:
                mostrarMensajeError("Esta opcion no tiene rutina");
                break;
        }
        return true;
    }

    public void imprimirDetalleOrdenTrabajo(Opcion opcion) {
//        Intent intent = new Intent(this, ImpresionActivity.class);
//        CorreriaFragment fragment = (CorreriaFragment) tabVistaPaginasCorreriaAdapter.getItem(TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_LECTURAREVISION);
//        intent.putExtra(Opcion.class.getName(), opcion);
//        intent.putExtra("tieneFiltroActivo", fragment.tieneFiltroActivo);
//        startActivity(intent);
    }

    private void mostrarSincronizacion() {
//        Intent intent = new Intent(this, CargaDatosActivity.class);
//        CorreriaFragment fragment = (CorreriaFragment) tabVistaPaginasCorreriaAdapter
//                .getItem(TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_LECTURAREVISION);
//        intent.putExtra("Posicion", fragment.posicionOrdenTrabajoActiva);
//        intent.putExtra("Sincronizacion", true);
//        startActivityForResult(intent, LISTAOT);

        CorreriaFragment fragment = (CorreriaFragment) tabVistaPaginasCorreriaAdapter
                .getItem(TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_LECTURAREVISION);

        SincronizacionPopUp sincronizacionPopUp = new SincronizacionPopUp();
        Bundle args = new Bundle();
        args.putBoolean("Sincronizacion", true);
        args.putInt("Posicion", fragment.posicionOrdenTrabajoActiva);
        sincronizacionPopUp.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        sincronizacionPopUp.show(fragmentManager, "");
    }

    private void mostrarEnvioWebParcial() {
//        Intent intent = new Intent(this, EnvioWebParcialActivity.class);
//        CorreriaFragment fragment = (CorreriaFragment) tabVistaPaginasCorreriaAdapter.getItem(TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_LECTURAREVISION);
//        intent.putExtra(OrdenTrabajoBusqueda.class.getName(), fragment.getFiltroOrdenTrabajoBusqueda());
//        intent.putExtra(Correria.class.getName(), presentador.getCorreria());
//        intent.putExtra("tieneFiltroActivo", fragment.tieneFiltroActivo);
//        startActivity(intent);
    }

    private void mostrarEnvioDirectoParcial() {
//        Intent intent = new Intent(this, EnvioDirectoParcialActivity.class);
//        CorreriaFragment fragment = (CorreriaFragment) tabVistaPaginasCorreriaAdapter.getItem(TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_LECTURAREVISION);
//        intent.putExtra(OrdenTrabajoBusqueda.class.getName(), fragment.getFiltroOrdenTrabajoBusqueda());
//        intent.putExtra(Correria.class.getName(), presentador.getCorreria());
//        intent.putExtra("tieneFiltroActivo", fragment.tieneFiltroActivo);
//        startActivity(intent);
    }


    public void mostrarCambioClave() {
        CambioClavePopup cambioClavePopup = new CambioClavePopup();
        cambioClavePopup.setUsuario(usuario);
        Bundle args = new Bundle();
        FragmentManager fragmentManager = getSupportFragmentManager();
        cambioClavePopup.setCambioClavePopupPopup(this);
        cambioClavePopup.setArguments(args);
        cambioClavePopup.setEsMenu(true);
        cambioClavePopup.show(fragmentManager, "");
    }

    private void cancelarOrdenTrabajo(Opcion opcion) {
//        Intent intent = new Intent(this, CancelarOrdenTrabajoActivity.class);
//        CorreriaFragment fragment = (CorreriaFragment) tabVistaPaginasCorreriaAdapter.getItem(TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_LECTURAREVISION);
//        intent.putExtra(OrdenTrabajo.class.getName(), fragment.getOrdenTrabajoActiva());
//        intent.putExtra(Opcion.class.getName(), opcion);
//        intent.putExtra("posicionOrdenTrabajoActual", fragment.posicionOrdenTrabajoActiva);
//        intent.putExtra("tieneFiltroActivo", fragment.tieneFiltroActivo);
//        startActivityForResult(intent, CANCELARORDENTRABAJO);
    }

    private void descargaPacial() {
        Intent intent = new Intent(this, DescargaDatosParcialActivity.class);
        CorreriaFragment fragment = (CorreriaFragment) tabVistaPaginasCorreriaAdapter.getItem(TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_LECTURAREVISION);
        intent.putExtra(OrdenTrabajoBusqueda.class.getName(), fragment.getFiltroOrdenTrabajoBusqueda());
        intent.putExtra(Correria.class.getName(), presentador.getCorreria());
        intent.putExtra("tieneFiltroActivo", fragment.tieneFiltroActivo);
        startActivity(intent);
    }

    private void mostrarAdministracion(Opcion opcion) {
        Intent intent = new Intent(this, AdministracionActivity.class);
        intent.putExtra(Opcion.class.getName(), opcion);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREACIONORDENTRABAJO || requestCode == CANCELARORDENTRABAJO) {
            if (RESULT_CANCELED != resultCode) {
                CorreriaFragment fragment = (CorreriaFragment) tabVistaPaginasCorreriaAdapter.getItem(TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_LECTURAREVISION);
                if (requestCode == CANCELARORDENTRABAJO){
                    fragment.setPosicionOrdenTrabajoActiva(data.getIntExtra("posicion", 1));
                }
                fragment.obtenerTareaXTrabajoOrdenTrabajoAdapter(DatosCache.getListaTareaXOrdenTrabajo());
                fragment.cargarDatosOnCreateView();
                fragment.posicionar(data.getIntExtra("posicion", 1));
            }
        }
    }

    private void mostrarCreacionOrdenesTrabajo(Opcion opcion) {
//        Intent intent = new Intent(this, NuevaOrdenTrabajoActivity.class);
//        CorreriaFragment fragment = (CorreriaFragment) tabVistaPaginasCorreriaAdapter.getItem(TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_LECTURAREVISION);
//        intent.putExtra("tieneFiltroActivo", fragment.tieneFiltroActivo);
//        intent.putExtra("posicionOrdenTrabajoActual", fragment.posicionOrdenTrabajoActiva);
//        intent.putExtra(Opcion.class.getName(), opcion);
//        startActivityForResult(intent, CREACIONORDENTRABAJO);
    }

    private void mostrarCreacionTareas(Opcion opcion) {
//        CorreriaFragment fragment = (CorreriaFragment) tabVistaPaginasCorreriaAdapter.getItem(TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_LECTURAREVISION);
//        if (fragment.getOrdenTrabajoActiva().getEstado().equals(OrdenTrabajo.EstadoOrdenTrabajo.CANCELADA)) {
//            mostrarMensajeError("La OT " + fragment.getOrdenTrabajoActiva().getCodigoOrdenTrabajo() + " está cancelada.");
//        } else {
//            NuevaTareaPopup nuevaTareaPopup = new NuevaTareaPopup();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            nuevaTareaPopup.setiNuevaTareaPopup(this);
//            nuevaTareaPopup.setOpcion(opcion);
//            nuevaTareaPopup.setOrdenTrabajo(fragment.getOrdenTrabajoActiva());
//            nuevaTareaPopup.setClasificacionVista(presentador.getClasificacionVista());
//            nuevaTareaPopup.show(fragmentManager, "");
//        }
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        findViewById(R.id.left_drawer).setVisibility(View.VISIBLE);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        findViewById(R.id.left_drawer).setVisibility(View.GONE);
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void onCorreriaSeleccionada(Correria correria) {
        cambiarColorBotonSinFiltro();
        presentador.setPosicionActiva(0);
        presentador.setCorreria(correria);
        presentador.iniciar();
    }

    @Override
    public void onBackPressed() {

    }

    @OnClick(R.id.ivBuscar)
    public void mostrarBusqueda(View view) {
        CorreriaFragment fragment = (CorreriaFragment) tabVistaPaginasCorreriaAdapter.getItem(TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_LECTURAREVISION);
        fragment.mostrarBusqueda();
    }

    @OnClick(R.id.btnFiltrar)
    public void filtrar(View view) {
        CorreriaFragment fragment = (CorreriaFragment) tabVistaPaginasCorreriaAdapter.getItem(TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_LECTURAREVISION);
        fragment.filtrar();
    }

    @OnClick(R.id.btnOrdenTrabajoActiva)
    public void iniciarActiva(View view) {
        CorreriaFragment fragment = (CorreriaFragment) tabVistaPaginasCorreriaAdapter.getItem(TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_LECTURAREVISION);
        fragment.iniciarActiva(view);
    }

    @Override
    public void refrescarFiltro(String mensaje) {
        btnFiltrar.setText(mensaje);
    }

    @Override
    public void cambiarColorBotonConFiltroActivo() {
        btnFiltrar.setBackgroundColor(getResources().getColor(R.color.naranjado));
        Drawable drawable = getContext().getResources().getDrawable(R.mipmap.ic_filter_blanco);
        drawable.setBounds(0, 0, 60, 60);
        btnFiltrar.setCompoundDrawables(drawable, null, null, null);
    }

    @Override
    public void cambiarColorBotonConFiltroInactivo() {
        btnFiltrar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        Drawable drawable = getContext().getResources().getDrawable(R.mipmap.ic_filter_naranja);
        drawable.setBounds(0, 0, 60, 60);
        btnFiltrar.setCompoundDrawables(drawable, null, null, null);
    }

    @Override
    public void cambiarColorBotonSinFiltro() {
        btnFiltrar.setBackgroundColor(Color.TRANSPARENT);
        btnFiltrar.setCompoundDrawables(null, null, null, null);
    }

    @Override
    public void habilitarFiltro(boolean valor) {
        btnFiltrar.setEnabled(valor);
    }

    @OnClick(R.id.ivDerecha)
    public void navegarALaDerecha(View view) {
        vistaPagina.setCurrentItem(vistaPagina.getCurrentItem() + 1);
    }

    @OnClick(R.id.ivIzquierda)
    public void navegarALaIzquierda(View view) {
        vistaPagina.setCurrentItem(vistaPagina.getCurrentItem() - 1);
    }

    @Override
    public boolean onTareaXTrabajoACrear(TareaXTrabajo tareaXTrabajo) {
//        if (presentador.crearTareaXOrdenTrabajo(tareaXTrabajo)) {
//            this.presentador.getOrdenTrabajoActiva().getTrabajoXOrdenTrabajo().getTareaXOrdenTrabajoList().clear();
//            this.vistaPagina.getAdapter().notifyDataSetChanged();
//            return true;
//        }
        return false;
    }

    @Override
    public void itemProcesado(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void regresarAPantallaPrincipal(int posicion) {
        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.putExtra(Correria.class.getName(),
                presentador.getCorreria());
        intent.putExtra("Sincronizacion", true);
        intent.putExtra("Posicion", posicion);
        finish();
        startActivity(intent);
//        mostrarControlesOrdenTrabajo();
//        tabVistaPaginasCorreriaAdapter.setEsVistaXOrdenTrabajo(true);
//        vistaPagina.setCurrentItem(TabVistaPaginasCorreriaAdapter.IDENTIFICADOR_LECTURAREVISION);
    }
}