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
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class Registration_Activity extends AppCompatActivity implements View.OnFocusChangeListener {

    private Button btn_next;
    private ImageButton btn_back;
    private EditText txt_username,txt_password,txt_confirm;
    ProgressDialog progressDialog;
    private Boolean username_check = false, password_check = false, confirm_password_check = false;

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_);
        getWindow().setBackgroundDrawableResource(R.drawable.background_logo);


        context = getApplicationContext();
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

        (findViewById(R.id.txt_username)).setOnFocusChangeListener(this);
        (findViewById(R.id.txt_password)).setOnFocusChangeListener(this);
        (findViewById(R.id.txt_confirm)).setOnFocusChangeListener(this);

        btn_back = (ImageButton) customView.findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
                finish();
            }
        });

        btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(txt_username.getText().toString()) ||
                        TextUtils.isEmpty(txt_password.getText().toString()) ||
                        TextUtils.isEmpty(txt_confirm.getText().toString())) {
                    Snackbar.make(view, "Please Enter All Required Fields", Snackbar.LENGTH_SHORT).show();
                } else {
                    if(!username_check && password_check && confirm_password_check){
                        Intent intent = new Intent(Registration_Activity.this, Register_Activity_2.class);
                        intent.putExtra("username", txt_username.getText().toString());
                        intent.putExtra("password", txt_password.getText().toString());
                        intent.putExtra("confirmpassword", txt_confirm.getText().toString());
                        startActivity(intent);
                    }
                }

            }
        });
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

    private void checkUsername(final View view) throws JSONException {

        if(txt_username.getText().toString().equals("")){
            txt_username.setError("This field is required");
        }
        else {
            txt_username.setError(null);

            String URL = "http://175.107.203.97:4013/api/users/CheckField";

            JSONObject map = new JSONObject();
            map.put("CName", "Username");
            map.put("CValue", txt_username.getText().toString());
            map.put("TbName", "useraccounts");
            String requestBody = map.toString();
            BooleanRequest booleanRequest = new BooleanRequest(Request.Method.POST, URL, requestBody, new Response.Listener<Boolean>() {
                @Override
                public void onResponse(Boolean response) {
                    Log.i("response", String.valueOf(response));
                    if (response.toString().equals("true")) {
                        txt_username.setError("Username Already Exists.");
                    }
                    username_check = response;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Snackbar.make(view, error.toString(), Snackbar.LENGTH_LONG).show();
                }
            });

            Volley.newRequestQueue(this).add(booleanRequest);
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            switch (view.getId()) {
                case R.id.txt_username:
                    try {
                        checkUsername(view);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.txt_password:
                    checkPasswords();
                    break;
                case R.id.txt_confirm:
                    checkConfirmPassword();
                    break;
            }
        }

    }

    private void checkConfirmPassword() {
        if (txt_password.getText().toString().equals(txt_confirm.getText().toString())) {
            confirm_password_check = true;
            txt_confirm.setError(null);
        } else {
            confirm_password_check = false;
            txt_confirm.setError("Password does not match");
        }
    }

    private void checkEmpty(EditText et_id){
        if(TextUtils.isEmpty(et_id.getText().toString()))
            et_id.setError("This field is required");
        else
            et_id.setError(null);
    }
}
