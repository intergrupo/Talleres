package com.example.dominio.administracion;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.Sirius;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public interface SiriusRepositorio extends RepositorioBase<Sirius> {

    Sirius cargarPrimerRegistro() throws ParseException;

    boolean guardar(List<Sirius> listaSirius);

    boolean actualizar(Sirius sirius);

    boolean actualizar(Sirius sirius, boolean param2d);

    boolean guardar(Sirius sirius);

    Sirius consultarSiriusXNumeroTerminal(String numeroTerminal) throws ParseException;

    boolean borrarDB();

    int generarID();
}
