package com.example.dominio.bussinesslogic.impresion;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.SeccionImpresion;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public interface SeccionImpresionRepositorio extends RepositorioBase<SeccionImpresion> {

    List<SeccionImpresion> cargar(String codigoImpresion);

    SeccionImpresion cargar(String codigoImpresion, String codigoSeccion);

}