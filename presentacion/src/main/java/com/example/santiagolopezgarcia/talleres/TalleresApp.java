package com.example.santiagolopezgarcia.talleres;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.dominio.modelonegocio.Usuario;
import com.example.santiagolopezgarcia.talleres.di.componentes.ComponenteAplicacion;
import com.example.santiagolopezgarcia.talleres.di.componentes.DaggerComponenteAplicacion;
import com.example.santiagolopezgarcia.talleres.di.modulos.ModuloAplicacion;
import com.example.santiagolopezgarcia.talleres.di.modulos.ModuloDominio;
import com.example.utilidades.Log;
import com.example.utilidades.helpers.StringHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */

public class TalleresApp extends Application {

    private ComponenteAplicacion appComponent;
    private String codigoTerminal;
    private Usuario usuario;
    private String codigoCorreria;

    public String getCodigoCorreria() {
        return codigoCorreria;
    }

    public void setCodigoCorreria(String codigoCorreria) {
        this.codigoCorreria = codigoCorreria;
    }

    public String getCodigoTerminal() {
        return codigoTerminal;
    }

    public void setCodigoTerminal(String codigoTerminal) {
        this.codigoTerminal = codigoTerminal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.codigoTerminal = "";
        this.usuario = new Usuario();
        this.iniciarInyeccionDependencias();
//        DatosCache.setListaOrdenTrabajo(new ListaOrdenTrabajo());
//        DatosCache.setListaOrdenTrabajoFiltrado(new ListaOrdenTrabajo());
//        DatosCache.setListaTareaXOrdenTrabajo(new ListaTareaXOrdenTrabajo());
//        DatosCache.setListaTareaXOrdenTrabajoFiltrado(new ListaTareaXOrdenTrabajo());
        inicializarRutas();
        inicializarImagenes();
        iniciarLog();
    }

    private void iniciarLog() {
        if (BuildConfig.DEBUG) {
            Log.iniciarDebug();
        } else {
            Log.iniciarRelease();
        }
        try {
            StringHelper.registrarLog("Iniciando aplicacion");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    public void inicializarRutas() {
//        new File(Constantes.traerRutaAdjuntos()).mkdirs();
//        new File(Constantes.traerRutaCamera()).mkdirs();
//        new File(Constantes.traerRutaDiagrams()).mkdirs();
//        new File(Constantes.traerRutaCarga() + Constantes.NOMBRE_CARPETA_MAESTROS + File.separator).mkdirs();
//        new File(Constantes.traerRutaCarga() + Constantes.NOMBRE_CARPETA_CARGA_DIARIA + File.separator).mkdirs();
//        new File(Constantes.traerRutaSirius() + Constantes.NOMBRE_CARPETA_lOGOS_EMPRESA).mkdir();
//        new File(Constantes.traerRutaSirius() + Constantes.NOMBRE_CARPETA_LOG).mkdir();
    }

    private void inicializarImagenes() {

    }

    private void cargarImagenes(String nombre, int idImagen,File direccion){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),idImagen,options);

        guardarImagenCarpeta(direccion,nombre,bitmap,Bitmap.CompressFormat.JPEG,100);
    }

    private boolean guardarImagenCarpeta(File dir, String nombreImagen, Bitmap bitmap,
                                         Bitmap.CompressFormat formato, int calidad) {

        File imageFile = new File(dir, nombreImagen);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);

            bitmap.compress(formato, calidad, fos);

            fos.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return false;
    }



    private void iniciarInyeccionDependencias() {
        appComponent = DaggerComponenteAplicacion.builder()
                .moduloAplicacion(new ModuloAplicacion(this))
                .moduloDominio(new ModuloDominio(this))
                .build();
    }

    public ComponenteAplicacion getAppComponent() {

        return appComponent;
    }

    public void limpiarSesion() {
        this.usuario = new Usuario();
        this.setCodigoCorreria(null);
    }

    public void limpiarCorreria() {
        this.setCodigoCorreria(null);
    }
}