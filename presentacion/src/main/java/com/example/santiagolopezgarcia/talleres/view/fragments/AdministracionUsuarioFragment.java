package com.example.santiagolopezgarcia.talleres.view.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dominio.modelonegocio.Opcion;
import com.example.dominio.modelonegocio.ParametrosOpcionAdministracion;
import com.example.dominio.modelonegocio.Usuario;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.presenters.AdministracionUsuarioPresenter;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;
import com.example.santiagolopezgarcia.talleres.view.adapters.UsuarioAdapter;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IAdministracionUsuarioView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class AdministracionUsuarioFragment extends BaseFragment<AdministracionUsuarioPresenter>
        implements IAdministracionUsuarioView {

    @BindView(R.id.tvIdentificacionAdmin)
    TextView tvIdentificacion;
    @BindView(R.id.tvUsuarioAdmin)
    TextView tvUsuario;
    @BindView(R.id.tvPerfilUsuarioAdmin)
    TextView tvPerfil;
    @BindView(R.id.rvAdministracionUsuarios)
    RecyclerView rvUsuarios;
    private Usuario usuario;
    private UsuarioAdapter usuarioAdapter;

    public AdministracionUsuarioFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vistaUsuario = inflater.inflate(R.layout.fragment_administracion_usuario, container, false);
        ButterKnife.bind(this, vistaUsuario);
        dependencia = new ContenedorDependencia(getActivity().getApplication());
        dependencia.getContenedor().build().inject(this);
        presentador.adicionarVista(this);
        obtenerParametros();
        asignarDatos();
        iniciar();
        return vistaUsuario;
    }

    private void obtenerParametros() {
        presentador.setOpcion((Opcion) getArguments().getSerializable(Opcion.class.getName()));
        if (presentador.getOpcion() != null) {
            presentador.setParametrosOpcionAdmistracion(
                    new ParametrosOpcionAdministracion(presentador.getOpcion().getParametros()));
        }else {
            presentador.setParametrosOpcionAdmistracion(
                    new ParametrosOpcionAdministracion(""));
        }

    }

    public void iniciar() {
        presentador.iniciar();
    }

    public void asignarDatos() {
        presentador.setApp(((SiriusApp) getActivity().getApplication()));
        Usuario usuario = presentador.getApp().getUsuario();
        presentador.setUsuario(usuario);
        tvIdentificacion.setText(usuario.getCodigoUsuario());
        tvUsuario.setText(usuario.getNombre());
        tvPerfil.setText(usuario.getPerfil().getDescripcion());
    }

    public void adicionarUsuario(Usuario usuario) {
        usuarioAdapter.adicionarUsuario(usuario);
    }

    @Override
    public void mostrarBarraProgreso() {

    }

    @Override
    public void ocultarBarraProgreso() {

    }

    @Override
    public void cargarListaUsuarios(List<Usuario> listaUsuarios) {
        getActivity().runOnUiThread(() -> {
            usuarioAdapter = new UsuarioAdapter(((SiriusApp) getActivity().getApplication()),
                    getContext(), listaUsuarios, presentador.getParametrosOpcionAdministracion());
            rvUsuarios.setAdapter(usuarioAdapter);
            rvUsuarios.setLayoutManager(new LinearLayoutManager(getContext()));
        });
    }


}

