package com.example.santiagolopezgarcia.talleres.view.interfaces;

import android.view.View;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public interface UsuarioActivo<TEntidad> {
    void itemUsuarioSeleccionado(TEntidad entidad, View item);
}