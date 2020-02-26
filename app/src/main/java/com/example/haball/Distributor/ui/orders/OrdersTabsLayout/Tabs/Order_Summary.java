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
import android.widget.TextView;
import android.widget.Toast;

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
    private Button btn_confirm, btn_more_items;
    private TextView gross_amount, discount_amount, gst_amount, total_amount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order__summary, container, false);
        gross_amount = view.findViewById(R.id.gross_amount);
        discount_amount = view.findViewById(R.id.discount_amount);
        gst_amount = view.findViewById(R.id.gst_amount);
        total_amount = view.findViewById(R.id.total_amount);
        btn_more_items = view.findViewById(R.id.btn_more_items);

        btn_more_items.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_LONG).show();
                SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = grossamount.edit();
                editor.putString("grossamount","0");
                editor.apply();

                getFragmentManager().popBackStack();
            }
        });

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
        Log.i("object_stringqty", object_stringqty);
        Type type = new TypeToken<List<OrderItemsModel>>() {
        }.getType();
        Type typeQty = new TypeToken<List<String>>() {
        }.getType();
        selectedProductsDataList = gson.fromJson(object_string, type);
        selectedProductsQuantityList = gson.fromJson(object_stringqty, typeQty);

        SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
                Context.MODE_PRIVATE);
        gross_amount.setText(grossamount.getString("grossamount", ""));
        discount_amount.setText(" - ");

        float gstAmount = (Float.parseFloat(grossamount.getString("grossamount", "")) * 17) / 100;
        float totalAmount = Float.parseFloat(grossamount.getString("grossamount", "")) + gstAmount;

        gst_amount.setText(String.valueOf(gstAmount));
        total_amount.setText(String.valueOf(totalAmount));


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
