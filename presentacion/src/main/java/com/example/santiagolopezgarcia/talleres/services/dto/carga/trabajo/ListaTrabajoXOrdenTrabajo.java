package com.example.santiagolopezgarcia.talleres.services.dto.carga.trabajo;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDtoCorreria;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_TRABAJOSXORDENTRABAJO")
public class ListaTrabajoXOrdenTrabajo implements BaseListaDto<com.example.dominio.modelonegocio.TrabajoXOrdenTrabajo>, BaseListaDtoCorreria {

    @ElementList(inline = true, required = false)
    public List<TrabajoXOrdenTrabajo> Sirius_TrabajoXOrdenTrabajo;

    @Override
    public List<com.example.dominio.modelonegocio.TrabajoXOrdenTrabajo> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.TrabajoXOrdenTrabajo> listaTrabajosXOrdenTrabajo = new ArrayList<>(Lists.transform(this.Sirius_TrabajoXOrdenTrabajo, trabajoXOrdenTRabajoXML ->
                {
                    com.example.dominio.modelonegocio.TrabajoXOrdenTrabajo trabajoXOrdenTrabajo = new com.example.dominio.modelonegocio.TrabajoXOrdenTrabajo();
                    trabajoXOrdenTrabajo.setCodigoCorreria(trabajoXOrdenTRabajoXML.CodigoCorreria);
                    trabajoXOrdenTrabajo.setCodigoOrdenTrabajo(trabajoXOrdenTRabajoXML.CodigoOrdenTrabajo);
                    trabajoXOrdenTrabajo.getTrabajo().setCodigoTrabajo(trabajoXOrdenTRabajoXML.CodigoTrabajo);
                    return trabajoXOrdenTrabajo;
                }
        ));
        return listaTrabajosXOrdenTrabajo;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_TrabajoXOrdenTrabajo != null) {
            longitud = this.Sirius_TrabajoXOrdenTrabajo.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_TrabajoXOrdenTrabajo.get(0).Operacion != null && this.Sirius_TrabajoXOrdenTrabajo.get(0).Operacion != "") {
                operacion = this.Sirius_TrabajoXOrdenTrabajo.get(0).Operacion;
            }
        }
        return operacion;
    }

    @Override
    public void eliminarItemPorCorreria(List<String> correriasOmitir) {
        if (this.getLongitudLista() > 0 && correriasOmitir.size() > 0) {
            Iterator<TrabajoXOrdenTrabajo> iterator = this.Sirius_TrabajoXOrdenTrabajo.iterator();
            while (iterator.hasNext()) {
                TrabajoXOrdenTrabajo trabajoXOrdenTrabajo = iterator.next();
                if (correriasOmitir.contains(trabajoXOrdenTrabajo.CodigoCorreria)) {
                    iterator.remove();
                }
            }
        }
    }
}