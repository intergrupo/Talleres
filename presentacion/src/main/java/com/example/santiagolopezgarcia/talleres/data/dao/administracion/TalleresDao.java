package com.example.santiagolopezgarcia.talleres.data.dao.administracion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.bussinesslogic.administracion.TalleresRepositorio;
import com.example.dominio.modelonegocio.Talleres;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;
import com.example.utilidades.helpers.DateHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class TalleresDao extends DaoBase implements TalleresRepositorio {


    static final String NombreTabla = "sirius";

    public TalleresDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public Talleres cargarPrimerRegistro() throws ParseException {
        Talleres talleres = new Talleres();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla);
        if (cursor.moveToFirst()) {
            talleres = convertirCursorAObjeto(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return talleres;
    }

    @Override
    public boolean borrarDB() {
        return this.operadorDatos.deleteDatabase();
    }

    @Override
    public Talleres consultarTalleresXNumeroTerminal(String numeroTerminal) throws ParseException {
        Talleres talleres = new Talleres();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " " +
                "WHERE " + ColumnaSirius.NUMEROTERMINAL + " = '" + numeroTerminal + "'");
        if (cursor.moveToFirst()) {
            talleres = convertirCursorAObjeto(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return talleres;
    }

    @Override
    public boolean guardar(List<Talleres> listaTalleres) {
        List<ContentValues> listaContentValues = new ArrayList<>(listaTalleres.size());
        for (Talleres talleres : listaTalleres) {
            try {
                listaContentValues.add(convertirObjetoAContentValues(talleres));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return this.operadorDatos.insertarConTransaccion(listaContentValues);
    }

    @Override
    public boolean guardar(Talleres talleres) {
        ContentValues contentValues = null;
        try {
            contentValues = convertirObjetoAContentValues(talleres);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.operadorDatos.insertar(contentValues);
    }

    @Override
    public boolean actualizar(Talleres talleres) {
        ContentValues contentTalleres = null;
        try {
            contentTalleres = convertirObjetoAContentValuesActualizacion(talleres, false);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            return this.operadorDatos.actualizar(contentTalleres, "id = ?"
                    , new String[]{cargarPrimerRegistro().getId()});
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Talleres talleres, boolean param2d) {
        ContentValues contentTalleres = null;
        try {
            contentTalleres = convertirObjetoAContentValuesActualizacion(talleres, true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            return this.operadorDatos.actualizar(contentTalleres, "id = ?"
                    , new String[]{cargarPrimerRegistro().getId()});
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public boolean eliminar(Talleres dato) {
        return this.operadorDatos.borrar(ColumnaSirius.NUMEROTERMINAL + " = ? "
                , new String[]{dato.getNumeroTerminal()}) > 0;
    }

    @Override
    public int generarID() {
        Cursor cursor = this.operadorDatos.cargar("SELECT MAX(" + ColumnaSirius.ID + ") '" +
                ColumnaSirius.ID + "' FROM " + TalleresDao.NombreTabla);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex(ColumnaSirius.ID)) + 1;
        }
        cursor.close();
        return 0;
    }

    private Talleres convertirCursorAObjeto(Cursor cursor) throws ParseException {
        Talleres talleres = new Talleres();
        talleres.setId(cursor.getString(cursor.getColumnIndex(ColumnaSirius.ID)));
        talleres.setNumeroTerminal(cursor.getString(cursor.getColumnIndex(ColumnaSirius.NUMEROTERMINAL)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaSirius.RUTASERVIDOR))) {
            talleres.setRutaServidor(cursor.getString(cursor.getColumnIndex(ColumnaSirius.RUTASERVIDOR)));
        }

        if (!cursor.isNull(cursor.getColumnIndex(ColumnaSirius.VERSIONMAESTROS))) {
            talleres.setVersionMaestros(cursor.getString(cursor.getColumnIndex(ColumnaSirius.VERSIONMAESTROS)));
        }
        talleres.setFechaMaestros(DateHelper.convertirStringADate(cursor.getString(cursor.getColumnIndex(ColumnaSirius.FECHAMAESTROS)),
                DateHelper.TipoFormato.yyyyMMddTHHmmss));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaSirius.WIFI))) {
            talleres.setWifi(cursor.getString(cursor.getColumnIndex(ColumnaSirius.WIFI)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaSirius.CONFIRMACION))) {
            talleres.setConfirmacion(cursor.getString(cursor.getColumnIndex(ColumnaSirius.CONFIRMACION)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaSirius.SINCRONIZACION))) {
            talleres.setSincronizacion(cursor.getString(cursor.getColumnIndex(ColumnaSirius.SINCRONIZACION)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaSirius.IMPRESORA))) {
            talleres.setImpresora(cursor.getString(cursor.getColumnIndex(ColumnaSirius.IMPRESORA)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaSirius.DIRECCIONIMPRESORA))) {
            talleres.setDireccionImpresora(cursor.getString(cursor.getColumnIndex(ColumnaSirius.DIRECCIONIMPRESORA)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaSirius.TIPOIDENTIFICACION))) {
            talleres.setTipoIdentificacion(cursor.getString(cursor.getColumnIndex(ColumnaSirius.TIPOIDENTIFICACION)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaSirius.LONGITUDIMPRESIONFENS))) {
            talleres.setLongitudImpresionFens(cursor.getInt(cursor.getColumnIndex(ColumnaSirius.LONGITUDIMPRESIONFENS)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaSirius.CANTIDADDECIMALESFENS))) {
            talleres.setCantidadDecimalesFens(cursor.getInt(cursor.getColumnIndex(ColumnaSirius.CANTIDADDECIMALESFENS)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaSirius.LOG))) {
            talleres.setLog(cursor.getString(cursor.getColumnIndex(ColumnaSirius.LOG)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaSirius.VERSIONPARAMETRIZACION))) {
            talleres.setVersionParametrizacion(cursor.getString(cursor.getColumnIndex(ColumnaSirius.VERSIONPARAMETRIZACION)));
        }
        return talleres;
    }

    private ContentValues convertirObjetoAContentValues(Talleres talleres) throws ParseException {
        ContentValues contentValues = new ContentValues();
        if (!talleres.getNumeroTerminal().isEmpty()) {
            contentValues.put(ColumnaSirius.NUMEROTERMINAL, talleres.getNumeroTerminal());
        }

        //if (sirius.getRutaServidor() != null && !sirius.getRutaServidor().isEmpty()) {
        contentValues.put(ColumnaSirius.RUTASERVIDOR, talleres.getRutaServidor());
        contentValues.put(ColumnaSirius.ID, generarID());
        //}
        contentValues.put(ColumnaSirius.VERSIONMAESTROS, talleres.getVersionMaestros());
        contentValues.put(ColumnaSirius.FECHAMAESTROS, DateHelper.convertirDateAString(talleres.getFechaMaestros(), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        contentValues.put(ColumnaSirius.IMPRESORA, talleres.getImpresora());
        contentValues.put(ColumnaSirius.DIRECCIONIMPRESORA, talleres.getDireccionImpresora());
        contentValues.put(ColumnaSirius.TIPOIDENTIFICACION, talleres.getTipoIdentificacion());
        if (talleres.getConfirmacion() != null) {
            contentValues.put(ColumnaSirius.CONFIRMACION, talleres.getConfirmacion());
        }
        if (talleres.getSincronizacion() != null) {
            contentValues.put(ColumnaSirius.SINCRONIZACION, talleres.getSincronizacion());
        }
        if (talleres.getWifi() != null) {
            contentValues.put(ColumnaSirius.WIFI, talleres.getWifi());
        }
        if (talleres.getLongitudImpresionFens() != 0) {
            contentValues.put(ColumnaSirius.LONGITUDIMPRESIONFENS, talleres.getLongitudImpresionFens());
        }
        if (talleres.getCantidadDecimalesFens() != 0) {
            contentValues.put(ColumnaSirius.CANTIDADDECIMALESFENS, talleres.getCantidadDecimalesFens());
        }
        contentValues.put(ColumnaSirius.LOG,talleres.getLog());
        contentValues.put(ColumnaSirius.VERSIONPARAMETRIZACION,talleres.getVersionParametrizacion());
        return contentValues;
    }

    private ContentValues convertirObjetoAContentValuesActualizacion(Talleres talleres, boolean param2d) throws ParseException {
        ContentValues contentValues = new ContentValues();
        Talleres talleresBD = cargarPrimerRegistro();

        contentValues.put(ColumnaSirius.NUMEROTERMINAL, !talleres.getNumeroTerminal().isEmpty() ?
                talleres.getNumeroTerminal() : talleresBD.getNumeroTerminal());

        //if (sirius.getRutaServidor() != null && !sirius.getRutaServidor().isEmpty()) {
        contentValues.put(ColumnaSirius.RUTASERVIDOR, !talleres.getRutaServidor().isEmpty() ?
                talleres.getRutaServidor() : talleresBD.getRutaServidor());
        //}
        contentValues.put(ColumnaSirius.VERSIONMAESTROS, !talleres.getVersionMaestros().isEmpty() ?
                talleres.getVersionMaestros() : talleresBD.getVersionMaestros());
        contentValues.put(ColumnaSirius.FECHAMAESTROS, talleres.getFechaMaestros() != null ?
                DateHelper.convertirDateAString(talleres.getFechaMaestros(), DateHelper.TipoFormato.yyyyMMddTHHmmss) :
                DateHelper.convertirDateAString(talleresBD.getFechaMaestros(), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        contentValues.put(ColumnaSirius.IMPRESORA, !talleres.getImpresora().isEmpty() ?
                talleres.getImpresora() : talleresBD.getImpresora());
        contentValues.put(ColumnaSirius.DIRECCIONIMPRESORA, !talleres.getDireccionImpresora().isEmpty() ?
                talleres.getDireccionImpresora() : talleresBD.getDireccionImpresora());
        contentValues.put(ColumnaSirius.TIPOIDENTIFICACION, !talleres.getTipoIdentificacion().isEmpty() ?
                talleres.getTipoIdentificacion() : talleresBD.getTipoIdentificacion());
        contentValues.put(ColumnaSirius.CONFIRMACION, talleres.getConfirmacion());
        contentValues.put(ColumnaSirius.SINCRONIZACION, !talleres.getSincronizacion().isEmpty() ?
                talleres.getSincronizacion() : talleresBD.getSincronizacion());
        contentValues.put(ColumnaSirius.WIFI, !talleres.getWifi().isEmpty() ? talleres.getWifi() :
                talleresBD.getWifi());
        contentValues.put(ColumnaSirius.LONGITUDIMPRESIONFENS, talleres.getLongitudImpresionFens());
        contentValues.put(ColumnaSirius.CANTIDADDECIMALESFENS, talleres.getCantidadDecimalesFens());
        contentValues.put(ColumnaSirius.LOG,!talleres.getLog().isEmpty() ?
                talleres.getLog() : talleresBD.getLog());
        contentValues.put(ColumnaSirius.VERSIONPARAMETRIZACION,!talleres.getVersionParametrizacion().isEmpty() ?
                talleres.getVersionParametrizacion() : talleresBD.getVersionParametrizacion());
        return contentValues;
    }

    public static class ColumnaSirius {

        public static final String ID = "id";
        public static final String NUMEROTERMINAL = "numeroterminal";
        public static final String RUTASERVIDOR = "rutaservidor";
        public static final String VERSIONMAESTROS = "versionmaestros";
        public static final String FECHAMAESTROS = "fechamaestros";
        public static final String WIFI = "wifi";
        public static final String IMPRESORA = "impresora";

        public static final String DIRECCIONIMPRESORA = "direccionimpresora";
        public static final String TIPOIDENTIFICACION = "tipoidentificacion";
        public static final String CONFIRMACION = "confirmacion";
        public static final String SINCRONIZACION = "sincronizacion";
        public static final String LONGITUDIMPRESIONFENS = "longitudimpresionfens";
        public static final String CANTIDADDECIMALESFENS = "cantidaddecimalesfens";
        public static final String LOG = "log";
        public static final String VERSIONPARAMETRIZACION = "versionparametrizacion";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaSirius.ID + " " + INT_TYPE + " not null," +
                    ColumnaSirius.NUMEROTERMINAL + " " + STRING_TYPE + " null," +
                    ColumnaSirius.TIPOIDENTIFICACION + " " + STRING_TYPE + " null," +
                    ColumnaSirius.RUTASERVIDOR + " " + STRING_TYPE + " null," +
                    ColumnaSirius.VERSIONMAESTROS + " " + STRING_TYPE + " null," +
                    ColumnaSirius.FECHAMAESTROS + " " + STRING_TYPE + " null," +
                    ColumnaSirius.WIFI + " " + STRING_TYPE + " null," +
                    ColumnaSirius.IMPRESORA + " " + STRING_TYPE + " null," +
                    ColumnaSirius.DIRECCIONIMPRESORA + " " + STRING_TYPE + " null," +
                    ColumnaSirius.CONFIRMACION + " " + STRING_TYPE + " null," +
                    ColumnaSirius.SINCRONIZACION + " " + STRING_TYPE + " null," +
                    ColumnaSirius.LONGITUDIMPRESIONFENS + " " + INT_TYPE + " null," +
                    ColumnaSirius.CANTIDADDECIMALESFENS + " " + INT_TYPE + " null," +
                    ColumnaSirius.LOG + " " + STRING_TYPE + " null," +
                    ColumnaSirius.VERSIONPARAMETRIZACION + " " + STRING_TYPE + " null," +
                    " primary key (" + ColumnaSirius.ID + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}
