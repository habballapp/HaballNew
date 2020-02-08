package com.example.haball.Distributor.ui.payments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreatePaymentRequestFragment extends Fragment {
    private String Token, DistributorId;
    private Button btn_create;

    private String URL_PAYMENT_REQUESTS_SELECT_COMPANY = "http://175.107.203.97:4008/api/company/ReadActiveCompanyContract/";
    private String URL_PAYMENT_REQUESTS_SAVE = "http://175.107.203.97:4008/api/prepaidrequests/save";

    private List<String> CompanyNames = new ArrayList<>();
    private HashMap<String,String> companyNameAndId = new HashMap<>();

    private Spinner spinner_company;
    private ArrayAdapter<String> arrayAdapterPayments;
    private String company_names;
    private EditText txt_amount;
    private String prepaid_number;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_payment__screen1, container, false);

        btn_create = root.findViewById(R.id.btn_create);
        spinner_company = root.findViewById(R.id.spinner_company);
        txt_amount = root.findViewById(R.id.txt_amount);

        CompanyNames.add("Company *");

        arrayAdapterPayments = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_dropdown_item_1line, CompanyNames);

        spinner_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                else{
                    company_names = CompanyNames.get(i);
                    Log.i("company name and id ", companyNameAndId.get(company_names));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fetchCompanyData();
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    makeSaveRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return root;
    }

    private void makeSaveRequest() throws JSONException{
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        JSONObject map = new JSONObject();
        map.put("Status", 0);
        map.put("ID", 0);
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("CompanyId", companyNameAndId.get(company_names));
        map.put("PaidAmount", txt_amount.getText().toString());

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_PAYMENT_REQUESTS_SAVE, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                try {
                    prepaid_number = result.getString("PrePaidNumber");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "Payment Request "+prepaid_number+" has been created successfully.", Toast.LENGTH_SHORT).show();
                Log.e("RESPONSE prepaid_number", result.toString());
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

    private void fetchCompanyData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        URL_PAYMENT_REQUESTS_SELECT_COMPANY = URL_PAYMENT_REQUESTS_SELECT_COMPANY + DistributorId;
        Log.i("URL_PROOF_OF_PAYMENTS ", URL_PAYMENT_REQUESTS_SELECT_COMPANY);

        Log.i("Token", Token);

        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_PAYMENT_REQUESTS_SELECT_COMPANY, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < result.length(); i++) {
                        jsonObject = result.getJSONObject(i);
                        CompanyNames.add(jsonObject.getString("Name"));
                        companyNameAndId.put(jsonObject.getString("Name"),jsonObject.getString("ID"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF COMPANY ID", result.toString());
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
        arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPayments.notifyDataSetChanged();
        spinner_company.setAdapter(arrayAdapterPayments);
    }
}
