package com.example.dominio.correria;

import com.example.dominio.IBaseDescarga;
import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;
import com.example.dominio.modelonegocio.ProgramacionCorreria;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class ProgramacionCorreriaBL implements LogicaNegocioBase<ProgramacionCorreria>,IBaseDescarga<ProgramacionCorreria> {

    ProgramacionCorreriaRepositorio programacionCorreriaRepositorio;

    public ProgramacionCorreriaBL(ProgramacionCorreriaRepositorio programacionCorreriaRepositorio) {
        this.programacionCorreriaRepositorio = programacionCorreriaRepositorio;
    }


    public ProgramacionCorreria cargar(String idProgramacionCorreria, String codigoCorreria, String numeroTerminal) {
        return programacionCorreriaRepositorio.cargar(idProgramacionCorreria, codigoCorreria, numeroTerminal);
    }

    @Override
    public List<ProgramacionCorreria> cargarXCorreria(String codigoCorreria) {
        return programacionCorreriaRepositorio.cargarXCorreria(codigoCorreria);
    }

    public List<ProgramacionCorreria> cargar() {
        return programacionCorreriaRepositorio.cargar();
    }

    public boolean guardar(ProgramacionCorreria programacionCorreria){
        try {
            return programacionCorreriaRepositorio.guardar(programacionCorreria);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(ProgramacionCorreria programacionCorreria){
        return programacionCorreriaRepositorio.actualizar(programacionCorreria);
    }

    public boolean eliminar(ProgramacionCorreria programacionCorreria){
        return programacionCorreriaRepositorio.eliminar(programacionCorreria);
    }

    @Override
    public List<ProgramacionCorreria> cargarXFiltro(OrdenTrabajoBusqueda filtro) {
        return programacionCorreriaRepositorio.cargarXCorreria(filtro.getCodigoCorreria());
    }

    @Override
    public boolean procesar(List<ProgramacionCorreria> listaDatos, String operacion) {
        boolean respuesta = false;
        for (ProgramacionCorreria programacionCorreria: listaDatos){
            switch (operacion){
                case "A":
                    if(cargar(programacionCorreria.getIdProgramacionCorreria(),
                            programacionCorreria.getCodigoCorreria(),
                            programacionCorreria.getNumeroTerminal()).esClaveLlena()) {
                        respuesta = guardar(programacionCorreria);
                    }
                    break;
                case "U":
                    respuesta = actualizar(programacionCorreria);
                    break;
                case "D":
                    respuesta = eliminar(programacionCorreria);
                    break;
                case "R":
                    if(cargar(programacionCorreria.getIdProgramacionCorreria(),
                            programacionCorreria.getCodigoCorreria(),
                            programacionCorreria.getNumeroTerminal()).esClaveLlena()){
                        respuesta = actualizar(programacionCorreria);
                    }else {
                        respuesta = guardar(programacionCorreria);
                    }
                    break;
                default:
                    respuesta = guardar(programacionCorreria);
                    break;
            }
        }
        return respuesta;
    }

    public List<ProgramacionCorreria> cargar(List<String> codigosCorreriasIntegradas) {
        String codigosCorreriasConsulta = "";
        for (int i = 0; i < codigosCorreriasIntegradas.size(); i++) {
            if (i == codigosCorreriasIntegradas.size() - 1)
                codigosCorreriasConsulta += "'" + codigosCorreriasIntegradas.get(i).trim() + "'";
            else
                codigosCorreriasConsulta += "'" + codigosCorreriasIntegradas.get(i).trim() + "',";
        }
        return programacionCorreriaRepositorio.cargar(codigosCorreriasConsulta);
    }
}
