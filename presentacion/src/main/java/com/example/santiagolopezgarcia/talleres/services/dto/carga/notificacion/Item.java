package com.example.santiagolopezgarcia.talleres.services.dto.carga.notificacion;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_ITEM")
public class Item extends BaseDtoCarga {

    public Item() {
        CodigoItem = "";
        Descripcion = "";
        TipoItem = "";
        Mascara = "";
        Formato = "";
        TablaLista = "";
        CampoLlaveItem = "";
        CampoValorLista = "";
        CampoDisplayLista = "";
        Rutina = "";
        Parametros = "";
    }

    @Element
    public String CodigoItem;

    @Element
    public String Descripcion;

    @Element
    public String TipoItem;

    @Element(required = false)
    public String Mascara;

    @Element(required = false)
    public String Formato;

    @Element(required = false)
    public String TablaLista;

    @Element(required = false)
    public String CampoLlaveItem;

    @Element(required = false)
    public String CampoValorLista;

    @Element(required = false)
    public String CampoDisplayLista;

    @Element
    public String Rutina;

    @Element(required = false)
    public String Parametros;
}