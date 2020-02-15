package com.example.haball.Distributor.ui.orders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.ui.orders.Adapter.DistributorOrderAdapter;
import com.example.haball.Distributor.ui.orders.Models.OrderFragmentModel;
import com.example.haball.Distributor.ui.orders.Models.OrdersViewModel;
import com.example.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.example.haball.Order.DistributorOrder_ItemSelection;
import com.example.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Orders_Fragment extends Fragment {

    private OrdersViewModel sendViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button create_payment;
    private List<OrderFragmentModel> OrderList;
    private String URL_ORDER = "http://175.107.203.97:4008/api/orders/search";
    private String Token, DistributorId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(OrdersViewModel.class);
        View root = inflater.inflate(R.layout.activity_distributer_order, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_order_ledger);

        create_payment = root.findViewById(R.id.place_order_button);
        create_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DistributorOrder_ItemSelection.class);
                startActivity(intent);
            }
        });
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        try {
            fetchOrders();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        // specify an adapter (see also next example)
//        mAdapter = new DistributorOrderAdapter(this,"Ghulam Rabani & Sons Traders & Distributors","1002312324251524","Invoice","Pending");
//        recyclerView.setAdapter(mAdapter);
        return root;
    }

    private void fetchOrders() throws JSONException {
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
        map.put("PageNumber", 0);
        map.put("Status", -1);
        map.put("OrderState", -1);

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_ORDER, map,new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray result) {
                Log.i("Payments Requests", result.toString());
                Gson gson = new Gson();
                Type type = new TypeToken<List<OrderFragmentModel>>(){}.getType();
                OrderList = gson.fromJson(result.toString(),type);

                mAdapter = new DistributorOrderAdapter(getContext(),OrderList);
                recyclerView.setAdapter(mAdapter);
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
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(sr);
    }
}