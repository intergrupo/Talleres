package com.example.santiagolopezgarcia.talleres.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.dominio.modelonegocio.TotalTarea;
import com.example.dominio.modelonegocio.TotalTareaXEstado;
import com.example.santiagolopezgarcia.talleres.R;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<TotalTarea> listaTotalesTarea;

    public ExpandableListAdapter(Context context, List<TotalTarea> listaTotalesTarea) {
        this.context = context;
        this.listaTotalesTarea = listaTotalesTarea;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listaTotalesTarea.get(groupPosition).getListaTareaXEstado().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final TotalTareaXEstado revisionItem = (TotalTareaXEstado) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_totaltareaxestado, null);
        }

        TextView tvNomRevision = (TextView) convertView.findViewById(R.id.tvNombre);
        tvNomRevision.setText(revisionItem.getEstado().getDescripcion());
        TextView tvCantidadRevision = (TextView) convertView.findViewById(R.id.tvCantidad);
        tvCantidadRevision.setText(String.valueOf(revisionItem.getCantidad()));
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listaTotalesTarea.get(groupPosition).getListaTareaXEstado().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listaTotalesTarea.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listaTotalesTarea.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final TotalTarea revisionItem = (TotalTarea) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_totaltarea, null);
        }

        TextView tvNomRevision = (TextView) convertView.findViewById(R.id.tvNombre);
        tvNomRevision.setText(revisionItem.getNombre());
        TextView tvCantidadRevision = (TextView) convertView.findViewById(R.id.tvCantidad);
        tvCantidadRevision.setText(String.valueOf(revisionItem.getCantidad()));
        convertView.setBackgroundColor(context.getResources().getColor(R.color.grisclaro3));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
