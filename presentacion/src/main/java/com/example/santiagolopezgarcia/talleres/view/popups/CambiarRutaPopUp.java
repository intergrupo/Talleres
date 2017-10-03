package com.example.santiagolopezgarcia.talleres.view.popups;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.dominio.bussinesslogic.administracion.TalleresBL;
import com.example.dominio.modelonegocio.Talleres;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;
import com.example.santiagolopezgarcia.talleres.util.LectorCodigoBarra;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IAdministracionView;
import com.example.utilidades.helpers.UrlHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class CambiarRutaPopUp extends BasePopup implements LectorCodigoBarra.OnLecturaCodigoBarra {

    @Inject
    TalleresBL talleresBL;
    @BindView(R.id.etRutaServidorGuardar)
    EditText etRutaServidor;
    private ContenedorDependencia dependencia;
    private IAdministracionView iAdministracionView;
    private Talleres talleres;
    private UrlHelper urlHelper;
    private LectorCodigoBarra lectorCodigoBarra;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inicializarInyeccionDependencias();
        return iniciarDialogo();
    }

    @NonNull
    private Dialog iniciarDialogo() {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_cambio_ruta_servidor);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this, dialog.getWindow().getDecorView());
        lectorCodigoBarra = new LectorCodigoBarra(dialog.getContext(), this);
        obtenerParametros();
        cargarDatos();
        return dialog;
    }

    private void obtenerParametros() {
        if (getContext() instanceof IAdministracionView) {
            iAdministracionView = (IAdministracionView) getContext();
        }
        talleres = (Talleres) getArguments().getSerializable(Talleres.class.getName());
    }

    private void inicializarInyeccionDependencias() {
        dependencia = new ContenedorDependencia(getActivity().getApplication());
        dependencia.getContenedor().build().inject(this);
    }

    public void cargarDatos() {
        etRutaServidor.setText(talleres.getRutaServidor());
    }

    @OnClick(R.id.btnGuardarRuta)
    public void guardar() {
        talleres.setRutaServidor(etRutaServidor.getText().toString());

        if (!talleres.getNumeroTerminal().isEmpty()) {
            talleresBL.actualizar(talleres);
        } else {
            talleresBL.guardar(talleres);
        }
        iAdministracionView.refrescarUrl(talleres.getRutaServidor());
        dismiss();
    }

    @OnClick(R.id.btnCancelarRuta)
    public void cancelar() {
        dismiss();
    }

    public void setUrl(String url) {
        getActivity().runOnUiThread(() -> etRutaServidor.setText(url));
    }

    @Override
    public void onLeerCodigoBarra(String codigoBarras, String tipoLectura) {
        setUrl(codigoBarras);
    }

    @Override
    public void onErrorCodigoBarra(String error) {

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        lectorCodigoBarra.cerrar();
    }
}
