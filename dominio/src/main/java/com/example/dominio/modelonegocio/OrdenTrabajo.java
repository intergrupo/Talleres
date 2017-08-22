package com.example.dominio.modelonegocio;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class  OrdenTrabajo implements Serializable {

    private String codigoCorreria;
    private String codigoOrdenTrabajo;
    private int secuencia;
    private boolean nueva;
    private String nombre;
    private String direccion;
    private Departamento departamento;
    private String gps;
    private String telefono;
    private String codigoLlave1;
    private String codigoLlave2;
    private EstadoOrdenTrabajo estado;
    @Nullable
    private Date fechaInicioOrdenTrabajo;
    @Nullable
    private Date fechaUltimaOrdenTrabajo;
    private String codigoUsuarioLabor;
    private TrabajoXOrdenTrabajo trabajoXOrdenTrabajo;
    private String parametros;
    private String estadoComunicacion;
    private String sesion;
    @Nullable
    private Date fechaCarga;
    private int concecutivo;
    private String imprimirFactura;
    private String codigoOrdenTrabajoRelacionada;

    public OrdenTrabajo() {
        codigoCorreria = "";
        codigoOrdenTrabajo = "";
        nombre = "";
        direccion = "";
        departamento = new Departamento();
        gps = "";
        telefono = "";
        codigoLlave1 = "";
        codigoLlave2 = "";
        codigoUsuarioLabor = "";
        estado = EstadoOrdenTrabajo.ENEJECUCION;
        trabajoXOrdenTrabajo = new TrabajoXOrdenTrabajo();
        parametros = "";
        estadoComunicacion = "";
        sesion = "";
        imprimirFactura = "";
    }

    public boolean esValidaInformacionObligatoriaGuardar() {
        return !codigoCorreria.isEmpty() && !codigoOrdenTrabajo.isEmpty() &&
                !departamento.getMunicipio().getCodigoMunicipio().isEmpty();
    }

    public boolean esClaveLlena(){
        return !codigoCorreria.isEmpty() && !codigoOrdenTrabajo.isEmpty();
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

    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public boolean isNueva() {
        return nueva;
    }

    public void setNueva(boolean nueva) {
        this.nueva = nueva;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public EstadoOrdenTrabajo getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrdenTrabajo estado) {
        this.estado = estado;
    }

    public Date getFechaInicioOrdenTrabajo() {
        return fechaInicioOrdenTrabajo;
    }

    public int getConcecutivo() {
        return concecutivo;
    }

    public void setConcecutivo(int concecutivo) {
        this.concecutivo = concecutivo;
    }

    public void setFechaInicioOrdenTrabajo(Date fechaInicioOrdenTrabajo) {
        this.fechaInicioOrdenTrabajo = fechaInicioOrdenTrabajo;
    }

    public Date getFechaUltimaOrdenTrabajo() {
        return fechaUltimaOrdenTrabajo;
    }

    public void setFechaUltimaOrdenTrabajo(Date fechaUltimaOrdenTrabajo) {
        this.fechaUltimaOrdenTrabajo = fechaUltimaOrdenTrabajo;
    }

    public String getCodigoUsuarioLabor() {
        return codigoUsuarioLabor;
    }

    public void setCodigoUsuarioLabor(String codigoUsuarioLabor) {
        this.codigoUsuarioLabor = codigoUsuarioLabor;
    }

    public TrabajoXOrdenTrabajo getTrabajoXOrdenTrabajo() {
        return trabajoXOrdenTrabajo;
    }

    public void setTrabajoXOrdenTrabajo(TrabajoXOrdenTrabajo trabajoXOrdenTrabajo) {
        this.trabajoXOrdenTrabajo = trabajoXOrdenTrabajo;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public String getEstadoComunicacion() {
        return estadoComunicacion;
    }

    public void setEstadoComunicacion(String estadoComunicacion) {
        this.estadoComunicacion = estadoComunicacion;
    }

    public String getSesion() {
        return sesion;
    }

    public void setSesion(String sesion) {
        this.sesion = sesion;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getImprimirFactura() {
        return imprimirFactura;
    }

    public void setImprimirFactura(String imprimirFactura) {
        this.imprimirFactura = imprimirFactura;
    }

    public String getCodigoOrdenTrabajoRelacionada() {
        return codigoOrdenTrabajoRelacionada;
    }

    public void setCodigoOrdenTrabajoRelacionada(String codigoOrdenTrabajoRelacionada) {
        this.codigoOrdenTrabajoRelacionada = codigoOrdenTrabajoRelacionada;
    }

    public enum EstadoOrdenTrabajo {

        ENEJECUCION("ENEJECUCION", "00"),
        FACTURADOIMPRESO("FacturadoImpreso","01"),
        FACTURADONOIMPRESO("FacturadoNoImpreso","02"),
        CANCELADA("Cancelada", "03");

        private String nombre;
        private String codigo;

        EstadoOrdenTrabajo(String nombreEstado, String codigo) {
            this.nombre = nombreEstado;
            this.codigo = codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public String setNombre(String nombre) {
            return this.nombre = nombre;
        }

        public String getCodigo() {
            return codigo;
        }

        public static EstadoOrdenTrabajo getEstado(String valor) {
            switch (valor) {
                case "01":
                    return FACTURADOIMPRESO;
                case "02":
                    return FACTURADONOIMPRESO;
                case "03":
                    return CANCELADA;
                default:
                    return ENEJECUCION;
            }
        }
    }

    public String getInformacionOrdenTrabajo(String parametroImpresion)
    {
        String valor = "";
        switch(parametroImpresion)
        {
            case "ord": // Id de la orden de trabajo
                valor = this.getCodigoOrdenTrabajo();
                break;
            case "ot": // Numero de la orden de trabajo
                valor = this.getCodigoOrdenTrabajo();
                break;
            case "ocod": // codigo de la orden de trabajo
                valor = this.getCodigoOrdenTrabajo();
                break;
            case "nom": // codigo de la orden de trabajo
                valor = this.getNombre();
                break;
            case "dir": // Dirección del enterado
                valor = this.getDireccion();
                break;

            case "tel": // Dirección del enterado
                valor = this.getTelefono();
                break;

            case "mun": // Municipio donde se atendio la orden de trabajo
                valor = this.getDepartamento().getMunicipio().getDescripcion();
                break;

            case "dep": // departamento donde se atendio la orden de trabajo
                valor = this.getDepartamento().getDescripcion();
                break;

            case "ins": // Instalación de la orden de trabajo
                valor = this.getCodigoLlave2();
                break;

            case "ran": // Rango de la orden de trabajo
                valor = this.getCodigoLlave2();
                break;

            case "clie": // Rango de la orden de trabajo
                valor = "";// Globals.Ot.ServicioSuscrito;
                break;

            case "estr": // Estrato
                valor = "";// Globals.Ot.ServicioSuscrito;
                break;
            case "cor": // Instalación de la orden de trabajo
                valor = this.getCodigoCorreria();
                break;
        }
        return valor;
    }
}