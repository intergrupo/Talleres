package com.example.santiagolopezgarcia.talleres.presenters;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.dominio.bussinesslogic.administracion.TalleresBL;
import com.example.dominio.bussinesslogic.correria.CorreriaBL;
import com.example.dominio.bussinesslogic.ordentrabajo.OrdenTrabajoBL;
import com.example.dominio.bussinesslogic.tarea.TareaXOrdenTrabajoBL;
import com.example.dominio.modelonegocio.Correria;
import com.example.dominio.modelonegocio.Mensaje;
import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;
import com.example.dominio.modelonegocio.Talleres;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.integracion.ComunicacionCarga;
import com.example.santiagolopezgarcia.talleres.integracion.ComunicacionDescarga;
import com.example.santiagolopezgarcia.talleres.integracion.EstadoConexionTerminal;
import com.example.santiagolopezgarcia.talleres.integracion.IEstadoComunicacion;
import com.example.santiagolopezgarcia.talleres.services.contracts.Suscriptor;
import com.example.santiagolopezgarcia.talleres.util.DatosCache;
import com.example.santiagolopezgarcia.talleres.view.activities.DescargaDatosParcialActivity;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IDescargaDatosParcialView;
import com.example.santiagolopezgarcia.talleres.view.popups.SincronizacionPopUp;
import com.example.utilidades.helpers.DateHelper;
import com.example.utilidades.helpers.StringHelper;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 10/3/17.
 */

public class DescargaDatosParcialPresenter extends Presenter<IDescargaDatosParcialView> implements Suscriptor, IEstadoComunicacion {
    private boolean filtroActivo;
    private OrdenTrabajoBusqueda ordenTrabajoBusqueda;
    private TalleresBL talleresBL;
    private Talleres talleres;
    private ComunicacionDescarga comunicacionDescarga;
    private Correria correria;
    private CorreriaBL correriaBL;
    private OrdenTrabajoBL ordenTrabajoBL;
    private TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL;
    private int posicionActiva;

    @Inject
    public DescargaDatosParcialPresenter(
            TalleresBL talleresBL,
            ComunicacionDescarga comunicacionDescarga,
            CorreriaBL correriaBL, OrdenTrabajoBL ordenTrabajoBL, TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL) {
        this.talleresBL = talleresBL;
        this.comunicacionDescarga = comunicacionDescarga;
        this.correriaBL = correriaBL;
        this.ordenTrabajoBL = ordenTrabajoBL;
        this.tareaXOrdenTrabajoBL = tareaXOrdenTrabajoBL;
    }

    public void setPosicionActiva(int posicionActiva) {
        this.posicionActiva = posicionActiva;
    }

    public void setTieneFiltroActivo(boolean filtroActivo) {
        this.filtroActivo = filtroActivo;
    }

    public boolean getTieneFiltroActivo() {
        return this.filtroActivo;
    }

    public void setOrdenTrabajoBusqueda(OrdenTrabajoBusqueda ordenTrabajoBusqueda) {
        this.ordenTrabajoBusqueda = ordenTrabajoBusqueda;
    }

    public OrdenTrabajoBusqueda getOrdenTrabajoBusqueda() {
        return this.ordenTrabajoBusqueda;
    }

    public void setCorreria(Correria correria) {
        this.correria = correria;
    }

    @Override
    public void iniciar() {
    }

    @Override
    public void detener() {

    }

    public void descargar() throws IOException {
        talleres = talleresBL.cargarPrimerRegistro();
        vista.mostrarMensajeProceso("Descargando maestros...", talleres.getLog());
        vista.mostrarBarraProgreso();
        cargarInformacionDelFiltro();
        firmarFechasCorreria();
        this.comunicacionDescarga.configurar(this.vista.getContext(), (SiriusApp)
                ((Activity) vista).getApplication(), this, this.correria.getCodigoCorreria(), this.ordenTrabajoBusqueda);
        this.comunicacionDescarga.ejecutar(ComunicacionDescarga.TipoComunicacion.DescargaConFiltro);
    }

    private void cargarInformacionDelFiltro() {
        this.ordenTrabajoBusqueda.setCodigoCorreria(this.correria.getCodigoCorreria());
        this.ordenTrabajoBusqueda.setListaOrdenTrabajo(ordenTrabajoBusqueda.getListaOrdenTrabajo().size()
                > 0 ? ordenTrabajoBusqueda.getListaOrdenTrabajo() : DatosCache.getListaOrdenTrabajoFiltrado());
        this.ordenTrabajoBusqueda.setListaTareaXOrdenTrabajo(tareaXOrdenTrabajoBL.cargar(this.ordenTrabajoBusqueda));
    }

    @Override
    public void onError(Throwable e) throws IOException {
        if (e instanceof UnknownHostException) {
            vista.mostrarMensajeError(e.getMessage().replace("java.net.UnknownHostException:", ""));
        } else if (e.getMessage().contains("java.net.ConnectException:")) {
            this.vista.mostrarMensajeError(e.getMessage().replace("java.net.ConnectException:", ""));
        } else {
            vista.mostrarMensajeError(e.getMessage());
        }
    }

    @Override
    public void onCompletetado() {

    }

    @Override
    public <T> void onResultado(T datos, String codigoServicio) {
//        recibirRespuestaDescarga((RespuestaDescarga) datos);
    }

//    private void recibirRespuestaDescarga(RespuestaDescarga respuestaDescarga) {
//        if (respuestaDescarga.respuesta == "true") {
//            String.valueOf(respuestaDescarga.respuesta);
//        }
//    }

    @Override
    public <T> void onResultadoAdjuntos(T datos) {

    }

    @Override
    public void informarResultado(Mensaje mensaje, ComunicacionCarga.TipoComunicacion tipoComunicacion,
                                  ComunicacionCarga.AccionTipoComunicacion accionTipoComunicacion) throws IOException {
        if (mensaje.getRespuesta()) {
            this.vista.mostrarMensaje(mensaje.getMensaje(), talleres.getLog());
            activarSincronizacion();

        } else {
            this.vista.mostrarMensajeError(mensaje.getMensaje());
        }
    }

    public void asignarDatosAListaOT() {
        DatosCache.setListaOrdenTrabajo(ordenTrabajoBL.cargarOrdenesTrabajo(correria.getCodigoCorreria()));
    }

    private void activarSincronizacion() {
        asignarDatosAListaOT();
        talleres = talleresBL.cargarPrimerRegistro();
        if (StringHelper.ToBoolean(talleres.getSincronizacion())) {


            SincronizacionPopUp sincronizacionPopUp = new SincronizacionPopUp();
            Bundle args = new Bundle();
            args.putBoolean("Sincronizacion", true);
            args.putInt("Posicion", posicionActiva);
            sincronizacionPopUp.setArguments(args);
            FragmentManager fragmentManager = ((DescargaDatosParcialActivity)
                    vista.getContext()).getSupportFragmentManager();
            sincronizacionPopUp.show(fragmentManager, "");


//            Intent intent = new Intent(vista.getContext(), CargaDatosActivity.class);
//            intent.putExtra("Posicion", posicionActiva);
//            intent.putExtra("Sincronizacion", true);
//            ((DescargaDatosParcialActivity) vista.getContext()).startActivityForResult(intent, 3);
        }
    }

    @Override
    public void informarFase(String fase) throws IOException {
        if (talleres.getLog().toUpperCase().equals("S")) {
            vista.registrarLog(fase);
        }
    }

    @Override
    public void establecerEstadoConexionTerminal(EstadoConexionTerminal estadoConexionTerminal) {

    }

    @Override
    public void informarProgreso(String mensaje, int progreso) throws IOException {
        this.vista.habilitarRegreso();
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

    public void firmarFechasCorreria() throws IOException {
        try {
            informarProgreso("Firmando Correría", 40);
            correria.setFechaUltimaDescarga(DateHelper.convertirDateAString(new Date(),
                    DateHelper.TipoFormato.yyyyMMddTHHmmss));
            correriaBL.actualizar(correria);
        } catch (ParseException e) {
            e.printStackTrace();
            vista.registrarLog(e.getMessage());
        }
    }

    public Correria getCorreriaActual(String codigoCorreria) {
        return correriaBL.cargar(codigoCorreria);
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
