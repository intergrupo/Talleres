package com.example.santiagolopezgarcia.talleres.view.popups;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class DetalleMapaOrdenTrabajo extends DialogFragment {

    @BindView(R.id.tvOrdenTrabajo)
    TextView tvOrdenTrabajo;
    @BindView(R.id.tvNombre)
    TextView tvNombre;
    @BindView(R.id.tvDireccion)
    TextView tvDireccion;
    OrdenTrabajo ordenTrabajo;

    public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return iniciarDialogo();
    }

    @NonNull
    private Dialog iniciarDialogo() {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_detalle_mapa_orden_trabajo);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this, dialog.getWindow().getDecorView());
        asignarDatosAControles();
        return dialog;
    }

    private void asignarDatosAControles() {
        tvOrdenTrabajo.setText(this.ordenTrabajo.getCodigoOrdenTrabajo());
        tvNombre.setText(this.ordenTrabajo.getNombre());
        tvDireccion.setText(this.ordenTrabajo.getDireccion());
    }

    @OnClick(R.id.btnCerrar)
    public void cancelar(View view) {
        this.dismiss();
    }
}