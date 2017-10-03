package com.example.santiagolopezgarcia.talleres.integracion.carga;

import com.example.dominio.IEliminarDatosPorRecarga;
import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.bussinesslogic.administracion.TalleresBL;
import com.example.dominio.bussinesslogic.administracion.UsuarioBL;
import com.example.dominio.bussinesslogic.correria.CorreriaBL;
import com.example.dominio.bussinesslogic.labor.LaborXOrdenTrabajoBL;
import com.example.dominio.bussinesslogic.labor.LaborXTareaBL;
import com.example.dominio.modelonegocio.ArchivoAdjunto;
import com.example.dominio.modelonegocio.FiltroCarga;
import com.example.dominio.modelonegocio.Item;
import com.example.dominio.modelonegocio.LaborXOrdenTrabajo;
import com.example.dominio.modelonegocio.LaborXTarea;
import com.example.dominio.modelonegocio.Mensaje;
import com.example.dominio.modelonegocio.NotificacionOrdenTrabajo;
import com.example.dominio.modelonegocio.Tarea;
import com.example.dominio.modelonegocio.TareaXTrabajoOrdenTrabajo;
import com.example.dominio.modelonegocio.Trabajo;
import com.example.dominio.bussinesslogic.notificacion.ItemBL;
import com.example.dominio.bussinesslogic.notificacion.ReporteNotificacionBL;
import com.example.dominio.bussinesslogic.tarea.TareaXOrdenTrabajoBL;
import com.example.dominio.bussinesslogic.trabajo.TrabajoBL;
import com.example.dominio.bussinesslogic.tarea.TareaBL;
import com.example.santiagolopezgarcia.talleres.data.OperadorDatos;
import com.example.santiagolopezgarcia.talleres.helpers.Constantes;
import com.example.santiagolopezgarcia.talleres.integracion.ComunicacionCarga;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.correria.Correria;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.correria.ListaCorrerias;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.notificacion.ListaReporteNotificacion;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.notificacion.ReporteNotificacion;
import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDtoCorreria;
import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDtoOtTarea;
import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDtoOtTrabajoTarea;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.Carga;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.ListaCarga;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.ordentrabajo.ListaNotificacionOrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.ordentrabajo.ListaOrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.ordentrabajo.ListaTareaXOrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.ordentrabajo.OrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.ordentrabajo.TareaXOrdenTrabajo;
import com.example.utilidades.FileManager;
import com.example.utilidades.helpers.DateHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class CargaDiaria extends CargaBase {

    private CorreriaBL correriaBL;
    private TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL;
    private TareaBL tareaBL;
    private TrabajoBL trabajoBL;
    private ItemBL itemBL;
    private LaborXTareaBL laborXTareaBL;
    private LaborXOrdenTrabajoBL laborXOrdenTrabajoBL;
    private List<NotificacionOrdenTrabajo> listaNotificacionOT = new ArrayList<>();
    private DependenciaCargaDiaria dependenciaCargaDiaria;
    private SolicitudRemplazoCorreria solicitudRemplazoCorreria;
    private SolicitudRemplazoTarea solicitudRemplazoTarea;
    private ListaCorrerias listaCorrerias;
    private ListaOrdenTrabajo listaOTs;
    private List<String> codigoCorreriasAConfirmar;
    private List<String> codigosOTAConfirmar;

    @Inject
    public CargaDiaria(TalleresBL talleresBL, CorreriaBL correriaBL
            , DependenciaCargaDiaria dependenciaCargaDiaria
            , TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL
            , ItemBL itemBL
            , TrabajoBL trabajoBL
            , TareaBL tareaBL
            , LaborXTareaBL laborXTareaBL
            , LaborXOrdenTrabajoBL laborXOrdenTrabajoBL) {
        super(talleresBL);
        this.correriaBL = correriaBL;
        this.tareaXOrdenTrabajoBL = tareaXOrdenTrabajoBL;
        this.itemBL = itemBL;
        this.dependenciaCargaDiaria = dependenciaCargaDiaria;
        this.trabajoBL = trabajoBL;
        this.tareaBL = tareaBL;
        this.laborXTareaBL = laborXTareaBL;
        this.laborXOrdenTrabajoBL = laborXOrdenTrabajoBL;

        this.solicitudRemplazoCorreria = new SolicitudRemplazoCorreria();
        this.solicitudRemplazoTarea = new SolicitudRemplazoTarea();
        this.codigoCorreriasAConfirmar = new ArrayList<>();
        this.codigosOTAConfirmar = new ArrayList<>();
    }

    public List<String> getCodigoCorreriasAConfirmar() {
        for (Correria correria : listaCorrerias.Sirius_Correria) {
            if (!solicitudRemplazoCorreria.getCodigosCorreriaAOmitirDeRemplazo().contains(correria.CodigoCorreria)) {
                codigoCorreriasAConfirmar.add(correria.CodigoCorreria);
            }
        }
        return codigoCorreriasAConfirmar;
    }

    public ListaCorrerias getListaCorrerias() {
        return listaCorrerias;
    }

    public List<String> getCodigosOTsAConfirmar() {
        asignarListaOTsIntegradas();

        if (listaOTs != null) {
            for (OrdenTrabajo ordenTrabajo : listaOTs.Sirius_OrdenTrabajo) {
                codigosOTAConfirmar.add(ordenTrabajo.CodigoOrdenTrabajo);
            }
        }
        return codigosOTAConfirmar;
    }

    public List<NotificacionOrdenTrabajo> getListaNotificacionOT() {
        return listaNotificacionOT;
    }

    @Override
    protected Carga obtenerDtoCarga() throws Exception {
        ListaCarga listaCarga = this.obtenerListaDto(ListaCarga.class, DependenciaCargaDiaria.CARGA, false);
        Carga carga = null;
        if (listaCarga != null) {
            carga = listaCarga.Carga.get(0);
        }
        return carga;
    }

    @Override
    protected int calcularOrdenEntidad(String nombre) {
        int posicion = -1;
        switch (nombre.toUpperCase()) {
            case DependenciaCargaDiaria.CORRERIA:
                posicion = 0;
                break;
            case DependenciaCargaDiaria.ORDENTRABAJO:
                posicion = 1;
                break;
            case DependenciaCargaDiaria.TRABAJO_X_ORDENTRABAJO:
                posicion = 4;
                break;
            case DependenciaCargaDiaria.TAREA_X_ORDENTRABAJO:
                posicion = 5;
                break;
            case DependenciaCargaDiaria.LABOR_X_ORDENTRABAJO:
                posicion = 7;
                break;
            case DependenciaCargaDiaria.REPORTE_NOTIFICACION:
                posicion = 9;
                break;
            case DependenciaCargaDiaria.USUARIO:
                posicion = 12;
                break;
            case DependenciaCargaDiaria.SIRIUS:
                posicion = 13;
                break;
            case DependenciaCargaDiaria.PROGRAMACIONCORRERIA:
                posicion = 21;
                break;
            case DependenciaCargaDiaria.NOTIFICACION_ORDENTRABAJO:
                posicion = 25;
                break;
            case DependenciaCargaDiaria.CARGA:
                posicion = 27;
                break;
        }
        return posicion;
    }

    public void asignarListaOTsIntegradas() {
        try {
            listaOTs = this.obtenerListaDto(ListaOrdenTrabajo.class, DependenciaCargaDiaria.ORDENTRABAJO, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validarCorrerias() throws Exception {

        listaCorrerias = this.obtenerListaDto(ListaCorrerias.class, DependenciaCargaDiaria.CORRERIA, true);
        for (int i = 0; i < listaCorrerias.Sirius_Correria.size(); i++) {
            Correria correria = listaCorrerias.Sirius_Correria.get(i);
            if (this.correriaBL.existe(correria.CodigoCorreria)) {
                this.solicitudRemplazoCorreria.adicionarCorreria(correria.CodigoCorreria);
            }
        }
        return !this.solicitudRemplazoCorreria.esNecesarioSolicitarRemplazo();
    }

    public boolean confirmarRemplazoCorreria(int indice) {
        boolean hayMasCorrerias = false;
        if (this.solicitudRemplazoCorreria != null && this.solicitudRemplazoCorreria.cantidadCorrerias() > indice) {
            this.estadoComunicacionCarga.confirmarRemplazoCorreria(
                    this.solicitudRemplazoCorreria.getMensajeConfirmacion(indice), indice);
            hayMasCorrerias = true;
        }
        return hayMasCorrerias;
    }

    public void respuestaRemplazoCorreria(int indiceCorreria, boolean remplazar) {
        this.solicitudRemplazoCorreria.establecerRespuesta(indiceCorreria, remplazar);
    }

    public boolean validarTareasXOrdenTrabajo() throws Exception {
        ListaTareaXOrdenTrabajo listaTarea = this.obtenerListaDto(ListaTareaXOrdenTrabajo.class
                , DependenciaCargaDiaria.TAREA_X_ORDENTRABAJO, false);

        if (listaTarea != null && listaTarea.getLongitudLista() > 0) {
            listaTarea.eliminarItemPorCorreria(this.solicitudRemplazoCorreria.getCodigosCorreriaAOmitirDeRemplazo());
        }

        for (int i = 0; i < listaTarea.getLongitudLista(); i++) {
            TareaXOrdenTrabajo tareaDto = listaTarea.Sirius_tareaxordentrabajo.get(i);
            TareaXTrabajoOrdenTrabajo tareaBaseDatos =
                    this.tareaXOrdenTrabajoBL.cargarTareaXTrabajoOrdenTrabajo(tareaDto.CodigoCorreria
                            , tareaDto.CodigoOrdenTrabajo, tareaDto.CodigoTarea, tareaDto.CodigoTrabajo);
            if (this.existeDatosTareaBL(tareaBaseDatos)) {
                if ((tareaBaseDatos.getFechaUltimaTarea() == null && tareaDto.getFechaUltimaTarea() != null)
                        || (tareaBaseDatos.getFechaUltimaTarea() != null && tareaDto.getFechaUltimaTarea() == null)) {

                    String fechaUltimaTareaBaseDatos = (tareaBaseDatos.getFechaUltimaTarea() != null) ? DateHelper.convertirDateAString(
                            tareaBaseDatos.getFechaUltimaTarea(), DateHelper.TipoFormato.ddMMMyyyy) : "Nueva";
                    String fechaUltimaTareaDto = (tareaDto.getFechaUltimaTarea() != null) ? DateHelper.convertirDateAString(
                            tareaDto.getFechaUltimaTarea(), DateHelper.TipoFormato.ddMMMyyyy) : "Nueva";


                    String nombreTrabajo = this.getNombreTrabajo(tareaDto.CodigoTrabajo);
                    String nombreTarea = this.getNombreTare(tareaDto.CodigoTarea);

                    this.solicitudRemplazoTarea.adicionarTarea(tareaDto.CodigoCorreria
                            , tareaDto.CodigoOrdenTrabajo, tareaDto.CodigoTrabajo, nombreTrabajo
                            , tareaDto.CodigoTarea, nombreTarea, fechaUltimaTareaBaseDatos, fechaUltimaTareaDto);

                } else if ((tareaBaseDatos.getFechaUltimaTarea() == null && tareaDto.getFechaUltimaTarea() == null)
                        || (tareaBaseDatos.getFechaUltimaTarea() == tareaDto.getFechaUltimaTarea())) {

                    this.solicitudRemplazoTarea.adicionarTareaAOmitirDeRemplazo(tareaDto.CodigoCorreria
                            , tareaDto.CodigoOrdenTrabajo, tareaDto.CodigoTrabajo, tareaDto.CodigoTarea);

                } else if (tareaBaseDatos.getFechaUltimaTarea() != null && tareaDto.getFechaUltimaTarea() != null) {
                    if (tareaBaseDatos.getFechaUltimaTarea() != tareaDto.getFechaUltimaTarea()) {

                        String fechaUltimaTareaBaseDatos = DateHelper.convertirDateAString(
                                tareaBaseDatos.getFechaUltimaTarea(), DateHelper.TipoFormato.ddMMMyyyy);
                        String fechaUltimaTareaDto = DateHelper.convertirDateAString(
                                tareaDto.getFechaUltimaTarea(), DateHelper.TipoFormato.ddMMMyyyy);

                        String nombreTrabajo = this.getNombreTrabajo(tareaDto.CodigoTrabajo);
                        String nombreTarea = this.getNombreTare(tareaDto.CodigoTarea);

                        this.solicitudRemplazoTarea.adicionarTarea(tareaDto.CodigoCorreria
                                , tareaDto.CodigoOrdenTrabajo, nombreTrabajo, tareaDto.CodigoTrabajo
                                , tareaDto.CodigoTarea, nombreTarea, fechaUltimaTareaBaseDatos, fechaUltimaTareaDto);
                    }
                }
            }
        }

        return !this.solicitudRemplazoTarea.esNecesarioSolicitarRemplazo();
    }

    private boolean existeDatosTareaBL(TareaXTrabajoOrdenTrabajo tareaXOt) {
        return (tareaXOt.getCodigoCorreria() != null && tareaXOt.getCodigoCorreria() != "");
    }

    public void respuestaRemplazoTarea(int indiceTarea, boolean remplazar) {
        this.solicitudRemplazoTarea.establecerRespuesta(indiceTarea, remplazar);
    }

    public boolean confirmarRemplazoTarea(int indiceTarea) {
        boolean hayMasCorrerias = false;
        if (this.solicitudRemplazoTarea != null && this.solicitudRemplazoTarea.cantidadTareas() > indiceTarea) {
            this.estadoComunicacionCarga.confirmarRemplazoTarea(
                    this.solicitudRemplazoTarea.getMensajeConfirmacion(indiceTarea), indiceTarea);
            hayMasCorrerias = true;
        }
        return hayMasCorrerias;
    }


    public List<ArchivoAdjunto> identificarNombresArchivosAdjuntos() throws Exception {
        List<String> codigoItemsParaCargarAdjuntos = this.obtenerCodigoItemsParaCargarAdjuntos();
        List<ArchivoAdjunto> nombreArchivosAdjuntos = new ArrayList<>();
        if (codigoItemsParaCargarAdjuntos != null && codigoItemsParaCargarAdjuntos.size() > 0) {
            ListaReporteNotificacion listaReporteNotificacion = this.obtenerListaDto(ListaReporteNotificacion.class
                    , DependenciaCargaDiaria.REPORTE_NOTIFICACION, false);
            if (listaReporteNotificacion != null && listaReporteNotificacion.getLongitudLista() > 0) {

                listaReporteNotificacion.eliminarItemPorCorreria(this.solicitudRemplazoCorreria.getCodigosCorreriaAOmitirDeRemplazo());
                listaReporteNotificacion.eliminarItemPorOtTarea(this.solicitudRemplazoTarea.getCodigoOtTareaAOmitirDeRemplazo());

                for (ReporteNotificacion reporteNotificacion : listaReporteNotificacion.Sirius_ReporteNotificacion) {
                    if (codigoItemsParaCargarAdjuntos.contains(reporteNotificacion.CodigoItem)) {
                        if (!reporteNotificacion.Descripcion.isEmpty()) {
                            Item item = itemBL.cargarItem(reporteNotificacion.CodigoItem);
                            ArchivoAdjunto archivoAdjunto = new ArchivoAdjunto();
                            archivoAdjunto.setNombreArchivo(reporteNotificacion.Descripcion);
                            archivoAdjunto.setRutaArchivo(item.getParametros());
                            nombreArchivosAdjuntos.add(archivoAdjunto);
                        }
                    }
                }
            }
        }
        return nombreArchivosAdjuntos;
    }


    List<String> obtenerCodigoItemsParaCargarAdjuntos() {
        //en este paso ya se hizo la carga de maestros, por eso vamos a BL por los items
        return this.itemBL.cargarCodigosItemParaCargarAdjuntos();
    }

    String getNombreTrabajo(String codigoTrabajo) {
        String nombreTrabajo = codigoTrabajo;
        Trabajo trabajo = this.trabajoBL.cargarTrabajo(codigoTrabajo);
        if (!trabajo.getNombre().isEmpty()) {
            nombreTrabajo = trabajo.getNombre();
        }
        return nombreTrabajo;
    }

    String getNombreTare(String codigoTarea) {
        String nombreTarea = codigoTarea;
        Tarea tarea = this.tareaBL.cargarTareaxCodigo(codigoTarea);
        if (!tarea.getNombre().isEmpty()) {
            nombreTarea = tarea.getNombre();
        }
        return nombreTarea;
    }

    public void integrar(ComunicacionCarga.TipoComunicacion tipoComunicacion) throws Exception {
        try {
            OperadorDatos.iniciarTransaccionGlobal();
            List<String> codigosCorreriaAOmitir = this.solicitudRemplazoCorreria.getCodigosCorreriaAOmitirDeRemplazo();
            List<String> codigosOtTrabajoTareaAOmitir = this.solicitudRemplazoTarea.getCodigoOtTrabajoTareaAOmitirDeRemplazo();
            List<String> codigosOtTareaAOmitir = this.solicitudRemplazoTarea.getCodigoOtTareaAOmitirDeRemplazo();

            this.eliminarDatosEntidadesRecarga();

            for (String rutaArchivo : this.listaArchivosOrdenados.values()) {
                File archivo = new File(rutaArchivo);
                String nombreArchivo = archivo.getName();
                this.estadoComunicacionCarga.informarProgreso("Integrando " + nombreArchivo.replace(".XML", ""));
                Class tipo = null;
                if (!integrarSirius(tipoComunicacion, nombreArchivo)) {
                    tipo = this.dependenciaCargaDiaria.obtenerTipoDto(nombreArchivo);
                }
                if (tipo != null) {
                    BaseListaDto listaDto = this.obtenerListaDto(tipo, nombreArchivo, false);

                    if (listaDto != null && listaDto.getLongitudLista() > 0 && !(listaDto instanceof ListaCarga)) {
                        //if (listaDto != null && !(listaDto instanceof ListaCarga)) {
                        if (listaDto instanceof BaseListaDtoCorreria) {
                            ((BaseListaDtoCorreria) listaDto).eliminarItemPorCorreria(codigosCorreriaAOmitir);
                        }
                        if (listaDto instanceof BaseListaDtoOtTrabajoTarea) {
                            ((BaseListaDtoOtTrabajoTarea) listaDto).eliminarItemPorOtTrabajoTarea(codigosOtTrabajoTareaAOmitir);
                        }
                        if (listaDto instanceof BaseListaDtoOtTarea) {
                            ((BaseListaDtoOtTarea) listaDto).eliminarItemPorOtTarea(codigosOtTareaAOmitir);
                        }

                        if (listaDto.getLongitudLista() > 0) {
                            List listaNegocio = listaDto.convertirListaDtoAListaDominio();
                            if (listaNegocio != null) {
                                LogicaNegocioBase logicaNegocio = this.dependenciaCargaDiaria.getClaseNegocio(nombreArchivo);
                                if (logicaNegocio != null) {

                                    logicaNegocio.procesar(listaNegocio, listaDto.getOperacion());

                                    if (listaDto instanceof ListaTareaXOrdenTrabajo) {
                                        this.crearLaborXOrdenTrabajo((ListaTareaXOrdenTrabajo) listaDto);
                                    }

                                    if (listaDto instanceof ListaNotificacionOrdenTrabajo) {
                                        listaNotificacionOT = listaNegocio;
                                    }

                                    if (logicaNegocio instanceof UsuarioBL) {
                                        ((UsuarioBL) logicaNegocio).modificarDespuesDeCarga();
                                    }
                                }
                            }
                        }
                    } /*else {
                        if(listaDto instanceof ListaLecturas) {
                            Exception exception = new Exception("No se cargaron datos de " + nombreArchivo.replace(".XML", ""));
                            OperadorDatos.terminarTransaccionGlobal();
                            throw exception;
                        }
                    }*/
                }
            }
            //this.eliminarAdjuntosTareasARecargar();
            OperadorDatos.establecerTransaccionGlobalSatisfactoria();
            this.informarResultadoCargaExitosa();
        } catch (Exception exp) {
            throw exp;
        } finally {
            OperadorDatos.terminarTransaccionGlobal();
        }
    }


    private boolean integrarSirius(ComunicacionCarga.TipoComunicacion tipoComunicacion,
                                   String nombreArchivo) {

        return tipoComunicacion.equals(ComunicacionCarga.TipoComunicacion.ReciboDirecto)
                && nombreArchivo.equals(DependenciaCargaDiaria.SIRIUS)
                || tipoComunicacion.equals(ComunicacionCarga.TipoComunicacion.ReciboWeb)
                && nombreArchivo.equals(DependenciaCargaDiaria.SIRIUS);
    }

    private void crearLaborXOrdenTrabajo(ListaTareaXOrdenTrabajo listaTareaXOrdenTrabajo) {
        for (TareaXOrdenTrabajo tareaXOrdenTrabajo : listaTareaXOrdenTrabajo.Sirius_tareaxordentrabajo) {
            if (tareaXOrdenTrabajo.CodigoEstado.equals(Tarea.EstadoTarea.CREARLABORES.getCodigo())) {
                Trabajo trabajo = trabajoBL.cargarTrabajo(tareaXOrdenTrabajo.CodigoTrabajo);
                List<LaborXTarea> laboresXTarea =
                        laborXTareaBL.cargarListaLaborxTareaXCodigoTarea(tareaXOrdenTrabajo.CodigoTarea);
                for (LaborXTarea laborXTarea : laboresXTarea) {
                    LaborXOrdenTrabajo laborXOrdenTrabajo = new LaborXOrdenTrabajo();
                    laborXOrdenTrabajo.setCodigoCorreria(tareaXOrdenTrabajo.CodigoCorreria);
                    laborXOrdenTrabajo.setCodigoOrdenTrabajo(tareaXOrdenTrabajo.CodigoOrdenTrabajo);
                    laborXOrdenTrabajo.setTrabajo(trabajo);
                    laborXOrdenTrabajo.setTarea(laborXTarea.getTarea());
                    laborXOrdenTrabajo.setLabor(laborXTarea.getLabor());
                    laborXOrdenTrabajo.setEstadoLaborXOrdenTrabajo(LaborXOrdenTrabajo.EstadoLaborXOrdenTrabajo.PENDIENTE);
                    laborXOrdenTrabajo.setParametros(laborXTarea.getParametros());
                    List<LaborXOrdenTrabajo> laboresXOrdenTrabajo = new ArrayList<>();
                    laboresXOrdenTrabajo.add(laborXOrdenTrabajo);
                    laborXOrdenTrabajoBL.procesar(laboresXOrdenTrabajo, "R");
                }
                this.cambiarEstadoCrearLaboresAPendiente(tareaXOrdenTrabajo);
            }
        }
    }

    private void cambiarEstadoCrearLaboresAPendiente(TareaXOrdenTrabajo tareaXOrdenTrabajo) {
        TareaXTrabajoOrdenTrabajo tareaXOrdenTrabajoNegocio = tareaXOrdenTrabajoBL.cargarTareaXTrabajoOrdenTrabajo(tareaXOrdenTrabajo.CodigoCorreria
                , tareaXOrdenTrabajo.CodigoOrdenTrabajo, tareaXOrdenTrabajo.CodigoTarea, tareaXOrdenTrabajo.CodigoTrabajo);

        if (tareaXOrdenTrabajoNegocio.getEstadoTarea() == Tarea.EstadoTarea.CREARLABORES) {
            Tarea tareaNegocio = tareaBL.cargarTareaxCodigo(tareaXOrdenTrabajo.CodigoTarea);
            if (tareaNegocio.getRutina().equals(Tarea.RutinaTarea.TAREALECTURA.getCodigo())) {
                tareaXOrdenTrabajoNegocio.setEstadoTarea(Tarea.EstadoTarea.SINLEER);
            } else if (tareaNegocio.getRutina().equals(Tarea.RutinaTarea.TAREAREVISIONES.getCodigo())) {
                tareaXOrdenTrabajoNegocio.setEstadoTarea(Tarea.EstadoTarea.PENDIENTE);
            }
            tareaXOrdenTrabajoBL.actualizarTareaXOrdenTrabajo(tareaXOrdenTrabajoNegocio);
        }
    }

    private void eliminarDatosEntidadesRecarga() throws Exception {
        List<FiltroCarga> filtrosTareasEliminarDatos = this.solicitudRemplazoTarea.getCodigosOtTrabajoTareaARemplazar();
        List<String> entidadesNegocioEliminarDatosRecarga = this.dependenciaCargaDiaria.obtenerNombresEntidadesAEliminarPorRecarga();
        for (FiltroCarga filtroCarga : filtrosTareasEliminarDatos) {
            for (String nombreEntidad : entidadesNegocioEliminarDatosRecarga) {
                IEliminarDatosPorRecarga entidadNegocioEliminarDatosXRecarga =
                        this.dependenciaCargaDiaria.obtenerEntidadAEliminarPorRecarga(nombreEntidad);
                if (entidadNegocioEliminarDatosXRecarga != null) {
                    entidadNegocioEliminarDatosXRecarga.eliminarDatosPorRecarga(filtroCarga);
                } else {
                    throw new Exception("No se obtuvo la entidad de negocio para " + nombreEntidad);
                }
            }
        }
    }

    public void eliminarAdjuntosTareasARecargar() {
        String rutaCarpeta = Constantes.traerRutaAdjuntos();
        List<FiltroCarga> tareasARecargar = this.solicitudRemplazoTarea.getCodigosOtTrabajoTareaARemplazar();
        if (tareasARecargar != null && tareasARecargar.size() > 0) {
            ReporteNotificacionBL reporteNotificacionBL = (ReporteNotificacionBL)
                    this.dependenciaCargaDiaria.obtenerEntidadAEliminarPorRecarga
                            (ReporteNotificacionBL.class.getSimpleName());

            for (FiltroCarga filtroCarga : tareasARecargar) {
                List<String> archivos = reporteNotificacionBL.cargarNombreArchivos(filtroCarga);
                if (archivos != null && archivos.size() > 0) {
                    FileManager.deleteFiles(rutaCarpeta, archivos);
                }
            }
        }
    }

    public void eliminarTodosLosAdjuntos() {
        String rutaCarpeta = Constantes.traerRutaAdjuntos();
        FileManager.deleteFolderContent(rutaCarpeta);
    }

    private void informarResultadoCargaExitosa() throws IOException {
        Mensaje mensaje = new Mensaje();
        mensaje.setRespuesta(true);
        mensaje.setMensaje("PROCESO TERMINADO");
        this.estadoComunicacionCarga.informarResultado(mensaje, null, null);
    }

    public Carga obtenerCargaDTO() {
        try {
            return obtenerDtoCarga();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}