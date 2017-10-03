package com.example.santiagolopezgarcia.talleres.view.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dominio.modelonegocio.MultiOpcion;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.util.SearchableSpinner;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IFiltro;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IListaOriginal;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IPosicionLista;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class MultiOpcionSpinnerAdapter extends ArrayAdapter<MultiOpcion> implements
        IFiltro, SearchableSpinner.SearchableSpinnerAdapterResize, IPosicionLista, IListaOriginal {

    private boolean esNegrilla;
    private Context context;
    private String descripcionValorPordefecto;
    private int posicionActiva;
    private ArrayList<MultiOpcion> listaTipoUsoOriginal;
    private MultiOpcion tipoUsoInicial;
    private boolean ajustarTamaño = true;

    public boolean isEsNegrilla() {
        return esNegrilla;
    }

    public void setEsNegrilla(boolean esNegrilla) {
        this.esNegrilla = esNegrilla;
    }

    public MultiOpcionSpinnerAdapter(Context context, List<MultiOpcion> listaListaMultiOpcion) {
        super(context, 0, listaListaMultiOpcion);
        this.context = context;
        this.descripcionValorPordefecto = "";
        listaTipoUsoOriginal = new ArrayList<>();
        listaTipoUsoOriginal.addAll(listaListaMultiOpcion);
    }

    public MultiOpcionSpinnerAdapter(Context context, List<MultiOpcion> listaListaMultiOpcion, String descripcionValorPordefecto) {
        super(context, 0, listaListaMultiOpcion);
        this.context = context;
        this.descripcionValorPordefecto = descripcionValorPordefecto;
        adicionarValorPorDefecto(listaListaMultiOpcion);
        listaTipoUsoOriginal = new ArrayList<>();
        listaTipoUsoOriginal.addAll(listaListaMultiOpcion);
    }

    private void adicionarValorPorDefecto(List<MultiOpcion> listaListaMultiOpcion) {
        tipoUsoInicial = new MultiOpcion();
        tipoUsoInicial.setDescripcion(descripcionValorPordefecto);
        listaListaMultiOpcion.add(0, tipoUsoInicial);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = getViewItem(position, convertView, parent);
        return convertView;
    }

    @NonNull
    private View getViewItem(int position, View convertView, ViewGroup parent) {
        MultiOpcionViewHolder trabajoViewHolder;
        final MultiOpcion multiOpcion = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner, parent, false);
            trabajoViewHolder = new MultiOpcionViewHolder();
            trabajoViewHolder.tvNombre = (TextView) convertView.findViewById(R.id.tvNombre);
            trabajoViewHolder.tvNombre.setTypeface(null, (esNegrilla) ? Typeface.BOLD : Typeface.NORMAL);
            convertView.setTag(trabajoViewHolder);
        } else {
            trabajoViewHolder = (MultiOpcionViewHolder) convertView.getTag();
        }
        trabajoViewHolder.tvNombre.setSingleLine(ajustarTamaño);
        trabajoViewHolder.tvNombre.setText(multiOpcion.getDescripcion());
        if (!this.descripcionValorPordefecto.isEmpty()) {
            if (position == 0) {
                if (ajustarTamaño) {
                    trabajoViewHolder.tvNombre.setText(descripcionValorPordefecto);
                } else {
                    trabajoViewHolder.tvNombre.setText("Ninguna");
                }
                trabajoViewHolder.tvNombre.setTextColor(context.getResources().getColor(R.color.grisclaro));
            } else {
                trabajoViewHolder.tvNombre.setTextColor(context.getResources().getColor(R.color.negro));
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
        List<MultiOpcion> listaFiltrada = new ArrayList<>();
        this.clear();
        if (dato.length() == 0) {
            listaFiltrada.addAll(listaTipoUsoOriginal);
        } else {
            for (MultiOpcion multiOpcion : listaTipoUsoOriginal) {
                if (multiOpcion.getDescripcion().toLowerCase(Locale.getDefault()).contains(dato)) {
                    listaFiltrada.add(multiOpcion);
                }
            }
            if (listaFiltrada.size() > 0) {
                if (!listaFiltrada.get(0).equals(tipoUsoInicial)) {
                    listaFiltrada.add(0, tipoUsoInicial);
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

    @Override
    public <T> int traerPosicionOriginal(T dato) {
        return listaTipoUsoOriginal.indexOf(dato);
    }

    @Override
    public <T> ArrayList<T> traerListaOriginal() {
        return (ArrayList<T>) listaTipoUsoOriginal;
    }


    static class MultiOpcionViewHolder {
        TextView tvNombre;
    }
}