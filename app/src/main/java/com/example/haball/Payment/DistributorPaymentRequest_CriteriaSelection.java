package com.example.haball.Payment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.haball.R;

public class DistributorPaymentRequest_CriteriaSelection extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_payment_request__criteria_selection);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.custom_actionbar, null);

        actionBar.setCustomView(customView);
        actionBar.setDisplayShowCustomEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.rv_payment_ledger);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new DistributorPaymentRequestAdaptor(DistributorPaymentRequest_CriteriaSelection.this,"Ghulam Rabani & Sons Traders & Distributors","1002312324251524","Invoice","PKR 50,000.00","PKR 600,000.00");
        mAdapter = new DistributorPaymentRequestAdaptor(DistributorPaymentRequest_CriteriaSelection.this,"Ghulam Rabani & Sons Traders & Distributors","1002312324251524","Invoice","PKR 50,000.00","PKR 600,000.00");
        mAdapter = new DistributorPaymentRequestAdaptor(DistributorPaymentRequest_CriteriaSelection.this,"Ghulam Rabani & Sons Traders & Distributors","1002312324251524","Invoice","PKR 50,000.00","PKR 600,000.00");
        recyclerView.setAdapter(mAdapter);
    }
}
