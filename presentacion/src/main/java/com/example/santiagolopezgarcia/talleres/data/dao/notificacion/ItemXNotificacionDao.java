package com.example.santiagolopezgarcia.talleres.data.dao.notificacion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.modelonegocio.ItemXNotificacion;
import com.example.dominio.bussinesslogic.notificacion.ItemXNotificacionRepositorio;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;
import com.example.utilidades.helpers.BooleanHelper;
import com.example.utilidades.helpers.StringHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class ItemXNotificacionDao extends DaoBase implements ItemXNotificacionRepositorio {

    static final String NombreTabla = "sirius_itemxnotificacion";

    public ItemXNotificacionDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }


    @Override
    public List<ItemXNotificacion> cargar() {
        List<ItemXNotificacion> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla);
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
    public List<ItemXNotificacion> cargarXCodigoNotificacion(String codigoNotificacion) {
        List<ItemXNotificacion> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaItemXNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "'" +
                " ORDER BY " + ColumnaItemXNotificacion.ORDEN);
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
    public List<ItemXNotificacion> cargarXCodigoNotificacion(String codigoNotificacion, String codigoOT) {
        List<ItemXNotificacion> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " ixn" +
                " JOIN " + ReporteNotificacionDao.NombreTabla + " rn" +
                " ON ixn." + ColumnaItemXNotificacion.CODIGONOTIFICACION +
                " = rn." + ReporteNotificacionDao.ColumnaReporteNotificacion.CODIGONOTIFICACION +
                " AND ixn." + ColumnaItemXNotificacion.CODIGOITEM +
                " = rn." + ReporteNotificacionDao.ColumnaReporteNotificacion.CODIGOITEM +
                " WHERE ixn." + ColumnaItemXNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "'" +
                " AND rn." + ReporteNotificacionDao.ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " = '" + codigoOT + "' " +
                " ORDER BY " + ColumnaItemXNotificacion.ORDEN);
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
    public List<ItemXNotificacion> cargarSinDatos(String codigoNotificacion, String codigoCorreria, String ordenTrabajo,
                                                  String codigoTarea, String codigoLabor) {
        List<ItemXNotificacion> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " it " +
                " WHERE it." + ColumnaItemXNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "'"+
                " AND it." + ColumnaItemXNotificacion.CODIGOITEM +
                " NOT IN (SELECT it." +ColumnaItemXNotificacion.CODIGOITEM  +" FROM " + NombreTabla + " it" +
                " INNER JOIN " + ReporteNotificacionDao.NombreTabla + " r" +
                " ON r." + ReporteNotificacionDao.ColumnaReporteNotificacion.CODIGONOTIFICACION +
                " = it." + ColumnaItemXNotificacion.CODIGONOTIFICACION +
                " AND r." + ReporteNotificacionDao.ColumnaReporteNotificacion.CODIGOITEM +
                " = it." + ColumnaItemXNotificacion.CODIGOITEM +
                " WHERE r." + ColumnaItemXNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "'" +
                " AND r." + ReporteNotificacionDao.ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "'" +
                " AND r." + ReporteNotificacionDao.ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " = '" + ordenTrabajo + "'" +
                " AND r." + ReporteNotificacionDao.ColumnaReporteNotificacion.CODIGOTAREA + " = '" + codigoTarea + "'" +
                " AND r." + ReporteNotificacionDao.ColumnaReporteNotificacion.CODIGOLABOR + " = '" + codigoLabor + "'"
                +")" +
                " ORDER BY " + ColumnaItemXNotificacion.ORDEN);

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
    public List<ItemXNotificacion> cargarXCodigoNotificacion(String codigoNotificacion, String codigoCorreria, String codigoOT,
                                                             String codigoTarea, String codigoLabor) {
        List<ItemXNotificacion> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT DISTINCT * FROM " + NombreTabla + " ixn " +
                "JOIN " + ReporteNotificacionDao.NombreTabla + " rn " +
                "ON ixn." + ColumnaItemXNotificacion.CODIGONOTIFICACION +
                " = rn." + ReporteNotificacionDao.ColumnaReporteNotificacion.CODIGONOTIFICACION +
                " AND ixn." + ColumnaItemXNotificacion.CODIGOITEM +
                " = rn." + ReporteNotificacionDao.ColumnaReporteNotificacion.CODIGOITEM +
                " WHERE rn." + ReporteNotificacionDao.ColumnaReporteNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "'" +
                " AND rn." + ReporteNotificacionDao.ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "'" +
                " AND rn." + ReporteNotificacionDao.ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " = '" + codigoOT + "'" +
                " AND rn." + ReporteNotificacionDao.ColumnaReporteNotificacion.CODIGOTAREA + " = '" + codigoTarea + "'" +
                " AND rn." + ReporteNotificacionDao.ColumnaReporteNotificacion.CODIGOLABOR + " = '" + codigoLabor + "'" +
                " AND rn." + ReporteNotificacionDao.ColumnaReporteNotificacion.DESCRIPCION + " <> ''" +
                " AND rn." + ReporteNotificacionDao.ColumnaReporteNotificacion.DESCRIPCION + " IS NOT NULL " +
                " Group by rn." + ReporteNotificacionDao.ColumnaReporteNotificacion.CODIGOITEM +
                " ORDER BY ixn." + ColumnaItemXNotificacion.ORDEN);
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
    public ItemXNotificacion cargarXCodigoNotificacionEItem(String codigoNotificacion,
                                                            String codigoItem) {
        ItemXNotificacion itemXNotificacion = new ItemXNotificacion();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaItemXNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "'" +
                " AND " + ColumnaItemXNotificacion.CODIGOITEM + " = '" + codigoItem + "'" +
                " ORDER BY " + ColumnaItemXNotificacion.ORDEN);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                itemXNotificacion = convertirCursorAObjeto(cursor);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return itemXNotificacion;
    }

    @Override
    public List<ItemXNotificacion> cargarObligatoriosXCodigoNotificacion(String codigoNotificacion) {
        List<ItemXNotificacion> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaItemXNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "'" +
                " AND " + ColumnaItemXNotificacion.OBLIGATORIO + " = 'S'" +
                " ORDER BY " + ColumnaItemXNotificacion.ORDEN);
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
    public List<ItemXNotificacion> cargarNoObligatoriosXCodigoNotificacion(String codigoNotificacion) {
        List<ItemXNotificacion> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaItemXNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "'" +
                " AND " + ColumnaItemXNotificacion.OBLIGATORIO + " = 'N'" +
                " ORDER BY " + ColumnaItemXNotificacion.ORDEN);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                lista.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    private ItemXNotificacion convertirCursorAObjeto(Cursor cursor) {
        ItemXNotificacion itemXNotificacion = new ItemXNotificacion();
        itemXNotificacion.setCodigoNotificacion(cursor.getString(cursor.getColumnIndex(ColumnaItemXNotificacion.CODIGONOTIFICACION)));
        itemXNotificacion.setCodigoItem(cursor.getString(cursor.getColumnIndex(ColumnaItemXNotificacion.CODIGOITEM)));
        itemXNotificacion.setOrden(cursor.getInt(cursor.getColumnIndex(ColumnaItemXNotificacion.ORDEN)));
        itemXNotificacion.setObligatorio(StringHelper.ToBoolean(cursor.getString(cursor.getColumnIndex(ColumnaItemXNotificacion.OBLIGATORIO))));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaItemXNotificacion.DESCARGA))) {
            itemXNotificacion.setDescarga(StringHelper.ToBoolean(cursor.getString(cursor.getColumnIndex(ColumnaItemXNotificacion.DESCARGA))));
        }
        return itemXNotificacion;
    }

    private ContentValues convertirObjetoAContentValues(ItemXNotificacion itemXNotificacion) {
        ContentValues contentValues = new ContentValues();
        if (!itemXNotificacion.getCodigoNotificacion().isEmpty()) {
            contentValues.put(ColumnaItemXNotificacion.CODIGONOTIFICACION, itemXNotificacion.getCodigoNotificacion());
        }
        if (!itemXNotificacion.getCodigoItem().isEmpty()) {
            contentValues.put(ColumnaItemXNotificacion.CODIGOITEM, itemXNotificacion.getCodigoItem());
        }
        contentValues.put(ColumnaItemXNotificacion.ORDEN, itemXNotificacion.getOrden());
        contentValues.put(ColumnaItemXNotificacion.OBLIGATORIO, BooleanHelper.ToString(itemXNotificacion.isObligatorio()));
        contentValues.put(ColumnaItemXNotificacion.DESCARGA, BooleanHelper.ToString(itemXNotificacion.isDescarga()));
        return contentValues;
    }

    @Override
    public boolean guardar(List<ItemXNotificacion> lista) throws ParseException {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (ItemXNotificacion itemXNotificacion : lista) {
            listaContentValues.add(convertirObjetoAContentValues(itemXNotificacion));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(ItemXNotificacion dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    @Override
    public boolean actualizar(ItemXNotificacion dato) {
        ContentValues datos = convertirObjetoAContentValues(dato);
        return this.operadorDatos.actualizar(datos, ColumnaItemXNotificacion.CODIGONOTIFICACION + " = ? " +
                        "AND " + ColumnaItemXNotificacion.CODIGOITEM + " = ? "
                , new String[]{dato.getCodigoNotificacion(), dato.getCodigoItem()});
    }

    @Override
    public boolean eliminar(ItemXNotificacion dato) {
        return this.operadorDatos.borrar(ColumnaItemXNotificacion.CODIGONOTIFICACION + " = ? " +
                        "AND " + ColumnaItemXNotificacion.CODIGOITEM + " = ? "
                , new String[]{dato.getCodigoNotificacion(), dato.getCodigoItem()}) > 0;
    }

    public static class ColumnaItemXNotificacion {

        public static final String CODIGONOTIFICACION = "codigonotificacion";
        public static final String CODIGOITEM = "codigoitem";
        public static final String ORDEN = "orden";
        public static final String OBLIGATORIO = "obligatorio";
        public static final String DESCARGA = "descarga";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaItemXNotificacion.CODIGONOTIFICACION + " " + STRING_TYPE + " not null," +
                    ColumnaItemXNotificacion.CODIGOITEM + " " + STRING_TYPE + " not null," +
                    ColumnaItemXNotificacion.ORDEN + " " + INT_TYPE + " not null," +
                    ColumnaItemXNotificacion.OBLIGATORIO + " " + STRING_TYPE + " not null," +
                    ColumnaItemXNotificacion.DESCARGA + " " + STRING_TYPE + " null," +
                    " primary key (" + ColumnaItemXNotificacion.CODIGONOTIFICACION + "," +
                    ColumnaItemXNotificacion.CODIGOITEM + ")" +
                    " FOREIGN KEY (" + ColumnaItemXNotificacion.CODIGOITEM + ") REFERENCES " +
                    ItemDao.NombreTabla + "( " + ItemDao.ColumnaItem.CODIGOITEM + ")," +
                    " FOREIGN KEY (" + ColumnaItemXNotificacion.CODIGONOTIFICACION + ") REFERENCES " +
                    NotificacionDao.NombreTabla + "( " + NotificacionDao.ColumnaNotificacion.CODIGONOTIFICACION + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}