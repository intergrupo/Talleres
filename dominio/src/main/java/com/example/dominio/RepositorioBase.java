package com.example.dominio;

import java.text.ParseException;
import java.util.List;

/**
 * Created by juanfelipegomezvelez on 9/10/15.
 */
public interface RepositorioBase<T> {

    boolean tieneRegistros();

    boolean guardar(List<T> lista) throws ParseException;

    boolean guardar(T dato) throws ParseException;

    boolean actualizar(T dato);

    boolean eliminar(T dato);

}
