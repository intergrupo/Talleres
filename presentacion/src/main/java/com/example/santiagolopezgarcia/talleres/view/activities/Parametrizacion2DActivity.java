package com.example.santiagolopezgarcia.talleres.view.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.presenters.Parametrizacion2DPresenter;
import com.example.santiagolopezgarcia.talleres.services.dto.Parametrizacion2D;
import com.example.santiagolopezgarcia.talleres.util.LectorCodigoBarra;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IParametrizacion2DView;
import com.example.utilidades.helpers.DateHelper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.utilidades.helpers.DateHelper.getCurrentDate;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class Parametrizacion2DActivity extends BaseActivity<Parametrizacion2DPresenter> implements
        IParametrizacion2DView, LectorCodigoBarra.OnLecturaCodigoBarra {

    @BindView(R.id.tvVersionParam2D)
    TextView tvVersion;
    @BindView(R.id.tvFechaParam2D)
    TextView tvFecha;
    @BindView(R.id.tvVencimientoParam2D)
    TextView tvVencimiento;
    @BindView(R.id.etDescripcionParam2D)
    EditText etDescripcion;
    @BindView(R.id.tvFechaActualSofware)
    TextView tvFechaActualSoftware;
    @BindView(R.id.btnAplicarParametrizacion)
    Button btnAplicarParametrizacion;
    @BindView(R.id.tvNotaParametrizacion)
    TextView tvNotaParametrizacion;
    private ActionBar actionBar;
    private LectorCodigoBarra lectorCodigoBarra;
    private String NOMBRE_ARCHIVO_XML_PARAM_2D = "parametrizacion.xml";
    private Parametrizacion2D parametrizacion2D;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametrizacion_2d);
        ButterKnife.bind(this);
        dependencia.getContenedor().build().inject(this);
        presentador.adicionarVista(this);
        configurarActionBar();
        presentador.iniciar();
        lectorCodigoBarra = new LectorCodigoBarra(this);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        iniciarValores();
    }

    private void iniciarValores() {
        tvFechaActualSoftware.setText(getCurrentDate(DateHelper.TipoFormato.ddMMMyyyy));
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void mostrarBarraProgreso() {

    }

    private void configurarActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(
                        getBaseContext().getPackageName() );
                intent .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLeerCodigoBarra(String codigoBarras, String tipoLectura) {
        if (tipoLectura.equals("r")) {
            File archivo = presentador.getGeneradorXmlHelper()
                    .generarXml(codigoBarras, NOMBRE_ARCHIVO_XML_PARAM_2D);
            Parametrizacion2D _parametrizacion2D = null;
            try {
                _parametrizacion2D = presentador.leerXmlCodigoBarras(archivo);
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    registrarLog(e.getMessage());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if(_parametrizacion2D != null) {
                this.parametrizacion2D = _parametrizacion2D;
                validarXML();
                try {
                    registrarLog(codigoBarras);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                runOnUiThread(() -> {
                    btnAplicarParametrizacion.setVisibility(View.GONE);
                    tvNotaParametrizacion.setVisibility(View.VISIBLE);
                });
                mostrarMensajeError("El código de barras no es válido.");
                limpiarCampos();
            }


        } else {
            runOnUiThread(() -> {
                btnAplicarParametrizacion.setVisibility(View.GONE);
                tvNotaParametrizacion.setVisibility(View.VISIBLE);
            });
            mostrarMensajeError("El código de barras no es válido.");
            limpiarCampos();
        }
    }

    @Override
    public void onErrorCodigoBarra(String error) {
    }

    @Override
    public void mostrarDatosCodigoBarras(Parametrizacion2D parametrizacion2D) {
        runOnUiThread(() -> {
            tvFecha.setText(parametrizacion2D.Fecha);
            tvVersion.setText(parametrizacion2D.Version);
            tvVencimiento.setText(parametrizacion2D.Vencimiento);
            etDescripcion.setText(parametrizacion2D.Descripcion);
            btnAplicarParametrizacion.setVisibility(View.VISIBLE);
            tvNotaParametrizacion.setVisibility(View.GONE);
        });
    }

    @Override
    public void aplicarParametrizacion(String codigoBarras, Parametrizacion2D parametrizacion2D) {
        try {
            presentador.procesarInformacion(parametrizacion2D);
        } catch (Exception e) {
            try {
                registrarLog(e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            mostrarMensajeError(getString(R.string.operacion_no_realizada));
            e.printStackTrace();
        }
    }

    @Override
    public void limpiarCampos() {
        runOnUiThread(() -> {
            tvVersion.setText("");
            tvFecha.setText("");
            tvVencimiento.setText("");
            etDescripcion.setText("");
        });
    }

    private void validarXML(){
        try {
            if (!esVersionIgual()){
                mostrarMensajeError(getResources().getString(R.string.version_2d_no_coincide));
                runOnUiThread(() -> btnAplicarParametrizacion.setVisibility(View.GONE));
            }else if(!esVencido()){
                mostrarMensajeError(getResources().getString(R.string.codigo_barras_vencido_2d));
                runOnUiThread(() -> btnAplicarParametrizacion.setVisibility(View.GONE));
            }
        }catch (ParseException e){
            mostrarMensajeError(getResources().getString(R.string.formato_vencimiento_invalido));
            runOnUiThread(() -> btnAplicarParametrizacion.setVisibility(View.GONE));
            e.printStackTrace();
        }
    }

    private boolean esVersionIgual(){
        return presentador.validarVersionApp(parametrizacion2D.Version,
                getResources().getString(R.string.version)) || parametrizacion2D.Version.isEmpty();
    }


    private boolean esVencido() throws ParseException{
        return DateHelper.convertirStringADate(parametrizacion2D.Vencimiento, DateHelper.TipoFormato.ddMMyyyy)
                .after(DateHelper.convertirStringADate(
                        getCurrentDate(DateHelper.TipoFormato.ddMMyyyy),
                        DateHelper.TipoFormato.ddMMyyyy));
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            pd.dismiss();
        }
    };

    @OnClick(R.id.btnAplicarParametrizacion)
    public void aplicar(){
        pd = ProgressDialog.show(this,"Datos","Aplicando Parametrización...");
        new Thread(() -> {
            aplicarParametrizacion(etDescripcion.getText().toString(), parametrizacion2D);
            handler.sendEmptyMessage(0);
        }).start();
        btnAplicarParametrizacion.setVisibility(View.GONE);
        limpiarCampos();
        tvNotaParametrizacion.setVisibility(View.VISIBLE);
    }
}