package com.example.dominio.modelonegocio;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class TareaXTrabajoOrdenTrabajo implements Serializable {

    private String codigoCorreria;
    private String codigoOrdenTrabajo;
    private String codigoTrabajo;
    private Tarea tarea;
    @Nullable
    private Date fechaInicioTarea;
    @Nullable
    private Date fechaUltimaTarea;
    private String codigoUsuarioTarea;
    private Tarea.EstadoTarea estadoTarea;
    private int secuencia;
    private Date fechaDescarga;
    private List<LaborXOrdenTrabajo> laborXOrdenTrabajoList;
    private String parametros;
    private int concecutivo;
    private boolean nueva;

    public boolean tieneClave() {
        return !codigoCorreria.isEmpty() &&
                !codigoOrdenTrabajo.isEmpty() &&
                !codigoTrabajo.isEmpty() &&
                !tarea.getCodigoTarea().isEmpty();

    }

    public TareaXTrabajoOrdenTrabajo() {
        codigoCorreria = "";
        codigoOrdenTrabajo = "";
        codigoTrabajo = "";
        tarea = new Tarea();
        codigoUsuarioTarea = "";
        estadoTarea = Tarea.EstadoTarea.NINGUNA;
        laborXOrdenTrabajoList = new ArrayList<>();
        parametros = "";
    }

    public String getCodigoCorreria() {
        return codigoCorreria;
    }

    public void setCodigoCorreria(String codigoCorreria) {
        this.codigoCorreria = codigoCorreria;
    }

    public String getCodigoOrdenTrabajo() {
        return codigoOrdenTrabajo;
    }

    public void setCodigoOrdenTrabajo(String codigoOrdenTrabajo) {
        this.codigoOrdenTrabajo = codigoOrdenTrabajo;
    }

    public String getCodigoTrabajo() {
        return codigoTrabajo;
    }

    public void setCodigoTrabajo(String trabajo) {
        this.codigoTrabajo = trabajo;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public Date getFechaInicioTarea() {
        return fechaInicioTarea;
    }

    public void setFechaInicioTarea(Date fechaInicioTarea) {
        this.fechaInicioTarea = fechaInicioTarea;
    }

    public Date getFechaUltimaTarea() {
        return fechaUltimaTarea;
    }

    public void setFechaUltimaTarea(Date fechaUltimaTarea) {
        this.fechaUltimaTarea = fechaUltimaTarea;
    }

    public String getCodigoUsuarioTarea() {
        return codigoUsuarioTarea;
    }

    public void setCodigoUsuarioTarea(String codigoUsuarioTarea) {
        this.codigoUsuarioTarea = codigoUsuarioTarea;
    }

    public Tarea.EstadoTarea getEstadoTarea() {
        return estadoTarea;
    }

    public void setEstadoTarea(Tarea.EstadoTarea estadoTarea) {
        this.estadoTarea = estadoTarea;
    }

    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public Date getFechaDescarga() {
        return fechaDescarga;
    }

    public void setFechaDescarga(Date fechaDescarga) {
        this.fechaDescarga = fechaDescarga;
    }

    public List<LaborXOrdenTrabajo> getLaborXOrdenTrabajoList() {
        return laborXOrdenTrabajoList;
    }

    public void setLaborXOrdenTrabajoList(List<LaborXOrdenTrabajo> laborXOrdenTrabajoList) {
        this.laborXOrdenTrabajoList = laborXOrdenTrabajoList;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public int getConcecutivo() {
        return concecutivo;
    }

    public void setConcecutivo(int concecutivo) {
        this.concecutivo = concecutivo;
    }

    public boolean isNueva() {
        return nueva;
    }

    public void setNueva(boolean nueva) {
        this.nueva = nueva;
    }

    public boolean esClaveLlena(){
        return !codigoCorreria.isEmpty() && !codigoOrdenTrabajo.isEmpty() &&
                !tarea.getCodigoTarea().isEmpty() && !codigoTrabajo.isEmpty();
    }
}