package com.example.utilidades.helpers;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.utilidades.helpers.DateHelper.getCurrentDate;

/**
 * Created by juanfelipegomezvelez on 15/03/16.
 */
public class StringHelper {
    private final static String ARCHIVO_LOG = "Siriuslog-" + getCurrentDate(DateHelper.TipoFormato.dMMMyyyy) + ".txt";
    private static final String NOMBRE_CARPETA_SIRIUS = "Sirius";
    private static final String NOMBRE_CARPETA_LOG = "Log terminal";

    private static String traerRutaLog() {
        return Environment.getExternalStorageDirectory() + File.separator + NOMBRE_CARPETA_SIRIUS + File.separator + NOMBRE_CARPETA_LOG + File.separator;
    }

    public static String leftPad(String valor, int cantidad, String padCaracter) {
        StringBuilder stringBuilder = new StringBuilder(valor);

        for (int i = stringBuilder.length(); i < cantidad; i++) {
            stringBuilder.insert(0, padCaracter);
        }

        return stringBuilder.toString();
    }

    public static boolean ToBoolean(String valor) {
        if (valor != null) {
            return valor.equals("S");
        }
        return false;
    }

    public static void registrarLog(String mensaje) throws IOException {
        Date date = new Date();
        DateFormat fechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String mensajeLog = mensaje + " Fecha: " + fechaHora.format(date) + " "
                + MemoryHelper.memoriaDipositivo() + "\n";
        File archivoLog = new File(traerRutaLog(), ARCHIVO_LOG);
        FileWriter writer = new FileWriter(archivoLog, true);
        writer.append(mensajeLog + "\n");
        writer.flush();
        writer.close();

    }

    public static boolean isNumeric(String valor) {
        try {
            Double.parseDouble(valor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
