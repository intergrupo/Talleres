package com.example.santiagolopezgarcia.talleres.integracion.carga;

import com.example.dominio.modelonegocio.FiltroCarga;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class SolicitudRemplazoTarea {
    private String mensajeConfirmacion;
    private List<RespuestaRemplazoTarea> listaRespuesta;
    private List<RespuestaRemplazoTarea> listaTareasAOmitirDeRemplazo;
    private List<RespuestaRemplazoTarea> listaTareasARemplazar;


    public SolicitudRemplazoTarea() {
        this.mensajeConfirmacion = "Correria: %s. \nTrabajo: %s. \nTarea: %s. \nOrden: %s. \nFecha actual: %s. Reemplazar por: %s. \n¿Reemplazar?";
        ;
        this.listaRespuesta = new ArrayList<>();
        this.listaTareasAOmitirDeRemplazo = new ArrayList<>();
        this.listaTareasARemplazar = new ArrayList<>();
    }

    public String getMensajeConfirmacion(int indice) {
        String codigoCorreria = this.listaRespuesta.get(indice).getCodigoCorreria();
        String codigoOrdenTrabajo = this.listaRespuesta.get(indice).getCodigoOrdenTrabajo();
        String nombreTrabajo = this.listaRespuesta.get(indice).getNombreTrabajo();
        String nombreTarea = this.listaRespuesta.get(indice).getNombreTarea();
        String mensajeFechaBaseDatos = this.listaRespuesta.get(indice).getMensajeFechaBaseDatos();
        String mensajeFechaDto = this.listaRespuesta.get(indice).getMensajeFechaDto();

        return String.format(this.mensajeConfirmacion
                , codigoCorreria, nombreTrabajo, nombreTarea
                , codigoOrdenTrabajo, mensajeFechaBaseDatos, mensajeFechaDto);
    }

    public boolean esNecesarioSolicitarRemplazo() {
        return this.listaRespuesta.size() > 0;
    }

    public int cantidadTareas() {
        return this.listaRespuesta.size();
    }

    public void adicionarTarea(String codigoCorreria
            , String codigoOrdenTrabajo
            , String codigoTrabajo
            , String nombreTrabajo
            , String codigoTarea
            , String nombreTarea
            , String mensajeFechaBaseDatos
            , String mensajeFechaDto) {
        RespuestaRemplazoTarea respuestaRemplazoTarea = new RespuestaRemplazoTarea(codigoCorreria, codigoOrdenTrabajo
                , codigoTrabajo, codigoTarea);

        respuestaRemplazoTarea.setNombreTrabajo(nombreTrabajo);
        respuestaRemplazoTarea.setNombreTarea(nombreTarea);

        respuestaRemplazoTarea.setMensajeFechaBaseDatos(mensajeFechaBaseDatos);
        respuestaRemplazoTarea.setMensajeFechaDto(mensajeFechaDto);

        this.listaRespuesta.add(respuestaRemplazoTarea);
    }

    public void establecerRespuesta(int indiceTarea, boolean remplazarTarea) {
        if (!remplazarTarea) {
            this.listaTareasAOmitirDeRemplazo.add(this.listaRespuesta.get(indiceTarea).clonar());
        } else {
            this.listaTareasARemplazar.add(this.listaRespuesta.get(indiceTarea).clonar());
        }
    }

    public void adicionarTareaAOmitirDeRemplazo(String codigoCorreria
            , String codigoOrdenTrabajo
            , String codigoTrabajo
            , String codigoTarea) {
        this.listaTareasAOmitirDeRemplazo.add(new RespuestaRemplazoTarea(codigoCorreria, codigoOrdenTrabajo
                , codigoTrabajo, codigoTarea));
    }

    public List<String> getCodigoOtTareaAOmitirDeRemplazo() {
        return new ArrayList<>(Lists.transform(this.listaTareasAOmitirDeRemplazo, tareasAOmitirDeRemplazo -> {
                    String codigoCorreriaOtTarea = tareasAOmitirDeRemplazo.getCodigoCorreria()
                            + tareasAOmitirDeRemplazo.getCodigoOrdenTrabajo()
                            + tareasAOmitirDeRemplazo.getCodigoTarea();
                    return codigoCorreriaOtTarea;
                }
        ));
    }

    public List<FiltroCarga> getCodigosOtTrabajoTareaARemplazar() {
        return new ArrayList<>(Lists.transform(this.listaTareasARemplazar, tareas -> {
            return new FiltroCarga(tareas.getCodigoCorreria(), tareas.getCodigoOrdenTrabajo()
                    , tareas.getCodigoTrabajo(), tareas.getCodigoTarea());
        }));
    }

    public List<String> getCodigoOtTrabajoTareaAOmitirDeRemplazo() {
        return new ArrayList<>(Lists.transform(this.listaTareasAOmitirDeRemplazo, tareasAOmitirDeRemplazo -> {
                    String codigoCorreriaOtTrabajoTarea = tareasAOmitirDeRemplazo.getCodigoCorreria()
                            + tareasAOmitirDeRemplazo.getCodigoOrdenTrabajo()
                            + tareasAOmitirDeRemplazo.getCodigoTrabajo()
                            + tareasAOmitirDeRemplazo.getCodigoTarea();
                    return codigoCorreriaOtTrabajoTarea;
                }
        ));
    }


    private class RespuestaRemplazoTarea {
        private String codigoCorreria;
        private String codigoOrdenTrabajo;
        private String codigoTrabajo;
        private String nombreTrabajo;
        private String codigoTarea;
        private String nombreTarea;
        private String mensajeFechaBaseDatos;
        private String mensajeFechaDto;


        public RespuestaRemplazoTarea(String codigoCorreria
                , String codigoOrdenTrabajo
                , String codigoTrabajo
                , String codigoTarea) {
            this.codigoCorreria = codigoCorreria;
            this.codigoOrdenTrabajo = codigoOrdenTrabajo;
            this.codigoTrabajo = codigoTrabajo;
            this.codigoTarea = codigoTarea;
        }

        public String getCodigoCorreria() {
            return codigoCorreria;
        }

        public String getCodigoOrdenTrabajo() {
            return codigoOrdenTrabajo;
        }

        public String getCodigoTrabajo() {
            return codigoTrabajo;
        }

        public String getCodigoTarea() {
            return codigoTarea;
        }

        public String getNombreTrabajo() {
            return nombreTrabajo;
        }

        public String getNombreTarea() {
            return nombreTarea;
        }

        public void setNombreTarea(String nombreTarea) {
            this.nombreTarea = nombreTarea;
        }

        public void setNombreTrabajo(String nombreTrabajo) {
            this.nombreTrabajo = nombreTrabajo;
        }

        public RespuestaRemplazoTarea clonar() {
            RespuestaRemplazoTarea respuestaRemplazoTarea = new
                    RespuestaRemplazoTarea(
                    this.getCodigoCorreria(), this.getCodigoOrdenTrabajo()
                    , this.getCodigoTrabajo(), this.getCodigoTarea()
            );

            respuestaRemplazoTarea.setMensajeFechaDto(this.getMensajeFechaDto());
            respuestaRemplazoTarea.setMensajeFechaBaseDatos(this.getMensajeFechaBaseDatos());

            return respuestaRemplazoTarea;
        }

        public String getMensajeFechaBaseDatos() {
            return mensajeFechaBaseDatos;
        }

        public void setMensajeFechaBaseDatos(String mensajeFechaBaseDatos) {
            this.mensajeFechaBaseDatos = mensajeFechaBaseDatos;
        }

        public String getMensajeFechaDto() {
            return mensajeFechaDto;
        }

        public void setMensajeFechaDto(String mensajeFechaDto) {
            this.mensajeFechaDto = mensajeFechaDto;
        }
    }
}

