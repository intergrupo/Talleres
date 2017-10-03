package com.example.santiagolopezgarcia.talleres.integracion.descarga;

import com.example.dominio.IBaseDescarga;
import com.example.dominio.bussinesslogic.administracion.TalleresBL;
import com.example.dominio.bussinesslogic.correria.CorreriaBL;
import com.example.dominio.bussinesslogic.correria.ProgramacionCorreriaBL;
import com.example.dominio.modelonegocio.ArchivoAdjunto;
import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;
import com.example.dominio.modelonegocio.Talleres;
import com.example.dominio.bussinesslogic.notificacion.ReporteNotificacionBL;
import com.example.dominio.bussinesslogic.ordentrabajo.OrdenTrabajoBL;
import com.example.dominio.bussinesslogic.tarea.TareaXOrdenTrabajoBL;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.helpers.Constantes;
import com.example.santiagolopezgarcia.talleres.integracion.IEstadoComunicacion;
import com.example.santiagolopezgarcia.talleres.services.dto.descarga.administracion.ListaCarga;
import com.example.santiagolopezgarcia.talleres.services.dto.descarga.correria.ListaCorrerias;
import com.example.santiagolopezgarcia.talleres.services.dto.descarga.ordentrabajo.ListaOrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.services.dto.descarga.ordentrabajo.ListaTareaXOrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.services.dto.interfaces.BaseListaDtoConfirmacion;
import com.example.utilidades.FileManager;
import com.example.utilidades.Log;
import com.example.utilidades.helpers.DateHelper;
import com.example.utilidades.helpers.StringHelper;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class Descarga {

    ReporteNotificacionBL reporteNotificacionBL;
    CorreriaBL correriaBL;
    TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL;
    DependenciaDescarga dependenciaDescarga;
    IEstadoComunicacion estadoComunicacion;
    OrdenTrabajoBL ordenTrabajoBL;
    String codigoCorreria;
    public List<String> archivosGenerados;
    TalleresBL talleresBL;
    private String sesion;
    private List<String> codigosCorreriasIntegradas;
    private List<String> codigosOTsIntegradas;
    private String versionSoftware;
    private SiriusApp app;

    @Inject
    public Descarga(DependenciaDescarga dependenciaDescarga
            , ReporteNotificacionBL reporteNotificacionBL
            , CorreriaBL correriaBL
            , TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL
            , TalleresBL talleresBL
            , OrdenTrabajoBL ordenTrabajoBL) {
        this.dependenciaDescarga = dependenciaDescarga;
        this.reporteNotificacionBL = reporteNotificacionBL;
        this.correriaBL = correriaBL;
        this.tareaXOrdenTrabajoBL = tareaXOrdenTrabajoBL;
        this.talleresBL = talleresBL;
        this.ordenTrabajoBL = ordenTrabajoBL;
    }

    public void setApp(SiriusApp app) {
        this.app = app;
    }

    public void setCodigoCorreria(String codigoCorreria) {
        this.codigoCorreria = codigoCorreria;
    }

    public void setVersionSoftware(String versionSoftware) {
        this.versionSoftware = versionSoftware;
    }

    public void setCodigosOTsIntegradas(List<String> codigosOTsIntegradas) {
        this.codigosOTsIntegradas = codigosOTsIntegradas;
    }

    public void setCodigosCorreriasIntegradas(List<String> codigosCorreriasIntegradas) {
        this.codigosCorreriasIntegradas = codigosCorreriasIntegradas;
    }

    public void setSesion(String sesion) {
        this.sesion = sesion;
    }

    public void configurar(IEstadoComunicacion estadoComunicacion) {
        this.estadoComunicacion = estadoComunicacion;
    }

    public String generarArchivosXmlDto(String codigoCorreria, String ruta) throws Exception {
        this.codigoCorreria = codigoCorreria;
        FileManager.deleteFolderContent(ruta);
        this.generarXmlDto(codigoCorreria, ruta);
        return ruta;
    }

    public String generarArchivosXmlDtoConfirmacion(String ruta) throws Exception {
        FileManager.deleteFolderContent(ruta);
        this.generarXmlDtoConfirmacion(ruta);
        return ruta;
    }

    public String generarArchivosXmlDtoEnvio(String codigoCorreria, String ruta, Talleres talleres) throws Exception {
        this.codigoCorreria = codigoCorreria;
        FileManager.deleteFolderContent(ruta);
        this.generarXmlDtoEnvio(codigoCorreria, ruta, talleres);
        return ruta;
    }

    public String generarArchivosXmlDto(OrdenTrabajoBusqueda ordenTrabajoBusqueda) throws Exception {
        this.codigoCorreria = ordenTrabajoBusqueda.getCodigoCorreria();
        String ruta = Constantes.traerRutaDescarga() + Constantes.NOMBRE_CARPETA_DESCARGA_PARCIAL + File.separator;
        FileManager.deleteFolderContent(ruta);
        this.generarXmlDto(ordenTrabajoBusqueda, ruta);
        return ruta;

    }

    public String generarArchivosXmlDtoEnvio(OrdenTrabajoBusqueda ordenTrabajoBusqueda, String ruta,
                                             Talleres talleres) throws Exception {
        this.codigoCorreria = ordenTrabajoBusqueda.getCodigoCorreria();
        FileManager.deleteFolderContent(ruta);
        this.generarXmlDtoEnvio(ordenTrabajoBusqueda, ruta, talleres);
        return ruta;
    }


    public void firmarDescarga(OrdenTrabajoBusqueda ordenTrabajoBusqueda) throws IOException {
        try {
            String fechaDescarga = DateHelper.convertirDateAString(new Date(), DateHelper.TipoFormato.yyyyMMddTHHmmss);
            this.correriaBL.actualizarFechaDescarga(this.codigoCorreria, fechaDescarga);
            this.tareaXOrdenTrabajoBL.actualizarFechaDescarga(this.codigoCorreria, fechaDescarga, ordenTrabajoBusqueda.getListaOrdenTrabajo());
        } catch (ParseException e) {
            Log.error(e, "Comunicaciones Descarga Firma");
            try {
                registrarLog("No se pudo firmar la descarga");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            this.estadoComunicacion.informarProgreso("No se pudo firmar la descarga");

        } catch (Exception e) {
            Log.error(e, "Comunicaciones Descarga Firma");
            try {
                registrarLog("No se pudo firmar la descarga");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            this.estadoComunicacion.informarProgreso("No se pudo firmar la descarga");
        }
    }

    public List<ArchivoAdjunto> obtenerArchivosAdjuntos() {
        return reporteNotificacionBL.cargarArchivosXCodigoCorreria(codigoCorreria);
    }

    public List<ArchivoAdjunto> obtenerArchivosAdjuntosFiltrado(OrdenTrabajoBusqueda filtro) {
        return reporteNotificacionBL.cargarArchivosXFiltro(filtro, app.getUsuario());
    }

    private void generarXmlDto(String codigoCorreria, String ruta) throws Exception {
        this.archivosGenerados = new ArrayList<>();
        String ultimoNombreEntidad = "";
        try {
            List<String> nombresEntidades = this.dependenciaDescarga.obtenerListaNombreEntidades();

            for (String nombreEntidad : nombresEntidades) {
                ultimoNombreEntidad = nombreEntidad;
                IBaseDescarga baseDescarga = this.dependenciaDescarga.obtenerObjetoNegocio(nombreEntidad);
                List listaNegocio = baseDescarga.cargarXCorreria(codigoCorreria);
                convertirDatosAdto(listaNegocio, nombreEntidad, ruta, false, false);
            }
        } catch (Exception exc) {
            Log.error(exc, " Error generando xml de de Dto " + ultimoNombreEntidad);
            registrarLog(" Error generando xml de de Dto " + ultimoNombreEntidad);
            throw exc;
        }
    }

    private void generarXmlDtoEnvio(String codigoCorreria, String ruta, Talleres talleres) throws Exception {
        this.archivosGenerados = new ArrayList<>();
        String ultimoNombreEntidad = "";
        try {
            List<String> nombresEntidades = this.dependenciaDescarga.obtenerListaNombreEntidadesEnvio();

            for (String nombreEntidad : nombresEntidades) {
                ultimoNombreEntidad = nombreEntidad;
                List listaNegocio = new ArrayList<>();
                if (nombreEntidad.equals(DependenciaDescarga.SIRIUS)) {
                    listaNegocio.add(talleres);
                } else {
                    IBaseDescarga baseDescarga = this.dependenciaDescarga.obtenerObjetoNegocio(nombreEntidad);
                    if (nombreEntidad.equals(DependenciaDescarga.REPORTE_NOTIFICACION))
                        listaNegocio = ((ReporteNotificacionBL) baseDescarga).cargarXCorreriaCompleto(codigoCorreria);
                    else
                        listaNegocio = baseDescarga.cargarXCorreria(codigoCorreria);
                }
                convertirDatosAdto(listaNegocio, nombreEntidad, ruta, true, false);
            }
        } catch (Exception exc) {
            Log.error(exc, " Error generando xml de de Dto " + ultimoNombreEntidad);
            registrarLog(" Error generando xml de de Dto " + ultimoNombreEntidad);
            throw exc;
        }
    }

    private void generarXmlDtoConfirmacion(String ruta) throws Exception {
        this.archivosGenerados = new ArrayList<>();
        String ultimoNombreEntidad = "";
        try {
            Talleres talleres = talleresBL.cargarPrimerRegistro();
            List<String> nombresEntidades = this.dependenciaDescarga.
                    obtenerListaNombreEntidadesConfirmacion(talleres.getConfirmacion());

            for (String nombreEntidad : nombresEntidades) {
                ultimoNombreEntidad = nombreEntidad;
                IBaseDescarga baseDescarga = this.dependenciaDescarga.obtenerObjetoNegocio(nombreEntidad);
                List listaNegocio;
                if (nombreEntidad.equals(DependenciaDescarga.CONFIRMACION_OT) && codigosOTsIntegradas.size() > 0) {
                    listaNegocio = ((OrdenTrabajoBL) baseDescarga).cargar(codigosOTsIntegradas);
                } else if (nombreEntidad.equals(DependenciaDescarga.CONFIRMACIONCORRERIA)) {
                    listaNegocio = ((ProgramacionCorreriaBL) baseDescarga).cargar(codigosCorreriasIntegradas);
                } else {
                    listaNegocio = baseDescarga.cargarXCorreria(codigoCorreria);
                }

                convertirDatosAdtoConfirmacion(listaNegocio, nombreEntidad, ruta);
            }
        } catch (Exception exc) {
            Log.error(exc, " Error generando xml de de Dto " + ultimoNombreEntidad);
            registrarLog(" Error generando xml de de Dto " + ultimoNombreEntidad);
            throw exc;
        }
    }

    private void convertirDatosAdtoConfirmacion(List listaNegocio, String nombreEntidad, String ruta) {
        if (listaNegocio != null && listaNegocio.size() > 0) {
            String nombreCarpeta = this.dependenciaDescarga.obtenerNumeroCarpetaConfirmacion(nombreEntidad);
            BaseListaDtoConfirmacion listaDtoDescarga = this.dependenciaDescarga.obtenerTipoDtoConfirmacion(nombreEntidad);
            try {
                listaDtoDescarga.convertirListaDominioAListaDto(listaNegocio, talleresBL.cargarPrimerRegistro().getNumeroTerminal(),
                        DateHelper.convertirDateAString(new Date(), DateHelper.TipoFormato.yyyyMMddTHHmmss), sesion);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String rutaCarpeta = ruta + nombreCarpeta;
            new File(rutaCarpeta).mkdirs();
            File file = new File(rutaCarpeta, nombreEntidad);
            Serializer serializer = new Persister();
            try {
                serializer.write(listaDtoDescarga, file);
            } catch (Exception exc) {
            }

            this.archivosGenerados.add(rutaCarpeta);
        }
    }

    private void generarXmlDtoEnvio(OrdenTrabajoBusqueda ordenTrabajoBusqueda, String ruta, Talleres talleres) throws Exception {
        this.archivosGenerados = new ArrayList<>();
        String ultimoNombreEntidad = "";
        try {
            List<String> nombresEntidades = this.dependenciaDescarga.obtenerListaNombreEntidadesEnvio();

            for (String nombreEntidad : nombresEntidades) {
                ultimoNombreEntidad = nombreEntidad;
                List listaNegocio = new ArrayList<>();
                if (nombreEntidad.equals(DependenciaDescarga.SIRIUS)) {
                    listaNegocio.add(talleres);
                } else {
                    IBaseDescarga baseDescarga = this.dependenciaDescarga.obtenerObjetoNegocio(nombreEntidad);
                    if (nombreEntidad.equals(DependenciaDescarga.REPORTE_NOTIFICACION))
                        listaNegocio = ((ReporteNotificacionBL) baseDescarga).cargarXFiltroCompleto(ordenTrabajoBusqueda, app.getUsuario());
                    else
                        listaNegocio = baseDescarga.cargarXFiltro(ordenTrabajoBusqueda);

                }
                convertirDatosAdto(listaNegocio, nombreEntidad, ruta, true, true);
            }
        } catch (Exception exc) {
            Log.error(exc, " Error generando xml de de Dto " + ultimoNombreEntidad);
            registrarLog(" Error generando xml de de Dto " + ultimoNombreEntidad);
            throw exc;
        }
    }

    private void generarXmlDto(OrdenTrabajoBusqueda ordenTrabajoBusqueda, String ruta) throws Exception {
        this.archivosGenerados = new ArrayList<>();
        String ultimoNombreEntidad = "";
        try {
            List<String> nombresEntidades = this.dependenciaDescarga.obtenerListaNombreEntidades(false);
            for (String nombreEntidad : nombresEntidades) {
                ultimoNombreEntidad = nombreEntidad;
                IBaseDescarga baseDescarga = this.dependenciaDescarga.obtenerObjetoNegocio(nombreEntidad);
                List listaNegocio;
                if (nombreEntidad.equals(DependenciaDescarga.REPORTE_NOTIFICACION))
                    listaNegocio = ((ReporteNotificacionBL) baseDescarga).cargarXFiltroCompleto(ordenTrabajoBusqueda, app.getUsuario());
                else
                    listaNegocio = baseDescarga.cargarXFiltro(ordenTrabajoBusqueda);
                convertirDatosAdto(listaNegocio, nombreEntidad, ruta, false, true);
            }
        } catch (Exception exc) {
            Log.error(exc, " Error generando xml de de Dto " + ultimoNombreEntidad);
            registrarLog(" Error generando xml de de Dto " + ultimoNombreEntidad);
            throw exc;
        }
    }

    private void convertirDatosAdto(List listaNegocio, String nombreEntidad,
                                    String ruta, boolean envio, boolean parcial) throws Exception {
        if (listaNegocio != null && listaNegocio.size() > 0) {
            String nombreCarpeta = this.dependenciaDescarga.obtenerNumeroCarpeta(nombreEntidad);
            BaseListaDtoDescarga listaDtoDescarga = this.dependenciaDescarga.obtenerTipoDto(nombreEntidad);

            casosEspeciales(envio, parcial, listaDtoDescarga);

            listaDtoDescarga.convertirListaDominioAListaDto(listaNegocio);
            String rutaCarpeta = ruta + nombreCarpeta;
            new File(rutaCarpeta).mkdirs();
            File file = new File(rutaCarpeta, nombreEntidad);
            Serializer serializer = new Persister();
            try {
                serializer.write(listaDtoDescarga, file);
            } catch (Exception exc) {
                throw exc;
            }

            this.archivosGenerados.add(rutaCarpeta);
        }
    }

    private void casosEspeciales(boolean envio, boolean parcial, BaseListaDtoDescarga listaDtoDescarga) throws ParseException {

        if (listaDtoDescarga instanceof ListaTareaXOrdenTrabajo) {
            ((ListaTareaXOrdenTrabajo) listaDtoDescarga).setEnvio(envio);
        }

        if (listaDtoDescarga instanceof ListaOrdenTrabajo) {
            ((ListaOrdenTrabajo) listaDtoDescarga).setSesion(parcial ? "P_" + sesion : "T_" + sesion);
            ((ListaOrdenTrabajo) listaDtoDescarga).setNumeroTerminal(talleresBL.cargarPrimerRegistro().getNumeroTerminal());
            ((ListaOrdenTrabajo) listaDtoDescarga).setFecha(DateHelper.convertirDateAString(new Date(), DateHelper.TipoFormato.yyyyMMddTHHmmss));

        }

        if (listaDtoDescarga instanceof ListaCorrerias) {
            ((ListaCorrerias) listaDtoDescarga).setSesion(parcial ? "P_" + sesion : "T_" + sesion);
            ((ListaCorrerias) listaDtoDescarga).setNumeroTerminal(talleresBL.cargarPrimerRegistro().getNumeroTerminal());
            ((ListaCorrerias) listaDtoDescarga).setFecha(DateHelper.convertirDateAString(new Date(), DateHelper.TipoFormato.yyyyMMddTHHmmss));

        }

        if (listaDtoDescarga instanceof ListaCarga) {
            ((ListaCarga) listaDtoDescarga).setVersionSoftware(versionSoftware);
        }

    }

    public String[] getArchivosGenerados() {
        String[] arregloRutaArchivos = null;
        if (this.archivosGenerados != null && this.archivosGenerados.size() > 0) {
            arregloRutaArchivos = new String[this.archivosGenerados.size()];

            for (int i = 0; i < this.archivosGenerados.size(); i++) {
                arregloRutaArchivos[i] = this.archivosGenerados.get(i);
            }
        }
        return arregloRutaArchivos;
    }

    private void registrarLog(String mensaje) throws IOException {
        StringHelper.registrarLog(mensaje);
    }

    public void actualizarEstadoComunicacionOTBD() {
        com.example.dominio.modelonegocio.ListaOrdenTrabajo listaOrdenTrabajo = ordenTrabajoBL.cargarOrdenesTrabajo(codigoCorreria);
        for (OrdenTrabajo ordenTrabajo : listaOrdenTrabajo) {
            ordenTrabajo.setEstadoComunicacion("D");
            ordenTrabajoBL.actualizarOrdenTrabajo(ordenTrabajo);
        }
    }
}