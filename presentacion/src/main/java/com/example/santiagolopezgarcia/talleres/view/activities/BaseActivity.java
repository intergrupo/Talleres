package com.example.santiagolopezgarcia.talleres.view.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.santiagolopezgarcia.talleres.AppContext;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.helpers.ToastHelper;
import com.example.santiagolopezgarcia.talleres.presenters.Presenter;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;
import com.example.santiagolopezgarcia.talleres.util.DelayedProgressDialog;
import com.example.santiagolopezgarcia.talleres.util.UnCaughtException;
import com.example.utilidades.helpers.StringHelper;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class BaseActivity<T extends Presenter> extends AppCompatActivity {

    protected ContenedorDependencia dependencia;
    private ProgressDialog dialogoBarraProgreso;
    protected SiriusApp app;
    @Inject
    T presentador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(this));
        dependencia = new ContenedorDependencia(getApplication());
        app = dependencia.getApp();
        AppContext.setContextoGlobal(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dependencia = new ContenedorDependencia(getApplication());
        app = dependencia.getApp();
        AppContext.setContextoGlobal(this);
    }

    protected void mostrarBarraProgreso(String mensaje) {
        dialogoBarraProgreso = DelayedProgressDialog.show(this, mensaje, 2000);
    }

    public void ocultarBarraProgreso() {
        if (dialogoBarraProgreso != null) {
            dialogoBarraProgreso.dismiss();
            dialogoBarraProgreso.cancel();
        }
    }

    protected void limpiarFocos() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void mostrarMensaje(final String mensaje, String log) {
        runOnUiThread(() -> {
            ToastHelper.mostrarMensaje(this, mensaje, log);
        });
    }

    public void mostrarMensajeError(final String mensaje) {
        runOnUiThread(() -> {
            ToastHelper.mostrarMensajeError(this, mensaje);
        });
    }

    public void mostrarMensajeAdvertencia(final String mensaje) {
        runOnUiThread(() -> {
            ToastHelper.mostrarMensajeAdvertencia(this, mensaje);
        });
    }

    public void registrarLog(String mensaje) throws IOException {
        StringHelper.registrarLog(mensaje);
    }


}

