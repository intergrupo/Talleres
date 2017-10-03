package com.example.dominio.bussinesslogic.labor;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.Labor;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public interface LaborRepositorio extends RepositorioBase<Labor> {
    Labor cargarLaborxCodigo(String codigoLabor);
}
