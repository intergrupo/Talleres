package com.example.dominio.bussinesslogic.administracion;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.PerfilXOpcion;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public interface PerfilXOpcionRepositorio extends RepositorioBase<PerfilXOpcion> {

    List<PerfilXOpcion> cargarPerfilOpcionXCodigoPerfil(String codigoPerfil);

    PerfilXOpcion cargarPerfilOpcion(String codigoPerfil, String codigoOpcion);
}
