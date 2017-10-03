package com.example.santiagolopezgarcia.talleres.view.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dominio.bussinesslogic.administracion.TalleresBL;
import com.example.dominio.bussinesslogic.correria.CorreriaBL;
import com.example.dominio.modelonegocio.ArchivoAdjunto;
import com.example.dominio.modelonegocio.Correria;
import com.example.dominio.modelonegocio.ParametrosOpcionAdministracion;
import com.example.dominio.modelonegocio.Talleres;
import com.example.dominio.bussinesslogic.notificacion.ReporteNotificacionBL;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.SiriusApp;
import com.example.santiagolopezgarcia.talleres.helpers.Constantes;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;
import com.example.santiagolopezgarcia.talleres.util.ControladorArchivosAdjuntos;
import com.example.santiagolopezgarcia.talleres.view.activities.AdministracionActivity;
import com.example.santiagolopezgarcia.talleres.view.activities.DescargaDatosActivity;
import com.example.santiagolopezgarcia.talleres.view.activities.EnvioDirectoActivity;
import com.example.santiagolopezgarcia.talleres.view.activities.EnvioWebActivity;
import com.example.santiagolopezgarcia.talleres.view.interfaces.CorreriaActiva;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IAdministracionView;
import com.example.santiagolopezgarcia.talleres.view.popups.AlertaPopUp;
import com.example.utilidades.helpers.DateHelper;
import com.example.utilidades.helpers.StringHelper;
import com.example.utilidades.helpers.WifiHelper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class CorreriaAdministracionAdapter extends RecyclerView.Adapter<CorreriaAdministracionAdapter.CorreriaHolder> {

    @Inject
    CorreriaBL correriaBL;
    @Inject
    ReporteNotificacionBL reporteNotificacionBL;
    @Inject
    TalleresBL talleresBL;
    private WifiHelper wifiHelper;
    private Talleres talleres;
    private LayoutInflater inflater;
    private List<Correria> listaCorrerias = Collections.emptyList();
    private ContenedorDependencia dependencia;
    private CorreriaActiva<Correria> correriaActiva;
    private IAdministracionView iAdministracionView;
    private static final int DURACION = 300;
    private static final int VIA_BLUETOOTH = 1;
    private ControladorArchivosAdjuntos controladorArchivosAdjuntos;
    private Context context;
    private SiriusApp application;
    private View ultimaPosicionMasInfo = null;
    private ParametrosOpcionAdministracion parametrosOpcionAdministracion;
    private ProgressDialog progressDialog;

    public CorreriaAdministracionAdapter(SiriusApp application, Context context,
                                         List<Correria> listaCorrerias,
                                         ParametrosOpcionAdministracion parametrosOpcionAdministracion) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.parametrosOpcionAdministracion = parametrosOpcionAdministracion;
        this.listaCorrerias = listaCorrerias;
        if (context instanceof CorreriaActiva) {
            correriaActiva = (CorreriaActiva) context;
        }
        if (context instanceof IAdministracionView) {
            iAdministracionView = (IAdministracionView) context;
        }
        this.application = application;
        dependencia = new ContenedorDependencia(application);
        dependencia.getContenedor().build().inject(this);
        controladorArchivosAdjuntos = new ControladorArchivosAdjuntos();
        talleres = talleresBL.consultarTerminalXCodigo(application.getCodigoTerminal());
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public CorreriaHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_correria_admin, parent, false);
        CorreriaHolder holder = new CorreriaHolder(view);
        //application = (SiriusApp) context.getApplicationContext();
        talleres = talleresBL.consultarTerminalXCodigo(application.getCodigoTerminal());
        wifiHelper = new WifiHelper(context);
        return holder;
    }

    public void adicionarCorreria(Correria correria) {
        listaCorrerias.add(correria);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(CorreriaHolder holder, int position) {

        Correria correria = listaCorrerias.get(position);
        asignarDatosCorreria(holder, correria, position);
    }

    private void asignarDatosCorreria(CorreriaHolder holder, Correria correria, int posicion) {
        holder.itemView.setTag(correria);

        if (correria.getCodigoCorreria().equals(application.getCodigoCorreria())) {
            holder.cvItemCorreria.setCardBackgroundColor(context.getResources().getColor(R.color.grisclaro2));
        }

        holder.tvCodigo.setText(correria.getCodigoCorreria());
        holder.tvDescripcion.setText(correria.getDescripcion());
        holder.tvInicioLabor.setText(mostrarFecha(correria.getFechaInicioCorreria()));
        holder.tvUltimaLabor.setText(mostrarFecha(correria.getFechaUltimaCorreria()));
        holder.tvFinJornada.setText(mostrarFecha(correria.getFechaFinJornada()));
        holder.tvUltimaCarga.setText(mostrarFecha(correria.getFechaCarga()));
        holder.tvUltimaDescarga.setText(mostrarFecha(correria.getFechaUltimaDescarga()));
        holder.tvUltimoEnvio.setText(mostrarFecha(correria.getFechaUltimoEnvio()));
        holder.tvUltimoRecibo.setText(mostrarFecha(correria.getFechaUltimoRecibo()));

        if (!application.getUsuario().esUsuarioValido()
                || parametrosOpcionAdministracion.isAdminPersonalizada()) {
            holder.lyEliminarCorreria.setVisibility(View.GONE);
        }

        holder.itemView.setOnLongClickListener(view -> {
            if (correriaActiva != null) {
                correriaActiva.itemCorreriaSeleccionada(correria, holder.itemView);
            }
            return true;
        });
        holder.asignarEventos(posicion, correria);
//        holder.validarCorreriaActiva();
    }

    private String mostrarFecha(String fechaAConvertir) {
        Date fecha = null;
        try {
            fecha = DateHelper.convertirStringADate(fechaAConvertir, DateHelper.TipoFormato.yyyyMMddTHHmmss);
        } catch (ParseException e) {
            e.printStackTrace();
            try {
                StringHelper.registrarLog(e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

        return mostrarFecha(fecha);
    }

    private String mostrarFecha(Date fechaAConvertir) {

        if (fechaAConvertir != null) {
            try {
                return DateHelper.convertirDateAString(fechaAConvertir, DateHelper.TipoFormato.yyyyMMddHHmmss);
            } catch (ParseException e) {
                e.printStackTrace();
                try {
                    StringHelper.registrarLog(e.getMessage());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return listaCorrerias.size();
    }

    public class CorreriaHolder extends RecyclerView.ViewHolder {

        TextView tvCodigo;
        TextView tvDescripcion;
        TextView tvInicioLabor;
        TextView tvUltimaLabor;
        TextView tvFinJornada;
        TextView tvUltimaCarga;
        TextView tvUltimaDescarga;
        TextView tvUltimoEnvio;
        TextView tvUltimoRecibo;
        LinearLayout lyEnvioDirecto;
        LinearLayout lyDescargaCentral;
        LinearLayout lyEnvioWeb;
        LinearLayout lyEliminarCorreria;
        Button btnMasInfo;
        LinearLayout lyMasInfo;
        CardView cvItemCorreria;

        public CorreriaHolder(View itemView) {

            super(itemView);

            tvCodigo = (TextView) itemView.findViewById(R.id.tvCodigoCorreriaAdmin);
            tvDescripcion = (TextView) itemView.findViewById(R.id.tvDescripcionCorreriaAdmin);
            tvInicioLabor = (TextView) itemView.findViewById(R.id.tvFechaInicioLabor);
            tvUltimaLabor = (TextView) itemView.findViewById(R.id.tvFechaUltimaLabor);
            tvFinJornada = (TextView) itemView.findViewById(R.id.tvFechaFinJornada);
            tvUltimaCarga = (TextView) itemView.findViewById(R.id.tvFechaUltimaCarga);
            tvUltimaDescarga = (TextView) itemView.findViewById(R.id.tvFechaUltimaDescarga);
            tvUltimoEnvio = (TextView) itemView.findViewById(R.id.tvFechaUltimoEnvio);
            tvUltimoRecibo = (TextView) itemView.findViewById(R.id.tvFechaUltimoRecibo);
            lyEnvioDirecto = (LinearLayout) itemView.findViewById(R.id.lyEnvioDirecto);
            lyDescargaCentral = (LinearLayout) itemView.findViewById(R.id.lyDescargaCentral);
            lyEnvioWeb = (LinearLayout) itemView.findViewById(R.id.lyEnvioWeb);
            lyEliminarCorreria = (LinearLayout) itemView.findViewById(R.id.lyEliminarCorreria);
            btnMasInfo = (Button) itemView.findViewById(R.id.btnMasInfoCorreria);
            lyMasInfo = (LinearLayout) itemView.findViewById(R.id.lyContenedorMasInfo);
            cvItemCorreria = (CardView) itemView.findViewById(R.id.cvItemCorreria);
        }

        private void asignarEventos(int posicion, Correria correria) {
            btnMasInfo.setOnClickListener(v -> {
                if (lyMasInfo.getVisibility() == View.GONE) {
                    masInfo();
                } else {
                    menosInfo();
                }
                iAdministracionView.moverPosicion(itemView.getTop());
            });


            lyEnvioWeb.setOnClickListener(v -> {
                AlertaPopUp alertaPopUp = new AlertaPopUp();
                alertaPopUp.setTitle(R.string.titulo_eliminar_correria);
                alertaPopUp.setMessage(String.format("%s %s ?",
                        context.getResources().getString(R.string.desea_ejecutar_envio_web),
                        correria.getDescripcion()));
                alertaPopUp.setContext(context);
                alertaPopUp.setPositiveButton("SI", (dialog, id) -> {

                    dialog.dismiss();
                });
                alertaPopUp.setNegativeButton("NO", (dialog, id) -> {
                    dialog.dismiss();
                });

                alertaPopUp.show();
            });

            lyEliminarCorreria.setOnClickListener(v -> {
                AlertaPopUp alertaPopUp = new AlertaPopUp();
                alertaPopUp.setTitle(R.string.titulo_eliminar_correria);
                alertaPopUp.setMessage(String.format("%s %s ?",
                        context.getResources().getString(R.string.mensaje_correria_eliminar),
                        correria.getDescripcion()));
                alertaPopUp.setContext(context);
                alertaPopUp.setPositiveButton("SI", (dialog, id) -> {
                    try {
                        if (hayDatosPorDescargar(correria)) {
                            mostrarAlertaPorDescargar(correria, posicion);
                        } else {
                            eliminarCorreria(posicion, correria);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                });
                alertaPopUp.setNegativeButton("NO", (dialog, id) -> {
                    dialog.dismiss();
                });

                alertaPopUp.show();
            });

            lyDescargaCentral.setOnClickListener(v -> {

                if (talleres.getWifi().equals("S")) {
                    if (!wifiHelper.tieneActivoWifi()) {
                        wifiHelper.activarODesactivarWifi(true);
                    }
                    AlertaPopUp alertaPopUp = new AlertaPopUp();
                    alertaPopUp.setTitle(R.string.titulo_descarga_central);
                    alertaPopUp.setMessage(R.string.alerta_descarga_central);
                    alertaPopUp.setContext(context);
                    alertaPopUp.setPositiveButton("SI", (dialog, id) -> {
                        Intent intent = new Intent(context, DescargaDatosActivity.class);
                        intent.putExtra(Correria.class.getName(), correria);
                        ((AdministracionActivity) context).startActivityForResult(intent, DescargaDatosActivity.REQUEST_CODE);

                        dialog.dismiss();

                    });
                    alertaPopUp.setNegativeButton("NO", (dialog, id) -> {
                        dialog.dismiss();
                    });

                    alertaPopUp.show();
                } else {
                    Intent intent = new Intent(context, DescargaDatosActivity.class);
                    intent.putExtra(Correria.class.getName(), correria);
                    ((AdministracionActivity) context).startActivityForResult(intent, DescargaDatosActivity.REQUEST_CODE);
                }

            });

            lyEnvioDirecto.setOnClickListener(v -> {
                AlertaPopUp alertaPopUp = new AlertaPopUp();
                alertaPopUp.setTitle(R.string.titulo_envio_directo);
                alertaPopUp.setMessage(R.string.alerta_envio_directo);
                alertaPopUp.setContext(context);
                alertaPopUp.setPositiveButton("SI", (dialog, id) -> {
                    Intent intent = new Intent(context, EnvioDirectoActivity.class);
                    intent.putExtra(Correria.class.getName(), correria);
                    context.startActivity(intent);
                    dialog.dismiss();

                });
                alertaPopUp.setNegativeButton("NO", (dialog, id) -> {
                    dialog.dismiss();
                });

                alertaPopUp.show();

            });

            lyEnvioWeb.setOnClickListener(v -> {
                if (talleres.getWifi().equals("S")) {
                    if (!wifiHelper.tieneActivoWifi()) {
                        wifiHelper.activarODesactivarWifi(true);
                    }
                    AlertaPopUp alertaPopUp = new AlertaPopUp();
                    alertaPopUp.setTitle(context.getString(R.string.titulo_envio_web_popup));
                    alertaPopUp.setMessage(R.string.alerta_envio_web);
                    alertaPopUp.setContext(context);
                    alertaPopUp.setPositiveButton("SI", (dialog, id) -> {
                        Intent intent = new Intent(context, EnvioWebActivity.class);
                        intent.putExtra(Correria.class.getName(), correria);
                        ((AdministracionActivity) context).startActivityForResult(intent, EnvioWebActivity.REQUEST_CODE);
                        dialog.dismiss();

                    });
                    alertaPopUp.setNegativeButton("NO", (dialog, id) -> {
                        dialog.dismiss();
                    });

                    alertaPopUp.show();
                } else {
                    Intent intent = new Intent(context, EnvioWebActivity.class);
                    intent.putExtra(Correria.class.getName(), correria);
                    ((AdministracionActivity) context).startActivityForResult(intent, EnvioWebActivity.REQUEST_CODE);
                }

            });

        }


        private void eliminarCorreria(int posicion, Correria correria) {
            eliminarAdjuntos(correria);
            correriaBL.eliminarCorreria(correria, Constantes.traerRutaAdjuntos());
            removeAt(posicion);
            if (correria.getCodigoCorreria().equals(application.getCodigoCorreria())) {
                application.limpiarCorreria();
                iAdministracionView.refrescarDatos(application);
                iAdministracionView.irALogin(true);
            }
            iAdministracionView.refrescarDatos(application);
            if (getItemCount() == 0) {
                application.limpiarSesion();
                iAdministracionView.desactivarOpcionesCorreria(true);
            }
            application.inicializarRutas();
        }

        private void mostrarAlertaPorDescargar(Correria correria, int posicion) {
            AlertaPopUp alertaPopUp = new AlertaPopUp();
            alertaPopUp.setTitle(R.string.titulo_alerta_eliminar_correria);
            alertaPopUp.setMessage(R.string.mensaje_correria_sin_descargar);
            alertaPopUp.setContext(context);
            alertaPopUp.setPositiveButton("SI", (dialog, id) -> {
                eliminarCorreria(posicion, correria);
                dialog.dismiss();

            });
            alertaPopUp.setNegativeButton("NO", (dialog, id) -> {
                dialog.dismiss();

            });
            alertaPopUp.show();

        }

        private boolean hayDatosPorDescargar(Correria correria) throws IOException {
            if (!correria.getFechaUltimaCorreria().isEmpty()
                    && correria.getFechaUltimaDescarga().isEmpty()) {
                return true;
            }
            try {
                if (!correria.getFechaUltimaCorreria().isEmpty() && !correria.getFechaUltimaDescarga().isEmpty()) {
                    if (DateHelper.convertirStringADate(correria.getFechaUltimaCorreria(), DateHelper.TipoFormato.yyyyMMddTHHmmss).after(
                            DateHelper.convertirStringADate(correria.getFechaUltimaDescarga(),
                                    DateHelper.TipoFormato.yyyyMMddTHHmmss))) {
                        return true;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
                registrarLog(e.getMessage());
                return false;
            }
            return false;
        }

        private void menosInfo() {
            iAdministracionView.desactivarOpcionesCorreria(true);
            lyMasInfo.setVisibility(View.GONE);
            btnMasInfo.setText(R.string.titulo_mas_info);
        }

        private void masInfo() {
            iAdministracionView.desactivarOpcionesCorreria(false);
            lyMasInfo.setVisibility(View.VISIBLE);
            btnMasInfo.setText(R.string.titulo_menos_info);
            if (ultimaPosicionMasInfo != null && ultimaPosicionMasInfo != itemView) {
                (ultimaPosicionMasInfo.findViewById(R.id.lyContenedorMasInfo))
                        .setVisibility(View.GONE);
                iAdministracionView.desactivarOpcionesCorreria(false);
                ((TextView) ultimaPosicionMasInfo.findViewById(R.id.btnMasInfoCorreria)).setText(R.string.titulo_mas_info);
            }
            ultimaPosicionMasInfo = itemView;
        }

        private void eliminarAdjuntos(Correria correria) {
            List<ArchivoAdjunto> listaArchivos = reporteNotificacionBL.cargarArchivosXCodigoCorreria(correria.getCodigoCorreria());
            File archivo;
            for (ArchivoAdjunto nombreArchivo : listaArchivos) {
                archivo = controladorArchivosAdjuntos.traerArchivoEncontrado(nombreArchivo);
                if (archivo != null) {
                    archivo.delete();
                }
            }
        }

        public void removeAt(int position) {
            listaCorrerias.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listaCorrerias.size());
            notifyDataSetChanged();
        }

        public void validarCorreriaActiva() {
            if (((Correria) itemView.getTag()).getCodigoCorreria().equals(application.getCodigoCorreria())) {
                masInfo();
            }
        }

        public void registrarLog(String mensaje) throws IOException {
            StringHelper.registrarLog(mensaje);
        }
    }
}
