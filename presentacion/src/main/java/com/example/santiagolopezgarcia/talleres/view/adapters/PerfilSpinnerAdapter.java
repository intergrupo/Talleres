package com.example.santiagolopezgarcia.talleres.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dominio.modelonegocio.Perfil;
import com.example.santiagolopezgarcia.talleres.R;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class PerfilSpinnerAdapter extends ArrayAdapter<Perfil> {

    Context context;

    public PerfilSpinnerAdapter(Context context, List<Perfil> listaPerfiles) {
        super(context, 0, listaPerfiles);
        this.context = context;
        adicionarValorPorDefecto(listaPerfiles);
    }

    private void adicionarValorPorDefecto(List<Perfil> listaPerfiles) {
        Perfil perfil = new Perfil();
        perfil.setDescripcion("Perfil");
        listaPerfiles.add(0, perfil);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = getViewItem(position, convertView, parent);
        return convertView;
    }

    @NonNull
    private View getViewItem(int position, View convertView, ViewGroup parent) {
        PerfilViewHolder perfilViewHolder;
        final Perfil perfil = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner, parent, false);
            perfilViewHolder = new PerfilViewHolder();
            perfilViewHolder.tvNombre = (TextView) convertView.findViewById(R.id.tvNombre);
            convertView.setTag(perfilViewHolder);
        } else {
            perfilViewHolder = (PerfilViewHolder) convertView.getTag();
        }
        perfilViewHolder.tvNombre.setText(perfil.getDescripcion());
        if (position == 0) {
            perfilViewHolder.tvNombre.setTextColor(context.getResources().getColor(R.color.grisclaro));
        } else {
            perfilViewHolder.tvNombre.setTextColor(context.getResources().getColor(R.color.negro));
        }
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = getViewItem(position, convertView, parent);
        return convertView;
    }


    static class PerfilViewHolder {
        TextView tvNombre;
    }
}