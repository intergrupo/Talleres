package com.example.santiagolopezgarcia.talleres.presenters;

import com.example.dominio.bussinesslogic.administracion.UsuarioBL;
import com.example.dominio.modelonegocio.Opcion;
import com.example.dominio.modelonegocio.ParametrosOpcionAdministracion;
import com.example.dominio.modelonegocio.Usuario;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IAdministracionUsuarioView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class AdministracionUsuarioPresenter extends Presenter<IAdministracionUsuarioView> {

    private final String NOMBREHILOCARGADATOS = "HiloCargaDatosAdministracionUsuariosPresenter";
    private UsuarioBL usuarioBL;
    private Usuario usuario;
    private SiriusApp app;
    private Opcion opcion;
    private ParametrosOpcionAdministracion parametrosOpcionAdministracion;

    @Inject
    public AdministracionUsuarioPresenter(UsuarioBL usuarioBL) {
        this.usuarioBL = usuarioBL;
    }

    public SiriusApp getApp() {
        return app;
    }

    public void setApp(SiriusApp app) {
        this.app = app;
    }

    public List<Usuario> consultarUsuarios(){
        if(app.getUsuario().esUsuarioValido()) {
            return usuarioBL.cargarUsuariosXContrato(usuario.getContrato().getCodigoContrato());
        }else {
            return usuarioBL.cargarUsuarios();
        }
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void iniciar() {
        vista.mostrarBarraProgreso();
        Thread thread = new Thread(() -> {
            vista.cargarListaUsuarios(consultarUsuarios());
        });
        thread.setName(NOMBREHILOCARGADATOS);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }


    @Override
    public void detener() {

    }

    public Opcion getOpcion() {
        return opcion;
    }

    public void setOpcion(Opcion opcion) {
        this.opcion = opcion;
    }

    public ParametrosOpcionAdministracion getParametrosOpcionAdministracion() {
        return parametrosOpcionAdministracion;
    }

    public void setParametrosOpcionAdmistracion(ParametrosOpcionAdministracion parametrosOpcionAdministracion) {
        this.parametrosOpcionAdministracion = parametrosOpcionAdministracion;
    }
}