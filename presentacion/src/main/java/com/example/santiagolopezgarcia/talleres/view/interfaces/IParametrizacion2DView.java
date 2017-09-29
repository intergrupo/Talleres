package com.example.santiagolopezgarcia.talleres.view.interfaces;

import com.example.santiagolopezgarcia.talleres.services.dto.Parametrizacion2D;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public interface IParametrizacion2DView extends BaseView {
    void mostrarDatosCodigoBarras(Parametrizacion2D parametrizacion2D);

    void aplicarParametrizacion(String codigoBarras, Parametrizacion2D parametrizacion2D);

    void limpiarCampos();
}
