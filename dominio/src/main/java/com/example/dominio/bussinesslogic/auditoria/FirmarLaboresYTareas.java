package com.example.dominio.bussinesslogic.auditoria;

import com.example.dominio.bussinesslogic.correria.CorreriaRepositorio;
import com.example.dominio.bussinesslogic.labor.LaborXOrdenTrabajoRepositorio;
import com.example.dominio.modelonegocio.Correria;
import com.example.dominio.modelonegocio.LaborXOrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.dominio.modelonegocio.TareaXOrdenTrabajo;
import com.example.dominio.modelonegocio.TareaXTrabajoOrdenTrabajo;
import com.example.dominio.bussinesslogic.ordentrabajo.OrdenTrabajoRepositorio;
import com.example.dominio.bussinesslogic.tarea.TareaXOrdenTrabajoRepositorio;
import com.example.utilidades.helpers.DateHelper;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public class FirmarLaboresYTareas {
    private TareaXOrdenTrabajoRepositorio tareaXOrdenTrabajoRepositorio;
    private LaborXOrdenTrabajoRepositorio laborXOrdenTrabajoRepositorio;
    private CorreriaRepositorio correriaRepositorio;
    private OrdenTrabajoRepositorio ordenTrabajoRepositorio;


    public FirmarLaboresYTareas(TareaXOrdenTrabajoRepositorio tareaXOrdenTrabajoRepositorio,
                                LaborXOrdenTrabajoRepositorio laborXOrdenTrabajoRepositorio,
                                CorreriaRepositorio correriaRepositorio,
                                OrdenTrabajoRepositorio ordenTrabajoRepositorio) {
        this.tareaXOrdenTrabajoRepositorio = tareaXOrdenTrabajoRepositorio;
        this.laborXOrdenTrabajoRepositorio = laborXOrdenTrabajoRepositorio;
        this.correriaRepositorio = correriaRepositorio;
        this.ordenTrabajoRepositorio = ordenTrabajoRepositorio;

    }

    public void actualizarFechasOT(String codigoCorreria, String codigoOT, String codigoUsuario) {
        OrdenTrabajo ordenTrabajo = ordenTrabajoRepositorio.cargar(codigoCorreria, codigoOT);
        ordenTrabajo.setCodigoUsuarioLabor(codigoUsuario);
        if (ordenTrabajo.getFechaInicioOrdenTrabajo() == null) {
            ordenTrabajo.setFechaInicioOrdenTrabajo(new Date());
        }
        ordenTrabajo.setFechaUltimaOrdenTrabajo(new Date());
        ordenTrabajoRepositorio.actualizar(ordenTrabajo);
    }

    public void actualizarFechasCorreria(String codigoCorreria) {
        Correria correria = correriaRepositorio.cargar(codigoCorreria);
        try {
            if (correria.getFechaInicioCorreria().isEmpty()) {
                correria.setFechaInicioCorreria(DateHelper.convertirDateAString(new Date(), DateHelper.TipoFormato.yyyyMMddTHHmmss));
            }
            correria.setFechaUltimaCorreria(DateHelper.convertirDateAString(new Date(), DateHelper.TipoFormato.yyyyMMddTHHmmss));
            correriaRepositorio.actualizar(correria);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void actualizarFechasLaborXOT(LaborXOrdenTrabajo laborXOrdenTrabajo) {
        String codigoUsuario = laborXOrdenTrabajo.getCodigoUsuarioLabor();
        try {
            laborXOrdenTrabajo = laborXOrdenTrabajoRepositorio.cargarLaborXOT(laborXOrdenTrabajo.getCodigoCorreria(),
                    laborXOrdenTrabajo.getCodigoOrdenTrabajo(),
                    laborXOrdenTrabajo.getTrabajo().getCodigoTrabajo(),
                    laborXOrdenTrabajo.getTarea().getCodigoTarea(), laborXOrdenTrabajo.getLabor().getCodigoLabor());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (laborXOrdenTrabajo.getFechaInicioLabor() == null) {
            laborXOrdenTrabajo.setFechaInicioLabor(new Date());
        }
        laborXOrdenTrabajo.setCodigoUsuarioLabor(codigoUsuario);
        laborXOrdenTrabajo.setFechaUltimoLabor(new Date());
        laborXOrdenTrabajoRepositorio.actualizar(laborXOrdenTrabajo);
    }

    public void actualizarFechasTareaXOT(TareaXOrdenTrabajo tareaXOrdenTrabajo) {
        TareaXTrabajoOrdenTrabajo tareaXTrabajoOrdenTrabajo = tareaXOrdenTrabajo;
        if (tareaXTrabajoOrdenTrabajo.getFechaInicioTarea() == null) {
            tareaXTrabajoOrdenTrabajo.setFechaInicioTarea(new Date());
        }
        tareaXTrabajoOrdenTrabajo.setFechaUltimaTarea(new Date());
        tareaXOrdenTrabajoRepositorio.actualizar(tareaXTrabajoOrdenTrabajo);
    }

    public void actualizarFechasTareaXOT(LaborXOrdenTrabajo laborXOrdenTrabajo) {
        TareaXTrabajoOrdenTrabajo tareaXTrabajoOrdenTrabajo = tareaXOrdenTrabajoRepositorio
                .cargarTareaXTrabajoOrdenTrabajo(laborXOrdenTrabajo.getCodigoCorreria(),
                        laborXOrdenTrabajo.getCodigoOrdenTrabajo(), laborXOrdenTrabajo.getTarea().getCodigoTarea(),
                        laborXOrdenTrabajo.getTrabajo().getCodigoTrabajo());
        if (tareaXTrabajoOrdenTrabajo.getFechaInicioTarea() == null) {
            tareaXTrabajoOrdenTrabajo.setFechaInicioTarea(new Date());
        }
        tareaXTrabajoOrdenTrabajo.setCodigoUsuarioTarea(laborXOrdenTrabajo.getCodigoUsuarioLabor());
        tareaXTrabajoOrdenTrabajo.setFechaUltimaTarea(new Date());
        tareaXOrdenTrabajoRepositorio.actualizar(tareaXTrabajoOrdenTrabajo);
    }
}