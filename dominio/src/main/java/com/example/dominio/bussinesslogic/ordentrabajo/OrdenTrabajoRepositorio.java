package com.example.dominio.bussinesslogic.ordentrabajo;

import com.example.dominio.RepositorioBase;
import com.example.dominio.modelonegocio.ListaOrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public interface OrdenTrabajoRepositorio extends RepositorioBase<OrdenTrabajo> {

    ListaOrdenTrabajo cargarOrdenesTrabajo(String codigoCorreria);

    ListaOrdenTrabajo cargar();

    OrdenTrabajo cargar(String codigoCorreria, String codigoOrdenTrabajo);

    boolean actualizarSecuencias(String codigoCorreria, int secuencia);

    int totalOrdenesTrabajo(String codigoCorreria);

    ListaOrdenTrabajo cargar(OrdenTrabajoBusqueda ordenTrabajoBusqueda);

    int asignarUltimaSecuencia(String codigoCorreria);

    ListaOrdenTrabajo cargar(String codigoCorreriasConsulta);
}
