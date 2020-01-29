package com.example.haball.Register_Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.haball.Distribution_Login.Distribution_Login;
import com.example.haball.Language_Selection.Language_Selection;
import com.example.haball.R;
import com.example.haball.RetailerLogin;

public class Register_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);
        CardView card_retailer = findViewById(R.id.card_retailer);
        CardView card_distributor = findViewById(R.id.card_distributor);

        card_retailer.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(Register_Activity.this, RetailerLogin.class);
                startActivity(intent);
            }

        });
        card_distributor.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(Register_Activity.this, Distribution_Login.class);
                startActivity(intent);
            }

        });


    }


}
