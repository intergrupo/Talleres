package com.example.dominio.trabajo;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.ListaTrabajo;
import com.example.dominio.modelonegocio.Trabajo;
import com.example.dominio.trabajo.TrabajoRepositorio;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class TrabajoBL implements LogicaNegocioBase<Trabajo> {

    private TrabajoRepositorio trabajoRepositorio;

    public TrabajoBL(TrabajoRepositorio trabajoRepositorio) {
        this.trabajoRepositorio = trabajoRepositorio;
    }

    public boolean guardarTrabajos(List<Trabajo> listaTrabajos) {
        try {
            return trabajoRepositorio.guardar(listaTrabajos);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Trabajo cargarTrabajo(String codigoTrabajo) {
        return trabajoRepositorio.cargarTrabajo(codigoTrabajo);
    }

    public ListaTrabajo cargarTrabajos(List<String> listaCodigoTrabajos) {
        return trabajoRepositorio.cargarTrabajos(listaCodigoTrabajos);
    }

    public ListaTrabajo cargarTrabajos() {
        return trabajoRepositorio.cargarTrabajos();
    }

    public boolean tieneRegistrosTrabajo() {
        return trabajoRepositorio.tieneRegistros();
    }

    public boolean guardar(Trabajo trabajo){
        try {
            return trabajoRepositorio.guardar(trabajo);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Trabajo trabajo){
        return trabajoRepositorio.actualizar(trabajo);
    }

    private boolean eliminar(Trabajo trabajo){
        return trabajoRepositorio.eliminar(trabajo);
    }

    @Override
    public boolean procesar(List<Trabajo> listaDatos, String operacion) {
        boolean respuesta = false;
        for (Trabajo trabajo : listaDatos){
            switch (operacion){
                case "A":
                    if(cargarTrabajo(trabajo.getCodigoTrabajo()).getCodigoTrabajo().isEmpty()) {
                        respuesta = guardar(trabajo);
                    }
                    break;
                case "U":
                    respuesta = actualizar(trabajo);
                    break;
                case "D":
                    respuesta = eliminar(trabajo);
                    break;
                case "R":
                    if(!cargarTrabajo(trabajo.getCodigoTrabajo()).getCodigoTrabajo().isEmpty()){
                        respuesta = actualizar(trabajo);
                    }else {
                        respuesta = guardar(trabajo);
                    }
                    break;
                default:
                    if(cargarTrabajo(trabajo.getCodigoTrabajo()).getCodigoTrabajo().isEmpty()) {
                        respuesta = guardar(trabajo);
                    }
                    break;
            }
        }
        return respuesta;
    }
}
