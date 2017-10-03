package com.example.santiagolopezgarcia.talleres.presenters;

import com.example.dominio.bussinesslogic.acceso.PermisoBL;
import com.example.dominio.bussinesslogic.administracion.TalleresBL;
import com.example.dominio.bussinesslogic.administracion.UsuarioBL;
import com.example.dominio.bussinesslogic.correria.CorreriaBL;
import com.example.dominio.modelonegocio.Correria;
import com.example.dominio.modelonegocio.Talleres;
import com.example.dominio.modelonegocio.Usuario;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.view.interfaces.LoginView;
import com.example.utilidades.helpers.DateHelper;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class LoginPresenter extends Presenter<LoginView> {

    private UsuarioBL usuarioBL;
    private CorreriaBL correriaBL;
    private Correria correriaActual;
    private Usuario usuario;
    private boolean loginCodigoBarras;
    private PermisoBL permisoBL;
    private List<Correria> listaCorrerias;
    private TalleresBL talleresBL;
    private Talleres talleres;
    private static final String TIPO_IDENTIFICACION_AMBOS = "A";

    public Talleres getTalleres() {
        return talleres;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Correria getCorreriaActual() {
        return correriaActual;
    }

    public void setCorreriaActual(Correria correriaActual) {
        this.correriaActual = correriaActual;
    }

    public void setLoginCodigoBarras(boolean loginCodigoBarras) {
        this.loginCodigoBarras = loginCodigoBarras;
    }

    public String getTipoIdentificacion() {
        return this.talleres.getTipoIdentificacion();
    }

    @Inject
    public LoginPresenter(UsuarioBL usuarioBL,
                          CorreriaBL correriaBL,
                          PermisoBL permisoBL,
                          TalleresBL talleresBL) {
        this.usuarioBL = usuarioBL;
        this.correriaBL = correriaBL;
        this.permisoBL = permisoBL;
        this.talleresBL = talleresBL;
    }

    @Override
    public void iniciar() {
        listaCorrerias = correriaBL.cargar();
        talleres = talleresBL.cargarPrimerRegistro();
        vista.mostrarNumeroTerminal(talleres.getNumeroTerminal());
        if (listaCorrerias.size() > 0)
            vista.mostrarListaCorrerias(listaCorrerias);
        vista.ocultarBarraProgreso();
        validarNumeroTerminal();
        validarTipoIdentificacion();
    }

    private void validarTipoIdentificacion() {
        vista.bloquearTipoIdentificacionDigitado(getTipoIdentificacion().equals(TIPO_IDENTIFICACION_AMBOS)
                || getTipoIdentificacion().isEmpty());
    }

    private void validarNumeroTerminal() {
        if (talleresBL.cargarPrimerRegistro().getNumeroTerminal().trim().isEmpty()) {
            vista.mostrarPopUpNumeroTerminal();
        }
    }

    @Override
    public void detener() {
        if (listaCorrerias != null) {
            listaCorrerias.clear();
            listaCorrerias = null;
        }
    }

    public void actualizarDatos() {
        List<Correria> listaCorrerias = correriaBL.cargar();
        vista.mostrarListaCorrerias(listaCorrerias);
        vista.mostrarNumeroTerminal(talleres.getNumeroTerminal());
    }

    private boolean validarCredenciales(String codigoUsuario, String clave) {
        if (codigoUsuario.isEmpty() && clave.isEmpty()) {
            vista.mostrarMensajeError(vista.getContext().getString(R.string.mensaje_debe_ingresar_indetificacion_y_clave));
            vista.limpiarControlesEdicion();
            return false;
        }
        if (codigoUsuario.isEmpty()) {
            vista.mostrarMensajeError(vista.getContext().getString(R.string.mensaje_debe_ingresar_identificacion));
            vista.limpiarControlesEdicion();
            return false;
        }
        if (clave.isEmpty()) {
            vista.mostrarMensajeError(vista.getContext().getString(R.string.mensaje_debe_ingresar_clave));
            vista.limpiarControlesEdicion();
            return false;
        }
        return true;
    }

    public boolean validarUsuario() {
        if (usuario.esUsuarioValido()) {
            if (usuario.getFechaIngreso() == null) {
                vista.mostrarCambioClave(usuario);
                return false;
            } else {
                return true;
            }
        }
        vista.mostrarMensajeError(vista.getContext().getString(R.string.mensaje_identificacion_incorrecta));
        vista.limpiarControlesEdicion();
        return false;
    }

    private boolean validarCredenciales() {
        if (usuario.validarClave(vista.obtenerClave())) {
            return true;
        }
        vista.mostrarMensajeError(vista.getContext().getResources().getString(R.string.clave_incorrecta));
        vista.limpiarControlesEdicion();
        return false;
    }

    private String obtenerUsuarioSinDigitoVerificacion(String codigoUsurio) {
        if (loginCodigoBarras) {
            return usuarioBL.obtenerIdentificacion(codigoUsurio);
        }
        return codigoUsurio.substring(0, codigoUsurio.length() - 1);
    }

    public void validarAcceso() {
        String identificacion;
        String digitoChequeo;
        String digitoChequeoIngresado = "";
        if (correriaActual == null) {
            vista.mostrarMensajeError(vista.getContext().getString(R.string.mensaje_debe_selecciona_una_correria));
            return;
        }
        identificacion = vista.obtenerUsuario();
        if (identificacion.length() > 1)
            digitoChequeoIngresado = identificacion.substring(identificacion.length() - 1, identificacion.length());

        identificacion = obtenerUsuarioSinDigitoVerificacion(identificacion);
        usuario = usuarioBL.cargarUsuario(identificacion);
        if (usuario.esUsuarioValido() && usuario.getClave().isEmpty()) {
            digitoChequeo = traerDigitoChequeo(usuario.getCodigoUsuario());
            if (digitoChequeo.equals(digitoChequeoIngresado) || loginCodigoBarras) {
                vista.mostrarCambioClave(usuario);
            } else {
                vista.mostrarMensajeError(vista.getContext().getString(R.string.mensaje_identificacion_incorrecta));
                vista.limpiarControlesEdicion();
            }
        } else {
            if (validarCredenciales(identificacion, vista.obtenerClave())) {
                if (!usuario.getCodigoUsuario().isEmpty()) {
                    digitoChequeo = traerDigitoChequeo(usuario.getCodigoUsuario());

                    if (digitoChequeo.equals(digitoChequeoIngresado) || loginCodigoBarras) {
                        if (validarUsuario()) {
                            if (validarCredenciales()) {
                                if (usuario.fechaIngresoValida()) {
                                    try {
                                        if (correriaActual.getCodigoContrato().equals(usuario.getContrato().getCodigoContrato())) {
                                            if (!correriaActual.getFechaProgramacion().isEmpty()) {
                                                if (DateHelper.convertirStringADate(correriaActual.getFechaProgramacion(), DateHelper.TipoFormato.yyyyMMddTHHmmss).after(new Date())) {
                                                    vista.mostrarMensajeError(vista.getContext().getResources()
                                                            .getString(R.string.fecha_programacion_correria) + " " +
                                                            DateHelper.convertirDateAString(DateHelper.convertirStringADate(correriaActual.getFechaProgramacion(), DateHelper.TipoFormato.yyyyMMddTHHmmss), DateHelper.TipoFormato.ddMMyyyy));
                                                    return;
                                                } else {
                                                    usuario.setPerfil(permisoBL.cargarPermisos(usuario.getPerfil().getCodigoPerfil()));
                                                    vista.mostrarCorrerias();
                                                }
                                            }
                                            usuario.setPerfil(permisoBL.cargarPermisos(usuario.getPerfil().getCodigoPerfil()));
                                            vista.mostrarCorrerias();
                                        } else {
                                            vista.mostrarMensajeError(vista.getContext().getResources()
                                                    .getString(R.string.contrato_correria_login));

                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    vista.mostrarCambioClave(usuario);
                                }
                            }
                        }
                    } else {

                        vista.mostrarMensajeError(vista.getContext().getString(R.string.mensaje_identificacion_clave_son_incorrectos));
                        vista.limpiarControlesEdicion();
                    }
                } else {
                    vista.mostrarMensajeError(vista.getContext().getString(R.string.usuario_invalido));
                    vista.limpiarControlesEdicion();
                }
            }
        }
    }

    private String traerDigitoChequeo(String identificacion) {
        String digitoChequeo = "";
        if (!identificacion.isEmpty()) {
            digitoChequeo = usuarioBL.traerDigitoChequeo(identificacion);
        }
        return digitoChequeo;
    }

    public boolean getLoginCodigoBarras() {
        return loginCodigoBarras;
    }

}
