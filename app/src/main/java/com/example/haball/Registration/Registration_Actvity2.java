package com.example.haball.Registration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.haball.Distributor.DistributorDashboard;

import com.example.haball.R;

public class Registration_Actvity2 extends AppCompatActivity {

    private Button btn_register;
    private ImageButton btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__actvity2);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));

        LayoutInflater inflater = LayoutInflater.from(this);

        View customView = inflater.inflate(R.layout.action_bar_main, null);

        bar.setCustomView(customView);
        bar.setDisplayShowCustomEnabled(true);
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        bar.setTitle("");

        btn_back = (ImageButton)customView.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Registration_Actvity2.this, DistributorDashboard.class);
                startActivity(i);
            }
        });
    }
}
