package com.example.santiagolopezgarcia.talleres.view.interfaces;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public interface IEnvioWebView extends BaseView {
    void mostrarMensajeProceso(String mensaje,String log);

    void aumentarProgresoBarra(int progreso);

    void cerrar();

    void iniciar(String numeroTerminal);

    void mostrarBotonesCotinuarEnvio(int indiceAdjuntoActual);
}
