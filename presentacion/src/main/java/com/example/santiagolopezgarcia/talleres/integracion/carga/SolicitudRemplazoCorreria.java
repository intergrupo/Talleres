package com.example.santiagolopezgarcia.talleres.integracion.carga;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class SolicitudRemplazoCorreria {

    private String mensajeConfirmacion;

    public List<RespuestaRemplazoCorreria> respuestaCorreria;
    private List<RespuestaRemplazoCorreria> listaCorreriaAOmitirRemplzao;

    public SolicitudRemplazoCorreria() {
        this.mensajeConfirmacion = "¿Desea recargar la correria %s?";
        this.respuestaCorreria = new ArrayList<>();
        this.listaCorreriaAOmitirRemplzao = new ArrayList<>();
    }

    public String getMensajeConfirmacion(int indice){
        String codigoCorreria=this.respuestaCorreria.get(indice).getCodigoCorreria();
        return String.format(this.mensajeConfirmacion, codigoCorreria);
    }

    public void adicionarCorreria(String codigoCorreria) {
        this.respuestaCorreria.add(new RespuestaRemplazoCorreria(codigoCorreria));
    }

    public boolean esNecesarioSolicitarRemplazo() {
        return this.respuestaCorreria.size() > 0;
    }

    public int cantidadCorrerias() {
        return this.respuestaCorreria.size();
    }

    public void establecerRespuesta(int indiceCorreria, boolean remplazar) {
        if (!remplazar) {
            this.listaCorreriaAOmitirRemplzao.add(this.respuestaCorreria.get(indiceCorreria).clonar());
        }
    }

    public List<String> getCodigosCorreriaAOmitirDeRemplazo() {
        return new ArrayList<>(Lists.transform(this.listaCorreriaAOmitirRemplzao, correriaOmitirRemplazo -> {
            return correriaOmitirRemplazo.getCodigoCorreria();
        }));
    }

    public class RespuestaRemplazoCorreria {
        private String codigoCorreria;

        public RespuestaRemplazoCorreria(String codigoCorreria) {
            this.codigoCorreria = codigoCorreria;
        }

        public String getCodigoCorreria() {
            return codigoCorreria;
        }

        public RespuestaRemplazoCorreria clonar() {
            return new RespuestaRemplazoCorreria(this.getCodigoCorreria());
        }
    }
}
