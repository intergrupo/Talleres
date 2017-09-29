package com.example.dominio.notificacion;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.ArchivoAdjunto;
import com.example.dominio.modelonegocio.FiltroCarga;
import com.example.dominio.modelonegocio.ReporteNotificacion;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public interface ReporteNotificacionRepositorio extends RepositorioBase<ReporteNotificacion> {
    List<ReporteNotificacion> cargar();

    List<ReporteNotificacion> cargarListaReporteNotificacionXLabor(String codigoCorreria, String codigoOrdenTrabajo,
                                                                   String codigoTarea, String codigoLabor);

    List<ReporteNotificacion> cargarReporteNotificacion(String codigoCorreria, String codigoOrdenTrabajo,
                                                        String codigoTarea, String codigoLabor, String codigoItem, String codigoNotificacion);

    ReporteNotificacion consultarReporteNotificacionItemLista(String codigoCorreria, String codigoOrdenTrabajo,
                                                              String codigoTarea, String codigoLabor, String codigoItem,
                                                              String codigoNotificacion, String codigoLista);

    ReporteNotificacion consultarReporteNotificacionItemListaSencilla(String codigoCorreria, String codigoOrdenTrabajo,
                                                                      String codigoTarea, String codigoLabor, String codigoItem,
                                                                      String codigoNotificacion);

    boolean eliminarReporteNotificacionSencillo(ReporteNotificacion reporteNotificacion);

    List<ReporteNotificacion> consultarExistenciaReporteNotificacion(String codigoCorreria,
                                                                     String codigoOT,
                                                                     String codigoTarea,
                                                                     String codigoLabor,
                                                                     String codigoNotificacion);

    List<ReporteNotificacion> consultarExistenciaReporteNotificacion(String codigoCorreria,
                                                                     String codigoOT, String codigoLabor,
                                                                     String codigoNotificacion);


    List<String> cargar(String codigoCorreria);

    List<ReporteNotificacion> cargarReporteNotificacion(String codigoCorreria);

    List<String> cargarNombreArchivos();

    List<String> cargarNombreArchivos(FiltroCarga filtroCarga);

    List<ArchivoAdjunto> cargarArchivosXCodigoCorreria(String codigoCorreria);

    boolean eliminar(String codigoCorreria,String codigoOrdenTrabajo,String codigoTarea);

    List<ReporteNotificacion> cargar(String codigoCorreria, String codigoOrdenTrabajo, String codigoTarea);

    List<ReporteNotificacion> consultarReporteNotificacionSencillo(String codigoCorreria, String codigoOT, String codigoTarea, String codigoItem, String codigoNotificacion);

    List<ReporteNotificacion> cargarReporteNotificacionCompleto(String codigoCorreria);

    List<ReporteNotificacion> cargarFiltroCompleto(String codigoCorreria, String codigoOrdenTrabajo, String codigoTarea);

    List<ReporteNotificacion> cargarFiltroCompleto(String codigoCorreria, String codigoOrdenTrabajo,
                                                   String codigoTarea, String codigoLabor, String codigoNotificacion);

    ReporteNotificacion cargarReporteNotificacionSencillo(String codigoCorreria,
                                                          String codigoOrdenTrabajo,
                                                          String codigoNotificacion,String codigoItem);

    List<ReporteNotificacion> cargar(String codigoCorreria, String codigoOrdenTrabajo);

    List<ReporteNotificacion> cargarReporteNotificacionXNotificacion(String codigoCorreria, String codigoOT,
                                                                     String codigoTarea, String codigoNotificacion);

    List<ReporteNotificacion> cargarReporteNotificacionXItems(String codigoCorreria, String codigoOT, String codigoTarea,
                                                              String codigoNotificacion, String codigoItems);

    List<ArchivoAdjunto> cargarArchivosXFiltro(String codigoCorreria, String codigoOrdenTrabajo, String codigoTarea);

    List<ArchivoAdjunto> cargarArchivosXFiltro(String codigoCorreria, String codigoOrdenTrabajo,
                                               String codigoTarea, String codigoLabor, String codigoNotificacion);
}