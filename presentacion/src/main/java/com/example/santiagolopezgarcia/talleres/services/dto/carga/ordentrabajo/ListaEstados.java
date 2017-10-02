package com.example.santiagolopezgarcia.talleres.services.dto.carga.ordentrabajo;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_ESTADOS")
public class ListaEstados implements BaseListaDto<com.example.dominio.modelonegocio.Estado> {
    @ElementList(inline = true, required = false)
    public List<Estado> Sirius_Estado;

    @Override
    public List<com.example.dominio.modelonegocio.Estado> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.Estado> listaEstados = null;
        if (this.getLongitudLista() > 0) {
            listaEstados = new ArrayList<>(Lists.transform(this.Sirius_Estado, estadoXML ->
                    {
                        com.example.dominio.modelonegocio.Estado estado = new com.example.dominio.modelonegocio.Estado();
                        estado.setCodigoEstado(estadoXML.CodigoEstado);
                        estado.setDescripcion(estadoXML.Descripcion);
                        estado.setGrupoEstado(com.example.dominio.modelonegocio.Estado.GrupoEstado.getEstado(estadoXML.GrupoEstado));
                        return estado;
                    }
            ));
        }
        return listaEstados;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_Estado != null) {
            longitud = this.Sirius_Estado.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_Estado.get(0).Operacion != null && this.Sirius_Estado.get(0).Operacion != "") {
                operacion = this.Sirius_Estado.get(0).Operacion;
            }
        }
        return operacion;
    }
}