package com.example.haball.Retailor.Retailor_SignUp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.R;
import com.example.haball.Retailer_Login.RetailerLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class SignUp extends AppCompatActivity implements View.OnFocusChangeListener {

    private ImageButton btn_back;
    private String URL = "http://175.107.203.97:3020/api/retailer/Register";
    private EditText txt_username, txt_password, txt_confirmpass, txt_fullname, txt_email, txt_cnic, txt_mobile_number, txt_business_name, txt_address;
    private Button btn_register_signup;
    private Boolean password_check = false, confirm_password_check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setBackgroundDrawableResource(R.drawable.background_logo);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));

        final LayoutInflater inflater = LayoutInflater.from(this);

        View customView = inflater.inflate(R.layout.action_bar_main, null);
        btn_back = (ImageButton) customView.findViewById(R.id.btn_back);

        bar.setCustomView(customView);
        bar.setDisplayShowCustomEnabled(true);
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        bar.setTitle("");

        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);
        txt_confirmpass = findViewById(R.id.txt_confirmpass);
        txt_fullname = findViewById(R.id.txt_fullname);
        txt_email = findViewById(R.id.txt_email);
        txt_cnic = findViewById(R.id.txt_cnic);
        txt_mobile_number = findViewById(R.id.txt_mobile_number);
        txt_business_name = findViewById(R.id.txt_business_name);
        txt_address = findViewById(R.id.txt_address);

        (findViewById(R.id.txt_password)).setOnFocusChangeListener(this);
        (findViewById(R.id.txt_confirmpass)).setOnFocusChangeListener(this);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_register_signup = findViewById(R.id.btn_register_signup);
        btn_register_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    makeRegisterRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void makeRegisterRequest() throws JSONException {
        JSONObject map = new JSONObject();
        if(password_check && confirm_password_check) {
            map.put("ID", 0);
            map.put("Email", txt_email.getText().toString());
            map.put("Username", txt_username.getText().toString());
            map.put("Password", txt_password.getText().toString());
            map.put("ConfirmPassword", txt_confirmpass.getText().toString());
            map.put("Name", txt_fullname.getText().toString());
            map.put("CNIC", txt_cnic.getText().toString());
            map.put("Mobile", txt_mobile_number.getText().toString());
            map.put("CompanyName", txt_business_name.getText().toString());
            map.put("Address", txt_address.getText().toString());
            map.put("CountryId", 1);
            map.put("ProvinceId", 1);
            map.put("CityId", 1);
            map.put("PostCode", "12345");

            Log.i("MAP OBJECT", String.valueOf(map));

            JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL, map, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject result) {
                    Log.e("RESPONSE", result.toString());
                    try {
                        if(!result.get("RetailerCode").toString().isEmpty()){
                            Intent i = new Intent(SignUp.this, RetailerLogin.class);
                            Toast.makeText(SignUp.this,"You have been registered successfully, please use login credentials to access the Portal.", Toast.LENGTH_LONG).show();
                            startActivity(i);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(SignUp.this,e.toString(),Toast.LENGTH_LONG).show();
                    }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        String responseBody = new String(error.networkResponse.data, "utf-8");
                        JSONObject data = new JSONObject(responseBody);
                        String message = data.getString("message");
                        Toast.makeText(new RetailerLogin(), message, Toast.LENGTH_LONG).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    error.printStackTrace();
                  //  Toast.makeText(SignUp.this,error.toString(),Toast.LENGTH_LONG).show();
                }

            });
            Volley.newRequestQueue(this).add(sr);
        } else {
            Toast.makeText(SignUp.this,"Password does not match",Toast.LENGTH_LONG).show();
        }
    }



    private void checkPasswords(){
        String reg_ex = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*[\\.,#';\\\\\\(\\)\\{\\}'`/$^+=!*()@%&])).{6,}$";
        if(txt_password.getText().toString().matches(reg_ex)) {
            password_check = true;
            txt_password.setError(null);
        }
        else{
            txt_password.setError("Please enter password with minimum 6 characters & 1 Numeric or special character");
            password_check = false;
        }
    }

    private void checkConfirmPassword() {
        if (txt_password.getText().toString().equals(txt_confirmpass.getText().toString())) {
            confirm_password_check = true;
            txt_confirmpass.setError(null);
        } else {
            confirm_password_check = false;
            txt_confirmpass.setError("Password does not match");
        }
    }


    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            switch (view.getId()) {
                case R.id.txt_password:
                    checkPasswords();
                    break;
                case R.id.txt_confirmpass:
                    checkConfirmPassword();
                    break;
            }
        }

    }

}
