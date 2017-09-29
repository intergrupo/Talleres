package com.example.santiagolopezgarcia.talleres.services.dto.carga.ordentrabajo;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

@Root(name = "SIRIUS_NOTIFICACIONOT")
public class NotificacionOrdenTrabajo extends BaseDtoCarga {

    public NotificacionOrdenTrabajo() {
        CodigoCorreria = "";
        CodigoOrdenTrabajo = "";
        CodigoTarea = "";
        Operacion = "";
    }

    @Element
    public String CodigoCorreria;

    @Element
    public String CodigoOrdenTrabajo;

    @Element(required = false)
    public String CodigoTarea;

    @Element(required = false)
    public String Operacion;
}