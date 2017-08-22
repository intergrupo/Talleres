package com.example.santiagolopezgarcia.talleres.integracion.carga.notificacion;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

@Root(name = "SIRIUS_REPORTENOTIFICACION")
public class ReporteNotificacion extends BaseDtoCarga {

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

    @Element(required = false)
    public String Descripcion;

    @Element(required = false)
    public String Descarga;

}