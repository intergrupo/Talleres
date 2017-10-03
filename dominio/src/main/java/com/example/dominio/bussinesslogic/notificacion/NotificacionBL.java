package com.example.dominio.bussinesslogic.notificacion;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.Notificacion;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class NotificacionBL implements LogicaNegocioBase<Notificacion> {

    NotificacionRepositorio notificacionRepositorio;

    public NotificacionBL(NotificacionRepositorio notificacionRepositorio) {
        this.notificacionRepositorio = notificacionRepositorio;
    }

    public boolean guardar(List<Notificacion> listaNotificacion) {
        try {
            return notificacionRepositorio.guardar(listaNotificacion);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean guardar(Notificacion notificacion) {
        try {
            return notificacionRepositorio.guardar(notificacion);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Notificacion notificacion) {
        return notificacionRepositorio.actualizar(notificacion);
    }

    public boolean eliminar(Notificacion notificacion) {
        return notificacionRepositorio.eliminar(notificacion);
    }

    public boolean tieneRegistros() {
        return notificacionRepositorio.tieneRegistros();
    }

    public List<Notificacion> cargar() {
        return notificacionRepositorio.cargar();
    }

    public Notificacion cargarXCodigoNotificacion(String codigoNotificacion) {
        return notificacionRepositorio.cargarNotificacion(codigoNotificacion);
    }

    @Override
    public boolean procesar(List<Notificacion> listaDatos, String operacion) {
        boolean respuesta = false;
        for (Notificacion notificacion : listaDatos) {
            switch (operacion) {
                case "A":
                    if (cargarXCodigoNotificacion(notificacion.getCodigoNotificacion()).getCodigoNotificacion().isEmpty()) {
                        respuesta = guardar(notificacion);
                    }
                    break;
                case "U":
                    respuesta = actualizar(notificacion);
                    break;
                case "D":
                    respuesta = eliminar(notificacion);
                    break;
                case "R":
                    if (!cargarXCodigoNotificacion(notificacion.getCodigoNotificacion()).getCodigoNotificacion().isEmpty()) {
                        respuesta = actualizar(notificacion);
                    } else {
                        respuesta = guardar(notificacion);
                    }
                    break;
                default:
                    if (cargarXCodigoNotificacion(notificacion.getCodigoNotificacion()).getCodigoNotificacion().isEmpty()) {
                        respuesta = guardar(notificacion);
                    }
                    break;
            }
        }
        return respuesta;
    }
}
