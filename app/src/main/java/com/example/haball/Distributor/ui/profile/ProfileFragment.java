package com.example.haball.Distributor.ui.profile;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private Button change_pwd, update_password, distributor_save_b;
    private EditText edt_firstname, edt_lastname, edt_email, edt_dist_mobile, et_test;
    public TextView edt_dist_code, tv_cnic, tv_NTN, tv_companyname, tv_created_date;
    private String PROFILE_URL = "http://175.107.203.97:4008/api/distributor/";
    private String Token;
    private String DistributorId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_distributor_profile, container, false);
        //init
        change_pwd = root.findViewById(R.id.btn_changepwd);
        //profile
        edt_dist_code = root.findViewById(R.id.edt_dist_code);
        edt_firstname = root.findViewById(R.id.edt_firstname);
        edt_firstname.setEnabled(false);
        edt_lastname = root.findViewById(R.id.edt_lastname);
        edt_lastname.setEnabled(false);
        edt_email = root.findViewById(R.id.edt_email);
        edt_email.setEnabled(false);
        edt_dist_mobile = root.findViewById(R.id.edt_dist_mobile);
        edt_dist_mobile.setEnabled(false);
        tv_cnic = root.findViewById(R.id.tv_cnic);
        tv_NTN = root.findViewById(R.id.tv_NTN);
        tv_companyname = root.findViewById(R.id.tv_companyname);
        tv_created_date = root.findViewById(R.id.tv_created_date);
        change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog change_password_dail = new Dialog(getActivity());
                //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                change_password_dail.setContentView(R.layout.pasword_change);
                change_password_dail.setCancelable(true);
                change_password_dail.show();
                ImageButton close_button = change_password_dail.findViewById(R.id.image_button);
                close_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        change_password_dail.dismiss();
                    }
                });
                update_password = change_password_dail.findViewById(R.id.update_password);
                update_password.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        change_password_dail.dismiss();

                        final Dialog fbDialogue = new Dialog(getActivity());
                        //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                        fbDialogue.setContentView(R.layout.password_updatepopup);
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
                });
            }

        });
        profileData();
        return root;
    }

    private void profileData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId1 ", DistributorId);
        PROFILE_URL = PROFILE_URL + DistributorId;
        Log.i("Token1", Token);
        StringRequest sr = new StringRequest(Request.Method.GET, PROFILE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                Log.i("aaaaa",result);
                try {
                    if (result != null && !result.equals("")) {
                        Gson gson = new Gson();
                        Profile_Model profile_model = gson.fromJson(result, Profile_Model.class);
                        edt_dist_code.setText(profile_model.getDealerCode());
                        edt_firstname.setText(profile_model.getFirstName());
                        edt_lastname.setText(profile_model.getLastName());
                        edt_email.setText(profile_model.getEmail());
                        edt_dist_mobile.setText(profile_model.getMobile());
                        tv_cnic.setText(profile_model.getCNIC());
                        tv_NTN.setText(profile_model.getCompanyNTN());
                        tv_companyname.setText(profile_model.getCompanyName());
                        String string = profile_model.getCreatedDate();
                        String[] parts = string.split("T");
                        String Date = parts[0];
                        tv_created_date.setText(Date);

                    }
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