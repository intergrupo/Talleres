package com.example.santiagolopezgarcia.talleres.view.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.dominio.modelonegocio.Correria;
import com.example.dominio.modelonegocio.Opcion;
import com.example.dominio.modelonegocio.ParametrosOpcionAdministracion;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.presenters.AdministracionCorreriaPresenter;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;
import com.example.santiagolopezgarcia.talleres.view.adapters.CorreriaAdministracionAdapter;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IAdministracionCorreriaView;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IAdministracionView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class AdministracionCorreriaFragment extends BaseFragment<AdministracionCorreriaPresenter>
        implements IAdministracionCorreriaView {

    @BindView(R.id.rvAdministracionCorrerias)
    RecyclerView rvCorrerias;
    @BindView(R.id.svCorreria)
    ScrollView scrollView;
    public CorreriaAdministracionAdapter correriaAdministracionAdapter;
    private IAdministracionView iAdministracionView;

    public AdministracionCorreriaFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vistaCorreria = inflater.inflate(R.layout.fragment_administracion_correria, container, false);
        ButterKnife.bind(this, vistaCorreria);
        dependencia = new ContenedorDependencia(getActivity().getApplication());
        dependencia.getContenedor().build().inject(this);
        obtenerParametros();
        cargarDatos();
        presentador.adicionarVista(this);
        iniciar();
        return vistaCorreria;
    }

    private void obtenerParametros() {
        presentador.setOpcion((Opcion) getArguments().getSerializable(Opcion.class.getName()));
        if (presentador.getOpcion() != null) {
            presentador.setParametrosOpcionAdmistracion(
                    new ParametrosOpcionAdministracion(presentador.getOpcion().getParametros()));
        } else {
            presentador.setParametrosOpcionAdmistracion(
                    new ParametrosOpcionAdministracion(""));
        }
    }

    public void iniciar() {
        presentador.iniciar();
    }

    private void cargarDatos() {
        presentador.setApp(((SiriusApp) getActivity().getApplication()));
        if (getContext() instanceof IAdministracionView) {
            iAdministracionView = (IAdministracionView) getContext();
        }
    }


    @Override
    public void mostrarListaCorrerias(List<Correria> listaCorrerias) {
        getActivity().runOnUiThread(() -> {
            correriaAdministracionAdapter = new CorreriaAdministracionAdapter(presentador.getApp(), getContext(),
                    listaCorrerias, presentador.getParametrosOpcionAdmistracion());
            rvCorrerias.setAdapter(correriaAdministracionAdapter);
            rvCorrerias.setLayoutManager(new LinearLayoutManager(getContext()));
        });
    }

    public void moverPosicion(int posicion) {
        if(rvCorrerias != null) {
            rvCorrerias.scrollTo(20, posicion);
        }
    }

    @Override
    public void mostrarBarraProgreso() {

    }

    @Override
    public void ocultarBarraProgreso() {

    }

}