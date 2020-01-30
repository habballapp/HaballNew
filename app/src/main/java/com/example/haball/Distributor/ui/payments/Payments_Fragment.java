package com.example.haball.Distributor.ui.payments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.haball.R;


public class Payments_Fragment extends Fragment {

    private PaymentsViewModel paymentsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        paymentsViewModel =
                ViewModelProviders.of(this).get(PaymentsViewModel.class);
        View root = inflater.inflate(R.layout.activity_payment_ledger, container, false);

        return root;
    }
}