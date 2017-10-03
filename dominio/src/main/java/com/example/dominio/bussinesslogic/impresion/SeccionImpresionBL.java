package com.example.dominio.bussinesslogic.impresion;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.SeccionImpresion;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class SeccionImpresionBL implements LogicaNegocioBase<SeccionImpresion> {

    private SeccionImpresionRepositorio seccionImpresionRepositorio;

    public SeccionImpresionBL(SeccionImpresionRepositorio seccionimpresion) {
        this.seccionImpresionRepositorio = seccionimpresion;
    }

    public boolean guardar(List<SeccionImpresion> listaSeccionImpresion) {
        try {
            return seccionImpresionRepositorio.guardar(listaSeccionImpresion);
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean tieneRegistros() {
        return seccionImpresionRepositorio.tieneRegistros();
    }

    public List<SeccionImpresion> cargar(String codigoImpresion){
        return seccionImpresionRepositorio.cargar(codigoImpresion);
    }

    public boolean guardar(SeccionImpresion seccionImpresion){
        try {
            return seccionImpresionRepositorio.guardar(seccionImpresion);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(SeccionImpresion seccionImpresion){
        return seccionImpresionRepositorio.actualizar(seccionImpresion);
    }

    public boolean eliminar(SeccionImpresion seccionImpresion){
        return seccionImpresionRepositorio.eliminar(seccionImpresion);
    }

    public SeccionImpresion cargar(String codigoImpresion, String codigoSeccion){
        return seccionImpresionRepositorio.cargar(codigoImpresion, codigoSeccion);
    }

    @Override
    public boolean procesar(List<SeccionImpresion> listaDatos, String operacion) {
        boolean respuesta = false;
        for (SeccionImpresion seccionImpresion : listaDatos) {
            switch (operacion) {
                case "A":
                    if (!cargar(seccionImpresion.getCodigoImpresion(), seccionImpresion.getCodigoSeccion()).esClaveLlena()) {
                        respuesta = guardar(seccionImpresion);
                    }
                    break;
                case "U":
                    respuesta = actualizar(seccionImpresion);
                    break;
                case "D":
                    respuesta = eliminar(seccionImpresion);
                    break;
                case "R":
                    if (cargar(seccionImpresion.getCodigoImpresion(), seccionImpresion.getCodigoSeccion()).esClaveLlena()) {
                        respuesta = actualizar(seccionImpresion);
                    } else {
                        respuesta = guardar(seccionImpresion);
                    }
                    break;
                default:
                    if (!cargar(seccionImpresion.getCodigoImpresion(), seccionImpresion.getCodigoSeccion()).esClaveLlena()) {
                        respuesta = guardar(seccionImpresion);
                    }
                    break;
            }
        }
        return respuesta;
    }
}