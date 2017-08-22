package com.example.dominio.tarea;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.Tarea;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public interface TareaRepositorio extends RepositorioBase<Tarea> {

    Tarea cargarTareaxCodigo(String codigoTarea);

    List<Tarea> cargarTareas() throws ParseException;
}