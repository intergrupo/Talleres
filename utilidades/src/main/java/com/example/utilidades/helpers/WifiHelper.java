package com.example.utilidades.helpers;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class WifiHelper {

    private Context context;

    public WifiHelper(Context context) {
        this.context = context;
    }

    public boolean estaConectadoWifi() {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }

    public void activarODesactivarWifi(boolean estado) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(estado);
    }

    public boolean tieneActivoWifi() {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }

    public void abrirConfiguracionWifi() {
        context.startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
    }
}
