package com.example.dominio.bussinesslogic.labor;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.LaborXTarea;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public interface LaborXTareaRepositorio extends RepositorioBase<LaborXTarea> {

    LaborXTarea cargarLaborxTarea(String codigoTarea, String codigoLabor);

    List<LaborXTarea> cargarListaLaborxTareaXCodigoTarea(String codigoTarea);

    List<LaborXTarea> cargarListaLaborxTareaObligatorias(String codigoTarea);

    List<LaborXTarea> cargarListaLaborxTareaXCodigoTareaMenu(String codigoTarea, String codigoLabor);

    List<LaborXTarea> cargarListaLaborxTareaXCodigoTareaMenu(String codigoTarea);
}