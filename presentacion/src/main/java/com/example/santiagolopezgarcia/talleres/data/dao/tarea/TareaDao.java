package com.example.santiagolopezgarcia.talleres.data.dao.tarea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.modelonegocio.Tarea;
import com.example.dominio.tarea.TareaRepositorio;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.example.santiagolopezgarcia.talleres.data.dao.DaoBase.STRING_TYPE;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class TareaDao extends DaoBase implements TareaRepositorio {

    public static final String NombreTabla = "sirius_tarea";

    public TareaDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public List<Tarea> cargarTareas() {
        List<Tarea> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                lista.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    @Override
    public boolean guardar(List<Tarea> lista) {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (Tarea tarea : lista) {
            listaContentValues.add(convertirObjetoAContentValues(tarea));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(Tarea dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    @Override
    public boolean actualizar(Tarea dato) {
        ContentValues datos = convertirObjetoAContentValues(dato);
        return this.operadorDatos.actualizar(datos, ColumnaTarea.CODIGOTAREA + " = ? "
                , new String[]{dato.getCodigoTarea()});
    }

    @Override
    public boolean eliminar(Tarea dato) {
        return this.operadorDatos.borrar(ColumnaTarea.CODIGOTAREA + " = ? "
                , new String[]{dato.getCodigoTarea()}) > 0;
    }

    private Tarea convertirCursorAObjeto(Cursor cursor) {
        Tarea tarea = new Tarea();
        tarea.setCodigoTarea(cursor.getString(cursor.getColumnIndex(ColumnaTarea.CODIGOTAREA)));
        tarea.setNombre(cursor.getString(cursor.getColumnIndex(ColumnaTarea.NOMBRE)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaTarea.CODIGOIMPRESION))) {
            tarea.setCodigoImpresion(cursor.getString(cursor.getColumnIndex(ColumnaTarea.CODIGOIMPRESION)));
        }
        tarea.setRutina(cursor.getString(cursor.getColumnIndex(ColumnaTarea.RUTINA)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaTarea.PARAMETROS))) {
            tarea.setParametros(cursor.getString(cursor.getColumnIndex(ColumnaTarea.PARAMETROS)));
        }
        return tarea;
    }

    private ContentValues convertirObjetoAContentValues(Tarea tarea) {
        ContentValues contentValues = new ContentValues();
        if (!tarea.getCodigoTarea().isEmpty()) {
            contentValues.put(ColumnaTarea.CODIGOTAREA, tarea.getCodigoTarea());
        }
        contentValues.put(ColumnaTarea.NOMBRE, tarea.getNombre());
        contentValues.put(ColumnaTarea.CODIGOIMPRESION, tarea.getCodigoImpresion());
        if (!tarea.getRutina().isEmpty()) {
            contentValues.put(ColumnaTarea.RUTINA, tarea.getRutina());
        }
        contentValues.put(ColumnaTarea.PARAMETROS, tarea.getParametros());
        return contentValues;
    }

    @Override
    public Tarea cargarTareaxCodigo(String codigoTarea) {
        Tarea tarea = new Tarea();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaTarea.CODIGOTAREA + " = '" + codigoTarea + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                tarea = convertirCursorAObjeto(cursor);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return tarea;
    }

    public static class ColumnaTarea {

        public static final String CODIGOTAREA = "codigotarea";
        public static final String NOMBRE = "nombre";
        public static final String CODIGOIMPRESION = "codigoimpresion";
        public static final String RUTINA = "rutina";
        public static final String PARAMETROS = "parametros";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaTarea.CODIGOTAREA + " " + STRING_TYPE + " not null," +
                    ColumnaTarea.NOMBRE + " " + STRING_TYPE + " not null," +
                    ColumnaTarea.CODIGOIMPRESION + " " + STRING_TYPE + " null," +
                    ColumnaTarea.RUTINA + " " + STRING_TYPE + " not null," +
                    ColumnaTarea.PARAMETROS + " " + STRING_TYPE + " null," +
                    " primary key (" + ColumnaTarea.CODIGOTAREA + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}
