package com.example.santiagolopezgarcia.talleres.services.dto.carga.notificacion;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_ITEMXNOTIFICACION")
public class ItemXNotificacion extends BaseDtoCarga {

    public ItemXNotificacion() {
        CodigoNotificacion = "";
        CodigoItem = "";
        Orden = "";
        Obligatorio = "";
        Descarga = "";
    }

    @Element
    public String CodigoNotificacion;

    @Element
    public String CodigoItem;

    @Element
    public String Orden;

    @Element
    public String Obligatorio;

    @Element(required = false)
    public String Descarga;
}