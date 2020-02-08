package com.example.haball.Registration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.haball.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class Register_Activity_2 extends AppCompatActivity implements View.OnFocusChangeListener {

    private Button btn_register;
    private ImageButton btn_back;
    private String username, password, confirmpassword;
    private EditText txt_firstname, txt_lastname, txt_email, txt_cnic, txt_mobile_number, txt_phone_number, txt_ntn, txt_conpany_name, txt_website;

    private ProgressDialog progressDialog;
    private Boolean email_check, phone_check, mobile_check, cnic_check, ntn_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__2);

        progressDialog = new ProgressDialog(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            username = extras.getString("username");
            password = extras.getString("password");
            confirmpassword = extras.getString("confirmpassword");
        }
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));

        LayoutInflater inflater = LayoutInflater.from(this);

        View customView = inflater.inflate(R.layout.action_bar_main, null);

        bar.setCustomView(customView);
        bar.setDisplayShowCustomEnabled(true);
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        bar.setTitle("");

        btn_back = (ImageButton) customView.findViewById(R.id.btn_back);

        txt_firstname = findViewById(R.id.txt_firstname);
        txt_lastname = findViewById(R.id.txt_lastname);
        txt_email = findViewById(R.id.txt_email);
        txt_cnic = findViewById(R.id.txt_cnic);
        txt_mobile_number = findViewById(R.id.txt_mobile_number);
        txt_phone_number = findViewById(R.id.txt_phone_number);
        txt_ntn = findViewById(R.id.txt_ntn);
        txt_conpany_name = findViewById(R.id.txt_conpany_name);
        txt_website = findViewById(R.id.txt_website);

        (findViewById(R.id.txt_email)).setOnFocusChangeListener(this);
        (findViewById(R.id.txt_cnic)).setOnFocusChangeListener(this);
        (findViewById(R.id.txt_mobile_number)).setOnFocusChangeListener(this);
        (findViewById(R.id.txt_phone_number)).setOnFocusChangeListener(this);
        (findViewById(R.id.txt_ntn)).setOnFocusChangeListener(this);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!phone_check && !mobile_check && !email_check && !cnic_check && !ntn_check){
                    Intent i = new Intent(Register_Activity_2.this, Registration_Actvity2.class);
                    i.putExtra("username",username);
                    i.putExtra("password",password);
                    i.putExtra("confirmpassword",confirmpassword);
                    i.putExtra("firstname",txt_firstname.getText().toString());
                    i.putExtra("lastname",txt_lastname.getText().toString());
                    i.putExtra("email",txt_email.getText().toString());
                    i.putExtra("cnic",txt_cnic.getText().toString());
                    i.putExtra("mobile_number",txt_mobile_number.getText().toString());
                    i.putExtra("phone_number",txt_phone_number.getText().toString());
                    i.putExtra("ntn",txt_ntn.getText().toString());
                    i.putExtra("conpany_name",txt_conpany_name.getText().toString());
                    i.putExtra("website",txt_website.getText().toString());
                    startActivity(i);
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkEmail(final View view)  throws JSONException {
        String URL = "http://175.107.203.97:4008/api/users/CheckField";

        JSONObject map = new JSONObject();
        map.put("CName", "EmailAddress");
        map.put("CValue", txt_email.getText().toString());
        map.put("TbName", "useraccounts");
        String requestBody = map.toString();
        BooleanRequest booleanRequest = new BooleanRequest(Request.Method.POST, URL, requestBody, new Response.Listener<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                if(response.toString().equals("true")){
                    Snackbar.make(view, "Email Already exists.", Snackbar.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
                email_check = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(view, error.toString(), Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

        Volley.newRequestQueue(this).add(booleanRequest);
    }

    private void checkMobile(final View view) throws JSONException {
        String URL = "http://175.107.203.97:4008/api/users/CheckField";

        JSONObject map = new JSONObject();
        map.put("CName", "Mobile");
        map.put("CValue", txt_mobile_number.getText().toString());
        map.put("TbName", "distributors");
        String requestBody = map.toString();
        BooleanRequest booleanRequest = new BooleanRequest(Request.Method.POST, URL, requestBody, new Response.Listener<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                if(response.toString().equals("true")){
                    Snackbar.make(view, "Mobile Number Already exists.", Snackbar.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
                mobile_check = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(view, error.toString(), Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

        Volley.newRequestQueue(this).add(booleanRequest);
    }

    private void checkPhone(final View view) throws JSONException {
        String URL = "http://175.107.203.97:4008/api/users/CheckField";

        JSONObject map = new JSONObject();
        map.put("CName", "Phone");
        map.put("CValue", txt_phone_number.getText().toString());
        map.put("TbName", "distributors");
        String requestBody = map.toString();
        BooleanRequest booleanRequest = new BooleanRequest(Request.Method.POST, URL, requestBody, new Response.Listener<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                if(response.toString().equals("true")){
                    Snackbar.make(view, "Phone Number Already exists.", Snackbar.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
                phone_check = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(view, error.toString(), Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

        Volley.newRequestQueue(this).add(booleanRequest);
    }

    private void checkCNIC(final View view) throws JSONException {
        String URL = "http://175.107.203.97:4008/api/users/CheckField";

        JSONObject map = new JSONObject();
        map.put("CName", "CNIC");
        map.put("CValue", txt_cnic.getText().toString());
        map.put("TbName", "distributors");
        String requestBody = map.toString();
        BooleanRequest booleanRequest = new BooleanRequest(Request.Method.POST, URL, requestBody, new Response.Listener<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                if(response.toString().equals("true")){
                    Snackbar.make(view, "CNIC Number Already exists.", Snackbar.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
                cnic_check = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(view, error.toString(), Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

        Volley.newRequestQueue(this).add(booleanRequest);
    }

    private void checkNTN(final View view) throws JSONException {
        String URL = "http://175.107.203.97:4008/api/users/CheckField";

        JSONObject map = new JSONObject();
        map.put("CName", "CompanyNTN");
        map.put("CValue", txt_ntn.getText().toString());
        map.put("TbName", "distributors");
        String requestBody = map.toString();
        BooleanRequest booleanRequest = new BooleanRequest(Request.Method.POST, URL, requestBody, new Response.Listener<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                if(response.toString().equals("true")){
                    Snackbar.make(view, "NTN Number Already exists.", Snackbar.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
                ntn_check = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(view, error.toString(), Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

        Volley.newRequestQueue(this).add(booleanRequest);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            switch (view.getId()) {
                case R.id.txt_email:
                    try {
                        checkEmail(view);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.txt_phone_number:
                    try {
                        checkPhone(view);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.txt_mobile_number:
                    try {
                        checkMobile(view);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.txt_cnic:
                    try {
                        checkCNIC(view);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.txt_ntn:
                    try {
                        checkNTN(view);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
