package com.example.santiagolopezgarcia.talleres.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dominio.modelonegocio.Correria;
import com.example.santiagolopezgarcia.talleres.R;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class CorreriaSpinnerAdapter extends ArrayAdapter<Correria> {

    Context context;

    public CorreriaSpinnerAdapter(Context context, List<Correria> listaCorrerias) {
        super(context, 0, listaCorrerias);
        this.context = context;
        adicionarValorPorDefecto(listaCorrerias);
    }

    private void adicionarValorPorDefecto(List<Correria> listaCorrerias) {
        Correria correria = new Correria();
        correria.setDescripcion("Correria");
        listaCorrerias.add(0, correria);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = getViewItem(position, convertView, parent);
        return convertView;
    }

    @NonNull
    private View getViewItem(int position, View convertView, ViewGroup parent) {
        CorreriaViewHolder correriaViewHolder;
        final Correria correria = getItem(position);
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner_correria, parent, false);
            correriaViewHolder = new CorreriaViewHolder();
            correriaViewHolder.tvCodigoCorreria = (TextView) convertView.findViewById(R.id.tvCodigoCorreriaItem);
            correriaViewHolder.tvDescripcionCorreria = (TextView) convertView.findViewById(R.id.tvDescripcionCorreriaItem);
            convertView.setTag(correriaViewHolder);
        } else {
            correriaViewHolder = (CorreriaViewHolder) convertView.getTag();
        }
        correriaViewHolder.tvCodigoCorreria.setText(correria.getCodigoCorreria());
        correriaViewHolder.tvDescripcionCorreria.setText(correria.getDescripcion());
        if (position == 0) {
            correriaViewHolder.tvDescripcionCorreria.setTextColor(this.context.getResources().getColor(R.color.grisclaro));
        } else {
            correriaViewHolder.tvDescripcionCorreria.setTextColor(context.getResources().getColor(R.color.negro));
        }
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = getViewItem(position, convertView, parent);
        return convertView;
    }

    static class CorreriaViewHolder {
        TextView tvCodigoCorreria;
        TextView tvDescripcionCorreria;
    }
}
