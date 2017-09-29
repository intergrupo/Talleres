package com.example.dominio.ordentrabajo;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.Estado;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public interface EstadoRepositorio extends RepositorioBase<Estado> {

    Estado cargarEstado(String codigoEstado);

    List<Estado> cargarEstadoXGrupo(String codigoGrupo);

    List<Estado> cargarEstadoTareas();

    List<Estado> cargarEstadoTareasRevisiones();

    List<Estado> cargarEstadoTareasLectura();

}
