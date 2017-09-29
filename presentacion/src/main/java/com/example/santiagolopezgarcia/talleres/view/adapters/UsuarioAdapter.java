package com.example.santiagolopezgarcia.talleres.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dominio.administracion.UsuarioBL;
import com.example.dominio.modelonegocio.ParametrosOpcionAdministracion;
import com.example.dominio.modelonegocio.Usuario;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;
import com.example.santiagolopezgarcia.talleres.view.activities.LoginActivity;
import com.example.santiagolopezgarcia.talleres.view.interfaces.UsuarioActivo;
import com.example.santiagolopezgarcia.talleres.view.popups.AlertaPopUp;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioHolder> {

    @Inject
    UsuarioBL usuarioBL;
    private SiriusApp application;
    private int ultimaPosicion;
    private LayoutInflater inflater;
    private List<Usuario> listaUsuarios = Collections.emptyList();
    private ContenedorDependencia dependencia;
    private Context context;
    private ParametrosOpcionAdministracion parametrosOpcionAdministracion;

    private UsuarioActivo<Usuario> usuarioActivo;

    public UsuarioAdapter(SiriusApp application, Context context, List<Usuario> listaUsuarios,
                          ParametrosOpcionAdministracion parametrosOpcionAdministracion) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listaUsuarios = listaUsuarios;
        this.parametrosOpcionAdministracion = parametrosOpcionAdministracion;

        if (context instanceof UsuarioActivo) {
            usuarioActivo = (UsuarioActivo) context;
        }
        this.application = application;
        dependencia = new ContenedorDependencia(application);
        dependencia.getContenedor().build().inject(this);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public UsuarioHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_usuario, parent, false);
        UsuarioHolder holder = new UsuarioHolder(view);
        return holder;
    }

    public void adicionarUsuario(Usuario usuario) {
        listaUsuarios.add(0, usuario);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(UsuarioHolder holder, int position) {

        Usuario usuario = listaUsuarios.get(position);
        asignarDatosUsuario(holder, usuario, position);
    }

    private void asignarDatosUsuario(UsuarioHolder holder, Usuario usuario, int posicion) {
        holder.itemView.setTag(usuario);

        if(usuario.getCodigoUsuario().equals(application.getUsuario().getCodigoUsuario())){
            holder.cardViewUsuario.setCardBackgroundColor(context.getResources().getColor(R.color.grisclaro2));
        }
        holder.tvNombre.setText(usuario.getNombre());
        holder.tvCodigo.setText(usuario.getCodigoUsuario());
        holder.tvPerfil.setText(usuario.getPerfil().getDescripcion());
        holder.tvContrato.setText(usuario.getContrato().getNombre());
        holder.asignarEventos(posicion);
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }


    public class UsuarioHolder extends RecyclerView.ViewHolder {

        TextView tvNombre;
        TextView tvCodigo;
        TextView tvPerfil;
        TextView tvContrato;
        LinearLayout lyEliminarUsuario;
        CardView cardViewUsuario;

        public UsuarioHolder(View itemView) {

            super(itemView);

            tvNombre = (TextView) itemView.findViewById(R.id.tvNombreUsuarioAdministracion);
            tvCodigo = (TextView) itemView.findViewById(R.id.tvCodigoUsuarioAdministracion);
            tvPerfil = (TextView) itemView.findViewById(R.id.tvPerfilUsuarioAdministracion);
            tvContrato = (TextView) itemView.findViewById(R.id.tvContratoUsuarioAdministracion);
            lyEliminarUsuario = (LinearLayout) itemView.findViewById(R.id.lyEliminarUsuario);
            cardViewUsuario = (CardView) itemView.findViewById(R.id.cvUsuario);

            if (!application.getUsuario().esUsuarioValido()
                    || parametrosOpcionAdministracion.isAdminPersonalizada()) {
                lyEliminarUsuario.setVisibility(View.GONE);
            }


        }

        public void asignarEventos(int posicion) {

//            itemView.setOnClickListener(v -> {
//                pintarFila(posicion);
//                ultimaPosicion = posicion;
//                lyEliminarUsuario.setVisibility(View.VISIBLE);
//                cardViewUsuario.setCardBackgroundColor(context.getResources().getColor(R.color.grisclaro2));
//            });

            lyEliminarUsuario.setOnClickListener(v -> {
                AlertaPopUp alertaPopUp = new AlertaPopUp();
                alertaPopUp.setTitle(R.string.titulo_eliminar_usuario);
                alertaPopUp.setMessage(context.getResources().getString(R.string.alerta_eliminar_usuario) + ((Usuario) itemView.getTag()).getNombre() + "?");
                alertaPopUp.setContext(context);
                alertaPopUp.setPositiveButton("SI", (dialog, id) -> {
                    if (((Usuario) itemView.getTag()).getCodigoUsuario().equals(application.getUsuario().getCodigoUsuario())) {
                        mostrarAlertaUsuarioActivo(posicion);
                    } else {
                        usuarioBL.eliminarUsuario((Usuario) itemView.getTag());
                        eliminarUsuario(posicion);
                    }
                    dialog.dismiss();
                });
                alertaPopUp.setNegativeButton("NO", (dialog, id) -> {
                    dialog.dismiss();
                });

                alertaPopUp.show();

            });
        }

        private void mostrarAlertaUsuarioActivo(int posicion) {
            AlertaPopUp alertaPopUp = new AlertaPopUp();
            alertaPopUp.setTitle(R.string.titulo_eliminar_usuario_activo);
            alertaPopUp.setMessage(R.string.alerta_eliminar_usuario_activo);
            alertaPopUp.setContext(context);
            alertaPopUp.setPositiveButton("SI", (dialog, id) -> {
                usuarioBL.eliminarUsuario((Usuario) itemView.getTag());
                eliminarUsuario(posicion);
                context.startActivity(new Intent(context, LoginActivity.class));
                dialog.dismiss();

            });
            alertaPopUp.setNegativeButton("NO", (dialog, id) -> {
                dialog.dismiss();

            });

            alertaPopUp.show();
        }

        private void pintarFila(int posicionSeleccionada) {
            notifyItemChanged(ultimaPosicion);
//            posicionSeleccionada = getLayoutPosition();
            notifyItemChanged(posicionSeleccionada);
        }

    }

    public void eliminarUsuario(int posicion) {
        int posicionUsuario = posicion;
        listaUsuarios.remove(posicionUsuario);
        notifyItemRemoved(posicionUsuario);
        notifyItemRangeChanged(posicionUsuario, listaUsuarios.size());
        //notifyDataSetChanged();
    }

}