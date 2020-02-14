package com.example.haball.Retailor.ui.Network.Select_Tabs;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.haball.Retailor.ui.Network.Adapters.Fragment_My_Network_Adapter;
import com.example.haball.Retailor.ui.Network.Models.Fragment_My_Netwok_Model;
import com.example.haball.R;

/**
        * A simple {@link Fragment} subclass.
        */
public class My_Network_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Fragment_My_Netwok_Model paymentsViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_my__network_, container, false);
      /*  paymentsViewModel =
                ViewModelProviders.of(this).get(Fragment_My_Netwok_Model.class);
       */
        View root = inflater.inflate(R.layout.fragment_my__network_, container, false);

        //inflater = LayoutInflater.from(getContext());
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_my_network);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter (see also next example)
       // mAdapter = new Fragment_My_Network_Adapter(this, "Connected", "123456789","Mz-2,Horizon Vista,Plot-10,Block-4,Clifton");
      //  recyclerView.setAdapter(mAdapter);
        return root;
    }

}
