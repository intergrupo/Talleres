package com.example.dominio;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */
public interface LogicaNegocioBase<T> {
    boolean procesar(List<T> listaDatos, String operacion);
}
