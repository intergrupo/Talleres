package com.example.santiagolopezgarcia.talleres.presenters;

import com.example.dominio.bussinesslogic.administracion.TalleresBL;
import com.example.dominio.bussinesslogic.correria.CorreriaBL;
import com.example.dominio.modelonegocio.Opcion;
import com.example.dominio.modelonegocio.ParametrosOpcionAdministracion;
import com.example.dominio.modelonegocio.Talleres;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IAdministracionView;
import com.example.santiagolopezgarcia.talleres.view.popups.AlertaPopUp;
import com.example.utilidades.helpers.WifiHelper;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class AdministracionPresenter extends Presenter<IAdministracionView> {
    private final String NOMBREHILOCARGADATOS = "HiloCargaDatosAdministracionPresenter";

    private TalleresBL talleresBL;
    private WifiHelper wifiHelper;
    private CorreriaBL correriaBL;
    private Opcion opcion;
    private ParametrosOpcionAdministracion parametrosOpcionAdministracion;

    @Inject
    public AdministracionPresenter(TalleresBL talleresBL, CorreriaBL correriaBL) {
        this.talleresBL = talleresBL;
        this.correriaBL = correriaBL;
    }

    @Override
    public void iniciar() {
        wifiHelper = new WifiHelper(vista.getContext());
        vista.mostrarBarraProgreso();
        Thread thread = new Thread(() -> {
        });
        thread.setName(NOMBREHILOCARGADATOS);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    public ParametrosOpcionAdministracion getParametrosOpcionAdministracion() {
        return parametrosOpcionAdministracion;
    }

    public void setParametrosOpcionAdministracion(ParametrosOpcionAdministracion parametrosOpcionAdministracion) {
        this.parametrosOpcionAdministracion = parametrosOpcionAdministracion;
    }

    public Opcion getOpcion() {
        return opcion;
    }

    public void setOpcion(Opcion opcion) {
        this.opcion = opcion;
    }

    public Talleres getTalleres() {
        return talleresBL.cargarPrimerRegistro();
    }

    public Talleres consultarTerminalXCodigo(String terminal){
        return talleresBL.consultarTerminalXCodigo(terminal);
    }

    @Override
    public void detener() {

    }

    /*public void configuracionWifi() {
        if (getTalleres().getWifi().equals("S")) {
            wifiHelper.abrirConfiguracionWifi();
        } else if (getTalleres().getWifi().equals("N")) {
            vista.mostrarMensaje(vista.getContext().getResources()
                    .getString(R.string.configurar_wifi));
        }
    }*/

    public void borrarMaestros() {
        if (correriaBL.cargar().size() > 0) {
            mostrarAlertaCorreriasMaestros();
        } else {
            mostrarAlertaMaestros();
        }
    }

    private void mostrarAlertaMaestros() {
        AlertaPopUp alertaPopUp = new AlertaPopUp();
        alertaPopUp.setTitle(R.string.titulo_maestros);
        alertaPopUp.setMessage(R.string.alerta_maestros);
        alertaPopUp.setContext(vista.getContext());
        alertaPopUp.setPositiveButton("SI", (dialog, id) -> {
            if (talleresBL.borrarBD()) {
                vista.actualizarMaestrosVista();
            }
            dialog.dismiss();

        });
        alertaPopUp.setNegativeButton("NO", (dialog, id) -> {
            dialog.dismiss();

        });

        alertaPopUp.show();

    }

    private void mostrarAlertaCorreriasMaestros() {
        AlertaPopUp alertaPopUp = new AlertaPopUp();
        alertaPopUp.setTitle(R.string.titulo_correria_maestros);
        alertaPopUp.setMessage(R.string.alerta_correria_maestros);
        alertaPopUp.setContext(vista.getContext());
        alertaPopUp.setPositiveButton("ACEPTAR", (dialog, id) -> {
            dialog.dismiss();

        });
        alertaPopUp.show();
    }

    public void mostrarVentadaCambiarEstadoLog() {
        AlertaPopUp alertaPopUp = new AlertaPopUp();
        alertaPopUp.setTitle(R.string.titulo_cambiar_log);
        if (talleresBL.cargarPrimerRegistro().getLog().toUpperCase().equals("S"))
            alertaPopUp.setMessage(R.string.alerta_log_habilitado);
        else
            alertaPopUp.setMessage(R.string.alerta_log_deshabilitado);
        alertaPopUp.setContext(vista.getContext());
        alertaPopUp.setPositiveButton("SI", (dialog, id) -> {
            if (talleresBL.cargarPrimerRegistro().getLog().toUpperCase().equals("S"))
                deshabilitarLog();
            else
                habilitarLog();
            dialog.dismiss();
        });
        alertaPopUp.setNegativeButton("NO", (dialog, id) -> {
            dialog.dismiss();
        });
        alertaPopUp.show();
    }

    private void deshabilitarLog() {
        Talleres sirius = talleresBL.cargarPrimerRegistro();
        sirius.setLog("N");
        talleresBL.actualizar(sirius);
    }

    private void habilitarLog() {
        Talleres sirius = talleresBL.cargarPrimerRegistro();
        sirius.setLog("S");
        talleresBL.actualizar(sirius);
    }
}