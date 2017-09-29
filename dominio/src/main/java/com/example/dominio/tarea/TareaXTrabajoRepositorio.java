package com.example.dominio.tarea;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.ListaTareaXTrabajo;
import com.example.dominio.modelonegocio.TareaXTrabajo;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public interface TareaXTrabajoRepositorio extends RepositorioBase<TareaXTrabajo> {

    ListaTareaXTrabajo cargarTareasXTrabajo(String codigoTrabajo);

    ListaTareaXTrabajo cargarTareasXTrabajoConCodigosDeTareas(String codigoTrabajo, List<String> listaCodigoTareas);

    TareaXTrabajo cargarTareaXTrabajo(String codigoTrabajo, String codigoTarea);

    ListaTareaXTrabajo cargarTareaXTrabajoXCodigoTarea(String codigoTarea);
}