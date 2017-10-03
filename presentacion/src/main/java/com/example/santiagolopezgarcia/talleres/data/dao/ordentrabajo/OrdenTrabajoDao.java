package com.example.santiagolopezgarcia.talleres.data.dao.ordentrabajo;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.bussinesslogic.ordentrabajo.OrdenTrabajoRepositorio;
import com.example.dominio.modelonegocio.ListaOrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;
import com.example.dominio.modelonegocio.Trabajo;
import com.example.dominio.modelonegocio.TrabajoXOrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;
import com.example.santiagolopezgarcia.talleres.data.dao.acceso.MunicipioDao;
import com.example.santiagolopezgarcia.talleres.data.dao.correria.CorreriaDao;
import com.example.santiagolopezgarcia.talleres.data.dao.trabajo.TrabajoXOrdenTrabajoDao;
import com.example.utilidades.helpers.BooleanHelper;
import com.example.utilidades.helpers.DateHelper;
import com.example.utilidades.helpers.StringHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class OrdenTrabajoDao extends DaoBase implements OrdenTrabajoRepositorio {

    public static final String NombreTabla = "sirius_ordentrabajo";

    public OrdenTrabajoDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public ListaOrdenTrabajo cargarOrdenesTrabajo(String codigoCorreria) {
        ListaOrdenTrabajo lista = new ListaOrdenTrabajo();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaOrdenTrabajo.CODIGOCORRERIA
                + " = '" + codigoCorreria + "' ORDER BY " + ColumnaOrdenTrabajo.SECUENCIA);
        if (cursor.moveToFirst()) {
            OrdenTrabajo ordenTrabajo;
            int concecutivo = 1;
            while (!cursor.isAfterLast()) {
                ordenTrabajo = convertirCursorAObjeto(cursor);
                ordenTrabajo.setConsecutivo(concecutivo);
                concecutivo++;
                lista.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    @Override
    public ListaOrdenTrabajo cargar() {
        ListaOrdenTrabajo lista = new ListaOrdenTrabajo();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " ORDER BY " + ColumnaOrdenTrabajo.SECUENCIA);
        try {
            if (cursor.moveToFirst()) {
                OrdenTrabajo ordenTrabajo;
                int consecutivo = 1;
                while (!cursor.isAfterLast()) {
                    ordenTrabajo = convertirCursorAObjeto(cursor);
                    ordenTrabajo.setConsecutivo(consecutivo);
                    consecutivo++;
                    lista.add(convertirCursorAObjeto(cursor));
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
        }
        return lista;
    }

    @Override
    public OrdenTrabajo cargar(String codigoCorreria, String codigoOrdenTrabajo) {
        OrdenTrabajo ordenTrabajo = new OrdenTrabajo();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaOrdenTrabajo.CODIGOCORRERIA
                + " = '" + codigoCorreria + "' AND " + ColumnaOrdenTrabajo.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "'");
        try {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    ordenTrabajo = convertirCursorAObjeto(cursor);
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
        }
        return ordenTrabajo;
    }

    @Override
    public boolean guardar(OrdenTrabajo ordenTrabajo) throws ParseException {
        ContentValues datos = convertirObjetoAContentValues(ordenTrabajo);
        this.operadorDatos.insertar(datos);
        return true;
    }

    @Override
    public boolean actualizar(OrdenTrabajo ordenTrabajoActiva) {
        ContentValues datos = convertirObjetoAContentValues(ordenTrabajoActiva);
        return this.operadorDatos.actualizar(datos, ColumnaOrdenTrabajo.CODIGOCORRERIA + " = ? AND " +
                        ColumnaOrdenTrabajo.CODIGOORDENTRABAJO + " = ? "
                , new String[]{ordenTrabajoActiva.getCodigoCorreria(), ordenTrabajoActiva.getCodigoOrdenTrabajo()});
    }

    @Override
    public boolean eliminar(OrdenTrabajo dato) {
        return this.operadorDatos.borrar(ColumnaOrdenTrabajo.CODIGOCORRERIA + " = ? AND " +
                        ColumnaOrdenTrabajo.CODIGOORDENTRABAJO + " = ? "
                , new String[]{dato.getCodigoCorreria(), dato.getCodigoOrdenTrabajo()}) > 0;
    }

    @Override
    public boolean actualizarSecuencias(String codigoCorreria, int secuencia) {
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaOrdenTrabajo.CODIGOCORRERIA
                + " = '" + codigoCorreria + "' AND " + ColumnaOrdenTrabajo.SECUENCIA + " >= " + secuencia + " ORDER BY " + ColumnaOrdenTrabajo.SECUENCIA);

        try {
            if (cursor.moveToFirst()) {
                ContentValues contentValues;
                while (!cursor.isAfterLast()) {
                    contentValues = new ContentValues();
                    contentValues.put(ColumnaOrdenTrabajo.SECUENCIA, cursor.getInt(cursor.getColumnIndex(ColumnaOrdenTrabajo.SECUENCIA)) + 1);
                    this.operadorDatos.actualizar(contentValues, ColumnaOrdenTrabajo.CODIGOCORRERIA + " = ? AND " +
                                    ColumnaOrdenTrabajo.CODIGOORDENTRABAJO + " = ? "
                            , new String[]{codigoCorreria, cursor.getString(cursor.getColumnIndex(ColumnaOrdenTrabajo.CODIGOORDENTRABAJO))});
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
        }
        return true;
    }

    @Override
    public int totalOrdenesTrabajo(String codigoCorreria) {
        int ordenTrabajo = 0;
        Cursor cursor = this.operadorDatos.cargar("SELECT COUNT(0) AS cantidad FROM " + NombreTabla + " WHERE " + ColumnaOrdenTrabajo.CODIGOCORRERIA
                + " = '" + codigoCorreria + "'");

        try {
            if (cursor.moveToFirst()) {
                ordenTrabajo = cursor.getInt(cursor.getColumnIndex("cantidad"));
            }
        } finally {
            cursor.close();
        }
        return ordenTrabajo;
    }

    @Override
    public boolean guardar(List<OrdenTrabajo> lista) {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (OrdenTrabajo ordenTrabajo : lista) {
            listaContentValues.add(convertirObjetoAContentValues(ordenTrabajo));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public ListaOrdenTrabajo cargar(OrdenTrabajoBusqueda ordenTrabajoBusqueda) {
        ListaOrdenTrabajo lista = new ListaOrdenTrabajo();
//        String consulta = "SELECT DISTINCT " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.CODIGOCORRERIA + ", " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.CODIGOORDENTRABAJO + ", " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.SECUENCIA + ", " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.NUEVA + ", " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.NOMBRE + ", " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.DIRECCION + ", " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.CODIGOMUNICIPIO + ", " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.GPS + ", " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.TELEFONO + ", " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.LLAVE1 + ", " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.LLAVE2 + ", " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.CODIGOESTADO + ", " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.FECHAINICIOORDENTRABAJO + ", " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.FECHAULTIMAORDENTRABAJO + ", " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.CODIGOUSUARIOLABOR + ", " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.PARAMETROS + ", " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.FECHACARGA + ", " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.SESION + ", " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.ESTADOCOMUNICACION + ", " +
//                TrabajoXOrdenTrabajoDao.NombreTabla + "." + TrabajoXOrdenTrabajoDao.ColumnaTrabajoXOrdenTrabajo.CODIGOTRABAJO +
//                " FROM " + NombreTabla +
//                " LEFT JOIN " + ElementoDao.NombreTabla +
//                " ON " + NombreTabla + "." + ColumnaOrdenTrabajo.CODIGOCORRERIA + " = " + ElementoDao.NombreTabla + "." + ElementoDao.ColumnaElemento.CODIGOCORRERIA +
//                " AND " + NombreTabla + "." + ColumnaOrdenTrabajo.CODIGOORDENTRABAJO + " = " + ElementoDao.NombreTabla + "." + ElementoDao.ColumnaElemento.CODIGOORDENTRABAJO +
//                " LEFT JOIN " + TrabajoXOrdenTrabajoDao.NombreTabla +
//                " ON " + NombreTabla + "." + ColumnaOrdenTrabajo.CODIGOCORRERIA + " = " + TrabajoXOrdenTrabajoDao.NombreTabla + "." + TrabajoXOrdenTrabajoDao.ColumnaTrabajoXOrdenTrabajo.CODIGOCORRERIA +
//                " AND " + NombreTabla + "." + ColumnaOrdenTrabajo.CODIGOORDENTRABAJO + " = " + TrabajoXOrdenTrabajoDao.NombreTabla + "." + TrabajoXOrdenTrabajoDao.ColumnaTrabajoXOrdenTrabajo.CODIGOORDENTRABAJO +
//                " WHERE " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.CODIGOCORRERIA + " = '" + ordenTrabajoBusqueda.getCodigoCorreria() + "' AND " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.CODIGOORDENTRABAJO + " like ('%" + ordenTrabajoBusqueda.getCodigoOrdenTrabajo() + "%') AND " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.DIRECCION + " like ('%" + ordenTrabajoBusqueda.getDireccion() + "%') AND " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.NOMBRE + " like ('%" + ordenTrabajoBusqueda.getCliente() + "%') AND " +
//                NombreTabla + "." + ColumnaOrdenTrabajo.LLAVE1 + " like ('%" + ordenTrabajoBusqueda.getCodigoLlave1() + "%') AND " +
//                ElementoDao.NombreTabla + "." + ElementoDao.ColumnaElemento.SERIE + " like ('%" + ordenTrabajoBusqueda.getSerieElemento() + "%')";
//
//        if (!ordenTrabajoBusqueda.getCodigoLlave2().isEmpty()) {
//            consulta = consulta + " AND " + ColumnaOrdenTrabajo.LLAVE2 + " = '" + ordenTrabajoBusqueda.getCodigoLlave2() + "'";
//        }
//        if (!ordenTrabajoBusqueda.getTareaXTrabajo().getTrabajo().getCodigoTrabajo().isEmpty()) {
//            consulta = consulta + " AND " + TrabajoXOrdenTrabajoDao.NombreTabla + "." + TrabajoXOrdenTrabajoDao.ColumnaTrabajoXOrdenTrabajo.CODIGOTRABAJO + " = '" + ordenTrabajoBusqueda.getTareaXTrabajo().getTrabajo().getCodigoTrabajo() + "'";
//        }
//
//        Cursor cursor = this.operadorDatos.cargar(consulta);
//        try {
//            if (cursor.moveToFirst()) {
//                while (!cursor.isAfterLast()) {
//                    lista.add(convertirCursorAObjeto(cursor));
//                    cursor.moveToNext();
//                }
//            }
//        } finally {
//            cursor.close();
//        }
        return lista;
    }

    @Override
    public int asignarUltimaSecuencia(String codigoCorreria) {
        Cursor cursor = this.operadorDatos.cargar("SELECT MAX(" + ColumnaOrdenTrabajo.SECUENCIA
                + ") '" + ColumnaOrdenTrabajo.SECUENCIA + "' FROM " + OrdenTrabajoDao.NombreTabla + " WHERE " +
                ColumnaOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' ");
        try {
            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndex(ColumnaOrdenTrabajo.SECUENCIA)) + 1;
            }
        } finally {
            cursor.close();
        }
        return 0;
    }

    @Override
    public ListaOrdenTrabajo cargar(String codigosOTsConsulta) {
        ListaOrdenTrabajo lista = new ListaOrdenTrabajo();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " +
                ColumnaOrdenTrabajo.CODIGOORDENTRABAJO + " IN(" + codigosOTsConsulta + ")");
        try {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    lista.add(convertirCursorAObjeto(cursor));
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
        }
        return lista;
    }


    private OrdenTrabajo convertirCursorAObjeto(Cursor cursor) {
        OrdenTrabajo ordenTrabajo = new OrdenTrabajo();
        ordenTrabajo.setCodigoCorreria(cursor.getString(cursor.getColumnIndex(ColumnaOrdenTrabajo.CODIGOCORRERIA)));
        ordenTrabajo.setCodigoOrdenTrabajo(cursor.getString(cursor.getColumnIndex(ColumnaOrdenTrabajo.CODIGOORDENTRABAJO)));
        ordenTrabajo.setSecuencia(cursor.getInt(cursor.getColumnIndex(ColumnaOrdenTrabajo.SECUENCIA)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaOrdenTrabajo.NUEVA))) {
            ordenTrabajo.setNueva(StringHelper.ToBoolean((cursor.getString(cursor.getColumnIndex(ColumnaOrdenTrabajo.NUEVA)))));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaOrdenTrabajo.NOMBRE))) {
            ordenTrabajo.setNombre(cursor.getString(cursor.getColumnIndex(ColumnaOrdenTrabajo.NOMBRE)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaOrdenTrabajo.DIRECCION))) {
            ordenTrabajo.setDireccion(cursor.getString(cursor.getColumnIndex(ColumnaOrdenTrabajo.DIRECCION)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaOrdenTrabajo.CODIGOMUNICIPIO))) {
            ordenTrabajo.getDepartamento().getMunicipio().setCodigoMunicipio(cursor.getString(cursor.getColumnIndex(ColumnaOrdenTrabajo.CODIGOMUNICIPIO)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaOrdenTrabajo.GPS))) {
            ordenTrabajo.setGps(cursor.getString(cursor.getColumnIndex(ColumnaOrdenTrabajo.GPS)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaOrdenTrabajo.TELEFONO))) {
            ordenTrabajo.setTelefono(cursor.getString(cursor.getColumnIndex(ColumnaOrdenTrabajo.TELEFONO)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaOrdenTrabajo.LLAVE1))) {
            ordenTrabajo.setCodigoLlave1(cursor.getString(cursor.getColumnIndex(ColumnaOrdenTrabajo.LLAVE1)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaOrdenTrabajo.LLAVE2))) {
            ordenTrabajo.setCodigoLlave2(cursor.getString(cursor.getColumnIndex(ColumnaOrdenTrabajo.LLAVE2)));
        }
        ordenTrabajo.setEstado(OrdenTrabajo.EstadoOrdenTrabajo.getEstado(cursor.getString(cursor.getColumnIndex(ColumnaOrdenTrabajo.CODIGOESTADO))));

        try {
            ordenTrabajo.setFechaInicioOrdenTrabajo(DateHelper.convertirStringADate(cursor.getString(cursor.getColumnIndex(ColumnaOrdenTrabajo.FECHAINICIOORDENTRABAJO)), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            ordenTrabajo.setFechaUltimaOrdenTrabajo(DateHelper.convertirStringADate(cursor.getString(cursor.getColumnIndex(ColumnaOrdenTrabajo.FECHAULTIMAORDENTRABAJO)), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            ordenTrabajo.setFechaCarga(DateHelper.convertirStringADate(cursor.getString(
                    cursor.getColumnIndex(ColumnaOrdenTrabajo.FECHACARGA)), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (!cursor.isNull(cursor.getColumnIndex(ColumnaOrdenTrabajo.SESION))) {
            ordenTrabajo.setSesion(cursor.getString(cursor.getColumnIndex(ColumnaOrdenTrabajo.SESION)));
        }

        if (!cursor.isNull(cursor.getColumnIndex(ColumnaOrdenTrabajo.CODIGOUSUARIOLABOR))) {
            ordenTrabajo.setCodigoUsuarioLabor(cursor.getString(cursor.getColumnIndex(ColumnaOrdenTrabajo.CODIGOUSUARIOLABOR)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaOrdenTrabajo.PARAMETROS))) {
            ordenTrabajo.setParametros(cursor.getString(cursor.getColumnIndex(ColumnaOrdenTrabajo.PARAMETROS)));
        }

        if (!cursor.isNull(cursor.getColumnIndex(ColumnaOrdenTrabajo.ESTADOCOMUNICACION))) {
            ordenTrabajo.setEstadoComunicacion(cursor.getString(cursor.getColumnIndex(ColumnaOrdenTrabajo.ESTADOCOMUNICACION)));
        }

        if (!cursor.isNull(cursor.getColumnIndex(TrabajoXOrdenTrabajoDao.ColumnaTrabajoXOrdenTrabajo.CODIGOTRABAJO))) {
            TrabajoXOrdenTrabajo trabajoXOrdenTrabajo = new TrabajoXOrdenTrabajo();
            Trabajo trabajo = new Trabajo();
            trabajo.setCodigoTrabajo(cursor.getString(cursor.getColumnIndex(TrabajoXOrdenTrabajoDao.ColumnaTrabajoXOrdenTrabajo.CODIGOTRABAJO)));
            trabajoXOrdenTrabajo.setTrabajo(trabajo);
            ordenTrabajo.setTrabajoXOrdenTrabajo(trabajoXOrdenTrabajo);
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaOrdenTrabajo.IMPRIMIRFACTURA))) {
            ordenTrabajo.setImprimirFactura(cursor.getString(cursor.getColumnIndex(ColumnaOrdenTrabajo.IMPRIMIRFACTURA)));
        }

        return ordenTrabajo;
    }

    private ContentValues convertirObjetoAContentValues(OrdenTrabajo ordenTrabajo) {
        ContentValues contentValues = new ContentValues();
        if (!ordenTrabajo.getCodigoCorreria().isEmpty()) {
            contentValues.put(ColumnaOrdenTrabajo.CODIGOCORRERIA, ordenTrabajo.getCodigoCorreria());
        }
        if (!ordenTrabajo.getCodigoOrdenTrabajo().isEmpty()) {
            contentValues.put(ColumnaOrdenTrabajo.CODIGOORDENTRABAJO, ordenTrabajo.getCodigoOrdenTrabajo());
        }
        contentValues.put(ColumnaOrdenTrabajo.SECUENCIA, ordenTrabajo.getSecuencia());
        contentValues.put(ColumnaOrdenTrabajo.NUEVA, BooleanHelper.ToString(ordenTrabajo.isNueva()));
        contentValues.put(ColumnaOrdenTrabajo.NOMBRE, ordenTrabajo.getNombre());
        contentValues.put(ColumnaOrdenTrabajo.DIRECCION, ordenTrabajo.getDireccion());
        if (!ordenTrabajo.getDepartamento().getMunicipio().getCodigoMunicipio().isEmpty()) {
            contentValues.put(ColumnaOrdenTrabajo.CODIGOMUNICIPIO, ordenTrabajo.getDepartamento().getMunicipio().getCodigoMunicipio());
        } else {
            contentValues.putNull(ColumnaOrdenTrabajo.CODIGOMUNICIPIO);
        }
        contentValues.put(ColumnaOrdenTrabajo.GPS, ordenTrabajo.getGps());
        contentValues.put(ColumnaOrdenTrabajo.TELEFONO, ordenTrabajo.getTelefono());
        contentValues.put(ColumnaOrdenTrabajo.LLAVE1, ordenTrabajo.getCodigoLlave1());
        contentValues.put(ColumnaOrdenTrabajo.LLAVE2, ordenTrabajo.getCodigoLlave2());
        contentValues.put(ColumnaOrdenTrabajo.CODIGOESTADO, ordenTrabajo.getEstado().getCodigo());

        try {
            contentValues.put(ColumnaOrdenTrabajo.FECHAINICIOORDENTRABAJO, DateHelper.convertirDateAString(
                    ordenTrabajo.getFechaInicioOrdenTrabajo(), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            contentValues.put(ColumnaOrdenTrabajo.FECHAULTIMAORDENTRABAJO, DateHelper.convertirDateAString(
                    ordenTrabajo.getFechaUltimaOrdenTrabajo(), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            contentValues.put(ColumnaOrdenTrabajo.FECHACARGA, DateHelper.convertirDateAString(
                    ordenTrabajo.getFechaCarga(), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        contentValues.put(ColumnaOrdenTrabajo.SESION, ordenTrabajo.getSesion());
        contentValues.put(ColumnaOrdenTrabajo.ESTADOCOMUNICACION, ordenTrabajo.getEstadoComunicacion());
        contentValues.put(ColumnaOrdenTrabajo.CODIGOUSUARIOLABOR, ordenTrabajo.getCodigoUsuarioLabor());
        contentValues.put(ColumnaOrdenTrabajo.PARAMETROS, ordenTrabajo.getParametros());
        contentValues.put(ColumnaOrdenTrabajo.IMPRIMIRFACTURA, ordenTrabajo.getImprimirFactura());

        return contentValues;
    }

    public static class ColumnaOrdenTrabajo {

        public static final String CODIGOCORRERIA = "codigocorreria";
        public static final String CODIGOORDENTRABAJO = "codigoordentrabajo";
        public static final String SECUENCIA = "secuencia";
        public static final String NUEVA = "nueva";
        public static final String NOMBRE = "nombre";
        public static final String DIRECCION = "direccion";
        public static final String CODIGOMUNICIPIO = "codigomunicipio";
        public static final String GPS = "gps";
        public static final String TELEFONO = "telefono";
        public static final String LLAVE1 = "llave1";
        public static final String LLAVE2 = "llave2";
        public static final String CODIGOESTADO = "codigoestado";
        public static final String FECHAINICIOORDENTRABAJO = "fechainicioordentrabajo";
        public static final String FECHAULTIMAORDENTRABAJO = "fechaultimaordentrabajo";
        public static final String CODIGOUSUARIOLABOR = "codigousuariolabor";
        public static final String PARAMETROS = "parametros";
        public static final String ESTADOCOMUNICACION = "estadocomunicacion";
        public static final String IMPRIMIRFACTURA = "imprimirfactura";
        public static final String FECHACARGA = "fechacarga";
        public static final String SESION = "sesion";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaOrdenTrabajo.CODIGOCORRERIA + " " + STRING_TYPE + " not null," +
                    ColumnaOrdenTrabajo.CODIGOORDENTRABAJO + " " + STRING_TYPE + " not null," +
                    ColumnaOrdenTrabajo.SECUENCIA + " " + INT_TYPE + " not null," +
                    ColumnaOrdenTrabajo.NUEVA + " " + STRING_TYPE + " null," +
                    ColumnaOrdenTrabajo.NOMBRE + " " + STRING_TYPE + " null," +
                    ColumnaOrdenTrabajo.DIRECCION + " " + STRING_TYPE + " null," +
                    ColumnaOrdenTrabajo.CODIGOMUNICIPIO + " " + STRING_TYPE + " null," +
                    ColumnaOrdenTrabajo.GPS + " " + STRING_TYPE + " null," +
                    ColumnaOrdenTrabajo.TELEFONO + " " + STRING_TYPE + " null," +
                    ColumnaOrdenTrabajo.LLAVE1 + " " + STRING_TYPE + " null," +
                    ColumnaOrdenTrabajo.LLAVE2 + " " + STRING_TYPE + " null," +
                    ColumnaOrdenTrabajo.CODIGOESTADO + " " + STRING_TYPE + " null," +
                    ColumnaOrdenTrabajo.FECHAINICIOORDENTRABAJO + " " + STRING_TYPE + " null," +
                    ColumnaOrdenTrabajo.FECHAULTIMAORDENTRABAJO + " " + STRING_TYPE + " null," +
                    ColumnaOrdenTrabajo.CODIGOUSUARIOLABOR + " " + STRING_TYPE + " null," +
                    ColumnaOrdenTrabajo.PARAMETROS + " " + STRING_TYPE + " null," +
                    ColumnaOrdenTrabajo.ESTADOCOMUNICACION + " " + STRING_TYPE + " null," +
                    ColumnaOrdenTrabajo.IMPRIMIRFACTURA + " " + STRING_TYPE + " null," +
                    ColumnaOrdenTrabajo.FECHACARGA + " " + STRING_TYPE + " null," +
                    ColumnaOrdenTrabajo.SESION + " " + STRING_TYPE + " null," +
                    " primary key (" + ColumnaOrdenTrabajo.CODIGOCORRERIA + "," +
                    ColumnaOrdenTrabajo.CODIGOORDENTRABAJO + ")" +
                    " FOREIGN KEY (" + ColumnaOrdenTrabajo.CODIGOESTADO + ") REFERENCES " +
                    EstadoDao.NombreTabla + "( " + EstadoDao.ColumnaEstado.CODIGOESTADO + ")," +
                    " FOREIGN KEY (" + ColumnaOrdenTrabajo.CODIGOMUNICIPIO + ") REFERENCES " +
                    MunicipioDao.NombreTabla + "( " + MunicipioDao.ColumnaMunicipio.CODIGOMUNICIPIO + ")," +
                    " FOREIGN KEY (" + ColumnaOrdenTrabajo.CODIGOCORRERIA + ") REFERENCES " +
                    CorreriaDao.NombreTabla + "( " + CorreriaDao.ColumnaCorreria.CODIGOCORRERIA + ") ON DELETE CASCADE)";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}