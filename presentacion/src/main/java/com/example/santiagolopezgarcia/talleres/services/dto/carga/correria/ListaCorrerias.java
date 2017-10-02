package com.example.santiagolopezgarcia.talleres.services.dto.carga.correria;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDtoCorreria;
import com.example.utilidades.helpers.DateHelper;
import com.example.utilidades.helpers.StringHelper;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

@Root(name = "SIRIUS_CORRERIAS")
public class ListaCorrerias implements BaseListaDto<com.example.dominio.modelonegocio.Correria>, BaseListaDtoCorreria {

    @ElementList(inline = true, required = false)
    public List<Correria> Sirius_Correria;

    @Override
    public List<com.example.dominio.modelonegocio.Correria> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.Correria> listaCorreria = new ArrayList<>(Lists.transform(this.Sirius_Correria, correriaXml ->
                {
                    com.example.dominio.modelonegocio.Correria correria = new com.example.dominio.modelonegocio.Correria();
                    correria.setCodigoCorreria(correriaXml.CodigoCorreria);
                    correria.setDescripcion(correriaXml.Descripcion);
                    correria.setAdvertencia(correriaXml.Advertencia);
                    correria.getEmpresa().setCodigoEmpresa(correriaXml.CodigoEmpresa);
                    correria.setCodigoContrato(correriaXml.CodigoContrato);
                    correria.setInformacion(correriaXml.Area);
                    correria.setParametros(correriaXml.Parametros);
                    correria.setRecargaCorreria(StringHelper.ToBoolean(correriaXml.RecargaCorreria));
                    correria.setFechaUltimoEnvio(correriaXml.FechaUltimoEnvio);
                    correria.setObservacion(correriaXml.Observacion);
                    if (!correria.getFechaUltimoEnvio().isEmpty()) {
                        try {
                            correria.setFechaUltimoRecibo(DateHelper.convertirDateAString(
                                    new Date(), DateHelper.TipoFormato.yyyyMMddTHHmmss));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    if (correriaXml.FechaCarga.isEmpty() && correria.getFechaUltimoEnvio().isEmpty()) {
                        try {
                            correria.setFechaCarga(DateHelper.convertirDateAString(
                                    new Date(), DateHelper.TipoFormato.yyyyMMddTHHmmss));
                        } catch (Exception ex) {

                        }


                    } else {
                        correria.setFechaCarga(correriaXml.FechaCarga);
                    }

                    correria.setFechaProgramacion(correriaXml.FechaProgramacion);
                    correria.setFechaInicioCorreria(correriaXml.FechaInicioCorreria);
                    correria.setFechaUltimaCorreria(correriaXml.FechaUltimaCorreria);
                    correria.setFechaFinJornada(correriaXml.FechaFinJornada);
                    correria.setFechaUltimaDescarga(correriaXml.FechaUltimaDescarga);
                    correria.setFechaUltimoEnvio(correriaXml.FechaUltimoEnvio);
                    if (!correriaXml.FechaUltimoRecibo.isEmpty())
                        correria.setFechaUltimoRecibo(correriaXml.FechaUltimoRecibo);

                    return correria;
                }
        ));
        return listaCorreria;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_Correria != null) {
            longitud = this.Sirius_Correria.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_Correria.get(0).Operacion != null && this.Sirius_Correria.get(0).Operacion != "") {
                operacion = this.Sirius_Correria.get(0).Operacion;
            }
        }
        return operacion;
    }

    @Override
    public void eliminarItemPorCorreria(List<String> correriasOmitir) {
        if (this.Sirius_Correria != null && correriasOmitir.size() > 0) {
            Iterator<Correria> iterator = this.Sirius_Correria.iterator();
            while (iterator.hasNext()) {
                Correria correria = iterator.next();
                if (correriasOmitir.contains(correria.CodigoCorreria)) {
                    iterator.remove();
                }
            }
        }
    }
}
