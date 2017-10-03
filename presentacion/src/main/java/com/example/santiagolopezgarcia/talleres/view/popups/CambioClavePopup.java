package com.example.santiagolopezgarcia.talleres.view.popups;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dominio.bussinesslogic.administracion.UsuarioBL;
import com.example.dominio.modelonegocio.Usuario;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;
import com.example.santiagolopezgarcia.talleres.view.interfaces.Item;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class CambioClavePopup extends BasePopup {

    private Item<Usuario> itemCambioClave;
    @BindView(R.id.btnGuardar)
    Button btnGuardar;
    @BindView(R.id.etNuevaClave)
    EditText etNuevaClave;
    @BindView(R.id.etConfirmacionNuevaClave)
    EditText etConfirmacionNuevaClave;
    @BindView(R.id.tvNombreUsuario)
    TextView tvNombreUsuario;
    @BindView(R.id.tvIdentificacionUsuario)
    TextView tvIdentificacionUsuario;
    @BindView(R.id.tvMensaje)
    TextView tvMensaje;
    @BindView(R.id.btnCancelar)
    Button btnCancelar;
    private Usuario usuario;
    private String textoIdentificacion;
    private boolean esMenu;
    @Inject
    UsuarioBL usuarioBL;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setEsMenu(boolean esMenu){
        this.esMenu = esMenu;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return iniciarDialogo();
    }

    private void asignarDatos() {
        textoIdentificacion = getArguments().getString("textoIdentificacion");
        tvNombreUsuario.setText(usuario.getNombre());
        tvIdentificacionUsuario.setText(usuario.getCodigoUsuario());
        if (esMenu)
            tvMensaje.setText(getContext().getResources().getString(R.string.titulo_ingrese_la_nueva_contraseña));

    }

    @NonNull
    private Dialog iniciarDialogo() {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_cambio_clave);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this, dialog.getWindow().getDecorView());
        dependencia = new ContenedorDependencia(getActivity().getApplication());
        dependencia.getContenedor().build().inject(this);
        asignarDatos();
        return dialog;
    }

    @OnClick(R.id.btnGuardar)
    public void guardar(View view) {
        if (validarClaveVacias()) {
            if (validarClavesIguales()) {
                if (validarClaveIgualAlaAnterior()) {
                    Usuario usuarioActual = usuarioBL.cargarUsuario(usuario.getCodigoUsuario());
                    if (usuarioActual.esUsuarioValido()) {
                        usuarioActual.setClave(etNuevaClave.getText().toString());
                        usuarioActual.setFechaIngreso(new Date());
                        if (usuarioBL.actualizar(usuarioActual)) {
                            usuarioActual.getPerfil().setListaOpciones(usuario.getPerfil().getListaOpciones());
                            itemCambioClave.itemProcesado(usuarioActual);
                        } else {
                            mostrarMensajeError("No se logro actualizar la clave");
                        }
                    } else {
                        mostrarMensajeError("El usuario no existe");
                    }
                    this.dismiss();
                } else {
                    mostrarMensajeError("La clave debe ser diferente a la anterior");
                }
            } else {
                mostrarMensajeError("No coinciden las claves");
            }
        } else {
            mostrarMensajeError("Ingrese una clave válida de 4 dígitos");
        }
    }

    @OnClick(R.id.btnCancelar)
    public void cancelar(View view) {
        this.dismiss();
    }

    private boolean validarClaveIgualAlaAnterior() {
        if (TextUtils.isEmpty(this.usuario.getClave()))
            return true;
        return !etNuevaClave.getText().toString().equals(this.usuario.getClave());
    }

    private boolean validarClavesIguales() {
        return etNuevaClave.getText().toString().equals(etConfirmacionNuevaClave.getText().toString());
    }

    private boolean validarClaveVacias() {
        return etNuevaClave.getText().toString().length() == 4 && etConfirmacionNuevaClave.getText().toString().length() == 4;
    }

    public void setCambioClavePopupPopup(Item<Usuario> itemCambioClave) {
        this.itemCambioClave = itemCambioClave;
    }
}
