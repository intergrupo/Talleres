package com.example.dominio.modelonegocio;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class Mensaje {

    private String mensaje;
    private boolean respuesta;
    private TipoMensaje tipoMensaje;

    public Mensaje(){
        tipoMensaje = TipoMensaje.CRITICA;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(boolean respuesta) {
        this.respuesta = respuesta;
    }

    public TipoMensaje getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(TipoMensaje tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public enum TipoMensaje {
        ADVERTENCIA("Advertencia"),
        CRITICA("Critica");

        private String codigo;

        TipoMensaje(String codigo) {
            this.codigo = codigo;
        }

        public String getCodigo(){
            return codigo;
        }
    }
}