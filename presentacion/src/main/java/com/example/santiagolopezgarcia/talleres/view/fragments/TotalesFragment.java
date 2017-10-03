package com.example.santiagolopezgarcia.talleres.view.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dominio.modelonegocio.ListaTrabajo;
import com.example.dominio.modelonegocio.TotalTarea;
import com.example.dominio.modelonegocio.Trabajo;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.presenters.TotalesPresenter;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;
import com.example.santiagolopezgarcia.talleres.view.adapters.ExpandableListAdapter;
import com.example.santiagolopezgarcia.talleres.view.adapters.TrabajoSpinnerAdapter;
import com.example.santiagolopezgarcia.talleres.view.interfaces.ITotalesView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class TotalesFragment extends BaseFragment<TotalesPresenter> implements ITotalesView {

    @BindView(R.id.lvTareas)
    ExpandableListView lvTareas;
    @BindView(R.id.spAforos)
    Spinner spTrabajo;
    @BindView(R.id.tvTareasTotales)
    TextView tvTareasTotales;
    @BindView(R.id.tvTotalOrdenesTrabajo)
    TextView tvTotalOrdenesTrabajo;
    protected ProgressDialog dialogoBarraProgreso;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_totales, container, false);
        ButterKnife.bind(this, vista);
        dependencia = new ContenedorDependencia(getActivity().getApplication());
        dependencia.getContenedor().build().inject(this);
        presentador.adicionarVista(this);
        crearBarraProgreso();
        presentador.setCodigoCorreria(dependencia.getApp().getCodigoCorreria());
        presentador.iniciar();
        return vista;
    }

    public void refrescarDatos() {
        presentador.iniciar();
    }

    @OnItemSelected(R.id.spAforos)
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        presentador.obtenerTareasXOrdenTrabajo(((Trabajo) parent.getItemAtPosition(position)));
    }

    protected void crearBarraProgreso() {
        dialogoBarraProgreso = new ProgressDialog(getActivity());
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void mostrarBarraProgreso() {
        dialogoBarraProgreso.setCancelable(false);
        dialogoBarraProgreso.setMessage(getActivity().getResources().getString(R.string.mensaje_cargando_datos));
        dialogoBarraProgreso.show();
    }

    @Override
    public void ocultarBarraProgreso() {
        dialogoBarraProgreso.dismiss();
    }

    @Override
    public void mostrarListaTrabajos(ListaTrabajo listaTrabajos) {
        getActivity().runOnUiThread(() -> {
            TrabajoSpinnerAdapter trabajoSpinnerAdapter = new TrabajoSpinnerAdapter(
                    getContext(), listaTrabajos, false);
            trabajoSpinnerAdapter.setEsNegrilla(true);
            spTrabajo.setAdapter(trabajoSpinnerAdapter);
            if (listaTrabajos == null || listaTrabajos.size() == 0) {
                spTrabajo.setVisibility(View.INVISIBLE);
                tvTareasTotales.setVisibility(View.INVISIBLE);
            } else {
                spTrabajo.setVisibility(View.VISIBLE);
                tvTareasTotales.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void mostrarTotalTarea(List<TotalTarea> listaTotalTarea, int total) {
        getActivity().runOnUiThread(() -> {
            tvTareasTotales.setText(String.valueOf(total));
            ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(getContext(), listaTotalTarea);
            lvTareas.setAdapter(expandableListAdapter);
            if (!listaTotalTarea.isEmpty()) {
                expandirTotales(expandableListAdapter.getGroupCount());
            }
        });
    }

    @Override
    public void mostrarTotales(int total) {
        getActivity().runOnUiThread(() -> {
            tvTotalOrdenesTrabajo.setText(String.valueOf(total));
        });
    }

    private void expandirTotales(int cantidad) {
        for (int expandible = 0; expandible < cantidad; expandible++) {
            lvTareas.expandGroup(expandible);
        }
    }
}