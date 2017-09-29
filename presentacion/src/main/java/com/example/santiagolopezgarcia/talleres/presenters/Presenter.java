package com.example.santiagolopezgarcia.talleres.presenters;

import com.example.santiagolopezgarcia.talleres.view.interfaces.BaseView;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public abstract class Presenter<T extends BaseView> {

    public abstract void iniciar();

    public abstract void detener();

    public void adicionarVista(T vista) {
        this.vista = vista;
    }

    T vista;



}