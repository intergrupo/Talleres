package com.example.santiagolopezgarcia.talleres.view.interfaces;

import com.example.dominio.modelonegocio.Correria;
import com.example.dominio.modelonegocio.Usuario;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public interface LoginView extends BaseView {

    void mostrarCorrerias();

    void mostrarListaCorrerias(List<Correria> listaCorrerias);

    String obtenerUsuario();

    String obtenerClave();

    void mostrarCambioClave(Usuario usuario);

    void limpiarControlesEdicion();

    void mostrarMensajeError(final String mensaje);

    void mostrarNumeroTerminal(String numeroTerminal);

    void mostrarPopUpNumeroTerminal();

    void bloquearTipoIdentificacionDigitado(boolean bloquear);
}

