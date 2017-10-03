package com.example.santiagolopezgarcia.talleres.data.dao.acceso;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.bussinesslogic.acceso.DepartamentoRepositorio;
import com.example.dominio.modelonegocio.Departamento;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class DepartamentoDao extends DaoBase implements DepartamentoRepositorio {

    static final String NombreTabla = "sirius_departamento";

    public DepartamentoDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public List<Departamento> cargarDepartamentos() {
        List<Departamento> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " ORDER BY " + ColumnaDepartamento.DESCRIPCION );
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
    public boolean guardar(List<Departamento> lista) {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (Departamento departamento : lista) {
            listaContentValues.add(convertirObjetoAContentValues(departamento));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(Departamento dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    @Override
    public boolean actualizar(Departamento dato) {
        ContentValues datos = convertirObjetoAContentValues(dato);
        return this.operadorDatos.actualizar(datos, ColumnaDepartamento.CODIGODEPARTAMENTO + " = ? "
                , new String[]{dato.getCodigoDepartamento()});
    }

    @Override
    public boolean eliminar(Departamento dato) {
        return this.operadorDatos.borrar(ColumnaDepartamento.CODIGODEPARTAMENTO + " = ? "
                , new String[]{dato.getCodigoDepartamento()}) > 0;
    }

    private Departamento convertirCursorAObjeto(Cursor cursor) {
        Departamento departamento = new Departamento();
        departamento.setCodigoDepartamento(cursor.getString(cursor.getColumnIndex(ColumnaDepartamento.CODIGODEPARTAMENTO)));
        departamento.setDescripcion(cursor.getString(cursor.getColumnIndex(ColumnaDepartamento.DESCRIPCION)));
        return departamento;
    }

    private ContentValues convertirObjetoAContentValues(Departamento departamento) {
        ContentValues contentValues = new ContentValues();
        if (!departamento.getCodigoDepartamento().isEmpty()) {
            contentValues.put(ColumnaDepartamento.CODIGODEPARTAMENTO, departamento.getCodigoDepartamento());
        }
        contentValues.put(ColumnaDepartamento.DESCRIPCION, departamento.getDescripcion());
        return contentValues;
    }

    @Override
    public Departamento cargarDepartamento(String codigoDepartamento) throws ParseException {
        Departamento departamento = new Departamento();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaDepartamento.CODIGODEPARTAMENTO +
                " = '" + codigoDepartamento + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                departamento = convertirCursorAObjeto(cursor);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return departamento;
    }

    public static class ColumnaDepartamento {

        public static final String CODIGODEPARTAMENTO = "codigodepartamento";
        public static final String DESCRIPCION = "descripcion";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaDepartamento.CODIGODEPARTAMENTO + " " + STRING_TYPE + " not null," +
                    ColumnaDepartamento.DESCRIPCION + " " + STRING_TYPE + " not null," +
                    " primary key (" + ColumnaDepartamento.CODIGODEPARTAMENTO + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}
