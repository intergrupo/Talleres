package com.example.santiagolopezgarcia.talleres.view.activities;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dominio.modelonegocio.Correria;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.helpers.Constantes;
import com.example.santiagolopezgarcia.talleres.presenters.EnvioDirectoPresenter;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IEnvioDirectoView;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public class EnvioDirectoActivity extends BaseActivity<EnvioDirectoPresenter> implements IEnvioDirectoView {

    @BindView(R.id.tvMensajeEnvioDirecto)
    TextView tvMensajeResultadoEnvioDirecto;
    @BindView(R.id.tvMensajeFaseEnvioDirecto)
    TextView tvEnviando;
    @BindView(R.id.pbEnvioDirecto)
    ProgressBar progressBar;
    @BindView(R.id.llContinuarEnvio)
    LinearLayout llContinuarEnvio;
    private ActionBar actionBar;
    private int indiceAdjuntoActual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_directo);
        ButterKnife.bind(this);
        dependencia.getContenedor().build().inject(this);
        presentador.adicionarVista(this);
        prenderBluetooth();
        configurarActionBar();
        runOnUiThread(() -> {
            progressBar.setVisibility(View.VISIBLE);
            obtenerParametros();
            presentador.iniciar();
        });
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    private void configurarActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    private void prenderBluetooth() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
            mBluetoothAdapter.startDiscovery();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void mostrarBarraProgreso() {

    }

    @Override
    public void mostrarMensajeProceso(String mensaje) {
        runOnUiThread(() -> {
            /*try {
                registrarLog(mensaje);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            tvEnviando.setText(mensaje);
        });
    }

    @Override
    public void aumentarProgresoBarra(int progreso) {
        /*runOnUiThread(() -> {
            numberProgressbar.incrementProgressBy(progreso);
        });*/
    }

    @Override
    public void cerrar() {
        eliminarCarpetaEnvioDirecto();
        finish();
    }

    private void eliminarCarpetaEnvioDirecto() {
//        FileManager.eliminarCarpetaRecursivo(new File(Constantes.traerRutaEnvioDirecto()));
    }

    @Override
    public void enviarZip() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        File file = new File(Constantes.traerRutaEnvioDirecto() +
                Constantes.NOMBRE_ARCHIVO_ZIP_ENVIO_DIRECTO);
        if (file.exists()) {
            sharingIntent.setType("application/abc");
            sharingIntent.setPackage("com.android.bluetooth");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            startActivity(Intent.createChooser(sharingIntent, "Share file"));
        } else {
            mostrarMensajeError("El archivo no existe");
        }

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
            progressBar.setVisibility(View.INVISIBLE);
            tvMensajeResultadoEnvioDirecto.setBackgroundColor(getResources().getColor(R.color.verdeoscuro));
            tvMensajeResultadoEnvioDirecto.setText(mensaje);
            tvEnviando.setVisibility(View.INVISIBLE);
            configurarActionBar();
        });
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
            tvMensajeResultadoEnvioDirecto.setBackgroundColor(getResources().getColor(R.color.rojo));
            tvMensajeResultadoEnvioDirecto.setText(mensaje);
            tvEnviando.setVisibility(View.INVISIBLE);
            configurarActionBar();
            habilitarRegreso();
        });

    }

    @Override
    public void habilitarRegreso() {
        runOnUiThread(() -> actionBar.setDisplayHomeAsUpEnabled(true));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                cerrar();
                EnvioDirectoActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
        }
        return true;
    }

    private void obtenerParametros() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(com.example.dominio.modelonegocio.Correria.class.getName())) {
                presentador.setCorreria((Correria) bundle.getSerializable(Correria.class.getName()));
            }
        }
    }

    @Override
    public void desaparecerBarraProgreso() {
        runOnUiThread(() ->
                progressBar.setVisibility(View.INVISIBLE)
        );

    }

    @Override
    public void onBackPressed() {

    }

    @OnClick(R.id.btnContinuarEnvio)
    public void continuarEnvio(){
        runOnUiThread(() -> {
            progressBar.setVisibility(View.VISIBLE);
            actionBar.setDisplayHomeAsUpEnabled(false);
            llContinuarEnvio.setVisibility(View.INVISIBLE);
            tvMensajeResultadoEnvioDirecto.setText("");
            tvMensajeResultadoEnvioDirecto.setBackgroundResource(0);
        });
        presentador.continuarDescargaAdjuntos(indiceAdjuntoActual);
    }

    @Override
    public void mostrarBotonesCotinuarEnvio(int indiceAdjuntoActual) {
        runOnUiThread(() -> {
            this.indiceAdjuntoActual = indiceAdjuntoActual;
            llContinuarEnvio.setVisibility(View.VISIBLE);
        });
    }
}
