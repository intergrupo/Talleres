package com.example.santiagolopezgarcia.talleres.services.dto.carga.ordentrabajo;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

@Root(name = "SIRIUS_ORDENTRABAJO", strict = false)
public class OrdenTrabajo extends BaseDtoCarga {

    public OrdenTrabajo() {
        CodigoCorreria = "";
        CodigoOrdenTrabajo = "";
        Nueva = "";
        Nombre = "";
        Direccion = "";
        CodigoMunicipio = "";
        GPS = "";
        Telefono = "";
        Llave1 = "";
        Llave2 = "";
        CodigoEstado = "";
        CodigoUsuarioLabor = "";
        Nodo = "";
        Transformador = "";
        Circuito = "";
        Parametros = "";
        EstadoComunicacion = "";
        Sesion = "";
        FechaCarga = "";
        ImprimirFactura = "";
        CodigoOrdenTrabajoRelacionada = "";
    }

    @Element
    public String CodigoCorreria;
    @Element
    public String CodigoOrdenTrabajo;
    @Element
    public int Secuencia;
    @Element(required = false)
    public String Nueva;
    @Element(required = false)
    public String Nombre;
    @Element(required = false)
    public String Direccion;
    @Element(required = false)
    public String CodigoMunicipio;
    @Element(required = false)
    public String GPS;
    @Element(required = false)
    public String Telefono;
    @Element(required = false)
    public String Llave1;
    @Element(required = false)
    public String Llave2;
    @Element(required = false)
    public String CodigoEstado;
    @Element(required = false)
    public String FechaInicioOrdenTrabajo;
    @Element(required = false)
    public String FechaUltimaOrdenTrabajo;
    @Element(required = false)
    public String CodigoUsuarioLabor;
    @Element(required = false)
    public String Nodo;
    @Element(required = false)
    public String Transformador;
    @Element(required = false)
    public String Circuito;
    @Element(required = false)
    public String Parametros;
    @Element(required = false)
    public String EstadoComunicacion;
    @Element(required = false)
    public String Sesion;
    @Element(required = false)
    public String FechaCarga;
    @Element(required = false)
    public String ImprimirFactura;
    @Element(required = false)
    public String CodigoOrdenTrabajoRelacionada;
}