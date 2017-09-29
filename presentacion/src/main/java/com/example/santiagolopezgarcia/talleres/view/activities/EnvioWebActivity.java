package com.example.santiagolopezgarcia.talleres.view.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dominio.modelonegocio.Correria;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.presenters.EnvioWebPresenter;
import com.example.santiagolopezgarcia.talleres.util.OnProgressBarListener;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IEnvioWebView;
import com.example.santiagolopezgarcia.talleres.view.popups.TerminalEnvioPopUp;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public class EnvioWebActivity extends BaseActivity<EnvioWebPresenter>
        implements IEnvioWebView, OnProgressBarListener {

    @BindView(R.id.tvMensajeDescarga)
    TextView tvMensajeResultadoDescarga;
    @BindView(R.id.tvMensajeDescargando)
    TextView tvDescargando;
    @BindView(R.id.pbEnvioWeb)
    ProgressBar pbEnvioWeb;
    @BindView(R.id.llCotinuarEnvio)
    LinearLayout llCotinuarEnvio;
    private ActionBar actionBar;
    private Correria correria;
    public static final int REQUEST_CODE = 12;
    private int indiceAdjuntoActual = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_web);
        ButterKnife.bind(this);
        dependencia.getContenedor().build().inject(this);
        presentador.adicionarVista(this);
        presentador.iniciar();
        configurarActionBar();
        cargarDatos();
        pedirTerminal();
        mostrarBarraProgreso();
    }

    private void pedirTerminal() {
        TerminalEnvioPopUp terminalEnvioPopUp = new TerminalEnvioPopUp();
        FragmentManager fragmentManager;
        fragmentManager = getSupportFragmentManager();
        terminalEnvioPopUp.show(fragmentManager, "");
    }

    private void cargarDatos() {
        correria = (Correria) getIntent().getSerializableExtra(Correria.class.getName());
        presentador.setCorreria(correria);
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
    }

    @Override
    public void ocultarBarraProgreso() {
        runOnUiThread(() -> {
            tvDescargando.setVisibility(View.GONE);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        presentador.mostrarAlertaWifi();
    }

    @Override
    public void mostrarMensajeError(String mensaje) {
        runOnUiThread(() -> {
            try {
                registrarLog(mensaje);
            } catch (IOException e) {
                e.printStackTrace();
            }
            pbEnvioWeb.setVisibility(View.INVISIBLE);
            tvMensajeResultadoDescarga.setBackgroundColor(getResources().getColor(R.color.rojo));
            tvMensajeResultadoDescarga.setText(mensaje);
            tvDescargando.setText("");
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
            pbEnvioWeb.setVisibility(View.INVISIBLE);
            tvMensajeResultadoDescarga.setBackgroundColor(getResources().getColor(R.color.verdeoscuro));
            tvMensajeResultadoDescarga.setText(mensaje);
            tvDescargando.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public void mostrarMensajeProceso(String mensaje,String log) {
        runOnUiThread(() -> {
            if (log.toUpperCase().equals("S")) {
                try {
                    registrarLog(mensaje);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            tvDescargando.setText(mensaje);
        });
    }

    @Override
    public void aumentarProgresoBarra(int progreso) {
    }

    @Override
    public void cerrar() {
        finish();
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public void iniciar(String numeroTerminal) {
        new Thread(() -> {
            pbEnvioWeb.setVisibility(View.VISIBLE);
            presentador.setNumeroTerminal(numeroTerminal);
            presentador.iniciarEnvio();
        }).start();
    }

    @Override
    public void onProgressChange(int current, int max) {
    }


    @Override
    public void mostrarBotonesCotinuarEnvio(int indiceAdjuntoActual) {
        runOnUiThread(() -> {
            this.indiceAdjuntoActual = indiceAdjuntoActual;
            llCotinuarEnvio.setVisibility(View.VISIBLE);
            tvDescargando.setText("");
        });
    }

    @OnClick(R.id.btnContinuarDescarga)
    public void continuarEnvio(){
        runOnUiThread(() -> {
            pbEnvioWeb.setVisibility(View.VISIBLE);
            llCotinuarEnvio.setVisibility(View.INVISIBLE);
            tvMensajeResultadoDescarga.setText("");
            tvMensajeResultadoDescarga.setBackgroundResource(0);
        });
        presentador.continuarDescargaAdjuntos(indiceAdjuntoActual);
    }

    @OnClick(R.id.btnCancelarDescarga)
    public void cancelarDescarga(){
        presentador.mostrarAlertaWifi();
        EnvioWebActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}