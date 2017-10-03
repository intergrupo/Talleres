package com.example.santiagolopezgarcia.talleres.presenters;

import com.example.dominio.bussinesslogic.administracion.TalleresBL;
import com.example.dominio.bussinesslogic.correria.CorreriaBL;
import com.example.dominio.bussinesslogic.ordentrabajo.OrdenTrabajoBL;
import com.example.dominio.modelonegocio.ClasificacionVista;
import com.example.dominio.modelonegocio.Correria;
import com.example.dominio.modelonegocio.ListaOpcion;
import com.example.dominio.modelonegocio.Opcion;
import com.example.dominio.modelonegocio.ParametrosCorreria;
import com.example.dominio.modelonegocio.Talleres;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.view.activities.PrincipalActivity;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IPrincipalView;
import com.example.santiagolopezgarcia.talleres.view.popups.AlertaPopUp;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class PrincipalPresenter extends Presenter<IPrincipalView> {

    private final String NOMBREHILOCARGADATOS = "HiloCargaDatosCorreriaPresenter";
    private CorreriaBL correriaBL;
    private OrdenTrabajoBL ordenTrabajoBL;
    private Correria correria;
    private ClasificacionVista clasificacionVista;
    private boolean SINCRONIZACION;
    private int posicionActiva;
    private TalleresBL talleresBL;
    private Talleres talleres;
    private ListaOpcion listaOpciones;

    public Correria getCorreria() {
        return correriaBL.cargar(correria.getCodigoCorreria());
    }

    public void setCorreria(Correria correria) {
        this.correria = correria;
    }

    @Inject
    public PrincipalPresenter(CorreriaBL correriaBL, OrdenTrabajoBL ordenTrabajoBL, TalleresBL talleresBL) {
        this.correriaBL = correriaBL;
        this.ordenTrabajoBL = ordenTrabajoBL;
        clasificacionVista = ClasificacionVista.NINGUNA;
        this.talleresBL = talleresBL;
    }

    public boolean isSINCRONIZACION() {
        return SINCRONIZACION;
    }

    public void setSINCRONIZACION(boolean SINCRONIZACION) {
        this.SINCRONIZACION = SINCRONIZACION;
    }

    @Override
    public void iniciar() {
        Thread thread = new Thread(() -> {
            talleres = talleresBL.cargarPrimerRegistro();
            if (correria != null) {
                ParametrosCorreria parametrosCorreria = new ParametrosCorreria(correria.getParametros());
                if (parametrosCorreria.getTipoVizualizacion() == ParametrosCorreria.TipoVizualizacion.Tarea) {
                    vista.mostrarControlesTarea();
                } else {
                    vista.mostrarControlesOrdenTrabajo();
                }
                vista.mostrarCorreria(correria);
            } else
                vista.mostrarMensaje("No se logró cargar la correria.", talleres.getLog());
            vista.ocultarBarraProgreso();
        });
        thread.setName(NOMBREHILOCARGADATOS);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    public int getPosicionActiva() {
        return posicionActiva;
    }

    public void setPosicionActiva(int posicionActiva) {
        this.posicionActiva = posicionActiva;
    }

    public void validarVistaCorreria() {
        if (clasificacionVista.equals(ClasificacionVista.TAREA)) {
            vista.mostrarControlesTarea();
        } else if (clasificacionVista.equals(ClasificacionVista.ORDENTRABAJO)) {
            vista.mostrarControlesOrdenTrabajo();
        }
    }

    @Override
    public void detener() {
    }

    public void guardarObservacion(String observacion) {
        correria = correriaBL.cargar(correria.getCodigoCorreria());
        correria.setObservacion(observacion);
        this.correriaBL.actualizar(correria);
    }

    public ClasificacionVista getClasificacionVista() {
        return clasificacionVista;
    }

    public void setClasificacionVista(ClasificacionVista clasificacionVista) {
        this.clasificacionVista = clasificacionVista;
    }

    public void mostrarAdvertenciaListado(String vista, Opcion opcion) {
        AlertaPopUp alertaPopUp = new AlertaPopUp();
        alertaPopUp.setTitle(R.string.titulo_advertecia);
        alertaPopUp.setMessage(vista.equals("OT") ? R.string.advertencia_listado_ot : R.string.advertencia_listado_tarea);
        alertaPopUp.setContext(this.vista.getContext());
        alertaPopUp.setPositiveButton("SÍ", (dialog, id) -> {
            if(vista.equals("OT")) {
                ((PrincipalActivity) this.vista.getContext()).imprimirDetalleOrdenTrabajo(opcion);
            }else {
                ((PrincipalActivity) this.vista.getContext()).imprimirDetalleTareas(opcion);
            }
            dialog.dismiss();

        }).setNegativeButton("NO", (dialog, id) -> {
            dialog.dismiss();

        });
        alertaPopUp.show();
    }


}