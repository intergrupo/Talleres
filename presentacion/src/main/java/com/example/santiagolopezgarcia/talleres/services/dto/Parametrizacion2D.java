package com.example.santiagolopezgarcia.talleres.services.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

@Root(name = "Info")
public class Parametrizacion2D implements Serializable {

    public Parametrizacion2D() {
        Version = "";
        Fecha = "";
        Descripcion = "";
        Vencimiento = "";
        Tabla = new Tabla();
    }

    @Element(required = false)
    public String Version;

    @Element
    public String Fecha;

    @Element
    public String Descripcion;

    @Element(required = false)
    public String Vencimiento;

    @Element
    public Tabla Tabla;
}
