package com.example.santiagolopezgarcia.talleres.services.dto.carga.notificacion;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_NOTIFICACIONES")
public class ListaNotificacion implements BaseListaDto<com.example.dominio.modelonegocio.Notificacion> {
    @ElementList(inline=true, required = false)
    public List<Notificacion> Sirius_Notificacion;

    @Override
    public List<com.example.dominio.modelonegocio.Notificacion> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.Notificacion> listaNotificacion = new ArrayList<>(Lists.transform(this.Sirius_Notificacion, notificacionXML ->
                {
                    com.example.dominio.modelonegocio.Notificacion notificacion = new com.example.dominio.modelonegocio.Notificacion();
                    notificacion.setCodigoNotificacion(notificacionXML.CodigoNotificacion);
                    notificacion.setDescripcion(notificacionXML.Descripcion);
                    notificacion.setRutina(notificacionXML.Rutina);
                    notificacion.setParametros(notificacionXML.Parametros);
                    return notificacion;
                }
        ));
        return listaNotificacion;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_Notificacion!= null) {
            longitud = this.Sirius_Notificacion.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_Notificacion.get(0).Operacion != null && this.Sirius_Notificacion.get(0).Operacion != "") {
                operacion = this.Sirius_Notificacion.get(0).Operacion;
            }
        }
        return operacion;
    }
}