package com.example.santiagolopezgarcia.talleres.view.interfaces;

import android.content.Context;

import java.io.IOException;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public interface BaseView {

    Context getContext();

    void mostrarBarraProgreso();

    void ocultarBarraProgreso();

    void mostrarMensaje(String mensaje,String log);

    void mostrarMensajeError(String mensaje);

    void mostrarMensajeAdvertencia(String mensaje);

    void registrarLog(String mensaje) throws IOException;
}
