package com.example.dominio.administracion;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.modelonegocio.Usuario;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class UsuarioBL implements LogicaNegocioBase<Usuario> {

    final String CODIGOINICIALENCODIGOBARRAS = "907707173981";
    final int LONGITUDCODIGOUSUARIO = 12;
    final int LONGITUDCODIGOBARRAS = 48;

    private UsuarioRepositorio usuarioRepositorio;
    private ContratoBL contratoBL;
    private PerfilBL perfilBL;

    public UsuarioBL(UsuarioRepositorio usuarioRepositorio, ContratoBL contratoBL, PerfilBL perfilBL) {
        this.contratoBL = contratoBL;
        this.perfilBL = perfilBL;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public boolean crear(Usuario usuario) {
        try {
            return usuarioRepositorio.guardar(usuario);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean guardar(List<Usuario> listaUsuarios) {
        try {
            return usuarioRepositorio.guardar(listaUsuarios);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean guardar(Usuario usuario) {
        try {
            return usuarioRepositorio.guardar(usuario);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarUsuario(Usuario usuario) {
        return usuarioRepositorio.eliminar(usuario);
    }

    public List<Usuario> cargarUsuariosXContrato(String codigoContrato) {
        List<Usuario> listaUsuarios = usuarioRepositorio.cargarXContrato(codigoContrato);
        for (Usuario usuario : listaUsuarios) {
            usuario.setContrato(contratoBL.cargarContratoXCodigo(usuario.getContrato().getCodigoContrato()));
            usuario.setPerfil(perfilBL.cargarPerfilXCodigo(usuario.getPerfil().getCodigoPerfil()));
        }
        return listaUsuarios;
    }

    public List<Usuario> cargarUsuarios() {
        List<Usuario> listaUsuarios = usuarioRepositorio.cargar();
        for (Usuario usuario : listaUsuarios) {
            usuario.setContrato(contratoBL.cargarContratoXCodigo(usuario.getContrato().getCodigoContrato()));
            usuario.setPerfil(perfilBL.cargarPerfilXCodigo(usuario.getPerfil().getCodigoPerfil()));
        }
        return listaUsuarios;
    }

    public boolean tieneRegistros() {
        return usuarioRepositorio.tieneRegistros();
    }

    public Usuario cargarUsuario(String codigoUsuario) {
        try {
            return usuarioRepositorio.obtener(codigoUsuario);
        } catch (ParseException e) {
            return new Usuario();
        }
    }

    public boolean actualizar(Usuario usuario) {
        return usuarioRepositorio.actualizar(usuario);
    }

    public String obtenerCodigoUsuarioDeCodigoBarras(String codigoBarras, String tipoLectura) {
        String codigoUsuario = codigoBarras;
        switch (tipoLectura) {
            case "j":
                if (codigoBarras.contains(CODIGOINICIALENCODIGOBARRAS)) {
                    codigoUsuario = codigoBarras.replace(CODIGOINICIALENCODIGOBARRAS, "");
                    if (codigoBarras.length() < LONGITUDCODIGOBARRAS) {

                        codigoUsuario = codigoUsuario.substring(0, codigoUsuario.length());
                    } else {
                        codigoUsuario = codigoUsuario.substring(0, LONGITUDCODIGOUSUARIO);
                    }
                }
                break;
        }
        return codigoUsuario.trim();
    }

    public String obtenerIdentificacion(String identificacion) {
        identificacion = identificacion.replace(CODIGOINICIALENCODIGOBARRAS, "");
        identificacion = identificacion.substring(0, identificacion.length() - 1);
        return identificacion;
    }

    public String traerDigitoChequeo(String identificacion) {
        int i = 0;
        int nCheckDigit = 0;
        String sIdUsuario = identificacion;
        String digitoChequeo = null;
        int valor = 0;
        char c;
        boolean factorDePeso = true;
        int nLen = sIdUsuario.length();

        nCheckDigit = 0;
        for (i = 0; i < nLen; i++) {

            c = sIdUsuario.toCharArray()[i];
            valor = Integer.parseInt(String.valueOf(c));
            if (factorDePeso) {
                nCheckDigit += 3 * valor;
                factorDePeso = false;
            } else {
                nCheckDigit += valor;
                factorDePeso = true;
            }
        }
        digitoChequeo = String.valueOf(nCheckDigit);

        digitoChequeo = digitoChequeo.substring(digitoChequeo.length() - 1, digitoChequeo.length());
        digitoChequeo = String.valueOf(10 - Integer.parseInt(digitoChequeo));
        if (digitoChequeo.equals("10")) {
            digitoChequeo = "0";
        }
        return digitoChequeo;
    }

    @Override
    public boolean procesar(List<Usuario> listaDatos, String operacion) {
        Usuario usuarioBD;
        boolean respuesta = false;
        for (Usuario usuario : listaDatos) {
            switch (operacion) {
                case "A":
                    usuarioBD = cargarUsuario(usuario.getCodigoUsuario());
                    if (!usuarioBD.esUsuarioValido()) {
                        respuesta = guardar(usuario);
                    }
                    break;
                case "U":
                    usuarioBD = cargarUsuario(usuario.getCodigoUsuario());
                    if (usuarioBD.esUsuarioValido()) {
                        if (!usuarioBD.getCodigoUsuarioIngreso().isEmpty()) {
                            usuario.setCodigoUsuarioIngreso(usuarioBD.getCodigoUsuarioIngreso());
                        }
                        respuesta = actualizar(usuario);
                    }
                    break;
                case "D":
                    respuesta = eliminarUsuario(usuario);
                    break;
                case "R":
                    usuarioBD = cargarUsuario(usuario.getCodigoUsuario());
                    if (usuarioBD.esUsuarioValido()) {
                        if (!usuarioBD.getCodigoUsuarioIngreso().toUpperCase().isEmpty()) {
                            usuario.setCodigoUsuarioIngreso(usuarioBD.getCodigoUsuarioIngreso());
                        }
                        respuesta = actualizar(usuario);
                    } else {
                        respuesta = guardar(usuario);
                    }
                    break;
                default:
                    respuesta = guardar(usuario);
                    break;
            }
        }
        return respuesta;
    }

    public boolean modificarDespuesDeCarga() {
        return this.usuarioRepositorio.modificarDespuesDeCarga();
    }
}

