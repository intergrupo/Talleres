package com.example.dominio.modelonegocio;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class ListaTareaXOrdenTrabajo extends ArrayList<TareaXOrdenTrabajo> implements Serializable {

    public ListaTareaXOrdenTrabajo(Collection<? extends TareaXOrdenTrabajo> collection) {
        super(collection);
    }

    public ListaTareaXOrdenTrabajo(int capacity) {
        super(capacity);
    }

    public ListaTareaXOrdenTrabajo() {
    }

    public ListaTareaXOrdenTrabajo filtrar(Predicate<? super TareaXOrdenTrabajo> predicate) {
        return new ListaTareaXOrdenTrabajo(Lists.newArrayList(Iterables.filter(this, predicate)));
    }

    public TareaXOrdenTrabajo buscar(Predicate<? super TareaXOrdenTrabajo> predicate) {
        return Iterables.find(this, predicate);
    }

    public int obtenerPosicion(TareaXOrdenTrabajo tareaXOrdenTrabajo) {
        int posicion = -1;
        ListaTareaXOrdenTrabajo lista = filtrar(tareaXOrdenTrabajoActual ->
                tareaXOrdenTrabajoActual.getCodigoOrdenTrabajo().equals(tareaXOrdenTrabajo.getCodigoOrdenTrabajo()) &&
                        tareaXOrdenTrabajoActual.getTarea().getCodigoTarea().equals(tareaXOrdenTrabajo.getTarea().getCodigoTarea()) &&
                        tareaXOrdenTrabajoActual.getCodigoTrabajo().equals(tareaXOrdenTrabajo.getCodigoTrabajo()));
        if (lista.size() > 0) {
            posicion = this.indexOf(lista.get(0));
        }
        return posicion;
    }

    public ListaTareaXOrdenTrabajo clonar() {
        ListaTareaXOrdenTrabajo listaTareaXOrdenTrabajo = new ListaTareaXOrdenTrabajo(this.size());
        listaTareaXOrdenTrabajo.addAll(this);
        return listaTareaXOrdenTrabajo;
    }
}