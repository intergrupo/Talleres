package com.example.santiagolopezgarcia.talleres.services.dto.carga.notificacion;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.example.utilidades.helpers.StringHelper;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_ITEMSXNOTIFICACIONES")
public class ListaItemXNotificacion implements BaseListaDto<com.example.dominio.modelonegocio.ItemXNotificacion> {
    @ElementList(inline=true, required = false)
    public List<ItemXNotificacion> Sirius_ItemXNotificacion;

    @Override
    public List<com.example.dominio.modelonegocio.ItemXNotificacion> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.ItemXNotificacion> listaItemXNotificacion = new ArrayList<>(Lists.transform(this.Sirius_ItemXNotificacion, itemXNotificacionXML ->
                {
                    com.example.dominio.modelonegocio.ItemXNotificacion itemXNotificacion = new com.example.dominio.modelonegocio.ItemXNotificacion();
                    itemXNotificacion.setCodigoNotificacion(itemXNotificacionXML.CodigoNotificacion);
                    itemXNotificacion.setCodigoItem(itemXNotificacionXML.CodigoItem);
                    itemXNotificacion.setOrden(Integer.parseInt(itemXNotificacionXML.Orden));
                    itemXNotificacion.setObligatorio(StringHelper.ToBoolean(itemXNotificacionXML.Obligatorio));
                    itemXNotificacion.setDescarga(StringHelper.ToBoolean(itemXNotificacionXML.Descarga));
                    return itemXNotificacion;
                }
        ));
        return listaItemXNotificacion;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_ItemXNotificacion!= null) {
            longitud = this.Sirius_ItemXNotificacion.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_ItemXNotificacion.get(0).Operacion != null && this.Sirius_ItemXNotificacion.get(0).Operacion != "") {
                operacion = this.Sirius_ItemXNotificacion.get(0).Operacion;
            }
        }
        return operacion;
    }
}