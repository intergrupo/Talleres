package com.example.santiagolopezgarcia.talleres.util;

import android.app.Application;

import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.di.componentes.DaggerComponentePresentacion;
import com.example.santiagolopezgarcia.talleres.di.modulos.ModuloPresentacion;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class ContenedorDependencia {

    DaggerComponentePresentacion.Builder contenedor;
    SiriusApp app;
    Application application;

    public ContenedorDependencia(Application application) {
        this.application = application;
        inicializarInyeccionDependencias();
    }

    public SiriusApp getApp() {
        return app;
    }

    public DaggerComponentePresentacion.Builder getContenedor() {
        return contenedor;
    }

    private void inicializarInyeccionDependencias() {
        app = (SiriusApp) application;
        contenedor = DaggerComponentePresentacion.builder();
        contenedor.componenteAplicacion(app.getAppComponent());
        contenedor.moduloPresentacion(new ModuloPresentacion());
    }
}