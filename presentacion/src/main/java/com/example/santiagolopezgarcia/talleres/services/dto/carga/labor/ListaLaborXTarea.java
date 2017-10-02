package com.example.santiagolopezgarcia.talleres.services.dto.carga.labor;

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

@Root(name = "SIRIUS_LABORESXTAREA")
public class ListaLaborXTarea implements BaseListaDto<com.example.dominio.modelonegocio.LaborXTarea> {

    @ElementList(inline = true)
    public List<LaborXTarea> Sirius_LaborXTarea;

    @Override
    public List<com.example.dominio.modelonegocio.LaborXTarea> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.LaborXTarea> listaLaborXTarea = new ArrayList<>(Lists.transform(this.Sirius_LaborXTarea, laborXTareaXML ->
                {
                    com.example.dominio.modelonegocio.LaborXTarea laborXTarea = new com.example.dominio.modelonegocio.LaborXTarea();
                    laborXTarea.getLabor().setCodigoLabor(laborXTareaXML.CodigoLabor);
                    laborXTarea.getTarea().setCodigoTarea(laborXTareaXML.CodigoTarea);
                    laborXTarea.setObligatoria(StringHelper.ToBoolean(laborXTareaXML.Obligatoria));
                    laborXTarea.setOrden(laborXTareaXML.Orden);
                    laborXTarea.setParametros(laborXTareaXML.Parametros);
                    return laborXTarea;
                }
        ));
        return listaLaborXTarea;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_LaborXTarea != null) {
            longitud = this.Sirius_LaborXTarea.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_LaborXTarea.get(0).Operacion != null && this.Sirius_LaborXTarea.get(0).Operacion != "") {
                operacion = this.Sirius_LaborXTarea.get(0).Operacion;
            }
        }
        return operacion;
    }
}