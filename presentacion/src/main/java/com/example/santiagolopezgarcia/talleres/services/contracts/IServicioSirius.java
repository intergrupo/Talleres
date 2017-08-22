package com.example.santiagolopezgarcia.talleres.services.contracts;

import com.example.santiagolopezgarcia.talleres.services.dto.PeticionCarga;
import com.example.santiagolopezgarcia.talleres.services.dto.PeticionDescarga;
import com.example.santiagolopezgarcia.talleres.services.dto.PeticionMensajeLog;
import com.example.santiagolopezgarcia.talleres.services.dto.RespuestaCarga;
import com.example.santiagolopezgarcia.talleres.services.dto.RespuestaDescarga;
import com.example.santiagolopezgarcia.talleres.services.dto.RespuestaMensajeLog;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public interface IServicioSirius {

    //    @Headers({
//            "Accept: application/json",
//            "Content-Type: application/json"
//    })
    @POST("Sirius_Carga")
    Observable<RespuestaCarga> cargar(@Body PeticionCarga peticionCarga);

    @POST("Sirius_Descarga")
    Observable<RespuestaDescarga> descargar(@Body PeticionDescarga peticionDescarga);

    @POST("Sirius_GetMensajeLog")
    Observable<RespuestaMensajeLog> getMensajeLog(@Body PeticionMensajeLog peticionMensajeLog);

//    @POST("Sirius_Descarga")
//    Observable<RespuestaCarga> generarArchivosXmlDto(@Body PeticionDescarga peticionDescarga);
}