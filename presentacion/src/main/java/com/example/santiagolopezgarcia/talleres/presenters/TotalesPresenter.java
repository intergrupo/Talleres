package com.example.santiagolopezgarcia.talleres.presenters;

import com.example.dominio.bussinesslogic.ordentrabajo.EstadoBL;
import com.example.dominio.bussinesslogic.ordentrabajo.OrdenTrabajoBL;
import com.example.dominio.bussinesslogic.tarea.TareaXOrdenTrabajoBL;
import com.example.dominio.bussinesslogic.trabajo.TrabajoBL;
import com.example.dominio.modelonegocio.ListaTrabajo;
import com.example.dominio.modelonegocio.TotalTarea;
import com.example.dominio.modelonegocio.TotalTareaXEstado;
import com.example.dominio.modelonegocio.Trabajo;
import com.example.santiagolopezgarcia.talleres.view.interfaces.ITotalesView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class TotalesPresenter extends Presenter<ITotalesView> {

    private final String NOMBREHILOCARGADATOS = "HiloCargaDatosTotalPresenter";
    private TrabajoBL trabajoBL;
    private OrdenTrabajoBL ordenTrabajoBL;
    private EstadoBL estadoBL;
    private List<TotalTarea> listaTotalesPorTarea;
    private String codigoCorreria;
    private TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL;

    public void setCodigoCorreria(String codigoCorreria) {
        this.codigoCorreria = codigoCorreria;
    }

    @Inject
    public TotalesPresenter(TrabajoBL trabajoBL, OrdenTrabajoBL ordenTrabajoBL, EstadoBL estadoBL,
                            TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL) {
        this.trabajoBL = trabajoBL;
        this.ordenTrabajoBL = ordenTrabajoBL;
        this.estadoBL = estadoBL;
        this.tareaXOrdenTrabajoBL = tareaXOrdenTrabajoBL;
        listaTotalesPorTarea = new ArrayList<>();
    }

    @Override
    public void iniciar() {
        obtenerDatos();
    }

    private void obtenerDatos() {
        vista.mostrarBarraProgreso();
        Thread thread = new Thread(() -> {
            try {
                List<String> listaCodigoTrabajos = ordenTrabajoBL.cargarTrabajosAgrupados(codigoCorreria);
                ListaTrabajo listaTrabajos = trabajoBL.cargarTrabajos(listaCodigoTrabajos);
                vista.mostrarListaTrabajos(listaTrabajos);
                vista.mostrarTotales(ordenTrabajoBL.totalOrdenesTrabajo(codigoCorreria));
                vista.ocultarBarraProgreso();
            } catch (Exception exc) {
                try {
                    vista.mostrarMensaje("Ocurrio un error, porfavor intente de nuevo","N");
                    vista.registrarLog(exc.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                vista.ocultarBarraProgreso();
            }
        });
        thread.setName(NOMBREHILOCARGADATOS);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    @Override
    public void detener() {
        if (listaTotalesPorTarea != null)
            listaTotalesPorTarea.clear();
    }

    public void obtenerTareasXOrdenTrabajo(Trabajo trabajo) {
        vista.mostrarBarraProgreso();
        listaTotalesPorTarea.clear();
        listaTotalesPorTarea = tareaXOrdenTrabajoBL.cargarTotalesPorTarea(codigoCorreria, trabajo.getCodigoTrabajo());
        int sumaCantidad = 0;
        for (TotalTarea total : listaTotalesPorTarea) {
            sumaCantidad += total.getCantidad();
            for (TotalTareaXEstado totalTareaXEstado : total.getListaTareaXEstado()) {
                totalTareaXEstado.setEstado(estadoBL.cargarEstado(totalTareaXEstado.getEstado().getCodigoEstado()));
            }
        }
        vista.mostrarTotalTarea(listaTotalesPorTarea, sumaCantidad);
        vista.ocultarBarraProgreso();
    }
}
