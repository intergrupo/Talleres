package com.example.santiagolopezgarcia.talleres.presenters;

import com.example.dominio.bussinesslogic.ordentrabajo.OrdenTrabajoBL;
import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.dominio.modelonegocio.TareaXOrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.view.interfaces.BaseView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 10/3/17.
 */

public class MapaTareasPresenter extends Presenter<BaseView> {

    private OrdenTrabajoBL ordenTrabajoBL;

    @Inject
    public MapaTareasPresenter(OrdenTrabajoBL ordenTrabajoBL) {
        this.ordenTrabajoBL = ordenTrabajoBL;
    }

    @Override
    public void iniciar() {

    }

    @Override
    public void detener() {

    }

    public List<OrdenTrabajo> obtenerListaOrdenes(List<TareaXOrdenTrabajo> listaTareaXOrdenTrabajo) {
        List<OrdenTrabajo> listaOrdenes = new ArrayList<>();
        List<String> codigosOT = new ArrayList<>();

        for(TareaXOrdenTrabajo tareaXOrdenTrabajo: listaTareaXOrdenTrabajo){
            if(!codigosOT.contains(tareaXOrdenTrabajo.getCodigoOrdenTrabajo())){
                codigosOT.add(tareaXOrdenTrabajo.getCodigoOrdenTrabajo());
                listaOrdenes.add(tareaXOrdenTrabajo.getOrdenTrabajo());
            }
        }
        return listaOrdenes;
    }
}