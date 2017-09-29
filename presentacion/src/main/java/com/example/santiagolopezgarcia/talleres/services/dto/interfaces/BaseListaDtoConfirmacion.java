package com.example.santiagolopezgarcia.talleres.services.dto.interfaces;

import java.io.Serializable;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public interface BaseListaDtoConfirmacion <T,U extends Serializable>{

    List<T> convertirListaDominioAListaDto(List<U> listaDominio, String numeroTerminal,
                                           String fecha, String sesion);

}