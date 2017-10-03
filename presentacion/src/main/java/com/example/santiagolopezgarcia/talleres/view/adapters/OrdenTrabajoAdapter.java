package com.example.santiagolopezgarcia.talleres.view.adapters;

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
import com.example.dominio.bussinesslogic.tarea.TareaXOrdenTrabajoBL;
import com.example.dominio.modelonegocio.ClasificacionVista;
import com.example.dominio.modelonegocio.ListaOrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.dominio.modelonegocio.Tarea;
import com.example.dominio.modelonegocio.TareaXTrabajoOrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;
import com.example.santiagolopezgarcia.talleres.view.interfaces.InvocarActivity;
import com.example.santiagolopezgarcia.talleres.view.interfaces.Item;
import com.example.santiagolopezgarcia.talleres.view.interfaces.ItemSeleccionado;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class OrdenTrabajoAdapter extends RecyclerView.Adapter<OrdenTrabajoAdapter.OrdenTrabajoViewHolder> {

    int posicionSeleccionada = 0;
    InvocarActivity invicarActivity;
    ListaOrdenTrabajo listaOrdenTrabajoFiltrados;
    ItemSeleccionado itemSeleccionado;
    Item itemActivo;
    int posicionOrdenTrabajoActiva = 0;
    @Inject
    TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL;
    @Inject
    OrdenTrabajoBL ordenTrabajoBL;
    SiriusApp app;
    private ContenedorDependencia dependencia;


    @Override
    public int getItemViewType(int position) {
        return listaOrdenTrabajoFiltrados.obtenerPosicion(listaOrdenTrabajoFiltrados.get(position));
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

    public OrdenTrabajoAdapter(InvocarActivity invicarActivity, ListaOrdenTrabajo listaOrdenTrabajo,
                               int posicionOrdenTrabajoActiva, SiriusApp app) {
        this.invicarActivity = invicarActivity;
        this.listaOrdenTrabajoFiltrados = listaOrdenTrabajo;
        this.posicionOrdenTrabajoActiva = posicionOrdenTrabajoActiva;
        this.dependencia = new ContenedorDependencia(app);
        this.dependencia.getContenedor().build().inject(this);
        this.app = app;
    }

    @Override
    public OrdenTrabajoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_orden_trabajo, parent, false);
        return new OrdenTrabajoViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(OrdenTrabajoViewHolder holder, int position) {
        final OrdenTrabajo ordenTrabajo = listaOrdenTrabajoFiltrados.get(position);
        holder.actualizarEstadoOrdenTrabajo(ordenTrabajo);
        holder.asignarDatosOrdenTrabajo(ordenTrabajo);
        holder.itemView.setTag(ordenTrabajo);
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

    private void mostrarOrdenTrabajo(int posicionOT) {
        Intent intent = new Intent();
        intent.putExtra("positionCurrentItem", posicionOT);
        this.invicarActivity.invocar(intent, ClasificacionVista.ORDENTRABAJO);
        if (itemActivo != null) {
            itemActivo.itemProcesado(listaOrdenTrabajoFiltrados.get(posicionOT));
        }
    }

    @Override
    public int getItemCount() {
        return (null != listaOrdenTrabajoFiltrados ? listaOrdenTrabajoFiltrados.size() : 0);
    }

    public class OrdenTrabajoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvCodigoOrdenTrabajo;
        private final TextView tvNombre;
        private final TextView tvDireccion;
        private final ImageView ivIconoOrdenTrabajo;

        public OrdenTrabajoViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvCodigoOrdenTrabajo = (TextView) itemView.findViewById(R.id.tvCodigoOrdenTrabajo);
            tvNombre = (TextView) itemView.findViewById(R.id.tvNombre);
            tvDireccion = (TextView) itemView.findViewById(R.id.tvDireccion);
            ivIconoOrdenTrabajo = (ImageView) itemView.findViewById(R.id.ivIconoTareaOrdenTrabajo);
            ivIconoOrdenTrabajo.setOnClickListener(v -> {
                notifyItemChanged(posicionOrdenTrabajoActiva);
                notifyItemChanged(posicionSeleccionada);
                posicionSeleccionada = getLayoutPosition();
                posicionOrdenTrabajoActiva = posicionSeleccionada;
                notifyItemChanged(posicionOrdenTrabajoActiva);
                mostrarOrdenTrabajo(posicionSeleccionada);
                if (itemSeleccionado != null) {
                    itemSeleccionado.itemSeleccionado(posicionSeleccionada);
                }
                TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 400);
                animation.setDuration(100);
                v.startAnimation(animation);
            });
        }

        public void actualizarEstadoOrdenTrabajo(OrdenTrabajo ordenTrabajo){
            List<TareaXTrabajoOrdenTrabajo> listaTareaXOrdenTrabajo = obtenerTareaXTrabajoOT(ordenTrabajo);
            int contadorTareasCanceladas = 0;
            if (listaTareaXOrdenTrabajo.size() > 0) {
                for (TareaXTrabajoOrdenTrabajo tareaXTrabajoOrdenTrabajo : listaTareaXOrdenTrabajo) {
                    if (tareaXTrabajoOrdenTrabajo.getEstadoTarea().equals(Tarea.EstadoTarea.CANCELADA))
                        contadorTareasCanceladas++;
                }
                if (contadorTareasCanceladas == listaTareaXOrdenTrabajo.size()) {
                    cambiarEstadoOT(ordenTrabajo,OrdenTrabajo.EstadoOrdenTrabajo.CANCELADA);
                }
            }
        }

        private List<TareaXTrabajoOrdenTrabajo> obtenerTareaXTrabajoOT(OrdenTrabajo ordenTrabajo){
            return tareaXOrdenTrabajoBL.cargarListaTareaXTrabajoOrdenTrabajo(ordenTrabajo.getCodigoCorreria(),
                    ordenTrabajo.getCodigoOrdenTrabajo());
        }

        private void cambiarEstadoOT(OrdenTrabajo ordenTrabajo,OrdenTrabajo.EstadoOrdenTrabajo estadoOrdenTrabajo){
            ordenTrabajo.setEstado(estadoOrdenTrabajo);
            ordenTrabajoBL.actualizarOrdenTrabajo(ordenTrabajo);
        }

        public void asignarDatosOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
            ordenTrabajo.setCodigoUsuarioLabor(app.getUsuario().getCodigoUsuario());
            tvCodigoOrdenTrabajo.setText(ordenTrabajo.getCodigoOrdenTrabajo());
            tvNombre.setText(ordenTrabajo.getNombre());
            tvDireccion.setText(ordenTrabajo.getDireccion());
            switch (ordenTrabajo.getEstado()) {
                case ENEJECUCION:
                    ivIconoOrdenTrabajo.setImageResource(R.drawable.img_pendiente);
                    break;
                case CANCELADA:
                    ivIconoOrdenTrabajo.setImageResource(R.drawable.img_cancelada);
                    break;
                case FACTURADOIMPRESO:
                    ivIconoOrdenTrabajo.setImageResource(R.drawable.img_ejecutado) ;
                    break;
                default:
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

    //TODO revisar el null de la orden de trabajo
    public void iniciarOrdenTrabajoActiva(OrdenTrabajo ordenTrabajo) {
        int posicion = listaOrdenTrabajoFiltrados.obtenerPosicion(ordenTrabajo);
        if (posicion > 0) {
            mostrarOrdenTrabajo(posicion);
            return;
        }
        mostrarOrdenTrabajo(0);
    }
}
