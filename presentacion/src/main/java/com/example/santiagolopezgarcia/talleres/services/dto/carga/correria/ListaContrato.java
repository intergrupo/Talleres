package com.example.santiagolopezgarcia.talleres.services.dto.carga.correria;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_CONTRATOS")
public class ListaContrato implements BaseListaDto<com.example.dominio.modelonegocio.Contrato> {

    @ElementList(inline=true, required = false)
    public List<Contrato> Sirius_Contrato;

    @Override
    public List<com.example.dominio.modelonegocio.Contrato> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.Contrato> listaObservacionAdicional = new ArrayList<>(Lists.transform(this.Sirius_Contrato, contratolXML ->
                {
                    com.example.dominio.modelonegocio.Contrato contrato = new com.example.dominio.modelonegocio.Contrato();
                    contrato.setCodigoContrato(contratolXML.CodigoContrato);
                    contrato.setNombre(contratolXML.Nombre);
                    return contrato;
                }
        ));
        return listaObservacionAdicional;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_Contrato != null) {
            longitud = this.Sirius_Contrato.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_Contrato.get(0).Operacion != null && this.Sirius_Contrato.get(0).Operacion != "") {
                operacion = this.Sirius_Contrato.get(0).Operacion;
            }
        }
        return operacion;
    }
}
