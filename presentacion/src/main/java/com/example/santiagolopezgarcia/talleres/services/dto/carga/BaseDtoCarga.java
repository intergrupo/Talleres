package com.example.santiagolopezgarcia.talleres.services.dto.carga;

import org.simpleframework.xml.Element;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class BaseDtoCarga {
    @Element(required = false,name = "OP")
    public String Operacion;
}
