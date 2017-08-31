package com.example.santiagolopezgarcia.talleres.helpers;

import android.os.Environment;

import java.io.File;

/**
 * Created by santiagolopezgarcia on 17/03/16.
 */

public class Constantes {
    public static final String NOMBRE_CARPETA_SIRIUS = "Talleres";
    public static final String NOMBRE_CARPETA_ADJUNTOS = "SiriusAdjuntos";
    public static final String NOMBRE_CARPETA_CARGA = "Carga";
    public static final String NOMBRE_CARPETA_DESCARGA = "Descarga";
    public static final String NOMBRE_CARPETA_MAESTROS = "Maestros";
    public static final String NOMBRE_CARPETA_CARGA_DIARIA = "CargaDiaria";
    public static final String NOMBRE_CARPETA_TELEMEDIDA = "Telemedida";
    public static final String NOMBRE_CARPETA_RECIBO_WEB = "ReciboWeb";
    public static final String NOMBRE_CARPETA_DESCARGA_COMPLETA = "DescargaCompleta";
    public static final String NOMBRE_CARPETA_DESCARGA_PARCIAL = "DescargaParcial";
    public static final String NOMBRE_CARPETA_ENVIO_DIRECTO = "EnvioDirecto";
    public static final String NOMBRE_CARPETA_ENVIO_WEB= "EnvioWeb";
    public static final String NOMBRE_ARCHIVO_ZIP_DESCARGA = "descarga.zip";
    public static final String NOMBRE_ARCHIVO_ZIP_CAMERA = "camera.zip";
    public static final String NOMBRE_ARCHIVO_ZIP_ENVIO_DIRECTO = "enviodirecto.zip";
    public static final String NOMBRE_ARCHIVO_ZIP_DIAGRAMS = "diagrams.zip";
    public static final String NOMBRE_CARPETA_CAMERA = "Camera";
    public static final String NOMBRE_CARPETA_DIAGRAMS = "Diagrams";
//    public static final String NOMBRE_CARPETA_DESCARGA_ADJUNTOS = "DescargaAdjuntos";
    public static final String NOMBRE_CARPETA_lOGOS_EMPRESA = "LogosEmpresa";
    public static final String NOMBRE_CARPETA_LOG = "Log terminal";
    public static final String NOMBRE_CARPETA_BLUETOOTH = "Bluetooth";
    public static final String NOMBRE_CARPETA_RECIBO_DIRECTO = "ReciboDirecto";
    public static final String NOMBRE_IMAGEN_LOGO_CENS_4p = "logocens_4p.JPG";
    public static final String NOMBRE_IMAGEN_LOGO_CENS = "logocens.JPG";
    public static final String NOMBRE_IMAGEN_LOGO_CHEC_4p = "logochec_4p.JPG";
    public static final String NOMBRE_IMAGEN_LOGO_CHEC = "logochec.JPG";
    public static final String NOMBRE_IMAGEN_LOGO_EDEQ_4p = "logodeq_4p.JPG";
    public static final String NOMBRE_IMAGEN_LOGO_EDEQ = "logoedeq.JPG";
    public static final String NOMBRE_IMAGEN_LOGO_EPM_4p = "logoepm_4p.JPG";
    public static final String NOMBRE_IMAGEN_LOGO_EPM = "logoepm.JPG";
    public static final String NOMBRE_IMAGEN_LOGO_ESSA_4p = "logoessa_4p.JPG";
    public static final String NOMBRE_IMAGEN_LOGO_ESSA = "logoessa.JPG";
    public final static String PROCESO_INICIO_SESION = "IS";
    public final static String PROCESO_FIN_SESION = "FS";
    public final static String MENSAJE_INICIO_SESION = "Inicio sesion";
    public final static String MENSAJE_FIN_SESION = "Fin sesion";
    public final static String ESTADO_OK = "0";
    public final static String ESTADO_ERROR = "1";


    public static String traerRutaSirius() {
        return Environment.getExternalStorageDirectory() + File.separator + NOMBRE_CARPETA_SIRIUS + File.separator;
    }

    public static String traerRutaAdjuntos() {
        return Environment.getExternalStorageDirectory() + File.separator + NOMBRE_CARPETA_SIRIUS + File.separator + NOMBRE_CARPETA_ADJUNTOS + File.separator;
    }

    public static String traerRutaReciboDirecto() {
        return Environment.getExternalStorageDirectory() + File.separator + NOMBRE_CARPETA_SIRIUS + File.separator + NOMBRE_CARPETA_RECIBO_DIRECTO + File.separator;
    }

    public static String traerRutaCarga() {
        return Environment.getExternalStorageDirectory() + File.separator + NOMBRE_CARPETA_SIRIUS + File.separator + NOMBRE_CARPETA_CARGA + File.separator;
    }

    public static String traerRutaDescarga() {
        return Environment.getExternalStorageDirectory() + File.separator + NOMBRE_CARPETA_SIRIUS + File.separator + NOMBRE_CARPETA_DESCARGA + File.separator;
    }

    public static String traerRutaCamera() {
        return Environment.getExternalStorageDirectory() + File.separator + NOMBRE_CARPETA_SIRIUS + File.separator + NOMBRE_CARPETA_CAMERA + File.separator;
    }

    public static String traerRutaDiagrams() {
        return Environment.getExternalStorageDirectory() + File.separator + NOMBRE_CARPETA_SIRIUS + File.separator + NOMBRE_CARPETA_DIAGRAMS + File.separator;
    }

    public static String traerRutaDescargaAdjuntos() {
        return Environment.getExternalStorageDirectory() + File.separator + NOMBRE_CARPETA_SIRIUS + File.separator
                + NOMBRE_CARPETA_DESCARGA + File.separator + NOMBRE_CARPETA_DIAGRAMS + File.separator;
    }

    public static String traerRutaLogoEmpresa() {
        return Environment.getExternalStorageDirectory() + File.separator + NOMBRE_CARPETA_SIRIUS + File.separator + NOMBRE_CARPETA_lOGOS_EMPRESA + File.separator;
    }

    public static String traerRutaBluetooth(){
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + NOMBRE_CARPETA_BLUETOOTH + File.separator;
    }

    public static String traerRutaEnvioDirecto() {
        return Environment.getExternalStorageDirectory() + File.separator + NOMBRE_CARPETA_SIRIUS + File.separator + NOMBRE_CARPETA_ENVIO_DIRECTO + File.separator;
    }

    public static String traerRutaEnvioWeb() {
        return Environment.getExternalStorageDirectory() + File.separator + NOMBRE_CARPETA_SIRIUS + File.separator + NOMBRE_CARPETA_ENVIO_WEB + File.separator;
    }

    public static String traerRutaReciboWeb() {
        return Environment.getExternalStorageDirectory() + File.separator + NOMBRE_CARPETA_SIRIUS + File.separator + NOMBRE_CARPETA_RECIBO_WEB + File.separator;
    }

}
