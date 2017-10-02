package com.example.santiagolopezgarcia.talleres.services.dto.carga.impresion;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_PARRAFOIMPRESION")
public class ListaParrafoImpresion implements BaseListaDto<com.example.dominio.modelonegocio.ParrafoImpresion> {
    @ElementList(inline = true,required = false)
    public List<ParrafoImpresion> Sirius_ParrafoImpresion;

    @Override
    public List<com.example.dominio.modelonegocio.ParrafoImpresion> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.ParrafoImpresion> listaParrafoImpresion = new ArrayList<>(Lists.transform(this.Sirius_ParrafoImpresion, parrafoImpresionXml ->
                {
                    com.example.dominio.modelonegocio.ParrafoImpresion parrafoImpresion = new com.example.dominio.modelonegocio.ParrafoImpresion();
                    parrafoImpresion.setCodigoImpresion(parrafoImpresionXml.CodigoImpresion);
                    parrafoImpresion.setCodigoParrafo(parrafoImpresionXml.CodigoParrafo);
                    parrafoImpresion.setParrafo(parrafoImpresionXml.Parrafo);

                    return parrafoImpresion;
                }
        ));
        return listaParrafoImpresion;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_ParrafoImpresion != null) {
            longitud = this.Sirius_ParrafoImpresion.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_ParrafoImpresion.get(0).Operacion != null && this.Sirius_ParrafoImpresion.get(0).Operacion != "") {
                operacion = this.Sirius_ParrafoImpresion.get(0).Operacion;
            }
        }
        return operacion;
    }
}
