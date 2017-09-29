package com.example.santiagolopezgarcia.talleres.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class DelayedProgressDialog extends ProgressDialog {
    private static Handler dialogHandler;
    private Runnable runner;

    static {
        dialogHandler = new Handler();
    }


    public DelayedProgressDialog(Context context) {
        super(context);
    }

    public static ProgressDialog show (Context c, CharSequence msg, long afterDelayMilliSec)
    {
        final DelayedProgressDialog pd = new DelayedProgressDialog(c);
        pd.setMessage(msg);
        pd.setCancelable(false);
        pd.runner = () -> {
            try {
                pd.show();
            }
            catch (Exception e) {
                /* do nothing */
            }
        };
        dialogHandler.postDelayed(pd.runner, afterDelayMilliSec);
        return pd;
    }

    @Override
    public void cancel() {
        dialogHandler.removeCallbacks(runner);
        super.cancel();
    }
}