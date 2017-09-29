package com.example.santiagolopezgarcia.talleres.view.interfaces;

import com.example.dominio.modelonegocio.Correria;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public interface IAdministracionCorreriaView extends BaseView {

    void mostrarListaCorrerias(List<Correria> listaCorrerias);
}

