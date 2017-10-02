package com.example.santiagolopezgarcia.talleres.services.dto.descarga.trabajo;

import com.example.santiagolopezgarcia.talleres.integracion.descarga.BaseListaDtoDescarga;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_TRABAJOSXORDENTRABAJO")
public class ListaTrabajoXOrdenTrabajo implements BaseListaDtoDescarga<TrabajoXOrdenTrabajo, com.example.dominio.modelonegocio.TrabajoXOrdenTrabajo> {
    @ElementList(inline = true)
    public List<TrabajoXOrdenTrabajo> Sirius_TrabajoXOrdenTrabajo;

    @Override
    public List<TrabajoXOrdenTrabajo> convertirListaDominioAListaDto(List<com.example.dominio.modelonegocio.TrabajoXOrdenTrabajo> listaDominio) {
        this.Sirius_TrabajoXOrdenTrabajo= Lists.transform(listaDominio, registro->{
            TrabajoXOrdenTrabajo trabajoXOrdenTrabajo=new TrabajoXOrdenTrabajo();
            trabajoXOrdenTrabajo.CodigoCorreria=registro.getCodigoCorreria();
            trabajoXOrdenTrabajo.CodigoOrdenTrabajo=registro.getCodigoOrdenTrabajo();
            trabajoXOrdenTrabajo.CodigoTrabajo=registro.getTrabajo().getCodigoTrabajo();
            return trabajoXOrdenTrabajo;
        });

        return this.Sirius_TrabajoXOrdenTrabajo;
    }
}