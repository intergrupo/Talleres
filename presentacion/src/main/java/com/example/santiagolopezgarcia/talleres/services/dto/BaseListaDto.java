package com.example.santiagolopezgarcia.talleres.services.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public interface BaseListaDto<T extends Serializable> {

    List<T> convertirListaDtoAListaDominio();

    int getLongitudLista();

    String getOperacion();
}
