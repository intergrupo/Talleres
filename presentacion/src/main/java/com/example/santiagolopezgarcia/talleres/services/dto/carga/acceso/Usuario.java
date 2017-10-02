package com.example.santiagolopezgarcia.talleres.services.dto.carga.acceso;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_USUARIO")
public class Usuario extends BaseDtoCarga {

    public Usuario() {
        CodigoUsuario = "";
        CodigoPerfil = "";
        Nombre = "";
        CodigoContrato = "";
        Clave = "";
        FechaIngreso = "";
        CodigoUsuarioIngreso = "";
    }

    @Element
    public String CodigoUsuario;
    @Element
    public String CodigoPerfil;
    @Element
    public String Nombre;
    @Element
    public String CodigoContrato;
    @Element(required = false)
    public String Clave;
    @Element(required = false)
    public String FechaIngreso;
    @Element(required = false)
    public String CodigoUsuarioIngreso;
}