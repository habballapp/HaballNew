package com.example.haball.Distributor.ui.retailer.RetailerOrder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.RetailerPlaceOrder;
import com.example.haball.R;

public class RetailerOrderDashboard extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button btn_place_order;
    private String URL_ORDER = "http://175.107.203.97:4007/api/retailerorder/search";
    private FragmentTransaction fragmentTransaction;

    public RetailerOrderDashboard() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_retailer_order_dashboard, container, false);
        btn_place_order = root.findViewById(R.id.btn_place_order);

        recyclerView = root.findViewById(R.id.rv_retailer_order_dashboard);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new RetailerPlaceOrder());
                fragmentTransaction.commit();

            }
        });
        return root;

    }

}
