package com.example.dominio.bussinesslogic.trabajo;

import com.example.dominio.IBaseDescarga;
import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;
import com.example.dominio.modelonegocio.Trabajo;
import com.example.dominio.modelonegocio.TrabajoXOrdenTrabajo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class TrabajoXOrdenTrabajoBL implements LogicaNegocioBase<TrabajoXOrdenTrabajo>,
        IBaseDescarga<TrabajoXOrdenTrabajo> {

    TrabajoXOrdenTrabajoRepositorio trabajoXOrdenTrabajoRepositorio;

    public TrabajoXOrdenTrabajoBL(TrabajoXOrdenTrabajoRepositorio trabajoXOrdenTrabajoRepositorio) {
        this.trabajoXOrdenTrabajoRepositorio = trabajoXOrdenTrabajoRepositorio;
    }

    public boolean guardar(List<TrabajoXOrdenTrabajo> listaTrabajosXOrdenTrabajo) {
        try {
            return trabajoXOrdenTrabajoRepositorio.guardar(listaTrabajosXOrdenTrabajo);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(TrabajoXOrdenTrabajo trabajoXOrdenTrabajo) {
        return trabajoXOrdenTrabajoRepositorio.actualizar(trabajoXOrdenTrabajo);
    }

    public boolean eliminar(TrabajoXOrdenTrabajo trabajoXOrdenTrabajo) {
        return trabajoXOrdenTrabajoRepositorio.eliminar(trabajoXOrdenTrabajo);
    }

    public boolean tieneRegistros() {
        return trabajoXOrdenTrabajoRepositorio.tieneRegistros();
    }

    public TrabajoXOrdenTrabajo cargarTrabajosXOrdenTrabajo(String codigoCorreria,
                                                            String codigoOrdenTrabajo,
                                                            String codigoTrabajo) {
        return trabajoXOrdenTrabajoRepositorio.cargarTrabajosXOrdenTrabajo(codigoCorreria,
                codigoOrdenTrabajo, codigoTrabajo);
    }

    public List<String> cargarCodigoOrdenTrabajoXTrabajo(Trabajo trabajo) {
        return trabajoXOrdenTrabajoRepositorio.cargarCodigoOrdenTrabajoXTrabajo(trabajo);
    }

    public List<TrabajoXOrdenTrabajo> cargarLista(String codigoCorreria, String codigoOrdenTrabajo) {
        return trabajoXOrdenTrabajoRepositorio.cargarLista(codigoCorreria, codigoOrdenTrabajo);
    }

    public boolean guardar(TrabajoXOrdenTrabajo trabajoXOrdenTrabajo) {
        return trabajoXOrdenTrabajoRepositorio.guardar(trabajoXOrdenTrabajo);
    }

    @Override
    public boolean procesar(List<TrabajoXOrdenTrabajo> listaDatos, String operacion) {
        boolean respuesta = false;
        for (TrabajoXOrdenTrabajo trabajoXOrdenTrabajo : listaDatos) {
            switch (operacion) {
                case "A":
                    if (cargarTrabajosXOrdenTrabajo(trabajoXOrdenTrabajo).esClaveVacia()) {
                        respuesta = guardar(trabajoXOrdenTrabajo);
                    }
                    break;
                case "U":
                    respuesta = actualizar(trabajoXOrdenTrabajo);
                    break;
                case "D":
                    respuesta = eliminar(trabajoXOrdenTrabajo);
                    break;
                case "R":
                    if (!cargarTrabajosXOrdenTrabajo(trabajoXOrdenTrabajo).esClaveVacia()) {
                        respuesta = actualizar(trabajoXOrdenTrabajo);
                    } else {
                        respuesta = guardar(trabajoXOrdenTrabajo);
                    }
                    break;
                default:
                    if (cargarTrabajosXOrdenTrabajo(trabajoXOrdenTrabajo).esClaveVacia()) {
                        respuesta = guardar(trabajoXOrdenTrabajo);
                    }
                    break;
            }
        }
        return respuesta;
    }

    public TrabajoXOrdenTrabajo cargarTrabajosXOrdenTrabajo(TrabajoXOrdenTrabajo trabajoXOrdenTrabajo) {
        return cargarTrabajosXOrdenTrabajo(trabajoXOrdenTrabajo.getCodigoCorreria(),
                trabajoXOrdenTrabajo.getCodigoOrdenTrabajo(), trabajoXOrdenTrabajo.getTrabajo().getCodigoTrabajo());
    }

    @Override
    public List<TrabajoXOrdenTrabajo> cargarXCorreria(String codigoCorreria) {
        return trabajoXOrdenTrabajoRepositorio.cargarXCorreria(codigoCorreria);
    }

    @Override
    public List<TrabajoXOrdenTrabajo> cargarXFiltro(OrdenTrabajoBusqueda filtro) {
        List<TrabajoXOrdenTrabajo> lista = new ArrayList<>();
        for (OrdenTrabajo ordenTrabajo : filtro.getListaOrdenTrabajo()) {
            if (filtro.getTareaXTrabajo().getTrabajo().getCodigoTrabajo().isEmpty()) {
                lista.addAll(trabajoXOrdenTrabajoRepositorio.cargarTrabajosXOrdenTrabajo(
                        ordenTrabajo.getCodigoCorreria(), ordenTrabajo.getCodigoOrdenTrabajo()));
            } else {
                lista.add(trabajoXOrdenTrabajoRepositorio.cargarTrabajosXOrdenTrabajo(
                        ordenTrabajo.getCodigoCorreria(), ordenTrabajo.getCodigoOrdenTrabajo(),
                        filtro.getTareaXTrabajo().getTrabajo().getCodigoTrabajo()));
            }
        }
        return lista;
    }
}
