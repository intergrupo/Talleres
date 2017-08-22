package com.example.santiagolopezgarcia.talleres.services.dto.carga;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */


@Root(name = "CARGA")
public class ListaCarga implements BaseListaDto<com.example.dominio.modelonegocio.Carga> {
    @ElementList(inline = true, required = false)
    public List<Carga> Carga;

    @Override
    public List<com.example.dominio.modelonegocio.Carga> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.Carga> listaCarga = new ArrayList<>(Lists.transform(this.Carga, cargaXML ->
                {
                    com.example.dominio.modelonegocio.Carga carga = new com.example.dominio.modelonegocio.Carga();
                    carga.setVersionMaestros(cargaXML.VersionMaestros);
                    carga.setVersionSoftware(cargaXML.VersionSoftware);
                    return carga;
                }
        ));
        return listaCarga;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Carga != null) {
            longitud = this.Carga.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Carga.get(0).Operacion != null && this.Carga.get(0).Operacion != "") {
                operacion = this.Carga.get(0).Operacion;
            }
        }
        return operacion;
    }
}