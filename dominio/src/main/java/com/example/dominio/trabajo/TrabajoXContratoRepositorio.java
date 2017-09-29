package com.example.dominio.trabajo;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.TrabajoXContrato;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public interface TrabajoXContratoRepositorio extends RepositorioBase<TrabajoXContrato> {

    List<TrabajoXContrato> cargarTrabajoXContratos(String codigoContrato);

    TrabajoXContrato cargarTrabajoXContrato(String codigoContrato, String codigoTrabajo);
}