package com.example.santiagolopezgarcia.talleres.view.popups;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.dominio.administracion.TalleresBL;
import com.example.dominio.modelonegocio.Talleres;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IEnvioWebParcialView;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IEnvioWebView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public class TerminalEnvioPopUp extends DialogFragment {

    @BindView(R.id.etNumeroTerminal)
    EditText etNumeroTerminal;
    @Inject
    TalleresBL talleresBL;
    private ContenedorDependencia dependencia;
    private Talleres talleres;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return iniciarDialogo();
    }

    @NonNull
    private Dialog iniciarDialogo() {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_terminal_envio);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this, dialog.getWindow().getDecorView());
        dependencia = new ContenedorDependencia(getActivity().getApplication());
        dependencia.getContenedor().build().inject(this);
        talleres = talleresBL.cargarPrimerRegistro();
        return dialog;
    }

    private void iniciarEnvio() {
        if (getActivity() instanceof IEnvioWebView) {
            ((IEnvioWebView) getActivity()).iniciar(etNumeroTerminal.getText().toString());
        } else if (getActivity() instanceof IEnvioWebParcialView) {
            ((IEnvioWebParcialView) getActivity()).iniciar(etNumeroTerminal.getText().toString());
        }
    }

    @OnClick(R.id.btnEnviarSirius)
    public void enviar() {
        if (!etNumeroTerminal.getText().toString().isEmpty()) {
            iniciarEnvio();
            dismiss();
        } else {
            mostrarMensaje(getString(R.string.ingresa_terminal));
        }
    }

    private void mostrarMensaje(String mensaje) {
        if (getActivity() instanceof IEnvioWebView) {
            ((IEnvioWebView) getActivity()).mostrarMensajeError(mensaje);
        } else if (getActivity() instanceof IEnvioWebParcialView) {
            ((IEnvioWebParcialView) getActivity()).mostrarMensajeError(mensaje);
        }
        dismiss();
    }


}
