package com.example.utilidades;

import timber.log.Timber;

/**
 * Created by juanfelipegomezvelez on 13/05/16.
 */
public class Log {

    private static String APPTAG = "SIRIUS";

    public static void iniciarDebug() {
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + " : number line " + element.getLineNumber();
            }
        });
    }

    public static void iniciarRelease() {
        Timber.plant(new ReleaseTree());
    }

    public static void info(String tag, String mensaje) {
        Timber.tag(APPTAG + " - " + tag).i(mensaje);
    }

    public static void info(String mensaje) {
        Timber.tag(APPTAG).i(mensaje);
    }

    public static void error(Throwable error, String mesanje) {
        Timber.tag(APPTAG).e(error, mesanje);
    }
}
