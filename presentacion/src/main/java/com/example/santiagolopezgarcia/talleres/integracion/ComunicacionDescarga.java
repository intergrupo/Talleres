package com.example.santiagolopezgarcia.talleres.integracion;

import android.content.Context;
import android.util.Base64;

import com.example.dominio.administracion.TalleresBL;
import com.example.dominio.modelonegocio.ArchivoAdjunto;
import com.example.dominio.modelonegocio.Mensaje;
import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;
import com.example.dominio.modelonegocio.Talleres;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.helpers.Constantes;
import com.example.santiagolopezgarcia.talleres.integracion.descarga.Descarga;
import com.example.santiagolopezgarcia.talleres.services.ServicioTalleres;
import com.example.santiagolopezgarcia.talleres.services.contracts.Suscriptor;
import com.example.santiagolopezgarcia.talleres.services.dto.PeticionDescarga;
import com.example.santiagolopezgarcia.talleres.services.dto.PeticionMensajeLog;
import com.example.santiagolopezgarcia.talleres.services.dto.RespuestaDescarga;
import com.example.santiagolopezgarcia.talleres.util.ControladorArchivosAdjuntos;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IEnvioDirectoParcialView;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IEnvioDirectoView;
import com.example.utilidades.FileManager;
import com.example.utilidades.Log;
import com.example.utilidades.ZipManager;
import com.example.utilidades.helpers.StringHelper;
import com.example.utilidades.helpers.WifiHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public class ComunicacionDescarga {
    private final String SERVICIO_DESCARGA = "04";
    private final String SERVICIO_ENVIO_WEB = "24";
    private final String SERVICIO_ENVIO_ADJUNTOS_WEB = "25";
    public final static String CODIGO_PROGRAMA = "04";
    private String SESION_DESCARGA = UUID.randomUUID().toString();

    private Descarga descarga;

    private Context contexto;

    private IEstadoComunicacion estadoComunicacion;
    private ExecutorService executorService;

    private String codigoCorreria;
    private String numeroTerminal;
    private String numeroTerminalEnvio = "";
    private ControladorArchivosAdjuntos controladorArchivosAdjuntos;
    private TalleresBL talleresBL;
    private Talleres talleres;
    private OrdenTrabajoBusqueda ordenTrabajoBusqueda;
    private TipoComunicacion tipoComunicacion;
    public String versionSoftware;
    public SiriusApp app;

    private final int progreso10 = 10;

    List<ArchivoAdjunto> listaNombreAdjuntosDescarga;

    public enum AccionTipoComunicacion {
        TransferirComprimido,
        TransferirAdjunto,
        EnviarComprimido
    }

    public enum TipoComunicacion {
        DescargaCompleta,
        DescargaConFiltro,
        EnvioDirecto,
        EnvioDirectoConFiltro,
        EnvioWeb,
        EnvioWebConFiltro
    }

    @Inject
    public ComunicacionDescarga(Descarga descarga, TalleresBL talleresBL) {
        this.descarga = descarga;
        this.talleresBL = talleresBL;
    }

    public void configurar(Context contexto, SiriusApp siriusApp, IEstadoComunicacion estadoComunicacion,
                           String numeroCorreria) {
        this.contexto = contexto;
        this.estadoComunicacion = estadoComunicacion;
        this.numeroTerminal = numeroTerminalEnvio.isEmpty() ? siriusApp.getCodigoTerminal() : numeroTerminalEnvio;
        this.executorService = Executors.newSingleThreadExecutor();
        this.controladorArchivosAdjuntos = new ControladorArchivosAdjuntos();
        this.talleres = this.talleresBL.cargarPrimerRegistro();
        this.codigoCorreria = numeroCorreria;
        this.descarga.configurar(this.estadoComunicacion);
        this.versionSoftware = this.contexto.getResources().getString(R.string.version);
        this.descarga.setApp(siriusApp);
    }

    public void configurar(Context contexto, SiriusApp siriusApp, IEstadoComunicacion estadoComunicacion,
                           String numeroCorreria, OrdenTrabajoBusqueda ordenTrabajoBusqueda) {
        this.contexto = contexto;
        this.estadoComunicacion = estadoComunicacion;
        this.numeroTerminal = numeroTerminalEnvio.isEmpty() ? siriusApp.getCodigoTerminal() : numeroTerminalEnvio;
        this.executorService = Executors.newSingleThreadExecutor();
        this.controladorArchivosAdjuntos = new ControladorArchivosAdjuntos();
        this.talleres = this.talleresBL.cargarPrimerRegistro();
        this.codigoCorreria = numeroCorreria;
        this.descarga.configurar(this.estadoComunicacion);
        this.ordenTrabajoBusqueda = ordenTrabajoBusqueda;
        this.versionSoftware = this.contexto.getResources().getString(R.string.version);
        this.descarga.setApp(siriusApp);
    }

    public void ejecutar(TipoComunicacion tipoComunicacion) {
        FileManager.deleteFile(Constantes.traerRutaSirius(), Constantes.NOMBRE_ARCHIVO_ZIP_DIAGRAMS);
        this.tipoComunicacion = tipoComunicacion;
        if (tipoComunicacion.equals(TipoComunicacion.EnvioDirecto)) {
            this.ejecutarDescarga(AccionTipoComunicacion.EnviarComprimido, "");
        } else if (tipoComunicacion.equals(TipoComunicacion.EnvioDirectoConFiltro)) {
            this.ejecutarDescarga(AccionTipoComunicacion.EnviarComprimido, "");
        } else {
            switch (tipoComunicacion) {
                case DescargaCompleta:
                case DescargaConFiltro:
                    //this.ejecutarDescarga(AccionTipoComunicacion.TransferirComprimido, SERVICIO_DESCARGA);
                    this.ejecutarDescarga(AccionTipoComunicacion.TransferirAdjunto, SERVICIO_DESCARGA);
                    break;
                case EnvioWeb:
                case EnvioWebConFiltro:
                    //this.ejecutarDescarga(AccionTipoComunicacion.TransferirComprimido, SERVICIO_ENVIO_WEB);
                    this.ejecutarDescarga(AccionTipoComunicacion.TransferirAdjunto, SERVICIO_ENVIO_ADJUNTOS_WEB);
                    break;
                default:
                    String mensaje = "No se ha implementado el tipo de comunication " + tipoComunicacion.toString();
                    try {
                        registrarLog(mensaje);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    throw new IllegalArgumentException(mensaje);

            }
        }
    }

    public void setNumeroTerminalEnvio(String numeroTerminalEnvio) {
        this.numeroTerminalEnvio = numeroTerminalEnvio;
    }

    public void ejecutarDescarga(AccionTipoComunicacion accionTipoComunicacion, String servicio) {
        this.ejecutarDescargaAsync(accionTipoComunicacion, servicio);
    }

    public void ejecutarDescargaSync(AccionTipoComunicacion accionTipoComunicacion, String servicio) {
        try {
            switch (accionTipoComunicacion) {
                case TransferirComprimido:
                    this.estadoComunicacion.informarProgreso("Generando archivos para enviar descarga...", progreso10);
                    this.transferirComprimido();
                    break;
                case TransferirAdjunto:
                    this.estadoComunicacion.informarProgreso("Enviando archivo adjuntos a servidor...", progreso10 * 8);
                    this.descargarAdjuntos(servicio);
                    break;
                case EnviarComprimido:
                    this.estadoComunicacion.informarProgreso("Generando archivos para enviar...", progreso10);
                    this.enviarComprimido();
                    break;
            }
        } catch (Exception ex) {

            try {
                this.estadoComunicacion.informarError(ex);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void transferirComprimido() throws Exception {
        String ruta = "";
        String servicio = "";
        String rutaArchivoComprimido = "";
        FileManager.eliminarCarpetaRecursivo(new File(Constantes.traerRutaDescarga()));
        FileManager.eliminarCarpetaRecursivo(new File(Constantes.traerRutaEnvioWeb()));
        this.talleres.setVersionSoftware(this.versionSoftware);
        switch (this.tipoComunicacion) {
            case DescargaCompleta:
                this.descarga.setSesion(SESION_DESCARGA);
                ruta = Constantes.traerRutaDescarga() + Constantes.NOMBRE_CARPETA_DESCARGA_COMPLETA + File.separator;
                ruta = this.descarga.generarArchivosXmlDto(this.codigoCorreria, ruta);
                rutaArchivoComprimido = ruta + Constantes.NOMBRE_ARCHIVO_ZIP_DESCARGA;
                servicio = SERVICIO_DESCARGA;
                break;
            case DescargaConFiltro:
                this.descarga.setSesion(SESION_DESCARGA);
                ruta = this.descarga.generarArchivosXmlDto(this.ordenTrabajoBusqueda);
                servicio = SERVICIO_DESCARGA;
                rutaArchivoComprimido = ruta + Constantes.NOMBRE_ARCHIVO_ZIP_DESCARGA;
                break;
            case EnvioWeb:
                talleres.setNumeroTerminal(numeroTerminal);
                this.descarga.generarArchivosXmlDtoEnvio(this.codigoCorreria,
                        Constantes.traerRutaEnvioWeb(), this.talleres);
                ruta = Constantes.traerRutaEnvioWeb();
                servicio = SERVICIO_ENVIO_WEB;
                rutaArchivoComprimido = ruta + numeroTerminal + ".zip";
                break;
            case EnvioWebConFiltro:
                talleres.setNumeroTerminal(numeroTerminal);
                ruta = this.descarga.generarArchivosXmlDtoEnvio(this.ordenTrabajoBusqueda,
                        Constantes.traerRutaEnvioWeb(), this.talleres);
                servicio = SERVICIO_ENVIO_WEB;
                rutaArchivoComprimido = ruta + numeroTerminal + ".zip";
                break;
        }
        this.estadoComunicacion.informarProgreso("Enviando archivo descarga a servidor...", progreso10 * 2);

        ZipManager.zip(this.descarga.getArchivosGenerados(), rutaArchivoComprimido,
                numeroTerminal + CODIGO_PROGRAMA);
        sesionMensajeLog(AccionTipoComunicacion.TransferirComprimido, servicio, Constantes.PROCESO_INICIO_SESION,
                Constantes.MENSAJE_INICIO_SESION, Constantes.ESTADO_OK);
        this.enviarZipAServidor(rutaArchivoComprimido, AccionTipoComunicacion.TransferirComprimido, servicio);
    }

    private void enviarComprimido() throws Exception {
        String ruta = Constantes.traerRutaEnvioDirecto();
        this.talleres.setVersionSoftware(this.versionSoftware);
        this.descarga.setVersionSoftware(this.versionSoftware);
        switch (this.tipoComunicacion) {
            case EnvioDirecto:
                ruta = this.descarga.generarArchivosXmlDtoEnvio(this.codigoCorreria, ruta, this.talleres);
                break;
            case EnvioDirectoConFiltro:
                ruta = this.descarga.generarArchivosXmlDtoEnvio(this.ordenTrabajoBusqueda, ruta, this.talleres);
                break;
        }
        this.estadoComunicacion.informarProgreso("Enviando archivo a terminal...", progreso10 * 2);
        String rutaArchivoComprimido = ruta + Constantes.NOMBRE_ARCHIVO_ZIP_ENVIO_DIRECTO;
        File rutaAdjuntosEnvioDirecto = new File(Constantes.traerRutaEnvioDirecto() + Constantes.NOMBRE_CARPETA_ADJUNTOS + File.separator);
        rutaAdjuntosEnvioDirecto.mkdirs();
        comprimirAdjuntosEnvioDirecto(rutaAdjuntosEnvioDirecto.getAbsolutePath());
        this.descarga.archivosGenerados.add(rutaAdjuntosEnvioDirecto.getAbsolutePath());
        ZipManager.zip(this.descarga.getArchivosGenerados(), rutaArchivoComprimido, CODIGO_PROGRAMA);
        if (contexto instanceof IEnvioDirectoView) {
            ((IEnvioDirectoView) contexto).enviarZip();
        }
        if (contexto instanceof IEnvioDirectoParcialView) {
            ((IEnvioDirectoParcialView) contexto).enviarZip();
        }
        informarResultadoEnvioExitoso();
    }

    private void comprimirAdjuntosEnvioDirecto(String rutaAdjuntosEnvioDirecto) {
        listaNombreAdjuntosDescarga = descarga.obtenerArchivosAdjuntosFiltrado(ordenTrabajoBusqueda);
        for (ArchivoAdjunto nombreArchivo : listaNombreAdjuntosDescarga) {
            File archivo;
            archivo = new File(nombreArchivo.getRutaArchivo()
                    + nombreArchivo.getNombreArchivo());
            if (!archivo.exists()) {
                archivo = new File(Constantes.traerRutaAdjuntos() + nombreArchivo.getNombreArchivo());
            }
            if (archivo.exists()) {
                try {
                    File archivoDestino = new File(rutaAdjuntosEnvioDirecto + File.separator + nombreArchivo);
                    controladorArchivosAdjuntos.copiarArchivo(archivo,
                            archivoDestino);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void ejecutarDescargaAsync(AccionTipoComunicacion accionTipoComunicacion, String servicio) {
        Runnable runnable = () -> {
            this.ejecutarDescargaSync(accionTipoComunicacion, servicio);
        };
        this.executorService.execute(runnable);
    }

    private boolean tieneConexionInternet() {
        WifiHelper wifiHelper = new WifiHelper(this.contexto);
        return wifiHelper.estaConectadoWifi();
    }

    private void descargarAdjuntos(String servicio) {
        new File(Constantes.traerRutaSirius() + Constantes.NOMBRE_CARPETA_CAMERA).mkdirs();
        new File(Constantes.traerRutaSirius() + Constantes.NOMBRE_CARPETA_DIAGRAMS).mkdirs();
        new File(Constantes.traerRutaDescargaAdjuntos()).mkdirs();
        this.descarga.setCodigoCorreria(this.codigoCorreria);

        obtenerListaAdjuntos();
        if (this.listaNombreAdjuntosDescarga.size() > 0) {
            try {
                sesionMensajeLog(AccionTipoComunicacion.TransferirAdjunto,
                        servicio, Constantes.PROCESO_INICIO_SESION, Constantes.MENSAJE_INICIO_SESION, Constantes.ESTADO_OK);
            } catch (Exception e) {
                try {
                    registrarLog(e.getMessage());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            this.procesarArchivoAdjunto(0, servicio);
        } else {
            try {
                sesionMensajeLog(AccionTipoComunicacion.TransferirAdjunto,
                        servicio, Constantes.PROCESO_FIN_SESION, Constantes.MENSAJE_FIN_SESION, Constantes.ESTADO_OK);
            } catch (Exception e) {
                try {
                    registrarLog(e.getMessage());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            ejecutarDescarga(AccionTipoComunicacion.TransferirComprimido, servicio);
        }
    }

    private void obtenerListaAdjuntos() {
        switch (this.tipoComunicacion){
            case EnvioDirectoConFiltro:
            case EnvioWebConFiltro:
            case DescargaConFiltro:
                listaNombreAdjuntosDescarga = descarga.obtenerArchivosAdjuntosFiltrado(ordenTrabajoBusqueda);
                break;
            case DescargaCompleta:
            case EnvioDirecto:
            case EnvioWeb:
                listaNombreAdjuntosDescarga = descarga.obtenerArchivosAdjuntos();
                break;
        }
    }

    private void informarResultadoDescargaExitoso() {
        Mensaje mensaje = new Mensaje();
        mensaje.setRespuesta(true);
        mensaje.setMensaje("La descarga se realizó satisfactoriamente");
        FileManager.deleteFile(Constantes.traerRutaSirius(), Constantes.NOMBRE_ARCHIVO_ZIP_DIAGRAMS);
        try {
            this.estadoComunicacion.informarProgreso("", 100);
            this.estadoComunicacion.informarResultado(mensaje, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void informarResultadoEnvioExitoso() {
        Mensaje mensaje = new Mensaje();
        mensaje.setRespuesta(true);
        mensaje.setMensaje("El envío se ha realizado satisfactoriamente");
        try {
            this.estadoComunicacion.informarProgreso("", 100);
            this.estadoComunicacion.informarResultado(mensaje, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void procesarArchivoAdjunto(int indiceArchivo, String servicio) {
        if (indiceArchivo < this.listaNombreAdjuntosDescarga.size()) {
            File archivo = controladorArchivosAdjuntos.traerArchivoEncontrado(
                    this.listaNombreAdjuntosDescarga.get(indiceArchivo));
            if (archivo.exists()) {
                try {
                    int contadorProgreso = indiceArchivo + 1;
                    this.estadoComunicacion.informarProgreso("DESCARGA. Adjuntos (" +
                            archivo.getName() + ")... \n \n  Enviando adjunto " + contadorProgreso
                            + " de " + listaNombreAdjuntosDescarga.size());

                    controladorArchivosAdjuntos.copiarArchivo(archivo,
                            new File(Constantes.traerRutaDescargaAdjuntos() + archivo.getName()));
                    String[] nombresArchivos = new String[1];
                    nombresArchivos[0] = Constantes.traerRutaDescargaAdjuntos();
                    ZipManager.zip(nombresArchivos, Constantes.traerRutaSirius() +
                            Constantes.NOMBRE_ARCHIVO_ZIP_DIAGRAMS, numeroTerminal + CODIGO_PROGRAMA);
                    enviarZipAServidor(Constantes.traerRutaSirius() + Constantes.NOMBRE_ARCHIVO_ZIP_DIAGRAMS,
                            AccionTipoComunicacion.TransferirAdjunto, indiceArchivo, servicio);
                } catch (Exception e) {
                    Log.error(e, "Comunicaciones Descarga Adjuntos");
                    try {
                        registrarLog("Error Comunicaciones Descarga Adjuntos " + e.getMessage());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        estadoComunicacion.informarError(e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            } else {
                estadoComunicacion.informarAdjuntoNoEncontrado("El adjunto con nombre " +
                        this.listaNombreAdjuntosDescarga.get(indiceArchivo).getNombreArchivo() + " no se encontró.", indiceArchivo);
            }
        } else {
            try {
                sesionMensajeLog(AccionTipoComunicacion.TransferirAdjunto,
                        servicio, Constantes.PROCESO_FIN_SESION, Constantes.MENSAJE_FIN_SESION, Constantes.ESTADO_OK);
            } catch (Exception e) {
                try {
                    registrarLog(e.getMessage());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
            ejecutarDescarga(AccionTipoComunicacion.TransferirComprimido, servicio);
            //this.informarResultadoDescargaExitoso();
        }
    }


    private void enviarZipAServidor(String ruta, AccionTipoComunicacion accionTipoComunicacion,
                                    int indiceArchivo, String servicio) throws Exception {
        PeticionDescarga peticionDescarga = this.crearPeticionDescarga(ruta, servicio);
        if (peticionDescarga != null) {
            new ServicioTalleres(new SuscriptorServicio(accionTipoComunicacion, indiceArchivo),
                    this.contexto, talleres.getRutaServidor()).solicitudDescarga(peticionDescarga);
        } else {
            this.procesarArchivoAdjunto(indiceArchivo + 1, servicio);
        }
    }

    private void sesionMensajeLog(AccionTipoComunicacion accionTipoComunicacion,
                                  String servicio, String idProceso,
                                  String mensaje, String estado) throws Exception {
        PeticionMensajeLog peticionMensajeLog = this.crearPeticionMensajeLog(servicio, idProceso, mensaje, estado);
        if (peticionMensajeLog != null) {
            new ServicioTalleres(new SuscriptorServicio(accionTipoComunicacion),
                    this.contexto, talleres.getRutaServidor()).solicitudMensajeLog(peticionMensajeLog);
        }
    }

    private void enviarZipAServidor(String ruta, AccionTipoComunicacion accionTipoComunicacion,
                                    String servicio) throws Exception {
        PeticionDescarga peticionDescarga = this.crearPeticionDescarga(ruta, servicio);
        if (peticionDescarga != null) {
            new ServicioTalleres(new SuscriptorServicio(accionTipoComunicacion), contexto,
                    talleres.getRutaServidor()).solicitudDescarga(peticionDescarga);
        } else {
            String mensaje = "No se obtuvo la petición para descarga.";
            estadoComunicacion.informarError(new Exception(mensaje));
        }
    }

    private PeticionDescarga crearPeticionDescarga(String ruta, String servicio) throws Exception {
        PeticionDescarga peticionDescarga;
        try {
            byte[] bytesArchivo = controladorArchivosAdjuntos.traerBytes(new File(ruta));

            String encodedString = Base64.encodeToString(bytesArchivo, Base64.DEFAULT);
            peticionDescarga = new PeticionDescarga();
            peticionDescarga.CodPrograma = CODIGO_PROGRAMA;
            peticionDescarga.CodTerminal = numeroTerminal;
            peticionDescarga.IdServicio = servicio;
            peticionDescarga.Archivo = encodedString;
            peticionDescarga.Longitud = String.valueOf(bytesArchivo.length);
            peticionDescarga.Sesion = SESION_DESCARGA;

        } catch (FileNotFoundException e) {
            Log.error(e, "Comunicaciones Descarga Adjuntos");
            registrarLog("Error Comunicaciones Descarga Adjuntos " + e.getMessage());
            throw e;
        }
        return peticionDescarga;
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
        peticionMensajeLog.Sesion = SESION_DESCARGA;
        peticionMensajeLog.Estado = estado;
        return peticionMensajeLog;
    }

    private void registrarLog(String mensaje) throws IOException {
        StringHelper.registrarLog(mensaje);
    }

    public void continuarDescargaAdjuntos(int indiceAdjuntoActual, String servicio) {
        if (servicio == SERVICIO_DESCARGA)
            this.procesarArchivoAdjunto(indiceAdjuntoActual + 1, SERVICIO_DESCARGA);
        else if (servicio == SERVICIO_ENVIO_ADJUNTOS_WEB)
            this.procesarArchivoAdjunto(indiceAdjuntoActual + 1, SERVICIO_ENVIO_ADJUNTOS_WEB);
    }

    final class SuscriptorServicio implements Suscriptor {
        int pasoEjecucion;
        AccionTipoComunicacion accionTipoComunicacion;

        SuscriptorServicio(AccionTipoComunicacion accionTipoComunicacion, int pasoEjecucion) {
            this.pasoEjecucion = pasoEjecucion;
            this.accionTipoComunicacion = accionTipoComunicacion;
        }

        SuscriptorServicio(AccionTipoComunicacion accionTipoComunicacion) {
            this.accionTipoComunicacion = accionTipoComunicacion;
        }

        @Override
        public void onError(Throwable e) throws IOException {
            if (e instanceof HttpException) {
                String mensaje;
                HttpException httpException = (HttpException) e;
                if (httpException.code() >= 400 && httpException.code() < 500) {
                    mensaje = "No se obtuvo el recurso. " + httpException.message() + " (" + String.valueOf(httpException.code()) + ")";
                } else if (httpException.code() >= 500 && httpException.code() < 600) {
                    mensaje = "Error interno del servicio. " + httpException.message() + " (" + String.valueOf(httpException.code()) + ")";
                } else {
                    mensaje = httpException.message() + " (" + String.valueOf(httpException.code()) + ")";
                }
                estadoComunicacion.informarError(
                        new Exception(mensaje));
            } else {
                estadoComunicacion.informarError(
                        new Exception(e));
            }
        }

        @Override
        public void onCompletetado() {

        }

        @Override
        public <T> void onResultado(T datos, String codigoServicio) {
            RespuestaDescarga respuestaDescarga = (RespuestaDescarga) datos;

            if (respuestaDescarga.Resultado) {
                try {
                    switch (codigoServicio) {
                        case SERVICIO_DESCARGA:
                            switch (this.accionTipoComunicacion) {
                                case TransferirAdjunto:
                                    //sesionMensajeLog(this.accionTipoComunicacion, codigoServicio, Constantes.PROCESO_FIN_SESION, Constantes.MENSAJE_FIN_SESION, Constantes.ESTADO_OK);
                                    FileManager.deleteFolderContent(new File(Constantes.traerRutaDescargaAdjuntos()).getAbsolutePath());
                                    FileManager.eliminarCarpetaRecursivo(new File(Constantes.traerRutaSirius() + File.separator + Constantes.NOMBRE_ARCHIVO_ZIP_DIAGRAMS));
                                    procesarArchivoAdjunto(this.pasoEjecucion + 1, codigoServicio);
                                    break;
                                case TransferirComprimido:
                                    descarga.firmarDescarga(ordenTrabajoBusqueda);
                                    sesionMensajeLog(this.accionTipoComunicacion, codigoServicio,
                                            Constantes.PROCESO_FIN_SESION, Constantes.MENSAJE_FIN_SESION, Constantes.ESTADO_OK);
                                    descarga.actualizarEstadoComunicacionOTBD();
                                    informarResultadoDescargaExitoso();

                                    break;
                            }
                            break;
                        case SERVICIO_ENVIO_WEB:
                            switch (this.accionTipoComunicacion) {
                                case TransferirComprimido:
                                    sesionMensajeLog(this.accionTipoComunicacion, codigoServicio,
                                            Constantes.PROCESO_FIN_SESION, Constantes.MENSAJE_FIN_SESION, Constantes.ESTADO_OK);
                                    informarResultadoEnvioExitoso();
                                    break;
                            }
                            break;
                        case SERVICIO_ENVIO_ADJUNTOS_WEB:
                            switch (this.accionTipoComunicacion) {
                                case TransferirAdjunto:
                                    sesionMensajeLog(this.accionTipoComunicacion, codigoServicio, Constantes.PROCESO_FIN_SESION, Constantes.MENSAJE_FIN_SESION, Constantes.ESTADO_OK);
                                    FileManager.deleteFolderContent(new File(Constantes.traerRutaDescargaAdjuntos()).getAbsolutePath());
                                    FileManager.eliminarCarpetaRecursivo(new File(Constantes.traerRutaSirius() + File.separator + Constantes.NOMBRE_ARCHIVO_ZIP_DIAGRAMS));
                                    procesarArchivoAdjunto(this.pasoEjecucion + 1, codigoServicio);
                                    break;
                            }
                            break;
                    }
                } catch (Exception ex) {
                    FileManager.deleteFolderContent(new File(Constantes.traerRutaEnvioWeb()).getAbsolutePath());
                    Log.error(ex, respuestaDescarga.Mensaje);
                    try {
//                        sesionMensajeLog(this.accionTipoComunicacion, codigoServicio,
//                                "", ex.getMessage() + respuestaDescarga.Mensaje, Constantes.ESTADO_ERROR);

//                        sesionMensajeLog(this.accionTipoComunicacion, codigoServicio,
//                                Constantes.PROCESO_FIN_SESION, Constantes.MENSAJE_FIN_SESION, Constantes.ESTADO_ERROR);
                    } catch (Exception e) {
                        try {
                            registrarLog(e.getMessage());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                    }

                    try {
                        estadoComunicacion.informarError(ex);
                    } catch (IOException e) {
                        try {
                            registrarLog(e.getMessage());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                }
            } else {
                try {

//                    sesionMensajeLog(this.accionTipoComunicacion, codigoServicio,
//                            "", respuestaDescarga.Mensaje, Constantes.ESTADO_ERROR);
//
//                    sesionMensajeLog(this.accionTipoComunicacion, codigoServicio,
//                            Constantes.PROCESO_FIN_SESION, Constantes.MENSAJE_FIN_SESION, Constantes.ESTADO_ERROR);
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        registrarLog(e.getMessage());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                try {
                    estadoComunicacion.informarError(new Exception(respuestaDescarga.Mensaje));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public <T> void onResultadoAdjuntos(T datos) {

        }


    }
}

