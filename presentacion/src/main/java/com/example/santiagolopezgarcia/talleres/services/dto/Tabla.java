package com.example.santiagolopezgarcia.talleres.services.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

@Root(name = "Tabla")
public class Tabla {

    public static final String ADICIONAR = "A";
    public static final String ACTUALIZAR = "U";
    public static final String ELIMINAR = "D";
    public static final String REEMPLAZAR = "R";

    public Tabla() {
        Nombre = "";
        OP = "";
        Valor = "";
    }

    @Element
    public String Nombre;

    @Element
    public String OP;

    @Element
    public String Valor;

}