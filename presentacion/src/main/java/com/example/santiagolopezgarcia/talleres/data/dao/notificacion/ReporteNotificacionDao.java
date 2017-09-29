package com.example.santiagolopezgarcia.talleres.data.dao.notificacion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.modelonegocio.ArchivoAdjunto;
import com.example.dominio.modelonegocio.FiltroCarga;
import com.example.dominio.modelonegocio.ReporteNotificacion;
import com.example.dominio.notificacion.ReporteNotificacionRepositorio;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;
import com.example.santiagolopezgarcia.talleres.data.dao.correria.CorreriaDao;
import com.example.utilidades.helpers.BooleanHelper;
import com.example.utilidades.helpers.StringHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class ReporteNotificacionDao extends DaoBase implements ReporteNotificacionRepositorio {

    static final String NombreTabla = "sirius_reportenotificacion";
    static final String INDICADOR_DESCARGA = "S";

    public ReporteNotificacionDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public List<ReporteNotificacion> cargar() {
        List<ReporteNotificacion> lista = new ArrayList<>();
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
    public List<ArchivoAdjunto> cargarArchivosXCodigoCorreria(String codigoCorreria) {
        List<ArchivoAdjunto> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar(
                "SELECT " + ReporteNotificacionDao.NombreTabla + "." +
                        ColumnaReporteNotificacion.DESCRIPCION + ", " + ItemDao.NombreTabla + "." +
                        ItemDao.ColumnaItem.PARAMETROS + " FROM " + ReporteNotificacionDao.NombreTabla +
                        " INNER JOIN " + ItemDao.NombreTabla +
                        " ON " + ReporteNotificacionDao.NombreTabla + "." + ColumnaReporteNotificacion.CODIGOITEM +
                        " = " + ItemDao.NombreTabla + "." + ItemDao.ColumnaItem.CODIGOITEM +
                        " WHERE " + ItemDao.ColumnaItem.RUTINA + " IN ('RutinaStandar3', 'RutinaStandar5', 'RutinaStandar7', 'RutinaStandar8')" +
                        " AND " + ReporteNotificacionDao.NombreTabla + "." +
                        ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "' " +
                        "AND " + ReporteNotificacionDao.NombreTabla + "." + ColumnaReporteNotificacion.DESCARGA + " = 'S'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ArchivoAdjunto archivoAdjunto = new ArchivoAdjunto();
                archivoAdjunto.setNombreArchivo(cursor.getString(cursor.getColumnIndex(ColumnaReporteNotificacion.DESCRIPCION)));
                archivoAdjunto.setRutaArchivo(cursor.getString(cursor.getColumnIndex(ItemDao.ColumnaItem.PARAMETROS)));
                lista.add(archivoAdjunto);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    @Override
    public boolean eliminar(String codigoCorreria, String codigoOrdenTrabajo, String codigoTarea) {
        int resultado = this.operadorDatos.borrar(ColumnaReporteNotificacion.CODIGOCORRERIA + " = ? AND " +
                        ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " = ? AND " +
                        ColumnaReporteNotificacion.CODIGOTAREA + " = ?"
                , new String[]{codigoCorreria, codigoOrdenTrabajo,
                        codigoTarea});
        return resultado > 0;
    }

    @Override
    public List<String> cargarNombreArchivos() {
        List<String> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar(
                "SELECT " + ReporteNotificacionDao.NombreTabla + "." +
                        ColumnaReporteNotificacion.DESCRIPCION + " FROM " + ReporteNotificacionDao.NombreTabla +
                        " INNER JOIN " + ItemDao.NombreTabla +
                        " ON " + ReporteNotificacionDao.NombreTabla + "." + ColumnaReporteNotificacion.CODIGOITEM +
                        " = " + ItemDao.NombreTabla + "." + ItemDao.ColumnaItem.CODIGOITEM +
                        " WHERE " + ItemDao.ColumnaItem.RUTINA + " IN ('RutinaStandar3', 'RutinaStandar5', 'RutinaStandar7', 'RutinaStandar8')");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                lista.add(cursor.getString(cursor.getColumnIndex(ColumnaReporteNotificacion.DESCRIPCION)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    @Override
    public List<String> cargarNombreArchivos(FiltroCarga filtroCarga) {
        List<String> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar(
                "SELECT " + ReporteNotificacionDao.NombreTabla + "." +
                        ColumnaReporteNotificacion.DESCRIPCION + " FROM " + ReporteNotificacionDao.NombreTabla +
                        " INNER JOIN " + ItemDao.NombreTabla +
                        " ON " + ReporteNotificacionDao.NombreTabla + "." + ColumnaReporteNotificacion.CODIGOITEM +
                        " = " + ItemDao.NombreTabla + "." + ItemDao.ColumnaItem.CODIGOITEM +
                        " WHERE " + ItemDao.ColumnaItem.RUTINA + " IN ('RutinaStandar3', 'RutinaStandar5', 'RutinaStandar7', 'RutinaStandar8') AND " +
                        ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + filtroCarga.getCodigoCorreria() + "' AND " +
                        ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " ='" + filtroCarga.getCodigoOrdenTrabajo() + "' AND " +
                        ColumnaReporteNotificacion.CODIGOTAREA + " = '" + filtroCarga.getCodigoTarea() + "' AND " +
                        ColumnaReporteNotificacion.DESCARGA + " = '" + INDICADOR_DESCARGA + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                lista.add(cursor.getString(cursor.getColumnIndex(ColumnaReporteNotificacion.DESCRIPCION)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    @Override
    public List<ReporteNotificacion> cargarListaReporteNotificacionXLabor(String codigoCorreria, String codigoOrdenTrabajo,
                                                                          String codigoTarea, String codigoLabor) {
        List<ReporteNotificacion> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGOTAREA + " = '" + codigoTarea + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGOLABOR + " = '" + codigoLabor + "'");
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
    public List<ReporteNotificacion> cargarReporteNotificacion(String codigoCorreria, String codigoOrdenTrabajo,
                                                               String codigoTarea, String codigoLabor, String codigoItem,
                                                               String codigoNotificacion) {
        List<ReporteNotificacion> listaReporteNotificacion = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOTAREA + " = '" + codigoTarea + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOLABOR + " = '" + codigoLabor + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOITEM + " = '" + codigoItem + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listaReporteNotificacion.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listaReporteNotificacion;
    }

    @Override
    public ReporteNotificacion consultarReporteNotificacionItemLista(String codigoCorreria, String codigoOrdenTrabajo,
                                                                     String codigoTarea, String codigoLabor, String codigoItem,
                                                                     String codigoNotificacion, String codigoLista) {
        ReporteNotificacion reporteNotificacion = new ReporteNotificacion();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGOTAREA + " = '" + codigoTarea + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGOLABOR + " = '" + codigoLabor + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGOITEM + " = '" + codigoItem + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGOLISTA + " = '" + codigoLista + "'");
        if (cursor.moveToFirst()) {
            reporteNotificacion = convertirCursorAObjeto(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return reporteNotificacion;
    }

    @Override
    public ReporteNotificacion consultarReporteNotificacionItemListaSencilla(String codigoCorreria, String codigoOrdenTrabajo,
                                                                             String codigoTarea, String codigoLabor, String codigoItem,
                                                                             String codigoNotificacion) {
        ReporteNotificacion reporteNotificacion = new ReporteNotificacion();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGOTAREA + " = '" + codigoTarea + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGOLABOR + " = '" + codigoLabor + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGOITEM + " = '" + codigoItem + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "'");
        if (cursor.moveToFirst()) {
            reporteNotificacion = convertirCursorAObjeto(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return reporteNotificacion;
    }

    @Override
    public List<ReporteNotificacion> consultarExistenciaReporteNotificacion(String codigoCorreria,
                                                                            String codigoOT,
                                                                            String codigoTarea,
                                                                            String codigoLabor,
                                                                            String codigoNotificacion) {
        List<ReporteNotificacion> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " = '" + codigoOT + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGOTAREA + " = '" + codigoTarea + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGOLABOR + " = '" + codigoLabor + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "'");
        if (cursor.moveToFirst()) {
            lista.add(convertirCursorAObjeto(cursor));
            cursor.moveToNext();
        }
        return lista;
    }

    @Override
    public List<ReporteNotificacion> consultarExistenciaReporteNotificacion(String codigoCorreria,
                                                                            String codigoOT,
                                                                            String codigoLabor,
                                                                            String codigoNotificacion) {
        List<ReporteNotificacion> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " = '" + codigoOT + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGOLABOR + " = '" + codigoLabor + "'" +
                " AND " + ColumnaReporteNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "'");
        if (cursor.moveToFirst()) {
            lista.add(convertirCursorAObjeto(cursor));
            cursor.moveToNext();
        }
        return lista;
    }

    @Override
    public boolean eliminarReporteNotificacionSencillo(ReporteNotificacion reporteNotificacion) {
        return this.operadorDatos.borrar("codigocorreria = ? AND codigoordentrabajo = ? " +
                        "AND codigotarea = ? AND codigolabor = ? AND codigoitem = ? AND codigonotificacion = ?"
                , new String[]{reporteNotificacion.getCodigoCorreria(), reporteNotificacion.getCodigoOrdenTrabajo(),
                        reporteNotificacion.getCodigoTarea(), reporteNotificacion.getCodigoLabor(),
                        reporteNotificacion.getCodigoItem(), reporteNotificacion.getCodigoNotificacion()}) > 0;
    }

    @Override
    public boolean eliminar(ReporteNotificacion reporteNotificacion) {
        return this.operadorDatos.borrar("codigocorreria = ? AND codigoordentrabajo = ? " +
                        "AND codigotarea = ? AND codigolabor = ? AND codigoitem = ? AND codigonotificacion = ? " +
                        "AND codigolista = ? "
                , new String[]{reporteNotificacion.getCodigoCorreria(), reporteNotificacion.getCodigoOrdenTrabajo(),
                        reporteNotificacion.getCodigoTarea(), reporteNotificacion.getCodigoLabor(),
                        reporteNotificacion.getCodigoItem(), reporteNotificacion.getCodigoNotificacion(),
                        reporteNotificacion.getCodigoLista()}) > 0;
    }

    @Override
    public boolean actualizar(ReporteNotificacion reporteNotificacion) {
        ContentValues datos = convertirObjetoAContentValues(reporteNotificacion);
        return this.operadorDatos.actualizar(datos, "codigocorreria = ? AND codigoordentrabajo = ? " +
                        "AND codigotarea = ? AND codigolabor = ? AND codigoitem = ? " +
                        "AND codigonotificacion = ?"
                , new String[]{reporteNotificacion.getCodigoCorreria(), reporteNotificacion.getCodigoOrdenTrabajo(),
                        reporteNotificacion.getCodigoTarea(), reporteNotificacion.getCodigoLabor(),
                        reporteNotificacion.getCodigoItem(), reporteNotificacion.getCodigoNotificacion()});

    }

    @Override
    public boolean guardar(ReporteNotificacion reporteNotificacion) {
        ContentValues contentValues = convertirObjetoAContentValues(reporteNotificacion);
        boolean resultado = this.operadorDatos.insertar(contentValues);
        return resultado;
    }

    @Override
    public List<String> cargar(String codigoCorreria) {
        List<String> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT " + ColumnaReporteNotificacion.DESCRIPCION + " FROM " + NombreTabla +
                " WHERE " + ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "'");

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                lista.add(cursor.getString(cursor.getColumnIndex(ColumnaReporteNotificacion.DESCRIPCION)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    @Override
    public List<ReporteNotificacion> cargarReporteNotificacion(String codigoCorreria) {
        List<ReporteNotificacion> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "'" +
                " AND " + ColumnaReporteNotificacion.DESCARGA + " = '" + INDICADOR_DESCARGA + "'");
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
    public List<ReporteNotificacion> cargar(String codigoCorreria, String codigoOrdenTrabajo, String codigoTarea) {
        List<ReporteNotificacion> listaReporteNotificacion = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOTAREA + " = '" + codigoTarea + "' " +
                "AND " + ColumnaReporteNotificacion.DESCARGA + " = '" + INDICADOR_DESCARGA + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listaReporteNotificacion.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listaReporteNotificacion;
    }

    @Override
    public List<ReporteNotificacion> consultarReporteNotificacionSencillo(String codigoCorreria, String codigoOT, String codigoTarea, String codigoItem, String codigoNotificacion) {
        List<ReporteNotificacion> listaReporteNotificacion = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " = '" + codigoOT + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOTAREA + " = '" + codigoTarea + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOITEM + " = '" + codigoItem + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listaReporteNotificacion.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listaReporteNotificacion;
    }

    @Override
    public List<ReporteNotificacion> cargarReporteNotificacionCompleto(String codigoCorreria) {
        List<ReporteNotificacion> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "'");
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
    public List<ReporteNotificacion> cargarReporteNotificacionXNotificacion(String codigoCorreria, String codigoOT, String codigoTarea, String codigoNotificacion) {
        List<ReporteNotificacion> lista = new ArrayList<>();

        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " = '" + codigoOT + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOTAREA + " = '" + codigoTarea + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "'");
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
    public List<ReporteNotificacion> cargarReporteNotificacionXItems(String codigoCorreria, String codigoOT, String codigoTarea,
                                                                     String codigoNotificacion, String codigoItems) {
        List<ReporteNotificacion> lista = new ArrayList<>();

        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " = '" + codigoOT + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOTAREA + " = '" + codigoTarea + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "'" +
                "AND " + ColumnaReporteNotificacion.CODIGOITEM + " IN (" + codigoItems + ")" );
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
    public List<ArchivoAdjunto> cargarArchivosXFiltro(String codigoCorreria, String codigoOrdenTrabajo, String codigoTarea) {
        List<ArchivoAdjunto> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar(
                "SELECT " + ReporteNotificacionDao.NombreTabla + "." +
                        ColumnaReporteNotificacion.DESCRIPCION + ", " + ItemDao.NombreTabla + "." +
                        ItemDao.ColumnaItem.PARAMETROS + " FROM " + ReporteNotificacionDao.NombreTabla +
                        " INNER JOIN " + ItemDao.NombreTabla +
                        " ON " + ReporteNotificacionDao.NombreTabla + "." + ColumnaReporteNotificacion.CODIGOITEM +
                        " = " + ItemDao.NombreTabla + "." + ItemDao.ColumnaItem.CODIGOITEM +
                        " WHERE " + ItemDao.ColumnaItem.RUTINA + " IN ('RutinaStandar3', 'RutinaStandar5', 'RutinaStandar7', 'RutinaStandar8')" +
                        " AND " + ReporteNotificacionDao.NombreTabla + "." +
                        ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "'" +
                        " AND " + ReporteNotificacionDao.NombreTabla + "." +
                        ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "' " +
                        " AND " + ReporteNotificacionDao.NombreTabla + "." +
                        ColumnaReporteNotificacion.CODIGOTAREA + " = '" + codigoTarea + "' " +
                        "AND " + ReporteNotificacionDao.NombreTabla + "." + ColumnaReporteNotificacion.DESCARGA + " = 'S'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ArchivoAdjunto archivoAdjunto = new ArchivoAdjunto();
                archivoAdjunto.setNombreArchivo(cursor.getString(cursor.getColumnIndex(ColumnaReporteNotificacion.DESCRIPCION)));
                archivoAdjunto.setRutaArchivo(cursor.getString(cursor.getColumnIndex(ItemDao.ColumnaItem.PARAMETROS)));
                lista.add(archivoAdjunto);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    @Override
    public List<ArchivoAdjunto> cargarArchivosXFiltro(String codigoCorreria, String codigoOrdenTrabajo,
                                                      String codigoTarea, String codigoLabor, String codigoNotificacion) {
        List<ArchivoAdjunto> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar(
                "SELECT " + ReporteNotificacionDao.NombreTabla + "." +
                        ColumnaReporteNotificacion.DESCRIPCION + ", " + ItemDao.NombreTabla + "." +
                        ItemDao.ColumnaItem.PARAMETROS + " FROM " + ReporteNotificacionDao.NombreTabla +
                        " INNER JOIN " + ItemDao.NombreTabla +
                        " ON " + ReporteNotificacionDao.NombreTabla + "." + ColumnaReporteNotificacion.CODIGOITEM +
                        " = " + ItemDao.NombreTabla + "." + ItemDao.ColumnaItem.CODIGOITEM +
                        " WHERE " + ItemDao.ColumnaItem.RUTINA + " IN ('RutinaStandar3', 'RutinaStandar5', 'RutinaStandar7', 'RutinaStandar8')" +
                        " AND " + ReporteNotificacionDao.NombreTabla + "." +
                        ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "'" +
                        " AND " + ReporteNotificacionDao.NombreTabla + "." +
                        ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "' " +
                        " AND " + ReporteNotificacionDao.NombreTabla + "." +
                        ColumnaReporteNotificacion.CODIGOTAREA + " = '" + codigoTarea + "' " +
                        " AND " + ReporteNotificacionDao.NombreTabla + "." +
                        ColumnaReporteNotificacion.CODIGOLABOR + " = '" + codigoLabor + "' " +
                        " AND " + ReporteNotificacionDao.NombreTabla + "." +
                        ColumnaReporteNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "' " +
                        "AND " + ReporteNotificacionDao.NombreTabla + "." + ColumnaReporteNotificacion.DESCARGA + " = 'S'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ArchivoAdjunto archivoAdjunto = new ArchivoAdjunto();
                archivoAdjunto.setNombreArchivo(cursor.getString(cursor.getColumnIndex(ColumnaReporteNotificacion.DESCRIPCION)));
                archivoAdjunto.setRutaArchivo(cursor.getString(cursor.getColumnIndex(ItemDao.ColumnaItem.PARAMETROS)));
                lista.add(archivoAdjunto);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }

    @Override
    public List<ReporteNotificacion> cargarFiltroCompleto(String codigoCorreria, String codigoOrdenTrabajo, String codigoTarea) {
        List<ReporteNotificacion> listaReporteNotificacion = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOTAREA + " = '" + codigoTarea + "' ");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listaReporteNotificacion.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listaReporteNotificacion;
    }


    @Override
    public List<ReporteNotificacion> cargarFiltroCompleto(String codigoCorreria, String codigoOrdenTrabajo,
                                                          String codigoTarea, String codigoLabor, String codigoNotificacion) {
        List<ReporteNotificacion> listaReporteNotificacion = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOTAREA + " = '" + codigoTarea + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOLABOR + " = '" + codigoLabor + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "' ");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listaReporteNotificacion.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listaReporteNotificacion;
    }

    @Override
    public ReporteNotificacion cargarReporteNotificacionSencillo(String codigoCorreria,
                                                                 String codigoOrdenTrabajo,
                                                                 String codigoNotificacion, String codigoItem) {
        ReporteNotificacion reporteNotificacion = new ReporteNotificacion();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGONOTIFICACION + " = '" + codigoNotificacion + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOITEM + " = '" + codigoItem + "' ");

        if (cursor.moveToFirst()) {
            reporteNotificacion = convertirCursorAObjeto(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return reporteNotificacion;
    }

    @Override
    public List<ReporteNotificacion> cargar(String codigoCorreria, String codigoOrdenTrabajo) {
        List<ReporteNotificacion> listaReporteNotificacion = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaReporteNotificacion.CODIGOCORRERIA + " = '" + codigoCorreria + "' " +
                "AND " + ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " = '" + codigoOrdenTrabajo + "' " +
                "AND " + ColumnaReporteNotificacion.DESCARGA + " = '" + INDICADOR_DESCARGA + "'");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listaReporteNotificacion.add(convertirCursorAObjeto(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listaReporteNotificacion;
    }

    private ReporteNotificacion convertirCursorAObjeto(Cursor cursor) {
        ReporteNotificacion reporteNotificacion = new ReporteNotificacion();
        reporteNotificacion.setCodigoCorreria(cursor.getString(cursor.getColumnIndex(ColumnaReporteNotificacion.CODIGOCORRERIA)));
        reporteNotificacion.setCodigoOrdenTrabajo(cursor.getString(cursor.getColumnIndex(ColumnaReporteNotificacion.CODIGOORDENTRABAJO)));
        reporteNotificacion.setCodigoTarea(cursor.getString(cursor.getColumnIndex(ColumnaReporteNotificacion.CODIGOTAREA)));
        reporteNotificacion.setCodigoLabor(cursor.getString(cursor.getColumnIndex(ColumnaReporteNotificacion.CODIGOLABOR)));
        reporteNotificacion.setCodigoNotificacion(cursor.getString(cursor.getColumnIndex(ColumnaReporteNotificacion.CODIGONOTIFICACION)));
        reporteNotificacion.setCodigoItem(cursor.getString(cursor.getColumnIndex(ColumnaReporteNotificacion.CODIGOITEM)));
        reporteNotificacion.setCodigoLista(cursor.getString(cursor.getColumnIndex(ColumnaReporteNotificacion.CODIGOLISTA)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaReporteNotificacion.ORDEN))) {
            reporteNotificacion.setOrden(cursor.getString(cursor.getColumnIndex(ColumnaReporteNotificacion.ORDEN)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaReporteNotificacion.DESCRIPCION))) {
            reporteNotificacion.setDescripcion(cursor.getString(cursor.getColumnIndex(ColumnaReporteNotificacion.DESCRIPCION)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaReporteNotificacion.DESCARGA))) {
            reporteNotificacion.setDescarga(StringHelper.ToBoolean(cursor.getString(cursor.getColumnIndex(ColumnaReporteNotificacion.DESCARGA))));
        }
        return reporteNotificacion;
    }

    private ContentValues convertirObjetoAContentValues(ReporteNotificacion reporteNotificacion) {
        ContentValues contentValues = new ContentValues();
        if (!reporteNotificacion.getCodigoCorreria().isEmpty()) {
            contentValues.put(ColumnaReporteNotificacion.CODIGOCORRERIA, reporteNotificacion.getCodigoCorreria());
        }
        if (!reporteNotificacion.getCodigoOrdenTrabajo().isEmpty()) {
            contentValues.put(ColumnaReporteNotificacion.CODIGOORDENTRABAJO, reporteNotificacion.getCodigoOrdenTrabajo());
        }
        if (!reporteNotificacion.getCodigoTarea().isEmpty()) {
            contentValues.put(ColumnaReporteNotificacion.CODIGOTAREA, reporteNotificacion.getCodigoTarea());
        }
        if (!reporteNotificacion.getCodigoLabor().isEmpty()) {
            contentValues.put(ColumnaReporteNotificacion.CODIGOLABOR, reporteNotificacion.getCodigoLabor());
        }
        if (!reporteNotificacion.getCodigoNotificacion().isEmpty()) {
            contentValues.put(ColumnaReporteNotificacion.CODIGONOTIFICACION, reporteNotificacion.getCodigoNotificacion());
        }
        if (!reporteNotificacion.getCodigoItem().isEmpty()) {
            contentValues.put(ColumnaReporteNotificacion.CODIGOITEM, reporteNotificacion.getCodigoItem());
        }
        if (!reporteNotificacion.getCodigoLista().isEmpty()) {
            contentValues.put(ColumnaReporteNotificacion.CODIGOLISTA, reporteNotificacion.getCodigoLista());
        }
        contentValues.put(ColumnaReporteNotificacion.ORDEN, reporteNotificacion.getOrden());
        contentValues.put(ColumnaReporteNotificacion.DESCRIPCION, reporteNotificacion.getDescripcion());
        contentValues.put(ColumnaReporteNotificacion.DESCARGA, BooleanHelper.ToString(reporteNotificacion.getDescarga()));
        return contentValues;
    }

    @Override
    public boolean guardar(List<ReporteNotificacion> lista) throws ParseException {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (ReporteNotificacion notificacion : lista) {
            listaContentValues.add(convertirObjetoAContentValues(notificacion));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    public static class ColumnaReporteNotificacion {

        public static final String CODIGOCORRERIA = "codigocorreria";
        public static final String CODIGOORDENTRABAJO = "codigoordentrabajo";
        public static final String CODIGOTAREA = "codigotarea";
        public static final String CODIGOLABOR = "codigolabor";
        public static final String CODIGONOTIFICACION = "codigonotificacion";
        public static final String CODIGOITEM = "codigoitem";
        public static final String CODIGOLISTA = "codigolista";
        public static final String ORDEN = "orden";
        public static final String DESCRIPCION = "descripcion";
        public static final String DESCARGA = "descarga";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaReporteNotificacion.CODIGOCORRERIA + " " + STRING_TYPE + " not null," +
                    ColumnaReporteNotificacion.CODIGOORDENTRABAJO + " " + STRING_TYPE + " not null," +
                    ColumnaReporteNotificacion.CODIGOTAREA + " " + STRING_TYPE + " not null," +
                    ColumnaReporteNotificacion.CODIGOLABOR + " " + STRING_TYPE + " not null," +
                    ColumnaReporteNotificacion.CODIGONOTIFICACION + " " + STRING_TYPE + " not null," +
                    ColumnaReporteNotificacion.CODIGOITEM + " " + STRING_TYPE + " not null," +
                    ColumnaReporteNotificacion.CODIGOLISTA + " " + STRING_TYPE + " not null," +
                    ColumnaReporteNotificacion.ORDEN + " " + STRING_TYPE + " null," +
                    ColumnaReporteNotificacion.DESCRIPCION + " " + STRING_TYPE + " null," +
                    ColumnaReporteNotificacion.DESCARGA + " " + STRING_TYPE + " null," +
                    " primary key (" + ColumnaReporteNotificacion.CODIGOCORRERIA + "," +
                    ColumnaReporteNotificacion.CODIGOORDENTRABAJO + "," +
                    ColumnaReporteNotificacion.CODIGOTAREA + "," +
                    ColumnaReporteNotificacion.CODIGOLABOR + "," +
                    ColumnaReporteNotificacion.CODIGONOTIFICACION + "," +
                    ColumnaReporteNotificacion.CODIGOITEM + "," +
                    ColumnaReporteNotificacion.CODIGOLISTA + ")" +
                    " FOREIGN KEY (" + ColumnaReporteNotificacion.CODIGONOTIFICACION + "," +
                    ColumnaReporteNotificacion.CODIGOITEM + ") REFERENCES " +
                    ItemXNotificacionDao.NombreTabla + "( " + ItemXNotificacionDao.ColumnaItemXNotificacion.CODIGONOTIFICACION + "," +
                    ItemXNotificacionDao.ColumnaItemXNotificacion.CODIGOITEM + ")," +
                    " FOREIGN KEY (" + ColumnaReporteNotificacion.CODIGOCORRERIA + ") REFERENCES " +
                    CorreriaDao.NombreTabla + "( " + CorreriaDao.ColumnaCorreria.CODIGOCORRERIA + ") ON DELETE CASCADE)";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}