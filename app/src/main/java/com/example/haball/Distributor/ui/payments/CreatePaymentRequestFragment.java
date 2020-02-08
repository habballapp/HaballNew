package com.example.haball.Distributor.ui.payments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.haball.R;

public class CreatePaymentRequestFragment extends Fragment {
    private String Token, DistributorId;
    private Button btn_create;
    private String URL_PAYMENT_REQUESTS = "http://175.107.203.97:4008/api/prepaidrequests/search";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_payment__screen1, container, false);

        btn_create = root.findViewById(R.id.btn_create);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return root;
    }
}
