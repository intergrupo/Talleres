package com.example.utilidades.helpers;

import android.support.annotation.Nullable;

import com.example.utilidades.Log;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by juanfelipegomezvelez on 7/10/15.
 */
public class DateHelper {

    public enum TipoFormato {

        //MMddyyyyhmmssa(0),
        dMMMyyyy(1),
        ddMMMyyyy(2),
        yyyyMMddTHHmmss(3),
        ddMMyyyy(4),
        hmma(5),
        dMMMyyyy_separador(6),
        yyyyMMddHHmmss(7),
        yyyyMMddHHmmss_separador(8),
        hhmmss(9),
        ddMMMyyyyhhmma(10);

        private int codigo;

        TipoFormato(int codigo) {

            this.codigo = codigo;
        }

        public int getCodigo() {
            return codigo;
        }
    }

    public static String getCurrentDate(TipoFormato tipoFormato) {
        String formatoFecha = obtenerFormato(tipoFormato);
        Format formato = new SimpleDateFormat(formatoFecha);
        return formato.format(new Date());
    }

    public static String getCurrentDate(String formatoFecha) {
        Format formato = new SimpleDateFormat(formatoFecha);
        return formato.format(new Date());
    }

    @Nullable
    public static Date convertirStringADate(String fechaAConvertir, TipoFormato tipoFormato) throws ParseException {
        if (fechaAConvertir != null && !fechaAConvertir.isEmpty()) {
            String formatoFecha = obtenerFormato(tipoFormato);
            DateFormat formato = new SimpleDateFormat(formatoFecha, new Locale("es_ES"));
            Date fecha = formato.parse(fechaAConvertir);
            return fecha;
        }
        return null;
    }

    public static Date convertirStringADate(String fechaAConvertir, String formatoFecha) throws ParseException {
        if (fechaAConvertir != null && !fechaAConvertir.isEmpty()) {
            DateFormat formato = new SimpleDateFormat(formatoFecha, new Locale("es_ES"));
            Date fecha = formato.parse(fechaAConvertir);
            return fecha;
        }
        return null;
    }

    public static String convertirDateAString(Date fechaAConvertir, TipoFormato tipoFormato) throws ParseException {
        String fechaConvertida = null;
        if (fechaAConvertir != null) {
            try {
                String formatoFecha = obtenerFormato(tipoFormato);
                Format formato = new SimpleDateFormat(formatoFecha, new Locale("es_ES"));
                fechaConvertida = formato.format(fechaAConvertir);
            } catch (Exception ex) {
                Log.error(ex,"Conversion de fechas");
            }
        }
        return fechaConvertida;
    }

    public static String convertirDateAString(Date fechaAConvertir, String formatoFecha) throws ParseException {
        Format formato = new SimpleDateFormat(formatoFecha, new Locale("es_ES"));
        String fechaConvertida = formato.format(fechaAConvertir);
        return fechaConvertida;
    }

    private static String obtenerFormato(TipoFormato tipoFormato) {
        String formatoFecha = "";
        switch (tipoFormato) {
//            case MMddyyyyhmmssa:
//                formatoFecha = "MM/dd/yyyy h:mm:ss a";
//                break;
            case ddMMyyyy:
                formatoFecha = "dd/MM/yyyy";
                break;
            case dMMMyyyy:
                formatoFecha = "d MMM yyyy";
                break;
            case ddMMMyyyy:
                formatoFecha = "dd/MMM/yyyy";
                break;
            case ddMMMyyyyhhmma:
                formatoFecha = "dd/MMM/yyyy hh:mm a";
                break;
            case yyyyMMddTHHmmss:
                formatoFecha = "yyyy-MM-dd'T'HH:mm:ss";
                break;
            case yyyyMMddHHmmss:
                formatoFecha = "yyyy-MM-dd HH:mm:ss";
                break;
            case hmma:
                formatoFecha = "h:mm a";
                break;
            case dMMMyyyy_separador:
                formatoFecha = "d/MMM/yyyy";
                break;
            case yyyyMMddHHmmss_separador:
                formatoFecha = "yyyy/MM/dd HH:mm:ss";
                break;
            case hhmmss:
                formatoFecha = "HH:mm:ss";
                break;
            default:
                break;
        }
        return formatoFecha;
    }
}