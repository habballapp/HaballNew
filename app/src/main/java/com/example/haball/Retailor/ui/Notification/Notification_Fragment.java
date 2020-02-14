package com.example.haball.Retailor.ui.Notification;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.haball.Distributor.ui.Fragment_Notification.NotificationAdapter;
import com.example.haball.R;
import com.example.haball.Retailor.ui.Notification.Adapters.Notification_Adapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notification_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter NotificationAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public Notification_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
   //     return inflater.inflate(R.layout.fragment_blank, container, false);
        View root = inflater.inflate(R.layout.notification_fragment, container, false);

        recyclerView = root.findViewById(R.id.rv_notification_retailor);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
        NotificationAdapter = new Notification_Adapter(getContext(),"Payment","Payment ID 345697977970 has been approved successfully" );
        recyclerView.setAdapter(NotificationAdapter);

        return root;
    }

}
