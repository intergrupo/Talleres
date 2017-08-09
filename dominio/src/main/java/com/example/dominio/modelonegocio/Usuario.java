package com.example.dominio.modelonegocio;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */

public class Usuario {
    private String codigoUsuario;
    private Perfil perfil;
    private String nombre;
    private Contrato contrato;
    private String clave;
    @Nullable
    private Date fechaIngreso;
    private String codigoUsuarioIngreso;

    public Usuario() {
        codigoUsuario = "";
        perfil = new Perfil();
        nombre = "";
        contrato = new Contrato();
        clave = "";
        codigoUsuarioIngreso = "";
    }

    public String getCodigoUsuario() {
        return codigoUsuario.trim();
    }

    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public String getClave() {
        return clave.trim();
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getCodigoUsuarioIngreso() {
        return codigoUsuarioIngreso;
    }

    public void setCodigoUsuarioIngreso(String codigoUsuarioIngreso) {
        this.codigoUsuarioIngreso = codigoUsuarioIngreso;
    }

    public boolean esUsuarioValido() {
        return !TextUtils.isEmpty(codigoUsuario) && !TextUtils.isEmpty(nombre);
    }

    public boolean validarClave(String contraseña) {
        return TextUtils.equals(contraseña, clave);
    }

    public boolean fechaIngresoValida() {
        Date fechaActual = new Date();
        Calendar calendarioActual = Calendar.getInstance();
        calendarioActual.setTime(fechaActual);
        int mesfechaActual = calendarioActual.get(Calendar.MONTH) + 1;

        Calendar calendarioUsuario = Calendar.getInstance();
        calendarioUsuario.setTime(fechaIngreso);
        int mesIngreso = calendarioUsuario.get(Calendar.MONTH) + 1;

        return (mesfechaActual <= mesIngreso);
    }
}
