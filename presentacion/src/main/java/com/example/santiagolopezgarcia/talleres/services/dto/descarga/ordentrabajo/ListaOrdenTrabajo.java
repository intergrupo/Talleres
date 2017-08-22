package com.example.santiagolopezgarcia.talleres.services.dto.descarga.ordentrabajo;

import com.example.santiagolopezgarcia.talleres.integracion.descarga.BaseListaDtoDescarga;
import com.example.utilidades.helpers.BooleanHelper;
import com.example.utilidades.helpers.DateHelper;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringEscapeUtils;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

@Root(name = "SIRIUS_ORDENTRABAJOS")
public class ListaOrdenTrabajo implements BaseListaDtoDescarga<OrdenTrabajo, com.example.dominio.modelonegocio.OrdenTrabajo> {
    @ElementList(inline = true)
    public List<OrdenTrabajo> Sirius_OrdenTrabajo;
    private String sesion;
    private String numeroTerminal;
    private String fecha;

    @Override
    public List<OrdenTrabajo> convertirListaDominioAListaDto(List<com.example.dominio.modelonegocio.OrdenTrabajo> listaDominio) {
        ListaOrdenTrabajo listaXml = new ListaOrdenTrabajo();
        this.Sirius_OrdenTrabajo = Lists.transform(listaDominio, registro ->
        {
            OrdenTrabajo registroXML = new OrdenTrabajo();
            registroXML.CodigoCorreria = registro.getCodigoCorreria();
            registroXML.CodigoOrdenTrabajo = registro.getCodigoOrdenTrabajo();
            registroXML.Secuencia = registro.getSecuencia();
            StringEscapeUtils stringEscapeUtils = new StringEscapeUtils();
            registroXML.Nombre = stringEscapeUtils.escapeXml10(registro.getNombre());
            registroXML.Direccion = registro.getDireccion();
            registroXML.CodigoMunicipio = registro.getDepartamento().getMunicipio().getCodigoMunicipio();
            registroXML.GPS = registro.getGps();
            registroXML.Telefono = registro.getTelefono();
            registroXML.CodigoEstado = registro.getEstado().getCodigo();
            registroXML.CodigoUsuarioLabor = registro.getCodigoUsuarioLabor();
            registroXML.Llave1 = registro.getCodigoLlave1();
            registroXML.Llave2 = registro.getCodigoLlave2();
            registroXML.Nueva = BooleanHelper.ToString(registro.isNueva());
            registroXML.Parametros = registro.getParametros();
            registroXML.EstadoComunicacion = registro.getEstadoComunicacion();
            registroXML.ImprimirFactura = registro.getImprimirFactura();
            registroXML.CodigoOrdenTrabajoRelacionada = registro.getCodigoOrdenTrabajoRelacionada();

            try {
                registroXML.FechaInicioOrdenTrabajo = DateHelper.convertirDateAString(
                        registro.getFechaInicioOrdenTrabajo(), DateHelper.TipoFormato.yyyyMMddHHmmss);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                registroXML.FechaUltimaOrdenTrabajo = DateHelper.convertirDateAString(
                        registro.getFechaUltimaOrdenTrabajo(), DateHelper.TipoFormato.yyyyMMddHHmmss);
            } catch (Exception e) {
                e.printStackTrace();
            }
            registroXML.Sesion = sesion;
            registroXML.Fecha = fecha;
            registroXML.NumeroTerminal = numeroTerminal;
            try {
                registroXML.FechaCarga = DateHelper.convertirDateAString(
                        registro.getFechaCarga(), DateHelper.TipoFormato.yyyyMMddHHmmss);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return registroXML;
        });
        return this.Sirius_OrdenTrabajo;
    }

    public void setSesion(String sesion) {
        this.sesion = sesion;
    }

    public void setNumeroTerminal(String numeroTerminal) {
        this.numeroTerminal = numeroTerminal;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
