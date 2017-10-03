package com.example.santiagolopezgarcia.talleres.services.dto.carga.tarea;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/3/17.
 */

@Root(name = "SIRIUS_TAREAS")
public class ListaTareas implements BaseListaDto<com.example.dominio.modelonegocio.Tarea> {

    @ElementList(inline = true, required = false)
    public List<Tarea> Sirius_tarea;

    @Override
    public List<com.example.dominio.modelonegocio.Tarea> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.Tarea> listaTareas = new ArrayList<>(Lists.transform(this.Sirius_tarea, tareaXML ->
                {
                    com.example.dominio.modelonegocio.Tarea tarea = new com.example.dominio.modelonegocio.Tarea();
                    tarea.setCodigoTarea(tareaXML.CodigoTarea);
                    tarea.setNombre(tareaXML.Nombre);
                    tarea.setCodigoImpresion(tareaXML.CodigoImpresion);
                    tarea.setRutina(tareaXML.Rutina);
                    tarea.setParametros(tareaXML.Parametros);
                    return tarea;
                }
        ));
        return listaTareas;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_tarea != null) {
            longitud = this.Sirius_tarea.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_tarea.get(0).Operacion != null && this.Sirius_tarea.get(0).Operacion != "") {
                operacion = this.Sirius_tarea.get(0).Operacion;
            }
        }
        return operacion;
    }
}