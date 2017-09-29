package com.example.dominio.modelonegocio;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class ParametrosConfirmacion {

    private boolean confirmarOT;
    private boolean confirmarCorreria;
    private boolean confirmarAmbos;
    private boolean noConfirmar;

    public ParametrosConfirmacion(String parametro) {
        confirmarOT = parametro.equals("O");
        confirmarCorreria = parametro.equals("C");
        confirmarAmbos = parametro.equals("T");
        noConfirmar = parametro.equals("N");
    }

    public boolean isConfirmarOT() {
        return confirmarOT;
    }

    public boolean isConfirmarCorreria() {
        return confirmarCorreria;
    }

    public boolean isConfirmarAmbos() {
        return confirmarAmbos;
    }

    public boolean isNoConfirmar() {
        return noConfirmar;
    }
}
