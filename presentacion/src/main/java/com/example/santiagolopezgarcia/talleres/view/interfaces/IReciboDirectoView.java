package com.example.santiagolopezgarcia.talleres.view.interfaces;

import com.example.santiagolopezgarcia.talleres.integracion.EstadoConexionTerminal;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public interface IReciboDirectoView extends BaseView{

    void iniciarObserver();

    void mostrarMensajeProceso(String mensaje,String log);

    void mostrarMensajeFase(String mensaje,String log);

    void establecerEstadoConexionTerminal(EstadoConexionTerminal estadoConexionTerminal);

    void aumentarProgresoBarra(int progreso);

    void cerrar();

    void mostrarConfirmacionRemplazoCorreria(String mensaje,int indice);

    void mostrarConfirmacionActualizarMaestros(String mensaje);

    void mostrarConfirmacionRemplazoTarea(String mensaje,int indice);

    void establecerCargaSatisfactoria();

    void habilitarRegreso(boolean habilitar);
}
