package com.example.dominio.labor;

import com.example.dominio.IBaseDescarga;
import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.LaborXOrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;
import com.example.dominio.modelonegocio.TareaXOrdenTrabajo;
import com.example.utilidades.helpers.DateHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.example.utilidades.helpers.DateHelper.getCurrentDate;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class LaborXOrdenTrabajoBL implements LogicaNegocioBase<LaborXOrdenTrabajo>,
        IBaseDescarga<LaborXOrdenTrabajo> {

    private LaborXOrdenTrabajoRepositorio laborXOrdenTrabajoRepositorio;

    @Inject
    public LaborXOrdenTrabajoBL(LaborXOrdenTrabajoRepositorio laborXOrdenTrabajoRepositorio) {
        this.laborXOrdenTrabajoRepositorio = laborXOrdenTrabajoRepositorio;
    }

    public boolean guardarLaborXOrdenTrabajo(List<LaborXOrdenTrabajo> listaLaboresXOrdenTrabajo) {
        try {
            return laborXOrdenTrabajoRepositorio.guardar(listaLaboresXOrdenTrabajo);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<LaborXOrdenTrabajo> cargarLaboresXOrdenTrabajo(String codigoCorreria, String codigoOrdenTrabajo, String codigoTrabajo, String codigoTarea) {
        try {
            return laborXOrdenTrabajoRepositorio.cargarLaboresXOrdenTrabajo(codigoCorreria, codigoOrdenTrabajo, codigoTrabajo, codigoTarea);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<LaborXOrdenTrabajo> cargarLaboresXOrdenTrabajoEjecutadas(String codigoCorreria,
                                                                         String codigoOrdenTrabajo,
                                                                         String codigoTrabajo,
                                                                         String codigoTarea) {
        try {
            return laborXOrdenTrabajoRepositorio.cargarLaboresXOrdenTrabajoEjecutadas(codigoCorreria, codigoOrdenTrabajo,
                    codigoTrabajo, codigoTarea);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<LaborXOrdenTrabajo> cargarLaboresXOrdenTrabajoObligatoriasSinEjecutar(String codigoCorreria, String codigoOrdenTrabajo, String codigoTrabajo, String codigoTarea) {
        try {
            return laborXOrdenTrabajoRepositorio.cargarLaboresXOrdenTrabajoObligatoriasSinEjecutar(codigoCorreria, codigoOrdenTrabajo, codigoTrabajo, codigoTarea);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<LaborXOrdenTrabajo> cargarLaboresXOrdenTrabajo(String codigoCorreria) {
        try {
            return laborXOrdenTrabajoRepositorio.cargarLaboresXOrdenTrabajo(codigoCorreria);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean tieneRegistrosLaborXOrdenTrabajo() {
        return laborXOrdenTrabajoRepositorio.tieneRegistros();
    }

    public boolean guardar(LaborXOrdenTrabajo laborXOrdenTrabajo) {
        try {
            return laborXOrdenTrabajoRepositorio.guardar(laborXOrdenTrabajo);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public LaborXOrdenTrabajo cargarLaborXOT(String codigoCorreria, String codigoOrdenTrabajo,
                                             String codigoTrabajo, String codigoTarea,
                                             String codigoLabor) {
        try {
            return laborXOrdenTrabajoRepositorio.cargarLaborXOT(codigoCorreria, codigoOrdenTrabajo, codigoTrabajo,
                    codigoTarea, codigoLabor);
        } catch (ParseException e) {
            e.printStackTrace();
            return new LaborXOrdenTrabajo();
        }
    }

    public boolean eliminar(LaborXOrdenTrabajo laborXOrdenTrabajo) {
        return laborXOrdenTrabajoRepositorio.eliminar(laborXOrdenTrabajo);
    }

    public boolean actualizaLaborXOrdenTrabajo(LaborXOrdenTrabajo laborXOrdenTrabajo) {
        return laborXOrdenTrabajoRepositorio.actualizar(laborXOrdenTrabajo);
    }

    @Override
    public boolean procesar(List<LaborXOrdenTrabajo> listaDatos, String operacion) {
        boolean respuesta = false;
        for (LaborXOrdenTrabajo laborXOrdenTrabajo : listaDatos) {
            switch (operacion) {
                case "A":
                    if (!cargarLaborXOT(laborXOrdenTrabajo).esClaveLlena()) {
                        respuesta = guardar(laborXOrdenTrabajo);
                    }
                    break;
                case "U":
                    respuesta = actualizaLaborXOrdenTrabajo(laborXOrdenTrabajo);
                    break;
                case "D":
                    respuesta = eliminar(laborXOrdenTrabajo);
                    break;
                case "R":
                    if (cargarLaborXOT(laborXOrdenTrabajo).esClaveLlena()) {
                        respuesta = actualizaLaborXOrdenTrabajo(laborXOrdenTrabajo);
                    } else {
                        respuesta = guardar(laborXOrdenTrabajo);
                    }
                    break;
                default:
                    if (!cargarLaborXOT(laborXOrdenTrabajo).esClaveLlena()) {
                        respuesta = guardar(laborXOrdenTrabajo);
                    }
                    break;
            }
        }
        return respuesta;
    }

    public LaborXOrdenTrabajo cargarLaborXOT(LaborXOrdenTrabajo laborXOrdenTrabajo) {
        return cargarLaborXOT(laborXOrdenTrabajo.getCodigoCorreria(),
                laborXOrdenTrabajo.getCodigoOrdenTrabajo(), laborXOrdenTrabajo.getTrabajo().getCodigoTrabajo(),
                laborXOrdenTrabajo.getTarea().getCodigoTarea(), laborXOrdenTrabajo.getLabor().getCodigoLabor());
    }

    @Override
    public List<LaborXOrdenTrabajo> cargarXCorreria(String codigoCorreria) {
        return cargarLaboresXOrdenTrabajo(codigoCorreria);
    }

    @Override
    public List<LaborXOrdenTrabajo> cargarXFiltro(OrdenTrabajoBusqueda filtro) {
        List<LaborXOrdenTrabajo> lista = new ArrayList<>();
        if(filtro.getListaTareaXOrdenTrabajo()!= null && filtro.getListaTareaXOrdenTrabajo().size() > 0){
            try{
                for (TareaXOrdenTrabajo tareaXot : filtro.getListaTareaXOrdenTrabajo()) {
                    lista.addAll(laborXOrdenTrabajoRepositorio.cargarLaboresXOrdenTrabajoFiltro(
                            tareaXot.getCodigoCorreria(), tareaXot.getCodigoOrdenTrabajo(),
                            tareaXot.getCodigoTrabajo(), tareaXot.getTarea().getCodigoTarea()));
                }
            }
            catch (ParseException e){
                e.getStackTrace();
                return new ArrayList();
            }
        }
        return lista;
    }

    public List<LaborXOrdenTrabajo> cargarLaboresXOT(String codigoCorreria, String codigoOT) {
        return laborXOrdenTrabajoRepositorio.cargarLaboresXOTMenu(codigoCorreria, codigoOT);
    }

    public void validarEstadoOTFacturada(OrdenTrabajo ordenTrabajo, String codEstado){

        actualizarEstadoOTFacturada(ordenTrabajo.getCodigoOrdenTrabajo(),ordenTrabajo.getCodigoCorreria(),codEstado);
    }

    private void actualizarEstadoOTFacturada(String numeroOT, String correria,String codEstado){
        laborXOrdenTrabajoRepositorio.actualizarEstadoOTFacturada(numeroOT,correria,codEstado,getCurrentDate(DateHelper.TipoFormato.yyyyMMddTHHmmss));
    }

    public boolean actualizarEstado(LaborXOrdenTrabajo laborXOrdenTrabajo){
        return laborXOrdenTrabajoRepositorio.actualizarEstado(laborXOrdenTrabajo);
    }

}

