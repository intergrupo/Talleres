package com.example.santiagolopezgarcia.talleres.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dominio.modelonegocio.TareaXTrabajo;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.util.SearchableSpinner;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IFiltro;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class TareaTrabajoSpinnerAdapter extends ArrayAdapter<TareaXTrabajo> implements IFiltro, SearchableSpinner.SearchableSpinnerAdapterResize {

    Context context;
    private boolean valorPorDefecto;
    private int posicionActiva;
    private ArrayList<TareaXTrabajo> listaTareaXTrabajoOriginal;
    private TareaXTrabajo tareaXTrabajoInicial;
    private boolean ajustarTamaño = true;

    public TareaTrabajoSpinnerAdapter(Context context, List<TareaXTrabajo> listaTareaXTrabajo) {
        super(context, 0, listaTareaXTrabajo);
        this.context = context;
        listaTareaXTrabajoOriginal = new ArrayList<>();
        listaTareaXTrabajoOriginal.addAll(listaTareaXTrabajo);
    }

    public TareaTrabajoSpinnerAdapter(Context context, List<TareaXTrabajo> listaTareaXTrabajo, boolean valorPorDefecto) {
        super(context, 0, listaTareaXTrabajo);
        this.context = context;
        this.valorPorDefecto = valorPorDefecto;
        if (valorPorDefecto) {
            adicionarValorPorDefecto(listaTareaXTrabajo);
        }
        listaTareaXTrabajoOriginal = new ArrayList<>();
        listaTareaXTrabajoOriginal.addAll(listaTareaXTrabajo);
    }

    private void adicionarValorPorDefecto(List<TareaXTrabajo> listaTareaXTrabajo) {
        tareaXTrabajoInicial = new TareaXTrabajo();
        tareaXTrabajoInicial.getTarea().setNombre("Tarea");
        listaTareaXTrabajo.add(0, tareaXTrabajoInicial);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = getViewItem(position, convertView, parent);
        return convertView;
    }

    @NonNull
    private View getViewItem(int position, View convertView, ViewGroup parent) {
        TareaXTrabajoViewHolder tareaXTrabajoViewHolder;
        final TareaXTrabajo tareaXTrabajo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner_tarea, parent, false);
            tareaXTrabajoViewHolder = new TareaXTrabajoViewHolder();
            tareaXTrabajoViewHolder.tvNombreTarea = (TextView) convertView.findViewById(R.id.tvDescripcionLaborMaterial);
            convertView.setTag(tareaXTrabajoViewHolder);
        } else {
            tareaXTrabajoViewHolder = (TareaXTrabajoViewHolder) convertView.getTag();
        }
        tareaXTrabajoViewHolder.tvNombreTarea.setSingleLine(ajustarTamaño);
        tareaXTrabajoViewHolder.tvNombreTarea.setText(tareaXTrabajo.getTarea().getNombre());
        if (valorPorDefecto) {
            if (position == 0) {
                if (ajustarTamaño) {
                    tareaXTrabajoViewHolder.tvNombreTarea.setText("Tarea");
                } else {
                    tareaXTrabajoViewHolder.tvNombreTarea.setText("Ninguna");
                }
                tareaXTrabajoViewHolder.tvNombreTarea.setTextColor(context.getResources().getColor(R.color.grisclaro));
            } else {
                tareaXTrabajoViewHolder.tvNombreTarea.setTextColor(context.getResources().getColor(R.color.negro));
            }
        }
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = getViewItem(position, convertView, parent);
        return convertView;
    }

    public void setPosicionActiva(int posicionActiva) {
        this.posicionActiva = posicionActiva;
    }

    @Override
    public void filtrar(String dato) {
        List<TareaXTrabajo> listaFiltrada = new ArrayList<>();
        this.clear();
        if (dato.length() == 0) {
            listaFiltrada.addAll(listaTareaXTrabajoOriginal);
        } else {
            for (TareaXTrabajo tareaXTrabajo : listaTareaXTrabajoOriginal) {
                if (tareaXTrabajo.getTarea().getNombre().toLowerCase(Locale.getDefault()).contains(dato)) {
                    listaFiltrada.add(tareaXTrabajo);
                }
            }
            if (listaFiltrada.size() > 0) {
                if (!listaFiltrada.get(0).equals(tareaXTrabajoInicial)) {
                    listaFiltrada.add(0, tareaXTrabajoInicial);
                }
            }
        }
        this.addAll(listaFiltrada);
        notifyDataSetChanged();
    }

    @Override
    public void ajustarTamaño(boolean ajustar) {
        ajustarTamaño = ajustar;
        notifyDataSetChanged();
    }

    @Override
    public int traerPosicionActiva() {
        return posicionActiva;
    }


    static class TareaXTrabajoViewHolder {
        TextView tvNombreTarea;
    }
}
