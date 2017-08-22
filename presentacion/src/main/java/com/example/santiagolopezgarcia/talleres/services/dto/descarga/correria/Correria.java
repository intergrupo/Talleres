package com.example.santiagolopezgarcia.talleres.services.dto.descarga.correria;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

@Root(name = "SIRIUS_CORRERIA")
public class Correria {

    public Correria() {
        CodigoCorreria = "";
        Area = "";
        FechaCarga = "";
        FechaUltimoEnvio = "";
        FechaUltimoRecibo = "";
        FechaFinJornada = "";
        FechaUltimaDescarga = "";
        Observacion = "";
        Descripcion = "";
        Advertencia = "";
        FechaProgramacion = "";
        CodigoEmpresa = "";
        CodigoContrato = "";
        Parametros = "";
    }

    @Element
    public String CodigoCorreria;

    @Element(required = false)
    public String Area;

    @Element(required = false)
    public String FechaCarga;

    @Element(required = false)
    public String FechaInicioCorreria;

    @Element(required = false)
    public String FechaUltimaCorreria;

    @Element(required = false)
    public String FechaUltimoEnvio;

    @Element(required = false)
    public String FechaUltimoRecibo;

    @Element(required = false)
    public String FechaFinJornada;

    @Element(required = false)
    public String FechaUltimaDescarga;

    @Element(required = false)
    public String Observacion;

    @Element
    public String Descripcion;

    @Element(required = false)
    public String Advertencia;

    @Element(required = false)
    public String FechaProgramacion;

    @Element(required = false)
    public String CodigoEmpresa;

    @Element(required = false)
    public String CodigoContrato;

    @Element(required = false)
    public String Parametros;

    @Element(required = false)
    public String RecargaCorreria;

    @Element(required = false)
    public String NumeroTerminal;

    @Element(required = false)
    public String Sesion;

    @Element(required = false)
    public String Fecha;
}
