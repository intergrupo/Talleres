package com.example.santiagolopezgarcia.talleres.data.dao.correria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.modelonegocio.Contrato;
import com.example.dominio.view.ContratoRepositorio;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */
public class ContratoDao extends DaoBase implements ContratoRepositorio {

    public static final String NombreTabla = "sirius_contrato";

    public ContratoDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public boolean tieneRegistros() {
        return this.operadorDatos.numeroRegistros() > 0;
    }

    @Override
    public List<Contrato> cargarContratos() {
        List<Contrato> lista = new ArrayList<>();
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
    public Contrato cargarContratoXCodigo(String codigoContrato) {
        Contrato contrato = new Contrato();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaContrato.CODIGOCONTRATO + " = '" + codigoContrato + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                contrato = convertirCursorAObjeto(cursor);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return contrato;
    }

    @Override
    public boolean guardar(List<Contrato> lista) {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (Contrato contrato : lista) {
            listaContentValues.add(convertirObjetoAContentValues(contrato));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(Contrato dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    @Override
    public boolean actualizar(Contrato dato) {
        ContentValues datos = convertirObjetoAContentValues(dato);
        return this.operadorDatos.actualizar(datos, ColumnaContrato.CODIGOCONTRATO + " = ? "
                , new String[]{dato.getCodigoContrato()});
    }

    @Override
    public boolean eliminar(Contrato dato) {
        return this.operadorDatos.borrar(ColumnaContrato.CODIGOCONTRATO + " = ? "
                , new String[]{dato.getCodigoContrato()}) > 0;
    }

    private Contrato convertirCursorAObjeto(Cursor cursor) {
        Contrato contrato = new Contrato();
        contrato.setCodigoContrato(cursor.getString(cursor.getColumnIndex(ColumnaContrato.CODIGOCONTRATO)));
        contrato.setNombre(cursor.getString(cursor.getColumnIndex(ColumnaContrato.NOMBRE)));
        return contrato;
    }

    private ContentValues convertirObjetoAContentValues(Contrato contrato) {
        ContentValues contentValues = new ContentValues();
        if (!contrato.getCodigoContrato().isEmpty()) {
            contentValues.put(ColumnaContrato.CODIGOCONTRATO, contrato.getCodigoContrato());
        }
        contentValues.put(ColumnaContrato.NOMBRE, contrato.getNombre());
        return contentValues;
    }

    public static class ColumnaContrato {

        public static final String CODIGOCONTRATO = "codigocontrato";
        public static final String NOMBRE = "nombre";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaContrato.CODIGOCONTRATO + " " + STRING_TYPE + " not null," +
                    ColumnaContrato.NOMBRE + " " + STRING_TYPE + " not null," +
                    " primary key (" + ColumnaContrato.CODIGOCONTRATO + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}
