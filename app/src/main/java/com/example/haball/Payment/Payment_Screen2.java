package com.example.haball.Payment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.haball.R;

public class Payment_Screen2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__screen2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.custom_actionbar, null);

        actionBar.setCustomView(customView);
        actionBar.setDisplayShowCustomEnabled(true);
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.banking_channel);

        rl.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                final AlertDialog alertDialog = new AlertDialog.Builder(Payment_Screen2.this).create();
                LayoutInflater inflater = LayoutInflater.from(Payment_Screen2.this);
                View view_popup = inflater.inflate(R.layout.payment_details, null);
                alertDialog.setView(view_popup);
                ImageButton img_btn = view_popup.findViewById(R.id.image_button);
                img_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }

        });
    }
    }

