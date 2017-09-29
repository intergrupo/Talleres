package com.example.santiagolopezgarcia.talleres.util;

import com.example.dominio.modelonegocio.ListaOrdenTrabajo;
import com.example.dominio.modelonegocio.ListaTareaXOrdenTrabajo;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class DatosCache {

    private static ListaOrdenTrabajo listaOrdenTrabajo;
    private static ListaOrdenTrabajo listaOrdenTrabajoFiltrado;
    private static ListaTareaXOrdenTrabajo listaTareaXOrdenTrabajo;
    private static ListaTareaXOrdenTrabajo listaTareaXOrdenTrabajoFiltrado;

    public static ListaOrdenTrabajo getListaOrdenTrabajo() {
        return listaOrdenTrabajo;
    }

    public static void setListaOrdenTrabajo(ListaOrdenTrabajo listaOrdenTrabajo) {
        DatosCache.listaOrdenTrabajo = listaOrdenTrabajo;
    }

    public static ListaOrdenTrabajo getListaOrdenTrabajoFiltrado() {
        return listaOrdenTrabajoFiltrado;
    }

    public static void setListaOrdenTrabajoFiltrado(ListaOrdenTrabajo listaOrdenTrabajoFiltrado) {
        DatosCache.listaOrdenTrabajoFiltrado = listaOrdenTrabajoFiltrado;
    }

    public static ListaTareaXOrdenTrabajo getListaTareaXOrdenTrabajo() {
        return listaTareaXOrdenTrabajo;
    }

    public static void setListaTareaXOrdenTrabajo(ListaTareaXOrdenTrabajo listaTareaXOrdenTrabajo) {
        DatosCache.listaTareaXOrdenTrabajo = listaTareaXOrdenTrabajo;
    }

    public static ListaTareaXOrdenTrabajo getListaTareaXOrdenTrabajoFiltrado() {
        return listaTareaXOrdenTrabajoFiltrado;
    }

    public static void setListaTareaXOrdenTrabajoFiltrado(ListaTareaXOrdenTrabajo listaTareaXOrdenTrabajoFiltrado) {
        DatosCache.listaTareaXOrdenTrabajoFiltrado = listaTareaXOrdenTrabajoFiltrado;
    }

    public static void limpiar(){
        if(listaOrdenTrabajo!=null){
            listaOrdenTrabajo.clear();
        }
        if(listaOrdenTrabajoFiltrado!=null){
            listaOrdenTrabajoFiltrado.clear();
        }
        if(listaTareaXOrdenTrabajo!=null){
            listaTareaXOrdenTrabajo.clear();
        }

        if(listaTareaXOrdenTrabajoFiltrado!=null){
            listaTareaXOrdenTrabajoFiltrado.clear();
        }
    }
}