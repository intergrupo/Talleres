package com.example.santiagolopezgarcia.talleres.di.componentes;

import com.example.santiagolopezgarcia.talleres.di.modulos.ModuloAplicacion;
import com.example.santiagolopezgarcia.talleres.di.modulos.ModuloDominio;
import com.example.dominio.view.UsuarioRepositorio;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */

@Singleton
@Component(modules = {ModuloAplicacion.class, ModuloDominio.class,})
public interface ComponenteAplicacion {

    UsuarioRepositorio usuarioRepository();
}
