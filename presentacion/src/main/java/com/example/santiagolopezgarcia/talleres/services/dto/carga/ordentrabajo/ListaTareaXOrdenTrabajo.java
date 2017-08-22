package com.example.santiagolopezgarcia.talleres.services.dto.carga.ordentrabajo;

import com.example.dominio.modelonegocio.Tarea;
import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDtoOtTrabajoTarea;
import com.example.utilidades.helpers.DateHelper;
import com.example.utilidades.helpers.StringHelper;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

@Root(name = "SIRIUS_TAREASXORDENTRABAJO")
public class ListaTareaXOrdenTrabajo implements BaseListaDto<com.example.dominio.modelonegocio.TareaXOrdenTrabajo>, BaseListaDtoOtTrabajoTarea {

    @ElementList(inline = true, required = false)
    public List<TareaXOrdenTrabajo> Sirius_tareaxordentrabajo;

    @Override
    public List<com.example.dominio.modelonegocio.TareaXOrdenTrabajo> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.TareaXOrdenTrabajo> listaTareaXOrdenTrabajo = new ArrayList<>(Lists.transform(this.Sirius_tareaxordentrabajo, tareaXOrdenTRabajoXML ->
                {
                    com.example.dominio.modelonegocio.TareaXOrdenTrabajo tareaXOrdenTrabajo = new com.example.dominio.modelonegocio.TareaXOrdenTrabajo();
                    tareaXOrdenTrabajo.setCodigoCorreria(tareaXOrdenTRabajoXML.CodigoCorreria);
                    tareaXOrdenTrabajo.setCodigoOrdenTrabajo(tareaXOrdenTRabajoXML.CodigoOrdenTrabajo);
                    tareaXOrdenTrabajo.setCodigoTrabajo(tareaXOrdenTRabajoXML.CodigoTrabajo);
                    tareaXOrdenTrabajo.getTarea().setCodigoTarea(tareaXOrdenTRabajoXML.CodigoTarea);
                    try {
                        tareaXOrdenTrabajo.setFechaInicioTarea(DateHelper.convertirStringADate(tareaXOrdenTRabajoXML.FechaInicioTarea, DateHelper.TipoFormato.yyyyMMddTHHmmss));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        tareaXOrdenTrabajo.setFechaUltimaTarea(DateHelper.convertirStringADate(tareaXOrdenTRabajoXML.FechaUltimaTarea, DateHelper.TipoFormato.yyyyMMddTHHmmss));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        tareaXOrdenTrabajo.setFechaDescarga(DateHelper.convertirStringADate(tareaXOrdenTRabajoXML.FechaDescarga, DateHelper.TipoFormato.yyyyMMddTHHmmss));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    tareaXOrdenTrabajo.setCodigoUsuarioTarea(tareaXOrdenTRabajoXML.CodigoUsuarioTarea);
                    tareaXOrdenTrabajo.setNueva(StringHelper.ToBoolean(tareaXOrdenTRabajoXML.Nueva));
                    tareaXOrdenTrabajo.setEstadoTarea(Tarea.EstadoTarea.getEstado(tareaXOrdenTRabajoXML.CodigoEstado));
                    tareaXOrdenTrabajo.setSecuencia(tareaXOrdenTRabajoXML.Secuencia);
                    tareaXOrdenTrabajo.setParametros(tareaXOrdenTRabajoXML.Parametros);
                    return tareaXOrdenTrabajo;
                }
        ));
        return listaTareaXOrdenTrabajo;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_tareaxordentrabajo != null) {
            longitud = this.Sirius_tareaxordentrabajo.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_tareaxordentrabajo.get(0).Operacion != null && this.Sirius_tareaxordentrabajo.get(0).Operacion != "") {
                operacion = this.Sirius_tareaxordentrabajo.get(0).Operacion;
            }
        }
        return operacion;
    }

    @Override
    public void eliminarItemPorOtTrabajoTarea(List<String> tareasXOtAOmitir) {
        if (this.getLongitudLista() > 0 && tareasXOtAOmitir.size() > 0) {
            Iterator<TareaXOrdenTrabajo> iterator = this.Sirius_tareaxordentrabajo.iterator();
            while (iterator.hasNext()) {
                TareaXOrdenTrabajo tareaXOrdenTrabajo = iterator.next();
                String codigoTarea = String.format("%s%s%s%s", tareaXOrdenTrabajo.CodigoCorreria, tareaXOrdenTrabajo.CodigoOrdenTrabajo
                        , tareaXOrdenTrabajo.CodigoTrabajo, tareaXOrdenTrabajo.CodigoTarea);
                if (tareasXOtAOmitir.contains(codigoTarea)) {
                    iterator.remove();
                }
            }
        }
    }

    @Override
    public void eliminarItemPorCorreria(List<String> correriasOmitir) {
        if (this.getLongitudLista() > 0 && correriasOmitir.size() > 0) {
            Iterator<TareaXOrdenTrabajo> iterator = this.Sirius_tareaxordentrabajo.iterator();
            while (iterator.hasNext()) {
                if (correriasOmitir.contains(iterator.next().CodigoCorreria)) {
                    iterator.remove();
                }
            }
        }
    }
}
