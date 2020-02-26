package com.example.haball.Distributor.ui.orders.OrdersTabsLayout.Tabs;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.haball.Distributor.ui.orders.Adapter.OrderSummaryAdapter;
import com.example.haball.Distributor.ui.orders.Models.OrderItemsModel;
import com.example.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Order_Summary extends Fragment {

    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.LayoutManager layoutManager1;
    private RecyclerView recyclerView1;
    private List<OrderItemsModel> selectedProductsDataList = new ArrayList<>();
    private List<String> selectedProductsQuantityList = new ArrayList<>();
    private String object_string, object_stringqty;
    private Button btn_confirm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order__summary, container, false);

        btn_confirm = view.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = selectedProducts.edit();
                editor.putString("selected_products","");
                editor.putString("selected_products_qty","");
                editor.apply();
            }
        });

        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        object_string = selectedProducts.getString("selected_products", "");
        object_stringqty = selectedProducts.getString("selected_products_qty", "");
        Log.i("object_string", object_string);
        Type type = new TypeToken<List<OrderItemsModel>>() {
        }.getType();
        Type typeQty = new TypeToken<List<String>>() {
        }.getType();
        selectedProductsDataList = gson.fromJson(object_string, type);
        selectedProductsQuantityList = gson.fromJson(object_stringqty, typeQty);

        recyclerView1 = view.findViewById(R.id.rv_orders_summary);
        recyclerView1.setHasFixedSize(false);
        layoutManager1 = new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(layoutManager1);

        mAdapter1 = new OrderSummaryAdapter(getContext(), selectedProductsDataList, selectedProductsQuantityList);
        recyclerView1.setAdapter(mAdapter1);
        recyclerView1.setNestedScrollingEnabled(false);

        Log.i("aaaaaa", String.valueOf(mAdapter1));

        return view;

    }


}
