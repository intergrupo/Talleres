package com.example.santiagolopezgarcia.talleres.services.dto.carga.acceso;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_PERFILOPCION")
public class PerfilXOpcion extends BaseDtoCarga {

    public PerfilXOpcion() {
        CodigoPerfil = "";
        CodigoOpcion = "";
    }

    @Element
    public String CodigoPerfil;

    @Element
    public String CodigoOpcion;
}
