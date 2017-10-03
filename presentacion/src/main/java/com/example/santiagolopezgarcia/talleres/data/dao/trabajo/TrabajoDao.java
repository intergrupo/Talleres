package com.example.santiagolopezgarcia.talleres.data.dao.trabajo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.modelonegocio.ListaTrabajo;
import com.example.dominio.modelonegocio.Trabajo;
import com.example.dominio.bussinesslogic.trabajo.TrabajoRepositorio;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class TrabajoDao extends DaoBase implements TrabajoRepositorio {
    public static final String NombreTabla = "sirius_trabajo";

    public TrabajoDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public boolean guardar(List<Trabajo> lista) {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (Trabajo trabajo : lista) {
            listaContentValues.add(convertirObjetoAContentValues(trabajo));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(Trabajo dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    @Override
    public boolean actualizar(Trabajo dato) {
        ContentValues datos = convertirObjetoAContentValues(dato);
        return this.operadorDatos.actualizar(datos, ColumnaTrabajo.CODIGOTRABAJO + " = ? "
                , new String[]{dato.getCodigoTrabajo()});
    }

    @Override
    public boolean eliminar(Trabajo dato) {
        return this.operadorDatos.borrar(ColumnaTrabajo.CODIGOTRABAJO + " = ? "
                , new String[]{dato.getCodigoTrabajo()}) > 0;
    }

    private Trabajo convertirCursorAObjeto(Cursor cursor) {
        Trabajo contrato = new Trabajo();
        contrato.setCodigoTrabajo(cursor.getString(cursor.getColumnIndex(ColumnaTrabajo.CODIGOTRABAJO)));
        contrato.setNombre(cursor.getString(cursor.getColumnIndex(ColumnaTrabajo.NOMBRE)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaTrabajo.PARAMETROS))) {
            contrato.setParametros(cursor.getString(cursor.getColumnIndex(ColumnaTrabajo.PARAMETROS)));
        }
        return contrato;
    }

    private ContentValues convertirObjetoAContentValues(Trabajo contrato) {
        ContentValues contentValues = new ContentValues();
        if (!contrato.getCodigoTrabajo().isEmpty()) {
            contentValues.put(ColumnaTrabajo.CODIGOTRABAJO, contrato.getCodigoTrabajo());
        }
        if (!contrato.getNombre().isEmpty()) {
            contentValues.put(ColumnaTrabajo.NOMBRE, contrato.getNombre());
        }
        contentValues.put(ColumnaTrabajo.PARAMETROS, contrato.getParametros());
        return contentValues;
    }

    @Override
    public Trabajo cargarTrabajo(String codigoTrabajo) {
        Trabajo trabajo = new Trabajo();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaTrabajo.CODIGOTRABAJO + " = '" + codigoTrabajo + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                trabajo = convertirCursorAObjeto(cursor);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return trabajo;
    }

    @Override
    public ListaTrabajo cargarTrabajos(List<String> listaCodigoTrabajos) {
        ListaTrabajo listaTrabajos = new ListaTrabajo();
        StringBuilder commaSepValueBuilder = new StringBuilder();
        for (int pocision = 0; pocision < listaCodigoTrabajos.size(); pocision++) {
            commaSepValueBuilder.append("'" + listaCodigoTrabajos.get(pocision) + "'");
            if (pocision != listaCodigoTrabajos.size() - 1) {
                commaSepValueBuilder.append(",");
            }
        }
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE "
                + ColumnaTrabajo.CODIGOTRABAJO + " IN(" + commaSepValueBuilder.toString() + ")"
                + " ORDER BY " + ColumnaTrabajo.NOMBRE);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listaTrabajos.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listaTrabajos;
    }

    @Override
    public ListaTrabajo cargarTrabajos() {
        ListaTrabajo listaTrabajos = new ListaTrabajo();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " ORDER BY " + ColumnaTrabajo.NOMBRE);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listaTrabajos.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listaTrabajos;
    }


    public static class ColumnaTrabajo {

        public static final String CODIGOTRABAJO = "codigotrabajo";
        public static final String NOMBRE = "nombre";
        public static final String PARAMETROS = "parametros";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaTrabajo.CODIGOTRABAJO + " " + STRING_TYPE + " not null," +
                    ColumnaTrabajo.NOMBRE + " " + STRING_TYPE + " not null," +
                    ColumnaTrabajo.PARAMETROS + " " + STRING_TYPE + " null," +
                    " primary key (" + ColumnaTrabajo.CODIGOTRABAJO + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}
