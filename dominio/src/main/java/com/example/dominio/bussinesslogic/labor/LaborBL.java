package com.example.dominio.bussinesslogic.labor;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.Labor;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class LaborBL implements LogicaNegocioBase<Labor> {

    LaborRepositorio laborRepositorio;

    public LaborBL(LaborRepositorio laborRepositorio, LaborXTareaRepositorio laborXTareaRepositorio) {
        this.laborRepositorio = laborRepositorio;
    }

    public boolean guardarLabor(List<Labor> listaLabores) {
        try {
            return laborRepositorio.guardar(listaLabores);
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean tieneRegistrosLabor() {
        return laborRepositorio.tieneRegistros();
    }

    public Labor cargarLaborxCodigo(String codigoLabor) {
        return laborRepositorio.cargarLaborxCodigo(codigoLabor);
    }

    public boolean guardar(Labor labor) {
        try {
            return laborRepositorio.guardar(labor);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Labor labor) {
        return laborRepositorio.actualizar(labor);
    }

    public boolean eliminar(Labor labor) {
        return laborRepositorio.eliminar(labor);
    }

    @Override
    public boolean procesar(List<Labor> listaDatos, String operacion) {
        boolean respuesta = false;
        for (Labor labor : listaDatos) {
            switch (operacion) {
                case "A":
                    if (cargarLaborxCodigo(labor.getCodigoLabor()).getCodigoLabor().isEmpty()) {
                        respuesta = guardar(labor);
                    }
                    break;
                case "U":
                    respuesta = actualizar(labor);
                    break;
                case "D":
                    respuesta = eliminar(labor);
                    break;
                case "R":
                    if (!cargarLaborxCodigo(labor.getCodigoLabor()).getCodigoLabor().isEmpty()) {
                        respuesta = actualizar(labor);
                    } else {
                        respuesta = guardar(labor);
                    }
                    break;
                default:
                    if (cargarLaborxCodigo(labor.getCodigoLabor()).getCodigoLabor().isEmpty()) {
                        respuesta = guardar(labor);
                    }
                    break;
            }
        }
        return respuesta;
    }
}