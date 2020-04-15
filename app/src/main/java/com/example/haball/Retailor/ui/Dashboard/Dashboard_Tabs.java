package com.example.haball.Retailor.ui.Dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
        final View root = inflater.inflate(R.layout.fragment_dashboard__tabs, container, false);

        SharedPreferences selectedTab = getContext().getSharedPreferences("OrderTabsFromDraft",
                Context.MODE_PRIVATE);
        int value = Integer.parseInt(selectedTab.getString("TabNo", "0"));
        SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
        editorOrderTabsFromDraft.putString("TabNo", "0");
        editorOrderTabsFromDraft.apply();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getChildFragmentManager());
        final ViewPager viewPager = root.findViewById(R.id.view_pager_ret5);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(value);
        final TabLayout tabs = root.findViewById(R.id.tabs_ret5);
        tabs.setupWithViewPager(viewPager);

//        LinearLayout tabStrip = ((LinearLayout)tabs.getChildAt(0));
//        for(int i = 0; i < tabStrip.getChildCount(); i++) {
//            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    return true;
//                }
//            });
//        }
        return root;

    }}