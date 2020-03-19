package com.example.haball.Distributor.ui.retailer.Payment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.ui.retailer.Payment.Adapters.PaymentDashboardAdapter;
import com.example.haball.Distributor.ui.retailer.Payment.Models.Dist_Retailer_Dashboard_Model;
import com.example.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetailerPaymentDashboard extends Fragment {
    private RecyclerView rv_paymentDashBoard;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String Token, DistributorId;
    ;
    private String URL = "http://175.107.203.97:4013/api/retailerprepaidrequest/search";
    private List<Dist_Retailer_Dashboard_Model> PaymentsList = new ArrayList<>();
    //spiner1
    private Spinner sp_payment_dist_retailer;
    private List<String> dist_ret_payment = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPayments_Ret;
    private String Filter_selected, Filter_selected_value;
    //spinner2
    private Spinner payment_retailer_spiner2;
    private List<String> payment_filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter_PaymentFeltter;
    private EditText edt_payment_ret;
    private TextView tv_shipment_no_data;

    public RetailerPaymentDashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout  create_payment = root.findViewById(R.id.create_payment); for this fragment
        View root = inflater.inflate(R.layout.fragment_retailer_payment_dashboard, container, false);
        rv_paymentDashBoard = (RecyclerView) root.findViewById(R.id.rv_dist_payment_retailer);
        sp_payment_dist_retailer = root.findViewById(R.id.spinner_dashboard_retailor);
        edt_payment_ret = root.findViewById(R.id.edt_dist_payment_ret);
        payment_retailer_spiner2 = root.findViewById(R.id.payment_dist_retailer_spiner);
        tv_shipment_no_data = root.findViewById(R.id.tv_shipment_no_data);
        tv_shipment_no_data.setVisibility(View.GONE);

        payment_retailer_spiner2.setVisibility(View.GONE);
        edt_payment_ret.setVisibility(View.GONE);
        dist_ret_payment.add("Select Criteria");
        dist_ret_payment.add("Company");
        dist_ret_payment.add("Payment Id");
        dist_ret_payment.add("Amount");
        dist_ret_payment.add("Status");

        arrayAdapterPayments_Ret = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, dist_ret_payment);
        sp_payment_dist_retailer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                } else {
                    Filter_selected = dist_ret_payment.get(i);

                    if (!Filter_selected.equals("Status"))
                        payment_retailer_spiner2.setSelection(0);
                    if (!edt_payment_ret.getText().equals(""))
                        edt_payment_ret.setText("");

                    if (Filter_selected.equals("Invoice No")) {
                        Filter_selected = "ConsolidatedInvoiceNumber";
                        payment_retailer_spiner2.setVisibility(View.GONE);
                        edt_payment_ret.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Company")) {
                        Filter_selected = "CompanyName";
                        payment_retailer_spiner2.setVisibility(View.GONE);
                        edt_payment_ret.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Created Date")) {
                        payment_retailer_spiner2.setVisibility(View.GONE);
                        edt_payment_ret.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Created Date selected", Toast.LENGTH_LONG).show();
                    } else if (Filter_selected.equals("Total Price")) {
                        payment_retailer_spiner2.setVisibility(View.GONE);
                        edt_payment_ret.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Total Price selected", Toast.LENGTH_LONG).show();
                    } else if (Filter_selected.equals("Paid Amount")) {
                        payment_retailer_spiner2.setVisibility(View.GONE);
                        edt_payment_ret.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Paid Amount selected", Toast.LENGTH_LONG).show();
                    } else if (Filter_selected.equals("Status")) {
                        Filter_selected = "Status";
                        payment_retailer_spiner2.setVisibility(View.VISIBLE);
                        edt_payment_ret.setVisibility(View.GONE);
                    } else if (Filter_selected.equals("Created By")) {
                        Filter_selected = "CreatedBy";
                        payment_retailer_spiner2.setVisibility(View.GONE);
                        edt_payment_ret.setVisibility(View.VISIBLE);
                    } else {
                        payment_retailer_spiner2.setVisibility(View.GONE);
                        edt_payment_ret.setVisibility(View.GONE);

                    }
                }
//                    try {
//                        fetchPaymentLedgerData(companies.get(Filter_selected));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        arrayAdapterPayments_Ret.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPayments_Ret.notifyDataSetChanged();
        sp_payment_dist_retailer.setAdapter(arrayAdapterPayments_Ret);

        //filter payment
        payment_filters.add("Status");
        payment_filters.add("Paid");
        payment_filters.add("Unpaid ");
        arrayAdapter_PaymentFeltter = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, payment_filters);

        payment_retailer_spiner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                } else {
                    Filter_selected_value = String.valueOf(i - 1);
                    Log.i("Filter_selected_value", Filter_selected_value);
                    try {
                        fetchFilteredRetailerPayments();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        arrayAdapter_PaymentFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter_PaymentFeltter.notifyDataSetChanged();
        payment_retailer_spiner2.setAdapter(arrayAdapter_PaymentFeltter);

        edt_payment_ret.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                Log.i("text1", "check");
                Log.i("text", String.valueOf(s));
                Filter_selected_value = String.valueOf(s);
                try {
                    fetchFilteredRetailerPayments();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        //recyclerview
        rv_paymentDashBoard.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        rv_paymentDashBoard.setLayoutManager(layoutManager);

        try {
            fetchPaymentsData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
    }

    private void fetchPaymentsData() throws JSONException {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("TotalRecords", 10);
        jsonObject.put("PageNumber", 0);

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject result) {
                try {
                    System.out.println("RESPONSE PAYMENTS" + result.getJSONArray("PrePaidRequestData"));
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Dist_Retailer_Dashboard_Model>>() {
                    }.getType();
                    PaymentsList = gson.fromJson(result.getJSONArray("PrePaidRequestData").toString(), type);
                    if (PaymentsList.size() != 0) {

                        mAdapter = new PaymentDashboardAdapter(getContext(), PaymentsList);
                        rv_paymentDashBoard.setAdapter(mAdapter);
                    } else {
                        tv_shipment_no_data.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json");

                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void fetchFilteredRetailerPayments() throws JSONException {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        JSONObject map = new JSONObject();

        map.put("TotalRecords", 10);
        map.put("PageNumber", 0);
        map.put(Filter_selected, Filter_selected_value);
        Log.i("Mapsssss", String.valueOf(map));
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.i("retailerPayment", result.toString());

                Gson gson = new Gson();
                Type type = new TypeToken<List<Dist_Retailer_Dashboard_Model>>() {
                }.getType();
                try {
                    PaymentsList = gson.fromJson(result.getJSONArray("PrePaidRequestData").toString(), type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mAdapter = new PaymentDashboardAdapter(getContext(), PaymentsList);
                rv_paymentDashBoard.setAdapter(mAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json; charset=UTF-8 ");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }

}
