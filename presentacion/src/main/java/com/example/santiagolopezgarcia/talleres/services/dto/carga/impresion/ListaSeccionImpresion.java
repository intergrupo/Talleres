package com.example.santiagolopezgarcia.talleres.services.dto.carga.impresion;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_SECCIONIMPRESION")
public class ListaSeccionImpresion implements BaseListaDto<com.example.dominio.modelonegocio.SeccionImpresion> {
    @ElementList(inline = true,required = false)
    public List<SeccionImpresion> Sirius_SeccionImpresion;

    @Override
    public List<com.example.dominio.modelonegocio.SeccionImpresion> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.SeccionImpresion> listaSeccionImpresion = new ArrayList<>(Lists.transform(this.Sirius_SeccionImpresion, seccionImpresionXml ->
                {
                    com.example.dominio.modelonegocio.SeccionImpresion seccionImpresion = new com.example.dominio.modelonegocio.SeccionImpresion();
                    seccionImpresion.setCodigoImpresion(seccionImpresionXml.CodigoImpresion);
                    seccionImpresion.setCodigoSeccion(seccionImpresionXml.CodigoSeccion);
                    seccionImpresion.setDescripcion(seccionImpresionXml.Descripcion);
                    seccionImpresion.setRutina(seccionImpresionXml.Rutina);
                    seccionImpresion.setParametros(seccionImpresionXml.Parametros);

                    return seccionImpresion;
                }
        ));
        return listaSeccionImpresion;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_SeccionImpresion != null) {
            longitud = this.Sirius_SeccionImpresion.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_SeccionImpresion.get(0).Operacion != null && this.Sirius_SeccionImpresion.get(0).Operacion != "") {
                operacion = this.Sirius_SeccionImpresion.get(0).Operacion;
            }
        }
        return operacion;
    }
}
