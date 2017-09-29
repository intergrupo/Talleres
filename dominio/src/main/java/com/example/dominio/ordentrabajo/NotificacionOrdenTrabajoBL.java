package com.example.dominio.ordentrabajo;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.auditoria.FirmarLaboresYTareas;
import com.example.dominio.labor.LaborRepositorio;
import com.example.dominio.modelonegocio.Labor;
import com.example.dominio.modelonegocio.NotificacionOrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.dominio.modelonegocio.Tarea;
import com.example.dominio.modelonegocio.TareaXOrdenTrabajo;
import com.example.dominio.modelonegocio.TareaXTrabajoOrdenTrabajo;
import com.example.dominio.tarea.TareaXOrdenTrabajoRepositorio;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/29/17.
 */

public class NotificacionOrdenTrabajoBL implements LogicaNegocioBase<NotificacionOrdenTrabajo> {

    private OrdenTrabajoRepositorio ordenTrabajoRepositorio;
    private TareaXOrdenTrabajoRepositorio tareaXOrdenTrabajoRepositorio;
    private LaborRepositorio laborRepositorio;
    private FirmarLaboresYTareas firmarLaboresYTareas;

    public NotificacionOrdenTrabajoBL(OrdenTrabajoRepositorio ordenTrabajoRepositorio,
                                      TareaXOrdenTrabajoRepositorio tareaXOrdenTrabajoRepositorio,
                                      LaborRepositorio laborRepositorio,
                                      FirmarLaboresYTareas firmarLaboresYTareas) {
        this.ordenTrabajoRepositorio = ordenTrabajoRepositorio;
        this.tareaXOrdenTrabajoRepositorio = tareaXOrdenTrabajoRepositorio;
        this.laborRepositorio = laborRepositorio;
        this.firmarLaboresYTareas = firmarLaboresYTareas;
    }


    @Override
    public boolean procesar(List<NotificacionOrdenTrabajo> listaDatos, String operacion) {

        for (NotificacionOrdenTrabajo notificacionOrdenTrabajo : listaDatos) {
            Labor labor = laborRepositorio.cargarLaborxCodigo(operacion);
            if (!labor.getCodigoLabor().isEmpty()) {
                switch (labor.getRutina()) {
                    case "Cancelar":
                        cancelarOrdenesYTareas(notificacionOrdenTrabajo, labor);
                        break;
                }
            }
        }
        return true;
    }

    private void cancelarOrdenesYTareas(NotificacionOrdenTrabajo notificacionOrdenTrabajo, Labor labor) {

        OrdenTrabajo ordenTrabajo = ordenTrabajoRepositorio.cargar(notificacionOrdenTrabajo.getCodigoCorreria(),
                notificacionOrdenTrabajo.getCodigoOrdenTrabajo());


        if (ordenTrabajo.getEstado().equals(OrdenTrabajo.EstadoOrdenTrabajo.ENEJECUCION)) {
            if (!notificacionOrdenTrabajo.getCodigoTarea().isEmpty()) {
                List<TareaXTrabajoOrdenTrabajo> listaTareaXOrdenTrabajo = tareaXOrdenTrabajoRepositorio.cargarListaTareaXTrabajoOrdenTrabajoXTarea(notificacionOrdenTrabajo.getCodigoCorreria(),
                        notificacionOrdenTrabajo.getCodigoOrdenTrabajo(), notificacionOrdenTrabajo.getCodigoTarea());

                if (listaTareaXOrdenTrabajo.size() > 0) {
                    for (TareaXTrabajoOrdenTrabajo tareaXTrabajoOrdenTrabajo : listaTareaXOrdenTrabajo) {
                        if (tareaXTrabajoOrdenTrabajo.getEstadoTarea().equals(Tarea.EstadoTarea.PENDIENTE)) {
                            tareaXTrabajoOrdenTrabajo.setEstadoTarea(Tarea.EstadoTarea.CANCELADA);
                            tareaXTrabajoOrdenTrabajo.setCodigoUsuarioTarea(ordenTrabajo.getCodigoUsuarioLabor());
                            tareaXOrdenTrabajoRepositorio.actualizar(tareaXTrabajoOrdenTrabajo);
                            firmarLaboresYTareas.actualizarFechasOT(tareaXTrabajoOrdenTrabajo.getCodigoCorreria(),
                                    tareaXTrabajoOrdenTrabajo.getCodigoOrdenTrabajo(), ordenTrabajo.getCodigoUsuarioLabor());
                            firmarLaboresYTareas.actualizarFechasTareaXOT((TareaXOrdenTrabajo) tareaXTrabajoOrdenTrabajo);
                            firmarLaboresYTareas.actualizarFechasCorreria(tareaXTrabajoOrdenTrabajo.getCodigoCorreria());
                            notificacionOrdenTrabajo.setResultado(NotificacionOrdenTrabajo.TAREA_CANCELADA);
                        } else {
                            notificacionOrdenTrabajo.setResultado(NotificacionOrdenTrabajo.ERROR_TAREA_NO_CANCELAR);
                        }
                    }
                } else {
                    notificacionOrdenTrabajo.setResultado(NotificacionOrdenTrabajo.ERROR_TAREA_NO_EXISTE);
                }
            } else {
                if (ordenTrabajo.esValidaInformacionObligatoriaGuardar()) {
                    List<TareaXTrabajoOrdenTrabajo> listaTareaXOrdenTrabajo = tareaXOrdenTrabajoRepositorio.cargarListaTareaXTrabajoOrdenTrabajo(notificacionOrdenTrabajo.getCodigoCorreria(),
                            notificacionOrdenTrabajo.getCodigoOrdenTrabajo());
                    if (listaTareaXOrdenTrabajo.size() > 0) {
                        for (TareaXTrabajoOrdenTrabajo tareaXTrabajoOrdenTrabajo : listaTareaXOrdenTrabajo) {
                            if (tareaXTrabajoOrdenTrabajo.getEstadoTarea().equals(Tarea.EstadoTarea.PENDIENTE)) {
                                tareaXTrabajoOrdenTrabajo.setEstadoTarea(Tarea.EstadoTarea.CANCELADA);
                                tareaXTrabajoOrdenTrabajo.setCodigoUsuarioTarea(ordenTrabajo.getCodigoUsuarioLabor());
                                tareaXOrdenTrabajoRepositorio.actualizar(tareaXTrabajoOrdenTrabajo);
                                firmarLaboresYTareas.actualizarFechasOT(tareaXTrabajoOrdenTrabajo.getCodigoCorreria(),
                                        tareaXTrabajoOrdenTrabajo.getCodigoOrdenTrabajo(), ordenTrabajo.getCodigoUsuarioLabor());
                                firmarLaboresYTareas.actualizarFechasTareaXOT((TareaXOrdenTrabajo) tareaXTrabajoOrdenTrabajo);
                                firmarLaboresYTareas.actualizarFechasCorreria(tareaXTrabajoOrdenTrabajo.getCodigoCorreria());
                            }
                        }
                    }
                    ordenTrabajo.setEstado(OrdenTrabajo.EstadoOrdenTrabajo.CANCELADA);
                    ordenTrabajoRepositorio.actualizar(ordenTrabajo);
                    firmarLaboresYTareas.actualizarFechasCorreria(ordenTrabajo.getCodigoCorreria());
                    firmarLaboresYTareas.actualizarFechasOT(ordenTrabajo.getCodigoCorreria(),
                            ordenTrabajo.getCodigoOrdenTrabajo(), ordenTrabajo.getCodigoUsuarioLabor());
                    notificacionOrdenTrabajo.setResultado(NotificacionOrdenTrabajo.ORDEN_CANCELADA);

                } else {
                    notificacionOrdenTrabajo.setResultado(NotificacionOrdenTrabajo.ERROR_ORDEN_NO_EXISTE);
                }

            }
        } else {
            notificacionOrdenTrabajo.setResultado(NotificacionOrdenTrabajo.ORDEN_NO_CANCELAR);
        }
    }

}
