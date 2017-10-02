package com.example.santiagolopezgarcia.talleres.services.dto.carga.trabajo;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_TRABAJOXCONTRATO")
public class TrabajoXContrato extends BaseDtoCarga {

    public TrabajoXContrato() {
        CodigoContrato = "";
        CodigoTrabajo = "";
    }

    @Element
    public String CodigoContrato;

    @Element
    public String CodigoTrabajo;
}
