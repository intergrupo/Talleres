package com.example.santiagolopezgarcia.talleres.services.dto.carga.acceso;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_PERFILES")
public class ListaPerfil implements BaseListaDto<com.example.dominio.modelonegocio.Perfil> {

    @ElementList(inline = true, required = false)
    public List<Perfil> Sirius_Perfil;

    @Override
    public List<com.example.dominio.modelonegocio.Perfil> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.Perfil> listaPerfiles = new ArrayList<>(Lists.transform(this.Sirius_Perfil, perfillXML ->
                {
                    com.example.dominio.modelonegocio.Perfil perfil = new com.example.dominio.modelonegocio.Perfil();
                    perfil.setCodigoPerfil(perfillXML.CodigoPerfil);
                    perfil.setDescripcion(perfillXML.Descripcion);
                    return perfil;
                }
        ));
        return listaPerfiles;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_Perfil != null) {
            longitud = this.Sirius_Perfil.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_Perfil.get(0).Operacion != null && this.Sirius_Perfil.get(0).Operacion != "") {
                operacion = this.Sirius_Perfil.get(0).Operacion;
            }
        }
        return operacion;
    }
}