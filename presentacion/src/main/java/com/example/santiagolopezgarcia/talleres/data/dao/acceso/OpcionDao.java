package com.example.santiagolopezgarcia.talleres.data.dao.acceso;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.administracion.OpcionRepositorio;
import com.example.dominio.modelonegocio.Opcion;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class OpcionDao extends DaoBase implements OpcionRepositorio {

    static final String NombreTabla = "sirius_opcion";

    public OpcionDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public Opcion cargarOpcionPorCodigo(String codigoOpcion) {
        Opcion opcion = new Opcion();
        Cursor cursorPerfil = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaOpcion.CODIGOOPCION
                + " = '" + codigoOpcion + "'");
        if (cursorPerfil.moveToFirst()) {
            opcion = convertirCursorAObjeto(cursorPerfil);
        }
        cursorPerfil.close();
        return opcion;
    }

    private Opcion convertirCursorAObjeto(Cursor cursor) {
        Opcion opcion = new Opcion();
        opcion.setCodigoOpcion(cursor.getString(cursor.getColumnIndex(ColumnaOpcion.CODIGOOPCION)));
        opcion.setDescripcion(cursor.getString(cursor.getColumnIndex(ColumnaOpcion.DESCRIPCION)));
        opcion.setRutina(cursor.getString(cursor.getColumnIndex(ColumnaOpcion.RUTINA)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaOpcion.PARAMETROS))) {
            opcion.setParametros(cursor.getString(cursor.getColumnIndex(ColumnaOpcion.PARAMETROS)));
        }
        opcion.setMenu(cursor.getString(cursor.getColumnIndex(ColumnaOpcion.MENU)));
        return opcion;
    }

    @Override
    public boolean guardar(List<Opcion> lista) throws ParseException {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (Opcion opcion : lista) {
            listaContentValues.add(convertirObjetoAContentValues(opcion));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(Opcion dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    @Override
    public boolean actualizar(Opcion dato) {
        ContentValues datos = convertirObjetoAContentValues(dato);
        return this.operadorDatos.actualizar(datos, ColumnaOpcion.CODIGOOPCION + " = ?"
                , new String[]{dato.getCodigoOpcion()});
    }

    @Override
    public boolean eliminar(Opcion dato) {
        return this.operadorDatos.borrar(ColumnaOpcion.CODIGOOPCION + " = ? "
                , new String[]{dato.getCodigoOpcion()}) > 0;
    }

    private ContentValues convertirObjetoAContentValues(Opcion opcion) {
        ContentValues contentValues = new ContentValues();
        if (!opcion.getCodigoOpcion().isEmpty()) {
            contentValues.put(ColumnaOpcion.CODIGOOPCION, opcion.getCodigoOpcion());
        }
        if (!opcion.getDescripcion().isEmpty()) {
            contentValues.put(ColumnaOpcion.DESCRIPCION, opcion.getDescripcion());
        }
        if (!opcion.getRutina().isEmpty()) {
            contentValues.put(ColumnaOpcion.RUTINA, opcion.getRutina());
        }
        contentValues.put(ColumnaOpcion.PARAMETROS, opcion.getParametros());
        if (!opcion.getMenu().isEmpty()) {
            contentValues.put(ColumnaOpcion.MENU, opcion.getMenu());
        }
        return contentValues;
    }


    public static class ColumnaOpcion {

        public static final String CODIGOOPCION = "codigoopcion";
        public static final String DESCRIPCION = "descripcion";
        public static final String RUTINA = "rutina";
        public static final String PARAMETROS = "parametros";
        public static final String MENU = "menu";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaOpcion.CODIGOOPCION + " " + STRING_TYPE + " not null," +
                    ColumnaOpcion.DESCRIPCION + " " + STRING_TYPE + " not null," +
                    ColumnaOpcion.RUTINA + " " + STRING_TYPE + " not null," +
                    ColumnaOpcion.PARAMETROS + " " + STRING_TYPE + " null," +
                    ColumnaOpcion.MENU + " " + STRING_TYPE + " not null," +
                    " primary key (" + ColumnaOpcion.CODIGOOPCION + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}