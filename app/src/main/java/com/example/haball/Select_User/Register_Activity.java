package com.example.haball.Select_User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haball.Distribution_Login.Distribution_Login;
import com.example.haball.R;
import com.example.haball.Retailer_Login.RetailerLogin;
import com.example.haball.Retailor.Retailer_TermsAndConditionsFragment;
import com.example.haball.Retailor.Retailer_UpdatePassword;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;

public class Register_Activity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);
        Drawable background_drawable = getResources().getDrawable(R.drawable.background_logo);
        background_drawable.setAlpha(80);
        LinearLayout ll_main_background = findViewById(R.id.ll_main_background);
        ll_main_background.setBackground(background_drawable);

//        testingFunction();

        RelativeLayout rl_distributor = findViewById(R.id.rl_distributor);
        RelativeLayout rl_retailor = findViewById(R.id.rl_retailor);

        rl_distributor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Register_Activity.this, Distribution_Login.class);
                startActivity(intent);
            }

        });
        rl_retailor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Register_Activity.this, RetailerLogin.class);
                startActivity(intent);
            }

        });


    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1500);

    }

    private void testingFunction() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view_popup = inflater.inflate(R.layout.progress_bar_loader, null);
        ImageView txt_header1 = view_popup.findViewById(R.id.txt_header1);
        alertDialog.setCancelable(false);
        AnimationDrawable animationDrawable;
        ImageView mProgressBar;
        txt_header1.setImageResource(R.drawable.loader_gif);
        animationDrawable = (AnimationDrawable) txt_header1.getBackground();
        txt_header1.setVisibility(View.VISIBLE);
        animationDrawable.start();
        txt_header1.setVisibility(View.GONE);
        animationDrawable.stop();
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.setView(view_popup);


        alertDialog.show();
    }

}
