package com.example.haball.Distributor.ui.orders;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.haball.Distributor.ui.main.SectionsPagerAdapter;
import com.example.haball.Order.DistributorOrder;
import com.example.haball.Order.DistributorOrderAdapter;
import com.example.haball.Order.DistributorOrder_ItemSelection;
import com.example.haball.R;
import com.example.haball.SplashScreen.SplashScreen;
import com.google.android.material.tabs.TabLayout;


public class Orders_Fragment extends Fragment {

    private OrdersViewModel sendViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button create_payment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(OrdersViewModel.class);
        View root = inflater.inflate(R.layout.activity_distributer_order, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_order_ledger);

        create_payment = root.findViewById(R.id.create_payment);
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

        // specify an adapter (see also next example)
        mAdapter = new DistributorOrderAdapter(this,"Ghulam Rabani & Sons Traders & Distributors","1002312324251524","Invoice","Pending");
        recyclerView.setAdapter(mAdapter);
        return root;
    }
}