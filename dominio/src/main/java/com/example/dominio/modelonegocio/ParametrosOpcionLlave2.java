package com.example.dominio.modelonegocio;

import java.util.regex.Pattern;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class ParametrosOpcionLlave2 {

    private String codigoLlave2;
    private String descripcionLlave2;

    public ParametrosOpcionLlave2(String parametro) {
        codigoLlave2 = "";
        descripcionLlave2 = "";
        if (!parametro.isEmpty()) {
            String[] claves = parametro.split(Pattern.quote(","));
            codigoLlave2 = claves[0];
            descripcionLlave2 = claves[1];
        }
    }

    public String getCodigoLlave2() {
        return codigoLlave2;
    }

    public String getDescripcionLlave2() {
        return descripcionLlave2;
    }
}
