package com.example.haball.Retailor.ui.Profile;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.ui.profile.Profile_Model;
import com.example.haball.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private Button btn_changepwd, btn_save_password, update_password;
    private EditText Rfirstname, Remail, Rcode, Rcnic, Rmobile, R_created_date, R_Address, txt_password, txt_newpassword, txt_cfmpassword;
    private TextView tv_pr1;
    private String PROFILE_URL = "http://175.107.203.97:3020/api/retailer/";
    private String ChangePass_URL = "http://175.107.203.97:3020/api/Users/ChangePassword";
    private String Token;
    private String RetailerId, ID, username;
    private Dialog change_password_dail;
    private Boolean password_check = false, confirm_password_check = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_retailor_profile, container, false);

        Rfirstname = root.findViewById(R.id.Rfirstname);
        Remail = root.findViewById(R.id.Remail);
        Rcode = root.findViewById(R.id.Rcode);
        Rcnic = root.findViewById(R.id.Rcnic);
        Rmobile = root.findViewById(R.id.Rmobile);
        R_created_date = root.findViewById(R.id.R_created_date);
        R_Address = root.findViewById(R.id.R_Address);
        btn_changepwd = root.findViewById(R.id.btn_changepwd);
        btn_save_password = root.findViewById(R.id.btn_save_password);

        btn_changepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_password_dail = new Dialog(getActivity());
                //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                change_password_dail.setContentView(R.layout.pasword_change);
                change_password_dail.setCancelable(true);
                change_password_dail.show();
                ImageButton close_button = change_password_dail.findViewById(R.id.image_button);
                txt_password = change_password_dail.findViewById(R.id.txt_password);
                txt_newpassword = change_password_dail.findViewById(R.id.txt_newpassword);
                txt_cfmpassword = change_password_dail.findViewById(R.id.txt_cfmpassword);
                close_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        change_password_dail.dismiss();
                    }
                });
                update_password = change_password_dail.findViewById(R.id.update_password);
                update_password.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        try {
                            updatePassword();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        });
        profileData();

        return root;
    }

    private void updatePassword() throws JSONException{
        checkPasswords();
        checkConfirmPassword();
        if(password_check && confirm_password_check) {

            SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                    Context.MODE_PRIVATE);
            Token = sharedPreferences.getString("Login_Token", "");
            Log.i("Login_Token", Token);
            SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                    Context.MODE_PRIVATE);
            ID = sharedPreferences1.getString("ID", "");
            username = sharedPreferences1.getString("username", "");
            Toast.makeText(getActivity(), "Update Password clicked", Toast.LENGTH_SHORT).show();

            change_password_dail.dismiss();

            JSONObject map = new JSONObject();
            map.put("Password", txt_password.getText().toString());
            map.put("NewPassword", txt_newpassword.getText().toString());
            map.put("NewPassword1", txt_cfmpassword.getText().toString());
            map.put("ID", ID);
            map.put("Username", username);
            Log.i("Map", map.toString());
            JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, ChangePass_URL, map, new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(JSONObject result) {
                    Log.i("response", String.valueOf(result));
                    try {
                        if(result.has("message")){
                            Toast.makeText(getActivity(), result.get("message").toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            final Dialog fbDialogue = new Dialog(getActivity());
                            //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                            fbDialogue.setContentView(R.layout.password_updatepopup);
                            tv_pr1 = fbDialogue.findViewById(R.id.tv_pr1);
                            tv_pr1.setText("User Profile ID " + ID + " password has been changed successfully.");
                            fbDialogue.setCancelable(true);
                            fbDialogue.show();
                            ImageButton close_button = fbDialogue.findViewById(R.id.image_button);
                            close_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    fbDialogue.dismiss();
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(getActivity(), String.valueOf(error),Toast.LENGTH_LONG).show();
                }

            }){

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "bearer " +Token);
                    params.put("Content-Type", "application/json; charset=UTF-8");
                    return params;
                }
            };
            Volley.newRequestQueue(getActivity()).add(sr);
        } else {
            Toast.makeText(getActivity(), "Password do not Match",Toast.LENGTH_LONG).show();
        }

    }

    private void checkPasswords() {
        String reg_ex = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*[\\.,#';\\\\\\(\\)\\{\\}'`/$^+=!*()@%&])).{6,}$";
        if (txt_newpassword.getText().toString().matches(reg_ex)) {
            password_check = true;
            txt_newpassword.setError(null);
        } else {
            txt_newpassword.setError("Please enter password with minimum 6 characters & 1 Numeric or special character");
            password_check = false;
        }
    }

    private void checkConfirmPassword() {
        if (txt_newpassword.getText().toString().equals(txt_cfmpassword.getText().toString())) {
            confirm_password_check = true;
            txt_cfmpassword.setError(null);
        } else {
            confirm_password_check = false;
            txt_cfmpassword.setError("Password does not match");
        }
    }

    private void profileData() {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        RetailerId = sharedPreferences1.getString("Retailer_Id", "");
        Log.i("RetailerId ", RetailerId);
        PROFILE_URL = PROFILE_URL + RetailerId;
        Log.i("Token Retailer ", Token);
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, PROFILE_URL,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                try {
                    Log.i("aaaaa",result.getString("Name"));

                    Rfirstname.setText(result.getString("Name"));
                    Remail.setText(result.getString("Email"));
                    Rcode.setText(result.getString("RetailerCode"));
                    Rcnic.setText(result.getString("CNIC"));
                    Rmobile.setText(result.getString("Mobile"));
                    R_Address.setText(result.getString("Address"));
                    String string = result.getString("CreatedDate");
                    String[] parts = string.split("T");
                    String Date = parts[0];
                    R_created_date.setText(Date);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(sr);


    }

}
