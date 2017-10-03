package com.example.dominio.bussinesslogic.ordentrabajo;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.Estado;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class EstadoBL implements LogicaNegocioBase<Estado> {

    private EstadoRepositorio estadoRepositorio;

    public EstadoBL(EstadoRepositorio estadoRepositorio) {
        this.estadoRepositorio = estadoRepositorio;
    }

    public boolean guardar(List<Estado> listaEstados) {
        try {
            return estadoRepositorio.guardar(listaEstados);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean guardar(Estado estado){
        try {
            return estadoRepositorio.guardar(estado);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Estado estado){
        return estadoRepositorio.actualizar(estado);
    }

    public boolean eliminar(Estado estado){
        return estadoRepositorio.eliminar(estado);
    }

    public Estado cargarEstado(String codigoEstado) {
        return estadoRepositorio.cargarEstado(codigoEstado);
    }

    public boolean tieneRegistros() {
        return estadoRepositorio.tieneRegistros();
    }

    public List<Estado> cargarEstadoXGrupo(String codigoGrupo) {
        return estadoRepositorio.cargarEstadoXGrupo(codigoGrupo);
    }

    public List<Estado> cargarEstadoTareas() {
        return estadoRepositorio.cargarEstadoTareas();
    }

    public List<Estado> cargarEstadoTareasLectura() {
        return estadoRepositorio.cargarEstadoTareasLectura();
    }

    public List<Estado> cargarEstadoTareasRevisiones() {
        return estadoRepositorio.cargarEstadoTareasRevisiones();
    }
    @Override
    public boolean procesar(List<Estado> listaDatos, String operacion) {
        boolean respuesta = false;
        for (Estado estado : listaDatos){
            switch (operacion){
                case "A":
                    if(cargarEstado(estado.getCodigoEstado()).getCodigoEstado().isEmpty()) {
                        respuesta = guardar(estado);
                    }
                    break;
                case "U":
                    respuesta = actualizar(estado);
                    break;
                case "D":
                    respuesta = eliminar(estado);
                    break;
                case "R":
                    if(!cargarEstado(estado.getCodigoEstado()).getCodigoEstado().isEmpty()){
                        respuesta = actualizar(estado);
                    }else {
                        respuesta = guardar(estado);
                    }
                    break;
                default:
                    if(cargarEstado(estado.getCodigoEstado()).getCodigoEstado().isEmpty()) {
                        respuesta = guardar(estado);
                    }
                    break;
            }
        }
        return respuesta;
    }
}