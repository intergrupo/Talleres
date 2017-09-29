package com.example.santiagolopezgarcia.talleres.services.dto.carga.ordentrabajo;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

@Root(name = "SIRIUS_NOTIFICACIONOTS")
public class ListaNotificacionOrdenTrabajo implements BaseListaDto<com.example.dominio.modelonegocio.NotificacionOrdenTrabajo> {

    @ElementList(inline = true, required = false)
    public List<NotificacionOrdenTrabajo> Sirius_NotificacionOrdenTrabajo;

    @Override
    public List<com.example.dominio.modelonegocio.NotificacionOrdenTrabajo> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.NotificacionOrdenTrabajo> listaNotificacionOT =
                new ArrayList<>(
                        Lists.transform(this.Sirius_NotificacionOrdenTrabajo, notificacionOrdenTrabajoXML ->
                                {
                                    com.example.dominio.modelonegocio.NotificacionOrdenTrabajo notificacionOrdenTrabajo =
                                            new com.example.dominio.modelonegocio.NotificacionOrdenTrabajo();
                                    notificacionOrdenTrabajo.setCodigoCorreria(notificacionOrdenTrabajoXML.CodigoCorreria);
                                    notificacionOrdenTrabajo.setCodigoOrdenTrabajo(notificacionOrdenTrabajoXML.CodigoOrdenTrabajo);
                                    notificacionOrdenTrabajo.setCodigoTarea(notificacionOrdenTrabajoXML.CodigoTarea);
                                    notificacionOrdenTrabajo.setOperacion(notificacionOrdenTrabajoXML.Operacion);
                                    return notificacionOrdenTrabajo;
                                }
                        )
                );
        return listaNotificacionOT;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_NotificacionOrdenTrabajo != null) {
            longitud = this.Sirius_NotificacionOrdenTrabajo.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_NotificacionOrdenTrabajo.get(0).Operacion != null && this.Sirius_NotificacionOrdenTrabajo.get(0).Operacion != "") {
                operacion = this.Sirius_NotificacionOrdenTrabajo.get(0).Operacion;
            }
        }
        return operacion;
    }

}
