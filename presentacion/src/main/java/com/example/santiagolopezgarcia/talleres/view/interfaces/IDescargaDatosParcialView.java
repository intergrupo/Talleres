package com.example.santiagolopezgarcia.talleres.view.interfaces;

/**
 * Created by santiagolopezgarcia on 10/3/17.
 */

public interface IDescargaDatosParcialView extends BaseView {
    void mostrarMensajeProceso(String mensaje,String log);

    void cerrar();
    void habilitarRegreso();

    void mostrarBontonesContinuarDescarga(int indiceAdjuntoActual);
}