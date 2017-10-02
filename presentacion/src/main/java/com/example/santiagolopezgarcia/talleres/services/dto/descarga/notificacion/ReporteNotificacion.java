package com.example.santiagolopezgarcia.talleres.services.dto.descarga.notificacion;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_REPORTENOTIFICACION")
public class ReporteNotificacion {

    public ReporteNotificacion() {
        CodigoCorreria = "";
        CodigoOrdenTrabajo = "";
        CodigoTarea = "";
        CodigoLabor = "";
        CodigoNotificacion = "";
        CodigoItem = "";
        CodigoLista = "";
        Orden = "";
        Descripcion = "";
        Descarga = "";
    }

    @Element
    public String CodigoCorreria;

    @Element
    public String CodigoOrdenTrabajo;

    @Element
    public String CodigoTarea;

    @Element
    public String CodigoLabor;

    @Element
    public String CodigoNotificacion;

    @Element
    public String CodigoItem;

    @Element
    public String CodigoLista;

    @Element
    public String Orden;

    @Element
    public String Descripcion;

    @Element(required = false)
    public String Descarga;
}
