package com.example.santiagolopezgarcia.talleres.data.dao.acceso;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.acceso.MultiOpcionRepositorio;
import com.example.dominio.modelonegocio.ListaMultiOpcion;
import com.example.dominio.modelonegocio.MultiOpcion;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class MultiOpcionDao extends DaoBase implements MultiOpcionRepositorio {

    static final String NombreTabla = "sirius_multiopcion";

    public MultiOpcionDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public ListaMultiOpcion cargarListaMultiOpcionPorTipo(String codigoTipoOpcion) throws ParseException {
        ListaMultiOpcion lista = new ListaMultiOpcion();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaMultiOpcion.COODIGOTIPOOPCION + " = '" + codigoTipoOpcion + "' ORDER BY " + ColumnaMultiOpcion.DESCRIPCION);
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
    public ListaMultiOpcion cargar() throws ParseException {
        ListaMultiOpcion lista = new ListaMultiOpcion();
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
    public MultiOpcion cargarMultiOpcion(String codigoTipoOpcion, String codigoOpcion) {
        MultiOpcion multiOpcion = new MultiOpcion();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaMultiOpcion.COODIGOTIPOOPCION + " = '" + codigoTipoOpcion + "' AND " +
                ColumnaMultiOpcion.CODIGOOPCION + " = '" + codigoOpcion + "'");
        if (cursor.moveToFirst()) {
            multiOpcion = convertirCursorAObjeto(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return multiOpcion;
    }

    private MultiOpcion convertirCursorAObjeto(Cursor cursor) {
        MultiOpcion multiOpcion = new MultiOpcion();
        multiOpcion.setCodigoTipoOpcion(cursor.getString(cursor.getColumnIndex(ColumnaMultiOpcion.COODIGOTIPOOPCION)));
        multiOpcion.setCodigoOpcion(cursor.getString(cursor.getColumnIndex(ColumnaMultiOpcion.CODIGOOPCION)));
        multiOpcion.setDescripcion(cursor.getString(cursor.getColumnIndex(ColumnaMultiOpcion.DESCRIPCION)));
        return multiOpcion;
    }

    @Override
    public boolean guardar(List<MultiOpcion> lista) throws ParseException {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (MultiOpcion multiOpcion : lista) {
            listaContentValues.add(convertirObjetoAContentValues(multiOpcion));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(MultiOpcion dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    @Override
    public boolean actualizar(MultiOpcion dato) {
        ContentValues datos = convertirObjetoAContentValues(dato);
        return this.operadorDatos.actualizar(datos, ColumnaMultiOpcion.COODIGOTIPOOPCION + " = ? " +
                        "AND " + ColumnaMultiOpcion.CODIGOOPCION + " = ?"
                , new String[]{dato.getCodigoTipoOpcion(), dato.getCodigoOpcion()});
    }

    @Override
    public boolean eliminar(MultiOpcion dato) {
        return this.operadorDatos.borrar(ColumnaMultiOpcion.COODIGOTIPOOPCION + " = ? " +
                        "AND " + ColumnaMultiOpcion.CODIGOOPCION + " = ? "
                , new String[]{dato.getCodigoTipoOpcion(), dato.getCodigoOpcion()}) > 0;
    }

    private ContentValues convertirObjetoAContentValues(MultiOpcion multiOpcion) {
        ContentValues contentValues = new ContentValues();
        if (!multiOpcion.getCodigoTipoOpcion().isEmpty()) {
            contentValues.put(ColumnaMultiOpcion.COODIGOTIPOOPCION, multiOpcion.getCodigoTipoOpcion());
        }
        if (!multiOpcion.getCodigoOpcion().isEmpty()) {
            contentValues.put(ColumnaMultiOpcion.CODIGOOPCION, multiOpcion.getCodigoOpcion());
        }
        if (!multiOpcion.getDescripcion().isEmpty()) {
            contentValues.put(ColumnaMultiOpcion.DESCRIPCION, multiOpcion.getDescripcion());
        }
        return contentValues;
    }

    public static class ColumnaMultiOpcion {

        public static final String COODIGOTIPOOPCION = "codigotipoopcion";
        public static final String CODIGOOPCION = "codigoopcion";
        public static final String DESCRIPCION = "descripcion";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaMultiOpcion.COODIGOTIPOOPCION + " " + STRING_TYPE + " not null," +
                    ColumnaMultiOpcion.CODIGOOPCION + " " + STRING_TYPE + " not null," +
                    ColumnaMultiOpcion.DESCRIPCION + " " + STRING_TYPE + " not null," +
                    " primary key (" + ColumnaMultiOpcion.COODIGOTIPOOPCION + "," +
                    ColumnaMultiOpcion.CODIGOOPCION + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}