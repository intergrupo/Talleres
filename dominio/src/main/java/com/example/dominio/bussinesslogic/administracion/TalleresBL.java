package com.example.dominio.bussinesslogic.administracion;

import com.example.dominio.IBaseDescarga;
import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;
import com.example.dominio.modelonegocio.Talleres;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class TalleresBL implements LogicaNegocioBase<Talleres>, IBaseDescarga<Talleres> {

    private TalleresRepositorio talleresRepositorio;
    private ICambioTerminal iCambioTerminal;
    private String versionMaestros;

    public TalleresBL(TalleresRepositorio talleresRepositorio) {
        this.talleresRepositorio = talleresRepositorio;
    }

    public Talleres cargarPrimerRegistro() {
        try {
            return this.talleresRepositorio.cargarPrimerRegistro();
        } catch (ParseException e) {
            e.printStackTrace();
            return new Talleres();
        }
    }

    public Talleres consultarTerminalXCodigo(String codigoTerminal) {
        try {
            return this.talleresRepositorio.consultarTalleresXNumeroTerminal(codigoTerminal);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Talleres();
        }
    }

    public ICambioTerminal getiCambioTerminal() {
        return iCambioTerminal;
    }

    public void setiCambioTerminal(ICambioTerminal iCambioTerminal) {
        this.iCambioTerminal = iCambioTerminal;
    }

    public boolean guardar(Talleres talleres) {
        return this.talleresRepositorio.guardar(talleres);
    }

    public boolean eliminar(Talleres talleres) {
        return this.talleresRepositorio.eliminar(talleres);
    }

    public boolean guardar(List<Talleres> listaTalleres) {
        return this.talleresRepositorio.guardar(listaTalleres);
    }

    public boolean procesarDatos(Talleres talleres) {
        try {
            if (!talleresRepositorio.cargarPrimerRegistro().getId().isEmpty()) {
                return actualizar(talleres);
            } else {
                return this.talleresRepositorio.guardar(talleres);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Talleres talleres) {
        return talleresRepositorio.actualizar(talleres);
    }

    public boolean cambiarNumeroTerminal(Talleres talleres, Talleres talleresPrimerRegistro) {
        talleres.setVersionSoftware(talleresPrimerRegistro.getVersionSoftware());
        talleres.setTipoIdentificacion(talleresPrimerRegistro.getTipoIdentificacion());
        talleres.setWifi(talleresPrimerRegistro.getWifi());
        talleres.setDireccionImpresora(talleresPrimerRegistro.getDireccionImpresora());
        talleres.setFechaMaestros(talleresPrimerRegistro.getFechaMaestros());
        talleres.setImpresora(talleresPrimerRegistro.getImpresora());
        talleres.setRutaServidor(talleresPrimerRegistro.getRutaServidor());
        return eliminar(talleresPrimerRegistro) && guardar(talleres);
    }

    public boolean cambiarNumeroTerminal(Talleres talleres) {
        return talleresRepositorio.actualizar(talleres, true);
    }

    public boolean tieneRegistros() {
        return this.talleresRepositorio.tieneRegistros();
    }

    public boolean borrarBD() {

        Talleres talleresRegistro = this.cargarPrimerRegistro();
        talleresRegistro.setFechaMaestros(null);
        talleresRegistro.setVersionMaestros("");
        talleresRegistro.setVersionSoftware("");
        boolean resultado = talleresRepositorio.borrarDB();
        this.guardar(talleresRegistro);
        return resultado;
    }

    @Override
    public boolean procesar(List<Talleres> listaDatos, String operacion) {
        Talleres talleresPrimerRegistro;
        boolean respuesta = false;
        for (Talleres talleres : listaDatos) {
            if(versionMaestros != null)
                talleres.setVersionMaestros(versionMaestros);
            switch (operacion) {
                case "A":
                    if (cargarPrimerRegistro().getId().isEmpty()) {
                        respuesta = guardar(talleres);
                    }
                    break;
                case "U":
                    respuesta = actualizar(talleres);
                    break;
                case "D":
                    respuesta = eliminar(talleres);
                    break;
                case "R":
                    talleresPrimerRegistro = cargarPrimerRegistro();
                    if (!talleresPrimerRegistro.getId().isEmpty()) {
                        if (iCambioTerminal != null) {
                            respuesta = talleresRepositorio.actualizar(talleres, true);
                        } else {
                            respuesta = actualizar(talleres);
                        }
                    } else {
                        respuesta = guardar(talleres);
                    }
                    break;
                default:
                    if (cargarPrimerRegistro().getId().isEmpty()) {
                        respuesta = guardar(talleres);
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
    public List<Talleres> cargarXCorreria(String codigoCorreria) {
        List<Talleres> listaSirius = new ArrayList<>();
        listaSirius.add(cargarPrimerRegistro());
        return listaSirius;
    }

    @Override
    public List<Talleres> cargarXFiltro(OrdenTrabajoBusqueda filtro) {
        List<Talleres> listaSirius = new ArrayList<>();
        listaSirius.add(cargarPrimerRegistro());
        return listaSirius;
    }
}