package com.example.santiagolopezgarcia.talleres.services.dto.descarga.trabajo;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_TRABAJOXORDENTRABAJO")
public class TrabajoXOrdenTrabajo {

    public TrabajoXOrdenTrabajo() {
        CodigoCorreria = "";
        CodigoOrdenTrabajo = "";
        CodigoTrabajo = "";
    }

    @Element
    public String CodigoCorreria;

    @Element
    public String CodigoOrdenTrabajo;

    @Element
    public String CodigoTrabajo;
}
