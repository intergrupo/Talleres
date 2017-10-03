package com.example.santiagolopezgarcia.talleres.view.interfaces;

import com.example.dominio.modelonegocio.ListaTrabajo;
import com.example.dominio.modelonegocio.TotalTarea;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public interface ITotalesView extends BaseView {

    void mostrarListaTrabajos(ListaTrabajo listaTrabajos);

    void mostrarTotalTarea(List<TotalTarea> listaTotalTarea, int total);

    void mostrarTotales(int total);
}
