package com.example.dominio.bussinesslogic.impresion;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.ParrafoImpresion;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class ParrafoImpresionBL implements LogicaNegocioBase<ParrafoImpresion> {


    private ParrafoImpresionRepositorio parrafoImpresionRepositorio;

    public ParrafoImpresionBL(ParrafoImpresionRepositorio parrafoimpresion) {
        this.parrafoImpresionRepositorio = parrafoimpresion;
    }

    public boolean guardar(List<ParrafoImpresion> listaParrafoImpresion) {
        try {
            return parrafoImpresionRepositorio.guardar(listaParrafoImpresion);
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean tieneRegistros() {
        return parrafoImpresionRepositorio.tieneRegistros();
    }

    public List<ParrafoImpresion> cargar(String codigoImpresion) {
        return parrafoImpresionRepositorio.cargar(codigoImpresion);
    }

    public boolean guardar(ParrafoImpresion parrafoImpresion) {
        try {
            return parrafoImpresionRepositorio.guardar(parrafoImpresion);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(ParrafoImpresion parrafoImpresion) {
        return parrafoImpresionRepositorio.actualizar(parrafoImpresion);
    }

    public boolean eliminar(ParrafoImpresion parrafoImpresion) {
        return parrafoImpresionRepositorio.eliminar(parrafoImpresion);
    }

    public ParrafoImpresion cargar(String codigoImpresion, String codigoParrafo) {
        return parrafoImpresionRepositorio.cargar(codigoImpresion, codigoParrafo);
    }

    @Override
    public boolean procesar(List<ParrafoImpresion> listaDatos, String operacion) {
        boolean respuesta = false;
        for (ParrafoImpresion parrafoImpresion : listaDatos) {
            switch (operacion) {
                case "A":
                    if (!cargar(parrafoImpresion.getCodigoImpresion(), parrafoImpresion.getCodigoParrafo()).esClaveLlena()) {
                        respuesta = guardar(parrafoImpresion);
                    }
                    break;
                case "U":
                    respuesta = actualizar(parrafoImpresion);
                    break;
                case "D":
                    respuesta = eliminar(parrafoImpresion);
                    break;
                case "R":
                    if (cargar(parrafoImpresion.getCodigoImpresion(), parrafoImpresion.getCodigoParrafo()).esClaveLlena()) {
                        respuesta = actualizar(parrafoImpresion);
                    } else {
                        respuesta = guardar(parrafoImpresion);
                    }
                    break;
                default:
                    if (!cargar(parrafoImpresion.getCodigoImpresion(), parrafoImpresion.getCodigoParrafo()).esClaveLlena()) {
                        respuesta = guardar(parrafoImpresion);
                    }
                    break;
            }
        }
        return respuesta;
    }
}