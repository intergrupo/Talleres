package com.example.santiagolopezgarcia.talleres.data;


import com.example.santiagolopezgarcia.talleres.data.dao.acceso.PerfilDao;
import com.example.santiagolopezgarcia.talleres.data.dao.correria.ContratoDao;

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
//            lista.add(EmpresaDao.CREAR_SCRIPT);
//            lista.add(CorreriaDao.CREAR_SCRIPT);
//            lista.add(DepartamentoDao.CREAR_SCRIPT);
//            lista.add(MunicipioDao.CREAR_SCRIPT);
//            lista.add(LaborDao.CREAR_SCRIPT);
            lista.add(ContratoDao.CREAR_SCRIPT);
            lista.add(PerfilDao.CREAR_SCRIPT);
//            lista.add(UsuarioDao.CREAR_SCRIPT);
//            lista.add(EstadoDao.CREAR_SCRIPT);
//            lista.add(RangoDao.CREAR_SCRIPT);
//            lista.add(TareaDao.CREAR_SCRIPT);
//            lista.add(TipoElementoDao.CREAR_SCRIPT);
//            lista.add(UbicacionElementoDao.CREAR_SCRIPT);
//            lista.add(MarcaElementoDao.CREAR_SCRIPT);
//            lista.add(ModeloElementoDao.CREAR_SCRIPT);
//            lista.add(ElementoDao.CREAR_SCRIPT);
//            lista.add(CausaNoLecturaDao.CREAR_SCRIPT);
//            lista.add(TipoLecturaDao.CREAR_SCRIPT);
//            lista.add(ObservacionAdicionalDao.CREAR_SCRIPT);
//            lista.add(ObservacionConsumoDao.CREAR_SCRIPT);
//            lista.add(ElementoAforoDao.CREAR_SCRIPT);
//            lista.add(UnidadDao.CREAR_SCRIPT);
//            lista.add(MaterialDao.CREAR_SCRIPT);
//            lista.add(MultiOpcionDao.CREAR_SCRIPT);
//            lista.add(OperacionDao.CREAR_SCRIPT);
//            lista.add(LocalidadDao.CREAR_SCRIPT);
//            lista.add(OpcionDao.CREAR_SCRIPT);
//            lista.add(ItemDao.CREAR_SCRIPT);
//            lista.add(NotificacionDao.CREAR_SCRIPT);
//            lista.add(OrdenTrabajoDao.CREAR_SCRIPT);
//            lista.add(TrabajoDao.CREAR_SCRIPT);
//            lista.add(TrabajoxContratoDao.CREAR_SCRIPT);
//            lista.add(TareaXTrabajoDao.CREAR_SCRIPT);
//            lista.add(LecturaElementoDao.CREAR_SCRIPT);
//            lista.add(TrabajoXOrdenTrabajoDao.CREAR_SCRIPT);
//            lista.add(TareaXOrdenTrabajoDao.CREAR_SCRIPT);
//            lista.add(LaborElementoDao.CREAR_SCRIPT);
//            lista.add(LaborLecturaDao.CREAR_SCRIPT);
//            lista.add(HistoriaElementoDao.CREAR_SCRIPT);
//            lista.add(LaborXTareaDao.CREAR_SCRIPT);
//            lista.add(LaborXOrdenTrabajoDao.CREAR_SCRIPT);
//            lista.add(ElementoAforoXLaborDao.CREAR_SCRIPT);
//            lista.add(LaborAforoDao.CREAR_SCRIPT);
//            lista.add(MaterialXLaborDao.CREAR_SCRIPT);
//            lista.add(LaborMaterialDao.CREAR_SCRIPT);
//            lista.add(RelacionElementoDao.CREAR_SCRIPT);
//            lista.add(ObservacionElementoDao.CREAR_SCRIPT);
//            lista.add(LaborObservacionElementoDao.CREAR_SCRIPT);
//            lista.add(ParametroPctDao.CREAR_SCRIPT);
//            lista.add(LaborPctDao.CREAR_SCRIPT);
//            lista.add(PerfilXOpcionDao.CREAR_SCRIPT);
//            lista.add(LaborCierreDao.CREAR_SCRIPT);
//            lista.add(ItemXNotificacionDao.CREAR_SCRIPT);
//            lista.add(ReporteNotificacionDao.CREAR_SCRIPT);
//            lista.add(ElementoDisponibleDao.CREAR_SCRIPT);
//            lista.add(NuevaElementoLecturaDisponibleDao.CREAR_SCRIPT);
//            lista.add(SiriusDao.CREAR_SCRIPT);
//            lista.add(ParrafoImpresionDao.CREAR_SCRIPT);
//            lista.add(SeccionImpresionDao.CREAR_SCRIPT);
//            lista.add(ProgramacionCorreriaDao.CREAR_SCRIPT);
//            lista.add(ConceptoDao.CREAR_SCRIPT );
//            lista.add(GrupoLecturaDao.CREAR_SCRIPT );
//            lista.add(ImpresionDao.CREAR_SCRIPT );
//            lista.add(LaborConceptoDao.CREAR_SCRIPT );
//            lista.add(RangoFacturacionDao.CREAR_SCRIPT );
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