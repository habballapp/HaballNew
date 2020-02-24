package com.example.haball.Distributor.ui.shipments.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.example.haball.Distributor.ui.shipments.main.Adapters.SectionsPagerAdapter;
import com.example.haball.Distributor.ui.shipments.main.Models.PageViewModel;
import com.example.haball.R;
import com.example.haball.Shipment.Adapters.ProductDetailsAdapter;
import com.example.haball.Shipment.ui.main.Models.Distributor_InvoiceModel;
import com.example.haball.Shipment.ui.main.Models.Distributor_OrderModel;
import com.example.haball.Shipment.ui.main.Models.Distributor_ProductModel;
import com.example.haball.Shipment.ui.main.Models.Distributor_ShipmentModel;
import com.google.android.material.tabs.TabLayout;
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

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    // invoice data
    private TextView invoice_id, invoice_tv_date, invoice_tv_amount, tv_status;
    private String INVOICE_URL = "http://175.107.203.97:4008/api/deliverynotes/";
    private String Token;
    private String DistributorId;
    private String shipmentID;
    // order data
    private Button btn_next;
    private TextView order_id, order_company_name, order_tr_mode, order_payment_term, order_tv_cdate, order_tv_status, order_tv_shaddress, order_tv_billingAdd;
    //shipmentDetails
    private TextView total_price, shipment_id, shipment_delivery_date, shipment_recieving_date, shipment_tv_quantity, shipment_tv_shstatus;
    //product details
    private RecyclerView product_RecyclerV;
    private RecyclerView.Adapter productDetailsAdapter;
    private List<Distributor_ProductModel> productList = new ArrayList<>();

    private RecyclerView.LayoutManager layoutManager;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;
    private ViewPager viewPager;

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
        Log.i("abbccc", String.valueOf(index));
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = null;

        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {

            case 1: {


                rootView = inflater.inflate(R.layout.distributor_shipment__view_shipment_1_fragment, container, false);
                final View root = inflater.inflate(R.layout.activity_distributor_shipment__view_dashboard, container, false);

                invoice_id = rootView.findViewById(R.id.shipment_invoice_id);
                invoice_tv_date = rootView.findViewById(R.id.invoice_tv_date);
                invoice_tv_amount = rootView.findViewById(R.id.invoice_tv_amount);
                tv_status = rootView.findViewById(R.id.shipment_tv_status);

                InvoiceData();
                break;
            }
            case 2: {
                rootView = inflater.inflate(R.layout.distributor_shipment__view_shipment_2_fragment, container, false);
                order_id = rootView.findViewById(R.id.order_id);
                order_company_name = rootView.findViewById(R.id.order_company_name);
                order_tr_mode = rootView.findViewById(R.id.order_tr_mode);
                order_payment_term = rootView.findViewById(R.id.order_payment_term);
                order_tv_cdate = rootView.findViewById(R.id.order_tv_cdate);
                order_tv_status = rootView.findViewById(R.id.order_tv_status);
                order_tv_shaddress = rootView.findViewById(R.id.order_tv_shaddress);
                order_tv_billingAdd = rootView.findViewById(R.id.order_tv_billingAdd);

                orderData();
                break;
            }

            case 3: {
                rootView = inflater.inflate(R.layout.distributor_shipment__view_shipment_3_fragment, container, false);
                product_RecyclerV = (RecyclerView) rootView.findViewById(R.id.product_rv_shipment);
                ProductData();
                product_RecyclerV.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(rootView.getContext());
                product_RecyclerV.setLayoutManager(layoutManager);
                total_price = rootView.findViewById(R.id.total_price);
                break;
            }

            case 4: {
                rootView = inflater.inflate(R.layout.distributor_shipment__view_shipment_fragment, container, false);
                shipment_id = rootView.findViewById(R.id.shipment_id);
                shipment_delivery_date = rootView.findViewById(R.id.shipment_delivery_date);
                shipment_tv_quantity = rootView.findViewById(R.id.shipment_tv_quantity);
                shipment_tv_shstatus = rootView.findViewById(R.id.shipment_tv_shstatus);
                shipment_recieving_date = rootView.findViewById(R.id.shipment_recieving_date);
                shipmentData();
                break;
            }
        }
        return rootView;

    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void ProductData() {
        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("Shipment_ID",
                Context.MODE_PRIVATE);
        shipmentID = sharedPreferences3.getString("ShipmentID", "");
        Log.i("shipmentID shared pref", shipmentID);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId invoice", DistributorId);
        Log.i("Token invoice", Token);
        if(!INVOICE_URL.contains(shipmentID))
            INVOICE_URL = INVOICE_URL + shipmentID;
        Log.i("INVOICE_URL12", INVOICE_URL);

        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, INVOICE_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    total_price.setText(response.getString("TotalPrice"));
                    Log.i("responesProductmy" ,response.getString("DeliveryNoteDetails").toString());
                    JSONArray jsonArray = new JSONArray(response.getString("DeliveryNoteDetails"));
                    Log.i("responesProduct" ,jsonArray.toString());

                    Gson gson = new Gson();
                        Type type = new TypeToken<List<Distributor_ProductModel>>() {
                        }.getType();
                        productList = gson.fromJson(String.valueOf(jsonArray), type);
                        Log.i("productList" , String.valueOf(productList));

                        productDetailsAdapter = new ProductDetailsAdapter(getContext(), productList);
                        product_RecyclerV.setAdapter(productDetailsAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("Error Product",e.toString());
                    Toast.makeText(getActivity(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();
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
                params.put("Authorization", "bearer " +Token);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(obj);

    }

    private void shipmentData() {
        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("Shipment_ID",
                Context.MODE_PRIVATE);
        shipmentID = sharedPreferences3.getString("ShipmentID", "");
        Log.i("shipmentID shared pref", shipmentID);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId invoice", DistributorId);
        Log.i("Token invoice", Token);
        if(!INVOICE_URL.contains(shipmentID))
            INVOICE_URL = INVOICE_URL + shipmentID;
        Log.i("INVOICE_URL1", INVOICE_URL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, INVOICE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("response", response);
                try {
                    if (response != null && !response.equals("")) {
                        Gson gson = new Gson();
                        Distributor_ShipmentModel shipmentModel = gson.fromJson(response, Distributor_ShipmentModel.class);
                        shipment_id.setText(shipmentModel.getDeliveryNumber());
                        String string = shipmentModel.getDeliveryDate();
                        String[] parts_d = string.split("T");
                        String Date = parts_d[0];
                        shipment_delivery_date.setText(Date);
                        if(shipmentModel.getGoodsreceivenotesReceivingDate() != null) {
                            String stringRecv = shipmentModel.getGoodsreceivenotesReceivingDate();
                            String[] parts_dRecv = stringRecv.split("T");
                            String DateRecv = parts_dRecv[0];
                            shipment_recieving_date.setText(DateRecv);
                        } else {
                            shipment_recieving_date.setText("");
                        }
                        shipment_tv_quantity.setText(shipmentModel.getGoodsreceivenotesReceiveQty());
//                        shipment_tv_shstatus.setText(shipmentModel.getDeliveryNoteStatus());

                        if (shipmentModel.getDeliveryNoteStatus().equals("0")) {
                            shipment_tv_shstatus.setText("Pending");
                        } else if (shipmentModel.getDeliveryNoteStatus().equals("1")) {
                            shipment_tv_shstatus.setText("Delivered");
                        } else if (shipmentModel.getDeliveryNoteStatus().equals("2")) {
                            shipment_tv_shstatus.setText("Received");
                        } else if (shipmentModel.getDeliveryNoteStatus().equals("3")) {
                            shipment_tv_shstatus.setText("Returned");
                        } else if (shipmentModel.getDeliveryNoteStatus().equals("4")) {
                            shipment_tv_shstatus.setText("Revised");
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
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }

    private void orderData() {
        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("Shipment_ID",
                Context.MODE_PRIVATE);
        shipmentID = sharedPreferences3.getString("ShipmentID", "");
        Log.i("shipmentID shared pref", shipmentID);

        Log.i("emthod", "kmkn");

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId invoice", DistributorId);
        Log.i("Token invoice", Token);
        if(!INVOICE_URL.contains(shipmentID))
            INVOICE_URL = INVOICE_URL + shipmentID;
        Log.i("INVOICE_URL", INVOICE_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, INVOICE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("response", response);
                try {
                    if (response != null && !response.equals("")) {
                        Gson gson = new Gson();
                        Distributor_OrderModel orderModel = gson.fromJson(response, Distributor_OrderModel.class);
                        order_id.setText(orderModel.getOrderNumber());
                        order_company_name.setText(orderModel.getDistributorCompanyName());
                        order_tr_mode.setText(orderModel.getTransportTypeDescription());
                        order_payment_term.setText(orderModel.getPaymentTermDescription());

                        String string = orderModel.getCreatedDate();
                        String[] parts = string.split("T");
                        String Date = parts[0];
                        order_tv_cdate.setText(Date);
                        order_tv_status.setText(orderModel.getOrderStatus());

                        if (orderModel.getOrderStatus().equals("0")) {
                            order_tv_status.setText("Pending");
                        } else if (orderModel.getOrderStatus().equals("1")) {
                            order_tv_status.setText("Approved");
                        } else if (orderModel.getOrderStatus().equals("2")) {
                            order_tv_status.setText("Rejected");
                        } else if (orderModel.getOrderStatus().equals("3")) {
                            order_tv_status.setText("Draft");
                        } else if (orderModel.getOrderStatus().equals("4")) {
                            order_tv_status.setText("Cancelled");
                        }
                        order_tv_shaddress.setText(orderModel.getOrdersShippingAddress());
                        order_tv_billingAdd.setText(orderModel.getOrdersBillingAddress());
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
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }


    private void InvoiceData() {

        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("Shipment_ID",
                Context.MODE_PRIVATE);
        shipmentID = sharedPreferences3.getString("ShipmentID", "");
        Log.i("shipmentID shared pref", shipmentID);

        Log.i("emthod", "kmkn");

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId invoice", DistributorId);
        Log.i("Token invoice", Token);
        if(!INVOICE_URL.contains(shipmentID))
            INVOICE_URL = INVOICE_URL + shipmentID;
        Log.i("INVOICE_URL", INVOICE_URL);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, INVOICE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("response", response);
                try {
                    if (response != null && !response.equals("")) {
                        Gson gson = new Gson();
                        Distributor_InvoiceModel invoiceModel = gson.fromJson(response, Distributor_InvoiceModel.class);
                        invoice_id.setText(invoiceModel.getDeliveryNumber());
                        String string = invoiceModel.getCreatedDate();
                        String[] parts = string.split("T");
                        String Date = parts[0];
                        invoice_tv_date.setText(Date);
                        invoice_tv_amount.setText(invoiceModel.getNetPrice());
//                        tv_status.setText(invoiceModel.getStatus());
                        if (invoiceModel.getStatus().equals("0")) {
                            tv_status.setText("Pending");
                        } else if (invoiceModel.getStatus().equals("1")) {
                            tv_status.setText("Unpaid");
                        } else if (invoiceModel.getStatus().equals("2")) {
                            tv_status.setText("Partially Paid");
                        } else if (invoiceModel.getStatus().equals("3")) {
                            tv_status.setText("Paid");
                        } else if (invoiceModel.getStatus().equals("4")) {
                            tv_status.setText("Payment Processing");
                        }


//                        if (invoiceModel.getStatus().equals("1")) {
//                            tv_status.setText("Delivered");
//                        } else if (invoiceModel.getStatus().equals("2")) {
//                            tv_status.setText("Received");
//                        } else if (invoiceModel.getStatus().equals("3")) {
//                            tv_status.setText("Returned");
//                        } else if (invoiceModel.getStatus().equals("4")) {
//                            tv_status.setText("Revised");
//                        }
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
        Volley.newRequestQueue(getContext()).add(stringRequest);

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