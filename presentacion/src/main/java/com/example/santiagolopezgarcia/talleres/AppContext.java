package com.example.santiagolopezgarcia.talleres;

import android.content.Context;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class AppContext {

    private static Context contextoGlobal;

    public static Context getContextoGlobal() {
        return contextoGlobal;
    }

    public static void setContextoGlobal(Context contextoGlobal) {
        AppContext.contextoGlobal = contextoGlobal;
    }
}