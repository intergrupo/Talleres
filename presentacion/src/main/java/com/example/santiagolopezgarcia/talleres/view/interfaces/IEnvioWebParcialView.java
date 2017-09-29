package com.example.santiagolopezgarcia.talleres.view.interfaces;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public interface IEnvioWebParcialView extends BaseView {
    void mostrarMensajeProceso(String mensaje,String log);
    void aumentarProgresoBarra(int progreso);
    void iniciar(String numeroTerminal);
    void cerrar();
    void habilitarRegreso();
    void mostrarBotonesCotinuarEnvio(int indiceAdjuntoActual);
}
