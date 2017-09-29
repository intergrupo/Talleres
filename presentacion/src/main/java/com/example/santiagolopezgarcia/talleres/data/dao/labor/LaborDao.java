package com.example.santiagolopezgarcia.talleres.data.dao.labor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.labor.LaborRepositorio;
import com.example.dominio.modelonegocio.Labor;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class LaborDao extends DaoBase implements LaborRepositorio {

    public static final String NombreTabla = "sirius_labor";

    public LaborDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public boolean guardar(List<Labor> lista) {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (Labor labor : lista) {
            listaContentValues.add(convertirObjetoAContentValues(labor));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(Labor dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    @Override
    public boolean actualizar(Labor dato) {
        ContentValues datos = convertirObjetoAContentValues(dato);
        return this.operadorDatos.actualizar(datos, ColumnaLabor.CODIGOLABOR + " = ? "
                , new String[]{dato.getCodigoLabor()});
    }

    @Override
    public boolean eliminar(Labor dato) {
        return this.operadorDatos.borrar(ColumnaLabor.CODIGOLABOR + " = ? "
                , new String[]{dato.getCodigoLabor()}) > 0;
    }

    private Labor convertirCursorAObjeto(Cursor cursor) {
        Labor labor = new Labor();
        labor.setCodigoLabor(cursor.getString(cursor.getColumnIndex(ColumnaLabor.CODIGOLABOR)));
        labor.setNombre(cursor.getString(cursor.getColumnIndex(ColumnaLabor.NOMBRE)));
        labor.setRutina(cursor.getString(cursor.getColumnIndex(ColumnaLabor.RUTINA)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaLabor.PARAMETROS))) {
            labor.setParametros(cursor.getString(cursor.getColumnIndex(ColumnaLabor.PARAMETROS)));
        }
        return labor;
    }

    private ContentValues convertirObjetoAContentValues(Labor labor) {
        ContentValues contentValues = new ContentValues();
        if (!labor.getCodigoLabor().isEmpty()) {
            contentValues.put(ColumnaLabor.CODIGOLABOR, labor.getCodigoLabor());
        }
        if (!labor.getNombre().isEmpty()) {
            contentValues.put(ColumnaLabor.NOMBRE, labor.getNombre());
        }
        if (!labor.getRutina().isEmpty()) {
            contentValues.put(ColumnaLabor.RUTINA, labor.getRutina());
        }
        contentValues.put(ColumnaLabor.PARAMETROS, labor.getParametros());
        return contentValues;
    }

    @Override
    public Labor cargarLaborxCodigo(String codigoLabor) {
        Labor labor = new Labor();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaLabor.CODIGOLABOR + " = '" + codigoLabor + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                labor = convertirCursorAObjeto(cursor);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return labor;
    }

    public static class ColumnaLabor {

        public static final String CODIGOLABOR = "codigolabor";
        public static final String NOMBRE = "nombre";
        public static final String RUTINA = "rutina";
        public static final String PARAMETROS = "parametros";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaLabor.CODIGOLABOR + " " + STRING_TYPE + " not null," +
                    ColumnaLabor.NOMBRE + " " + STRING_TYPE + " not null," +
                    ColumnaLabor.RUTINA + " " + STRING_TYPE + " not null," +
                    ColumnaLabor.PARAMETROS + " " + STRING_TYPE + " null," +
                    " primary key (" + ColumnaLabor.CODIGOLABOR + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}