package com.example.santiagolopezgarcia.talleres.view.popups;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.integracion.EstadoConexionTerminal;
import com.example.santiagolopezgarcia.talleres.presenters.CargaDatosPresenter;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;
import com.example.santiagolopezgarcia.talleres.view.interfaces.ICargaDatosView;
import com.example.santiagolopezgarcia.talleres.view.interfaces.ISincronizacionView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public class SincronizacionPopUp extends BasePopUpPresenter<CargaDatosPresenter> implements ICargaDatosView {

    @BindView(R.id.tvMensajeCarga)
    TextView tvMensajeResultadoCarga;
    @BindView(R.id.tvMensajeCargando)
    TextView tvCargando;
    @BindView(R.id.pbCargar)
    ProgressBar progressBar;
    @BindView(R.id.btnSalir)
    Button btnSalir;
    private ISincronizacionView iSincronizacionView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inicializarInyeccionDependencias();
        presentador.adicionarVista(this);
        return iniciarDialogo();
    }

    private void inicializarInyeccionDependencias() {
        dependencia = new ContenedorDependencia(getActivity().getApplication());
        dependencia.getContenedor().build().inject(this);
    }

    @NonNull
    private Dialog iniciarDialogo() {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sincronizacion);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this, dialog.getWindow().getDecorView());
        getActivity().runOnUiThread(() -> {
            progressBar.setVisibility(View.VISIBLE);
            obtenerParametros();
            presentador.iniciar();
        });
        return dialog;
    }

    private void obtenerParametros() {
        if(getActivity() instanceof ISincronizacionView){
            iSincronizacionView = (ISincronizacionView) getActivity();
        }
        presentador.setPosicionActiva(getArguments().getInt("Posicion", 0));
        presentador.setSiriusApp((SiriusApp) getActivity().getApplication());
        presentador.setSINCRONIZACION(getArguments().getBoolean("Sincronizacion", false));
    }

    @Override
    public void mostrarMensajeProceso(String mensaje, String log) {
        getActivity().runOnUiThread(() -> {
            if (log.toUpperCase().equals("S")) {
                try {
                    registrarLog(mensaje);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            tvCargando.setText("Sincronizando...");
        });
    }

    @Override
    public void mostrarMensajeFase(String mensaje, String log) {

    }

    @Override
    public void establecerEstadoConexionTerminal(EstadoConexionTerminal estadoConexionTerminal) {

    }

    @Override
    public void aumentarProgresoBarra(int progreso) {

    }

    @Override
    public void cerrar() {

    }

    @Override
    public void mostrarConfirmacionRemplazoCorreria(String mensaje, int indice) {
        getActivity().runOnUiThread(() -> {
            AlertaPopUp alertaPopUp = new AlertaPopUp();
            alertaPopUp.setTitle(R.string.titulo_reemplazo_correria);
            alertaPopUp.setMessage(mensaje);
            alertaPopUp.setCancelable(false);
            alertaPopUp.setContext(this.getContext());
            alertaPopUp.setPositiveButton("SI", (dialog, id) -> {
                try {
                    this.presentador.respuestaRemplazoCorreria(indice, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            });
            alertaPopUp.setNegativeButton("NO", (dialog, id) -> {
                try {
                    this.presentador.respuestaRemplazoCorreria(indice, false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();

            });

            alertaPopUp.show();
        });
    }

    @Override
    public void mostrarConfirmacionRemplazoTarea(String mensaje, int indice) {
        getActivity().runOnUiThread(() -> {
            AlertaPopUp alertaPopUp = new AlertaPopUp();
            alertaPopUp.setTitle(R.string.titulo_reemplazo_tarea);
            alertaPopUp.setMessage(mensaje);
            alertaPopUp.setCancelable(false);
            alertaPopUp.setContext(this.getContext());
            alertaPopUp.setPositiveButton("SI", (dialog, id) -> {
                try {
                    this.presentador.respuestaRemplazoTarea(indice, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            });
            alertaPopUp.setNegativeButton("NO", (dialog, id) -> {
                try {
                    this.presentador.respuestaRemplazoTarea(indice, false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            });

            alertaPopUp.show();
        });
    }

    @Override
    public void mostrarConfirmacionActualizarMaestros(String mensaje) {
        getActivity().runOnUiThread(() -> {
            AlertaPopUp alertaPopUp = new AlertaPopUp();
            alertaPopUp.setTitle(R.string.titulo_actualizar_maestro);
            alertaPopUp.setMessage(mensaje);
            alertaPopUp.setCancelable(false);
            alertaPopUp.setContext(this.getContext());
            alertaPopUp.setPositiveButton("SI", (dialog, id) -> {
                try {
                    this.presentador.respuestaConfirmacionActualizarMaestros(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            });
            alertaPopUp.setNegativeButton("NO", (dialog, id) -> {
                try {
                    this.presentador.respuestaConfirmacionActualizarMaestros(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            });

            alertaPopUp.show();

        });
    }

    @Override
    public void establecerCargaSatisfactoria() {

    }

    @Override
    public void habilitarRegreso(boolean habilitar) {
//        getActivity().runOnUiThread(() -> dismiss());
    }

    @Override
    public void mostrarBarraProgreso() {

    }

    @Override
    public void ocultarBarraProgreso() {

    }

    @Override
    public void mostrarMensajeError(String mensaje) {
        getActivity().runOnUiThread(() -> {
            try {
                registrarLog(mensaje);
            } catch (IOException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(View.GONE);
            tvMensajeResultadoCarga.setBackgroundColor(getResources().getColor(R.color.rojo));
            tvMensajeResultadoCarga.setText(mensaje);
            tvCargando.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            btnSalir.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void mostrarMensaje(String mensaje, String log) {
        getActivity().runOnUiThread(() -> {
            if (log.toUpperCase().equals("S")) {
                try {
                    registrarLog(mensaje);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            progressBar.setVisibility(View.INVISIBLE);
            tvMensajeResultadoCarga.setBackgroundColor(getResources().getColor(R.color.verdeoscuro));
            tvMensajeResultadoCarga.setText(mensaje);
            tvCargando.setVisibility(View.INVISIBLE);
            salir();
        });
    }

    @OnClick(R.id.btnSalir)
    public void salir() {
        if (presentador.getCantidadOTSIniciales() < presentador.getCantidadOTSFinales()
                || presentador.getCantidadTareasIniciales() < presentador.getCantidadTareasFinales()
                && iSincronizacionView != null) {
            iSincronizacionView.regresarAPantallaPrincipal(presentador.getPosicionActiva());
        }
        dismiss();
    }
}