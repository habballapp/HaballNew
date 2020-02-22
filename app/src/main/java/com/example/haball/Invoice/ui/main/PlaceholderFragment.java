package com.example.haball.Invoice.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Invoice.Models.InvoiceDetails_Model;
import com.example.haball.R;
import com.example.haball.Shipment.ui.main.Models.Distributor_InvoiceModel;
import com.google.gson.Gson;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.HashMap;
import java.util.Map;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    private String Token;
    private String DistributorId;
    private String paymentID;
    //invoice Details
    private TextView distri_invoiceID, distri_invoiceDate, distri_invoiceAmount, distri_payment_date, distri_Transaction_amount, distri_status, distri_state;
    private String INVOICE_URL = "http://175.107.203.97:4008/api/Invoices/";
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
                rootView = inflater.inflate(R.layout.fragment_invoice_details, container, false);
                distri_invoiceID = rootView.findViewById(R.id.distri_invoiceID);
                distri_invoiceDate = rootView.findViewById(R.id.distri_invoiceDate);
                distri_invoiceAmount = rootView.findViewById(R.id.distri_invoiceAmount);
                distri_payment_date = rootView.findViewById(R.id.distri_payment_date);
                distri_Transaction_amount = rootView.findViewById(R.id.distri_Transaction_amount);
                distri_status = rootView.findViewById(R.id.distri_status);
                distri_state = rootView.findViewById(R.id.distri_state);
                InvoiceDetailsData();


                break;
            }
            case 2: {
                rootView = inflater.inflate(R.layout.fragment_dealer_information, container, false);
                break;
            }

            case 3: {
                rootView = inflater.inflate(R.layout.fragment_orders_details, container, false);
                break;
            }

            case 4: {
                rootView = inflater.inflate(R.layout.fragment_product_details, container, false);
                break;
            }
            case 5: {
                rootView = inflater.inflate(R.layout.fragment_shipment_details, container, false);
                break;
            }
        }
        return rootView;

    }

    private void InvoiceDetailsData() {
        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("Invoice_ID",
                Context.MODE_PRIVATE);
        paymentID = sharedPreferences3.getString("InvoiceID", "");
        Log.i("payment ID", paymentID);

        Log.i("emthod", "kmkn");

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId invoice", DistributorId);
        Log.i("Token invoice", Token);
        if (!INVOICE_URL.contains(paymentID))
            INVOICE_URL = INVOICE_URL + paymentID;
        Log.i("INVOICE_URL", INVOICE_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, INVOICE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("response", response);
                try {
                    if (response != null && !response.equals("")) {
                        Gson gson = new Gson();
                        InvoiceDetails_Model invoiceModel = gson.fromJson(response, InvoiceDetails_Model.class);
                        distri_invoiceID.setText(invoiceModel.getInvoiceNumber());
                        String string = invoiceModel.getCreatedDate();
                        String[] parts = string.split("T");
                        String Date = parts[0];
                        distri_invoiceDate.setText(Date);
                        distri_invoiceAmount.setText(invoiceModel.getInvoiceTotal());
                        String string_payment = invoiceModel.getCreatedDate();
                        String[] parts_payment = string_payment.split("T");
                        String Date_payment = parts_payment[0];
                        distri_payment_date.setText(Date_payment);
                        distri_Transaction_amount.setText(invoiceModel.getTotalPrice());
//                        tv_status.setText(invoiceModel.getStatus());
                        if (invoiceModel.getStatus().equals("1")) {
                            distri_status.setText("Delivered");
                        } else if (invoiceModel.getStatus().equals("2")) {
                            distri_status.setText("Received");
                        } else if (invoiceModel.getStatus().equals("3")) {
                            distri_status.setText("Returned");
                        } else if (invoiceModel.getStatus().equals("4")) {
                            distri_status.setText("Revised");
                        }
                        if (invoiceModel.getState().equals("0")) {
                            distri_state.setText("Normal");
                        } else if (invoiceModel.getStatus().equals("1")) {
                            distri_state.setText("Consolidate");
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }
}
