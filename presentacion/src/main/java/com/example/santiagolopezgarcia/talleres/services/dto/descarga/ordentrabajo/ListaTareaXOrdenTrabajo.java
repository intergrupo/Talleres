package com.example.santiagolopezgarcia.talleres.services.dto.descarga.ordentrabajo;

import com.example.santiagolopezgarcia.talleres.integracion.descarga.BaseListaDtoDescarga;
import com.example.utilidades.helpers.BooleanHelper;
import com.example.utilidades.helpers.DateHelper;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */
@Root(name = "SIRIUS_TAREASXORDENTRABAJO")
public class ListaTareaXOrdenTrabajo implements BaseListaDtoDescarga<TareaXOrdenTrabajo, com.example.dominio.modelonegocio.TareaXOrdenTrabajo> {
    @ElementList(inline = true)
    public List<TareaXOrdenTrabajo> Sirius_tareaxordentrabajo;
    public boolean envio;

    public void setEnvio(boolean envio) {
        this.envio = envio;
    }

    @Override
    public List<TareaXOrdenTrabajo> convertirListaDominioAListaDto(List<com.example.dominio.modelonegocio.TareaXOrdenTrabajo> listaDominio) {
        this.Sirius_tareaxordentrabajo = Lists.transform(listaDominio, registro ->
        {
            TareaXOrdenTrabajo registroXML = new TareaXOrdenTrabajo();
            registroXML.CodigoCorreria = registro.getCodigoCorreria();
            registroXML.CodigoOrdenTrabajo = registro.getCodigoOrdenTrabajo();
            registroXML.CodigoTrabajo = registro.getCodigoTrabajo();
            registroXML.CodigoTarea = registro.getTarea().getCodigoTarea();
            registroXML.CodigoEstado = registro.getEstadoTarea().getCodigo();
            registroXML.Secuencia = registro.getSecuencia();
            registroXML.Parametros = registro.getParametros();
            registroXML.Nueva = BooleanHelper.ToString(registro.isNueva());
            try {
                registroXML.FechaUltimaTarea = DateHelper.convertirDateAString(registro.getFechaUltimaTarea(), DateHelper.TipoFormato.yyyyMMddTHHmmss);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            registroXML.CodigoUsuarioTarea = registro.getCodigoUsuarioTarea();

            if (!envio) {
                try {
                    registroXML.FechaDescarga = DateHelper.convertirDateAString(new Date(), DateHelper.TipoFormato.yyyyMMddTHHmmss);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            try {
                registroXML.FechaInicioTarea = DateHelper.convertirDateAString(registro.getFechaInicioTarea(), DateHelper.TipoFormato.yyyyMMddTHHmmss);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return registroXML;
        });
        return this.Sirius_tareaxordentrabajo;
    }
}
