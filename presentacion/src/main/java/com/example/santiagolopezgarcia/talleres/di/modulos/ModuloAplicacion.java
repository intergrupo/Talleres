package com.example.santiagolopezgarcia.talleres.di.modulos;

import android.content.Context;

import com.example.santiagolopezgarcia.talleres.TalleresApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */

@Module
public class ModuloAplicacion {

    private final TalleresApp application;

    public ModuloAplicacion(TalleresApp application) {

        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application;
    }
}