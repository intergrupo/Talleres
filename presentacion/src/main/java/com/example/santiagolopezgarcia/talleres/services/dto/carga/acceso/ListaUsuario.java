package com.example.santiagolopezgarcia.talleres.services.dto.carga.acceso;

import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.example.utilidades.helpers.DateHelper;
import com.google.common.collect.Lists;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_USUARIOS")
public class ListaUsuario implements BaseListaDto<com.example.dominio.modelonegocio.Usuario> {

    @ElementList(inline = true)
    public List<Usuario> Sirius_Usuario;

    @Override
    public List<com.example.dominio.modelonegocio.Usuario> convertirListaDtoAListaDominio() {
        List<com.example.dominio.modelonegocio.Usuario> listaUsuarios = new ArrayList<>(Lists.transform(this.Sirius_Usuario, usuariolXML ->
                {
                    com.example.dominio.modelonegocio.Usuario usuario = new com.example.dominio.modelonegocio.Usuario();
                    usuario.setNombre(usuariolXML.Nombre);
                    usuario.getContrato().setCodigoContrato(usuariolXML.CodigoContrato);

                    usuario.setCodigoUsuario(usuariolXML.CodigoUsuario);
                    try {
                        usuario.setFechaIngreso(DateHelper.convertirStringADate(usuariolXML.FechaIngreso, DateHelper.TipoFormato.yyyyMMddTHHmmss));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    usuario.getPerfil().setCodigoPerfil(usuariolXML.CodigoPerfil);
                    usuario.setCodigoUsuarioIngreso("Nuevo");
                    return usuario;
                }
        ));
        return listaUsuarios;
    }

    @Override
    public int getLongitudLista() {
        int longitud = 0;
        if (this.Sirius_Usuario != null) {
            longitud = this.Sirius_Usuario.size();
        }
        return longitud;
    }

    @Override
    public String getOperacion() {
        String operacion = "R";
        if (this.getLongitudLista() > 0) {
            if (this.Sirius_Usuario.get(0).Operacion != null && this.Sirius_Usuario.get(0).Operacion != "") {
                operacion = this.Sirius_Usuario.get(0).Operacion;
            }
        }
        return operacion;
    }
}