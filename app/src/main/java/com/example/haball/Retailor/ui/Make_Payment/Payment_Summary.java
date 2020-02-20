package com.example.haball.Retailor.ui.Make_Payment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.ui.payments.PaymentsViewModel;
import com.example.haball.Payment.Payment_Amount;
import com.example.haball.R;
import com.example.haball.Retailor.ui.Dashboard.RetailerPaymentAdapter;
import com.example.haball.Retailor.ui.Dashboard.RetailerPaymentModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Payment_Summary extends Fragment {

    private PaymentsViewModel paymentsViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String URL = "http://175.107.203.97:3020/api/prepaidrequests/Search";
    private String Token;
    private List<RetailerPaymentModel> PaymentsList = new ArrayList<>();

    private Button create_payment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        paymentsViewModel =
                ViewModelProviders.of(this).get(PaymentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_payment__summary, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_payment_request);
        create_payment = root.findViewById(R.id.create_payment);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        try {
            fetchPaymentsData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return root;
    }
    private void fetchPaymentsData() throws JSONException {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token","");
        Log.i("Token", Token);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("CompanyName", null);
        jsonObject.put("CreateDateFrom", null);
        jsonObject.put("CreateDateTo", null);
        jsonObject.put("Status", null);
        jsonObject.put("AmountMin", null);
        jsonObject.put("AmountMax", null);
        jsonObject.put("TotalRecords", 10);
        jsonObject.put("PageNumber", 0);

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL,jsonObject, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject result) {
                try {
                    System.out.println("RESPONSE PAYMENTS"+result.getJSONArray("PrePaidRequestData"));
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<RetailerPaymentModel>>(){}.getType();
                    PaymentsList = gson.fromJson(result.getJSONArray("PrePaidRequestData").toString(),type);

                    mAdapter = new RetailerPaymentAdapter(getContext(),PaymentsList);
                    recyclerView.setAdapter(mAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " +Token);
                params.put("Content-Type", "application/json");

                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(sr);
    }
}
