package com.example.santiagolopezgarcia.talleres.data.dao.trabajo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.modelonegocio.Trabajo;
import com.example.dominio.modelonegocio.TrabajoXOrdenTrabajo;
import com.example.dominio.bussinesslogic.trabajo.TrabajoXOrdenTrabajoRepositorio;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;
import com.example.santiagolopezgarcia.talleres.data.dao.ordentrabajo.OrdenTrabajoDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class TrabajoXOrdenTrabajoDao extends DaoBase implements TrabajoXOrdenTrabajoRepositorio {

    public static final String NombreTabla = "sirius_trabajoxordentrabajo";

    public TrabajoXOrdenTrabajoDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public boolean guardar(List<TrabajoXOrdenTrabajo> lista) {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (TrabajoXOrdenTrabajo trabajoXOrdenTrabajo : lista) {
            listaContentValues.add(convertirObjetoAContentValues(trabajoXOrdenTrabajo));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    private TrabajoXOrdenTrabajo convertirCursorAObjeto(Cursor cursor) {
        TrabajoXOrdenTrabajo trabajoXOrdenTrabajo = new TrabajoXOrdenTrabajo();
        trabajoXOrdenTrabajo.setCodigoCorreria(cursor.getString(cursor.getColumnIndex(ColumnaTrabajoXOrdenTrabajo.CODIGOCORRERIA)));
        trabajoXOrdenTrabajo.setCodigoOrdenTrabajo(cursor.getString(cursor.getColumnIndex(ColumnaTrabajoXOrdenTrabajo.CODIGOORDENTRABAJO)));
        trabajoXOrdenTrabajo.getTrabajo().setCodigoTrabajo(cursor.getString(cursor.getColumnIndex(ColumnaTrabajoXOrdenTrabajo.CODIGOTRABAJO)));
        return trabajoXOrdenTrabajo;
    }

    private ContentValues convertirObjetoAContentValues(TrabajoXOrdenTrabajo trabajoXOrdenTrabajo) {
        ContentValues contentValues = new ContentValues();
        if (!trabajoXOrdenTrabajo.getCodigoCorreria().isEmpty()) {
            contentValues.put(ColumnaTrabajoXOrdenTrabajo.CODIGOCORRERIA, trabajoXOrdenTrabajo.getCodigoCorreria());
        }
        if (!trabajoXOrdenTrabajo.getCodigoOrdenTrabajo().isEmpty()) {
            contentValues.put(ColumnaTrabajoXOrdenTrabajo.CODIGOORDENTRABAJO, trabajoXOrdenTrabajo.getCodigoOrdenTrabajo());
        }
        if (!trabajoXOrdenTrabajo.getTrabajo().getCodigoTrabajo().isEmpty()) {
            contentValues.put(ColumnaTrabajoXOrdenTrabajo.CODIGOTRABAJO, trabajoXOrdenTrabajo.getTrabajo().getCodigoTrabajo());
        }
        return contentValues;
    }

    @Override
    public TrabajoXOrdenTrabajo cargarTrabajosXOrdenTrabajo(String codigoCorreria, String codigoOrdenTrabajo, String codigoTrabajo) {
        TrabajoXOrdenTrabajo trabajoXOrdenTrabajo = new TrabajoXOrdenTrabajo();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE "
                + ColumnaTrabajoXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' AND "
                + ColumnaTrabajoXOrdenTrabajo.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "' AND "
                + ColumnaTrabajoXOrdenTrabajo.CODIGOTRABAJO + " = '" + codigoTrabajo + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                trabajoXOrdenTrabajo = convertirCursorAObjeto(cursor);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return trabajoXOrdenTrabajo;
    }

    @Override
    public List<TrabajoXOrdenTrabajo> cargarLista(String codigoCorreria, String codigoOrdenTrabajo) {
        List<TrabajoXOrdenTrabajo> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaTrabajoXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' AND " +
                ColumnaTrabajoXOrdenTrabajo.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "'");
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
    public List<TrabajoXOrdenTrabajo> cargarXCorreria(String codigoCorreria) {
        List<TrabajoXOrdenTrabajo> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaTrabajoXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "'");
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
    public List<TrabajoXOrdenTrabajo> cargarTrabajosXOrdenTrabajo(String codigoCorreria, String codigoOrdenTrabajo) {
        List<TrabajoXOrdenTrabajo> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaTrabajoXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' " +
                "AND " + ColumnaTrabajoXOrdenTrabajo.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "'");
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
    public List<String> cargarCodigoOrdenTrabajoXTrabajo(Trabajo trabajo) {
        List<String> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT " + ColumnaTrabajoXOrdenTrabajo.CODIGOORDENTRABAJO + " FROM " + NombreTabla +
                " WHERE " + ColumnaTrabajoXOrdenTrabajo.CODIGOTRABAJO + " = '" + trabajo.getCodigoTrabajo() + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                lista.add(cursor.getString(cursor.getColumnIndex(ColumnaTrabajoXOrdenTrabajo.CODIGOORDENTRABAJO)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    @Override
    public boolean guardar(TrabajoXOrdenTrabajo trabajoXOrdenTrabajo) {
        ContentValues contentValues = convertirObjetoAContentValues(trabajoXOrdenTrabajo);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    @Override
    public boolean actualizar(TrabajoXOrdenTrabajo trabajoXOrdenTrabajo) {
        ContentValues datos = convertirObjetoAContentValues(trabajoXOrdenTrabajo);
        return this.operadorDatos.actualizar(datos, "codigocorreria = ? AND codigoordentrabajo = ? " +
                        "AND codigotrabajo = ? "
                , new String[]{trabajoXOrdenTrabajo.getCodigoCorreria(), trabajoXOrdenTrabajo.getCodigoOrdenTrabajo(),
                        trabajoXOrdenTrabajo.getTrabajo().getCodigoTrabajo()});
    }

    @Override
    public boolean eliminar(TrabajoXOrdenTrabajo dato) {
        return this.operadorDatos.borrar("codigocorreria = ? AND codigoordentrabajo = ? " +
                        "AND codigotrabajo = ? "
                , new String[]{dato.getCodigoCorreria(), dato.getCodigoOrdenTrabajo(),
                        dato.getTrabajo().getCodigoTrabajo()}) > 0;
    }

    public static class ColumnaTrabajoXOrdenTrabajo {

        public static final String CODIGOCORRERIA = "codigocorreria";
        public static final String CODIGOORDENTRABAJO = "codigoordentrabajo";
        public static final String CODIGOTRABAJO = "codigotrabajo";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaTrabajoXOrdenTrabajo.CODIGOCORRERIA + " " + STRING_TYPE + " not null," +
                    ColumnaTrabajoXOrdenTrabajo.CODIGOORDENTRABAJO + " " + STRING_TYPE + " not null," +
                    ColumnaTrabajoXOrdenTrabajo.CODIGOTRABAJO + " " + STRING_TYPE + " not null," +
                    " primary key (" + ColumnaTrabajoXOrdenTrabajo.CODIGOCORRERIA + "," +
                    ColumnaTrabajoXOrdenTrabajo.CODIGOORDENTRABAJO + "," +
                    ColumnaTrabajoXOrdenTrabajo.CODIGOTRABAJO + ")" +
                    " FOREIGN KEY (" + ColumnaTrabajoXOrdenTrabajo.CODIGOTRABAJO + ") REFERENCES " +
                    TrabajoDao.NombreTabla + "( " + TrabajoDao.ColumnaTrabajo.CODIGOTRABAJO + ")," +
                    " FOREIGN KEY (" + ColumnaTrabajoXOrdenTrabajo.CODIGOCORRERIA + "," +
                    ColumnaTrabajoXOrdenTrabajo.CODIGOORDENTRABAJO + ") REFERENCES " +
                    OrdenTrabajoDao.NombreTabla + "( " + OrdenTrabajoDao.ColumnaOrdenTrabajo.CODIGOCORRERIA + "," +
                    OrdenTrabajoDao.ColumnaOrdenTrabajo.CODIGOORDENTRABAJO + ") ON DELETE CASCADE)";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}
