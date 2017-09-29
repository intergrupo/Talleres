package com.example.santiagolopezgarcia.talleres.di.modulos;

import com.example.dominio.acceso.DepartamentoBL;
import com.example.dominio.acceso.DepartamentoRepositorio;
import com.example.dominio.acceso.MultiOpcionBL;
import com.example.dominio.acceso.MultiOpcionRepositorio;
import com.example.dominio.acceso.MunicipioBL;
import com.example.dominio.acceso.MunicipioRepositorio;
import com.example.dominio.acceso.PermisoBL;
import com.example.dominio.administracion.ContratoBL;
import com.example.dominio.administracion.ContratoRepositorio;
import com.example.dominio.administracion.OpcionBL;
import com.example.dominio.administracion.OpcionRepositorio;
import com.example.dominio.administracion.PerfilBL;
import com.example.dominio.administracion.PerfilRepositorio;
import com.example.dominio.administracion.PerfilXOpcionBL;
import com.example.dominio.administracion.PerfilXOpcionRepositorio;
import com.example.dominio.administracion.TalleresBL;
import com.example.dominio.administracion.TalleresRepositorio;
import com.example.dominio.administracion.UsuarioBL;
import com.example.dominio.administracion.UsuarioRepositorio;
import com.example.dominio.auditoria.FirmarLaboresYTareas;
import com.example.dominio.correria.CorreriaBL;
import com.example.dominio.correria.CorreriaRepositorio;
import com.example.dominio.correria.EmpresaBL;
import com.example.dominio.correria.EmpresaRepositorio;
import com.example.dominio.correria.ProgramacionCorreriaBL;
import com.example.dominio.correria.ProgramacionCorreriaRepositorio;
import com.example.dominio.impresion.ParrafoImpresionBL;
import com.example.dominio.impresion.ParrafoImpresionRepositorio;
import com.example.dominio.impresion.SeccionImpresionBL;
import com.example.dominio.impresion.SeccionImpresionRepositorio;
import com.example.dominio.labor.LaborBL;
import com.example.dominio.labor.LaborRepositorio;
import com.example.dominio.labor.LaborXOrdenTrabajoBL;
import com.example.dominio.labor.LaborXOrdenTrabajoRepositorio;
import com.example.dominio.labor.LaborXTareaBL;
import com.example.dominio.labor.LaborXTareaRepositorio;
import com.example.dominio.notificacion.ItemBL;
import com.example.dominio.notificacion.ItemRepositorio;
import com.example.dominio.notificacion.ItemXNotificacionBL;
import com.example.dominio.notificacion.ItemXNotificacionRepositorio;
import com.example.dominio.notificacion.NotificacionBL;
import com.example.dominio.notificacion.NotificacionRepositorio;
import com.example.dominio.notificacion.ReporteNotificacionBL;
import com.example.dominio.notificacion.ReporteNotificacionRepositorio;
import com.example.dominio.ordentrabajo.EstadoBL;
import com.example.dominio.ordentrabajo.EstadoRepositorio;
import com.example.dominio.ordentrabajo.NotificacionOrdenTrabajoBL;
import com.example.dominio.ordentrabajo.OrdenTrabajoBL;
import com.example.dominio.ordentrabajo.OrdenTrabajoRepositorio;
import com.example.dominio.tarea.TareaBL;
import com.example.dominio.tarea.TareaRepositorio;
import com.example.dominio.tarea.TareaXOrdenTrabajoBL;
import com.example.dominio.tarea.TareaXOrdenTrabajoRepositorio;
import com.example.dominio.tarea.TareaXTrabajoBL;
import com.example.dominio.tarea.TareaXTrabajoRepositorio;
import com.example.dominio.trabajo.TrabajoBL;
import com.example.dominio.trabajo.TrabajoRepositorio;
import com.example.dominio.trabajo.TrabajoXContratoBL;
import com.example.dominio.trabajo.TrabajoXContratoRepositorio;
import com.example.dominio.trabajo.TrabajoXOrdenTrabajoBL;
import com.example.dominio.trabajo.TrabajoXOrdenTrabajoRepositorio;

import dagger.Module;
import dagger.Provides;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */

@Module
public class ModuloPresentacion {

    @Provides
    UsuarioBL provideUsuarioBL(UsuarioRepositorio usuarioRepositorio, ContratoBL contratoBL, PerfilBL perfilBL) {
        return new UsuarioBL(usuarioRepositorio, contratoBL, perfilBL);
    }

    @Provides
    CorreriaBL provideCorreriaBL(CorreriaRepositorio correriaRepositorio, ReporteNotificacionBL reporteNotificacionBL) {
        return new CorreriaBL(correriaRepositorio, reporteNotificacionBL);

    }

    @Provides
    OrdenTrabajoBL provideOrdenTrabajoBL(OrdenTrabajoRepositorio ordenTrabajoRepositorio,
                                         CorreriaRepositorio correriaRepositorio,
                                         TareaXOrdenTrabajoBL tareaXOrdenTrabajoBL,
                                         LaborXOrdenTrabajoBL laborXOrdenTrabajoBL) {
        return new OrdenTrabajoBL(ordenTrabajoRepositorio, correriaRepositorio, tareaXOrdenTrabajoBL,
                laborXOrdenTrabajoBL);
    }

    @Provides
    EstadoBL provideEstadoBL(EstadoRepositorio estadoRepositorio) {
        return new EstadoBL(estadoRepositorio);
    }

    @Provides
    TareaBL provideTareaBL(TareaRepositorio tareaRepositorio) {
        return new TareaBL(tareaRepositorio);
    }

    @Provides
    TrabajoBL provideTrabajoBL(TrabajoRepositorio trabajoRepositorio) {
        return new TrabajoBL(trabajoRepositorio);
    }

    @Provides
    TrabajoXOrdenTrabajoBL provideTrabajoXOrdenTrabajoBL(TrabajoXOrdenTrabajoRepositorio trabajoXOrdenTrabajoRepositorio) {
        return new TrabajoXOrdenTrabajoBL(trabajoXOrdenTrabajoRepositorio);
    }

    @Provides
    ContratoBL provideContratoBL(ContratoRepositorio contratoRepositorio) {
        return new ContratoBL(contratoRepositorio);
    }

    @Provides
    PerfilBL providePerfilBLInterface(PerfilRepositorio perfilRepositorio) {
        return new PerfilBL(perfilRepositorio);
    }

    @Provides
    DepartamentoBL provideDepartamentoBL(DepartamentoRepositorio departamentoRepositorio, MunicipioBL municipioBL) {
        return new DepartamentoBL(departamentoRepositorio, municipioBL);
    }

    @Provides
    MunicipioBL provideMunicipioBL(MunicipioRepositorio municipioRepositorio) {
        return new MunicipioBL(municipioRepositorio);
    }

    @Provides
    LaborBL provideLaborBL(LaborRepositorio laborRepositorio, LaborXTareaRepositorio laborXTareaRepositorio) {
        return new LaborBL(laborRepositorio, laborXTareaRepositorio);
    }

    @Provides
    MultiOpcionBL provideMultiOpcionBL(MultiOpcionRepositorio multiOpcionRepositorio) {
        return new MultiOpcionBL(multiOpcionRepositorio);
    }

    @Provides
    OpcionBL provideOpcionBL(OpcionRepositorio opcionRepositorio) {
        return new OpcionBL(opcionRepositorio);
    }

    @Provides
    PerfilXOpcionBL providePerfilXOpcionBL(PerfilXOpcionRepositorio perfilXOpcionRepositorio) {
        return new PerfilXOpcionBL(perfilXOpcionRepositorio);
    }

    @Provides
    PermisoBL providePermisoBL(PerfilBL perfilBL, OpcionBL opcionBL, PerfilXOpcionBL perfilXOpcionBL) {
        return new PermisoBL(perfilBL, opcionBL, perfilXOpcionBL);
    }

    @Provides
    NotificacionBL provideNotificacionBL(NotificacionRepositorio notificacionRepositorio) {
        return new NotificacionBL(notificacionRepositorio);
    }

    @Provides
    ItemBL provideItemBL(ItemRepositorio itemRepositorio) {
        return new ItemBL(itemRepositorio);
    }

    @Provides
    ItemXNotificacionBL provideItemXNotificacionBL(ItemXNotificacionRepositorio itemXNotificacionRepositorio) {
        return new ItemXNotificacionBL(itemXNotificacionRepositorio);
    }

    @Provides
    ReporteNotificacionBL provideReporteNotificacionBL(ReporteNotificacionRepositorio reporteNotificacionRepositorio) {
        return new ReporteNotificacionBL(reporteNotificacionRepositorio);
    }

    @Provides
    EmpresaBL provideEmpresaBL(EmpresaRepositorio empresaRepositorio) {
        return new EmpresaBL(empresaRepositorio);
    }

    @Provides
    TalleresBL provideTalleresBL(TalleresRepositorio talleresRepositorio) {
        return new TalleresBL(talleresRepositorio);
    }

    @Provides
    LaborXTareaBL provideLaborXTareaBL(LaborXTareaRepositorio laborXTareaRepositorio) {
        return new LaborXTareaBL(laborXTareaRepositorio);
    }

    @Provides
    TareaXTrabajoBL provideTareaXTrabajoBL(TareaXTrabajoRepositorio tareaXTrabajoRepositorio,
                                           TareaBL tareaBL) {
        return new TareaXTrabajoBL(tareaXTrabajoRepositorio, tareaBL);
    }

    @Provides
    TrabajoXContratoBL provideTrabajoXContratoBL(TrabajoXContratoRepositorio trabajoXContratoRepositorio,
                                                 TrabajoBL trabajoBL) {
        return new TrabajoXContratoBL(trabajoXContratoRepositorio, trabajoBL);
    }

    @Provides
    TareaXOrdenTrabajoBL provideTareaXOrdenTrabajoBL(TareaXOrdenTrabajoRepositorio tareaXOrdenTrabajoRepositorio) {
        return new TareaXOrdenTrabajoBL(tareaXOrdenTrabajoRepositorio);
    }

    @Provides
    LaborXOrdenTrabajoBL provideLaborXOrdenTrabajoBL(LaborXOrdenTrabajoRepositorio laborXOrdenTrabajoRepositorio) {
        return new LaborXOrdenTrabajoBL(laborXOrdenTrabajoRepositorio);
    }

    @Provides
    ParrafoImpresionBL provideParrafoImpresionBL(ParrafoImpresionRepositorio parrafoImpresionRepositorio) {
        return new ParrafoImpresionBL(parrafoImpresionRepositorio);
    }

    @Provides
    SeccionImpresionBL provideSeccionImpresionBL(SeccionImpresionRepositorio seccionImpresionRepositorio) {
        return new SeccionImpresionBL(seccionImpresionRepositorio);
    }

    @Provides
    ProgramacionCorreriaBL provideProgramacionCorreriaBL(ProgramacionCorreriaRepositorio programacionCorreriaRepositorio) {
        return new ProgramacionCorreriaBL(programacionCorreriaRepositorio);
    }

    @Provides
    FirmarLaboresYTareas provideFirmarLaboresYTareas(TareaXOrdenTrabajoRepositorio tareaXOrdenTrabajoRepositorio,
                                                     LaborXOrdenTrabajoRepositorio laborXOrdenTrabajoRepositorio,
                                                     CorreriaRepositorio correriaRepositorio,
                                                     OrdenTrabajoRepositorio ordenTrabajoRepositorio) {
        return new FirmarLaboresYTareas(tareaXOrdenTrabajoRepositorio, laborXOrdenTrabajoRepositorio,
                correriaRepositorio, ordenTrabajoRepositorio);
    }

    @Provides
    NotificacionOrdenTrabajoBL notificacionOrdenTrabajoBL(OrdenTrabajoRepositorio ordenTrabajoRepositorio,
                                                          TareaXOrdenTrabajoRepositorio tareaXOrdenTrabajoRepositorio,
                                                          LaborRepositorio laborRepositorio,
                                                          FirmarLaboresYTareas firmarLaboresYTareas) {
        return new NotificacionOrdenTrabajoBL(ordenTrabajoRepositorio, tareaXOrdenTrabajoRepositorio,
                laborRepositorio, firmarLaboresYTareas);
    }

}