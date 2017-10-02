package com.example.santiagolopezgarcia.talleres.services.dto.carga.correria;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

@Root(name = "SIRIUS_CORRERIA")
public class Correria extends BaseDtoCarga {

    public Correria() {
        CodigoCorreria = "";
        Descripcion = "";
        Advertencia = "";
        Observacion = "";
        FechaProgramacion = "";
        CodigoEmpresa = "";
        CodigoContrato = "";
        Area = "";
        FechaCarga = "";
        FechaInicioCorreria = "";
        FechaUltimaCorreria = "";
        FechaUltimoEnvio = "";
        FechaFinJornada = "";
        FechaUltimaDescarga = "";
        Parametros = "";
        FechaUltimoRecibo = "";
        RecargaCorreria = "";
        Fecha = "";
        Sesion = "";
        NumeroTerminal = "";
    }

    @Element
    public String CodigoCorreria;

    @Element
    public String Descripcion;

    @Element(required = false)
    public String Advertencia;

    @Element(required = false)
    public String Observacion;

    @Element(required = false)
    public String FechaProgramacion;

    @Element(required = false)
    public String CodigoEmpresa;

    @Element(required = false)
    public String CodigoContrato;

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
    public String FechaFinJornada;

    @Element(required = false)
    public String FechaUltimaDescarga;

    @Element(required = false)
    public String Parametros;

    @Element(required = false)
    public String FechaUltimoRecibo;

    @Element(required = false)
    public String RecargaCorreria;

    @Element(required = false)
    public String NumeroTerminal;

    @Element(required = false)
    public String Sesion;

    @Element(required = false)
    public String Fecha;
}
