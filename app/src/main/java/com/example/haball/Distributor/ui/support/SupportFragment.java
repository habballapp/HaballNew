package com.example.haball.Distributor.ui.support;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haball.R;
import com.example.haball.Support.SupportDashboardAdapter;
import com.example.haball.Support.Support_Ticket_Form;
import com.example.haball.Support.Support_dashboard;

import java.util.ArrayList;

public class SupportFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> array = new ArrayList<>();
    private Button btn_add_ticket;

    private SupportViewModel supportViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        supportViewModel =
                ViewModelProviders.of(this).get(SupportViewModel.class);
        View root = inflater.inflate(R.layout.activity_support_dashboard, container, false);
        btn_add_ticket = root.findViewById(R.id.btn_add_ticket);
        btn_add_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Support_Ticket_Form.class);
                startActivity(intent);
            }
        });
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_support_complaints);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new SupportDashboardAdapter(getActivity(),this.getActivity(),"Distributor_Dashboard","584325","Pending","01/01/2020");
        recyclerView.setAdapter(mAdapter);
        return root;
    }
}