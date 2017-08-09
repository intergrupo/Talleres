package com.example.dominio.view;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.Perfil;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */

public interface PerfilRepositorio extends RepositorioBase<Perfil> {

    Perfil cargarPerfilXCodigo(String codigoPerfil);

    List<Perfil> cargarPerfiles();
}
