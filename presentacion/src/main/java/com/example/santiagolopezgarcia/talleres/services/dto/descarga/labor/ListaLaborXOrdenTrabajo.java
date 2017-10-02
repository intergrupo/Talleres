package com.example.santiagolopezgarcia.talleres.services.dto.descarga.labor;

import com.example.santiagolopezgarcia.talleres.integracion.descarga.BaseListaDtoDescarga;
import com.example.utilidades.helpers.DateHelper;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_LABORESXORDENTRABAJO")
public class ListaLaborXOrdenTrabajo implements BaseListaDtoDescarga<LaborXOrdenTrabajo, com.example.dominio.modelonegocio.LaborXOrdenTrabajo> {
    @ElementList(inline = true)
    public List<LaborXOrdenTrabajo> Sirius_LaborXOrdenTrabajo;

    @Override
    public List<LaborXOrdenTrabajo> convertirListaDominioAListaDto(List<com.example.dominio.modelonegocio.LaborXOrdenTrabajo> listaDominio) {
        this.Sirius_LaborXOrdenTrabajo = Lists.transform(listaDominio, registro ->
        {
            LaborXOrdenTrabajo registroXML = new LaborXOrdenTrabajo();
            registroXML.CodigoCorreria = registro.getCodigoCorreria();
            registroXML.CodigoOrdenTrabajo = registro.getCodigoOrdenTrabajo();
            registroXML.IdOrdenTrabajo = registro.getCodigoOrdenTrabajo();
            registroXML.CodigoTrabajo = registro.getTrabajo().getCodigoTrabajo();
            registroXML.CodigoTarea = registro.getTarea().getCodigoTarea();
            registroXML.CodigoLabor = registro.getLabor().getCodigoLabor();
            registroXML.CodigoUsuarioLabor = registro.getCodigoUsuarioLabor();
            registroXML.CodigoEstado = registro.getEstadoLaborXOrdenTrabajo().getCodigo();
            registroXML.Parametros = registro.getParametros();
            try {
                registroXML.FechaInicioLabor = DateHelper.convertirDateAString(
                        registro.getFechaInicioLabor(), DateHelper.TipoFormato.yyyyMMddTHHmmss);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                registroXML.FechaUltimoLabor = DateHelper.convertirDateAString(
                        registro.getFechaUltimoLabor(),
                        DateHelper.TipoFormato.yyyyMMddTHHmmss);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return registroXML;
        });
        return this.Sirius_LaborXOrdenTrabajo;
    }
}