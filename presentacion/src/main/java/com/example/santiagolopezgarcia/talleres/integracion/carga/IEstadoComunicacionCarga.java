package com.example.santiagolopezgarcia.talleres.integracion.carga;

import com.example.santiagolopezgarcia.talleres.integracion.IEstadoComunicacion;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public interface IEstadoComunicacionCarga extends IEstadoComunicacion {
    void confirmarRemplazoCorreria(String mensaje,int indiceCorreria);

    void confirmarRemplazoTarea(String mensaje,int indiceTarea);

    void confirmarActualizarMaestros(String mensaje);

    void establecerCargaSatisfactoria();

    void habilitarRegreso(boolean habilitar);
}