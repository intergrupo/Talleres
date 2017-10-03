package com.example.santiagolopezgarcia.talleres.util;

import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.dominio.modelonegocio.TareaXOrdenTrabajo;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class PosicionesTareasYOTS {


    public static int obtenerPosicionTareaXOT(boolean tieneFiltroActivo, String codigoOT) {
        int posicion = 0;
        if (tieneFiltroActivo) {

            for (TareaXOrdenTrabajo tareaXOrdenTrabajo : DatosCache.getListaTareaXOrdenTrabajoFiltrado()) {
                if (tareaXOrdenTrabajo.getCodigoOrdenTrabajo().equals(codigoOT)) {
                    break;
                }
                posicion++;
            }
            if(posicion >= DatosCache.getListaTareaXOrdenTrabajoFiltrado().size()){
                posicion = 1;
            }
        } else {
            for (TareaXOrdenTrabajo tareaXOrdenTrabajo : DatosCache.getListaTareaXOrdenTrabajo()) {
                if (tareaXOrdenTrabajo.getCodigoOrdenTrabajo().equals(codigoOT)) {
                    break;
                }
                posicion++;
            }
            if(posicion >= DatosCache.getListaTareaXOrdenTrabajo().size()){
                posicion = 0;
            }
        }
        return posicion;
    }

    public static int obtenerPosicionOT(boolean tieneFiltroActivo, String codigoOT) {
        int posicion = 0;
        if (tieneFiltroActivo) {

            for (OrdenTrabajo ordenTrabajo : DatosCache.getListaOrdenTrabajoFiltrado()) {
                if (ordenTrabajo.getCodigoOrdenTrabajo().equals(codigoOT)) {
                    break;
                }
                posicion++;
            }
        } else {
            for (OrdenTrabajo ordenTrabajo : DatosCache.getListaOrdenTrabajo()) {
                if (ordenTrabajo.getCodigoOrdenTrabajo().equals(codigoOT)) {
                    break;
                }
                posicion++;
            }
        }
        return posicion;
    }



    public static int obtenerPosicionTarea(boolean tieneFiltroActivo, TareaXOrdenTrabajo tareaXOrdenTrabajoActiva) {
        int posicion = 0;
        if (tieneFiltroActivo) {
            for (TareaXOrdenTrabajo tareaXOrdenTrabajo : DatosCache.getListaTareaXOrdenTrabajoFiltrado()) {
                if (tareaXOrdenTrabajo.getCodigoOrdenTrabajo().equals(tareaXOrdenTrabajoActiva.getCodigoOrdenTrabajo())
                        && tareaXOrdenTrabajo.getTarea().getCodigoTarea().equals(
                        tareaXOrdenTrabajoActiva.getTarea().getCodigoTarea())) {
                    break;
                }
                posicion++;
            }
        } else {
            for (TareaXOrdenTrabajo tareaXOrdenTrabajo : DatosCache.getListaTareaXOrdenTrabajo()) {
                if (tareaXOrdenTrabajo.getCodigoOrdenTrabajo().equals(tareaXOrdenTrabajoActiva.getCodigoOrdenTrabajo())
                        && tareaXOrdenTrabajo.getTarea().getCodigoTarea().equals(
                        tareaXOrdenTrabajoActiva.getTarea().getCodigoTarea())) {
                    break;
                }
                posicion++;
            }
        }
        return posicion;
    }
}
