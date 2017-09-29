package com.example.santiagolopezgarcia.talleres.services.dto.descarga.confirmacion;

import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.services.dto.interfaces.BaseListaDtoConfirmacion;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

@Root(name = "SIRIUS_CONFIRMACIONOTS")
public class ListaConfirmacionOrdenTrabajo implements BaseListaDtoConfirmacion<ConfirmacionOrdenTrabajo, OrdenTrabajo> {


    @ElementList(inline = true)
    public List<ConfirmacionOrdenTrabajo> Sirius_ConfirmacionOT;

    @Override
    public List<ConfirmacionOrdenTrabajo> convertirListaDominioAListaDto(
            List<OrdenTrabajo> listaDominio,
            String numeroTerminal, String fecha, String sesion) {
        this.Sirius_ConfirmacionOT = Lists.transform(listaDominio, ordenTrabajoNegocio ->
        {
            ConfirmacionOrdenTrabajo confirmacionOrdenTrabajoDto =
                    new ConfirmacionOrdenTrabajo();
            confirmacionOrdenTrabajoDto.CodigoCorreria = ordenTrabajoNegocio.getCodigoCorreria();
            confirmacionOrdenTrabajoDto.CodigoOrdenTrabajo = ordenTrabajoNegocio.getCodigoOrdenTrabajo();
            confirmacionOrdenTrabajoDto.NumeroTerminal = numeroTerminal;
            confirmacionOrdenTrabajoDto.Fecha = fecha;
            confirmacionOrdenTrabajoDto.Sesion = sesion;
            confirmacionOrdenTrabajoDto.Estado = "C";
            return confirmacionOrdenTrabajoDto;
        });
        return this.Sirius_ConfirmacionOT;
    }
}