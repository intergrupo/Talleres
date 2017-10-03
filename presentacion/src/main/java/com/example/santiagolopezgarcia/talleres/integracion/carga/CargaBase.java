package com.example.santiagolopezgarcia.talleres.integracion.carga;

import com.example.dominio.bussinesslogic.administracion.TalleresBL;
import com.example.dominio.modelonegocio.Mensaje;
import com.example.dominio.modelonegocio.Talleres;
import com.example.santiagolopezgarcia.talleres.helpers.Constantes;
import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.example.santiagolopezgarcia.talleres.services.dto.RespuestaCarga;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.Carga;
import com.example.utilidades.FileManager;
import com.example.utilidades.Log;
import com.example.utilidades.ZipManager;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */
public abstract class CargaBase {

    protected TalleresBL talleresBL;

    protected IEstadoComunicacionCarga estadoComunicacionCarga;

    protected Map<Integer, String> listaArchivosOrdenados;

    protected SolicitudRemplazoMaestros solicitudRemplazoMaestros;

    public void configurar(IEstadoComunicacionCarga estadoComunicacion) {
        this.estadoComunicacionCarga = estadoComunicacion;
    }


    public CargaBase(TalleresBL talleresBL) {
        this.talleresBL = talleresBL;
    }

    protected <T> T leerXml(File archivo, Class<T> tipoClase) throws Exception {
        T lista;
        try {
            Serializer serializer = new Persister();
            lista = serializer.read(tipoClase, archivo);
            return lista;
        } catch (Exception exc) {
            Log.error(exc, "Error leyendo xml " + archivo.getName());
            throw exc;
        }
    }


    protected void establecerOrdenArchivos(String ruta) {
        File rutaInicial = new File(ruta);
        File[] listaArchivos = rutaInicial.listFiles();
        if (listaArchivos != null) {
            Map listaNombreArchivos = new HashMap();
            for (File archivo : listaArchivos) {
                if (!archivo.isDirectory()) {
                    int posicion = calcularOrdenEntidad(archivo.getName());
                    if (posicion >= 0) {
                        listaNombreArchivos.put(posicion, archivo.getAbsolutePath());
                    }
                } else {
                    listaNombreArchivos.putAll(leerMaestrosRecursivoXML(archivo.getAbsolutePath()));
                }
            }
            listaArchivosOrdenados = new TreeMap<>(listaNombreArchivos);
        }
    }

    protected Map<Integer, String> leerMaestrosRecursivoXML(String ruta) {
        File rutaInicial = new File(ruta);
        File[] listaArchivos = rutaInicial.listFiles();
        Map<Integer, String> listaNombreArchivos = new HashMap();
        for (File archivo : listaArchivos) {
            if (!archivo.isDirectory()) {
                int posicion = calcularOrdenEntidad(archivo.getName());
                if (posicion >= 0) {
                    listaNombreArchivos.put(posicion, archivo.getAbsolutePath());
                }
            } else {
                listaNombreArchivos.putAll(leerMaestrosRecursivoXML(archivo.getAbsolutePath()));
            }
        }
        Map<Integer, String> listaArchivosOrdenados = new TreeMap<>(listaNombreArchivos);
        return listaArchivosOrdenados;
    }

    protected abstract int calcularOrdenEntidad(String nombreArchivo);

    protected abstract Carga obtenerDtoCarga() throws Exception;

    public void postEjecucionServicioCarga(RespuestaCarga respuestaCarga, String nombreCarpeta
            , String numeroTerminal, String codigoPrograma) throws Exception {
        String rutaArchivo = Constantes.traerRutaCarga() + nombreCarpeta + File.separator;
        FileManager.deleteFolderContent(rutaArchivo);
        this.guardarArchivoEnAlmacenamiento(respuestaCarga, nombreCarpeta, numeroTerminal, codigoPrograma);
        this.establecerOrdenArchivos(rutaArchivo);
    }

    public <T extends BaseListaDto> T obtenerListaDto(Class<T> tipo, String nombreArchivo, boolean esObligatorio) throws Exception {
        int posicionArchivoCorreria = this.calcularOrdenEntidad(nombreArchivo);
        T listaDto = null;
        if (listaArchivosOrdenados != null && listaArchivosOrdenados.containsKey(posicionArchivoCorreria)) {
            String nombreArchivoObtenido = listaArchivosOrdenados.get(posicionArchivoCorreria);
            File archivo = new File(nombreArchivoObtenido);
            listaDto = leerXml(archivo, tipo);
            if (esObligatorio == true && (listaDto == null || listaDto.getLongitudLista() == 0)) {
                throw new Exception("No se cargaron datos del archivo " + nombreArchivo);
            }
        } else if (esObligatorio == true) {
            throw new Exception("No se cargó el archivo " + nombreArchivo);
        }
        return listaDto;
    }

    public boolean validarExistenciaMaestros(Talleres talleres) {
        boolean resultado = false;
        if (talleres != null) {
            if (talleres.getVersionMaestros() != null && !talleres.getVersionMaestros().equals("")) {
                resultado = true;
            }
        }
        return resultado;
    }

    public boolean validarExistenciaMaestros() {
        Talleres talleres = this.talleresBL.cargarPrimerRegistro();
        return this.validarExistenciaMaestros(talleres);
    }

    public boolean validarVersionMaestros() throws Exception {
        boolean resultado = false;
        Talleres talleres = this.talleresBL.cargarPrimerRegistro();
        if (this.validarExistenciaMaestros(talleres)) {
            Carga cargaDto = this.obtenerDtoCarga();
            if (cargaDto != null) {
                int versionMestrosSistema = 0;
                if (cargaDto.VersionMaestros != null && !cargaDto.VersionMaestros.isEmpty()) {
                    versionMestrosSistema = Integer.parseInt(cargaDto.VersionMaestros.replace(".", ""));
                }
                int versionMaestrosTerminal = Integer.parseInt(talleres.getVersionMaestros().replace(".", ""));
                if (versionMaestrosTerminal == versionMestrosSistema) {
                    resultado = true;
                } else if (versionMestrosSistema > versionMaestrosTerminal) {
                    this.crearObjetoSolicitudRemplazoMaestros(cargaDto.VersionMaestros, talleres.getVersionMaestros());
                } else {
                    String mensaje = "Versiones de maestros no corresponden.\nSistema: " + cargaDto.VersionMaestros
                            + "\nTerminal: " + talleres.getVersionMaestros();
                    throw new Exception(mensaje);
                }
            } else {
                resultado = true;
            }
        }
        return resultado;
    }

    public Mensaje validarVersionApp(String versionActual) {
        Mensaje mensajeResultado = new Mensaje();
        Carga cargaDto;
        try {
            cargaDto = this.obtenerDtoCarga();
            if (cargaDto != null) {
                int tamanoActual = versionActual.length();
                int tamanoCarga = cargaDto.VersionSoftware.length();
                if (tamanoActual > tamanoCarga) {
                    if (versionActual.substring(0, tamanoCarga).equals(cargaDto.VersionSoftware)) {
                        mensajeResultado.setRespuesta(true);
                    } else {
                        versionNoCompatible(versionActual, mensajeResultado, cargaDto);
                    }
                } else if (tamanoActual < tamanoCarga) {
                    if (cargaDto.VersionSoftware.substring(0, tamanoActual).equals(versionActual)) {
                        mensajeResultado.setRespuesta(true);
                    } else {
                        versionNoCompatible(versionActual, mensajeResultado, cargaDto);
                    }
                } else {
                    if (cargaDto.VersionSoftware.equals(versionActual)) {
                        mensajeResultado.setRespuesta(true);
                    } else {
                        versionNoCompatible(versionActual, mensajeResultado, cargaDto);
                    }
                }
            } else {
                mensajeResultado.setRespuesta(true);
            }
        } catch (Exception e) {
            mensajeResultado.setRespuesta(false);
            mensajeResultado.setMensaje(e.getMessage());
        }
        return mensajeResultado;
    }

    private void versionNoCompatible(String versionActual, Mensaje mensajeResultado, Carga cargaDto) {
        mensajeResultado.setRespuesta(false);
        mensajeResultado.setMensaje("La versión del software no corresponde al de los datos de carga.\nSistema: " + cargaDto.VersionSoftware + "\nTerminal: " + versionActual);
    }


    protected void guardarArchivoEnAlmacenamiento(RespuestaCarga respuestaCarga, String nombreCarpeta
            , String numeroTerminal, String codigoPrograma) throws Exception {
        if (respuestaCarga.Binario != null) {

            byte[] archivoZIP = FileManager.convertStringToByteArray(respuestaCarga.Binario);
            String ruta = Constantes.traerRutaCarga() + nombreCarpeta + File.separator;
            new File(ruta).mkdirs();
            FileManager.createFile(archivoZIP, Constantes.traerRutaCarga() + "cargar.zip");

            try {
                ZipManager.unZip(Constantes.traerRutaCarga() + "cargar.zip", ruta, String.format("%s%s", numeroTerminal, codigoPrograma));
            } catch (IOException e) {
                throw e;
            }
        } else {
            throw new Exception(respuestaCarga.MensajeDeError != null ? respuestaCarga.MensajeDeError :
                    "No se obtuvo ningún archivo desde el servidor.  Verifique si tiene correrías programadas.");
        }
    }

    protected void crearObjetoSolicitudRemplazoMaestros(String versionSistema, String versionTerminal) {
        this.solicitudRemplazoMaestros = new SolicitudRemplazoMaestros();
        this.solicitudRemplazoMaestros.setVersionSistema(versionSistema);
        this.solicitudRemplazoMaestros.setVersionTerminal(versionTerminal);
    }

    public boolean esNecesarioConfirmarRemplazoMaestros() {
        boolean esNecesario = false;
        if (this.solicitudRemplazoMaestros != null && this.solicitudRemplazoMaestros.solicitarRemplazoMaestros()) {
            esNecesario = true;
            this.estadoComunicacionCarga.confirmarActualizarMaestros(this.solicitudRemplazoMaestros.getMensaje());
        }
        return esNecesario;
    }

    public void postEjecucionCarga(String rutaCarpeta, String nombreArchivo, String codigoPrograma) throws Exception {
        ZipManager.unZip(rutaCarpeta + nombreArchivo, rutaCarpeta, codigoPrograma);
        this.establecerOrdenArchivos(rutaCarpeta);
        //FileManager.deleteFolderContent(rutaCarpeta + nombreArchivo); TODO:REVISAR SI ES NECESARIO BORRAR LOS ARCHIVOS
    }
}
