package com.example.santiagolopezgarcia.talleres.view.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dominio.administracion.TalleresBL;
import com.example.dominio.modelonegocio.Talleres;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.presenters.ConfiguracionPresenter;
import com.example.santiagolopezgarcia.talleres.view.activities.Parametrizacion2DActivity;
import com.example.santiagolopezgarcia.talleres.view.adapters.StringAdapter;
import com.example.santiagolopezgarcia.talleres.view.interfaces.BaseView;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IAdministracionConfiguracionView;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IAdministracionView;
import com.example.santiagolopezgarcia.talleres.view.popups.CambiarRutaPopUp;
import com.example.utilidades.helpers.UrlHelper;
import com.example.utilidades.helpers.WifiHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class ConfiguracionFragment extends BaseFragment<ConfiguracionPresenter>
        implements IAdministracionConfiguracionView {

    @BindView(R.id.tvUrlSiriusServer)
    TextView tvUrl;
    @BindView(R.id.ivExplorador)
    ImageView ivExplorador;
    @BindView(R.id.ivWifi)
    ImageView ivWifi;
    @BindView(R.id.ivParametrizacion2D)
    ImageView ivParametrizacion2D;
    @BindView(R.id.spImpresora)
    Spinner spImpresora;
    @Inject
    TalleresBL talleresBL;
    private static final String WIFI_HABILITADO = "S";
    private static final String WIFI_DESHABILITADO = "N";
    private IAdministracionView iAdministracionView;
    private UrlHelper urlHelper;
    private WifiHelper wifiHelper;
    int posicionImpresora = 0;
    String urlServidor;
    Talleres talleres;
    SiriusApp app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vistaConfiguracion = inflater.inflate(R.layout.fragment_configuracion_administracion, container, false);
        ButterKnife.bind(this, vistaConfiguracion);
        dependencia.getContenedor().build().inject(this);
        if (getContext() instanceof IAdministracionView) {
            iAdministracionView = (IAdministracionView) getContext();
        }
        wifiHelper = new WifiHelper(getContext());
        obtenerParametros();
        try {
            cargarDatos();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vistaConfiguracion;
    }

    @Override
    public void onResume() {
        super.onResume();
        actualizarUrl();
        refrescarWifi();
    }

    private void obtenerParametros() {
        presentador.setSirius((Talleres) getArguments().getSerializable(Talleres.class.getName()));
        presentador.setApp((SiriusApp) getContext().getApplicationContext());
        app = (SiriusApp) getActivity().getApplication();
        talleres = talleresBL.consultarTerminalXCodigo(app.getCodigoTerminal());
    }

    private void cargarDatos() throws IOException {
        if (!presentador.getTalleres().getRutaServidor().isEmpty()) {
            tvUrl.setText(presentador.getTalleres().getRutaServidor());
            tvUrl.setTextColor(getResources().getColor(R.color.negro));
        }
        refrescarWifi();
        cargarDatosImpresora();
    }


    private void actualizarUrl() {
        talleres = presentador.getTalleres();
        if (urlServidor != null){
            tvUrl.setText(urlServidor);
        }else {
            if(!talleres.getRutaServidor().isEmpty()) {
                tvUrl.setText(talleres.getRutaServidor());
            }
        }

    }


    private void cargarDatosImpresora() throws IOException {
        List<String> listaImpresoras = new ArrayList<>();
        listaImpresoras.add("Zebra ZQ520");

        spImpresora.setAdapter(new StringAdapter(
                getContext(), listaImpresoras, "Sin impresora"));

        try{
            for (int i = 0; i <= listaImpresoras.size(); i++) {

                if (listaImpresoras.get(i).equals(talleres.getImpresora())) {
                    posicionImpresora = i;
                    break;
                }

            }
            spImpresora.setSelection(posicionImpresora);

        } catch (Exception e) {
            registrarLog(e.getMessage());
        }


        spImpresora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presentador.guardarImpresora((String) parent.getItemAtPosition(position));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void mostrarBarraProgreso() {

    }

    @Override
    public void ocultarBarraProgreso() {

    }

    @OnClick(R.id.ivParametrizacion2D)
    public void abrirParametrizacion2D() {
        getActivity().startActivity(new Intent(getActivity(), Parametrizacion2DActivity.class));
        getActivity().finish();
    }

    @OnClick(R.id.tvUrlSiriusServer)
    public void abrirPopUpCambiarRuta() {
        CambiarRutaPopUp cambiarRutaPopUp = new CambiarRutaPopUp();
        Bundle args = new Bundle();
        args.putSerializable(Talleres.class.getName(), presentador.getTalleres());
        cambiarRutaPopUp.setArguments(args);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        cambiarRutaPopUp.show(fragmentManager, "");
    }

    public void setUrl(String url) {
        getActivity().runOnUiThread(() -> tvUrl.setText(url));
        urlServidor = url;
    }

    @OnClick(R.id.ivExplorador)
    public void abrirUrlExplorador() {
        String url = tvUrl.getText().toString();
        Intent baseIntent = new Intent(Intent.ACTION_VIEW);
        baseIntent.setData(Uri.parse(url));
        Intent chooserIntent = Intent.createChooser(baseIntent, "Seleccione una Aplicación");
        getActivity().startActivity(chooserIntent);
    }

    @OnClick(R.id.ivWifi)
    public void activarODesactivarWifi() {
        if (presentador.getTalleres().getWifi().equals(WIFI_HABILITADO)) {
            presentador.getTalleres().setWifi(WIFI_DESHABILITADO);
            ivWifi.setImageResource(R.mipmap.ic_wifi_gris);
        } else if (presentador.getTalleres().getWifi().equals(WIFI_DESHABILITADO) ||
                presentador.getTalleres().getWifi().isEmpty()){
            presentador.getTalleres().setWifi(WIFI_HABILITADO);
            ivWifi.setImageResource(R.mipmap.ic_wifi_verde);
        }
        presentador.procesarSirius();
    }

    private void refrescarWifi() {
        talleres = talleresBL.consultarTerminalXCodigo(app.getCodigoTerminal());
        if (talleres.getWifi().equals(WIFI_DESHABILITADO)) {
            ivWifi.setImageResource(R.mipmap.ic_wifi_gris);
        } else if (talleres.getWifi().equals(WIFI_HABILITADO)){
            ivWifi.setImageResource(R.mipmap.ic_wifi_verde);
        }
    }
}
