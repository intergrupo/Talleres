package com.example.santiagolopezgarcia.talleres.view.interfaces;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public interface IEnvioDirectoView extends BaseView{

    void mostrarMensajeProceso(String mensaje);

    void aumentarProgresoBarra(int progreso);

    void cerrar();

    void enviarZip();

    void habilitarRegreso();

    void desaparecerBarraProgreso();

    void mostrarBotonesCotinuarEnvio(int indiceAdjuntoActual);
}
