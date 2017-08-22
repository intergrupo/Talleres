package com.example.santiagolopezgarcia.talleres.services.dto;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public interface BaseListaDtoOtTarea extends BaseListaDtoCorreria {
    void eliminarItemPorOtTarea(List<String> otTareas);
}