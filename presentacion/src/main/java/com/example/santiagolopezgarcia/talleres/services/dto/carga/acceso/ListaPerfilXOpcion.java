package com.example.santiagolopezgarcia.talleres.services.dto.carga.acceso;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_PERFILESOPCION")
public class ListaPerfilXOpcion implements BaseListaDto<com.example.dominio.modelonegocio.PerfilXOpcion> {

    @ElementList(inline = true)
    public List<PerfilXOpcion> Sirius_PerfilXOpcion;

    @Override
    public List<com.example.dominio.modelonegocio.PerfilXOpcion> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.PerfilXOpcion> listaPerfilXOpcion = new ArrayList<>(Lists.transform(this.Sirius_PerfilXOpcion, perfilXOpcionXML ->
                {
                    com.example.dominio.modelonegocio.PerfilXOpcion localidad = new com.example.dominio.modelonegocio.PerfilXOpcion();
                    localidad.getPerfil().setCodigoPerfil(perfilXOpcionXML.CodigoPerfil);
                    localidad.getOpcion().setCodigoOpcion(perfilXOpcionXML.CodigoOpcion);
                    return localidad;
                }
        ));
        return listaPerfilXOpcion;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_PerfilXOpcion != null) {
            longitud = this.Sirius_PerfilXOpcion.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_PerfilXOpcion.get(0).Operacion != null && this.Sirius_PerfilXOpcion.get(0).Operacion != "") {
                operacion = this.Sirius_PerfilXOpcion.get(0).Operacion;
            }
        }
        return operacion;
    }
}
