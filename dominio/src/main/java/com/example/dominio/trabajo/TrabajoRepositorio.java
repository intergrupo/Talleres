package com.example.dominio.trabajo;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.ListaTrabajo;
import com.example.dominio.modelonegocio.Trabajo;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public interface TrabajoRepositorio extends RepositorioBase<Trabajo> {

    Trabajo cargarTrabajo(String codigoTrabajo);

    ListaTrabajo cargarTrabajos(List<String> listaCodigoTrabajos);

    ListaTrabajo cargarTrabajos();
}
