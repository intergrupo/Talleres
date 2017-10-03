package com.example.santiagolopezgarcia.talleres.integracion.descarga;

import com.example.dominio.IBaseDescarga;
import com.example.dominio.bussinesslogic.administracion.TalleresBL;
import com.example.dominio.bussinesslogic.correria.CorreriaBL;
import com.example.dominio.bussinesslogic.correria.ProgramacionCorreriaBL;
import com.example.dominio.bussinesslogic.labor.LaborXOrdenTrabajoBL;
import com.example.dominio.modelonegocio.ParametrosConfirmacion;
import com.example.dominio.bussinesslogic.notificacion.ReporteNotificacionBL;
import com.example.dominio.bussinesslogic.ordentrabajo.OrdenTrabajoBL;
import com.example.dominio.bussinesslogic.tarea.TareaXOrdenTrabajoBL;
import com.example.dominio.bussinesslogic.trabajo.TrabajoXOrdenTrabajoBL;
import com.example.santiagolopezgarcia.talleres.services.dto.descarga.administracion.ListaCarga;
import com.example.santiagolopezgarcia.talleres.services.dto.descarga.administracion.ListaTalleres;
import com.example.santiagolopezgarcia.talleres.services.dto.descarga.confirmacion.ListaConfirmacionCorreria;
import com.example.santiagolopezgarcia.talleres.services.dto.descarga.confirmacion.ListaConfirmacionOrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.services.dto.descarga.correria.ListaCorrerias;
import com.example.santiagolopezgarcia.talleres.services.dto.descarga.correria.ListaProgramacionCorreria;
import com.example.santiagolopezgarcia.talleres.services.dto.descarga.labor.ListaLaborXOrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.services.dto.descarga.notificacion.ListaReporteNotificacion;
import com.example.santiagolopezgarcia.talleres.services.dto.descarga.ordentrabajo.ListaOrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.services.dto.descarga.ordentrabajo.ListaTareaXOrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.services.dto.descarga.trabajo.ListaTrabajoXOrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.services.dto.interfaces.BaseListaDtoConfirmacion;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class DependenciaDescarga {
    public static final String CORRERIA = "SIRIUS_CORRERIA.XML";
    public static final String LABOR_X_ORDENTRABAJO = "SIRIUS_LABORXORDENTRABAJO.XML";
    public static final String ORDEN_TRABAJO = "SIRIUS_ORDENTRABAJO.XML";
    public static final String TAREA_X_ORDEN_TRABAJO = "SIRIUS_TAREAXORDENTRABAJO.XML";
    public static final String REPORTE_NOTIFICACION = "SIRIUS_REPORTENOTIFICACION.XML";
    public static final String TRABAJO_X_ORDENTRABAJO = "SIRIUS_TRABAJOXORDENTRABAJ.XML";
    public static final String SIRIUS = "SIRIUS.XML";
    public static final String PROGRAMACIONCORRERIA = "SIRIUS_PROGRAMACIONCORRERI.XML";
    public static final String CONFIRMACION_OT = "SIRIUS_CONFIRMACIONOT.XML";
    public static final String CONFIRMACIONCORRERIA = "SIRIUS_CONFIRMACIONCORRERI.XML";
    public static final String CARGA = "CARGA.XML";

    private CorreriaBL correriaBL;
    private OrdenTrabajoBL ordenTrabajoBL;
    private TrabajoXOrdenTrabajoBL trabajoXOrdenTrabajoBL;
    private ReporteNotificacionBL reporteNotificacionBL;
    private LaborXOrdenTrabajoBL laborXOrdenTrabajoBL;
    private TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL;
    private ProgramacionCorreriaBL programacionCorreriaBL;
    private TalleresBL talleresBL;

    @Inject
    public DependenciaDescarga(CorreriaBL correriaBL, TrabajoXOrdenTrabajoBL trabajoXOrdenTrabajoBL,
                               OrdenTrabajoBL ordenTrabajoBL,
                               ReporteNotificacionBL reporteNotificacionBL,
                               LaborXOrdenTrabajoBL laborXOrdenTrabajoBL,
                               TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL,
                               ProgramacionCorreriaBL programacionCorreriaBL, TalleresBL talleresBL) {
        this.talleresBL = talleresBL;
        this.correriaBL = correriaBL;
        this.ordenTrabajoBL = ordenTrabajoBL;
        this.trabajoXOrdenTrabajoBL = trabajoXOrdenTrabajoBL;
        this.reporteNotificacionBL = reporteNotificacionBL;
        this.laborXOrdenTrabajoBL = laborXOrdenTrabajoBL;
        this.tareaXOrdenTrabajoBL = tareaXOrdenTrabajoBL;
        this.programacionCorreriaBL = programacionCorreriaBL;
    }

    public IBaseDescarga obtenerObjetoNegocio(String nombreEntidad) {
        IBaseDescarga entidad;
        switch (nombreEntidad.toUpperCase()) {
            case CORRERIA:
                entidad = this.correriaBL;
                break;
            case LABOR_X_ORDENTRABAJO:
                entidad = this.laborXOrdenTrabajoBL;
                break;
            case ORDEN_TRABAJO:
                entidad = this.ordenTrabajoBL;
                break;
            case TAREA_X_ORDEN_TRABAJO:
                entidad = this.tareaXOrdenTrabajoBL;
                break;
            case REPORTE_NOTIFICACION:
                entidad = this.reporteNotificacionBL;
                break;
            case TRABAJO_X_ORDENTRABAJO:
                entidad = this.trabajoXOrdenTrabajoBL;
                break;
            case PROGRAMACIONCORRERIA:
                entidad = this.programacionCorreriaBL;
                break;
            case CONFIRMACION_OT:
                entidad = this.ordenTrabajoBL;
                break;
            case CONFIRMACIONCORRERIA:
                entidad = this.programacionCorreriaBL;
                break;
            case CARGA:
                entidad = this.talleresBL;
                break;
            default:
                throw new IllegalArgumentException("No se pudo obtener el objeto de negocio para la entidad " + nombreEntidad);

        }
        return entidad;
    }

    public BaseListaDtoDescarga obtenerTipoDto(String nombreEntidad) {
        BaseListaDtoDescarga baseListaDtoDescarga = null;
        switch (nombreEntidad.toUpperCase()) {
            case CORRERIA:
                baseListaDtoDescarga = new ListaCorrerias();
                break;
            case LABOR_X_ORDENTRABAJO:
                baseListaDtoDescarga = new ListaLaborXOrdenTrabajo();
                break;
            case ORDEN_TRABAJO:
                baseListaDtoDescarga = new ListaOrdenTrabajo();
                break;
            case TAREA_X_ORDEN_TRABAJO:
                baseListaDtoDescarga = new ListaTareaXOrdenTrabajo();
                break;
            case REPORTE_NOTIFICACION:
                baseListaDtoDescarga = new ListaReporteNotificacion();
                break;
            case TRABAJO_X_ORDENTRABAJO:
                baseListaDtoDescarga = new ListaTrabajoXOrdenTrabajo();
                break;
            case SIRIUS:
                baseListaDtoDescarga = new ListaTalleres();
                break;
            case PROGRAMACIONCORRERIA:
                baseListaDtoDescarga = new ListaProgramacionCorreria();
                break;
            case CARGA:
                baseListaDtoDescarga = new ListaCarga();
                break;
            default:
                throw new IllegalArgumentException("No se pudo obtener el tipo de listado de objetos de transferencia de datos para la entidad " + nombreEntidad);

        }
        return baseListaDtoDescarga;
    }

    public BaseListaDtoConfirmacion obtenerTipoDtoConfirmacion(String nombreEntidad) {
        BaseListaDtoConfirmacion baseListaDtoDescarga;
        switch (nombreEntidad.toUpperCase()) {
            case CONFIRMACION_OT:
                baseListaDtoDescarga = new ListaConfirmacionOrdenTrabajo();
                break;
            case CONFIRMACIONCORRERIA:
                baseListaDtoDescarga = new ListaConfirmacionCorreria();
                break;
            default:
                throw new IllegalArgumentException("No se pudo obtener el tipo de listado de objetos de transferencia de datos para la entidad " + nombreEntidad);

        }
        return baseListaDtoDescarga;
    }

    public List<String> obtenerListaNombreEntidades() {
        return obtenerListaNombreEntidades(true);
    }

    public List<String> obtenerListaNombreEntidades(boolean descargaCompleta) {
        List<String> listaNombreEntidades = new ArrayList<>();

        if(descargaCompleta) {
            listaNombreEntidades.add(CORRERIA);
        }
        listaNombreEntidades.add(LABOR_X_ORDENTRABAJO);
        listaNombreEntidades.add(ORDEN_TRABAJO);
        listaNombreEntidades.add(TRABAJO_X_ORDENTRABAJO);
        listaNombreEntidades.add(TAREA_X_ORDEN_TRABAJO);
        listaNombreEntidades.add(REPORTE_NOTIFICACION);
        listaNombreEntidades.add(PROGRAMACIONCORRERIA);

        return listaNombreEntidades;
    }

    public List<String> obtenerListaNombreEntidadesEnvio() {
        List<String> listaNombreEntidades = new ArrayList<>();

        listaNombreEntidades.add(CORRERIA);
        listaNombreEntidades.add(REPORTE_NOTIFICACION);
        listaNombreEntidades.add(LABOR_X_ORDENTRABAJO);
        listaNombreEntidades.add(ORDEN_TRABAJO);
        listaNombreEntidades.add(TRABAJO_X_ORDENTRABAJO);
        listaNombreEntidades.add(TAREA_X_ORDEN_TRABAJO);
        listaNombreEntidades.add(REPORTE_NOTIFICACION);
        listaNombreEntidades.add(SIRIUS);
        listaNombreEntidades.add(PROGRAMACIONCORRERIA);
        listaNombreEntidades.add(CARGA);
        return listaNombreEntidades;
    }

    public List<String> obtenerListaNombreEntidadesConfirmacion(String confirmacionDescarga) {
        List<String> listaNombreEntidades = new ArrayList<>();
        ParametrosConfirmacion parametrosConfirmacion = new ParametrosConfirmacion(confirmacionDescarga);
        if (parametrosConfirmacion.isConfirmarCorreria()) {
            listaNombreEntidades.add(CONFIRMACIONCORRERIA);
        } else if (parametrosConfirmacion.isConfirmarOT()) {
            listaNombreEntidades.add(CONFIRMACION_OT);
        } else if (parametrosConfirmacion.isConfirmarAmbos()) {
            listaNombreEntidades.add(CONFIRMACIONCORRERIA);
            listaNombreEntidades.add(CONFIRMACION_OT);
        }

        return listaNombreEntidades;
    }

    public String obtenerNumeroCarpeta(String nombreEntidad) {
        final String CARPETA_CORRERIA = "01";
        final String CARPETA_ORDEN_TRABAJO = "02";
        final String CARPETA_TRABAJO_X_ORDENTRABAJO = "04";
        final String CARPETA_TAREA_X_ORDEN_TRABAJO = "05";
        final String CARPETA_LABOR_X_ORDENTRABAJO = "07";
        final String CARPETA_REPORTE_NOTIFICACION = "14";
        final String CARPETA_SIRIUS = "20";
        final String CARPETA_PROGRAMACIONCORRERIA = "19";
        final String CARPETA_CARGA = "24";

        String carpeta;

        switch (nombreEntidad.toUpperCase()) {
            case CORRERIA:
                carpeta = CARPETA_CORRERIA;
                break;
            case LABOR_X_ORDENTRABAJO:
                carpeta = CARPETA_LABOR_X_ORDENTRABAJO;
                break;
            case ORDEN_TRABAJO:
                carpeta = CARPETA_ORDEN_TRABAJO;
                break;
            case TAREA_X_ORDEN_TRABAJO:
                carpeta = CARPETA_TAREA_X_ORDEN_TRABAJO;
                break;
            case REPORTE_NOTIFICACION:
                carpeta = CARPETA_REPORTE_NOTIFICACION;
                break;
            case TRABAJO_X_ORDENTRABAJO:
                carpeta = CARPETA_TRABAJO_X_ORDENTRABAJO;
                break;
            case SIRIUS:
                carpeta = CARPETA_SIRIUS;
                break;
            case PROGRAMACIONCORRERIA:
                carpeta = CARPETA_PROGRAMACIONCORRERIA;
                break;
            case CARGA:
                carpeta = CARPETA_CARGA;
                break;
            default:
                throw new IllegalArgumentException("No se pudo obtener el objeto de negocio para la entidad " + nombreEntidad);
        }
        return carpeta;
    }

    public String obtenerNumeroCarpetaConfirmacion(String nombreEntidad) {
        final String CARPETA_CONFIRMACIONOT = "02";
        final String CARPETA_CONFIRMACIONCORRERIA = "01";

        String carpeta;

        switch (nombreEntidad.toUpperCase()) {
            case CONFIRMACION_OT:
                carpeta = CARPETA_CONFIRMACIONOT;
                break;
            case CONFIRMACIONCORRERIA:
                carpeta = CARPETA_CONFIRMACIONCORRERIA;
                break;
            default:
                throw new IllegalArgumentException("No se pudo obtener el objeto de negocio para la entidad " + nombreEntidad);
        }
        return carpeta;
    }
}
