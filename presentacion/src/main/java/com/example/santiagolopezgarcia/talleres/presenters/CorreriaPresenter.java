package com.example.santiagolopezgarcia.talleres.presenters;

import com.example.dominio.modelonegocio.ClasificacionVista;
import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;
import com.example.dominio.modelonegocio.TareaXOrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.util.PosicionesTareasYOTS;
import com.example.santiagolopezgarcia.talleres.view.interfaces.ICorreriaView;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class CorreriaPresenter  extends Presenter<ICorreriaView>   {

    private ClasificacionVista clasificacionVista;
    private OrdenTrabajo ordenTrabajoActiva;
    private TareaXOrdenTrabajo tareaXOrdenTrabajoActiva;
    private OrdenTrabajoBusqueda ordenTrabajoBusqueda;
    private int posicionActiva;

    @Inject
    public CorreriaPresenter()
    {
        ordenTrabajoBusqueda = new OrdenTrabajoBusqueda();
    }

    public ClasificacionVista getClasificacionVista() {
        return clasificacionVista;
    }

    public void setClasificacionVista(ClasificacionVista clasificacionVista) {
        this.clasificacionVista = clasificacionVista;
    }

    public int getPosicionActiva() {
        return posicionActiva;
    }

    public void setPosicionActiva(int posicionActiva) {
        this.posicionActiva = posicionActiva;
    }

    public OrdenTrabajo getOrdenTrabajoActiva() {
        return ordenTrabajoActiva;
    }

    public void setOrdenTrabajoActiva(OrdenTrabajo ordenTrabajoActiva) {
        this.ordenTrabajoActiva = ordenTrabajoActiva;
    }

    public TareaXOrdenTrabajo getTareaXOrdenTrabajoActiva() {
        return tareaXOrdenTrabajoActiva;
    }

    public void setTareaXOrdenTrabajoActiva(TareaXOrdenTrabajo tareaXOrdenTrabajoActiva) {
        this.tareaXOrdenTrabajoActiva = tareaXOrdenTrabajoActiva;
    }

    public OrdenTrabajoBusqueda getOrdenTrabajoBusqueda() {
        return ordenTrabajoBusqueda;
    }

    public void setOrdenTrabajoBusqueda(OrdenTrabajoBusqueda ordenTrabajoBusqueda) {
        this.ordenTrabajoBusqueda = ordenTrabajoBusqueda;
    }

    public int obtenerPosicionTareaXOT(boolean tieneFiltroActivo, String codigoOT) {
        return PosicionesTareasYOTS.obtenerPosicionTareaXOT(tieneFiltroActivo, codigoOT);
    }

    public int obtenerPosicionOT(boolean tieneFiltroActivo, String codigoOT) {
        return PosicionesTareasYOTS.obtenerPosicionOT(tieneFiltroActivo, codigoOT);
    }



    public int obtenerPosicionTarea(boolean tieneFiltroActivo, TareaXOrdenTrabajo tareaXOrdenTrabajoActiva) {
        return PosicionesTareasYOTS.obtenerPosicionTarea(tieneFiltroActivo, tareaXOrdenTrabajoActiva);
    }

    @Override
    public void iniciar() {

    }

    @Override
    public void detener() {

    }
}