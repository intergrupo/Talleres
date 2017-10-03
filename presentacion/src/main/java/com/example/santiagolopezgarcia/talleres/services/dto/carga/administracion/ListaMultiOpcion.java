package com.example.santiagolopezgarcia.talleres.services.dto.carga.administracion;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/3/17.
 */

@Root(name = "SIRIUS_MULTIOPCIONES")
public class ListaMultiOpcion implements BaseListaDto<com.example.dominio.modelonegocio.MultiOpcion> {

    @ElementList(inline = true)
    public List<MultiOpcion> Sirius_MultiOpcion;

    @Override
    public List<com.example.dominio.modelonegocio.MultiOpcion> convertirListaDtoAListaDominio() {
        com.example.dominio.modelonegocio.ListaMultiOpcion listaMultiOpcion = new com.example.dominio.modelonegocio.ListaMultiOpcion(Lists.transform(this.Sirius_MultiOpcion, multiOpcionXML ->
                {
                    com.example.dominio.modelonegocio.MultiOpcion multiOpcion = new com.example.dominio.modelonegocio.MultiOpcion();
                    multiOpcion.setCodigoTipoOpcion(multiOpcionXML.CodigoTipoOpcion);
                    multiOpcion.setCodigoOpcion(multiOpcionXML.CodigoOpcion);
                    multiOpcion.setDescripcion(multiOpcionXML.Descripcion);
                    return multiOpcion;
                }
        ));
        return listaMultiOpcion;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_MultiOpcion != null) {
            longitud = this.Sirius_MultiOpcion.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_MultiOpcion.get(0).Operacion != null && this.Sirius_MultiOpcion.get(0).Operacion != "") {
                operacion = this.Sirius_MultiOpcion.get(0).Operacion;
            }
        }
        return operacion;
    }
}