package com.example.santiagolopezgarcia.talleres.view.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dominio.modelonegocio.ListaTrabajo;
import com.example.dominio.modelonegocio.Trabajo;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.util.SearchableSpinner;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IFiltro;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class TrabajoSpinnerAdapter extends ArrayAdapter<Trabajo> implements IFiltro, SearchableSpinner.SearchableSpinnerAdapterResize {

    private boolean esNegrilla;
    private boolean valorPorDefecto;
    private Context context;
    private int posicionActiva;
    private ArrayList<Trabajo> listaTrabajoOriginal;
    private Trabajo trabajoInicial;
    private boolean ajustarTamaño = true;
    private boolean pintarAzul;

    public boolean isEsNegrilla() {
        return esNegrilla;
    }

    public void setEsNegrilla(boolean esNegrilla) {
        this.esNegrilla = esNegrilla;
    }

    public TrabajoSpinnerAdapter(Context context, ListaTrabajo listaTrabajo, boolean pintarAzul) {
        super(context, 0, listaTrabajo);
        this.context = context;
        valorPorDefecto = false;
        this.pintarAzul = pintarAzul;
        listaTrabajoOriginal = new ArrayList<>();
        listaTrabajoOriginal.addAll(listaTrabajo);
    }

    public TrabajoSpinnerAdapter(Context context, ListaTrabajo listaTrabajo,
                                 Boolean valorPorDefecto, boolean pintarAzul) {
        super(context, 0, listaTrabajo);
        this.context = context;
        this.valorPorDefecto = valorPorDefecto;
        this.pintarAzul = pintarAzul;
        if (valorPorDefecto) {
            adicionarValorPorDefecto(listaTrabajo);
        }
        listaTrabajoOriginal = new ArrayList<>();
        listaTrabajoOriginal.addAll(listaTrabajo);
    }

    private void adicionarValorPorDefecto(List<Trabajo> listaTrabajo) {
        trabajoInicial = new Trabajo();
        trabajoInicial.setNombre("Trabajo");
        listaTrabajo.add(0, trabajoInicial);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = getViewItem(position, convertView, parent);
        return convertView;
    }

    @NonNull
    private View getViewItem(int position, View convertView, ViewGroup parent) {
        TrabajoViewHolder trabajoViewHolder;
        Trabajo trabajo;
        try {
            trabajo = getItem(position);
        } catch (Exception e) {
            trabajo = getItem(0);
        }
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner_trabajo, parent, false);
            trabajoViewHolder = new TrabajoViewHolder();
            trabajoViewHolder.tvNombreTrabajo = (TextView) convertView.findViewById(R.id.tvNombreTrabajo);
            trabajoViewHolder.tvNombreTrabajo.setTypeface(null, (esNegrilla) ? Typeface.BOLD : Typeface.NORMAL);
            convertView.setTag(trabajoViewHolder);
        } else {
            trabajoViewHolder = (TrabajoViewHolder) convertView.getTag();
        }
        trabajoViewHolder.tvNombreTrabajo.setSingleLine(ajustarTamaño);
        trabajoViewHolder.tvNombreTrabajo.setText(trabajo.getNombre());
        if (pintarAzul) {
            trabajoViewHolder.tvNombreTrabajo.setTextColor(context.getResources().getColor(R.color.rojo));
        }
        if (valorPorDefecto) {
            if (position == 0) {
                if (ajustarTamaño) {
                    trabajoViewHolder.tvNombreTrabajo.setText("Trabajo");
                } else {
                    trabajoViewHolder.tvNombreTrabajo.setText("Ninguno");
                }
                trabajoViewHolder.tvNombreTrabajo.setTextColor(context.getResources().getColor(R.color.grisclaro));
            } else {
                trabajoViewHolder.tvNombreTrabajo.setTextColor(context.getResources().getColor(R.color.negro));
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
        List<Trabajo> listaFiltrada = new ArrayList<>();
        this.clear();
        if (dato.length() == 0) {
            listaFiltrada.addAll(listaTrabajoOriginal);
        } else {
            for (Trabajo trabajo : listaTrabajoOriginal) {
                if (trabajo.getNombre().toLowerCase(Locale.getDefault()).contains(dato)) {
                    listaFiltrada.add(trabajo);
                }
            }
            if (listaFiltrada.size() > 0) {
                if (!listaFiltrada.get(0).equals(trabajoInicial)) {
                    listaFiltrada.add(0, trabajoInicial);
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


    static class TrabajoViewHolder {
        TextView tvNombreTrabajo;
    }
}
