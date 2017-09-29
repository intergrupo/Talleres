package com.example.santiagolopezgarcia.talleres.view.popups;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.dominio.modelonegocio.NotificacionOrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;
import com.example.santiagolopezgarcia.talleres.view.adapters.ResultadoNotificacionOTAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class ResultadoNotificacionOTPopUp extends BasePopup {

    @BindView(R.id.rvResultado)
    RecyclerView rvResultado;
    @BindView(R.id.btnAceptar)
    Button btnAceptar;

    public List<NotificacionOrdenTrabajo> listaNotificacionOT;


    public List<NotificacionOrdenTrabajo> getListaNotificacionOT() {
        return listaNotificacionOT;
    }

    public void setListaNotificacionOT(List<NotificacionOrdenTrabajo> listaNotificacionOT) {
        this.listaNotificacionOT = listaNotificacionOT;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return iniciarDialogo();
    }

    @NonNull
    private Dialog iniciarDialogo() {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_resultado_notificacion);
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
        rvResultado.setAdapter(new ResultadoNotificacionOTAdapter(((SiriusApp) getActivity().getApplication()), getContext(),
                listaNotificacionOT));
        rvResultado.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @OnClick(R.id.btnAceptar)
    public void aceptar(){
        this.dismiss();
    }
}
