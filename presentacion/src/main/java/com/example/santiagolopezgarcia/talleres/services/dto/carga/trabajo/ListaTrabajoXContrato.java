package com.example.santiagolopezgarcia.talleres.services.dto.carga.trabajo;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_TRABAJOSXCONTRATOS")
public class ListaTrabajoXContrato implements BaseListaDto<com.example.dominio.modelonegocio.TrabajoXContrato> {

    @ElementList(inline = true, required = false)
    public List<TrabajoXContrato> Sirius_trabajoXContrato;

    @Override
    public List<com.example.dominio.modelonegocio.TrabajoXContrato> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.TrabajoXContrato> listaTrabajoXContratos = new ArrayList<>(Lists.transform(this.Sirius_trabajoXContrato, TrabajoXContratoXML ->
                {
                    com.example.dominio.modelonegocio.TrabajoXContrato trabajoXContrato = new com.example.dominio.modelonegocio.TrabajoXContrato();
                    trabajoXContrato.getContrato().setCodigoContrato(TrabajoXContratoXML.CodigoContrato);
                    trabajoXContrato.getTrabajo().setCodigoTrabajo(TrabajoXContratoXML.CodigoTrabajo);
                    return trabajoXContrato;
                }
        ));
        return listaTrabajoXContratos;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_trabajoXContrato != null) {
            longitud = this.Sirius_trabajoXContrato.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_trabajoXContrato.get(0).Operacion != null && this.Sirius_trabajoXContrato.get(0).Operacion != "") {
                operacion = this.Sirius_trabajoXContrato.get(0).Operacion;
            }
        }
        return operacion;
    }
}
