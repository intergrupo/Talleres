package com.example.santiagolopezgarcia.talleres.view.adapters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.example.dominio.modelonegocio.Correria;
import com.example.santiagolopezgarcia.talleres.view.fragments.CambioCorreriaFragment;
import com.example.santiagolopezgarcia.talleres.view.fragments.CorreriaFragment;
import com.example.santiagolopezgarcia.talleres.view.fragments.InformacionCorreriaFragment;
import com.example.santiagolopezgarcia.talleres.view.fragments.TotalesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class TabVistaPaginasCorreriaAdapter extends FragmentStatePagerAdapter {

    private Correria correria;
    public static final int IDENTIFICADOR_LOGIN = 0;
    public static final int IDENTIFICADOR_INFORMACION_CORRERIA = 1;
    public static final int IDENTIFICADOR_TOTALES = 2;
    public static final int IDENTIFICADOR_LECTURAREVISION = 3;
    boolean esVistaXOrdenTrabajo;
    List<Fragment> listaFragmentos;
    FragmentManager fragmentManager;
    Bundle parametros;
    private int posicionActiva;

    public TabVistaPaginasCorreriaAdapter(FragmentManager fragmentManager, Correria correria,
                                          boolean esVistaXOrdenTrabajo, int posicionActiva) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        this.correria = correria;
        this.esVistaXOrdenTrabajo = esVistaXOrdenTrabajo;
        this.posicionActiva = posicionActiva;
        this.crearListaDeFragmentos();
    }


    public void setEsVistaXOrdenTrabajo(boolean esVistaXOrdenTrabajo) {
        this.esVistaXOrdenTrabajo = esVistaXOrdenTrabajo;
    }

    private void crearListaDeFragmentos() {
        parametros = new Bundle();
        parametros.putSerializable(Correria.class.getName(), correria);
        listaFragmentos = new ArrayList<>();
        listaFragmentos.add(crearFragmentoLogin());
        listaFragmentos.add(crearFragmentoInformacionCorreria());
        listaFragmentos.add(new TotalesFragment());
        listaFragmentos.add(crearFragmentoCorreria());
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = listaFragmentos.get(position);
        if (fragment == null) {
            fragment = crearFragment(position);
            listaFragmentos.set(position, fragment);
        }
        return fragment;
    }

    private Fragment crearFragment(int position) {
        switch (position) {
            case IDENTIFICADOR_LOGIN:
                return crearFragmentoLogin();
            case IDENTIFICADOR_INFORMACION_CORRERIA:
                return crearFragmentoInformacionCorreria();
            case IDENTIFICADOR_TOTALES:
                return new TotalesFragment();
            case IDENTIFICADOR_LECTURAREVISION:
                return crearFragmentoCorreria();
            default:
                return new Fragment();
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        listaFragmentos.set(position, null);
    }

    @NonNull
    private Fragment crearFragmentoInformacionCorreria() {
        InformacionCorreriaFragment informacionCorreriaFragment = new InformacionCorreriaFragment();
        informacionCorreriaFragment.setArguments(parametros);
        return informacionCorreriaFragment;
    }

    @NonNull
    private Fragment crearFragmentoLogin() {
        CambioCorreriaFragment loginFragment = new CambioCorreriaFragment();
        loginFragment.setArguments(parametros);
        return loginFragment;
    }

    @NonNull
    private Fragment crearFragmentoCorreria() {
        CorreriaFragment correriaFragment = new CorreriaFragment();
        parametros.putBoolean(CorreriaFragment.class.getName(), esVistaXOrdenTrabajo);
        parametros.putInt("Posicion", posicionActiva);
        correriaFragment.setArguments(parametros);
        return correriaFragment;
    }

    @Override
    public int getCount() {
        return listaFragmentos.size();
    }
}