package com.example.santiagolopezgarcia.talleres.di.modulos;

import com.example.dominio.bussinesslogic.acceso.DepartamentoRepositorio;
import com.example.dominio.bussinesslogic.acceso.MultiOpcionRepositorio;
import com.example.dominio.bussinesslogic.acceso.MunicipioRepositorio;
import com.example.dominio.bussinesslogic.administracion.OpcionRepositorio;
import com.example.dominio.bussinesslogic.administracion.PerfilRepositorio;
import com.example.dominio.bussinesslogic.administracion.PerfilXOpcionRepositorio;
import com.example.dominio.bussinesslogic.administracion.ContratoRepositorio;
import com.example.dominio.bussinesslogic.administracion.TalleresRepositorio;
import com.example.dominio.bussinesslogic.administracion.UsuarioRepositorio;
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
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.data.dao.acceso.DepartamentoDao;
import com.example.santiagolopezgarcia.talleres.data.dao.acceso.MultiOpcionDao;
import com.example.santiagolopezgarcia.talleres.data.dao.acceso.MunicipioDao;
import com.example.santiagolopezgarcia.talleres.data.dao.acceso.OpcionDao;
import com.example.santiagolopezgarcia.talleres.data.dao.acceso.PerfilDao;
import com.example.santiagolopezgarcia.talleres.data.dao.acceso.PerfilXOpcionDao;
import com.example.santiagolopezgarcia.talleres.data.dao.acceso.UsuarioDao;
import com.example.santiagolopezgarcia.talleres.data.dao.administracion.TalleresDao;
import com.example.santiagolopezgarcia.talleres.data.dao.correria.ContratoDao;
import com.example.santiagolopezgarcia.talleres.data.dao.correria.CorreriaDao;
import com.example.santiagolopezgarcia.talleres.data.dao.correria.EmpresaDao;
import com.example.santiagolopezgarcia.talleres.data.dao.correria.ProgramacionCorreriaDao;
import com.example.santiagolopezgarcia.talleres.data.dao.impresion.ParrafoImpresionDao;
import com.example.santiagolopezgarcia.talleres.data.dao.impresion.SeccionImpresionDao;
import com.example.santiagolopezgarcia.talleres.data.dao.labor.LaborDao;
import com.example.santiagolopezgarcia.talleres.data.dao.labor.LaborXOrdenTrabajoDao;
import com.example.santiagolopezgarcia.talleres.data.dao.labor.LaborXTareaDao;
import com.example.santiagolopezgarcia.talleres.data.dao.notificacion.ItemDao;
import com.example.santiagolopezgarcia.talleres.data.dao.notificacion.ItemXNotificacionDao;
import com.example.santiagolopezgarcia.talleres.data.dao.notificacion.NotificacionDao;
import com.example.santiagolopezgarcia.talleres.data.dao.notificacion.ReporteNotificacionDao;
import com.example.santiagolopezgarcia.talleres.data.dao.ordentrabajo.EstadoDao;
import com.example.santiagolopezgarcia.talleres.data.dao.ordentrabajo.OrdenTrabajoDao;
import com.example.santiagolopezgarcia.talleres.data.dao.tarea.TareaDao;
import com.example.santiagolopezgarcia.talleres.data.dao.tarea.TareaXOrdenTrabajoDao;
import com.example.santiagolopezgarcia.talleres.data.dao.tarea.TareaXTrabajoDao;
import com.example.santiagolopezgarcia.talleres.data.dao.trabajo.TrabajoDao;
import com.example.santiagolopezgarcia.talleres.data.dao.trabajo.TrabajoXContratoDao;
import com.example.santiagolopezgarcia.talleres.data.dao.trabajo.TrabajoXOrdenTrabajoDao;

import dagger.Module;
import dagger.Provides;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */

@Module
public class ModuloDominio {

    private final SiriusApp application;

    public ModuloDominio(SiriusApp application) {

        this.application = application;
    }

    @Provides
    UsuarioRepositorio provideUsuario() {
        return new UsuarioDao(application.getApplicationContext());
    }

    @Provides
    ProgramacionCorreriaRepositorio provideProgramacionCorreriaRepositorio() {
        return new ProgramacionCorreriaDao(application.getApplicationContext());
    }

    @Provides
    CorreriaRepositorio provideCorreriaRepositorio() {
        return new CorreriaDao(application.getApplicationContext());
    }

    @Provides
    OrdenTrabajoRepositorio provideOrdenTrabajoRepositorio() {
        return new OrdenTrabajoDao(application.getApplicationContext());
    }

    @Provides
    EstadoRepositorio provideEstadoRepositorio() {
        return new EstadoDao(application.getApplicationContext());
    }

    @Provides
    TareaRepositorio provideTareaRepositorio() {
        return new TareaDao(application.getApplicationContext());
    }

    @Provides
    TrabajoRepositorio provideTrabajoRepositorio() {
        return new TrabajoDao(application.getApplicationContext());
    }

    @Provides
    TareaXOrdenTrabajoRepositorio provideTareaXOrdenTrabajoRepositorio() {
        return new TareaXOrdenTrabajoDao(application.getApplicationContext());
    }

    @Provides
    TrabajoXOrdenTrabajoRepositorio provideTrabajoXOrdenTrabajoRepositorio() {
        return new TrabajoXOrdenTrabajoDao(application.getApplicationContext());
    }

    @Provides
    ContratoRepositorio provideContratoRepositorio() {
        return new ContratoDao(application.getApplicationContext());
    }

    @Provides
    PerfilRepositorio providePerfilRepositorio() {
        return new PerfilDao(application.getApplicationContext());
    }

    @Provides
    TareaXTrabajoRepositorio provideTareaXTrabajo() {
        return new TareaXTrabajoDao(application.getApplicationContext());
    }

    @Provides
    DepartamentoRepositorio provideDepartamentoRepositorio() {
        return new DepartamentoDao(application.getApplicationContext());
    }

    @Provides
    MunicipioRepositorio provideMunicipioRepositorio() {
        return new MunicipioDao(application.getApplicationContext());
    }

    @Provides
    LaborRepositorio provideLaborRepositorio() {
        return new LaborDao(application.getApplicationContext());
    }

    @Provides
    LaborXOrdenTrabajoRepositorio provideLaborXOrdenTrabajoRepositorio() {
        return new LaborXOrdenTrabajoDao(application.getApplicationContext());
    }

    @Provides
    LaborXTareaRepositorio provideLaborXTareaRepositorio() {
        return new LaborXTareaDao(application.getApplicationContext());
    }

    @Provides
    MultiOpcionRepositorio provideMultiOpcionRepositorio() {
        return new MultiOpcionDao(application.getApplicationContext());
    }

    @Provides
    OpcionRepositorio provideOpcionRepositorio() {
        return new OpcionDao(application.getApplicationContext());
    }

    @Provides
    PerfilXOpcionRepositorio providePerfilXOpcionRepositorio() {
        return new PerfilXOpcionDao(application.getApplicationContext());
    }

    @Provides
    NotificacionRepositorio provideNotificacionRepositorio() {
        return new NotificacionDao(application.getApplicationContext());
    }

    @Provides
    ItemRepositorio provideItemRepositorio() {
        return new ItemDao(application.getApplicationContext());
    }

    @Provides
    ItemXNotificacionRepositorio provideItemXNotificacionRepositorio() {
        return new ItemXNotificacionDao(application.getApplicationContext());
    }

    @Provides
    ReporteNotificacionRepositorio provideReporteNotificacionRepositorio() {
        return new ReporteNotificacionDao(application.getApplicationContext());
    }

    @Provides
    TrabajoXContratoRepositorio provideTrabajoXContratoRepositorio()
    {
        return new TrabajoXContratoDao(application.getApplicationContext());
    }

    @Provides
    EmpresaRepositorio provideEmpresaRepositorio() {
        return new EmpresaDao(application.getApplicationContext());
    }

    @Provides
    TalleresRepositorio provideTalleresRepositorio()
    {
        return new TalleresDao(application.getApplicationContext());
    }

    @Provides
    SeccionImpresionRepositorio provideSeccionImpresionRepositorio() {
        return new SeccionImpresionDao(application.getApplicationContext());
    }

    @Provides
    ParrafoImpresionRepositorio provideParrafoImpresionRepositorio() {
        return new ParrafoImpresionDao(application.getApplicationContext());
    }
}
