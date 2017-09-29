package com.example.santiagolopezgarcia.talleres.view.popups;

import android.support.v4.app.DialogFragment;

import com.example.santiagolopezgarcia.talleres.helpers.ToastHelper;
import com.example.santiagolopezgarcia.talleres.presenters.Presenter;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;
import com.example.utilidades.helpers.StringHelper;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public class BasePopUpPresenter<T extends Presenter> extends DialogFragment {

    protected ContenedorDependencia dependencia;
    @Inject
    T presentador;

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

    public void registrarLog(String mensaje) throws IOException {
        StringHelper.registrarLog(mensaje);
    }

}