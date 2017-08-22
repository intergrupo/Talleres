package com.example.dominio.modelonegocio;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class Item implements Serializable {

    private String codigoItem;
    private String descripcion;
    private String tipoItem;
    private String mascara;
    private String formato;
    private String tablaLista;
    private String campoLlaveItem;
    private String campoValorLista;
    private String campoDisplayLista;
    private String rutina;
    private String parametros;
    private ParametrosTextoYListas parametrosTextoYListas;
    private ParametrosNotificacionFecha parametrosNotificacionFecha;

    public Item() {
        codigoItem = "";
        descripcion = "";
        tipoItem = "";
        mascara = "";
        formato = "";
        tablaLista = "";
        campoLlaveItem = "";
        campoValorLista = "";
        campoDisplayLista = "";
        rutina = "";
        parametros = "";
    }

    public String getCodigoItem() {
        return codigoItem;
    }

    public void setCodigoItem(String codigoItem) {
        this.codigoItem = codigoItem;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(String tipoItem) {
        this.tipoItem = tipoItem;
    }

    public String getMascara() {
        return mascara;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getTablaLista() {
        return tablaLista;
    }

    public void setTablaLista(String tablaLista) {
        this.tablaLista = tablaLista;
    }

    public String getCampoLlaveItem() {
        return campoLlaveItem;
    }

    public void setCampoLlaveItem(String campoLlaveItem) {
        this.campoLlaveItem = campoLlaveItem;
    }

    public String getCampoValorLista() {
        return campoValorLista;
    }

    public void setCampoValorLista(String campoValorLista) {
        this.campoValorLista = campoValorLista;
    }

    public String getCampoDisplayLista() {
        return campoDisplayLista;
    }

    public void setCampoDisplayLista(String campoDisplayLista) {
        this.campoDisplayLista = campoDisplayLista;
    }

    public String getRutina() {
        return rutina;
    }

    public void setRutina(String rutina) {
        this.rutina = rutina;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public ParametrosTextoYListas getParametrosTextoYListas() {
        return parametrosTextoYListas;
    }

    public void setParametrosTextoYListas(ParametrosTextoYListas parametrosTextoYListas) {
        this.parametrosTextoYListas = parametrosTextoYListas;
    }

    public ParametrosNotificacionFecha getParametrosNotificacionFecha() {
        return parametrosNotificacionFecha;
    }

    public void setParametrosNotificacionFecha(ParametrosNotificacionFecha parametrosNotificacionFecha) {
        this.parametrosNotificacionFecha = parametrosNotificacionFecha;
    }
}
