package com.example.santiagolopezgarcia.talleres.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dominio.bussinesslogic.ordentrabajo.OrdenTrabajoBL;
import com.example.dominio.bussinesslogic.tarea.TareaBL;
import com.example.dominio.modelonegocio.ClasificacionVista;
import com.example.dominio.modelonegocio.ListaTareaXOrdenTrabajo;
import com.example.dominio.modelonegocio.Tarea;
import com.example.dominio.modelonegocio.TareaXOrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.helpers.ToastHelper;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;
import com.example.santiagolopezgarcia.talleres.view.interfaces.InvocarActivity;
import com.example.santiagolopezgarcia.talleres.view.interfaces.Item;
import com.example.santiagolopezgarcia.talleres.view.interfaces.ItemSeleccionado;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class TareaXTrabajoOrdenTrabajoAdapter extends RecyclerView.Adapter<TareaXTrabajoOrdenTrabajoAdapter.OrdenTrabajoXTareaViewHolder> {

    int posicionSeleccionada = 0;
    ListaTareaXOrdenTrabajo listaTareaXOrdenTrabajo;
    ContenedorDependencia dependencia;
    private ItemSeleccionado itemSeleccionado;
    private Item itemActivo;
    @Inject
    OrdenTrabajoBL ordenTrabajoBL;
    @Inject
    TareaBL tareaBL;
    int posicionOrdenTrabajoActiva = 0;
    InvocarActivity invicarActivity;
    SiriusApp app;
    Context context;

    @Override
    public int getItemViewType(int position) {
        return listaTareaXOrdenTrabajo.obtenerPosicion(listaTareaXOrdenTrabajo.get(position));
    }

    public void setItemActivo(Item itemActivo) {
        this.itemActivo = itemActivo;
    }

    public void setPosicionOrdenTrabajoActiva(int posicionOrdenTrabajoActiva) {
        this.posicionOrdenTrabajoActiva = posicionOrdenTrabajoActiva;
    }

    public void setItemSeleccionado(ItemSeleccionado itemSeleccionado) {
        this.itemSeleccionado = itemSeleccionado;
    }

    public TareaXTrabajoOrdenTrabajoAdapter(Context context, InvocarActivity invicarActivity, SiriusApp application,
                                            ListaTareaXOrdenTrabajo listaTareaXOrdenTrabajo,
                                            int posicionOrdenTrabajoActiva) {
        this.invicarActivity = invicarActivity;
        this.listaTareaXOrdenTrabajo = listaTareaXOrdenTrabajo;
        dependencia = new ContenedorDependencia(application);
        dependencia.getContenedor().build().inject(this);
        this.posicionOrdenTrabajoActiva = posicionOrdenTrabajoActiva;
        this.app = application;
        this.context = context;
    }

    @Override
    public OrdenTrabajoXTareaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_orden_trabajo_x_tarea, parent, false);
        return new OrdenTrabajoXTareaViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(OrdenTrabajoXTareaViewHolder holder, int position) {
        final TareaXOrdenTrabajo tareaXOrdenTrabajo = listaTareaXOrdenTrabajo.get(position);
        if (tareaXOrdenTrabajo.getOrdenTrabajo().getNombre().isEmpty()) {
            tareaXOrdenTrabajo.setOrdenTrabajo(ordenTrabajoBL.cargarOrdenTrabajo(tareaXOrdenTrabajo.getCodigoCorreria(), tareaXOrdenTrabajo.getCodigoOrdenTrabajo()));
        }
        if (tareaXOrdenTrabajo.getTarea().getNombre().isEmpty()) {
            tareaXOrdenTrabajo.setTarea(tareaBL.cargarTareaxCodigo(tareaXOrdenTrabajo.getTarea().getCodigoTarea()));
        }
        holder.asignarDatosOrdenTrabajo(tareaXOrdenTrabajo);
        holder.itemView.setTag(tareaXOrdenTrabajo);
        holder.itemView.setSelected(posicionSeleccionada == position);
        if (position != posicionOrdenTrabajoActiva) {
            if (position != posicionSeleccionada) {
                holder.itemView.setBackgroundColor(invicarActivity.obtenerContexto().getResources().getColor(R.color.grisfondo));
                holder.cambiarColorLetra(invicarActivity.obtenerContexto().getResources().getColor(R.color.grisclaro));
            } else {
                holder.itemView.setBackgroundColor(invicarActivity.obtenerContexto().getResources().getColor(R.color.grisclaro3));
                holder.cambiarColorLetra(Color.BLACK);
            }
        } else {
            holder.itemView.setBackgroundColor(invicarActivity.obtenerContexto().getResources().getColor(R.color.verdeclaro));
            holder.cambiarColorLetra(Color.BLACK);
        }
    }

    private void mostrarPaginasLecturas(int posicionOT) {
        Intent intent = new Intent();
        intent.putExtra("positionCurrentItem", posicionOT);
        this.invicarActivity.invocar(intent,
                ClasificacionVista.TAREA);
        if (itemActivo != null) {
            itemActivo.itemProcesado(listaTareaXOrdenTrabajo.get(posicionOT));
        }
    }

    @Override
    public int getItemCount() {
        return (null != listaTareaXOrdenTrabajo ? listaTareaXOrdenTrabajo.size() : 0);
    }

    public class OrdenTrabajoXTareaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvDescripcionTarea;
        private final TextView tvNombre;
        private final TextView tvDireccion;
        private final ImageView ivIconoTareaXOrdenTrabajo;

        public OrdenTrabajoXTareaViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvDescripcionTarea = (TextView) itemView.findViewById(R.id.tvDescripcionTarea);
            tvNombre = (TextView) itemView.findViewById(R.id.tvNombre);
            tvDireccion = (TextView) itemView.findViewById(R.id.tvDireccion);
            ivIconoTareaXOrdenTrabajo = (ImageView) itemView.findViewById(R.id.ivIconoTareaOrdenTrabajo);
            TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 400);
            animation.setDuration(100);
            ivIconoTareaXOrdenTrabajo.setOnClickListener(v -> {
                v.startAnimation(animation);
                if(listaTareaXOrdenTrabajo.get(getLayoutPosition()).getEstadoTarea().equals(Tarea.EstadoTarea.CANCELADA)){
                    ToastHelper.mostrarMensajeError(context, "La tarea " +
                            listaTareaXOrdenTrabajo.get(getLayoutPosition()).getTarea().getNombre() + "" +
                            " está cancelada.");

                }else {
                    mostrarPaginasLecturas(getLayoutPosition());
                    notifyItemChanged(posicionOrdenTrabajoActiva);
                    notifyItemChanged(posicionSeleccionada);
                    posicionSeleccionada = getLayoutPosition();
                    posicionOrdenTrabajoActiva = posicionSeleccionada;
                    notifyItemChanged(posicionOrdenTrabajoActiva);
                    if (itemSeleccionado != null) {
                        itemSeleccionado.itemSeleccionado(posicionSeleccionada);
                    }
                }
            });
        }

        public void asignarDatosOrdenTrabajo(TareaXOrdenTrabajo tareaXOrdenTrabajo) {
            tareaXOrdenTrabajo.setCodigoUsuarioTarea(app.getUsuario().getCodigoUsuario());
            tvNombre.setText(tareaXOrdenTrabajo.getOrdenTrabajo().getNombre());
            tvDireccion.setText(tareaXOrdenTrabajo.getOrdenTrabajo().getDireccion());
            tvDescripcionTarea.setText(tareaXOrdenTrabajo.getTarea().getNombre());
            switch (tareaXOrdenTrabajo.getEstadoTarea()) {
                case CANCELADA:
                    ivIconoTareaXOrdenTrabajo.setImageResource(R.drawable.img_cancelada);
                    break;
                case LEIDA:
                case CAUSANOLECTURA:
                case CRITICADAOBSERVADA:
                case CRITICADANOOBSERVADA:
                case CRITICADACAMBIABLE:
                case EJECUTADA:
                    ivIconoTareaXOrdenTrabajo.setImageResource(R.drawable.img_ejecutado);
                    break;
                case IMPRESA:
                    ivIconoTareaXOrdenTrabajo.setImageResource(R.drawable.img_impresa);
                    break;
                case SINLEER:
                case PENDIENTE:
                    ivIconoTareaXOrdenTrabajo.setImageResource(R.drawable.img_pendiente);
                    break;
                case SINASIGNAR:
                    ivIconoTareaXOrdenTrabajo.setImageResource(R.drawable.img_sin_asignar);
                    break;
                default:
                    ivIconoTareaXOrdenTrabajo.setImageResource(R.drawable.img_pendiente);
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            pintarFila();
            if (itemSeleccionado != null) {
                itemSeleccionado.itemSeleccionado(posicionSeleccionada);
            }
        }

        private void pintarFila() {
            if (posicionSeleccionada != posicionOrdenTrabajoActiva) {
                notifyItemChanged(posicionSeleccionada);
                posicionSeleccionada = getLayoutPosition();
                notifyItemChanged(posicionSeleccionada);
            } else {
                posicionSeleccionada = getLayoutPosition();
                notifyItemChanged(posicionSeleccionada);
            }
        }

        public void cambiarColorLetra(int color) {
            tvNombre.setTextColor(color);
            tvDireccion.setTextColor(color);
        }
    }

    //TODO revisar la comparacion de null d ela tareax orden trabajo
    public void iniciarTareaXOrdenTrabajoActiva(TareaXOrdenTrabajo traeaXOrdenTrabajo) {
        if (traeaXOrdenTrabajo != null) {
            int posicion = listaTareaXOrdenTrabajo.obtenerPosicion(traeaXOrdenTrabajo);
            if (posicion > 0) {
                mostrarPaginasLecturas(posicion);
                return;
            }
        }
        mostrarPaginasLecturas(0);
    }
}