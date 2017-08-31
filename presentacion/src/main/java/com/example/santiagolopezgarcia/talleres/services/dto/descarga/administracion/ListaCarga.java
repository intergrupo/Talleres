package com.example.santiagolopezgarcia.talleres.services.dto.descarga.administracion;

import com.example.dominio.modelonegocio.Talleres;
import com.example.santiagolopezgarcia.talleres.integracion.descarga.BaseListaDtoDescarga;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

@Root(name = "CARGA")
public class ListaCarga implements BaseListaDtoDescarga<Carga, Talleres> {
    @ElementList(inline=true)
    public List<Carga> Carga;
    private String versionSoftware;

    public void setVersionSoftware(String versionSoftware) {
        this.versionSoftware = versionSoftware;
    }

    @Override
    public List<Carga> convertirListaDominioAListaDto(List<Talleres> listaDominio) {
        this.Carga = Lists.transform(listaDominio, registro ->
        {
            Carga registroXML =
                    new Carga();
            registroXML.VersionMaestros = registro.getVersionMaestros();
            registroXML.VersionSoftware = versionSoftware;
            return registroXML;
        });
        return this.Carga;
    }

}