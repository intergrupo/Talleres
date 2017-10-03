package com.example.santiagolopezgarcia.talleres.presenters;

import android.app.Activity;

import com.example.dominio.bussinesslogic.administracion.TalleresBL;
import com.example.dominio.bussinesslogic.correria.CorreriaBL;
import com.example.dominio.modelonegocio.Correria;
import com.example.dominio.modelonegocio.Mensaje;
import com.example.dominio.modelonegocio.Talleres;
import com.example.dominio.bussinesslogic.ordentrabajo.OrdenTrabajoBL;
import com.example.dominio.bussinesslogic.tarea.TareaXOrdenTrabajoBL;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.integracion.ComunicacionCarga;
import com.example.santiagolopezgarcia.talleres.integracion.EstadoConexionTerminal;
import com.example.santiagolopezgarcia.talleres.integracion.carga.IEstadoComunicacionCarga;
import com.example.santiagolopezgarcia.talleres.view.interfaces.ICargaDatosView;
import com.example.santiagolopezgarcia.talleres.view.popups.AlertaPopUp;
import com.example.santiagolopezgarcia.talleres.view.popups.SincronizacionPopUp;
import com.example.utilidades.helpers.DateHelper;
import com.example.utilidades.helpers.WifiHelper;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class CargaDatosPresenter extends Presenter<ICargaDatosView> implements IEstadoComunicacionCarga {

    private ComunicacionCarga comunicacionCarga;
    private TalleresBL talleresBL;
    private Talleres talleres;
    private WifiHelper wifiHelper;
    private SiriusApp aplicacion;
    private CorreriaBL correriaBL;
    private TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL;
    private OrdenTrabajoBL ordenTrabajoBL;
    private boolean SINCRONIZACION;
    private int cantidadOTSIniciales;
    private int cantidadTareasIniciales;
    private int cantidadOTSFinales;
    private int cantidadTareasFinales;
    private SiriusApp siriusApp;
    private int posicionActiva;


    @Inject
    public CargaDatosPresenter(ComunicacionCarga comunicacionCarga, TalleresBL talleresBL,
                               CorreriaBL correriaBL, TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL,
                               OrdenTrabajoBL ordenTrabajoBL) {
        this.comunicacionCarga = comunicacionCarga;
        this.talleresBL = talleresBL;
        this.correriaBL = correriaBL;
        this.tareaXOrdenTrabajoBL = tareaXOrdenTrabajoBL;
        this.ordenTrabajoBL = ordenTrabajoBL;
    }

    @Override
    public void iniciar() {
        if (vista instanceof Activity) {
            this.aplicacion = (SiriusApp) ((Activity) vista).getApplication();
        } else {
            this.aplicacion = (SiriusApp) ((SincronizacionPopUp) vista).getActivity().getApplication();
        }
        vista.mostrarBarraProgreso();
        talleres = talleresBL.cargarPrimerRegistro();
        wifiHelper = new WifiHelper(vista.getContext());
        this.comunicacionCarga.configurar(this.aplicacion, vista.getContext(), this);
        try {
            if (SINCRONIZACION) {
                cantidadOTSIniciales = ordenTrabajoBL.cargarXCorreria(aplicacion.getCodigoCorreria()).size();
                cantidadTareasIniciales = tareaXOrdenTrabajoBL.cargarXCorreria(aplicacion.getCodigoCorreria()).size();
            }
            this.comunicacionCarga.ejecutar(ComunicacionCarga.TipoComunicacion.CargaMaestros);

        } catch (Exception e) {
            vista.mostrarMensajeError(e.getMessage());
        }
    }

    @Override
    public void detener() {

    }

    public int getPosicionActiva() {
        return posicionActiva;
    }

    public void setPosicionActiva(int posicionActiva) {
        this.posicionActiva = posicionActiva;
    }

    public void setSiriusApp(SiriusApp siriusApp) {
        this.siriusApp = siriusApp;
    }

    public int getCantidadOTSFinales() {
        return cantidadOTSFinales;
    }

    public int getCantidadTareasFinales() {
        return cantidadTareasFinales;
    }


    public int getCantidadTareasIniciales() {
        return cantidadTareasIniciales;
    }

    public int getCantidadOTSIniciales() {
        return cantidadOTSIniciales;
    }

    public void setSINCRONIZACION(boolean SINCRONIZACION) {
        this.SINCRONIZACION = SINCRONIZACION;
    }

    @Override
    public void informarResultado(Mensaje mensaje, ComunicacionCarga.TipoComunicacion tipoComunicacion, ComunicacionCarga.AccionTipoComunicacion accionTipoComunicacion) throws IOException {
        if (mensaje.getRespuesta()) {
            if (SINCRONIZACION) {
                cantidadOTSFinales = ordenTrabajoBL.cargarXCorreria(aplicacion.getCodigoCorreria()).size();
                cantidadTareasFinales = tareaXOrdenTrabajoBL.cargarXCorreria(aplicacion.getCodigoCorreria()).size();
            }
            this.vista.mostrarMensaje(mensaje.getMensaje(), talleres.getLog());
            if (talleres.getLog().toUpperCase().equals("S"))
                vista.registrarLog(mensaje.getMensaje());
        } else {
            this.vista.mostrarMensajeError(mensaje.getMensaje());
        }

        //vista.registrarLog(mensaje.getMensaje());
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
        if (excepcion.getMessage() != null) {
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
    }

    @Override
    public void confirmarRemplazoCorreria(String mensaje, int indiceCorreria) {
        if (correriaBL.cargar(comunicacionCarga.getListaCorrerias().Sirius_Correria.get(indiceCorreria)
                .CodigoCorreria).isRecargaCorreria()) {
            this.vista.mostrarConfirmacionRemplazoCorreria(mensaje, indiceCorreria);
        } else {
            mostrarMensajeCorreriaSinReemplazo("La correría " + correriaBL.cargar(comunicacionCarga.getListaCorrerias().
                    Sirius_Correria.get(indiceCorreria).CodigoCorreria).getCodigoCorreria() +
                    " no se puede recargar.", indiceCorreria);
        }
    }

    @Override
    public void confirmarRemplazoTarea(String mensaje, int indiceTarea) {
        this.vista.mostrarConfirmacionRemplazoTarea(mensaje, indiceTarea);
    }

    @Override
    public void confirmarActualizarMaestros(String mensaje) {
        this.vista.mostrarConfirmacionActualizarMaestros(mensaje);
    }

    @Override
    public void establecerCargaSatisfactoria() {
        this.vista.establecerCargaSatisfactoria();
    }

    @Override
    public void habilitarRegreso(boolean habilitar) {
        vista.habilitarRegreso(habilitar);
    }

    public void respuestaRemplazoCorreria(int indiceCorreria, boolean respuesta) throws IOException {
        this.comunicacionCarga.respuestaRemplazoCorreria(indiceCorreria, respuesta);
        if (respuesta) {
            //vista.registrarLog("Recarga de correria.");
            this.comunicacionCarga.getCodigosCorreriasIntegradas().add(
                    this.comunicacionCarga.getListaCorrerias().Sirius_Correria.get(indiceCorreria).CodigoCorreria);
        }
    }

    public void respuestaRemplazoTarea(int indice, boolean respuesta) throws IOException {
        this.comunicacionCarga.respuestaRemplazoTarea(indice, respuesta);
        /*if (respuesta)
            vista.registrarLog("Recarga de tareas.");*/
    }

    public void respuestaConfirmacionActualizarMaestros(boolean respuesta) throws IOException {
        this.comunicacionCarga.respuestaConfirmarActualizarMaestros(respuesta);
        /*if (respuesta)
            vista.registrarLog("Actulizacion de maestros");*/
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

    public void mostrarMensajeCorreriaSinReemplazo(String mensaje, int indiceCorreria) {
        AlertaPopUp alertaPopUp = new AlertaPopUp();
        alertaPopUp.setTitle(R.string.titulo_recarga_correria);
        alertaPopUp.setMessage(mensaje);
        alertaPopUp.setContext(vista.getContext());
        alertaPopUp.setPositiveButton("Aceptar", (dialog, id) -> {
            try {
                respuestaRemplazoCorreria(indiceCorreria, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        });
        alertaPopUp.show();
    }

    @Override
    public void informarAdjuntoNoEncontrado(String mensaje, int indiceAdjuntoActual) {

    }

    @Override
    public void continuarDescargaAdjuntos(int indiceImagenActual) {

    }

    public void firmarCorreria(String codigoCorreria) {
        Correria correria = correriaBL.cargar(codigoCorreria);
        if (!correria.getCodigoCorreria().isEmpty()) {
            try {
                correria.setFechaFinJornada(DateHelper.convertirDateAString(new Date(), DateHelper.TipoFormato.yyyyMMddTHHmmss));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            correriaBL.actualizar(correria);
        }
    }
}
