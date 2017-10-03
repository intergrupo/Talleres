package com.example.santiagolopezgarcia.talleres.presenters;

import com.example.dominio.bussinesslogic.correria.CorreriaBL;
import com.example.dominio.modelonegocio.Correria;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.view.interfaces.BaseView;
import com.example.utilidades.helpers.DateHelper;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class CambioCorreriaPresenter extends Presenter<BaseView> {

    private CorreriaBL correriaBL;
    private Correria correriaActiva;

    public Correria getCorreriaActiva() {
        return correriaActiva;
    }

    public void setCorreriaActiva(Correria correriaActiva) {
        this.correriaActiva = correriaActiva;
    }

    @Inject
    public CambioCorreriaPresenter(CorreriaBL correriaBL) {
        this.correriaBL = correriaBL;
    }


    @Override
    public void iniciar() {

    }

    @Override
    public void detener() {

    }

    public void firmarCorreria() {
        try {
            correriaActiva = correriaBL.cargar(correriaActiva.getCodigoCorreria());
            correriaActiva.setFechaFinJornada(DateHelper.convertirDateAString(new Date(), DateHelper.TipoFormato.yyyyMMddTHHmmss));
            correriaBL.actualizar(correriaActiva);
        } catch (ParseException e) {
            e.printStackTrace();
            try {
                vista.registrarLog(e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public boolean sePuedeIniciarCorreria(Correria correriaCambio) {
        try {
            if (!correriaCambio.getFechaProgramacion().isEmpty()) {
                if (DateHelper.convertirStringADate(correriaCambio.getFechaProgramacion(),
                        DateHelper.TipoFormato.yyyyMMddTHHmmss).after(new Date())) {
                    vista.mostrarMensajeError(vista.getContext().getResources()
                            .getString(R.string.fecha_programacion_correria) + " " +
                            DateHelper.convertirDateAString(DateHelper.convertirStringADate(
                                    correriaCambio.getFechaProgramacion(), DateHelper.TipoFormato.yyyyMMddTHHmmss),
                                    DateHelper.TipoFormato.ddMMyyyy));
                    return false;
                }
            }
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
