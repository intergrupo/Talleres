package com.example.santiagolopezgarcia.talleres.data.dao;

import android.content.Context;
import android.os.Environment;

import com.example.santiagolopezgarcia.talleres.data.AdministradorBaseDatos;
import com.example.santiagolopezgarcia.talleres.data.OperadorDatos;
import com.example.santiagolopezgarcia.talleres.helpers.Constantes;

import java.io.File;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */

public abstract class DaoBase {

    protected Context context;
    protected OperadorDatos operadorDatos;
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";
    public static final String REAL_TYPE = "real";
    public static final String NUMERIC_TYPE = "numeric";
    private static final String NOMBRE_BASE_DATOS = "Sirius.db";
    private static final int VERSION_BASE_DATOS = 3;

    public DaoBase(Context context) {
        this.context = context;
        String rutaBaseDatos = Environment.getExternalStorageDirectory() + File.separator + Constantes.NOMBRE_CARPETA_SIRIUS + File.separator + NOMBRE_BASE_DATOS;
        operadorDatos = new OperadorDatos(context, rutaBaseDatos, VERSION_BASE_DATOS, new AdministradorBaseDatos());
    }

    public boolean tieneRegistros() {
        return this.operadorDatos.numeroRegistros() > 0;
    }
}