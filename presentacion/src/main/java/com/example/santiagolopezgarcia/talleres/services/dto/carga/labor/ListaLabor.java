package com.example.santiagolopezgarcia.talleres.services.dto.carga.labor;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_LABORES")
public class ListaLabor implements BaseListaDto<com.example.dominio.modelonegocio.Labor> {

    @ElementList(inline=true)
    public List<Labor> Sirius_Labor;

    @Override
    public List<com.example.dominio.modelonegocio.Labor> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.Labor> listaLabores = new ArrayList<>(Lists.transform(this.Sirius_Labor, laborXML ->
                {
                    com.example.dominio.modelonegocio.Labor labor = new com.example.dominio.modelonegocio.Labor();
                    labor.setCodigoLabor(laborXML.CodigoLabor);
                    labor.setNombre(laborXML.Nombre);
                    labor.setParametros(laborXML.Parametros);
                    labor.setRutina(laborXML.Rutina);
                    return labor;
                }
        ));
        return listaLabores;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_Labor != null) {
            longitud = this.Sirius_Labor.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_Labor.get(0).Operacion != null && this.Sirius_Labor.get(0).Operacion != "") {
                operacion = this.Sirius_Labor.get(0).Operacion;
            }
        }
        return operacion;
    }
}