package com.example.santiagolopezgarcia.talleres.integracion.carga;

import com.example.dominio.IEliminarDatosPorRecarga;
import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.administracion.TalleresBL;
import com.example.dominio.administracion.UsuarioBL;
import com.example.dominio.correria.CorreriaBL;
import com.example.dominio.correria.ProgramacionCorreriaBL;
import com.example.dominio.labor.LaborXOrdenTrabajoBL;
import com.example.dominio.notificacion.ReporteNotificacionBL;
import com.example.dominio.ordentrabajo.NotificacionOrdenTrabajoBL;
import com.example.dominio.ordentrabajo.OrdenTrabajoBL;
import com.example.dominio.tarea.TareaXOrdenTrabajoBL;
import com.example.dominio.trabajo.TrabajoXOrdenTrabajoBL;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.administracion.ListaCarga;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.administracion.ListaTalleres;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.ordentrabajo.ListaOrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.ordentrabajo.ListaTareaXOrdenTrabajo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class DependenciaCargaDiaria {
    public static final String SIRIUS = "SIRIUS.XML";
    public static final String CORRERIA = "SIRIUS_CORRERIA.XML";
    public static final String ORDENTRABAJO = "SIRIUS_ORDENTRABAJO.XML";
    public static final String LECTURA_ELEMENTO = "SIRIUS_LECTURAELEMENTO.XML";
    public static final String ELEMENTO = "SIRIUS_ELEMENTO.XML";
    public static final String TAREA_X_ORDENTRABAJO = "SIRIUS_TAREAXORDENTRABAJO.XML";//ordentrabajobl
    public static final String TRABAJO_X_ORDENTRABAJO = "SIRIUS_TRABAJOXORDENTRABAJ.XML";
    public static final String HISTORIA_ELEMENTO = "SIRIUS_HISTORIAELEMENTO.XML";//ordentrabajobl
    public static final String LABOR_X_ORDENTRABAJO = "SIRIUS_LABORXORDENTRABAJO.XML";//ordentrabajobl
    public static final String RELACION_ELEMENTO = "SIRIUS_RELACIONELEMENTO.XML";
    public static final String REPORTE_NOTIFICACION = "SIRIUS_REPORTENOTIFICACION.XML";
    public static final String ELEMENTO_DISPONIBLE = "SIRIUS_ELEMENTODISPONIBLE.XML";//ElementoBL
    public static final String NUEVA_LECTURA_ELEMENTO_DISPONIBLE = "SIRIUS_NUEVOLECTURAELEMENT.XML";//ElementoBL
    public static final String LABOR_OBSERVACION_ELEMENTO = "SIRIUS_LABOROBSELEMENTO.XML";
    public static final String USUARIO = "SIRIUS_USUARIO.XML";
    public static final String LABOR_ELEMENTO = "SIRIUS_LABORELEMENTO.XML";
    public static final String LABOR_LECTURA = "SIRIUS_LABORLECTURA.XML";
    public static final String LABOR_MATERIAL = "SIRIUS_LABORMATERIAL.XML";
    public static final String LABOR_AFORO = "SIRIUS_LABORAFORO.XML";
    public static final String LABOR_CIERRE = "SIRIUS_LABORCIERRE.XML";
    public static final String LABOR_PCT = "SIRIUS_LABORPCT.XML";
    public static final String PROGRAMACIONCORRERIA = "SIRIUS_PROGRAMACIONCORRERI.XML";
    public static final String NOTIFICACION_ORDENTRABAJO = "SIRIUS_NOTIFICACIONOT.XML";
    public static final String IMPRESION_FACTURACION = "FAC_IMPRESION.XML";
    public static final String LABOR_CONCEPTO = "FAC_LABORCONCEPTO.XML";
    public static final String RANGO_FACTURACION = "FAC_RANGOFACTURACION.XML";
    public static final String LECTURAS = "SIRIUS_LECTURAS.XML";
    public static final String CARGA = "CARGA.XML";

    private TalleresBL talleresBL;
    private CorreriaBL correriaBL;
    private OrdenTrabajoBL ordenTrabajoBL;
    private TrabajoXOrdenTrabajoBL trabajoXOrdenTrabajoBL;
    private ReporteNotificacionBL reporteNotificacionBL;
    private UsuarioBL usuarioBL;
    private TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL;
    private LaborXOrdenTrabajoBL laborXOrdenTrabajoBL;
    private ProgramacionCorreriaBL programacionCorreriaBL;
    private NotificacionOrdenTrabajoBL notificacionOrdenTrabajoBL;

    @Inject
    public DependenciaCargaDiaria(TalleresBL talleresBL, CorreriaBL correriaBL, OrdenTrabajoBL ordenTrabajoBL
            , TrabajoXOrdenTrabajoBL trabajoXOrdenTrabajoBL, ReporteNotificacionBL reporteNotificacionBL
            , UsuarioBL usuarioBL, TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL, LaborXOrdenTrabajoBL laborXOrdenTrabajoBL
            , ProgramacionCorreriaBL programacionCorreriaBL
            , NotificacionOrdenTrabajoBL notificacionOrdenTrabajoBL) {
        this.talleresBL = talleresBL;
        this.correriaBL = correriaBL;
        this.ordenTrabajoBL = ordenTrabajoBL;
        this.trabajoXOrdenTrabajoBL = trabajoXOrdenTrabajoBL;
        this.reporteNotificacionBL = reporteNotificacionBL;
        this.usuarioBL = usuarioBL;
        this.tareaXOrdenTrabajoBL = tareaXOrdenTrabajoBL;
        this.laborXOrdenTrabajoBL = laborXOrdenTrabajoBL;
        this.programacionCorreriaBL = programacionCorreriaBL;
    }

    public LogicaNegocioBase getClaseNegocio(String nombreEntidad) {
        LogicaNegocioBase entidad;

        switch (nombreEntidad.toUpperCase()) {
            case CORRERIA:
                entidad = this.correriaBL;
                break;
            case ORDENTRABAJO:
                entidad = this.ordenTrabajoBL;
                break;
            case TAREA_X_ORDENTRABAJO:
                entidad = this.tareaXOrdenTrabajoBL;
                break;
            case LABOR_X_ORDENTRABAJO:
                entidad = this.laborXOrdenTrabajoBL;
                break;
            case USUARIO:
                entidad = this.usuarioBL;
                break;
            case SIRIUS:
                entidad = this.talleresBL;
                break;
            case PROGRAMACIONCORRERIA:
                entidad = this.programacionCorreriaBL;
                break;
            case NOTIFICACION_ORDENTRABAJO:
                entidad = this.notificacionOrdenTrabajoBL;
                break;
            default:
                throw new IllegalArgumentException("La entidad no se encuentra implementada obtener la logica de negocio. (" + nombreEntidad + ")");
        }

        return entidad;
    }

    public Class obtenerTipoDto(String nombreEntidad) {
        Class tipo;
        switch (nombreEntidad) {
//            case CORRERIA:
//                tipo = ListaC.class;
//                break;
            case ORDENTRABAJO:
                tipo = ListaOrdenTrabajo.class;
                break;
//            case ELEMENTO:
//                tipo = ListaElementos.class;
//                break;
//            case LECTURA_ELEMENTO:
//                tipo = ListaLecturaElemento.class;
//                break;
//            case TRABAJO_X_ORDENTRABAJO:
//                tipo = ListaTrabajoXOrdenTrabajo.class;
//                break;
            case TAREA_X_ORDENTRABAJO:
                tipo = ListaTareaXOrdenTrabajo.class;
                break;
//            case HISTORIA_ELEMENTO:
//                tipo = ListaHistoriaElemento.class;
//                break;
//            case LABOR_X_ORDENTRABAJO:
//                tipo = ListaLaborXOrdenTrabajo.class;
//                break;
//            case RELACION_ELEMENTO:
//                tipo = ListaRelacionElemento.class;
//                break;
//            case REPORTE_NOTIFICACION:
//                tipo = ListaReporteNotificacion.class;
//                break;
//            case ELEMENTO_DISPONIBLE:
//                tipo = ListaElementoDisponible.class;
//                break;
//            case NUEVA_LECTURA_ELEMENTO_DISPONIBLE:
//                tipo = ListaNuevaLecturaElementoDisponible.class;
//                break;
//            case USUARIO:
//                tipo = ListaUsuario.class;
//                break;
            case SIRIUS:
                tipo = ListaTalleres.class;
                break;
//            case LABOR_OBSERVACION_ELEMENTO:
//                tipo = ListaLaborObservacionElemento.class;
//                break;
//            case LABOR_ELEMENTO:
//                tipo = ListaLaborElemento.class;
//                break;
//            case LABOR_LECTURA:
//                tipo = ListaLaborLectura.class;
//                break;
//            case LABOR_MATERIAL:
//                tipo = ListaLaborMaterial.class;
//                break;
//            case LABOR_AFORO:
//                tipo = ListaLaborAforo.class;
//                break;
//            case LABOR_CIERRE:
//                tipo = ListaLaborCierre.class;
//                break;
//            case LABOR_PCT:
//                tipo = ListaLaborPCT.class;
//                break;
//            case PROGRAMACIONCORRERIA:
//                tipo = ListaProgramacionCorreria.class;
//                break;
//            case NOTIFICACION_ORDENTRABAJO:
//                tipo = ListaNotificacionOrdenTrabajo.class;
//                break;
//            case IMPRESION_FACTURACION:
//                tipo = ListaImpresion.class;
//                break;
//            case LABOR_CONCEPTO:
//                tipo = ListaLaborConcepto.class;
//                break;
//            case RANGO_FACTURACION:
//                tipo = ListaRangoFacturacion.class;
//                break;
//            case LECTURAS:
//                tipo = ListaLecturas.class;
//                break;
            case CARGA:
                tipo = ListaCarga.class;
                break;
            default:
                throw new IllegalArgumentException("La entidad no se encuentra implementada para DTO. (" + nombreEntidad + ")");
        }
        return tipo;
    }

    public List<String> obtenerNombresEntidadesAEliminarPorRecarga() {
        List<String> listaEntidades = new ArrayList<>();
//        listaEntidades.add(LaborAforoBL.class.getSimpleName());
//        listaEntidades.add(LaborCierreBL.class.getSimpleName());
//        listaEntidades.add(LaborElementoLecturaBL.class.getSimpleName());
//        listaEntidades.add(LaborLecturaBL.class.getSimpleName());
//        listaEntidades.add(LaborMaterialBL.class.getSimpleName());
//        listaEntidades.add(LaborObservacionElementoBL.class.getSimpleName());
//        listaEntidades.add(LaborPctBL.class.getSimpleName());
        listaEntidades.add(ReporteNotificacionBL.class.getSimpleName());
//        listaEntidades.add(ImpresionFacturacionBL.class.getSimpleName());
//        listaEntidades.add(LaborConceptoBL.class.getSimpleName());
//        listaEntidades.add(RangoFacturacionBL.class.getSimpleName());
        return listaEntidades;
    }

    public IEliminarDatosPorRecarga obtenerEntidadAEliminarPorRecarga(String nombreEntidad) {
        IEliminarDatosPorRecarga eliminarDatosPorRecarga = null;

//        if (nombreEntidad.equals(LaborAforoBL.class.getSimpleName())) {
//            eliminarDatosPorRecarga = this.laborAforoBL;
//        } else if (nombreEntidad.equals(LaborCierreBL.class.getSimpleName())) {
//            eliminarDatosPorRecarga = this.laborCierreBL;
//        } else if (nombreEntidad.equals(LaborElementoLecturaBL.class.getSimpleName())) {
//            eliminarDatosPorRecarga = this.laborElementoBL;
//        } else if (nombreEntidad.equals(LaborLecturaBL.class.getSimpleName())) {
//            eliminarDatosPorRecarga = this.laborLecturaBL;
//        } else if (nombreEntidad.equals(LaborMaterialBL.class.getSimpleName())) {
//            eliminarDatosPorRecarga = this.laborMaterialBL;
//        } else if (nombreEntidad.equals(LaborObservacionElementoBL.class.getSimpleName())) {
//            eliminarDatosPorRecarga = this.laborObservacionElementoBL;
//        } else if (nombreEntidad.equals(LaborPctBL.class.getSimpleName())) {
//            eliminarDatosPorRecarga = this.laborPctBL;
//        } else
            if (nombreEntidad.equals(ReporteNotificacionBL.class.getSimpleName())) {
                eliminarDatosPorRecarga = this.reporteNotificacionBL;
            }
//        } else if (nombreEntidad.equals(LaborConceptoBL.class.getSimpleName())) {
//            eliminarDatosPorRecarga = this.laborConceptoBL;
//        }else if (nombreEntidad.equals(ImpresionFacturacionBL.class.getSimpleName())) {
//            eliminarDatosPorRecarga = this.impresionFacturacionBL;
//        } else if (nombreEntidad.equals(RangoFacturacionBL.class.getSimpleName())) {
//            eliminarDatosPorRecarga = this.rangoFacturacionBL;
//        }

        return eliminarDatosPorRecarga;
    }
}
