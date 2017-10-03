package com.example.santiagolopezgarcia.talleres.data.dao.trabajo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.modelonegocio.TrabajoXContrato;
import com.example.dominio.bussinesslogic.trabajo.TrabajoXContratoRepositorio;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;
import com.example.santiagolopezgarcia.talleres.data.dao.correria.ContratoDao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class TrabajoXContratoDao extends DaoBase implements TrabajoXContratoRepositorio {

    static final String NombreTabla = "sirius_trabajoxcontrato";

    public TrabajoXContratoDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public List<TrabajoXContrato> cargarTrabajoXContratos(String codigoContrato) {
        List<TrabajoXContrato> listaTrabajoXContrato = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " txc " +
                " JOIN " + TrabajoDao.NombreTabla + " t" +
                " ON txc." + ColumnaTrabajoXContrato.CODIGOTRABAJO + " = t." + TrabajoDao.ColumnaTrabajo.CODIGOTRABAJO +
                " WHERE txc." + ColumnaTrabajoXContrato.CODIGOCONTRATO + " = '" + codigoContrato + "'" +
                " ORDER BY t." + TrabajoDao.ColumnaTrabajo.NOMBRE);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listaTrabajoXContrato.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listaTrabajoXContrato;
    }

    @Override
    public TrabajoXContrato cargarTrabajoXContrato(String codigoContrato, String codigoTrabajo) {
        TrabajoXContrato trabajoXContrato = new TrabajoXContrato();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaTrabajoXContrato.CODIGOCONTRATO + " = '" + codigoContrato + "' " +
                "AND " + ColumnaTrabajoXContrato.CODIGOTRABAJO + " = '" + codigoTrabajo + "' ");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                trabajoXContrato = convertirCursorAObjeto(cursor);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return trabajoXContrato;
    }

    private TrabajoXContrato convertirCursorAObjeto(Cursor cursor) {
        TrabajoXContrato trabajoXContrato = new TrabajoXContrato();
        trabajoXContrato.getContrato().setCodigoContrato(cursor.getString(cursor.getColumnIndex(ColumnaTrabajoXContrato.CODIGOCONTRATO)));
        trabajoXContrato.getTrabajo().setCodigoTrabajo(cursor.getString(cursor.getColumnIndex(ColumnaTrabajoXContrato.CODIGOTRABAJO)));
        return trabajoXContrato;
    }

    @Override
    public boolean guardar(List<TrabajoXContrato> lista) throws ParseException {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (TrabajoXContrato trabajoXContrato : lista) {
            listaContentValues.add(convertirObjetoAContentValues(trabajoXContrato));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(TrabajoXContrato dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    @Override
    public boolean actualizar(TrabajoXContrato dato) {
        ContentValues datos = convertirObjetoAContentValues(dato);
        return this.operadorDatos.actualizar(datos, ColumnaTrabajoXContrato.CODIGOCONTRATO + " = ? " +
                        "AND " + ColumnaTrabajoXContrato.CODIGOTRABAJO + " = ? "
                , new String[]{dato.getContrato().getCodigoContrato()});
    }

    @Override
    public boolean eliminar(TrabajoXContrato dato) {
        return this.operadorDatos.borrar(ColumnaTrabajoXContrato.CODIGOCONTRATO + " = ? " +
                        "AND " + ColumnaTrabajoXContrato.CODIGOTRABAJO + " = ? "
                , new String[]{dato.getContrato().getCodigoContrato()}) > 0;
    }

    private ContentValues convertirObjetoAContentValues(TrabajoXContrato trabajoXContrato) {
        ContentValues contentValues = new ContentValues();
        if (!trabajoXContrato.getContrato().getCodigoContrato().isEmpty()) {
            contentValues.put(ColumnaTrabajoXContrato.CODIGOCONTRATO, trabajoXContrato.getContrato().getCodigoContrato());
        }
        if (!trabajoXContrato.getTrabajo().getCodigoTrabajo().isEmpty()) {
            contentValues.put(ColumnaTrabajoXContrato.CODIGOTRABAJO, trabajoXContrato.getTrabajo().getCodigoTrabajo());
        }
        return contentValues;
    }

    public static class ColumnaTrabajoXContrato {

        public static final String CODIGOCONTRATO = "codigocontrato";
        public static final String CODIGOTRABAJO = "codigotrabajo";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaTrabajoXContrato.CODIGOCONTRATO + " " + STRING_TYPE + " not null," +
                    ColumnaTrabajoXContrato.CODIGOTRABAJO + " " + STRING_TYPE + " not null," +
                    " primary key (" + ColumnaTrabajoXContrato.CODIGOCONTRATO + "," +
                    ColumnaTrabajoXContrato.CODIGOTRABAJO + ")" +
                    " FOREIGN KEY (" + ColumnaTrabajoXContrato.CODIGOCONTRATO + ") REFERENCES " +
                    ContratoDao.NombreTabla + "( " + ContratoDao.ColumnaContrato.CODIGOCONTRATO + "), " +
                    " FOREIGN KEY (" + ColumnaTrabajoXContrato.CODIGOTRABAJO + ") REFERENCES " +
                    TrabajoDao.NombreTabla + "( " + TrabajoDao.ColumnaTrabajo.CODIGOTRABAJO + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}