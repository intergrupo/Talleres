package com.example.dominio.bussinesslogic.administracion;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.Opcion;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class OpcionBL implements LogicaNegocioBase<Opcion> {

    private OpcionRepositorio opcionRepositorio;

    public OpcionBL(OpcionRepositorio opcionRepositorio) {
        this.opcionRepositorio = opcionRepositorio;
    }

    public Opcion cargarOpcionPorCodigo(String codigoOpcion) {
        return this.opcionRepositorio.cargarOpcionPorCodigo(codigoOpcion);
    }


    public boolean guardar(List<Opcion> listaOpciones) {
        try {
            return opcionRepositorio.guardar(listaOpciones);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean guardar(Opcion opcion){
        try {
            return opcionRepositorio.guardar(opcion);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Opcion opcion){
        return opcionRepositorio.actualizar(opcion);
    }

    public boolean eliminar(Opcion opcion){
        return opcionRepositorio.eliminar(opcion);
    }

    public boolean tieneRegistros() {
        return opcionRepositorio.tieneRegistros();
    }

    @Override
    public boolean procesar(List<Opcion> listaDatos, String operacion) {
        boolean respuesta = false;
        for (Opcion opcion : listaDatos){
            switch (operacion){
                case "A":
                    respuesta = guardar(opcion);
                    break;
                case "U":
                    respuesta = actualizar(opcion);
                    break;
                case "D":
                    respuesta = eliminar(opcion);
                    break;
                case "R":
                    if(!cargarOpcionPorCodigo(opcion.getCodigoOpcion()).getCodigoOpcion().isEmpty()){
                        respuesta =actualizar(opcion);
                    }else {
                        respuesta =guardar(opcion);
                    }
                    break;
                default:
                    respuesta = guardar(opcion);
                    break;
            }
        }
        return respuesta;
    }
}