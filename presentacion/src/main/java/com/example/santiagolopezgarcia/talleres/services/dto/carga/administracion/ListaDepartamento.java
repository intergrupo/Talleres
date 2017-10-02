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

@Root(name = "SIRIUS_DEPARTAMENTOS")
public class ListaDepartamento implements BaseListaDto<com.example.dominio.modelonegocio.Departamento> {

    @ElementList(inline = true)
    public List<Departamento> Sirius_Departamento;

    @Override
    public List<com.example.dominio.modelonegocio.Departamento> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.Departamento> listaDepartamentos = new ArrayList<>(Lists.transform(this.Sirius_Departamento, departamentoXML ->
                {
                    com.example.dominio.modelonegocio.Departamento departamento = new com.example.dominio.modelonegocio.Departamento();
                    departamento.setCodigoDepartamento(departamentoXML.CodigoDepartamento);
                    departamento.setDescripcion(departamentoXML.Descripcion);
                    return departamento;
                }
        ));
        return listaDepartamentos;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_Departamento != null) {
            longitud = this.Sirius_Departamento.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_Departamento.get(0).Operacion != null && this.Sirius_Departamento.get(0).Operacion != "") {
                operacion = this.Sirius_Departamento.get(0).Operacion;
            }
        }
        return operacion;
    }
}