package com.example.utilidades.helpers;

/**
 * Created by miguelangelarenascorrea on 7/12/16.
 */

public class MemoryHelper {

    public static String memoriaDipositivo(){
        long freeSize = 0L;
        long totalSize = 0L;
        try {
            Runtime info = Runtime.getRuntime();
            freeSize = info.freeMemory();
            totalSize = info.totalMemory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Memoria Total: " + totalSize + " Memoria Disponible: " + freeSize;
    }
}
