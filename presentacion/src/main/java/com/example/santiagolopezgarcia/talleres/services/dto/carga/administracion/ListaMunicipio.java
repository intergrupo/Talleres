package com.example.santiagolopezgarcia.talleres.services.dto.carga.administracion;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_MUNICIPIOS")
public class ListaMunicipio implements BaseListaDto<com.example.dominio.modelonegocio.Municipio> {
    @ElementList(inline = true)
    public List<Municipio> Sirius_Municipio;

    @Override
    public List<com.example.dominio.modelonegocio.Municipio> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.Municipio> listaMunicipios = new ArrayList<>(Lists.transform(this.Sirius_Municipio, municipioXML ->
                {
                    com.example.dominio.modelonegocio.Municipio municipio = new com.example.dominio.modelonegocio.Municipio();
                    municipio.setCodigoMunicipio(municipioXML.CodigoMunicipio);
                    municipio.setCodigoDepartamento(municipioXML.CodigoMunicipio.substring(0, 2));
                    municipio.setDescripcion(municipioXML.Descripcion);
                    return municipio;
                }
        ));
        return listaMunicipios;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_Municipio != null) {
            longitud = this.Sirius_Municipio.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_Municipio.get(0).Operacion != null && this.Sirius_Municipio.get(0).Operacion != "") {
                operacion = this.Sirius_Municipio.get(0).Operacion;
            }
        }
        return operacion;
    }
}