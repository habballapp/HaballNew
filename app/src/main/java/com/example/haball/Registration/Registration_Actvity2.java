package com.example.haball.Registration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.haball.Distributor.Distributor_Dashboard;
import com.example.haball.R;

public class Registration_Actvity2 extends AppCompatActivity {

    private Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__actvity2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.action_bar_main, null);

        actionBar.setCustomView(customView);
        actionBar.setDisplayShowCustomEnabled(true);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Registration_Actvity2.this, Distributor_Dashboard.class);
                startActivity(i);
            }
        });
    }
}
