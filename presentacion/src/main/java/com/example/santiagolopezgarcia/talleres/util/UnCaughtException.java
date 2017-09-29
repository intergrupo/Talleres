package com.example.santiagolopezgarcia.talleres.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.StatFs;
import android.util.Log;

import com.example.dominio.administracion.TalleresBL;
import com.example.dominio.modelonegocio.Talleres;
import com.example.santiagolopezgarcia.talleres.view.activities.LoginActivity;
import com.example.utilidades.helpers.StringHelper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class UnCaughtException implements Thread.UncaughtExceptionHandler {


    @Inject
    TalleresBL talleresBL;
    private Context context;
    private static Context context1;
    private ContenedorDependencia dependencia;

    public UnCaughtException(Context ctx) {
        context = ctx;
        context1 = ctx;
        dependencia = new ContenedorDependencia(((Activity) ctx).getApplication());
        dependencia.getContenedor().build().inject(this);
    }

    private StatFs getStatFs() {
        File path = Environment.getDataDirectory();
        return new StatFs(path.getPath());
    }

    private long getAvailableInternalMemorySize(StatFs stat) {
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    private long getTotalInternalMemorySize(StatFs stat) {
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    private void addInformation(StringBuilder message) {
        message.append("Locale: ").append(Locale.getDefault()).append('\n');
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi;
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            message.append("Version: ").append(pi.versionName).append('\n');
            message.append("Package: ").append(pi.packageName).append('\n');
        } catch (Exception e) {
            Log.e("CustomExceptionHandler", "Error", e);
            message.append("Could not get Version information for ").append(
                    context.getPackageName());
        }
        message.append("Phone Model ").append(android.os.Build.MODEL)
                .append('\n');
        message.append("Android Version : ")
                .append(android.os.Build.VERSION.RELEASE).append('\n');
        message.append("Board: ").append(android.os.Build.BOARD).append('\n');
        message.append("Brand: ").append(android.os.Build.BRAND).append('\n');
        message.append("Device: ").append(android.os.Build.DEVICE).append('\n');
        message.append("Host: ").append(android.os.Build.HOST).append('\n');
        message.append("ID: ").append(android.os.Build.ID).append('\n');
        message.append("Model: ").append(android.os.Build.MODEL).append('\n');
        message.append("Product: ").append(android.os.Build.PRODUCT)
                .append('\n');
        message.append("Type: ").append(android.os.Build.TYPE).append('\n');
        StatFs stat = getStatFs();
        message.append("Total Internal memory: ")
                .append(getTotalInternalMemorySize(stat)).append('\n');
        message.append("Available Internal memory: ")
                .append(getAvailableInternalMemorySize(stat)).append('\n');
    }

    public void uncaughtException(Thread t, Throwable e) {
        try {
            StringBuilder report = new StringBuilder();
            Date curDate = new Date();
            report.append("Error Report collected on : ")
                    .append(curDate.toString()).append('\n').append('\n');
            report.append("Informations :").append('\n');
            addInformation(report);
            report.append('\n').append('\n');
            report.append("Stack:\n");
            final Writer result = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(result);
            e.printStackTrace(printWriter);
            report.append(result.toString());
            printWriter.close();
            report.append('\n');
            report.append("**** End of current Report ***");
            Log.e(UnCaughtException.class.getName(),
                    "Error while sendErrorMail" + report);
            sendErrorMail(report);
        } catch (Throwable ignore) {
            Log.e(UnCaughtException.class.getName(),
                    "Error while sending error e-mail", ignore);
        }
    }


    public void sendErrorMail(final StringBuilder errorContent) {

        final Talleres sirius = talleresBL.cargarPrimerRegistro();

        try {
            StringHelper.registrarLog(errorContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        new Thread() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                Looper.prepare();
                builder.setTitle("Lo sentimos...!");
                builder.create();
//                builder.setNegativeButton("Reportar",
//                        (dialog, which) -> {
//                            Intent sendIntent = new Intent(
//                                    Intent.ACTION_SEND);
//                            String subject = sirius.getNumeroTerminal() + " - " +
//                                    context.getString(R.string.version) + ", Sirius se ha detenido! Revisa!";
//                            StringBuilder body = new StringBuilder("Cordial saludo,");
//                            body.append('\n').append('\n');
//                            body.append(errorContent).append('\n')
//                                    .append('\n');
//                            // sendIntent.setType("text/plain");
//                            sendIntent.setType("message/rfc822");
//                            sendIntent.putExtra(Intent.EXTRA_EMAIL,
//                                    new String[]{"intergruposirius@gmail.com"});
//                            sendIntent.putExtra(Intent.EXTRA_TEXT,
//                                    body.toString());
//                            sendIntent.putExtra(Intent.EXTRA_SUBJECT,
//                                    subject);
//                            sendIntent.setType("message/rfc822");
//                            context1.startActivity(sendIntent);
//                            System.exit(0);
//
//                        });
                builder.setPositiveButton("Aceptar",
                        (dialog, which) -> reiniciar());
                builder.setMessage("Ups, Sirius se ha detenido");
                builder.show();
                Looper.loop();
            }
        }.start();
    }

    private void reiniciar() {
        Intent mStartActivity = new Intent(context, LoginActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis(), mPendingIntent);
        System.exit(0);
    }
}