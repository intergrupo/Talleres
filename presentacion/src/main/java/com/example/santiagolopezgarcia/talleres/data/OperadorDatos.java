package com.example.santiagolopezgarcia.talleres.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.santiagolopezgarcia.talleres.helpers.DataBaseHelper;
import com.example.utilidades.Log;
import com.example.utilidades.helpers.StringHelper;

import java.io.IOException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 6/10/15.
 */
public class OperadorDatos {

    String nombreBaseDatos;
    Context context;
    DataBaseHelper dataBaseHelper;
    String nombreTabla;

    protected static boolean esTransaccionGlobal = false;

    public OperadorDatos(Context context, String nombreBaseDatos, int version, AdministradorBaseDatosInterface dataBaseManager) {
        dataBaseHelper = DataBaseHelper.getInstance(context, nombreBaseDatos, version, dataBaseManager);
        this.context = context;
        this.nombreBaseDatos = nombreBaseDatos;
    }

    public static void iniciarTransaccionGlobal() {
        OperadorDatos.esTransaccionGlobal = true;
    }

    public static void establecerTransaccionGlobalSatisfactoria() {
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance();
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        if (OperadorDatos.esTransaccionGlobal && db.inTransaction()) {
            db.setTransactionSuccessful();
        }
    }

    public static void terminarTransaccionGlobal() {
        if (OperadorDatos.esTransaccionGlobal) {
            OperadorDatos.esTransaccionGlobal = false;
            DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance();
            SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
            if (db.inTransaction()) {
                db.endTransaction();
            }
        }
    }

    public void SetNombreTabla(String tableName) {
        this.nombreTabla = tableName;
    }

    public boolean insertar(ContentValues contentValues) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        try {
            if (OperadorDatos.esTransaccionGlobal && !db.inTransaction())
                db.beginTransaction();

            db.insertOrThrow(nombreTabla, null, contentValues);

            return true;
        } catch (Exception exp) {
            Log.error(exp, " " + nombreTabla + " Data: " + contentValues.toString());
            try {
                StringHelper.registrarLog(exp.getMessage() + " " + " Data: " + contentValues.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            OperadorDatos.terminarTransaccionGlobal();
            throw new RuntimeException(exp.getMessage() + " " + nombreTabla + " Data: " + contentValues.toString());
        }
    }

    public boolean insertarConTransaccion(List<ContentValues> contentValues) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        ContentValues contentValuesLog = new ContentValues();
        try {
            if (!db.inTransaction())
                db.beginTransactionNonExclusive();

            for (ContentValues contentValue : contentValues) {
                contentValuesLog = contentValue;
                db.insertOrThrow(nombreTabla, null, contentValue);
            }
            if (!OperadorDatos.esTransaccionGlobal)
                db.setTransactionSuccessful();

        } catch (Exception exp) {
            Log.error(exp, " " + nombreTabla + " Data: " + contentValuesLog.toString());
            try {
                StringHelper.registrarLog(exp.getMessage() + " " + " Data: " + contentValues.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            OperadorDatos.terminarTransaccionGlobal();
            throw new RuntimeException(exp.getMessage() + " " + nombreTabla + " Data: " + contentValues.toString());
        } finally {
            if (!OperadorDatos.esTransaccionGlobal && db.inTransaction())
                db.endTransaction();
        }
        return true;
    }

    public boolean insertarConTransaccion(List<List<String>> values, String preparedStatement) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        try {
            if (!db.inTransaction())
                db.beginTransactionNonExclusive();

            SQLiteStatement stmt = db.compileStatement(preparedStatement);
            int position = 0;
            for (List<String> valores : values) {
                for (String valor : valores) {
                    stmt.bindString(position++, valor);
                }
                stmt.execute();
                stmt.clearBindings();
            }
            if (!OperadorDatos.esTransaccionGlobal)
                db.setTransactionSuccessful();

        } catch (Exception exp) {
            OperadorDatos.terminarTransaccionGlobal();
            throw exp;
        } finally {
            if (!OperadorDatos.esTransaccionGlobal && db.inTransaction())
                db.endTransaction();
        }
        return true;
    }

    public Cursor cargar(String query) {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        return res;
    }

    public boolean actualizar(ContentValues contentValues, String clause) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        try {
            if (OperadorDatos.esTransaccionGlobal && !db.inTransaction())
                db.beginTransactionNonExclusive();

            db.update(nombreTabla, contentValues, clause, null);

//            if (OperadorDatos.esTransaccionGlobal)
//                db.setTransactionSuccessful();

            return true;
        } catch (Exception exp) {
            OperadorDatos.terminarTransaccionGlobal();
            try {
                StringHelper.registrarLog(exp.getMessage() + " " + " Data: " + contentValues.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new RuntimeException(exp.getMessage() + " " + nombreTabla + " Data: " + contentValues.toString());
        }
    }

    public boolean actualizar(ContentValues contentValues, String clause, String[] where) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        try {
            if (OperadorDatos.esTransaccionGlobal && !db.inTransaction())
                db.beginTransactionNonExclusive();

            boolean resultado = db.update(nombreTabla, contentValues, clause, where) > 0;

//            if (OperadorDatos.esTransaccionGlobal)
//                db.setTransactionSuccessful();

            return resultado;
        } catch (Exception exp) {
            OperadorDatos.terminarTransaccionGlobal();
            try {
                StringHelper.registrarLog(exp.getMessage() + " " + " Data: " + contentValues.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new RuntimeException(exp.getMessage() + " " + nombreTabla + " Data: " + contentValues.toString());
        }
    }

    public boolean actualizarConTransaccion(List<UpdateContentValues> updateContentValues) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        UpdateContentValues contentValuesLog = new UpdateContentValues();
        boolean resultado = false;
        try {
            if (!db.inTransaction())
                db.beginTransactionNonExclusive();

            for (UpdateContentValues contentValue : updateContentValues) {
                contentValuesLog = contentValue;
                resultado = db.update(nombreTabla, contentValue.getContentValues(), contentValue.getClause(), contentValue.getWhere()) > 0;
                if (!resultado) {
                    break;
                }
            }
            if (resultado) {
                if (!OperadorDatos.esTransaccionGlobal)
                    db.setTransactionSuccessful();
            } else {
                if (!OperadorDatos.esTransaccionGlobal)
                    db.endTransaction();
            }
        } catch (Exception exp) {
            OperadorDatos.terminarTransaccionGlobal();
            Log.error(exp, " " + nombreTabla + " Data: " + contentValuesLog.toString());
            try {
                StringHelper.registrarLog(exp.getMessage() + " " + " Data: " + updateContentValues.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new RuntimeException(exp.getMessage() + " " + nombreTabla + " Data: " + updateContentValues.toString());
        } finally {
            if (!OperadorDatos.esTransaccionGlobal && db.inTransaction())
                db.endTransaction();
        }
        return resultado;
    }

    public int borrar(String clause, String[] where) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        return db.delete(nombreTabla, clause, where);
    }

    public void ejecutarQuery(String query) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        db.execSQL(query);
    }

    public void borrar() {
        ejecutarQuery("Delete from " + nombreTabla);
    }

    public int numeroRegistros() {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, nombreTabla);
        return numRows;
    }

    public boolean deleteDatabase() {
        dataBaseHelper.close();
        boolean resultado = context.deleteDatabase(nombreBaseDatos);
        dataBaseHelper.getWritableDatabase();
        return resultado;
    }
}
