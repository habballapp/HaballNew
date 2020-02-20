package com.example.haball.Retailor.ui.Support;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.ui.support.MyJsonArrayRequest;
import com.example.haball.Distributor.ui.support.SupportTicketFormFragment;
import com.example.haball.Distributor.ui.support.SupportViewModel;
import com.example.haball.R;
import com.example.haball.Support.SupportDashboardAdapter;
import com.example.haball.Support.SupportDashboardModel;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class SupportFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> array = new ArrayList<>();
    private Button btn_add_ticket_retailer;
    private String Token;
    private String URL_SUPPORT = "http://175.107.203.97:3020/api/support/Search";
    private SupportViewModel supportViewModel;
    private List<SupportDashboardModel> SupportList = new ArrayList<>();
    private String DistributorId;

    public SupportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_support, container, false);

        btn_add_ticket_retailer = root.findViewById(R.id.btn_add_ticket_retailer);
        btn_add_ticket_retailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(), new SupportTicketFormFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        recyclerView = root.findViewById(R.id.rv_support_complaints_retailer);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        try {
            fetchSupport();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
    }
    private void fetchSupport() throws JSONException {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token","");
        Log.i("Token  ", Token);

        JSONObject map = new JSONObject();
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0);

        MyJsonArrayRequest request = new MyJsonArrayRequest(Request.Method.POST, URL_SUPPORT, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.i("onResponse => SUPPORT ", ""+response.get(0).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                JSONObject jsonObject = new JSONObject();
//                for(int i=0;i<response.length();i++){
//                    try {
//                        jsonObject = response.getJSONObject(i);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
                Gson gson = new Gson();
                Type type = new TypeToken<List<SupportDashboardModel>>(){}.getType();
                try {
                    SupportList = gson.fromJson(String.valueOf(response.get(0)),type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mAdapter = new SupportDashboardAdapter(getContext(),SupportList);
                recyclerView.setAdapter(mAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("onErrorResponse", "Error");
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
        Volley.newRequestQueue(getContext()).add(request);
    }
}
