package com.example.haball.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.haball.Distributor.DistributorDashboard;
import com.example.haball.Distributor.StatusKVP;
import com.example.haball.Distributor.ui.terms_and_conditions.TermsAndConditionsFragment;
import com.example.haball.R;
import com.example.haball.Distribution_Login.Distribution_Login;
import com.example.haball.Retailer_Login.RetailerLogin;
import com.example.haball.Retailor.Retailer_TermsAndConditionsFragment;
import com.example.haball.Retailor.Retailer_UpdatePassword;
import com.example.haball.Retailor.RetailorDashboard;
import com.example.haball.Retailor.ui.Dashboard.Dashboard_Tabs;
import com.example.haball.Select_User.Register_Activity;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    private String Token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Drawable background_drawable = getResources().getDrawable(R.drawable.background_logo);
        background_drawable.setAlpha(80);
        RelativeLayout rl_main_background = findViewById(R.id.rl_main_background);
        rl_main_background.setBackground(background_drawable);
//        rl_main_background.typ
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

//                Intent intent1 = new Intent(SplashScreen.this, Retailer_UpdatePassword.class);
//                startActivity(intent1);
//                finish();


                SharedPreferences selectedProducts = getSharedPreferences("selectedProducts_retailer",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = selectedProducts.edit();
                editor.remove("selected_products_qty");
                editor.remove("selected_products");
                editor.commit();
                String IsTermAndConditionAccepted = "";
                String UpdatePassword = "";
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LoginToken",
                        Context.MODE_PRIVATE);
                if (!sharedPreferences.getString("Login_Token", "").equals(""))
                    Token = sharedPreferences.getString("Login_Token", "");
                if (!sharedPreferences.getString("IsTermAndConditionAccepted", "").equals(""))
                    IsTermAndConditionAccepted = sharedPreferences.getString("IsTermAndConditionAccepted", "");
                if (!sharedPreferences.getString("UpdatePassword", "").equals(""))
                    UpdatePassword = sharedPreferences.getString("UpdatePassword", "");
                Log.i("Token Splash", Token);
                Log.i("User Type", sharedPreferences.getString("User_Type", ""));

//                SharedPreferences companyId = getApplicationContext().getSharedPreferences("SendData",
//                        Context.MODE_PRIVATE);
//                SharedPreferences.Editor editorSupport = companyId.edit();
//                editorSupport.putString("first_name" , edt_firstname.getText().toString());
//                editorSupport.putString("email" , edt_email.getText().toString());
//                editorSupport.putString("phone_number" , edt_dist_mobile.getText().toString());
//                editorSupport.apply();

                if (!Token.equals("")) {
                    if (IsTermAndConditionAccepted.equals("1")) {
                        if (sharedPreferences.getString("User_Type", "").equals("Distributor")) {
                            Intent intent = new Intent(SplashScreen.this, DistributorDashboard.class);
                            startActivity(intent);
                            finish();
                        } else if (sharedPreferences.getString("User_Type", "").equals("Retailer")) {
                            Intent intent = new Intent(SplashScreen.this, RetailorDashboard.class);
                            StatusKVP statusKVP = new StatusKVP(getApplicationContext(), Token);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(SplashScreen.this, Register_Activity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else if (IsTermAndConditionAccepted.equals("0") || UpdatePassword.equals("0")) {
                        if (sharedPreferences.getString("User_Type", "").equals("Distributor")) {
                            Intent intent = new Intent(SplashScreen.this, Distribution_Login.class);
                            startActivity(intent);
                            finish();
                        } else if (sharedPreferences.getString("User_Type", "").equals("Retailer")) {
                            Intent intent = new Intent(SplashScreen.this, RetailerLogin.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(SplashScreen.this, Register_Activity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        if (sharedPreferences.getString("User_Type", "").equals("Distributor")) {
                            Intent intent = new Intent(SplashScreen.this, Distribution_Login.class);
                            startActivity(intent);
                            finish();
                        } else if (sharedPreferences.getString("User_Type", "").equals("Retailer")) {
                            Intent intent = new Intent(SplashScreen.this, RetailerLogin.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(SplashScreen.this, Register_Activity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                } else {
                    if (sharedPreferences.getString("User_Type", "").equals("Distributor")) {
                        Intent intent = new Intent(SplashScreen.this, Distribution_Login.class);
                        startActivity(intent);
                        finish();
                    } else if (sharedPreferences.getString("User_Type", "").equals("Retailer")) {
                        Intent intent = new Intent(SplashScreen.this, RetailerLogin.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashScreen.this, Register_Activity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }, 3500);
    }
}
