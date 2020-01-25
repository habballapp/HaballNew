package com.example.haball.Distribution_Login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.haball.Distributor.Distributor_Dashboard;
import com.example.haball.R;
import com.example.haball.Register_Activity.Register_Activity;
import com.example.haball.Registration.Registration_Activity;
import com.example.haball.Support.Support_dashboard;

public class Distribution_Login extends AppCompatActivity {

    private Button btn_login,btn_signup,btn_support;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribution__login);

        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);
        btn_support = findViewById(R.id.btn_support);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.action_bar_main, null);

        actionBar.setCustomView(customView);
        actionBar.setDisplayShowCustomEnabled(true);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Distribution_Login.this, Distributor_Dashboard.class);
                startActivity(intent);
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Distribution_Login.this, Registration_Activity.class);
                startActivity(intent);
            }
        });
        btn_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Distribution_Login.this, Support_dashboard.class);
                startActivity(intent);
            }
        });

    }
}
