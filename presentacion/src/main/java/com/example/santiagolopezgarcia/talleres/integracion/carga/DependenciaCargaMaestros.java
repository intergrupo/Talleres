package com.example.santiagolopezgarcia.talleres.integracion.carga;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.bussinesslogic.acceso.DepartamentoBL;
import com.example.dominio.bussinesslogic.acceso.MultiOpcionBL;
import com.example.dominio.bussinesslogic.acceso.MunicipioBL;
import com.example.dominio.bussinesslogic.administracion.ContratoBL;
import com.example.dominio.bussinesslogic.administracion.OpcionBL;
import com.example.dominio.bussinesslogic.administracion.PerfilBL;
import com.example.dominio.bussinesslogic.administracion.PerfilXOpcionBL;
import com.example.dominio.bussinesslogic.administracion.TalleresBL;
import com.example.dominio.bussinesslogic.correria.EmpresaBL;
import com.example.dominio.bussinesslogic.impresion.ParrafoImpresionBL;
import com.example.dominio.bussinesslogic.impresion.SeccionImpresionBL;
import com.example.dominio.bussinesslogic.labor.LaborBL;
import com.example.dominio.bussinesslogic.labor.LaborXTareaBL;
import com.example.dominio.bussinesslogic.notificacion.ItemBL;
import com.example.dominio.bussinesslogic.notificacion.ItemXNotificacionBL;
import com.example.dominio.bussinesslogic.notificacion.NotificacionBL;
import com.example.dominio.bussinesslogic.ordentrabajo.EstadoBL;
import com.example.dominio.bussinesslogic.tarea.TareaBL;
import com.example.dominio.bussinesslogic.tarea.TareaXTrabajoBL;
import com.example.dominio.bussinesslogic.trabajo.TrabajoBL;
import com.example.dominio.bussinesslogic.trabajo.TrabajoXContratoBL;
import com.example.dominio.modelonegocio.ListaMultiOpcion;
import com.example.dominio.modelonegocio.ListaOpcion;
import com.example.dominio.modelonegocio.ListaTareaXTrabajo;
import com.example.dominio.modelonegocio.ListaTareas;
import com.example.dominio.modelonegocio.ListaTrabajo;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.ListaCarga;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.acceso.ListaPerfil;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.acceso.ListaPerfilXOpcion;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.administracion.ListaDepartamento;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.administracion.ListaMunicipio;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.administracion.ListaTalleres;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.correria.ListaContrato;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.correria.ListaEmpresa;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.impresion.ListaParrafoImpresion;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.impresion.ListaSeccionImpresion;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.labor.ListaLabor;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.labor.ListaLaborXTarea;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.notificacion.ListaItem;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.notificacion.ListaItemXNotificacion;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.notificacion.ListaNotificacion;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.ordentrabajo.ListaEstados;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.trabajo.ListaTrabajoXContrato;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 10/3/17.
 */

public class DependenciaCargaMaestros {
    public static final String ESTADO = "SIRIUS_ESTADO.XML";
    public static final String TAREA = "SIRIUS_TAREA.XML";
    public static final String TRABAJO = "SIRIUS_TRABAJO.XML";
    public static final String CONTRATO = "SIRIUS_CONTRATO.XML";
    public static final String PERFIL = "SIRIUS_PERFIL.XML";
    public static final String DEPARTAMENTO = "SIRIUS_DEPARTAMENTO.XML";
    public static final String MUNICIPIO = "SIRIUS_MUNICIPIO.XML";
    public static final String LABOR = "SIRIUS_LABOR.XML";
    public static final String MULTIOPCION = "SIRIUS_MULTIOPCION.XML";
    public static final String OPCION = "SIRIUS_OPCION.XML";
    public static final String PERFIL_X_OPCION = "SIRIUS_PERFILOPCION.XML";
    public static final String NOTIFICACION = "SIRIUS_NOTIFICACION.XML";
    public static final String ITEM = "SIRIUS_ITEM.XML";
    public static final String ITEM_X_NOTIFICACION = "SIRIUS_ITEMXNOTIFICACION.XML";
    public static final String EMPRESA = "SIRIUS_EMPRESA.XML";
    public static final String TAREAXTRABAJO = "SIRIUS_TAREAXTRABAJO.XML";
    public static final String LABOR_X_TAREA = "SIRIUS_LABORXTAREA.XML";
    public static final String TRABAJO_X_CONTRATO = "SIRIUS_TRABAJOXCONTRATO.XML";
    public static final String SECCION_IMPRESION = "SIRIUS_SECCIONIMPRESION.XML";
    public static final String PARRAFO_IMPRESION = "SIRIUS_PARRAFOIMPRESION.XML";
    public static final String SIRIUS = "SIRIUS.XML";
    public static final String CARGA = "CARGA.XML";

    private EstadoBL estadoBL;
    private TareaBL tareaBL;
    private TrabajoBL trabajoBL;
    private ContratoBL contratoBL;
    private PerfilBL perfilBL;
    private DepartamentoBL departamentoBL;
    private MunicipioBL municipioBL;
    private LaborBL laborBL;
    private MultiOpcionBL multiOpcionBL;
    private OpcionBL opcionBL;
    private PerfilXOpcionBL perfilXOpcionBL;
    private NotificacionBL notificacionBL;
    private ItemBL itemBL;
    private ItemXNotificacionBL itemXNotificacionBL;
    private EmpresaBL empresaBL;
    private TalleresBL talleresBL;
    private LaborXTareaBL laborXTareaBL;
    private TareaXTrabajoBL tareaXTrabajoBL;
    private TrabajoXContratoBL trabajoXContratoBL;
    private SeccionImpresionBL seccionImpresionBL;
    private ParrafoImpresionBL parrafoImpresionBL;

    @Inject
    public DependenciaCargaMaestros(EstadoBL estadoBL, TareaBL tareaBL, TrabajoBL trabajoBL, ContratoBL contratoBL,
                                    PerfilBL perfilBL,
                                    DepartamentoBL departamentoBL, MunicipioBL municipioBL,
                                    LaborBL laborBL, MultiOpcionBL multiOpcionBL, OpcionBL opcionBL,
                                    PerfilXOpcionBL perfilXOpcionBL,
                                    NotificacionBL notificacionBL, ItemBL itemBL,
                                    ItemXNotificacionBL itemXNotificacionBL,
                                    EmpresaBL empresaBL, TalleresBL talleresBL,
                                    LaborXTareaBL laborXTareaBL, TareaXTrabajoBL tareaXTrabajoBL,
                                    TrabajoXContratoBL trabajoXContratoBL,ParrafoImpresionBL parrafoImpresionBL,
                                    SeccionImpresionBL seccionImpresionBL) {
        this.estadoBL = estadoBL;
        this.tareaBL = tareaBL;
        this.trabajoBL = trabajoBL;
        this.contratoBL = contratoBL;
        this.perfilBL = perfilBL;
        this.departamentoBL = departamentoBL;
        this.municipioBL = municipioBL;
        this.laborBL = laborBL;
        this.multiOpcionBL = multiOpcionBL;
        this.opcionBL = opcionBL;
        this.perfilXOpcionBL = perfilXOpcionBL;
        this.notificacionBL = notificacionBL;
        this.itemBL = itemBL;
        this.itemXNotificacionBL = itemXNotificacionBL;
        this.empresaBL = empresaBL;
        this.talleresBL = talleresBL;
        this.laborXTareaBL = laborXTareaBL;
        this.tareaXTrabajoBL = tareaXTrabajoBL;
        this.trabajoXContratoBL = trabajoXContratoBL;
        this.parrafoImpresionBL=parrafoImpresionBL;
        this.seccionImpresionBL=seccionImpresionBL;
    }

    public LogicaNegocioBase getClaseNegocio(String nombreEntidad) {
        LogicaNegocioBase entidad = null;
        switch (nombreEntidad) {
            case CONTRATO:
                entidad = this.contratoBL;
                break;
            case DEPARTAMENTO:
                entidad = this.departamentoBL;
                break;
            case EMPRESA:
                entidad = this.empresaBL;
                break;
            case ESTADO:
                entidad = this.estadoBL;
                break;
            case ITEM:
                entidad = this.itemBL;
                break;
            case ITEM_X_NOTIFICACION:
                entidad = this.itemXNotificacionBL;
                break;
            case LABOR:
                entidad = this.laborBL;
                break;
            case LABOR_X_TAREA:
                entidad = this.laborXTareaBL;
                break;
            case MULTIOPCION:
                entidad = this.multiOpcionBL;
                break;
            case MUNICIPIO:
                entidad = this.municipioBL;
                break;
            case NOTIFICACION:
                entidad = this.notificacionBL;
                break;
            case OPCION:
                entidad = this.opcionBL;
                break;
            case PERFIL:
                entidad = this.perfilBL;
                break;
            case PERFIL_X_OPCION:
                entidad = this.perfilXOpcionBL;
                break;
            case SIRIUS:
                entidad = this.talleresBL;
                break;
            case TAREA:
                entidad = this.tareaBL;
                break;
            case TAREAXTRABAJO:
                entidad = this.tareaXTrabajoBL;
                break;
            case TRABAJO:
                entidad = this.trabajoBL;
                break;
            case TRABAJO_X_CONTRATO:
                entidad = this.trabajoXContratoBL;
                break;
            case SECCION_IMPRESION:
                entidad = this.seccionImpresionBL;
                break;
            case PARRAFO_IMPRESION:
                entidad = this.parrafoImpresionBL;
                break;
        }
        return entidad;
    }

    public Class obtenerTipoDto(String nombreEntidad) {
        Class tipo;
        switch (nombreEntidad) {
            case CONTRATO:
                tipo = ListaContrato.class;
                break;
            case DEPARTAMENTO:
                tipo = ListaDepartamento.class;
                break;
            case EMPRESA:
                tipo = ListaEmpresa.class;
                break;
            case ESTADO:
                tipo = ListaEstados.class;
                break;
            case ITEM:
                tipo = ListaItem.class;
                break;
            case ITEM_X_NOTIFICACION:
                tipo = ListaItemXNotificacion.class;
                break;
            case LABOR:
                tipo = ListaLabor.class;
                break;
            case LABOR_X_TAREA:
                tipo = ListaLaborXTarea.class;
                break;
            case MULTIOPCION:
                tipo = ListaMultiOpcion.class;
                break;
            case MUNICIPIO:
                tipo = ListaMunicipio.class;
                break;
            case NOTIFICACION:
                tipo = ListaNotificacion.class;
                break;
            case OPCION:
                tipo = ListaOpcion.class;
                break;
            case PERFIL:
                tipo = ListaPerfil.class;
                break;
            case PERFIL_X_OPCION:
                tipo = ListaPerfilXOpcion.class;
                break;
            case SIRIUS:
                tipo = ListaTalleres.class;
                break;
            case TAREA:
                tipo = ListaTareas.class;
                break;
            case TAREAXTRABAJO:
                tipo = ListaTareaXTrabajo.class;
                break;
            case TRABAJO:
                tipo = ListaTrabajo.class;
                break;
            case TRABAJO_X_CONTRATO:
                tipo = ListaTrabajoXContrato.class;
                break;
            case PARRAFO_IMPRESION:
                tipo = ListaParrafoImpresion.class;
                break;
            case SECCION_IMPRESION:
                tipo = ListaSeccionImpresion.class;
                break;
            case CARGA:
                tipo = ListaCarga.class;
                break;
            default:
                throw new IllegalArgumentException("La entidad no se encuentra implementada para DTO. (" + nombreEntidad + ")");
        }
        return tipo;
    }
}