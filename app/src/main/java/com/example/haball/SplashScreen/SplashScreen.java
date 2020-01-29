package com.example.haball.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.haball.Order.DistributorOrder_ItemSelection;
import com.example.haball.Order.DistributorOrder_OnaddtoCart;
import com.example.haball.Order.DistributorOrder_ShopSelection;
import com.example.haball.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, DistributorOrder_OnaddtoCart.class);
                startActivity(intent);
                finish();
            }
        },2500);
    }
}
