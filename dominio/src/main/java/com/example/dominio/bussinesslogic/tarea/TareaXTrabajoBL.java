package com.example.dominio.bussinesslogic.tarea;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.ListaTareaXTrabajo;
import com.example.dominio.modelonegocio.TareaXTrabajo;

import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class TareaXTrabajoBL implements LogicaNegocioBase<TareaXTrabajo> {

    private TareaXTrabajoRepositorio tareaXTrabajoRepositorio;
    private TareaBL tareaBL;

    @Inject
    public TareaXTrabajoBL(TareaXTrabajoRepositorio tareaXTrabajoRepositorio, TareaBL tareaBL) {
        this.tareaXTrabajoRepositorio = tareaXTrabajoRepositorio;
        this.tareaBL = tareaBL;
    }


    public ListaTareaXTrabajo cargarTareasXTrabajo(String codigoTrabajo) {
        ListaTareaXTrabajo listaTareasXTrabajo = this.tareaXTrabajoRepositorio.cargarTareasXTrabajo(codigoTrabajo);
        for (TareaXTrabajo tareaXTrabajo : listaTareasXTrabajo) {
            tareaXTrabajo.setTarea(tareaBL.cargarTareaxCodigo(tareaXTrabajo.getTarea().getCodigoTarea()));
        }
        return listaTareasXTrabajo;
    }

    public List<TareaXTrabajo> cargarTareasXTrabajoConCodigosDeTareas(String codigoTrabajo, List<String> listaCodigosTareas) {
        List<TareaXTrabajo> listaTareasXTrabajo = this.tareaXTrabajoRepositorio.cargarTareasXTrabajoConCodigosDeTareas(codigoTrabajo, listaCodigosTareas);
        for (TareaXTrabajo tareaXTrabajo : listaTareasXTrabajo) {
            tareaXTrabajo.setTarea(tareaBL.cargarTareaxCodigo(tareaXTrabajo.getTarea().getCodigoTarea()));
        }
        return listaTareasXTrabajo;
    }

    public boolean guardarTareaXTrabajo(List<TareaXTrabajo> listaTareaXTrabajo) {
        try {
            return this.tareaXTrabajoRepositorio.guardar(listaTareaXTrabajo);
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean guardar(TareaXTrabajo tareaXTrabajo) {
        try {
            return tareaXTrabajoRepositorio.guardar(tareaXTrabajo);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(TareaXTrabajo tareaXTrabajo) {
        return tareaXTrabajoRepositorio.actualizar(tareaXTrabajo);
    }

    public boolean eliminar(TareaXTrabajo tareaXTrabajo) {
        return tareaXTrabajoRepositorio.eliminar(tareaXTrabajo);
    }

    public boolean tieneRegistrosTareaXTabajo() {
        return this.tareaXTrabajoRepositorio.tieneRegistros();
    }

    public TareaXTrabajo cargarTareaXTrabajo(String codigoTrabajo, String codigoTarea) {
        return tareaXTrabajoRepositorio.cargarTareaXTrabajo(codigoTrabajo, codigoTarea);
    }

    @Override
    public boolean procesar(List<TareaXTrabajo> listaDatos, String operacion) {
        boolean respuesta = false;
        for (TareaXTrabajo tareaXTrabajo : listaDatos) {
            switch (operacion) {
                case "A":
                    if (!cargarTareaXTrabajo(tareaXTrabajo.getTrabajo().getCodigoTrabajo(),
                            tareaXTrabajo.getTarea().getCodigoTarea()).esClaveLlena()) {
                        respuesta = guardar(tareaXTrabajo);
                    }
                    break;
                case "U":
                    respuesta = actualizar(tareaXTrabajo);
                    break;
                case "D":
                    respuesta = eliminar(tareaXTrabajo);
                    break;
                case "R":
                    if (cargarTareaXTrabajo(tareaXTrabajo.getTrabajo().getCodigoTrabajo(),
                            tareaXTrabajo.getTarea().getCodigoTarea()).esClaveLlena()) {
                        respuesta = actualizar(tareaXTrabajo);
                    } else {
                        respuesta = guardar(tareaXTrabajo);
                    }
                    break;
                default:
                    if (!cargarTareaXTrabajo(tareaXTrabajo.getTrabajo().getCodigoTrabajo(),
                            tareaXTrabajo.getTarea().getCodigoTarea()).esClaveLlena()) {
                        respuesta = guardar(tareaXTrabajo);
                    }
                    break;
            }
        }
        return respuesta;
    }

    public ListaTareaXTrabajo cargarTareasXTrabajoXCodigoTarea(String codigoTarea) {
        return tareaXTrabajoRepositorio.cargarTareaXTrabajoXCodigoTarea(codigoTarea);
    }
}