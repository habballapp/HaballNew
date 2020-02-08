package com.example.haball.Distributor.ui.payments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Payment.PaymentLedgerAdapter;
import com.example.haball.Payment.PaymentLedgerModel;
import com.example.haball.Payment.ProofOfPaymentAdapter;
import com.example.haball.Payment.ProofOfPaymentModel;
import com.example.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProofOfPaymentsDashboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private FragmentTransaction fragmentTransaction;

    private Button btn_create_proof_of_payment;
    private String Token;
    private String URL_PROOF_OF_PAYMENTS = "http://175.107.203.97:4008/api/proofofpayment/search";
    private ArrayAdapter<String> arrayAdapterPayments;
    private List<ProofOfPaymentModel> proofOfPaymentsList = new ArrayList<>();

    private String DistributorId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_proof_of_payment_dashboard, container, false);

        btn_create_proof_of_payment = root.findViewById(R.id.btn_create_proof_of_payment);
        recyclerView = root.findViewById(R.id.rv_proof_of_payments);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        btn_create_proof_of_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(ProofOfPaymentsDashboardFragment.this);
                fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(), new ProofOfPaymentForm());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        try {
            fetchProofOfPaymentsData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return root;
    }

    private void fetchProofOfPaymentsData() throws JSONException{
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token","");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id","");
        Log.i("DistributorId ", DistributorId);

        Log.i("Token", Token);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0);

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_PROOF_OF_PAYMENTS,map,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for(int i=0;i<result.length();i++){
                        jsonObject  = result.getJSONObject(i);
                    }
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<ProofOfPaymentModel>>(){}.getType();
                    proofOfPaymentsList = gson.fromJson(String.valueOf(result),type);

                    mAdapter = new ProofOfPaymentAdapter(getContext(),proofOfPaymentsList);
                    recyclerView.setAdapter(mAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF P_O_P", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer "+Token);
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(sr);
    }





//    private void fetchPaymentLedgerData(String companyId) throws JSONException{
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
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
//        map.put("Status", -1);
//        map.put("DistributorId", Integer.parseInt(DistributorId));
//        map.put("CompanyId", companyId);
//        map.put("TotalRecords", 10);
//        map.put("PageNumber", 0.1);
//
//        com.example.haball.Distributor.ui.support.MyJsonArrayRequest request = new MyJsonArrayRequest(Request.Method.POST, URL_PAYMENT_LEDGER, map, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                Log.i(" PAYMENT LEDGER => ", ""+response.toString());
//                JSONObject jsonObject = new JSONObject();
//                for(int i=0;i<response.length();i++){
//                    try {
//                        jsonObject = response.getJSONObject(i);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<PaymentLedgerModel>>(){}.getType();
////                paymentLedgerList = gson.fromJson(String.valueOf(response),type);
//
////                mAdapter = new PaymentLedgerAdapter(getContext(),paymentLedgerList);
////                recyclerView.setAdapter(mAdapter);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                Log.i("onErrorResponse", "Error");
//            }
//        }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer "+Token);
//                return params;
//            }
//        };
//        Volley.newRequestQueue(getContext()).add(request);
////        mAdapter = new PaymentLedgerAdapter(getContext(),paymentLedgerList);
////        recyclerView.setAdapter(mAdapter);
//    }
}
