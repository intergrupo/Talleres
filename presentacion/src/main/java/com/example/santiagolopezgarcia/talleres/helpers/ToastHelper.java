package com.example.santiagolopezgarcia.talleres.helpers;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.santiagolopezgarcia.talleres.R;
import com.example.utilidades.helpers.StringHelper;

import java.io.IOException;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class ToastHelper {

    public static void mostrarMensajeError(Context context, String mensaje) {
        Toast toast = Toast.makeText(context, mensaje, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundColor(context.getResources().getColor(R.color.rojo));
        TextView text = (TextView) view.findViewById(android.R.id.message);
        text.setTextAppearance(context, R.style.EstiloTextoMensaje);
        text.setTextColor(context.getResources().getColor(R.color.blanco));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50, 5, 5, 5);
        text.setLayoutParams(layoutParams);
        try {
            registrarLog(mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mostrar(toast);

    }

    public static void mostrarMensajeAdvertencia(Context context, String mensaje) {
        Toast toast = Toast.makeText(context, mensaje, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundColor(context.getResources().getColor(R.color.naranjado));
        TextView text = (TextView) view.findViewById(android.R.id.message);
        text.setTextAppearance(context, R.style.EstiloTextoMensaje);
        text.setTextColor(context.getResources().getColor(R.color.blanco));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50, 5, 5, 5);
        text.setLayoutParams(layoutParams);
        mostrar(toast);
    }

    public static void mostrarMensaje(Context context, String mensaje,String log) {
        Toast toast = Toast.makeText(context, mensaje, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundColor(context.getResources().getColor(R.color.azulclaro));
        TextView text = (TextView) view.findViewById(android.R.id.message);
        text.setTextAppearance(context, R.style.EstiloTextoMensaje);
        text.setTextColor(context.getResources().getColor(R.color.blanco));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50, 5, 5, 5);
        text.setLayoutParams(layoutParams);
        if (log.toUpperCase().equals("S")) {
            try {
                registrarLog(mensaje);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mostrar(toast);
    }

    private static void mostrar(Toast toast) {
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);
        int duracionEnMilisegundos = 4000;
        CountDownTimer toastCountDown;
        toastCountDown = new CountDownTimer(duracionEnMilisegundos, 1000) {
            public void onTick(long millisUntilFinished) {
                toast.show();
            }

            public void onFinish() {
                toast.cancel();
            }
        };
        toast.show();
        toastCountDown.start();
    }

    private static void registrarLog(String mensaje) throws IOException {
        StringHelper.registrarLog(mensaje);
    }
}