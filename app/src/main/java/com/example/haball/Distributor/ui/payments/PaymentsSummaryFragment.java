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
import com.example.haball.Distributor.DistributorPaymentsAdapter;
import com.example.haball.Distributor.DistributorPaymentsModel;
import com.example.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

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
    private String Token;
    private String URL_PAYMENTS = "http://175.107.203.97:4008/api/dashboard/ReadDistributorPayments";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_payment__summary, container, false);

        recyclerView = root.findViewById(R.id.rv_payment_request);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        fetchPaymentsSummary();

        return root;
    }

    private void fetchPaymentsSummary() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token","");
        Log.i("Token", Token);

        StringRequest sr = new StringRequest(Request.Method.POST, URL_PAYMENTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                try{
                    JSONArray jsonArray = new JSONArray(result);
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<DistributorPaymentsModel>>(){}.getType();
                    PaymentsList = gson.fromJson(jsonArray.toString(),type);

                    mAdapter = new DistributorPaymentsAdapter(getContext(),PaymentsList);
                    recyclerView.setAdapter(mAdapter);

                }catch (JSONException e) {
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
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(sr);
    }
}
