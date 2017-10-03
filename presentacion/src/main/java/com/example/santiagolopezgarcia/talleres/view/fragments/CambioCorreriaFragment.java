package com.example.santiagolopezgarcia.talleres.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dominio.bussinesslogic.correria.CorreriaBL;
import com.example.dominio.modelonegocio.Correria;
import com.example.dominio.modelonegocio.Talleres;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.presenters.CambioCorreriaPresenter;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;
import com.example.santiagolopezgarcia.talleres.util.DatosCache;
import com.example.santiagolopezgarcia.talleres.view.activities.LoginActivity;
import com.example.santiagolopezgarcia.talleres.view.adapters.CorreriaSpinnerAdapter;
import com.example.santiagolopezgarcia.talleres.view.interfaces.BaseView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class CambioCorreriaFragment extends BaseFragment<CambioCorreriaPresenter>
        implements BaseView {

    @Inject
    CorreriaBL correriaBL;
    @BindView(R.id.spListaCorreria)
    Spinner spListaCorreria;
    @BindView(R.id.btnCerrarSesion)
    Button btnCerrarSesion;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    OnCambioCorreria onCambioCorreria;
    SiriusApp siriusApp;
    @BindView(R.id.tvTerminal)
    TextView tvTerminalFrag;
    Talleres sirius;
    LoginActivity loginActivity;
    private List<Correria> listaCorrerias;

    @Override
    public void mostrarBarraProgreso() {

    }


    public interface OnCambioCorreria {
        void onCorreriaSeleccionada(Correria correria);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, vista);
        siriusApp = (SiriusApp) getActivity().getApplication();
        dependencia = new ContenedorDependencia(siriusApp);
        dependencia.getContenedor().build().inject(this);
        presentador.adicionarVista(this);

        obtenerDatos();
        asignarDatos();
        return vista;

    }

    private void obtenerDatos() {
        Bundle bundle = getArguments();
        if ((bundle != null) && bundle.containsKey(Correria.class.getName()))
            presentador.setCorreriaActiva((Correria) bundle.getSerializable(Correria.class.getName()));
    }

    private void asignarDatos() {
        SiriusApp app = (SiriusApp) getActivity().getApplication();
        tvVersion.setText("Versión " + getResources().getString(R.string.version));
        tvTerminalFrag.setText(app.getCodigoTerminal());
        listaCorrerias = correriaBL.cargarXContrato(
                siriusApp.getUsuario().getContrato().getCodigoContrato());
        spListaCorreria.setAdapter(new CorreriaSpinnerAdapter(getContext(), listaCorrerias));
        asignarCorreriaActiva(listaCorrerias);
    }

    private void asignarCorreriaActiva(List<Correria> listaCorrerias) {
        int posicionCorreria = 0;
        for (Correria correria : listaCorrerias) {
            if (presentador.getCorreriaActiva().getCodigoCorreria().equals(correria.getCodigoCorreria()))
                break;
            posicionCorreria++;
        }
        spListaCorreria.setSelection(posicionCorreria);
    }

    @OnItemSelected(R.id.spListaCorreria)
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Correria correriaCambio;
        if (position > 0) {
            correriaCambio = (Correria) (parent.getItemAtPosition(position));
            if (!correriaCambio.getCodigoCorreria().equals(dependencia.getApp().getCodigoCorreria())
                    && presentador.sePuedeIniciarCorreria(correriaCambio)) {
                presentador.firmarCorreria();
                DatosCache.limpiar();
                dependencia.getApp().setCodigoCorreria(correriaCambio.getCodigoCorreria());
                onCambioCorreria.onCorreriaSeleccionada(correriaCambio);
            }else {
                asignarCorreriaActiva(listaCorrerias);
            }
        }
    }


    @OnClick(R.id.btnCerrarSesion)
    public void cerrarSesion(View view) {
        presentador.firmarCorreria();
        ((SiriusApp) getActivity().getApplication()).limpiarSesion();
        getActivity().finish();
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onCambioCorreria = ((OnCambioCorreria) context);
    }


}