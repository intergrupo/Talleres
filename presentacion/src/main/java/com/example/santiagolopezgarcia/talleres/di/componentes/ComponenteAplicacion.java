package com.example.santiagolopezgarcia.talleres.di.componentes;

import com.example.dominio.acceso.DepartamentoRepositorio;
import com.example.dominio.acceso.MultiOpcionRepositorio;
import com.example.dominio.acceso.MunicipioRepositorio;
import com.example.dominio.administracion.OpcionRepositorio;
import com.example.dominio.administracion.PerfilRepositorio;
import com.example.dominio.administracion.PerfilXOpcionRepositorio;
import com.example.dominio.administracion.ContratoRepositorio;
import com.example.dominio.administracion.TalleresRepositorio;
import com.example.dominio.correria.CorreriaRepositorio;
import com.example.dominio.correria.EmpresaRepositorio;
import com.example.dominio.correria.ProgramacionCorreriaRepositorio;
import com.example.dominio.impresion.ParrafoImpresionRepositorio;
import com.example.dominio.impresion.SeccionImpresionRepositorio;
import com.example.dominio.labor.LaborRepositorio;
import com.example.dominio.labor.LaborXOrdenTrabajoRepositorio;
import com.example.dominio.labor.LaborXTareaRepositorio;
import com.example.dominio.notificacion.ItemRepositorio;
import com.example.dominio.notificacion.ItemXNotificacionRepositorio;
import com.example.dominio.notificacion.NotificacionRepositorio;
import com.example.dominio.notificacion.ReporteNotificacionRepositorio;
import com.example.dominio.ordentrabajo.EstadoRepositorio;
import com.example.dominio.ordentrabajo.OrdenTrabajoRepositorio;
import com.example.dominio.tarea.TareaXOrdenTrabajoRepositorio;
import com.example.dominio.tarea.TareaXTrabajoRepositorio;
import com.example.dominio.trabajo.TrabajoRepositorio;
import com.example.dominio.tarea.TareaRepositorio;
import com.example.dominio.trabajo.TrabajoXContratoRepositorio;
import com.example.dominio.trabajo.TrabajoXOrdenTrabajoRepositorio;
import com.example.santiagolopezgarcia.talleres.di.modulos.ModuloAplicacion;
import com.example.santiagolopezgarcia.talleres.di.modulos.ModuloDominio;
import com.example.dominio.administracion.UsuarioRepositorio;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */

@Singleton
@Component(modules = {ModuloAplicacion.class, ModuloDominio.class,})
public interface ComponenteAplicacion {

    UsuarioRepositorio usuarioRepository();

    OrdenTrabajoRepositorio ordenTrabajoRepositorio();

    EstadoRepositorio estadoRepositorio();

    CorreriaRepositorio correriaRepositorio();

    TareaRepositorio tareaRepositorio();

    TrabajoRepositorio trabajoRepositorio();

    TareaXOrdenTrabajoRepositorio tareaXOrdenTrabajoRepositorio();

    TrabajoXOrdenTrabajoRepositorio trabajoXOrdenTrabajoRepositorio();

    ContratoRepositorio contratoRepositorio();

    PerfilRepositorio perfilRepositorio();

    TareaXTrabajoRepositorio tareaXTrabajoRepositorio();

    DepartamentoRepositorio departamentoRepositorio();

    MunicipioRepositorio municipioRepositorio();

    LaborRepositorio laborRepositorio();

    LaborXOrdenTrabajoRepositorio laborXOrdenTrabajoRepositorio();

    LaborXTareaRepositorio laborXTareaRepositorio();

    MultiOpcionRepositorio multiOpcionRepositorio();

    OpcionRepositorio opcionRepositorio();

    PerfilXOpcionRepositorio perfilXOpcionRepositorio();

    NotificacionRepositorio notificacionRepositorio();

    ItemRepositorio itemRepositorio();

    ProgramacionCorreriaRepositorio programacionCorreriaRepositorio();

    ItemXNotificacionRepositorio itemXNotificacionRepositorio();

    ReporteNotificacionRepositorio reporteNotificacionRepositorio();

    TrabajoXContratoRepositorio trabajoXContratoRepositorio();

    EmpresaRepositorio empresaRepositorio();

    TalleresRepositorio talleresRepositorio();

    ParrafoImpresionRepositorio parrafoImpresionRepositorio();

    SeccionImpresionRepositorio seccionImpresionRepositorio();

}
