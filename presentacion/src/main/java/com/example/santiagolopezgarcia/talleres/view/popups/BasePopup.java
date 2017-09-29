package com.example.santiagolopezgarcia.talleres.view.popups;

import android.support.v4.app.DialogFragment;

import com.example.santiagolopezgarcia.talleres.helpers.ToastHelper;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;
import com.example.utilidades.helpers.StringHelper;

import java.io.IOException;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class BasePopup extends DialogFragment {

    protected ContenedorDependencia dependencia;

    public void mostrarMensaje(final String mensaje,String log) {
        getActivity().runOnUiThread(() -> {
            ToastHelper.mostrarMensaje(getContext(), mensaje,log);
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
