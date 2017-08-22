package com.example.santiagolopezgarcia.talleres.services.dto.carga;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.administracion.SiriusBL;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.administracion.ListaSirius;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class DependenciaCargaMaestros {
    public static final String ESTADO = "SIRIUS_ESTADO.XML";
    public static final String TAREA = "SIRIUS_TAREA.XML";
    public static final String CAUSA_NO_LECTURA = "SIRIUS_CAUSANOLECTURA.XML";
    public static final String TIPO_LECTURA = "SIRIUS_TIPOLECTURA.XML";
    public static final String TRABAJO = "SIRIUS_TRABAJO.XML";
    public static final String OBSERVACION_CONSUMO = "SIRIUS_OBSERVACIONCONSUMO.XML";
    public static final String OBSERVACION_ADICIONAL_LECTURA = "SIRIUS_OBSADICIONALLECTURA.XML";
    public static final String CONTRATO = "SIRIUS_CONTRATO.XML";
    public static final String PERFIL = "SIRIUS_PERFIL.XML";
    public static final String DEPARTAMENTO = "SIRIUS_DEPARTAMENTO.XML";
    public static final String MUNICIPIO = "SIRIUS_MUNICIPIO.XML";
    public static final String LABOR = "SIRIUS_LABOR.XML";
    public static final String ELEMENTO_AFORO = "SIRIUS_ELEMENTOAFORO.XML";
    public static final String UNIDAD = "SIRIUS_UNIDAD.XML";
    public static final String MATERIAL = "SIRIUS_MATERIAL.XML";
    public static final String MATERIAL_X_LABOR = "SIRIUS_MATERIALXLABOR.XML";
    public static final String MULTIOPCION = "SIRIUS_MULTIOPCION.XML";
    public static final String OPERACION = "SIRIUS_OPERACION.XML";
    public static final String OBSERVACION_ELEMENTO = "SIRIUS_OBSERVACIONELEMENTO.XML";
    public static final String LOCALIDAD = "SIRIUS_LOCALIDAD.XML";
    public static final String PARAMETRO_PCT = "SIRIUS_PARAMETROPCT.XML";
    public static final String OPCION = "SIRIUS_OPCION.XML";
    public static final String PERFIL_X_OPCION = "SIRIUS_PERFILOPCION.XML";
    public static final String RANGO = "SIRIUS_RANGO.XML";
    public static final String NOTIFICACION = "SIRIUS_NOTIFICACION.XML";
    public static final String ITEM = "SIRIUS_ITEM.XML";
    public static final String ITEM_X_NOTIFICACION = "SIRIUS_ITEMXNOTIFICACION.XML";
    public static final String EMPRESA = "SIRIUS_EMPRESA.XML";
    public static final String MARCAR_ELEMENTO = "SIRIUS_MARCAELEMENTO.XML";
    public static final String MODELO_ELEMENTO = "SIRIUS_MODELOELEMENTO.XML";
    public static final String TIPO_ELEMENTO = "SIRIUS_TIPOELEMENTO.XML";
    public static final String UBICACION_ELEMENTO = "SIRIUS_UBICACIONELEMENTO.XML";
    public static final String ELEMENTO_AFORO_X_LABOR = "SIRIUS_ELEMENTOAFOROXLABOR.XML";
    public static final String TAREAXTRABAJO = "SIRIUS_TAREAXTRABAJO.XML";
    public static final String LABOR_X_TAREA = "SIRIUS_LABORXTAREA.XML";
    public static final String TRABAJO_X_CONTRATO = "SIRIUS_TRABAJOXCONTRATO.XML";
    public static final String SECCION_IMPRESION = "SIRIUS_SECCIONIMPRESION.XML";
    public static final String PARRAFO_IMPRESION = "SIRIUS_PARRAFOIMPRESION.XML";
    public static final String ELEMENTO_DISPONIBLE = "SIRIUS_ELEMENTODISPONIBLE.XML";//ElementoBL
    public static final String NUEVA_LECTURA_ELEMENTO_DISPONIBLE = "SIRIUS_NUEVOLECTURAELEMENT.XML";//ElementoBL
    public static final String SIRIUS = "SIRIUS.XML";
    public static final String CONCEPTO = "FAC_CONCEPTO.XML";
    public static final String GRUPO_LECTURA = "FAC_GRUPOLECTURA.XML";
    public static final String CARGA = "CARGA.XML";
//
//    private EstadoBL estadoBL;
//    private TareaBL tareaBL;
//    private CausaNoLecturaBL causaNoLecturaBL;
//    private TipoLecturaBL tipoLecturaBL;
//    private TrabajoBL trabajoBL;
//    private ObservacionConsumoBL observacionConsumoBL;
//    private ObservacionAdicionalBL observacionAdicionalBL;
//    private ContratoBL contratoBL;
//    private PerfilBL perfilBL;
//    private DepartamentoBL departamentoBL;
//    private MunicipioBL municipioBL;
//    private LaborBL laborBL;
//    private ElementoAforoBL elementoAforoBL;
//    private ElementoAforoXLaborBL elementoAforoXLaborBL;
//    private UnidadBL unidadBL;
//    private MaterialBL materialBL;
//    private MaterialXLaborBL materialXLaborBL;
//    private MultiOpcionBL multiOpcionBL;
//    private OperacionBL operacionBL;
//    private ObservacionElementoBL observacionElementoBL;
//    private ParametroPctBL parametroPctBL;
//    private OpcionBL opcionBL;
//    private PerfilXOpcionBL perfilXOpcionBL;
//    private NotificacionBL notificacionBL;
//    private ItemBL itemBL;
//    private ItemXNotificacionBL itemXNotificacionBL;
//    private EmpresaBL empresaBL;
    private SiriusBL siriusBL;
//    private LaborXTareaBL laborXTareaBL;
//    private MarcaElementoBL marcaElementoBL;
//    private ModeloElementoBL modeloElementoBL;
//    private TipoElementoBL tipoElementoBL;
//    private UbicacionElementoBL ubicacionElementoBL;
//    private TareaXTrabajoBL tareaXTrabajoBL;
//    private TrabajoXContratoBL trabajoXContratoBL;
//    private SeccionImpresionBL seccionImpresionBL;
//    private ParrafoImpresionBL parrafoImpresionBL;
//    private ConceptoBL conceptoBL;
//    private GrupoLecturaBL grupoLecturaBL;
//    private ElementoDisponibleBL elementoDisponibleBL;
//    private NuevaLecturaElementoDisponibleBL nuevaLecturaElementoDisponibleBL;

    @Inject
    public DependenciaCargaMaestros(SiriusBL siriusBL) {
        this.siriusBL = siriusBL;
    }

    public LogicaNegocioBase getClaseNegocio(String nombreEntidad) {
        LogicaNegocioBase entidad = null;
        switch (nombreEntidad) {
//            case CAUSA_NO_LECTURA:
//                entidad = causaNoLecturaBL;
//                break;
//            case CONTRATO:
//                entidad = this.contratoBL;
//                break;
//            case DEPARTAMENTO:
//                entidad = this.departamentoBL;
//                break;
//            case ELEMENTO_AFORO:
//                entidad = this.elementoAforoBL;
//                break;
//            case ELEMENTO_AFORO_X_LABOR:
//                entidad = elementoAforoXLaborBL;
//                break;
//            case EMPRESA:
//                entidad = this.empresaBL;
//                break;
//            case ESTADO:
//                entidad = this.estadoBL;
//                break;
//            case ITEM:
//                entidad = this.itemBL;
//                break;
//            case ITEM_X_NOTIFICACION:
//                entidad = this.itemXNotificacionBL;
//                break;
//            case LABOR:
//                entidad = this.laborBL;
//                break;
//            case LABOR_X_TAREA:
//                entidad = this.laborXTareaBL;
//                break;
//            case MARCAR_ELEMENTO:
//                entidad = this.marcaElementoBL;
//                break;
//            case MATERIAL:
//                entidad = this.materialBL;
//                break;
//            case MATERIAL_X_LABOR:
//                entidad = this.materialXLaborBL;
//                break;
//            case MODELO_ELEMENTO:
//                entidad = this.modeloElementoBL;
//                break;
//            case MULTIOPCION:
//                entidad = this.multiOpcionBL;
//                break;
//            case MUNICIPIO:
//                entidad = this.municipioBL;
//                break;
//            case NOTIFICACION:
//                entidad = this.notificacionBL;
//                break;
//            case OBSERVACION_ADICIONAL_LECTURA:
//                entidad = this.observacionAdicionalBL;
//                break;
//            case OBSERVACION_CONSUMO:
//                entidad = this.observacionConsumoBL;
//                break;
//            case OBSERVACION_ELEMENTO:
//                entidad = this.observacionElementoBL;
//                break;
//            case OPCION:
//                entidad = this.opcionBL;
//                break;
//            case OPERACION:
//                entidad = this.operacionBL;
//                break;
//            case PARAMETRO_PCT:
//                entidad = this.parametroPctBL;
//                break;
//            case PERFIL:
//                entidad = this.perfilBL;
//                break;
//            case PERFIL_X_OPCION:
//                entidad = this.perfilXOpcionBL;
//                break;
            case SIRIUS:
                entidad = this.siriusBL;
                break;
//            case TAREA:
//                entidad = this.tareaBL;
//                break;
//            case TAREAXTRABAJO:
//                entidad = this.tareaXTrabajoBL;
//                break;
//            case TIPO_ELEMENTO:
//                entidad = this.tipoElementoBL;
//                break;
//            case TIPO_LECTURA:
//                entidad = this.tipoLecturaBL;
//                break;
//            case TRABAJO:
//                entidad = this.trabajoBL;
//                break;
//            case TRABAJO_X_CONTRATO:
//                entidad = this.trabajoXContratoBL;
//                break;
//            case UBICACION_ELEMENTO:
//                entidad = this.ubicacionElementoBL;
//                break;
//            case UNIDAD:
//                entidad = this.unidadBL;
//                break;
//            case SECCION_IMPRESION:
//                entidad = this.seccionImpresionBL;
//                break;
//            case PARRAFO_IMPRESION:
//                entidad = this.parrafoImpresionBL;
//                break;
//            case CONCEPTO:
//                entidad = this.conceptoBL;
//                break;
//            case GRUPO_LECTURA:
//                entidad = this.grupoLecturaBL;
//                break;
//            case ELEMENTO_DISPONIBLE:
//                entidad = this.elementoDisponibleBL;
//                break;
//            case NUEVA_LECTURA_ELEMENTO_DISPONIBLE:
//                entidad = this.nuevaLecturaElementoDisponibleBL;
//                break;
        }
        return entidad;
    }

    public Class obtenerTipoDto(String nombreEntidad) {
        Class tipo;
        switch (nombreEntidad) {
//            case CAUSA_NO_LECTURA:
//                tipo = ListaCausaNoLectura.class;
//                break;
//            case CONTRATO:
//                tipo = ListaContrato.class;
//                break;
//            case DEPARTAMENTO:
//                tipo = ListaDepartamento.class;
//                break;
//            case ELEMENTO_AFORO:
//                tipo = ListaElementoAforo.class;
//                break;
//            case ELEMENTO_AFORO_X_LABOR:
//                tipo = ListaElementoAforoXLabor.class;
//                break;
//            case EMPRESA:
//                tipo = ListaEmpresa.class;
//                break;
//            case ESTADO:
//                tipo = ListaEstados.class;
//                break;
//            case ITEM:
//                tipo = ListaItem.class;
//                break;
//            case ITEM_X_NOTIFICACION:
//                tipo = ListaItemXNotificacion.class;
//                break;
//            case LABOR:
//                tipo = ListaLabor.class;
//                break;
//            case LABOR_X_TAREA:
//                tipo = ListaLaborXTarea.class;
//                break;
//            case MARCAR_ELEMENTO:
//                tipo = ListaMarcaElemento.class;
//                break;
//            case MATERIAL:
//                tipo = ListaMaterial.class;
//                break;
//            case MATERIAL_X_LABOR:
//                tipo = ListaMaterialXLabor.class;
//                break;
//            case MODELO_ELEMENTO:
//                tipo = ListaModeloElemento.class;
//                break;
//            case MULTIOPCION:
//                tipo = ListaMultiOpcion.class;
//                break;
//            case MUNICIPIO:
//                tipo = ListaMunicipio.class;
//                break;
//            case NOTIFICACION:
//                tipo = ListaNotificacion.class;
//                break;
//            case OBSERVACION_ADICIONAL_LECTURA:
//                tipo = ListaObservacionAdicional.class;
//                break;
//            case OBSERVACION_CONSUMO:
//                tipo = ListaObservacionConsumo.class;
//                break;
//            case OBSERVACION_ELEMENTO:
//                tipo = ListaObservacionElemento.class;
//                break;
//            case OPCION:
//                tipo = ListaOpcion.class;
//                break;
//            case OPERACION:
//                tipo = ListaOperacion.class;
//                break;
//            case PARAMETRO_PCT:
//                tipo = ListaParametroPCT.class;
//                break;
//            case PERFIL:
//                tipo = ListaPerfil.class;
//                break;
//            case PERFIL_X_OPCION:
//                tipo = ListaPerfilXOpcion.class;
//                break;
            case SIRIUS:
                tipo = ListaSirius.class;
                break;
//            case TAREA:
//                tipo = ListaTareas.class;
//                break;
//            case TAREAXTRABAJO:
//                tipo = ListaTareaXTrabajo.class;
//                break;
//            case TIPO_ELEMENTO:
//                tipo = ListaTiposElemento.class;
//                break;
//            case TIPO_LECTURA:
//                tipo = ListaTipoLectura.class;
//                break;
//            case TRABAJO:
//                tipo = ListaTrabajo.class;
//                break;
//            case TRABAJO_X_CONTRATO:
//                tipo = ListaTrabajoXContrato.class;
//                break;
//            case UBICACION_ELEMENTO:
//                tipo = ListaUbicacionElemento.class;
//                break;
//            case UNIDAD:
//                tipo = ListaUnidad.class;
//                break;
//            case PARRAFO_IMPRESION:
//                tipo = ListaParrafoImpresion.class;
//                break;
//            case SECCION_IMPRESION:
//                tipo = ListaSeccionImpresion.class;
//                break;
//            case CONCEPTO:
//                tipo = ListaConcepto.class;
//                break;
//            case GRUPO_LECTURA:
//                tipo = ListaGrupoLectura.class;
//                break;
//            case CARGA:
//                tipo = ListaCarga.class;
//                break;
//            case ELEMENTO_DISPONIBLE:
//                tipo = ListaElementoDisponible.class;
//                break;
//            case NUEVA_LECTURA_ELEMENTO_DISPONIBLE:
//                tipo = ListaNuevaLecturaElementoDisponible.class;
//                break;
            default:
                throw new IllegalArgumentException("La entidad no se encuentra implementada para DTO. (" + nombreEntidad + ")");
        }
        return tipo;
    }
}