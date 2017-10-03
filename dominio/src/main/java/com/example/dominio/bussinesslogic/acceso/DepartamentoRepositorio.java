package com.example.dominio.bussinesslogic.acceso;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.Departamento;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public interface DepartamentoRepositorio extends RepositorioBase<Departamento> {

    Departamento cargarDepartamento(String codigoDepartamento) throws ParseException;

    List<Departamento> cargarDepartamentos() throws ParseException;
}
