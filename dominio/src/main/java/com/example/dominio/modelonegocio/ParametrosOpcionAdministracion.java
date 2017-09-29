package com.example.dominio.modelonegocio;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class ParametrosOpcionAdministracion {

    private static final String ADMINISTRACION_PERSONALIZADA = "P";

    private boolean adminPersonalizada;

    public ParametrosOpcionAdministracion(String parametros) {
        if (!parametros.isEmpty()) {
            adminPersonalizada = parametros.equals(ADMINISTRACION_PERSONALIZADA);
        }
    }

    public boolean isAdminPersonalizada() {
        return adminPersonalizada;
    }
}
