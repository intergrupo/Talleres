package com.example.dominio.notificacion;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.ItemXNotificacion;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public interface ItemXNotificacionRepositorio extends RepositorioBase<ItemXNotificacion> {
    List<ItemXNotificacion> cargar();

    List<ItemXNotificacion> cargarXCodigoNotificacion(String codigoNotificacion);

    List<ItemXNotificacion> cargarXCodigoNotificacion(String codigoNotificacion, String codigoOT);

    List<ItemXNotificacion> cargarXCodigoNotificacion(String codigoNotificacion, String codigoCorreria, String codigoOT,
                                                      String codigoTarea, String codigoLabor);

    List<ItemXNotificacion> cargarObligatoriosXCodigoNotificacion(String codigoNotificacion);

    ItemXNotificacion cargarXCodigoNotificacionEItem(String codigoNotificacion,
                                                     String codigoItem);

    List<ItemXNotificacion> cargarSinDatos(String codigoNotificacion, String codigoCorreria, String ordenTrabajo, String codigoTarea, String codigoLabor);

    List<ItemXNotificacion> cargarNoObligatoriosXCodigoNotificacion(String codigoNotificacion);
}