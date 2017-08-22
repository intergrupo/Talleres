package com.example.utilidades;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.io.IOException;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class ZipManager {

    private static final int BUFFER_SIZE = 4096;

    public static boolean unZip(String rutazip, String rutadestino, String clave) throws IOException {
        try {
            File src = new File(rutazip);
            net.lingala.zip4j.core.ZipFile zipFile = new net.lingala.zip4j.core.ZipFile(src);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(clave);
            }
            zipFile.extractAll(rutadestino);
        } catch (net.lingala.zip4j.exception.ZipException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void zip(String[] _files, String zipFileName, String clave) throws ZipException {
        try {
            net.lingala.zip4j.core.ZipFile zipFile = new net.lingala.zip4j.core.ZipFile(zipFileName);
            net.lingala.zip4j.model.ZipParameters parameters = new net.lingala.zip4j.model.ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);

            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

            parameters.setEncryptFiles(true);

            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);

            parameters.setPassword(clave);


            for (String file : _files) {
                File archivo = new File(file);
                if (!archivo.isDirectory()) {
                    zipFile.addFile(archivo, parameters);
                } else {
                    zipFile.addFolder(archivo, parameters);
                }

            }
        } catch (ZipException e) {
            Log.error(e,"Comprimiendo archivo");
            throw e;
        }
    }
}
