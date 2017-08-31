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
public class ListaTalleres implements BaseListaDto<com.example.dominio.modelonegocio.Talleres> {
    @ElementList(inline = true)
    public List<Talleres> Talleres;

    @Override
    public List<com.example.dominio.modelonegocio.Talleres> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.Talleres> listaTalleres = new ArrayList<>(Lists.transform(this.Talleres, talleresXML ->
                {
                    com.example.dominio.modelonegocio.Talleres talleres = new com.example.dominio.modelonegocio.Talleres();
                    talleres.setRutaServidor(talleresXML.RutaServidor);
                    talleres.setVersionMaestros(talleresXML.VersionMaestros);
                    talleres.setNumeroTerminal(talleresXML.NumeroTerminal);
                    talleres.setWifi(talleresXML.Wifi);
                    talleres.setTipoIdentificacion(talleresXML.TipoIdentificacion);
                    talleres.setVersionSoftware(talleresXML.VersionSoftware);
                    talleres.setConfirmacion(talleresXML.Confirmacion);
                    talleres.setSincronizacion(talleresXML.Sincronizacion);
                    try {
                        talleres.setFechaMaestros(DateHelper.convertirStringADate(talleresXML.FechaMaestros, DateHelper.TipoFormato.yyyyMMddTHHmmss));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    talleres.setLongitudImpresionFens(talleresXML.LongitudImpresionFens);
                    talleres.setCantidadDecimalesFens(talleresXML.CantidadDecimalesFens);
                    talleres.setLog(talleresXML.Log);
                    talleres.setVersionParametrizacion(talleresXML.VersionParametrizacion);
                    return talleres;
                }
        ));
        return listaTalleres;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Talleres != null) {
            longitud = this.Talleres.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Talleres.get(0).Operacion != null && this.Talleres.get(0).Operacion != "") {
                operacion = this.Talleres.get(0).Operacion;
            }
        }
        return operacion;
    }
}