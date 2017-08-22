package com.example.dominio.correria;

import com.example.dominio.IBaseDescarga;
import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.Correria;
import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;
import com.example.dominio.notificacion.ReporteNotificacionBL;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class CorreriaBL implements LogicaNegocioBase<Correria>,IBaseDescarga<Correria> {

    private CorreriaRepositorio correriaRepositorio;
    private ReporteNotificacionBL reporteNotificacionBL;

    public CorreriaBL(CorreriaRepositorio correriaRepositorio,
                      ReporteNotificacionBL reporteNotificacionBL) {
        this.correriaRepositorio = correriaRepositorio;
        this.reporteNotificacionBL = reporteNotificacionBL;
    }

    public Correria cargar(String codigoCorreria) {
        return correriaRepositorio.cargar(codigoCorreria);
    }

    public List<Correria> cargar() {
        try {
            return correriaRepositorio.cargarCorrerias();
        } catch (ParseException e) {
            return null;
        }
    }

    public List<Correria> cargarXContrato(String codigoContrato){
        return correriaRepositorio.cargarXContrato(codigoContrato);
    }

    public List<Correria> cargarCorrerias(String codigo) {
        try {
            return correriaRepositorio.cargarCorrerias(codigo);
        } catch (ParseException e) {
            return null;
        }
    }

    public boolean guardar(List<Correria> listaCorrerias) {
        try {
            return correriaRepositorio.guardar(listaCorrerias);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean tieneRegistros() {
        return correriaRepositorio.tieneRegistros();
    }

    public boolean actualizar(Correria correria) {
        return correriaRepositorio.actualizar(correria);
    }

    public boolean guardar(Correria correria) {
        try {
            return correriaRepositorio.guardar(correria);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCorreria(Correria correria, String ruta) {

        boolean elimino = true;

        try {
            //Eliminar archivos adjuntos
            elimino = reporteNotificacionBL.borrarArchivosAdjuntos(correria.getCodigoCorreria(), ruta);

            if(elimino)
            {
                //TODO: Falta eliminar los adjuntos de la orden de trabajo

                //Elimnar correria y datos relacionados
                elimino = correriaRepositorio.eliminar(correria);
            }

        } catch (Exception e) {
            e.printStackTrace();
            elimino = false;
        }
        return elimino;
    }

    public boolean existe(String codigo){
        return this.correriaRepositorio.existe(codigo);
    }

    public boolean actualizarFechaDescarga(String codigoCorreria, String fechaDescarga) {
        return correriaRepositorio.actualizarFechaDescarga(codigoCorreria,fechaDescarga);
    }

    @Override
    public boolean procesar(List<Correria> listaDatos, String operacion) {
        boolean respuesta = false;
        for (Correria correria: listaDatos){
            switch (operacion){
                case "A":
                    if(cargar(correria.getCodigoCorreria()).getCodigoCorreria().isEmpty()) {
                        respuesta = guardar(correria);
                    }
                    break;
                case "U":
                    respuesta = actualizar(correria);
                    break;
                case "D":
                    respuesta = eliminarCorreria(correria, "");
                    break;
                case "R":
                    if(!cargar(correria.getCodigoCorreria()).getCodigoCorreria().isEmpty()){
                        respuesta = actualizar(correria);
                    }else {
                        respuesta = guardar(correria);
                    }
                    break;
                default:
                    respuesta = guardar(correria);
                    break;
            }
        }
        return respuesta;
    }

    @Override
    public List<Correria> cargarXCorreria(String codigoCorreria) {
        List<Correria> correrias=new ArrayList<>();
        correrias.add(this.cargar(codigoCorreria));
        return correrias;
    }

    @Override
    public List<Correria> cargarXFiltro(OrdenTrabajoBusqueda filtro) {
        return this.cargarXCorreria(filtro.getCodigoCorreria());
    }

    public List<Correria> cargarXContratoYSinContrato(String codigoContrato) {
        return correriaRepositorio.cargarXContratoYSinContrato(codigoContrato);
    }
}
