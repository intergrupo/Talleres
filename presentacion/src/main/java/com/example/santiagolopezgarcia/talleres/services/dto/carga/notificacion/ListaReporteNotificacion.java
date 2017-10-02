package com.example.santiagolopezgarcia.talleres.services.dto.carga.notificacion;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDtoOtTarea;
import com.example.utilidades.helpers.StringHelper;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

@Root(name = "SIRIUS_REPORTESNOTIFICACIONES")
public class ListaReporteNotificacion implements BaseListaDto<com.example.dominio.modelonegocio.ReporteNotificacion>, BaseListaDtoOtTarea {
    @ElementList(inline = true,required = false)
    public List<ReporteNotificacion> Sirius_ReporteNotificacion;

    @Override
    public List<com.example.dominio.modelonegocio.ReporteNotificacion> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.ReporteNotificacion> listaItemXNotificacion = new ArrayList<>(Lists.transform(this.Sirius_ReporteNotificacion, reporteNotificacionXML ->
                {
                    com.example.dominio.modelonegocio.ReporteNotificacion reporteNotificacion = new com.example.dominio.modelonegocio.ReporteNotificacion();
                    reporteNotificacion.setCodigoCorreria(reporteNotificacionXML.CodigoCorreria);
                    reporteNotificacion.setCodigoOrdenTrabajo(reporteNotificacionXML.CodigoOrdenTrabajo);
                    reporteNotificacion.setCodigoTarea(reporteNotificacionXML.CodigoTarea);
                    reporteNotificacion.setCodigoLabor(reporteNotificacionXML.CodigoLabor);
                    reporteNotificacion.setCodigoNotificacion(reporteNotificacionXML.CodigoNotificacion);
                    reporteNotificacion.setCodigoItem(reporteNotificacionXML.CodigoItem);
                    reporteNotificacion.setCodigoLista(reporteNotificacionXML.CodigoLista);
                    reporteNotificacion.setOrden(reporteNotificacionXML.Orden);
                    reporteNotificacion.setDescripcion(reporteNotificacionXML.Descripcion);
                    reporteNotificacion.setDescarga(StringHelper.ToBoolean(reporteNotificacionXML.Descarga));
                    return reporteNotificacion;
                }
        ));
        return listaItemXNotificacion;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_ReporteNotificacion != null) {
            longitud = this.Sirius_ReporteNotificacion.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_ReporteNotificacion.get(0).Operacion != null && this.Sirius_ReporteNotificacion.get(0).Operacion != "") {
                operacion = this.Sirius_ReporteNotificacion.get(0).Operacion;
            }
        }
        return operacion;
    }

    @Override
    public void eliminarItemPorOtTarea(List<String> otTareas) {
        if (this.getLongitudLista() > 0 && otTareas.size() > 0) {
            Iterator<ReporteNotificacion> iterator = this.Sirius_ReporteNotificacion.iterator();
            while (iterator.hasNext()) {
                ReporteNotificacion reporteNotificacion = iterator.next();
                String codigoTarea = String.format("%s%s%s", reporteNotificacion.CodigoCorreria, reporteNotificacion.CodigoOrdenTrabajo
                        , reporteNotificacion.CodigoTarea);
                if (otTareas.contains(codigoTarea)) {
                    iterator.remove();
                }
            }
        }
    }

    @Override
    public void eliminarItemPorCorreria(List<String> correriasOmitir) {
        if (this.getLongitudLista() > 0 && correriasOmitir.size() > 0) {
            Iterator<ReporteNotificacion> iterator = this.Sirius_ReporteNotificacion.iterator();
            while (iterator.hasNext()) {
                ReporteNotificacion reporteNotificacion = iterator.next();
                if (correriasOmitir.contains(reporteNotificacion.CodigoCorreria)) {
                    iterator.remove();
                }
            }
        }
    }
}
