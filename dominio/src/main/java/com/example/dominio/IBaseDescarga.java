package com.example.dominio;


import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 21/06/16.
 */
public interface IBaseDescarga<T> {

    List<T> cargarXCorreria(String codigoCorreria);
    List<T> cargarXFiltro(OrdenTrabajoBusqueda filtro);
}
