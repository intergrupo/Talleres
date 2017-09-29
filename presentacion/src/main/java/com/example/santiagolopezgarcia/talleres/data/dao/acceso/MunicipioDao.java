package com.example.santiagolopezgarcia.talleres.data.dao.acceso;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.acceso.MunicipioRepositorio;
import com.example.dominio.modelonegocio.Municipio;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class MunicipioDao extends DaoBase implements MunicipioRepositorio {

    public static final String NombreTabla = "sirius_municipio";

    public MunicipioDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public boolean guardar(List<Municipio> lista) {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (Municipio municipio : lista) {
            listaContentValues.add(convertirObjetoAContentValues(municipio));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(Municipio dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    @Override
    public boolean actualizar(Municipio dato) {
        ContentValues datos = convertirObjetoAContentValues(dato);
        return this.operadorDatos.actualizar(datos, ColumnaMunicipio.CODIGOMUNICIPIO + " = ? "
                , new String[]{dato.getCodigoMunicipio()});
    }

    @Override
    public boolean eliminar(Municipio dato) {
        return this.operadorDatos.borrar(ColumnaMunicipio.CODIGOMUNICIPIO + " = ? "
                , new String[]{dato.getCodigoMunicipio()}) > 0;
    }

    private Municipio convertirCursorAObjeto(Cursor cursor) {
        Municipio municipio = new Municipio();
        municipio.setCodigoMunicipio(cursor.getString(cursor.getColumnIndex(ColumnaMunicipio.CODIGOMUNICIPIO)));
        municipio.setCodigoDepartamento(cursor.getString(cursor.getColumnIndex(ColumnaMunicipio.CODIGODEPARTAMENTO)));
        municipio.setDescripcion(cursor.getString(cursor.getColumnIndex(ColumnaMunicipio.DESCRIPCION)));
        return municipio;
    }

    private ContentValues convertirObjetoAContentValues(Municipio municipio) {
        ContentValues contentValues = new ContentValues();
        if (!municipio.getCodigoMunicipio().isEmpty()) {
            contentValues.put(ColumnaMunicipio.CODIGOMUNICIPIO, municipio.getCodigoMunicipio());
        }
        if (!municipio.getCodigoDepartamento().isEmpty()) {
            contentValues.put(ColumnaMunicipio.CODIGODEPARTAMENTO, municipio.getCodigoDepartamento());
        }
        if (!municipio.getDescripcion().isEmpty()) {
            contentValues.put(ColumnaMunicipio.DESCRIPCION, municipio.getDescripcion());
        }
        return contentValues;
    }

    @Override
    public List<Municipio> cargarMunicipios(String codigoDepartamento) throws ParseException {
        List<Municipio> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaMunicipio.CODIGODEPARTAMENTO + " = '" + codigoDepartamento + "' ORDER BY " + ColumnaMunicipio.DESCRIPCION);
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
    public Municipio cargarMunicipio(String codigoMunicipio) throws ParseException {
        Municipio municipio = new Municipio();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaMunicipio.CODIGOMUNICIPIO +
                " = '" + codigoMunicipio + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                municipio = convertirCursorAObjeto(cursor);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return municipio;
    }

    public static class ColumnaMunicipio {

        public static final String CODIGOMUNICIPIO = "codigomunicipio";
        public static final String CODIGODEPARTAMENTO = "codigodepartamento";
        public static final String DESCRIPCION = "descripcion";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaMunicipio.CODIGOMUNICIPIO + " " + STRING_TYPE + " not null," +
                    ColumnaMunicipio.CODIGODEPARTAMENTO + " " + STRING_TYPE + " not null," +
                    ColumnaMunicipio.DESCRIPCION + " " + STRING_TYPE + " not null," +
                    " primary key (" + ColumnaMunicipio.CODIGOMUNICIPIO + ")" +
                    " FOREIGN KEY (" + ColumnaMunicipio.CODIGODEPARTAMENTO + ") REFERENCES " +
                    DepartamentoDao.NombreTabla + "( " + DepartamentoDao.ColumnaDepartamento.CODIGODEPARTAMENTO + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}
