package com.example.santiagolopezgarcia.talleres.data.dao.impresion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.bussinesslogic.impresion.SeccionImpresionRepositorio;
import com.example.dominio.modelonegocio.SeccionImpresion;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class SeccionImpresionDao extends DaoBase implements SeccionImpresionRepositorio {

    public static final String NombreTabla = "sirius_seccionimpresion";

    public SeccionImpresionDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public boolean guardar(List<SeccionImpresion> lista) throws ParseException {

        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (SeccionImpresion seccionimpresion : lista) {
            listaContentValues.add(convertirObjetoAContentValues(seccionimpresion));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(SeccionImpresion dato) throws ParseException {
        ContentValues datos = convertirObjetoAContentValues(dato);
        this.operadorDatos.insertar(datos);
        return true;
    }

    @Override
    public boolean actualizar(SeccionImpresion dato) {
        ContentValues datos = convertirObjetoAContentValues(dato);
        return this.operadorDatos.actualizar(datos, ColumnaSeccionImpresion.CODIGOIMPRESION + " = ? AND " +
                        ColumnaSeccionImpresion.CODIGOSECCION + " = ? "
                , new String[]{dato.getCodigoImpresion(), dato.getCodigoSeccion()});
    }

    @Override
    public boolean eliminar(SeccionImpresion dato) {
        return this.operadorDatos.borrar(ColumnaSeccionImpresion.CODIGOIMPRESION + " = ? AND " +
                        ColumnaSeccionImpresion.CODIGOSECCION + " = ? "
                , new String[]{dato.getCodigoImpresion(), dato.getCodigoSeccion()}) > 0;
    }

    @Override
    public List<SeccionImpresion> cargar(String codigoImpresion) {
        List<SeccionImpresion> listaSeccionImpresion = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaSeccionImpresion.CODIGOIMPRESION +
                " = '" + codigoImpresion + "' ORDER BY " + ColumnaSeccionImpresion.CODIGOSECCION);
        /*if (cursor.moveToFirst()) {
            if (!cursor.isAfterLast()) {
                listaSeccionImpresion.add(convertirCursorAObjeto(cursor));
            }
        }*/
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listaSeccionImpresion.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listaSeccionImpresion;
    }

    @Override
    public SeccionImpresion cargar(String codigoImpresion, String codigoSeccion) {
        SeccionImpresion seccionImpresion = new SeccionImpresion();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaSeccionImpresion.CODIGOIMPRESION +
                " = '" + codigoImpresion + "' " +
                "AND " + ColumnaSeccionImpresion.CODIGOSECCION + " = '" + codigoSeccion + "'");
        if (cursor.moveToFirst()) {
            if (!cursor.isAfterLast()) {
                seccionImpresion = convertirCursorAObjeto(cursor);
            }
        }
        cursor.close();
        return seccionImpresion;
    }

    private ContentValues convertirObjetoAContentValues(SeccionImpresion seccionImpresion) {
        ContentValues contentValues = new ContentValues();

        if (!seccionImpresion.getCodigoImpresion().isEmpty()) {
            contentValues.put(ColumnaSeccionImpresion.CODIGOIMPRESION, seccionImpresion.getCodigoImpresion());
        }

        if (!seccionImpresion.getCodigoSeccion().isEmpty()) {
            contentValues.put(ColumnaSeccionImpresion.CODIGOSECCION, seccionImpresion.getCodigoSeccion());
        }

        contentValues.put(ColumnaSeccionImpresion.DESCRIPCION, seccionImpresion.getDescripcion());

        if (!seccionImpresion.getRutina().isEmpty()) {
            contentValues.put(ColumnaSeccionImpresion.RUTINA, seccionImpresion.getRutina());
        }

        contentValues.put(ColumnaSeccionImpresion.PARAMETROS,seccionImpresion.getParametros());

        return contentValues;
    }

    private SeccionImpresion convertirCursorAObjeto(Cursor cursor) {
        SeccionImpresion seccionImpresion = new SeccionImpresion();

        if (!cursor.isNull(cursor.getColumnIndex(ColumnaSeccionImpresion.CODIGOIMPRESION))) {
            seccionImpresion.setCodigoImpresion(cursor.getString(cursor.getColumnIndex(ColumnaSeccionImpresion.CODIGOIMPRESION)));
        }

        if (!cursor.isNull(cursor.getColumnIndex(ColumnaSeccionImpresion.CODIGOSECCION))) {
            seccionImpresion.setCodigoSeccion(cursor.getString(cursor.getColumnIndex(ColumnaSeccionImpresion.CODIGOSECCION)));
        }

        seccionImpresion.setDescripcion(cursor.getString(cursor.getColumnIndex(ColumnaSeccionImpresion.DESCRIPCION)));

        if (!cursor.isNull(cursor.getColumnIndex(ColumnaSeccionImpresion.RUTINA))) {
            seccionImpresion.setRutina(cursor.getString(cursor.getColumnIndex(ColumnaSeccionImpresion.RUTINA)));
        }

        seccionImpresion.setParametros(cursor.getString(cursor.getColumnIndex(ColumnaSeccionImpresion.PARAMETROS)));

        return seccionImpresion;
    }

    public static class ColumnaSeccionImpresion {

        public static final String CODIGOIMPRESION = "codigoimpresion";
        public static final String CODIGOSECCION = "codigoseccion";
        public static final String DESCRIPCION = "descripcion";
        public static final String RUTINA = "rutina";
        public static final String PARAMETROS = "parametros";

    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaSeccionImpresion.CODIGOIMPRESION + " " + STRING_TYPE + " not null," +
                    ColumnaSeccionImpresion.CODIGOSECCION + " " + STRING_TYPE + " not null," +
                    ColumnaSeccionImpresion.DESCRIPCION + " " + STRING_TYPE + " null," +
                    ColumnaSeccionImpresion.RUTINA + " " + STRING_TYPE + " not null," +
                    ColumnaSeccionImpresion.PARAMETROS + " " + STRING_TYPE + " null," +
                    " primary key (" + ColumnaSeccionImpresion.CODIGOIMPRESION + "," + ColumnaSeccionImpresion.CODIGOSECCION + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}