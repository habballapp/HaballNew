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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Templates;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupportFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> array = new ArrayList<>();
    private Button btn_add_ticket_retailer;
    private String Token, DistributorId;
    private String URL_SUPPORT = "http://175.107.203.97:3020/api/support/Search";
    private SupportViewModel supportViewModel;
    private List<SupportDashboardModel> SupportList = new ArrayList<>();
    //spinner1
    private Spinner support_retailer_spiner1;
    private List<String> supportspinner_List = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterSupport_ret;
    private String Filter_Select, Felter_Selected_value;
    //spinner2
    private Spinner support_retailer_spiner2;
    private List<String> filterList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterFilterList;
    private EditText edt_support_ret;

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
                fragmentTransaction.replace(((ViewGroup) getView().getParent()).getId(), new SupportTicketFormFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        //init
        recyclerView = root.findViewById(R.id.rv_support_complaints_retailer);
//        support_retailer_spiner1 = root.findViewById(R.id.support_retailer_spiner1);
//        support_retailer_spiner2 = root.findViewById(R.id.support_retailer_spiner2);
//
//        supportspinner_List.add("abc");
//        supportspinner_List.add("zxc");
//        supportspinner_List.add("qwe");
//        supportspinner_List.add("ghg");
//
//        arrayAdapterSupport_ret = new ArrayAdapter<>(root.getContext(),
//                android.R.layout.simple_spinner_dropdown_item, supportspinner_List);
//        support_retailer_spiner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
//                if (i == 0) {
//                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
//                } else {
//                    Filter_Select = supportspinner_List.get(i);
//                    //if (!Filter_Select.equals("Status"))
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        arrayAdapterSupport_ret.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        arrayAdapterSupport_ret.notifyDataSetChanged();
//        support_retailer_spiner1.setAdapter(arrayAdapterSupport_ret);

        //filter SPinner2


        //recycler view
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
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token  ", Token);

        JSONObject map = new JSONObject();
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0);

        MyJsonArrayRequest request = new MyJsonArrayRequest(Request.Method.POST, URL_SUPPORT, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.i("onResponse => SUPPORT ", "" + response.get(0).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//
                Gson gson = new Gson();
                Type type = new TypeToken<List<SupportDashboardModel>>() {
                }.getType();
                try {
                    SupportList = gson.fromJson(String.valueOf(response.get(0)), type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mAdapter = new SupportDashboardAdapter(getContext(), SupportList);
                recyclerView.setAdapter(mAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject data = new JSONObject(responseBody);
                    String message = data.getString("message");
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                error.printStackTrace();
                Log.i("onErrorResponse", "Error");
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
        Volley.newRequestQueue(getContext()).add(request);
    }
}
