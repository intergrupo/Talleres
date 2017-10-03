package com.example.santiagolopezgarcia.talleres.data.dao.tarea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.modelonegocio.ListaOrdenTrabajo;
import com.example.dominio.modelonegocio.ListaTareaXOrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.dominio.modelonegocio.Tarea;
import com.example.dominio.modelonegocio.TareaXOrdenTrabajo;
import com.example.dominio.modelonegocio.TareaXTrabajoOrdenTrabajo;
import com.example.dominio.modelonegocio.TotalTarea;
import com.example.dominio.modelonegocio.TotalTareaXEstado;
import com.example.dominio.bussinesslogic.tarea.TareaXOrdenTrabajoRepositorio;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;
import com.example.santiagolopezgarcia.talleres.data.dao.ordentrabajo.OrdenTrabajoDao;
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

public class TareaXOrdenTrabajoDao extends DaoBase implements TareaXOrdenTrabajoRepositorio {

    public static final String NombreTabla = "sirius_tareaxordentrabajo";

    public TareaXOrdenTrabajoDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public boolean guardar(List<TareaXTrabajoOrdenTrabajo> lista) {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (TareaXTrabajoOrdenTrabajo tareaXOrdenTrabajo : lista) {
            listaContentValues.add(convertirObjetoAContentValues(tareaXOrdenTrabajo));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    @Override
    public List<String> cargarCodigoOrdenTrabajoXEstadoTarea(Tarea.EstadoTarea estadoTarea) {
        List<String> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT " + ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO + " FROM " + NombreTabla +
                " WHERE " + ColumnaTareaXOrdenTrabajo.CODIGOESTADOTAREA + " = '" + estadoTarea.getCodigo() + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                lista.add(cursor.getString(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    @Override
    public List<String> cargarCodigoOrdenTrabajoXTarea(Tarea tarea) {
        List<String> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT " + ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO + " FROM " + NombreTabla +
                " WHERE " + ColumnaTareaXOrdenTrabajo.CODIGOTAREA + " = '" + tarea.getCodigoTarea() + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                lista.add(cursor.getString(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    @Override
    public ListaTareaXOrdenTrabajo cargarTareasXOrdenTrabajo(String codigoCorreria) {
        ListaTareaXOrdenTrabajo lista = new ListaTareaXOrdenTrabajo();
        Cursor cursor = this.operadorDatos.cargar("SELECT stxo.codigocorreria, stxo.codigoordentrabajo, stxo.codigotrabajo, stxo.codigotarea, \n" +
                "stxo.fechainiciotarea, stxo.fechaultimatarea, stxo.codigousuariotarea, stxo.nueva, stxo.codigoestado," +
                "stxo.secuencia, stxo.parametros, stxo.fechadescargada FROM " + NombreTabla +
                " stxo JOIN sirius_tarea st ON st." + ColumnaTareaXOrdenTrabajo.CODIGOTAREA
                + " = stxo." + ColumnaTareaXOrdenTrabajo.CODIGOTAREA + " WHERE stxo." +
                ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "'" +
                "ORDER BY " + ColumnaTareaXOrdenTrabajo.SECUENCIA);
        if (cursor.moveToFirst()) {
            TareaXOrdenTrabajo tareaXOrdenTrabajo;
            int concecutivo = 1;
            while (!cursor.isAfterLast()) {
                tareaXOrdenTrabajo = convertirCursorAObjetoTareaXOrdenTrabajo(cursor);
                tareaXOrdenTrabajo.setConsecutivo(concecutivo);
                concecutivo++;
                lista.add(tareaXOrdenTrabajo);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    @Override
    public List<String> cargarCodigoOrdenTrabajoXDescarga() {
        List<String> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT DISTINCT ot." + ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO + " FROM " + OrdenTrabajoDao.NombreTabla + " ot" +
                " JOIN " + NombreTabla + " txot ON ot." + OrdenTrabajoDao.ColumnaOrdenTrabajo.CODIGOORDENTRABAJO + " = txot." + ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO +
                " WHERE txot." + ColumnaTareaXOrdenTrabajo.FECHADESCARGADA + " IS NULL OR " +
                ColumnaTareaXOrdenTrabajo.FECHADESCARGADA + " = ''");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                lista.add(cursor.getString(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    @Override
    public List<TareaXTrabajoOrdenTrabajo> cargarListaTareaXTrabajoOrdenTrabajo(String codigoCorreria,
                                                                                String codigoOrdenTrabajo,
                                                                                String codigoTrabajo) {
        List<TareaXTrabajoOrdenTrabajo> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT stxo.codigocorreria, stxo.codigoordentrabajo, stxo.codigotrabajo, stxo.codigotarea, \n" +
                "stxo.fechainiciotarea, stxo.fechaultimatarea, stxo.codigousuariotarea, stxo.nueva, stxo.codigoestado," +
                "stxo.secuencia, stxo.parametros, stxo.fechadescargada FROM " + NombreTabla +
                " stxo JOIN sirius_tarea st ON st." + ColumnaTareaXOrdenTrabajo.CODIGOTAREA
                + " = stxo." + ColumnaTareaXOrdenTrabajo.CODIGOTAREA +
                " WHERE stxo." + ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' AND stxo." +
                ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "' AND stxo." +
                ColumnaTareaXOrdenTrabajo.CODIGOTRABAJO + " = '" + codigoTrabajo + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                lista.add(convertirCursorAObjetoTareaXOrdenTrabajo(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    @Override
    public List<TareaXTrabajoOrdenTrabajo> cargarListaTareaXTrabajoOrdenTrabajoXTarea(String codigoCorreria,
                                                                                      String codigoOrdenTrabajo,
                                                                                      String codigoTarea) {
        List<TareaXTrabajoOrdenTrabajo> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT stxo.codigocorreria, stxo.codigoordentrabajo, stxo.codigotrabajo, stxo.codigotarea, \n" +
                "stxo.fechainiciotarea, stxo.fechaultimatarea, stxo.codigousuariotarea, stxo.nueva, stxo.codigoestado," +
                "stxo.secuencia, stxo.parametros, stxo.fechadescargada FROM " + NombreTabla +
                " stxo JOIN sirius_tarea st ON st." + ColumnaTareaXOrdenTrabajo.CODIGOTAREA
                + " = stxo." + ColumnaTareaXOrdenTrabajo.CODIGOTAREA +
                " WHERE stxo." + ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' AND stxo." +
                ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "' AND stxo." +
                ColumnaTareaXOrdenTrabajo.CODIGOTAREA + " = '" + codigoTarea + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                lista.add(convertirCursorAObjetoTareaXOrdenTrabajo(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    @Override
    public List<TotalTarea> cargarTotalesPorTarea(String codigoCorreria, String codigoTrabajo) {
        List<TotalTarea> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT st.nombre, stxo." + ColumnaTareaXOrdenTrabajo.CODIGOTAREA
                + ", count(0) " + ColumnaTareaXOrdenTrabajo.CANTIDADTAREAS + " FROM " + NombreTabla +
                " stxo JOIN sirius_tarea st ON st." + ColumnaTareaXOrdenTrabajo.CODIGOTAREA
                + " = stxo." + ColumnaTareaXOrdenTrabajo.CODIGOTAREA + " WHERE " + ColumnaTareaXOrdenTrabajo.CODIGOTRABAJO
                + " = '" + codigoTrabajo + "' AND " + ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "'"
                + " GROUP BY stxo." + ColumnaTareaXOrdenTrabajo.CODIGOTAREA);
        if (cursor.moveToFirst()) {
            TotalTarea total = new TotalTarea();
            while (!cursor.isAfterLast()) {
                total.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                total.setCantidad(cursor.getInt(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.CANTIDADTAREAS)));
                total.setListaTareaXEstado(cargarTotalesPorEstado(codigoCorreria, cursor.getString(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.CODIGOTAREA)),
                        codigoTrabajo));
                lista.add(total);
                total = new TotalTarea();
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    @Override
    public boolean guardar(TareaXTrabajoOrdenTrabajo tareaXTrabajoOrdenTrabajo) {
        ContentValues datos = convertirObjetoAContentValues(tareaXTrabajoOrdenTrabajo);
        return this.operadorDatos.insertar(datos);
    }

    @Override
    public List<TareaXTrabajoOrdenTrabajo> cargarListaTareaXTrabajoOrdenTrabajo(String codigoCorreria, String codigoOrdenTrabajo) {
        List<TareaXTrabajoOrdenTrabajo> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT stxo.codigocorreria, stxo.codigoordentrabajo, stxo.codigotrabajo, stxo.codigotarea, \n" +
                "stxo.fechainiciotarea, stxo.fechaultimatarea, stxo.codigousuariotarea, stxo.nueva, stxo.codigoestado," +
                "stxo.secuencia, stxo.parametros, stxo.fechadescargada FROM " + NombreTabla +
                " stxo JOIN sirius_tarea st ON st." + ColumnaTareaXOrdenTrabajo.CODIGOTAREA
                + " = stxo." + ColumnaTareaXOrdenTrabajo.CODIGOTAREA +
                " WHERE stxo." + ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' AND stxo."
                + ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                lista.add(convertirCursorAObjetoTareaXOrdenTrabajo(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    @Override
    public List<TareaXTrabajoOrdenTrabajo> cargarListaTareaXTrabajoOrdenTrabajoXCorreria(String codigoCorreria) {
        List<TareaXTrabajoOrdenTrabajo> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT stxo.codigocorreria, stxo.codigoordentrabajo, stxo.codigotrabajo, stxo.codigotarea, \n" +
                "stxo.fechainiciotarea, stxo.fechaultimatarea, stxo.codigousuariotarea, stxo.nueva, stxo.codigoestado," +
                "stxo.secuencia, stxo.parametros, stxo.fechadescargada FROM " + NombreTabla +
                " stxo JOIN sirius_tarea st ON st." + ColumnaTareaXOrdenTrabajo.CODIGOTAREA
                + " = stxo." + ColumnaTareaXOrdenTrabajo.CODIGOTAREA +
                " WHERE stxo." + ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                lista.add(convertirCursorAObjetoTareaXOrdenTrabajo(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    @Override
    public TareaXTrabajoOrdenTrabajo cargarTareaXTrabajoOrdenTrabajo(String codigoCorreria, String codigoOrdenTrabajo, String codigoTarea, String codigoTrabajo) {
        TareaXTrabajoOrdenTrabajo tareaXTrabajoOrdenTrabajo = new TareaXOrdenTrabajo();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' AND "
                + ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "' AND "
                + ColumnaTareaXOrdenTrabajo.CODIGOTAREA + " = '" + codigoTarea + "' AND "
                + ColumnaTareaXOrdenTrabajo.CODIGOTRABAJO + " = '" + codigoTrabajo + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                tareaXTrabajoOrdenTrabajo = convertirCursorAObjetoTareaXOrdenTrabajo(cursor);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return tareaXTrabajoOrdenTrabajo;
    }

    @Override
    public boolean actualizar(TareaXTrabajoOrdenTrabajo tareaXTrabajoOrdenTrabajo) {
        ContentValues datos = convertirObjetoAContentValues(tareaXTrabajoOrdenTrabajo);
        return this.operadorDatos.actualizar(datos, ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + " = ? AND " +
                        ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO + " = ? AND " +
                        ColumnaTareaXOrdenTrabajo.CODIGOTAREA + " = ? AND " +
                        ColumnaTareaXOrdenTrabajo.CODIGOTRABAJO + " = ? "
                , new String[]{tareaXTrabajoOrdenTrabajo.getCodigoCorreria(), tareaXTrabajoOrdenTrabajo.getCodigoOrdenTrabajo(),
                        tareaXTrabajoOrdenTrabajo.getTarea().getCodigoTarea(), tareaXTrabajoOrdenTrabajo.getCodigoTrabajo()});
    }

    @Override
    public boolean eliminar(TareaXTrabajoOrdenTrabajo dato) {
        return this.operadorDatos.borrar(ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + " = ? AND " +
                        ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO + " = ? AND " +
                        ColumnaTareaXOrdenTrabajo.CODIGOTAREA + " = ? AND " +
                        ColumnaTareaXOrdenTrabajo.CODIGOTRABAJO + " = ? "
                , new String[]{dato.getCodigoCorreria(), dato.getCodigoOrdenTrabajo(),
                        dato.getTarea().getCodigoTarea(), dato.getCodigoTrabajo()}) > 0;
    }

    @Override
    public List<String> cargarCodigosTrabajosAgrupados(String codigoCorreria) {
        List<String> listaCodigos = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT " + ColumnaTareaXOrdenTrabajo.CODIGOTRABAJO + " FROM " + NombreTabla +
                " WHERE " + ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' GROUP BY " + ColumnaTareaXOrdenTrabajo.CODIGOTRABAJO);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listaCodigos.add(cursor.getString(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.CODIGOTRABAJO)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listaCodigos;
    }

    @Override
    public boolean actualizarFechaDescarga(String codigoCorreria, String fechaDescarga, ListaOrdenTrabajo listaOrdenTrabajo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ColumnaTareaXOrdenTrabajo.FECHADESCARGADA, fechaDescarga);
        for (OrdenTrabajo ordenTrabajo : listaOrdenTrabajo) {
            this.operadorDatos.actualizar(contentValues, ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO + " = ?",
                    new String[]{ordenTrabajo.getCodigoOrdenTrabajo()});
        }
        return true;
    }


    @Override
    public TareaXOrdenTrabajo cargar(String codigoCorreria, String codigoOrdenTrabajo, String codigoTrabajo, String codigoTarea, String codigoEstadoTarea) {
        TareaXOrdenTrabajo tareaXOrdenTrabajo = new TareaXOrdenTrabajo();
        String consulta = "SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' AND "
                + ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "' AND "
                + ColumnaTareaXOrdenTrabajo.CODIGOTRABAJO + " = '" + codigoTrabajo + "' AND "
                + ColumnaTareaXOrdenTrabajo.CODIGOTAREA + " = '" + codigoTarea + "'";
        if (!codigoEstadoTarea.isEmpty() && codigoEstadoTarea != Tarea.EstadoTarea.NINGUNA.getCodigo()) {
            consulta = consulta + " AND " + ColumnaTareaXOrdenTrabajo.CODIGOESTADOTAREA + " = '" + codigoEstadoTarea + "'";
        }

        Cursor cursor = this.operadorDatos.cargar(consulta);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                tareaXOrdenTrabajo = convertirCursorAObjetoTareaXOrdenTrabajo(cursor);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return tareaXOrdenTrabajo;
    }

    /*public ListaTareaXOrdenTrabajo cargar(String codigoCorreria, String codigoOrdenTrabajo,
                                          String codigoTrabajo, String codigoTarea, String codigoEstadoTarea,
                                          boolean noDescargada){
        ListaTareaXOrdenTrabajo lista = new ListaTareaXOrdenTrabajo();
        String consulta = "SELECT * FROM " + NombreTabla +
                " INNER JOIN " + TareaDao.NombreTabla +
                " ON " + NombreTabla + "." + ColumnaTareaXOrdenTrabajo.CODIGOTAREA + " = " + TareaDao.NombreTabla + "." + TareaDao.ColumnaTarea.CODIGOTAREA +
                " WHERE " + ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' AND "
                + ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "' AND "
                + ColumnaTareaXOrdenTrabajo.CODIGOTRABAJO + " = '" + codigoTrabajo + "' AND "
                + TareaDao.NombreTabla + "." + TareaDao.ColumnaTarea.PARAMETROS + " <> '" + TAREA_TIPO_MENU + "'";
        if(!codigoTarea.isEmpty()){
            consulta = consulta + " AND " + ColumnaTareaXOrdenTrabajo.CODIGOTAREA + " = '" + codigoTarea + "'";
        }
        if(!codigoEstadoTarea.isEmpty() && codigoEstadoTarea != Tarea.EstadoTarea.ENEJECUCION.getCodigo()){
            consulta = consulta + " AND " + ColumnaTareaXOrdenTrabajo.CODIGOESTADOTAREA + " = '" + codigoEstadoTarea + "'";
        }
        if(noDescargada){
            consulta = consulta + " AND " + ColumnaTareaXOrdenTrabajo.FECHADESCARGADA + " IS NULL OR " + ColumnaTareaXOrdenTrabajo.FECHADESCARGADA + " = '' ";
        }

        Cursor cursor = this.operadorDatos.cargar(consulta);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                lista.add(convertirCursorAObjetoTareaXOrdenTrabajo(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }*/

    public ListaTareaXOrdenTrabajo cargar(String codigoCorreria, String codigoOrdenTrabajo,
                                          String codigoTrabajo, String codigoTarea, String codigoEstadoTarea, String serie,
                                          boolean noDescargada) {
        ListaTareaXOrdenTrabajo lista = new ListaTareaXOrdenTrabajo();
//        String consulta = "SELECT DISTINCT " +
//                NombreTabla + "." + ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + ", " +
//                NombreTabla + "." + ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO + ", " +
//                NombreTabla + "." + ColumnaTareaXOrdenTrabajo.CODIGOTRABAJO + ", " +
//                NombreTabla + "." + ColumnaTareaXOrdenTrabajo.CODIGOTAREA + ", " +
//                NombreTabla + "." + ColumnaTareaXOrdenTrabajo.FECHAINICIOTAREA + ", " +
//                NombreTabla + "." + ColumnaTareaXOrdenTrabajo.FECHAULTIMATAREA + ", " +
//                NombreTabla + "." + ColumnaTareaXOrdenTrabajo.CODIGOUSUARIOTAREA + ", " +
//                NombreTabla + "." + ColumnaTareaXOrdenTrabajo.NUEVA + ", " +
//                NombreTabla + "." + ColumnaTareaXOrdenTrabajo.CODIGOESTADOTAREA + ", " +
//                NombreTabla + "." + ColumnaTareaXOrdenTrabajo.SECUENCIA + ", " +
//                NombreTabla + "." + ColumnaTareaXOrdenTrabajo.PARAMETROS + ", " +
//                NombreTabla + "." + ColumnaTareaXOrdenTrabajo.FECHADESCARGADA +
//                " FROM " + NombreTabla +
//                " INNER JOIN " + TareaDao.NombreTabla +
//                " ON " + NombreTabla + "." + ColumnaTareaXOrdenTrabajo.CODIGOTAREA + " = " + TareaDao.NombreTabla + "." + TareaDao.ColumnaTarea.CODIGOTAREA +
//                " LEFT JOIN " + LaborElementoDao.NombreTabla +
//                " ON " + NombreTabla + "." + ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + " = " + LaborElementoDao.NombreTabla + "." + LaborElementoDao.ColumnaLaborElemento.CODIGOCORRERIA +
//                " AND " + NombreTabla + "." + ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO + " = " + LaborElementoDao.NombreTabla + "." + LaborElementoDao.ColumnaLaborElemento.CODIGOORDENTRABAJO +
//                " AND " + NombreTabla + "." + ColumnaTareaXOrdenTrabajo.CODIGOTAREA + " = " + LaborElementoDao.NombreTabla + "." + LaborElementoDao.ColumnaLaborElemento.CODIGOTAREA +
//                " WHERE " +
//                NombreTabla + "." + ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' AND " +
//                NombreTabla + "." + ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "'";
//
//        if (!codigoTrabajo.isEmpty()) {
//            consulta = consulta + " AND " + NombreTabla + "." + ColumnaTareaXOrdenTrabajo.CODIGOTRABAJO + " = '" + codigoTrabajo + "'";
//        }
//        if (!codigoTarea.isEmpty()) {
//            consulta = consulta + " AND " + NombreTabla + "." + ColumnaTareaXOrdenTrabajo.CODIGOTAREA + " = '" + codigoTarea + "'";
//        }
//        if (!codigoEstadoTarea.isEmpty() && codigoEstadoTarea != Tarea.EstadoTarea.NINGUNA.getCodigo()) {
//            consulta = consulta + " AND " + ColumnaTareaXOrdenTrabajo.CODIGOESTADOTAREA + " = '" + codigoEstadoTarea + "'";
//        }
//        if (noDescargada) {
//            consulta = consulta + " AND " + ColumnaTareaXOrdenTrabajo.FECHADESCARGADA + " IS NULL OR " + ColumnaTareaXOrdenTrabajo.FECHADESCARGADA + " = '' ";
//        }
//        if (!serie.isEmpty()) {
//            consulta = consulta + " AND (" + LaborElementoDao.NombreTabla + "." + LaborElementoDao.ColumnaLaborElemento.SERIE + " like ('%" + serie + "%') OR " + NombreTabla + "." + ColumnaTareaXOrdenTrabajo.PARAMETROS + " like ('%" + serie + "%'))";
//        }
//
//        Cursor cursor = this.operadorDatos.cargar(consulta);
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//                lista.add(convertirCursorAObjetoTareaXOrdenTrabajo(cursor));
//                cursor.moveToNext();
//            }
//        }
//        cursor.close();
        return lista;
    }

    @Override
    public int asignarUltimaSecuencia(String codigoCorreria) {
        Cursor cursor = this.operadorDatos.cargar("SELECT MAX(" + ColumnaTareaXOrdenTrabajo.SECUENCIA
                + ") '" + ColumnaTareaXOrdenTrabajo.SECUENCIA + "' FROM " + TareaXOrdenTrabajoDao.NombreTabla + " WHERE " +
                ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' ");
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.SECUENCIA)) + 1;
        }
        cursor.close();
        return 0;
    }

    private List<TotalTareaXEstado> cargarTotalesPorEstado(String codigoCorreria,
                                                           String codigoTarea, String codigoTrabajo) {
        List<TotalTareaXEstado> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT " + ColumnaTareaXOrdenTrabajo.CODIGOESTADOTAREA
                + ", count(0) " + ColumnaTareaXOrdenTrabajo.CANTIDADTAREAS + " FROM " + NombreTabla +
                " WHERE " + ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + " = '" + codigoCorreria + "' " +
                "AND " + ColumnaTareaXOrdenTrabajo.CODIGOTAREA + " = '" + codigoTarea + "' AND " +
                ColumnaTareaXOrdenTrabajo.CODIGOTRABAJO + " = '" + codigoTrabajo + "' "
                + "GROUP BY " + ColumnaTareaXOrdenTrabajo.CODIGOESTADOTAREA);
        if (cursor.moveToFirst()) {
            TotalTareaXEstado totalTareaXEstado = new TotalTareaXEstado();
            while (!cursor.isAfterLast()) {
                totalTareaXEstado.getEstado().setCodigoEstado(cursor.getString(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.CODIGOESTADOTAREA)));
                totalTareaXEstado.setCantidad(cursor.getInt(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.CANTIDADTAREAS)));
                lista.add(totalTareaXEstado);
                totalTareaXEstado = new TotalTareaXEstado();
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    private TareaXOrdenTrabajo convertirCursorAObjetoTareaXOrdenTrabajo(Cursor cursor) {
        TareaXOrdenTrabajo tareaXOrdenTrabajo = new TareaXOrdenTrabajo();
        tareaXOrdenTrabajo.setCodigoCorreria(cursor.getString(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA)));
        tareaXOrdenTrabajo.setCodigoOrdenTrabajo(cursor.getString(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO)));
        tareaXOrdenTrabajo.setCodigoTrabajo(cursor.getString(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.CODIGOTRABAJO)));
        tareaXOrdenTrabajo.getTarea().setCodigoTarea(cursor.getString(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.CODIGOTAREA)));
        try {
            tareaXOrdenTrabajo.setFechaInicioTarea(DateHelper.convertirStringADate(cursor.getString(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.FECHAINICIOTAREA)), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            tareaXOrdenTrabajo.setFechaUltimaTarea(DateHelper.convertirStringADate(cursor.getString(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.FECHAULTIMATAREA)), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            tareaXOrdenTrabajo.setFechaDescarga(DateHelper.convertirStringADate(
                    cursor.getString(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.FECHADESCARGADA)),
                    DateHelper.TipoFormato.yyyyMMddTHHmmss));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.CODIGOUSUARIOTAREA))) {
            tareaXOrdenTrabajo.setCodigoUsuarioTarea(cursor.getString(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.CODIGOUSUARIOTAREA)));
        }
        tareaXOrdenTrabajo.setNueva(StringHelper.ToBoolean(cursor.getString(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.NUEVA))));
        tareaXOrdenTrabajo.setEstadoTarea(Tarea.EstadoTarea.getEstado(cursor.getString(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.CODIGOESTADOTAREA))));
        tareaXOrdenTrabajo.setSecuencia(cursor.getInt(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.SECUENCIA)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.PARAMETROS))) {
            tareaXOrdenTrabajo.setParametros(cursor.getString(cursor.getColumnIndex(ColumnaTareaXOrdenTrabajo.PARAMETROS)));
        }
        return tareaXOrdenTrabajo;
    }

    private ContentValues convertirObjetoAContentValues(TareaXTrabajoOrdenTrabajo tareaXOrdenTrabajo) {
        ContentValues contentValues = new ContentValues();
        if (!tareaXOrdenTrabajo.getCodigoCorreria().isEmpty()) {
            contentValues.put(ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA, tareaXOrdenTrabajo.getCodigoCorreria());
        }
        if (!tareaXOrdenTrabajo.getCodigoOrdenTrabajo().isEmpty()) {
            contentValues.put(ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO, tareaXOrdenTrabajo.getCodigoOrdenTrabajo());
        }
        if (!tareaXOrdenTrabajo.getCodigoTrabajo().isEmpty()) {
            contentValues.put(ColumnaTareaXOrdenTrabajo.CODIGOTRABAJO, tareaXOrdenTrabajo.getCodigoTrabajo());
        }
        if (!tareaXOrdenTrabajo.getTarea().getCodigoTarea().isEmpty()) {
            contentValues.put(ColumnaTareaXOrdenTrabajo.CODIGOTAREA, tareaXOrdenTrabajo.getTarea().getCodigoTarea());
        }

        try {
            contentValues.put(ColumnaTareaXOrdenTrabajo.FECHAINICIOTAREA, DateHelper.convertirDateAString(tareaXOrdenTrabajo.getFechaInicioTarea(), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        try {
            contentValues.put(ColumnaTareaXOrdenTrabajo.FECHAULTIMATAREA, DateHelper.convertirDateAString(tareaXOrdenTrabajo.getFechaUltimaTarea(), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        contentValues.put(ColumnaTareaXOrdenTrabajo.CODIGOUSUARIOTAREA, tareaXOrdenTrabajo.getCodigoUsuarioTarea());
        contentValues.put(ColumnaTareaXOrdenTrabajo.NUEVA, BooleanHelper.ToString(tareaXOrdenTrabajo.isNueva()));
        contentValues.put(ColumnaTareaXOrdenTrabajo.CODIGOESTADOTAREA, tareaXOrdenTrabajo.getEstadoTarea().getCodigo());
        contentValues.put(ColumnaTareaXOrdenTrabajo.SECUENCIA, tareaXOrdenTrabajo.getSecuencia());
        contentValues.put(ColumnaTareaXOrdenTrabajo.PARAMETROS, tareaXOrdenTrabajo.getParametros());
        return contentValues;
    }

    public static class ColumnaTareaXOrdenTrabajo {

        public static final String CODIGOCORRERIA = "codigocorreria";
        public static final String CODIGOORDENTRABAJO = "codigoordentrabajo";
        public static final String CODIGOTRABAJO = "codigotrabajo";
        public static final String CODIGOTAREA = "codigotarea";
        public static final String FECHAINICIOTAREA = "fechainiciotarea";
        public static final String FECHAULTIMATAREA = "fechaultimatarea";
        public static final String CODIGOUSUARIOTAREA = "codigousuariotarea";
        public static final String NUEVA = "nueva";
        public static final String CODIGOESTADOTAREA = "codigoestado";
        public static final String SECUENCIA = "secuencia";
        public static final String CANTIDADTAREAS = "cantidadtareas";
        public static final String PARAMETROS = "parametros";
        public static final String FECHADESCARGADA = "fechadescargada";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + " " + STRING_TYPE + " not null," +
                    ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO + " " + STRING_TYPE + " not null," +
                    ColumnaTareaXOrdenTrabajo.CODIGOTRABAJO + " " + STRING_TYPE + " not null," +
                    ColumnaTareaXOrdenTrabajo.CODIGOTAREA + " " + STRING_TYPE + " not null," +
                    ColumnaTareaXOrdenTrabajo.FECHAINICIOTAREA + " " + STRING_TYPE + " null," +
                    ColumnaTareaXOrdenTrabajo.FECHAULTIMATAREA + " " + STRING_TYPE + " null," +
                    ColumnaTareaXOrdenTrabajo.CODIGOUSUARIOTAREA + " " + STRING_TYPE + " null," +
                    ColumnaTareaXOrdenTrabajo.NUEVA + " " + STRING_TYPE + " null," +
                    ColumnaTareaXOrdenTrabajo.CODIGOESTADOTAREA + " " + STRING_TYPE + " not null," +
                    ColumnaTareaXOrdenTrabajo.SECUENCIA + " " + INT_TYPE + " not null," +
                    ColumnaTareaXOrdenTrabajo.PARAMETROS + " " + STRING_TYPE + " null," +
                    ColumnaTareaXOrdenTrabajo.FECHADESCARGADA + " " + STRING_TYPE + " null," +
                    " primary key (" + ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + "," +
                    ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO + "," +
                    ColumnaTareaXOrdenTrabajo.CODIGOTRABAJO + "," +
                    ColumnaTareaXOrdenTrabajo.CODIGOTAREA + ")" +
                    " FOREIGN KEY (" + ColumnaTareaXOrdenTrabajo.CODIGOCORRERIA + "," +
                    ColumnaTareaXOrdenTrabajo.CODIGOORDENTRABAJO + "," +
                    ColumnaTareaXOrdenTrabajo.CODIGOTRABAJO + ") REFERENCES " +
                    TrabajoXOrdenTrabajoDao.NombreTabla + "( " + TrabajoXOrdenTrabajoDao.ColumnaTrabajoXOrdenTrabajo.CODIGOCORRERIA + "," +
                    TrabajoXOrdenTrabajoDao.ColumnaTrabajoXOrdenTrabajo.CODIGOORDENTRABAJO + "," +
                    TrabajoXOrdenTrabajoDao.ColumnaTrabajoXOrdenTrabajo.CODIGOTRABAJO + ")  ON DELETE CASCADE ," +
                    " FOREIGN KEY (" + ColumnaTareaXOrdenTrabajo.CODIGOTRABAJO + "," +
                    ColumnaTareaXOrdenTrabajo.CODIGOTAREA + ") REFERENCES " +
                    TareaXTrabajoDao.NombreTabla + "( " + TareaXTrabajoDao.ColumnaTareaXTrabajo.CODIGOTRABAJO + "," +
                    TareaXTrabajoDao.ColumnaTareaXTrabajo.CODIGOTAREA + ") ON DELETE CASCADE)";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}

