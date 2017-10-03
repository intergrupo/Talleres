package com.example.santiagolopezgarcia.talleres.data.dao.notificacion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.modelonegocio.Item;
import com.example.dominio.bussinesslogic.notificacion.ItemRepositorio;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class ItemDao extends DaoBase implements ItemRepositorio {

    static final String NombreTabla = "sirius_item";

    public ItemDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }


    @Override
    public List<Item> cargar() {
        List<Item> lista = new ArrayList<>();
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
    public Item cargarItem(String codigoItem) {
        Item item = new Item();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaItem.CODIGOITEM + " = '" + codigoItem + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                item = convertirCursorAObjeto(cursor);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return item;
    }

    @Override
    public List<String> cargarCodigosItemParaCargarAdjuntos() {
        List<String> codigosItem=new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT " + ColumnaItem.CODIGOITEM + " FROM " + NombreTabla +
                " WHERE " + ItemDao.ColumnaItem.RUTINA + " IN ('RutinaStandar3', 'RutinaStandar5', 'RutinaStandar7', 'RutinaStandar8')");

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                codigosItem.add(cursor.getString(cursor.getColumnIndex(ColumnaItem.CODIGOITEM)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return codigosItem;
    }

    @Override
    public Item consultarItemReporteNotificacion(String codigoCorreria, String codigoOrdenTrabajo, String codigoTarea, String codigoItem, String codigoNotificacion) {
        Item item = new Item();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                "  INNER JOIN " + ItemXNotificacionDao.NombreTabla + " ON " +
                ItemXNotificacionDao.NombreTabla + "." + ItemXNotificacionDao.ColumnaItemXNotificacion.CODIGOITEM + " = " + NombreTabla + "." + ColumnaItem.CODIGOITEM +
                " INNER JOIN " + NotificacionDao.NombreTabla + " ON " +
                NotificacionDao.NombreTabla + "." + NotificacionDao.ColumnaNotificacion.CODIGONOTIFICACION + " = " + ItemXNotificacionDao.NombreTabla + "." +ItemXNotificacionDao.ColumnaItemXNotificacion.CODIGONOTIFICACION +
                " WHERE " +ItemXNotificacionDao.NombreTabla + "." + ItemXNotificacionDao.ColumnaItemXNotificacion.CODIGOITEM + " = '" + codigoItem + "'" +
                " AND " +  ItemXNotificacionDao.NombreTabla + "." + ItemXNotificacionDao.ColumnaItemXNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "'" );
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                item = convertirCursorAObjeto(cursor);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return item;
    }

    private Item convertirCursorAObjeto(Cursor cursor) {
        Item item = new Item();
        item.setCodigoItem(cursor.getString(cursor.getColumnIndex(ColumnaItem.CODIGOITEM)));
        item.setDescripcion(cursor.getString(cursor.getColumnIndex(ColumnaItem.DESCRIPCION)));
        item.setTipoItem(cursor.getString(cursor.getColumnIndex(ColumnaItem.TIPOITEM)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaItem.MASCARA))) {
            item.setMascara(cursor.getString(cursor.getColumnIndex(ColumnaItem.MASCARA)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaItem.FORMATO))) {
            item.setFormato(cursor.getString(cursor.getColumnIndex(ColumnaItem.FORMATO)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaItem.TABLALISTA))) {
            item.setTablaLista(cursor.getString(cursor.getColumnIndex(ColumnaItem.TABLALISTA)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaItem.CAMPOLLAVEITEM))) {
            item.setCampoLlaveItem(cursor.getString(cursor.getColumnIndex(ColumnaItem.CAMPOLLAVEITEM)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaItem.CAMPOVALORLISTA))) {
            item.setCampoValorLista(cursor.getString(cursor.getColumnIndex(ColumnaItem.CAMPOVALORLISTA)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaItem.CAMPODISPLAYLISTA))) {
            item.setCampoDisplayLista(cursor.getString(cursor.getColumnIndex(ColumnaItem.CAMPODISPLAYLISTA)));
        }
        item.setRutina(cursor.getString(cursor.getColumnIndex(ColumnaItem.RUTINA)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaItem.PARAMETROS))) {
            item.setParametros(cursor.getString(cursor.getColumnIndex(ColumnaItem.PARAMETROS)));
        }
        return item;
    }

    private ContentValues convertirObjetoAContentValues(Item item) {
        ContentValues contentValues = new ContentValues();
        if (!item.getCodigoItem().isEmpty()) {
            contentValues.put(ColumnaItem.CODIGOITEM, item.getCodigoItem());
        }
        contentValues.put(ColumnaItem.DESCRIPCION, item.getDescripcion());
        contentValues.put(ColumnaItem.TIPOITEM, item.getTipoItem());
        contentValues.put(ColumnaItem.MASCARA, item.getMascara());
        contentValues.put(ColumnaItem.FORMATO, item.getFormato());
        contentValues.put(ColumnaItem.TABLALISTA, item.getTablaLista());
        contentValues.put(ColumnaItem.CAMPOLLAVEITEM, item.getCampoLlaveItem());
        contentValues.put(ColumnaItem.CAMPOVALORLISTA, item.getCampoValorLista());
        contentValues.put(ColumnaItem.CAMPODISPLAYLISTA, item.getCampoDisplayLista());
        contentValues.put(ColumnaItem.RUTINA, item.getRutina());
        contentValues.put(ColumnaItem.PARAMETROS, item.getParametros());
        return contentValues;
    }

    @Override
    public boolean guardar(List<Item> lista) throws ParseException {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (Item item : lista) {
            listaContentValues.add(convertirObjetoAContentValues(item));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(Item dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    @Override
    public boolean actualizar(Item dato) {
        ContentValues datos = convertirObjetoAContentValues(dato);
        return this.operadorDatos.actualizar(datos, ColumnaItem.CODIGOITEM + " = ? "
                , new String[]{dato.getCodigoItem()});
    }

    @Override
    public boolean eliminar(Item dato) {
        return this.operadorDatos.borrar(ColumnaItem.CODIGOITEM + " = ? "
                , new String[]{dato.getCodigoItem()}) > 0;
    }

    public static class ColumnaItem {

        public static final String CODIGOITEM = "codigoitem";
        public static final String DESCRIPCION = "descripcion";
        public static final String TIPOITEM = "tipoitem";
        public static final String MASCARA = "mascara";
        public static final String FORMATO = "formato";
        public static final String TABLALISTA = "tablalista";
        public static final String CAMPOLLAVEITEM = "campollaveitem";
        public static final String CAMPOVALORLISTA = "campovalorlista";
        public static final String CAMPODISPLAYLISTA = "campodisplaylista";
        public static final String RUTINA = "rutina";
        public static final String PARAMETROS = "parametros";

    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaItem.CODIGOITEM + " " + STRING_TYPE + " not null," +
                    ColumnaItem.DESCRIPCION + " " + STRING_TYPE + " not null," +
                    ColumnaItem.TIPOITEM + " " + STRING_TYPE + " not null," +
                    ColumnaItem.MASCARA + " " + STRING_TYPE + " null," +
                    ColumnaItem.FORMATO + " " + STRING_TYPE + " null," +
                    ColumnaItem.TABLALISTA + " " + STRING_TYPE + " null," +
                    ColumnaItem.CAMPOLLAVEITEM + " " + STRING_TYPE + " null," +
                    ColumnaItem.CAMPOVALORLISTA + " " + STRING_TYPE + " null," +
                    ColumnaItem.CAMPODISPLAYLISTA + " " + STRING_TYPE + " null," +
                    ColumnaItem.RUTINA + " " + STRING_TYPE + " not null," +
                    ColumnaItem.PARAMETROS + " " + STRING_TYPE + " null," +
                    " primary key (" + ColumnaItem.CODIGOITEM + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}