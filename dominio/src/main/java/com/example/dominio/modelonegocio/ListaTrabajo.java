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

public class ListaTrabajo extends ArrayList<Trabajo> implements Serializable {

    public ListaTrabajo(int capacity) {
        super(capacity);
    }

    public ListaTrabajo(Collection<? extends Trabajo> collection) {
        super(collection);
    }

    public ListaTrabajo() {
    }

    public ListaTrabajo filtrar(Predicate<? super Trabajo> predicate) {
        return new ListaTrabajo(Lists.newArrayList(Iterables.filter(this, predicate)));
    }

    public int obtenerPosicion(Trabajo trabajo) {
        int posicion = -1;
        if (trabajo.getCodigoTrabajo().isEmpty())
            return posicion;
        for (Trabajo trabajoActual : this) {
            posicion++;
            if (trabajoActual.getCodigoTrabajo().equals(trabajo.getCodigoTrabajo()))
                break;
        }
        return posicion;
    }
}
