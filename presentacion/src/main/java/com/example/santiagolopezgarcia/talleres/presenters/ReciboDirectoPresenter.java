package com.example.santiagolopezgarcia.talleres.presenters;

import android.app.Activity;

import com.example.dominio.bussinesslogic.administracion.TalleresBL;
import com.example.dominio.bussinesslogic.correria.CorreriaBL;
import com.example.dominio.modelonegocio.Mensaje;
import com.example.dominio.modelonegocio.Talleres;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.helpers.Constantes;
import com.example.santiagolopezgarcia.talleres.integracion.ComunicacionCarga;
import com.example.santiagolopezgarcia.talleres.integracion.EstadoConexionTerminal;
import com.example.santiagolopezgarcia.talleres.integracion.carga.IEstadoComunicacionCarga;
import com.example.santiagolopezgarcia.talleres.services.contracts.Suscriptor;
import com.example.santiagolopezgarcia.talleres.util.ControladorArchivosAdjuntos;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IReciboDirectoView;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public class ReciboDirectoPresenter extends Presenter<IReciboDirectoView> implements Suscriptor, IEstadoComunicacionCarga {

    private TalleresBL talleresBL;
    private Talleres talleres;
    private ComunicacionCarga comunicacionCarga;
    private ControladorArchivosAdjuntos controladorArchivosAdjuntos;
    private CorreriaBL correriaBL;

    @Inject
    public ReciboDirectoPresenter(ComunicacionCarga comunicacionCarga,
                                  TalleresBL talleresBL, CorreriaBL correriaBL) {
        this.talleresBL = talleresBL;
        this.comunicacionCarga = comunicacionCarga;
        this.correriaBL = correriaBL;
    }

    @Override
    public void iniciar() {
        controladorArchivosAdjuntos = new ControladorArchivosAdjuntos();
        talleres = talleresBL.cargarPrimerRegistro();
        vista.iniciarObserver();
    }

    @Override
    public void detener() {

    }

    public void moverArchivoASirius() {
        File origen = new File(Constantes.traerRutaBluetooth() + Constantes.NOMBRE_ARCHIVO_ZIP_ENVIO_DIRECTO);
        File destino = new File(Constantes.traerRutaReciboDirecto() + Constantes.NOMBRE_ARCHIVO_ZIP_ENVIO_DIRECTO);
        try {
            controladorArchivosAdjuntos.copiarArchivo(origen, destino);
        } catch (IOException e) {
            e.printStackTrace();
        }
        origen.delete();

    }

    public void descomprimirZip() {
        vista.mostrarBarraProgreso();
        this.comunicacionCarga.configurar((SiriusApp) ((Activity) vista).getApplication(), vista.getContext(), this);
        try {
            this.comunicacionCarga.ejecutar(ComunicacionCarga.TipoComunicacion.ReciboDirecto);
        } catch (Exception e) {
            vista.mostrarMensajeError(e.getMessage());
        }
    }

    public void respuestaRemplazoTarea(int indice, boolean respuesta) throws IOException {
        this.comunicacionCarga.respuestaRemplazoTareaReciboDirecto(indice, respuesta);
        if (respuesta && talleres.getLog().toUpperCase().equals("S"))
            vista.registrarLog("Recarga de tarea.");
    }

    public void respuestaRemplazoCorreria(int indiceCorreria, boolean respuesta) throws IOException {
        this.comunicacionCarga.respuestaRemplazoCorreriaReciboDirecto(indiceCorreria, respuesta);
        if (respuesta && talleres.getLog().toUpperCase().equals("S"))
            vista.registrarLog("Recarga de tarea.");
    }

    @Override
    public void informarResultado(Mensaje mensaje, ComunicacionCarga.TipoComunicacion tipoComunicacion,
                                  ComunicacionCarga.AccionTipoComunicacion accionTipoComunicacion) throws IOException {
        if (mensaje.getRespuesta()) {
            this.vista.mostrarMensaje(mensaje.getMensaje(), talleres.getLog());
        } else {
            this.vista.mostrarMensajeError(mensaje.getMensaje());
        }
    }

    @Override
    public void informarFase(String fase) throws IOException {
        this.vista.mostrarMensajeFase(fase, talleres.getLog());
    }

    @Override
    public void establecerEstadoConexionTerminal(EstadoConexionTerminal estadoConexionTerminal) {
        this.vista.establecerEstadoConexionTerminal(estadoConexionTerminal);
    }

    @Override
    public void informarProgreso(String mensaje, int progreso) throws IOException {
        this.informarProgreso(progreso);
        this.informarProgreso(mensaje);
    }

    @Override
    public void informarProgreso(String mensaje) throws IOException {
        if (mensaje != null && !mensaje.isEmpty()) {
            this.vista.mostrarMensajeProceso(mensaje, talleres.getLog());
        }
    }

    @Override
    public void informarProgreso(int progreso) {
        if (progreso >= 0) {
            this.vista.aumentarProgresoBarra(progreso);
        }
    }

    @Override
    public void informarError(Exception excepcion) throws IOException {
        this.vista.aumentarProgresoBarra(100);
        this.vista.establecerEstadoConexionTerminal(EstadoConexionTerminal.NoMostrar);
        if (excepcion.getMessage().contains("java.net.UnknownHostException:")) {
            this.vista.mostrarMensajeError(excepcion.getMessage().replace("java.net.UnknownHostException:", ""));
        } else if (excepcion.getMessage().contains("(Connection timed out)")) {
            this.vista.mostrarMensajeError(excepcion.getMessage().replace("java.net.SocketException: sendto failed: ETIMEDOUT (Connection timed out)", "La red se ha desconectado."));
        } else if (excepcion.getMessage().contains("java.net.ConnectException:")) {
            this.vista.mostrarMensajeError(excepcion.getMessage().replace("java.net.ConnectException:", ""));
        } else {
            this.vista.mostrarMensajeError(excepcion.getMessage());
        }
    }

    @Override
    public void confirmarRemplazoCorreria(String mensaje, int indiceCorreria) {
        if (correriaBL.cargar(comunicacionCarga.getListaCorrerias().Sirius_Correria.get(indiceCorreria).CodigoCorreria).isRecargaCorreria())
            this.vista.mostrarConfirmacionRemplazoCorreria(mensaje, indiceCorreria);
        else
            try {
                respuestaRemplazoCorreria(indiceCorreria, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void confirmarRemplazoTarea(String mensaje, int indiceTarea) {
        this.vista.mostrarConfirmacionRemplazoTarea(mensaje, indiceTarea);
    }

    @Override
    public void establecerCargaSatisfactoria() {
        this.vista.establecerCargaSatisfactoria();
    }


    public void habilitarRegreso(boolean habilitar) {
        this.vista.habilitarRegreso(habilitar);
    }

    @Override
    public void onError(Throwable e) throws IOException {
        vista.ocultarBarraProgreso();
        if (e.getMessage().contains("java.net.UnknownHostException:")) {
            this.vista.mostrarMensajeError(e.getMessage().replace("java.net.UnknownHostException:", ""));
        } else if (e.getMessage().contains("(Connection timed out)")) {
            this.vista.mostrarMensajeError(e.getMessage().replace("java.net.SocketException: sendto failed: ETIMEDOUT (Connection timed out)", "La red se ha desconectado."));
        } else if (e.getMessage().contains("java.net.ConnectException:")) {
            this.vista.mostrarMensajeError(e.getMessage().replace("java.net.ConnectException:", ""));
        } else {
            this.vista.mostrarMensajeError(e.getMessage());
        }
    }

    @Override
    public void onCompletetado() {

    }

    @Override
    public <T> void onResultado(T datos, String codigoServicio) {
        /*Thread hilo = new Thread(() -> {
            procesarDatos((RespuestaCarga) datos);
        });
        hilo.start();
        hilo.setPriority(Thread.MIN_PRIORITY);*/

    }

    @Override
    public <T> void onResultadoAdjuntos(T datos) {
        //recibirAdjuntos((RespuestaCarga) datos);
    }

    @Override
    public void confirmarActualizarMaestros(String mensaje) {
        this.vista.mostrarConfirmacionActualizarMaestros(mensaje);
    }

    @Override
    public void informarAdjuntoNoEncontrado(String mensaje, int indiceAdjuntoActual) {

    }

    @Override
    public void continuarDescargaAdjuntos(int indiceImagenActual) {

    }
}