package com.example.haball;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;

public class Loader {
    private Dialog fbDialogue;
    private Context mContext;
    public Loader(Context context) {
        mContext = context;
    }

    public void showLoader() {
//        fbDialogue = new Dialog(mContext);
////        fbDialogue.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
////        fbDialogue.getWindow().setDimAmount(0);
//        fbDialogue.setContentView(R.layout.loader);
//        fbDialogue.setCancelable(false);
//        fbDialogue.show();


        fbDialogue = new Dialog(mContext, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        fbDialogue.setContentView(R.layout.loader);
        Window window = fbDialogue.getWindow();

        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        window.setBackgroundDrawableResource(R.color.dialog_back);
        fbDialogue.show();

        new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                fbDialogue.dismiss();

            }
        }.start();
    }

    public void hideLoader() {
        fbDialogue.dismiss();
    }
}
