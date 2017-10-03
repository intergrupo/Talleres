package com.example.dominio.bussinesslogic.trabajo;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.Trabajo;
import com.example.dominio.modelonegocio.TrabajoXOrdenTrabajo;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public interface TrabajoXOrdenTrabajoRepositorio extends RepositorioBase<TrabajoXOrdenTrabajo> {

    TrabajoXOrdenTrabajo cargarTrabajosXOrdenTrabajo(String codigoCorreria, String codigoOrdenTrabajo, String codigoTrabajo);


    List<TrabajoXOrdenTrabajo> cargarLista(String codigoCorreria, String codigoOrdenTrabajo);

    List<String> cargarCodigoOrdenTrabajoXTrabajo(Trabajo trabajo);

    boolean guardar(TrabajoXOrdenTrabajo trabajoXOrdenTrabajo);

    boolean actualizar(TrabajoXOrdenTrabajo trabajoXOrdenTrabajo);

    List<TrabajoXOrdenTrabajo> cargarXCorreria(String codigoCorreria);

    List<TrabajoXOrdenTrabajo> cargarTrabajosXOrdenTrabajo(String codigoCorreria, String codigoOrdenTrabajo);
}