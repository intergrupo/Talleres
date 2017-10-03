package com.example.santiagolopezgarcia.talleres.presenters;

import android.app.Activity;
import android.os.Handler;

import com.example.dominio.bussinesslogic.administracion.TalleresBL;
import com.example.dominio.bussinesslogic.correria.CorreriaBL;
import com.example.dominio.modelonegocio.ArchivoAdjunto;
import com.example.dominio.modelonegocio.Correria;
import com.example.dominio.modelonegocio.Mensaje;
import com.example.dominio.modelonegocio.Talleres;
import com.example.dominio.bussinesslogic.notificacion.ReporteNotificacionBL;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.helpers.Constantes;
import com.example.santiagolopezgarcia.talleres.integracion.ComunicacionCarga;
import com.example.santiagolopezgarcia.talleres.integracion.ComunicacionDescarga;
import com.example.santiagolopezgarcia.talleres.integracion.EstadoConexionTerminal;
import com.example.santiagolopezgarcia.talleres.integracion.IEstadoComunicacion;
import com.example.santiagolopezgarcia.talleres.util.ControladorArchivosAdjuntos;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IDescargaDatosView;
import com.example.santiagolopezgarcia.talleres.view.popups.AlertaPopUp;
import com.example.utilidades.helpers.DateHelper;
import com.example.utilidades.helpers.WifiHelper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public class DescargaDatosPresenter extends Presenter<IDescargaDatosView> implements IEstadoComunicacion {
    private final int NUMERO_TABLAS = 13;
    private Correria correria;
    private TalleresBL talleresBL;
    private CorreriaBL correriaBL;
    private ReporteNotificacionBL reporteNotificacionBL;
    private Talleres talleres;
    private ControladorArchivosAdjuntos controladorArchivosAdjuntos = new ControladorArchivosAdjuntos();

    private WifiHelper wifiHelper;

    private ComunicacionDescarga comunicacionDescarga;
    private SiriusApp app;

    @Inject
    public DescargaDatosPresenter(TalleresBL talleresBL, ComunicacionDescarga comunicacionDescarga,
                                  CorreriaBL correriaBL, ReporteNotificacionBL reporteNotificacionBL) {
        this.talleresBL = talleresBL;
        this.correriaBL = correriaBL;
        this.reporteNotificacionBL = reporteNotificacionBL;
        this.comunicacionDescarga = comunicacionDescarga;
    }

    @Override
    public void iniciar() {
        talleres = talleresBL.cargarPrimerRegistro();
        vista.mostrarMensajeProceso("Descargando maestros...", talleres.getLog());
        vista.mostrarBarraProgreso();
        wifiHelper = new WifiHelper(vista.getContext());
        try {
            firmarFechasCorreria();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.comunicacionDescarga.configurar(this.vista.getContext(), (SiriusApp) ((Activity) vista)
                .getApplication(), this, this.correria.getCodigoCorreria());
        this.comunicacionDescarga.ejecutar(ComunicacionDescarga.TipoComunicacion.DescargaCompleta);
    }

    @Override
    public void detener() {

    }

    public void setCorreria(Correria correria) {
        this.correria = correria;
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
        //vista.registrarLog(fase);
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

    }

    @Override
    public void informarError(Exception excepcion) throws IOException {
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
            correria.setFechaFinJornada(DateHelper.convertirDateAString(new Date(),
                    DateHelper.TipoFormato.yyyyMMddTHHmmss));
            correria.setFechaUltimaDescarga(DateHelper.convertirDateAString(new Date(),
                    DateHelper.TipoFormato.yyyyMMddTHHmmss));
            correriaBL.actualizar(correria);
        } catch (ParseException e) {
            e.printStackTrace();
            vista.registrarLog(e.getMessage());
        }
    }

    public void preguntarEliminarCorreria() {
        AlertaPopUp alertaPopUp = new AlertaPopUp();
        alertaPopUp.setTitle(R.string.titulo_eliminar_correria);
        alertaPopUp.setMessage(String.format("%s %s ?",
                vista.getContext().getResources().getString(R.string.mensaje_correria_eliminar),
                correria.getDescripcion()));
        alertaPopUp.setContext(vista.getContext());
        alertaPopUp.setPositiveButton("SI", (dialog, id) -> {
            try {
                eliminarCorreria();
            } catch (IOException e) {
                try {
                    vista.registrarLog(e.getMessage());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
            dialog.dismiss();
            vista.cerrar();
        });
        alertaPopUp.setNegativeButton("NO", (dialog, id) -> {
            dialog.dismiss();
            vista.cerrar();
        });

        Handler handler = new Handler();
        handler.post(() -> alertaPopUp.show());
    }

    private void eliminarCorreria() throws IOException {
        eliminarAdjuntos();
        //vista.registrarLog("Eliminando Correria");
        correriaBL.eliminarCorreria(correria, Constantes.traerRutaAdjuntos());
        if (correria.getCodigoCorreria().equals(app.getCodigoCorreria())) {
            app.limpiarCorreria();
//            vista.irAlLogin();
        }
    }

    private void eliminarAdjuntos() throws IOException {
        //vista.registrarLog("Eliminando Adjuntos");
        List<ArchivoAdjunto> listaArchivos = reporteNotificacionBL.cargarArchivosXCodigoCorreria(correria.getCodigoCorreria());
        File archivo;
        for (ArchivoAdjunto nombreArchivo : listaArchivos) {
            archivo = controladorArchivosAdjuntos.traerArchivoEncontrado(nombreArchivo);
            if (archivo != null) {
                archivo.delete();
            }
        }
    }

    public void setApp(SiriusApp app) {
        this.app = app;
    }

    @Override
    public void informarAdjuntoNoEncontrado(String mensaje, int indiceAdjuntoActual) {
        vista.mostrarMensajeError(mensaje);
        vista.mostrarBontonesContinuarDescarga(indiceAdjuntoActual);
    }

    @Override
    public void continuarDescargaAdjuntos(int indiceImagenActual) {
        this.comunicacionDescarga.continuarDescargaAdjuntos(indiceImagenActual, "04");
    }
}