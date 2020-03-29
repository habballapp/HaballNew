package com.example.haball.Retailer_Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.R;
import com.example.haball.Retailor.RetailorDashboard;
import com.example.haball.Retailor.Retailor_SignUp.SignUp;
import com.example.haball.Retailor.ui.Dashboard.Dashboard_Tabs;
import com.example.haball.Select_User.Register_Activity;
import com.example.haball.Support.Support_Ditributor.Support_Ticket_Form;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RetailerLogin extends AppCompatActivity {

    private Button btn_login, btn_signup, btn_support, btn_password, btn_reset;
    public ImageButton btn_back;
    private EditText et_username, et_password, txt_email;
    private TextInputLayout layout_username, layout_password;
    private Toolbar tb;
    private RequestQueue queue;
    private String URL = "http://175.107.203.97:4014/Token";
//    private String URL_FORGOT_PASSWORD = "http://175.107.203.97:4014/api/Users/forgot";
    private String URL_FORGOT_PASSWORD = "http://175.107.203.97:4013/api/users/forgot";
    private HttpURLConnection urlConnection = null;
    private java.net.URL url;
    private String token;
    private String success_text = "";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.background_logo);

        setContentView(R.layout.activity_retailer_login);
        btn_login = findViewById(R.id.retailer_btn_login);
        btn_login.setEnabled(false);
        btn_login.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
        btn_signup = findViewById(R.id.ret_btn_signup);
        btn_support = findViewById(R.id.ret_btn_support);
        btn_password = findViewById(R.id.ret_btn_password);
        layout_username = findViewById(R.id.layout_username);
        layout_password = findViewById(R.id.layout_password);

        layout_username.setBoxStrokeColor(getResources().getColor(R.color.color_text));
        layout_password.setBoxStrokeColor(getResources().getColor(R.color.color_text));

        progressDialog = new ProgressDialog(this);

        et_username = findViewById(R.id.txt_username);
        et_password = findViewById(R.id.txt_password);

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                layout_password.setBoxStrokeColor(getResources().getColor(R.color.color_text));
                layout_password.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                et_password.setTextColor(getResources().getColor(R.color.textcolor));
                layout_password.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFieldsForEmptyValues();

            }
        };

        et_username.addTextChangedListener(textWatcher);
        et_password.addTextChangedListener(textWatcher);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setDisplayShowTitleEnabled(false);
//
//
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View customView = inflater.inflate(R.layout.actio   n_bar_main, null);
//
//        actionBar.setCustomView(customView);
//        actionBar.setDisplayShowCustomEnabled(true);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));

        LayoutInflater inflater = LayoutInflater.from(this);

        View customView = inflater.inflate(R.layout.action_bar_main, null);

        bar.setCustomView(customView);
        bar.setDisplayShowCustomEnabled(true);
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        bar.setTitle("");
        btn_back = customView.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RetailerLogin.this, Register_Activity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(RetailerLogin.this, RetailorDashboard.class);
//                startActivity(intent);
                try {
                    loginRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RetailerLogin.this, SignUp.class);
                startActivity(intent);
            }
        });
        btn_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RetailerLogin.this, Support_Ticket_Form.class);
                startActivity(intent);
            }
        });

        btn_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog alertDialog = new AlertDialog.Builder(RetailerLogin.this).create();
                LayoutInflater inflater = LayoutInflater.from(RetailerLogin.this);
                View view_popup = inflater.inflate(R.layout.forget_password, null);
                alertDialog.setView(view_popup);
                txt_email = view_popup.findViewById(R.id.txt_email);
                btn_reset = view_popup.findViewById(R.id.btn_reset);
                final ImageButton img_btn = view_popup.findViewById(R.id.image_button);
                btn_reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!txt_email.getText().toString().equals("")) {
                            final AlertDialog alertDialog1 = new AlertDialog.Builder(RetailerLogin.this).create();
                            LayoutInflater inflater = LayoutInflater.from(RetailerLogin.this);
                            View view_popup = inflater.inflate(R.layout.email_sent, null);
                            alertDialog1.setView(view_popup);
                            ImageButton img_email = view_popup.findViewById(R.id.image_email);
                            forgotPasswordRequest(alertDialog, alertDialog1, img_email);
                        } else {
                            Toast.makeText(getApplication(), "Please enter Email Address!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                img_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });


    }

    private void checkFieldsForEmptyValues() {
        String username_ = et_username.getText().toString();
        String password = et_password.getText().toString();

        if (username_.equals("") || password.equals("")) {
            btn_login.setEnabled(false);
            btn_login.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        } else {
            btn_login.setEnabled(true);
            btn_login.setBackground(getResources().getDrawable(R.drawable.button_background));
        }
    }

    private String forgotPasswordRequest(final AlertDialog alertDialog, final AlertDialog alertDialog1, final ImageButton img_email) {

        progressDialog.setTitle("Resetting Password");
        progressDialog.setMessage("Loading, Please Wait..");
        progressDialog.show();
        StringRequest sr = new StringRequest(Request.Method.POST, URL_FORGOT_PASSWORD, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String result) {
                success_text = result;
                Log.e("RESPONSE", result);
                progressDialog.dismiss();
                alertDialog.dismiss();
                img_email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog1.dismiss();
                    }
                });
                alertDialog1.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                printErrorMessage(error);
                error.printStackTrace();
                // Toast.makeText(Distribution_Login.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }

            @Override
            public byte[] getBody() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Email", txt_email.getText().toString());
                    return jsonObject.toString().getBytes("utf-8");
                } catch (Exception e) {
                    return null;
                }
            }
        };
        sr.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 1000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        Volley.newRequestQueue(this).add(sr);
        return success_text;
    }

    private void loginRequest() throws JSONException {

        JSONObject map = new JSONObject();
        map.put("Username", et_username.getText().toString());
        map.put("Password", et_password.getText().toString());
        map.put("grant_type", "password");

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL, map, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject result) {
                try {
                    if (!result.get("access_token").toString().isEmpty()) {
                        token = result.get("access_token").toString();
                        JSONObject userAccount = new JSONObject(String.valueOf(result.get("UserAccount")));
                        Log.i("user account => ", userAccount.get("RetailerID").toString());
                        String RetailerId = userAccount.get("RetailerID").toString();
                        String username = userAccount.get("Username").toString();
                        String CompanyName = userAccount.get("CompanyName").toString();
                        String ID = userAccount.get("UserId").toString();
                        SharedPreferences login_token = getSharedPreferences("LoginToken",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = login_token.edit();
                        editor.putString("Login_Token", token);
                        editor.putString("User_Type", "Retailer");
                        editor.putString("Retailer_Id", RetailerId);
                        editor.putString("username", username);
                        editor.putString("CompanyName", CompanyName);
                        editor.putString("UserId", ID);

                        editor.commit();

                        Toast.makeText(RetailerLogin.this, "Login Success", Toast.LENGTH_LONG).show();
                        Intent login_intent = new Intent(RetailerLogin.this, Dashboard_Tabs.class);
                        startActivity(login_intent);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    try {
                        layout_password.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
                        layout_password.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
                        layout_password.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
                        et_password.setTextColor(getResources().getColor(R.color.error_stroke_color));
                        Toast.makeText(RetailerLogin.this, result.get("ErrorMessage").toString(), Toast.LENGTH_LONG).show();
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printErrorMessage(error);
                error.printStackTrace();
                //Toast.makeText(RetailerLogin.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(sr);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sr);
    }

    private void printErrorMessage(VolleyError error) {
        if (error instanceof NetworkError) {
            Toast.makeText(RetailerLogin.this, "Network Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(RetailerLogin.this, "Server Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(RetailerLogin.this, "Auth Failure Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(RetailerLogin.this, "Parse Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(RetailerLogin.this, "No Connection Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(RetailerLogin.this, "Timeout Error !", Toast.LENGTH_LONG).show();
        }

        if (error.networkResponse != null && error.networkResponse.data != null) {
            try {
                String message = "";
                String responseBody = new String(error.networkResponse.data, "utf-8");
                JSONObject data = new JSONObject(responseBody);
                Iterator<String> keys = data.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
//                if (data.get(key) instanceof JSONObject) {
                    message = message + data.get(key) + "\n";
//                }
                }
//                    if(data.has("message"))
//                        message = data.getString("message");
//                    else if(data. has("Error"))
                Toast.makeText(RetailerLogin.this, message, Toast.LENGTH_LONG).show();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
