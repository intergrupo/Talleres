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

public class ListaEstadoTarea extends ArrayList<Tarea.EstadoTarea> implements Serializable {

    public ListaEstadoTarea(Collection<? extends Tarea.EstadoTarea> collection) {
        super(collection);
    }

    public ListaEstadoTarea() {
    }

    public ListaEstadoTarea filtrar(Predicate<? super Tarea.EstadoTarea> predicate) {
        return new ListaEstadoTarea(Lists.newArrayList(Iterables.filter(this, predicate)));
    }

    public int obtenerPosicion(Tarea.EstadoTarea estadoTareaActual) {
        int posicion = -1;
        if (Tarea.EstadoTarea.NINGUNA.equals(estadoTareaActual))
            return posicion;
        for (Tarea.EstadoTarea estadoTarea : this) {
            posicion++;
            if (estadoTarea.equals(estadoTareaActual))
                break;
        }
        return posicion;
    }
}
