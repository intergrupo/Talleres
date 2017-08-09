package com.example.santiagolopezgarcia.talleres.di.componentes;

import com.example.santiagolopezgarcia.talleres.di.modulos.ModuloPresentacion;
import com.example.santiagolopezgarcia.talleres.di.scopes.PerActivity;

import dagger.Component;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */

@PerActivity
@Component(dependencies = ComponenteAplicacion.class, modules = ModuloPresentacion.class)
public interface ComponentePresentacion {

}
