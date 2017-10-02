package com.example.santiagolopezgarcia.talleres.services.dto.carga.notificacion;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */
@Root(name = "SIRIUS_ITEMS")
public class ListaItem implements BaseListaDto<com.example.dominio.modelonegocio.Item> {
    @ElementList(inline=true, required = false)
    public List<Item> Sirius_Item;

    @Override
    public List<com.example.dominio.modelonegocio.Item> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.Item> listaItem = new ArrayList<>(Lists.transform(this.Sirius_Item, itemXML ->
                {
                    com.example.dominio.modelonegocio.Item item = new com.example.dominio.modelonegocio.Item();
                    item.setCodigoItem(itemXML.CodigoItem);
                    item.setDescripcion(itemXML.Descripcion);
                    item.setTipoItem(itemXML.TipoItem);
                    item.setMascara(itemXML.Mascara);
                    item.setFormato(itemXML.Formato);
                    item.setTablaLista(itemXML.TablaLista);
                    item.setCampoLlaveItem(itemXML.CampoLlaveItem);
                    item.setCampoValorLista(itemXML.CampoValorLista);
                    item.setCampoDisplayLista(itemXML.CampoDisplayLista);
                    item.setRutina(itemXML.Rutina);
                    item.setParametros(itemXML.Parametros);
                    return item;
                }
        ));
        return listaItem;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_Item!= null) {
            longitud = this.Sirius_Item.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_Item.get(0).Operacion != null && this.Sirius_Item.get(0).Operacion != "") {
                operacion = this.Sirius_Item.get(0).Operacion;
            }
        }
        return operacion;
    }
}