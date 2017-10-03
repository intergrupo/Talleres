package com.example.santiagolopezgarcia.talleres.data.dao.acceso;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.bussinesslogic.administracion.PerfilXOpcionRepositorio;
import com.example.dominio.modelonegocio.PerfilXOpcion;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class PerfilXOpcionDao extends DaoBase implements PerfilXOpcionRepositorio {

    static final String NombreTabla = "sirius_perfilopcion";

    public PerfilXOpcionDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public List<PerfilXOpcion> cargarPerfilOpcionXCodigoPerfil(String codigoPerfil) {
        List<PerfilXOpcion> listaPerfilXOpcion = new ArrayList<>();
        Cursor cursorPerfil = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaPerfilxOpcion.CODIGOPERFIL
                + " = '" + codigoPerfil + "'");
        if (cursorPerfil.moveToFirst()) {
            while (!cursorPerfil.isAfterLast()) {
                listaPerfilXOpcion.add(convertirCursorAObjeto(cursorPerfil));
                cursorPerfil.moveToNext();
            }
        }
        cursorPerfil.close();
        return listaPerfilXOpcion;
    }

    @Override
    public PerfilXOpcion cargarPerfilOpcion(String codigoPerfil, String codigoOpcion) {
        PerfilXOpcion perfilXOpcion = new PerfilXOpcion();
        Cursor cursorPerfil = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaPerfilxOpcion.CODIGOPERFIL
                + " = '" + codigoPerfil + "' " +
                "AND " + ColumnaPerfilxOpcion.CODIGOOPCION + " = '" + codigoOpcion + "'");
        if (cursorPerfil.moveToFirst()) {
            while (!cursorPerfil.isAfterLast()) {
                perfilXOpcion = convertirCursorAObjeto(cursorPerfil);
                cursorPerfil.moveToNext();
            }
        }
        cursorPerfil.close();
        return perfilXOpcion;
    }

    private PerfilXOpcion convertirCursorAObjeto(Cursor cursor) {
        PerfilXOpcion perfilXOpcion = new PerfilXOpcion();
        perfilXOpcion.getOpcion().setCodigoOpcion(cursor.getString(cursor.getColumnIndex(ColumnaPerfilxOpcion.CODIGOOPCION)));
        perfilXOpcion.getPerfil().setCodigoPerfil(cursor.getString(cursor.getColumnIndex(ColumnaPerfilxOpcion.CODIGOPERFIL)));
        return perfilXOpcion;
    }

    @Override
    public boolean guardar(List<PerfilXOpcion> lista) throws ParseException {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (PerfilXOpcion perfilxOpcion : lista) {
            listaContentValues.add(convertirObjetoAContentValues(perfilxOpcion));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(PerfilXOpcion dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    @Override
    public boolean actualizar(PerfilXOpcion dato) {
        ContentValues datos = convertirObjetoAContentValues(dato);
        return this.operadorDatos.actualizar(datos, ColumnaPerfilxOpcion.CODIGOOPCION + " = ? " +
                        "AND " + ColumnaPerfilxOpcion.CODIGOPERFIL + " = ?"
                , new String[]{dato.getOpcion().getCodigoOpcion(), dato.getPerfil().getCodigoPerfil()});
    }

    @Override
    public boolean eliminar(PerfilXOpcion dato) {
        return this.operadorDatos.borrar(ColumnaPerfilxOpcion.CODIGOOPCION + " = ? " +
                        "AND " + ColumnaPerfilxOpcion.CODIGOPERFIL + " = ? "
                , new String[]{dato.getOpcion().getCodigoOpcion(), dato.getPerfil().getCodigoPerfil()}) > 0;
    }

    private ContentValues convertirObjetoAContentValues(PerfilXOpcion perfilxOpcion) {
        ContentValues contentValues = new ContentValues();
        if (!perfilxOpcion.getOpcion().getCodigoOpcion().isEmpty()) {
            contentValues.put(ColumnaPerfilxOpcion.CODIGOOPCION, perfilxOpcion.getOpcion().getCodigoOpcion());
        }
        if (!perfilxOpcion.getPerfil().getCodigoPerfil().isEmpty()) {
            contentValues.put(ColumnaPerfilxOpcion.CODIGOPERFIL, perfilxOpcion.getPerfil().getCodigoPerfil());
        }
        return contentValues;
    }

    public static class ColumnaPerfilxOpcion {

        public static final String CODIGOOPCION = "codigoopcion";
        public static final String CODIGOPERFIL = "codigoperfil";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaPerfilxOpcion.CODIGOOPCION + " " + STRING_TYPE + " not null," +
                    ColumnaPerfilxOpcion.CODIGOPERFIL + " " + STRING_TYPE + " not null," +
                    " primary key (" + ColumnaPerfilxOpcion.CODIGOOPCION + "," + ColumnaPerfilxOpcion.CODIGOPERFIL + ")" +
                    " FOREIGN KEY (" + ColumnaPerfilxOpcion.CODIGOOPCION + ") REFERENCES " +
                    OpcionDao.NombreTabla + "( " + OpcionDao.ColumnaOpcion.CODIGOOPCION + "), " +
                    " FOREIGN KEY (" + ColumnaPerfilxOpcion.CODIGOPERFIL + ") REFERENCES " +
                    PerfilDao.NombreTabla + "( " + PerfilDao.ColumnaPerfil.CODIGOPERFIL + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}
