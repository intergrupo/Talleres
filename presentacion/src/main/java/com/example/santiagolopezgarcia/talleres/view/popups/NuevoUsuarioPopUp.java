package com.example.santiagolopezgarcia.talleres.view.popups;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dominio.bussinesslogic.administracion.ContratoBL;
import com.example.dominio.bussinesslogic.administracion.PerfilBL;
import com.example.dominio.bussinesslogic.administracion.UsuarioBL;
import com.example.dominio.modelonegocio.Contrato;
import com.example.dominio.modelonegocio.Perfil;
import com.example.dominio.modelonegocio.Talleres;
import com.example.dominio.modelonegocio.Usuario;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;
import com.example.santiagolopezgarcia.talleres.util.LectorCodigoBarra;
import com.example.santiagolopezgarcia.talleres.view.activities.AdministracionActivity;
import com.example.santiagolopezgarcia.talleres.view.adapters.PerfilSpinnerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class NuevoUsuarioPopUp extends BasePopup implements LectorCodigoBarra.OnLecturaCodigoBarra {

    @Inject
    ContratoBL contratoBL;
    @Inject
    PerfilBL perfilBL;
    @Inject
    UsuarioBL usuarioBL;
    @BindView(R.id.tvContratoNuevo)
    TextView tvContrato;
    @BindView(R.id.spPerfilNuevo)
    Spinner spPerfil;
    @BindView(R.id.etIdentificacionNuevo)
    EditText etIdentificacion;
    @BindView(R.id.etNombreNuevo)
    EditText etNombre;
    private Usuario usuario;
    private String digitoChequeo;
    private LectorCodigoBarra lectorCodigoBarra;
    private Contrato contrato;
    private Talleres talleres;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return iniciarDialogo();
    }

    @NonNull
    private Dialog iniciarDialogo() {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_nuevo_usuario);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this, dialog.getWindow().getDecorView());
        dependencia = new ContenedorDependencia(getActivity().getApplication());
        dependencia.getContenedor().build().inject(this);
        lectorCodigoBarra = new LectorCodigoBarra(getContext(), this);
        obtenerParametros();
        cargarDatos();
        validarTipoIdentificacion();
        return dialog;
    }

    private void validarTipoIdentificacion() {
        bloquearTipoIdentificacionDigitado(talleres.getTipoIdentificacion().equals(talleres.TIPO_IDENTIFICACION_AMBOS)
                || talleres.getTipoIdentificacion().isEmpty());
    }

    private void obtenerParametros() {
        contrato = contratoBL.cargarContratoXCodigo(getArguments().getString("CodigoContrato"));
        talleres = (Talleres) getArguments().getSerializable(Talleres.class.getName());
    }

    private void cargarDatos() {
        tvContrato.setText(contrato.getNombre());
        spPerfil.setAdapter(new PerfilSpinnerAdapter(getContext(), perfilBL.cargarPerfiles()));
    }

    @OnClick(R.id.btnGuardarNuevoUsuario)
    public void guardar(View view) {
        usuario = new Usuario();
        usuario.setCodigoUsuario(etIdentificacion.getText().toString());
        usuario.setNombre(etNombre.getText().toString());
        usuario.setContrato(contrato);
        usuario.setPerfil((Perfil) spPerfil.getSelectedItem());
        usuario.setCodigoUsuarioIngreso(((SiriusApp) getActivity().getApplication()).getUsuario().getCodigoUsuario());

        if (!etIdentificacion.getText().toString().isEmpty() && !etNombre.getText().toString().isEmpty()
                && !tvContrato.getText().toString().isEmpty() && spPerfil.getSelectedItemPosition() > 0) {
            if (usuarioBL.cargarUsuario(usuario.getCodigoUsuario()).esUsuarioValido()) {
                mostrarMensajeError("Este usuario ya existe");
            } else {
                usuarioBL.guardar(usuario);
                digitoChequeo = usuarioBL.traerDigitoChequeo(etIdentificacion.getText().toString());
                mostrarAlertaDigitoChequeo(digitoChequeo);
                ((AdministracionActivity) getActivity()).adicionarUsuario(usuario);
                lectorCodigoBarra.cerrar();
                dismiss();
            }
        } else {
            mostrarMensajeError("Falta campos obligatorios por llenar");
        }
    }

    public void mostrarAlertaDigitoChequeo(String digitoChequeo) {
        AlertaPopUp alertaPopUp = new AlertaPopUp();
        alertaPopUp.setTitle("Dígito de chequeo");
        alertaPopUp.setMessage(String.format("%s %s",
                getResources().getString(R.string.mensaje_digito_chequeo), digitoChequeo));
        alertaPopUp.setContext(getContext());
        alertaPopUp.setPositiveButton("ACEPTAR", (dialog, id) -> {
            alertaPopUp.dismiss();
        });
        alertaPopUp.show();


    }

    @OnClick(R.id.btnCancelarNuevoUsuario)
    public void cancelar() {
        lectorCodigoBarra.cerrar();
        dismiss();
    }

    @Override
    public void onLeerCodigoBarra(String codigoBarras, String tipoLectura) {
        getActivity().runOnUiThread(() -> etIdentificacion.setText(usuarioBL.obtenerIdentificacion(codigoBarras)));
    }

    @Override
    public void onErrorCodigoBarra(String error) {

    }

    public void bloquearTipoIdentificacionDigitado(boolean bloquear) {
        etIdentificacion.setEnabled(bloquear);
        etIdentificacion.setTextColor(getResources().getColor(R.color.negro));
    }
}
