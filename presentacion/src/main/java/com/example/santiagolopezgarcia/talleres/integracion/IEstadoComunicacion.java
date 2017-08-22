package com.example.santiagolopezgarcia.talleres.integracion;

import com.example.dominio.modelonegocio.Mensaje;

import java.io.IOException;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public interface IEstadoComunicacion {

    void informarResultado(Mensaje mensaje, ComunicacionCarga.TipoComunicacion tipoComunicacion
            , ComunicacionCarga.AccionTipoComunicacion accionTipoComunicacion) throws IOException;

    void informarFase(String fase) throws IOException;

    void establecerEstadoConexionTerminal(EstadoConexionTerminal estadoConexionTerminal);

    void informarProgreso(String mensaje,int progreso) throws IOException;

    void informarProgreso(String mensaje) throws IOException;

    void informarProgreso(int progreso);

    void informarError(Exception excepcion) throws IOException;

    void informarAdjuntoNoEncontrado(String mensaje,int indiceAdjuntoActual);

    void continuarDescargaAdjuntos(int indiceImagenActual);
}