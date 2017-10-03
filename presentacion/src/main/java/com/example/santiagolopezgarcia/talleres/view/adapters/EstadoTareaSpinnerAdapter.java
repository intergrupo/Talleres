package com.example.santiagolopezgarcia.talleres.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dominio.modelonegocio.Tarea;
import com.example.santiagolopezgarcia.talleres.R;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class EstadoTareaSpinnerAdapter extends ArrayAdapter<Tarea.EstadoTarea> {

    Context context;

    public EstadoTareaSpinnerAdapter(Context context, List<Tarea.EstadoTarea> listaEstadoTareas) {
        super(context, 0, listaEstadoTareas);
        this.context = context;
        adicionarValorPorDefecto(listaEstadoTareas);
    }

    private void adicionarValorPorDefecto(List<Tarea.EstadoTarea> listaEstadoTareas) {
        Tarea.EstadoTarea estadoTarea = Tarea.EstadoTarea.NINGUNA;
        estadoTarea.setNombre("Estado tarea");
        listaEstadoTareas.add(0, estadoTarea);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = getViewItem(position, convertView, parent);
        return convertView;
    }

    @NonNull
    private View getViewItem(int position, View convertView, ViewGroup parent) {
        EstadoTareaViewHolder estadoTareaViewHolder;
        final Tarea.EstadoTarea estadoTarea = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner_estado_tarea, parent, false);
            estadoTareaViewHolder = new EstadoTareaViewHolder();
            estadoTareaViewHolder.tvNombreEstadoTarea = (TextView) convertView.findViewById(R.id.tvNombreEstadoTarea);
            convertView.setTag(estadoTareaViewHolder);
        } else {
            estadoTareaViewHolder = (EstadoTareaViewHolder) convertView.getTag();
        }
        estadoTareaViewHolder.tvNombreEstadoTarea.setText(estadoTarea.getNombre());
        if (position == 0) {
            estadoTareaViewHolder.tvNombreEstadoTarea.setTextColor(context.getResources().getColor(R.color.grisclaro));
        } else {
            estadoTareaViewHolder.tvNombreEstadoTarea.setTextColor(context.getResources().getColor(R.color.negro));
        }
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = getViewItem(position, convertView, parent);
        return convertView;
    }

    static class EstadoTareaViewHolder {
        TextView tvNombreEstadoTarea;
    }
}