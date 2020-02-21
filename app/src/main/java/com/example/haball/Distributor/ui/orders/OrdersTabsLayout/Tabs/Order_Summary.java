package com.example.haball.Distributor.ui.orders.OrdersTabsLayout.Tabs;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.haball.Distributor.ui.orders.Adapter.OrderSummaryAdapter;
import com.example.haball.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Order_Summary extends Fragment {

    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.LayoutManager layoutManager1;
    RecyclerView recyclerView1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_order__summary, container, false);


        recyclerView1 = view.findViewById(R.id.rv_orders_summary);

        recyclerView1.setHasFixedSize(false);
        // use a linear layout manager
        layoutManager1 = new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(layoutManager1);
        mAdapter1 = new OrderSummaryAdapter(getContext(),"0","abc","1232","230","32678");

        recyclerView1.setAdapter(mAdapter1);
        Log.i("aaaaaa", String.valueOf(mAdapter1));

        return view;

    }


}
