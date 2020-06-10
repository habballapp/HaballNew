package com.example.haball.Select_User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.haball.Distribution_Login.Distribution_Login;
import com.example.haball.R;
import com.example.haball.Retailer_Login.RetailerLogin;

import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.ActionBar;

public class Register_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);
        LinearLayout ll_main_background;
        RelativeLayout rl_distributor = findViewById(R.id.rl_distributor);
        RelativeLayout rl_retailor = findViewById(R.id.rl_retailor);

        rl_distributor.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(Register_Activity.this, Distribution_Login.class);
                startActivity(intent);
            }

        });
        rl_retailor.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(Register_Activity.this, RetailerLogin.class);
                startActivity(intent);
            }

        });


    }

}
