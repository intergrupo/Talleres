package com.example.santiagolopezgarcia.talleres.presenters;

import android.app.Activity;

import com.example.dominio.correria.CorreriaBL;
import com.example.dominio.modelonegocio.Correria;
import com.example.dominio.modelonegocio.Mensaje;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.integracion.ComunicacionCarga;
import com.example.santiagolopezgarcia.talleres.integracion.ComunicacionDescarga;
import com.example.santiagolopezgarcia.talleres.integracion.EstadoConexionTerminal;
import com.example.santiagolopezgarcia.talleres.integracion.IEstadoComunicacion;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IEnvioDirectoView;
import com.example.utilidades.helpers.DateHelper;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public class EnvioDirectoPresenter extends Presenter<IEnvioDirectoView> implements IEstadoComunicacion {

    private ComunicacionDescarga comunicacionDescarga;
    private Correria correria;
    private CorreriaBL correriaBL;

    @Inject
    public EnvioDirectoPresenter(ComunicacionDescarga comunicacionDescarga,
                                 CorreriaBL correriaBL) {
        this.comunicacionDescarga = comunicacionDescarga;
        this.correriaBL = correriaBL;
    }

    @Override
    public void iniciar() {
        vista.mostrarBarraProgreso();
        try {
            generarZip();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void detener() {

    }

    @Override
    public void informarResultado(Mensaje mensaje, ComunicacionCarga.TipoComunicacion tipoComunicacion,
                                  ComunicacionCarga.AccionTipoComunicacion accionTipoComunicacion) throws IOException {
        if (mensaje.getRespuesta()) {
            informarProgreso("ZIP Generado!!");
            this.vista.desaparecerBarraProgreso();
            this.vista.habilitarRegreso();
        } else {
            this.vista.mostrarMensajeError(mensaje.getMensaje());
            vista.registrarLog(mensaje.getMensaje());
            this.vista.desaparecerBarraProgreso();
            this.vista.habilitarRegreso();
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
            this.vista.mostrarMensajeProceso(mensaje);
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
        String mensaje;
        if (excepcion.getMessage().contains("java.net.UnknownHostException:")) {
            mensaje =excepcion.getMessage().replace("java.net.UnknownHostException:", "");
            this.vista.mostrarMensajeError(mensaje);
        } else if (excepcion.getMessage().contains("(Connection timed out)")) {
            mensaje = excepcion.getMessage().replace("java.net.SocketException: sendto failed: ETIMEDOUT (Connection timed out)", "La red se ha desconectado.");
            this.vista.mostrarMensajeError(mensaje);
        } else {
            mensaje = excepcion.getMessage();
            this.vista.mostrarMensajeError(mensaje);
        }
        vista.registrarLog(mensaje);
    }

    public void setCorreria(Correria correria) {
        this.correria = correria;
    }

    public void generarZip() throws IOException {
        firmarFechasCorreria();
        informarProgreso("Generando ZIP", 50);
        this.comunicacionDescarga.configurar(this.vista.getContext(), (SiriusApp) ((Activity) vista).getApplication(), this, this.correria.getCodigoCorreria());
        this.comunicacionDescarga.ejecutar(ComunicacionDescarga.TipoComunicacion.EnvioDirecto);
    }

    public void firmarFechasCorreria() throws IOException {
        try {
            informarProgreso("Firmando Correría", 40);
            correria.setFechaUltimoEnvio(DateHelper.convertirDateAString(new Date(), DateHelper.TipoFormato.yyyyMMddTHHmmss));
            correriaBL.actualizar(correria);
        } catch (ParseException e) {
            e.printStackTrace();
            vista.registrarLog(e.getMessage());
        }
    }

    @Override
    public void informarAdjuntoNoEncontrado(String mensaje,int indiceAdjuntoActual) {
        vista.mostrarMensajeError(mensaje);
        vista.mostrarBotonesCotinuarEnvio(indiceAdjuntoActual);
    }

    @Override
    public void continuarDescargaAdjuntos(int indiceImagenActual) {
        this.comunicacionDescarga.continuarDescargaAdjuntos(indiceImagenActual,"");
    }
}