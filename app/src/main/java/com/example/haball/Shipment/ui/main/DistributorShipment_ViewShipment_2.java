package com.example.haball.Shipment.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.haball.R;
import com.example.haball.Shipment.ui.main.Models.DistributorShipmentViewShipment2ViewModel;

public class DistributorShipment_ViewShipment_2 extends Fragment {

    private DistributorShipmentViewShipment2ViewModel mViewModel;

    public static DistributorShipment_ViewShipment_2 newInstance() {
        return new DistributorShipment_ViewShipment_2();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.distributor_shipment__view_shipment_2_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DistributorShipmentViewShipment2ViewModel.class);
        // TODO: Use the ViewModel
    }

}
