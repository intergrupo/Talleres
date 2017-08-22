package com.example.santiagolopezgarcia.talleres.services.contracts;

import java.io.IOException;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public interface Suscriptor {

    void onError(Throwable e) throws IOException;

    void onCompletetado();

    <T> void  onResultado(T datos, String codigoServicio);

    <T> void  onResultadoAdjuntos(T datos);
}