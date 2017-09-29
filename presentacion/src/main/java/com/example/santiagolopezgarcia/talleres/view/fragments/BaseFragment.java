package com.example.santiagolopezgarcia.talleres.view.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.santiagolopezgarcia.talleres.AppContext;
import com.example.santiagolopezgarcia.talleres.helpers.ToastHelper;
import com.example.santiagolopezgarcia.talleres.presenters.Presenter;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;
import com.example.santiagolopezgarcia.talleres.util.DelayedProgressDialog;
import com.example.santiagolopezgarcia.talleres.util.UnCaughtException;
import com.example.utilidades.helpers.StringHelper;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class BaseFragment<T extends Presenter> extends Fragment {

    protected ContenedorDependencia dependencia;
    private ProgressDialog dialogoBarraProgreso;
    @Inject
    T presentador;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(getActivity()));
        dependencia = new ContenedorDependencia(getActivity().getApplication());

    }

    @Override
    public void onResume(){
        super.onResume();
        dependencia = new ContenedorDependencia(getActivity().getApplication());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void mostrarMensaje(final String mensaje, String log) {
        getActivity().runOnUiThread(() -> {
            ToastHelper.mostrarMensaje(getContext(), mensaje, log);
        });
    }

    public void mostrarMensajeError(final String mensaje) {
        getActivity().runOnUiThread(() -> {
            ToastHelper.mostrarMensajeError(getContext(), mensaje);
        });
    }

    public void mostrarMensajeAdvertencia(final String mensaje) {
        getActivity().runOnUiThread(() -> {
            ToastHelper.mostrarMensajeAdvertencia(getContext(), mensaje);
        });
    }

    protected void mostrarBarraProgreso(String mensaje) {
        dialogoBarraProgreso = DelayedProgressDialog.show(AppContext.getContextoGlobal()
                , mensaje, 2000);
    }

    public void ocultarBarraProgreso() {
        dialogoBarraProgreso.dismiss();
        dialogoBarraProgreso.cancel();
    }

    public void registrarLog(String mensaje) throws IOException {
        StringHelper.registrarLog(mensaje);
    }
}