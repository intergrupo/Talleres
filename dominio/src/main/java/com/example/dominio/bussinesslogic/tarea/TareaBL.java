package com.example.dominio.bussinesslogic.tarea;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.Estado;
import com.example.dominio.modelonegocio.ListaEstadoTarea;
import com.example.dominio.modelonegocio.ListaTareas;
import com.example.dominio.modelonegocio.Tarea;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class TareaBL implements LogicaNegocioBase<Tarea> {

    private TareaRepositorio tareaRepositorio;

    public TareaBL(TareaRepositorio tareaRepositorio) {
        this.tareaRepositorio = tareaRepositorio;
    }

    public ListaTareas cargarTareas() {
        try {
            return new ListaTareas(tareaRepositorio.cargarTareas());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean guardarTareas(List<Tarea> listaTareas) {
        try {
            return tareaRepositorio.guardar(listaTareas);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ListaEstadoTarea cargarEstadosTarea(List<Estado> listaEstados) {
        ListaEstadoTarea listaEstadoTareas = new ListaEstadoTarea();
        for (Estado estado : listaEstados) {
            listaEstadoTareas.add(Tarea.EstadoTarea.getEstado(estado.getCodigoEstado()));
        }
        return listaEstadoTareas;
    }

    public boolean tieneRegistrosTarea() {
        return tareaRepositorio.tieneRegistros();
    }

    public Tarea cargarTareaxCodigo(String codigoTarea) {
        return tareaRepositorio.cargarTareaxCodigo(codigoTarea);
    }

    public boolean guardar(Tarea tarea) {
        try {
            return tareaRepositorio.guardar(tarea);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Tarea tarea) {
        return tareaRepositorio.actualizar(tarea);
    }

    public boolean eliminar(Tarea tarea) {
        return tareaRepositorio.eliminar(tarea);
    }

    @Override
    public boolean procesar(List<Tarea> listaDatos, String operacion) {
        boolean respuesta = false;
        for (Tarea tarea : listaDatos) {
            switch (operacion) {
                case "A":
                    if (cargarTareaxCodigo(tarea.getCodigoTarea()).getCodigoTarea().isEmpty()) {
                        respuesta = guardar(tarea);
                    }
                    break;
                case "U":
                    respuesta = actualizar(tarea);
                    break;
                case "D":
                    respuesta = eliminar(tarea);
                    break;
                case "R":
                    if (!cargarTareaxCodigo(tarea.getCodigoTarea()).getCodigoTarea().isEmpty()) {
                        respuesta = actualizar(tarea);
                    } else {
                        respuesta = guardar(tarea);
                    }
                    break;
                default:
                    if (cargarTareaxCodigo(tarea.getCodigoTarea()).getCodigoTarea().isEmpty()) {
                        respuesta = guardar(tarea);
                    }
                    break;
            }
        }
        return respuesta;
    }
}
