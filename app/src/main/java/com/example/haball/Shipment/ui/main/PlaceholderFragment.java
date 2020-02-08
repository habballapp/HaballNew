package com.example.haball.Shipment.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.haball.R;
import com.example.haball.Shipment.ui.main.Models.PageViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);

        }
        Log.i("abbccc", String.valueOf(index));
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = null;

        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {

            case 1: {
                rootView = inflater.inflate(R.layout.distributor_shipment__view_shipment_1_fragment, container, false);
                break;
            }
            case 2: {
                rootView = inflater.inflate(R.layout.distributor_shipment__view_shipment_2_fragment, container, false);
                break;
            }

            case 3: {
                rootView = inflater.inflate(R.layout.distributor_shipment__view_shipment_3_fragment, container, false);
                break;
            }

            case 4: {
                rootView = inflater.inflate(R.layout.distributor_shipment__view_shipment_fragment  ,container,false);
                break;
            }
        }
        return rootView;

    }
}