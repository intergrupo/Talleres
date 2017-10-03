package com.example.dominio.bussinesslogic.notificacion;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.ItemXNotificacion;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class ItemXNotificacionBL implements LogicaNegocioBase<ItemXNotificacion> {

    ItemXNotificacionRepositorio itemXNotificacionRepositorio;

    public ItemXNotificacionBL(ItemXNotificacionRepositorio itemXNotificacionRepositorio) {
        this.itemXNotificacionRepositorio = itemXNotificacionRepositorio;
    }

    public boolean guardar(List<ItemXNotificacion> listaItemXNotificacion) {
        try {
            return itemXNotificacionRepositorio.guardar(listaItemXNotificacion);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean guardar(ItemXNotificacion itemXNotificacion) {
        try {
            return itemXNotificacionRepositorio.guardar(itemXNotificacion);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(ItemXNotificacion itemXNotificacion) {
        return itemXNotificacionRepositorio.actualizar(itemXNotificacion);
    }

    public boolean eliminar(ItemXNotificacion itemXNotificacion) {
        return itemXNotificacionRepositorio.eliminar(itemXNotificacion);
    }

    public boolean tieneRegistros() {
        return itemXNotificacionRepositorio.tieneRegistros();
    }

    public List<ItemXNotificacion> cargar() {
        return itemXNotificacionRepositorio.cargar();
    }

    public List<ItemXNotificacion> cargarXCodigoNotificacion(String codigoNotificacion) {
        return itemXNotificacionRepositorio.cargarXCodigoNotificacion(codigoNotificacion);
    }

    public List<ItemXNotificacion> cargarXCodigoNotificacion(String codigoNotificacion, String codigoOT) {
        return itemXNotificacionRepositorio.cargarXCodigoNotificacion(codigoNotificacion, codigoOT);
    }

    public List<ItemXNotificacion> cargarItemsSinGuardar(String codigoNotificacion, String codigoCorreria, String codigoOrdenTrabajo,
                                                         String codigoTarea, String codigoLabor) {
        return itemXNotificacionRepositorio.cargarSinDatos(codigoNotificacion, codigoCorreria, codigoOrdenTrabajo,
                codigoTarea, codigoLabor);
    }

    public List<ItemXNotificacion> cargarXCodigoNotificacion(String codigoNotificacion, String codigoCorreria, String codigoOT,
                                                             String codigoTarea, String codigoLabor) {
        return itemXNotificacionRepositorio.cargarXCodigoNotificacion(codigoNotificacion, codigoCorreria, codigoOT,
                codigoTarea, codigoLabor);
    }

    public ItemXNotificacion cargarXCodigoNotificacionEItem(String codigoNotificacion,
                                                            String codigoItem) {
        return itemXNotificacionRepositorio.cargarXCodigoNotificacionEItem(codigoNotificacion, codigoItem);
    }

    public List<ItemXNotificacion> cargarObligatoriosXCodigoNotificacion(String codigoNotificacion) {
        return itemXNotificacionRepositorio.cargarObligatoriosXCodigoNotificacion(codigoNotificacion);
    }

    public List<ItemXNotificacion> cargarNoObligatoriosXCodigoNotificacion(String codigoNotificacion) {
        return itemXNotificacionRepositorio.cargarNoObligatoriosXCodigoNotificacion(codigoNotificacion);
    }

    @Override
    public boolean procesar(List<ItemXNotificacion> listaDatos, String operacion) {
        boolean respuesta = false;
        for (ItemXNotificacion itemXNotificacion : listaDatos) {
            switch (operacion) {
                case "A":
                    if (!cargarXCodigoNotificacionEItem(itemXNotificacion.getCodigoNotificacion(),
                            itemXNotificacion.getCodigoItem()).esClaveLlena()) {
                        respuesta = guardar(itemXNotificacion);
                    }
                    break;
                case "U":
                    respuesta = actualizar(itemXNotificacion);
                    break;
                case "D":
                    respuesta = eliminar(itemXNotificacion);
                    break;
                case "R":
                    if (cargarXCodigoNotificacionEItem(itemXNotificacion.getCodigoNotificacion(),
                            itemXNotificacion.getCodigoItem()).esClaveLlena()) {
                        respuesta = actualizar(itemXNotificacion);
                    } else {
                        respuesta = guardar(itemXNotificacion);
                    }
                    break;
                default:
                    if (!cargarXCodigoNotificacionEItem(itemXNotificacion.getCodigoNotificacion(),
                            itemXNotificacion.getCodigoItem()).esClaveLlena()) {
                        respuesta = guardar(itemXNotificacion);
                    }
                    break;
            }
        }
        return respuesta;
    }
}
