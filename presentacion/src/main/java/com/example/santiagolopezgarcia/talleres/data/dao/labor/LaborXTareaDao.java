package com.example.santiagolopezgarcia.talleres.data.dao.labor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.bussinesslogic.labor.LaborXTareaRepositorio;
import com.example.dominio.modelonegocio.LaborXTarea;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;
import com.example.santiagolopezgarcia.talleres.data.dao.tarea.TareaDao;
import com.example.utilidades.helpers.BooleanHelper;
import com.example.utilidades.helpers.StringHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class LaborXTareaDao extends DaoBase implements LaborXTareaRepositorio {

    static final String NombreTabla = "sirius_laborxtarea";
    static final String ValorObligatorio = "S";

    public LaborXTareaDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public boolean guardar(List<LaborXTarea> lista) {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (LaborXTarea laborxtarea : lista) {
            listaContentValues.add(convertirObjetoAContentValues(laborxtarea));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(LaborXTarea dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    @Override
    public boolean actualizar(LaborXTarea dato) {
        ContentValues datos = convertirObjetoAContentValues(dato);
        return this.operadorDatos.actualizar(datos, ColumnaLaborXTarea.CODIGOTAREA + " = ? " +
                        "AND " + ColumnaLaborXTarea.CODIGOLABOR + " = ? "
                , new String[]{dato.getTarea().getCodigoTarea(), dato.getLabor().getCodigoLabor()});
    }

    @Override
    public boolean eliminar(LaborXTarea dato) {
        return this.operadorDatos.borrar(ColumnaLaborXTarea.CODIGOTAREA + " = ? " +
                        "AND " + ColumnaLaborXTarea.CODIGOLABOR + " = ? "
                , new String[]{dato.getTarea().getCodigoTarea(), dato.getLabor().getCodigoLabor()}) > 0;
    }

    private LaborXTarea convertirCursorAObjeto(Cursor cursor) {
        LaborXTarea laborxtarea = new LaborXTarea();
        laborxtarea.getTarea().setCodigoTarea(cursor.getString(cursor.getColumnIndex(ColumnaLaborXTarea.CODIGOTAREA)));
        laborxtarea.getLabor().setCodigoLabor(cursor.getString(cursor.getColumnIndex(ColumnaLaborXTarea.CODIGOLABOR)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaLaborXTarea.OBLIGATORIA))) {
            laborxtarea.setObligatoria(StringHelper.ToBoolean(cursor.getString(cursor.getColumnIndex(ColumnaLaborXTarea.OBLIGATORIA))));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaLaborXTarea.ORDEN))) {
            laborxtarea.setOrden(cursor.getInt(cursor.getColumnIndex(ColumnaLaborXTarea.ORDEN)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaLaborXTarea.PARAMETROS))) {
            laborxtarea.setParametros(cursor.getString(cursor.getColumnIndex(ColumnaLaborXTarea.PARAMETROS)));
        }
        return laborxtarea;
    }

    private ContentValues convertirObjetoAContentValues(LaborXTarea laborxtarea) {
        ContentValues contentValues = new ContentValues();
        if (!laborxtarea.getTarea().getCodigoTarea().isEmpty()) {
            contentValues.put(ColumnaLaborXTarea.CODIGOTAREA, laborxtarea.getTarea().getCodigoTarea());
        }
        if (!laborxtarea.getLabor().getCodigoLabor().isEmpty()) {
            contentValues.put(ColumnaLaborXTarea.CODIGOLABOR, laborxtarea.getLabor().getCodigoLabor());
        }
        contentValues.put(ColumnaLaborXTarea.OBLIGATORIA, BooleanHelper.ToString(laborxtarea.isObligatoria()));
        contentValues.put(ColumnaLaborXTarea.ORDEN, laborxtarea.getOrden());
        contentValues.put(ColumnaLaborXTarea.PARAMETROS, laborxtarea.getParametros());
        return contentValues;
    }

    @Override
    public LaborXTarea cargarLaborxTarea(String codigoTarea, String codigoLabor) {
        LaborXTarea laborXTarea = new LaborXTarea();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaLaborXTarea.CODIGOTAREA + " = '" + codigoTarea + "' AND " +
                ColumnaLaborXTarea.CODIGOLABOR + " = '" + codigoLabor + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                laborXTarea = convertirCursorAObjeto(cursor);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return laborXTarea;
    }

    @Override
    public List<LaborXTarea> cargarListaLaborxTareaXCodigoTarea(String codigoTarea) {
        List<LaborXTarea> listaLaborXTarea = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaLaborXTarea.CODIGOTAREA + " = '" + codigoTarea + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listaLaborXTarea.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listaLaborXTarea;
    }

    @Override
    public List<LaborXTarea> cargarListaLaborxTareaXCodigoTareaMenu(String codigoTarea) {
        List<LaborXTarea> listaLaborXTarea = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaLaborXTarea.CODIGOTAREA + " = '" + codigoTarea + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listaLaborXTarea.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listaLaborXTarea;
    }

    @Override
    public List<LaborXTarea> cargarListaLaborxTareaXCodigoTareaMenu(String codigoTarea, String codigoLabor) {
        List<LaborXTarea> listaLaborXTarea = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaLaborXTarea.CODIGOTAREA + " = '" + codigoTarea + "' AND " +
                ColumnaLaborXTarea.CODIGOLABOR + " <> '" + codigoLabor + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listaLaborXTarea.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listaLaborXTarea;
    }

    @Override
    public List<LaborXTarea> cargarListaLaborxTareaObligatorias(String codigoTarea) {
        List<LaborXTarea> listaLaborXTarea = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaLaborXTarea.CODIGOTAREA + " = '" + codigoTarea + "' AND " +
                ColumnaLaborXTarea.OBLIGATORIA + "='" + ValorObligatorio + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listaLaborXTarea.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listaLaborXTarea;
    }

    public static class ColumnaLaborXTarea {

        public static final String CODIGOTAREA = "codigotarea";
        public static final String CODIGOLABOR = "codigolabor";
        public static final String OBLIGATORIA = "obligatoria";
        public static final String ORDEN = "orden";
        public static final String PARAMETROS = "parametros";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaLaborXTarea.CODIGOTAREA + " " + STRING_TYPE + " not null," +
                    ColumnaLaborXTarea.CODIGOLABOR + " " + STRING_TYPE + " not null," +
                    ColumnaLaborXTarea.OBLIGATORIA + " " + STRING_TYPE + " null," +
                    ColumnaLaborXTarea.ORDEN + " " + STRING_TYPE + " null," +
                    ColumnaLaborXTarea.PARAMETROS + " " + STRING_TYPE + " null," +
                    " primary key (" + ColumnaLaborXTarea.CODIGOTAREA + "," +
                    ColumnaLaborXTarea.CODIGOLABOR + ")" +
                    " FOREIGN KEY (" + ColumnaLaborXTarea.CODIGOLABOR + ") REFERENCES " +
                    LaborDao.NombreTabla + "( " + LaborDao.ColumnaLabor.CODIGOLABOR + ")," +
                    " FOREIGN KEY (" + ColumnaLaborXTarea.CODIGOTAREA + ") REFERENCES " +
                    TareaDao.NombreTabla + "( " + TareaDao.ColumnaTarea.CODIGOTAREA + "))";


    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}