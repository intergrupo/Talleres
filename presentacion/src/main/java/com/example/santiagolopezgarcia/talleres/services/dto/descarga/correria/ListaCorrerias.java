package com.example.santiagolopezgarcia.talleres.services.dto.descarga.correria;

import com.example.santiagolopezgarcia.talleres.integracion.descarga.BaseListaDtoDescarga;
import com.example.utilidades.helpers.BooleanHelper;
import com.example.utilidades.helpers.DateHelper;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

@Root(name = "SIRIUS_CORRERIAS")
public class ListaCorrerias implements BaseListaDtoDescarga<Correria, com.example.dominio.modelonegocio.Correria> {

    @ElementList(inline = true)
    public List<Correria> Sirius_Correria;
    private String sesion;
    private String fecha;
    private String numeroTerminal;


    @Override
    public List<Correria> convertirListaDominioAListaDto(List<com.example.dominio.modelonegocio.Correria> listaDominio) {
        //TODO: VALIDAR LOS CAMPOS DE AUDITORIA QUE SE DEBEN ENVIAR
        this.Sirius_Correria = Lists.transform(listaDominio, correriaNegocio ->
        {
            Correria correriaDto = new Correria();
            correriaDto.CodigoCorreria = correriaNegocio.getCodigoCorreria();
            correriaDto.Area = correriaNegocio.getInformacion();
            correriaDto.FechaCarga = correriaNegocio.getFechaCarga();
            correriaDto.Descripcion = correriaNegocio.getDescripcion();
            correriaDto.Advertencia = correriaNegocio.getAdvertencia();
            correriaDto.CodigoEmpresa = correriaNegocio.getEmpresa().getCodigoEmpresa();
            correriaDto.CodigoContrato = correriaNegocio.getCodigoContrato();
            correriaDto.Parametros = correriaNegocio.getParametros();
            correriaDto.RecargaCorreria = BooleanHelper.ToString(correriaNegocio.isRecargaCorreria());
            correriaDto.Observacion = correriaNegocio.getObservacion();
            correriaDto.FechaProgramacion = correriaNegocio.getFechaProgramacion();
            correriaDto.FechaInicioCorreria = correriaNegocio.getFechaInicioCorreria();
            correriaDto.FechaUltimaCorreria = correriaNegocio.getFechaUltimaCorreria();
            correriaDto.Fecha = fecha;
            correriaDto.NumeroTerminal = numeroTerminal;
            correriaDto.Sesion = sesion;
            correriaDto.FechaUltimoEnvio = correriaNegocio.getFechaUltimoEnvio();
            correriaDto.FechaUltimoRecibo = correriaNegocio.getFechaUltimoRecibo();
            correriaDto.FechaFinJornada = correriaNegocio.getFechaFinJornada();
            correriaDto.FechaUltimaDescarga = correriaNegocio.getFechaUltimaDescarga();
            return correriaDto;
        });
        return this.Sirius_Correria;
    }

    public void setSesion(String sesion) {
        this.sesion = sesion;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setNumeroTerminal(String numeroTerminal) {
        this.numeroTerminal = numeroTerminal;
    }
}
