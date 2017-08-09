package com.example.santiagolopezgarcia.talleres.di.modulos;

import com.example.dominio.view.UsuarioRepositorio;
import com.example.santiagolopezgarcia.talleres.TalleresApp;
import com.example.santiagolopezgarcia.talleres.data.dao.acceso.UsuarioDao;

import dagger.Module;
import dagger.Provides;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */

@Module
public class ModuloDominio {

    private final TalleresApp application;

    public ModuloDominio(TalleresApp application) {

        this.application = application;
    }

    @Provides
    UsuarioRepositorio provideUsuario() {
        return new UsuarioDao(application.getApplicationContext());
    }
}
