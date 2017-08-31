package com.example.dominio.administracion;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.Talleres;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public interface TalleresRepositorio extends RepositorioBase<Talleres> {

    Talleres cargarPrimerRegistro() throws ParseException;

    boolean guardar(List<Talleres> listaSirius);

    boolean actualizar(Talleres talleres);

    boolean actualizar(Talleres talleres, boolean param2d);

    boolean guardar(Talleres talleres);

    Talleres consultarTalleresXNumeroTerminal(String numeroTerminal) throws ParseException;

    boolean borrarDB();

    int generarID();
}
