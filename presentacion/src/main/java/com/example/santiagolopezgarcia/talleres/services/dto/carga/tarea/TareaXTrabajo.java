package com.example.santiagolopezgarcia.talleres.services.dto.carga.tarea;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/3/17.
 */

@Root(name = "SIRIUS_TAREAXTRABAJO")
public class TareaXTrabajo extends BaseDtoCarga {

    public TareaXTrabajo() {
        CodigoTarea = "";
        CodigoTrabajo = "";
    }

    @Element
    public String CodigoTarea;

    @Element
    public String CodigoTrabajo;

}
