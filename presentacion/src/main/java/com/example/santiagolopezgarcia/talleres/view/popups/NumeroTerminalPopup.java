package com.example.santiagolopezgarcia.talleres.view.popups;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dominio.administracion.TalleresBL;
import com.example.dominio.modelonegocio.Talleres;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;
import com.example.santiagolopezgarcia.talleres.view.interfaces.LoginView;
import com.example.utilidades.helpers.UrlHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class NumeroTerminalPopup extends BasePopup {

    @Inject
    TalleresBL talleresBL;
    @BindView(R.id.etNumeroTerminal)
    EditText etNumeroTerminal;
    @BindView(R.id.btnGuardarSirius)
    Button btnGuardar;
    private ContenedorDependencia dependencia;
    private LoginView loginView;
    private UrlHelper urlHelper;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return iniciarDialogo();
    }

    @NonNull
    private Dialog iniciarDialogo() {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_numero_terminal);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this, dialog.getWindow().getDecorView());
        dependencia = new ContenedorDependencia(getActivity().getApplication());
        dependencia.getContenedor().build().inject(this);
        cargarDatos();
        return dialog;
    }

    private void cargarDatos() {
        if (getActivity() instanceof LoginView) {
            loginView = (LoginView) getActivity();
        }
        Talleres talleres = talleresBL.cargarPrimerRegistro();
        if (!talleres.getNumeroTerminal().isEmpty()) {
            etNumeroTerminal.setText(talleres.getNumeroTerminal());
        }
    }

    @OnClick(R.id.btnGuardarSirius)
    public void guardarSirius() {
        if (!etNumeroTerminal.getText().toString().isEmpty()) {
            Talleres talleres = new Talleres();
            talleres.setNumeroTerminal(etNumeroTerminal.getText().toString().trim());
            if (talleresBL.procesarDatos(talleres)) {
                loginView.mostrarNumeroTerminal(talleres.getNumeroTerminal());
                dismiss();
            }
        } else {
            Toast.makeText(getContext(),
                    R.string.ingresar_numero_terminal, Toast.LENGTH_LONG).show();
        }
    }


}