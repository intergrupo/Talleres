package com.example.santiagolopezgarcia.talleres.services.dto.descarga.confirmacion;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

@Root(name = "SIRIUS_CONFIRMACIONOT")
public class ConfirmacionOrdenTrabajo {

    public ConfirmacionOrdenTrabajo() {
        CodigoCorreria = "";
        CodigoOrdenTrabajo = "";
        NumeroTerminal = "";
        Fecha = "";
        Sesion = "";
        Estado = "";
    }

    @Element
    public String CodigoCorreria;

    @Element
    public String CodigoOrdenTrabajo;

    @Element
    public String NumeroTerminal;

    @Element
    public String Fecha;

    @Element
    public String Sesion;

    @Element
    public String Estado;
}
