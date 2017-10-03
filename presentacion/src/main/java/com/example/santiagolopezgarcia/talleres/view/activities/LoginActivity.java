package com.example.santiagolopezgarcia.talleres.view.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dominio.bussinesslogic.administracion.UsuarioBL;
import com.example.dominio.modelonegocio.Correria;
import com.example.dominio.modelonegocio.Usuario;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.presenters.LoginPresenter;
import com.example.santiagolopezgarcia.talleres.util.LectorCodigoBarra;
import com.example.santiagolopezgarcia.talleres.view.interfaces.Item;
import com.example.santiagolopezgarcia.talleres.view.interfaces.LoginView;
import com.example.santiagolopezgarcia.talleres.view.adapters.CorreriaSpinnerAdapter;
import com.example.santiagolopezgarcia.talleres.view.popups.CambioClavePopup;
import com.example.santiagolopezgarcia.talleres.view.popups.NumeroTerminalPopup;
import com.example.utilidades.helpers.DateHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnItemSelected;
import butterknife.Optional;

import static com.example.utilidades.helpers.DateHelper.getCurrentDate;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView,
        Item<Usuario>, LectorCodigoBarra.OnLecturaCodigoBarra {

    private final String NOMBREHILOCARGADATOS = "HiloIniciarLoginPresenter";
    @BindView(R.id.spListaCorreria)
    Spinner spListaCorreria;
    @BindView(R.id.btnIniciarSesion)
    Button btnIniciarSesion;
    @BindView(R.id.tvTerminal)
    TextView tvTerminal;
    @BindView(R.id.etIdentificacion)
    EditText etIdentificacion;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    @BindView(R.id.etClave)
    EditText etClave;
    @Inject
    UsuarioBL usuarioBL;
    @BindView(R.id.ContenedorPrincipal)
    CoordinatorLayout coordinatorLayout;
    private ProgressDialog pd;
    private LectorCodigoBarra lectorCodigoBarra;
    private String identificacionCodigoBarras = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mostrarBarraProgreso();
        ButterKnife.bind(this);
        dependencia.getContenedor().build().inject(this);
        presentador.adicionarVista(this);
        configurarActionBar();
        Thread thread = new Thread(() -> {
            presentador.iniciar();
        });
        thread.setName(NOMBREHILOCARGADATOS);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
        limpiarFocos();
        lectorCodigoBarra = new LectorCodigoBarra(this);
    }

    @OnFocusChange(R.id.etIdentificacion)
    public void onFocusChangedIdentificacion(View view, boolean hasFocus) {
        if (presentador.getLoginCodigoBarras() && hasFocus) {
            etIdentificacion.setText("");
            presentador.setLoginCodigoBarras(false);
            identificacionCodigoBarras = "";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_administracion_login:
                Intent intent = new Intent(LoginActivity.this, AdministracionActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            pd.dismiss();
        }
    };

    @Override
    public void mostrarListaCorrerias(final List<Correria> listaCorrerias) {
        runOnUiThread(() -> {
            spListaCorreria.setAdapter(new CorreriaSpinnerAdapter(getContext(), listaCorrerias));
        });
    }

    @Override
    public String obtenerUsuario() {
        if (identificacionCodigoBarras.isEmpty())
            return etIdentificacion.getText().toString();
        else
            return identificacionCodigoBarras;
    }

    @Override
    public String obtenerClave() {
        return etClave.getText().toString();
    }

    @Override
    public void mostrarCambioClave(Usuario usuario) {
        CambioClavePopup cambioClavePopup = new CambioClavePopup();
        cambioClavePopup.setUsuario(usuario);
        Bundle args = new Bundle();
        if (identificacionCodigoBarras.isEmpty())
            args.putString("textoIdentificacion", etIdentificacion.getText().toString());
        else
            args.putString("textoIdentificacion", identificacionCodigoBarras);
        FragmentManager fragmentManager = getSupportFragmentManager();
        cambioClavePopup.setCambioClavePopupPopup(this);
        cambioClavePopup.setArguments(args);
        cambioClavePopup.show(fragmentManager, "");
    }

    @OnClick(R.id.btnIniciarSesion)
    public void iniciarSesion(View view) {
        if (!etIdentificacion.getText().toString().isEmpty()) {
            presentador.validarAcceso();
        } else {
            mostrarMensajeError("Debe ingresar su identificación");
        }
    }

    @Override
    public void mostrarCorrerias() {
        SiriusApp app = (SiriusApp) getApplication();
        app.setUsuario(presentador.getUsuario());
        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.putExtra(Correria.class.getName(), presentador.getCorreriaActual());
        dependencia.getApp().setCodigoCorreria(presentador.getCorreriaActual().getCodigoCorreria());
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    @Override
    public void mostrarBarraProgreso() {
        mostrarBarraProgreso("Cargando Datos...");
    }

    @Override
    public void ocultarBarraProgreso() {
        super.ocultarBarraProgreso();
    }

    @Override
    public void limpiarControlesEdicion() {
        etClave.getText().clear();
    }

    @Override
    public void mostrarNumeroTerminal(String numeroTerminal) {
        app.setCodigoTerminal(numeroTerminal);
        runOnUiThread(() -> {
            tvTerminal.setText(numeroTerminal);
            tvVersion.setText("Versión " + getResources().getString(R.string.version));
        });

    }

    @Override
    public void mostrarPopUpNumeroTerminal() {
        NumeroTerminalPopup numeroTerminalPopup = new NumeroTerminalPopup();
        FragmentManager fragmentManager;
        fragmentManager = getSupportFragmentManager();
        numeroTerminalPopup.show(fragmentManager, "");
    }

    @Override
    public void bloquearTipoIdentificacionDigitado(boolean bloquear) {
        runOnUiThread(() -> {
            etIdentificacion.setEnabled(bloquear);
            etIdentificacion.setTextColor(getResources().getColor(R.color.negro));
        });
    }

    private void asignarFocoControlClave() {
        etClave.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(etClave, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void configurarActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.verdeoscuro)));
        LayoutInflater mInflater = LayoutInflater.from(this);
        View vistaActionBar = mInflater.inflate(R.layout.actionbar_general, null);
        ImageButton btnAtras = (ImageButton) vistaActionBar.findViewById(R.id.imAtras);
        btnAtras.setVisibility(View.GONE);
        TextView tvTitulo = (TextView) vistaActionBar.findViewById(R.id.tvTitulo);
        actionBar.setCustomView(vistaActionBar);
        actionBar.setDisplayShowCustomEnabled(true);
        tvTitulo.setText(getCurrentDate(DateHelper.TipoFormato.ddMMMyyyy));
        tvTitulo.setTextColor(getResources().getColor(R.color.negro));
        tvTitulo.setBackground(new ColorDrawable(getResources().getColor(R.color.grisfondo)));
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.grisfondo)));
    }

    @Optional
    @OnItemSelected(R.id.spListaCorreria)
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position > 0)
            presentador.setCorreriaActual((Correria) (parent.getItemAtPosition(position)));
        else
            presentador.setCorreriaActual(null);
    }

    @Override
    public void onLeerCodigoBarra(String codigoBarras, String tipoLectura) {
        identificacionCodigoBarras = usuarioBL.obtenerCodigoUsuarioDeCodigoBarras(codigoBarras, tipoLectura);
        runOnUiThread(() -> {
            presentador.setLoginCodigoBarras(true);
            etIdentificacion.setText(identificacionCodigoBarras.substring(
                    0, identificacionCodigoBarras.length() - 1));
            asignarFocoControlClave();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode && requestCode == 0) {
            presentador.actualizarDatos();
        }
    }

    @Override
    public void onErrorCodigoBarra(String error) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lectorCodigoBarra.cerrar();
        presentador.detener();
    }

    @Override
    public void itemProcesado(Usuario usuario) {
        etClave.getText().clear();
        etClave.requestFocus();
        etClave.postDelayed(() -> {
            InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.showSoftInput(etClave, 0);
        }, 50);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        configurarActionBar();
    }
}
