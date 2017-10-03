package com.example.dominio.bussinesslogic.acceso;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.Municipio;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class MunicipioBL implements LogicaNegocioBase<Municipio> {

    MunicipioRepositorio municipioRepositorio;

    public MunicipioBL(MunicipioRepositorio municipioRepositorio) {
        this.municipioRepositorio = municipioRepositorio;
    }

    public List<Municipio> cargarMunicipios(String codigoDepartamento) {
        try {
            return municipioRepositorio.cargarMunicipios(codigoDepartamento);
        } catch (ParseException e) {
            return null;
        }
    }

    public Municipio cargarMunicipio(String codigoMunicipio) {
        try {
            return municipioRepositorio.cargarMunicipio(codigoMunicipio);
        } catch (ParseException e) {
            return null;
        }
    }

    public boolean guardar(List<Municipio> listaMunicipios) {
        try {
            return municipioRepositorio.guardar(listaMunicipios);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean guardar(Municipio municipio){
        try {
            return municipioRepositorio.guardar(municipio);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Municipio municipio){
        return municipioRepositorio.actualizar(municipio);
    }

    public boolean eliminar(Municipio municipio){
        return municipioRepositorio.eliminar(municipio);
    }

    public boolean tieneRegistros() {
        return municipioRepositorio.tieneRegistros();
    }

    @Override
    public boolean procesar(List<Municipio> listaDatos, String operacion) {
        boolean respuesta = false;
        for (Municipio municipio : listaDatos){
            switch (operacion){
                case "A":
                    if(cargarMunicipio(municipio.getCodigoMunicipio()).getCodigoMunicipio().isEmpty()) {
                        respuesta = guardar(municipio);
                    }
                    break;
                case "U":
                    respuesta = actualizar(municipio);
                    break;
                case "D":
                    respuesta = eliminar(municipio);
                    break;
                case "R":
                    if(!cargarMunicipio(municipio.getCodigoMunicipio()).getCodigoMunicipio().isEmpty()){
                        respuesta = actualizar(municipio);
                    }else {
                        respuesta = guardar(municipio);
                    }
                    break;
                default:
                    respuesta = guardar(municipio);
                    break;
            }
        }
        return respuesta;
    }
}