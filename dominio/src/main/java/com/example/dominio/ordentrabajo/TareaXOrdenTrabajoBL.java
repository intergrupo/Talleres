package com.example.dominio.ordentrabajo;

import com.example.dominio.IBaseDescarga;
import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.ListaOrdenTrabajo;
import com.example.dominio.modelonegocio.ListaTareaXOrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;
import com.example.dominio.modelonegocio.Tarea;
import com.example.dominio.modelonegocio.TareaXOrdenTrabajo;
import com.example.dominio.modelonegocio.TareaXTrabajoOrdenTrabajo;
import com.example.dominio.modelonegocio.TotalTarea;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class TareaXOrdenTrabajoBL implements LogicaNegocioBase<TareaXTrabajoOrdenTrabajo>,
        IBaseDescarga<TareaXOrdenTrabajo> {

    private TareaXOrdenTrabajoRepositorio tareaXOrdenTrabajoRepositorio;

    @Inject
    public TareaXOrdenTrabajoBL(TareaXOrdenTrabajoRepositorio tareaXOrdenTrabajoRepositorio) {
        this.tareaXOrdenTrabajoRepositorio = tareaXOrdenTrabajoRepositorio;
    }

    public List<String> cargarCodigoOrdenTrabajoXEstadoTarea(Tarea.EstadoTarea estadoTarea) {
        return this.tareaXOrdenTrabajoRepositorio.cargarCodigoOrdenTrabajoXEstadoTarea(estadoTarea);
    }

    public List<String> cargarCodigoOrdenTrabajoXTarea(Tarea tarea) {
        return this.tareaXOrdenTrabajoRepositorio.cargarCodigoOrdenTrabajoXTarea(tarea);
    }

    public boolean guardarTareaXTrabajoOrdenTrabajo(List<TareaXTrabajoOrdenTrabajo> listaTareaXOrdenTrabajo) {
        try {
            return tareaXOrdenTrabajoRepositorio.guardar(listaTareaXOrdenTrabajo);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean guardar(TareaXTrabajoOrdenTrabajo tareaXOrdenTrabajo) {
        try {
            return tareaXOrdenTrabajoRepositorio.guardar(tareaXOrdenTrabajo);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> cargarCodigosTrabajosAgrupados(String codigoCorreria){
        return tareaXOrdenTrabajoRepositorio.cargarCodigosTrabajosAgrupados(codigoCorreria);
    }

    public List<String> cargarCodigosOTXDescarga(){
        return tareaXOrdenTrabajoRepositorio.cargarCodigoOrdenTrabajoXDescarga();
    }

    public boolean tieneRegistrosTareaXOrdenTrabajo() {
        return tareaXOrdenTrabajoRepositorio.tieneRegistros();
    }

    public ListaTareaXOrdenTrabajo cargarTareasXOrdenTrabajo(String codigoCorreria) {
        return tareaXOrdenTrabajoRepositorio.cargarTareasXOrdenTrabajo(codigoCorreria);
    }

    public List<TareaXTrabajoOrdenTrabajo> cargarListaTareaXTrabajoOrdenTrabajo(String codigoCorreria,
                                                                                String codigoOrdenTrabajo) {
        return tareaXOrdenTrabajoRepositorio.cargarListaTareaXTrabajoOrdenTrabajo(codigoCorreria,
                codigoOrdenTrabajo);
    }

    public List<TareaXTrabajoOrdenTrabajo> cargarListaTareaXTrabajoOrdenTrabajoXCorreria(String codigoCorreria) {
        return tareaXOrdenTrabajoRepositorio.cargarListaTareaXTrabajoOrdenTrabajoXCorreria(codigoCorreria);
    }

    public List<TareaXTrabajoOrdenTrabajo> cargarListaTareaXTrabajoOrdenTrabajo(String codigoCorreria,
                                                                                String codigoOrdenTrabajo,
                                                                                String codigoTrabajo) {
        return tareaXOrdenTrabajoRepositorio.cargarListaTareaXTrabajoOrdenTrabajo(codigoCorreria,
                codigoOrdenTrabajo,
                codigoTrabajo);
    }

    public TareaXTrabajoOrdenTrabajo cargarTareaXTrabajoOrdenTrabajo(String codigoCorreria,
                                                                     String codigoOrdenTrabajo,
                                                                     String codigoTarea,
                                                                     String codigoTrabajo) {
        return tareaXOrdenTrabajoRepositorio.cargarTareaXTrabajoOrdenTrabajo(codigoCorreria, codigoOrdenTrabajo, codigoTarea, codigoTrabajo);
    }

    public List<TotalTarea> cargarTotalesPorTarea(String codigoCorreria, String codigoTrabajo) {
        return tareaXOrdenTrabajoRepositorio.cargarTotalesPorTarea(codigoCorreria, codigoTrabajo);
    }

    public boolean actualizarTareaXOrdenTrabajo(TareaXTrabajoOrdenTrabajo tareaXTrabajoOrdenTrabajo) {
        return tareaXOrdenTrabajoRepositorio.actualizar(tareaXTrabajoOrdenTrabajo);
    }

    public boolean eliminar(TareaXTrabajoOrdenTrabajo tareaXTrabajoOrdenTrabajo){
        return tareaXOrdenTrabajoRepositorio.eliminar(tareaXTrabajoOrdenTrabajo);
    }

    public boolean actualizarFechaDescarga(String codigoCorreria, String fechaDescarga, ListaOrdenTrabajo listaOrdenTrabajo) {
        return tareaXOrdenTrabajoRepositorio.actualizarFechaDescarga(codigoCorreria,fechaDescarga, listaOrdenTrabajo);
    }

    @Override
    public boolean procesar(List<TareaXTrabajoOrdenTrabajo> listaDatos, String operacion) {
        boolean respuesta = false;
        for (TareaXTrabajoOrdenTrabajo tareaXTrabajoOrdenTrabajo: listaDatos){
            switch (operacion){
                case "A":
                    if(!cargarTareaXTrabajoOrdenTrabajo(tareaXTrabajoOrdenTrabajo).esClaveLlena()) {
                        respuesta = guardar(tareaXTrabajoOrdenTrabajo);
                    }
                    break;
                case "U":
                    respuesta = actualizarTareaXOrdenTrabajo(tareaXTrabajoOrdenTrabajo);
                    break;
                case "D":
                    respuesta = eliminar(tareaXTrabajoOrdenTrabajo);
                    break;
                case "R":
                    if(cargarTareaXTrabajoOrdenTrabajo(tareaXTrabajoOrdenTrabajo).esClaveLlena()){
                        respuesta = actualizarTareaXOrdenTrabajo(tareaXTrabajoOrdenTrabajo);
                    }else {
                        respuesta = guardar(tareaXTrabajoOrdenTrabajo);
                    }
                    break;
                default:
                    if(!cargarTareaXTrabajoOrdenTrabajo(tareaXTrabajoOrdenTrabajo).esClaveLlena()) {
                        respuesta = guardar(tareaXTrabajoOrdenTrabajo);
                    }
                    break;
            }
        }
        return respuesta;
    }

    public TareaXTrabajoOrdenTrabajo cargarTareaXTrabajoOrdenTrabajo(TareaXTrabajoOrdenTrabajo tareaXTrabajoOrdenTrabajo){
        return cargarTareaXTrabajoOrdenTrabajo(tareaXTrabajoOrdenTrabajo.getCodigoCorreria(),
                tareaXTrabajoOrdenTrabajo.getCodigoOrdenTrabajo(),
                tareaXTrabajoOrdenTrabajo.getTarea().getCodigoTarea(),
                tareaXTrabajoOrdenTrabajo.getCodigoTrabajo());
    }

    @Override
    public List<TareaXOrdenTrabajo> cargarXCorreria(String codigoCorreria) {
        return new ArrayList<>(cargarTareasXOrdenTrabajo(codigoCorreria));
    }

    @Override
    public List<TareaXOrdenTrabajo> cargarXFiltro(OrdenTrabajoBusqueda filtro) {
        List<TareaXOrdenTrabajo> lista = new ArrayList<>();
        if(filtro.getListaTareaXOrdenTrabajo()!= null && filtro.getListaTareaXOrdenTrabajo().size() > 0){
            for (TareaXOrdenTrabajo tareaXot : filtro.getListaTareaXOrdenTrabajo()) {
                lista.add(tareaXOrdenTrabajoRepositorio.cargar(tareaXot.getCodigoCorreria(),
                        tareaXot.getCodigoOrdenTrabajo(), tareaXot.getCodigoTrabajo(),
                        tareaXot.getTarea().getCodigoTarea(), filtro.getEstadoTarea().getCodigo()));
            }
        }
        return lista;
    }

    public ListaTareaXOrdenTrabajo cargar(OrdenTrabajoBusqueda filtro) {
        ListaTareaXOrdenTrabajo lista = new ListaTareaXOrdenTrabajo();
        if(filtro.isTodos()){
            lista.addAll(tareaXOrdenTrabajoRepositorio.cargarTareasXOrdenTrabajo(filtro.getCodigoCorreria()));
        }
        else{
            if(filtro.getListaOrdenTrabajo()!= null && filtro.getListaOrdenTrabajo().size() > 0){
                for (OrdenTrabajo ot : filtro.getListaOrdenTrabajo()) {
                    lista.addAll(tareaXOrdenTrabajoRepositorio.cargar(ot.getCodigoCorreria(),
                            ot.getCodigoOrdenTrabajo(),
                            filtro.getTareaXTrabajo().getTrabajo().getCodigoTrabajo(),
                            filtro.getTareaXTrabajo().getTarea().getCodigoTarea(),
                            filtro.getEstadoTarea().getCodigo(), filtro.getSerieElemento(), filtro.isNoDescargados()));
                }
            }
        }
        return lista;
    }

    public int asignarUltimaSecuencia(String codigoCorreria) {
        return tareaXOrdenTrabajoRepositorio.asignarUltimaSecuencia(codigoCorreria);
    }
}
