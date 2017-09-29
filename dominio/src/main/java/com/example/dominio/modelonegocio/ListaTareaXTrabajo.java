package com.example.dominio.modelonegocio;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class ListaTareaXTrabajo extends ArrayList<TareaXTrabajo> implements Serializable {

    public ListaTareaXTrabajo(Collection<? extends TareaXTrabajo> collection) {
        super(collection);
    }

    public ListaTareaXTrabajo() {
    }

    public ListaTareaXTrabajo filtrar(Predicate<? super TareaXTrabajo> predicate) {
        return new ListaTareaXTrabajo(Lists.newArrayList(Iterables.filter(this, predicate)));
    }

    public int obtenerPosicion(TareaXTrabajo tareaXTrabajo) {
        int posicion = -1;
        if (tareaXTrabajo.getTarea().getCodigoTarea().isEmpty() || tareaXTrabajo.getTrabajo().getCodigoTrabajo().isEmpty())
            return posicion;
        for (TareaXTrabajo tareaXTrabajoActual : this) {
            posicion++;
            if (tareaXTrabajoActual.getTrabajo().getCodigoTrabajo().equals(tareaXTrabajo.getTrabajo().getCodigoTrabajo())
                    && tareaXTrabajoActual.getTarea().getCodigoTarea().equals(tareaXTrabajo.getTarea().getCodigoTarea()))
                break;
        }
        return posicion;
    }
}
