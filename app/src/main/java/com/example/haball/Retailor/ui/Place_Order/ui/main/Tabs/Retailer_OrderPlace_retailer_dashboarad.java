package com.example.haball.Retailor.ui.Place_Order.ui.main.Tabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.bignerdranch.expandablerecyclerview.model.SimpleParent;
import com.example.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.example.haball.NonSwipeableViewPager;
import com.example.haball.R;
import com.example.haball.Retailor.ui.Place_Order.ui.main.Adapters.ParentListAdapter;
import com.example.haball.Retailor.ui.Place_Order.ui.main.Models.OrderChildlist_Model;
import com.example.haball.Retailor.ui.Place_Order.ui.main.Models.OrderParentlist_Model;
import com.google.android.material.textfield.TextInputEditText;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Retailer_OrderPlace_retailer_dashboarad extends Fragment {
    RecyclerView recyclerView, subchlid_RV;
    private List<OrderParentlist_Model> titles = new ArrayList<>();
    private List<OrderChildlist_Model> productList = new ArrayList<>();
    private List<SimpleParent> parentObjects = new ArrayList<>();
    private String URL_PRODUCT_CATEGORY = "http://175.107.203.97:4014/api/products/GetProductByDealerCode/";
    private String URL_PRODUCT = "http://175.107.203.97:4014/api/products/GetProductByDealerCode/";
    private String Token, Retailer_Id, CompanyId;
    private String object_string, object_stringqty;
    private List<OrderChildlist_Model> selectedProductsDataList = new ArrayList<>();
    private List<OrderChildlist_Model> temp_list = new ArrayList<>();
    private List<String> temp_listqty = new ArrayList<>();
    private List<String> selectedProductsQuantityList = new ArrayList<>();
    private float grossAmount = 0;
    private Button btn_checkout, btn_close;
    private Spinner spinner_conso;
    private List<String> totalCategoryTitle = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterSpinnerConso;
    private String Category_selected;
    private HashMap<String, String> Categories = new HashMap<>();
    private TextInputEditText et_test;
    List<OrderParentlist_Model> temp_titles = new ArrayList<>();
    private static int y;
    private List<String> scrollEvent = new ArrayList<>();
    private RelativeLayout spinner_container_main;

    public Retailer_OrderPlace_retailer_dashboarad() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_place_retailer_dashboarad, container, false);
        btn_checkout = view.findViewById(R.id.btn_checkout);
        btn_close = view.findViewById(R.id.close_button);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        recyclerView = view.findViewById(R.id.rv_order_list);
        spinner_container_main = view.findViewById(R.id.spinner_container_main);
//        subchlid_RV = view.findViewById(R.id.subchlid_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        subchlid_RV.setLayoutManager(new LinearLayoutManager(getActivity()));
//        ParentListAdapter adapter = new ParentListAdapter(getActivity(), (List<ParentObject>) initData());
//        adapter.setParentClickableViewAnimationDefaultDuration();
//        adapter.setParentAndIconExpandOnClick(true);
//        recyclerView.setAdapter(adapter);
        spinner_conso = view.findViewById(R.id.spinner_conso);
        et_test = view.findViewById(R.id.et_test);
//        totalCategoryTitle.add("Select All");
        arrayAdapterSpinnerConso = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_dropdown_item, totalCategoryTitle);

        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = selectedProducts.edit();
        editor.putString("selected_products", "");
        editor.putString("selected_products_qty", "");
        editor.apply();


        arrayAdapterSpinnerConso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_conso.setAdapter(arrayAdapterSpinnerConso);
        spinner_conso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category_selected = totalCategoryTitle.get(position);
//                try {
//                    Log.i("Categoriesselected", Categories.get(Category_selected) + " - " + Category_selected);
//                    getFilteredProductCategory(Categories.get(Category_selected));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        et_test.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                titles = new ArrayList<>();
                if (!String.valueOf(s).equals("")) {
                    Log.i("titles123", "in if");
//                    try {
//                        getFilteredProductsFromCategory(String.valueOf(s));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                } else {
                    Log.i("titles123", "in else");
                    try {
                        getProductCategory();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

//                int visibleItemCount = layoutManager.getChildCount();
//                int totalItemCount = layoutManager.getItemCount();
//                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
//                    if (totalPages != 0 && pageNumber < totalPages) {
////                                Toast.makeText(getContext(), pageNumber + " - " + totalPages, Toast.LENGTH_LONG).show();
//                        btn_load_more.setVisibility(View.VISIBLE);
//                    }
            }

        });
        try {
            getProductCategory();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;

    }


    private boolean enableCheckout() {
        Log.i("checkout", "in checkout");
        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        object_stringqty = selectedProducts.getString("selected_products_qty", "");
        object_string = selectedProducts.getString("selected_products", "");
        Type type = new TypeToken<List<OrderChildlist_Model>>() {
        }.getType();
        Type typeString = new TypeToken<List<String>>() {
        }.getType();
        if (!object_string.equals("") && !object_stringqty.equals("")) {
            selectedProductsDataList = gson.fromJson(object_string, type);
            selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
        }
        float totalQty = 0;
        if (selectedProductsDataList != null) {
            if (selectedProductsDataList.size() > 0) {
                for (int i = 0; i < selectedProductsDataList.size(); i++) {
                    Log.i("unit price", selectedProductsDataList.get(i).getProductUnitPrice());
                    Log.i("qty", selectedProductsQuantityList.get(i));
                    if (!selectedProductsDataList.get(i).getProductUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
                        if (Float.parseFloat(selectedProductsQuantityList.get(i)) > 0) {
                            totalQty = totalQty + Float.parseFloat(selectedProductsQuantityList.get(i));
                        }
                }
            }
        }
        Log.i("totalQty", "here");
        Log.i("totalQty", String.valueOf(totalQty));
        if (totalQty > 0) {
            btn_checkout.setEnabled(true);
            btn_checkout.setBackgroundResource(R.drawable.button_round);
        } else {
            btn_checkout.setEnabled(false);
            btn_checkout.setBackgroundResource(R.drawable.button_grey_round);
        }
        new MyAsyncTask().execute();

//            selectedProductsDataList = gson.fromJson(object_string, type);
        if (selectedProductsDataList != null) {
            if (selectedProductsDataList.size() > 0) {
//                btn_checkout.setBackgroundResource(R.drawable.button_round);
//                btn_checkout.setEnabled(true);
                btn_checkout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NonSwipeableViewPager viewPager = getActivity().findViewById(R.id.view_pager_rpoid);
                        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
                                Context.MODE_PRIVATE);
                        Gson gson = new Gson();
                        object_stringqty = selectedProducts.getString("selected_products_qty", "");
                        object_string = selectedProducts.getString("selected_products", "");
                        Type type = new TypeToken<List<OrderChildlist_Model>>() {
                        }.getType();
                        Type typeString = new TypeToken<List<String>>() {
                        }.getType();
                        selectedProductsDataList = gson.fromJson(object_string, type);
                        selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
                        if (selectedProductsDataList.size() > 0) {
                            for (int i = 0; i < selectedProductsDataList.size(); i++) {
                                Log.i("unit price", selectedProductsDataList.get(i).getProductUnitPrice());
                                Log.i("qty", selectedProductsQuantityList.get(i));
                                if (!selectedProductsDataList.get(i).getProductUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
                                    grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getProductUnitPrice()) * Float.parseFloat(selectedProductsQuantityList.get(i));
                            }

                            SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = grossamount.edit();
                            editor.putString("grossamount", String.valueOf(grossAmount));
                            editor.apply();
                            Toast.makeText(getContext(), "Total Amount: " + grossAmount, Toast.LENGTH_SHORT).show();
                            grossAmount = 0;
                            viewPager.setCurrentItem(1);
                            FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.main_container_ret, new Retailer_Order_Summary());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    }
                });
                return true;
            }
        }
        return false;
    }


//    private void getFilteredProductsFromCategory(final String Product) throws JSONException {
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
//
//        JSONObject map = new JSONObject();
//        map.put("DistributorId", Integer.parseInt(DistributorId));
//        Log.i("Map", String.valueOf(map));
//        if (!URL_PRODUCT.contains("/" + CompanyId))
//            URL_PRODUCT = URL_PRODUCT + CompanyId;
//
//        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.GET, URL_PRODUCT, null, new Response.Listener<JSONArray>() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onResponse(JSONArray result) {
//                productList = new ArrayList<>();
//                List<OrderParentlist_Model> temp12_titles = titles;
////                temp_titles = titles;
//                titles = new ArrayList<>();
//                Log.i("temp_titles", String.valueOf(temp_titles));
//                Log.i("resultLength", String.valueOf(result.length()));
//                Log.i("result", String.valueOf(result));
//                for (int i = 0; i < result.length(); i++) {
//                    Gson gson = new Gson();
//                    Type type = new TypeToken<List<OrderChildlist_Model>>() {
//                    }.getType();
//
//                    OrderChildlist_Model tempModel = null;
//
//                    try {
//                        tempModel = gson.fromJson(((JSONArray) result).get(i).toString(), OrderChildlist_Model.class);
//                        if (tempModel.getTitle().toLowerCase().contains(Product.toLowerCase())) {
//                            productList.add(tempModel);
////                        boolean found = false;
//                            for (int j = 0; j < temp_titles.size(); j++) {
//                                if (temp_titles.get(j).getCategoryId().equals(tempModel.getProductCategoryId())) {
////                                Categories.put(tempModel.getTitle(), tempModel.getID());
////                                totalCategoryTitle.add(tempModel.getTitle());
//                                    if (!titles.contains(temp_titles.get(j)))
//                                        titles.add(temp_titles.get(j));
//                                    Log.i("titles", String.valueOf(titles));
////                                found = true;
//                                }
//                            }
//
////                        if (!found) {
////                            try {
////                                getFilteredProductCategoryForProducts(tempModel.getCategoryId());
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
////                        }
//
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                Log.i("productList", String.valueOf(productList));
////                Log.i("titles123", String.valueOf(titles));
//                ParentListAdapter adapter = new ParentListAdapter(getActivity(), initData());
////                adapter.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
//                adapter.setParentClickableViewAnimationDefaultDuration();
//                adapter.setParentAndIconExpandOnClick(true);
//                recyclerView.setAdapter(adapter);
//
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
//        sr.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(getContext()).add(sr);
//        new MyAsyncTask().execute();
//    }

    private void getFilteredProductCategory(final String ParentId) throws JSONException {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Retailer_Id = sharedPreferences1.getString("Retailer_Id", "");
        Log.i("Retailer_Id ", Retailer_Id);

        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("CompanyInfo",
                Context.MODE_PRIVATE);
        CompanyId = sharedPreferences2.getString("CompanyId", "");
        Log.i("CompanyId", CompanyId);


//        JSONObject map = new JSONObject();
//        map.put("RetailerId", Integer.parseInt(Retailer_Id));
//        Log.i("Map", String.valueOf(map));
        if (!URL_PRODUCT_CATEGORY.contains("/" + CompanyId))
            URL_PRODUCT_CATEGORY = URL_PRODUCT_CATEGORY + CompanyId;

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, URL_PRODUCT_CATEGORY, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject resultMain) {
                JSONArray result = null;
                try {
                    result = resultMain.getJSONArray("SubCategory");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                titles = new ArrayList<>();
                Log.i("result", String.valueOf(result));
                for (int i = 0; i < result.length(); i++) {
//                    totalCategory = new ArrayList<>();
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<OrderParentlist_Model>>() {
                    }.getType();
                    try {
                        Object item = result.get(i);

                        // `instanceof` tells us whether the object can be cast to a specific type
                        if (item instanceof JSONArray) {
//                            titles = gson.fromJson(result.get(i).toString(), type);
//                            Log.i("productCategory", String.valueOf(titles));
                            for (int j = 0; j < ((JSONArray) item).length(); j++) {
                                OrderParentlist_Model tempModel = gson.fromJson(((JSONArray) item).get(j).toString(), OrderParentlist_Model.class);
                                if (tempModel.getParentId().equals(ParentId)) {
                                    titles.add(tempModel);
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                temp_titles = titles;

                Log.i("titles", String.valueOf(titles));

                try {
                    Gson gsonChild = new Gson();
                    Type typeChild = new TypeToken<List<OrderChildlist_Model>>() {
                    }.getType();
                    productList = gsonChild.fromJson(String.valueOf(resultMain.get("Products")), typeChild);
                    Log.i("productList", String.valueOf(productList));

                    ParentListAdapter adapter = new ParentListAdapter(getActivity(), initData());
                    //adapter.setParentClickableViewAnimationDefaultDuration();
                    //adapter.setParentAndIconExpandOnClick(false);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
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

    boolean bool = true;

    private void getProductCategory() throws JSONException {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);

//        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Retailer_Id = sharedPreferences1.getString("Retailer_Id", "");
//        Log.i("Retailer_Id ", DistributorId);

        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("DealerInfo",
                Context.MODE_PRIVATE);
        CompanyId = sharedPreferences2.getString("DealerCode", "");
        Log.i("DealerCode", CompanyId);


//        JSONObject map = new JSONObject();
//        map.put("DistributorId", Integer.parseInt(DistributorId));
//        Log.i("Map", String.valueOf(map));
        if (!URL_PRODUCT_CATEGORY.contains("/" + CompanyId))
            URL_PRODUCT_CATEGORY = URL_PRODUCT_CATEGORY + CompanyId;

//        Log.i("Map", String.valueOf(map));

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, URL_PRODUCT_CATEGORY, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject resultMain) {
                JSONArray resultFilter = null;
                JSONArray result = null;
                JSONArray resultProduct = null;
                try {
                    resultFilter = resultMain.getJSONArray("MainCategory");
                    result = resultMain.getJSONArray("SubCategory");
                    resultProduct = resultMain.getJSONArray("Products");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                titles = new ArrayList<>();
                Log.i("result", String.valueOf(result));
                Gson gson = new Gson();
                Type type = new TypeToken<List<OrderParentlist_Model>>() {
                }.getType();
                try {
                    for (int j = 0; j < ((JSONArray) result).length(); j++) {
                        OrderParentlist_Model tempModel = gson.fromJson(((JSONArray) result).get(j).toString(), OrderParentlist_Model.class);
                        int countOfProduct = 0;
                        for (int k = 0; k < ((JSONArray) resultProduct).length(); k++) {
                            OrderChildlist_Model tempModelProduct = gson.fromJson(((JSONArray) resultProduct).get(k).toString(), OrderChildlist_Model.class);
                            if (tempModel.getCategoryId().equals(tempModelProduct.getProductCategoryId()))
                                countOfProduct++;
                        }

                        if (countOfProduct > 0)
                            titles.add(tempModel);
                    }

                    for (int j = 0; j < ((JSONArray) resultFilter).length(); j++) {
                        OrderParentlist_Model tempModel = gson.fromJson(((JSONArray) resultFilter).get(j).toString(), OrderParentlist_Model.class);
                        int countOfProduct = 0;
                        for (int k = 0; k < titles.size(); k++) {
                            OrderParentlist_Model tempModelProduct = titles.get(k);
//                            Log.i("tempModelProduct", tempModel.getCategoryId() + " - " + tempModelProduct.getParentId());
                            if (tempModel.getCategoryId().equals(tempModelProduct.getParentId())) {
//                                Log.i("tempModelProduct", "found: " + tempModel.getCategoryId() + " - " + tempModelProduct.getParentId());
                                countOfProduct++;
                            }
                        }

                        if (countOfProduct > 0) {
                            Categories.put(tempModel.getTitle(), tempModel.getCategoryId());
                            totalCategoryTitle.add(tempModel.getTitle());
                        }
                    }
                    Log.i("totalCategoryTitle", String.valueOf(totalCategoryTitle));
                    arrayAdapterSpinnerConso.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                temp_titles = titles;

                Log.i("titles", String.valueOf(titles));

                try {
                    Gson gsonChild = new Gson();
                    Type typeChild = new TypeToken<List<OrderChildlist_Model>>() {
                    }.getType();
                    productList = gsonChild.fromJson(String.valueOf(resultMain.get("Products")), typeChild);
                    Log.i("productList", String.valueOf(productList));

                    ParentListAdapter adapter = new ParentListAdapter(getActivity(), initData());
                    //adapter.setParentClickableViewAnimationDefaultDuration();
                    //adapter.setParentAndIconExpandOnClick(false);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
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
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);

        new MyAsyncTask().execute();
    }
//
//    private void getProductsFromCategory() throws JSONException {
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
//
//        JSONObject map = new JSONObject();
//        map.put("DistributorId", Integer.parseInt(DistributorId));
//        Log.i("Map", String.valueOf(map));
//
//
//        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.GET, URL_PRODUCT, null, new Response.Listener<JSONArray>() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onResponse(JSONArray result) {
//                Log.i("result", String.valueOf(result));
//
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<OrderChildlist_Model>>() {
//                }.getType();
//                productList = gson.fromJson(String.valueOf(result), type);
//                Log.i("productList", String.valueOf(productList));
//
//                ParentListAdapter adapter = new ParentListAdapter(getActivity(), initData());
//                //adapter.setParentClickableViewAnimationDefaultDuration();
//                //adapter.setParentAndIconExpandOnClick(false);
//                recyclerView.setAdapter(adapter);
////
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
//        sr.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(getContext()).add(sr);
//        new MyAsyncTask().execute();
//    }

    private List<OrderParentlist_Model> initData() {
        List<OrderParentlist_Model> parentObjects = new ArrayList<>();
        for (OrderParentlist_Model title : titles) {
            Log.i("title", String.valueOf(title.getCategoryId()));
            List<Object> childlist = new ArrayList<>();
//            childlist.add(new OrderChildlist_Model());
            for (OrderChildlist_Model product : productList) {
                Log.i("product", String.valueOf(product.getProductCategoryId()));
                if (title.getCategoryId().equals(product.getProductCategoryId()))
                    childlist.add(product);
            }
            title.setChildList(childlist);
            parentObjects.add(title);
        }
        return parentObjects;
    }


    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            while (getContext() != null) {
//                Log.i("productsAsync", "in loop");
//                Log.i("productsAsync", String.valueOf(selectedProductsDataList));
                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
                        Context.MODE_PRIVATE);
                object_string = selectedProducts.getString("selected_products", "");
                Gson gson = new Gson();
                Type type = new TypeToken<List<OrderChildlist_Model>>() {
                }.getType();
                temp_list = gson.fromJson(object_string, type);
                object_stringqty = selectedProducts.getString("selected_products_qty", "");
                Type typestr = new TypeToken<List<String>>() {
                }.getType();
                temp_listqty = gson.fromJson(object_stringqty, typestr);
                if (!object_string.equals("")) {
                    if (selectedProductsDataList != null) {
                        if (temp_list != selectedProductsDataList) {
                            selectedProductsDataList = temp_list;
                            selectedProductsQuantityList = temp_listqty;
                            break;
                        }
                    }
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (getContext() != null)
                enableCheckout();
//            mAdapter1 = new OrdersItemsAdapter(getContext(), ProductsDataList);
//            itemsSelect_Rv.setAdapter(mAdapter1);

//            ParentListAdapter adapter = new ParentListAdapter(getActivity(), initData());
//            adapter.setParentClickableViewAnimationDefaultDuration();
//            adapter.setParentAndIconExpandOnClick(true);
//            recyclerView.setAdapter(adapter);

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


