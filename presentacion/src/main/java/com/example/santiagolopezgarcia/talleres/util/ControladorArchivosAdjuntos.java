package com.example.santiagolopezgarcia.talleres.util;

import com.example.dominio.modelonegocio.ArchivoAdjunto;
import com.example.santiagolopezgarcia.talleres.helpers.Constantes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class ControladorArchivosAdjuntos {

    public File traerArchivoEncontrado(String nombreArchivo) {
        File ruta = new File(Constantes.traerRutaAdjuntos());
        File[] listaArchivos = ruta.listFiles();
        for (File archivo : listaArchivos) {
            if (!archivo.isDirectory()) {
                if (archivo.getName().equals(nombreArchivo)) {
                    return archivo;
                }
            } else {
                File retorno = traerArchivoEncontradoRecursivo(nombreArchivo, new File(archivo.getAbsolutePath()));
                if (retorno != null ) {
                    if (retorno.getName().equals(nombreArchivo))
                        return retorno;
                }
            }
        }
        return new File("");
    }

    public File traerArchivoEncontradoRecursivo(String nombreArchivo, File ruta) {
        File[] listaArchivos = ruta.listFiles();
        for (File archivo : listaArchivos) {
            if (!archivo.isDirectory()) {
                if (archivo.getName().equals(nombreArchivo)) {
                    return archivo;
                }
            } else {
                traerArchivoEncontradoRecursivo(nombreArchivo, new File(archivo.getAbsolutePath()));
            }
        }
        return new File("");
    }


    public File traerArchivoEncontrado(ArchivoAdjunto nombreArchivo) {
        File ruta;
        ruta = new File(nombreArchivo.getRutaArchivo());
        if(!ruta.exists()){
            ruta = new File(Constantes.traerRutaAdjuntos());
        }
        File[] listaArchivos = ruta.listFiles();
        for (File archivo : listaArchivos) {
            if (!archivo.isDirectory()) {
                if (archivo.getName().equals(nombreArchivo.getNombreArchivo())) {
                    return archivo;
                }
            } else {
                File retorno = traerArchivoEncontradoRecursivo(nombreArchivo
                        , new File(archivo.getAbsolutePath()));
                if (retorno != null ) {
                    if (retorno.getName().equals(nombreArchivo))
                        return retorno;
                }
            }
        }
        return new File("");
    }

    public File traerArchivoEncontradoRecursivo(ArchivoAdjunto nombreArchivo, File ruta) {
        File[] listaArchivos = ruta.listFiles();
        for (File archivo : listaArchivos) {
            if (!archivo.isDirectory()) {
                if (archivo.getName().equals(nombreArchivo.getNombreArchivo())) {
                    return archivo;
                }
            } else {
                traerArchivoEncontradoRecursivo(nombreArchivo
                        , new File(archivo.getAbsolutePath()));
            }
        }
        return new File("");
    }

    public void copiarArchivo(File origen, File destino) throws IOException {
        InputStream in = new FileInputStream(origen);
        OutputStream out = new FileOutputStream(destino);

        // Transfer bytes from in to out
        byte[] buf = new byte[traerBytes(origen).length];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public static byte[] traerBytes(File archivo) throws FileNotFoundException {
        FileInputStream fileInputStream = null;
        byte[] bFile = new byte[(int) archivo.length()];
        try {
            fileInputStream = new FileInputStream(archivo);
            fileInputStream.read(bFile);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bFile;
    }

    public void copiarArchivos(String rutaOrigen, String rutaDestino) throws IOException {
        File file =  new File(rutaOrigen);
        File[] listaArchivos = file.listFiles();
        for (File archivoACopiar : listaArchivos){
            this.copiarArchivo(archivoACopiar, new File(rutaDestino + archivoACopiar.getName()));
        }
    }

}
