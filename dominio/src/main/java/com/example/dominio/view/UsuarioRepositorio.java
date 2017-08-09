package com.example.dominio.view;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.Usuario;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */

public interface UsuarioRepositorio extends RepositorioBase<Usuario> {

    Usuario obtener(String codigo) throws ParseException;

    List<Usuario> cargar();

    List<Usuario> cargarXContrato(String codigoContrato);

    boolean modificarDespuesDeCarga();
}
