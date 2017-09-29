package com.example.dominio.acceso;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.Municipio;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public interface MunicipioRepositorio extends RepositorioBase<Municipio> {

    List<Municipio> cargarMunicipios(String codigoDepartamento) throws ParseException;

    Municipio cargarMunicipio(String codigoMunicipio) throws ParseException;
}
