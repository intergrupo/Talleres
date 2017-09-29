package com.example.santiagolopezgarcia.talleres.view.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.integracion.EstadoConexionTerminal;
import com.example.santiagolopezgarcia.talleres.presenters.ReciboWebPresenter;
import com.example.santiagolopezgarcia.talleres.util.OnProgressBarListener;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IReciboWebView;
import com.example.santiagolopezgarcia.talleres.view.popups.AlertaPopUp;
import com.example.utilidades.helpers.WifiHelper;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public class ReciboWebActivity extends BaseActivity<ReciboWebPresenter>
        implements IReciboWebView, OnProgressBarListener {


    @BindView(R.id.tvMensajeCarga)
    TextView tvMensajeResultadoCarga;
    @BindView(R.id.tvMensajeCargando)
    TextView tvCargando;
    @BindView(R.id.tvMensajeFase)
    TextView textViewFase;
    @BindView(R.id.tvMensajeAdvertenciaConexion)
    TextView textViewAdvertenciaConexion;
    @BindView(R.id.pbReciboWeb)
    ProgressBar pbReciboWeb;
    private ActionBar actionBar;
    private WifiHelper wifiHelper;

    public static final int REQUEST_CODE = 16;
    private boolean fueCargaSatisfactoria = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recibo_web);
        ButterKnife.bind(this);
        dependencia.getContenedor().build().inject(this);
        wifiHelper = new WifiHelper(getContext());
        presentador.adicionarVista(this);
        runOnUiThread(() -> {
            pbReciboWeb.setVisibility(View.VISIBLE);
            presentador.iniciar();
        });
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
        //numberProgressbar.setOnProgressBarListener(this);
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
                ReciboWebActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
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
            tvMensajeResultadoCarga.setBackgroundColor(getResources().getColor(R.color.rojo));
            tvMensajeResultadoCarga.setText(mensaje);
            textViewAdvertenciaConexion.setVisibility(View.INVISIBLE);
            pbReciboWeb.setVisibility(View.INVISIBLE);
            configurarActionBar();
        });
    }

    @Override
    public void mostrarMensaje(String mensaje,String log) {
        runOnUiThread(() -> {
            if (log.toUpperCase().equals("S")) {
                try {
                    registrarLog(mensaje);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            tvMensajeResultadoCarga.setBackgroundColor(getResources().getColor(R.color.verdeoscuro));
            tvMensajeResultadoCarga.setText(mensaje);
            textViewAdvertenciaConexion.setVisibility(View.INVISIBLE);
            textViewFase.setVisibility(View.INVISIBLE);
            tvCargando.setVisibility(View.INVISIBLE);
            pbReciboWeb.setVisibility(View.INVISIBLE);
            configurarActionBar();
        });
    }

    @Override
    public void mostrarMensajeProceso(String mensaje,String log) {
        runOnUiThread(() ->{
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
    public void mostrarMensajeFase(String mensaje,String log) {
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
        //runOnUiThread(() -> numberProgressbar.setProgress(progreso));
    }

    @Override
    public void cerrar() {
        int resultCode = fueCargaSatisfactoria ? Activity.RESULT_OK : Activity.RESULT_CANCELED;
        setResult(resultCode);
        finish();
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
    public void establecerCargaSatisfactoria() {
        this.fueCargaSatisfactoria = true;
    }

    @Override
    public void habilitarRegreso(boolean habilitar) {

    }


    @Override
    public void onProgressChange(int current, int max) {

    }

    @Override
    public void onBackPressed() {
        runOnUiThread(() -> actionBar.setDisplayHomeAsUpEnabled(true));
    }
}
