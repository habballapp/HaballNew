package com.example.haball.Distributor.ui.retailer.RetailerOrder;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.ui.orders.Adapter.CompanyFragmentAdapter;
import com.example.haball.Distributor.ui.orders.Models.Company_Fragment_Model;
import com.example.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.example.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersAdapter.RetailerOrdersAdapter;
import com.example.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersModel.RetailerOrdersModel;
import com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.RetailerPlaceOrder;
import com.example.haball.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class RetailerOrderDashboard extends Fragment implements DatePickerDialog.OnDateSetListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button btn_place_order;
    private String URL_ORDER = "http://175.107.203.97:4013/api/retailerorder/search";
    private String Token, DistributorId;
    private TextView tv_shipment_no_data;
    private String URL_FETCH_ORDERS = "http://175.107.203.97:4013/api/retailerorder/search";
    private List<RetailerOrdersModel> OrdersList;
    private Spinner spinner_order_ret;
    private RelativeLayout spinner_container1;
    private Spinner spinner_consolidate;
    private Spinner spinner2;
    private EditText conso_edittext;
    private List<String> consolidate_felter;
    private List<String> filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPayments;
    private ArrayAdapter<String> arrayAdapterFeltter;
    private Button consolidate;
    private String Filter_selected, Filter_selected1, Filter_selected2, Filter_selected_value;
    private int pageNumber = 0;
    private double totalPages = 0;
    private double totalEntries = 0;
    private TextInputLayout search_bar;
    public String dateType = "";
    private int year1, year2, month1, month2, date1, date2;


    private ImageButton first_date_btn, second_date_btn;
    private LinearLayout date_filter_rl, amount_filter_rl;
    private TextView first_date, second_date;
    private EditText et_amount1, et_amount2;
    private int pageNumberOrder = 0;
    private double totalPagesOrder = 0;
    private double totalEntriesOrder = 0;
    private String fromDate, toDate;
    private FragmentTransaction fragmentTransaction;

    public RetailerOrderDashboard() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_retailer_order_dashboard, container, false);
        recyclerView = root.findViewById(R.id.rv_retailer_order_dashboard);
        btn_place_order = root.findViewById(R.id.btn_place_order);
        search_bar = root.findViewById(R.id.search_bar);
        consolidate = root.findViewById(R.id.consolidate);
        tv_shipment_no_data = root.findViewById(R.id.tv_shipment_no_data);
        tv_shipment_no_data.setVisibility(View.GONE);

        // DATE FILTERS ......
        date_filter_rl = root.findViewById(R.id.date_filter_rl);
        first_date = root.findViewById(R.id.first_date);
        first_date_btn = root.findViewById(R.id.first_date_btn);
        second_date = root.findViewById(R.id.second_date);
        second_date_btn = root.findViewById(R.id.second_date_btn);

        // AMOUNT FILTERS ......
        amount_filter_rl = root.findViewById(R.id.amount_filter_rl);
        et_amount1 = root.findViewById(R.id.et_amount1);
        et_amount2 = root.findViewById(R.id.et_amount2);

        spinner_container1 = root.findViewById(R.id.spinner_container1);
        spinner_consolidate = (Spinner) root.findViewById(R.id.spinner_conso);
        spinner2 = (Spinner) root.findViewById(R.id.conso_spinner2);
        conso_edittext = (EditText) root.findViewById(R.id.conso_edittext);
        spinner_container1.setVisibility(View.GONE);
        conso_edittext.setVisibility(View.GONE);
        consolidate_felter = new ArrayList<>();
        consolidate_felter = new ArrayList<>();
        consolidate_felter.add("Select Criteria");
        consolidate_felter.add("Order ID");
        consolidate_felter.add("Retailer");
        consolidate_felter.add("Amount");
        consolidate_felter.add("Created Date");
        consolidate_felter.add("Submitter");
        consolidate_felter.add("Status");

        arrayAdapterPayments = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, consolidate_felter);

        spinner_consolidate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(), consolidate_felter.get(i), Toast.LENGTH_LONG).show();
                spinner_container1.setVisibility(View.GONE);
                conso_edittext.setVisibility(View.GONE);
                date_filter_rl.setVisibility(View.GONE);
                amount_filter_rl.setVisibility(View.GONE);

                if (i == 0) {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    Filter_selected = consolidate_felter.get(i);

                    if (!Filter_selected.equals("Status"))
                        spinner2.setSelection(0);
                    if (!conso_edittext.getText().equals(""))
                        conso_edittext.setText("");

                    recyclerView = root.findViewById(R.id.rv_retailer_order_dashboard);
                    recyclerView.setHasFixedSize(true);


                    if (Filter_selected.equals("Order ID")) {
                        search_bar.setHint("Search by " + Filter_selected);
                        Filter_selected = "OrderNumber";
                        conso_edittext.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Retailer")) {
                        search_bar.setHint("Search by " + Filter_selected);
                        Filter_selected = "Retailer";
                        conso_edittext.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Created Date")) {
                        date_filter_rl.setVisibility(View.VISIBLE);
                        Filter_selected = "date";
                        Filter_selected1 = "DateFrom";
                        Filter_selected2 = "DateTo";
                        first_date_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                openCalenderPopup("first date");
                            }
                        });
                        second_date_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                openCalenderPopup("second date");
                            }
                        });
                    } else if (Filter_selected.equals("Amount")) {
                        amount_filter_rl.setVisibility(View.VISIBLE);
                        Filter_selected = "amount";
                        Filter_selected1 = "AmountMin";
                        Filter_selected2 = "AmountMax";
                    } else if (Filter_selected.equals("Submitter")) {
                        search_bar.setHint("Search by " + Filter_selected);
                        Filter_selected = "Submitter";
                        conso_edittext.setVisibility(View.VISIBLE);

                    } else if (Filter_selected.equals("Status")) {
                        Filter_selected = "OrderStatus";
                        spinner_container1.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_consolidate.setAdapter(arrayAdapterPayments);

        filters.add("Status");
        filters.add("Pending");
        filters.add("Approved");
        filters.add("Rejected");
        filters.add("Draft");
        filters.add("Cancelled");
        arrayAdapterFeltter = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, filters);
        Log.i("aaaa1111", String.valueOf(consolidate_felter));
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                } else {
                    Filter_selected_value = String.valueOf(i - 1);
                    Log.i("Filter_selected_value", Filter_selected_value);
                    if (!Filter_selected_value.equals("")) {
                        try {
                            fetchRetailerOrdersData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        arrayAdapterFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapterFeltter);

        conso_edittext.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                Log.i("text2", String.valueOf(Filter_selected));
                Log.i("text1", "check");
                Log.i("text", String.valueOf(s));
                Filter_selected_value = String.valueOf(s);
                if (!Filter_selected_value.equals("")) {
                    try {
                        fetchFilteredOrderData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//                else {
//                    if(!Filter_selected.equals(""))
//                        Filter_selected = "";
//                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new RetailerPlaceOrder());
                fragmentTransaction.commit();

            }
        });

        try {
            fetchRetailerOrdersData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return root;

    }

    private void openCalenderPopup(String date_type) {
        dateType = date_type;
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(getContext(), R.style.DialogTheme, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }


    private void fetchRetailerOrdersData() throws JSONException {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DistributorId", DistributorId);
        jsonObject.put("TotalRecords", 10);
        jsonObject.put("PageNumber", 0);

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_FETCH_ORDERS, jsonObject, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject result) {
                try {
                    Log.i("ORDERS DATA - ", result.toString());
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<RetailerOrdersModel>>() {
                    }.getType();
                    OrdersList = gson.fromJson(result.get("Data").toString(), type);
                    if(OrdersList.size()!=0){
                        tv_shipment_no_data.setVisibility(View.GONE);
                        Log.i("OrdersList", String.valueOf(OrdersList));
                        mAdapter = new RetailerOrdersAdapter(getContext(), OrdersList);
                        recyclerView.setAdapter(mAdapter);


                    }
                    else{
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
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void fetchFilteredOrderData() throws JSONException {
        Log.i("map", "in function");
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DistributorId", DistributorId);
        jsonObject.put("TotalRecords", 10);
        jsonObject.put("PageNumber", 0);
        jsonObject.put(Filter_selected, Filter_selected_value);
        Log.i("map", String.valueOf(jsonObject));
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_FETCH_ORDERS, jsonObject, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject result) {
                    try {
                        Log.i("ORDERS DATA - ", result.toString());
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<RetailerOrdersModel>>() {
                        }.getType();
                        OrdersList = gson.fromJson(result.get("Data").toString(), type);
                            tv_shipment_no_data.setVisibility(View.GONE);
                            Log.i("OrdersList", String.valueOf(OrdersList));
                            mAdapter = new RetailerOrdersAdapter(getContext(), OrdersList);
                            recyclerView.setAdapter(mAdapter);
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
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        if (dateType.equals("first date")) {
            year1 = i;
            month1 = i1;
            date1 = i2;
            updateDisplay(dateType);
        } else if (dateType.equals("second date")) {
            year2 = i;
            month2 = i1;
            date2 = i2;
            updateDisplay(dateType);
        }
    }

    private void updateDisplay(String date_type) {
        if (date_type.equals("first date")) {
            fromDate = year1 + "-" + String.format("%02d", (month1 + 1)) + "-" + String.format("%02d", date1) + "T00:00:00.000Z";
            Log.i("fromDate", fromDate);

            first_date.setText(new StringBuilder()
                    .append(date1).append("/").append(month1 + 1).append("/").append(year1).append(" "));
        } else if (date_type.equals("second date")) {
            toDate = year2 + "-" + String.format("%02d", (month2 + 1)) + "-" + String.format("%02d", date2) + "T00:00:00.000Z";
            second_date.setText(new StringBuilder()
                    .append(date2).append("/").append(month2 + 1).append("/").append(year2).append(" "));
        }
//        try {
//            fetchRetailerOrdersData();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
}
