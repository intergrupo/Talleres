package com.example.santiagolopezgarcia.talleres.data.dao.notificacion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.modelonegocio.Notificacion;
import com.example.dominio.notificacion.NotificacionRepositorio;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class NotificacionDao extends DaoBase implements NotificacionRepositorio {

    static final String NombreTabla = "sirius_notificacion";

    public NotificacionDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }


    @Override
    public List<Notificacion> cargar() {
        List<Notificacion> lista = new ArrayList<>();
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
    public Notificacion cargarNotificacion(String codigoNotificacion) {
        Notificacion notificacion = new Notificacion();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                notificacion = convertirCursorAObjeto(cursor);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return notificacion;
    }


    private Notificacion convertirCursorAObjeto(Cursor cursor) {
        Notificacion notificacion = new Notificacion();
        notificacion.setCodigoNotificacion(cursor.getString(cursor.getColumnIndex(ColumnaNotificacion.CODIGONOTIFICACION)));
        notificacion.setDescripcion(cursor.getString(cursor.getColumnIndex(ColumnaNotificacion.DESCRIPCION)));
        notificacion.setRutina(cursor.getString(cursor.getColumnIndex(ColumnaNotificacion.RUTINA)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaNotificacion.PARAMETROS))) {
            notificacion.setParametros(cursor.getString(cursor.getColumnIndex(ColumnaNotificacion.PARAMETROS)));
        }
        return notificacion;
    }

    private ContentValues convertirObjetoAContentValues(Notificacion notificacion) {
        ContentValues contentValues = new ContentValues();
        if (!notificacion.getCodigoNotificacion().isEmpty()) {
            contentValues.put(ColumnaNotificacion.CODIGONOTIFICACION, notificacion.getCodigoNotificacion());
        }
        if (!notificacion.getDescripcion().isEmpty()) {
            contentValues.put(ColumnaNotificacion.DESCRIPCION, notificacion.getDescripcion());
        }
        if (!notificacion.getRutina().isEmpty()) {
            contentValues.put(ColumnaNotificacion.RUTINA, notificacion.getRutina());
        }
        contentValues.put(ColumnaNotificacion.PARAMETROS, notificacion.getParametros());
        return contentValues;
    }

    @Override
    public boolean guardar(List<Notificacion> lista) throws ParseException {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (Notificacion notificacion : lista) {
            listaContentValues.add(convertirObjetoAContentValues(notificacion));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(Notificacion dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    @Override
    public boolean actualizar(Notificacion dato) {
        ContentValues datos = convertirObjetoAContentValues(dato);
        return this.operadorDatos.actualizar(datos, ColumnaNotificacion.CODIGONOTIFICACION + " = ? "
                , new String[]{dato.getCodigoNotificacion()});
    }

    @Override
    public boolean eliminar(Notificacion dato) {
        return this.operadorDatos.borrar(ColumnaNotificacion.CODIGONOTIFICACION + " = ? "
                , new String[]{dato.getCodigoNotificacion()}) > 0;
    }

    public static class ColumnaNotificacion {

        public static final String CODIGONOTIFICACION = "codigonotificacion";
        public static final String DESCRIPCION = "descripcion";
        public static final String RUTINA = "rutina";
        public static final String PARAMETROS = "parametros";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaNotificacion.CODIGONOTIFICACION + " " + STRING_TYPE + " not null," +
                    ColumnaNotificacion.DESCRIPCION + " " + STRING_TYPE + " null," +
                    ColumnaNotificacion.RUTINA + " " + STRING_TYPE + " not null," +
                    ColumnaNotificacion.PARAMETROS + " " + STRING_TYPE + " null," +
                    " primary key (" + ColumnaNotificacion.CODIGONOTIFICACION + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;


}