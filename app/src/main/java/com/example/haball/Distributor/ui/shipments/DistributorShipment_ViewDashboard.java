package com.example.haball.Distributor.ui.shipments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.haball.R;
import com.example.haball.Shipment.ui.main.Adapters.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class DistributorShipment_ViewDashboard extends Fragment {

    private ShipmentsViewModel sendViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private DistributorShipmentViewDashboardViewModel mViewModel;

    public static DistributorShipment_ViewDashboard newInstance() {
        return new DistributorShipment_ViewDashboard();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       // return inflater.inflate(R.layout.distributor_shipment__view_dashboard_fragment, container, false);
        sendViewModel =
                ViewModelProviders.of(this).get(ShipmentsViewModel.class);
        View root = inflater.inflate(R.layout.activity_distributor_shipment__view_dashboard, container, false);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(), getChildFragmentManager());
        ViewPager viewPager = root.findViewById(R.id.view_pager2);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs2);
        tabs.setupWithViewPager(viewPager);

        return root;
    }



}
