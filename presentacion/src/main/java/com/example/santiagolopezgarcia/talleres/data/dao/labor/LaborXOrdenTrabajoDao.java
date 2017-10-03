package com.example.santiagolopezgarcia.talleres.data.dao.labor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.example.dominio.bussinesslogic.labor.LaborXOrdenTrabajoRepositorio;
import com.example.dominio.modelonegocio.LaborXOrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;
import com.example.santiagolopezgarcia.talleres.data.dao.tarea.TareaXOrdenTrabajoDao;
import com.example.santiagolopezgarcia.talleres.data.dao.tarea.TareaXTrabajoDao;
import com.example.utilidades.helpers.DateHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class LaborXOrdenTrabajoDao extends DaoBase implements LaborXOrdenTrabajoRepositorio {

    static final String NombreTabla = "sirius_laborxordentrabajo";
    static final String ValorObligatorio = "S";

    public LaborXOrdenTrabajoDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public boolean guardar(List<LaborXOrdenTrabajo> lista) {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (LaborXOrdenTrabajo laborXOrdenTrabajo : lista) {
            listaContentValues.add(convertirObjetoAContentValues(laborXOrdenTrabajo));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(LaborXOrdenTrabajo dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    private LaborXOrdenTrabajo convertirCursorAObjeto(Cursor cursor) {
        LaborXOrdenTrabajo laborXOrdenTrabajo = new LaborXOrdenTrabajo();
        laborXOrdenTrabajo.setCodigoCorreria(cursor.getString(cursor.getColumnIndex(ColumnaLaborXOrdenTrabajo.CODIGOCORRERIA)));
        laborXOrdenTrabajo.setCodigoOrdenTrabajo(cursor.getString(cursor.getColumnIndex(ColumnaLaborXOrdenTrabajo.CODIGOORDENTRABAJO)));
        laborXOrdenTrabajo.getTrabajo().setCodigoTrabajo(cursor.getString(cursor.getColumnIndex(ColumnaLaborXOrdenTrabajo.CODIGOTRABAJO)));
        laborXOrdenTrabajo.getTarea().setCodigoTarea(cursor.getString(cursor.getColumnIndex(ColumnaLaborXOrdenTrabajo.CODIGOTAREA)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaLaborXOrdenTrabajo.CODIGOLABOR))) {
            laborXOrdenTrabajo.getLabor().setCodigoLabor(cursor.getString(cursor.getColumnIndex(ColumnaLaborXOrdenTrabajo.CODIGOLABOR)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaLaborXOrdenTrabajo.CODIGOUSUARIOLABOR))) {
            laborXOrdenTrabajo.setCodigoUsuarioLabor(cursor.getString(cursor.getColumnIndex(ColumnaLaborXOrdenTrabajo.CODIGOUSUARIOLABOR)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaLaborXOrdenTrabajo.CODIGOESTADO))) {
            laborXOrdenTrabajo.setEstadoLaborXOrdenTrabajo(LaborXOrdenTrabajo.EstadoLaborXOrdenTrabajo.getEstado(cursor.getString(cursor.getColumnIndex(ColumnaLaborXOrdenTrabajo.CODIGOESTADO))));
        }
        try {
            laborXOrdenTrabajo.setFechaInicioLabor(DateHelper.convertirStringADate(cursor.getString(cursor.getColumnIndex(ColumnaLaborXOrdenTrabajo.FECHAINICIOLABOR)), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            laborXOrdenTrabajo.setFechaUltimoLabor(DateHelper.convertirStringADate(cursor.getString(cursor.getColumnIndex(ColumnaLaborXOrdenTrabajo.FECHAULTIMOLABOR)), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (!cursor.isNull(cursor.getColumnIndex(ColumnaLaborXOrdenTrabajo.PARAMETROS))) {
            laborXOrdenTrabajo.setParametros(cursor.getString(cursor.getColumnIndex(ColumnaLaborXOrdenTrabajo.PARAMETROS)));
        }
        return laborXOrdenTrabajo;
    }

    private ContentValues convertirObjetoAContentValues(LaborXOrdenTrabajo laborXOrdenTrabajo) {
        ContentValues contentValues = new ContentValues();
        if (!laborXOrdenTrabajo.getCodigoCorreria().isEmpty()) {
            contentValues.put(ColumnaLaborXOrdenTrabajo.CODIGOCORRERIA, laborXOrdenTrabajo.getCodigoCorreria());
        }
        if (!laborXOrdenTrabajo.getCodigoOrdenTrabajo().isEmpty()) {
            contentValues.put(ColumnaLaborXOrdenTrabajo.CODIGOORDENTRABAJO, laborXOrdenTrabajo.getCodigoOrdenTrabajo());
        }
        if (!laborXOrdenTrabajo.getTrabajo().getCodigoTrabajo().isEmpty()) {
            contentValues.put(ColumnaLaborXOrdenTrabajo.CODIGOTRABAJO, laborXOrdenTrabajo.getTrabajo().getCodigoTrabajo());
        }
        if (!laborXOrdenTrabajo.getTarea().getCodigoTarea().isEmpty()) {
            contentValues.put(ColumnaLaborXOrdenTrabajo.CODIGOTAREA, laborXOrdenTrabajo.getTarea().getCodigoTarea());
        }
        if (!laborXOrdenTrabajo.getLabor().getCodigoLabor().isEmpty()) {
            contentValues.put(ColumnaLaborXOrdenTrabajo.CODIGOLABOR, laborXOrdenTrabajo.getLabor().getCodigoLabor());
        }
        contentValues.put(ColumnaLaborXOrdenTrabajo.CODIGOUSUARIOLABOR, laborXOrdenTrabajo.getCodigoUsuarioLabor());
        contentValues.put(ColumnaLaborXOrdenTrabajo.CODIGOESTADO, laborXOrdenTrabajo.getEstadoLaborXOrdenTrabajo().getCodigo());
        try {
            contentValues.put(ColumnaLaborXOrdenTrabajo.FECHAINICIOLABOR,
                    DateHelper.convertirDateAString(laborXOrdenTrabajo.getFechaInicioLabor(), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            contentValues.put(ColumnaLaborXOrdenTrabajo.FECHAULTIMOLABOR,
                    DateHelper.convertirDateAString(laborXOrdenTrabajo.getFechaUltimoLabor(), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        contentValues.put(ColumnaLaborXOrdenTrabajo.PARAMETROS, laborXOrdenTrabajo.getParametros());
        return contentValues;
    }

    private ContentValues convertirObjetoAContentValuesActualizacion(LaborXOrdenTrabajo laborXOrdenTrabajo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ColumnaLaborXOrdenTrabajo.CODIGOUSUARIOLABOR, laborXOrdenTrabajo.getCodigoUsuarioLabor());
        contentValues.put(ColumnaLaborXOrdenTrabajo.CODIGOESTADO, laborXOrdenTrabajo.getEstadoLaborXOrdenTrabajo().getCodigo());

        try {
            contentValues.put(ColumnaLaborXOrdenTrabajo.FECHAINICIOLABOR,
                    DateHelper.convertirDateAString(laborXOrdenTrabajo.getFechaInicioLabor(), DateHelper.TipoFormato.yyyyMMddTHHmmss));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            contentValues.put(ColumnaLaborXOrdenTrabajo.FECHAULTIMOLABOR,
                    DateHelper.convertirDateAString(laborXOrdenTrabajo.getFechaUltimoLabor(), DateHelper.TipoFormato.yyyyMMddTHHmmss));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        contentValues.put(ColumnaLaborXOrdenTrabajo.PARAMETROS, laborXOrdenTrabajo.getParametros());
        return contentValues;
    }

    @Override
    public List<LaborXOrdenTrabajo> cargarLaboresXOrdenTrabajo(String codigoCorreria, String codigoOrdenTrabajo,
                                                               String codigoTrabajo, String codigoTarea) throws ParseException {
        List<LaborXOrdenTrabajo> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT lxot.*, Cast(lxt." + LaborXTareaDao.ColumnaLaborXTarea.ORDEN + " AS real) AS CastOrden FROM " + NombreTabla + " lxot" +
                " INNER JOIN " + LaborXTareaDao.NombreTabla + " lxt " +
                "ON lxt." + LaborXTareaDao.ColumnaLaborXTarea.CODIGOTAREA + " = " +
                " lxot." + ColumnaLaborXOrdenTrabajo.CODIGOTAREA + " " +
                "AND lxt." + LaborXTareaDao.ColumnaLaborXTarea.CODIGOLABOR + " = " +
                "lxot." + ColumnaLaborXOrdenTrabajo.CODIGOLABOR + " WHERE lxot." +
                ColumnaLaborXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' AND lxot." +
                ColumnaLaborXOrdenTrabajo.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "' AND lxot." +
                ColumnaLaborXOrdenTrabajo.CODIGOTRABAJO + " = '" + codigoTrabajo + "' AND lxot." +
                ColumnaLaborXOrdenTrabajo.CODIGOTAREA + " = '" + codigoTarea + "' " +
                "ORDER BY CastOrden");
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
    public List<LaborXOrdenTrabajo> cargarLaboresXOrdenTrabajoFiltro(String codigoCorreria, String codigoOrdenTrabajo,
                                                                     String codigoTrabajo, String codigoTarea) throws ParseException {
        List<LaborXOrdenTrabajo> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " lxot" +
                " INNER JOIN " + LaborXTareaDao.NombreTabla + " lxt " +
                "ON lxt." + LaborXTareaDao.ColumnaLaborXTarea.CODIGOTAREA + " = " +
                " lxot." + ColumnaLaborXOrdenTrabajo.CODIGOTAREA + " " +
                "AND lxt." + LaborXTareaDao.ColumnaLaborXTarea.CODIGOLABOR + " = " +
                "lxot." + ColumnaLaborXOrdenTrabajo.CODIGOLABOR + " WHERE lxot." +
                ColumnaLaborXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' AND lxot." +
                ColumnaLaborXOrdenTrabajo.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "' AND lxot." +
                ColumnaLaborXOrdenTrabajo.CODIGOTRABAJO + " = '" + codigoTrabajo + "' AND lxot." +
                ColumnaLaborXOrdenTrabajo.CODIGOTAREA + " = '" + codigoTarea + "' AND lxot." +
                ColumnaLaborXOrdenTrabajo.PARAMETROS + " = '' " +
                "ORDER BY lxt." + LaborXTareaDao.ColumnaLaborXTarea.ORDEN);
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
    public List<LaborXOrdenTrabajo> cargarLaboresXOrdenTrabajoObligatoriasSinEjecutar(String codigoCorreria, String codigoOrdenTrabajo,
                                                                                      String codigoTrabajo, String codigoTarea) throws ParseException {
        List<LaborXOrdenTrabajo> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + LaborXOrdenTrabajoDao.NombreTabla +
                " INNER JOIN " + LaborXTareaDao.NombreTabla +
                " ON " + LaborXOrdenTrabajoDao.NombreTabla + "." + ColumnaLaborXOrdenTrabajo.CODIGOLABOR +
                " = " + LaborXTareaDao.NombreTabla + "." + LaborXTareaDao.ColumnaLaborXTarea.CODIGOLABOR +
                " AND " + LaborXOrdenTrabajoDao.NombreTabla + "." + ColumnaLaborXOrdenTrabajo.CODIGOTAREA +
                " = " + LaborXTareaDao.NombreTabla + "." + LaborXTareaDao.ColumnaLaborXTarea.CODIGOTAREA +
                " WHERE " +
                ColumnaLaborXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' AND " +
                ColumnaLaborXOrdenTrabajo.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "' AND " +
                ColumnaLaborXOrdenTrabajo.CODIGOTRABAJO + " = '" + codigoTrabajo + "' AND " +
                LaborXOrdenTrabajoDao.NombreTabla + "." + ColumnaLaborXOrdenTrabajo.CODIGOTAREA + " = '" + codigoTarea + "' AND (" +
                ColumnaLaborXOrdenTrabajo.CODIGOESTADO + " != '" + LaborXOrdenTrabajo.EstadoLaborXOrdenTrabajo.EJECUTADA.getCodigo() + "' AND " +
                ColumnaLaborXOrdenTrabajo.CODIGOESTADO + " != '" + LaborXOrdenTrabajo.EstadoLaborXOrdenTrabajo.IMPRESA.getCodigo() + "') AND " +
                LaborXTareaDao.ColumnaLaborXTarea.OBLIGATORIA + " ='" + ValorObligatorio + "'");
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
    public List<LaborXOrdenTrabajo> cargarLaboresXOrdenTrabajoEjecutadas(String codigoCorreria, String codigoOrdenTrabajo,
                                                                         String codigoTrabajo, String codigoTarea) throws ParseException {
        List<LaborXOrdenTrabajo> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaLaborXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' AND " +
                ColumnaLaborXOrdenTrabajo.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "' AND " +
                ColumnaLaborXOrdenTrabajo.CODIGOTRABAJO + " = '" + codigoTrabajo + "' AND " +
                ColumnaLaborXOrdenTrabajo.CODIGOTAREA + " = '" + codigoTarea + "' AND (" +
                ColumnaLaborXOrdenTrabajo.CODIGOESTADO + " = '" + LaborXOrdenTrabajo.EstadoLaborXOrdenTrabajo.EJECUTADA.getCodigo() + "' OR " +
                ColumnaLaborXOrdenTrabajo.CODIGOESTADO + " = '" + LaborXOrdenTrabajo.EstadoLaborXOrdenTrabajo.IMPRESA.getCodigo() + "')");
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
    public List<LaborXOrdenTrabajo> cargarLaboresXOTMenu(String codigoCorreria, String codigoOT) {
        List<LaborXOrdenTrabajo> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaLaborXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' AND " +
                ColumnaLaborXOrdenTrabajo.CODIGOORDENTRABAJO + " = '" + codigoOT + "'");
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
    public void actualizarEstadoOrdenTrabajo(String numeroOt, String correria, @Nullable String codEstado) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ColumnaLaborXOrdenTrabajo.CODIGOESTADO, codEstado);
        this.operadorDatos.actualizar(contentValues, "codigocorreria = ? AND codigoordentrabajo = ? ",
                new String[]{correria, numeroOt});
    }

    @Override
    public void actualizarEstadoOTFacturada(String numeroOt, String correria, String codEstado, String fechaUltimaLabor) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ColumnaLaborXOrdenTrabajo.CODIGOESTADO, codEstado);
        contentValues.put(ColumnaLaborXOrdenTrabajo.FECHAULTIMOLABOR, fechaUltimaLabor);
        this.operadorDatos.actualizar(contentValues, "codigocorreria = ? AND codigoordentrabajo = ? ",
                new String[]{correria, numeroOt});
    }

    @Override
    public LaborXOrdenTrabajo cargarLaborXOT(String codigoCorreria, String codigoOrdenTrabajo,
                                             String codigoTrabajo, String codigoTarea,
                                             String codigoLabor) throws ParseException {
        LaborXOrdenTrabajo laborXOrdenTrabajo = new LaborXOrdenTrabajo();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaLaborXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' AND " +
                ColumnaLaborXOrdenTrabajo.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "' AND " +
                ColumnaLaborXOrdenTrabajo.CODIGOTRABAJO + " = '" + codigoTrabajo + "' AND " +
                ColumnaLaborXOrdenTrabajo.CODIGOTAREA + " = '" + codigoTarea + "' AND " +
                ColumnaLaborXOrdenTrabajo.CODIGOLABOR + " = '" + codigoLabor + "'");
        if (cursor.moveToFirst()) {
            laborXOrdenTrabajo = convertirCursorAObjeto(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return laborXOrdenTrabajo;
    }


    @Override
    public boolean actualizar(LaborXOrdenTrabajo laborXOrdenTrabajo) {
        ContentValues datos = convertirObjetoAContentValuesActualizacion(laborXOrdenTrabajo);
        return this.operadorDatos.actualizar(datos, ColumnaLaborXOrdenTrabajo.CODIGOCORRERIA + " = ? AND " +
                        ColumnaLaborXOrdenTrabajo.CODIGOORDENTRABAJO + " = ? AND " +
                        ColumnaLaborXOrdenTrabajo.CODIGOTAREA + " = ? AND " +
                        ColumnaLaborXOrdenTrabajo.CODIGOTRABAJO + " = ? AND " +
                        ColumnaLaborXOrdenTrabajo.CODIGOLABOR + " = ? "
                , new String[]{laborXOrdenTrabajo.getCodigoCorreria(), laborXOrdenTrabajo.getCodigoOrdenTrabajo(),
                        laborXOrdenTrabajo.getTarea().getCodigoTarea(), laborXOrdenTrabajo.getTrabajo().getCodigoTrabajo(),
                        laborXOrdenTrabajo.getLabor().getCodigoLabor()});
    }

    @Override
    public boolean actualizarEstado(LaborXOrdenTrabajo laborXOrdenTrabajo) {
        ContentValues datos = new ContentValues();
        datos.put(ColumnaLaborXOrdenTrabajo.CODIGOESTADO, laborXOrdenTrabajo.getEstadoLaborXOrdenTrabajo().getCodigo());
        return this.operadorDatos.actualizar(datos, ColumnaLaborXOrdenTrabajo.CODIGOCORRERIA + " = ? AND " +
                        ColumnaLaborXOrdenTrabajo.CODIGOORDENTRABAJO + " = ? AND " +
                        ColumnaLaborXOrdenTrabajo.CODIGOTAREA + " = ? AND " +
                        ColumnaLaborXOrdenTrabajo.CODIGOTRABAJO + " = ? AND " +
                        ColumnaLaborXOrdenTrabajo.CODIGOLABOR + " = ? "
                , new String[]{laborXOrdenTrabajo.getCodigoCorreria(), laborXOrdenTrabajo.getCodigoOrdenTrabajo(),
                        laborXOrdenTrabajo.getTarea().getCodigoTarea(), laborXOrdenTrabajo.getTrabajo().getCodigoTrabajo(),
                        laborXOrdenTrabajo.getLabor().getCodigoLabor()});
    }

    @Override
    public boolean eliminar(LaborXOrdenTrabajo dato) {
        return this.operadorDatos.borrar(ColumnaLaborXOrdenTrabajo.CODIGOCORRERIA + " = ? AND " +
                        ColumnaLaborXOrdenTrabajo.CODIGOORDENTRABAJO + " = ? AND " +
                        ColumnaLaborXOrdenTrabajo.CODIGOTAREA + " = ? AND " +
                        ColumnaLaborXOrdenTrabajo.CODIGOTRABAJO + " = ? AND " +
                        ColumnaLaborXOrdenTrabajo.CODIGOLABOR + " = ? "
                , new String[]{dato.getCodigoCorreria(), dato.getCodigoOrdenTrabajo(),
                        dato.getTarea().getCodigoTarea(), dato.getTrabajo().getCodigoTrabajo(),
                        dato.getLabor().getCodigoLabor()}) > 0;
    }

    @Override
    public List<LaborXOrdenTrabajo> cargarLaboresXOrdenTrabajo(String codigoCorreria) throws ParseException {
        List<LaborXOrdenTrabajo> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaLaborXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' AND " +
                ColumnaLaborXOrdenTrabajo.PARAMETROS + " = ''");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                lista.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    public static class ColumnaLaborXOrdenTrabajo {

        public static final String CODIGOCORRERIA = "codigocorreria";
        public static final String CODIGOORDENTRABAJO = "codigoordentrabajo";
        public static final String CODIGOTRABAJO = "codigotrabajo";
        public static final String CODIGOTAREA = "codigotarea";
        public static final String CODIGOLABOR = "codigolabor";
        public static final String CODIGOUSUARIOLABOR = "codigousuariolabor";
        public static final String CODIGOESTADO = "codigoestado";
        public static final String FECHAINICIOLABOR = "fechainiciolabor";
        public static final String FECHAULTIMOLABOR = "fechaultimolabor";
        public static final String PARAMETROS = "parametros";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaLaborXOrdenTrabajo.CODIGOCORRERIA + " " + STRING_TYPE + " not null," +
                    ColumnaLaborXOrdenTrabajo.CODIGOORDENTRABAJO + " " + STRING_TYPE + " not null," +
                    ColumnaLaborXOrdenTrabajo.CODIGOTRABAJO + " " + STRING_TYPE + " not null," +
                    ColumnaLaborXOrdenTrabajo.CODIGOTAREA + " " + STRING_TYPE + " not null," +
                    ColumnaLaborXOrdenTrabajo.CODIGOLABOR + " " + STRING_TYPE + " not null," +
                    ColumnaLaborXOrdenTrabajo.CODIGOUSUARIOLABOR + " " + STRING_TYPE + " null," +
                    ColumnaLaborXOrdenTrabajo.CODIGOESTADO + " " + STRING_TYPE + " not null," +
                    ColumnaLaborXOrdenTrabajo.FECHAINICIOLABOR + " " + STRING_TYPE + " null," +
                    ColumnaLaborXOrdenTrabajo.FECHAULTIMOLABOR + " " + STRING_TYPE + " null," +
                    ColumnaLaborXOrdenTrabajo.PARAMETROS + " " + STRING_TYPE + " null," +
                    " primary key (" + ColumnaLaborXOrdenTrabajo.CODIGOCORRERIA + "," +
                    ColumnaLaborXOrdenTrabajo.CODIGOORDENTRABAJO + "," +
                    ColumnaLaborXOrdenTrabajo.CODIGOTRABAJO + "," +
                    ColumnaLaborXOrdenTrabajo.CODIGOTAREA + "," +
                    ColumnaLaborXOrdenTrabajo.CODIGOLABOR + ")" +
                    " FOREIGN KEY (" + ColumnaLaborXOrdenTrabajo.CODIGOTAREA + "," +
                    ColumnaLaborXOrdenTrabajo.CODIGOLABOR + ") REFERENCES " +
                    LaborXTareaDao.NombreTabla + "( " + LaborXTareaDao.ColumnaLaborXTarea.CODIGOTAREA + "," +
                    LaborXTareaDao.ColumnaLaborXTarea.CODIGOLABOR + ")," +
                    " FOREIGN KEY (" + ColumnaLaborXOrdenTrabajo.CODIGOTRABAJO + "," +
                    ColumnaLaborXOrdenTrabajo.CODIGOTAREA + ") REFERENCES " +
                    TareaXTrabajoDao.NombreTabla + "( " + TareaXTrabajoDao.ColumnaTareaXTrabajo.CODIGOTRABAJO + "," +
                    TareaXTrabajoDao.ColumnaTareaXTrabajo.CODIGOTAREA + ")," +
                    " FOREIGN KEY (" + ColumnaLaborXOrdenTrabajo.CODIGOCORRERIA + "," +
                    ColumnaLaborXOrdenTrabajo.CODIGOORDENTRABAJO + "," +
                    ColumnaLaborXOrdenTrabajo.CODIGOTRABAJO + "," +
                    ColumnaLaborXOrdenTrabajo.CODIGOTAREA + ") REFERENCES " +
                    TareaXOrdenTrabajoDao.NombreTabla + "( " + TareaXOrdenTrabajoDao.ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + "," +
                    TareaXOrdenTrabajoDao.ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO + "," +
                    TareaXOrdenTrabajoDao.ColumnaTareaXOrdenTrabajo.CODIGOTRABAJO + "," +
                    TareaXOrdenTrabajoDao.ColumnaTareaXOrdenTrabajo.CODIGOTAREA + ") ON DELETE CASCADE)";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}
