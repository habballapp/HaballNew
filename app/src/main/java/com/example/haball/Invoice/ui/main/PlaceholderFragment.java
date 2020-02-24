package com.example.haball.Invoice.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Invoice.Adapters.ProductAdapter;
import com.example.haball.Invoice.Models.DealerDetails_Model;
import com.example.haball.Invoice.Models.InvoiceDetails_Model;
import com.example.haball.Invoice.Models.OrdersDetails_Model;
import com.example.haball.Invoice.Models.ProductDetails_Model;
import com.example.haball.Invoice.Models.ShipmentDetails_Model;
import com.example.haball.R;
import com.example.haball.Shipment.ui.main.Models.Distributor_InvoiceModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    //Dealer Details
    private TextView dealer_Code ,dealer_first_name ,dealer_last_name,dealer_email,dealer_mobile_no ,dealer_landline,dealer_NTN,dealer_company_name,dealer_created_date;
    //Order Details
    private  TextView total_price, invoice_order_id ,invoice_company_name , invoice_tr_mode, invoice_payment_term ,invoice_Order_cdate, invoice_Order_status ,Order_shipaddress ,Order_billingAddress;
    //Product Details
    private RecyclerView rv_invo_product;
    private RecyclerView.Adapter rv_productAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ProductDetails_Model> invo_productList = new ArrayList<>();
     // Shipment Details
    private  TextView invoice_shipment_id,invoice_shpDelivery_date,invoice_shpRecieving_date ,invoice_shpstatus;

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
                dealer_Code = rootView.findViewById(R.id.dealer_Code);
                dealer_first_name = rootView.findViewById(R.id.dealer_first_name);
                dealer_last_name = rootView.findViewById(R.id.dealer_last_name);
                dealer_email = rootView.findViewById(R.id.dealer_email);
                dealer_mobile_no = rootView.findViewById(R.id.dealer_mobile_no);
                dealer_landline = rootView.findViewById(R.id.dealer_landline);
                dealer_NTN = rootView.findViewById(R.id.dealer_NTN);
                dealer_company_name = rootView.findViewById(R.id.dealer_company_name);
                dealer_created_date = rootView.findViewById(R.id.dealer_created_date);
                DealerDetailsData();
                break;
            }

            case 3: {
                rootView = inflater.inflate(R.layout.fragment_orders_details, container, false);
                invoice_order_id = rootView.findViewById(R.id.invoice_order_id);
                invoice_company_name = rootView.findViewById(R.id.invoice_company_name);
                invoice_tr_mode = rootView.findViewById(R.id.invoice_tr_mode);
                invoice_payment_term = rootView.findViewById(R.id.invoice_payment_term);
                invoice_Order_cdate = rootView.findViewById(R.id.invoice_Order_cdate);
                invoice_Order_status = rootView.findViewById(R.id.invoice_Order_status);
                Order_shipaddress = rootView.findViewById(R.id.Order_shipaddress);
                Order_billingAddress = rootView.findViewById(R.id.Order_billingAddress);
                OrderDetailsData();
                break;
            }

            case 4: {
                rootView = inflater.inflate(R.layout.fragment_product_details, container, false);
                rv_invo_product = rootView.findViewById(R.id.rv_invo_product);
                total_price = rootView.findViewById(R.id.total_price);
                ProductDetailsData();
                rv_invo_product.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(rootView.getContext());
                rv_invo_product.setLayoutManager(layoutManager);


                break;
            }
            case 5: {
                rootView = inflater.inflate(R.layout.fragment_shipment_details, container, false);
//                invoice_shipment_id = rootView.findViewById(R.id.invoice_shipment_id);
//                invoice_shpDelivery_date = rootView.findViewById(R.id.invoice_shpDelivery_date);
//                invoice_shpRecieving_date = rootView.findViewById(R.id.invoice_shpRecieving_date);
//                invoice_shpstatus = rootView.findViewById(R.id.invoice_shpstatus);
               // ShipmentDetailsData();
                break;
            }
        }
        return rootView;

    }

    private void ProductDetailsData() {
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

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, INVOICE_URL,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response", String.valueOf(response));
                try {
                    total_price.setText(response.get("InvoiceTotal").toString());
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<ProductDetails_Model>>(){}.getType();
                    invo_productList = gson.fromJson(response.get("InvoiceDetails").toString(),type);
                    Log.i("ProductList", String.valueOf(response.get("InvoiceDetails")));
                    ProductAdapter productAdapter = new ProductAdapter(getContext(), invo_productList);
                    rv_invo_product.setAdapter(productAdapter);


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

//    private void ShipmentDetailsData() {
//        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("Invoice_ID",
//                Context.MODE_PRIVATE);
//        paymentID = sharedPreferences3.getString("InvoiceID", "");
//        Log.i("payment ID", paymentID);
//
//        Log.i("emthod", "kmkn");
//
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Token = sharedPreferences.getString("Login_Token", "");
//
//        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
//        Log.i("DistributorId invoice", DistributorId);
//        Log.i("Token invoice", Token);
//        if (!INVOICE_URL.contains(paymentID))
//            INVOICE_URL = INVOICE_URL + paymentID;
//        Log.i("INVOICE_URL", INVOICE_URL);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, INVOICE_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.i("response", response);
//                try {
//                    if (response != null && !response.equals("")) {
//                        Gson gson = new Gson();
//                        ShipmentDetails_Model shipmentDetails_model = gson.fromJson(response, ShipmentDetails_Model.class);
//                        invoice_shipment_id.setText(shipmentDetails_model.g());
//                        invoice_company_name.setText(shipmentDetails_model.getDistributorCompanyName());
//                        invoice_tr_mode.setText(shipmentDetails_model.getTransportTypeDescription());
//                        invoice_payment_term.setText(shipmentDetails_model.getPaymentTermDescription());
//                        String string = ordersDetails_model.getOrderDate();
//                        String[] parts = string.split("T");
//                        String Date = parts[0];
//                        invoice_Order_cdate.setText(Date);
//                        if (ordersDetails_model.getOrderStatus().equals("1")) {
//                            invoice_Order_status.setText("Delivered");
//                        } else if (ordersDetails_model.getOrderStatus().equals("2")) {
//                            invoice_Order_status.setText("Received");
//                        } else if (ordersDetails_model.getOrderStatus().equals("3")) {
//                            invoice_Order_status.setText("Returned");
//                        } else if (ordersDetails_model.getOrderStatus().equals("4")) {
//                            invoice_Order_status.setText("Revised");
//                        }
//                        Order_shipaddress.setText(ordersDetails_model.getOrdersShippingAddress());
//                        Order_billingAddress.setText(ordersDetails_model.getOrdersBillingAddress());
//
//                    }
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(getActivity(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + Token);
//                return params;
//            }
//        };
//        Volley.newRequestQueue(getContext()).add(stringRequest);
//
//    }

    private void OrderDetailsData() {
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
                        OrdersDetails_Model ordersDetails_model = gson.fromJson(response, OrdersDetails_Model.class);
                        invoice_order_id.setText(ordersDetails_model.getOrderNumber());
                        invoice_company_name.setText(ordersDetails_model.getDistributorCompanyName());
                        invoice_tr_mode.setText(ordersDetails_model.getTransportTypeDescription());
                        invoice_payment_term.setText(ordersDetails_model.getPaymentTermDescription());
                        String string = ordersDetails_model.getOrderDate();
                        String[] parts = string.split("T");
                        String Date = parts[0];
                        invoice_Order_cdate.setText(Date);
                        if (ordersDetails_model.getOrderStatus().equals("1")) {
                            invoice_Order_status.setText("Delivered");
                        } else if (ordersDetails_model.getOrderStatus().equals("2")) {
                            invoice_Order_status.setText("Received");
                        } else if (ordersDetails_model.getOrderStatus().equals("3")) {
                            invoice_Order_status.setText("Returned");
                        } else if (ordersDetails_model.getOrderStatus().equals("4")) {
                            invoice_Order_status.setText("Revised");
                        }
                        Order_shipaddress.setText(ordersDetails_model.getOrdersShippingAddress());
                        Order_billingAddress.setText(ordersDetails_model.getOrdersBillingAddress());

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

    private void DealerDetailsData() {
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
                        DealerDetails_Model dealerDetails = gson.fromJson(response, DealerDetails_Model.class);
                        dealer_Code.setText(dealerDetails.getDistributorDealerCode());
                        dealer_first_name.setText(dealerDetails.getDistributorFirstName());
                        dealer_last_name.setText(dealerDetails.getDistributorLastName());
                        dealer_email.setText(dealerDetails.getDistributorEmail());
                        dealer_mobile_no.setText(dealerDetails.getDistributorMobile());
                        dealer_landline.setText(dealerDetails.getDistributorPhone());
                        dealer_NTN.setText(dealerDetails.getDistributorsCompanyNTN());
                        dealer_company_name.setText(dealerDetails.getDistributorCompanyName());
                        String string = dealerDetails.getDistributorCreatedDate();
                        String[] parts = string.split("T");
                        String Date = parts[0];
                        dealer_created_date.setText(Date);
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

    private void InvoiceDetailsData() {
        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("Invoice_ID",
                Context.MODE_PRIVATE);
        paymentID = sharedPreferences3.getString("InvoiceID", "");
        Log.i("payment ID", paymentID);

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
