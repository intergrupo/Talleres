package com.example.dominio.impresion;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.ParrafoImpresion;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public interface ParrafoImpresionRepositorio extends RepositorioBase<ParrafoImpresion> {

    List<ParrafoImpresion> cargar(String codigoImpresion);

    ParrafoImpresion cargar(String codigoImpresion, String codigoParrafo);
}
