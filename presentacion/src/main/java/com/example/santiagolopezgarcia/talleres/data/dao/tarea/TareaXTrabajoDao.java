package com.example.santiagolopezgarcia.talleres.data.dao.tarea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.modelonegocio.ListaTareaXTrabajo;
import com.example.dominio.modelonegocio.TareaXTrabajo;
import com.example.dominio.bussinesslogic.tarea.TareaXTrabajoRepositorio;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;
import com.example.santiagolopezgarcia.talleres.data.dao.trabajo.TrabajoDao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class TareaXTrabajoDao extends DaoBase implements TareaXTrabajoRepositorio {

    public static final String NombreTabla = "sirius_tareaxtrabajo";

    public TareaXTrabajoDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public ListaTareaXTrabajo cargarTareasXTrabajo(String codigoTrabajo) {
        ListaTareaXTrabajo lista = new ListaTareaXTrabajo();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " stxt JOIN sirius_tarea st ON st." + ColumnaTareaXTrabajo.CODIGOTAREA
                + " = stxt." + ColumnaTareaXTrabajo.CODIGOTAREA + " WHERE stxt." +
                ColumnaTareaXTrabajo.CODIGOTRABAJO + " = '" + codigoTrabajo + "' AND st." +
                TareaDao.ColumnaTarea.PARAMETROS + " <> 'M' ORDER BY st." + TareaDao.ColumnaTarea.NOMBRE);
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
    public TareaXTrabajo cargarTareaXTrabajo(String codigoTrabajo, String codigoTarea) {
        TareaXTrabajo tareaXTrabajo = new TareaXTrabajo();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaTareaXTrabajo.CODIGOTRABAJO + " = '" + codigoTrabajo + "' " +
                "AND " + ColumnaTareaXTrabajo.CODIGOTAREA + " = '" + codigoTarea + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                tareaXTrabajo = convertirCursorAObjeto(cursor);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return tareaXTrabajo;
    }

    @Override
    public ListaTareaXTrabajo cargarTareaXTrabajoXCodigoTarea(String codigoTarea) {
        ListaTareaXTrabajo listaTareaXTrabajo = new ListaTareaXTrabajo();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaTareaXTrabajo.CODIGOTAREA + " = '" + codigoTarea + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listaTareaXTrabajo.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listaTareaXTrabajo;
    }

    @Override
    public ListaTareaXTrabajo cargarTareasXTrabajoConCodigosDeTareas(String codigoTrabajo, List<String> listaCodigoTareas) {
        ListaTareaXTrabajo lista = new ListaTareaXTrabajo();
        StringBuilder codigostareas = new StringBuilder();
        for (int i = 0; i < listaCodigoTareas.size(); i++) {
            if (i == listaCodigoTareas.size() - 1)
                codigostareas.append("'" + listaCodigoTareas.get(i).trim() + "'");
            else
                codigostareas.append("'" + listaCodigoTareas.get(i).trim() + "',");
        }
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " " +
                "stxt JOIN sirius_tarea st ON st." + ColumnaTareaXTrabajo.CODIGOTAREA + " = stxt." +
                ColumnaTareaXTrabajo.CODIGOTAREA + "  WHERE stxt." +
                ColumnaTareaXTrabajo.CODIGOTRABAJO + " = '" + codigoTrabajo + "' AND stxt." +
                ColumnaTareaXTrabajo.CODIGOTAREA + " NOT IN(" + codigostareas + ") AND st." +
                TareaDao.ColumnaTarea.PARAMETROS + " <> 'M' ORDER BY st." + TareaDao.ColumnaTarea.NOMBRE);
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
    public boolean guardar(List<TareaXTrabajo> lista) throws ParseException {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (TareaXTrabajo tareaxTrabajo : lista) {
            listaContentValues.add(convertirObjetoAContentValues(tareaxTrabajo));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(TareaXTrabajo dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    @Override
    public boolean actualizar(TareaXTrabajo dato) {
        ContentValues datos = convertirObjetoAContentValues(dato);
        return this.operadorDatos.actualizar(datos, ColumnaTareaXTrabajo.CODIGOTAREA + " = ? " +
                        "AND " + ColumnaTareaXTrabajo.CODIGOTRABAJO + " = ? "
                , new String[]{dato.getTarea().getCodigoTarea(), dato.getTrabajo().getCodigoTrabajo()});
    }

    @Override
    public boolean eliminar(TareaXTrabajo dato) {
        return this.operadorDatos.borrar(ColumnaTareaXTrabajo.CODIGOTAREA + " = ? " +
                        "AND " + ColumnaTareaXTrabajo.CODIGOTRABAJO + " = ? "
                , new String[]{dato.getTarea().getCodigoTarea(), dato.getTrabajo().getCodigoTrabajo()}) > 0;
    }

    private TareaXTrabajo convertirCursorAObjeto(Cursor cursor) {
        TareaXTrabajo tareaXTrabajo = new TareaXTrabajo();
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaTareaXTrabajo.CODIGOTAREA))) {
            tareaXTrabajo.getTarea().setCodigoTarea(cursor.getString(cursor.getColumnIndex(ColumnaTareaXTrabajo.CODIGOTAREA)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaTareaXTrabajo.CODIGOTRABAJO))) {
            tareaXTrabajo.getTrabajo().setCodigoTrabajo(cursor.getString(cursor.getColumnIndex(ColumnaTareaXTrabajo.CODIGOTRABAJO)));
        }
        return tareaXTrabajo;
    }

    private ContentValues convertirObjetoAContentValues(TareaXTrabajo tareaXTrabajo) {
        ContentValues contentValues = new ContentValues();
        if (!tareaXTrabajo.getTarea().getCodigoTarea().isEmpty()) {
            contentValues.put(ColumnaTareaXTrabajo.CODIGOTAREA, tareaXTrabajo.getTarea().getCodigoTarea());
        }
        if (!tareaXTrabajo.getTrabajo().getCodigoTrabajo().isEmpty()) {
            contentValues.put(ColumnaTareaXTrabajo.CODIGOTRABAJO, tareaXTrabajo.getTrabajo().getCodigoTrabajo());
        }
        return contentValues;
    }

    public static class ColumnaTareaXTrabajo {

        public static final String CODIGOTAREA = "codigotarea";
        public static final String CODIGOTRABAJO = "codigotrabajo";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaTareaXTrabajo.CODIGOTAREA + " " + STRING_TYPE + " not null," +
                    ColumnaTareaXTrabajo.CODIGOTRABAJO + " " + STRING_TYPE + " not null," +
                    " primary key (" + ColumnaTareaXTrabajo.CODIGOTAREA + "," + ColumnaTareaXTrabajo.CODIGOTRABAJO + ")" +
                    " FOREIGN KEY (" + ColumnaTareaXTrabajo.CODIGOTAREA + ") REFERENCES " +
                    TareaDao.NombreTabla + "( " + TareaDao.ColumnaTarea.CODIGOTAREA + "), " +
                    " FOREIGN KEY (" + ColumnaTareaXTrabajo.CODIGOTRABAJO + ") REFERENCES " +
                    TrabajoDao.NombreTabla + "( " + TrabajoDao.ColumnaTrabajo.CODIGOTRABAJO + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}