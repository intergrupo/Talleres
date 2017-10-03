package com.example.santiagolopezgarcia.talleres.view.interfaces;

import com.example.dominio.modelonegocio.Correria;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public interface IPrincipalView extends BaseView {

    void mostrarCorreria(Correria correria);

    void mostrarControlesTarea();

    void mostrarControlesOrdenTrabajo();
}