package com.example.dominio.modelonegocio;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */

public class ListaOpcion extends ArrayList<Opcion> implements Serializable {

    public ListaOpcion(Collection<? extends Opcion> collection) {
        super(collection);
    }

    public ListaOpcion() {
    }

    public ListaOpcion filtrar(Predicate<? super Opcion> predicate) {
        return new ListaOpcion(Lists.newArrayList(Iterables.filter(this, predicate)));
    }
}