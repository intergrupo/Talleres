package com.example.santiagolopezgarcia.talleres.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dominio.modelonegocio.NotificacionOrdenTrabajo;
import com.example.dominio.bussinesslogic.tarea.TareaBL;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class ResultadoNotificacionOTAdapter extends RecyclerView.Adapter<ResultadoNotificacionOTAdapter.ResultadoNotificacionHolder> {

    @Inject
    TareaBL tareaBL;
    private LayoutInflater inflater;
    private List<NotificacionOrdenTrabajo> listaNotificacionOT;
    private Context context;
    private SiriusApp application;
    private ContenedorDependencia dependencia;

    public ResultadoNotificacionOTAdapter(SiriusApp application, Context context, List<NotificacionOrdenTrabajo> listaNotificacionOT) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listaNotificacionOT = listaNotificacionOT;
        this.application = application;
        dependencia = new ContenedorDependencia(application);
        dependencia.getContenedor().build().inject(this);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listaNotificacionOT.size();
    }

    @Override
    public ResultadoNotificacionHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_resultado, parent, false);
        ResultadoNotificacionHolder holder = new ResultadoNotificacionHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ResultadoNotificacionHolder holder, int position) {
        NotificacionOrdenTrabajo notificacionOrdenTrabajo = listaNotificacionOT.get(position);
        asignarDatosResultado(holder, notificacionOrdenTrabajo, position);
    }

    private void asignarDatosResultado(ResultadoNotificacionHolder holder,
                                       NotificacionOrdenTrabajo notificacionOrdenTrabajo, int position) {
        String tarea = tareaBL.cargarTareaxCodigo(notificacionOrdenTrabajo.getCodigoTarea()).getNombre();
        holder.itemView.setTag(notificacionOrdenTrabajo);
        holder.tvCodigoCorreria.setText(notificacionOrdenTrabajo.getCodigoCorreria());
        holder.tvCodigoOT.setText(notificacionOrdenTrabajo.getCodigoOrdenTrabajo());
        holder.tvCodigoTarea.setText(notificacionOrdenTrabajo.getCodigoTarea());
        holder.tvTarea.setText(tarea);
        holder.tvOperacion.setText(notificacionOrdenTrabajo.getOperacion());
        holder.tvResultado.setText(notificacionOrdenTrabajo.getResultado());
        holder.asignarEventos(position);

    }

    public class ResultadoNotificacionHolder extends RecyclerView.ViewHolder {

        TextView tvCodigoCorreria;
        TextView tvCodigoOT;
        TextView tvCodigoTarea;
        TextView tvTarea;
        TextView tvOperacion;
        TextView tvDescripcion;
        TextView tvResultado;
        LinearLayout viewResultado;

        public ResultadoNotificacionHolder(View itemView) {

            super(itemView);

            tvCodigoCorreria = (TextView) itemView.findViewById(R.id.tvCodigoCorreria);
            tvCodigoOT = (TextView) itemView.findViewById(R.id.tvCodigoOT);
            tvCodigoTarea = (TextView) itemView.findViewById(R.id.tvCodigoTarea);
            tvTarea = (TextView) itemView.findViewById(R.id.tvTarea);
            tvOperacion = (TextView) itemView.findViewById(R.id.tvOperacion);
            tvDescripcion = (TextView) itemView.findViewById(R.id.
                    tvDescripcion);
            tvResultado = (TextView) itemView.findViewById(R.id.tvResultado);
            viewResultado = (LinearLayout) itemView.findViewById(R.id.viewResultado);



        }

        public void asignarEventos(int posicion) {

        }



    }
}