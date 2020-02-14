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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Payment.ConsolidatePaymentsModel;
import com.example.haball.Payment.Consolidate_Fragment_Adapter;
import com.example.haball.Payment.DistributorPaymentRequestAdaptor;
import com.example.haball.Payment.DistributorPaymentRequestModel;
import com.example.haball.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsolidatedPaymentsFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Spinner spinner_consolidate;
    private Spinner spinner2;
    private List<String> consolidate_felter = new ArrayList<>();
    private List<String> filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPayments;
    private ArrayAdapter<String> arrayAdapterFeltter;
    private List<ConsolidatePaymentsModel> ConsolidatePaymentsRequestList = new ArrayList<>();
    private String Token, DistributorId;
    private Button create_payment;
    private String URL_CONSOLIDATE_PAYMENTS = "http://175.107.203.97:4008/api/consolidatedinvoices/search";
    private FragmentTransaction fragmentTransaction;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_consolidate_, container, false);

        recyclerView = root.findViewById(R.id.rv_consolidate);

        recyclerView.setHasFixedSize(true);
        spinner_consolidate = (Spinner) root.findViewById(R.id.spinner_conso);
        spinner2 = (Spinner) root.findViewById(R.id.conso_spinner2);
        spinner2.setVisibility(View.GONE);
        consolidate_felter.add ("Select Criteria");
        consolidate_felter.add ("Invoice No");
        consolidate_felter.add ("Company");
        consolidate_felter.add ("Created Date");
        consolidate_felter.add ("Total Price");
        consolidate_felter.add ("Paid Amount");
        consolidate_felter.add ("Status");
        consolidate_felter.add ("Created By");

        arrayAdapterPayments = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_dropdown_item_1line, consolidate_felter);

        spinner_consolidate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPayments.notifyDataSetChanged();
        spinner_consolidate.setAdapter(arrayAdapterPayments);

        filters.add ("Pending");
        filters.add ("Unpaid ");
        filters.add ("Company");
        filters.add ("Paid");
        filters.add ("Payment Processing");
        arrayAdapterFeltter = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_dropdown_item_1line, filters);
            Log.i("aaaa1111", String.valueOf(consolidate_felter));
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        arrayAdapterFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterFeltter.notifyDataSetChanged();
        spinner2.setAdapter(arrayAdapterFeltter);

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
