package com.example.dominio.bussinesslogic.administracion;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.PerfilXOpcion;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class PerfilXOpcionBL implements LogicaNegocioBase<PerfilXOpcion> {

    private PerfilXOpcionRepositorio perfilXOpcionRepositorio;

    public PerfilXOpcionBL(PerfilXOpcionRepositorio perfilXOpcionRepositorio) {
        this.perfilXOpcionRepositorio = perfilXOpcionRepositorio;
    }

    public List<PerfilXOpcion> cargarPerfilXCodigo(String codigoPerfil) {
        return this.perfilXOpcionRepositorio.cargarPerfilOpcionXCodigoPerfil(codigoPerfil);
    }

    public PerfilXOpcion cargarPerfilOpcion(String codigoPerfil, String codigoOpcion){
        return perfilXOpcionRepositorio.cargarPerfilOpcion(codigoPerfil, codigoOpcion);
    }

    public boolean guardar(List<PerfilXOpcion> listaPerfilesXOpcion) {
        try {
            return perfilXOpcionRepositorio.guardar(listaPerfilesXOpcion);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean guardar(PerfilXOpcion perfilXOpcion){
        try {
            return perfilXOpcionRepositorio.guardar(perfilXOpcion);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(PerfilXOpcion perfilXOpcion){
        return perfilXOpcionRepositorio.actualizar(perfilXOpcion);
    }

    public boolean eliminar(PerfilXOpcion perfilXOpcion){
        return perfilXOpcionRepositorio.eliminar(perfilXOpcion);
    }

    public boolean tieneRegistros() {
        return perfilXOpcionRepositorio.tieneRegistros();
    }

    @Override
    public boolean procesar(List<PerfilXOpcion> listaDatos, String operacion) {
        boolean respuesta = false;
        for (PerfilXOpcion perfilXOpcion : listaDatos){
            switch (operacion){
                case "A":
                    respuesta = guardar(perfilXOpcion);
                    break;
                case "U":
                    respuesta = actualizar(perfilXOpcion);
                    break;
                case "D":
                    respuesta = eliminar(perfilXOpcion);
                    break;
                case "R":
                    if(cargarPerfilOpcion(perfilXOpcion.getPerfil().getCodigoPerfil(),
                            perfilXOpcion.getOpcion().getCodigoOpcion()).esClaveLlena()){
                        respuesta = actualizar(perfilXOpcion);
                    }else {
                        respuesta = guardar(perfilXOpcion);
                    }
                    break;
                default:
                    respuesta = guardar(perfilXOpcion);
                    break;
            }
        }
        return respuesta;
    }
}
