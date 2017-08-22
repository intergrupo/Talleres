package com.example.santiagolopezgarcia.talleres.services.dto.carga.ordentrabajo;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDtoCorreria;
import com.example.utilidades.helpers.DateHelper;
import com.example.utilidades.helpers.StringHelper;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

@Root(name = "SIRIUS_ORDENTRABAJOS")
public class ListaOrdenTrabajo implements BaseListaDto<com.example.dominio.modelonegocio.OrdenTrabajo>, BaseListaDtoCorreria {

    @ElementList(inline = true, required = false)
    public List<OrdenTrabajo> Sirius_OrdenTrabajo;

    @Override
    public List<com.example.dominio.modelonegocio.OrdenTrabajo> convertirListaDtoAListaDominio() {
        com.example.dominio.modelonegocio.ListaOrdenTrabajo listaOrdenesTrabajo = new com.example.dominio.modelonegocio.ListaOrdenTrabajo(
                Lists.transform(this.Sirius_OrdenTrabajo, ordenTrabajoXml ->
                        {
                            com.example.dominio.modelonegocio.OrdenTrabajo ordenTrabajo = new com.example.dominio.modelonegocio.OrdenTrabajo();
                            ordenTrabajo.setNombre(ordenTrabajoXml.Nombre);
                            ordenTrabajo.setCodigoCorreria(ordenTrabajoXml.CodigoCorreria);
                            ordenTrabajo.getDepartamento().getMunicipio().setCodigoMunicipio(ordenTrabajoXml.CodigoMunicipio);
                            ordenTrabajo.setCodigoOrdenTrabajo(ordenTrabajoXml.CodigoOrdenTrabajo);
                            ordenTrabajo.setCodigoUsuarioLabor(ordenTrabajoXml.CodigoUsuarioLabor);
                            ordenTrabajo.setDireccion(ordenTrabajoXml.Direccion);
                            ordenTrabajo.setEstado(com.example.dominio.modelonegocio.OrdenTrabajo.EstadoOrdenTrabajo.getEstado(ordenTrabajoXml.CodigoEstado));
                            try {
                                ordenTrabajo.setFechaInicioOrdenTrabajo(DateHelper.convertirStringADate(ordenTrabajoXml.FechaInicioOrdenTrabajo, DateHelper.TipoFormato.yyyyMMddTHHmmss));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            try {
                                ordenTrabajo.setFechaUltimaOrdenTrabajo(DateHelper.convertirStringADate(ordenTrabajoXml.FechaUltimaOrdenTrabajo, DateHelper.TipoFormato.yyyyMMddTHHmmss));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            ordenTrabajo.setGps(ordenTrabajoXml.GPS);
                            ordenTrabajo.setNueva(StringHelper.ToBoolean(ordenTrabajoXml.Nueva));
                            ordenTrabajo.setCodigoLlave1(ordenTrabajoXml.Llave1);
                            ordenTrabajo.setCodigoLlave2(ordenTrabajoXml.Llave2);
                            ordenTrabajo.setSecuencia(ordenTrabajoXml.Secuencia);
                            ordenTrabajo.setTelefono(ordenTrabajoXml.Telefono);
                            ordenTrabajo.setParametros(ordenTrabajoXml.Parametros);
                            ordenTrabajo.setEstadoComunicacion(ordenTrabajoXml.EstadoComunicacion);
                            ordenTrabajo.setSesion(ordenTrabajoXml.Sesion);
                            ordenTrabajo.setFechaCarga(new Date());
                            ordenTrabajo.setImprimirFactura(ordenTrabajoXml.ImprimirFactura);
                            ordenTrabajo.setCodigoOrdenTrabajoRelacionada(ordenTrabajoXml.CodigoOrdenTrabajoRelacionada);
                            return ordenTrabajo;
                        }
                )
        );
        return listaOrdenesTrabajo;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_OrdenTrabajo != null) {
            longitud = this.Sirius_OrdenTrabajo.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_OrdenTrabajo.get(0).Operacion != null && this.Sirius_OrdenTrabajo.get(0).Operacion != "") {
                operacion = this.Sirius_OrdenTrabajo.get(0).Operacion;
            }
        }
        return operacion;
    }

    @Override
    public void eliminarItemPorCorreria(List<String> correriasOmitir) {
        if (this.Sirius_OrdenTrabajo != null) {
            Iterator<OrdenTrabajo> iterator = this.Sirius_OrdenTrabajo.iterator();
            while (iterator.hasNext()) {
                OrdenTrabajo ordenItem = iterator.next();
                if (correriasOmitir.contains(ordenItem.CodigoCorreria)) {
                    iterator.remove();
                }
            }
        }
    }
}
