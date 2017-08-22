package com.example.santiagolopezgarcia.talleres.services.dto.carga.administracion;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.example.utilidades.helpers.DateHelper;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

@Root(name = "SIRIUS")
public class ListaSirius implements BaseListaDto<com.example.dominio.modelonegocio.Sirius> {
    @ElementList(inline = true)
    public List<Sirius> Sirius;

    @Override
    public List<com.example.dominio.modelonegocio.Sirius> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.Sirius> listaSirius = new ArrayList<>(Lists.transform(this.Sirius, siriusXML ->
                {
                    com.example.dominio.modelonegocio.Sirius sirius = new com.example.dominio.modelonegocio.Sirius();
                    sirius.setRutaServidor(siriusXML.RutaServidor);
                    sirius.setVersionMaestros(siriusXML.VersionMaestros);
                    sirius.setNumeroTerminal(siriusXML.NumeroTerminal);
                    sirius.setWifi(siriusXML.Wifi);
                    sirius.setTipoIdentificacion(siriusXML.TipoIdentificacion);
                    sirius.setVersionSoftware(siriusXML.VersionSoftware);
                    sirius.setConfirmacion(siriusXML.Confirmacion);
                    sirius.setSincronizacion(siriusXML.Sincronizacion);
                    try {
                        sirius.setFechaMaestros(DateHelper.convertirStringADate(siriusXML.FechaMaestros, DateHelper.TipoFormato.yyyyMMddTHHmmss));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    sirius.setLongitudImpresionFens(siriusXML.LongitudImpresionFens);
                    sirius.setCantidadDecimalesFens(siriusXML.CantidadDecimalesFens);
                    sirius.setLog(siriusXML.Log);
                    sirius.setVersionParametrizacion(siriusXML.VersionParametrizacion);
                    return sirius;
                }
        ));
        return listaSirius;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius != null) {
            longitud = this.Sirius.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius.get(0).Operacion != null && this.Sirius.get(0).Operacion != "") {
                operacion = this.Sirius.get(0).Operacion;
            }
        }
        return operacion;
    }
}