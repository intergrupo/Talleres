package com.example.dominio.modelonegocio;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class ListaMultiOpcion extends ArrayList<MultiOpcion> {

    public ListaMultiOpcion(Collection<? extends MultiOpcion> collection) {
        super(collection);
    }

    public ListaMultiOpcion() {
    }

    public int obtenerPosicion(String codigoMultiOpcion) {
        int posicion = -1;
        if (codigoMultiOpcion.isEmpty())
            return posicion;
        for (MultiOpcion multiOpcionActual : this) {
            posicion++;
            if (multiOpcionActual.getCodigoOpcion().equals(codigoMultiOpcion))
                break;
        }
        return posicion;
    }
}