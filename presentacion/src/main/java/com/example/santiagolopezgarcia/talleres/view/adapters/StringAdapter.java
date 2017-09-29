package com.example.santiagolopezgarcia.talleres.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.santiagolopezgarcia.talleres.R;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class StringAdapter extends ArrayAdapter<String> {

    private Context context;
    private String valorDefecto;

    public StringAdapter(Context context, List<String> listaString, String valorDefecto) {
        super(context, 0, listaString);
        this.context = context;
        this.valorDefecto = valorDefecto;
        adicionarValorPorDefecto(listaString);
    }

    private void adicionarValorPorDefecto(List<String> listaString) {
        String valor = valorDefecto;
        listaString.add(0, valor);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = getViewItem(position, convertView, parent);
        return convertView;
    }

    @NonNull
    private View getViewItem(int position, View convertView, ViewGroup parent) {
        StringViewHolder stringViewHolder;
        final String valor = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner, parent, false);
            stringViewHolder = new StringViewHolder();
            stringViewHolder.tvValor = (TextView) convertView.findViewById(R.id.tvNombre);
            convertView.setTag(stringViewHolder);
        } else {
            stringViewHolder = (StringViewHolder) convertView.getTag();
        }
        stringViewHolder.tvValor.setText(valor);
        if (position == 0) {
            stringViewHolder.tvValor.setTextColor(context.getResources().getColor(R.color.grisclaro));
        } else {
            stringViewHolder.tvValor.setTextColor(context.getResources().getColor(R.color.negro));
        }
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = getViewItem(position, convertView, parent);
        return convertView;
    }

    static class StringViewHolder {
        TextView tvValor;
    }
}