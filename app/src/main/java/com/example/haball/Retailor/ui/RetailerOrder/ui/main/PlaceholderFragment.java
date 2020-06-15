package com.example.haball.Retailor.ui.RetailerOrder.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.haball.Distributor.StatusKVP;
import com.example.haball.R;
import com.example.haball.Retailor.ui.Dashboard.Dashboard_Tabs;
import com.example.haball.Retailor.ui.RetailerOrder.RetailerOrdersAdapter.RetailerViewOrderProductAdapter;
import com.example.haball.Retailor.ui.RetailerOrder.RetailerOrdersModel.RetailerViewOrderProductModel;
import com.example.haball.TextField;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private String orderID, InvoiceStatus;
    private String URL_Order_Data = "http://175.107.203.97:4014/api/Orders/";
    private PageViewModel pageViewModel;
    private TextInputLayout layout_txt_orderID, layout_txt_order_company, layout_txt_created_date_order, layout_txt_status_order, layout_txt_comments,
            layout_txt_companName, layout_txt_paymentID, layout_txt_created_date, layout_transaction_date,
            layout_txt_bank, layout_txt_authorization_id, layout_txt_settlement_id, layout_txt_status,
            layout_txt_amount, layout_txt_transaction_charges, layout_txt_total_amount;
    private TextInputEditText txt_orderID, txt_company_order, txt_created_date_order, txt_status_order, txt_comments;
    private TextInputEditText txt_companyName, txt_paymentID, txt_created_date, txt_confirm, txt_bank, txt_authorization_id, txt_settlement_id, txt_status, txt_amount, txt_transaction_charges, txt_total_amount;
    private RecyclerView rv_fragment_retailer_order_details;
    private TextView tv_shipment_no_data;
    private RecyclerView.Adapter rv_productAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<RetailerViewOrderProductModel> invo_productList = new ArrayList<>();
    private String Token;
    private HashMap<String, String> RetailerOrderStatusKVP = new HashMap<>();
    private StatusKVP StatusKVPClass;
    private TextView discount_amount;
    private TextView total_amount, disclaimer_tv;
    private Button button_back;
    private FragmentTransaction fragmentTransaction;

    //    private String DistributorId;
    // private TextInputLayout layout_txt_created_date, layout_transaction_date, layout_txt_bank, layout_txt_authorization_id, layout_txt_settlement_id, layout_txt_status, layout_txt_amount, layout_txt_transaction_charges, layout_txt_total_amount;

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
        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("OrderId",
                Context.MODE_PRIVATE);


        orderID = sharedPreferences3.getString("OrderId", "");
        Log.i("OrderId", orderID);
        if (!URL_Order_Data.contains(orderID)) {
            URL_Order_Data = URL_Order_Data + orderID;
            Log.i("URL_Order_Data", URL_Order_Data);
        }
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        StatusKVPClass = new StatusKVP(getContext(), Token);
        RetailerOrderStatusKVP = StatusKVPClass.getRetailerOrderStatusKVP();
        SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
        editorOrderTabsFromDraft.putString("TabNo", "1");
        editorOrderTabsFromDraft.apply();

        View rootView = null;
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1: {
                rootView = inflater.inflate(R.layout.fragment_retailer_orders_tab, container, false);

                layout_txt_orderID = rootView.findViewById(R.id.layout_txt_orderID);
                layout_txt_order_company = rootView.findViewById(R.id.layout_txt_order_company);
                layout_txt_created_date_order = rootView.findViewById(R.id.layout_txt_created_date_order);
                layout_txt_status_order = rootView.findViewById(R.id.layout_txt_status_order);
                layout_txt_comments = rootView.findViewById(R.id.layout_txt_comments);
                txt_orderID = rootView.findViewById(R.id.txt_orderID);
                txt_company_order = rootView.findViewById(R.id.txt_company_order);
                txt_created_date_order = rootView.findViewById(R.id.txt_created_date_order);
                txt_status_order = rootView.findViewById(R.id.txt_status_order);
                txt_comments = rootView.findViewById(R.id.txt_comments);
                button_back = rootView.findViewById(R.id.button_back);

                new TextField().changeColor(this.getContext(), layout_txt_orderID, txt_orderID);
                new TextField().changeColor(this.getContext(), layout_txt_order_company, txt_company_order);
                new TextField().changeColor(this.getContext(), layout_txt_created_date_order, txt_company_order);
                new TextField().changeColor(this.getContext(), layout_txt_status_order, txt_status_order);
                new TextField().changeColor(this.getContext(), layout_txt_comments, txt_comments);

                txt_orderID.setEnabled(false);
                txt_company_order.setEnabled(false);
                txt_created_date_order.setEnabled(false);
                txt_status_order.setEnabled(false);
                txt_comments.setEnabled(false);

                button_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container_ret, new Dashboard_Tabs());
                        fragmentTransaction.commit();
                    }
                });

                getOrderData();
                break;
            }
            case 2: {
                rootView = inflater.inflate(R.layout.fragment_retailer_orders_details_tab, container, false);
                rv_fragment_retailer_order_details = rootView.findViewById(R.id.rv_fragment_retailer_order_details);
                discount_amount = rootView.findViewById(R.id.discount_amount);
                total_amount = rootView.findViewById(R.id.total_amount);
                rv_fragment_retailer_order_details.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(rootView.getContext());
                rv_fragment_retailer_order_details.setLayoutManager(layoutManager);
                disclaimer_tv = rootView.findViewById(R.id.disclaimer_tv);
                button_back = rootView.findViewById(R.id.button_back);

                SharedPreferences sharedPreferences1 = getContext().getSharedPreferences("OrderId",
                        Context.MODE_PRIVATE);
                InvoiceStatus = sharedPreferences1.getString("InvoiceStatus", "");
                Log.i("InvoiceStatus", InvoiceStatus);

//        SectionsPagerAdapter sectionsPagerAdapter = null;
                if (!InvoiceStatus.equals("null")) {
                    disclaimer_tv.setVisibility(View.GONE);
                }


                button_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container_ret, new Dashboard_Tabs());
                        fragmentTransaction.commit();
                    }
                });

                getOrderDetailsData(rootView);
                break;
            }
            case 3: {

                rootView = inflater.inflate(R.layout.fragment_retailer_payment_tab, container, false);
                layout_txt_companName = rootView.findViewById(R.id.layout_txt_companName);
                layout_txt_paymentID = rootView.findViewById(R.id.layout_txt_paymentID);
                layout_txt_created_date = rootView.findViewById(R.id.layout_txt_created_date);
                layout_transaction_date = rootView.findViewById(R.id.layout_transaction_date);
                layout_txt_bank = rootView.findViewById(R.id.layout_txt_bank);
                layout_txt_status = rootView.findViewById(R.id.layout_txt_status);
                layout_txt_authorization_id = rootView.findViewById(R.id.layout_txt_authorization_id);
                layout_txt_settlement_id = rootView.findViewById(R.id.layout_txt_settlement_id);
                layout_txt_amount = rootView.findViewById(R.id.layout_txt_amount);
                layout_txt_transaction_charges = rootView.findViewById(R.id.layout_txt_transaction_charges);
                layout_txt_total_amount = rootView.findViewById(R.id.layout_txt_total_amount);
                button_back = rootView.findViewById(R.id.button_back);

                txt_companyName = rootView.findViewById(R.id.txt_companyName);
                txt_paymentID = rootView.findViewById(R.id.txt_paymentID);
                txt_created_date = rootView.findViewById(R.id.txt_created_date);
                txt_confirm = rootView.findViewById(R.id.txt_confirm);
                txt_bank = rootView.findViewById(R.id.txt_bank);
                txt_authorization_id = rootView.findViewById(R.id.txt_authorization_id);
                txt_settlement_id = rootView.findViewById(R.id.txt_settlement_id);
                txt_status = rootView.findViewById(R.id.txt_status);
                txt_amount = rootView.findViewById(R.id.txt_amount);
                txt_transaction_charges = rootView.findViewById(R.id.txt_transaction_charges);
                txt_total_amount = rootView.findViewById(R.id.txt_total_amount);


                new TextField().changeColor(getContext(), layout_txt_companName, txt_companyName);
                new TextField().changeColor(getContext(), layout_txt_paymentID, txt_paymentID);
                new TextField().changeColor(getContext(), layout_txt_created_date, txt_created_date);
                new TextField().changeColor(getContext(), layout_transaction_date, txt_confirm);
                new TextField().changeColor(getContext(), layout_txt_bank, txt_bank);
                new TextField().changeColor(getContext(), layout_txt_authorization_id, txt_authorization_id);
                new TextField().changeColor(getContext(), layout_txt_settlement_id, txt_settlement_id);
                new TextField().changeColor(getContext(), layout_txt_status, txt_status);
                new TextField().changeColor(getContext(), layout_txt_amount, txt_amount);
                new TextField().changeColor(getContext(), layout_txt_transaction_charges, txt_transaction_charges);
                new TextField().changeColor(getContext(), layout_txt_total_amount, txt_total_amount);

//                layout_txt_created_date.setVisibility(View.GONE);
//                layout_transaction_date.setVisibility(View.GONE);
//                layout_txt_bank.setVisibility(View.GONE);
//                layout_txt_authorization_id.setVisibility(View.GONE);
//                layout_txt_settlement_id.setVisibility(View.GONE);
//                layout_txt_status.setVisibility(View.GONE);
//                layout_txt_amount.setVisibility(View.GONE);
//                layout_txt_transaction_charges.setVisibility(View.GONE);
//                layout_txt_total_amount.setVisibility(View.GONE);

                txt_companyName.setEnabled(false);
                txt_paymentID.setEnabled(false);
                txt_created_date.setEnabled(false);
                txt_confirm.setEnabled(false);
                txt_bank.setEnabled(false);
                txt_authorization_id.setEnabled(false);
                txt_settlement_id.setEnabled(false);
                txt_status.setEnabled(false);
                txt_amount.setEnabled(false);
                txt_transaction_charges.setEnabled(false);
                txt_total_amount.setEnabled(false);

                button_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container_ret, new Dashboard_Tabs());
                        fragmentTransaction.commit();
                    }
                });

                getPaymentData();
                break;
            }
        }
        return rootView;
    }

    private void getOrderData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

//        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
//        Log.i("DistributorId invoice", DistributorId);
        Log.i("Token invoice", Token);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Order_Data, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.i("Order Data response", String.valueOf(result));
                try {
                    JSONObject response = result.getJSONObject("OrderPaymentDetails");
                    if (!String.valueOf(response.get("OrderNumber")).equals("") && !String.valueOf(response.get("OrderNumber")).equals("null"))
                        txt_orderID.setText(String.valueOf(response.get("OrderNumber")));
                    if (!String.valueOf(response.get("CompanyName")).equals("") && !String.valueOf(response.get("CompanyName")).equals("null"))
                        txt_company_order.setText(String.valueOf(response.get("CompanyName")));
                    if (!String.valueOf(response.get("OrderCreatedDate")).equals("") && !String.valueOf(response.get("OrderCreatedDate")).equals("null"))
                        txt_created_date_order.setText(String.valueOf(response.get("OrderCreatedDate")).split("T")[0]);
                    if (!String.valueOf(response.get("Status")).equals("") && !String.valueOf(response.get("Status")).equals("null"))
                        txt_status_order.setText(RetailerOrderStatusKVP.get(String.valueOf(response.get("Status"))));

                    if (!String.valueOf(response.get("OrderNumber")).equals("") && !String.valueOf(response.get("OrderNumber")).equals("null"))
                        txt_orderID.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("CompanyName")).equals("") && !String.valueOf(response.get("CompanyName")).equals("null"))
                        txt_company_order.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("OrderCreatedDate")).equals("") && !String.valueOf(response.get("OrderCreatedDate")).equals("null"))
                        txt_created_date_order.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("Status")).equals("") && !String.valueOf(response.get("Status")).equals("null"))
                        txt_status_order.setTextColor(getResources().getColor(R.color.textcolor));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        printErrorMessage(error);

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }

    private void getOrderDetailsData(View rootView) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        tv_shipment_no_data = rootView.findViewById(R.id.tv_shipment_no_data);
        tv_shipment_no_data.setVisibility(View.GONE);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Order_Data, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<RetailerViewOrderProductModel>>() {
                }.getType();
                try {
                    double totalPrice = 0;
                    invo_productList = gson.fromJson(response.get("OrderDetails").toString(), type);
                    for (int i = 0; i < invo_productList.size(); i++) {
                        totalPrice += Double.parseDouble(invo_productList.get(i).getTotalPrice());
                    }
                    Log.i("OrderDetails", String.valueOf(response.get("OrderDetails")));
                    RetailerViewOrderProductAdapter productAdapter = new RetailerViewOrderProductAdapter(getContext(), invo_productList);
                    rv_fragment_retailer_order_details.setAdapter(productAdapter);
                    total_amount.setText(String.valueOf(totalPrice));
                    if (invo_productList.size() != 0) {
                        tv_shipment_no_data.setVisibility(View.GONE);
                    } else {
                        tv_shipment_no_data.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        printErrorMessage(error);

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }

    private void getPaymentData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

//        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
//        Log.i("DistributorId invoice", DistributorId);
        Log.i("Token invoice12", Token);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Order_Data, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.i("Order Data response2", String.valueOf(result));
                try {
                    JSONObject response = result.getJSONObject("OrderPaymentDetails");
                    txt_companyName.setText(String.valueOf(response.get("CompanyName")));
                    txt_paymentID.setText(String.valueOf(response.get("InvoiceNumber")));
                    setTextAndShowDate(layout_txt_created_date, txt_created_date, String.valueOf(response.get("InvoiceCreatedDate")).split("T")[0]);
                    setTextAndShow(layout_txt_amount, txt_amount, String.valueOf(response.get("InvoiceTotalAmount")));
                    setTextAndShow(layout_txt_status, txt_status, String.valueOf(response.getString("InvoiceStatus")));
                    setTextAndShow(layout_transaction_date, txt_confirm, String.valueOf(response.getString("TransactionDate")).split("T")[0]);
                    setTextAndShow(layout_txt_bank, txt_bank, String.valueOf(response.getString("BankName")));
                    setTextAndShow(layout_txt_authorization_id, txt_authorization_id, String.valueOf(response.getString("AuthID")));
                    setTextAndShow(layout_txt_settlement_id, txt_settlement_id, String.valueOf(response.getString("SettlementID")));
                    setTextAndShow(layout_txt_total_amount, txt_total_amount, String.valueOf(response.getString("TotalAmount")));
                    setTextAndShow(layout_txt_transaction_charges, txt_transaction_charges, String.valueOf(response.getString("TransactionCharges")));

                    if (!String.valueOf(response.get("CompanyName")).equals("") && !String.valueOf(response.get("CompanyName")).equals("null"))
                        txt_companyName.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("InvoiceNumber")).equals("") && !String.valueOf(response.get("InvoiceNumber")).equals("null"))
                        txt_paymentID.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("InvoiceCreatedDate")).split("T")[0].equals("") && !String.valueOf(response.get("InvoiceCreatedDate")).split("T")[0].equals("null"))
                        txt_created_date.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("TransactionDate")).equals("") && !String.valueOf(response.get("TransactionDate")).equals("null"))
                        txt_confirm.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("BankName")).equals("") && !String.valueOf(response.get("BankName")).equals("null"))
                        txt_bank.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("AuthID")).equals("") && !String.valueOf(response.get("AuthID")).equals("null"))
                        txt_authorization_id.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("SettlementID")).equals("") && !String.valueOf(response.get("SettlementID")).equals("null"))
                        txt_settlement_id.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("InvoiceStatus")).equals("") && !String.valueOf(response.get("InvoiceStatus")).equals("null"))
                        txt_status.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("InvoiceTotalAmount")).equals("") && !String.valueOf(response.get("InvoiceTotalAmount")).equals("null"))
                        txt_amount.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("TransactionCharges")).equals("") && !String.valueOf(response.get("TransactionCharges")).equals("null"))
                        txt_transaction_charges.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("TotalAmount")).equals("") && !String.valueOf(response.get("TotalAmount")).equals("null"))
                        txt_total_amount.setTextColor(getResources().getColor(R.color.textcolor));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        printErrorMessage(error);

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }

    private void setTextAndShow(TextInputLayout layout, TextInputEditText editText, String value) {
        if (!value.equals("null")) {
            layout.setVisibility(View.VISIBLE);
            editText.setText(value);
        }
    }

    private void setTextAndShowDate(TextInputLayout layout, TextInputEditText editText, String value) {
        if (!value.equals("null")) {
            layout.setVisibility(View.VISIBLE);
            editText.setText(value.split("T")[0]);
        }
    }

    private void printErrorMessage(VolleyError error) {
        if (getContext() != null) {
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
                    Log.i("responseBody", responseBody);
                    JSONObject data = new JSONObject(responseBody);
                    Log.i("data", String.valueOf(data));
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
    }
}