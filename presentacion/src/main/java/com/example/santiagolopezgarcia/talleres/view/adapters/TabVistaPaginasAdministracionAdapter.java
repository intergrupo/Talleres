package com.example.santiagolopezgarcia.talleres.view.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dominio.modelonegocio.Opcion;
import com.example.dominio.modelonegocio.Talleres;
import com.example.santiagolopezgarcia.talleres.view.fragments.AdministracionCorreriaFragment;
import com.example.santiagolopezgarcia.talleres.view.fragments.AdministracionUsuarioFragment;
import com.example.santiagolopezgarcia.talleres.view.fragments.ConfiguracionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class TabVistaPaginasAdministracionAdapter extends FragmentPagerAdapter {

    public static final int IDENTIFICADOR_CORRERIAS = 0;
    public static final int IDENTIFICADOR_USUARIOS = 1;
    public static final int IDENTIFICADOR_CONFIGURACION = 2;
    private Talleres talleres;
    private Opcion opcion;

    private List<Fragment> listaFragmentos = new ArrayList<>();
    private List<String> listaTitulos = new ArrayList<>();

    public static final String TITULO_CORRERIA = "Correrías";
    public static final String TITULO_USUARIO = "Usuarios";
    public static final String TITULO_CONFIGURACION = "Configuración";

    public TabVistaPaginasAdministracionAdapter(FragmentManager fm, Talleres talleres, Opcion opcion) {
        super(fm);
        this.talleres = talleres;
        this.opcion = opcion;
        crearListaDeFragmentos();
        crearListaDeTitulo();
    }

    @Override
    public Fragment getItem(int position) {
        return listaFragmentos.get(position);
    }

    @Override
    public int getCount() {
        return listaFragmentos.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listaTitulos.get(position);
    }

    private void crearListaDeTitulo() {
        listaTitulos.add(TITULO_CORRERIA);
        listaTitulos.add(TITULO_USUARIO);
        listaTitulos.add(TITULO_CONFIGURACION);
    }

    private void crearListaDeFragmentos() {
        listaFragmentos = new ArrayList<>(3);
        listaFragmentos.add(crearFragmentoListaCorrerias());
        listaFragmentos.add(crearFragmentoListaUsuarios());
        listaFragmentos.add(crearFragmentoConfiguracion());
    }

    private Fragment crearFragmentoListaCorrerias() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Opcion.class.getName(), opcion);
        AdministracionCorreriaFragment administracionCorreriaFragment = new AdministracionCorreriaFragment();
        administracionCorreriaFragment.setArguments(bundle);
        return administracionCorreriaFragment;
    }

    private Fragment crearFragmentoListaUsuarios() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Opcion.class.getName(), opcion);

        AdministracionUsuarioFragment administracionUsuarioFragment = new AdministracionUsuarioFragment();
        administracionUsuarioFragment.setArguments(bundle);
        return administracionUsuarioFragment;
    }

    private Fragment crearFragmentoConfiguracion(){
        Bundle bundle = new Bundle();
        bundle.putSerializable(Talleres.class.getName(), talleres);

        ConfiguracionFragment configuracionFragment = new ConfiguracionFragment();
        configuracionFragment.setArguments(bundle);
        return configuracionFragment;
    }
}