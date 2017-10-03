package com.example.dominio.bussinesslogic.acceso;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.ListaMultiOpcion;
import com.example.dominio.modelonegocio.MultiOpcion;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class MultiOpcionBL implements LogicaNegocioBase<MultiOpcion> {

    MultiOpcionRepositorio multiOpcionRepositorio;

    public MultiOpcionBL(MultiOpcionRepositorio multiOpcionRepositorio) {
        this.multiOpcionRepositorio = multiOpcionRepositorio;
    }

    public boolean guardar(List<MultiOpcion> listaMultiOpcion) {
        try {
            return multiOpcionRepositorio.guardar(listaMultiOpcion);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean guardar(MultiOpcion multiOpcion) {
        try {
            return multiOpcionRepositorio.guardar(multiOpcion);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(MultiOpcion multiOpcion) {
        return multiOpcionRepositorio.actualizar(multiOpcion);
    }

    public boolean eliminar(MultiOpcion multiOpcion) {
        return multiOpcionRepositorio.eliminar(multiOpcion);
    }

    public boolean tieneRegistros() {
        return multiOpcionRepositorio.tieneRegistros();
    }

    public ListaMultiOpcion cargarListaMultiOpcionPorTipo(String codigoTipoOpcion) {
        try {
            return multiOpcionRepositorio.cargarListaMultiOpcionPorTipo(codigoTipoOpcion);
        } catch (ParseException e) {
            return null;
        }
    }

    public MultiOpcion cargarMultiOpcion(String codigoTipoOpcion, String codigoOpcion) {
        return multiOpcionRepositorio.cargarMultiOpcion(codigoTipoOpcion, codigoOpcion);
    }

    @Override
    public boolean procesar(List<MultiOpcion> listaDatos, String operacion) {
        boolean respuesta = false;
        for (MultiOpcion multiOpcion : listaDatos) {
            switch (operacion) {
                case "A":
                    if (!cargarMultiOpcion(multiOpcion.getCodigoTipoOpcion(), multiOpcion.getCodigoOpcion()).esClaveLlena()) {
                        respuesta = guardar(multiOpcion);
                    }
                    break;
                case "U":
                    respuesta = actualizar(multiOpcion);
                    break;
                case "D":
                    respuesta = eliminar(multiOpcion);
                    break;
                case "R":
                    if (cargarMultiOpcion(multiOpcion.getCodigoTipoOpcion(), multiOpcion.getCodigoOpcion()).esClaveLlena()) {
                        respuesta = actualizar(multiOpcion);
                    } else {
                        respuesta = guardar(multiOpcion);
                    }
                    break;
                default:
                    respuesta = guardar(multiOpcion);
                    break;
            }
        }
        return respuesta;
    }
}