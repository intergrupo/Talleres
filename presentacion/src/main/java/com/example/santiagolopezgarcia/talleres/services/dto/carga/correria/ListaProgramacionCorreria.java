package com.example.santiagolopezgarcia.talleres.services.dto.carga.correria;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_PROGRAMACIONCORRERIAS")
public class ListaProgramacionCorreria implements BaseListaDto<com.example.dominio.modelonegocio.ProgramacionCorreria> {

    @ElementList(inline = true, required = false)
    public List<ProgramacionCorreria> Sirius_ProgramacionCorreria;

    @Override
    public List<com.example.dominio.modelonegocio.ProgramacionCorreria> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.ProgramacionCorreria> listaProgramacionCorreria =
                new ArrayList<>(Lists.transform(this.Sirius_ProgramacionCorreria, programacionCorreriaXML ->
                        {
                            com.example.dominio.modelonegocio.ProgramacionCorreria programacionCorreria =
                                    new com.example.dominio.modelonegocio.ProgramacionCorreria();
                            programacionCorreria.setIdProgramacionCorreria(programacionCorreriaXML.IdProgramacionCorreria);
                            programacionCorreria.setCodigoPrograma(programacionCorreriaXML.CodigoPrograma);
                            programacionCorreria.setCodigoCorreria(programacionCorreriaXML.CodigoCorreria);
                            programacionCorreria.setNumeroTerminal(programacionCorreriaXML.NumeroTerminal);
                            programacionCorreria.setEstadoProgramada(programacionCorreriaXML.EstadoProgramada);
                            return programacionCorreria;
                        }
                ));
        return listaProgramacionCorreria;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_ProgramacionCorreria != null) {
            longitud = this.Sirius_ProgramacionCorreria.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_ProgramacionCorreria.get(0).Operacion != null && this.Sirius_ProgramacionCorreria.get(0).Operacion != "") {
                operacion = this.Sirius_ProgramacionCorreria.get(0).Operacion;
            }
        }
        return operacion;
    }
}

