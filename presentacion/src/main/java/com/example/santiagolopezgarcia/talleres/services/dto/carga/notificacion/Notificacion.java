package com.example.santiagolopezgarcia.talleres.services.dto.carga.notificacion;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_NOTIFICACION")
public class Notificacion extends BaseDtoCarga {

    public Notificacion() {
        CodigoNotificacion = "";
        Descripcion = "";
        Rutina = "";
        Parametros = "";
    }

    @Element
    public String CodigoNotificacion;

    @Element
    public String Descripcion;

    @Element
    public String Rutina;

    @Element(required = false)
    public String Parametros;
}
