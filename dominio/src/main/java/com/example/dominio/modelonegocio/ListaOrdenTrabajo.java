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

public class ListaOrdenTrabajo extends ArrayList<OrdenTrabajo> implements Serializable {

    public ListaOrdenTrabajo(Collection<? extends OrdenTrabajo> collection) {
        super(collection);
    }

    public ListaOrdenTrabajo() {
    }

    public ListaOrdenTrabajo(int capacity) {
        super(capacity);
    }

    public ListaOrdenTrabajo filtrar(Predicate<? super OrdenTrabajo> predicate) {
        return new ListaOrdenTrabajo(Lists.newArrayList(Iterables.filter(this, predicate)));
    }

    public OrdenTrabajo buscar(Predicate<? super OrdenTrabajo> predicate) {
        return Iterables.find(this, predicate);
    }

    public int obtenerPosicion(OrdenTrabajo ordenTrabajo) {
        int posicion = -1;
        ListaOrdenTrabajo lista = filtrar(ordenTrabajoActual -> ordenTrabajoActual.getCodigoOrdenTrabajo().equals(ordenTrabajo.getCodigoOrdenTrabajo()));
        if (lista.size() > 0) {
            posicion = this.indexOf(lista.get(0));
        }
        return posicion;
    }

    public OrdenTrabajo obtenerOrdenTrabajo(OrdenTrabajo ordenTrabajoABuscar) {
        OrdenTrabajo ordenTrabajo = buscar(ordenTrabajoActual -> ordenTrabajoActual.getCodigoOrdenTrabajo().equals(ordenTrabajoABuscar.getCodigoOrdenTrabajo()));
        return ordenTrabajo;
    }

    public ListaOrdenTrabajo clonar() {
        ListaOrdenTrabajo listaOrdenTrabajo = new ListaOrdenTrabajo(this.size());
        listaOrdenTrabajo.addAll(this);
        return listaOrdenTrabajo;
    }
}