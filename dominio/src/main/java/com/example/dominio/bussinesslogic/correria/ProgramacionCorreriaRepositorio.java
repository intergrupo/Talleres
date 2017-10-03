package com.example.dominio.bussinesslogic.correria;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.ProgramacionCorreria;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public interface ProgramacionCorreriaRepositorio extends RepositorioBase<ProgramacionCorreria> {

    List<ProgramacionCorreria> cargarXCorreria(String codigoCorreria);

    List<ProgramacionCorreria> cargar();

    List<ProgramacionCorreria> cargarXNumeroTerminal(String numeroTerminal);

    ProgramacionCorreria cargar(String idProgramacionCorreria, String codigoCorreria, String numeroTerminal);

    List<ProgramacionCorreria> cargar(String codigosCorreriasIntegradas);
}
