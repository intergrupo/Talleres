package com.example.santiagolopezgarcia.talleres.view;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public enum Rutina {

    NINGUNA,
    LABORAFORO,
    LABORELEMENTO,
    LABORMATERIAL,
    NUEVAORDENTRABAJO,
    NUEVATAREA,
    ELEMENTOS,
    CANCELARORDENTRABAJO,
    PRUEBA_PCT,
    PRUEBA_TRANSFORMADOR_CORRIENTE,
    PRUEBA_TRANSFORMADOR_POTENCIA,
    PRUEBA_PATRON,
    INFORMACIONTECNICA,
    NOTIFICACION,
    INDICADORES_DE_CAMBIO,
    NUEVO_TRABAJO_TAREA_LECTURA,
    MARCAR,
    VOLVER,
    ADMINISTRACION,
    CAMBIOCLAVE,
    CORRERIATAREA,
    CORRERIAORDENTRABAJO,
    IMPRIMIRDLLETAREAS,
    IMPRESION,
    DESCARGA_PARCIAL,
    ENVIODIRECTOPARCIAL,
    ENVIOWEBPARCIAL,
    TELEMEDIDA,
    FENS,
    PREPAGO,
    SINCRONIZACION,
    IMPRIMIRDLLEORDENTRABAJO,
    VALORFACTURAENTREGA,
    ELEMENTOSOT;


    public static Rutina getRutina(String nombre) {
        switch (nombre) {
            case "LaborAforo":
                return LABORAFORO;
            case "LaborElemento":
                return LABORELEMENTO;
            case "LaborMaterial":
                return LABORMATERIAL;
            case "NuevaTarea":
                return NUEVAORDENTRABAJO;
            case "NuevoTrabajoTarea":
                return NUEVATAREA;
            case "ElemConsOt":
                return ELEMENTOSOT;
            case "ElemConstLectura":
                return ELEMENTOS;
            case "CancelarOrden":
            case "CerrarOT2":
                return CANCELARORDENTRABAJO;
            case "InformacionTecnica":
                return INFORMACIONTECNICA;
            case "LaborNotificacion":
                return NOTIFICACION;
            case "PruebaPct":
                return PRUEBA_PCT;
            case "PruebaTC":
                return PRUEBA_TRANSFORMADOR_CORRIENTE;
            case "PruebaTP":
                return PRUEBA_TRANSFORMADOR_POTENCIA;
            case "PruebaPatron":
                return PRUEBA_PATRON;
            case "RetBookMark":
                return MARCAR;
            case "EnvBookMark":
                return VOLVER;
            case "Administracion":
                return ADMINISTRACION;
            case "CorreriaTarea":
                return CORRERIATAREA;

            case "CorreriaOrdenTrabajo":
                return CORRERIAORDENTRABAJO;
            case "CambioClave":
                return CAMBIOCLAVE;
            case "Constlect":
                return IMPRESION;
            case "ImprimirDlleTareas":
                return IMPRIMIRDLLETAREAS;
            case "DescargaParcial":
                return DESCARGA_PARCIAL;
            case "EnvioDirectoParcial":
                return ENVIODIRECTOPARCIAL;
            case "EnvioWebParcial":
                return ENVIOWEBPARCIAL;
            case "Fens":
                return FENS;
            case "Telemedida":
                return TELEMEDIDA;
            case "NexoPin":
                return PREPAGO;
            case "Sincronizacion":
                return SINCRONIZACION;
            case "ImprimirDlleCorreria":
                return IMPRIMIRDLLEORDENTRABAJO;
            case "ValFactEntrega":
                return VALORFACTURAENTREGA;
            default:
                return NINGUNA;
        }
    }
}