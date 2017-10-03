package com.example.santiagolopezgarcia.talleres.view.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.dominio.modelonegocio.OrdenTrabajo;
import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.presenters.MapaOrdenesTrabajoPresenter;
import com.example.santiagolopezgarcia.talleres.presenters.Presenter;
import com.example.santiagolopezgarcia.talleres.util.DatosCache;
import com.example.santiagolopezgarcia.talleres.util.googlemaps.ResponseGoogleMap;
import com.example.santiagolopezgarcia.talleres.view.interfaces.BaseView;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IMapaOrdenesTrabajoView;
import com.example.santiagolopezgarcia.talleres.view.popups.AlertaPopUp;
import com.example.santiagolopezgarcia.talleres.view.popups.DetalleMapaOrdenTrabajo;
import com.example.utilidades.helpers.GpsHelper;
import com.example.utilidades.perifericos.GPSTracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class MapaOrdenesTrabajo extends BaseActivity<MapaOrdenesTrabajoPresenter> implements OnMapReadyCallback {

    List<OrdenTrabajo> listaOrdenesTrabajo;
    private GPSTracker gps;
    private GpsHelper gpsHelper;

    GoogleMap googleMap;
    double[] CONFIG_MAP_COLOMBIA = {
            4.5238, //Lat
            -72.7947, //Lng
            5 //Zoom
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_ordenes_trabajo);
        iniciarActivity();
    }

    private void iniciarActivity() {
        configurarActionBar();
        SupportMapFragment mapOrdenesTrabajo = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapOrdenesTrabajo);
        mapOrdenesTrabajo.getMapAsync(this);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        gps = new GPSTracker(this);
        gpsHelper = new GpsHelper(this);
        gpsHelper.iniciar();
    }

    private void configurarActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (gpsHelper.gpsActivo()) {
            mostrarAlertaDeshabilitarGps();
        } else {
            MapaOrdenesTrabajo.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            finish();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        this.googleMap.setMyLocationEnabled(true);
        ubicarEnColomabia();
        //escucharCambioDeGPS();
        mostrarOrdenesTrabajo();
        asignarEventoMarkerClick();
    }

    private void ubicarEnColomabia() {
        LatLng latLng = new LatLng(CONFIG_MAP_COLOMBIA[0], CONFIG_MAP_COLOMBIA[1]);
        int zoom = (int) CONFIG_MAP_COLOMBIA[2];
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        this.googleMap.animateCamera(cameraUpdate);
    }

    public void escucharCambioDeGPS() {
        GoogleMap.OnMyLocationChangeListener onMyLocationChangeListener = location -> {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 14));
            googleMap.setOnMyLocationChangeListener(null);
        };
        googleMap.setOnMyLocationChangeListener(onMyLocationChangeListener);
    }

    public void mostrarOrdenesTrabajo() {
        Thread thread = new Thread(() -> {
            try {
                listaOrdenesTrabajo = DatosCache.getListaOrdenTrabajoFiltrado();
                if (listaOrdenesTrabajo.isEmpty()) {
                    listaOrdenesTrabajo = DatosCache.getListaOrdenTrabajo();
                }
                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.point_oficinas_de_atencion);
                LatLng gpsPrimeraOrdenTrabajo = null;
                LatLng gpsActual;
                LatLng gpsAnterior = null;
                for (OrdenTrabajo ordenTrabajo : listaOrdenesTrabajo) {
                    if (!ordenTrabajo.getGps().isEmpty()) {
                        String[] parts = ordenTrabajo.getGps().split(",");
                        String latitude = parts[0];
                        String longitude = parts[1];
                        gpsActual = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                        if (gpsPrimeraOrdenTrabajo == null) {
                            gpsPrimeraOrdenTrabajo = gpsActual;
                        }
                        MarkerOptions markerOptions = new MarkerOptions()
                                .title(ordenTrabajo.getNombre())
                                .snippet(ordenTrabajo.getDireccion())
                                .icon(icon)
                                .position(gpsActual);
                        runOnUiThread(() -> this.googleMap.addMarker(markerOptions));
                        if (gpsAnterior != null) {
                            //iniciarComunicacionGoogle(gpsAnterior, gpsActual);
                            PolylineOptions polylineOptions = new PolylineOptions();
                            polylineOptions.add(gpsAnterior, gpsActual).color(Color.RED);
                            runOnUiThread(() -> this.googleMap.addPolyline(polylineOptions));
                        }
                        gpsAnterior = gpsActual;
                    }
                }
                final LatLng finalGpsPrimeraOrdenTrabajo = gpsPrimeraOrdenTrabajo != null ?
                        gpsPrimeraOrdenTrabajo : new LatLng(gps.getLocation().getLatitude(),
                        gps.getLocation().getLongitude());
                runOnUiThread(() -> googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(finalGpsPrimeraOrdenTrabajo, 15)));
                ocultarBarraProgreso();
            } catch (Exception exc) {
                ocultarBarraProgreso();
                try {
                    registrarLog(exc.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    private void iniciarComunicacionGoogle(LatLng origin, LatLng destination) {
        Thread thread = new Thread(() -> {
            getRouteByOriginDestin(origin, destination);
        });
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    private void pintarPuntos(ResponseGoogleMap rutaGoogleRest) {
        runOnUiThread(() -> {
            List<LatLng> lista = decodePoly(rutaGoogleRest.routes.get(0).overview_polyline.points);
            PolylineOptions polylineOptions = new PolylineOptions();
            for (LatLng latLng : lista) {
                polylineOptions.add(latLng).color(Color.RED);
            }
            this.googleMap.addPolyline(polylineOptions);
        });
    }

    private void getRouteByOriginDestin(LatLng origin, LatLng destination) {
        ResponseGoogleMap ruta = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet del = new HttpGet(
                "http://maps.googleapis.com/maps/api/directions/json?origin=" + origin.latitude + ",%20" + origin.longitude + "&destination=" + destination.latitude + ",%20" + destination.longitude);
        del.setHeader("content-type", "application/json");
        try {
            HttpResponse resp = httpClient.execute(del);
            String respStr = EntityUtils.toString(resp.getEntity());
            Gson gson = new GsonBuilder().create();
            ruta = gson.fromJson(respStr, ResponseGoogleMap.class);
        } catch (Exception ex) {
            Log.e("ServicioRest", "Error!", ex);
            try {
                registrarLog(ex.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        pintarPuntos(ruta);
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((double) lat / 1E5, (double) lng / 1E5);
            poly.add(p);
        }
        return poly;
    }

    public OrdenTrabajo obtenerOrdenTrabajo(LatLng latLng) {
        for (OrdenTrabajo ordenTrabajo : listaOrdenesTrabajo) {
            if (ordenTrabajo.getGps().equals(latLng.longitude + "," + latLng.latitude)) {
                return ordenTrabajo;
            }
        }
        return null;
    }

    private void asignarEventoMarkerClick() {
        googleMap.setOnMarkerClickListener(marker -> {
            OrdenTrabajo ordenTrabajo = obtenerOrdenTrabajo(marker.getPosition());
            if (ordenTrabajo != null) {
                DetalleMapaOrdenTrabajo busquedaOrdenTrabajoPopup = new DetalleMapaOrdenTrabajo();
                busquedaOrdenTrabajoPopup.setOrdenTrabajo(ordenTrabajo);
                FragmentManager fragmentManager = getSupportFragmentManager();
                busquedaOrdenTrabajoPopup.show(fragmentManager, "");
            }
            return false;
        });
    }


    private void mostrarAlertaDeshabilitarGps(){
        AlertaPopUp alertaPopUp = new AlertaPopUp();
        alertaPopUp.setContext(this);
        alertaPopUp.setTitle(R.string.titulo_gps_habilitado);
        alertaPopUp.setMessage(R.string.alerta_gps_habilitado);
        alertaPopUp.setPositiveButton("Aceptar", (dialog, which) -> {
            gpsHelper.desconectarGPS();
            MapaOrdenesTrabajo.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            dialog.dismiss();
            finish();
        });
        alertaPopUp.show();
    }

    @Override
    public void ocultarBarraProgreso() {
        super.ocultarBarraProgreso();
    }
}