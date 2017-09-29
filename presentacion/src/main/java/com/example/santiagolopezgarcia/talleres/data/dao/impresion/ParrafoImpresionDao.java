package com.example.santiagolopezgarcia.talleres.data.dao.impresion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.impresion.ParrafoImpresionRepositorio;
import com.example.dominio.modelonegocio.ParrafoImpresion;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class ParrafoImpresionDao extends DaoBase implements ParrafoImpresionRepositorio {

    public static final String NombreTabla = "sirius_parrafoimpresion";

    public ParrafoImpresionDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public boolean guardar(List<ParrafoImpresion> lista) throws ParseException {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (ParrafoImpresion parrafoimpresion : lista) {
            listaContentValues.add(convertirObjetoAContentValues(parrafoimpresion));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(ParrafoImpresion dato) throws ParseException {
        ContentValues datos = convertirObjetoAContentValues(dato);
        this.operadorDatos.insertar(datos);
        return true;
    }

    @Override
    public boolean actualizar(ParrafoImpresion dato) {
        ContentValues datos = convertirObjetoAContentValues(dato);
        return this.operadorDatos.actualizar(datos, ColumnaParrafoImpresion.CODIGOIMPRESION + " = ? AND " +
                        ColumnaParrafoImpresion.CODIGOPARRAFO + " = ? "
                , new String[]{dato.getCodigoImpresion(), dato.getCodigoParrafo()});
    }

    @Override
    public boolean eliminar(ParrafoImpresion dato) {
        return this.operadorDatos.borrar(ColumnaParrafoImpresion.CODIGOIMPRESION + " = ? AND " +
                        ColumnaParrafoImpresion.CODIGOPARRAFO + " = ? "
                , new String[]{dato.getCodigoImpresion(), dato.getCodigoParrafo()}) > 0;
    }

    @Override
    public ParrafoImpresion cargar(String codigoImpresion, String codigoParrafo) {

        ParrafoImpresion parrafoImpresion = new ParrafoImpresion();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaParrafoImpresion.CODIGOIMPRESION +
                " = '" + codigoImpresion + "' "
                + "AND " + ColumnaParrafoImpresion.CODIGOPARRAFO + " = '" + codigoParrafo + "'");
        if (cursor.moveToFirst()) {
            if (!cursor.isAfterLast()) {
                parrafoImpresion = convertirCursorAObjeto(cursor);
            }
        }
        cursor.close();
        return parrafoImpresion;

    }

    @Override
    public List<ParrafoImpresion> cargar(String codigoImpresion) {

        List<ParrafoImpresion> listaParrafoImpresion = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaParrafoImpresion.CODIGOIMPRESION +
                " = '" + codigoImpresion + "'");
        if (cursor.moveToFirst()) {
            if (!cursor.isAfterLast()) {
                listaParrafoImpresion.add(convertirCursorAObjeto(cursor));
            }
        }
        cursor.close();
        return listaParrafoImpresion;

    }

    private ContentValues convertirObjetoAContentValues(ParrafoImpresion parrafoImpresion) {
        ContentValues contentValues = new ContentValues();

        if (!parrafoImpresion.getCodigoImpresion().isEmpty()) {
            contentValues.put(ColumnaParrafoImpresion.CODIGOIMPRESION, parrafoImpresion.getCodigoImpresion());
        }

        if (!parrafoImpresion.getCodigoParrafo().isEmpty()) {
            contentValues.put(ColumnaParrafoImpresion.CODIGOPARRAFO, parrafoImpresion.getCodigoParrafo());
        }

        contentValues.put(ColumnaParrafoImpresion.PARRAFO, parrafoImpresion.getParrafo());

        return contentValues;
    }

    private ParrafoImpresion convertirCursorAObjeto(Cursor cursor) {
        ParrafoImpresion parrafoImpresion = new ParrafoImpresion();

        if (!cursor.isNull(cursor.getColumnIndex(ColumnaParrafoImpresion.CODIGOIMPRESION))) {
            parrafoImpresion.setCodigoImpresion(cursor.getString(cursor.getColumnIndex(ColumnaParrafoImpresion.CODIGOIMPRESION)));
        }

        if (!cursor.isNull(cursor.getColumnIndex(ColumnaParrafoImpresion.CODIGOPARRAFO))) {
            parrafoImpresion.setCodigoParrafo(cursor.getString(cursor.getColumnIndex(ColumnaParrafoImpresion.CODIGOPARRAFO)));
        }

        parrafoImpresion.setParrafo(cursor.getString(cursor.getColumnIndex(ColumnaParrafoImpresion.PARRAFO)));
        return parrafoImpresion;
    }

    public static class ColumnaParrafoImpresion {
        public static final String CODIGOIMPRESION = "codigoimpresion";
        public static final String CODIGOPARRAFO = "codigoparrafo";
        public static final String PARRAFO = "parrafo";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaParrafoImpresion.CODIGOIMPRESION + " " + STRING_TYPE + " not null," +
                    ColumnaParrafoImpresion.CODIGOPARRAFO + " " + STRING_TYPE + " not null," +
                    ColumnaParrafoImpresion.PARRAFO + " " + STRING_TYPE + " null," +
                    " primary key (" + ColumnaParrafoImpresion.CODIGOIMPRESION + "," + ColumnaParrafoImpresion.CODIGOPARRAFO + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}