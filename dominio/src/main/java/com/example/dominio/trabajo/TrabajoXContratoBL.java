package com.example.dominio.trabajo;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.TrabajoXContrato;

import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class TrabajoXContratoBL implements LogicaNegocioBase<TrabajoXContrato> {

    private TrabajoXContratoRepositorio trabajoXContratoRepositorio;
    private TrabajoBL trabajoBL;

    @Inject
    public TrabajoXContratoBL(TrabajoXContratoRepositorio trabajoXContratoRepositorio,
                              TrabajoBL trabajoBL) {
        this.trabajoXContratoRepositorio = trabajoXContratoRepositorio;
        this.trabajoBL = trabajoBL;
    }

    public boolean guardarTrabajosXContratos(List<TrabajoXContrato> listaTrabajosXContratos) {
        try {
            return trabajoXContratoRepositorio.guardar(listaTrabajosXContratos);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean tieneRegistrosTrabajoXContrato() {
        return trabajoXContratoRepositorio.tieneRegistros();
    }

    public List<TrabajoXContrato> cargarTrabajoXContratos(String codigoContrato) {
        List<TrabajoXContrato> listaTrabajosXContratos = trabajoXContratoRepositorio.cargarTrabajoXContratos(codigoContrato);
        for (TrabajoXContrato trabajoXContrato : listaTrabajosXContratos) {
            trabajoXContrato.setTrabajo(trabajoBL.cargarTrabajo(trabajoXContrato.getTrabajo().getCodigoTrabajo()));
        }
        return listaTrabajosXContratos;
    }

    public TrabajoXContrato cargarTrabajoXContrato(String codigoContrato, String codigoTrabajo) {
        return trabajoXContratoRepositorio.cargarTrabajoXContrato(codigoContrato, codigoTrabajo);
    }

    public boolean guardar(TrabajoXContrato trabajoXContrato) {
        try {
            return trabajoXContratoRepositorio.guardar(trabajoXContrato);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(TrabajoXContrato trabajoXContrato) {
        return trabajoXContratoRepositorio.actualizar(trabajoXContrato);
    }

    public boolean eliminar(TrabajoXContrato trabajoXContrato) {
        return trabajoXContratoRepositorio.eliminar(trabajoXContrato);
    }


    @Override
    public boolean procesar(List<TrabajoXContrato> listaDatos, String operacion) {
        boolean respuesta = false;
        for (TrabajoXContrato trabajoXContrato : listaDatos) {
            switch (operacion) {
                case "A":
                    if (!cargarTrabajoXContrato(trabajoXContrato.getContrato().getCodigoContrato(),
                            trabajoXContrato.getTrabajo().getCodigoTrabajo()).esClaveLlena()) {
                        respuesta = guardar(trabajoXContrato);
                    }
                    break;
                case "U":
                    respuesta = actualizar(trabajoXContrato);
                    break;
                case "D":
                    respuesta = eliminar(trabajoXContrato);
                    break;
                case "R":
                    if (cargarTrabajoXContrato(trabajoXContrato.getContrato().getCodigoContrato(),
                            trabajoXContrato.getTrabajo().getCodigoTrabajo()).esClaveLlena()) {
                        respuesta = actualizar(trabajoXContrato);
                    } else {
                        respuesta = guardar(trabajoXContrato);
                    }
                    break;
                default:
                    if (!cargarTrabajoXContrato(trabajoXContrato.getContrato().getCodigoContrato(),
                            trabajoXContrato.getTrabajo().getCodigoTrabajo()).esClaveLlena()) {
                        respuesta = guardar(trabajoXContrato);
                    }
                    break;
            }
        }
        return respuesta;
    }
}