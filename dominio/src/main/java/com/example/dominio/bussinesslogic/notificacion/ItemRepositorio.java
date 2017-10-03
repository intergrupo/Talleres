package com.example.dominio.bussinesslogic.notificacion;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.Item;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public interface ItemRepositorio extends RepositorioBase<Item> {
    List<Item> cargar();

    Item cargarItem(String codigoItem);

    List<String> cargarCodigosItemParaCargarAdjuntos();

    Item consultarItemReporteNotificacion(String codigoCorreria, String codigoOrdenTrabajo, String codigoTarea, String codigoItem, String codigoNotificacion);
}
