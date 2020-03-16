package com.example.haball.Distributor.ui.retailer.Payment.ViewPayment;

import android.os.Bundle;

import com.example.haball.Invoice.ui.main.SectionsPagerAdapter;
import com.example.haball.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class RetailerPaymentView extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.distributor_shipment__view_dashboard_fragment, container, false);
//        sendViewModel =
//                ViewModelProviders.of(this).get(ShipmentsViewModel.class);
        View root = inflater.inflate(R.layout.activity_retailer_payment_view, container, false);
        com.example.haball.Invoice.ui.main.SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(), getChildFragmentManager());
        ViewPager viewPager = root.findViewById(R.id.view_pager_payment);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabspayment);
        tabs.setupWithViewPager(viewPager);
        return  root;

    }
}