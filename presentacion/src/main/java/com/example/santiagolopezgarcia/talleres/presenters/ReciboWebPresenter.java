package com.example.santiagolopezgarcia.talleres.presenters;

import android.app.Activity;

import com.example.dominio.bussinesslogic.administracion.TalleresBL;
import com.example.dominio.bussinesslogic.correria.CorreriaBL;
import com.example.dominio.modelonegocio.Mensaje;
import com.example.dominio.modelonegocio.Talleres;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.helpers.Constantes;
import com.example.santiagolopezgarcia.talleres.integracion.ComunicacionCarga;
import com.example.santiagolopezgarcia.talleres.integracion.EstadoConexionTerminal;
import com.example.santiagolopezgarcia.talleres.integracion.carga.IEstadoComunicacionCarga;
import com.example.santiagolopezgarcia.talleres.services.contracts.Suscriptor;
import com.example.santiagolopezgarcia.talleres.services.dto.RespuestaCarga;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IReciboWebView;
import com.example.santiagolopezgarcia.talleres.view.popups.AlertaPopUp;
import com.example.utilidades.FileManager;
import com.example.utilidades.Log;
import com.example.utilidades.ZipManager;
import com.example.utilidades.helpers.WifiHelper;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public class ReciboWebPresenter extends Presenter<IReciboWebView> implements Suscriptor, IEstadoComunicacionCarga {

    private ComunicacionCarga comunicacionCarga;
    private int porcentajeProgreso10 = 10;

    private TalleresBL talleresBL;
    private Talleres talleres;
    private WifiHelper wifiHelper;
    private CorreriaBL correriaBL;

    @Inject
    public ReciboWebPresenter(ComunicacionCarga comunicacionCarga,
                              TalleresBL talleresBL, CorreriaBL correriaBL) {
        this.comunicacionCarga = comunicacionCarga;
        this.talleresBL = talleresBL;
        this.correriaBL = correriaBL;
    }

    @Override
    public void iniciar() {
        vista.mostrarBarraProgreso();
        talleres = talleresBL.cargarPrimerRegistro();
        wifiHelper = new WifiHelper(vista.getContext());
        //solicitarDatos();
        this.comunicacionCarga.configurar((SiriusApp) ((Activity) vista).getApplication(), vista.getContext(), this);
        try {
            this.comunicacionCarga.ejecutar(ComunicacionCarga.TipoComunicacion.ReciboWeb);
        } catch (Exception e) {
            vista.mostrarMensajeError(e.getMessage());
        }

    }

    @Override
    public void detener() {

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

    private void completado() throws IOException {
        vista.aumentarProgresoBarra(porcentajeProgreso10);
        vista.mostrarMensajeProceso("Descargando carga diaria...", talleres.getLog());
//        solicitarDatos();
    }

    @Override
    public <T> void onResultado(T datos, String codigoServicio) {
        Thread hilo = new Thread(() -> {
            try {
                procesarDatos((RespuestaCarga) datos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        hilo.start();
        hilo.setPriority(Thread.MIN_PRIORITY);

    }

    @Override
    public <T> void onResultadoAdjuntos(T datos) {
        try {
            recibirAdjuntos((RespuestaCarga) datos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recibirAdjuntos(RespuestaCarga respuestaCarga) throws IOException {
        vista.aumentarProgresoBarra(5);
        vista.mostrarMensajeProceso("Creando Zip...", talleres.getLog());
        byte[] archivoZIP;
        String ruta = "";
        if (respuestaCarga.Binario != null) {
            archivoZIP = FileManager.convertStringToByteArray(respuestaCarga.Binario);
            ruta = Constantes.traerRutaAdjuntos();
            new File(ruta).mkdirs();
            Calendar calendar = Calendar.getInstance();
            Date time = calendar.getTime();
            long milliseconds = time.getTime();
            String nombreZip = "adjuntos_" + String.valueOf(milliseconds) + ".zip";
            FileManager.createFile(archivoZIP, Constantes.traerRutaAdjuntos() + nombreZip);
            vista.aumentarProgresoBarra(5);
            vista.mostrarMensajeProceso("Descomprimiendo Zip...", talleres.getLog());
            try {
                ZipManager.unZip(Constantes.traerRutaAdjuntos() + nombreZip, ruta, talleres.getNumeroTerminal() + "04");
            } catch (IOException e) {
                e.printStackTrace();
                vista.registrarLog("No se pudo descomprimir el Zip.");
                Log.info("No lo descomprimio");
            }
            return;
        }
    }

    private void procesarDatos(RespuestaCarga respuestaCarga) throws IOException {
        vista.aumentarProgresoBarra(5);
        vista.mostrarMensajeProceso("Creando Zip...", talleres.getLog());
        byte[] archivoZIP = FileManager.convertStringToByteArray(respuestaCarga.Binario);
        String ruta = "";
        ruta = Constantes.traerRutaReciboWeb();
        new File(ruta).mkdirs();
        FileManager.createFile(archivoZIP, Constantes.traerRutaReciboWeb() + "cargar.zip");
        vista.aumentarProgresoBarra(porcentajeProgreso10);
        vista.mostrarMensajeProceso("Descomprimiendo Zip...", talleres.getLog());
        try {
            ZipManager.unZip(Constantes.traerRutaCarga() + "cargar.zip", ruta, talleres.getNumeroTerminal() + "04");
        } catch (IOException e) {
            e.printStackTrace();
            vista.registrarLog("No se pudo descomprimir el Zip.");
        }
        completado();
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
    public void confirmarActualizarMaestros(String mensaje) {
//        this.vista.mostrarConfirmacionActualizarMaestros(mensaje);
    }

    @Override
    public void establecerCargaSatisfactoria() {
        this.vista.establecerCargaSatisfactoria();
    }

    @Override
    public void habilitarRegreso(boolean habilitar) {
        this.vista.habilitarRegreso(habilitar);
    }

    public void respuestaRemplazoCorreria(int indiceCorreria, boolean respuesta) throws IOException {
        this.comunicacionCarga.respuestaRemplazoCorreria(indiceCorreria, respuesta);
        if (respuesta && talleres.getLog().toUpperCase().equals("S"))
            vista.registrarLog("Recarga de correrias.");
    }

    public void respuestaRemplazoTarea(int indice, boolean respuesta) throws IOException {
        this.comunicacionCarga.respuestaRemplazoTarea(indice, respuesta);
        if (respuesta && talleres.getLog().toUpperCase().equals("S"))
            vista.registrarLog("Recarga de tareas.");
    }

    public void mostrarAlertaWifi() {
        if (wifiHelper.estaConectadoWifi() || wifiHelper.tieneActivoWifi()) {
            AlertaPopUp alertaPopUp = new AlertaPopUp();
            alertaPopUp.setTitle(R.string.titulo_wifi_activo);
            alertaPopUp.setMessage(R.string.alerta_wifi);
            alertaPopUp.setContext(vista.getContext());
            alertaPopUp.setPositiveButton("SI", (dialog, id) -> {
                dialog.dismiss();
                vista.cerrar();

            });
            alertaPopUp.setNegativeButton("NO", (dialog, id) -> {
                wifiHelper.activarODesactivarWifi(false);
                dialog.dismiss();
                vista.cerrar();

            });

            alertaPopUp.show();
        } else {
            vista.cerrar();
        }
    }

    @Override
    public void informarAdjuntoNoEncontrado(String mensaje, int indiceAdjuntoActual) {

    }

    @Override
    public void continuarDescargaAdjuntos(int indiceImagenActual) {

    }
}