package com.example.santiagolopezgarcia.talleres.di.componentes;

import com.example.santiagolopezgarcia.talleres.di.modulos.ModuloPresentacion;
import com.example.santiagolopezgarcia.talleres.di.scopes.PerActivity;
import com.example.santiagolopezgarcia.talleres.util.UnCaughtException;
import com.example.santiagolopezgarcia.talleres.view.activities.AdministracionActivity;
import com.example.santiagolopezgarcia.talleres.view.activities.CargaDatosActivity;
import com.example.santiagolopezgarcia.talleres.view.activities.DescargaDatosActivity;
import com.example.santiagolopezgarcia.talleres.view.activities.DescargaDatosParcialActivity;
import com.example.santiagolopezgarcia.talleres.view.activities.EnvioDirectoActivity;
import com.example.santiagolopezgarcia.talleres.view.activities.EnvioWebActivity;
import com.example.santiagolopezgarcia.talleres.view.activities.LoginActivity;
import com.example.santiagolopezgarcia.talleres.view.activities.MapaTareas;
import com.example.santiagolopezgarcia.talleres.view.activities.Parametrizacion2DActivity;
import com.example.santiagolopezgarcia.talleres.view.activities.PrincipalActivity;
import com.example.santiagolopezgarcia.talleres.view.activities.ReciboDirectoActivity;
import com.example.santiagolopezgarcia.talleres.view.activities.ReciboWebActivity;
import com.example.santiagolopezgarcia.talleres.view.adapters.CorreriaAdministracionAdapter;
import com.example.santiagolopezgarcia.talleres.view.adapters.OrdenTrabajoAdapter;
import com.example.santiagolopezgarcia.talleres.view.adapters.ResultadoNotificacionOTAdapter;
import com.example.santiagolopezgarcia.talleres.view.adapters.TareaXTrabajoOrdenTrabajoAdapter;
import com.example.santiagolopezgarcia.talleres.view.adapters.UsuarioAdapter;
import com.example.santiagolopezgarcia.talleres.view.fragments.AdministracionCorreriaFragment;
import com.example.santiagolopezgarcia.talleres.view.fragments.AdministracionUsuarioFragment;
import com.example.santiagolopezgarcia.talleres.view.fragments.CambioCorreriaFragment;
import com.example.santiagolopezgarcia.talleres.view.fragments.ConfiguracionFragment;
import com.example.santiagolopezgarcia.talleres.view.fragments.CorreriaFragment;
import com.example.santiagolopezgarcia.talleres.view.fragments.TotalesFragment;
import com.example.santiagolopezgarcia.talleres.view.popups.AlertaPopUp;
import com.example.santiagolopezgarcia.talleres.view.popups.BusquedaOrdenTrabajoPopup;
import com.example.santiagolopezgarcia.talleres.view.popups.CambiarRutaPopUp;
import com.example.santiagolopezgarcia.talleres.view.popups.CambioClavePopup;
import com.example.santiagolopezgarcia.talleres.view.popups.NuevoUsuarioPopUp;
import com.example.santiagolopezgarcia.talleres.view.popups.NumeroTerminalPopup;
import com.example.santiagolopezgarcia.talleres.view.popups.ResultadoNotificacionOTPopUp;
import com.example.santiagolopezgarcia.talleres.view.popups.SincronizacionPopUp;
import com.example.santiagolopezgarcia.talleres.view.popups.TerminalEnvioPopUp;

import dagger.Component;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */

@PerActivity
@Component(dependencies = ComponenteAplicacion.class, modules = ModuloPresentacion.class)
public interface ComponentePresentacion {

    void inject(LoginActivity loginActivity);

    void inject(PrincipalActivity correriaActivity);

    void inject(BusquedaOrdenTrabajoPopup busquedaOrdenTrabajoPopup);

    void inject(CorreriaFragment correriaFragment);

    void inject(TotalesFragment totalesFragment);

    void inject(TareaXTrabajoOrdenTrabajoAdapter totalesFragment);

    void inject(CambioCorreriaFragment loginFragment);

    void inject(OrdenTrabajoAdapter ordenTrabajoAdapter);

    void inject(CambioClavePopup cambioClavePopup);

    void inject(UsuarioAdapter usuarioAdapter);

    void inject(NuevoUsuarioPopUp nuevoUsuarioPopUp);

    void inject(AdministracionActivity administracionActivity);

    void inject(AdministracionUsuarioFragment administracionUsuarioFragment);

    void inject(AdministracionCorreriaFragment administracionCorreriaFragment);

    void inject(CorreriaAdministracionAdapter correriaAdministracionAdapter);

    void inject(CargaDatosActivity cargaDatosActivity);

    void inject(DescargaDatosActivity descargaDatosActivity);

    void inject(ConfiguracionFragment configuracionFragment);

    void inject(Parametrizacion2DActivity parametrizacion2DActivity);

    void inject(NumeroTerminalPopup numeroTerminalPopup);

    void inject(CambiarRutaPopUp cambiarRutaPopUp);

    void inject(MapaTareas mapaTareas);

    void inject(AlertaPopUp alertaPopUp);

    void inject(DescargaDatosParcialActivity descargaDatosParcial);

    void inject(EnvioDirectoActivity envioDirectoActivity);

    void inject(ReciboDirectoActivity reciboDirectoActivity);

    void inject(EnvioWebActivity envioWebActivity);

    void inject(ReciboWebActivity reciboWebActivity);

    void inject(TerminalEnvioPopUp terminalEnvioPopUp);

    void inject(ResultadoNotificacionOTPopUp resultadoNotificacionOTPopUp);

    void inject(ResultadoNotificacionOTAdapter resultadoNotificacionOTAdapter);

    void inject(SincronizacionPopUp sincronizacionPopUp);

    void inject(UnCaughtException unCaughtException);
}
