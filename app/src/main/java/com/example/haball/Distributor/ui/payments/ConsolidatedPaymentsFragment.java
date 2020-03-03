package com.example.haball.Distributor.ui.payments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
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
import com.example.haball.Distribution_Login.Distribution_Login;
import com.example.haball.Payment.ConsolidatePaymentsModel;
import com.example.haball.Payment.Consolidate_Fragment_Adapter;
import com.example.haball.Payment.DistributorPaymentRequestAdaptor;
import com.example.haball.Payment.DistributorPaymentRequestModel;
import com.example.haball.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConsolidatedPaymentsFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Spinner spinner_consolidate;
    private Spinner spinner2;
    private EditText conso_edittext;
    private List<String> consolidate_felter = new ArrayList<>();
    private List<String> filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPayments;
    private ArrayAdapter<String> arrayAdapterFeltter;
    private List<ConsolidatePaymentsModel> ConsolidatePaymentsRequestList = new ArrayList<>();
    private String Token, DistributorId;
    private Button create_payment;
    private String URL_CONSOLIDATE_PAYMENTS = "http://175.107.203.97:4013/api/consolidatedinvoices/search";
    private FragmentTransaction fragmentTransaction;
    private String Filter_selected, Filter_selected_value;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_consolidate_, container, false);

        recyclerView = root.findViewById(R.id.rv_consolidate);

        recyclerView.setHasFixedSize(true);
        spinner_consolidate = (Spinner) root.findViewById(R.id.spinner_conso);
        spinner2 = (Spinner) root.findViewById(R.id.conso_spinner2);
        conso_edittext = (EditText) root.findViewById(R.id.conso_edittext);
        spinner2.setVisibility(View.GONE);
        conso_edittext.setVisibility(View.GONE);
        consolidate_felter.add ("Select Criteria");
        consolidate_felter.add ("Invoice No");
        consolidate_felter.add ("Company");
        consolidate_felter.add ("Created Date");
        consolidate_felter.add ("Total Price");
        consolidate_felter.add ("Paid Amount");
        consolidate_felter.add ("Status");
        consolidate_felter.add ("Created By");

        arrayAdapterPayments = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, consolidate_felter);

        spinner_consolidate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                else{
                    Filter_selected = consolidate_felter.get(i);

                    if(!Filter_selected.equals("Status"))
                        spinner2.setSelection(0);
                    if(!conso_edittext.getText().equals(""))
                        conso_edittext.setText("");

                    if(Filter_selected.equals("Invoice No")) {
                        Filter_selected = "ConsolidatedInvoiceNumber";
                        spinner2.setVisibility(View.GONE);
                        conso_edittext.setVisibility(View.VISIBLE);
                    } else if(Filter_selected.equals("Company")) {
                        Filter_selected = "CompanyName";
                        spinner2.setVisibility(View.GONE);
                        conso_edittext.setVisibility(View.VISIBLE);
                    } else if(Filter_selected.equals("Created Date")) {
                        spinner2.setVisibility(View.GONE);
                        conso_edittext.setVisibility(View.GONE);
                        Toast.makeText(getContext(),"Created Date selected",Toast.LENGTH_LONG).show();
                    } else if(Filter_selected.equals("Total Price")) {
                        spinner2.setVisibility(View.GONE);
                        conso_edittext.setVisibility(View.GONE);
                        Toast.makeText(getContext(),"Total Price selected",Toast.LENGTH_LONG).show();
                    } else if(Filter_selected.equals("Paid Amount")) {
                        spinner2.setVisibility(View.GONE);
                        conso_edittext.setVisibility(View.GONE);
                        Toast.makeText(getContext(),"Paid Amount selected",Toast.LENGTH_LONG).show();
                    } else if(Filter_selected.equals("Status")) {
                        Filter_selected = "Status";
                        spinner2.setVisibility(View.VISIBLE);
                        conso_edittext.setVisibility(View.GONE);
                    } else if(Filter_selected.equals("Created By")) {
                        Filter_selected = "CreatedBy";
                        spinner2.setVisibility(View.GONE);
                        conso_edittext.setVisibility(View.VISIBLE);
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
        arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPayments.notifyDataSetChanged();
        spinner_consolidate.setAdapter(arrayAdapterPayments);

        filters.add ("Status");
        filters.add ("Pending");
        filters.add ("Unpaid ");
        filters.add ("Partially Paid");
        filters.add ("Paid");
        filters.add ("Payment Processing");
        arrayAdapterFeltter = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, filters);
            Log.i("aaaa1111", String.valueOf(consolidate_felter));
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                else{
                    Filter_selected_value = String.valueOf(i-1);
                    Log.i("Filter_selected_value",Filter_selected_value);
                    try {
                        fetchFilteredConsolidatePayments();
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
                    fetchFilteredConsolidatePayments();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

//        spinner_consolidate("Select Criteria","Invoice No", "Company", "Created Date", "Total Price", "Paid Amount" ,"Status","Created By");
//        spinner_consolidate.setOnItemSelectedListener(new Spinner().OnItemSelectedListener<String>() {
//            @Override public void onItemSelected(Spinner view, int position, long id, String item) {
//                if (position!=6){
//                    spinner_consolidate.setVisibility(View.GONE);
//                }
//                else{
//                    spinner2.setVisibility(View.VISIBLE);
//
//                }
//                Snackbar.make(view, "Clicked " + item , Snackbar.LENGTH_LONG).show();
//                Toast.makeText(getActivity(), "id"+position, Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        spinner2.setItems("Pending", "Unpaid", "Partially Paid", "Paid", "Payment Processing");
//        spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
//
//
//            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                Snackbar.make(view, "Clicked " + item , Snackbar.LENGTH_LONG).show();
//                Toast.makeText(getActivity(), "id"+position, Toast.LENGTH_SHORT).show();
//
//            }
//        });
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        try {
            fetchConsolidatePayments();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return root;
    }

    private void fetchConsolidatePayments() throws JSONException{
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0.1);

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_CONSOLIDATE_PAYMENTS, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                Log.i("ConsolidatePayments", result.toString());

                Gson gson = new Gson();
                Type type = new TypeToken<List<ConsolidatePaymentsModel>>(){}.getType();
                ConsolidatePaymentsRequestList = gson.fromJson(result.toString(),type);

                mAdapter = new Consolidate_Fragment_Adapter(getContext(),ConsolidatePaymentsRequestList);
                recyclerView.setAdapter(mAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printErrorMessage(error);

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

    private void fetchFilteredConsolidatePayments() throws JSONException{
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0.1);
        map.put(Filter_selected, Filter_selected_value);
        Log.i("Map", String.valueOf(map));
        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_CONSOLIDATE_PAYMENTS, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                Log.i("ConsolidatePayments", result.toString());

                Gson gson = new Gson();
                Type type = new TypeToken<List<ConsolidatePaymentsModel>>(){}.getType();
                ConsolidatePaymentsRequestList = gson.fromJson(result.toString(),type);

                mAdapter = new Consolidate_Fragment_Adapter(getContext(),ConsolidatePaymentsRequestList);
                recyclerView.setAdapter(mAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printErrorMessage(error);

                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json; charset=UTF-8 ");
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(sr);
    }


        private void printErrorMessage(VolleyError error) {
        if (error instanceof NetworkError) {
            Toast.makeText(getContext(), "Network Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(getContext(), "Server Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(getContext(), "Auth Failure Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(getContext(), "Parse Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(getContext(), "No Connection Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(getContext(), "Timeout Error !", Toast.LENGTH_LONG).show();
        }

        if (error.networkResponse != null && error.networkResponse.data != null) {
            try {
                String message = "";
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.i("responseBody",responseBody);
                JSONObject data = new JSONObject(responseBody);
                Log.i("data",String.valueOf(data));
                Iterator<String> keys = data.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    message = message + data.get(key) + "\n";
                }
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
