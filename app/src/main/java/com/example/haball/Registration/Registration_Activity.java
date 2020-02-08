package com.example.haball.Registration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.haball.Distribution_Login.Distribution_Login;
import com.example.haball.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class Registration_Activity extends AppCompatActivity {

    private Button btn_next;
    private ImageButton btn_back;
    private EditText txt_username,txt_password,txt_confirm;
    ProgressDialog progressDialog;

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_);

        context = getApplicationContext();
        progressDialog = new ProgressDialog(this);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));

        final LayoutInflater inflater = LayoutInflater.from(this);

        View customView = inflater.inflate(R.layout.action_bar_main, null);

        bar.setCustomView(customView);
        bar.setDisplayShowCustomEnabled(true);
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        bar.setTitle("");

        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);
        txt_confirm = findViewById(R.id.txt_confirm);

        btn_back = (ImageButton) customView.findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(txt_username.getText().toString())
                        || TextUtils.isEmpty(txt_password.getText().toString())
                        || TextUtils.isEmpty(txt_confirm.getText().toString())){
                    Snackbar.make(view,"Please Enter All Required Fields",Snackbar.LENGTH_LONG).show();
                }
                else{
                    try {
                        checkUsername(view);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void checkUsername(final View view) throws JSONException {
        progressDialog.setMessage("Loading, Please Wait");
        progressDialog.show();
        String URL = "http://175.107.203.97:4008/api/users/CheckField";

        JSONObject map = new JSONObject();
        map.put("CName", "Username");
        map.put("CValue", txt_username.getText().toString());
        map.put("TbName", "useraccounts");
        String requestBody = map.toString();
        BooleanRequest booleanRequest = new BooleanRequest(Request.Method.POST, URL, requestBody, new Response.Listener<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                if(response.toString().equals("true")){
                    Snackbar.make(view, "Username Already exists.", Snackbar.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
                else {
                    if(txt_password.getText().toString().equals(txt_confirm.getText().toString())){
                        Intent intent = new Intent(Registration_Activity.this, Register_Activity_2.class);
                        intent.putExtra("username",txt_username.getText().toString());
                        intent.putExtra("password",txt_password.getText().toString());
                        intent.putExtra("confirmpassword",txt_confirm.getText().toString());
                        startActivity(intent);
                    }
                    else{
                        Snackbar.make(view, "Passwords are not same.", Snackbar.LENGTH_LONG).show();
                    }
                }
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
}
