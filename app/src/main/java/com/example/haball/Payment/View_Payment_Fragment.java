package com.example.haball.Payment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.haball.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class View_Payment_Fragment extends Fragment {


    public View_Payment_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view__payment_, container, false);
    }

}
