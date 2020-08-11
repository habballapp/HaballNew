package com.haball.Distributor.ui.orders.OrdersTabsNew.Tabs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haball.CustomToast;
import com.haball.Distributor.DistributorDashboard;
import com.haball.Distributor.ui.home.HomeFragment;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Adapters.ParentList_Adapter_DistOrder;
import com.haball.Distributor.ui.orders.OrdersTabsNew.ExpandableRecyclerAdapter;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderChildlist_Model_DistOrder;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderParentlist_Model_DistOrder;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Order_PlaceOrder;
import com.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.NonSwipeableViewPager;
import com.haball.ProcessingError;
import com.haball.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haball.Retailor.RetailorDashboard;
import com.haball.Retailor.ui.Place_Order.ui.main.Tabs.Retailer_OrderPlace_retailer_dashboarad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;
import androidx.annotation.UiThread;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Dist_OrderPlace extends Fragment {

    RecyclerView recyclerView, subchlid_RV;
    private List<OrderParentlist_Model_DistOrder> titles = new ArrayList<>();
    private List<OrderChildlist_Model_DistOrder> productList = new ArrayList<>();
    //    private List<SimpleParent> parentObjects = new ArrayList<>();
    private String URL_DISTRIBUTOR_DASHBOARD = "http://175.107.203.97:4013/api/dashboard/ReadDistributorDashboard";
    private String URL_PRODUCT_CATEGORY = "http://175.107.203.97:4013/api/products/ReadCategories/0/";
    private String URL_PRODUCT = "http://175.107.203.97:4013/api/products/ReadProductsByCategories/0/";
    private String Token, DistributorId;
    private String object_string, object_stringqty;
    private List<OrderChildlist_Model_DistOrder> selectedProductsDataList = new ArrayList<>();
    private List<OrderChildlist_Model_DistOrder> temp_list = new ArrayList<>();
    private List<String> temp_listqty = new ArrayList<>();
    private List<String> selectedProductsQuantityList = new ArrayList<>();
    private float grossAmount = 0;
    private Button btn_checkout, close_order_button;
    private String CompanyId;
    private Spinner spinner_conso;
    private RelativeLayout spinner_container_main;
    //    private List<OrderParentlist_Model_DistOrder> totalCategory = new ArrayList<>();
    private List<String> totalCategoryTitle = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterSpinnerConso;
    private String Category_selected;
    private HashMap<String, String> Categories = new HashMap<>();
    private TextInputEditText et_test;
    List<OrderParentlist_Model_DistOrder> temp_titles = new ArrayList<>();
    private static int y;
    private List<String> scrollEvent = new ArrayList<>();
    private FragmentTransaction fragmentTransaction;
    private int lastExpandedPosition = -1;
    private Typeface myFont;
    private Loader loader;
    private View myview = null;
    //    private MyAsyncTask myAsyncTask;
    private String editTextValue = "";
    String current_balance;
    private int selected_category_index = 0;
    boolean byDefaultStatus = true;

    public Dist_OrderPlace() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dist_main_placeorder, container, false);
        myview = view;
        btn_checkout = view.findViewById(R.id.btn_checkout);
        close_order_button = view.findViewById(R.id.close_button);
        recyclerView = view.findViewById(R.id.rv_order_list);
        spinner_container_main = view.findViewById(R.id.spinner_container_main);
//        subchlid_RV = view.findViewById(R.id.subchlid_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        spinner_conso = view.findViewById(R.id.spinner_conso);
        myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);
        et_test = view.findViewById(R.id.et_test);
        arrayAdapterSpinnerConso = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_dropdown_item, totalCategoryTitle) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(30, 0, 30, 0);
                text.setTypeface(myFont);
                return view;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(30, 0, 30, 0);
                return view;
            }
        };
//
//        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
//                Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = selectedProducts.edit();
//        editor.putString("selected_products", "");
//        editor.putString("selected_products_qty", "");
//        editor.apply();
        SharedPreferences add_more_product = getContext().getSharedPreferences("add_more_product",
                Context.MODE_PRIVATE);
        if (!add_more_product.getString("add_more_product", "").equals("fromAddMore")) {
            Log.i("debugOrder_AddMore", "not from add more product");
            SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = selectedProducts.edit();
            editor.putString("selected_products", "");
            editor.putString("selected_products_qty", "");
            editor.apply();
        }

//        fetchDashboardData();
        loader = new Loader(getContext());
//
//        close_order_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.add(R.id.main_container, new HomeFragment());
//                fragmentTransaction.commit();
//
//            }
//        });

        close_order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences orderCheckout = getContext().getSharedPreferences("orderCheckout",
                        Context.MODE_PRIVATE);
                Gson gson = new Gson();
                String orderCheckedOut = orderCheckout.getString("orderCheckout", "");

                if (selectedProductsDataList != null && selectedProductsDataList.size() > 0 && (orderCheckedOut.equals("orderCheckout") || orderCheckedOut.equals("orderCheckout123"))) {
                    showDiscardDialog();
                } else {

                    InputMethodManager imm = (InputMethodManager) (getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(myview.getWindowToken(), 0);

                    fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.main_container, new Order_PlaceOrder()).addToBackStack("null");
                    fragmentTransaction.commit();
                }
            }
        });
        arrayAdapterSpinnerConso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_conso.setAdapter(arrayAdapterSpinnerConso);
        spinner_conso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                Category_selected = totalCategoryTitle.get(position);

                try {
                    ((TextView) parent.getChildAt(position)).setTextColor(getResources().getColor(R.color.textcolor));
                    ((TextView) parent.getChildAt(position)).setTextSize((float) 13.6);
                    ((TextView) parent.getChildAt(position)).setPadding(50, 0, 50, 0);
                    Log.i("Categoriesselected", Categories.get(Category_selected) + " - " + Category_selected);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                try {
                    getFilteredProductCategory(Categories.get(Category_selected));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//
//                SharedPreferences orderCheckout = getContext().getSharedPreferences("orderCheckout",
//                        Context.MODE_PRIVATE);
//                final String orderCheckedOut = orderCheckout.getString("orderCheckout", "");
//
//                if(orderCheckedOut.equals("orderCheckout")) {
//                    SharedPreferences companyInfo = getContext().getSharedPreferences("CompanyInfo",
//                            Context.MODE_PRIVATE);
//                    String CategoryIndex = companyInfo.getString("CategoryIndex", "0");
//
//                    spinner_conso.setSelection(Integer.parseInt(CategoryIndex));
//                    byDefaultStatus = true;
//                }
//
//                if (!byDefaultStatus) {
//
//
//                    loader.showLoader();
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    loader.hideLoader();
//                                    SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
//                                            Context.MODE_PRIVATE);
//                                    Gson gson = new Gson();
//                                    object_stringqty = selectedProducts.getString("selected_products_qty", "");
//                                    object_string = selectedProducts.getString("selected_products", "");
//                                    Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
//                                    }.getType();
//                                    Type typeString = new TypeToken<List<String>>() {
//                                    }.getType();
//                                    if (!object_string.equals("") && !object_stringqty.equals("")) {
//                                        selectedProductsDataList = gson.fromJson(object_string, type);
//                                        selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
//                                    }
//
//                                    if (selectedProductsDataList.size() > 0 && (orderCheckedOut.equals("") || orderCheckedOut.equals("orderCheckout123"))) {
//                                        spinner_conso.setSelection(selected_category_index);
//                                        byDefaultStatus = true;
//                                        new CustomToast().showToast(((FragmentActivity) getContext()), "Cross-Category Product selection is not allowed.");
//                                    } else {
//                                        selected_category_index = position;
//                                        SharedPreferences companyInfo = getContext().getSharedPreferences("CompanyInfo",
//                                                Context.MODE_PRIVATE);
//                                        SharedPreferences.Editor editor = companyInfo.edit();
//                                        editor.putString("CategoryIndex", String.valueOf(selected_category_index));
//                                        editor.apply();
//                                        try {
//                                            ((TextView) parent.getChildAt(position)).setTextColor(getResources().getColor(R.color.textcolor));
//                                            ((TextView) parent.getChildAt(position)).setTextSize((float) 13.6);
//                                            ((TextView) parent.getChildAt(position)).setPadding(50, 0, 50, 0);
//                                            Log.i("Categoriesselected", Categories.get(Category_selected) + " - " + Category_selected);
//                                        } catch (NullPointerException e) {
//                                            e.printStackTrace();
//                                        }
//                                        try {
//                                            getFilteredProductCategory(Categories.get(Category_selected));
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }
//                            }, 3000);
//                        }
//                    });
//
//
//
//                    if(orderCheckedOut.equals("orderCheckout")) {
//                        SharedPreferences.Editor orderCheckout_editor = orderCheckout.edit();
//                        orderCheckout_editor.putString("orderCheckout123", "");
//                        orderCheckout_editor.apply();
//                    }
//                } else {
//                    byDefaultStatus = false;
//                    SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
//                            Context.MODE_PRIVATE);
//                    Gson gson = new Gson();
//                    object_stringqty = selectedProducts.getString("selected_products_qty", "");
//                    object_string = selectedProducts.getString("selected_products", "");
//                    Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
//                    }.getType();
//                    Type typeString = new TypeToken<List<String>>() {
//                    }.getType();
//                    if (!object_string.equals("") && !object_stringqty.equals("")) {
//                        selectedProductsDataList = gson.fromJson(object_string, type);
//                        selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
//                    }
//
//
//                    if (selectedProductsDataList.size() > 0 && (orderCheckedOut.equals("") || orderCheckedOut.equals("orderCheckout123"))) {
//                        spinner_conso.setSelection(selected_category_index);
//                        byDefaultStatus = true;
//                        new CustomToast().showToast(((FragmentActivity) getContext()), "Cross-Category Product selection is not allowed.");
//                    } else {
//                        selected_category_index = position;
//                        SharedPreferences companyInfo = getContext().getSharedPreferences("CompanyInfo",
//                                Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = companyInfo.edit();
//                        editor.putString("CategoryIndex", String.valueOf(selected_category_index));
//                        editor.apply();
//                        try {
//                            ((TextView) parent.getChildAt(position)).setTextColor(getResources().getColor(R.color.textcolor));
//                            ((TextView) parent.getChildAt(position)).setTextSize((float) 13.6);
//                            ((TextView) parent.getChildAt(position)).setPadding(50, 0, 50, 0);
//                            Log.i("Categoriesselected", Categories.get(Category_selected) + " - " + Category_selected);
//                        } catch (NullPointerException e) {
//                            e.printStackTrace();
//                        }
//                        try {
//                            getFilteredProductCategory(Categories.get(Category_selected));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        et_test.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
////                titles = new ArrayList<>();
//                if (!String.valueOf(s).equals("")) {
//                    Log.i("titles123", "in if");
//                    try {
//                        getFilteredProductsFromCategory(String.valueOf(s));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Log.i("titles123", "in else");
//                    try {
//                        getProductCategory();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });

        et_test.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("DebugFilter", "in edit text: " + s);
                Log.i("DebugFilter", "in edit text: C, " + Category_selected);
                editTextValue = String.valueOf(s);

//                titles = new ArrayList<>();
                if (!String.valueOf(s).equals("")) {
                    spinner_conso.setSelection(0);
                    Log.i("titles123", "in if");
                    try {
                        getFilteredProductsFromCategory(String.valueOf(s));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i("titles123", "in else");
                    if (Category_selected != null && Category_selected.equals("All Category")) {
                        try {
                            getProductCategory();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
//        et_test.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                String Filter_selected_value = String.valueOf(et_test.getText());
//                if (!Filter_selected_value.equals("")) {
//                    try {
//                        getFilteredProductsFromCategory(String.valueOf(Filter_selected_value));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    try {
//                        getProductCategory();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });

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
//                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
//                    if (totalPages != 0 && pageNumber < totalPages) {
////                                Toast.makeText(getContext(), pageNumber + " - " + totalPages, Toast.LENGTH_LONG).show();
//                        btn_load_more.setVisibility(View.VISIBLE);
//                    }
            }

        });

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.showLoader();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loader.hideLoader();
                                //Do something after 1 second

                                SharedPreferences orderCheckout = getContext().getSharedPreferences("orderCheckout",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor orderCheckout_editor = orderCheckout.edit();
                                orderCheckout_editor.putString("orderCheckout", "orderCheckout");
                                orderCheckout_editor.apply();
                                NonSwipeableViewPager viewPager = getActivity().findViewById(R.id.view_pager5);
                                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
                                        Context.MODE_PRIVATE);
                                Gson gson = new Gson();
                                object_stringqty = selectedProducts.getString("selected_products_qty", "");
                                object_string = selectedProducts.getString("selected_products", "");
                                Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
                                }.getType();
                                Type typeString = new TypeToken<List<String>>() {
                                }.getType();
                                selectedProductsDataList = gson.fromJson(object_string, type);
                                selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
//                        if (selectedProductsDataList.size() > 0) {
//                            for (int i = 0; i < selectedProductsDataList.size(); i++) {
//                                Log.i("unit price", selectedProductsDataList.get(i).getProductUnitPrice());
//                                Log.i("qty", selectedProductsQuantityList.get(i));
//                                if (!selectedProductsDataList.get(i).getProductUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
//                                    grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getProductUnitPrice()) * Float.parseFloat(selectedProductsQuantityList.get(i));
//                            }
                                if (selectedProductsDataList.size() > 0) {
                                    for (int i = 0; i < selectedProductsDataList.size(); i++) {
//                                Log.i("unit price", selectedProductsDataList.get(i).getProductUnitPrice());
//                                Log.i("qty", selectedProductsQuantityList.get(i));
                                        if (!selectedProductsDataList.get(i).getUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
                                            grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice()) * Float.parseFloat(selectedProductsQuantityList.get(i));
                                    }
                                    SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
                                            Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = grossamount.edit();
                                    editor.putString("grossamount", String.valueOf(grossAmount));
                                    editor.apply();
//                            Toast.makeText(getContext(), "Total Amount: " + grossAmount, Toast.LENGTH_SHORT).show();
                                    grossAmount = 0;
                                    viewPager.setCurrentItem(1);

                                    InputMethodManager imm = (InputMethodManager) (getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(myview.getWindowToken(), 0);


                                    FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.add(R.id.main_container, new Dist_Order_Summary());
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();

                                }
                            }
                        }, 3000);
                    }
                });
            }
        });

        try {
            getProductCategory();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;

    }
//
//    private boolean enableCheckout() {
//
////        Log.i("checkout", "in checkout");
//        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
//                Context.MODE_PRIVATE);
//        Gson gson = new Gson();
//        object_stringqty = selectedProducts.getString("selected_products_qty", "");
//        object_string = selectedProducts.getString("selected_products", "");
//        Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
//        }.getType();
//        Type typeString = new TypeToken<List<String>>() {
//        }.getType();
//        if (!object_string.equals("") && !object_stringqty.equals("")) {
//            selectedProductsDataList = gson.fromJson(object_string, type);
//            selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
//        }
//        float totalQty = 0;
//        if (selectedProductsDataList != null) {
//            if (selectedProductsDataList.size() > 0) {
//                for (int i = 0; i < selectedProductsDataList.size(); i++) {
////                    Log.i("unit price", selectedProductsDataList.get(i).getUnitPrice());
////                    Log.i("qty", selectedProductsQuantityList.get(i));
//                    if (!selectedProductsDataList.get(i).getUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
//                        if (Float.parseFloat(selectedProductsQuantityList.get(i)) > 0) {
//                            totalQty = totalQty + Float.parseFloat(selectedProductsQuantityList.get(i));
//                        }
//                }
//            }
//        }
////        Log.i("totalQty", "here");
////        Log.i("totalQty", String.valueOf(totalQty));
//        if (totalQty > 0) {
//            btn_checkout.setEnabled(true);
//            btn_checkout.setBackgroundResource(R.drawable.button_round);
//        } else {
//            btn_checkout.setEnabled(false);
//            btn_checkout.setBackgroundResource(R.drawable.button_grey_round);
//        }
//        new MyAsyncTask().execute();
//
////            selectedProductsDataList = gson.fromJson(object_string, type);
//        if (selectedProductsDataList != null) {
//            if (selectedProductsDataList.size() > 0) {
//                btn_checkout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        loader.showLoader();
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        loader.hideLoader();
//                                        NonSwipeableViewPager viewPager = getActivity().findViewById(R.id.view_pager5);
//                                        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
//                                                Context.MODE_PRIVATE);
//                                        Gson gson = new Gson();
//                                        object_stringqty = selectedProducts.getString("selected_products_qty", "");
//                                        object_string = selectedProducts.getString("selected_products", "");
//                                        Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
//                                        }.getType();
//                                        Type typeString = new TypeToken<List<String>>() {
//                                        }.getType();
//                                        selectedProductsDataList = gson.fromJson(object_string, type);
//                                        selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
//                                        if (selectedProductsDataList != null) {
//                                            if (selectedProductsDataList.size() > 0) {
//                                                for (int i = 0; i < selectedProductsDataList.size(); i++) {
////                                    Log.i("unit price", selectedProductsDataList.get(i).getUnitPrice());
////                                    Log.i("qty", selectedProductsQuantityList.get(i));
//                                                    if (!selectedProductsDataList.get(i).getUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
//                                                        grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice()) * Float.parseFloat(selectedProductsQuantityList.get(i));
//                                                    if (Float.parseFloat(selectedProductsQuantityList.get(i)) > 0)
//                                                        btn_checkout.setEnabled(true);
//
//                                                }
//                                            }
//                                        }
//                                        SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
//                                                Context.MODE_PRIVATE);
//                                        SharedPreferences.Editor editor = grossamount.edit();
//                                        editor.putString("grossamount", String.valueOf(grossAmount));
//                                        editor.apply();
//                                        // Toast.makeText(getContext(), "Total Amount: " + grossAmount, Toast.LENGTH_SHORT).show();
//                                        grossAmount = 0;
//                                        viewPager.setCurrentItem(1);
//                                        FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
//                                        fragmentTransaction.add(R.id.main_container, new Dist_Order_Summary());
//                                        fragmentTransaction.addToBackStack(null);
//                                        fragmentTransaction.commit();
//                                    }
//                                }, 3000);
//                            }
//                        });
//                    }
//                });
//                return true;
//            }
//        }
//        return false;
//    }


    private void showDiscardDialog() {
        Log.i("CreatePayment", "In Dialog");
        final FragmentManager fm = getActivity().getSupportFragmentManager();

        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view_popup = inflater.inflate(R.layout.discard_changes, null);
        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
        tv_discard_txt.setText("Are you sure, you want to leave this page? Your changes will be discarded.");
        alertDialog.setView(view_popup);
        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        alertDialog.getWindow().setAttributes(layoutParams);
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("CreatePayment", "Button Clicked");
                alertDialog.dismiss();
                SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                editorOrderTabsFromDraft.putString("TabNo", "0");
                editorOrderTabsFromDraft.apply();

                SharedPreferences orderCheckout = getContext().getSharedPreferences("orderCheckout",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor orderCheckout_editor = orderCheckout.edit();
                orderCheckout_editor.putString("orderCheckout", "");
                orderCheckout_editor.apply();

                InputMethodManager imm = (InputMethodManager) (getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(myview.getWindowToken(), 0);

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.main_container, new Dist_OrderPlace()).addToBackStack("null");
                fragmentTransaction.add(R.id.main_container, new HomeFragment()).addToBackStack("null");
                fragmentTransaction.commit();


//                Intent login_intent = new Intent(((FragmentActivity) getContext()), DistributorDashboard.class);
//                ((FragmentActivity) getContext()).startActivity(login_intent);
//                ((FragmentActivity) getContext()).finish();

//                fm.popBackStack();
            }
        });

        ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.btn_close);
        img_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        alertDialog.show();
    }


    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
//                    Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
//
//                    if (selectedProductsDataList == null || selectedProductsDataList.size() == 0) {
                    SharedPreferences orderCheckout = getContext().getSharedPreferences("orderCheckout",
                            Context.MODE_PRIVATE);
                    Gson gson = new Gson();
                    String orderCheckedOut = orderCheckout.getString("orderCheckout", "");

                    if (selectedProductsDataList != null && selectedProductsDataList.size() > 0 && (orderCheckedOut.equals("orderCheckout") || orderCheckedOut.equals("orderCheckout123"))) {
                        showDiscardDialog();
                        return true;
                    } else {
                        InputMethodManager imm = (InputMethodManager) (getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(myview.getWindowToken(), 0);


                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.main_container, new Dist_OrderPlace()).addToBackStack("null");
                        fragmentTransaction.add(R.id.main_container, new Order_PlaceOrder()).addToBackStack("null");
                        fragmentTransaction.commit();
                        return true;
//                    } else {
//                        showDiscardDialog();
//                        return true;
                    }
//                    SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
//                            Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
//                    editorOrderTabsFromDraft.putString("TabNo", "0");
//                    editorOrderTabsFromDraft.apply();
//
//                    Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
//                    ((FragmentActivity) getContext()).startActivity(login_intent);
//                    ((FragmentActivity) getContext()).finish();
                }
                return false;
            }
        });

    }

    private boolean enableCheckout() {
//        Log.i("checkout", "in checkout");
        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        object_stringqty = selectedProducts.getString("selected_products_qty", "");
        object_string = selectedProducts.getString("selected_products", "");
        Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
        }.getType();
        Type typeString = new TypeToken<List<String>>() {
        }.getType();
        if (!object_string.equals("") && !object_stringqty.equals("")) {
            selectedProductsDataList = gson.fromJson(object_string, type);
            selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
        }
//        float totalQty = 0;
//        if (selectedProductsDataList != null) {
//            if (selectedProductsDataList.size() > 0) {
//                for (int i = 0; i < selectedProductsDataList.size(); i++) {
////                    Log.i("unit price", selectedProductsDataList.get(i).getProductUnitPrice());
////                    Log.i("qty", selectedProductsQuantityList.get(i));
//                    if (!selectedProductsDataList.get(i).getProductUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
//                        if (Float.parseFloat(selectedProductsQuantityList.get(i)) > 0) {
//                            totalQty = totalQty + Float.parseFloat(selectedProductsQuantityList.get(i));
//                        }
//                }
//            }
//        }
////        Log.i("totalQty", "here");
////        Log.i("totalQty", String.valueOf(totalQty));
//        if (totalQty > 0) {
//            btn_checkout.setEnabled(true);
//            btn_checkout.setBackgroundResource(R.drawable.button_round);
//        } else {
//            btn_checkout.setEnabled(false);
//            btn_checkout.setBackgroundResource(R.drawable.button_grey_round);
//        }
//        myAsyncTask = new MyAsyncTask();
//        myAsyncTask.execute();
        final Loader loader = new Loader(getContext());

//            selectedProductsDataList = gson.fromJson(object_string, type);
        if (selectedProductsDataList != null) {
            if (selectedProductsDataList.size() > 0) {
//                btn_checkout.setBackgroundResource(R.drawable.button_round);
//                btn_checkout.setEnabled(true);
                btn_checkout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loader.showLoader();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        loader.hideLoader();
                                        //Do something after 1 second

                                        SharedPreferences orderCheckout = getContext().getSharedPreferences("orderCheckout",
                                                Context.MODE_PRIVATE);
                                        SharedPreferences.Editor orderCheckout_editor = orderCheckout.edit();
                                        orderCheckout_editor.putString("orderCheckout", "orderCheckout");
                                        orderCheckout_editor.apply();
                                        NonSwipeableViewPager viewPager = getActivity().findViewById(R.id.view_pager5);
                                        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
                                                Context.MODE_PRIVATE);
                                        Gson gson = new Gson();
                                        object_stringqty = selectedProducts.getString("selected_products_qty", "");
                                        object_string = selectedProducts.getString("selected_products", "");
                                        Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
                                        }.getType();
                                        Type typeString = new TypeToken<List<String>>() {
                                        }.getType();
                                        selectedProductsDataList = gson.fromJson(object_string, type);
                                        selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
//                        if (selectedProductsDataList.size() > 0) {
//                            for (int i = 0; i < selectedProductsDataList.size(); i++) {
//                                Log.i("unit price", selectedProductsDataList.get(i).getProductUnitPrice());
//                                Log.i("qty", selectedProductsQuantityList.get(i));
//                                if (!selectedProductsDataList.get(i).getProductUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
//                                    grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getProductUnitPrice()) * Float.parseFloat(selectedProductsQuantityList.get(i));
//                            }
                                        if (selectedProductsDataList.size() > 0) {
                                            for (int i = 0; i < selectedProductsDataList.size(); i++) {
//                                Log.i("unit price", selectedProductsDataList.get(i).getProductUnitPrice());
//                                Log.i("qty", selectedProductsQuantityList.get(i));
                                                if (!selectedProductsDataList.get(i).getUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
                                                    grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice()) * Float.parseFloat(selectedProductsQuantityList.get(i));
                                            }
                                            SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
                                                    Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = grossamount.edit();
                                            editor.putString("grossamount", String.valueOf(grossAmount));
                                            editor.apply();
//                            Toast.makeText(getContext(), "Total Amount: " + grossAmount, Toast.LENGTH_SHORT).show();
                                            grossAmount = 0;
                                            viewPager.setCurrentItem(1);

                                            InputMethodManager imm = (InputMethodManager) (getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(myview.getWindowToken(), 0);


                                            FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                                            fragmentTransaction.add(R.id.main_container, new Dist_Order_Summary());
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();

                                        }
                                    }
                                }, 3000);
                            }
                        });
                    }
                });
                return true;
            }
        }
        return false;
    }


    private void getProductCategory() throws JSONException {
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("CompanyInfo",
                Context.MODE_PRIVATE);
        CompanyId = sharedPreferences2.getString("CompanyId", "");
        Log.i("CompanyId", CompanyId);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        Log.i("Map", String.valueOf(map));
        if (!URL_PRODUCT_CATEGORY.contains("/" + CompanyId))
            URL_PRODUCT_CATEGORY = URL_PRODUCT_CATEGORY + CompanyId;
//        titles = new ArrayList<>();

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.GET, URL_PRODUCT_CATEGORY, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray result) {
                loader.hideLoader();
                Log.i("result", String.valueOf(result));
                for (int i = 0; i < result.length(); i++) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<OrderParentlist_Model_DistOrder>>() {
                    }.getType();
                    try {
                        Object item = result.get(i);

                        // `instanceof` tells us whether the object can be cast to a specific type
                        if (item instanceof JSONArray) {
//                            titles = gson.fromJson(result.get(i).toString(), type);
//                            Log.i("productCategory", String.valueOf(titles));
                        } else if (item instanceof JSONObject) {
                            OrderParentlist_Model_DistOrder tempModel = gson.fromJson(item.toString(), OrderParentlist_Model_DistOrder.class);
                            Categories.put(tempModel.getTitle(), tempModel.getID());
                            totalCategoryTitle.add(tempModel.getTitle());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                arrayAdapterSpinnerConso.notifyDataSetChanged();

//                Log.i("totalCategory", String.valueOf(totalCategory));
                try {
                    getProductsFromCategory();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());

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
        sr.setRetryPolicy(new

                DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(

                getContext()).

                add(sr);
    }

    private void getFilteredProductCategory(final String ParentId) throws JSONException {
        byDefaultStatus = false;
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("CompanyInfo",
                Context.MODE_PRIVATE);
        CompanyId = sharedPreferences2.getString("CompanyId", "");
        Log.i("CompanyId", CompanyId);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        Log.i("Map", String.valueOf(map));
        if (!URL_PRODUCT_CATEGORY.contains("/" + CompanyId))
            URL_PRODUCT_CATEGORY = URL_PRODUCT_CATEGORY + CompanyId;

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.GET, URL_PRODUCT_CATEGORY, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray result) {
                loader.hideLoader();
                titles = new ArrayList<>();
                Log.i("result", String.valueOf(result));
                for (int i = 0; i < result.length(); i++) {
//                    totalCategory = new ArrayList<>();
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<OrderParentlist_Model_DistOrder>>() {
                    }.getType();
                    try {
                        Object item = result.get(i);

                        // `instanceof` tells us whether the object can be cast to a specific type
                        if (item instanceof JSONArray) {
//                            titles = gson.fromJson(result.get(i).toString(), type);
//                            Log.i("productCategory", String.valueOf(titles));
                            for (int j = 0; j < ((JSONArray) item).length(); j++) {
                                OrderParentlist_Model_DistOrder tempModel = gson.fromJson(((JSONArray) item).get(j).toString(), OrderParentlist_Model_DistOrder.class);
                                if (tempModel.getParentId().equals(ParentId)) {
                                    titles.add(tempModel);
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

//                titles.add(new OrderParentlist_Model_DistOrder("", "", "", "", "", "", ""));
//                titles.add(new OrderParentlist_Model_DistOrder("", "", "", "", "", "", ""));
//                titles.add(new OrderParentlist_Model_DistOrder("", "", "", "", "", "", ""));
//                titles.add(new OrderParentlist_Model_DistOrder("", "", "", "", "", "", ""));
//                titles.add(new OrderParentlist_Model_DistOrder("", "", "", "", "", "", ""));
                temp_titles = titles;

                Log.i("titles", String.valueOf(titles));
                try {
                    getProductsFromCategory();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());

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
        sr.setRetryPolicy(new

                DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(

                getContext()).

                add(sr);
    }

    private void getProductsFromCategory() throws JSONException {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("CompanyInfo",
                Context.MODE_PRIVATE);
        CompanyId = sharedPreferences2.getString("CompanyId", "");

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        Log.i("Map", String.valueOf(map));
        if (!URL_PRODUCT.contains("/" + CompanyId))
            URL_PRODUCT = URL_PRODUCT + CompanyId;

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.GET, URL_PRODUCT, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray result) {
                loader.hideLoader();
                Log.i("resultLength", String.valueOf(result.length()));
                Log.i("result", String.valueOf(result));

                Gson gson = new Gson();
                Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
                }.getType();
                productList = gson.fromJson(String.valueOf(result), type);
                Log.i("productList", String.valueOf(productList));

//                productList.add(new OrderChildlist_Model_DistOrder("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
//                productList.add(new OrderChildlist_Model_DistOrder("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
//                productList.add(new OrderChildlist_Model_DistOrder("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
//                productList.add(new OrderChildlist_Model_DistOrder("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));

                final ParentList_Adapter_DistOrder adapter = new ParentList_Adapter_DistOrder(getActivity(), initData(), spinner_container_main, btn_checkout, productList, Category_selected);
//                adapter.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
//                adapter.setParentClickableViewAnimationDefaultDuration();
                adapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
                    @UiThread
                    @Override
                    public void onParentExpanded(int parentPosition) {

                        if (lastExpandedPosition != -1
                                && parentPosition != lastExpandedPosition) {
                            adapter.collapseParent(lastExpandedPosition);
//                                adapter.OrderParentList.get(lastExpandedPosition).togglePlusMinusIcon();
//                                adapter.OrderParentList.get(parentPosition).togglePlusMinusIcon();
                        }
                        lastExpandedPosition = parentPosition;
                    }

                    @UiThread
                    @Override
                    public void onParentCollapsed(int parentPosition) {
//                            adapter.OrderParentList.get(parentPosition).togglePlusMinusIcon();
                    }
                });
                //adapter.setParentClickableViewAnimationDefaultDuration();
//                    adapter.setParentAndIconExpandOnClick(false);
//                    recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 30));
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());

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
//        new MyAsyncTask().execute();
    }

    private void getFilteredProductsFromCategory(final String Product) throws JSONException {
//        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("CompanyInfo",
                Context.MODE_PRIVATE);
        CompanyId = sharedPreferences2.getString("CompanyId", "");

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        Log.i("Map", String.valueOf(map));
        if (!URL_PRODUCT.contains("/" + CompanyId))
            URL_PRODUCT = URL_PRODUCT + CompanyId;

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.GET, URL_PRODUCT, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray result) {
                loader.hideLoader();
                productList = new ArrayList<>();
                List<OrderParentlist_Model_DistOrder> temp12_titles = titles;
//                temp_titles = titles;
                titles = new ArrayList<>();
                Log.i("resultLength", String.valueOf(result.length()));
                Log.i("result", String.valueOf(result));
                for (int i = 0; i < result.length(); i++) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
                    }.getType();

                    OrderChildlist_Model_DistOrder tempModel = null;

                    try {
                        tempModel = gson.fromJson(((JSONArray) result).get(i).toString(), OrderChildlist_Model_DistOrder.class);
                        if (tempModel.getTitle().toLowerCase().contains(Product.toLowerCase())) {
                            productList.add(tempModel);
//                        boolean found = false;
                            for (int j = 0; j < temp_titles.size(); j++) {
                                if (temp_titles.get(j).getID().equals(tempModel.getCategoryId())) {
//                                Categories.put(tempModel.getTitle(), tempModel.getID());
//                                totalCategoryTitle.add(tempModel.getTitle());
                                    if (!titles.contains(temp_titles.get(j)))
                                        titles.add(temp_titles.get(j));
//                                found = true;
                                }
                            }

//                        if (!found) {
//                            try {
//                                getFilteredProductCategoryForProducts(tempModel.getCategoryId());
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("productList", String.valueOf(productList));
//                Log.i("titles123", String.valueOf(titles));
                final ParentList_Adapter_DistOrder adapter = new ParentList_Adapter_DistOrder(getActivity(), initData(), spinner_container_main, btn_checkout, productList, Category_selected);
//                adapter.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
//                adapter.set .setParentClickableViewAnimationDefaultDuration();
//                adapter.setParentAndIconExpandOnClick(false);
//                adapter.onParentItemClickListener(1);
                adapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
                    @UiThread
                    @Override
                    public void onParentExpanded(int parentPosition) {

                        if (lastExpandedPosition != -1
                                && parentPosition != lastExpandedPosition) {
                            adapter.collapseParent(lastExpandedPosition);
//                                adapter.OrderParentList.get(lastExpandedPosition).togglePlusMinusIcon();
//                                adapter.OrderParentList.get(parentPosition).togglePlusMinusIcon();
                        }
                        lastExpandedPosition = parentPosition;
                    }

                    @UiThread
                    @Override
                    public void onParentCollapsed(int parentPosition) {
//                            adapter.OrderParentList.get(parentPosition).togglePlusMinusIcon();
                    }
                });
                //adapter.setParentClickableViewAnimationDefaultDuration();
//                    adapter.setParentAndIconExpandOnClick(false);
//                    recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 30));
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());

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
//        new MyAsyncTask().execute();
    }
//
//    private void getFilteredProductCategoryForProducts(final String CategoryId) throws JSONException {
//        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Token = sharedPreferences.getString("Login_Token", "");
//        Log.i("Token", Token);
//
//        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
//        Log.i("DistributorId ", DistributorId);
//
//        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("CompanyInfo",
//                Context.MODE_PRIVATE);
//        CompanyId = sharedPreferences2.getString("CompanyId", "");
//        Log.i("CompanyId", CompanyId);
//
//        JSONObject map = new JSONObject();
//        map.put("DistributorId", Integer.parseInt(DistributorId));
//        Log.i("Map", String.valueOf(map));
//        if (!URL_PRODUCT_CATEGORY.contains("/" + CompanyId))
//            URL_PRODUCT_CATEGORY = URL_PRODUCT_CATEGORY + CompanyId;
//
//        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.GET, URL_PRODUCT_CATEGORY, null, new Response.Listener<JSONArray>() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onResponse(JSONArray result) {
//                Log.i("result", String.valueOf(result));
//                for (int i = 0; i < result.length(); i++) {
//                    totalCategory = new ArrayList<>();
//                    Gson gson = new Gson();
//                    Type type = new TypeToken<List<OrderParentlist_Model_DistOrder>>() {
//                    }.getType();
//                    try {
//                        Object item = result.get(i);
//
//                        // `instanceof` tells us whether the object can be cast to a specific type
//                        if (item instanceof JSONArray) {
////                            titles = gson.fromJson(result.get(i).toString(), type);
////                            Log.i("productCategory", String.valueOf(titles));
//                            for (int j = 0; j < ((JSONArray) item).length(); j++) {
//                                OrderParentlist_Model_DistOrder tempModel = gson.fromJson(((JSONArray) item).get(j).toString(), OrderParentlist_Model_DistOrder.class);
//                                if (tempModel.getID().equals(CategoryId)) {
//                                    titles.add(tempModel);
//                                }
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//
//                Log.i("titles", String.valueOf(titles));
////                try {
////                    getProductsFromCategory();
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                printErrorMessage(error);
//
//                error.printStackTrace();
//            }
//        }) {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + Token);
//                params.put("Content-Type", "application/json; charset=UTF-8");
//                return params;
//            }
//        };
//        sr.setRetryPolicy(new
//
//                DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(
//
//                getContext()).
//
//                add(sr);
//    }

//    private List<OrderParentlist_Model_DistOrder> initData() {
//        List<OrderParentlist_Model_DistOrder> parentObjects = new ArrayList<>();
//        for (OrderParentlist_Model_DistOrder title : titles) {
//            Log.i("title", String.valueOf(title.getTitle()));
//            List<Object> childlist = new ArrayList<>();
////            childlist.add(new OrderChildlist_Model());
//            for (OrderChildlist_Model_DistOrder product : productList) {
//                Log.i("product", String.valueOf(product.getCategoryTitle()));
//                if (title.getTitle().equals(product.getCategoryTitle())) {
//                    Log.i("productAdded", product.getTitle());
//                    childlist.add(product);
//                }
//            }
//            title.setChildList(childlist);
//            parentObjects.add(title);
//        }
//        return parentObjects;
//    }


//    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... params) {
//            while (getContext() != null) {
////                Log.i("productsAsync", "in loop");
////                Log.i("productsAsync", String.valueOf(selectedProductsDataList));
//                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
//                        Context.MODE_PRIVATE);
//                object_string = selectedProducts.getString("selected_products", "");
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
//                }.getType();
//                temp_list = gson.fromJson(object_string, type);
//                object_stringqty = selectedProducts.getString("selected_products_qty", "");
//                Type typestr = new TypeToken<List<String>>() {
//                }.getType();
//                temp_listqty = gson.fromJson(object_stringqty, typestr);
//                if (!object_string.equals("")) {
//                    if (selectedProductsDataList != null) {
//                        if (temp_list != selectedProductsDataList) {
//                            selectedProductsDataList = temp_list;
//                            selectedProductsQuantityList = temp_listqty;
//                            break;
//                        }
//                    }
//                    break;
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            if (getContext() != null)
//                enableCheckout();
//        }
//    }

    private void printErrMessage(VolleyError error) {
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


    private List<OrderParentlist_Model_DistOrder> initData() {
        List<OrderParentlist_Model_DistOrder> parentObjects = new ArrayList<>();
        for (OrderParentlist_Model_DistOrder title : titles) {
            Log.i("title", String.valueOf(title.getTitle()));
            List<Object> childlist = new ArrayList<>();
//            childlist.add(new OrderChildlist_Model());
            for (OrderChildlist_Model_DistOrder product : productList) {
                Log.i("product", String.valueOf(product.getCategoryId()));
                if (title.getTitle().equals(product.getCategoryTitle())) {
                    childlist.add(product);
                }
            }
            title.setChildList(childlist);
            parentObjects.add(title);
        }
        return parentObjects;
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (!isVisibleToUser) {
//            if (myAsyncTask != null && myAsyncTask.getStatus() == AsyncTask.Status.RUNNING)
//                myAsyncTask.cancel(true);
//
//            if (myAsyncTask != null && myAsyncTask.getStatus() == AsyncTask.Status.RUNNING)
//                myAsyncTask.cancel(true);
//        }
//    }

//    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... params) {
//            while (getContext() != null && !isCancelled()) {
////                Log.i("productsAsync", "in loop");
////                Log.i("productsAsync", String.valueOf(selectedProductsDataList));
//                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
//                        Context.MODE_PRIVATE);
//                object_string = selectedProducts.getString("selected_products", "");
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
//                }.getType();
//                temp_list = gson.fromJson(object_string, type);
//                object_stringqty = selectedProducts.getString("selected_products_qty", "");
//                Type typestr = new TypeToken<List<String>>() {
//                }.getType();
//                temp_listqty = gson.fromJson(object_stringqty, typestr);
//                if (!object_string.equals("")) {
//                    if (selectedProductsDataList != null) {
//                        if (temp_list != selectedProductsDataList) {
//                            selectedProductsDataList = temp_list;
//                            selectedProductsQuantityList = temp_listqty;
//                            break;
//                        }
//                    }
//                    break;
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            if (getContext() != null)
//                enableCheckout();
//        }
//    }

    // private void printErrMessage(VolleyError error) {
    //     if (getContext() != null) {
    //         if (error instanceof NetworkError) {
    //             Toast.makeText(getContext(), "Network Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof ServerError) {
    //             Toast.makeText(getContext(), "Server Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof AuthFailureError) {
    //             Toast.makeText(getContext(), "Auth Failure Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof ParseError) {
    //             Toast.makeText(getContext(), "Parse Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof NoConnectionError) {
    //             Toast.makeText(getContext(), "No Connection Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof TimeoutError) {
    //             Toast.makeText(getContext(), "Timeout Error !", Toast.LENGTH_LONG).show();
    //         }

    //         if (error.networkResponse != null && error.networkResponse.data != null) {
    //             try {
    //                 String message = "";
    //                 String responseBody = new String(error.networkResponse.data, "utf-8");
    //                 Log.i("responseBody", responseBody);
    //                 JSONObject data = new JSONObject(responseBody);
    //                 Log.i("data", String.valueOf(data));
    //                 Iterator<String> keys = data.keys();
    //                 while (keys.hasNext()) {
    //                     String key = keys.next();
    //                     message = message + data.get(key) + "\n";
    //                 }
    //                 Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    //             } catch (UnsupportedEncodingException e) {
    //                 e.printStackTrace();
    //             } catch (JSONException e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }

    // }

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
//
//    private void fetchDashboardData() {
////        loader.showLoader();
//        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Token = sharedPreferences.getString("Login_Token", "");
//
//        StringRequest sr = new StringRequest(Request.Method.POST, URL_DISTRIBUTOR_DASHBOARD, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String result) {
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    DecimalFormat formatter3 = new DecimalFormat("#,###,###,##0.00");
//                     current_balance = formatter3.format(Double.parseDouble(jsonObject.get("TotalDistributorBalance").toString()));
//                    SharedPreferences tabsFromDraft = getContext().getSharedPreferences("currentBalance",
//                            Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
//                    editorOrderTabsFromDraft.putString("current_balance" , String.valueOf(current_balance));
//                    editorOrderTabsFromDraft.apply();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                new HaballError().printErrorMessage(getContext(), error);
//                new ProcessingError().showError(getContext());
//                loader.hideLoader();
//
//                error.printStackTrace();
//            }
//        }) {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + Token);
//                return params;
//            }
//        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(getContext()).add(sr);
//    }
}
