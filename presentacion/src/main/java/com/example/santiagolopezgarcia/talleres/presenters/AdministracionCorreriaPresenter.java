package com.example.santiagolopezgarcia.talleres.presenters;

import com.example.dominio.bussinesslogic.correria.CorreriaBL;
import com.example.dominio.modelonegocio.Correria;
import com.example.dominio.modelonegocio.Opcion;
import com.example.dominio.modelonegocio.ParametrosOpcionAdministracion;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IAdministracionCorreriaView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class AdministracionCorreriaPresenter extends Presenter<IAdministracionCorreriaView> {

    private final String NOMBREHILOCARGADATOS = "HiloCargaDatosAdministracionCorreriasPresenter";
    private CorreriaBL correriaBL;
    private List<Correria> listaCorrerias;
    private SiriusApp app;
    private Opcion opcion;
    private ParametrosOpcionAdministracion parametrosOpcionAdministracion;

    @Inject
    public AdministracionCorreriaPresenter(CorreriaBL correriaBL) {
        this.correriaBL = correriaBL;
    }

    private void consultarCorrerias(){
        if(app.getUsuario().esUsuarioValido()) {
            listaCorrerias = correriaBL.cargarXContratoYSinContrato(app.getUsuario().getContrato().getCodigoContrato());
        }else {
            listaCorrerias = correriaBL.cargar();
        }
        vista.mostrarListaCorrerias(listaCorrerias);
    }

    public void setApp(SiriusApp app) {
        this.app = app;
    }

    public SiriusApp getApp() {
        return app;
    }

    public Opcion getOpcion() {
        return opcion;
    }

    public void setOpcion(Opcion opcion) {
        this.opcion = opcion;
    }

    public ParametrosOpcionAdministracion getParametrosOpcionAdmistracion() {
        return parametrosOpcionAdministracion;
    }

    public void setParametrosOpcionAdmistracion(ParametrosOpcionAdministracion parametrosOpcionAdministracion) {
        this.parametrosOpcionAdministracion = parametrosOpcionAdministracion;
    }

    @Override
    public void iniciar() {
        vista.mostrarBarraProgreso();
        Thread thread = new Thread(() -> {
            consultarCorrerias();
        });
        thread.setName(NOMBREHILOCARGADATOS);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    @Override
    public void detener() {
        if(listaCorrerias != null)
        {
            listaCorrerias.clear();
        }
    }
}