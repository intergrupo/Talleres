package com.example.santiagolopezgarcia.talleres.services.dto.descarga.ordentrabajo;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

@Root(name = "SIRIUS_TAREAXORDENTRABAJO")
public class TareaXOrdenTrabajo {

    public TareaXOrdenTrabajo() {
        CodigoCorreria = "";
        CodigoOrdenTrabajo = "";
        CodigoTrabajo = "";
        CodigoTarea = "";
        CodigoEstado = "";
        FechaUltimaTarea = "";
        CodigoUsuarioTarea = "";
        FechaDescarga = "";
        FechaInicioTarea = "";
        Nueva = "";
        Parametros = "";
    }

    @Element
    public String CodigoCorreria;
    @Element
    public String CodigoOrdenTrabajo;
    @Element
    public String CodigoTrabajo;
    @Element
    public String CodigoTarea;
    @Element
    public String CodigoEstado;
    @Element(required = false)
    public String FechaUltimaTarea;
    @Element(required = false)
    public String CodigoUsuarioTarea;
    @Element(required = false)
    public String FechaDescarga;
    @Element
    public int Secuencia;


    @Element(required = false)
    public String FechaInicioTarea;

    @Element(required = false)
    public String Nueva;
    @Element(required = false)
    public String Parametros;
}
