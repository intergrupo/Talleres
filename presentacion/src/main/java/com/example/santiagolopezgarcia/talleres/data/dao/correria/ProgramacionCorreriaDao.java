package com.example.santiagolopezgarcia.talleres.data.dao.correria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.correria.ProgramacionCorreriaRepositorio;
import com.example.dominio.modelonegocio.ProgramacionCorreria;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public class ProgramacionCorreriaDao extends DaoBase implements ProgramacionCorreriaRepositorio {

    public static final String NombreTabla = "sirius_programacioncorreria";

    public ProgramacionCorreriaDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }


    @Override
    public boolean guardar(List<ProgramacionCorreria> lista) throws ParseException {
        return false;
    }

    @Override
    public boolean guardar(ProgramacionCorreria dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }


    @Override
    public boolean actualizar(ProgramacionCorreria programacionCorreria) {
        ContentValues datos = convertirObjetoAContentValues(programacionCorreria);
        return this.operadorDatos.actualizar(datos, "idprogramacioncorreria = ? AND codigocorreria = ? " +
                        "AND numeroterminal = ?"
                , new String[]{programacionCorreria.getIdProgramacionCorreria(),
                        programacionCorreria.getCodigoCorreria(), programacionCorreria.getNumeroTerminal()});
    }

    @Override
    public boolean eliminar(ProgramacionCorreria programacionCorreria) {
        return this.operadorDatos.borrar("idprogramacioncorreria = ? AND codigocorreria = ? AND numeroterminal = ?"
                , new String[]{programacionCorreria.getIdProgramacionCorreria(),
                        programacionCorreria.getCodigoCorreria(), programacionCorreria.getNumeroTerminal()}) > 0;
    }

    private ProgramacionCorreria convertirCursorAObjeto(Cursor cursor) {
        ProgramacionCorreria programacionCorreria = new ProgramacionCorreria();
        programacionCorreria.setIdProgramacionCorreria(cursor.getString(cursor.getColumnIndex(ColumnaProgramacionCorreria.IDPROGRAMACIONCORRERIA)));
        programacionCorreria.setCodigoPrograma(cursor.getString(cursor.getColumnIndex(ColumnaProgramacionCorreria.CODIGOPROGRAMA)));
        programacionCorreria.setCodigoCorreria(cursor.getString(cursor.getColumnIndex(ColumnaProgramacionCorreria.CODIGOCORRERIA)));
        programacionCorreria.setNumeroTerminal(cursor.getString(cursor.getColumnIndex(ColumnaProgramacionCorreria.NUMEROTERMINAL)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaProgramacionCorreria.ESTADOPROGRAMADA))) {
            programacionCorreria.setEstadoProgramada(cursor.getString(cursor.getColumnIndex(ColumnaProgramacionCorreria.ESTADOPROGRAMADA)));
        }
        return programacionCorreria;
    }

    private ContentValues convertirObjetoAContentValues(ProgramacionCorreria programacionCorreria) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ColumnaProgramacionCorreria.IDPROGRAMACIONCORRERIA, programacionCorreria.getIdProgramacionCorreria());
        contentValues.put(ColumnaProgramacionCorreria.CODIGOPROGRAMA, programacionCorreria.getCodigoPrograma());
        contentValues.put(ColumnaProgramacionCorreria.CODIGOCORRERIA, programacionCorreria.getCodigoCorreria());
        contentValues.put(ColumnaProgramacionCorreria.NUMEROTERMINAL, programacionCorreria.getNumeroTerminal());
        contentValues.put(ColumnaProgramacionCorreria.ESTADOPROGRAMADA, programacionCorreria.getEstadoProgramada());
        return contentValues;
    }

    @Override
    public List<ProgramacionCorreria> cargarXCorreria(String codigoCorreria) {
        List<ProgramacionCorreria> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " P " +
                "JOIN " + CorreriaDao.NombreTabla + " C " +
                "ON P." + ColumnaProgramacionCorreria.CODIGOCORRERIA + " = C." + CorreriaDao.ColumnaCorreria.CODIGOCORRERIA + " " +
                "WHERE C." + CorreriaDao.ColumnaCorreria.RECARGACORRERIA + " <> 'S' AND P." +
                ColumnaProgramacionCorreria.CODIGOCORRERIA + " = '" + codigoCorreria + "'");
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
    public List<ProgramacionCorreria> cargar() {
        List<ProgramacionCorreria> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " P " +
                "JOIN " + CorreriaDao.NombreTabla + " C " +
                "ON P." + ColumnaProgramacionCorreria.CODIGOCORRERIA + " = C." + CorreriaDao.ColumnaCorreria.CODIGOCORRERIA + " " +
                "WHERE C." + CorreriaDao.ColumnaCorreria.RECARGACORRERIA + " <> 'S'");
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
    public List<ProgramacionCorreria> cargarXNumeroTerminal(String numeroTerminal) {
        List<ProgramacionCorreria> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaProgramacionCorreria.NUMEROTERMINAL + " = '" + numeroTerminal + "'");
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
    public ProgramacionCorreria cargar(String idProgramacionCorreria, String codigoCorreria, String numeroTerminal) {
        ProgramacionCorreria programacionCorreria = new ProgramacionCorreria();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaProgramacionCorreria.IDPROGRAMACIONCORRERIA + " = '" + idProgramacionCorreria + "' AND " +
                ColumnaProgramacionCorreria.CODIGOCORRERIA + " = '" + codigoCorreria + "' AND " +
                ColumnaProgramacionCorreria.NUMEROTERMINAL + " = '" + numeroTerminal + "'");
        if (cursor.moveToFirst()) {
            if (!cursor.isAfterLast()) {
                programacionCorreria = convertirCursorAObjeto(cursor);
            }
        }
        cursor.close();
        return programacionCorreria;
    }

    @Override
    public List<ProgramacionCorreria> cargar(String codigosCorreriasIntegradas) {
        List<ProgramacionCorreria> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaProgramacionCorreria.CODIGOCORRERIA + " IN(" + codigosCorreriasIntegradas + ")");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                lista.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    public static class ColumnaProgramacionCorreria {

        public static final String IDPROGRAMACIONCORRERIA = "idprogramacioncorreria";
        public static final String NUMEROTERMINAL = "numeroterminal";
        public static final String CODIGOCORRERIA = "codigocorreria";
        public static final String ESTADOPROGRAMADA = "estadoprogramada";
        public static final String CODIGOPROGRAMA = "codigoprograma";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaProgramacionCorreria.IDPROGRAMACIONCORRERIA + " " + STRING_TYPE + " not null," +
                    ColumnaProgramacionCorreria.CODIGOPROGRAMA + " " + STRING_TYPE + " not null," +
                    ColumnaProgramacionCorreria.NUMEROTERMINAL + " " + STRING_TYPE + " not null," +
                    ColumnaProgramacionCorreria.CODIGOCORRERIA + " " + STRING_TYPE + " not null," +
                    ColumnaProgramacionCorreria.ESTADOPROGRAMADA + " " + STRING_TYPE + " not null," +
                    " primary key (" +
                    ColumnaProgramacionCorreria.IDPROGRAMACIONCORRERIA + "," +
                    ColumnaProgramacionCorreria.CODIGOCORRERIA + "," +
                    ColumnaProgramacionCorreria.NUMEROTERMINAL + "," +
                    ColumnaProgramacionCorreria.CODIGOPROGRAMA + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}