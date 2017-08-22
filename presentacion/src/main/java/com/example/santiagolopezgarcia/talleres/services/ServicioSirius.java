package com.example.santiagolopezgarcia.talleres.services;

import android.content.Context;

import com.example.santiagolopezgarcia.talleres.helpers.Constantes;
import com.example.santiagolopezgarcia.talleres.services.contracts.IServicioSirius;
import com.example.santiagolopezgarcia.talleres.services.contracts.Suscriptor;
import com.example.santiagolopezgarcia.talleres.services.dto.PeticionCarga;
import com.example.santiagolopezgarcia.talleres.services.dto.PeticionDescarga;
import com.example.santiagolopezgarcia.talleres.services.dto.PeticionMensajeLog;
import com.example.santiagolopezgarcia.talleres.services.dto.RespuestaCarga;
import com.example.santiagolopezgarcia.talleres.services.dto.RespuestaDescarga;
import com.example.santiagolopezgarcia.talleres.services.dto.RespuestaMensajeLog;
import com.example.utilidades.Log;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.UUID;

import javax.net.ssl.SSLException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.santiagolopezgarcia.talleres.integracion.ComunicacionCarga.CODIGO_PROGRAMA;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class ServicioSirius {

    private Suscriptor suscriptor;
    private IServicioSirius iServicioSirius;
    private FabricaServicios<IServicioSirius> fabricaServicios;
    private Context context;
    private String url;

    public ServicioSirius(Suscriptor suscriptor, Context context, String url) {
        this.context = context;
        this.url = url;
        fabricaServicios = new FabricaServicios<>();
        try {
            if (url != null && !url.isEmpty()) {
                iServicioSirius = fabricaServicios.getInstancia(IServicioSirius.class, context, url);
            } else {
                throw new IllegalArgumentException("La url para acceder al servicio no puede estar vacia");
            }
        } catch (NoSuchAlgorithmException e) {
            Log.error(e, "Error construyendo servicio de comunicación");
        } catch (KeyStoreException e) {
            Log.error(e, "Error construyendo servicio de comunicación");
        } catch (UnrecoverableKeyException e) {
            Log.error(e, "Error construyendo servicio de comunicación");
        } catch (KeyManagementException e) {
            Log.error(e, "Error construyendo servicio de comunicación");
        }
        this.suscriptor = suscriptor;
    }

    public void solicitudCarga(PeticionCarga peticionCarga) {
        if (suscriptor != null) {
            Observable<RespuestaCarga> respuesta = iServicioSirius.cargar(peticionCarga);
            respuesta


                    .subscribe(new Subscriber<RespuestaCarga>() {
                        @Override
                        public void onCompleted() {
                            suscriptor.onCompletetado();
                        }

                        @Override
                        public void onError(Throwable e) {
                            solicitudMensajeLog(crearPeticionMensajeLog(peticionCarga.CodTerminal,
                                    peticionCarga.IdServicio, "",
                                    e.getMessage(), Constantes.ESTADO_ERROR));
                            solicitudMensajeLog(crearPeticionMensajeLog(peticionCarga.CodTerminal,
                                    peticionCarga.IdServicio, Constantes.PROCESO_FIN_SESION,
                                    Constantes.MENSAJE_FIN_SESION, Constantes.ESTADO_ERROR));
                            try {
                                manejarError(e);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }

                        @Override
                        public void onNext(RespuestaCarga respuesta) {
                            suscriptor.onResultado(respuesta, peticionCarga.IdServicio);
                        }
                    });
        }
    }

    public void solicitudDescarga(PeticionDescarga peticionDescarga) {
        if (suscriptor != null) {
            Observable<RespuestaDescarga> respuesta = iServicioSirius.descargar(peticionDescarga);
            respuesta
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<RespuestaDescarga>() {
                        @Override
                        public void onCompleted() {
                            suscriptor.onCompletetado();
                        }

                        @Override
                        public void onError(Throwable e) {
                            try {
                                manejarError(e);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }

                        @Override
                        public void onNext(RespuestaDescarga respuesta) {
                            suscriptor.onResultado(respuesta, peticionDescarga.IdServicio);
                        }
                    });
        }
    }


    public void solicitudCargaAdjuntos(PeticionCarga peticionCarga) {
        if (suscriptor != null) {
            Observable<RespuestaCarga> respuesta = iServicioSirius.cargar(peticionCarga);
            respuesta
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<RespuestaCarga>() {
                        @Override
                        public void onCompleted() {
                            suscriptor.onCompletetado();
                        }

                        @Override
                        public void onError(Throwable e) {
                            try {
                                manejarError(e);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }

                        @Override
                        public void onNext(RespuestaCarga respuesta) {
                            suscriptor.onResultadoAdjuntos(respuesta);
                        }
                    });
        }
    }


    public void solicitudMensajeLog(PeticionMensajeLog peticionMensajeLog) {
        if (suscriptor != null) {
            Observable<RespuestaMensajeLog> respuesta = iServicioSirius.getMensajeLog(peticionMensajeLog);
            respuesta
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<RespuestaMensajeLog>() {
                        @Override
                        public void onCompleted() {
                            suscriptor.onCompletetado();
                        }

                        @Override
                        public void onError(Throwable e) {
//                            if (!(e instanceof UnknownHostException) && !(e instanceof ConnectException)) {
////                                solicitudMensajeLog(crearPeticionMensajeLog(peticionMensajeLog.CodTerminal,
////                                        peticionMensajeLog.IdServicio, "",
////                                        e.getMessage(), Constantes.ESTADO_ERROR));
//                                solicitudMensajeLog(crearPeticionMensajeLog(peticionMensajeLog.CodTerminal,
//                                        peticionMensajeLog.IdServicio, Constantes.PROCESO_FIN_SESION,
//                                        Constantes.MENSAJE_FIN_SESION, Constantes.ESTADO_ERROR));
//                            }
                            try {
                                manejarError(e);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }

                        @Override
                        public void onNext(RespuestaMensajeLog respuestaMensajeLog) {

                        }
                    });
        }
    }

    private void manejarError(Throwable e) throws IOException {
        Exception exception;
        if (e instanceof UnknownHostException) {
            exception = new UnknownHostException("No se pudo acceder al servicio desde la red" +
                    " que está conectado o la url está errada. (" + url + ")");
            exception.setStackTrace(e.getStackTrace());
            exception.addSuppressed(e);
            suscriptor.onError(exception);
        } else if (e instanceof SocketTimeoutException) {
            exception = new UnknownHostException("Verifique el estado de su red o válide que el servicio esté activo.");
            exception.setStackTrace(e.getStackTrace());
            exception.addSuppressed(e);
            suscriptor.onError(exception);
        } else if (e instanceof SocketException) {
            exception = new ConnectException("Verifique el estado de su red o válide que el servicio esté activo.");
            exception.setStackTrace(e.getStackTrace());
            exception.addSuppressed(e);
            suscriptor.onError(exception);
        } else if (e instanceof ConnectException) {
            exception = new ConnectException("Ocurrió un error conectandose a la red.");
            exception.setStackTrace(e.getStackTrace());
            exception.addSuppressed(e);
            suscriptor.onError(exception);
        } else if (e instanceof JsonSyntaxException) {
            exception = new ConnectException("Verifique la conectividad a la red.");
            exception.setStackTrace(e.getStackTrace());
            exception.addSuppressed(e);
            suscriptor.onError(exception);
        } else if (e instanceof SSLException) {
            exception = new ConnectException("Verifique la conectividad a la red y acceso al servicio.");
            exception.setStackTrace(e.getStackTrace());
            exception.addSuppressed(e);
            suscriptor.onError(exception);
        }  else if (e instanceof SSLException) {
            exception = new ConnectException("No se pudo generar el archivo de descarga, verifique la conectividad a la red.");
            exception.setStackTrace(e.getStackTrace());
            exception.addSuppressed(e);
            suscriptor.onError(exception);
        } else {
            suscriptor.onError(e);
        }
    }

    private PeticionMensajeLog crearPeticionMensajeLog(String numeroTerminal, String servicio, String idProceso,
                                                       String mensaje, String estado) {
        PeticionMensajeLog peticionMensajeLog = null;
        peticionMensajeLog = new PeticionMensajeLog();
        peticionMensajeLog.CodPrograma = CODIGO_PROGRAMA;
        peticionMensajeLog.CodTerminal = numeroTerminal;
        peticionMensajeLog.IdServicio = servicio;
        peticionMensajeLog.IdProceso = idProceso;
        peticionMensajeLog.Mensaje = mensaje;
        peticionMensajeLog.Sesion = UUID.randomUUID().toString();
        peticionMensajeLog.Estado = estado;
        return peticionMensajeLog;
    }

}