package com.example.dominio.notificacion;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.Notificacion;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public interface NotificacionRepositorio extends RepositorioBase<Notificacion> {
    List<Notificacion> cargar();

    Notificacion cargarNotificacion(String codigoNotificacion);
}