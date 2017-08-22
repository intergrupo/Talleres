package com.example.dominio.notificacion;

import com.example.dominio.IBaseDescarga;
import com.example.dominio.IEliminarDatosPorRecarga;
import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.ArchivoAdjunto;
import com.example.dominio.modelonegocio.FiltroCarga;
import com.example.dominio.modelonegocio.Item;
import com.example.dominio.modelonegocio.ItemXNotificacion;
import com.example.dominio.modelonegocio.LaborXOrdenTrabajo;
import com.example.dominio.modelonegocio.ListaOpcion;
import com.example.dominio.modelonegocio.Notificacion;
import com.example.dominio.modelonegocio.Opcion;
import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;
import com.example.dominio.modelonegocio.ParametrosOpcionNotificacion;
import com.example.dominio.modelonegocio.ReporteNotificacion;
import com.example.dominio.modelonegocio.TareaXOrdenTrabajo;
import com.example.dominio.modelonegocio.Usuario;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class ReporteNotificacionBL implements LogicaNegocioBase<ReporteNotificacion>,
        IBaseDescarga<ReporteNotificacion>, IEliminarDatosPorRecarga {

    private ReporteNotificacionRepositorio reporteNotificacionRepositorio;

    public ReporteNotificacionBL(ReporteNotificacionRepositorio reporteNotificacionRepositorio) {
        this.reporteNotificacionRepositorio = reporteNotificacionRepositorio;
    }

    public boolean guardar(List<ReporteNotificacion> listaReporteNotificacion) {
        try {
            return reporteNotificacionRepositorio.guardar(listaReporteNotificacion);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ReporteNotificacion generarReporteNotificacion(String codigoCorreria,
                                                          String codigoOT,
                                                          String codigoTarea, String codigoLabor,
                                                          ItemXNotificacion itemXNotificacion,
                                                          String descripcion) {
        ReporteNotificacion reporteNotificacion = new ReporteNotificacion();
        reporteNotificacion.setCodigoCorreria(codigoCorreria);
        reporteNotificacion.setCodigoOrdenTrabajo(codigoOT);
        reporteNotificacion.setCodigoTarea(codigoTarea);
        reporteNotificacion.setCodigoLabor(codigoLabor);
        reporteNotificacion.setCodigoItem(itemXNotificacion.getCodigoItem());
        reporteNotificacion.setCodigoNotificacion(itemXNotificacion.getCodigoNotificacion());
        reporteNotificacion.setDescripcion(descripcion);
        reporteNotificacion.setOrden(String.valueOf(itemXNotificacion.getOrden()));
        reporteNotificacion.setCodigoLista("00");
        reporteNotificacion.setDescarga(itemXNotificacion.isDescarga());
        return reporteNotificacion;
    }

    public boolean guardar(ReporteNotificacion reporteNotificacion) {
        try {
            return reporteNotificacionRepositorio.guardar(reporteNotificacion);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean tieneRegistros() {
        return reporteNotificacionRepositorio.tieneRegistros();
    }

    public List<ReporteNotificacion> cargar() {
        return reporteNotificacionRepositorio.cargar();
    }

    public List<ReporteNotificacion> cargarListaReporteNotificacionXLabor(LaborXOrdenTrabajo laborXOrdenTrabajo) {
        return reporteNotificacionRepositorio.cargarListaReporteNotificacionXLabor(laborXOrdenTrabajo.getCodigoCorreria(),
                laborXOrdenTrabajo.getCodigoOrdenTrabajo(), laborXOrdenTrabajo.getTarea().getCodigoTarea(),
                laborXOrdenTrabajo.getLabor().getCodigoLabor());
    }

    public List<ReporteNotificacion> cargarListaReporteNotificacionXLabor(String codigoCorreria,
                                                                          String codigoOT, String codigoTarea,
                                                                          String codigoLabor) {
        return reporteNotificacionRepositorio.cargarListaReporteNotificacionXLabor(
                codigoCorreria, codigoOT, codigoTarea, codigoLabor);
    }

    public List<ReporteNotificacion> cargarReporteNotificacion(String codigoCorreria, String codigoOrdenTrabajo, String codigoTarea,
                                                               String codigoLabor, String codigoItem, String codigoNotificacion) {
        return reporteNotificacionRepositorio.cargarReporteNotificacion(codigoCorreria, codigoOrdenTrabajo,
                codigoTarea, codigoLabor, codigoItem, codigoNotificacion);
    }

    public List<ArchivoAdjunto> cargarArchivosXCodigoCorreria(String codigoCorreria) {
        return reporteNotificacionRepositorio.cargarArchivosXCodigoCorreria(codigoCorreria);
    }

    public List<ArchivoAdjunto> cargarArchivosXFiltro(OrdenTrabajoBusqueda filtro, Usuario usuario) {
        List<ArchivoAdjunto> lista = new ArrayList<>();

        if (filtro.getListaTareaXOrdenTrabajo().size() > 0) {
            for (TareaXOrdenTrabajo tareaXOt : filtro.getListaTareaXOrdenTrabajo()) {
                lista.addAll(reporteNotificacionRepositorio.cargarArchivosXFiltro(tareaXOt.getCodigoCorreria(),
                        tareaXOt.getCodigoOrdenTrabajo(), tareaXOt.getTarea().getCodigoTarea()));

            }
        }

        if (filtro.getListaOrdenTrabajo() != null && filtro.getListaOrdenTrabajo().size() > 0) {
            for (OrdenTrabajo ot : filtro.getListaOrdenTrabajo()) {
                lista.addAll(archivosMenu(ot, usuario));
            }
        }
        return lista;
    }

    public ReporteNotificacion consultarReporteNotificacionItemLista(ReporteNotificacion reporteNotificacion) {
        return reporteNotificacionRepositorio.consultarReporteNotificacionItemLista(reporteNotificacion.getCodigoCorreria(), reporteNotificacion.getCodigoOrdenTrabajo(),
                reporteNotificacion.getCodigoTarea(), reporteNotificacion.getCodigoLabor(), reporteNotificacion.getCodigoItem(),
                reporteNotificacion.getCodigoNotificacion(), reporteNotificacion.getCodigoLista());
    }

    public ReporteNotificacion consultarReporteNotificacionItemSencillo(ReporteNotificacion reporteNotificacion) {
        return reporteNotificacionRepositorio.consultarReporteNotificacionItemListaSencilla(reporteNotificacion.getCodigoCorreria(), reporteNotificacion.getCodigoOrdenTrabajo(),
                reporteNotificacion.getCodigoTarea(), reporteNotificacion.getCodigoLabor(), reporteNotificacion.getCodigoItem(),
                reporteNotificacion.getCodigoNotificacion());
    }

    public List<ReporteNotificacion> consultarExistenciaReporteNotificacion(String codigoCorreria,
                                                                            String codigoOT,
                                                                            String codigoTarea,
                                                                            String codigoLabor, String codigoNotificacion) {
        return reporteNotificacionRepositorio.consultarExistenciaReporteNotificacion(codigoCorreria, codigoOT, codigoTarea,
                codigoLabor, codigoNotificacion);
    }

    public ReporteNotificacion cargarReporteNotificacionSencillo(String codigoCorreria,
                                                                 String codigoOrdenTrabajo,
                                                                 String codigoNotificacion, String codigoItem) {
        return reporteNotificacionRepositorio.cargarReporteNotificacionSencillo(codigoCorreria, codigoOrdenTrabajo, codigoNotificacion, codigoItem);
    }


    public List<ReporteNotificacion> consultarReporteNotificacionSencillo(String codigoCorreria, String codigoOT, String codigoTarea, String codigoItem, String codigoNotificacion) {
        return reporteNotificacionRepositorio.consultarReporteNotificacionSencillo(codigoCorreria,
                codigoOT, codigoTarea, codigoItem, codigoNotificacion);
    }

    public boolean eliminarReporteNotificacion(ReporteNotificacion reporteNotificacion) {
        return reporteNotificacionRepositorio.eliminar(reporteNotificacion);
    }

    public boolean eliminarReporteNotificacionSencillo(ReporteNotificacion reporteNotificacion) {
        return reporteNotificacionRepositorio.eliminarReporteNotificacionSencillo(reporteNotificacion);
    }

    public boolean modificarReporteNotificacion(ReporteNotificacion reporteNotificacion) {
        return reporteNotificacionRepositorio.actualizar(reporteNotificacion);
    }

    public ReporteNotificacion consultarReporteNotificacion(String codigoCorreria, String codigoOT,
                                                            String codigoTarea, String codigoLabor, Notificacion notificacion, Item item) {
        ReporteNotificacion reporteNotificacion = new ReporteNotificacion();
        reporteNotificacion.setCodigoCorreria(codigoCorreria);
        reporteNotificacion.setCodigoOrdenTrabajo(codigoOT);
        reporteNotificacion.setCodigoTarea(codigoTarea);
        reporteNotificacion.setCodigoLabor(codigoLabor);
        reporteNotificacion.setCodigoNotificacion(notificacion.getCodigoNotificacion());
        reporteNotificacion.setCodigoItem(item.getCodigoItem());
        return consultarReporteNotificacionItemSencillo(reporteNotificacion);
    }

    public boolean borrarArchivosAdjuntos(String codigoCorreria, String ruta) {
        boolean elimino = true;

        try {
            List<String> listaReporteNotificacion = reporteNotificacionRepositorio.cargar(codigoCorreria);
            File file;

            for (int i = 0; i < listaReporteNotificacion.size(); i++) {
                file = new File(ruta, listaReporteNotificacion.get(i));
                if (file.exists()) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            elimino = false;
        }
        return elimino;
    }

    public List<ReporteNotificacion> cargarReporteNotificacion(String codigoCorreria) {
        return reporteNotificacionRepositorio.cargarReporteNotificacion(codigoCorreria);
    }

    public List<ReporteNotificacion> cargarReporteNotificacionXNotificacion(String codigoNotificacion) {
        return reporteNotificacionRepositorio.cargarReporteNotificacionXNotificacion(codigoNotificacion);
    }

    public List<String> cargarNombreArchivos() {
        return reporteNotificacionRepositorio.cargarNombreArchivos();
    }

    public List<String> cargarNombreArchivos(FiltroCarga filtroCarga) {
        return reporteNotificacionRepositorio.cargarNombreArchivos(filtroCarga);
    }

    @Override
    public boolean procesar(List<ReporteNotificacion> listaDatos, String operacion) {
        boolean respuesta = false;
        for (ReporteNotificacion reporteNotificacion : listaDatos) {
            switch (operacion) {
                case "A":
                    if (!consultarReporteNotificacionItemLista(reporteNotificacion).esClaveLlena()) {
                        respuesta = guardar(reporteNotificacion);
                    }
                    break;
                case "U":
                    respuesta = modificarReporteNotificacion(reporteNotificacion);
                    break;
                case "D":
                    respuesta = eliminarReporteNotificacion(reporteNotificacion);
                    break;
                case "R":
                    if (consultarReporteNotificacionItemLista(reporteNotificacion).esClaveLlena()) {
                        respuesta = modificarReporteNotificacion(reporteNotificacion);
                    } else {
                        respuesta = guardar(reporteNotificacion);
                    }
                    break;
                default:
                    if (!consultarReporteNotificacionItemLista(reporteNotificacion).esClaveLlena()) {
                        respuesta = guardar(reporteNotificacion);
                    }
                    break;
            }
        }
        return respuesta;
    }

    @Override
    public List<ReporteNotificacion> cargarXCorreria(String codigoCorreria) {
        return cargarReporteNotificacion(codigoCorreria);
    }


    public List<ReporteNotificacion> cargarXCorreriaCompleto(String codigoCorreria) {
        return reporteNotificacionRepositorio.cargarReporteNotificacionCompleto(codigoCorreria);
    }


    @Override
    public List<ReporteNotificacion> cargarXFiltro(OrdenTrabajoBusqueda filtro) {
        List<ReporteNotificacion> lista = new ArrayList<>();
        if (filtro.getListaTareaXOrdenTrabajo().size() > 0) {
            for (TareaXOrdenTrabajo tareaXot : filtro.getListaTareaXOrdenTrabajo()) {
                lista.addAll(reporteNotificacionRepositorio.cargar(tareaXot.getCodigoCorreria(), tareaXot.getCodigoOrdenTrabajo(), tareaXot.getTarea().getCodigoTarea()));
            }
        }

        if (filtro.getListaOrdenTrabajo().size() > 0) {
            for (OrdenTrabajo ot : filtro.getListaOrdenTrabajo()) {
                lista.addAll(reporteNotificacionRepositorio.cargar(ot.getCodigoCorreria(), ot.getCodigoOrdenTrabajo()));
            }
        }
        return lista;
    }

    @Override
    public boolean eliminarDatosPorRecarga(FiltroCarga filtroCarga) {
        return reporteNotificacionRepositorio.eliminar(filtroCarga.getCodigoCorreria(),
                filtroCarga.getCodigoOrdenTrabajo(), filtroCarga.getCodigoTarea());
    }

    public List<ReporteNotificacion> cargarXFiltroCompleto(OrdenTrabajoBusqueda filtro, Usuario usuario) {
        List<ReporteNotificacion> lista = new ArrayList<>();
        if (filtro.getListaTareaXOrdenTrabajo() != null && filtro.getListaTareaXOrdenTrabajo().size() > 0) {
            for (TareaXOrdenTrabajo tareaXot : filtro.getListaTareaXOrdenTrabajo()) {
                lista.addAll(reporteNotificacionRepositorio.cargarFiltroCompleto(
                        tareaXot.getCodigoCorreria(), tareaXot.getCodigoOrdenTrabajo(),
                        tareaXot.getTarea().getCodigoTarea()));
            }
        }

        if (filtro.getListaOrdenTrabajo() != null && filtro.getListaOrdenTrabajo().size() > 0) {
            for (OrdenTrabajo ot : filtro.getListaOrdenTrabajo()) {
                lista.addAll(reporteNotificacionMenu(ot, usuario));
            }
        }
        return lista;
    }

    private List<ReporteNotificacion> reporteNotificacionMenu(OrdenTrabajo ot, Usuario usuario) {
        ListaOpcion opciones = usuario.getPerfil().getListaOpciones().filtrar(opcion -> opcion.getRutina().equals("LaborNotificacion"));
        List<ReporteNotificacion> lista = new ArrayList<>();
        for (Opcion opcion : opciones) {
            ParametrosOpcionNotificacion parametros = new ParametrosOpcionNotificacion(opcion.getParametros());
            if (!parametros.getCodigoTarea().isEmpty()) {
                lista.addAll(reporteNotificacionRepositorio.cargarFiltroCompleto(ot.getCodigoCorreria(),
                        ot.getCodigoOrdenTrabajo(), parametros.getCodigoTarea(),
                        parametros.getCodigoLabor(), parametros.getCodigoNotificacion()));
            }
        }
        return lista;
    }

    private List<ArchivoAdjunto> archivosMenu(OrdenTrabajo ot, Usuario usuario) {
        ListaOpcion opciones = usuario.getPerfil().getListaOpciones().filtrar(opcion -> opcion.getRutina().equals("LaborNotificacion"));
        List<ArchivoAdjunto> lista = new ArrayList<>();
        for (Opcion opcion : opciones) {
            ParametrosOpcionNotificacion parametros = new ParametrosOpcionNotificacion(opcion.getParametros());
            if (!parametros.getCodigoTarea().isEmpty()) {
                lista.addAll(reporteNotificacionRepositorio.cargarArchivosXFiltro(ot.getCodigoCorreria(),
                        ot.getCodigoOrdenTrabajo(), parametros.getCodigoTarea(),
                        parametros.getCodigoLabor(), parametros.getCodigoNotificacion()));
            }
        }
        return lista;
    }
}