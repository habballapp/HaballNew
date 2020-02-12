package com.example.haball.Distributor.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.DistributorOrdersAdapter;
import com.example.haball.Distributor.DistributorPaymentsAdapter;

import com.example.haball.Distributor.DistributorPaymentsModel;
import com.example.haball.Distributor.ui.orders.Orders_Fragment;
import com.example.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter PaymentsAdapter;
    private RecyclerView.Adapter OrdersAdapter;

    private RecyclerView.LayoutManager layoutManager;
    private String URL_DISTRIBUTOR_DASHBOARD = "http://175.107.203.97:4008/api/dashboard/ReadDistributorDashboard";
    private String URL_DISTRIBUTOR_PAYMENTS = "http://175.107.203.97:4008/api/dashboard/ReadDistributorPayments";

    private TextView value_unpaid_amount, value_paid_amount;
    private List<DistributorPaymentsModel> PaymentsList = new ArrayList<>();
        private String Token;

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;

        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1: {
                rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
                value_unpaid_amount = rootView.findViewById(R.id.value_unpaid_amount);
                value_paid_amount = rootView.findViewById(R.id.value_paid_amount);
                fetchDashboardData();
                break;
            }
            case 2: {
                rootView = inflater.inflate(R.layout.fragment_payments, container, false);
                fetchPaymentsData();
                recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_fragment_payments);

                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(rootView.getContext());
                recyclerView.setLayoutManager(layoutManager);

                break;
            }

            case 3: {
                rootView = inflater.inflate(R.layout.fragment_orders, container, false);
                recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_fragment_orders);

                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                recyclerView.setHasFixedSize(true);

                // use a linear layout manager
                layoutManager = new LinearLayoutManager(rootView.getContext());
                recyclerView.setLayoutManager(layoutManager);

                // specify an adapter (see also next example)
                OrdersAdapter = new DistributorOrdersAdapter(this,"Ghulam Rabani & Sons Traders & Distributors","51247895354254780369","PKR 600,000.00","Approved");
                recyclerView.setAdapter(OrdersAdapter);
                break;
            }
        }
        return rootView;
    }

    private void fetchPaymentsData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token","");
        Log.i("Token", Token);

        StringRequest sr = new StringRequest(Request.Method.POST, URL_DISTRIBUTOR_PAYMENTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                try{
                    JSONArray jsonArray = new JSONArray(result);
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<DistributorPaymentsModel>>(){}.getType();
                    PaymentsList = gson.fromJson(jsonArray.toString(),type);

                    PaymentsAdapter = new DistributorPaymentsAdapter(getContext(),PaymentsList);
                    recyclerView.setAdapter(PaymentsAdapter);

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

    private void fetchDashboardData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token","");

        StringRequest sr = new StringRequest(Request.Method.POST, URL_DISTRIBUTOR_DASHBOARD, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    DecimalFormat formatter1 = new DecimalFormat("#,###,###,##0.00");
                    String yourFormattedString1 = formatter1.format(Double.parseDouble(jsonObject.get("TotalUnpaidAmount").toString()));
                    DecimalFormat formatter2 = new DecimalFormat("#,###,###,##0.00");
                    String yourFormattedString2 = formatter2.format(Double.parseDouble(jsonObject.get("TotalPrepaidAmount").toString()));
                    value_unpaid_amount.setText(yourFormattedString1);
                    value_paid_amount.setText(yourFormattedString2);
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
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(sr);
    }
}