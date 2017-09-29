package com.example.santiagolopezgarcia.talleres.view.interfaces;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public interface IEnvioDirectoParcialView extends BaseView  {

    void mostrarMensajeProceso(String mensaje,String log);
    void aumentarProgresoBarra(int progreso);
    void enviarZip();

    void habilitarRegreso();
    void cerrar();

    void desaparecerBarraProgreso();
    void mostrarBotonesCotinuarEnvio(int indiceAdjuntoActual);

}
