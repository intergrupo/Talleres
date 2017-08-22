package com.example.dominio.labor;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.LaborXTarea;

import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class LaborXTareaBL implements LogicaNegocioBase<LaborXTarea> {

    LaborXTareaRepositorio laborXTareaRepositorio;

    @Inject
    public LaborXTareaBL(LaborXTareaRepositorio laborXTareaRepositorio) {
        this.laborXTareaRepositorio = laborXTareaRepositorio;
    }

    public boolean guardar(List<LaborXTarea> listaLaborXTarea) {
        try {
            return laborXTareaRepositorio.guardar(listaLaborXTarea);
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean tieneRegistrosLaborXTarea() {
        return laborXTareaRepositorio.tieneRegistros();
    }

    public LaborXTarea cargarLaborxTarea(String codigoTarea, String codigoLabor) {
        return laborXTareaRepositorio.cargarLaborxTarea(codigoTarea, codigoLabor);
    }

    public List<LaborXTarea> cargarListaLaborxTareaXCodigoTarea(String codigoTarea) {
        return laborXTareaRepositorio.cargarListaLaborxTareaXCodigoTarea(codigoTarea);
    }

    public List<LaborXTarea> cargarListaLaborxTareaXCodigoTareaMenu(String codigoTarea, String codigoLabor) {
        return laborXTareaRepositorio.cargarListaLaborxTareaXCodigoTareaMenu(codigoTarea, codigoLabor);
    }

    public List<LaborXTarea> cargarListaLaborxTareaXCodigoTareaMenu(String codigoTarea) {
        return laborXTareaRepositorio.cargarListaLaborxTareaXCodigoTareaMenu(codigoTarea);
    }

    public List<LaborXTarea> cargarListaLaborxTareaObligatorias(String codigoTarea) {
        return laborXTareaRepositorio.cargarListaLaborxTareaObligatorias(codigoTarea);
    }

    public boolean guardar(LaborXTarea laborXTarea) {
        try {
            return laborXTareaRepositorio.guardar(laborXTarea);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(LaborXTarea laborXTarea) {
        return laborXTareaRepositorio.actualizar(laborXTarea);
    }

    public boolean eliminar(LaborXTarea laborXTarea) {
        return laborXTareaRepositorio.eliminar(laborXTarea);
    }

    @Override
    public boolean procesar(List<LaborXTarea> listaDatos, String operacion) {
        boolean respuesta = false;
        for (LaborXTarea laborXTarea : listaDatos) {
            switch (operacion) {
                case "A":
                    if (!cargarLaborxTarea(laborXTarea.getTarea().getCodigoTarea(),
                            laborXTarea.getLabor().getCodigoLabor()).esClaveLlena()) {
                        respuesta = guardar(laborXTarea);
                    }
                    break;
                case "U":
                    respuesta = actualizar(laborXTarea);
                    break;
                case "D":
                    respuesta = eliminar(laborXTarea);
                    break;
                case "R":
                    if (cargarLaborxTarea(laborXTarea.getTarea().getCodigoTarea(),
                            laborXTarea.getLabor().getCodigoLabor()).esClaveLlena()) {
                        respuesta = actualizar(laborXTarea);
                    } else {
                        respuesta = guardar(laborXTarea);
                    }
                    break;
                default:
                    if (!cargarLaborxTarea(laborXTarea.getTarea().getCodigoTarea(),
                            laborXTarea.getLabor().getCodigoLabor()).esClaveLlena()) {
                        respuesta = guardar(laborXTarea);
                    }
                    break;
            }
        }
        return respuesta;
    }
}

