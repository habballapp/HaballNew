package com.example.haball.Distributor.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distribution_Login.Distribution_Login;
import com.example.haball.Distributor.DistributorDashboard;
import com.example.haball.Distributor.DistributorOrdersAdapter;
import com.example.haball.Distributor.DistributorOrdersModel;
import com.example.haball.Distributor.DistributorPaymentsAdapter;

import com.example.haball.Distributor.DistributorPaymentsModel;
import com.example.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.example.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter PaymentsAdapter;
    private RecyclerView.Adapter OrdersAdapter;

    private RecyclerView.LayoutManager layoutManager;
    private String URL_DISTRIBUTOR_DASHBOARD = "http://175.107.203.97:4008/api/dashboard/ReadDistributorDashboard";
    private String URL_DISTRIBUTOR_PAYMENTS = "http://175.107.203.97:4008/api/dashboard/ReadDistributorPayments";
    private String URL_DISTRIBUTOR_ORDERS = "http://175.107.203.97:4008/api/orders/search";

    private TextView value_unpaid_amount, value_paid_amount;
    private List<DistributorPaymentsModel> PaymentsList = new ArrayList<>();
    private List<DistributorOrdersModel> OrdersList = new ArrayList<>();
    private String Token, DistributorId;

    private PageViewModel pageViewModel;

    private Spinner spinner_consolidate;
    private Spinner spinner2;
    private EditText conso_edittext;
    private List<String> consolidate_felter = new ArrayList<>();
    private List<String> filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPayments;
    private ArrayAdapter<String> arrayAdapterFeltter;
    private Button consolidate;
    private String Filter_selected, Filter_selected_value;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;

        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1: {
                rootView = inflater.inflate(R.layout.fragment_payments, container, false);
                fetchPaymentsData();
                recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_fragment_payments);

                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(rootView.getContext());
                recyclerView.setLayoutManager(layoutManager);
                consolidate = rootView.findViewById(R.id.consolidate);

                spinner_consolidate = (Spinner) rootView.findViewById(R.id.spinner_conso);
                spinner2 = (Spinner) rootView.findViewById(R.id.conso_spinner2);
                conso_edittext = (EditText) rootView.findViewById(R.id.conso_edittext);
                spinner2.setVisibility(View.GONE);
                conso_edittext.setVisibility(View.GONE);
                consolidate_felter.add ("Select Criteria");
                consolidate_felter.add ("Payment ID");
                consolidate_felter.add ("Company");
                consolidate_felter.add ("Transaction Date");
                consolidate_felter.add ("Created Date");
                consolidate_felter.add ("Amount");
                consolidate_felter.add ("Status");

                arrayAdapterPayments = new ArrayAdapter<>(rootView.getContext(),
                        android.R.layout.simple_spinner_dropdown_item, consolidate_felter);

                spinner_consolidate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        spinner2.setVisibility(View.GONE);
                        conso_edittext.setVisibility(View.GONE);
                        if(i == 0){
                            ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                        }
                        else{
                            Filter_selected = consolidate_felter.get(i);

                            if(!Filter_selected.equals("Status"))
                                spinner2.setSelection(0);
                            if(!conso_edittext.getText().equals(""))
                                conso_edittext.setText("");

                            if(Filter_selected.equals("Payment ID")) {
                                Filter_selected = "PrePaidNumber";
                                conso_edittext.setVisibility(View.VISIBLE);
                            } else if(Filter_selected.equals("Company")) {
                                Filter_selected = "CompanyName";
                                conso_edittext.setVisibility(View.VISIBLE);
                            } else if(Filter_selected.equals("Transaction Date")) {
                                Toast.makeText(getContext(),"Transaction Date selected",Toast.LENGTH_LONG).show();
                            } else if(Filter_selected.equals("Created Date")) {
                                Toast.makeText(getContext(),"Created Date selected",Toast.LENGTH_LONG).show();
                            } else if(Filter_selected.equals("Amount")) {
                                Toast.makeText(getContext(),"Amount selected",Toast.LENGTH_LONG).show();
                            } else if(Filter_selected.equals("Status")) {
                                Filter_selected = "Status";
                                spinner2.setVisibility(View.VISIBLE);
                            }
//                    try {
//                        fetchPaymentLedgerData(companies.get(Filter_selected));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                arrayAdapterPayments.notifyDataSetChanged();
                spinner_consolidate.setAdapter(arrayAdapterPayments);

                filters.add ("Status");
                filters.add ("Processing Payment");
                filters.add ("Unpaid ");
                filters.add ("Paid");
                arrayAdapterFeltter = new ArrayAdapter<>(rootView.getContext(),
                        android.R.layout.simple_spinner_dropdown_item, filters);
                Log.i("aaaa1111", String.valueOf(consolidate_felter));
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(i == 0){
                            ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                        }
                        else{
                            Filter_selected_value = String.valueOf(i-2);
                            Log.i("Filter_selected_value",Filter_selected_value);
//                            try {
//                                fetchFilteredPaymentRequests();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                arrayAdapterFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                arrayAdapterFeltter.notifyDataSetChanged();
                spinner2.setAdapter(arrayAdapterFeltter);


                conso_edittext.addTextChangedListener(new TextWatcher() {

                    public void afterTextChanged(Editable s) {
                        Log.i("text1", "check");
                        Log.i("text", String.valueOf(s));
                        Filter_selected_value = String.valueOf(s);
//                        try {
//                            fetchFilteredPaymentRequests();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                });

//        spinner_consolidate("Select Criteria","Invoice No", "Company", "Created Date", "Total Price", "Paid Amount" ,"Status","Created By");
//        spinner_consolidate.setOnItemSelectedListener(new Spinner().OnItemSelectedListener<String>() {
//            @Override public void onItemSelected(Spinner view, int position, long id, String item) {
//                if (position!=6){
//                    spinner_consolidate.setVisibility(View.GONE);
//                }
//                else{
//                    spinner2.setVisibility(View.VISIBLE);
//
//                }
//                Snackbar.make(view, "Clicked " + item , Snackbar.LENGTH_LONG).show();
//                Toast.makeText(getActivity(), "id"+position, Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        spinner2.setItems("Pending", "Unpaid", "Partially Paid", "Paid", "Payment Processing");
//        spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
//
//
//            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                Snackbar.make(view, "Clicked " + item , Snackbar.LENGTH_LONG).show();
//                Toast.makeText(getActivity(), "id"+position, Toast.LENGTH_SHORT).show();
//
//            }
//        });

                layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);

                consolidate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(),"Consolidate clicked",Toast.LENGTH_LONG).show();
//                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.remove(PaymentRequestDashboard.this);
//                        fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(), new CreatePaymentRequestFragment());
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.commit();
                    }
                });





                break;
            }

            case 2: {
                rootView = inflater.inflate(R.layout.fragment_orders, container, false);
                try {
                    fetchOrderData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_fragment_orders);

                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(rootView.getContext());
                recyclerView.setLayoutManager(layoutManager);

                break;

//                recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_fragment_orders);
//
//                // use this setting to improve performance if you know that changes
//                // in content do not change the layout size of the RecyclerView
//                recyclerView.setHasFixedSize(true);
//
//                // use a linear layout manager
//                layoutManager = new LinearLayoutManager(rootView.getContext());
//                recyclerView.setLayoutManager(layoutManager);
//
//                // specify an adapter (see also next example)
//                OrdersAdapter = new DistributorOrdersAdapter(this,"Ghulam Rabani & Sons Traders & Distributors","51247895354254780369","PKR 600,000.00","Approved");
//                recyclerView.setAdapter(OrdersAdapter);
//                break;
            }
            case 3: {
                rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
                value_unpaid_amount = rootView.findViewById(R.id.value_unpaid_amount);
                value_paid_amount = rootView.findViewById(R.id.value_paid_amount);
                fetchDashboardData();
                break;
            }

        }
        return rootView;
    }
    private void fetchOrderData() throws JSONException {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token","");
        DistributorId = sharedPreferences.getString("Distributor_Id","");
        Log.i("Token", Token);
        JSONObject map = new JSONObject();
        map.put("Status", -1);
        map.put("OrderState", -1);
        map.put("DistributorId", DistributorId);
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0);

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_DISTRIBUTOR_ORDERS, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                //                    JSONArray jsonArray = new JSONArray(result);
                Gson gson = new Gson();
                Type type = new TypeToken<List<DistributorOrdersModel>>(){}.getType();
                OrdersList = gson.fromJson(result.toString(),type);

                OrdersAdapter = new DistributorOrdersAdapter(getContext(),OrdersList);
                recyclerView.setAdapter(OrdersAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printErrorMessage(error);

                error.printStackTrace();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " +Token);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(sr);
    }


    private void fetchPaymentsData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token","");
        Log.i("Token", Token);

        StringRequest sr = new StringRequest(Request.Method.POST, URL_DISTRIBUTOR_PAYMENTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                try{
                    JSONArray jsonArray = new JSONArray(result);
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<DistributorPaymentsModel>>(){}.getType();
                    PaymentsList = gson.fromJson(jsonArray.toString(),type);

                    PaymentsAdapter = new DistributorPaymentsAdapter(getContext(),PaymentsList, null);
                    recyclerView.setAdapter(PaymentsAdapter);

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printErrorMessage(error);

                error.printStackTrace();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " +Token);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void fetchDashboardData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token","");

        StringRequest sr = new StringRequest(Request.Method.POST, URL_DISTRIBUTOR_DASHBOARD, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    DecimalFormat formatter1 = new DecimalFormat("#,###,###,##0.00");
                    String yourFormattedString1 = formatter1.format(Double.parseDouble(jsonObject.get("TotalUnpaidAmount").toString()));
                    DecimalFormat formatter2 = new DecimalFormat("#,###,###,##0.00");
                    String yourFormattedString2 = formatter2.format(Double.parseDouble(jsonObject.get("TotalPrepaidAmount").toString()));
                    value_unpaid_amount.setText(yourFormattedString1);
                    value_paid_amount.setText(yourFormattedString2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printErrorMessage(error);

                error.printStackTrace();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " +Token);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(sr);
    }


    private void printErrorMessage(VolleyError error) {
        if(error.networkResponse != null && error.networkResponse.data != null) {
            try {
                String message = "";
                String responseBody = new String(error.networkResponse.data, "utf-8");
                JSONObject data = new JSONObject(responseBody);
                Iterator<String> keys = data.keys();
                while(keys.hasNext()) {
                    String key = keys.next();
//                if (data.get(key) instanceof JSONObject) {
                    Log.i("message", String.valueOf(data.get(key)));
                    if(data.get(key).equals("TokenExpiredError")) {
                        SharedPreferences login_token = getContext().getSharedPreferences("LoginToken",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = login_token.edit();
                        editor.remove("Login_Token");
                        editor.commit();
                        Intent login = new Intent(getActivity(), Distribution_Login.class);
                        startActivity(login);
                        getActivity().finish();

                    }

                    message = message + data.get(key) + "\n";
//                }
                }
//                    if(data.has("message"))
//                        message = data.getString("message");
//                    else if(data. has("Error"))
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}