package com.example.santiagolopezgarcia.talleres.presenters;

import com.example.dominio.bussinesslogic.administracion.TalleresBL;
import com.example.dominio.modelonegocio.Talleres;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IAdministracionConfiguracionView;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class ConfiguracionPresenter extends Presenter<IAdministracionConfiguracionView> {

    private Talleres talleres;
    private TalleresBL talleresBL;
    SiriusApp app;

    public Talleres getTalleres() {
        return talleres;
    }

    public void setSirius(Talleres sirius) {
        this.talleres = sirius;
    }

    @Inject
    public ConfiguracionPresenter(TalleresBL siriusBL) {
        this.talleresBL = siriusBL;
    }

    @Override
    public void iniciar() {

    }

    public void procesarSirius(){
        if (!talleres.getNumeroTerminal().isEmpty()) {
            talleresBL.actualizar(talleres);
        } else {
            talleresBL.guardar(talleres);
        }
    }

    public void guardarImpresora(String impresora) {

        talleres = this.talleresBL.consultarTerminalXCodigo(this.app.getCodigoTerminal());
        if (talleres != null) {
            talleres.setImpresora(impresora);
            switch (talleres.getImpresora()){
                case "Zebra ZQ520":
                    talleres.setDireccionImpresora("AC3FA447BA9B");
                    break;
                case "Sin impresora":
                    talleres.setDireccionImpresora("");
                    break;
            }
            talleresBL.actualizar(talleres);
        }


    }

    @Override
    public void detener() {

    }

    public void setApp(SiriusApp app) {
        this.app = app;
    }

}
