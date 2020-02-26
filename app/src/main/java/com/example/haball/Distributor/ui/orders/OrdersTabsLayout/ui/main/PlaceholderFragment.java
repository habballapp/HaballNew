package com.example.haball.Distributor.ui.orders.OrdersTabsLayout.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.ui.orders.Adapter.CompanyFragmentAdapter;
import com.example.haball.Distributor.ui.orders.Adapter.DistributorOrderAdapter;
import com.example.haball.Distributor.ui.orders.Adapter.OrderSummaryAdapter;
import com.example.haball.Distributor.ui.orders.Adapter.OrdersItemsAdapter;
import com.example.haball.Distributor.ui.orders.Models.Company_Fragment_Model;
import com.example.haball.Distributor.ui.orders.Models.OrderFragmentModel;
import com.example.haball.Distributor.ui.orders.OrdersTabsLayout.Tabs.Orders_Items_Fragment;
import com.example.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.example.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private RecyclerView recyclerView,recyclerView1;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.LayoutManager layoutManager ,layoutManager1;
    private Button create_payment;
    private Spinner spinner_consolidate;
    private List<Company_Fragment_Model> CompanyList;
    private String URL_Company = "http://175.107.203.97:4008/api/company/ReadActiveCompanyOrders/";
    private String Token, DistributorId;
    private String Filter_selected, Filter_selected_value;
    private Spinner spinner2;
    private EditText conso_edittext;
    private List<String> consolidate_felter = new ArrayList<>();
    private List<String> filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPayments;
    private ArrayAdapter<String> arrayAdapterFeltter;
    private ViewGroup mycontainer;
    private LayoutInflater myinflater;
    private ViewPager mPager;
    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private  Button place_item_button;

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
        mycontainer = container;
        myinflater = inflater;
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {

            case 1: {
                rootView = inflater.inflate(R.layout.activity_distributer_order, container, false);
                Holderorders(rootView);

                Log.i("aaaaaa", String
               .valueOf(mAdapter));
                break;

            }



            case 2: {

                rootView = inflater.inflate(R.layout.fragment_order__summary, container, false);
                  break;
            }

        }
            return rootView;


    }


    private void Holderorders(final View root){


        recyclerView =  root.findViewById(R.id.rv_order_ledger);
       // create_payment = root.findViewById(R.id.place_order_button);

       /* create_payment = root.findViewById(R.id.place_order_button);
        create_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(1); // Change to page 1, i.e., FragmentB
            }
        });*/


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

//        spinner_consolidate = (Spinner) root.findViewById(R.id.spinner_conso);
//        spinner2 = (Spinner) root.findViewById(R.id.conso_spinner2);
//        conso_edittext = (EditText) root.findViewById(R.id.conso_edittext);
//        spinner2.setVisibility(View.GONE);
//        conso_edittext.setVisibility(View.GONE);
//        consolidate_felter.add ("Select Criteria");
//        consolidate_felter.add ("Order No");
//        consolidate_felter.add ("Company");
//        consolidate_felter.add ("Payment Term");
//        consolidate_felter.add ("Created Date");
//        consolidate_felter.add ("Amount");
//        consolidate_felter.add ("Status");
//
//        arrayAdapterPayments = new ArrayAdapter<>(root.getContext(),
//                android.R.layout.simple_dropdown_item_1line, consolidate_felter);
//
//
//        spinner_consolidate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                spinner2.setVisibility(View.GONE);
//                conso_edittext.setVisibility(View.GONE);
//                if(i == 0){
//                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
//                }
//                else{
//                    Filter_selected = consolidate_felter.get(i);
//                    Log.i("Filter_selected", Filter_selected);
//                    if(!Filter_selected.equals("Status"))
//                        spinner2.setSelection(0);
//                    if(!Filter_selected.equals("Payment Term"))
//                        spinner2.setSelection(0);
//                    if(!conso_edittext.getText().equals(""))
//                        conso_edittext.setText("");
//
//                    if(Filter_selected.equals("Order No")) {
//                        Filter_selected = "OrderNumber";
//                        conso_edittext.setVisibility(View.VISIBLE);
//                    } else if(Filter_selected.equals("Company")) {
//                        Filter_selected = "Company";
//                        conso_edittext.setVisibility(View.VISIBLE);
//                    } else if(Filter_selected.equals("Payment Term")) {
//                        Filter_selected = "PaymentType";
//                        filters = new ArrayList<>();
//                        filters.add ("Select All");
//                        filters.add ("Pre Payment");
//                        filters.add ("Post Payment");
//                        arrayAdapterFeltter = new ArrayAdapter<>(root.getContext(),
//                                android.R.layout.simple_dropdown_item_1line, filters);
//                        arrayAdapterFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        arrayAdapterFeltter.notifyDataSetChanged();
//                        spinner2.setAdapter(arrayAdapterFeltter);
//
//                        spinner2.setVisibility(View.VISIBLE);
//                    } else if(Filter_selected.equals("Created Date")) {
//                        Toast.makeText(getContext(),"Created Date selected",Toast.LENGTH_LONG).show();
//                    } else if(Filter_selected.equals("Amount")) {
//                        Toast.makeText(getContext(),"Amount selected",Toast.LENGTH_LONG).show();
//                    } else if(Filter_selected.equals("Status")) {
//                        Filter_selected = "Status";
//                        filters = new ArrayList<>();
//                        filters.add ("Status");
//                        filters.add ("Pending");
//                        filters.add ("Approved");
//                        filters.add ("Rejected");
//                        filters.add ("Draft");
//                        filters.add ("Cancelled");
//                        arrayAdapterFeltter = new ArrayAdapter<>(root.getContext(),
//                                android.R.layout.simple_dropdown_item_1line, filters);
//
//                        arrayAdapterFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        arrayAdapterFeltter.notifyDataSetChanged();
//                        spinner2.setAdapter(arrayAdapterFeltter);
//
//                        spinner2.setVisibility(View.VISIBLE);
//                    }
////                    try {
////                        fetchPaymentLedgerData(companies.get(Filter_selected));
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        arrayAdapterPayments.notifyDataSetChanged();
//        spinner_consolidate.setAdapter(arrayAdapterPayments);
//
//        Log.i("aaaa1111", String.valueOf(consolidate_felter));
//        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if(i == 0){
//                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
//                }
//                else{
//                    if(Filter_selected.equals("Status"))
//                        Filter_selected_value = String.valueOf(i-1);
//                    else
//                        Filter_selected_value = String.valueOf(i);
//                    if(filters.get(i).equals("Select All"))
//                        Filter_selected_value = "-1";
//                    Log.i("Filter_selected_value",Filter_selected_value);
//                    try {
//                       fetchFilteredOrders();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
////        arrayAdapterFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        arrayAdapterFeltter.notifyDataSetChanged();
////        spinner2.setAdapter(arrayAdapterFeltter);
//
//
//        conso_edittext.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//                Log.i("text1", "check");
//                Log.i("text", String.valueOf(s));
//                Filter_selected_value = String.valueOf(s);
//                try {
//                    fetchFilteredOrders();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//        });

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        try {
            fetchCompany();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        mAdapter = new DistributorOrderAdapter(getContext(),"Ghulam Rabani & Sons Traders & Distributors","1002312324251524","89465","Pending");
//        recyclerView.setAdapter(mAdapter);
//        mAdapter = new CompanyFragmentAdapter(getContext(),"Ghulam Rabani & Sons Traders & Distributors", mPager, mycontainer, myinflater);
//        recyclerView.setAdapter(mAdapter);

        Log.i("qqqqqqq", String.valueOf(mPager));


    }

    private void fetchCompany() throws JSONException {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token","");
        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id","");
        Log.i("DistributorId ", DistributorId);
        if(!URL_Company.contains(DistributorId))
            URL_Company = URL_Company + DistributorId;

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.GET, URL_Company, null,new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray result) {
                Log.i("Payments Requests", result.toString());
                Gson gson = new Gson();
                Type type = new TypeToken<List<Company_Fragment_Model>>(){}.getType();
                CompanyList = gson.fromJson(result.toString(),type);
                Log.i("CompanyList", String.valueOf(CompanyList));
                mAdapter = new CompanyFragmentAdapter(getContext(),CompanyList,mPager);
                recyclerView.setAdapter(mAdapter);
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
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        sr.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 1000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                printErrorMessage(error);

            }
        });
        Volley.newRequestQueue(getContext()).add(sr);
    }
//
//
//    private void fetchFilteredOrders() throws JSONException {
//        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Token = sharedPreferences.getString("Login_Token","");
//        Log.i("Token", Token);
//
//        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        DistributorId = sharedPreferences1.getString("Distributor_Id","");
//        Log.i("DistributorId ", DistributorId);
//
//        JSONObject map = new JSONObject();
//        map.put("DistributorId", Integer.parseInt(DistributorId));
//        map.put("TotalRecords", 10);
//        map.put("PageNumber", 0);
////        map.put("Status", -1);
//        map.put("OrderState", -1);
//        map.put(Filter_selected, Filter_selected_value);
//        Log.i("Map", String.valueOf(map));
//
//        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_ORDER, map,new Response.Listener<JSONArray>() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onResponse(JSONArray result) {
//                Log.i("Payments Requests", result.toString());
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<OrderFragmentModel>>(){}.getType();
//                OrderList = gson.fromJson(result.toString(),type);
//
//                mAdapter = new DistributorOrderAdapter(getContext(),OrderList);
//                recyclerView.setAdapter(mAdapter);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        }){
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " +Token);
//                params.put("Content-Type", "application/json; charset=UTF-8");
//                return params;
//            }
//        };
//        Volley.newRequestQueue(getContext()).add(sr);
//    }

    private void printErrorMessage(VolleyError error) {
        if(error.networkResponse != null && error.networkResponse.data != null) {
            try {
                String message = "";
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.i("responseBody", responseBody);
                JSONObject data = new JSONObject(responseBody);
                Log.i("data", String.valueOf(data));
                Iterator<String> keys = data.keys();
                while(keys.hasNext()) {
                    String key = keys.next();
    //                if (data.get(key) instanceof JSONObject) {
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

