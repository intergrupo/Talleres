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

public class ListaTareas extends ArrayList<Tarea> implements Serializable {

    public ListaTareas(Collection<? extends Tarea> collection) {
        super(collection);
    }

    public ListaTareas() {
    }

    public ListaTareas filtrar(Predicate<? super Tarea> predicate) {
        return new ListaTareas(Lists.newArrayList(Iterables.filter(this, predicate)));
    }

    public int obtenerPosicion(Tarea tarea) {
        int posicion = -1;
        if (tarea.getCodigoTarea().isEmpty())
            return posicion;
        for (Tarea tareaActual : this) {
            posicion++;
            if (tareaActual.getCodigoTarea().equals(tarea.getCodigoTarea()))
                break;
        }
        return posicion;
    }
}