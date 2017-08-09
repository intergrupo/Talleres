package com.example.santiagolopezgarcia.talleres.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.santiagolopezgarcia.talleres.data.AdministradorBaseDatosInterface;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 6/10/15.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    AdministradorBaseDatosInterface dataBaseManager;
    private static DataBaseHelper instanccia;

    private DataBaseHelper(Context context, String nombreBaseDatos, int version, AdministradorBaseDatosInterface dataBaseManager) {
        super(context, nombreBaseDatos, null, version);
        this.dataBaseManager = dataBaseManager;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        //db.setForeignKeyConstraintsEnabled(true);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
                db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }



    public static synchronized DataBaseHelper getInstance(Context context, String nombreBaseDatos, int version, AdministradorBaseDatosInterface dataBaseManager) {
        if (instanccia == null) {
            instanccia = new DataBaseHelper(context, nombreBaseDatos, version, dataBaseManager);
        }
        return instanccia;
    }

    public static DataBaseHelper getInstance(){
        if (instanccia == null) {
            throw new NullPointerException("No se ha iniciado DataBaseHelper");
        }
        return instanccia;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (dataBaseManager != null) {
            List<String> querys = dataBaseManager.GetQuerysCreate();
            for (String query : querys) {
                db.execSQL(query);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (dataBaseManager != null) {
            List<String> querys = dataBaseManager.GetQuerysUpgrade();
            for (String query : querys) {
                db.execSQL(query);
            }
        }
        onCreate(db);
    }
}