package com.example.santiagolopezgarcia.talleres.util;

import android.content.Context;
import android.widget.Toast;

import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerUnavailableException;
import com.honeywell.aidc.UnsupportedPropertyException;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class LectorCodigoBarra implements BarcodeReader.BarcodeListener {

    private AidcManager manager;
    private com.honeywell.aidc.BarcodeReader reader;
    private OnLecturaCodigoBarra onLecturaCodigoBarra;
    private Context context;

    public LectorCodigoBarra(Context context) {
        if (context instanceof OnLecturaCodigoBarra) {
            this.onLecturaCodigoBarra = (OnLecturaCodigoBarra) context;
        }
        this.context = context;
        iniciarScaner();
    }

    public LectorCodigoBarra(Context context, OnLecturaCodigoBarra onLecturaCodigoBarra) {
        this.onLecturaCodigoBarra = onLecturaCodigoBarra;
        this.context = context;
        iniciarScaner();
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        this.onLecturaCodigoBarra.onLeerCodigoBarra(barcodeReadEvent.getBarcodeData(), barcodeReadEvent.getCodeId());
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {
        this.onLecturaCodigoBarra.onErrorCodigoBarra(barcodeFailureEvent.toString());
        //Toast.makeText(context, "Por favor encienda el lector", Toast.LENGTH_LONG).show();
    }

    public void cerrar() {
        if (reader != null) {
            reader.close();
            reader = null;
        }
    }

    public interface OnLecturaCodigoBarra {
        void onLeerCodigoBarra(String codigoBarras, String tipoLectura);

        void onErrorCodigoBarra(String error);
    }

    private void iniciarScaner() {
        AidcManager.create(context, aidcManager -> {
            manager = aidcManager;
            reader = manager.createBarcodeReader();
            try {
                reader.setProperty(BarcodeReader.PROPERTY_CODE_128_ENABLED, true);
                reader.setProperty(BarcodeReader.PROPERTY_DATAMATRIX_ENABLED, true);
                reader.setProperty(BarcodeReader.PROPERTY_PDF_417_ENABLED, true);
                reader.setProperty(BarcodeReader.PROPERTY_CODE_39_ENABLED, true);
                reader.setProperty(BarcodeReader.PROPERTY_UPC_A_COUPON_CODE_MODE_ENABLED, true);
                reader.setProperty(BarcodeReader.PROPERTY_DATA_PROCESSOR_LAUNCH_BROWSER, false);
                try {
                    reader.claim();
                } catch (ScannerUnavailableException e) {
                    e.printStackTrace();
                }
                reader.setProperty(BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE, BarcodeReader.TRIGGER_CONTROL_MODE_AUTO_CONTROL);
            } catch (UnsupportedPropertyException e) {
                Toast.makeText(context, "Failed to apply properties",
                        Toast.LENGTH_SHORT).show();
            }
            reader.addBarcodeListener(this);
        });
    }
}
