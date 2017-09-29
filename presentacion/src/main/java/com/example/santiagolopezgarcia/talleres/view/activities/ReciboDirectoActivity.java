package com.example.santiagolopezgarcia.talleres.view.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Bundle;
import android.os.FileObserver;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.helpers.Constantes;
import com.example.santiagolopezgarcia.talleres.integracion.EstadoConexionTerminal;
import com.example.santiagolopezgarcia.talleres.presenters.ReciboDirectoPresenter;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IReciboDirectoView;
import com.example.santiagolopezgarcia.talleres.view.popups.AlertaPopUp;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */
public class ReciboDirectoActivity extends BaseActivity<ReciboDirectoPresenter> implements IReciboDirectoView {

    @BindView(R.id.tvMensajeCarga)
    TextView tvMensajeResultadoCarga;
    @BindView(R.id.pbReciboDirecto)
    ProgressBar progressBar;
    @BindView(R.id.tvMensajeAdvertenciaConexion)
    TextView textViewAdvertenciaConexion;
    @BindView(R.id.tvMensajeCargando)
    TextView tvCargando;
    @BindView(R.id.tvMensajeFase)
    TextView textViewFase;
    private ActionBar actionBar;
    private FileObserver fileObserver;
    private ProgressDialog pd;
    private boolean fueCargaSatisfactoria = false;
    public static final int REQUEST_CODE = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recibo_directo);
        ButterKnife.bind(this);
        dependencia.getContenedor().build().inject(this);
        presentador.adicionarVista(this);
        prenderBluetooth();
        configurarActionBar();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        runOnUiThread(() -> {
            progressBar.setVisibility(View.VISIBLE);
            presentador.iniciar();
            integrarDatos();
        });
    }

    private void prenderBluetooth() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
            mBluetoothAdapter.startDiscovery();
        }
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
    public void aumentarProgresoBarra(int progreso) {
        //runOnUiThread(() -> pbEnvioWeb.setProgress(progreso));
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
        runOnUiThread(() ->{
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
    public void iniciarObserver() {
        new File(Constantes.traerRutaReciboDirecto()).mkdirs();
        File filePath = new File(Constantes.traerRutaBluetooth());
        if (filePath.exists()) {
            fileObserver = new FileObserver(filePath.getAbsolutePath()) {

                @Override
                public void onEvent(int event, String file) {
                    if (event == FileObserver.CREATE) {
                        Log.d("", "File created [" + filePath.getAbsolutePath() + file + "]");

                        runOnUiThread(() -> Toast.makeText(ReciboDirectoActivity.this,
                                file + " Llegó el archivo!", Toast.LENGTH_LONG).show());
                    }
                }
            };
            fileObserver.startWatching();
        }
    }

    public void integrarDatos() {
        pd = ProgressDialog.show(ReciboDirectoActivity.this, "Integración",
                "Descomprimiendo zip...");
        new Thread(() -> {
            if (!new File(Constantes.traerRutaReciboDirecto() +
                    Constantes.NOMBRE_ARCHIVO_ZIP_ENVIO_DIRECTO).exists()) {
                presentador.moverArchivoASirius();
            }
            presentador.descomprimirZip();
            handler.sendEmptyMessage(0);
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            pd.dismiss();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                cerrar();
                break;
        }
        return true;
    }

    @Override
    public void establecerCargaSatisfactoria() {
        this.fueCargaSatisfactoria = true;
        //FileManager.deleteFolderContent(Constantes.traerRutaReciboDirecto());
    }

    @Override
    public void habilitarRegreso(boolean habilitar) {
        runOnUiThread(() -> actionBar.setDisplayHomeAsUpEnabled(true));
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
    public void cerrar() {
        eliminarCarpetaReciboDirecto();
        int resultCode = fueCargaSatisfactoria ? Activity.RESULT_OK : Activity.RESULT_CANCELED;
        setResult(resultCode);
        finish();
    }

    private void eliminarCarpetaReciboDirecto() {
        File carpetaReciboDirecto = new File(Constantes.traerRutaReciboDirecto());
        if(carpetaReciboDirecto.exists()){
            carpetaReciboDirecto.delete();
        }
    }

    @Override
    public void mostrarConfirmacionActualizarMaestros(String mensaje) {

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
            //pbEnvioWeb.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
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
            progressBar.setVisibility(View.INVISIBLE);
            //pbEnvioWeb.setVisibility(View.INVISIBLE);
            configurarActionBar();
        });
    }
}
