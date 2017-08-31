package com.example.dominio.modelonegocio;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class Talleres implements Serializable{
    private String numeroTerminal;
    private String rutaServidor;
    private String versionMaestros;
    private String versionSoftware;
    private String wifi;
    private String impresora;
    private String direccionImpresora;
    @Nullable
    private Date fechaMaestros;
    private String tipoIdentificacion;
    private String confirmacion;
    private String sincronizacion;
    private int longitudImpresionFens;
    private int cantidadDecimalesFens;
    private String id;
    public static final String TIPO_IDENTIFICACION_AMBOS = "A";
    private String log;
    private String versionParametrizacion;

    public Talleres() {
        numeroTerminal = "";
        rutaServidor = "";
        versionMaestros = "";
        versionSoftware = "";
        wifi = "";
        impresora = "";
        direccionImpresora = "";
        tipoIdentificacion = "";
        confirmacion = "";
        sincronizacion = "";
        id = "";
        log = "";
        versionParametrizacion = "";
    }

    public int getCantidadDecimalesFens() {
        return cantidadDecimalesFens;
    }

    public void setCantidadDecimalesFens(int cantidadDecimalesFens) {
        this.cantidadDecimalesFens = cantidadDecimalesFens;
    }

    public int getLongitudImpresionFens() {
        return longitudImpresionFens;
    }

    public void setLongitudImpresionFens(int longitudImpresionFens) {
        this.longitudImpresionFens = longitudImpresionFens;
    }

    public String getNumeroTerminal() {
        return numeroTerminal;
    }

    public void setNumeroTerminal(String numeroTerminal) {
        this.numeroTerminal = numeroTerminal;
    }

    public String getRutaServidor() {
        return rutaServidor;
    }

    public void setRutaServidor(String rutaServidor) {
        this.rutaServidor = rutaServidor;
    }

    public String getVersionMaestros() {
        return versionMaestros;
    }

    public void setVersionMaestros(String versionMaestros) {
        this.versionMaestros = versionMaestros;
    }

    public Date getFechaMaestros() {
        return fechaMaestros;
    }

    public void setFechaMaestros(Date fechaMaestros) {
        this.fechaMaestros = fechaMaestros;
    }

    public String getVersionSoftware() {
        return versionSoftware;
    }

    public void setVersionSoftware(String versionSoftware) {
        this.versionSoftware = versionSoftware;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getImpresora() {
        return impresora;
    }

    public void setImpresora(String impresora) {
        this.impresora = impresora;
    }

    public String getDireccionImpresora() {
        return direccionImpresora;
    }

    public void setDireccionImpresora(String direccionImpresora) {
        this.direccionImpresora = direccionImpresora;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(String confirmacion) {
        this.confirmacion = confirmacion;
    }

    public String getSincronizacion() {
        return sincronizacion;
    }

    public void setSincronizacion(String sincronizacion) {
        this.sincronizacion = sincronizacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getVersionParametrizacion() {
        return versionParametrizacion;
    }

    public void setVersionParametrizacion(String versionParametrizacion) {
        this.versionParametrizacion = versionParametrizacion;
    }
}
