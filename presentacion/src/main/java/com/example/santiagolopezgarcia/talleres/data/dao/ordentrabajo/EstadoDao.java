package com.example.santiagolopezgarcia.talleres.data.dao.ordentrabajo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.modelonegocio.Estado;
import com.example.dominio.ordentrabajo.EstadoRepositorio;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class EstadoDao extends DaoBase implements EstadoRepositorio {
    static final String NombreTabla = "sirius_estado";

    public EstadoDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public boolean guardar(List<Estado> lista) {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (Estado estado : lista) {
            listaContentValues.add(convertirObjetoAContentValues(estado));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(Estado dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    @Override
    public boolean actualizar(Estado dato) {
        ContentValues datos = convertirObjetoAContentValues(dato);
        return this.operadorDatos.actualizar(datos, ColumnaEstado.CODIGOESTADO + " = ? "
                , new String[]{dato.getCodigoEstado()});
    }

    @Override
    public boolean eliminar(Estado dato) {
        return this.operadorDatos.borrar(ColumnaEstado.CODIGOESTADO + " = ? "
                , new String[]{dato.getCodigoEstado()}) > 0;
    }

    private Estado convertirCursorAObjeto(Cursor cursor) {
        Estado estado = new Estado();
        estado.setCodigoEstado(cursor.getString(cursor.getColumnIndex(ColumnaEstado.CODIGOESTADO)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaEstado.DESCRIPCION))) {
            estado.setDescripcion(cursor.getString(cursor.getColumnIndex(ColumnaEstado.DESCRIPCION)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaEstado.GRUPOESTADO))) {
            estado.setGrupoEstado(Estado.GrupoEstado.getEstado(cursor.getString(cursor.getColumnIndex(ColumnaEstado.GRUPOESTADO))));
        }
        return estado;
    }

    private ContentValues convertirObjetoAContentValues(Estado estado) {
        ContentValues contentValues = new ContentValues();
        if (!estado.getCodigoEstado().isEmpty()) {
            contentValues.put(ColumnaEstado.CODIGOESTADO, estado.getCodigoEstado());
        }
        contentValues.put(ColumnaEstado.DESCRIPCION, estado.getDescripcion());
        contentValues.put(ColumnaEstado.GRUPOESTADO, estado.getGrupoEstado().getCodigo());
        return contentValues;
    }

    @Override
    public Estado cargarEstado(String codigoEstado) {
        Estado estado = new Estado();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaEstado.CODIGOESTADO + " = '" + codigoEstado + "'");
        if (cursor.moveToFirst()) {
            estado = convertirCursorAObjeto(cursor);
        }
        cursor.close();
        return estado;
    }

    @Override
    public List<Estado> cargarEstadoXGrupo(String codigoGrupo) {
        List<Estado> listaEstado = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaEstado.GRUPOESTADO + " = '" + codigoGrupo + "' " +
                "ORDER BY " + ColumnaEstado.DESCRIPCION);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listaEstado.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listaEstado;
    }

    @Override
    public List<Estado> cargarEstadoTareas() {
        List<Estado> listaEstado = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaEstado.GRUPOESTADO + " IN ('" + Estado.GrupoEstado.TAREASREVISIONES.getCodigo()
                + "', '" + Estado.GrupoEstado.TAREASLECTURA.getCodigo() + "') " +
                "ORDER BY " + ColumnaEstado.DESCRIPCION);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listaEstado.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listaEstado;
    }

    @Override
    public List<Estado> cargarEstadoTareasRevisiones() {
        List<Estado> listaEstado = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaEstado.GRUPOESTADO + " = '" + Estado.GrupoEstado.TAREASREVISIONES.getCodigo() + "' " +
                " ORDER BY " + ColumnaEstado.DESCRIPCION);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listaEstado.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listaEstado;
    }

    @Override
    public List<Estado> cargarEstadoTareasLectura() {
        List<Estado> listaEstado = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaEstado.GRUPOESTADO + " = '" + Estado.GrupoEstado.TAREASLECTURA.getCodigo() + "' " +
                "ORDER BY " + ColumnaEstado.DESCRIPCION);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listaEstado.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listaEstado;
    }

    public static class ColumnaEstado {

        public static final String CODIGOESTADO = "codigoestado";
        public static final String DESCRIPCION = "descripcion";
        public static final String GRUPOESTADO = "grupoestado";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaEstado.CODIGOESTADO + " " + STRING_TYPE + " not null," +
                    ColumnaEstado.DESCRIPCION + " " + STRING_TYPE + " null," +
                    ColumnaEstado.GRUPOESTADO + " " + STRING_TYPE + " null," +
                    " primary key (" + ColumnaEstado.CODIGOESTADO + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}