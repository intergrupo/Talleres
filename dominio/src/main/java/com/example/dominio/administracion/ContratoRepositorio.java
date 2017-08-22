package com.example.dominio.administracion;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.Contrato;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public interface ContratoRepositorio extends RepositorioBase<Contrato> {

    List<Contrato> cargarContratos() throws ParseException;

    Contrato cargarContratoXCodigo(String codigoContrato);
}