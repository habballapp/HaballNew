package com.example.haball.Distributor.ui.retailer.Payment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.haball.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetailerPaymentDashboard extends Fragment {

    public RetailerPaymentDashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_retailer_payment_dashboard, container, false);
    }
}
