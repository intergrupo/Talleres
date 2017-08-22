package com.example.santiagolopezgarcia.talleres.services.dto;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public interface BaseListaDtoOtTrabajoTarea extends BaseListaDtoCorreria{
    void eliminarItemPorOtTrabajoTarea(List<String> tareasXOtAOmitir);
}