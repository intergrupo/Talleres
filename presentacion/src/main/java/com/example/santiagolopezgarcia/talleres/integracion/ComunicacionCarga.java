package com.example.santiagolopezgarcia.talleres.integracion;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;

import com.example.dominio.administracion.TalleresBL;
import com.example.dominio.administracion.UsuarioBL;
import com.example.dominio.modelonegocio.ArchivoAdjunto;
import com.example.dominio.modelonegocio.ListaOrdenTrabajo;
import com.example.dominio.modelonegocio.Mensaje;
import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.dominio.modelonegocio.ParametrosConfirmacion;
import com.example.dominio.modelonegocio.Talleres;
import com.example.dominio.modelonegocio.Usuario;
import com.example.dominio.ordentrabajo.OrdenTrabajoBL;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.helpers.Constantes;
import com.example.santiagolopezgarcia.talleres.integracion.carga.CargaDiaria;
import com.example.santiagolopezgarcia.talleres.integracion.carga.CargaMaestros;
import com.example.santiagolopezgarcia.talleres.integracion.carga.IEstadoComunicacionCarga;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.correria.ListaCorrerias;
import com.example.santiagolopezgarcia.talleres.integracion.descarga.Descarga;
import com.example.santiagolopezgarcia.talleres.services.ServicioTalleres;
import com.example.santiagolopezgarcia.talleres.services.contracts.Suscriptor;
import com.example.santiagolopezgarcia.talleres.services.dto.PeticionCarga;
import com.example.santiagolopezgarcia.talleres.services.dto.PeticionDescarga;
import com.example.santiagolopezgarcia.talleres.services.dto.PeticionMensajeLog;
import com.example.santiagolopezgarcia.talleres.services.dto.RespuestaCarga;
import com.example.santiagolopezgarcia.talleres.services.dto.RespuestaDescarga;
import com.example.santiagolopezgarcia.talleres.util.ControladorArchivosAdjuntos;
import com.example.santiagolopezgarcia.talleres.view.popups.ResultadoNotificacionOTPopUp;
import com.example.utilidades.FileManager;
import com.example.utilidades.Log;
import com.example.utilidades.ZipManager;
import com.example.utilidades.helpers.MobileDataHelper;
import com.example.utilidades.helpers.StringHelper;
import com.example.utilidades.helpers.WifiHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import static com.example.santiagolopezgarcia.talleres.integracion.ComunicacionCarga.AccionTipoComunicacion.TransferirComprimido;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class ComunicacionCarga {// implements Suscriptor {

    private final String SERVICIO_CARGA_MAESTROS = "01";
    private final String SERVICIO_CARGA_DIARIA = "02";
    private final String SERVICIO_CARGA_ADJUNTOS = "03";
    private final String SERVICIO_RECIBO_WEB = "22";
    private final String SERVICIO_ADJUNTOS_RECIBO_WEB = "23";
    private final String SERVICIO_CONFIRMACION = "26";
    private final String SESION_CARGA = UUID.randomUUID().toString();
    private Descarga descarga;
    private boolean hayMaestros;

    private int progreso10 = 10;

    public final static String CODIGO_PROGRAMA = "04";

    private CargaMaestros cargaMaestros;
    private CargaDiaria cargaDiaria;

    private Context contexto;
    private ControladorArchivosAdjuntos controladorArchivosAdjuntos;
    public String numeroTerminal;
    public String versionSoftware;
    public TalleresBL talleresBL;
    public UsuarioBL usuarioBL;
    public OrdenTrabajoBL ordenTrabajoBL;
    public Talleres talleres;
    public TipoComunicacion tipoComunicacion;

    private IEstadoComunicacionCarga estadoComunicacion;
    private ExecutorService executorService;

    private List<ArchivoAdjunto> nombresArchivosAdjuntos;
    private ListaCorrerias listaCorrerias;
    private List<String> codigosCorreriasIntegradas = new ArrayList<>();

    public List<String> getCodigosCorreriasIntegradas() {
        return codigosCorreriasIntegradas;
    }

    public ListaCorrerias getListaCorrerias() {
        return listaCorrerias;
    }

    public enum TipoComunicacion {
        CargarCentral,
        CargaMaestros,
        ReciboDirecto,
        ReciboWeb,
        Telemedida
    }

    public enum AccionTipoComunicacion {
        TransferirComprimido,
        TransferirAdjunto,
        IntegrarDatos,
        ValidacionExistenciaMaestro,
        ValidacionVersionApp,
        ValidacionVersionMaestro,
        ValidacionCorreria,
        ValidacionOrdenTrabajo
    }

    @Inject
    public ComunicacionCarga(CargaMaestros cargaMaestros
            , CargaDiaria cargaDiaria
            , TalleresBL talleresBL
            , UsuarioBL usuarioBL
            , Descarga descarga
            , OrdenTrabajoBL ordenTrabajoBL) {
        this.cargaDiaria = cargaDiaria;
        this.cargaMaestros = cargaMaestros;
        this.talleresBL = talleresBL;
        this.usuarioBL = usuarioBL;
        this.descarga = descarga;
        this.ordenTrabajoBL = ordenTrabajoBL;
    }

    public void configurar(SiriusApp siriusApp, Context contexto, IEstadoComunicacionCarga estadoComunicacion) {
        this.contexto = contexto;
        this.estadoComunicacion = estadoComunicacion;
        this.numeroTerminal = siriusApp.getCodigoTerminal();
        this.versionSoftware = this.contexto.getResources().getString(R.string.version);
        this.cargaDiaria.configurar(estadoComunicacion);
        this.cargaMaestros.configurar(estadoComunicacion);
        this.talleres = this.talleresBL.cargarPrimerRegistro();
        this.executorService = Executors.newSingleThreadExecutor();
        this.descarga.configurar(this.estadoComunicacion);
        this.controladorArchivosAdjuntos = new ControladorArchivosAdjuntos();
    }

    public void ejecutar(TipoComunicacion tipoComunicacion) {
        if (tipoComunicacion.equals(TipoComunicacion.ReciboDirecto)) {
            this.ejecutarReciboDirecto(TransferirComprimido);
        } else {
            switch (tipoComunicacion) {
                case CargarCentral:
                    this.ejecutarCargaCentral(AccionTipoComunicacion.ValidacionExistenciaMaestro);
                    break;
                case ReciboWeb:
                    this.ejecutarReciboWeb(AccionTipoComunicacion.ValidacionExistenciaMaestro);
                    break;
                case CargaMaestros:
                    if (!this.cargaDiaria.validarExistenciaMaestros())
                        this.ejecutarCargaMaestros(TransferirComprimido);
                    else
                        this.ejecutarCargaCentral(AccionTipoComunicacion.ValidacionExistenciaMaestro);
                    break;
            }
            this.tipoComunicacion = tipoComunicacion;
        }
    }

    public void ejecutarCargaCentral(AccionTipoComunicacion accionTipoComunicacion) {
        this.ejecutarCargaCentralAsync(accionTipoComunicacion);
    }

    private void ejecutarCargaCentralAsync(AccionTipoComunicacion accionTipoComunicacion) {
        Runnable runnable = () -> {
            ejecutarCargaCentralSync(accionTipoComunicacion);
        };
        this.executorService.execute(runnable);
    }

    private void ejecutarReciboWeb(AccionTipoComunicacion accionTipoComunicacion) {
        this.ejecutarReciboWebAsync(accionTipoComunicacion);
    }

    private void ejecutarReciboWebAsync(AccionTipoComunicacion accionTipoComunicacion) {
        Runnable runnable = () -> {
            ejecutarReciboWebSync(accionTipoComunicacion);
        };
        this.executorService.execute(runnable);
    }

    public void ejecutarReciboDirecto(AccionTipoComunicacion accionTipoComunicacion) {
        this.ejecutarReciboDirectoAsync(accionTipoComunicacion);
    }

    private void ejecutarReciboDirectoAsync(AccionTipoComunicacion accionTipoComunicacion) {
        Runnable runnable = () -> {
            ejecutarReciboDirectoSync(accionTipoComunicacion);
        };
        this.executorService.execute(runnable);
    }

    private void ejecutarCargaCentralSync(AccionTipoComunicacion accionTipoComunicacion) {
        try {
            switch (accionTipoComunicacion) {
                case TransferirComprimido:
                    sesionMensajeLog(SERVICIO_CARGA_DIARIA, Constantes.PROCESO_INICIO_SESION,
                            Constantes.MENSAJE_INICIO_SESION, Constantes.ESTADO_OK);
                    mostrarProgresoCarga();
                    this.estadoComunicacion.habilitarRegreso(true);
                    this.estadoComunicacion.informarProgreso("Cargando Datos...", (progreso10 * 1));
                    this.solicitarCargaDatos(this.SERVICIO_CARGA_DIARIA);
                    break;
                case ValidacionVersionApp:
                    this.estadoComunicacion.informarProgreso("VERIFICANDO VERSIÓN...", (progreso10 * 2));
                    Mensaje mensajeResultado = this.cargaDiaria.validarVersionApp(this.versionSoftware);
                    if (mensajeResultado.getRespuesta()) {
                        this.ejecutarCargaCentral(AccionTipoComunicacion.ValidacionVersionMaestro);
                    } else {
                        this.estadoComunicacion.informarError(new Exception(mensajeResultado.getMensaje()));
                    }
                    break;
                case ValidacionExistenciaMaestro:
                    this.estadoComunicacion.informarProgreso("VERIFICANDO MAESTROS...", (progreso10 * 3));
                    if (this.cargaDiaria.validarExistenciaMaestros()) {
                        //empezar la carga diaria
                        hayMaestros = true;
                        this.ejecutarCargaCentral(TransferirComprimido);
                    } else {
                        //cargar maestros
                        this.ejecutarCargaMaestros(TransferirComprimido);
                    }
                    break;
                case ValidacionVersionMaestro:
                    this.estadoComunicacion.informarProgreso("VERIFICANDO MAESTROS...", (progreso10 * 4));
                    if (this.cargaDiaria.validarVersionMaestros()) {
                        this.ejecutarCargaCentral(AccionTipoComunicacion.ValidacionCorreria);
                    } else {
                        if (!this.cargaDiaria.esNecesarioConfirmarRemplazoMaestros()) {
                            this.ejecutarCargaMaestros(TransferirComprimido);
                        }
                    }
                    break;

                case ValidacionCorreria:
                    this.estadoComunicacion.informarProgreso("VERIFICANDO CORRERÍAS...", (progreso10 * 5));
                    boolean validarcorrerias = this.cargaDiaria.validarCorrerias();
                    listaCorrerias = this.cargaDiaria.getListaCorrerias();
                    if (validarcorrerias) {
                        this.ejecutarCargaCentral(AccionTipoComunicacion.TransferirAdjunto);
                    } else {
                        this.cargaDiaria.confirmarRemplazoCorreria(0);
                    }
                    break;
                case ValidacionOrdenTrabajo:
                    this.estadoComunicacion.informarProgreso("VERIFICANDO TAREAS...", (progreso10 * 5));
                    if (this.cargaDiaria.validarTareasXOrdenTrabajo()) {
                        this.ejecutarCargaCentral(AccionTipoComunicacion.IntegrarDatos);
                    } else {
                        this.cargaDiaria.confirmarRemplazoTarea(0);
                    }
                    break;
                case TransferirAdjunto:
                    this.estadoComunicacion.informarFase("TRANSFERIENDO ARCHIVOS");
                    this.estadoComunicacion.informarProgreso((progreso10 * 7));
                    this.cargaDiaria.eliminarAdjuntosTareasARecargar();
                    this.iniciarCargaAdjuntos();
                    break;
                case IntegrarDatos:
                    if (talleres.getConfirmacion().isEmpty())
                        this.estadoComunicacion.establecerEstadoConexionTerminal(EstadoConexionTerminal.PuedeDesconectar);
                    mostrarProgresoIntegracion();
                    this.estadoComunicacion.habilitarRegreso(false);
                    this.estadoComunicacion.informarProgreso((progreso10 * 8));
                    this.cargaDiaria.integrar(TipoComunicacion.CargarCentral);
                    talleres = talleresBL.cargarPrimerRegistro();
                    confirmacion();
                    terminar();
                    break;
            }
        } catch (Exception e) {
            try {
                estadoComunicacion.informarError(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void terminar() {
        if (this.cargaDiaria.getListaNotificacionOT().size() > 0) {
            this.mostrarResultadoTareasYOrdenesCanceladas(this.cargaDiaria.getListaNotificacionOT());
        }
        this.estadoComunicacion.establecerEstadoConexionTerminal(EstadoConexionTerminal.PuedeDesconectar);
        this.estadoComunicacion.establecerCargaSatisfactoria();
        this.estadoComunicacion.informarProgreso((progreso10 * 10));
    }

    private void mostrarProgresoCarga() throws IOException {
        if (hayMaestros)
            this.estadoComunicacion.informarFase("Paso 1 de 2. CARGA CORRERÍA.");
        else
            this.estadoComunicacion.informarFase("Paso 3 de 4. CARGA CORRERÍA.");
    }

    private void mostrarResultadoTareasYOrdenesCanceladas(List<com.example.dominio.modelonegocio.NotificacionOrdenTrabajo> listaNotificacionOT) {
        ResultadoNotificacionOTPopUp resultadoNotificacionOTPopUp = new ResultadoNotificacionOTPopUp();
        resultadoNotificacionOTPopUp.setListaNotificacionOT(listaNotificacionOT);
        FragmentManager fragmentManager = ((AppCompatActivity) contexto).getSupportFragmentManager();
        resultadoNotificacionOTPopUp.show(fragmentManager, "");
    }

    private void enviarZipAServidor(String ruta,
                                    String servicio) throws Exception {
        PeticionDescarga peticionDescarga = this.crearPeticionDescarga(ruta, servicio);
        if (peticionDescarga != null) {
            new ServicioTalleres(new SuscriptorServicio(), contexto,
                    talleres.getRutaServidor()).solicitudDescarga(peticionDescarga);
        } else {
            String mensaje = "No se obtuvo la petición para descarga.";
            estadoComunicacion.informarError(new Exception(mensaje));
        }
    }

    private PeticionDescarga crearPeticionDescarga(String ruta, String servicio) throws Exception {
        PeticionDescarga peticionDescarga = null;
        try {
            byte[] bytesArchivo = controladorArchivosAdjuntos.traerBytes(new File(ruta));

            String encodedString = Base64.encodeToString(bytesArchivo, Base64.DEFAULT);
            peticionDescarga = new PeticionDescarga();
            peticionDescarga.CodPrograma = CODIGO_PROGRAMA;
            peticionDescarga.CodTerminal = numeroTerminal;
            peticionDescarga.IdServicio = servicio;
            peticionDescarga.Archivo = encodedString;
            peticionDescarga.Longitud = String.valueOf(bytesArchivo.length);
            peticionDescarga.Sesion = SESION_CARGA;

        } catch (FileNotFoundException e) {
            Log.error(e, "Comunicaciones Descarga Adjuntos");
            registrarLog("Error Comunicaciones Descarga Adjuntos " + e.getMessage());
            throw e;
        }
        return peticionDescarga;
    }

    private void ejecutarReciboWebSync(AccionTipoComunicacion accionTipoComunicacion) {
        try {
            switch (accionTipoComunicacion) {
                case TransferirComprimido:
                    sesionMensajeLog(SERVICIO_RECIBO_WEB, Constantes.PROCESO_INICIO_SESION,
                            Constantes.MENSAJE_INICIO_SESION, Constantes.ESTADO_OK);

                    mostrarProgresoCarga();

                    this.estadoComunicacion.habilitarRegreso(true);
                    this.estadoComunicacion.informarProgreso("Cargando Datos...", (progreso10));
                    this.solicitarCargaDatos(this.SERVICIO_RECIBO_WEB);
                    break;
                case ValidacionVersionApp:
                    this.estadoComunicacion.informarProgreso("VERIFICANDO VERSIÓN...", (progreso10 * 2));
                    Mensaje mensajeResultado = this.cargaDiaria.validarVersionApp(this.versionSoftware);
                    if (mensajeResultado.getRespuesta()) {
                        this.ejecutarReciboWeb(AccionTipoComunicacion.ValidacionVersionMaestro);
                    } else {
                        this.estadoComunicacion.informarError(new Exception(mensajeResultado.getMensaje()));
                    }
                    break;
                case ValidacionExistenciaMaestro:
                    this.estadoComunicacion.informarProgreso("VERIFICANDO MAESTROS...", (progreso10 * 3));
                    if (this.cargaDiaria.validarExistenciaMaestros()) {
                        //empezar la carga diaria
                        hayMaestros = true;
                        this.ejecutarReciboWeb(TransferirComprimido);
                    } else {
                        this.estadoComunicacion.informarError(new Exception("Para ejecutar el" +
                                " recibo web debe tener previamente maestros cargados."));
                    }
                    break;
                case ValidacionVersionMaestro:
                    this.estadoComunicacion.informarProgreso("VERIFICANDO MAESTROS...", (progreso10 * 4));
                    if (this.cargaDiaria.validarVersionMaestros()) {
                        this.ejecutarReciboWeb(AccionTipoComunicacion.ValidacionCorreria);
                    } else {
                        if (!this.cargaDiaria.esNecesarioConfirmarRemplazoMaestros()) {
                            this.ejecutarCargaMaestros(TransferirComprimido);
                        }
                    }
                    break;

                case ValidacionCorreria:
                    this.estadoComunicacion.informarProgreso("VERIFICANDO CORRERÍAS...", (progreso10 * 5));
                    boolean validarcorrerias = this.cargaDiaria.validarCorrerias();
                    listaCorrerias = this.cargaDiaria.getListaCorrerias();
                    if (validarcorrerias) {
                        this.ejecutarReciboWeb(AccionTipoComunicacion.TransferirAdjunto);
                    } else {
                        this.cargaDiaria.confirmarRemplazoCorreria(0);
                    }
                    break;
                case ValidacionOrdenTrabajo:
                    this.estadoComunicacion.informarProgreso("VERIFICANDO TAREAS...", (progreso10 * 5));
                    if (this.cargaDiaria.validarTareasXOrdenTrabajo()) {
                        this.ejecutarReciboWeb(AccionTipoComunicacion.IntegrarDatos);
                    } else {
                        this.cargaDiaria.confirmarRemplazoTarea(0);
                    }
                    break;
                case TransferirAdjunto:
                    this.estadoComunicacion.informarFase("TRANSFERIENDO ARCHIVOS");
                    this.estadoComunicacion.informarProgreso((progreso10 * 7));
                    this.cargaDiaria.eliminarAdjuntosTareasARecargar();
                    this.iniciarCargaAdjuntosReciboWeb();
                    break;
                case IntegrarDatos:
                    mostrarProgresoIntegracion();
                    this.estadoComunicacion.habilitarRegreso(false);
                    this.estadoComunicacion.informarProgreso((progreso10 * 8));
                    this.cargaDiaria.integrar(TipoComunicacion.ReciboWeb);
                    this.estadoComunicacion.establecerEstadoConexionTerminal(EstadoConexionTerminal.PuedeDesconectar);
                    this.estadoComunicacion.informarProgreso((progreso10 * 10));
                    this.estadoComunicacion.establecerCargaSatisfactoria();
                    break;
            }
        } catch (Exception e) {
            try {
                estadoComunicacion.informarError(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private boolean confirmacion() throws Exception {
        sesionMensajeLog(SERVICIO_CONFIRMACION, Constantes.PROCESO_INICIO_SESION,
                Constantes.MENSAJE_INICIO_SESION, Constantes.ESTADO_OK);

        ParametrosConfirmacion parametrosConfirmacion = new ParametrosConfirmacion(talleres.getConfirmacion());

        if (!parametrosConfirmacion.isNoConfirmar()) {
            this.estadoComunicacion.informarFase("Paso 1 de 1. REALIZANDO CONFIRMACIÓN.");
            this.descarga.setSesion("T_" + SESION_CARGA);
            this.descarga.setCodigosOTsIntegradas(this.cargaDiaria.getCodigosOTsAConfirmar());
            this.descarga.setCodigosCorreriasIntegradas(this.cargaDiaria.getCodigoCorreriasAConfirmar());
            this.descarga.generarArchivosXmlDtoConfirmacion(Constantes.traerRutaDescarga());
            actualizarEstadoComunicacionBD();
            String rutaArchivoComprimido = Constantes.traerRutaDescarga() + numeroTerminal + ".zip";
            ZipManager.zip(this.descarga.getArchivosGenerados(), rutaArchivoComprimido,
                    numeroTerminal + CODIGO_PROGRAMA);
            this.enviarZipAServidor(rutaArchivoComprimido,
                    SERVICIO_CONFIRMACION);
        }
        return !parametrosConfirmacion.isNoConfirmar();

    }

    private void actualizarEstadoComunicacionBD() {
        for (String codigoCorreria : this.codigosCorreriasIntegradas) {
            ListaOrdenTrabajo listaOrdenTrabajo = ordenTrabajoBL.cargarOrdenesTrabajo(codigoCorreria);

            for (OrdenTrabajo ordenTrabajo : listaOrdenTrabajo) {
                ordenTrabajo.setEstadoComunicacion("C");
                ordenTrabajoBL.actualizarOrdenTrabajo(ordenTrabajo);
            }
        }
    }

    private void mostrarProgresoIntegracion() throws IOException {
        if (hayMaestros)
            this.estadoComunicacion.informarFase("Paso 2 de 2. INTEGRANDO CORRERÍA.");
        else
            this.estadoComunicacion.informarFase("Paso 4 de 4. INTEGRANDO CORRERÍA.");
    }

    public void ejecutarCargaMaestros(AccionTipoComunicacion accionTipoComunicacion) {
        this.ejecutarCargaMaestrosAsync(accionTipoComunicacion);
    }

    private void ejecutarCargaMaestrosAsync(AccionTipoComunicacion accionTipoComunicacion) {
        Runnable runnable = () -> ejecutarCargaMaestrosSync(accionTipoComunicacion);
        this.executorService.execute(runnable);
    }

    private void ejecutarCargaMaestrosSync(AccionTipoComunicacion accionTipoComunicacion) {
        try {
            switch (accionTipoComunicacion) {
                case TransferirComprimido:
                    sesionMensajeLog(this.SERVICIO_CARGA_MAESTROS, Constantes.PROCESO_INICIO_SESION,
                            Constantes.MENSAJE_INICIO_SESION, Constantes.ESTADO_OK);
                    this.estadoComunicacion.habilitarRegreso(true);
                    this.estadoComunicacion.informarFase("Paso 1 de 4. CARGANDO MAESTROS.");
                    estadoComunicacion.informarProgreso("Cargando datos...", progreso10 * 3);
                    this.solicitarCargaDatos(this.SERVICIO_CARGA_MAESTROS);
                    break;
                case ValidacionVersionApp:
                    estadoComunicacion.informarProgreso("Verificando versión...", progreso10 * 3);
                    Mensaje mensajeResultado = this.cargaMaestros.validarVersionApp(this.versionSoftware);
                    if (mensajeResultado.getRespuesta()) {
                        this.ejecutarCargaMaestros(AccionTipoComunicacion.ValidacionExistenciaMaestro);
                    } else {
                        this.estadoComunicacion.informarError(new Exception(mensajeResultado.getMensaje()));
                    }
                    break;
                case ValidacionExistenciaMaestro:
                    this.estadoComunicacion.informarProgreso("Verificando maestros...", progreso10 * 3);
                    if (this.cargaMaestros.validarExistenciaMaestros()) {
                        this.ejecutarCargaMaestros(AccionTipoComunicacion.ValidacionVersionMaestro);
                    } else {
                        //cargar maestros
                        this.ejecutarCargaMaestros(AccionTipoComunicacion.IntegrarDatos);
                    }
                    break;
                case ValidacionVersionMaestro:
                    this.estadoComunicacion.informarProgreso("Verificando maestros...", progreso10 * 4);
                    if (this.cargaMaestros.validarVersionMaestros()) {
                        sesionMensajeLog(SERVICIO_CARGA_MAESTROS, Constantes.PROCESO_FIN_SESION,
                                Constantes.MENSAJE_FIN_SESION, Constantes.ESTADO_OK);
                        if (tipoComunicacion.equals(TipoComunicacion.ReciboWeb)) {
                            this.ejecutarReciboWeb(TransferirComprimido);
                        } else {
                            this.ejecutarCargaCentral(TransferirComprimido);
                        }
                    } else {
                        if (!this.cargaMaestros.esNecesarioConfirmarRemplazoMaestros()) {
                            this.ejecutarCargaMaestros(AccionTipoComunicacion.IntegrarDatos);
                        }
                    }
                    break;
                case IntegrarDatos:
                    this.estadoComunicacion.habilitarRegreso(false);
                    this.estadoComunicacion.informarFase("Paso 2 de 4. INTEGRANDO MAESTROS.");
                    estadoComunicacion.informarProgreso(progreso10 * 7);
                    this.cargaMaestros.setActualizarMaestros(this.cargaDiaria.obtenerCargaDTO() != null ?
                            this.cargaDiaria.obtenerCargaDTO().VersionMaestros : null);
                    this.cargaMaestros.integrar();
                    this.estadoComunicacion.establecerCargaSatisfactoria();
                    estadoComunicacion.informarProgreso(progreso10 * 100);
                    sesionMensajeLog(SERVICIO_CARGA_MAESTROS, Constantes.PROCESO_FIN_SESION,
                            Constantes.MENSAJE_FIN_SESION, Constantes.ESTADO_OK);
                    if (tipoComunicacion.equals(TipoComunicacion.ReciboWeb)) {
                        this.ejecutarReciboWeb(TransferirComprimido);
                    } else {
                        this.ejecutarCargaCentral(TransferirComprimido);
                    }
                    break;
            }
        } catch (Exception e) {
            try {
                estadoComunicacion.informarError(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void ejecutarReciboDirectoSync(AccionTipoComunicacion accionTipoComunicacion) {
        try {
            switch (accionTipoComunicacion) {
                case TransferirComprimido:
                    this.estadoComunicacion.informarFase("RECIBO DIRECTO CORRERÍA.");
                    this.estadoComunicacion.informarProgreso("Cargando datos...", (progreso10 * 1));
                    cargaDiaria.postEjecucionCarga(Constantes.traerRutaReciboDirecto(), Constantes.NOMBRE_ARCHIVO_ZIP_ENVIO_DIRECTO, CODIGO_PROGRAMA);
                    ejecutarReciboDirecto(AccionTipoComunicacion.ValidacionVersionApp);
                    break;
                case ValidacionVersionApp:
                    this.estadoComunicacion.informarProgreso("VERIFICANDO VERSIÓN...", (progreso10 * 2));
                    Mensaje mensajeResultado = this.cargaDiaria.validarVersionApp(this.versionSoftware);
                    if (mensajeResultado.getRespuesta()) {
                        this.ejecutarReciboDirecto(AccionTipoComunicacion.ValidacionVersionMaestro);
                    } else {
                        this.estadoComunicacion.informarError(new Exception(mensajeResultado.getMensaje()));
                    }
                    break;
                case ValidacionExistenciaMaestro:
                    this.estadoComunicacion.informarProgreso("VERIFICANDO MAESTROS...", (progreso10 * 3));
                    if (this.cargaDiaria.validarExistenciaMaestros()) {
                        //Iniciar recibo directo
                        this.ejecutarReciboDirecto(AccionTipoComunicacion.ValidacionVersionMaestro);
                    } else {
                        this.estadoComunicacion.informarError(new Exception("No existen maestros cargados"));
                    }
                    break;
                case ValidacionVersionMaestro:
                    this.estadoComunicacion.informarProgreso("VERIFICANDO MAESTROS...", (progreso10 * 4));
                    if (this.cargaDiaria.validarVersionMaestros()) {
                        this.ejecutarReciboDirecto(AccionTipoComunicacion.ValidacionCorreria);
                    } else {
                        //this.estadoComunicacion.informarError(new Exception(mensajeResultado.getMensaje())); TODO: REVISAR SI NO ES LA MISMA VERSION DE MAESTROS, QUE SI RETORNE EL MENSAJE DE ERROR Y QUE PARE LA CARGA
                    }
                    break;
                case ValidacionCorreria:
                    this.estadoComunicacion.informarProgreso("VERIFICANDO CORRERÍAS...", (progreso10 * 5));
                    boolean validarcorrerias = this.cargaDiaria.validarCorrerias();
                    listaCorrerias = this.cargaDiaria.getListaCorrerias();
                    if (validarcorrerias) {
                        this.ejecutarReciboWeb(AccionTipoComunicacion.TransferirAdjunto);
                    } else {
                        this.cargaDiaria.confirmarRemplazoCorreria(0);
                    }
                    break;
                case ValidacionOrdenTrabajo:
                    this.estadoComunicacion.informarProgreso("VERIFICANDO TAREAS...", (progreso10 * 5));
                    if (this.cargaDiaria.validarTareasXOrdenTrabajo()) {
                        this.ejecutarReciboDirecto(AccionTipoComunicacion.IntegrarDatos);
                    } else {
                        this.cargaDiaria.confirmarRemplazoTarea(0);
                    }
                    break;
                case TransferirAdjunto:
                    this.estadoComunicacion.informarFase("TRANSFERIENDO ARCHIVOS");
                    this.estadoComunicacion.informarProgreso("TRANSFERIENDO ARCHIVOS...", (progreso10 * 7));
                    this.cargaDiaria.eliminarAdjuntosTareasARecargar();
                    String rutaAdjuntos = Constantes.traerRutaReciboDirecto() + Constantes.NOMBRE_CARPETA_ADJUNTOS;
                    this.iniciarReciboDirectoAdjuntos(rutaAdjuntos);
                    break;
                case IntegrarDatos:
                    this.estadoComunicacion.establecerEstadoConexionTerminal(EstadoConexionTerminal.PuedeDesconectar);
                    this.estadoComunicacion.informarFase("INTEGRANDO DATOS.");
                    this.estadoComunicacion.informarProgreso((progreso10 * 8));
                    this.cargaDiaria.integrar(TipoComunicacion.ReciboDirecto);
                    this.estadoComunicacion.establecerCargaSatisfactoria();
                    this.estadoComunicacion.informarProgreso((progreso10 * 10));
                    if (this.cargaDiaria.getListaNotificacionOT().size() > 0) {
                        this.mostrarResultadoTareasYOrdenesCanceladas(this.cargaDiaria.getListaNotificacionOT());
                    }
                    FileManager.eliminarCarpetaRecursivo(new File(Constantes.traerRutaReciboDirecto()));
                    break;
            }
        } catch (Exception e) {
            try {
                estadoComunicacion.informarError(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void solicitarCargaDatos(String codigoServicio) {
        PeticionCarga peticionCarga = new PeticionCarga();
        peticionCarga.CodPrograma = ComunicacionCarga.CODIGO_PROGRAMA;
        peticionCarga.CodTerminal = this.numeroTerminal;
        peticionCarga.IdServicio = codigoServicio;
        peticionCarga.Sesion = SESION_CARGA;

        SuscriptorServicio suscriptorServicio = new SuscriptorServicio();

        new ServicioTalleres(suscriptorServicio, contexto, this.talleres.getRutaServidor()).solicitudCarga(peticionCarga);
    }

    public void solicitarDescargaDatos(String codigoServicio) {
        PeticionDescarga peticionDescarga = new PeticionDescarga();
        peticionDescarga.CodPrograma = ComunicacionCarga.CODIGO_PROGRAMA;
        peticionDescarga.CodTerminal = this.numeroTerminal;
        peticionDescarga.IdServicio = codigoServicio;
        peticionDescarga.Sesion = SESION_CARGA;

        SuscriptorServicio suscriptorServicio = new SuscriptorServicio();

        new ServicioTalleres(suscriptorServicio, contexto, this.talleres.getRutaServidor()).solicitudDescarga(peticionDescarga);
    }

    private void sesionMensajeLog(String servicio, String idProceso,
                                  String mensaje, String estado) throws Exception {
        PeticionMensajeLog peticionMensajeLog = this.crearPeticionMensajeLog(servicio, idProceso, mensaje, estado);
        //registrarLog(mensaje);
        if (peticionMensajeLog != null) {
            new ServicioTalleres(new SuscriptorServicio(),
                    this.contexto, talleres.getRutaServidor()).solicitudMensajeLog(peticionMensajeLog);
        }
    }

    private PeticionMensajeLog crearPeticionMensajeLog(String servicio, String idProceso,
                                                       String mensaje, String estado) {
        PeticionMensajeLog peticionMensajeLog = null;
        peticionMensajeLog = new PeticionMensajeLog();
        peticionMensajeLog.CodPrograma = CODIGO_PROGRAMA;
        peticionMensajeLog.CodTerminal = numeroTerminal;
        peticionMensajeLog.IdServicio = servicio;
        peticionMensajeLog.IdProceso = idProceso;
        peticionMensajeLog.Mensaje = mensaje;
        peticionMensajeLog.Sesion = SESION_CARGA;
        peticionMensajeLog.Estado = estado;
        return peticionMensajeLog;
    }

    public void respuestaRemplazoCorreria(int indiceCorreria, boolean remplazar) {
        this.cargaDiaria.respuestaRemplazoCorreria(indiceCorreria, remplazar);
        if (!this.cargaDiaria.confirmarRemplazoCorreria((indiceCorreria + 1))) {
            if (tipoComunicacion.equals(TipoComunicacion.ReciboWeb)) {
                this.ejecutarReciboWeb(AccionTipoComunicacion.ValidacionOrdenTrabajo);
            } else if (tipoComunicacion.equals(TipoComunicacion.ReciboDirecto)) {
                this.ejecutarReciboWeb(AccionTipoComunicacion.ValidacionOrdenTrabajo);
            } else {
                this.ejecutarCargaCentral(AccionTipoComunicacion.ValidacionOrdenTrabajo);
            }
        }
    }

    public void respuestaRemplazoTarea(int indiceTarea, boolean remplazar) {
        this.cargaDiaria.respuestaRemplazoTarea(indiceTarea, remplazar);
        if (!this.cargaDiaria.confirmarRemplazoTarea((indiceTarea + 1))) {
            if (tipoComunicacion.equals(TipoComunicacion.ReciboWeb)) {
                this.ejecutarReciboWeb(AccionTipoComunicacion.TransferirAdjunto);
            } else {
                this.ejecutarCargaCentral(AccionTipoComunicacion.TransferirAdjunto);
            }
        }
    }

    public void respuestaRemplazoCorreriaReciboDirecto(int indiceCorreria, boolean remplazar) {
        this.cargaDiaria.respuestaRemplazoCorreria(indiceCorreria, remplazar);
        if (!this.cargaDiaria.confirmarRemplazoCorreria((indiceCorreria + 1))) {
            this.ejecutarReciboDirecto(AccionTipoComunicacion.ValidacionOrdenTrabajo);
        }
    }

    public void respuestaRemplazoTareaReciboDirecto(int indiceTarea, boolean remplazar) {
        this.cargaDiaria.respuestaRemplazoTarea(indiceTarea, remplazar);
        if (!this.cargaDiaria.confirmarRemplazoTarea((indiceTarea + 1))) {
            this.ejecutarReciboDirecto(AccionTipoComunicacion.TransferirAdjunto);
        }
    }

    public void respuestaConfirmarActualizarMaestros(boolean actualizar) throws IOException {
        if (actualizar) {
            try {
                this.borrarMaestros();
                this.ejecutarCargaMaestros(AccionTipoComunicacion.IntegrarDatos);
            } catch (Exception e) {
                e.printStackTrace();
                this.estadoComunicacion.informarError(e);
            }
        } else {
            Mensaje mensaje = new Mensaje();
            mensaje.setRespuesta(false);
            mensaje.setMensaje("No se realizó la carga porque no se remplazó maestros.");
            estadoComunicacion.informarResultado(mensaje, null, null);
            estadoComunicacion.informarProgreso("Proceso terminado", 100);
        }
    }

    private boolean tieneConexionInternet() {
        WifiHelper wifiHelper = new WifiHelper(this.contexto);
        MobileDataHelper mobileDataHelper = new MobileDataHelper(this.contexto);
        return wifiHelper.estaConectadoWifi() || mobileDataHelper.estaConectado();
    }

    private void borrarMaestros() {
        List<Usuario> usuarios = this.usuarioBL.cargarUsuarios();
        this.talleresBL.borrarBD();
        this.usuarioBL.guardar(usuarios);
        this.cargaDiaria.eliminarTodosLosAdjuntos();
    }

    private void registrarLog(String mensaje) throws IOException {
        StringHelper.registrarLog(mensaje);
    }

    void iniciarCargaAdjuntos() throws Exception {
        this.nombresArchivosAdjuntos = this.cargaDiaria.identificarNombresArchivosAdjuntos();
        if (this.nombresArchivosAdjuntos != null && this.nombresArchivosAdjuntos.size() > 0) {
            sesionMensajeLog(this.SERVICIO_CARGA_DIARIA, Constantes.PROCESO_FIN_SESION,
                    Constantes.MENSAJE_FIN_SESION, Constantes.ESTADO_OK);
            ejecutarServicioCargaArchivoAdjuntos(0, SERVICIO_CARGA_ADJUNTOS);
        } else {
            sesionMensajeLog(this.SERVICIO_CARGA_DIARIA, Constantes.PROCESO_FIN_SESION,
                    Constantes.MENSAJE_FIN_SESION, Constantes.ESTADO_OK);
            this.ejecutarCargaCentral(AccionTipoComunicacion.IntegrarDatos);
        }
    }

    void iniciarCargaAdjuntosReciboWeb() throws Exception {
        this.nombresArchivosAdjuntos = this.cargaDiaria.identificarNombresArchivosAdjuntos();
        if (this.nombresArchivosAdjuntos != null && this.nombresArchivosAdjuntos.size() > 0) {
            ejecutarServicioCargaArchivoAdjuntos(0, SERVICIO_ADJUNTOS_RECIBO_WEB);
        } else {
            this.ejecutarReciboWeb(AccionTipoComunicacion.IntegrarDatos);
        }
    }

    void iniciarReciboDirectoAdjuntos(String ruta) throws Exception {
        this.nombresArchivosAdjuntos = this.cargaDiaria.identificarNombresArchivosAdjuntos();
        if (this.nombresArchivosAdjuntos != null && this.nombresArchivosAdjuntos.size() > 0) {
            ejecutarTransferenciaArchivosAdjuntos(ruta);
        } else {
            this.ejecutarReciboDirecto(AccionTipoComunicacion.IntegrarDatos);
        }
    }

    void ejecutarServicioCargaArchivoAdjuntos(int pasoEjecucion, String servicio) throws Exception {
        if (pasoEjecucion < this.nombresArchivosAdjuntos.size()) {

            String nombreArchivo = this.nombresArchivosAdjuntos.get(pasoEjecucion).getNombreArchivo();
            this.estadoComunicacion.informarFase("TRANSFERIENDO ARCHIVOS. (" + nombreArchivo + ")");
            PeticionCarga peticionCarga = new PeticionCarga();
            peticionCarga.CodPrograma = CODIGO_PROGRAMA;
            peticionCarga.CodTerminal = talleres.getNumeroTerminal();
            peticionCarga.IdServicio = servicio;
            peticionCarga.Sesion = SESION_CARGA;
            peticionCarga.ParamFile = nombreArchivo;
            this.estadoComunicacion.informarProgreso("Tranfiriendo archivo " + (pasoEjecucion + 1) + " de " + this.nombresArchivosAdjuntos.size());
            new ServicioTalleres(new SuscriptorServicio(pasoEjecucion), this.contexto,
                    talleres.getRutaServidor()).solicitudCargaAdjuntos(peticionCarga);
        } else {
            if (tipoComunicacion.equals(TipoComunicacion.ReciboWeb)) {
                this.ejecutarReciboWeb(AccionTipoComunicacion.IntegrarDatos);
            } else if (tipoComunicacion.equals(TipoComunicacion.CargarCentral)) {
                this.ejecutarCargaCentral(AccionTipoComunicacion.IntegrarDatos);
            }

        }
    }

    void ejecutarTransferenciaArchivosAdjuntos(String ruta) throws Exception {
        this.estadoComunicacion.informarFase("TRANSFIRIENDO ARCHIVOS.");
        try {
            controladorArchivosAdjuntos.copiarArchivos(ruta, Constantes.traerRutaAdjuntos());
            this.ejecutarReciboDirecto(AccionTipoComunicacion.IntegrarDatos);
        } catch (Exception e) {
            Log.error(e, "Comunicaciones - Recibo de adjuntos");
            String mensaje = "Falló recibo de adjuntos. " + e.getMessage();
            estadoComunicacion.informarError(new Exception(mensaje));
        }
    }

    final class SuscriptorServicio implements Suscriptor {
        int pasoEjecucion;

        SuscriptorServicio(int pasoEjecucion) {
            this.pasoEjecucion = pasoEjecucion;
        }

        SuscriptorServicio() {
        }

        @Override
        public void onError(Throwable e) throws IOException {
            estadoComunicacion.informarError(new Exception(e));
        }

        @Override
        public void onCompletetado() {

        }

        @Override
        public <T> void onResultado(T datos, String codigoServicio) {
            try {
                switch (codigoServicio) {
                    case SERVICIO_CARGA_DIARIA:
                        cargaDiaria.postEjecucionServicioCarga((RespuestaCarga) datos
                                , Constantes.NOMBRE_CARPETA_CARGA_DIARIA
                                , numeroTerminal
                                , ComunicacionCarga.CODIGO_PROGRAMA);
                        ejecutarCargaCentral(AccionTipoComunicacion.ValidacionVersionApp);
                        break;
                    case SERVICIO_CARGA_MAESTROS:
                        cargaMaestros.postEjecucionServicioCarga((RespuestaCarga) datos
                                , Constantes.NOMBRE_CARPETA_MAESTROS
                                , numeroTerminal
                                , ComunicacionCarga.CODIGO_PROGRAMA);
                        ejecutarCargaMaestros(AccionTipoComunicacion.ValidacionVersionApp);
                        break;
                    case SERVICIO_RECIBO_WEB:
                        cargaDiaria.postEjecucionServicioCarga((RespuestaCarga) datos
                                , Constantes.NOMBRE_CARPETA_RECIBO_WEB
                                , numeroTerminal
                                , ComunicacionCarga.CODIGO_PROGRAMA);
                        ejecutarReciboWeb(AccionTipoComunicacion.ValidacionVersionApp);
                        break;
                    case SERVICIO_CONFIRMACION:
                        RespuestaDescarga respuestaDescarga = (RespuestaDescarga) datos;
                        if (respuestaDescarga.Resultado) {
                            sesionMensajeLog(SERVICIO_CONFIRMACION, Constantes.PROCESO_FIN_SESION,
                                    Constantes.MENSAJE_FIN_SESION, Constantes.ESTADO_OK);
                            FileManager.deleteFolderContent(new File(Constantes.traerRutaDescarga()).getAbsolutePath());
                            FileManager.eliminarCarpetaRecursivo(new File(Constantes.traerRutaDescarga()));
                            terminar();
                        } else {
                            estadoComunicacion.informarError(new Exception(respuestaDescarga.Mensaje));
                        }
                        break;
                }
            } catch (Exception ex) {
                Log.error(ex, "Error comunicaciones");
                try {
//                    sesionMensajeLog(codigoServicio,
//                            "", ex.getMessage(), Constantes.ESTADO_ERROR);
                    sesionMensajeLog(codigoServicio, Constantes.PROCESO_FIN_SESION,
                            Constantes.MENSAJE_FIN_SESION, Constantes.ESTADO_ERROR);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    estadoComunicacion.informarError(ex);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        @Override
        public <T> void onResultadoAdjuntos(T datos) {
            try {
                RespuestaCarga respuestaCarga = (RespuestaCarga) datos;
                byte[] archivoZIP;
                if (respuestaCarga.Binario != null) {
                    archivoZIP = FileManager.convertStringToByteArray(respuestaCarga.Binario);
                    File archivo = new File(nombresArchivosAdjuntos.get(pasoEjecucion).getRutaArchivo());
                    String ruta;
                    if (FileManager.existFolder(archivo)) {
                        ruta = nombresArchivosAdjuntos.get(pasoEjecucion).getRutaArchivo();
                    } else {
                        ruta = Constantes.traerRutaAdjuntos();
                    }
                    new File(ruta).mkdirs();
                    Calendar calendar = Calendar.getInstance();
                    Date time = calendar.getTime();
                    long milliseconds = time.getTime();
                    String nombreZip = "adjuntos_" + String.valueOf(milliseconds) + ".zip";
                    FileManager.createFile(archivoZIP, ruta + nombreZip);
                    ZipManager.unZip(ruta + nombreZip, ruta, talleres.getNumeroTerminal() + CODIGO_PROGRAMA);

                    switch (tipoComunicacion) {
                        case ReciboWeb:
                            ejecutarServicioCargaArchivoAdjuntos(this.pasoEjecucion + 1, SERVICIO_ADJUNTOS_RECIBO_WEB);
                            break;
                        case CargarCentral:
                        case CargaMaestros:
                            ejecutarServicioCargaArchivoAdjuntos(this.pasoEjecucion + 1, SERVICIO_CARGA_ADJUNTOS);
                            break;
                    }

                    new File(ruta + nombreZip).delete();

                } else {
                    sesionMensajeLog(SERVICIO_CARGA_ADJUNTOS, "",
                            "No se pudo cargar el adjunto " + nombresArchivosAdjuntos.get(this.pasoEjecucion).getNombreArchivo(), Constantes.ESTADO_ERROR);
                    estadoComunicacion.informarError(new Exception("No se pudo cargar el adjunto " + nombresArchivosAdjuntos.get(this.pasoEjecucion).getNombreArchivo()));
                }
            } catch (IOException e) {
                Log.error(e, "Comunicaciones - Carga adjuntos");
                String mensaje = "Falló carga adjunto (" + nombresArchivosAdjuntos.get(this.pasoEjecucion) + ")." + e.getMessage();
                try {
                    sesionMensajeLog(SERVICIO_CARGA_ADJUNTOS, "",
                            mensaje, Constantes.ESTADO_ERROR);
                    registrarLog(mensaje);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                try {
                    estadoComunicacion.informarError(new Exception(mensaje));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            } catch (Exception e) {
                Log.error(e, "Comunicaciones - Carga adjuntos");
                String mensaje = "Falló carga adjunto (" + nombresArchivosAdjuntos.get(this.pasoEjecucion) + ")." + e.getMessage();
                try {
                    sesionMensajeLog(SERVICIO_CARGA_ADJUNTOS, "",
                            mensaje, Constantes.ESTADO_ERROR);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                try {
                    estadoComunicacion.informarError(new Exception(mensaje));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
