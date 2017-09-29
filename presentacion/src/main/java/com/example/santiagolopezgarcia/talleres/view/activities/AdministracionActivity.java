package com.example.santiagolopezgarcia.talleres.view.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dominio.modelonegocio.Opcion;
import com.example.dominio.modelonegocio.ParametrosOpcionAdministracion;
import com.example.dominio.modelonegocio.Talleres;
import com.example.dominio.modelonegocio.Usuario;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.helpers.Constantes;
import com.example.santiagolopezgarcia.talleres.presenters.AdministracionPresenter;
import com.example.santiagolopezgarcia.talleres.view.adapters.TabVistaPaginasAdministracionAdapter;
import com.example.santiagolopezgarcia.talleres.view.fragments.AdministracionCorreriaFragment;
import com.example.santiagolopezgarcia.talleres.view.fragments.AdministracionUsuarioFragment;
import com.example.santiagolopezgarcia.talleres.view.fragments.ConfiguracionFragment;
import com.example.santiagolopezgarcia.talleres.view.popups.NuevoUsuarioPopUp;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IAdministracionView;
import com.example.santiagolopezgarcia.talleres.view.popups.AlertaPopUp;
import com.example.utilidades.helpers.DateHelper;
import com.example.utilidades.helpers.WifiHelper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class AdministracionActivity extends BaseActivity<AdministracionPresenter> implements
        IAdministracionView {

    @BindView(R.id.tabLayoutAdministracion)
    TabLayout tabLayoutAdmin;
    @BindView(R.id.tvFecha)
    TextView tvFecha;
    @BindView(R.id.tvCorreriaActiva)
    TextView tvCorreriaActiva;
    @BindView(R.id.tvTerminal)
    TextView tvTerminal;
    @BindView(R.id.tvVersionMaestros)
    TextView tvVersionMaestros;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    @BindView(R.id.tvVersionParametrizacion)
    TextView tvVersionParametrizacion;
    @BindView(R.id.tvTituloParametrizacion)
    TextView tvTituloParametrizacion;
    @BindView(R.id.viewPagerAdministracion)
    ViewPager viewPager;
    @BindView(R.id.lyOpcionesCorreria)
    LinearLayout lyOpcionesCorreria;
    @BindView(R.id.lyCargaCentral)
    LinearLayout lyCargaCentral;
    @BindView(R.id.lyReciboDirecto)
    LinearLayout lyReciboDirecto;
    @BindView(R.id.lyReciboWeb)
    LinearLayout lyReciboWeb;
    @BindView(R.id.lyActualizarMaestros)
    LinearLayout lyActualizarMaestros;
    SiriusApp app;
    private boolean irALogin;
    private WifiHelper wifiHelper;
    TabVistaPaginasAdministracionAdapter tabVistaPaginasAdministracionAdapter;
    private ActionBar actionBar;
    public int IDENTIFICADOR_CORRERIA = 0;
    public int IDENTIFICADOR_USUARIO = 1;
    private static final int DURACION = 300;
    private static final int VIA_BLUETOOTH = 1;
    public int IDENTIFICADOR_CONFIGURACION = 2;
    private MenuItem nuevoUsuarioMenu;
    //private MenuItem configuracionWifiMenu;
    private MenuItem finalizarSiriusMenu;
    public Talleres talleres;
    ProgressDialog progressDialog;
    private static String INSTANCESIRIUS = "talleres";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            refrescarReferencias(savedInstanceState);
        setContentView(R.layout.activity_administracion);
        ButterKnife.bind(this);
        dependencia.getContenedor().build().inject(this);
        presentador.adicionarVista(this);
        configurarActionBar();
        presentador.iniciar();
        obtenerParametros();
        try {
            cargarDatos();
        } catch (IOException e) {
            e.printStackTrace();
        }
        asignarViewPager(viewPager);
        agregarTabs();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(INSTANCESIRIUS, talleres);
        super.onSaveInstanceState(outState);
    }

    private void refrescarReferencias(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(INSTANCESIRIUS))
            talleres = (Talleres) savedInstanceState.getSerializable(INSTANCESIRIUS);
    }

    private void obtenerParametros() {
        presentador.setOpcion((Opcion) getIntent().getSerializableExtra(Opcion.class.getName()));
        if (presentador.getOpcion() != null) {
            presentador.setParametrosOpcionAdministracion(
                    new ParametrosOpcionAdministracion(presentador.getOpcion().getParametros()));
        } else {
            presentador.setParametrosOpcionAdministracion(
                    new ParametrosOpcionAdministracion(""));
        }
    }

    private void cargarDatos() throws IOException {
        wifiHelper = new WifiHelper(this);
        app = (SiriusApp) getApplication();
        talleres = presentador.consultarTerminalXCodigo(app.getCodigoTerminal());
        tvVersion.setText("Versión " + getResources().getString(R.string.version));
        try {
            if (talleres.getFechaMaestros() != null)
                tvFecha.setText(DateHelper.convertirDateAString(talleres.getFechaMaestros(), DateHelper.TipoFormato.ddMMMyyyy));
        } catch (ParseException e) {
            e.printStackTrace();
            registrarLog(e.getMessage());
        }
        tvVersionMaestros.setText(talleres.getVersionMaestros() + " - ");
        if (!talleres.getVersionParametrizacion().isEmpty()) {
            tvTituloParametrizacion.setVisibility(View.VISIBLE);
            tvVersionParametrizacion.setText(talleres.getVersionParametrizacion());
        }
        tvTerminal.setText(app.getCodigoTerminal());
        if (app.getCodigoCorreria() != null) {
            tvCorreriaActiva.setText("Correría activa:  " + app.getCodigoCorreria());
        } else {
            tvCorreriaActiva.setVisibility(View.INVISIBLE);
        }
    }

    private void agregarTabs() {
        tabLayoutAdmin.setupWithViewPager(viewPager);
    }

    private void asignarViewPager(ViewPager viewPager) {
        tabVistaPaginasAdministracionAdapter = new TabVistaPaginasAdministracionAdapter(
                getSupportFragmentManager(), talleres, presentador.getOpcion());
        viewPager.setAdapter(tabVistaPaginasAdministracionAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                invalidateOptionsMenu();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void mostrarOpciones(MenuItem opciones) {
        if (viewPager.getCurrentItem() == IDENTIFICADOR_CORRERIA) {
            nuevoUsuarioMenu.setVisible(false);
            if (!app.getUsuario().esUsuarioValido())
                lyOpcionesCorreria.setVisibility(View.VISIBLE);
            tvCorreriaActiva.setVisibility(View.VISIBLE);
            lyOpcionesCorreria.setVisibility(View.VISIBLE);
            //configuracionWifiMenu.setVisible(false);
            finalizarSiriusMenu.setVisible(false);
            opciones.setVisible(false);
        } else if (viewPager.getCurrentItem() == IDENTIFICADOR_USUARIO) {
            if (!app.getUsuario().esUsuarioValido()
                    || presentador.getParametrosOpcionAdministracion().isAdminPersonalizada()) {
                nuevoUsuarioMenu.setVisible(false);
            } else {
                nuevoUsuarioMenu.setVisible(true);
            }
            //configuracionWifiMenu.setVisible(false);
            opciones.setVisible(false);
            finalizarSiriusMenu.setVisible(false);
            lyOpcionesCorreria.setVisibility(View.GONE);
            tvCorreriaActiva.setVisibility(View.GONE);
        } else {
            //configuracionWifiMenu.setVisible(true);
            if (!app.getUsuario().esUsuarioValido()
                    || presentador.getParametrosOpcionAdministracion().isAdminPersonalizada()) {
                opciones.setVisible(false);
            } else {
                opciones.setVisible(true);
            }
            finalizarSiriusMenu.setVisible(true);
            nuevoUsuarioMenu.setVisible(false);
            lyOpcionesCorreria.setVisibility(View.GONE);
            tvCorreriaActiva.setVisibility(View.GONE);
        }
    }

    private void configurarActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void adicionarUsuario(Usuario usuario) {
        Fragment fragment = tabVistaPaginasAdministracionAdapter.getItem(IDENTIFICADOR_USUARIO);
        ((AdministracionUsuarioFragment) fragment).adicionarUsuario(usuario);
    }

    @Override
    public void desactivarOpcionesCorreria(boolean activar) {
        if (activar) {
            lyOpcionesCorreria.setVisibility(View.VISIBLE);
        } else {
            lyOpcionesCorreria.setVisibility(View.GONE);
        }
    }

    @Override
    public void cerrarSesion() {
        ((SiriusApp) getApplication()).limpiarSesion();
        startActivity(new Intent(getContext(), LoginActivity.class));
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        this.finish();
    }

    @Override
    public void refrescarUrl(String url) {
        Fragment fragment = tabVistaPaginasAdministracionAdapter.getItem(
                TabVistaPaginasAdministracionAdapter.IDENTIFICADOR_CONFIGURACION);
        ((ConfiguracionFragment) fragment).setUrl(url);
    }

    @Override
    public void salir() {
        if (irALogin) {
            cerrarSesion();
        } else {
            finish();
            AdministracionActivity.this.
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    @OnClick(R.id.lyActualizarMaestros)
    public void actualizarMaestros() {
        presentador.borrarMaestros();
    }

    @Override
    public void actualizarMaestrosVista() {
        ((AdministracionCorreriaFragment) tabVistaPaginasAdministracionAdapter
                .getItem(TabVistaPaginasAdministracionAdapter.IDENTIFICADOR_CORRERIAS))
                .iniciar();
        ((AdministracionUsuarioFragment) tabVistaPaginasAdministracionAdapter
                .getItem(TabVistaPaginasAdministracionAdapter.IDENTIFICADOR_USUARIOS))
                .iniciar();

        app.limpiarSesion();
        refrescarDatos(app);
        actualizarVista();
        ((AdministracionUsuarioFragment) tabVistaPaginasAdministracionAdapter
                .getItem(TabVistaPaginasAdministracionAdapter.IDENTIFICADOR_USUARIOS))
                .asignarDatos();

        irALogin = true;
    }

    @Override
    public void moverPosicion(int posicion) {
        ((AdministracionCorreriaFragment) tabVistaPaginasAdministracionAdapter
                .getItem(TabVistaPaginasAdministracionAdapter.IDENTIFICADOR_CORRERIAS))
                .moverPosicion(posicion);
    }

    @Override
    public void irALogin(boolean cerrar) {
        irALogin = cerrar;
    }

    @Override
    public void actualizarVista() {
        talleres = presentador.consultarTerminalXCodigo(app.getCodigoTerminal());
        tvFecha.setText("");
        tvVersionMaestros.setText(talleres.getVersionMaestros() + " - ");
    }

    @Override
    public void refrescarDatos(SiriusApp talleresApp) {
        app = talleresApp;
        tvTerminal.setText(app.getCodigoTerminal());
        if (app.getCodigoCorreria() != null) {
            tvCorreriaActiva.setText("Correría activa:  " + app.getCodigoCorreria());
        } else {
            tvCorreriaActiva.setText("");
        }
    }

    @OnClick(R.id.lyCargaCentral)
    public void cargaCentral() {
        talleres = presentador.consultarTerminalXCodigo(app.getCodigoTerminal());

        if (talleres.getWifi().equals("S")) {
            if (!wifiHelper.tieneActivoWifi()) {
                wifiHelper.activarODesactivarWifi(true);
            }
            AlertaPopUp alertaPopUp = new AlertaPopUp();
            alertaPopUp.setTitle(R.string.titulo_carga_central);
            alertaPopUp.setMessage(R.string.alerta_carga_central);
            alertaPopUp.setContext(this);
            alertaPopUp.setPositiveButton("SI", (dialog, id) -> {
                startActivityForResult(new Intent(AdministracionActivity.this, CargaDatosActivity.class), CargaDatosActivity.REQUEST_CODE);
                dialog.dismiss();
            });
            alertaPopUp.setNegativeButton("NO", (dialog, id) -> {
                dialog.dismiss();
            });

            alertaPopUp.show();
        } else {
            startActivityForResult(new Intent(AdministracionActivity.this, CargaDatosActivity.class), CargaDatosActivity.REQUEST_CODE);
        }

    }

    @OnClick(R.id.lyReciboDirecto)
    public void reciboDirecto() {
        AlertaPopUp alertaPopUp = new AlertaPopUp();
        alertaPopUp.setTitle(R.string.titulo_recibo_directo);
        alertaPopUp.setMessage(R.string.alerta_recibo_directo);
        alertaPopUp.setContext(this);
        alertaPopUp.setPositiveButton("SI", (dialog, id) -> {
            startActivityForResult(new Intent(AdministracionActivity.this, ReciboDirectoActivity.class),
                    ReciboDirectoActivity.REQUEST_CODE);
            dialog.dismiss();

        });
        alertaPopUp.setNegativeButton("NO", (dialog, id) -> {
            dialog.dismiss();
        });

        alertaPopUp.show();
    }

    @OnClick(R.id.lyReciboWeb)
    public void reciboWeb() {
        talleres = presentador.consultarTerminalXCodigo(app.getCodigoTerminal());

        if (talleres.getWifi().equals("S")) {
            if (!wifiHelper.tieneActivoWifi()) {
                wifiHelper.activarODesactivarWifi(true);
            }
            AlertaPopUp alertaPopUp = new AlertaPopUp();
            alertaPopUp.setTitle(R.string.titulo_recibo_web);
            alertaPopUp.setMessage(R.string.alerta_recibo_web);
            alertaPopUp.setContext(this);
            alertaPopUp.setPositiveButton("SI", (dialog, id) -> {
                startActivityForResult(new Intent(AdministracionActivity.this,
                        ReciboWebActivity.class), ReciboWebActivity.REQUEST_CODE);
                dialog.dismiss();
            });
            alertaPopUp.setNegativeButton("NO", (dialog, id) -> {
                dialog.dismiss();
            });

            alertaPopUp.show();
        } else {
            startActivityForResult(new Intent(AdministracionActivity.this,
                    ReciboWebActivity.class), ReciboWebActivity.REQUEST_CODE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                salir();
                break;

            case R.id.action_nuevo_usuario:
                iniciarNuevoUsuario();
                break;

            /*case R.id.action_configuracion_wifi:
                presentador.configuracionWifi();
                break;
            */
            case R.id.action_log:
                presentador.mostrarVentadaCambiarEstadoLog();
                break;

            case R.id.action_finalizar_sirius:
                cerrartalleres();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == DURACION && requestCode == VIA_BLUETOOTH) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("application/zip");
            File file = new File(Constantes.traerRutaDescarga() +
                    Constantes.NOMBRE_CARPETA_DESCARGA_COMPLETA + File.separator + Constantes.NOMBRE_ARCHIVO_ZIP_DESCARGA);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

            PackageManager packageManager = getPackageManager();
            List<ResolveInfo> appList = packageManager.queryIntentActivities(intent, 0);

            if (appList.size() > 0) {
                String packageName = null;
                String className = null;
                boolean found = false;

                for (ResolveInfo info : appList) {
                    packageName = info.activityInfo.packageName;
                    if (packageName.equals("com.android.bluetooth")) {
                        className = info.activityInfo.name;
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    Toast.makeText(this, "Bluetooth no ha sido encontrado",
                            Toast.LENGTH_LONG).show();
                } else {
                    intent.setClassName(packageName, className);
                    startActivity(intent);
                }
            }
        } else if (requestCode == CargaDatosActivity.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                this.restablecerAdminDespuesDeCarga();
            }
        } else if (requestCode == DescargaDatosActivity.REQUEST_CODE) {
            ((AdministracionCorreriaFragment) tabVistaPaginasAdministracionAdapter
                    .getItem(TabVistaPaginasAdministracionAdapter.IDENTIFICADOR_CORRERIAS))
                    .iniciar();
            lyOpcionesCorreria.setVisibility(View.VISIBLE);
        } else if (requestCode == ReciboDirectoActivity.REQUEST_CODE) {
            this.restablecerAdminDespuesDeCarga();
        } else if (requestCode == ReciboWebActivity.REQUEST_CODE) {
            this.restablecerAdminDespuesDeCarga();
        } else {
        }
    }

    private void restablecerAdminDespuesDeCarga() {
        this.irALogin(true);
        this.salir();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void mostrarBarraProgreso() {


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_administracion, menu);
        nuevoUsuarioMenu = menu.findItem(R.id.action_nuevo_usuario);
        //configuracionWifiMenu = menu.findItem(R.id.action_configuracion_wifi);
        finalizarSiriusMenu = menu.findItem(R.id.action_finalizar_sirius);
        mostrarOpciones(menu.findItem(R.id.action_menu_configuracion));
        return true;
    }

    @Override
    public void ocultarBarraProgreso() {

    }

    private void iniciarNuevoUsuario() {
        NuevoUsuarioPopUp nuevoUsuarioPopUp = new NuevoUsuarioPopUp();
        Bundle args = new Bundle();
        args.putSerializable("CodigoContrato", app.getUsuario().getContrato().getCodigoContrato());
        args.putSerializable(Talleres.class.getName(), presentador.getTalleres());
        FragmentManager fragmentManager = getSupportFragmentManager();
        nuevoUsuarioPopUp.setArguments(args);
        nuevoUsuarioPopUp.show(fragmentManager, "");
    }

    private void cerrartalleres() {
        AlertaPopUp alertaPopUp = new AlertaPopUp();
        alertaPopUp.setTitle(R.string.titulo_finalizar_sirius);
        alertaPopUp.setMessage(R.string.titulo_finalizar_sirius);
        alertaPopUp.setContext(this);
        alertaPopUp.setPositiveButton("SI", (dialog, id) -> {
            finishAffinity();
            dialog.dismiss();
        });

        alertaPopUp.setNegativeButton("NO", (dialog, id) -> {
            dialog.dismiss();
        });

        alertaPopUp.show();
    }
}
