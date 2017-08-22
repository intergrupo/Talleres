package com.example.utilidades;

import android.util.Base64;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class FileManager {

    public static void writeFile(byte[] data, String fileName) throws IOException {
        FileOutputStream out = new FileOutputStream(fileName);
        out.write(data);
        out.close();
    }

    public static byte[] convertStringToByteArray(String datos) {
        byte[] archivo = Base64.decode(datos.getBytes(), Base64.DEFAULT);
        return archivo;
    }

    public static boolean createFile(byte[] archivo, String nombre) {
        File file = new File(nombre);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(archivo);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteFile(String rutaCarpeta, String nombreArchivo) {
        boolean resultado = false;
        try {
            File file = new File(rutaCarpeta, nombreArchivo);
            if (file.exists()) {
                resultado = file.delete();
            }
        } catch (Exception ex) {
            Log.error(ex, "Eliminar Archivo");
        }
        return resultado;
    }

    public static boolean deleteFiles(String rutaCarpeta, List<String> nombreArchivos) {
        boolean todosArchivosBorrados = true;
        for (String nombreArchivo : nombreArchivos) {
            if (!deleteFile(rutaCarpeta, nombreArchivo)) {
                todosArchivosBorrados = false;
            }
        }
        return todosArchivosBorrados;
    }

    public static boolean deleteFolderContent(String rutaCarpeta) {
        boolean todosArchivosBorrados = true;
        try {
            File directorio = new File(rutaCarpeta);
            if (directorio.exists() && directorio.isDirectory()) {
                for (File file : directorio.listFiles()) {
                    if (file.isDirectory() && file.list().length > 0) {
                        deleteFolderContent(file.getAbsolutePath());

                    } else {
                        file.delete();
                    }
                }
            }
        } catch (Exception ex) {
            todosArchivosBorrados = false;
            Log.error(ex, "FileManager.deleteFolderContent");
        }
        return todosArchivosBorrados;
    }

    public static void eliminarCarpetaRecursivo(File archivoOCarpeta) {
        if (archivoOCarpeta.isDirectory())
            for (File archivo : archivoOCarpeta.listFiles())
                eliminarCarpetaRecursivo(archivo);
        archivoOCarpeta.delete();
    }

    public static boolean existFolder(File carpeta){
        return (carpeta.exists() && carpeta.isDirectory());
    }
}
