package com.example.haball.Retailor.ui.Dashboard.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.DistributorOrdersAdapter;
import com.example.haball.Distributor.DistributorOrdersModel;
import com.example.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.example.haball.Distributor.ui.payments.PaymentsViewModel;
import com.example.haball.Payment.DistributorPaymentRequestModel;
import com.example.haball.R;
import com.example.haball.Retailor.ui.Dashboard.RetailerPaymentAdapter;
import com.example.haball.Retailor.ui.Dashboard.RetailerPaymentModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private PaymentsViewModel paymentsViewModel;
    private RecyclerView.Adapter OrdersAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String Token,DistributorId;;
    private String URL = "http://175.107.203.97:4014/api/prepaidrequests/search";
    private String URL_DISTRIBUTOR_ORDERS = "http://175.107.203.97:4014/api/Orders/Search";
//    private String URL_DISTRIBUTOR_PAYMENTS_COUNT = "http://175.107.203.97:4013/api/prepaidrequests/searchCount";
//    private String URL_DISTRIBUTOR_ORDERS_COUNT = "http://175.107.203.97:4013/api/orders/searchCount";
    private TextView tv_shipment_no_data, tv_shipment_no_data1;
    private List<RetailerPaymentModel> PaymentsList = new ArrayList<>();
    //spiner1
    private Spinner payment_retailer_spiner1;
    private List<String> payment = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPayments_Ret;
    private String Filter_selected, Filter_selected_value;
    //spinner2
    private Spinner payment_retailer_spiner2;
    private List<String> payment_filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter_PaymentFeltter;
    private EditText edt_payment_ret;

    private TextView value_unpaid_amount, value_paid_amount;
    private List<DistributorPaymentRequestModel> PaymentsRequestList = new ArrayList<>();
    private List<DistributorOrdersModel> OrdersList = new ArrayList<>();
   // private String Token, DistributorId;

    private PageViewModel pageViewModel;
    private RelativeLayout spinner_container1;
    private Spinner spinner_consolidate;
    private Spinner spinner2;
    private EditText conso_edittext;
    private List<String> consolidate_felter;
    private List<String> filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPayments;
    private ArrayAdapter<String> arrayAdapterFeltter;
    private Button consolidate;
    private String  Filter_selected1, Filter_selected2;

    private TextInputLayout search_bar;
    private Button btn_load_more;
    private int pageNumber = 0;
    private double totalPages = 0;
    private double totalEntries = 0;

    private String dateType = "";
    private int year1, year2, month1, month2, date1, date2;

    private ImageButton first_date_btn, second_date_btn;
    private LinearLayout date_filter_rl, amount_filter_rl;
    private TextView first_date, second_date;
    private EditText et_amount1, et_amount2;

    private int pageNumberOrder = 0;
    private double totalPagesOrder = 0;
    private double totalEntriesOrder = 0;
    private String fromDate, toDate, fromAmount, toAmount;
    private FragmentTransaction fragmentTransaction;
    private String tabName;
    private RelativeLayout rv_filter,spinner_container_main;
    //    private ScrollView scroll_view_main;
//    private ObservableScrollView scroll_view_main;
    private static int y;
    private List<String> scrollEvent = new ArrayList<>();
    private RelativeLayout line_bottom;



    private Button create_payment;

    private static final String ARG_SECTION_NUMBER = "section_number";



    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt( ARG_SECTION_NUMBER, index );
        fragment.setArguments( bundle );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        pageViewModel = ViewModelProviders.of( this ).get( PageViewModel.class );
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt( ARG_SECTION_NUMBER );
        }
        pageViewModel.setIndex( index );
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
       // View root = inflater.inflate( R.layout.activity_dashboard__tabs, container, false );
        View root = null;

        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {


            case 1: {

                root = inflater.inflate( R.layout.fragment_dashboard_retailor, container, false );
                try {
                    paymentFragmentTask(root);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            }

            case 2: {
//                root = inflater.inflate( R.layout.fragment_orders, container, false );
//                break;
                root = inflater.inflate(R.layout.fragment_orders, container, false);
                orderFragmentTask(root);

                break;

            }
        }

        return root;
    }

    private void fetchOrderData() {
    }

    private String getScrollEvent() {

        String scroll = "";

        if (scrollEvent.size() > 0) {
            if (scrollEvent.size() > 15)
                scrollEvent = new ArrayList<>();
            if (Collections.frequency(scrollEvent, "ScrollUp") > Collections.frequency(scrollEvent, "ScrollDown")) {
                if (Collections.frequency(scrollEvent, "ScrollDown") > 0) {
                    if (Collections.frequency(scrollEvent, "ScrollUp") > 3)
                        scroll = "ScrollUp";
                } else {
                    scroll = "ScrollUp";
                }
            } else if (Collections.frequency(scrollEvent, "ScrollUp") < Collections.frequency(scrollEvent, "ScrollDown")) {
                if (Collections.frequency(scrollEvent, "ScrollUp") > 0) {
                    if (Collections.frequency(scrollEvent, "ScrollDown") > 3)
                        scroll = "ScrollDown";
                } else {
                    scroll = "ScrollDown";
                }
            }
        }
        return scroll;
    }

    private void paymentFragmentTask(View root) throws JSONException {

        recyclerView = (RecyclerView) root.findViewById(R.id.rv_payment_request);
        create_payment = root.findViewById(R.id.create_payment);
        payment_retailer_spiner1 = root.findViewById(R.id.spinner_dashboard_retailor);
        edt_payment_ret = root.findViewById(R.id.edt_payment_ret);
        payment_retailer_spiner2 = root.findViewById(R.id.payment_retailer_spiner);

        payment_retailer_spiner2.setVisibility(View.GONE);
        edt_payment_ret.setVisibility(View.GONE);
        payment.add ("Select Criteria");
        payment.add ("Company");
        payment.add ("Payment Id");
        payment.add ("Amount");
        payment.add ("Status");

        arrayAdapterPayments_Ret = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, payment);
        payment_retailer_spiner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                else{
                    Filter_selected = payment.get(i);

                    if(!Filter_selected.equals("Status"))
                        payment_retailer_spiner2.setSelection(0);
                    if(!edt_payment_ret.getText().equals(""))
                        edt_payment_ret.setText("");

                    if(Filter_selected.equals("Invoice No")) {
                        Filter_selected = "ConsolidatedInvoiceNumber";
                        payment_retailer_spiner2.setVisibility(View.GONE);
                        edt_payment_ret.setVisibility(View.VISIBLE);
                    } else if(Filter_selected.equals("Company")) {
                        Filter_selected = "CompanyName";
                        payment_retailer_spiner2.setVisibility(View.GONE);
                        edt_payment_ret.setVisibility(View.VISIBLE);
                    } else if(Filter_selected.equals("Created Date")) {
                        payment_retailer_spiner2.setVisibility(View.GONE);
                        edt_payment_ret.setVisibility(View.GONE);
                        Toast.makeText(getContext(),"Created Date selected",Toast.LENGTH_LONG).show();
                    } else if(Filter_selected.equals("Total Price")) {
                        payment_retailer_spiner2.setVisibility(View.GONE);
                        edt_payment_ret.setVisibility(View.GONE);
                        Toast.makeText(getContext(),"Total Price selected",Toast.LENGTH_LONG).show();
                    } else if(Filter_selected.equals("Paid Amount")) {
                        payment_retailer_spiner2.setVisibility(View.GONE);
                        edt_payment_ret.setVisibility(View.GONE);
                        Toast.makeText(getContext(),"Paid Amount selected",Toast.LENGTH_LONG).show();
                    } else if(Filter_selected.equals("Status")) {
                        Filter_selected = "Status";
                        payment_retailer_spiner2.setVisibility(View.VISIBLE);
                        edt_payment_ret.setVisibility(View.GONE);
                    } else if(Filter_selected.equals("Created By")) {
                        Filter_selected = "CreatedBy";
                        payment_retailer_spiner2.setVisibility(View.GONE);
                        edt_payment_ret.setVisibility(View.VISIBLE);
                    } else {
                        payment_retailer_spiner2.setVisibility(View.GONE);
                        edt_payment_ret.setVisibility(View.GONE);
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

        arrayAdapterPayments_Ret.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPayments_Ret.notifyDataSetChanged();
        payment_retailer_spiner1.setAdapter(arrayAdapterPayments_Ret);

        //filter payment
        payment_filters.add ("Status");
        payment_filters.add ("Paid");
        payment_filters.add ("Unpaid ");
        arrayAdapter_PaymentFeltter = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, payment_filters);

        payment_retailer_spiner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                else{
                    Filter_selected_value = String.valueOf(i-1);
                    Log.i("Filter_selected_value",Filter_selected_value);
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

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });



        //recyclerview
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

            fetchPaymentsData();




    }

    private void fetchPaymentsData() throws JSONException {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token","");
        Log.i("Token", Token);
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("CompanyName", null);
//        jsonObject.put("CreateDateFrom", null);
//        jsonObject.put("CreateDateTo", null);
//        jsonObject.put("Status", null);
//        jsonObject.put("AmountMin", null);
//        jsonObject.put("AmountMax", null);

            jsonObject.put("TotalRecords", 10);


            jsonObject.put("PageNumber", 0);


        JsonObjectRequest sr = new JsonObjectRequest( Request.Method.POST, URL,jsonObject, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject result) {
                try {
                    System.out.println("RESPONSE PAYMENTS"+result.getJSONArray("PrePaidRequestData"));
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<RetailerPaymentModel>>(){}.getType();
                    PaymentsList = gson.fromJson(result.getJSONArray("PrePaidRequestData").toString(),type);

                    mAdapter = new RetailerPaymentAdapter(getContext(),PaymentsList);
                    recyclerView.setAdapter(mAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // printErrorMessage(error);
                error.printStackTrace();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " +Token);
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
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL,map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.i("retailerPayment", result.toString());

                Gson gson = new Gson();
                Type type = new TypeToken<List<RetailerPaymentModel>>(){}.getType();
                try {
                    PaymentsList = gson.fromJson(result.getJSONArray("PrePaidRequestData").toString(),type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mAdapter = new RetailerPaymentAdapter(getContext(),PaymentsList);
                recyclerView.setAdapter(mAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //printErrorMessage(error);
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

    private void printErrorMessage(VolleyError error) {
        if (error instanceof NetworkError) {
            Toast.makeText(getContext(), "Network Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(getContext(), "Server Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(getContext(), "Auth Failure Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(getContext(), "Parse Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(getContext(), "No Connection Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(getContext(), "Timeout Error !", Toast.LENGTH_LONG).show();
        }

        if (error.networkResponse != null && error.networkResponse.data != null) {
            try {
                String message = "";
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.i("responseBody",responseBody);
                JSONObject data = new JSONObject(responseBody);
                Log.i("data",String.valueOf(data));
                Iterator<String> keys = data.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    message = message + data.get(key) + "\n";
                }
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void performPaginationOrder() throws JSONException {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        DistributorId = sharedPreferences.getString("Distributor_Id", "");
//        Log.i("Token", Token);
        JSONObject map = new JSONObject();
        map.put("Status", -1);
        map.put("OrderState", -1);
        map.put("DistributorId", DistributorId);
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumberOrder);

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_DISTRIBUTOR_ORDERS, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                btn_load_more.setVisibility(View.GONE);
                Gson gson = new Gson();
                Type type = new TypeToken<List<DistributorOrdersModel>>() {
                }.getType();
                OrdersList = gson.fromJson(result.toString(), type);
                ((DistributorOrdersAdapter) recyclerView.getAdapter()).addListItem(OrdersList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printErrorMessage(error);

                error.printStackTrace();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);

    }

    private void orderFragmentTask(View root) {
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_fragment_orders);
        spinner_container_main = root.findViewById(R.id.spinner_container_main);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

        btn_load_more = root.findViewById(R.id.btn_load_more);
        tv_shipment_no_data = root.findViewById(R.id.tv_shipment_no_data);

        SpannableString content = new SpannableString("Load More");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        btn_load_more.setText(content);

        btn_load_more.setVisibility(View.GONE);

        btn_load_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageNumberOrder++;
                try {
                    performPaginationOrder();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                scrollEvent = new ArrayList<>();

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                y = dy;
                if (dy <= -5) {
                    scrollEvent.add("ScrollDown");
//                            Log.i("scrolling", "Scroll Down");
                } else if (dy > 5) {
                    scrollEvent.add("ScrollUp");
//                            Log.i("scrolling", "Scroll Up");
                }
                String scroll = getScrollEvent();

                if (scroll.equals("ScrollDown")) {
                    if (spinner_container_main.getVisibility() == View.GONE) {

                        spinner_container_main.setVisibility(View.VISIBLE);
                        TranslateAnimation animate1 = new TranslateAnimation(
                                0,                 // fromXDelta
                                0,                 // toXDelta
                                -spinner_container_main.getHeight(),  // fromYDelta
                                0);                // toYDelta
                        animate1.setDuration(250);
                        animate1.setFillAfter(true);
                        spinner_container_main.clearAnimation();
                        spinner_container_main.startAnimation(animate1);
                    }
                } else if (scroll.equals("ScrollUp")) {
                    y = 0;
                    if (spinner_container_main.getVisibility() == View.VISIBLE) {
//                                line_bottom.setVisibility(View.INVISIBLE);
                        TranslateAnimation animate = new TranslateAnimation(
                                0,                 // fromXDelta
                                0,                 // toXDelta
                                0,  // fromYDelta
                                -spinner_container_main.getHeight()); // toYDelta
                        animate.setDuration(100);
                        animate.setFillAfter(true);
                        spinner_container_main.clearAnimation();
                        spinner_container_main.startAnimation(animate);
                        spinner_container_main.setVisibility(View.GONE);
                    }
                }

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    if (totalPages != 0 && pageNumber < totalPages) {
//                                Toast.makeText(getContext(), pageNumber + " - " + totalPages, Toast.LENGTH_LONG).show();
                        btn_load_more.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        fetchOrderData();
    }

    private void orderData() throws JSONException {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        DistributorId = sharedPreferences.getString("Distributor_Id", "");

        JSONObject map = new JSONObject();
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumberOrder);

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_DISTRIBUTOR_ORDERS, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                //                    JSONArray jsonArray = new JSONArray(result);

                Gson gson = new Gson();
                Type type = new TypeToken<List<DistributorOrdersModel>>() {
                }.getType();
                try {
                    totalEntriesOrder = Double.parseDouble(String.valueOf(result.getJSONObject(1).get("RecordCount")));
                    totalPagesOrder = Math.ceil(totalEntriesOrder / 10);
                    OrdersList = gson.fromJson(result.get(0).toString(), type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("OrdersList", String.valueOf(OrdersList));
                OrdersAdapter = new DistributorOrdersAdapter(getContext(), OrdersList);
                recyclerView.setAdapter(OrdersAdapter);
                if (OrdersList.size() != 0) {
                    tv_shipment_no_data.setVisibility(View.GONE);
                } else {
                    tv_shipment_no_data.setVisibility(View.VISIBLE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printErrorMessage(error);

                error.printStackTrace();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
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