package com.example.dominio.bussinesslogic.correria;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.Empresa;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class EmpresaBL implements LogicaNegocioBase<Empresa> {

    private EmpresaRepositorio empresaRepositorio;

    public EmpresaBL(EmpresaRepositorio empresaRepositorio) {
        this.empresaRepositorio = empresaRepositorio;
    }

    public boolean tieneRegistros() {
        return empresaRepositorio.tieneRegistros();
    }

    public boolean guardar(List<Empresa> listaEmpresa) {
        try {
            return empresaRepositorio.guardar(listaEmpresa);
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean guardar(Empresa empresa) {
        try {
            return empresaRepositorio.guardar(empresa);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Empresa cargar(Empresa empresa){
        return empresaRepositorio.cargarEmpresa(empresa);
    }

    public Empresa cargar(String codigoEmpresa){
        return empresaRepositorio.cargar(codigoEmpresa);
    }

    public boolean actualizar(Empresa empresa) {
        return empresaRepositorio.actualizar(empresa);
    }

    public boolean eliminar(Empresa empresa) {
        return empresaRepositorio.eliminar(empresa);
    }

    @Override
    public boolean procesar(List<Empresa> listaDatos, String operacion) {
        boolean respuesta = false;
        for (Empresa empresa : listaDatos) {
            switch (operacion) {
                case "A":
                    if (cargar(empresa).getCodigoEmpresa().isEmpty()) {
                        respuesta = guardar(empresa);
                    }
                    break;
                case "U":
                    respuesta = actualizar(empresa);
                    break;
                case "D":
                    respuesta = eliminar(empresa);
                    break;
                case "R":
                    if (!cargar(empresa).getCodigoEmpresa().isEmpty()) {
                        respuesta = actualizar(empresa);
                    } else {
                        respuesta = guardar(empresa);
                    }
                    break;
                default:
                    respuesta = guardar(empresa);
                    break;
            }
        }
        return respuesta;
    }
}
