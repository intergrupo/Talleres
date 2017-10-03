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

@Root(name = "SIRIUS_TAREAXTRABAJOS")
public class ListaTareaXTrabajo implements BaseListaDto<com.example.dominio.modelonegocio.TareaXTrabajo> {

    @ElementList(inline = true, required = false)
    public List<TareaXTrabajo> Sirius_TareaXTrabajo;

    @Override
    public List<com.example.dominio.modelonegocio.TareaXTrabajo> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.TareaXTrabajo> listaTareaXTrabajo = new ArrayList<>(Lists.transform(this.Sirius_TareaXTrabajo, tareaXtrabajoXML ->
                {
                    com.example.dominio.modelonegocio.TareaXTrabajo tareaXTrabajo = new com.example.dominio.modelonegocio.TareaXTrabajo();
                    tareaXTrabajo.getTarea().setCodigoTarea(tareaXtrabajoXML.CodigoTarea);
                    tareaXTrabajo.getTrabajo().setCodigoTrabajo(tareaXtrabajoXML.CodigoTrabajo);
                    return tareaXTrabajo;
                }
        ));
        return listaTareaXTrabajo;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_TareaXTrabajo!= null) {
            longitud = this.Sirius_TareaXTrabajo.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_TareaXTrabajo.get(0).Operacion != null && this.Sirius_TareaXTrabajo.get(0).Operacion != "") {
                operacion = this.Sirius_TareaXTrabajo.get(0).Operacion;
            }
        }
        return operacion;
    }
}