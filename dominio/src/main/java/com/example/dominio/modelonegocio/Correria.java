package com.example.dominio.modelonegocio;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class Correria implements Serializable {

    private String codigoCorreria;
    private String descripcion;
    private String advertencia;
    private String observacion;
    @Nullable
    private String fechaProgramacion;
    private Empresa empresa;
    private String codigoContrato;
    private String fechaCarga;
    @Nullable
    private String fechaInicioCorreria;
    @Nullable
    private String fechaUltimaCorreria;
    private String fechaUltimoEnvio;
    private String fechaUltimoRecibo;
    private String fechaFinJornada;
    private String fechaUltimaDescarga;
    private ListaOrdenTrabajo ordenTrabajoList;
    private String informacion;
    private String parametros;
    private boolean recargaCorreria;

    public Correria() {
        empresa = new Empresa();
        ordenTrabajoList = new ListaOrdenTrabajo();
        codigoCorreria = "";
        descripcion = "";
        advertencia = "";
        observacion = "";
        codigoContrato = "";
        fechaCarga = "";
        fechaUltimoEnvio = "";
        fechaUltimoRecibo = "";
        fechaFinJornada = "";
        fechaUltimaDescarga = "";
        informacion = "";
        parametros = "";
    }

    public String getCodigoCorreria() {
        return codigoCorreria;
    }

    public void setCodigoCorreria(String codigoCorreria) {
        this.codigoCorreria = codigoCorreria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAdvertencia() {
        return advertencia;
    }

    public void setAdvertencia(String advertencia) {
        this.advertencia = advertencia;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFechaProgramacion() {
        return fechaProgramacion;
    }

    public void setFechaProgramacion(String fechaProgramacion) {
        this.fechaProgramacion = fechaProgramacion;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getCodigoContrato() {
        return codigoContrato;
    }

    public void setCodigoContrato(String codigoContrato) {
        this.codigoContrato = codigoContrato;
    }

    public String getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(String fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getFechaInicioCorreria() {
        return fechaInicioCorreria;
    }

    public void setFechaInicioCorreria(String fechaInicioCorreria) {
        this.fechaInicioCorreria = fechaInicioCorreria;
    }

    public String getFechaUltimaCorreria() {
        return fechaUltimaCorreria;
    }

    public void setFechaUltimaCorreria(String fechaUltimaCorreria) {
        this.fechaUltimaCorreria = fechaUltimaCorreria;
    }

    public String getFechaUltimoEnvio() {
        return fechaUltimoEnvio;
    }

    public void setFechaUltimoEnvio(String fechaUltimoEnvio) {
        this.fechaUltimoEnvio = fechaUltimoEnvio;
    }

    public String getFechaUltimoRecibo() {
        return fechaUltimoRecibo;
    }

    public void setFechaUltimoRecibo(String fechaUltimoRecibo) {
        this.fechaUltimoRecibo = fechaUltimoRecibo;
    }

    public String getFechaFinJornada() {
        return fechaFinJornada;
    }

    public void setFechaFinJornada(String fechaFinJornada) {
        this.fechaFinJornada = fechaFinJornada;
    }

    public String getFechaUltimaDescarga() {
        return fechaUltimaDescarga;
    }

    public void setFechaUltimaDescarga(String fechaUltimaDescarga) {
        this.fechaUltimaDescarga = fechaUltimaDescarga;
    }

    public ListaOrdenTrabajo getOrdenTrabajoList() {
        return ordenTrabajoList;
    }

    public void setOrdenTrabajoList(ListaOrdenTrabajo ordenTrabajoList) {
        this.ordenTrabajoList = ordenTrabajoList;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public boolean isRecargaCorreria() {
        return recargaCorreria;
    }

    public void setRecargaCorreria(boolean recargaCorreria) {
        this.recargaCorreria = recargaCorreria;
    }
}
