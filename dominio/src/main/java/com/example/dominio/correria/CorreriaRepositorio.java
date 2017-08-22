package com.example.dominio.correria;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.Correria;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public interface CorreriaRepositorio extends RepositorioBase<Correria> {

    Correria cargar(String codigo);

    List<Correria> cargarCorrerias() throws ParseException;

    List<Correria> cargarCorrerias(String codigo) throws ParseException;

    List<Correria> cargarXContrato(String codigoContrato);

    boolean existe(String codigo);

    boolean actualizarFechaDescarga(String codigoCorreria,String fechaDescarga);

    List<Correria> cargarXContratoYSinContrato(String codigoContrato);
}
