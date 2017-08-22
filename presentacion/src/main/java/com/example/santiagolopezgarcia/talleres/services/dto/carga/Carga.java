package com.example.santiagolopezgarcia.talleres.services.dto.carga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

@Root(name = "CARGA")
public class Carga extends BaseDtoCarga {

    public Carga() {
        VersionSoftware = "";
        VersionMaestros = "";
    }

    @Element(required = false)
    public String VersionSoftware;

    @Element(required = false)
    public String VersionMaestros;

}