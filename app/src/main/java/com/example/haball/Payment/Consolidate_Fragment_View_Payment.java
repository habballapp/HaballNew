package com.example.haball.Payment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.haball.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Consolidate_Fragment_View_Payment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_consolidate__fragment__view__payment, container, false);
//        recyclerView = root.findViewById(R.id.rv_consolidate);
//
//        recyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        mAdapter = new Consolidate_Fragment_Adapter(this,"Ghulam Rabani & Sons Traders & Distributors","1002312324251524","Shield","12/02/2020","PKR 600,000.00","600,000.00","Paid");
//
//        recyclerView.setAdapter(mAdapter);

        return root;
    }
}
