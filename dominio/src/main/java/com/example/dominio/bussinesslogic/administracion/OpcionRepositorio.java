package com.example.dominio.bussinesslogic.administracion;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.Opcion;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public interface OpcionRepositorio extends RepositorioBase<Opcion> {

    Opcion cargarOpcionPorCodigo(String codigoOpcion);
}
