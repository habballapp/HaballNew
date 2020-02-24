package com.example.haball.Distributor.ui.payments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.ui.support.MyJsonArrayRequest;
import com.example.haball.Payment.PaymentLedger;
import com.example.haball.Payment.PaymentLedgerAdapter;
import com.example.haball.Payment.PaymentLedgerModel;
import com.example.haball.R;
import com.example.haball.Support.SupportDashboardAdapter;
import com.example.haball.Support.SupportDashboardModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Payments_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private HashMap<String,String> companies = new HashMap<>();
    private List<String> company_names = new ArrayList<>();
    private String Token;
    private String URL_PAYMENT_LEDGER_COMPANY = "http://175.107.203.97:4008/api/company/ReadActiveCompanyContract/";
    private String URL_PAYMENT_LEDGER = "http://175.107.203.97:4008/api/transactions/search";
    private ArrayAdapter<String> arrayAdapterPayments, arrayAdapterPaymentsFilter;
    private List<PaymentLedgerModel> paymentLedgerList = new ArrayList<>();
    private Spinner spinner_criteria;
    private ProgressDialog progressDialog;

    private PaymentsViewModel paymentsViewModel;
    private Spinner spinner_consolidate;
    private Spinner spinner2;
    private EditText conso_edittext;
    private List<String> consolidate_felter = new ArrayList<>();
    private List<String> filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterFeltter;

    private String Company_selected, DistributorId;
    private String Filter_selected, Filter_selected_value;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        paymentsViewModel =
                ViewModelProviders.of(this).get(PaymentsViewModel.class);
        View root = inflater.inflate(R.layout.activity_payment_ledger, container, false);

        company_names.add("Company ");

        spinner_criteria = root.findViewById(R.id.spinner_criteria);
        arrayAdapterPayments = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, company_names);
        spinner_consolidate = (Spinner) root.findViewById(R.id.spinner_conso);
        spinner2 = (Spinner) root.findViewById(R.id.conso_spinner2);
        conso_edittext = (EditText) root.findViewById(R.id.conso_edittext);
        spinner_consolidate.setVisibility(View.GONE);
        spinner2.setVisibility(View.GONE);
        conso_edittext.setVisibility(View.GONE);
        consolidate_felter.add ("Select Criteria");
        consolidate_felter.add ("Ledger ID");
        consolidate_felter.add ("Document Type");
        consolidate_felter.add ("Date");
        consolidate_felter.add ("Credit");
        consolidate_felter.add ("Debit");
        consolidate_felter.add ("Balance");

        arrayAdapterPaymentsFilter = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_dropdown_item_1line, consolidate_felter);

        spinner_consolidate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                else{
                    Filter_selected = consolidate_felter.get(i);

                    if(!Filter_selected.equals("Document Type"))
                        spinner2.setSelection(0);
                    if(!conso_edittext.getText().equals(""))
                        conso_edittext.setText("");

                    if(Filter_selected.equals("Ledger ID")) {
                        Filter_selected = "DocumentNumber";
                        spinner2.setVisibility(View.GONE);
                        conso_edittext.setVisibility(View.VISIBLE);
                    } else if(Filter_selected.equals("Document Type")) {
                        Filter_selected = "DocumentType";
                        spinner2.setVisibility(View.VISIBLE);
                        conso_edittext.setVisibility(View.GONE);
                    } else if(Filter_selected.equals("Date")) {
                        spinner2.setVisibility(View.GONE);
                        conso_edittext.setVisibility(View.GONE);
                        Toast.makeText(getContext(),"Date selected",Toast.LENGTH_LONG).show();
                    } else if(Filter_selected.equals("Credit")) {
                        spinner2.setVisibility(View.GONE);
                        conso_edittext.setVisibility(View.GONE);
                        Toast.makeText(getContext(),"Credit selected",Toast.LENGTH_LONG).show();
                    } else if(Filter_selected.equals("Debit")) {
                        spinner2.setVisibility(View.GONE);
                        conso_edittext.setVisibility(View.GONE);
                        Toast.makeText(getContext(),"Debit selected",Toast.LENGTH_LONG).show();
                    } else if(Filter_selected.equals("Balance")) {
                        spinner2.setVisibility(View.GONE);
                        conso_edittext.setVisibility(View.GONE);
                        Toast.makeText(getContext(),"Balance selected",Toast.LENGTH_LONG).show();
                    } else {
                        spinner2.setVisibility(View.GONE);
                        conso_edittext.setVisibility(View.GONE);
                    }
//                    try {
//                        fetchPaymentLedgerData(companies.get(Filter_selected));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        arrayAdapterPaymentsFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPaymentsFilter.notifyDataSetChanged();
        spinner_consolidate.setAdapter(arrayAdapterPaymentsFilter);

        filters.add ("Document Type");
        filters.add ("Invoice");
        filters.add ("Prepaid ");
        filters.add ("Shipment");
        arrayAdapterFeltter = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_dropdown_item_1line, filters);
        Log.i("aaaa1111", String.valueOf(consolidate_felter));
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                else{
                    Filter_selected_value = String.valueOf(i);
                    Log.i("Filter_selected_value",Filter_selected_value);
                    try {
                        fetchFilteredPaymentLedgerData(companies.get(Company_selected));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        arrayAdapterFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterFeltter.notifyDataSetChanged();
        spinner2.setAdapter(arrayAdapterFeltter);


        conso_edittext.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                Log.i("text1", "check");
                Log.i("text", String.valueOf(s));
                Filter_selected_value = String.valueOf(s);
                try {
                    fetchFilteredPaymentLedgerData(companies.get(Company_selected));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        spinner_criteria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                    spinner_consolidate.setVisibility(View.GONE);
                    spinner2.setVisibility(View.GONE);
                    conso_edittext.setVisibility(View.GONE);
                }
                else{
                    Company_selected = company_names.get(i);
                    spinner_consolidate.setVisibility(View.VISIBLE);
                    spinner2.setVisibility(View.GONE);
                    conso_edittext.setVisibility(View.GONE);
                    try {
                        fetchPaymentLedgerData(companies.get(Company_selected));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        recyclerView = root.findViewById(R.id.rv_payment_ledger);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        fetchCompanyNames();

        return root;
    }

    private void fetchCompanyNames() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token","");
        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");

        URL_PAYMENT_LEDGER_COMPANY = URL_PAYMENT_LEDGER_COMPANY+DistributorId;
        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_PAYMENT_LEDGER_COMPANY,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for(int i=0;i<result.length();i++){
                        jsonObject  = result.getJSONObject(i);
                        company_names.add(jsonObject.getString("Name"));
                        companies.put(jsonObject.getString("Name"),jsonObject.getString("ID"));
                    }
//                    System.out.println("companies values => "+companies.get(company_names.get(0)));
//                    Log.i("companies values => ",companies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF companies", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printErrorMessage(error);

                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer "+Token);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(sr);
        arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPayments.notifyDataSetChanged();
        spinner_criteria.setAdapter(arrayAdapterPayments);
    }

    private void fetchPaymentLedgerData(String companyId) throws JSONException{
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token pl .. ", Token);

        JSONObject map = new JSONObject();
        map.put("Status", -1);
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("CompanyId", companyId);
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0.1);

        MyJsonArrayRequest request = new MyJsonArrayRequest(Request.Method.POST, URL_PAYMENT_LEDGER, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(" PAYMENT LEDGER => ", ""+response.toString());
                JSONObject jsonObject = new JSONObject();
                for(int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Gson gson = new Gson();
                Type type = new TypeToken<List<PaymentLedgerModel>>(){}.getType();
                paymentLedgerList = gson.fromJson(String.valueOf(response),type);

                mAdapter = new PaymentLedgerAdapter(getContext(),paymentLedgerList);
                recyclerView.setAdapter(mAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printErrorMessage(error);

                error.printStackTrace();
                Log.i("onErrorResponse", "Error");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer "+Token);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);
        mAdapter = new PaymentLedgerAdapter(getContext(),paymentLedgerList);
        recyclerView.setAdapter(mAdapter);
    }


    private void fetchFilteredPaymentLedgerData(String companyId) throws JSONException{
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token pl .. ", Token);

        JSONObject map = new JSONObject();
        map.put("Status", -1);
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("CompanyId", companyId);
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0.1);
        map.put(Filter_selected, Filter_selected_value);
        Log.i("Map", String.valueOf(map));

        MyJsonArrayRequest request = new MyJsonArrayRequest(Request.Method.POST, URL_PAYMENT_LEDGER, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(" PAYMENT LEDGER => ", ""+response.toString());
                JSONObject jsonObject = new JSONObject();
                for(int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Gson gson = new Gson();
                Type type = new TypeToken<List<PaymentLedgerModel>>(){}.getType();
                paymentLedgerList = gson.fromJson(String.valueOf(response),type);

                mAdapter = new PaymentLedgerAdapter(getContext(),paymentLedgerList);
                recyclerView.setAdapter(mAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printErrorMessage(error);

                error.printStackTrace();
                Log.i("onErrorResponse", "Error");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer "+Token);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);
        mAdapter = new PaymentLedgerAdapter(getContext(),paymentLedgerList);
        recyclerView.setAdapter(mAdapter);
    }


    private void printErrorMessage(VolleyError error) {
        try {
            String message = "";
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);
            Iterator<String> keys = data.keys();
            while(keys.hasNext()) {
                String key = keys.next();
                if (data.get(key) instanceof JSONObject) {
                    message = message + data.get(key) + "\n";
                }
            }
//                    if(data.has("message"))
//                        message = data.getString("message");
//                    else if(data. has("Error"))
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}