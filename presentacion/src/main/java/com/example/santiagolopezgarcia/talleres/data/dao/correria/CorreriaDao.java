package com.example.santiagolopezgarcia.talleres.data.dao.correria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.correria.CorreriaRepositorio;
import com.example.dominio.modelonegocio.Correria;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;
import com.example.utilidades.helpers.BooleanHelper;
import com.example.utilidades.helpers.StringHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class CorreriaDao extends DaoBase implements CorreriaRepositorio {

    public static final String NombreTabla = "sirius_correria";

    public CorreriaDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public List<Correria> cargarCorrerias() {
        List<Correria> lista = new ArrayList<>();
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
    public List<Correria> cargarXContrato(String codigoContrato) {
        List<Correria> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaCorreria.CODIGOCONTRATO + " = '" + codigoContrato + "'");
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
    public List<Correria> cargarXContratoYSinContrato(String codigoContrato) {
        List<Correria> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaCorreria.CODIGOCONTRATO + " = '" + codigoContrato + "'" +
                " OR " + ColumnaCorreria.CODIGOCONTRATO + " IS NULL");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                lista.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    public boolean guardar(List<Correria> lista) {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (Correria correria : lista) {
            listaContentValues.add(convertirObjetoAContentValues(correria));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public boolean guardar(Correria dato) throws ParseException {
        ContentValues contentValues = convertirObjetoAContentValues(dato);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    public Correria cargar(String codigo) {
        Correria correria = new Correria();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaCorreria.CODIGOCORRERIA +
                " = '" + codigo + "'");
        if (cursor.moveToFirst()) {
            if (!cursor.isAfterLast()) {
                correria = convertirCursorAObjeto(cursor);
            }
        }
        cursor.close();
        return correria;
    }

    @Override
    public boolean actualizar(Correria correria) {
        ContentValues contentCorreria = convertirObjetoAContentValues(correria);
        return this.operadorDatos.actualizar(contentCorreria, ColumnaCorreria.CODIGOCORRERIA + " = '" + correria.getCodigoCorreria() + "'");
    }

    @Override
    public boolean eliminar(Correria correria) {
        return this.operadorDatos.borrar("codigocorreria = ? "
                , new String[]{correria.getCodigoCorreria()}) > 0;
    }

    @Override
    public List<Correria> cargarCorrerias(String codigo) {
        List<Correria> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaCorreria.CODIGOCORRERIA +
                " = '" + codigo + "'");

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
    public boolean existe(String codigo) {
        boolean resultado = false;
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaCorreria.CODIGOCORRERIA +
                " = '" + codigo + "'");
        if (cursor.moveToFirst()) {
            resultado = true;
        }
        cursor.close();
        return resultado;
    }

    @Override
    public boolean actualizarFechaDescarga(String codigoCorreria, String fechaDescarga) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ColumnaCorreria.FECHAULTIMADESCARGA, fechaDescarga);
        return this.operadorDatos.actualizar(contentValues, ColumnaCorreria.CODIGOCORRERIA + " = '" + codigoCorreria + "'");
    }

    private Correria convertirCursorAObjeto(Cursor cursor) {
        Correria correria = new Correria();
        correria.setCodigoCorreria(cursor.getString(cursor.getColumnIndex(ColumnaCorreria.CODIGOCORRERIA)));
        correria.setDescripcion(cursor.getString(cursor.getColumnIndex(ColumnaCorreria.DESCRIPCION)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaCorreria.ADVERTENCIA))) {
            correria.setAdvertencia(cursor.getString(cursor.getColumnIndex(ColumnaCorreria.ADVERTENCIA)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaCorreria.OBSERVACION))) {
            correria.setObservacion(cursor.getString(cursor.getColumnIndex(ColumnaCorreria.OBSERVACION)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaCorreria.CODIGOEMPRESA))) {
            correria.getEmpresa().setCodigoEmpresa(cursor.getString(cursor.getColumnIndex(ColumnaCorreria.CODIGOEMPRESA)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaCorreria.CODIGOCONTRATO))) {
            correria.setCodigoContrato(cursor.getString(cursor.getColumnIndex(ColumnaCorreria.CODIGOCONTRATO)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaCorreria.INFORMACION))) {
            correria.setInformacion(cursor.getString(cursor.getColumnIndex(ColumnaCorreria.INFORMACION)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaCorreria.FECHACARGA))) {
            correria.setFechaCarga(cursor.getString(cursor.getColumnIndex(ColumnaCorreria.FECHACARGA)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaCorreria.FECHAINICIOCORRERIA))) {
            correria.setFechaInicioCorreria(
                    cursor.getString(cursor.getColumnIndex(ColumnaCorreria.FECHAINICIOCORRERIA)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaCorreria.FECHAULTIMACORRERIA))) {
            correria.setFechaUltimaCorreria(
                    cursor.getString(cursor.getColumnIndex(ColumnaCorreria.FECHAULTIMACORRERIA)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaCorreria.FECHAPROGRAMACION))) {
            correria.setFechaProgramacion(
                    cursor.getString(cursor.getColumnIndex(ColumnaCorreria.FECHAPROGRAMACION)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaCorreria.FECHAULTIMOENVIO))) {
            correria.setFechaUltimoEnvio(cursor.getString(cursor.getColumnIndex(ColumnaCorreria.FECHAULTIMOENVIO)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaCorreria.FECHAULTIMORECIBO))) {
            correria.setFechaUltimoRecibo(cursor.getString(cursor.getColumnIndex(ColumnaCorreria.FECHAULTIMORECIBO)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaCorreria.FECHAFINJORNADA))) {
            correria.setFechaFinJornada(cursor.getString(cursor.getColumnIndex(ColumnaCorreria.FECHAFINJORNADA)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaCorreria.FECHAULTIMADESCARGA))) {
            correria.setFechaUltimaDescarga(cursor.getString(cursor.getColumnIndex(ColumnaCorreria.FECHAULTIMADESCARGA)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaCorreria.PARAMETROS))) {
            correria.setParametros(cursor.getString(cursor.getColumnIndex(ColumnaCorreria.PARAMETROS)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaCorreria.RECARGACORRERIA))) {
            correria.setRecargaCorreria(StringHelper.ToBoolean(cursor.getString(cursor.getColumnIndex(ColumnaCorreria.RECARGACORRERIA))));
        }
        return correria;
    }

    private ContentValues convertirObjetoAContentValues(Correria correria) {
        ContentValues contentValues = new ContentValues();
        if (!correria.getCodigoCorreria().isEmpty()) {
            contentValues.put(ColumnaCorreria.CODIGOCORRERIA, correria.getCodigoCorreria());
        }
        contentValues.put(ColumnaCorreria.DESCRIPCION, correria.getDescripcion());
        contentValues.put(ColumnaCorreria.ADVERTENCIA, correria.getAdvertencia());
        contentValues.put(ColumnaCorreria.OBSERVACION, correria.getObservacion());
        contentValues.put(ColumnaCorreria.FECHAULTIMACORRERIA, correria.getFechaUltimaCorreria());
        contentValues.put(ColumnaCorreria.FECHAINICIOCORRERIA, correria.getFechaInicioCorreria());
        contentValues.put(ColumnaCorreria.FECHAPROGRAMACION, correria.getFechaProgramacion());
        if (!correria.getEmpresa().getCodigoEmpresa().isEmpty()) {
            contentValues.put(ColumnaCorreria.CODIGOEMPRESA, correria.getEmpresa().getCodigoEmpresa());
        } else {
            contentValues.putNull(ColumnaCorreria.CODIGOEMPRESA);
        }
        contentValues.put(ColumnaCorreria.CODIGOCONTRATO, correria.getCodigoContrato());
        contentValues.put(ColumnaCorreria.INFORMACION, correria.getInformacion());
        contentValues.put(ColumnaCorreria.FECHACARGA, correria.getFechaCarga());
        contentValues.put(ColumnaCorreria.FECHAULTIMOENVIO, correria.getFechaUltimoEnvio());
        contentValues.put(ColumnaCorreria.FECHAULTIMORECIBO, correria.getFechaUltimoRecibo());
        contentValues.put(ColumnaCorreria.FECHAFINJORNADA, correria.getFechaFinJornada());
        contentValues.put(ColumnaCorreria.FECHAULTIMADESCARGA, correria.getFechaUltimaDescarga());
        contentValues.put(ColumnaCorreria.PARAMETROS, correria.getParametros());
        contentValues.put(ColumnaCorreria.RECARGACORRERIA, BooleanHelper.ToString(correria.isRecargaCorreria()));
        return contentValues;
    }

    public static class ColumnaCorreria {

        public static final String CODIGOCORRERIA = "codigocorreria";
        public static final String DESCRIPCION = "descripcion";
        public static final String ADVERTENCIA = "advertencia";
        public static final String OBSERVACION = "observacion";
        public static final String FECHAPROGRAMACION = "fechaprogramacion";
        public static final String CODIGOEMPRESA = "codigoempresa";
        public static final String CODIGOCONTRATO = "codigocontrato";
        public static final String INFORMACION = "informacion";
        public static final String FECHACARGA = "fechacarga";
        public static final String FECHAINICIOCORRERIA = "fechainiciocorreria";
        public static final String FECHAULTIMACORRERIA = "fechaultimacorreria";
        public static final String FECHAULTIMOENVIO = "fechaultimoenvio";
        public static final String FECHAULTIMORECIBO = "fechaultimorecibo";
        public static final String FECHAFINJORNADA = "fechafinjornada";
        public static final String FECHAULTIMADESCARGA = "fechaultimadescarga";
        public static final String PARAMETROS = "parametros";
        public static final String RECARGACORRERIA = "recargacorreria";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaCorreria.CODIGOCORRERIA + " " + STRING_TYPE + " not null," +
                    ColumnaCorreria.DESCRIPCION + " " + STRING_TYPE + " not null," +
                    ColumnaCorreria.ADVERTENCIA + " " + STRING_TYPE + " null," +
                    ColumnaCorreria.OBSERVACION + " " + STRING_TYPE + " null," +
                    ColumnaCorreria.FECHAPROGRAMACION + " " + STRING_TYPE + " null," +
                    ColumnaCorreria.CODIGOEMPRESA + " " + STRING_TYPE + " not null," +
                    ColumnaCorreria.CODIGOCONTRATO + " " + STRING_TYPE + " null," +
                    ColumnaCorreria.INFORMACION + " " + STRING_TYPE + " null," +
                    ColumnaCorreria.FECHACARGA + " " + STRING_TYPE + " null," +
                    ColumnaCorreria.FECHAINICIOCORRERIA + " " + STRING_TYPE + " null," +
                    ColumnaCorreria.FECHAULTIMACORRERIA + " " + STRING_TYPE + " null," +
                    ColumnaCorreria.FECHAULTIMOENVIO + " " + STRING_TYPE + " null," +
                    ColumnaCorreria.FECHAULTIMORECIBO + " " + STRING_TYPE + " null," +
                    ColumnaCorreria.FECHAFINJORNADA + " " + STRING_TYPE + " null," +
                    ColumnaCorreria.FECHAULTIMADESCARGA + " " + STRING_TYPE + " null," +
                    ColumnaCorreria.PARAMETROS + " " + STRING_TYPE + " null," +
                    ColumnaCorreria.RECARGACORRERIA + " " + STRING_TYPE + " null," +
                    " primary key (" + ColumnaCorreria.CODIGOCORRERIA + ")" +
                    " FOREIGN KEY (" + ColumnaCorreria.CODIGOEMPRESA + ") REFERENCES " +
                    EmpresaDao.NombreTabla + "( " + EmpresaDao.ColumnaEmpresa.CODIGOEMPRESA + ")," +
                    " FOREIGN KEY (" + ColumnaCorreria.CODIGOCONTRATO + ") REFERENCES " +
                    ContratoDao.NombreTabla + "( " + ContratoDao.ColumnaContrato.CODIGOCONTRATO + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;


}