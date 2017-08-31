package com.example.santiagolopezgarcia.talleres.services.dto.carga.administracion;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

@Root(name = "SIRIUS")
public class Talleres extends BaseDtoCarga {

    public Talleres() {
        NumeroTerminal = "";
        RutaServidor = "";
        VersionSoftware = "";
        VersionMaestros = "";
        FechaMaestros = "";
        FechaServidor = "";
        Wifi = "";
        TipoIdentificacion = "";
        Confirmacion = "";
        Sincronizacion = "";
        Log = "";
        VersionParametrizacion = "";
    }

    @Element(required = false)
    public String NumeroTerminal;

    @Element(required = false)
    public String Wifi;

    @Element(required = false)
    public String RutaServidor;

    @Element(required = false)
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
    public int LongitudImpresionFens;

    @Element(required = false)
    public int CantidadDecimalesFens;

    @Element(required = false)
    public String Log;

    @Element(required = false)
    public String VersionParametrizacion;

}
