package com.example.santiagolopezgarcia.talleres.services.dto.descarga.correria;

import com.example.santiagolopezgarcia.talleres.integracion.descarga.BaseListaDtoDescarga;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_PROGRAMACIONCORRERIAS")
public class ListaProgramacionCorreria implements BaseListaDtoDescarga<ProgramacionCorreria, com.example.dominio.modelonegocio.ProgramacionCorreria> {

    @ElementList(inline = true)
    public List<ProgramacionCorreria> Sirius_ProgramacionCorreria;

    @Override
    public List<ProgramacionCorreria> convertirListaDominioAListaDto(List<com.example.dominio.modelonegocio.ProgramacionCorreria> listaDominio) {
        this.Sirius_ProgramacionCorreria = Lists.transform(listaDominio, programacionCorreriaNegocio ->
        {
            ProgramacionCorreria programacionCorreriaDto = new ProgramacionCorreria();
            programacionCorreriaDto.IdProgramacionCorreria = programacionCorreriaNegocio.getIdProgramacionCorreria();
            programacionCorreriaDto.CodigoPrograma = programacionCorreriaNegocio.getCodigoPrograma();
            programacionCorreriaDto.CodigoCorreria = programacionCorreriaNegocio.getCodigoCorreria();
            programacionCorreriaDto.NumeroTerminal = programacionCorreriaNegocio.getNumeroTerminal();
            programacionCorreriaDto.EstadoProgramada = programacionCorreriaNegocio.getEstadoProgramada();
            return programacionCorreriaDto;
        });
        return this.Sirius_ProgramacionCorreria;
    }
}

