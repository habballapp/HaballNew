package com.example.haball.Distributor.ui.shipments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.DistributorPaymentsAdapter;
import com.example.haball.Distributor.DistributorPaymentsModel;
import com.example.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.example.haball.Payment.DistributorPaymentRequestAdaptor;
import com.example.haball.Payment.DistributorPaymentRequestModel;
import com.example.haball.R;
import com.example.haball.Shipment.Adapters.DistributorShipmentAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

public class Shipments_Fragments extends Fragment {

    private ShipmentsViewModel sendViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<ShipmentModel> ShipmentList = new ArrayList<>();
    private String Token, DistributorId;
    private String URL_SHIPMENTS = "http://175.107.203.97:4008/api/deliverynotes/search";
    private FragmentTransaction fragmentTransaction;
    private String Filter_selected, Filter_selected_value;

    private Spinner spinner_consolidate;
    private Spinner spinner2;
    private EditText conso_edittext;
    private List<String> consolidate_felter = new ArrayList<>();
    private List<String> filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPayments;
    private ArrayAdapter<String> arrayAdapterFeltter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_distributor_shipment, container, false);
        sendViewModel =
                ViewModelProviders.of(this).get(ShipmentsViewModel.class);
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_shipment);
        recyclerView.setHasFixedSize(true);
        spinner_consolidate = (Spinner) root.findViewById(R.id.spinner_conso);
        spinner2 = (Spinner) root.findViewById(R.id.conso_spinner2);
        conso_edittext = (EditText) root.findViewById(R.id.conso_edittext);
        spinner2.setVisibility(View.GONE);
        conso_edittext.setVisibility(View.GONE);
        consolidate_felter.add ("Select Criteria");
        consolidate_felter.add ("Shipment No");
        consolidate_felter.add ("Company");
        consolidate_felter.add ("Delivery Date");
        consolidate_felter.add ("Receiving Date");
        consolidate_felter.add ("Quantity");
        consolidate_felter.add ("Status");

        arrayAdapterPayments = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_dropdown_item_1line, consolidate_felter);

        spinner_consolidate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner2.setVisibility(View.GONE);
                conso_edittext.setVisibility(View.GONE);
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                else{
                    Filter_selected = consolidate_felter.get(i);

                    if(!Filter_selected.equals("Status"))
                        spinner2.setSelection(0);
                    if(!conso_edittext.getText().equals(""))
                        conso_edittext.setText("");

                    if(Filter_selected.equals("Shipment No")) {
                        Filter_selected = "DeliveryNumber";
                        conso_edittext.setVisibility(View.VISIBLE);
                    } else if(Filter_selected.equals("Company")) {
                        Filter_selected = "CompanyName";
                        conso_edittext.setVisibility(View.VISIBLE);
                    } else if(Filter_selected.equals("Delivery Date")) {
                        Toast.makeText(getContext(),"Delivery Date selected",Toast.LENGTH_LONG).show();
                    } else if(Filter_selected.equals("Receiving Date")) {
                        Toast.makeText(getContext(),"Receiving Date selected",Toast.LENGTH_LONG).show();
                    } else if(Filter_selected.equals("Quantity")) {
                        Toast.makeText(getContext(),"Quantity selected",Toast.LENGTH_LONG).show();
                    } else if(Filter_selected.equals("Status")) {
                        Filter_selected = "Status";
                        spinner2.setVisibility(View.VISIBLE);
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
        filters.add ("Delivered");
        filters.add ("Received");
        filters.add ("Returned");
        filters.add ("Revised");

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
                    Filter_selected_value = String.valueOf(i-1);
                    Log.i("Filter_selected_value",Filter_selected_value);
                    try {
                        fetchFilteredShipments();
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
                    fetchFilteredShipments();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        try {
            fetchShipments();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return root;
    }

    private void fetchShipments() throws JSONException{
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token","");
        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id","");
        Log.i("DistributorId ", DistributorId);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0.1);

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_SHIPMENTS, map,new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray result) {
                Log.i("Shipment", result.toString());
                Gson gson = new Gson();
                Type type = new TypeToken<List<ShipmentModel>>(){}.getType();
                ShipmentList = gson.fromJson(result.toString(),type);

                mAdapter = new DistributorShipmentAdapter(getContext(),ShipmentList);
                recyclerView.setAdapter(mAdapter);
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
                params.put("Authorization", "bearer " +Token);
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void fetchFilteredShipments() throws JSONException{
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token","");
        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id","");
        Log.i("DistributorId ", DistributorId);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0.1);
        map.put(Filter_selected, Filter_selected_value);
        Log.i("Map", String.valueOf(map));

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_SHIPMENTS, map,new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray result) {
                Log.i("Shipment Filtered", result.toString());
                Gson gson = new Gson();
                Type type = new TypeToken<List<ShipmentModel>>(){}.getType();
                ShipmentList = gson.fromJson(result.toString(),type);

                mAdapter = new DistributorShipmentAdapter(getContext(),ShipmentList);
                recyclerView.setAdapter(mAdapter);
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
                params.put("Authorization", "bearer " +Token);
                params.put("Content-Type", "application/json; charset=UTF-8");
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