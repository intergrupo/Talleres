package com.example.santiagolopezgarcia.talleres.services.dto.carga.labor;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDtoOtTrabajoTarea;
import com.example.utilidades.helpers.DateHelper;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_LABORESXORDENTRABAJO")
public class ListaLaborXOrdenTrabajo implements BaseListaDto<com.example.dominio.modelonegocio.LaborXOrdenTrabajo>, BaseListaDtoOtTrabajoTarea {

    @ElementList(inline = true, required = false)
    public List<LaborXOrdenTrabajo> Sirius_LaborXOrdenTrabajo;

    @Override
    public List<com.example.dominio.modelonegocio.LaborXOrdenTrabajo> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.LaborXOrdenTrabajo> listaLaborXOrdenTrabajo = new ArrayList<>(Lists.transform(this.Sirius_LaborXOrdenTrabajo, laborXOrdenTrabajoXML ->
                {
                    com.example.dominio.modelonegocio.LaborXOrdenTrabajo laborXOrdenTrabajo = new com.example.dominio.modelonegocio.LaborXOrdenTrabajo();
                    laborXOrdenTrabajo.setCodigoCorreria(laborXOrdenTrabajoXML.CodigoCorreria);
                    laborXOrdenTrabajo.setEstadoLaborXOrdenTrabajo(com.example.dominio.modelonegocio.
                            LaborXOrdenTrabajo.EstadoLaborXOrdenTrabajo.getEstado(laborXOrdenTrabajoXML.CodigoEstado));
                    laborXOrdenTrabajo.setCodigoOrdenTrabajo(laborXOrdenTrabajoXML.CodigoOrdenTrabajo);
                    laborXOrdenTrabajo.setCodigoUsuarioLabor(laborXOrdenTrabajoXML.CodigoUsuarioLabor);
                    laborXOrdenTrabajo.setEstadoLaborXOrdenTrabajo(com.example.dominio.modelonegocio.LaborXOrdenTrabajo.EstadoLaborXOrdenTrabajo.getEstado(laborXOrdenTrabajoXML.CodigoEstado));
                    try {
                        laborXOrdenTrabajo.setFechaInicioLabor(DateHelper.convertirStringADate(laborXOrdenTrabajoXML.FechaInicioLabor, DateHelper.TipoFormato.yyyyMMddTHHmmss));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        laborXOrdenTrabajo.setFechaUltimoLabor(DateHelper.convertirStringADate(laborXOrdenTrabajoXML.FechaUltimoLabor, DateHelper.TipoFormato.yyyyMMddTHHmmss));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    laborXOrdenTrabajo.getLabor().setCodigoLabor(laborXOrdenTrabajoXML.CodigoLabor);
                    laborXOrdenTrabajo.getTarea().setCodigoTarea(laborXOrdenTrabajoXML.CodigoTarea);
                    laborXOrdenTrabajo.getTrabajo().setCodigoTrabajo(laborXOrdenTrabajoXML.CodigoTrabajo);
                    laborXOrdenTrabajo.setParametros(laborXOrdenTrabajoXML.Parametros);
                    return laborXOrdenTrabajo;
                }
        ));
        return listaLaborXOrdenTrabajo;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_LaborXOrdenTrabajo != null) {
            longitud = this.Sirius_LaborXOrdenTrabajo.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_LaborXOrdenTrabajo.get(0).Operacion != null && this.Sirius_LaborXOrdenTrabajo.get(0).Operacion != "") {
                operacion = this.Sirius_LaborXOrdenTrabajo.get(0).Operacion;
            }
        }
        return operacion;
    }

    @Override
    public void eliminarItemPorOtTrabajoTarea(List<String> tareasXOtAOmitir) {
        if (this.Sirius_LaborXOrdenTrabajo != null && tareasXOtAOmitir.size() > 0) {
            Iterator<LaborXOrdenTrabajo> iterator = this.Sirius_LaborXOrdenTrabajo.iterator();
            while (iterator.hasNext()) {
                LaborXOrdenTrabajo laborXOrdenTrabajo = iterator.next();
                String codigoTarea = String.format("%s%s%s%s", laborXOrdenTrabajo.CodigoCorreria, laborXOrdenTrabajo.CodigoOrdenTrabajo
                        , laborXOrdenTrabajo.CodigoTrabajo, laborXOrdenTrabajo.CodigoTarea);
                if (tareasXOtAOmitir.contains(codigoTarea)) {
                    iterator.remove();
                }
            }
        }
    }

    @Override
    public void eliminarItemPorCorreria(List<String> correriasOmitir) {
        if (this.Sirius_LaborXOrdenTrabajo.size() > 0 && correriasOmitir.size() > 0) {
            Iterator<LaborXOrdenTrabajo> iterator = this.Sirius_LaborXOrdenTrabajo.iterator();
            while (iterator.hasNext()) {
                if (correriasOmitir.contains(iterator.next().CodigoCorreria)) {
                    iterator.remove();
                }
            }
        }
    }
}