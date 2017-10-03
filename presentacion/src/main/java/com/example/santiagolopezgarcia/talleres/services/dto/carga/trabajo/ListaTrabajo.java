package com.example.santiagolopezgarcia.talleres.services.dto.carga.trabajo;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/3/17.
 */

@Root(name = "SIRIUS_TRABAJOS")
public class ListaTrabajo implements BaseListaDto<com.example.dominio.modelonegocio.Trabajo> {

    @ElementList(inline = true, required = false)
    public List<Trabajo> Sirius_trabajo;

    @Override
    public List<com.example.dominio.modelonegocio.Trabajo> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.Trabajo> listaTrabajos = new ArrayList<>(Lists.transform(this.Sirius_trabajo, trabajoXML ->
                {
                    com.example.dominio.modelonegocio.Trabajo trabajo = new com.example.dominio.modelonegocio.Trabajo();
                    trabajo.setCodigoTrabajo(trabajoXML.CodigoTrabajo);
                    trabajo.setNombre(trabajoXML.Descripcion);
                    trabajo.setParametros(trabajoXML.Parametros);
                    return trabajo;
                }
        ));
        return listaTrabajos;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_trabajo != null) {
            longitud = this.Sirius_trabajo.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_trabajo.get(0).Operacion != null && this.Sirius_trabajo.get(0).Operacion != "") {
                operacion = this.Sirius_trabajo.get(0).Operacion;
            }
        }
        return operacion;
    }
}