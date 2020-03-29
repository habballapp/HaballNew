package com.example.haball.Retailor.ui.Dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.haball.R;
import com.example.haball.Retailor.ui.Dashboard.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class Dashboard_Tabs extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.distributor_shipment__view_dashboard_fragment, container, false);
//        sendViewModel =
//                ViewModelProviders.of(this).get( ShipmentsViewModel.class);
        View root = inflater.inflate(R.layout.activity_dashboard__tabs, container, false);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(), getChildFragmentManager());
        ViewPager viewPager = root.findViewById(R.id.view_pager_retailer_dashboard);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        TabLayout tabs = root.findViewById(R.id.tabs_retailer_dashboard);
        tabs.setupWithViewPager(viewPager);

        return root;

    }}