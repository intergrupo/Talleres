package com.example.santiagolopezgarcia.talleres.services.dto.descarga.administracion;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS")
public class Talleres {

    static final String TIPO_IDENTIFICACION_AMBOS  = "A";

    public Talleres() {
        NumeroTerminal = "";
        RutaServidor = "";
        VersionSoftware = "";
        VersionMaestros = "";
        FechaMaestros = "";
        FechaServidor = "";
        Wifi = "";
        TipoIdentificacion = TIPO_IDENTIFICACION_AMBOS;
        Confirmacion = "";
        Sincronizacion = "";
        Log = "";
    }

    @Element(required = false)
    public String NumeroTerminal;

    @Element(required = false)
    public String Wifi;

    @Element(required = false)
    public String RutaServidor;

    @Element
    public String VersionSoftware;

    @Element(required = false)
    public String VersionMaestros;

    @Element(required = false)
    public String FechaMaestros;

    @Element(required = false)
    public String FechaServidor;

    @Element(required = false)
    public String TipoIdentificacion;

    @Element(required = false)
    public String Confirmacion;

    @Element(required = false)
    public String Sincronizacion;

    @Element(required = false)
    public String Log;
}