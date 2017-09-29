package com.example.dominio.acceso;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.Departamento;
import com.example.dominio.modelonegocio.Municipio;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class DepartamentoBL implements LogicaNegocioBase<Departamento> {

    DepartamentoRepositorio departamentoRepositorio;
    private MunicipioBL municipioBL;

    public DepartamentoBL(DepartamentoRepositorio departamentoRepositorio,
                          MunicipioBL municipioBL) {
        this.departamentoRepositorio = departamentoRepositorio;
        this.municipioBL = municipioBL;
    }

    public List<Departamento> cargar() {
        try {
            return departamentoRepositorio.cargarDepartamentos();
        } catch (ParseException e) {
            return null;
        }
    }

    public boolean guardar(List<Departamento> listaDepartamentos) {
        try {
            return departamentoRepositorio.guardar(listaDepartamentos);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean guardar(Departamento departamento) {
        try {
            return departamentoRepositorio.guardar(departamento);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Departamento departamento) {
        return departamentoRepositorio.actualizar(departamento);
    }

    public boolean eliminar(Departamento departamento) {
        return departamentoRepositorio.eliminar(departamento);
    }

    public boolean tieneRegistros() {
        return departamentoRepositorio.tieneRegistros();
    }

    public Departamento cargarDepratamento(String codigoMunicipio) {
        try {
            Municipio municipio = municipioBL.cargarMunicipio(codigoMunicipio);
            Departamento departamento = departamentoRepositorio.cargarDepartamento(municipio.getCodigoDepartamento());
            departamento.setMunicipio(municipio);
            return departamento;
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public boolean procesar(List<Departamento> listaDatos, String operacion) {
        boolean respuesta = false;
        for (Departamento departamento : listaDatos) {
            switch (operacion) {
                case "A":
                    if (!cargarDepratamento(departamento.getMunicipio().getCodigoMunicipio()).esCLaveLlena()) {
                        respuesta = guardar(departamento);
                    }
                    break;
                case "U":
                    respuesta = actualizar(departamento);
                    break;
                case "D":
                    respuesta = eliminar(departamento);
                    break;
                case "R":
                    if (cargarDepratamento(departamento.getMunicipio().getCodigoMunicipio()).esCLaveLlena()) {
                        respuesta = actualizar(departamento);
                    } else {
                        respuesta = guardar(departamento);
                    }
                    break;
                default:
                    respuesta = guardar(departamento);
                    break;
            }
        }
        return respuesta;
    }
}