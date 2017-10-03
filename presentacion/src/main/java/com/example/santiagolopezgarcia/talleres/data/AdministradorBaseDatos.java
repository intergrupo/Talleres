package com.example.santiagolopezgarcia.talleres.data;


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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 6/10/15.
 */
public class AdministradorBaseDatos implements AdministradorBaseDatosInterface {

    private boolean ejecutoActualizacion;

    @Override
    public List<String> GetQuerysCreate() {
        List<String> lista = new ArrayList<>();
        if (!ejecutoActualizacion) {
            lista.add(EmpresaDao.CREAR_SCRIPT);
            lista.add(CorreriaDao.CREAR_SCRIPT);
            lista.add(DepartamentoDao.CREAR_SCRIPT);
            lista.add(MunicipioDao.CREAR_SCRIPT);
            lista.add(LaborDao.CREAR_SCRIPT);
            lista.add(ContratoDao.CREAR_SCRIPT);
            lista.add(PerfilDao.CREAR_SCRIPT);
            lista.add(UsuarioDao.CREAR_SCRIPT);
            lista.add(EstadoDao.CREAR_SCRIPT);
            lista.add(TareaDao.CREAR_SCRIPT);
            lista.add(MultiOpcionDao.CREAR_SCRIPT);
            lista.add(OpcionDao.CREAR_SCRIPT);
            lista.add(ItemDao.CREAR_SCRIPT);
            lista.add(NotificacionDao.CREAR_SCRIPT);
            lista.add(OrdenTrabajoDao.CREAR_SCRIPT);
            lista.add(TrabajoDao.CREAR_SCRIPT);
            lista.add(TrabajoXContratoDao.CREAR_SCRIPT);
            lista.add(TareaXTrabajoDao.CREAR_SCRIPT);
            lista.add(TrabajoXOrdenTrabajoDao.CREAR_SCRIPT);
            lista.add(TareaXOrdenTrabajoDao.CREAR_SCRIPT);
            lista.add(LaborXTareaDao.CREAR_SCRIPT);
            lista.add(LaborXOrdenTrabajoDao.CREAR_SCRIPT);
            lista.add(PerfilXOpcionDao.CREAR_SCRIPT);
            lista.add(ItemXNotificacionDao.CREAR_SCRIPT);
            lista.add(ReporteNotificacionDao.CREAR_SCRIPT);
            lista.add(TalleresDao.CREAR_SCRIPT);
            lista.add(ParrafoImpresionDao.CREAR_SCRIPT);
            lista.add(SeccionImpresionDao.CREAR_SCRIPT);
            lista.add(ProgramacionCorreriaDao.CREAR_SCRIPT);
        }
        return lista;
    }

    @Override
    public List<String> GetQuerysUpgrade() {
        ejecutoActualizacion = true;
        List<String> lista = new ArrayList<>();
        return lista;
    }
}