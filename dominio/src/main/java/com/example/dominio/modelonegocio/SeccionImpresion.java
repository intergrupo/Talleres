package com.example.dominio.modelonegocio;

import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class SeccionImpresion implements Serializable {

    private String codigoImpresion;
    private String codigoSeccion;
    private String descripcion;
    private String rutina;
    @Nullable
    private String parametros;

    public static  class Rutina{
        public static final String IMPRIMIR_ENCABEZADO3 = "ImprimirEncabezado3";
        public static final String IMPRIMIR_ENCABEZADO = "ImprimirEncabezado";
        public static final String IMPRIMIR_ENCABEZADO2 = "ImprimirEncabezado2";
        public static final String IMPR_LAB_ELEMENTO_LECT = "ImprLabElementoLect";
        public static final String IMPRIMIR_CODIGO_BARRAS = "ImprimirCodigoBarras";
        public static final String IMPRIMIR_INFO_OT = "ImprimirInfoOt";
        public static final String IMPRIMIR_INFO_FIRMA = "ImprimirInfoFirma";
        public static final String IMPRIMIR_INFO_IMAGEN = "ImprimirInfoImage";
        public static final String IMPRIMIR_INFO_NOTI = "ImprimirInfoNoti";
        public static final String IMPRIMIR_LAB_NOTIFICA = "ImprimirLabNotifica";
        public static final String IMPRIMIR_PARRAFO = "ImprimirParrafo";
        public static final String IMPRIMIR_PIE_PAGINA = "ImprimirPiePagina";
        public static final String IMPRIMIR_LAB_MATERIAL1 = "ImprimirLabMaterial1";
        public static final String IMPRIMIR_LAB_MATERIAL2 = "ImprimirLabMaterial2";
        public static final String IMPRIMIR_LAB_MATERIAL3 = "ImprimirLabMaterial3";
        public static final String IMPRIMIR_LAB_AFORO = "ImprimirLabAforo";
        public static final String IMPRIMIR_LAB_AFORO2 = "ImprimirLabAforo2";
        public static final String IMPRIMIR_LAB_AFORO3 = "ImprimirLabAforo3";
        public static final String IMPRIMIR_LAB_ELEMENTO = "ImprimirLabElemento";

    }

    public SeccionImpresion() {
        codigoImpresion = "";
        descripcion = "";
        codigoSeccion = "";
        descripcion = "";
        rutina = "";
        parametros = "";
    }

    public String getCodigoImpresion() {
        return codigoImpresion;
    }

    public void setCodigoImpresion(String codigoImpresion) {
        this.codigoImpresion = codigoImpresion;
    }

    public String getCodigoSeccion() {
        return codigoSeccion;
    }

    public void setCodigoSeccion(String codigoSeccion) {
        this.codigoSeccion = codigoSeccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRutina() {
        return rutina;
    }

    public void setRutina(String rutina) {
        this.rutina = rutina;
    }

    @Nullable
    public String getParametros() {
        return parametros;
    }

    public void setParametros(@Nullable String parametros) {
        this.parametros = parametros;
    }

    public boolean esClaveLlena(){
        return !codigoImpresion.isEmpty() && !codigoSeccion.isEmpty();
    }

    @Nullable
    public String[] getParametros(String separador) {
        return this.parametros.split(separador);
    }
}
