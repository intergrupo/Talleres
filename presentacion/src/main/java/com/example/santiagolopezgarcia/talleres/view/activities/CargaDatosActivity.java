package com.example.santiagolopezgarcia.talleres.view.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.integracion.EstadoConexionTerminal;
import com.example.santiagolopezgarcia.talleres.presenters.CargaDatosPresenter;
import com.example.santiagolopezgarcia.talleres.util.OnProgressBarListener;
import com.example.santiagolopezgarcia.talleres.view.interfaces.ICargaDatosView;
import com.example.santiagolopezgarcia.talleres.view.popups.AlertaPopUp;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class CargaDatosActivity extends BaseActivity<CargaDatosPresenter>
        implements ICargaDatosView, OnProgressBarListener {

    @BindView(R.id.tvMensajeCarga)
    TextView tvMensajeResultadoCarga;
    @BindView(R.id.tvMensajeCargando)
    TextView tvCargando;
    @BindView(R.id.tvMensajeAdvertenciaConexion)
    TextView textViewAdvertenciaConexion;
    @BindView(R.id.pbCargar)
    ProgressBar progressBar;
    @BindView(R.id.tvMensajeFase)
    TextView textViewFase;
    private ActionBar actionBar;

    public static final int REQUEST_CODE = 11;
    private boolean fueCargaSatisfactoria = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_datos);
        ButterKnife.bind(this);
        dependencia.getContenedor().build().inject(this);
        presentador.adicionarVista(this);
        mostrarBarraProgreso();
        configurarActionBar();
        runOnUiThread(() -> {
            progressBar.setVisibility(View.VISIBLE);
            obtenerParametros();
            presentador.iniciar();
        });
    }

    private void obtenerParametros() {
        presentador.setPosicionActiva(getIntent().getIntExtra("Posicion", 0));
        presentador.setSiriusApp((SiriusApp) getApplication());
        presentador.setSINCRONIZACION(getIntent().getBooleanExtra("Sincronizacion", false));
    }

    private void configurarActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void mostrarBarraProgreso() {
//        numberProgressbar.setOnProgressBarListener(this);
    }

    @Override
    public void ocultarBarraProgreso() {
        runOnUiThread(() -> tvCargando.setVisibility(View.GONE));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                presentador.mostrarAlertaWifi();
                CargaDatosActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void mostrarMensajeError(String mensaje) {
        runOnUiThread(() -> {
            try {
                registrarLog(mensaje);
            } catch (IOException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(View.INVISIBLE);
            tvMensajeResultadoCarga.setBackgroundColor(getResources().getColor(R.color.rojo));
            tvMensajeResultadoCarga.setText(mensaje);
            textViewAdvertenciaConexion.setVisibility(View.INVISIBLE);
            configurarActionBar();
        });
    }

    @Override
    public void mostrarMensaje(String mensaje, String log) {
        runOnUiThread(() -> {
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
            textViewAdvertenciaConexion.setVisibility(View.INVISIBLE);
            textViewFase.setVisibility(View.INVISIBLE);
            tvCargando.setVisibility(View.INVISIBLE);
            configurarActionBar();
        });
    }

    @Override
    public void mostrarMensajeProceso(String mensaje, String log) {
        runOnUiThread(() -> {
            if (log.toUpperCase().equals("S")) {
                try {
                    registrarLog(mensaje);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            tvCargando.setText(mensaje);
        });
    }

    @Override
    public void mostrarMensajeFase(String mensaje, String log) {
        runOnUiThread(() -> {
            if (log.toUpperCase().equals("S")) {
                try {
                    registrarLog(mensaje);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            textViewFase.setText(mensaje);
        });
    }

    @Override
    public void establecerEstadoConexionTerminal(EstadoConexionTerminal estadoConexionTerminal) {
        runOnUiThread(() ->
                {
                    switch (estadoConexionTerminal) {
                        case NoDesconectar:
                            textViewAdvertenciaConexion.setText(R.string.mensaje_carga_no_desconecte);
                            break;
                        case PuedeDesconectar:
                            textViewAdvertenciaConexion.setBackgroundColor(getResources().getColor(R.color.naranjado));
                            textViewAdvertenciaConexion.setText(R.string.mensaje_carga_puede_desconectar);
                            break;
                        case NoMostrar:
                            textViewAdvertenciaConexion.setVisibility(View.GONE);
                            break;
                    }
                }
        );
    }

    @Override
    public void aumentarProgresoBarra(int progreso) {
//        runOnUiThread(() -> numberProgressbar.setProgress(progreso));
    }

    @Override
    public void mostrarConfirmacionRemplazoCorreria(String mensaje, int indice) {
        runOnUiThread(() -> {
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
        runOnUiThread(() -> {
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
        runOnUiThread(() -> {
            AlertaPopUp alertaPopUp = new AlertaPopUp();
            alertaPopUp.setTitle(R.string.titulo_actualizar_maestro);
            alertaPopUp.setMessage(mensaje);
            alertaPopUp.setCancelable(false);
            alertaPopUp.setContext(this);
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
        this.fueCargaSatisfactoria = true;
    }

    @Override
    public void habilitarRegreso(boolean habilitar) {
        runOnUiThread(() -> actionBar.setDisplayHomeAsUpEnabled(habilitar));

    }

    @Override
    public void cerrar() {
        int resultCode = fueCargaSatisfactoria ? Activity.RESULT_OK : Activity.RESULT_CANCELED;
        if (presentador.getCantidadOTSIniciales() < presentador.getCantidadOTSFinales()
                || presentador.getCantidadTareasIniciales() < presentador.getCantidadTareasFinales()) {
            Intent intent = new Intent();
            intent.putExtra("Posicion", presentador.getPosicionActiva());
            intent.putExtra("Refrescar", true);
            setResult(resultCode, intent);
        } else {
            setResult(resultCode);
        }
        presentador.firmarCorreria(app.getCodigoCorreria());
        finish();
    }

    @Override
    public void onProgressChange(int current, int max) {

    }

    @Override
    public void onBackPressed() {

    }

}
