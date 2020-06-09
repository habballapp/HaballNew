package com.example.haball.Distributor.ui.support;

import android.app.DatePickerDialog;
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
import android.view.animation.TranslateAnimation;
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

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.haball.R;
import com.example.haball.Support.Support_Ditributor.Adapter.SupportDashboardAdapter;
import com.example.haball.Support.Support_Ditributor.Model.SupportDashboardModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SupportFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> array = new ArrayList<>();
    private TextView btn_add_ticket;
    private TextView tv_shipment_no_data;
    private String Token;
    private String URL_SUPPORT = "http://175.107.203.97:4013/api/contact/search";
    private SupportViewModel supportViewModel;
    private List<SupportDashboardModel> SupportList = new ArrayList<>();
    private Spinner spinner_criteria;

    private Spinner spinner_consolidate;
    private Spinner spinner2;
    private EditText conso_edittext;
    private List<String> consolidate_felter = new ArrayList<>();
    private List<String> filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterFeltter;

    private String Company_selected, DistributorId;
    private String Filter_selected, Filter_selected_value;
    private List<String> company_names = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPayments, arrayAdapterPaymentsFilter;

    private RelativeLayout spinner_container1;

    private Button consolidate;
    private String Filter_selected1, Filter_selected2;
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
    private RelativeLayout spinner_container_main;
    private static int y;
    private List<String> scrollEvent = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        supportViewModel =
                ViewModelProviders.of(this).get(SupportViewModel.class);
        View root = inflater.inflate(R.layout.activity_support_dashboard, container, false);
        btn_add_ticket = root.findViewById(R.id.btn_add_ticket);
        btn_add_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(((ViewGroup) getView().getParent()).getId(), new SupportTicketFormFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        recyclerView = root.findViewById(R.id.rv_support_complaints);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        try {
            fetchSupport();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tv_shipment_no_data = root.findViewById(R.id.tv_shipment_no_data);
        search_bar = root.findViewById(R.id.search_bar);

        // DATE FILTERS ......
        date_filter_rl = root.findViewById(R.id.date_filter_rl);
        first_date = root.findViewById(R.id.first_date);
        first_date_btn = root.findViewById(R.id.first_date_btn);
        second_date = root.findViewById(R.id.second_date);
        second_date_btn = root.findViewById(R.id.second_date_btn);

        spinner_consolidate = (Spinner) root.findViewById(R.id.spinner_conso);
        spinner2 = (Spinner) root.findViewById(R.id.conso_spinner2);
        conso_edittext = (EditText) root.findViewById(R.id.conso_edittext);
        spinner_container_main = root.findViewById(R.id.spinner_container_main);
        tv_shipment_no_data = root.findViewById(R.id.tv_shipment_no_data);
        tv_shipment_no_data.setVisibility(View.GONE);
        spinner_container1 = root.findViewById(R.id.spinner_container1);
        spinner_container1.setVisibility(View.GONE);
        date_filter_rl.setVisibility(View.GONE);
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
                spinner_container1.setVisibility(View.GONE);
                date_filter_rl.setVisibility(View.GONE);
                conso_edittext.setVisibility(View.GONE);

                if (i == 0) {
                    try {
                           ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(50,0 ,50 ,0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(50,0 ,50 ,0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                    Filter_selected = consolidate_felter.get(i);

                    spinner2.setSelection(0);
                    conso_edittext.setText("");
                    if (Filter_selected.equals("Contact Name")) {
                        search_bar.setHint("Search by " + Filter_selected);
                        Filter_selected = "CompanyName";
                        conso_edittext.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Issue Type")) {
                        Filter_selected = "IssueType";
                        spinner_container1.setVisibility(View.VISIBLE);
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
                        date_filter_rl.setVisibility(View.VISIBLE);
//                        Toast.makeText(getContext(), "Created Date selected", Toast.LENGTH_LONG).show();
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
                    } else if (Filter_selected.equals("Status")) {

                        Filter_selected = "Status";
                        tv_shipment_no_data.setVisibility(View.GONE);
                        spinner_container1.setVisibility(View.VISIBLE);

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

        Log.i("aaaa1111", String.valueOf(consolidate_felter));

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (Filter_selected.equals("Status")) {
                    if (i == 0) {
                        try {
                               ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                            ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                            ((TextView) adapterView.getChildAt(0)).setPadding(50,0 ,50 ,0);
                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        try {
                            ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                            ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                            ((TextView) adapterView.getChildAt(0)).setPadding(50,0 ,50 ,0);
                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                        }

                        Filter_selected_value = String.valueOf(i - 1);
                        if (!Filter_selected_value.equals("")) {
                            try {
                                fetchFilteredSupport();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    Filter_selected_value = filters.get(i);
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


        conso_edittext.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                Log.i("text1", "check");
                Log.i("text", String.valueOf(s));
                Filter_selected_value = String.valueOf(s);
                if (!Filter_selected_value.equals("")) {

                    try {
                        fetchFilteredSupport();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        fetchSupport();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
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

//                int visibleItemCount = layoutManager.getChildCount();
//                int totalItemCount = layoutManager.getItemCount();
//                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
//                    if (totalPages != 0 && pageNumber < totalPages) {
////                                Toast.makeText(getContext(), pageNumber + " - " + totalPages, Toast.LENGTH_LONG).show();
//                        btn_load_more.setVisibility(View.VISIBLE);
//                    }
//                }
            }

        });
        return root;
    }

    private void fetchSupport() throws JSONException {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        final JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0);

        MyJsonArrayRequest request = new MyJsonArrayRequest(Request.Method.POST, URL_SUPPORT, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                Log.i("onResponse => SUPPORT ", "" + response.toString());
                JSONObject jsonObject = new JSONObject();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Gson gson = new Gson();
                Type type = new TypeToken<List<SupportDashboardModel>>() {
                }.getType();
                SupportList = gson.fromJson(String.valueOf(response), type);

                mAdapter = new SupportDashboardAdapter(getContext(), SupportList, recyclerView, mAdapter);
                recyclerView.setAdapter(mAdapter);
                if (response.length() != 0) {
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
                Log.i("onErrorResponse", "Error");
            }
        });
        Volley.newRequestQueue(getContext()).add(request);
    }


    private void fetchFilteredSupport() throws JSONException {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0);
        if (Filter_selected.equals("date")) {
            map.put(Filter_selected1, fromDate);
            map.put(Filter_selected2, toDate);
        } else {
            map.put(Filter_selected, Filter_selected_value);
        }
        Log.i("map", String.valueOf(map));
        MyJsonArrayRequest request = new MyJsonArrayRequest(Request.Method.POST, URL_SUPPORT, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("onResponse => SUPPORT ", "" + response.toString());
//
                Gson gson = new Gson();
                Type type = new TypeToken<List<SupportDashboardModel>>() {
                }.getType();
                SupportList = gson.fromJson(String.valueOf(response), type);

                mAdapter = new SupportDashboardAdapter(getContext(), SupportList, recyclerView, mAdapter);
                recyclerView.setAdapter(mAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printErrorMessage(error);

                error.printStackTrace();
                Log.i("onErrorResponse", "Error");
            }
        });
        Volley.newRequestQueue(getContext()).add(request);
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

    private void openCalenderPopup(String date_type) {
        dateType = date_type;
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(getContext(), R.style.DialogTheme, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
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

        try {
            fetchFilteredSupport();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
//        Log.i("distinct", scroll);
        return scroll;
    }
}
