package com.example.santiagolopezgarcia.talleres.integracion.descarga;

import com.example.dominio.IBaseDescarga;
import com.example.dominio.administracion.TalleresBL;
import com.example.dominio.correria.CorreriaBL;
import com.example.dominio.notificacion.ReporteNotificacionBL;
import com.example.dominio.ordentrabajo.TareaXOrdenTrabajoBL;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class DependenciaDescarga {
    public static final String CORRERIA = "SIRIUS_CORRERIA.XML";
    public static final String ELEMENTO = "SIRIUS_ELEMENTO.XML";
    public static final String HISTORIA_ELEMENTO = "SIRIUS_HISTORIAELEMENTO.XML";
    public static final String LECTURA_ELEMENTO = "SIRIUS_LECTURAELEMENTO.XML";
    public static final String NUEVO_LECTURA_ELEMENTO_DISPONIBLE = "SIRIUS_NUEVOLECTURAELEMENT.XML";
    public static final String ELEMENTO_DISPONIBLE = "SIRIUS_ELEMENTODISPONIBLE.XML";
    public static final String LABOR_OBSERVACION_ELEMENTO = "SIRIUS_LABOROBSELEMENTO.XML";
    public static final String LABOR_X_ORDENTRABAJO = "SIRIUS_LABORXORDENTRABAJO.XML";
    public static final String LABOR_LECTURA = "SIRIUS_LABORLECTURA.XML";
    public static final String ORDEN_TRABAJO = "SIRIUS_ORDENTRABAJO.XML";
    public static final String TAREA_X_ORDEN_TRABAJO = "SIRIUS_TAREAXORDENTRABAJO.XML";
    public static final String LABOR_AFORO = "SIRIUS_LABORAFORO.XML";
    public static final String LABOR_CIERRE = "SIRIUS_LABORCIERRE.XML";
    public static final String LABOR_ELEMENTO = "SIRIUS_LABORELEMENTO.XML";
    public static final String LABOR_MATERIAL = "SIRIUS_LABORMATERIAL.XML";
    public static final String LABOR_PCT = "SIRIUS_LABORPCT.XML";
    public static final String RELACION_ELEMENTO = "SIRIUS_RELACIONELEMENTO.XML";
    public static final String REPORTE_NOTIFICACION = "SIRIUS_REPORTENOTIFICACION.XML";
    public static final String TRABAJO_X_ORDENTRABAJO = "SIRIUS_TRABAJOXORDENTRABAJ.XML";
    public static final String SIRIUS = "SIRIUS.XML";
    public static final String PROGRAMACIONCORRERIA = "SIRIUS_PROGRAMACIONCORRERI.XML";
    public static final String IMPRESION_FACTURACION = "FAC_IMPRESION.XML";
    public static final String LABOR_CONCEPTO = "FAC_LABORCONCEPTO.XML";
    public static final String RANGO_FACTURACION = "FAC_RANGOFACTURACION.XML";
    public static final String CONFIRMACION_OT = "SIRIUS_CONFIRMACIONOT.XML";
    public static final String CONFIRMACIONCORRERIA = "SIRIUS_CONFIRMACIONCORRERI.XML";
    public static final String CARGA = "CARGA.XML";

    private CorreriaBL correriaBL;
//    private OrdenTrabajoBL ordenTrabajoBL;
//    private TrabajoXOrdenTrabajoBL trabajoXOrdenTrabajoBL;
//    private LaborObservacionElementoBL laborObservacionElementoBL;
//    private LaborLecturaBL laborLecturaBL;
//    private LaborAforoBL laborAforoBL;
//    private LaborCierreBL laborCierreBL;
//    private LaborElementoLecturaBL laborElementoLecturaBL;
//    private LaborMaterialBL laborMaterialBL;
//    private LaborPctBL laborPctBL;
//    private RelacionElementoBL relacionElementoBL;
    private ReporteNotificacionBL reporteNotificacionBL;
//    private LaborXOrdenTrabajoBL laborXOrdenTrabajoBL;
    private TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL;
//    private ElementoBL elementoBL;
//    private ElementoDisponibleBL elementoDisponibleBL;
//    private HistoricoElementoBL historicoElementoBL;
//    private LecturaElementoBL lecturaElementoBL;
//    private NuevaLecturaElementoDisponibleBL nuevaLecturaElementoDisponibleBL;
//    private ProgramacionCorreriaBL programacionCorreriaBL;
//    private LaborConceptoBL laborConceptoBL;
//    private ImpresionFacturacionBL impresionFacturacionBL;
//    private RangoFacturacionBL rangoFacturacionBL;
    private TalleresBL talleresBL;

    @Inject
    public DependenciaDescarga(CorreriaBL correriaBL,
//                               OrdenTrabajoBL ordenTrabajoBL,
//                               LecturaElementoBL lecturaElementoBL,
//                               TrabajoXOrdenTrabajoBL trabajoXOrdenTrabajoBL,
//                               LaborObservacionElementoBL laborObservacionElementoBL,
//                               LaborLecturaBL laborLecturaBL,
//                               LaborAforoBL laborAforoBL,
//                               LaborCierreBL laborCierreBL,
//                               LaborElementoLecturaBL laborElementoLecturaBL,
//                               LaborMaterialBL laborMaterialBL,
//                               LaborPctBL laborPctBL,
//                               RelacionElementoBL relacionElementoBL,
                               ReporteNotificacionBL reporteNotificacionBL,
//                               LaborXOrdenTrabajoBL laborXOrdenTrabajoBL,
                               TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL
//                               ElementoBL elementoBL, ElementoDisponibleBL elementoDisponibleBL,
//                               HistoricoElementoBL historicoElementoBL,
//                               NuevaLecturaElementoDisponibleBL nuevaLecturaElementoDisponibleBL,
//                               ProgramacionCorreriaBL programacionCorreriaBL,
//                               LaborConceptoBL laborConceptoBL,
//                               ImpresionFacturacionBL impresionFacturacionBL,
//                               RangoFacturacionBL rangoFacturacionBL
                        , TalleresBL talleresBL) {
        this.talleresBL = talleresBL;
        this.correriaBL = correriaBL;
//        this.ordenTrabajoBL = ordenTrabajoBL;
//        this.trabajoXOrdenTrabajoBL = trabajoXOrdenTrabajoBL;
//        this.laborObservacionElementoBL = laborObservacionElementoBL;
//        this.laborLecturaBL = laborLecturaBL;
//        this.laborAforoBL = laborAforoBL;
//        this.laborCierreBL = laborCierreBL;
//        this.laborElementoLecturaBL = laborElementoLecturaBL;
//        this.laborMaterialBL = laborMaterialBL;
//        this.laborPctBL = laborPctBL;
//        this.relacionElementoBL = relacionElementoBL;
        this.reporteNotificacionBL = reporteNotificacionBL;
//        this.laborXOrdenTrabajoBL = laborXOrdenTrabajoBL;
        this.tareaXOrdenTrabajoBL = tareaXOrdenTrabajoBL;
//        this.elementoBL = elementoBL;
//        this.elementoDisponibleBL = elementoDisponibleBL;
//        this.historicoElementoBL = historicoElementoBL;
//        this.lecturaElementoBL = lecturaElementoBL;
//        this.nuevaLecturaElementoDisponibleBL = nuevaLecturaElementoDisponibleBL;
//        this.programacionCorreriaBL = programacionCorreriaBL;
//        this.laborConceptoBL = laborConceptoBL;
//        this.impresionFacturacionBL = impresionFacturacionBL;
//        this.rangoFacturacionBL = rangoFacturacionBL;
    }

    public IBaseDescarga obtenerObjetoNegocio(String nombreEntidad) {
        IBaseDescarga entidad;
        switch (nombreEntidad.toUpperCase()) {
            case CORRERIA:
                entidad = this.correriaBL;
                break;
//            case LABOR_AFORO:
//                entidad = this.laborAforoBL;
//                break;
//            case LABOR_CIERRE:
//                entidad = this.laborCierreBL;
//                break;
//            case LABOR_ELEMENTO:
//                entidad = this.laborElementoLecturaBL;
//                break;
//            case LABOR_LECTURA:
//                entidad = this.laborLecturaBL;
//                break;
//            case LABOR_MATERIAL:
//                entidad = this.laborMaterialBL;
//                break;
//            case LABOR_OBSERVACION_ELEMENTO:
//                entidad = this.laborObservacionElementoBL;
//                break;
//            case LABOR_PCT:
//                entidad = this.laborPctBL;
//                break;
//            case LABOR_X_ORDENTRABAJO:
//                entidad = this.laborXOrdenTrabajoBL;
//                break;
//            case ORDEN_TRABAJO:
//                entidad = this.ordenTrabajoBL;
//                break;
            case TAREA_X_ORDEN_TRABAJO:
                entidad = this.tareaXOrdenTrabajoBL;
                break;
//            case RELACION_ELEMENTO:
//                entidad = this.relacionElementoBL;
//                break;
            case REPORTE_NOTIFICACION:
                entidad = this.reporteNotificacionBL;
                break;
//            case TRABAJO_X_ORDENTRABAJO:
//                entidad = this.trabajoXOrdenTrabajoBL;
//                break;
//            case ELEMENTO:
//                entidad = this.elementoBL;
//                break;
//            case ELEMENTO_DISPONIBLE:
//                entidad = this.elementoDisponibleBL;
//                break;
//            case HISTORIA_ELEMENTO:
//                entidad = this.historicoElementoBL;
//                break;
//            case LECTURA_ELEMENTO:
//                entidad = this.lecturaElementoBL;
//                break;
//            case NUEVO_LECTURA_ELEMENTO_DISPONIBLE:
//                entidad = this.nuevaLecturaElementoDisponibleBL;
//                break;
//            case PROGRAMACIONCORRERIA:
//                entidad = this.programacionCorreriaBL;
//                break;
//            case LABOR_CONCEPTO:
//                entidad = this.laborConceptoBL;
//                break;
//            case IMPRESION_FACTURACION:
//                entidad = this.impresionFacturacionBL;
//                break;
//            case RANGO_FACTURACION:
//                entidad = this.rangoFacturacionBL;
//                break;
//            case CONFIRMACION_OT:
//                entidad = this.ordenTrabajoBL;
//                break;
//            case CONFIRMACIONCORRERIA:
//                entidad = this.programacionCorreriaBL;
//                break;
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
//            case CORRERIA:
//                baseListaDtoDescarga = new ListaCorrerias();
//                break;
//            case LABOR_AFORO:
//                baseListaDtoDescarga = new ListaLaborAforo();
//                break;
//            case LABOR_CIERRE:
//                baseListaDtoDescarga = new ListaLaborCierre();
//                break;
//            case LABOR_ELEMENTO:
//                baseListaDtoDescarga = new ListaLaborElemento();
//                break;
//            case LABOR_LECTURA:
//                baseListaDtoDescarga = new ListaLaborLectura();
//                break;
//            case LABOR_MATERIAL:
//                baseListaDtoDescarga = new ListaLaborMaterial();
//                break;
//            case LABOR_OBSERVACION_ELEMENTO:
//                baseListaDtoDescarga = new ListaLaborObservacionElemento();
//                break;
//            case LABOR_PCT:
//                baseListaDtoDescarga = new ListaLaborPCT();
//                break;
//            case LABOR_X_ORDENTRABAJO:
//                baseListaDtoDescarga = new ListaLaborXOrdenTrabajo();
//                break;
//            case ORDEN_TRABAJO:
//                baseListaDtoDescarga = new ListaOrdenTrabajo();
//                break;
//            case TAREA_X_ORDEN_TRABAJO:
//                baseListaDtoDescarga = new ListaTareaXOrdenTrabajo();
//                break;
//            case RELACION_ELEMENTO:
//                baseListaDtoDescarga = new ListaRelacionElemento();
//                break;
//            case REPORTE_NOTIFICACION:
//                baseListaDtoDescarga = new ListaReporteNotificacion();
//                break;
//            case TRABAJO_X_ORDENTRABAJO:
//                baseListaDtoDescarga = new ListaTrabajoXOrdenTrabajo();
//                break;
//            case SIRIUS:
//                baseListaDtoDescarga = new ListaTalleres();
//                break;
//            case ELEMENTO:
//                baseListaDtoDescarga = new ListaElementos();
//                break;
//            case ELEMENTO_DISPONIBLE:
//                baseListaDtoDescarga = new ListaElementoDisponible();
//                break;
//            case HISTORIA_ELEMENTO:
//                baseListaDtoDescarga = new ListaHistoriaElemento();
//                break;
//            case LECTURA_ELEMENTO:
//                baseListaDtoDescarga = new ListaLecturaElemento();
//                break;
//            case NUEVO_LECTURA_ELEMENTO_DISPONIBLE:
//                baseListaDtoDescarga = new ListaNuevoLecturaElementoDisponible();
//                break;
//            case PROGRAMACIONCORRERIA:
//                baseListaDtoDescarga = new ListaProgramacionCorreria();
//                break;
//            case LABOR_CONCEPTO:
//                baseListaDtoDescarga = new ListaLaborConcepto();
//                break;
//            case CARGA:
//                baseListaDtoDescarga = new ListaCarga();
//                break;
//            case IMPRESION_FACTURACION:
//                baseListaDtoDescarga = new ListaImpresion();
//                break;
//            case RANGO_FACTURACION:
//                baseListaDtoDescarga = new ListaRangoFacturacion();
//                break;
//            default:
//                throw new IllegalArgumentException("No se pudo obtener el tipo de listado de objetos de transferencia de datos para la entidad " + nombreEntidad);

        }
        return baseListaDtoDescarga;
    }

//    public BaseListaDtoConfirmacion obtenerTipoDtoConfirmacion(String nombreEntidad) {
//        BaseListaDtoConfirmacion baseListaDtoDescarga;
//        switch (nombreEntidad.toUpperCase()) {
//            case CONFIRMACION_OT:
//                baseListaDtoDescarga = new ListaConfirmacionOrdenTrabajo();
//                break;
//            case CONFIRMACIONCORRERIA:
//                baseListaDtoDescarga = new ListaConfirmacionCorreria();
//                break;
//            default:
//                throw new IllegalArgumentException("No se pudo obtener el tipo de listado de objetos de transferencia de datos para la entidad " + nombreEntidad);
//
//        }
//        return baseListaDtoDescarga;
//    }

    public List<String> obtenerListaNombreEntidades() {
        List<String> listaNombreEntidades = new ArrayList<>();

        listaNombreEntidades.add(CORRERIA);
        listaNombreEntidades.add(LABOR_OBSERVACION_ELEMENTO);
        listaNombreEntidades.add(LABOR_X_ORDENTRABAJO);
        listaNombreEntidades.add(LABOR_LECTURA);
        listaNombreEntidades.add(ORDEN_TRABAJO);
        listaNombreEntidades.add(TRABAJO_X_ORDENTRABAJO);
        listaNombreEntidades.add(TAREA_X_ORDEN_TRABAJO);
        listaNombreEntidades.add(LABOR_AFORO);
        listaNombreEntidades.add(LABOR_CIERRE);
        listaNombreEntidades.add(LABOR_ELEMENTO);
        listaNombreEntidades.add(LABOR_MATERIAL);
        listaNombreEntidades.add(LABOR_PCT);
        listaNombreEntidades.add(RELACION_ELEMENTO);
        listaNombreEntidades.add(REPORTE_NOTIFICACION);
        listaNombreEntidades.add(ELEMENTO);
        listaNombreEntidades.add(LECTURA_ELEMENTO);
        listaNombreEntidades.add(PROGRAMACIONCORRERIA);
        listaNombreEntidades.add(LABOR_CONCEPTO);
        return listaNombreEntidades;
    }

    public List<String> obtenerListaNombreEntidadesEnvio() {
        List<String> listaNombreEntidades = new ArrayList<>();

        listaNombreEntidades.add(CORRERIA);
        listaNombreEntidades.add(ELEMENTO);
        listaNombreEntidades.add(ELEMENTO_DISPONIBLE);
        listaNombreEntidades.add(HISTORIA_ELEMENTO);
        listaNombreEntidades.add(LECTURA_ELEMENTO);
        listaNombreEntidades.add(NUEVO_LECTURA_ELEMENTO_DISPONIBLE);
        listaNombreEntidades.add(REPORTE_NOTIFICACION);
        listaNombreEntidades.add(LABOR_OBSERVACION_ELEMENTO);
        listaNombreEntidades.add(LABOR_X_ORDENTRABAJO);
        listaNombreEntidades.add(LABOR_LECTURA);
        listaNombreEntidades.add(ORDEN_TRABAJO);
        listaNombreEntidades.add(TRABAJO_X_ORDENTRABAJO);
        listaNombreEntidades.add(TAREA_X_ORDEN_TRABAJO);
        listaNombreEntidades.add(LABOR_AFORO);
        listaNombreEntidades.add(LABOR_CIERRE);
        listaNombreEntidades.add(LABOR_ELEMENTO);
        listaNombreEntidades.add(LABOR_MATERIAL);
        listaNombreEntidades.add(LABOR_PCT);
        listaNombreEntidades.add(RELACION_ELEMENTO);
        listaNombreEntidades.add(REPORTE_NOTIFICACION);
        listaNombreEntidades.add(SIRIUS);
        listaNombreEntidades.add(PROGRAMACIONCORRERIA);
        listaNombreEntidades.add(LABOR_CONCEPTO);
        listaNombreEntidades.add(IMPRESION_FACTURACION);
        listaNombreEntidades.add(RANGO_FACTURACION);
        listaNombreEntidades.add(CARGA);
        return listaNombreEntidades;
    }

//    public List<String> obtenerListaNombreEntidadesConfirmacion(String confirmacionDescarga) {
//        List<String> listaNombreEntidades = new ArrayList<>();
//        ParametrosConfirmacion parametrosConfirmacion = new ParametrosConfirmacion(confirmacionDescarga);
//        if (parametrosConfirmacion.isConfirmarCorreria()) {
//            listaNombreEntidades.add(CONFIRMACIONCORRERIA);
//        } else if (parametrosConfirmacion.isConfirmarOT()) {
//            listaNombreEntidades.add(CONFIRMACION_OT);
//        } else if (parametrosConfirmacion.isConfirmarAmbos()) {
//            listaNombreEntidades.add(CONFIRMACIONCORRERIA);
//            listaNombreEntidades.add(CONFIRMACION_OT);
//        }
//
//        return listaNombreEntidades;
//    }

    public String obtenerNumeroCarpeta(String nombreEntidad) {
        final String CARPETA_CORRERIA = "01";
        final String CARPETA_ORDEN_TRABAJO = "02";
        final String CARPETA_ELEMENTO = "03";
        final String CARPETA_TRABAJO_X_ORDENTRABAJO = "04";
        final String CARPETA_TAREA_X_ORDEN_TRABAJO = "05";
        final String CARPETA_LABOR_AFORO = "06";
        final String CARPETA_LABOR_X_ORDENTRABAJO = "07";
        final String CARPETA_RELACION_ELEMENTO = "08";
        final String CARPETA_LECTURA_ELEMENTO = "09";
        final String CARPETA_LABOR_OBSERVACION_ELEMENTO = "10";
        final String CARPETA_LABOR_LECTURA = "11";
        final String CARPETA_LABOR_CIERRE = "12";
        final String CARPETA_LABOR_PCT = "13";
        final String CARPETA_REPORTE_NOTIFICACION = "14";
        final String CARPETA_LABOR_ELEMENTO = "15";
        final String CARPETA_ELEMENTO_DISPONIBLE = "16";
        final String CARPETA_HISTORIA_ELEMENTO = "17";
        final String CARPETA_LABOR_MATERIAL = "18";
        final String CARPETA_NUEVO_LECTURA_ELEMENTO_DISPONIBLE = "21";
        final String CARPETA_SIRIUS = "20";
        final String CARPETA_PROGRAMACIONCORRERIA = "19";
        final String CARPETA_IMPRESION = "21";
        final String CARPETA_LABOR_CONCEPTO = "22";
        final String CARPETA_RANGO_FACTURACION = "23";
        final String CARPETA_CARGA = "24";

        String carpeta;

        switch (nombreEntidad.toUpperCase()) {
            case CORRERIA:
                carpeta = CARPETA_CORRERIA;
                break;
            case LABOR_AFORO:
                carpeta = CARPETA_LABOR_AFORO;
                break;
            case LABOR_CIERRE:
                carpeta = CARPETA_LABOR_CIERRE;
                break;
            case LABOR_ELEMENTO:
                carpeta = CARPETA_LABOR_ELEMENTO;
                break;
            case LABOR_LECTURA:
                carpeta = CARPETA_LABOR_LECTURA;
                break;
            case LABOR_MATERIAL:
                carpeta = CARPETA_LABOR_MATERIAL;
                break;
            case LABOR_OBSERVACION_ELEMENTO:
                carpeta = CARPETA_LABOR_OBSERVACION_ELEMENTO;
                break;
            case LABOR_PCT:
                carpeta = CARPETA_LABOR_PCT;
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
            case RELACION_ELEMENTO:
                carpeta = CARPETA_RELACION_ELEMENTO;
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
            case ELEMENTO:
                carpeta = CARPETA_ELEMENTO;
                break;
            case ELEMENTO_DISPONIBLE:
                carpeta = CARPETA_ELEMENTO_DISPONIBLE;
                break;
            case HISTORIA_ELEMENTO:
                carpeta = CARPETA_HISTORIA_ELEMENTO;
                break;
            case LECTURA_ELEMENTO:
                carpeta = CARPETA_LECTURA_ELEMENTO;
                break;
            case NUEVO_LECTURA_ELEMENTO_DISPONIBLE:
                carpeta = CARPETA_NUEVO_LECTURA_ELEMENTO_DISPONIBLE;
                break;
            case PROGRAMACIONCORRERIA:
                carpeta = CARPETA_PROGRAMACIONCORRERIA;
                break;
            case LABOR_CONCEPTO:
                carpeta = CARPETA_LABOR_CONCEPTO;
                break;
            case IMPRESION_FACTURACION:
                carpeta = CARPETA_IMPRESION;
                break;
            case RANGO_FACTURACION:
                carpeta = CARPETA_RANGO_FACTURACION;
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
