package com.example.dominio.bussinesslogic.notificacion;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.Item;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class ItemBL implements LogicaNegocioBase<Item> {
    ItemRepositorio itemRepositorio;

    public ItemBL(ItemRepositorio itemRepositorio) {
        this.itemRepositorio = itemRepositorio;
    }

    public boolean guardar(List<Item> listaItem) {
        try {
            return itemRepositorio.guardar(listaItem);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean guardar(Item item) {
        try {
            return itemRepositorio.guardar(item);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Item item) {
        return itemRepositorio.actualizar(item);
    }

    public boolean eliminar(Item item) {
        return itemRepositorio.eliminar(item);
    }

    public boolean tieneRegistros() {
        return itemRepositorio.tieneRegistros();
    }

    public List<Item> cargar() {
        return itemRepositorio.cargar();
    }

    public Item cargarItem(String codigoItem) {
        return itemRepositorio.cargarItem(codigoItem);
    }

    public List<String> cargarCodigosItemParaCargarAdjuntos() {
        return itemRepositorio.cargarCodigosItemParaCargarAdjuntos();
    }

    @Override
    public boolean procesar(List<Item> listaDatos, String operacion) {
        boolean respuesta = false;
        for (Item item : listaDatos) {
            switch (operacion) {
                case "A":
                    if (cargarItem(item.getCodigoItem()).getCodigoItem().isEmpty()) {
                        respuesta = guardar(item);
                    }
                    break;
                case "U":
                    respuesta = actualizar(item);
                    break;
                case "D":
                    respuesta = eliminar(item);
                    break;
                case "R":
                    if (!cargarItem(item.getCodigoItem()).getCodigoItem().isEmpty()) {
                        respuesta = actualizar(item);
                    } else {
                        respuesta = guardar(item);
                    }
                    break;
                default:
                    if (cargarItem(item.getCodigoItem()).getCodigoItem().isEmpty()) {
                        respuesta = guardar(item);
                    }
                    break;
            }
        }
        return respuesta;
    }

    public Item consultarValorItemReporteNotificacion(String codigoCorreria, String codigoOrdenTrabajo, String codigoTarea, String codigoItem, String codigoNotificacion) {
        return itemRepositorio.consultarItemReporteNotificacion(codigoCorreria,codigoOrdenTrabajo,codigoTarea, codigoItem,codigoNotificacion);
    }
}
