package com.example.haball.Retailor.ui.Network.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haball.R;
import com.example.haball.Retailor.ui.Network.Adapters.Fragment_My_Network_Adapter;
import com.example.haball.Retailor.ui.Network.Adapters.Fragment_Recieved_Adaptor;
import com.example.haball.Retailor.ui.Network.Adapters.Fragment_Sent_Adaptor;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private RecyclerView recyclerView,recyclerView1,recyclerView2;
    private RecyclerView.Adapter mAdapter,mAdapter1,mAdapter2;
    private RecyclerView.LayoutManager layoutManager;
    private TextView tv_shipment_no_data, tv_shipment_no_data1,tv_shipment_no_data3;

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
        pageViewModel.setIndex(index);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = null;


        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {

            case 1: {

                rootView = inflater.inflate(R.layout.fragment_my__network_, container, false);
                recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_my_network);

                recyclerView.setHasFixedSize(false);
                // use a linear layout manager
                layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                mAdapter = new Fragment_My_Network_Adapter(getContext(), "Connected", "123456789","Mz-2,Horizon Vista,Plot-10,Block-4,Clifton");
                recyclerView.setAdapter(mAdapter);
                Log.i("aaaaaa", String.valueOf(mAdapter));
                break;
            }
            case 2: {
                rootView = inflater.inflate(R.layout.fragment_sent_, container, false);
                recyclerView1 = (RecyclerView) rootView.findViewById(R.id.rv_sent);
                recyclerView1.setHasFixedSize(false);
                // use a linear layout manager
                layoutManager = new LinearLayoutManager(getContext());
                recyclerView1.setLayoutManager(layoutManager);
                mAdapter1 = new Fragment_Sent_Adaptor(getContext(), "12345","Mz-2,Horizon Vista,Plot-10,Block-4,Clifton");
                recyclerView1.setAdapter(mAdapter1);
                Log.i("aaaaaa", String.valueOf(mAdapter1));
                break;
            }

            case 3: {
                rootView = inflater.inflate(R.layout.fragment_recieved_, container, false);
                recyclerView2 = (RecyclerView) rootView.findViewById(R.id.rv_my_network_recieved);
                recyclerView2.setHasFixedSize(false);
                // use a linear layout manager
                layoutManager = new LinearLayoutManager(getContext());
                recyclerView2.setLayoutManager(layoutManager);
                mAdapter2 = new Fragment_Recieved_Adaptor(getContext(), "123456789","Mz-2,Horizon Vista,Plot-10,Block-4,Clifton");
                recyclerView2.setAdapter(mAdapter2);
                Log.i("aaaaaa", String.valueOf(mAdapter2));
                break;
            }
        }
        return rootView;

    }
}