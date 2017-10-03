package com.example.dominio.bussinesslogic.correria;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.Empresa;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public interface EmpresaRepositorio extends RepositorioBase<Empresa> {

    Empresa cargarEmpresa(Empresa empresa);
    Empresa cargar(String codigoEmpresa);
}
