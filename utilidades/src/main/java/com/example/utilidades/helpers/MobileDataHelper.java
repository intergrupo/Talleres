package com.example.utilidades.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class MobileDataHelper {
    private Context context;

    public MobileDataHelper(Context context) {
        this.context = context;
    }

    public boolean estaConectado() {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return mWifi.isConnected();
    }
}
