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

@Root(name = "SIRIUS_EMPRESAS")
public class ListaEmpresa implements BaseListaDto<com.example.dominio.modelonegocio.Empresa> {

    @ElementList(inline = true, required = false)
    public List<Empresa> Sirius_Empresa;

    @Override
    public List<com.example.dominio.modelonegocio.Empresa> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.Empresa> listaEmpresas = new ArrayList<>(Lists.transform(this.Sirius_Empresa, empresaXML ->
                {
                    com.example.dominio.modelonegocio.Empresa empresa = new com.example.dominio.modelonegocio.Empresa();
                    empresa.setCodigoEmpresa(empresaXML.CodigoEmpresa);
                    empresa.setDescripcion(empresaXML.Descripcion);
                    empresa.setLogo(empresaXML.Logo);
                    return empresa;
                }
        ));
        return listaEmpresas;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_Empresa != null) {
            longitud = this.Sirius_Empresa.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_Empresa.get(0).Operacion != null && this.Sirius_Empresa.get(0).Operacion != "") {
                operacion = this.Sirius_Empresa.get(0).Operacion;
            }
        }
        return operacion;
    }
}