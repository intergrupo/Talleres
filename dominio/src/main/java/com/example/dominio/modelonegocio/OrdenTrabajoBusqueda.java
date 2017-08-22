package com.example.dominio.modelonegocio;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class OrdenTrabajoBusqueda  implements Serializable {

    private String codigoOrdenTrabajo;
    private TareaXTrabajo tareaXTrabajo;
    private Tarea.EstadoTarea estadoTarea;
    private String direccion;
    private String cliente;
    private String codigoLlave1;
    private String codigoLlave2;
    private boolean todos;
    private boolean noDescargados;
    private boolean limpiar;
    private String serieElemento;

    private String codigoCorreria;
    private ListaOrdenTrabajo listaOrdenTrabajo;
    private ListaTareaXOrdenTrabajo listaTareaXOrdenTrabajo;

    public OrdenTrabajoBusqueda() {
        codigoOrdenTrabajo = "";
        tareaXTrabajo = new TareaXTrabajo();
        estadoTarea = Tarea.EstadoTarea.NINGUNA;
        direccion = "";
        cliente = "";
        codigoLlave1 = "";
        codigoLlave2 = "";
        serieElemento = "";
        listaOrdenTrabajo = new ListaOrdenTrabajo();
        listaTareaXOrdenTrabajo = new ListaTareaXOrdenTrabajo();
    }

    public boolean esVacio()
    {
        return TextUtils.isEmpty(codigoOrdenTrabajo) && TextUtils.isEmpty(tareaXTrabajo.getTarea().getCodigoTarea()) &&
                TextUtils.isEmpty(tareaXTrabajo.getTrabajo().getCodigoTrabajo()) &&
                TextUtils.isEmpty(direccion) && TextUtils.isEmpty(cliente) &&
                TextUtils.isEmpty(codigoLlave1) && TextUtils.isEmpty(codigoLlave2) &&
                estadoTarea.equals(Tarea.EstadoTarea.NINGUNA) &&
                !todos && !noDescargados && serieElemento.isEmpty();
    }
    public String getCodigoOrdenTrabajo() {
        return codigoOrdenTrabajo;
    }

    public void setCodigoOrdenTrabajo(String codigoOrdenTrabajo) {
        this.codigoOrdenTrabajo = codigoOrdenTrabajo;
    }

    public Tarea.EstadoTarea getEstadoTarea() {
        return estadoTarea;
    }

    public void setEstadoTarea(Tarea.EstadoTarea estadoTarea) {
        this.estadoTarea = estadoTarea;
    }

    public TareaXTrabajo getTareaXTrabajo() {
        return tareaXTrabajo;
    }

    public void setTareaXTrabajo(TareaXTrabajo tareaXTrabajo) {
        this.tareaXTrabajo = tareaXTrabajo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCodigoLlave1() {
        return codigoLlave1;
    }

    public void setCodigoLlave1(String codigoLlave1) {
        this.codigoLlave1 = codigoLlave1;
    }

    public String getCodigoLlave2() {
        return codigoLlave2;
    }

    public void setCodigoLlave2(String codigoLlave2) {
        this.codigoLlave2 = codigoLlave2;
    }

    public boolean isTodos() {
        return todos;
    }

    public void setTodos(boolean todos) {
        this.todos = todos;
    }

    public boolean isNoDescargados() {
        return noDescargados;
    }

    public void setNoDescargados(boolean noDescargados) {
        this.noDescargados = noDescargados;
    }

    public boolean isLimpiar() {
        return limpiar;
    }

    public void setLimpiar(boolean limpiar) {
        this.limpiar = limpiar;
    }

    public String getSerieElemento() {
        return serieElemento;
    }

    public void setSerieElemento(String serieElemento) {
        this.serieElemento = serieElemento;
    }

    public void setCodigoCorreria(String codigoCorreria){
        this.codigoCorreria = codigoCorreria;
    }

    public String getCodigoCorreria(){
        return this.codigoCorreria;
    }

    public void setListaOrdenTrabajo(ListaOrdenTrabajo listaOrdenTrabajo) {
        this.listaOrdenTrabajo = listaOrdenTrabajo;
    }

    public ListaOrdenTrabajo getListaOrdenTrabajo(){
        return this.listaOrdenTrabajo;
    }

    public void setListaTareaXOrdenTrabajo(ListaTareaXOrdenTrabajo listaTareaXOrdenTrabajo) {
        this.listaTareaXOrdenTrabajo = listaTareaXOrdenTrabajo;
    }

    public ListaTareaXOrdenTrabajo getListaTareaXOrdenTrabajo(){
        return this.listaTareaXOrdenTrabajo;
    }
}