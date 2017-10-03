package com.example.santiagolopezgarcia.talleres.di.componentes;

import com.example.dominio.bussinesslogic.acceso.DepartamentoRepositorio;
import com.example.dominio.bussinesslogic.acceso.MultiOpcionRepositorio;
import com.example.dominio.bussinesslogic.acceso.MunicipioRepositorio;
import com.example.dominio.bussinesslogic.administracion.OpcionRepositorio;
import com.example.dominio.bussinesslogic.administracion.PerfilRepositorio;
import com.example.dominio.bussinesslogic.administracion.PerfilXOpcionRepositorio;
import com.example.dominio.bussinesslogic.administracion.ContratoRepositorio;
import com.example.dominio.bussinesslogic.administracion.TalleresRepositorio;
import com.example.dominio.bussinesslogic.correria.CorreriaRepositorio;
import com.example.dominio.bussinesslogic.correria.EmpresaRepositorio;
import com.example.dominio.bussinesslogic.correria.ProgramacionCorreriaRepositorio;
import com.example.dominio.bussinesslogic.impresion.ParrafoImpresionRepositorio;
import com.example.dominio.bussinesslogic.impresion.SeccionImpresionRepositorio;
import com.example.dominio.bussinesslogic.labor.LaborRepositorio;
import com.example.dominio.bussinesslogic.labor.LaborXOrdenTrabajoRepositorio;
import com.example.dominio.bussinesslogic.labor.LaborXTareaRepositorio;
import com.example.dominio.bussinesslogic.notificacion.ItemRepositorio;
import com.example.dominio.bussinesslogic.notificacion.ItemXNotificacionRepositorio;
import com.example.dominio.bussinesslogic.notificacion.NotificacionRepositorio;
import com.example.dominio.bussinesslogic.notificacion.ReporteNotificacionRepositorio;
import com.example.dominio.bussinesslogic.ordentrabajo.EstadoRepositorio;
import com.example.dominio.bussinesslogic.ordentrabajo.OrdenTrabajoRepositorio;
import com.example.dominio.bussinesslogic.tarea.TareaXOrdenTrabajoRepositorio;
import com.example.dominio.bussinesslogic.tarea.TareaXTrabajoRepositorio;
import com.example.dominio.bussinesslogic.trabajo.TrabajoRepositorio;
import com.example.dominio.bussinesslogic.tarea.TareaRepositorio;
import com.example.dominio.bussinesslogic.trabajo.TrabajoXContratoRepositorio;
import com.example.dominio.bussinesslogic.trabajo.TrabajoXOrdenTrabajoRepositorio;
import com.example.santiagolopezgarcia.talleres.di.modulos.ModuloAplicacion;
import com.example.santiagolopezgarcia.talleres.di.modulos.ModuloDominio;
import com.example.dominio.bussinesslogic.administracion.UsuarioRepositorio;

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
