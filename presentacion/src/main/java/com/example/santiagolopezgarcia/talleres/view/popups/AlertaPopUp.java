package com.example.santiagolopezgarcia.talleres.view.popups;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.util.ContenedorDependencia;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class AlertaPopUp extends BasePopup implements DialogInterface {

    private final String TAG = "AlertaPopup";
    private Activity context;

    OnClickListener positiveButton;
    OnClickListener negativeButton;
    int messageColor = Color.BLACK;

    @BindView(R.id.tvTituloAlerta)
    TextView textViewTitle;

    @BindView(R.id.tvMensajeAlert)
    TextView textViewMessage;

    @BindView(R.id.btnSiAlerta)
    Button buttonPositive;

    @BindView(R.id.btnNoAlerta)
    Button buttonNegative;

    CharSequence textMessage;
    int idMessage;
    CharSequence textTitle;
    int idTitle;
    CharSequence textPositiveButton;
    CharSequence textNegativeButton;


    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        inicializarInyeccionDependencias();
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_alerta);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this, dialog.getWindow().getDecorView());
        this.build();
        return dialog;
    }


    private void inicializarInyeccionDependencias() {
        dependencia = new ContenedorDependencia(getActivity().getApplication());
        dependencia.getContenedor().build().inject(this);
    }

    private void build() {
        if (this.textMessage != null) {
            this.textViewMessage.setText(this.textMessage);

        } else {
            this.textViewMessage.setText(context.getText(this.idMessage));
        }

        if (this.textTitle != null) {
            this.textViewTitle.setText(this.textTitle);

        } else {
            this.textViewTitle.setText(context.getText(this.idTitle));
        }
        this.buttonPositive.setText(this.textPositiveButton);
        this.buttonNegative.setText(this.textNegativeButton);
        this.textViewMessage.setTextColor(messageColor);
    }


    @Override
    public void cancel() {

    }

    public void setMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public void setTitle(int idTitle) {
        this.idTitle = idTitle;
    }

    public AlertaPopUp setMessage(CharSequence message) {
        textMessage = message;
        return this;
    }

    public AlertaPopUp setTitle(CharSequence title) {
        textTitle = title;
        return this;
    }

    public AlertaPopUp setPositiveButton(CharSequence text, OnClickListener clickListener) {
        this.positiveButton = clickListener;
        textPositiveButton = text;
        return this;
    }

    public AlertaPopUp setPositiveButton(OnClickListener clickListener) {
        this.positiveButton = clickListener;
        return this;
    }

    public AlertaPopUp setNegativeButton(CharSequence text, OnClickListener clickListener) {
        this.negativeButton = clickListener;
        this.textNegativeButton = text;
        return this;
    }

    public AlertaPopUp setNegativeButton(OnClickListener clickListener) {
        this.negativeButton = clickListener;
        return this;
    }

    public AlertaPopUp setContext(Context context) {
        this.context = (Activity) context;
        return this;
    }


    @OnClick(R.id.btnSiAlerta)
    protected void positiveButtonClick() {
        if (this.positiveButton != null) {
            positiveButton.onClick(this, 0);
        }
    }

    @OnClick(R.id.btnNoAlerta)
    protected void negativeButtonClick() {
        if (this.negativeButton != null) {
            this.negativeButton.onClick(this, 0);
        }
    }

    public void show() {
        if (this.context == null)
            throw new ExceptionInInitializerError("Mensaje error");
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        show(fragmentManager, TAG);


    }


    public void colorMensaje(int color) {
        messageColor = color;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
