package com.example.dominio.ordentrabajo;

import com.example.dominio.IBaseDescarga;
import com.example.dominio.LogicaNegocioBase;
import com.example.dominio.correria.CorreriaRepositorio;
import com.example.dominio.labor.LaborXOrdenTrabajoBL;
import com.example.dominio.modelonegocio.Correria;
import com.example.dominio.modelonegocio.LaborXOrdenTrabajo;
import com.example.dominio.modelonegocio.ListaOrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.dominio.modelonegocio.OrdenTrabajoBusqueda;
import com.example.dominio.modelonegocio.Tarea;
import com.example.dominio.modelonegocio.TareaXTrabajoOrdenTrabajo;
import com.example.dominio.modelonegocio.Usuario;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class OrdenTrabajoBL implements LogicaNegocioBase<OrdenTrabajo>, IBaseDescarga<OrdenTrabajo> {

    private OrdenTrabajoReposoitorio ordenTrabajoReposoitorio;
    private CorreriaRepositorio correriaRepositorio;
    private TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL;
    private LaborXOrdenTrabajoBL laborXOrdenTrabajoBL;

    public OrdenTrabajoBL(OrdenTrabajoReposoitorio ordenTrabajoReposoitorio,
                          CorreriaRepositorio correriaRepositorio,
                          TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL,
                          LaborXOrdenTrabajoBL laborXOrdenTrabajoBL) {
        this.ordenTrabajoReposoitorio = ordenTrabajoReposoitorio;
        this.correriaRepositorio = correriaRepositorio;
        this.tareaXOrdenTrabajoBL = tareaXOrdenTrabajoBL;
        this.laborXOrdenTrabajoBL = laborXOrdenTrabajoBL;
    }

    public int totalOrdenesTrabajo(String codigoCorreria) {
        return ordenTrabajoReposoitorio.totalOrdenesTrabajo(codigoCorreria);
    }

    public ListaOrdenTrabajo cargarOrdenesTrabajo(String codigoCorreria) {
        return ordenTrabajoReposoitorio.cargarOrdenesTrabajo(codigoCorreria);
    }

    public boolean guardarOrdenesTrabajo(ListaOrdenTrabajo listaOrdenesTrabajo) {
        try {
            return ordenTrabajoReposoitorio.guardar(listaOrdenesTrabajo);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean guardarOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        try {
            return ordenTrabajoReposoitorio.guardar(ordenTrabajo);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean tieneRegistrosOrdenTrabajo() {
        return ordenTrabajoReposoitorio.tieneRegistros();
    }

    public OrdenTrabajo cargarOrdenTrabajo(String codigoCorreria, String codigoOrdenTrabajo) {
        return ordenTrabajoReposoitorio.cargar(codigoCorreria, codigoOrdenTrabajo);
    }

    public boolean actualizarOrdenTrabajo(OrdenTrabajo ordenTrabajoActiva) {
        return ordenTrabajoReposoitorio.actualizar(ordenTrabajoActiva);
    }

    public boolean tareaTieneLaboresEjecutadas(String codigoCorreria, String codigoOrdenTrabajo, String codigoTrabajo, String codigoTarea) {
        List<LaborXOrdenTrabajo> laboresXot =
                laborXOrdenTrabajoBL.cargarLaboresXOrdenTrabajoEjecutadas(codigoCorreria, codigoOrdenTrabajo, codigoTrabajo, codigoTarea);

        if (laboresXot.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean cancelarOrdenTrabajo(OrdenTrabajo ordenTrabajoActiva, Usuario usuario) {

        if (ordenTrabajoActiva.getEstado().equals(OrdenTrabajo.EstadoOrdenTrabajo.ENEJECUCION)) {
            ordenTrabajoActiva.setEstado(OrdenTrabajo.EstadoOrdenTrabajo.CANCELADA);
            obtenerDatosAuditoriaOrdenTrabajo(ordenTrabajoActiva, usuario.getCodigoUsuario());

            if (ordenTrabajoReposoitorio.actualizar(ordenTrabajoActiva)) {
                GuardarDatosAuditoriaCorreria(ordenTrabajoActiva.getCodigoCorreria());

                if (ordenTrabajoActiva.getTrabajoXOrdenTrabajo().getTareaXOrdenTrabajoList().isEmpty()) {
                    ordenTrabajoActiva.getTrabajoXOrdenTrabajo().setTareaXOrdenTrabajoList(
                            tareaXOrdenTrabajoBL.cargarListaTareaXTrabajoOrdenTrabajo(
                                    ordenTrabajoActiva.getCodigoCorreria(), ordenTrabajoActiva.getCodigoOrdenTrabajo()));
                }

                for (TareaXTrabajoOrdenTrabajo tareaXTrabajoOrdenTrabajo : ordenTrabajoActiva.getTrabajoXOrdenTrabajo().getTareaXOrdenTrabajoList()) {
                    if (tareaXTrabajoOrdenTrabajo.getEstadoTarea().equals(Tarea.EstadoTarea.PENDIENTE)) {
                        tareaXTrabajoOrdenTrabajo.setEstadoTarea(Tarea.EstadoTarea.CANCELADA);
                        obtenerDatosAuditoriaTareaxOrdenTrabajo(tareaXTrabajoOrdenTrabajo, usuario.getCodigoUsuario());
                        tareaXOrdenTrabajoBL.actualizarTareaXOrdenTrabajo(tareaXTrabajoOrdenTrabajo);
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean actualizarSecuencias(String codigoCorreria, int secuencia) {
        return ordenTrabajoReposoitorio.actualizarSecuencias(codigoCorreria, secuencia);
    }

    public List<String> cargarTrabajosAgrupados(String codigoCorreria) {
        return tareaXOrdenTrabajoBL.cargarCodigosTrabajosAgrupados(codigoCorreria);
    }

    public boolean actualizarEstadoOrdenTrabajo(OrdenTrabajo ordenTrabajo, Usuario usuario) {
        obtenerDatosAuditoriaOrdenTrabajo(ordenTrabajo, usuario.getCodigoUsuario());

        if (ordenTrabajoReposoitorio.actualizar(ordenTrabajo)) {
            GuardarDatosAuditoriaCorreria(ordenTrabajo.getCodigoCorreria());
        }
        return true;
    }

    public boolean eliminarOT(OrdenTrabajo ordenTrabajo) {
        return ordenTrabajoReposoitorio.eliminar(ordenTrabajo);
    }

    public boolean firmarOT(OrdenTrabajo ordenTrabajo, Usuario usuario) {
        obtenerDatosAuditoriaOrdenTrabajo(ordenTrabajo, usuario.getCodigoUsuario());
        if (ordenTrabajoReposoitorio.actualizar(ordenTrabajo)) {
            GuardarDatosAuditoriaCorreria(ordenTrabajo.getCodigoCorreria());
        }
        return true;
    }

    private void obtenerDatosAuditoriaOrdenTrabajo(OrdenTrabajo ordenTrabajoActiva, String codigoUsuario) {
        ordenTrabajoActiva.setCodigoUsuarioLabor(codigoUsuario);
        ordenTrabajoActiva.setFechaUltimaOrdenTrabajo(new Date());
        if (ordenTrabajoActiva.getFechaInicioOrdenTrabajo() == null) {
            ordenTrabajoActiva.setFechaInicioOrdenTrabajo(new Date());
        }
    }

    private void GuardarDatosAuditoriaCorreria(String codigoCorreria) {
        Correria correria = correriaRepositorio.cargar(codigoCorreria);

        if (correria.getFechaInicioCorreria() == null) {
            correria.setFechaInicioCorreria(new Date());
        } else {
            correria.setFechaUltimaCorreria(new Date());
        }
        correriaRepositorio.actualizar(correria);
    }

    private void obtenerDatosAuditoriaTareaxOrdenTrabajo(TareaXTrabajoOrdenTrabajo tareaXTrabajoOrdenTrabajo, String codigoUsuario) {
        tareaXTrabajoOrdenTrabajo.setFechaUltimaTarea(new Date());
        tareaXTrabajoOrdenTrabajo.setCodigoUsuarioTarea(codigoUsuario);
        if (tareaXTrabajoOrdenTrabajo.getFechaInicioTarea() == null) {
            tareaXTrabajoOrdenTrabajo.setFechaInicioTarea(new Date());
        }
    }

    public ListaOrdenTrabajo cargar() {
        return ordenTrabajoReposoitorio.cargar();
    }

    @Override
    public boolean procesar(List<OrdenTrabajo> listaDatos, String operacion) {
        boolean respuesta = false;
        for (OrdenTrabajo ordenTrabajo : listaDatos) {
            switch (operacion) {
                case "A":
                    if (!cargarOrdenTrabajo(ordenTrabajo.getCodigoCorreria(), ordenTrabajo.getCodigoOrdenTrabajo()).esClaveLlena()) {
                        respuesta = guardarOrdenTrabajo(ordenTrabajo);
                    }
                    break;
                case "U":
                    respuesta = actualizarOrdenTrabajo(ordenTrabajo);
                    break;
                case "D":
                    respuesta = eliminarOT(ordenTrabajo);
                    break;
                case "R":
                    if (cargarOrdenTrabajo(ordenTrabajo.getCodigoCorreria(), ordenTrabajo.getCodigoOrdenTrabajo()).esClaveLlena()) {
                        respuesta = actualizarOrdenTrabajo(ordenTrabajo);
                    } else {
                        respuesta = guardarOrdenTrabajo(ordenTrabajo);
                    }
                    break;
                default:
                    if (!cargarOrdenTrabajo(ordenTrabajo.getCodigoCorreria(), ordenTrabajo.getCodigoOrdenTrabajo()).esClaveLlena()) {
                        respuesta = guardarOrdenTrabajo(ordenTrabajo);
                    }
                    break;
            }
        }
        return respuesta;
    }

    @Override
    public List<OrdenTrabajo> cargarXCorreria(String codigoCorreria) {
        return cargarOrdenesTrabajo(codigoCorreria);
    }

    @Override
    public List<OrdenTrabajo> cargarXFiltro(OrdenTrabajoBusqueda filtro) {
        List<OrdenTrabajo> lista = new ArrayList<>();
        for (OrdenTrabajo ordenTrabajo : filtro.getListaOrdenTrabajo()) {
            lista.add(ordenTrabajoReposoitorio.cargar(ordenTrabajo.getCodigoCorreria(), ordenTrabajo.getCodigoOrdenTrabajo()));
        }
        return lista;
    }

    public ListaOrdenTrabajo cargar(OrdenTrabajoBusqueda ordenTrabajoBusqueda) {
        if (ordenTrabajoBusqueda.isTodos()) {
            return ordenTrabajoReposoitorio.cargarOrdenesTrabajo(ordenTrabajoBusqueda.getCodigoCorreria());
        } else {
            return ordenTrabajoReposoitorio.cargar(ordenTrabajoBusqueda);
        }
    }

    public ListaOrdenTrabajo cargar(List<String> codigosCorreriasIntegradas) {
        String codigosCorreriasConsulta = "";
        for (int i = 0; i < codigosCorreriasIntegradas.size(); i++) {
            if (i == codigosCorreriasIntegradas.size() - 1)
                codigosCorreriasConsulta += "'" + codigosCorreriasIntegradas.get(i).trim() + "'";
            else
                codigosCorreriasConsulta += "'" + codigosCorreriasIntegradas.get(i).trim() + "',";
        }
        return ordenTrabajoReposoitorio.cargar(codigosCorreriasConsulta);
    }

    public int asignarUltimaSecuencia(String codigoCorreria) {
        return ordenTrabajoReposoitorio.asignarUltimaSecuencia(codigoCorreria);
    }
}
