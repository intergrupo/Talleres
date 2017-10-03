package com.example.santiagolopezgarcia.talleres.integracion.carga;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.bussinesslogic.administracion.TalleresBL;
import com.example.santiagolopezgarcia.talleres.data.OperadorDatos;
import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.Carga;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.ListaCarga;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.administracion.ListaTalleres;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class CargaMaestros extends CargaBase {

    private DependenciaCargaMaestros dependenciaCargaMaestros;
    private String versionMaestros;

    @Override
    protected Carga obtenerDtoCarga() throws Exception {
        ListaCarga listaCarga = this.obtenerListaDto(ListaCarga.class, DependenciaCargaMaestros.CARGA, false);
        Carga carga = null;
        if (listaCarga != null) {
            carga = listaCarga.Carga.get(0);
        }
        return carga;
    }

    @Inject
    public CargaMaestros(
            TalleresBL talleresBL,
            DependenciaCargaMaestros dependenciaCargaMaestros) {
        super(talleresBL);
        this.dependenciaCargaMaestros = dependenciaCargaMaestros;
    }


    @Override
    protected int calcularOrdenEntidad(String nombre) {
        int posicion = -1;
        switch (nombre.toUpperCase()) {
            case DependenciaCargaMaestros.EMPRESA:
                posicion = 0;
                break;
            case DependenciaCargaMaestros.DEPARTAMENTO:
                posicion = 1;
                break;
            case DependenciaCargaMaestros.MUNICIPIO:
                posicion = 2;
                break;
            case DependenciaCargaMaestros.CONTRATO:
                posicion = 3;
                break;
            case DependenciaCargaMaestros.LABOR:
                posicion = 4;
                break;
            case DependenciaCargaMaestros.TAREA:
                posicion = 5;
                break;
            case DependenciaCargaMaestros.PERFIL:
                posicion = 6;
                break;
            case DependenciaCargaMaestros.TRABAJO:
                posicion = 7;
                break;
            case DependenciaCargaMaestros.ESTADO:
                posicion = 8;
                break;
            case DependenciaCargaMaestros.MULTIOPCION:
                posicion = 19;
                break;
            case DependenciaCargaMaestros.OPCION:
                posicion = 23;
                break;
            case DependenciaCargaMaestros.NOTIFICACION:
                posicion = 24;
                break;
            case DependenciaCargaMaestros.ITEM:
                posicion = 25;
                break;
            case DependenciaCargaMaestros.TRABAJO_X_CONTRATO:
                posicion = 26;
                break;
            case DependenciaCargaMaestros.TAREAXTRABAJO:
                posicion = 27;
                break;
            case DependenciaCargaMaestros.LABOR_X_TAREA:
                posicion = 30;
                break;
            case DependenciaCargaMaestros.PERFIL_X_OPCION:
                posicion = 33;
                break;
            case DependenciaCargaMaestros.ITEM_X_NOTIFICACION:
                posicion = 34;
                break;
            case DependenciaCargaMaestros.SIRIUS:
                posicion = 36;
                break;
            case DependenciaCargaMaestros.SECCION_IMPRESION:
                posicion = 37;
                break;
            case DependenciaCargaMaestros.PARRAFO_IMPRESION:
                posicion = 38;
                break;
            case DependenciaCargaMaestros.CARGA:
                posicion = 45;
                break;
        }
        return posicion;
    }

    public void integrar() throws Exception {
        try {
            OperadorDatos.iniciarTransaccionGlobal();
            for (String rutaArchivo : this.listaArchivosOrdenados.values()) {
                File archivo = new File(rutaArchivo);
                String nombreArchivo = archivo.getName();
                this.estadoComunicacionCarga.informarProgreso("Integrando " + nombreArchivo.replace(".XML", ""));
                Class tipo = this.dependenciaCargaMaestros.obtenerTipoDto(nombreArchivo);
                if (tipo != null) {
                    BaseListaDto listaDto = this.obtenerListaDto(tipo, nombreArchivo, false);
                    if (listaDto != null && !(listaDto instanceof ListaCarga)) {
                        if (listaDto.getLongitudLista() > 0) {
                            List listaNegocio = listaDto.convertirListaDtoAListaDominio();
                            if (listaNegocio != null) {
                                LogicaNegocioBase logicaNegocio = this.dependenciaCargaMaestros.getClaseNegocio(nombreArchivo);
                                if (logicaNegocio != null) {
                                    if (listaDto instanceof ListaTalleres) {
                                        if (versionMaestros != null) {
                                            ((TalleresBL) logicaNegocio).setVersionMaestros(versionMaestros);
                                        }
                                    }
                                    logicaNegocio.procesar(listaNegocio, listaDto.getOperacion());
                                }
                            }
                        }/* else {
                            Exception exception = new Exception("No se cargaron datos de " + nombreArchivo.replace(".XML", ""));
                            OperadorDatos.terminarTransaccionGlobal();
                            throw exception;
                        }*/
                    }
                }
            }
            OperadorDatos.establecerTransaccionGlobalSatisfactoria();
        } catch (Exception exp) {
            throw exp;
        } finally {
            OperadorDatos.terminarTransaccionGlobal();
        }
    }

    public void setActualizarMaestros(String versionMaestros) {
        this.versionMaestros = versionMaestros;

    }

    private interface OnCambioArchivo {
        void refrescar(String descripcion);
    }

}
