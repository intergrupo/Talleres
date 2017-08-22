package com.example.santiagolopezgarcia.talleres.integracion.descarga;

import java.io.Serializable;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public interface BaseListaDtoDescarga <T,U extends Serializable>{

    List<T> convertirListaDominioAListaDto(List<U> listaDominio);

}
