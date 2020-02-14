package com.example.haball.Retailor.ui.Dashboard;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.haball.Distributor.ui.payments.PaymentsViewModel;
import com.example.haball.Payment.Payment_Amount;
import com.example.haball.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends Fragment {

    private PaymentsViewModel paymentsViewModel;
    private RecyclerView recyclerView;
    private DrawerLayout drawer;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Button create_payment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        paymentsViewModel =
                ViewModelProviders.of(this).get(PaymentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard_retailor, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_payment_request);
        create_payment = root.findViewById(R.id.create_payment);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);



        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
       //mAdapter = new DistributorPaymentRequestAdaptor(DashBoardFragment.this,"Ghulam Rabani & Sons Traders & Distributors","1002312324251524","Invoice","PKR 50,000.00","PKR 600,000.00");

        recyclerView.setAdapter(mAdapter);
        return root;
    }
}
