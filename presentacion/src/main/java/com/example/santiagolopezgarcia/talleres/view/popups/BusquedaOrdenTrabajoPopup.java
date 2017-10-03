package com.example.santiagolopezgarcia.talleres.view.popups;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.dominio.bussinesslogic.acceso.MultiOpcionBL;
import com.example.dominio.bussinesslogic.correria.CorreriaBL;
import com.example.dominio.bussinesslogic.ordentrabajo.EstadoBL;
import com.example.dominio.bussinesslogic.tarea.TareaBL;
import com.example.dominio.bussinesslogic.tarea.TareaXTrabajoBL;
import com.example.dominio.bussinesslogic.trabajo.TrabajoBL;
import com.example.dominio.modelonegocio.Correria;
import com.example.dominio.modelonegocio.ListaEstadoTarea;
import com.example.dominio.modelonegocio.ListaTareaXTrabajo;
import com.example.dominio.modelonegocio.ListaTrabajo;
import com.example.dominio.modelonegocio.MultiOpcion;
import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;
import com.example.dominio.modelonegocio.ParametrosCorreria;
import com.example.dominio.modelonegocio.Tarea;
import com.example.dominio.modelonegocio.TareaXTrabajo;
import com.example.dominio.modelonegocio.Trabajo;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;
import com.example.santiagolopezgarcia.talleres.view.adapters.EstadoTareaSpinnerAdapter;
import com.example.santiagolopezgarcia.talleres.view.adapters.MultiOpcionSpinnerAdapter;
import com.example.santiagolopezgarcia.talleres.view.adapters.TareaTrabajoSpinnerAdapter;
import com.example.santiagolopezgarcia.talleres.view.adapters.TrabajoSpinnerAdapter;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IBusquedaOrdenTrabajoPopup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class BusquedaOrdenTrabajoPopup extends BasePopup {

    @BindView(R.id.cbTodos)
    CheckBox cbTodos;
    @BindView(R.id.cbNoDescargados)
    CheckBox cbNoDescargados;
    @BindView(R.id.btnCancelar)
    Button btnCancelar;
    @BindView(R.id.btnBuscar)
    Button btnBuscar;
    @BindView(R.id.etCodigoOrdenTrabajo)
    EditText etCodigoOrdenTrabajo;
    @BindView(R.id.etCodigo)
    EditText etCodigo;
    @BindView(R.id.etElemento)
    EditText etElemento;
    @BindView(R.id.etDireccion)
    EditText etDireccion;
    @BindView(R.id.etCliente)
    EditText etCliente;
    @BindView(R.id.spTrabajo)
    Spinner spTrabajo;
    @BindView(R.id.spTipoUso)
    Spinner spTarea;
    @BindView(R.id.spEstadoTarea)
    Spinner spEstadoTarea;
    @BindView(R.id.spSeleccion)
    Spinner spSeleccion;
    private OrdenTrabajoBusqueda ordenTrabajoABuscar;
    private IBusquedaOrdenTrabajoPopup busquedaOrdenTrabajoPopup;
    @Inject
    TareaBL tareaBL;
    @Inject
    EstadoBL estadoBL;
    @Inject
    TrabajoBL trabajoBL;
    @Inject
    MultiOpcionBL multiOpcionBL;
    @Inject
    CorreriaBL correriaBL;
    @Inject
    TareaXTrabajoBL tareaXTrabajoBL;
    @BindView(R.id.ibLimpiar)
    ImageView ibLimpiar;
    private Correria correria;
    private ParametrosCorreria parametrosCorreria;
    private ListaTrabajo listaTrabajo;
    private ListaTareaXTrabajo listaTareasXTrabajo;
    private List<Tarea.EstadoTarea> listaEstadoTareas;
    private ContenedorDependencia dependencia;
    private TrabajoSpinnerAdapter trabajoSpinnerAdapter;
    private EstadoTareaSpinnerAdapter estadoTareaSpinnerAdapter;
    private boolean seleccionarItemTarea;
    private ProgressDialog dialogoBarraProgreso;
    private boolean esSeleccionarTareaXTrabajo;
    private boolean esSeleccionarEstado;

    public void setOrdenTrabajoABuscar(OrdenTrabajoBusqueda ordenTrabajoABuscar) {
        this.ordenTrabajoABuscar = ordenTrabajoABuscar;
    }

    private void inicializarInyeccionDependencias() {
        dependencia = new ContenedorDependencia(getActivity().getApplication());
        dependencia.getContenedor().build().inject(this);
    }

    public void setBusquedaOrdenTrabajoPopup(IBusquedaOrdenTrabajoPopup busquedaOrdenTrabajoPopup) {
        this.busquedaOrdenTrabajoPopup = busquedaOrdenTrabajoPopup;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inicializarInyeccionDependencias();
        return iniciarDialogo();
    }

    @NonNull
    private Dialog iniciarDialogo() {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_busqueda_orden_trabajo);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this, dialog.getWindow().getDecorView());
        obtenerDatos();
        asignarDatos();
        return dialog;
    }

    private void obtenerDatos() {
        correria = correriaBL.cargar(((SiriusApp) getActivity().getApplication()).getCodigoCorreria());
        parametrosCorreria = new ParametrosCorreria(correria.getParametros());
        listaEstadoTareas = new ArrayList<>();
        listaTrabajo = trabajoBL.cargarTrabajos();
        listaTareasXTrabajo = new ListaTareaXTrabajo();
    }

    private void asignarDatos() {
        trabajoSpinnerAdapter = new TrabajoSpinnerAdapter(getContext(), listaTrabajo, true, false);
        spTrabajo.setAdapter(trabajoSpinnerAdapter);
        spSeleccion.setAdapter(new MultiOpcionSpinnerAdapter(getContext(),
                multiOpcionBL.cargarListaMultiOpcionPorTipo(parametrosCorreria.getParametrosOpcionLlave2().getCodigoLlave2()),
                parametrosCorreria.getParametrosOpcionLlave2().getDescripcionLlave2()));
        if(parametrosCorreria.getParametrosOpcionLlave2().getCodigoLlave2().isEmpty()
                && parametrosCorreria.getParametrosOpcionLlave2().getDescripcionLlave2().isEmpty())
            spSeleccion.setVisibility(View.INVISIBLE);
        if (!ordenTrabajoABuscar.esVacio()) {
            etCodigoOrdenTrabajo.setText(ordenTrabajoABuscar.getCodigoOrdenTrabajo());
            seleccionarItemTarea = true;
            esSeleccionarEstado = true;
            esSeleccionarTareaXTrabajo = true;
            int posicionTrabajo = listaTrabajo.obtenerPosicion(ordenTrabajoABuscar.getTareaXTrabajo().getTrabajo());
            if (posicionTrabajo > 0) {
                spTrabajo.setSelection(posicionTrabajo);
            } else {
                spTrabajo.setSelection(0);
            }

            int posicionLlave2 = obtenerPosicionLlave2(ordenTrabajoABuscar);
            if (posicionLlave2 > 0) {
                spSeleccion.setSelection(posicionLlave2);
            } else {
                spSeleccion.setSelection(0);
            }
            etDireccion.setText(ordenTrabajoABuscar.getDireccion());
            etCliente.setText(ordenTrabajoABuscar.getCliente());
            etCodigo.setText(ordenTrabajoABuscar.getCodigoLlave1());
            cbTodos.setChecked(ordenTrabajoABuscar.isTodos());
            cbNoDescargados.setChecked(ordenTrabajoABuscar.isNoDescargados());
            etElemento.setText(ordenTrabajoABuscar.getSerieElemento());
        }
    }

    private void limpiar() {
        spTrabajo.setAdapter(trabajoSpinnerAdapter);
        spTarea.setAdapter(new TareaTrabajoSpinnerAdapter(getContext(), new ArrayList<>(), true));
        spEstadoTarea.setAdapter(new EstadoTareaSpinnerAdapter(getContext(), new ArrayList<>()));
        spSeleccion.setAdapter(new MultiOpcionSpinnerAdapter(getContext(), new ArrayList<>(),
                parametrosCorreria.getParametrosOpcionLlave2().getDescripcionLlave2()));
        etCodigoOrdenTrabajo.getText().clear();
        etDireccion.getText().clear();
        etCliente.getText().clear();
        etCodigo.getText().clear();
        etElemento.getText().clear();
        cbTodos.setChecked(false);
        cbNoDescargados.setChecked(false);
    }

    @OnClick(R.id.btnCancelar)
    public void cancelar(View view) {
        if (ordenTrabajoABuscar.isLimpiar())
            busquedaOrdenTrabajoPopup.buscar(ordenTrabajoABuscar);
        ordenTrabajoABuscar.setLimpiar(false);
        this.dismiss();
    }

    @OnClick(R.id.ibLimpiar)
    public void limpiar(View view) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 8);
        animation.setDuration(150);
        view.startAnimation(animation);
        ordenTrabajoABuscar = new OrdenTrabajoBusqueda();
        limpiar();
        ordenTrabajoABuscar.setLimpiar(true);
    }

    @OnItemSelected(R.id.spTrabajo)
    public void onItemSelectedTrabajo(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0) {
            cargarTareas((Trabajo) parent.getItemAtPosition(position));
            cargarEstadoTareas();
        } else {
            spTarea.setAdapter(new TareaTrabajoSpinnerAdapter(getContext(), new ArrayList<>(), true));
            spEstadoTarea.setAdapter(new EstadoTareaSpinnerAdapter(getContext(), new ArrayList<>()));
        }
    }

    @OnItemSelected(R.id.spTipoUso)
    public void onItemSelectedTarea(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0) {
            cargarEstadosXTarea((TareaXTrabajo) parent.getItemAtPosition(position));
        }
    }

    @OnCheckedChanged(R.id.cbNoDescargados)
    public void checkNoDescargadas(CompoundButton compoundButton, boolean b){
        ordenTrabajoABuscar.setNoDescargados(b);
    }

    private void cargarEstadosXTarea(TareaXTrabajo tareaXTrabajo) {
        ListaEstadoTarea listaEstadoTarea;
        if(tareaXTrabajo.getTarea().getRutina().equals(Tarea.RutinaTarea.TAREALECTURA.getCodigo())){
            listaEstadoTarea = tareaBL.cargarEstadosTarea(estadoBL.cargarEstadoTareasLectura());
        }else {
            listaEstadoTarea = tareaBL.cargarEstadosTarea(estadoBL.cargarEstadoTareasRevisiones());
        }

        int posicion = listaEstadoTarea.obtenerPosicion(Tarea.EstadoTarea.CRITICADACAMBIABLE);
        if (posicion != -1) {
            listaEstadoTarea.remove(posicion);
        }
        estadoTareaSpinnerAdapter = new EstadoTareaSpinnerAdapter(getContext(), listaEstadoTarea);
        spEstadoTarea.setAdapter(estadoTareaSpinnerAdapter);
        if (esSeleccionarTareaXTrabajo) {
            int posicionEstadoTarea = listaEstadoTarea.obtenerPosicion(ordenTrabajoABuscar.getEstadoTarea());
            if (posicionEstadoTarea > 0) {
                spEstadoTarea.setSelection(posicionEstadoTarea);
            } else {
                spEstadoTarea.setSelection(0);
            }
            esSeleccionarTareaXTrabajo = false;
        }
    }

    private void cargarTareas(Trabajo trabajo) {
        listaTareasXTrabajo = tareaXTrabajoBL.cargarTareasXTrabajo(trabajo.getCodigoTrabajo());
        spTarea.setAdapter(new TareaTrabajoSpinnerAdapter(getContext(), listaTareasXTrabajo, true));
        if (seleccionarItemTarea) {
            int posicionTarea = listaTareasXTrabajo.obtenerPosicion(ordenTrabajoABuscar.getTareaXTrabajo());
            if (posicionTarea > 0) {
                spTarea.setSelection(posicionTarea);
            } else {
                spTarea.setSelection(0);
            }
            seleccionarItemTarea = false;
        }
    }

    private void cargarEstadoTareas() {
        ListaEstadoTarea listaEstadoTarea = tareaBL.cargarEstadosTarea(estadoBL.cargarEstadoTareas());
        int posicion = listaEstadoTarea.obtenerPosicion(Tarea.EstadoTarea.CRITICADACAMBIABLE);
        if (posicion != -1) {
            listaEstadoTarea.remove(posicion);
        }
        estadoTareaSpinnerAdapter = new EstadoTareaSpinnerAdapter(getContext(), listaEstadoTarea);
        spEstadoTarea.setAdapter(estadoTareaSpinnerAdapter);
        if (esSeleccionarEstado) {
            int posicionEstadoTarea = listaEstadoTarea.obtenerPosicion(ordenTrabajoABuscar.getEstadoTarea());
            if (posicionEstadoTarea > 0) {
                spEstadoTarea.setSelection(posicionEstadoTarea);
            } else {
                spEstadoTarea.setSelection(0);
            }
            esSeleccionarEstado = false;
        }
    }

    @OnClick(R.id.btnBuscar)
    public void iniciarBusqueda(View view) {
        dialogoBarraProgreso = new ProgressDialog(getContext());
        dialogoBarraProgreso.setCancelable(false);
        dialogoBarraProgreso.setMessage("Buscando...");
        dialogoBarraProgreso.show();
        Thread thread = new Thread(() -> {
            ordenTrabajoABuscar = obtenerDatosBusqueda();
            if (!ordenTrabajoABuscar.esVacio()) {
                if (busquedaOrdenTrabajoPopup.buscar(ordenTrabajoABuscar)) {
                    dismiss();
                } else {
                    mostrarMensajeError("El filtro no obtuvo resultados");
                }
            } else {
                mostrarMensajeError("No tiene criterios de búsqueda");
            }
            dialogoBarraProgreso.dismiss();
        });
        thread.setName("Hilo aplicar filtro");
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    @OnCheckedChanged(R.id.cbTodos)
    void seleccionarTodos(boolean checked) {
        spTrabajo.setEnabled(!checked);
        spTarea.setEnabled(!checked);
        spEstadoTarea.setEnabled(!checked);
        spSeleccion.setEnabled(!checked);
        etCodigoOrdenTrabajo.setEnabled(!checked);
        etDireccion.setEnabled(!checked);
        etCliente.setEnabled(!checked);
        etCodigo.setEnabled(!checked);
        cbNoDescargados.setEnabled(!checked);
        etElemento.setEnabled(!checked);
    }

    private OrdenTrabajoBusqueda obtenerDatosBusqueda() {
        OrdenTrabajoBusqueda ordenTrabajoABuscar = new OrdenTrabajoBusqueda();
        ordenTrabajoABuscar.setCodigoOrdenTrabajo(etCodigoOrdenTrabajo.getText().toString());
        ordenTrabajoABuscar.setTareaXTrabajo((TareaXTrabajo) spTarea.getSelectedItem());
        ordenTrabajoABuscar.getTareaXTrabajo().setTrabajo((Trabajo) spTrabajo.getSelectedItem());
        ordenTrabajoABuscar.setEstadoTarea((Tarea.EstadoTarea) spEstadoTarea.getSelectedItem());
        ordenTrabajoABuscar.setDireccion(etDireccion.getText().toString());
        ordenTrabajoABuscar.setCliente(etCliente.getText().toString());
        ordenTrabajoABuscar.setCodigoLlave1(etCodigo.getText().toString());
        if(spSeleccion.getSelectedItemPosition() > 0) {
            ordenTrabajoABuscar.setCodigoLlave2(((MultiOpcion) spSeleccion.getSelectedItem()).getCodigoOpcion());
        }
        ordenTrabajoABuscar.setTodos(cbTodos.isChecked());
        ordenTrabajoABuscar.setNoDescargados(cbNoDescargados.isChecked());
        ordenTrabajoABuscar.setSerieElemento(etElemento.getText().toString());
        return ordenTrabajoABuscar;
    }

    public int obtenerPosicionLlave2(OrdenTrabajoBusqueda ordenTrabajoABuscar) {
        int posicion = 0;
        ParametrosCorreria parametrosCorreria = new ParametrosCorreria(correria.getParametros());
        MultiOpcion multiOpcionLlave2 = multiOpcionBL.cargarMultiOpcion(parametrosCorreria.getParametrosOpcionLlave2().getCodigoLlave2(),
                ordenTrabajoABuscar.getCodigoLlave2());
        if (!multiOpcionLlave2.esClaveLlena())
            return posicion;
        for (MultiOpcion multiOpcion : multiOpcionBL.cargarListaMultiOpcionPorTipo(
                parametrosCorreria.getParametrosOpcionLlave2().getCodigoLlave2())) {
            posicion++;
            if (multiOpcion.getCodigoOpcion().equals(multiOpcionLlave2.getCodigoOpcion())
                    && multiOpcion.getCodigoTipoOpcion().equals(multiOpcionLlave2.getCodigoTipoOpcion()))
                break;
        }
        return posicion;
    }
}