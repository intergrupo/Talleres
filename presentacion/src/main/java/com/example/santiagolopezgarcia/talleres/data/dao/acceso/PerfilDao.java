package com.example.santiagolopezgarcia.talleres.data.dao.acceso;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.modelonegocio.Perfil;
import com.example.dominio.bussinesslogic.administracion.PerfilRepositorio;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */

public class PerfilDao extends DaoBase implements PerfilRepositorio {


    static final String NombreTabla = "sirius_perfil";

    public PerfilDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public boolean guardar(List<Perfil> lista) {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (Perfil perfil : lista) {
            listaContentValues.add(convertirObjetoAContentValues(perfil));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(Perfil dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    @Override
    public boolean actualizar(Perfil dato) {
        ContentValues datos = convertirObjetoAContentValues(dato);
        return this.operadorDatos.actualizar(datos, ColumnaPerfil.CODIGOPERFIL + " = ?"
                , new String[]{dato.getCodigoPerfil()});
    }

    @Override
    public boolean eliminar(Perfil dato) {
        return this.operadorDatos.borrar(ColumnaPerfil.CODIGOPERFIL + " = ? "
                , new String[]{dato.getCodigoPerfil()}) > 0;
    }

    @Override
    public List<Perfil> cargarPerfiles() {
        List<Perfil> listaPerfiles = new ArrayList<>();
        Cursor cursorPerfil = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla);
        if (cursorPerfil.moveToFirst()) {
            while (!cursorPerfil.isAfterLast()) {
                listaPerfiles.add(convertirCursorAObjeto(cursorPerfil));
                cursorPerfil.moveToNext();
            }
        }
        cursorPerfil.close();
        return listaPerfiles;
    }

    private Perfil convertirCursorAObjeto(Cursor cursor) {
        Perfil perfil = new Perfil();
        perfil.setCodigoPerfil(cursor.getString(cursor.getColumnIndex(ColumnaPerfil.CODIGOPERFIL)));
        perfil.setDescripcion(cursor.getString(cursor.getColumnIndex(ColumnaPerfil.DESCRIPCION)));
        return perfil;
    }

    private ContentValues convertirObjetoAContentValues(Perfil perfil) {
        ContentValues contentValues = new ContentValues();
        if (!perfil.getCodigoPerfil().isEmpty()) {
            contentValues.put(ColumnaPerfil.CODIGOPERFIL, perfil.getCodigoPerfil());
        }
        if (!perfil.getDescripcion().isEmpty()) {
            contentValues.put(ColumnaPerfil.DESCRIPCION, perfil.getDescripcion());
        }
        return contentValues;
    }

    @Override
    public Perfil cargarPerfilXCodigo(String codigoPerfil) {
        Perfil perfil = new Perfil();
        Cursor cursorPerfil = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaPerfil.CODIGOPERFIL
                + " = '" + codigoPerfil + "'");
        if (cursorPerfil.moveToFirst()) {
            perfil = convertirCursorAObjeto(cursorPerfil);
        }
        cursorPerfil.close();
        return perfil;
    }

    public static class ColumnaPerfil {

        public static final String CODIGOPERFIL = "codigoperfil";
        public static final String DESCRIPCION = "descripcion";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaPerfil.CODIGOPERFIL + " " + STRING_TYPE + " not null," +
                    ColumnaPerfil.DESCRIPCION + " " + STRING_TYPE + " not null," +
                    " primary key (" + ColumnaPerfil.CODIGOPERFIL + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}
