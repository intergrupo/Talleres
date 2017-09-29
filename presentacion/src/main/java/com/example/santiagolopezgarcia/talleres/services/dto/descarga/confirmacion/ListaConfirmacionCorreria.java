package com.example.santiagolopezgarcia.talleres.services.dto.descarga.confirmacion;

import com.example.dominio.modelonegocio.ProgramacionCorreria;
import com.example.santiagolopezgarcia.talleres.services.dto.interfaces.BaseListaDtoConfirmacion;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

@Root(name = "SIRIUS_CONFIRMACIONCORRERIAS")
public class ListaConfirmacionCorreria implements BaseListaDtoConfirmacion<ConfirmacionCorreria, ProgramacionCorreria> {


    @ElementList(inline = true)
    public List<ConfirmacionCorreria> Sirius_ConfirmacionCorreria;

    @Override
    public List<ConfirmacionCorreria> convertirListaDominioAListaDto(
            List<com.example.dominio.modelonegocio.ProgramacionCorreria> listaDominio,
            String numeroTerminal, String fecha, String sesion) {
        this.Sirius_ConfirmacionCorreria = Lists.transform(listaDominio, programacionCorreriaNegocio ->
        {
            ConfirmacionCorreria confirmacionCorreriaDto =
                    new ConfirmacionCorreria();
            confirmacionCorreriaDto.IdProgramacionCorreria = programacionCorreriaNegocio.getIdProgramacionCorreria();
            confirmacionCorreriaDto.CodigoCorreria = programacionCorreriaNegocio.getCodigoCorreria();
            confirmacionCorreriaDto.NumeroTerminal = numeroTerminal;
            confirmacionCorreriaDto.Fecha = fecha;
            confirmacionCorreriaDto.Sesion = sesion;
            confirmacionCorreriaDto.Estado = programacionCorreriaNegocio.getEstadoProgramada();
            return confirmacionCorreriaDto;
        });
        return this.Sirius_ConfirmacionCorreria;
    }
}