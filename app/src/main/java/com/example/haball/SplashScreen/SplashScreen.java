package com.example.haball.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.haball.Distributor.DistributorDashboard;
import com.example.haball.Language_Selection.Language_Selection;
import com.example.haball.Order.DistributorOrder;
import com.example.haball.Order.DistributorOrder_ItemSelection;
import com.example.haball.Order.DistributorOrder_OnaddtoCart;
import com.example.haball.Order.DistributorOrder_ShopSelection;
import com.example.haball.Payment.DistributorPaymentRequest_CriteriaSelection;
import com.example.haball.Payment.PaymentLedger;
import com.example.haball.Payment.Payment_Screen1;
import com.example.haball.Payment.Payment_Screen2;
import com.example.haball.Payment.Payment_Screen3;
import com.example.haball.Payment.Proof_Of_Payment_Form;
import com.example.haball.R;
import com.example.haball.Distribution_Login.Distribution_Login;
import com.example.haball.Register_Activity.Register_Activity;
import com.example.haball.Registration.Registration_Activity;
import com.example.haball.Registration.Registration_Actvity2;
import com.example.haball.Support.Support_Ticket_Form;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
             //  Intent intent = new Intent(SplashScreen.this, Distribution_Login.class);
               Intent intent = new Intent(SplashScreen.this, DistributorOrder_OnaddtoCart.class);
                startActivity(intent);
                finish();
            }
        },4500);
    }
}
