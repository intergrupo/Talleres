package com.example.santiagolopezgarcia.talleres.view.interfaces;

import com.example.dominio.modelonegocio.Usuario;
import com.example.santiagolopezgarcia.talleres.SiriusApp;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public interface IAdministracionView extends BaseView {

    void adicionarUsuario(Usuario usuario);

    void desactivarOpcionesCorreria(boolean activar);

    void cerrarSesion();

    void refrescarUrl(String url);

    void salir();

    void irALogin(boolean cerrar);

    void refrescarDatos(SiriusApp siriusApp);

    void actualizarMaestrosVista();

    void moverPosicion(int posicion);

    void actualizarVista();
}