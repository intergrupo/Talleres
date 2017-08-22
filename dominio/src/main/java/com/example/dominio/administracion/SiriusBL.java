package com.example.dominio.administracion;

import com.example.dominio.IBaseDescarga;
import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;
import com.example.dominio.modelonegocio.Sirius;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class SiriusBL implements LogicaNegocioBase<Sirius>, IBaseDescarga<Sirius> {

    private SiriusRepositorio siriusRepositorio;
    private ICambioTerminal iCambioTerminal;
    private String versionMaestros;

    public SiriusBL(SiriusRepositorio siriusRepositorio) {
        this.siriusRepositorio = siriusRepositorio;
    }

    public Sirius cargarPrimerRegistro() {
        try {
            return this.siriusRepositorio.cargarPrimerRegistro();
        } catch (ParseException e) {
            e.printStackTrace();
            return new Sirius();
        }
    }

    public Sirius consultarTerminalXCodigo(String codigoTerminal) {
        try {
            return this.siriusRepositorio.consultarSiriusXNumeroTerminal(codigoTerminal);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Sirius();
        }
    }

    public ICambioTerminal getiCambioTerminal() {
        return iCambioTerminal;
    }

    public void setiCambioTerminal(ICambioTerminal iCambioTerminal) {
        this.iCambioTerminal = iCambioTerminal;
    }

    public boolean guardar(Sirius sirius) {
        return this.siriusRepositorio.guardar(sirius);
    }

    public boolean eliminar(Sirius sirius) {
        return this.siriusRepositorio.eliminar(sirius);
    }

    public boolean guardar(List<Sirius> listaSirius) {
        return this.siriusRepositorio.guardar(listaSirius);
    }

    public boolean procesarDatos(Sirius sirius) {
        try {
            if (!siriusRepositorio.cargarPrimerRegistro().getId().isEmpty()) {
                return actualizar(sirius);
            } else {
                return this.siriusRepositorio.guardar(sirius);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Sirius sirius) {
        return siriusRepositorio.actualizar(sirius);
    }

    public boolean cambiarNumeroTerminal(Sirius sirius, Sirius siriusPrimerRegistro) {
        sirius.setVersionSoftware(siriusPrimerRegistro.getVersionSoftware());
        sirius.setTipoIdentificacion(siriusPrimerRegistro.getTipoIdentificacion());
        sirius.setWifi(siriusPrimerRegistro.getWifi());
        sirius.setDireccionImpresora(siriusPrimerRegistro.getDireccionImpresora());
        sirius.setFechaMaestros(siriusPrimerRegistro.getFechaMaestros());
        sirius.setImpresora(siriusPrimerRegistro.getImpresora());
        sirius.setRutaServidor(siriusPrimerRegistro.getRutaServidor());
        return eliminar(siriusPrimerRegistro) && guardar(sirius);
    }

    public boolean cambiarNumeroTerminal(Sirius sirius) {
        return siriusRepositorio.actualizar(sirius, true);
    }

    public boolean tieneRegistros() {
        return this.siriusRepositorio.tieneRegistros();
    }

    public boolean borrarBD() {

        Sirius siriusRegistro = this.cargarPrimerRegistro();
        siriusRegistro.setFechaMaestros(null);
        siriusRegistro.setVersionMaestros("");
        siriusRegistro.setVersionSoftware("");
        boolean resultado = siriusRepositorio.borrarDB();
        this.guardar(siriusRegistro);
        return resultado;
    }

    @Override
    public boolean procesar(List<Sirius> listaDatos, String operacion) {
        Sirius siriusPrimerRegistro;
        boolean respuesta = false;
        for (Sirius sirius : listaDatos) {
            if(versionMaestros != null)
                sirius.setVersionMaestros(versionMaestros);
            switch (operacion) {
                case "A":
                    if (cargarPrimerRegistro().getId().isEmpty()) {
                        respuesta = guardar(sirius);
                    }
                    break;
                case "U":
                    respuesta = actualizar(sirius);
                    break;
                case "D":
                    respuesta = eliminar(sirius);
                    break;
                case "R":
                    siriusPrimerRegistro = cargarPrimerRegistro();
                    if (!siriusPrimerRegistro.getId().isEmpty()) {
                        if (iCambioTerminal != null) {
                            respuesta = siriusRepositorio.actualizar(sirius, true);
                        } else {
                            respuesta = actualizar(sirius);
                        }
                    } else {
                        respuesta = guardar(sirius);
                    }
                    break;
                default:
                    if (cargarPrimerRegistro().getId().isEmpty()) {
                        respuesta = guardar(sirius);
                    }
                    break;
            }
        }
        return respuesta;
    }

    public void setVersionMaestros(String versionMaestros) {
        this.versionMaestros = versionMaestros;
    }

    @Override
    public List<Sirius> cargarXCorreria(String codigoCorreria) {
        List<Sirius> listaSirius = new ArrayList<>();
        listaSirius.add(cargarPrimerRegistro());
        return listaSirius;
    }

    @Override
    public List<Sirius> cargarXFiltro(OrdenTrabajoBusqueda filtro) {
        List<Sirius> listaSirius = new ArrayList<>();
        listaSirius.add(cargarPrimerRegistro());
        return listaSirius;
    }
}