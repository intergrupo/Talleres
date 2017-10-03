package com.example.santiagolopezgarcia.talleres.view.interfaces;

import android.content.Context;
import android.content.Intent;

import com.example.dominio.modelonegocio.ClasificacionVista;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public interface InvocarActivity {

    void invocar(Intent intent, ClasificacionVista clasificacionVista);

    Context obtenerContexto();
}
