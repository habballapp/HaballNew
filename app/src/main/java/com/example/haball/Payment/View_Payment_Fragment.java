package com.example.haball.Payment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.haball.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class View_Payment_Fragment extends Fragment {

    private String PaymentsRequestId;

    public View_Payment_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("paymentsRequestListID",
                Context.MODE_PRIVATE);
        PaymentsRequestId = sharedPreferences3.getString("paymentsRequestListID", "");

//        PaymentsRequestId = getArguments().getString("PaymentsRequestId");
        Toast.makeText(getContext(),PaymentsRequestId,Toast.LENGTH_SHORT);
        return inflater.inflate(R.layout.fragment_view__payment_, container, false);

    }

}
