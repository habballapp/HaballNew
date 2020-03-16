package com.example.haball.Retailor.ui.Support;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

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
import android.widget.EditText;
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
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.ui.support.MyJsonArrayRequest;
import com.example.haball.Distributor.ui.support.SupportTicketFormFragment;
import com.example.haball.Distributor.ui.support.SupportViewModel;
import com.example.haball.R;
import com.example.haball.Support.Support_Retailer.Model.SupportDashboardModel;
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
    private List<SupportDashboardRetailerModel> SupportList = new ArrayList<>();
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
    private Spinner spinner_criteria;

    private Spinner spinner_consolidate;
    private Spinner spinner2;
    private EditText conso_edittext;
    private List<String> consolidate_felter = new ArrayList<>();
    private List<String> filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterFeltter;

    private String Company_selected;
    private String Filter_selected, Filter_selected_value;
    private List<String> company_names = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPayments, arrayAdapterPaymentsFilter;

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

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        try {
            fetchSupport();
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        spinner_criteria = root.findViewById(R.id.spinner_criteria);
//        arrayAdapterPayments = new ArrayAdapter<>(root.getContext(),
//                android.R.layout.simple_spinner_dropdown_item, company_names);
        spinner_consolidate = (Spinner) root.findViewById(R.id.spinner_conso);
        spinner2 = (Spinner) root.findViewById(R.id.conso_spinner2);
        conso_edittext = (EditText) root.findViewById(R.id.conso_edittext);
//        spinner_consolidate.setVisibility(View.GONE);
        spinner2.setVisibility(View.GONE);
        conso_edittext.setVisibility(View.GONE);
        consolidate_felter.add("Select Criteria");
        consolidate_felter.add("Contact Name");
        consolidate_felter.add("Issue Type");
        consolidate_felter.add("Created Date");
        consolidate_felter.add("Status");

        arrayAdapterPaymentsFilter = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_dropdown_item_1line, consolidate_felter);

        spinner_consolidate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filters = new ArrayList<>();
                spinner2.setVisibility(View.GONE);
                conso_edittext.setVisibility(View.GONE);
                if (i == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                } else {
                    Filter_selected = consolidate_felter.get(i);

                    if (!Filter_selected.equals("Issue Type"))
                        spinner2.setSelection(0);
                    if (!conso_edittext.getText().equals(""))
                        conso_edittext.setText("");

                    if (Filter_selected.equals("Contact Name")) {
                        Filter_selected = "ContactName";
                        conso_edittext.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Issue Type")) {
                        Filter_selected = "IssueType";
                        spinner2.setVisibility(View.VISIBLE);

                        filters.add("Issue Type");
                        filters.add("Main Dashboard");
                        filters.add("Connecting with Businesses");
                        filters.add("Contracting");
                        filters.add("Order");
                        filters.add("Invoice");
                        filters.add("Shipment");
                        filters.add("My Prepaid Account");
                        filters.add("My Profile");
                        filters.add("Reports");

                        arrayAdapterFeltter = new ArrayAdapter<>(getContext(),
                                android.R.layout.simple_dropdown_item_1line, filters);

                        arrayAdapterFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        arrayAdapterFeltter.notifyDataSetChanged();
                        spinner2.setAdapter(arrayAdapterFeltter);

                    } else if (Filter_selected.equals("Created Date")) {
                        Toast.makeText(getContext(), "Created Date selected", Toast.LENGTH_LONG).show();
                    } else if (Filter_selected.equals("Status")) {
                        Filter_selected = "Status";
                        spinner2.setVisibility(View.VISIBLE);

                        filters.add("Status");
                        filters.add("Pending");
                        filters.add("Resolved");

                        arrayAdapterFeltter = new ArrayAdapter<>(getContext(),
                                android.R.layout.simple_dropdown_item_1line, filters);

                        arrayAdapterFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        arrayAdapterFeltter.notifyDataSetChanged();
                        spinner2.setAdapter(arrayAdapterFeltter);


                    }
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        arrayAdapterPaymentsFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPaymentsFilter.notifyDataSetChanged();
        spinner_consolidate.setAdapter(arrayAdapterPaymentsFilter);

        filters.add("Document Type");
        filters.add("Invoice");
        filters.add("Prepaid ");
        filters.add("Shipment");
        arrayAdapterFeltter = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_dropdown_item_1line, filters);
        Log.i("aaaa1111", String.valueOf(consolidate_felter));
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                } else {
                    Filter_selected_value = String.valueOf(i);
                    Log.i("Filter_selected_value", Filter_selected_value);
                    try {
                        fetchFilteredSupport();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                try {
                    fetchFilteredSupport();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

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
                Type type = new TypeToken<List<SupportDashboardRetailerModel>>() {
                }.getType();
                try {
                    SupportList = gson.fromJson(String.valueOf(response.get(0)), type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mAdapter = new SupportDashboardRetailerAdapter(getContext(), SupportList);
                recyclerView.setAdapter(mAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printErrorMessage(error);
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
        request.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(request);
    }


    private void fetchFilteredSupport() throws JSONException {
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

                mAdapter = new SupportDashboardRetailerAdapter(getContext(), SupportList);
                recyclerView.setAdapter(mAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printErrorMessage(error);
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
        request.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(request);
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
