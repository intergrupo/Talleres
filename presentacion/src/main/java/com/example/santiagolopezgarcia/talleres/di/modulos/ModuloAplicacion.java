package com.example.santiagolopezgarcia.talleres.di.modulos;

import android.content.Context;

import com.example.santiagolopezgarcia.talleres.SiriusApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */

@Module
public class ModuloAplicacion {

    private final SiriusApp application;

    public ModuloAplicacion(SiriusApp application) {

        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application;
    }
}