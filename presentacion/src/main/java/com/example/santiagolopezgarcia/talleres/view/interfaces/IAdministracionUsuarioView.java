package com.example.santiagolopezgarcia.talleres.view.interfaces;

import com.example.dominio.modelonegocio.Usuario;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public interface IAdministracionUsuarioView extends BaseView {
    void cargarListaUsuarios(List<Usuario> listaUsuarios);
}