package com.example.haball.Distributor.ui.retailer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haball.Distributor.ui.retailer.Retailor_Management.Adapter.Reatiler_Management_Dashboard_Adapter;
import com.example.haball.R;

public class RetailerFragment extends Fragment {

    private RetailerViewModel shareViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(RetailerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.rv_reatailer_dashboard);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new Reatiler_Management_Dashboard_Adapter("Ghulam Rabani & Sons Traders & Distributors","1002312324251524","334455","Paid");
        recyclerView.setAdapter(mAdapter);

        return root;
    }
}