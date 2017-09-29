package com.example.santiagolopezgarcia.talleres.data.dao.correria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.correria.EmpresaRepositorio;
import com.example.dominio.modelonegocio.Empresa;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class EmpresaDao extends DaoBase implements EmpresaRepositorio {

    static final String NombreTabla = "sirius_empresa";

    public EmpresaDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public boolean guardar(List<Empresa> lista) {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (Empresa empresa : lista) {
            listaContentValues.add(convertirObjetoAContentValues(empresa));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(Empresa dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    @Override
    public boolean actualizar(Empresa dato) {
        ContentValues datos = convertirObjetoAContentValues(dato);
        return this.operadorDatos.actualizar(datos, ColumnaEmpresa.CODIGOEMPRESA + " = ? "
                , new String[]{dato.getCodigoEmpresa()});
    }

    @Override
    public boolean eliminar(Empresa dato) {
        return this.operadorDatos.borrar(ColumnaEmpresa.CODIGOEMPRESA + " = ? "
                , new String[]{dato.getCodigoEmpresa()}) > 0;
    }

    private Empresa convertirCursorAObjeto(Cursor cursor) {
        Empresa empresa = new Empresa();
        empresa.setCodigoEmpresa(cursor.getString(cursor.getColumnIndex(ColumnaEmpresa.CODIGOEMPRESA)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaEmpresa.DESCRIPCION))) {
            empresa.setDescripcion(cursor.getString(cursor.getColumnIndex(ColumnaEmpresa.DESCRIPCION)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaEmpresa.LOGO))) {
            empresa.setLogo(cursor.getString(cursor.getColumnIndex(ColumnaEmpresa.LOGO)));
        }
        return empresa;
    }

    private ContentValues convertirObjetoAContentValues(Empresa empresa) {
        ContentValues contentValues = new ContentValues();
        if (!empresa.getCodigoEmpresa().isEmpty()) {
            contentValues.put(ColumnaEmpresa.CODIGOEMPRESA, empresa.getCodigoEmpresa());
        }
        contentValues.put(ColumnaEmpresa.DESCRIPCION, empresa.getDescripcion());
        contentValues.put(ColumnaEmpresa.LOGO, empresa.getLogo());
        return contentValues;
    }

    @Override
    public Empresa cargarEmpresa(Empresa empresa) {
        Empresa empresaRes = new Empresa();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaEmpresa.CODIGOEMPRESA +
                " = '" + empresa.getCodigoEmpresa() + "'");
        if (cursor.moveToFirst()) {
            if (!cursor.isAfterLast()) {
                empresaRes = convertirCursorAObjeto(cursor);
            }
        }
        cursor.close();
        return empresaRes;
    }

    @Override
    public Empresa cargar(String codigoEmpresa) {
        Empresa empresa = new Empresa();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaEmpresa.CODIGOEMPRESA +
                " = '" + codigoEmpresa + "'");
        if (cursor.moveToFirst()) {
            if (!cursor.isAfterLast()) {
                empresa = convertirCursorAObjeto(cursor);
            }
        }
        cursor.close();
        return empresa;
    }

    public static class ColumnaEmpresa {

        public static final String CODIGOEMPRESA = "codigoempresa";
        public static final String DESCRIPCION = "descripcion";
        public static final String LOGO = "logo";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaEmpresa.CODIGOEMPRESA + " " + STRING_TYPE + " not null," +
                    ColumnaEmpresa.DESCRIPCION + " " + STRING_TYPE + " null," +
                    ColumnaEmpresa.LOGO + " " + STRING_TYPE + " null," +
                    " primary key (" + ColumnaEmpresa.CODIGOEMPRESA + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}
