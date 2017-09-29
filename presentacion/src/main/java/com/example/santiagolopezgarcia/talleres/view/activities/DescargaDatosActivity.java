package com.example.santiagolopezgarcia.talleres.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dominio.modelonegocio.Correria;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.presenters.DescargaDatosPresenter;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IDescargaDatosView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public class DescargaDatosActivity extends BaseActivity<DescargaDatosPresenter>
        implements IDescargaDatosView {

    @BindView(R.id.tvMensajeDescarga)
    TextView tvMensajeResultadoDescarga;
    @BindView(R.id.tvMensajeDescargando)
    TextView tvDescargando;
    @BindView(R.id.pbDescargar)
    ProgressBar progressBar;
    @BindView(R.id.llContinuarDescarga)
    LinearLayout llContinuarDescarga;
    private ActionBar actionBar;
    private Correria correria;
    public static final int REQUEST_CODE = 12;
    private static final String INSTANCECORRERIA = "correria";
    private int indiceAdjuntoActual = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            refrescarReferencias(savedInstanceState);
        setContentView(R.layout.activity_descarga_datos);
        ButterKnife.bind(this);
        dependencia.getContenedor().build().inject(this);
        presentador.setApp(dependencia.getApp());
        presentador.adicionarVista(this);
        configurarActionBar();
        cargarDatos();
        mostrarBarraProgreso();
        new Thread(() -> {
            progressBar.setVisibility(View.VISIBLE         );
            presentador.iniciar();
        }).start();
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(INSTANCECORRERIA,correria);
        super.onSaveInstanceState(outState);
    }

    private void refrescarReferencias(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(INSTANCECORRERIA))
            correria = (Correria) savedInstanceState.getSerializable(INSTANCECORRERIA);
    }


    private void cargarDatos() {
        correria = (Correria) getIntent().getSerializableExtra(Correria.class.getName());
        presentador.setCorreria(correria);
    }

    private void configurarActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
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
                presentador.mostrarAlertaWifi();
                DescargaDatosActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
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
            progressBar.setVisibility(View.GONE);
            actionBar.setDisplayHomeAsUpEnabled(true);
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
            progressBar.setVisibility(View.GONE);
            actionBar.setDisplayHomeAsUpEnabled(true);
            tvMensajeResultadoDescarga.setBackgroundColor(getResources().getColor(R.color.verdeoscuro));
            tvMensajeResultadoDescarga.setText(mensaje);
            tvDescargando.setText("");
            Handler handler = new Handler();
            handler.post(() -> presentador.preguntarEliminarCorreria());
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
    public void cerrar() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void irAlLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void mostrarBontonesContinuarDescarga(int indiceAdjuntoActual) {
        runOnUiThread(() -> {
            this.indiceAdjuntoActual = indiceAdjuntoActual;
            llContinuarDescarga.setVisibility(View.VISIBLE);
        });
    }

    @OnClick(R.id.btnContinuarDescarga)
    public void continuarDescarga(){
        runOnUiThread(() -> {
            progressBar.setVisibility(View.VISIBLE);
            actionBar.setDisplayHomeAsUpEnabled(false);
            tvMensajeResultadoDescarga.setText("");
            tvMensajeResultadoDescarga.setBackgroundResource(0);
            llContinuarDescarga.setVisibility(View.INVISIBLE);
        });
        presentador.continuarDescargaAdjuntos(indiceAdjuntoActual);
    }

    @OnClick(R.id.btnCancelarDescarga)
    public void cancelarDescarga(){
        presentador.mostrarAlertaWifi();
        DescargaDatosActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

}