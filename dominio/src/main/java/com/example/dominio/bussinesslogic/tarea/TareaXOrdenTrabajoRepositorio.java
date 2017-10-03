package com.example.dominio.bussinesslogic.tarea;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.ListaOrdenTrabajo;
import com.example.dominio.modelonegocio.ListaTareaXOrdenTrabajo;
import com.example.dominio.modelonegocio.Tarea;
import com.example.dominio.modelonegocio.TareaXOrdenTrabajo;
import com.example.dominio.modelonegocio.TareaXTrabajoOrdenTrabajo;
import com.example.dominio.modelonegocio.TotalTarea;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public interface TareaXOrdenTrabajoRepositorio extends RepositorioBase<TareaXTrabajoOrdenTrabajo> {

    List<String> cargarCodigoOrdenTrabajoXEstadoTarea(Tarea.EstadoTarea estadoTarea);

    List<String> cargarCodigoOrdenTrabajoXTarea(Tarea tarea);

    ListaTareaXOrdenTrabajo cargarTareasXOrdenTrabajo(String codigoCorreria);

    List<String> cargarCodigoOrdenTrabajoXDescarga();

    List<TareaXTrabajoOrdenTrabajo> cargarListaTareaXTrabajoOrdenTrabajoXCorreria(String codigoCorreria);


    List<TareaXTrabajoOrdenTrabajo> cargarListaTareaXTrabajoOrdenTrabajo(String codigoCorreria,
                                                                         String codigoOrdenTrabajo);

    List<TareaXTrabajoOrdenTrabajo> cargarListaTareaXTrabajoOrdenTrabajo(String codigoCorreria,
                                                                         String codigoOrdenTrabajo,
                                                                         String codigoTrabajo);

    List<TareaXTrabajoOrdenTrabajo> cargarListaTareaXTrabajoOrdenTrabajoXTarea(String codigoCorreria,
                                                                               String codigoOrdenTrabajo,
                                                                               String codigoTarea);

    List<TotalTarea> cargarTotalesPorTarea(String codigoCorreria, String codigoTrabajo);

    TareaXTrabajoOrdenTrabajo cargarTareaXTrabajoOrdenTrabajo(String codigoCorreria,
                                                              String codigoOrdenTrabajo,
                                                              String codigoTarea,
                                                              String codigoTrabajo);

    List<String> cargarCodigosTrabajosAgrupados(String codigoCorreria);

    boolean actualizarFechaDescarga(String codigoCorreria, String fechaDescarga, ListaOrdenTrabajo listaOrdenTrabajo);

    TareaXOrdenTrabajo cargar(String codigoCorreria, String codigoOrdenTrabajo, String codigoTrabajo, String codigoTarea, String codigoEstadoTarea);

    //ListaTareaXOrdenTrabajo cargar(String codigoCorreria, String codigoOrdenTrabajo, String codigoTrabajo, String codigoTarea, String codigoEstadoTarea, boolean noDescargada);

    ListaTareaXOrdenTrabajo cargar(String codigoCorreria, String codigoOrdenTrabajo, String codigoTrabajo, String codigoTarea, String codigoEstadoTarea, String serie, boolean noDescargada);

    int asignarUltimaSecuencia(String codigoCorreria);
}