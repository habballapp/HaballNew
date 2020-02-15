package com.example.haball.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.haball.Distributor.DistributorDashboard;
import com.example.haball.Language_Selection.Language_Selection;
import com.example.haball.MainActivity;
import com.example.haball.Order.DistributorOrder;
import com.example.haball.Order.DistributorOrder_ItemSelection;
import com.example.haball.Order.DistributorOrder_OnaddtoCart;
import com.example.haball.Payment.DistributorPaymentRequest_CriteriaSelection;
import com.example.haball.Payment.PaymentLedger;
import com.example.haball.Payment.Payment_Screen1;
import com.example.haball.Payment.Payment_Screen2;
import com.example.haball.Payment.Payment_Screen3;
import com.example.haball.Payment.Proof_Of_Payment_Form;
import com.example.haball.R;
import com.example.haball.Distribution_Login.Distribution_Login;
import com.example.haball.Registration.Registration_Activity;
import com.example.haball.Registration.Registration_Actvity2;
import com.example.haball.Support.Support_Ticket_Form;

public class SplashScreen extends AppCompatActivity {

    private String Token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LoginToken",
                        Context.MODE_PRIVATE);
                if(!sharedPreferences.getString("Login_Token","").equals(""))
                    Token = sharedPreferences.getString("Login_Token","");
                Log.i("Token Splash", Token);

                if(!Token.equals("")){
                    Intent intent = new Intent(SplashScreen.this, DistributorDashboard.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashScreen.this, Distribution_Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3500);
    }
}
