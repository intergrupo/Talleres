package com.example.dominio.labor;

import android.support.annotation.Nullable;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.LaborXOrdenTrabajo;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public interface LaborXOrdenTrabajoRepositorio extends RepositorioBase<LaborXOrdenTrabajo> {
    List<LaborXOrdenTrabajo> cargarLaboresXOrdenTrabajo(String codigoCorreria, String codigoOrdenTrabajo, String codigoTrabajo, String codigoTarea) throws ParseException;

    List<LaborXOrdenTrabajo> cargarLaboresXOrdenTrabajoFiltro(String codigoCorreria, String codigoOrdenTrabajo, String codigoTrabajo, String codigoTarea) throws ParseException;

    List<LaborXOrdenTrabajo> cargarLaboresXOrdenTrabajo(String codigoCorreria) throws ParseException;

    LaborXOrdenTrabajo cargarLaborXOT(String codigoCorreria, String codigoOrdenTrabajo, String codigoTrabajo, String codigoTarea, String codigoLabor) throws ParseException;

    List<LaborXOrdenTrabajo> cargarLaboresXOrdenTrabajoObligatoriasSinEjecutar(String codigoCorreria, String codigoOrdenTrabajo, String codigoTrabajo, String codigoTarea) throws ParseException;

    List<LaborXOrdenTrabajo> cargarLaboresXOrdenTrabajoEjecutadas(String codigoCorreria, String codigoOrdenTrabajo, String codigoTrabajo, String codigoTarea) throws ParseException;

    List<LaborXOrdenTrabajo> cargarLaboresXOTMenu(String codigoCorreria, String codigoOT);

    void actualizarEstadoOrdenTrabajo(String numeroOt, String correria, @Nullable String codEstado);

    void actualizarEstadoOTFacturada(String numeroOt, String correria,String codEstado, String fechaUltimaLabor);

    boolean actualizarEstado(LaborXOrdenTrabajo laborXOrdenTrabajo);

}