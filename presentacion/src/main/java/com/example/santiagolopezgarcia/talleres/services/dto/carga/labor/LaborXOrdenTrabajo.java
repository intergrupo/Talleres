package com.example.santiagolopezgarcia.talleres.services.dto.carga.labor;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_LABORXORDENTRABAJO")
public class LaborXOrdenTrabajo extends BaseDtoCarga {

    public LaborXOrdenTrabajo() {
        CodigoCorreria = "";
        CodigoOrdenTrabajo = "";
        CodigoTrabajo = "";
        CodigoTarea = "";
        CodigoLabor = "";
        CodigoUsuarioLabor = "";
        CodigoEstado = "";
        Parametros = "";
        FechaInicioLabor = "";
        FechaUltimoLabor = "";
    }

    @Element()
    public String CodigoCorreria;

    @Element()
    public String CodigoOrdenTrabajo;

    @Element()
    public String CodigoTrabajo;

    @Element()
    public String CodigoTarea;

    @Element()
    public String CodigoLabor;

    @Element(required = false)
    public String CodigoUsuarioLabor;

    @Element()
    public String CodigoEstado;

    @Element(required = false)
    public String FechaInicioLabor;

    @Element(required = false)
    public String FechaUltimoLabor;

    @Element(required = false)
    public String Parametros;

    @Element(required = false)
    public String IdOrdenTrabajo;
}
