package com.example.santiagolopezgarcia.talleres.services.dto.descarga.notificacion;

import com.example.santiagolopezgarcia.talleres.integracion.descarga.BaseListaDtoDescarga;
import com.example.utilidades.helpers.BooleanHelper;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_REPORTENOTIFICACIONES")
public class ListaReporteNotificacion implements BaseListaDtoDescarga<ReporteNotificacion, com.example.dominio.modelonegocio.ReporteNotificacion> {
    @ElementList(inline=true)
    public List<ReporteNotificacion> Sirius_ReporteNotificacion;

    @Override
    public List<ReporteNotificacion> convertirListaDominioAListaDto(List<com.example.dominio.modelonegocio.ReporteNotificacion> listaDominio) {
        this.Sirius_ReporteNotificacion = Lists.transform(listaDominio, registro ->
        {
            ReporteNotificacion registroXML = new ReporteNotificacion();
            registroXML.CodigoCorreria = registro.getCodigoCorreria();
            registroXML.CodigoOrdenTrabajo = registro.getCodigoOrdenTrabajo();
            registroXML.CodigoTarea = registro.getCodigoTarea();
            registroXML.CodigoLabor = registro.getCodigoLabor();
            registroXML.CodigoNotificacion = registro.getCodigoNotificacion();
            registroXML.CodigoItem = registro.getCodigoItem();
            registroXML.CodigoLista = registro.getCodigoLista();
            registroXML.Orden = registro.getOrden();
            registroXML.Descripcion = registro.getDescripcion();
            registroXML.Descarga = BooleanHelper.ToString(registro.getDescarga());
            return registroXML;
        });
        return this.Sirius_ReporteNotificacion;
    }
}