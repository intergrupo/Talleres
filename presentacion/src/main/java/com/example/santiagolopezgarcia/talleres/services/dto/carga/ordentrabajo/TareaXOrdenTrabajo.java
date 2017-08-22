package com.example.santiagolopezgarcia.talleres.services.dto.carga.ordentrabajo;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;
import com.example.utilidades.helpers.DateHelper;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

@Root(name = "SIRIUS_TAREAXORDENTRABAJO")
public class TareaXOrdenTrabajo extends BaseDtoCarga {

    public TareaXOrdenTrabajo() {
        CodigoCorreria = "";
        CodigoOrdenTrabajo = "";
        CodigoTarea = "";
        CodigoTrabajo = "";
        FechaInicioTarea = "";
        FechaUltimaTarea = "";
        CodigoUsuarioTarea = "";
        Nueva = "";
        FechaDescarga = "";
        Parametros = "";
    }

    @Element
    public String CodigoCorreria;
    @Element
    public String CodigoOrdenTrabajo;
    @Element
    public String CodigoTarea;
    @Element
    public String CodigoTrabajo;
    @Element(required = false)
    public String FechaInicioTarea;
    @Element(required = false)
    public String FechaUltimaTarea;
    @Element(required = false)
    public String CodigoUsuarioTarea;
    @Element(required = false)
    public String Nueva;
    @Element
    public String CodigoEstado;
    @Element(required = false)
    public int Secuencia;
    @Element(required = false)
    public String FechaDescarga;
    @Element(required = false)
    public String Parametros;

    public Date getFechaUltimaTarea() {
        Date fechaUltimaTarea;
        try {
            fechaUltimaTarea = DateHelper.convertirStringADate(FechaUltimaTarea, DateHelper.TipoFormato.yyyyMMddTHHmmss);
        } catch (ParseException ex) {
            fechaUltimaTarea = null;
            ex.printStackTrace();
        }
        return fechaUltimaTarea;
    }
}
