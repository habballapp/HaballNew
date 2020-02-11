package com.example.haball.Distributor.ui.payments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.DistributorInvoicesAdapter;
import com.example.haball.Distributor.DistributorInvoicesModel;
import com.example.haball.Distributor.DistributorPaymentsAdapter;
import com.example.haball.Distributor.DistributorPaymentsModel;
import com.example.haball.R;
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

public class PaymentsSummaryFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<DistributorPaymentsModel> PaymentsList = new ArrayList<>();
    private List<DistributorInvoicesModel> InvoicesList = new ArrayList<>();
    private String Token, DistributorId;
    private String URL_PAYMENTS = "http://175.107.203.97:4008/api/dashboard/ReadDistributorPayments";
    private String URL_INVOICES = "http://175.107.203.97:4008/api/Invoices/search";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_payment__summary, container, false);

        recyclerView = root.findViewById(R.id.rv_payment_request);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

//        fetchPaymentsSummary();
        try {
            fetchInvoicesSummary();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
    }
//
//    private void fetchPaymentsSummary() {
//        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Token = sharedPreferences.getString("Login_Token","");
//        Log.i("Token", Token);
//
//        StringRequest sr = new StringRequest(Request.Method.POST, URL_PAYMENTS, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String result) {
//                try{
//                    JSONArray jsonArray = new JSONArray(result);
//                    Gson gson = new Gson();
//                    Type type = new TypeToken<List<DistributorPaymentsModel>>(){}.getType();
//                    PaymentsList = gson.fromJson(jsonArray.toString(),type);
//
//                    mAdapter = new DistributorPaymentsAdapter(getContext(),PaymentsList);
//                    recyclerView.setAdapter(mAdapter);
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        }){
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " +Token);
//                return params;
//            }
//        };
//        Volley.newRequestQueue(getContext()).add(sr);
//    }

    private void fetchInvoicesSummary() throws JSONException{
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

            MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_INVOICES, map, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray result) {
                    Log.i("ConsolidatePayments", result.toString());

                    Gson gson = new Gson();
                    Type type = new TypeToken<List<DistributorInvoicesModel>>(){}.getType();
                    InvoicesList = gson.fromJson(result.toString(),type);

                    mAdapter = new DistributorInvoicesAdapter(getContext(),InvoicesList);
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
                    params.put("Content-Type", "application/json; charset=UTF-8");
                    return params;
                }
            };
            Volley.newRequestQueue(getContext()).add(sr);
        }
}
