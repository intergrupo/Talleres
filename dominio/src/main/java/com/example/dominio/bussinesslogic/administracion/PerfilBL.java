package com.example.dominio.bussinesslogic.administracion;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.Perfil;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class PerfilBL implements LogicaNegocioBase<Perfil> {

    PerfilRepositorio perfilRepositorio;

    public PerfilBL(PerfilRepositorio perfilRepositorio) {
        this.perfilRepositorio = perfilRepositorio;
    }

    public boolean guardar(List<Perfil> listaPerfiles) {
        try {
            return perfilRepositorio.guardar(listaPerfiles);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean guardar(Perfil perfil){
        try {
            return perfilRepositorio.guardar(perfil);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Perfil perfil){
        return perfilRepositorio.actualizar(perfil);
    }

    public boolean eliminar(Perfil perfil){
        return perfilRepositorio.eliminar(perfil);
    }

    public boolean tieneRegistros() {
        return perfilRepositorio.tieneRegistros();
    }

    public Perfil cargarPerfilXCodigo(String codigoPerfil) {
        return perfilRepositorio.cargarPerfilXCodigo(codigoPerfil);
    }

    public List<Perfil> cargarPerfiles() {
        return perfilRepositorio.cargarPerfiles();
    }

    @Override
    public boolean procesar(List<Perfil> listaDatos, String operacion) {
        boolean respuesta = false;
        for (Perfil perfil : listaDatos){
            switch (operacion){
                case "A":
                    respuesta = guardar(perfil);
                    break;
                case "U":
                    respuesta = actualizar(perfil);
                    break;
                case "D":
                    respuesta = eliminar(perfil);
                    break;
                case "R":
                    if(!cargarPerfilXCodigo(perfil.getCodigoPerfil()).getCodigoPerfil().isEmpty()){
                        respuesta = actualizar(perfil);
                    }else {
                        respuesta = guardar(perfil);
                    }
                    break;
                default:
                    respuesta = guardar(perfil);
                    break;
            }
        }
        return respuesta;
    }
}
