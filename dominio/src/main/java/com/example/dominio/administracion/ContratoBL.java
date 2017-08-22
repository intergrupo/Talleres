package com.example.dominio.administracion;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.Contrato;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class ContratoBL implements LogicaNegocioBase<Contrato> {

    ContratoRepositorio contratoRepositorio;

    public ContratoBL(ContratoRepositorio contratoRepositorio) {
        this.contratoRepositorio = contratoRepositorio;
    }

    public boolean guardar(List<Contrato> listaContratos) {
        try {
            return contratoRepositorio.guardar(listaContratos);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean guardar(Contrato contrato){
        try {
            return contratoRepositorio.guardar(contrato);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Contrato contrato){
        return contratoRepositorio.actualizar(contrato);
    }

    public boolean eliminar(Contrato contrato){
        return contratoRepositorio.eliminar(contrato);
    }

    public List<Contrato> cargarContratos() {
        try {
            return contratoRepositorio.cargarContratos();
        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Contrato cargarContratoXCodigo(String codigoContrato) {
        return contratoRepositorio.cargarContratoXCodigo(codigoContrato);
    }

    public boolean tieneRegistros() {
        return contratoRepositorio.tieneRegistros();
    }

    @Override
    public boolean procesar(List<Contrato> listaDatos, String operacion) {
        boolean respuesta = false;
        for (Contrato contrato : listaDatos) {
            switch (operacion) {
                case "A":
                    if (cargarContratoXCodigo(contrato.getCodigoContrato()).getCodigoContrato().isEmpty()) {
                        respuesta = guardar(contrato);
                    }
                    break;
                case "U":
                    respuesta = actualizar(contrato);
                    break;
                case "D":
                    respuesta = eliminar(contrato);
                    break;
                case "R":
                    if (!cargarContratoXCodigo(contrato.getCodigoContrato()).getCodigoContrato().isEmpty()) {
                        respuesta = actualizar(contrato);
                    } else {
                        respuesta = guardar(contrato);
                    }
                    break;
                default:
                    if (cargarContratoXCodigo(contrato.getCodigoContrato()).getCodigoContrato().isEmpty()) {
                        respuesta = guardar(contrato);
                    }
                    break;
            }
        }
        return respuesta;
    }
}
