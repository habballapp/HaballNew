package com.example.haball.Payment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.haball.R;

public class Proof_Of_Payment_Form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proof__of__payment__form);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.custom_actionbar, null);

        actionBar.setCustomView(customView);
        actionBar.setDisplayShowCustomEnabled(true);
        Button rl = (Button) findViewById(R.id.btn_upload);

        rl.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                final AlertDialog alertDialog = new AlertDialog.Builder(Proof_Of_Payment_Form.this).create();
                LayoutInflater inflater = LayoutInflater.from(Proof_Of_Payment_Form.this);
                View view_popup = inflater.inflate(R.layout.file_upload, null);
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

