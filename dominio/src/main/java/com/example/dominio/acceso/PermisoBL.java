package com.example.dominio.acceso;

import com.example.dominio.administracion.OpcionBL;
import com.example.dominio.administracion.PerfilBL;
import com.example.dominio.administracion.PerfilXOpcionBL;
import com.example.dominio.modelonegocio.Opcion;
import com.example.dominio.modelonegocio.Perfil;
import com.example.dominio.modelonegocio.PerfilXOpcion;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class PermisoBL {

    private PerfilBL perfilBL;
    private OpcionBL opcionBL;
    private PerfilXOpcionBL perfilXOpcionBL;

    public PermisoBL(PerfilBL perfilBL, OpcionBL opcionBL, PerfilXOpcionBL perfilXOpcionBL) {
        this.perfilBL = perfilBL;
        this.opcionBL = opcionBL;
        this.perfilXOpcionBL = perfilXOpcionBL;
    }

    public Perfil cargarPermisos(String codigoPerfil) {
        Perfil perfil;
        perfil = perfilBL.cargarPerfilXCodigo(codigoPerfil);
        List<PerfilXOpcion> listaPerfilXOpcion = perfilXOpcionBL.cargarPerfilXCodigo(codigoPerfil);
        Opcion opcion;
        int identificador = 0;
        for (PerfilXOpcion perfilXOpcion : listaPerfilXOpcion) {
            opcion = opcionBL.cargarOpcionPorCodigo(perfilXOpcion.getOpcion().getCodigoOpcion());
            if (!opcion.getCodigoOpcion().isEmpty()) {
                opcion.setIdentificador(identificador);
                perfil.getListaOpciones().add(opcion);
                identificador++;
            }
        }
        return perfil;
    }
}