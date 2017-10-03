package com.example.santiagolopezgarcia.talleres.services.dto.carga.acceso;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/3/17.
 */

@Root(name = "SIRIUS_OPCIONES")
public class ListaOpcion implements BaseListaDto<com.example.dominio.modelonegocio.Opcion> {

    @ElementList(inline = true)
    public List<Opcion> Sirius_Opcion;

    @Override
    public List<com.example.dominio.modelonegocio.Opcion> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.Opcion> listaOpcion = new ArrayList<>(Lists.transform(this.Sirius_Opcion, opcionXML ->
                {
                    com.example.dominio.modelonegocio.Opcion localidad = new com.example.dominio.modelonegocio.Opcion();
                    localidad.setCodigoOpcion(opcionXML.CodigoOpcion);
                    localidad.setParametros(opcionXML.Parametros);
                    localidad.setRutina(opcionXML.Rutina);
                    localidad.setDescripcion(opcionXML.Descripcion);
                    localidad.setMenu(opcionXML.Menu);
                    return localidad;
                }
        ));
        return listaOpcion;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_Opcion != null) {
            longitud = this.Sirius_Opcion.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_Opcion.get(0).Operacion != null && this.Sirius_Opcion.get(0).Operacion != "") {
                operacion = this.Sirius_Opcion.get(0).Operacion;
            }
        }
        return operacion;
    }
}