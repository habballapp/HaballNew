package com.example.haball.Distribution_Login;

import android.annotation.SuppressLint;
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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.DistributorDashboard;
import com.example.haball.R;
import com.example.haball.Registration.Registration_Activity;
import com.example.haball.Select_User.Register_Activity;
import com.example.haball.Support.Support_Ditributor.Support_Ticket_Form;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import Forgot_Password.Forgot_Pass_Distributor;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Distribution_Login extends AppCompatActivity {

    private Button btn_login;
    private Button btn_reset;
    public ImageButton btn_back;
    private EditText et_username, et_password, txt_email;
    private String URL_FORGOT_PASSWORD = "http://175.107.203.97:4013/api/Users/forgot";
    private String token;
    ProgressDialog progressDialog;
    private TextInputLayout layout_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_distribution__login );
        getWindow().setBackgroundDrawableResource( R.drawable.background_logo );

        et_username = findViewById( R.id.txt_username );
        et_password = findViewById( R.id.txt_password );
        layout_password = findViewById( R.id.layout_password );

        et_password.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                layout_password.setBoxStrokeColor( getResources().getColor( R.color.color_text ) );
                layout_password.setDefaultHintTextColor( ColorStateList.valueOf( getResources().getColor( R.color.green_color ) ) );
                et_password.setTextColor( getResources().getColor( R.color.textcolor ) );
                layout_password.setPasswordVisibilityToggleTintList( ColorStateList.valueOf( getResources().getColor( R.color.textcolorhint ) ) );
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );

        btn_login = findViewById( R.id.btn_login );
        btn_login.setEnabled( false );
        btn_login.setBackground( getResources().getDrawable( R.drawable.disabled_button_background ) );

        Button btn_signup = findViewById( R.id.btn_signup );
        Button btn_support = findViewById( R.id.btn_support );
        Button btn_password = findViewById( R.id.btn_password );

        progressDialog = new ProgressDialog( this );


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

        et_username.addTextChangedListener( textWatcher );
        et_password.addTextChangedListener( textWatcher );

        ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setBackgroundDrawable( new ColorDrawable( Color.parseColor( "#FFFFFF" ) ) );

        LayoutInflater inflater = LayoutInflater.from( this );

        @SuppressLint("InflateParams") View customView = inflater.inflate( R.layout.action_bar_main, null );

        bar.setCustomView( customView );
        bar.setDisplayShowCustomEnabled( true );
        bar.setBackgroundDrawable( new ColorDrawable( Color.parseColor( "#FFFFFF" ) ) );
        bar.setTitle( "" );
        btn_back = customView.findViewById( R.id.btn_back );
        btn_back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Distribution_Login.this, Register_Activity.class );
                startActivity( intent );
            }
        } );

        btn_login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    loginRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } );
        btn_signup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Distribution_Login.this, Registration_Activity.class );
                startActivity( intent );

            }
        } );
        btn_support.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Distribution_Login.this, Support_Ticket_Form.class );
                startActivity( intent );

            }
        } );


        btn_password.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Distribution_Login.this, Forgot_Pass_Distributor.class );
                startActivity( intent );
            }
        } );
    }

//        btn_password.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final AlertDialog alertDialog = new AlertDialog.Builder(Distribution_Login.this).create();
//                LayoutInflater inflater = LayoutInflater.from(Distribution_Login.this);
//                @SuppressLint("InflateParams") View view_popup = inflater.inflate(R.layout.forget_password, null);
//                alertDialog.setView(view_popup);
//                txt_email = view_popup.findViewById(R.id.txt_email);
//                btn_reset = view_popup.findViewById(R.id.btn_reset);
//                final ImageButton img_btn = view_popup.findViewById(R.id.image_button);
//                btn_reset.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (!txt_email.getText().toString().equals("")) {
//                            final AlertDialog alertDialog1 = new AlertDialog.Builder(Distribution_Login.this).create();
//                            LayoutInflater inflater = LayoutInflater.from(Distribution_Login.this);
//                            @SuppressLint("InflateParams") View view_popup = inflater.inflate(R.layout.email_sent, null);
//                            alertDialog1.setView(view_popup);
//                       ImageButton img_email = view_popup.findViewById(R.id.image_email);
//                            forgotPasswordRequest(alertDialog, alertDialog1, img_email);
//                        } else {
//                            Toast.makeText(getApplication(), "Please enter Email Address!", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//                img_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        alertDialog.dismiss();
//                    }
//                });
//                alertDialog.show();
//            }
//        });
//
//    }




//    private void makeLoginRequest() {
//
//        queue = Volley.newRequestQueue(this);
//        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(Distribution_Login.this, response, Toast.LENGTH_LONG).show();
//                if (!response.equals("Invalid username or password!")) {
//
//                    SharedPreferences sharedPref = getSharedPreferences("Login_Check", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor login_check = sharedPref.edit();
//                    login_check.putBoolean("login_success", true);
//                    login_check.commit();
//
//                    Intent login_intent = new Intent(Distribution_Login.this, DistributorDashboard.class);
//                    startActivity(login_intent);
//                    finish();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                printErrorMessage(error);
////                Toast.makeText(Distribution_Login.this,error.toString(), Toast.LENGTH_LONG).show();
//            }
//        }) {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<String, String>();
//                params.put("Username", "test-user-02");
//                params.put("Password", "Force@321");
//                params.put("grant_type", "password");
//                return params;
//            }
//
//            ;
//        };
//
//        queue.add(request);
//    }

        private void loginRequest () throws JSONException {

            JSONObject map = new JSONObject();
            try {
                map.put( "Username", et_username.getText().toString() );
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                map.put( "Password", et_password.getText().toString() );
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                map.put( "grant_type", "password" );
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i( "map", String.valueOf( map ) );
            String URL = "http://175.107.203.97:4013/Token";
            JsonObjectRequest sr = new JsonObjectRequest( Request.Method.POST, URL, map, new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(JSONObject result) {
                    try {
                        if (!result.get( "access_token" ).toString().isEmpty()) {
                            token = result.get( "access_token" ).toString();
                            JSONObject userAccount = new JSONObject( String.valueOf( result.get( "UserAccount" ) ) );
                            Log.i( "user account => ", userAccount.get( "DistributorID" ).toString() );
                            String DistributorId = userAccount.get( "DistributorID" ).toString();
                            String username = userAccount.get( "Username" ).toString();
                            String CompanyName = userAccount.get( "CompanyName" ).toString();
                            String DealerCode = userAccount.get( "DealerCode" ).toString();
                            String ID = userAccount.get( "ID" ).toString();
                            SharedPreferences login_token = getSharedPreferences( "LoginToken",
                                    Context.MODE_PRIVATE );
                            SharedPreferences.Editor editor = login_token.edit();
                            editor.putString( "Login_Token", token );
                            editor.putString( "User_Type", "Distributor" );
                            editor.putString( "Distributor_Id", DistributorId );
                            editor.putString( "username", username );
                            editor.putString( "CompanyName", CompanyName );
                            editor.putString( "DealerCode", DealerCode );
                            editor.putString( "ID", ID );

                            editor.apply();

                            // Toast.makeText(Distribution_Login.this, "Login Success", Toast.LENGTH_LONG).show();
                            Intent login_intent = new Intent( Distribution_Login.this, DistributorDashboard.class );
                            startActivity( login_intent );
                            finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        try {
                            layout_password.setBoxStrokeColor( getResources().getColor( R.color.error_stroke_color ) );
                            layout_password.setDefaultHintTextColor( ColorStateList.valueOf( getResources().getColor( R.color.error_stroke_color ) ) );
                            layout_password.setPasswordVisibilityToggleTintList( ColorStateList.valueOf( getResources().getColor( R.color.error_stroke_color ) ) );
                            et_password.setTextColor( getResources().getColor( R.color.error_stroke_color ) );
                            Toast.makeText( Distribution_Login.this, result.get( "ErrorMessage" ).toString(), Toast.LENGTH_LONG ).show();
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }

                    }
//                Log.e("RESPONSE", result.toString());
//                Toast.makeText(Distribution_Login.this,result.toString(),Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
//                Toast.makeText(Distribution_Login.this,error.toString(),Toast.LENGTH_LONG).show();
                      printErrorMessage(error);
                }
            } );
            sr.setRetryPolicy( new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
            Volley.newRequestQueue( this ).add( sr );

//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(sr);
        }


        private void checkFieldsForEmptyValues () {

            String username_ = et_username.getText().toString();
            String password = et_password.getText().toString();

            if (username_.equals( "" ) || password.equals( "" )) {
                btn_login.setEnabled( false );
                btn_login.setBackground( getResources().getDrawable( R.drawable.disabled_button_background ) );

            } else {
                btn_login.setEnabled( true );
                btn_login.setBackground( getResources().getDrawable( R.drawable.button_background ) );
            }
        }

    private void printErrorMessage(VolleyError error) {
        if (error instanceof NetworkError) {
            Toast.makeText( Distribution_Login.this, "Network Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(Distribution_Login.this, "Server Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(Distribution_Login.this, "Auth Failure Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(Distribution_Login.this, "Parse Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(Distribution_Login.this, "No Connection Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(Distribution_Login.this, "Timeout Error !", Toast.LENGTH_LONG).show();
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
                Toast.makeText(Distribution_Login.this, message, Toast.LENGTH_LONG).show();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}







