package com.example.santiagolopezgarcia.talleres.services.dto.descarga.administracion;

import com.example.santiagolopezgarcia.talleres.integracion.descarga.BaseListaDtoDescarga;
import com.example.utilidades.helpers.DateHelper;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_SIRIUS")
public class ListaTalleres implements BaseListaDtoDescarga<Talleres, com.example.dominio.modelonegocio.Talleres> {

    @ElementList(inline = true)
    public List<Talleres> Sirius_Sirius;

    @Override
    public List<Talleres> convertirListaDominioAListaDto(List<com.example.dominio.modelonegocio.Talleres> listaDominio) {
        this.Sirius_Sirius = Lists.transform(listaDominio, siriusNegocio ->
        {
            Talleres siriusDto = new Talleres();
            try {
                siriusDto.FechaMaestros = DateHelper.convertirDateAString(
                        siriusNegocio.getFechaMaestros(), DateHelper.TipoFormato.yyyyMMddTHHmmss);
            } catch (Exception e) {
                e.printStackTrace();
            }
            siriusDto.NumeroTerminal = siriusNegocio.getNumeroTerminal();
            siriusDto.RutaServidor = siriusNegocio.getRutaServidor();
            siriusDto.VersionMaestros = siriusNegocio.getVersionMaestros();
            siriusDto.VersionSoftware = siriusNegocio.getVersionSoftware();
            siriusDto.Confirmacion = siriusNegocio.getConfirmacion();
            siriusDto.Sincronizacion = siriusNegocio.getSincronizacion();
            siriusDto.Log = siriusNegocio.getLog();
            return siriusDto;
        });
        return this.Sirius_Sirius;
    }
}

