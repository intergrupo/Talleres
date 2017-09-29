package com.example.santiagolopezgarcia.talleres.presenters;

import android.app.Activity;

import com.example.dominio.administracion.TalleresBL;
import com.example.dominio.correria.CorreriaBL;
import com.example.dominio.modelonegocio.Correria;
import com.example.dominio.modelonegocio.Mensaje;
import com.example.dominio.modelonegocio.Talleres;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.integracion.ComunicacionCarga;
import com.example.santiagolopezgarcia.talleres.integracion.ComunicacionDescarga;
import com.example.santiagolopezgarcia.talleres.integracion.EstadoConexionTerminal;
import com.example.santiagolopezgarcia.talleres.integracion.IEstadoComunicacion;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IEnvioWebView;
import com.example.santiagolopezgarcia.talleres.view.popups.AlertaPopUp;
import com.example.utilidades.helpers.DateHelper;
import com.example.utilidades.helpers.WifiHelper;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public class EnvioWebPresenter extends Presenter<IEnvioWebView> implements IEstadoComunicacion {

    private ComunicacionDescarga comunicacionDescarga;
    private Correria correria;
    private Talleres talleres;
    private TalleresBL talleresBL;
    private CorreriaBL correriaBL;
    private WifiHelper wifiHelper;
    private String numeroTerminal = "";

    @Inject
    public EnvioWebPresenter(ComunicacionDescarga comunicacionDescarga, TalleresBL talleresBL,
                             CorreriaBL correriaBL) {
        this.comunicacionDescarga = comunicacionDescarga;
        this.talleresBL = talleresBL;
        this.correriaBL = correriaBL;
    }

    @Override
    public void iniciar() {
        talleres = talleresBL.cargarPrimerRegistro();
        wifiHelper = new WifiHelper(vista.getContext());
    }

    public void setNumeroTerminal(String numeroTerminal) {
        this.numeroTerminal = numeroTerminal;
    }

    public Correria getCorreria() {
        return correria;
    }

    public void setCorreria(Correria correria) {
        this.correria = correria;
    }


    @Override
    public void detener() {

    }

    @Override
    public void informarResultado(Mensaje mensaje, ComunicacionCarga.TipoComunicacion tipoComunicacion,
                                  ComunicacionCarga.AccionTipoComunicacion accionTipoComunicacion) throws IOException {
        if (mensaje.getRespuesta()) {
            informarProgreso("ZIP enviado!!");
            this.vista.mostrarMensaje(mensaje.getMensaje(), talleres.getLog());
        } else {
            this.vista.mostrarMensajeError(mensaje.getMensaje());
        }
    }

    @Override
    public void informarFase(String fase) throws IOException {
        vista.registrarLog(fase);
    }

    @Override
    public void establecerEstadoConexionTerminal(EstadoConexionTerminal estadoConexionTerminal) {

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
        if (excepcion.getMessage().contains("java.net.UnknownHostException:")) {
            this.vista.mostrarMensajeError(excepcion.getMessage().replace("java.net.UnknownHostException:", ""));
        } else if (excepcion.getMessage().contains("(Connection timed out)")) {
            this.vista.mostrarMensajeError(excepcion.getMessage().replace("java.net.SocketException: sendto failed: ETIMEDOUT (Connection timed out)", "La red se ha desconectado."));
        } else {
            this.vista.mostrarMensajeError(excepcion.getMessage());
        }
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

    public void firmarFechasCorreria() throws IOException {
        try {
            informarProgreso("Firmando Correría", 40);
            correria.setFechaUltimoEnvio(DateHelper.convertirDateAString(new Date(),
                    DateHelper.TipoFormato.yyyyMMddTHHmmss));
            correriaBL.actualizar(correria);
        } catch (ParseException e) {
            e.printStackTrace();
            vista.registrarLog(e.getMessage());
        }
    }

    @Override
    public void informarAdjuntoNoEncontrado(String mensaje, int indiceAdjuntoActual) {
        vista.mostrarMensajeError(mensaje);
        vista.mostrarBotonesCotinuarEnvio(indiceAdjuntoActual);
    }

    @Override
    public void continuarDescargaAdjuntos(int indiceImagenActual) {
        this.comunicacionDescarga.continuarDescargaAdjuntos(indiceImagenActual, "25");
    }

    public void prenderWifi() {
        if (!wifiHelper.estaConectadoWifi() || !wifiHelper.tieneActivoWifi()) {
            wifiHelper.activarODesactivarWifi(true);
        }
    }

    public void iniciarEnvio() {
        vista.mostrarMensajeProceso("Descargando maestros...", talleres.getLog());
        vista.mostrarBarraProgreso();
        try {
            firmarFechasCorreria();
        } catch (IOException e) {
            e.printStackTrace();
        }
        prenderWifi();
        this.comunicacionDescarga.setNumeroTerminalEnvio(numeroTerminal);
        this.comunicacionDescarga.configurar(this.vista.getContext(), (SiriusApp) ((Activity) vista).getApplication(),
                this, this.correria.getCodigoCorreria());
        this.comunicacionDescarga.ejecutar(ComunicacionDescarga.TipoComunicacion.EnvioWeb);
    }
}