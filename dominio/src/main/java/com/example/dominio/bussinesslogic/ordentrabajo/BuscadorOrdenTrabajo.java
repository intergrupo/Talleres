package com.example.dominio.bussinesslogic.ordentrabajo;

import android.text.TextUtils;

import com.example.dominio.bussinesslogic.tarea.TareaXOrdenTrabajoBL;
import com.example.dominio.bussinesslogic.trabajo.TrabajoXOrdenTrabajoBL;
import com.example.dominio.modelonegocio.ListaOrdenTrabajo;
import com.example.dominio.modelonegocio.ListaTareaXOrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;
import com.example.dominio.modelonegocio.Tarea;
import com.example.dominio.modelonegocio.TareaXOrdenTrabajo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class BuscadorOrdenTrabajo {

    private OrdenTrabajoBL ordenTrabajoBL;
    private TrabajoXOrdenTrabajoBL trabajoXOrdenTrabajoBL;
    private TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL;

    public BuscadorOrdenTrabajo(OrdenTrabajoBL ordenTrabajoBL,
                                TrabajoXOrdenTrabajoBL trabajoXOrdenTrabajoBL,
                                TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL) {
        this.ordenTrabajoBL = ordenTrabajoBL;
        this.trabajoXOrdenTrabajoBL = trabajoXOrdenTrabajoBL;
        this.tareaXOrdenTrabajoBL = tareaXOrdenTrabajoBL;
    }

    private List<String> obtenerListaCodigoOrdenesTrabajoParaFiltroPorOrdenTrabajo(OrdenTrabajoBusqueda ordenTrabajoBusqueda) {
        List<String> listaCodigoOrdenTrabajos = new ArrayList<>();
        boolean buscarXTrabajo = true;
        boolean realizoFiltro=false;

        if (!TextUtils.isEmpty(ordenTrabajoBusqueda.getTareaXTrabajo().getTarea().getCodigoTarea())) {
            if(!realizoFiltro) {
                listaCodigoOrdenTrabajos.addAll(tareaXOrdenTrabajoBL.cargarCodigoOrdenTrabajoXTarea(
                        ordenTrabajoBusqueda.getTareaXTrabajo().getTarea()));
            }else{
                listaCodigoOrdenTrabajos.retainAll(tareaXOrdenTrabajoBL.cargarCodigoOrdenTrabajoXTarea(
                        ordenTrabajoBusqueda.getTareaXTrabajo().getTarea()));
            }
            buscarXTrabajo = false;
            realizoFiltro=true;
        }

        if (ordenTrabajoBusqueda.getEstadoTarea() != Tarea.EstadoTarea.NINGUNA) {
            if(!realizoFiltro) {
                listaCodigoOrdenTrabajos.addAll(tareaXOrdenTrabajoBL.cargarCodigoOrdenTrabajoXEstadoTarea(
                        ordenTrabajoBusqueda.getEstadoTarea()));
            }else{
                listaCodigoOrdenTrabajos.retainAll(tareaXOrdenTrabajoBL.cargarCodigoOrdenTrabajoXEstadoTarea(
                        ordenTrabajoBusqueda.getEstadoTarea()));
            }
            buscarXTrabajo = false;
            realizoFiltro=true;
        }

        if (buscarXTrabajo && !TextUtils.isEmpty(ordenTrabajoBusqueda.getTareaXTrabajo().getTrabajo().getCodigoTrabajo())) {
            listaCodigoOrdenTrabajos.addAll(trabajoXOrdenTrabajoBL.cargarCodigoOrdenTrabajoXTrabajo(
                    ordenTrabajoBusqueda.getTareaXTrabajo().getTrabajo()));
            realizoFiltro=true;
        }

        if(ordenTrabajoBusqueda.isNoDescargados()){
            listaCodigoOrdenTrabajos.addAll(tareaXOrdenTrabajoBL.cargarCodigosOTXDescarga());
        }
        HashSet hashSet = new HashSet();
        hashSet.addAll(listaCodigoOrdenTrabajos);
        listaCodigoOrdenTrabajos.clear();
        listaCodigoOrdenTrabajos.addAll(hashSet);
        return listaCodigoOrdenTrabajos;
    }

    private List<String> obtenerListaCodigoOrdenesTrabajoParaFiltroPorTarea(OrdenTrabajoBusqueda ordenTrabajoBusqueda) {
        List<String> listaCodigoOrdenTrabajos = new ArrayList<>();
        if (!TextUtils.isEmpty(ordenTrabajoBusqueda.getTareaXTrabajo().getTrabajo().getCodigoTrabajo())) {
            listaCodigoOrdenTrabajos.addAll(trabajoXOrdenTrabajoBL.cargarCodigoOrdenTrabajoXTrabajo(ordenTrabajoBusqueda.getTareaXTrabajo().getTrabajo()));
        }

        if(ordenTrabajoBusqueda.isNoDescargados()){
            listaCodigoOrdenTrabajos.addAll(tareaXOrdenTrabajoBL.cargarCodigosOTXDescarga());
        }

        HashSet hashSet = new HashSet();
        hashSet.addAll(listaCodigoOrdenTrabajos);
        listaCodigoOrdenTrabajos.clear();
        listaCodigoOrdenTrabajos.addAll(hashSet);
        return listaCodigoOrdenTrabajos;
    }

    private boolean validarCondicionesVacias(OrdenTrabajoBusqueda ordenTrabajoBusqueda) {
        return TextUtils.isEmpty(ordenTrabajoBusqueda.getCodigoOrdenTrabajo()) &&
                TextUtils.isEmpty(ordenTrabajoBusqueda.getDireccion()) &&
                TextUtils.isEmpty(ordenTrabajoBusqueda.getCliente()) &&
                TextUtils.isEmpty(ordenTrabajoBusqueda.getCodigoLlave1()) &&
                TextUtils.isEmpty(ordenTrabajoBusqueda.getCodigoLlave2()) &&
                TextUtils.isEmpty(ordenTrabajoBusqueda.getTareaXTrabajo().getTrabajo().getCodigoTrabajo()) &&
                TextUtils.isEmpty(ordenTrabajoBusqueda.getTareaXTrabajo().getTarea().getCodigoTarea()) &&
                TextUtils.isEmpty(ordenTrabajoBusqueda.getSerieElemento()) &&
                (ordenTrabajoBusqueda.getEstadoTarea().equals(Tarea.EstadoTarea.NINGUNA)
                        && !ordenTrabajoBusqueda.isNoDescargados());
    }

    public ListaOrdenTrabajo filtrarPorOrdenTrabajo(ListaOrdenTrabajo listaOrdenTrabajo, OrdenTrabajoBusqueda ordenTrabajoBusqueda) {

        ListaOrdenTrabajo listaFiltradaOrdenTrabajo = new ListaOrdenTrabajo();

        if (validarCondicionesVacias(ordenTrabajoBusqueda)) {
            return listaFiltradaOrdenTrabajo;
        }

        List<String> listaCodigoOrdenTrabajos = obtenerListaCodigoOrdenesTrabajoParaFiltroPorOrdenTrabajo(
                ordenTrabajoBusqueda);

        listaFiltradaOrdenTrabajo = listaOrdenTrabajo.filtrar(ordenTrabajo -> validarFiltro(
                ordenTrabajo, listaCodigoOrdenTrabajos, ordenTrabajoBusqueda));
        return listaFiltradaOrdenTrabajo;
    }

    public ListaTareaXOrdenTrabajo filtrarPorTarea(ListaTareaXOrdenTrabajo listaTareaXOrdenTrabajo,
                                                   OrdenTrabajoBusqueda ordenTrabajoBusqueda) {


        ListaTareaXOrdenTrabajo listaFiltradaTareaXOrdenTrabajo = new ListaTareaXOrdenTrabajo();

        if (validarCondicionesVacias(ordenTrabajoBusqueda)) {
            return listaFiltradaTareaXOrdenTrabajo;
        }

        List<String> listaCodigoOrdenTrabajos = obtenerListaCodigoOrdenesTrabajoParaFiltroPorTarea(ordenTrabajoBusqueda);

        String codigoCorreria = "";

        if (!listaTareaXOrdenTrabajo.isEmpty()) {
            codigoCorreria = listaTareaXOrdenTrabajo.get(0).getCodigoCorreria();
        }

        for (TareaXOrdenTrabajo tareaXOrdenTrabajo : listaTareaXOrdenTrabajo) {
            if (tareaXOrdenTrabajo.getOrdenTrabajo().getNombre().isEmpty()) {
                tareaXOrdenTrabajo.setOrdenTrabajo(ordenTrabajoBL.cargarOrdenTrabajo(tareaXOrdenTrabajo.getCodigoCorreria(), tareaXOrdenTrabajo.getCodigoOrdenTrabajo()));
            }
        }

        listaCodigoOrdenTrabajos.clear();
        return listaFiltradaTareaXOrdenTrabajo;
    }

    private boolean validarFiltro(OrdenTrabajo ordenTrabajo, List<String> listaCodigoOrdenTrabajos,
                                  OrdenTrabajoBusqueda ordenTrabajoBusqueda) {
        boolean resultadoComparacion = true;
        if (!TextUtils.isEmpty(ordenTrabajoBusqueda.getCodigoOrdenTrabajo())) {
            resultadoComparacion = ordenTrabajo.getCodigoOrdenTrabajo().toUpperCase().contains(ordenTrabajoBusqueda.getCodigoOrdenTrabajo().toUpperCase());
        }

        if (!TextUtils.isEmpty(ordenTrabajoBusqueda.getDireccion())) {
            resultadoComparacion = ordenTrabajo.getDireccion().toUpperCase().contains(ordenTrabajoBusqueda.getDireccion().toUpperCase()) &&
                    resultadoComparacion;
        }

        if (!TextUtils.isEmpty(ordenTrabajoBusqueda.getCliente())) {
            resultadoComparacion = ordenTrabajo.getNombre().toUpperCase().contains(ordenTrabajoBusqueda.getCliente().toUpperCase()) &&
                    resultadoComparacion;
        }

        if (!TextUtils.isEmpty(ordenTrabajoBusqueda.getCodigoLlave1())) {
            resultadoComparacion = ordenTrabajo.getCodigoLlave1().toUpperCase().contains(ordenTrabajoBusqueda.getCodigoLlave1().toUpperCase()) &&
                    resultadoComparacion;
        }

        if (!TextUtils.isEmpty(ordenTrabajoBusqueda.getCodigoLlave2())) {
            resultadoComparacion = ordenTrabajo.getCodigoLlave2().toUpperCase().contains(ordenTrabajoBusqueda.getCodigoLlave2().toUpperCase()) &&
                    resultadoComparacion;
        }

        if (!TextUtils.isEmpty(ordenTrabajoBusqueda.getTareaXTrabajo().getTarea().getCodigoTarea()) ||
                (ordenTrabajoBusqueda.getEstadoTarea() != Tarea.EstadoTarea.NINGUNA)) {

            resultadoComparacion = listaCodigoOrdenTrabajos.contains(ordenTrabajo.getCodigoOrdenTrabajo()) &&
                    resultadoComparacion;
        }

        if (!TextUtils.isEmpty(ordenTrabajoBusqueda.getTareaXTrabajo().getTrabajo().getCodigoTrabajo())) {
            resultadoComparacion = listaCodigoOrdenTrabajos.contains(ordenTrabajo.getCodigoOrdenTrabajo()) &&
                    resultadoComparacion;
        }

        if (!TextUtils.isEmpty(ordenTrabajoBusqueda.getSerieElemento())) {
            resultadoComparacion = listaCodigoOrdenTrabajos.contains(ordenTrabajo.getCodigoOrdenTrabajo()) &&
                    resultadoComparacion;
        }

        if (ordenTrabajoBusqueda.isNoDescargados()) {
            resultadoComparacion = listaCodigoOrdenTrabajos.contains(ordenTrabajo.getCodigoOrdenTrabajo()) &&
                    resultadoComparacion;
        }
        return resultadoComparacion;
    }


    private boolean validarFiltro(TareaXOrdenTrabajo tareaXOrdenTrabajo, List<String> listaCodigoOrdenTrabajos,
                                  OrdenTrabajoBusqueda ordenTrabajoBusqueda) {
        boolean resultadoComparacion = true;
        if (!TextUtils.isEmpty(ordenTrabajoBusqueda.getCodigoOrdenTrabajo())) {
            resultadoComparacion = tareaXOrdenTrabajo.getCodigoOrdenTrabajo().toUpperCase().contains(ordenTrabajoBusqueda.getCodigoOrdenTrabajo().toUpperCase());
        }

        if (!TextUtils.isEmpty(ordenTrabajoBusqueda.getDireccion())) {
            resultadoComparacion = tareaXOrdenTrabajo.getOrdenTrabajo().getDireccion().toUpperCase().contains(ordenTrabajoBusqueda.getDireccion().toUpperCase()) &&
                    resultadoComparacion;
        }

        if (!TextUtils.isEmpty(ordenTrabajoBusqueda.getCliente())) {
            resultadoComparacion = tareaXOrdenTrabajo.getOrdenTrabajo().getNombre().toUpperCase().contains(ordenTrabajoBusqueda.getCliente().toUpperCase()) &&
                    resultadoComparacion;

        }

        if (!TextUtils.isEmpty(ordenTrabajoBusqueda.getCodigoLlave1())) {
            resultadoComparacion = tareaXOrdenTrabajo.getOrdenTrabajo().getCodigoLlave1().toUpperCase().contains(ordenTrabajoBusqueda.getCodigoLlave1().toUpperCase()) &&
                    resultadoComparacion;
        }

        if (!TextUtils.isEmpty(ordenTrabajoBusqueda.getCodigoLlave2())) {
            resultadoComparacion = tareaXOrdenTrabajo.getOrdenTrabajo().getCodigoLlave2().toUpperCase().contains(ordenTrabajoBusqueda.getCodigoLlave2().toUpperCase()) &&
                    resultadoComparacion;
        }

        if (!TextUtils.isEmpty(ordenTrabajoBusqueda.getTareaXTrabajo().getTarea().getCodigoTarea())) {
            resultadoComparacion = tareaXOrdenTrabajo.getTarea().getCodigoTarea().toUpperCase().contains(ordenTrabajoBusqueda.getTareaXTrabajo().getTarea().getCodigoTarea().toUpperCase()) &&
                    resultadoComparacion;
        }

        if (ordenTrabajoBusqueda.getEstadoTarea() != Tarea.EstadoTarea.NINGUNA) {
            resultadoComparacion = tareaXOrdenTrabajo.getEstadoTarea().equals(ordenTrabajoBusqueda.getEstadoTarea()) &&
                    resultadoComparacion;
        }

        if (!TextUtils.isEmpty(ordenTrabajoBusqueda.getTareaXTrabajo().getTrabajo().getCodigoTrabajo())) {
            resultadoComparacion = listaCodigoOrdenTrabajos.contains(tareaXOrdenTrabajo.getOrdenTrabajo().getCodigoOrdenTrabajo()) &&
                    resultadoComparacion;
        }

        if (ordenTrabajoBusqueda.isNoDescargados()) {
            resultadoComparacion = tareaXOrdenTrabajo.getFechaDescarga() == null &&
                    resultadoComparacion;
        }
        return resultadoComparacion;
    }
}