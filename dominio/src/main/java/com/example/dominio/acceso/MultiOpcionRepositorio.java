package com.example.dominio.acceso;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.ListaMultiOpcion;
import com.example.dominio.modelonegocio.MultiOpcion;

import java.text.ParseException;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public interface MultiOpcionRepositorio extends RepositorioBase<MultiOpcion> {

    ListaMultiOpcion cargarListaMultiOpcionPorTipo(String codigoTipoOpcion) throws ParseException;

    ListaMultiOpcion cargar() throws ParseException;

    MultiOpcion cargarMultiOpcion(String codigoTipoOpcion, String codigoOpcion);
}
