package com.example.santiagolopezgarcia.talleres.view.interfaces;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public interface IDescargaDatosView extends BaseView{
    void mostrarMensajeProceso(String mensaje,String log);

    void cerrar();

    void irAlLogin();

    void mostrarBontonesContinuarDescarga(int indiceAdjuntoActual);
}
