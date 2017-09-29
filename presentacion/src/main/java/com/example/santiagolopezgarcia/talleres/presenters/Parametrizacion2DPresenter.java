package com.example.santiagolopezgarcia.talleres.presenters;

import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.administracion.ICambioTerminal;
import com.example.dominio.administracion.TalleresBL;
import com.example.dominio.modelonegocio.Talleres;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.helpers.GeneradorXmlHelper;
import com.example.santiagolopezgarcia.talleres.integracion.carga.DependenciaCargaDiaria;
import com.example.santiagolopezgarcia.talleres.services.dto.BaseListaDto;
import com.example.santiagolopezgarcia.talleres.services.dto.Parametrizacion2D;
import com.example.santiagolopezgarcia.talleres.services.dto.carga.DependenciaCargaMaestros;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IParametrizacion2DView;
import com.example.utilidades.Log;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class Parametrizacion2DPresenter extends Presenter<IParametrizacion2DView> implements ICambioTerminal {

    private GeneradorXmlHelper generadorXmlHelper;
    private DependenciaCargaMaestros dependenciaCargaMaestros;
    private DependenciaCargaDiaria dependenciaCargaDiaria;
    private TalleresBL talleresBL;
    private Talleres talleres;

    public GeneradorXmlHelper getGeneradorXmlHelper() {
        return generadorXmlHelper;
    }

    @Inject
    public Parametrizacion2DPresenter(DependenciaCargaDiaria dependenciaCargaDiaria,
                                      DependenciaCargaMaestros dependenciaCargaMaestros, TalleresBL talleresBL) {
        this.dependenciaCargaDiaria = dependenciaCargaDiaria;
        this.dependenciaCargaMaestros = dependenciaCargaMaestros;
        this.talleresBL = talleresBL;
    }

    @Override
    public void iniciar() {
        generadorXmlHelper = new GeneradorXmlHelper();
        talleres = talleresBL.cargarPrimerRegistro();
    }

    @Override
    public void detener() {

    }

    public Parametrizacion2D leerXmlCodigoBarras(File archivo) throws IOException {
        try {
            Serializer serializer = new Persister();
            Parametrizacion2D parametrizacion2D =
                    serializer.read(Parametrizacion2D.class, archivo);
            vista.mostrarDatosCodigoBarras(parametrizacion2D);
            return parametrizacion2D;
        } catch (Exception exc) {
            Log.error(exc, "Error leyendo xml " + archivo.getName());
            vista.registrarLog("Error leyendo xml " + archivo.getName() + " " + exc.getMessage());
            return null;
        }
    }

    public void procesarInformacion(Parametrizacion2D parametrizacion2D) throws Exception {

        File archivo = generadorXmlHelper.generarXml(parametrizacion2D.Tabla.Valor,
                parametrizacion2D.Tabla.Nombre);
        Class tipo;
        boolean maestro;

        try {
            tipo = this.dependenciaCargaMaestros.obtenerTipoDto(archivo.getName());
            maestro = true;
        } catch (IllegalArgumentException ex) {
            tipo = this.dependenciaCargaDiaria.obtenerTipoDto(archivo.getName());
            maestro = false;
        }

        if (tipo != null) {
            BaseListaDto listaDto = this.obtenerListaDto(tipo, archivo.getAbsolutePath());
            if (listaDto != null) {
                List listaNegocio = listaDto.convertirListaDtoAListaDominio();
                if (listaNegocio != null) {
                    if (maestro) {
                        procesarMaestro(parametrizacion2D, archivo, listaNegocio);
                    } else {
                        procesarCargaDiaria(parametrizacion2D, archivo, listaNegocio);
                    }
                }
            }
        }
        archivo.delete();

    }

    private void procesarMaestro(Parametrizacion2D parametrizacion2D, File archivo, List listaNegocio) {
        LogicaNegocioBase logicaNegocio = this.dependenciaCargaMaestros.getClaseNegocio(archivo.getName());
        if (logicaNegocio != null) {
            if (logicaNegocio instanceof TalleresBL)
                ((TalleresBL) logicaNegocio).setiCambioTerminal(this);
            if (!logicaNegocio.procesar(listaNegocio, parametrizacion2D.Tabla.OP)) {
                vista.mostrarMensajeError(vista.getContext().
                        getString(R.string.operacion_no_realizada));
            } else {
                vista.mostrarMensaje(vista.getContext().
                        getString(R.string.operacion_realizada), talleres.getLog());
            }
        }
    }

    private void procesarCargaDiaria(Parametrizacion2D parametrizacion2D, File archivo, List listaNegocio) {
        LogicaNegocioBase logicaNegocio = this.dependenciaCargaDiaria.getClaseNegocio(archivo.getName());
        if (logicaNegocio != null) {
            if (logicaNegocio instanceof TalleresBL)
                ((TalleresBL) logicaNegocio).setiCambioTerminal(this);
            if (!logicaNegocio.procesar(listaNegocio, parametrizacion2D.Tabla.OP)) {
                vista.mostrarMensajeError(vista.getContext().
                        getString(R.string.operacion_no_realizada));
            } else {
                vista.mostrarMensaje(vista.getContext().
                        getString(R.string.operacion_realizada), talleres.getLog());
            }
        }
    }

    public <T extends BaseListaDto> T obtenerListaDto(Class<T> tipo, String nombreArchivo) throws IOException {
        T listaDto = null;
        File archivo = new File(nombreArchivo);
        listaDto = leerXml(archivo, tipo);
        if (listaDto == null || listaDto.getLongitudLista() == 0) {
            try {
                vista.registrarLog(vista.getContext().getString(R.string.no_cargaron_datos) + nombreArchivo);
                throw new Exception(vista.getContext().
                        getString(R.string.no_cargaron_datos) + nombreArchivo);
            } catch (Exception e) {
                e.printStackTrace();
                vista.registrarLog(e.getMessage());
            }
        }
        return listaDto;
    }

    protected <T> T leerXml(File archivo, Class<T> tipoClase) throws IOException {
        T lista = null;
        try {
            Serializer serializer = new Persister();
            lista = serializer.read(tipoClase, archivo);
            return lista;
        } catch (Exception exc) {
            Log.error(exc, "Error leyendo xml " + archivo.getName());
            vista.registrarLog("Error leyendo xml " + archivo.getName() + " " + exc.getMessage());
            return lista;
        }
    }

    @Override
    public void confirmarCambioCorreria(String mensaje) {
        vista.mostrarMensaje(mensaje, talleres.getLog());
    }

    public boolean validarVersionApp(String version2D, String versionActual) {
        int tamanoActual = versionActual.length();
        int tamanoCarga = version2D.length();
        if (tamanoActual > tamanoCarga) {
            return versionActual.substring(0, tamanoCarga).equals(version2D);
        } else if (tamanoActual < tamanoCarga) {
            return version2D.substring(0, tamanoActual).equals(versionActual);
        } else {
            return version2D.equals(versionActual);
        }
    }
}
