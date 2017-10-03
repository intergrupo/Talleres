package com.example.santiagolopezgarcia.talleres.view.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dominio.modelonegocio.Correria;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.presenters.Presenter;
import com.example.santiagolopezgarcia.talleres.view.interfaces.BaseView;
import com.example.utilidades.helpers.DateHelper;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class InformacionCorreriaFragment extends BaseFragment<Presenter>
        implements BaseView{

    @BindView(R.id.tvTerminal)
    TextView tvTermina;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    @BindView(R.id.tvCodigoUsuario)
    TextView tvCodigoUsuario;
    @BindView(R.id.tvNombreUsuario)
    TextView tvNombreUsuario;
    @BindView(R.id.tvAdvertencia)
    TextView tvAdvertencia;
    @BindView(R.id.tvFecha)
    TextView tvFecha;
    @BindView(R.id.tvCodigoCorreria)
    TextView tvCodigoCorreria;
    @BindView(R.id.tvNombreCorreria)
    TextView tvNombreCorreria;
    @BindView(R.id.etObservacionCorreria)
    EditText etObservacionCorreria;
    @BindView(R.id.tvArea)
    TextView tvArea;
    private Correria correria;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_informacion_correria, container, false);
        ButterKnife.bind(this, vista);
        obtenerParametros();
        try {
            asignarDatos();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vista;
    }

    private void obtenerParametros() {
        Bundle bundle = getArguments();
        if (bundle.containsKey(Correria.class.getName()))
            correria = (Correria) bundle.getSerializable(Correria.class.getName());
    }

    private void asignarDatos() throws IOException {
        SiriusApp app = (SiriusApp) getActivity().getApplication();
        tvFecha.setText(DateHelper.getCurrentDate(DateHelper.TipoFormato.ddMMMyyyy));
        tvTermina.setText(app.getCodigoTerminal());
        tvCodigoUsuario.setText(app.getUsuario().getCodigoUsuario());
        tvNombreUsuario.setText(app.getUsuario().getNombre());
        tvVersion.setText("Versión " + getResources().getString(R.string.version));
        etObservacionCorreria.clearFocus();
        if (correria != null) {
            tvCodigoCorreria.setText(correria.getCodigoCorreria());
            tvAdvertencia.setText(correria.getAdvertencia());
            tvNombreCorreria.setText(correria.getDescripcion());
            tvArea.setText(correria.getInformacion());
            etObservacionCorreria.setText(correria.getObservacion());
            try {
                Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/DroidSansMono.ttf");
                tvAdvertencia.setTypeface(typeface);
            } catch (Exception e) {
                Log.e("Sirius", "Could not get typeface fonts/DroidSansMono.ttf because " + e.getMessage());
                registrarLog("Sirius Could not get typeface fonts/DroidSansMono.ttf because " + e.getMessage());
            }
        }
    }

    public String obtenerObservacion() {
        if(etObservacionCorreria != null) {
            return etObservacionCorreria.getText().toString();
        }
        return "";
    }

    @Override
    public void mostrarBarraProgreso() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if(tvFecha != null) {
            tvFecha.setText(DateHelper.getCurrentDate(DateHelper.TipoFormato.ddMMMyyyy));
        }
    }
}