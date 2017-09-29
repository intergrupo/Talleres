package com.example.santiagolopezgarcia.talleres.view.interfaces;

import com.example.santiagolopezgarcia.talleres.integracion.EstadoConexionTerminal;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */
public interface IReciboWebView extends BaseView {

    void mostrarMensajeProceso(String mensaje,String log);

    void mostrarMensajeFase(String mensaje,String log);

    void establecerEstadoConexionTerminal(EstadoConexionTerminal estadoConexionTerminal);

    void aumentarProgresoBarra(int progreso);

    void cerrar();

    void mostrarConfirmacionRemplazoCorreria(String mensaje,int indice);

    void mostrarConfirmacionRemplazoTarea(String mensaje,int indice);

    void establecerCargaSatisfactoria();

    void habilitarRegreso(boolean habilitar);
}
