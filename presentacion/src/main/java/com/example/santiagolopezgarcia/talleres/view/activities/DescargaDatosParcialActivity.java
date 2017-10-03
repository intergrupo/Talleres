package com.example.santiagolopezgarcia.talleres.view.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dominio.modelonegocio.Correria;
import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.presenters.DescargaDatosParcialPresenter;
import com.example.santiagolopezgarcia.talleres.util.DatosCache;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IDescargaDatosParcialView;
import com.example.santiagolopezgarcia.talleres.view.interfaces.ISincronizacionView;
import com.example.utilidades.helpers.DateHelper;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by santiagolopezgarcia on 10/3/17.
 */

public class DescargaDatosParcialActivity extends BaseActivity<DescargaDatosParcialPresenter>
        implements IDescargaDatosParcialView, ISincronizacionView {


    @BindView(R.id.tvMensajeDescarga)
    TextView tvMensajeResultadoDescarga;
    @BindView(R.id.tvMensajeDescargando)
    TextView tvDescargando;
    @BindView(R.id.pbDescargar)
    ProgressBar progressBar;
    @BindView(R.id.btnDescargar)
    Button btnDescargar;
    @BindView(R.id.btnCancelar)
    Button btnCancelar;
    @BindView(R.id.tvMensajeCantidadLecturaNumero)
    TextView tvMensajeCantidadLecturaNumero;
    @BindView(R.id.tvMensajeCantidadLectura)
    TextView TvMensajeCantidadLectura;
    @BindView(R.id.tvNumeroTerminal)
    TextView tvNumeroTerminal;
    @BindView(R.id.tvNumeroCorreria)
    TextView tvNumeroCorreria;
    @BindView(R.id.tvNumeroUsuario)
    TextView tvNumeroUsuario;
    @BindView(R.id.tvNombreUsuario)
    TextView tvNombreUsuario;
    @BindView(R.id.tvArea)
    TextView tvArea;
    @BindView(R.id.tvFechaActual)
    TextView tvFechaActual;
    @BindView(R.id.tvNombreCorreria)
    TextView tvNombreCorreria;
    @BindView(R.id.btnContinuarDescarga)
    Button btnContinuarDescarga;
    private ActionBar actionBar;
    private Correria correria;
    private int LISTAOT = 3;
    private int indiceAdjuntoActual = 0;
    private boolean comenzarDescarga;
    private boolean refrescar;
    private int posicionActualOT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descarga_datos_parcial);
        ButterKnife.bind(this);
        dependencia.getContenedor().build().inject(this);
        presentador.adicionarVista(this);
        configurarActionBar();
        obtenerParametros();
        asignarDatos();
        validarFiltro();
        if (comenzarDescarga)
            descargar();
    }

    private void asignarDatos() {
        tvNumeroTerminal.setText(app.getCodigoTerminal());
        tvNumeroUsuario.setText(app.getUsuario().getCodigoUsuario());
        tvFechaActual.setText(DateHelper.getCurrentDate(DateHelper.TipoFormato.ddMMMyyyy));
        tvNombreUsuario.setText(app.getUsuario().getNombre());
        if (correria != null) {
            tvNumeroCorreria.setText(correria.getCodigoCorreria());
            tvNombreCorreria.setText(correria.getDescripcion());
            tvArea.setText(correria.getInformacion());
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void mostrarMensajeProceso(String mensaje, String log) {
        runOnUiThread(() -> {
            tvDescargando.setText(mensaje);
            if (log.toUpperCase().equals("S")) {
                try {
                    registrarLog(mensaje);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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
    public void mostrarMensajeError(String mensaje) {
        runOnUiThread(() -> {
            progressBar.setVisibility(View.GONE);
            tvMensajeResultadoDescarga.setBackgroundColor(getResources().getColor(R.color.rojo));
            tvMensajeResultadoDescarga.setText(mensaje);
            try {
                registrarLog(mensaje);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void mostrarMensaje(String mensaje, String log) {
        runOnUiThread(() -> {
            progressBar.setVisibility(View.GONE);
            tvMensajeResultadoDescarga.setBackgroundColor(getResources().getColor(R.color.verdeoscuro));
            tvMensajeResultadoDescarga.setText(mensaje);
            if (log.toUpperCase().equals("S")) {
                try {
                    registrarLog(mensaje);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick(R.id.btnDescargar)
    public void descargar(View view) {
        descargar();
    }

    @OnClick(R.id.btnCancelar)
    public void cancelar(View view) {
        cerrar();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                cerrar();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void configurarActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void habilitarRegreso() {
        runOnUiThread(() -> actionBar.setDisplayHomeAsUpEnabled(true));
    }

    @Override
    public void cerrar() {
        if (refrescar) {
            Intent intent = new Intent(DescargaDatosParcialActivity.this, PrincipalActivity.class);
            intent.putExtra("Posicion", posicionActualOT);
            intent.putExtra(Correria.class.getName(), presentador.getCorreriaActual(app.getCodigoCorreria()));
            intent.putExtra("Sincronizacion", true);
            startActivity(intent);
            finish();
        } else onBackPressed();
    }

    private void obtenerParametros() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("tieneFiltroActivo")) {
                presentador.setTieneFiltroActivo(getIntent().getBooleanExtra("tieneFiltroActivo", false));
            }
            if (bundle.containsKey(OrdenTrabajoBusqueda.class.getName())) {
                presentador.setOrdenTrabajoBusqueda((OrdenTrabajoBusqueda) bundle.getSerializable(OrdenTrabajoBusqueda.class.getName()));
            }
            if (bundle.containsKey(Correria.class.getName())) {
                presentador.setCorreria((Correria) bundle.getSerializable(Correria.class.getName()));
            }
            if (bundle.containsKey(Correria.class.getName())) {
                correria = (Correria) bundle.getSerializable(Correria.class.getName());
            }
            if (bundle.containsKey("comenzarDescarga")) {
                comenzarDescarga = getIntent().getBooleanExtra("comenzarDescarga", false);
            }
            presentador.setPosicionActiva(bundle.containsKey("Posicion") ? bundle.getInt("Posicion") : 0);
        }
    }

    private void validarFiltro() {
        if (!presentador.getTieneFiltroActivo() || presentador.getOrdenTrabajoBusqueda() == null) {
            mostrarMensajeError(getString(R.string.mensaje_sin_criterio_de_busqueda));
            btnDescargar.setEnabled(false);
            btnDescargar.setTextColor(Color.GRAY);
        } else {
            TvMensajeCantidadLectura.setVisibility(View.VISIBLE);
            tvMensajeCantidadLecturaNumero.setVisibility(View.VISIBLE);
            tvMensajeCantidadLecturaNumero.setText(presentador.getOrdenTrabajoBusqueda().getListaOrdenTrabajo().size() > 0 ?
                    String.valueOf(presentador.getOrdenTrabajoBusqueda().getListaOrdenTrabajo().size()) :
                    String.valueOf(DatosCache.getListaOrdenTrabajoFiltrado().size()));
        }
    }

    @Override
    public void mostrarBontonesContinuarDescarga(int indiceAdjuntoActual) {
        runOnUiThread(() -> {
            this.indiceAdjuntoActual = indiceAdjuntoActual;
            btnContinuarDescarga.setVisibility(View.VISIBLE);
        });
    }

    @OnClick(R.id.btnContinuarDescarga)
    public void continuarDescarga() {
        runOnUiThread(() -> {
            progressBar.setVisibility(View.VISIBLE);
            actionBar.setDisplayHomeAsUpEnabled(false);
            btnContinuarDescarga.setVisibility(View.INVISIBLE);
            tvMensajeResultadoDescarga.setText("");
            tvMensajeResultadoDescarga.setBackgroundResource(0);
        });
        presentador.continuarDescargaAdjuntos(indiceAdjuntoActual);
    }

    private void descargar() {
        mostrarBarraProgreso();
        btnDescargar.setEnabled(false);
        btnDescargar.setTextColor(Color.GRAY);
        mostrarBarraProgreso();
        progressBar.setVisibility(View.VISIBLE);
        new Thread(() -> {
            try {
                presentador.descargar();
            } catch (IOException e) {
                try {
                    registrarLog(e.getMessage());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void regresarAPantallaPrincipal(int posicion) {
        refrescar = true;
        posicionActualOT = posicion;
    }
}