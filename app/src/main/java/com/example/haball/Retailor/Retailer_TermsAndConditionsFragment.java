package com.example.haball.Retailor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.haball.R;
import com.example.haball.Registration.BooleanRequest;
import com.example.haball.Retailer_Login.RetailerLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Retailer_TermsAndConditionsFragment extends AppCompatActivity {

    private Button agree_button, disagree_button;
    private String URL = "http://175.107.203.97:4014/api/users/termsandcondition";
    private String Token;
    boolean doubleBackToExitPressedOnce = false;

    public static Retailer_TermsAndConditionsFragment newInstance() {
        return new Retailer_TermsAndConditionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_and_conditions_fragment);
        Drawable background_drawable = getResources().getDrawable(R.drawable.background_logo);
        background_drawable.setAlpha(80);
        RelativeLayout rl_main_background = findViewById(R.id.rl_main_background);
        rl_main_background.setBackground(background_drawable);
        agree_button = findViewById(R.id.agree_button);
        disagree_button = findViewById(R.id.disagree_button);

        agree_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    termsAndConditionAccepted();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        disagree_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Retailer_TermsAndConditionsFragment.this, RetailerLogin.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Retailer_TermsAndConditionsFragment.this, RetailerLogin.class);
        startActivity(intent);
        finish();

    }

    private void termsAndConditionAccepted() throws JSONException {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        String RetailerID = sharedPreferences.getString("Retailer_Id", "");
        Log.i("Token", Token);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("RetailerID", Integer.parseInt(RetailerID));

        String requestBody = jsonObject.toString();

        BooleanRequest sr = new BooleanRequest(Request.Method.POST, URL, requestBody, new Response.Listener<Boolean>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Boolean result) {
                if(result) {
                    Intent intent = new Intent(Retailer_TermsAndConditionsFragment.this, Retailer_UpdatePassword.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Retailer_TermsAndConditionsFragment.this, "Error occurred.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Retailer_TermsAndConditionsFragment.this, RetailerLogin.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // printErrorMessage(error);
                error.printStackTrace();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json");

                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(sr);
    }

}
