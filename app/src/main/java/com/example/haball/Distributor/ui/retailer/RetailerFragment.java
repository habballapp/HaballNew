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
import com.example.haball.Distributor.ui.retailer.Retailor_Management.Adapter.Retailer_Management_Dashboard_Adapter;
import com.example.haball.R;

public class RetailerFragment extends Fragment {

    private RetailerViewModel shareViewModel;

    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(RetailerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_retailer__dashboard, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.rv_retailer_dashboard);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        // use a linear layout manager



        // specify an adapter (see also next example)
        mAdapter = new Retailer_Management_Dashboard_Adapter(getContext(), "Ghulam Rabani & Sons Traders & Distributors","1002312324251524","12/3/20","Paid");
        recyclerView.setAdapter(mAdapter);

        return root;
    }
}