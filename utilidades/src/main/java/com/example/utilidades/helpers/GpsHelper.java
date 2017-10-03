package com.example.utilidades.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class GpsHelper implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener {

    private Context context;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest.Builder builder;
    private PendingResult<LocationSettingsResult> result;

    public GpsHelper(Context context) {
        this.context = context;
    }

    public void iniciar(){
        conectarGoogleApiClient();
        realizarPeticionLocalizacion();
        mostrarResultado();
    }

    public boolean gpsActivo(){
        LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void conectarGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    private void realizarPeticionLocalizacion(){
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

    }

    private void mostrarResultado(){
        result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        //La configuración de GPS esta habilitada
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        //La configuración de GPS esta deshabilitada
                        //Se le muestra al usuario una ventana para habilitar
                        try {
                            //Muestra la ventada de dialogo
                            status.startResolutionForResult((Activity) context, 1000);
                        } catch (IntentSender.SendIntentException ignored) {}
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // La configuración de GPS esta deshabilitada, y no se puede habilitar.
                        //No muestra ventada de dialogo.
                        break;
                }
            }
        });
    }



    public void desconectarGPS(){
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}