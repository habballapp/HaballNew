package com.example.haball.Distributor.ui.orders.OrdersTabsNew.Tabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.example.haball.Distributor.ui.orders.OrdersTabsNew.Adapters.ParentList_Adapter_DistOrder;
import com.example.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderChildlist_Model_DistOrder;
import com.example.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderParentlist_Model_DistOrder;
import com.example.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.example.haball.NonSwipeableViewPager;
import com.example.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.cachapa.expandablelayout.ExpandableLayout;

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
public class Dist_OrderPlace extends Fragment {

    RecyclerView recyclerView, subchlid_RV;
    private List<OrderParentlist_Model_DistOrder> titles = new ArrayList<>();
    private List<OrderChildlist_Model_DistOrder> productList = new ArrayList<>();
    private List<ParentObject> parentObjects = new ArrayList<>();
    private String URL_PRODUCT_CATEGORY = "http://175.107.203.97:4013/api/products/ReadCategories/0/";
    private String URL_PRODUCT = "http://175.107.203.97:4013/api/products/ReadProductsByCategories/0/";
    private String Token, DistributorId;
    private String object_string, object_stringqty;
    private List<OrderChildlist_Model_DistOrder> selectedProductsDataList = new ArrayList<>();
    private List<OrderChildlist_Model_DistOrder> temp_list = new ArrayList<>();
    private List<String> temp_listqty = new ArrayList<>();
    private List<String> selectedProductsQuantityList = new ArrayList<>();
    private float grossAmount = 0;
    private Button btn_checkout;
    private String CompanyId;
    public Dist_OrderPlace() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dist_main_placeorder, container, false);
        btn_checkout = view.findViewById(R.id.btn_checkout);
        recyclerView = view.findViewById(R.id.rv_order_list);
        recyclerView.setNestedScrollingEnabled(false);
//        subchlid_RV = view.findViewById(R.id.subchlid_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        subchlid_RV.setLayoutManager(new LinearLayoutManager(getActivity()));
//        ParentListAdapter adapter = new ParentListAdapter(getActivity(), (List<ParentObject>) initData());
//        adapter.setParentClickableViewAnimationDefaultDuration();
//        adapter.setParentAndIconExpandOnClick(true);
//        recyclerView.setAdapter(adapter);

        try {
            getProductCategory();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;

    }

    private boolean enableCheckout() {
        Log.i("checkout", "in checkout");
        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        object_string = selectedProducts.getString("selected_products", "");
        Log.i("object_string", object_string);
        Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
        }.getType();
        if (!object_string.equals(""))
            selectedProductsDataList = gson.fromJson(object_string, type);
        if (selectedProductsDataList != null) {
            if (selectedProductsDataList.size() > 0) {
                btn_checkout.setBackgroundResource(R.drawable.button_round);
                btn_checkout.setEnabled(true);
                btn_checkout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                        if (selectedProductsDataList.size() > 0) {
                            for (int i = 0; i < selectedProductsDataList.size(); i++) {
                                Log.i("unit price", selectedProductsDataList.get(i).getUnitPrice());
                                Log.i("qty", selectedProductsQuantityList.get(i));
                                if (!selectedProductsDataList.get(i).getUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
                                    grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice()) * Float.parseFloat(selectedProductsQuantityList.get(i));
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
                            fragmentTransaction.add(R.id.main_container, new Dist_Order_Summary());
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

    private void getProductCategory() throws JSONException {
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
                Log.i("result", String.valueOf(result));

                Gson gson = new Gson();
                Type type = new TypeToken<List<OrderParentlist_Model_DistOrder>>() {
                }.getType();
                try {
                    titles = gson.fromJson(result.get(4).toString(), type);
                    Log.i("productCategory", String.valueOf(titles));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    getProductsFromCategory();
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
                Log.i("resultLength", String.valueOf(result.length()));
                Log.i("result", String.valueOf(result));

                Gson gson = new Gson();
                Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
                }.getType();
                productList = gson.fromJson(String.valueOf(result), type);
                Log.i("productList", String.valueOf(productList));

                ParentList_Adapter_DistOrder adapter = new ParentList_Adapter_DistOrder(getActivity(), initData());
//                adapter.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
//                adapter.setParentClickableViewAnimationDefaultDuration();
//                adapter.setParentAndIconExpandOnClick(true);
                recyclerView.setAdapter(adapter);
//                ParentListAdapter adapter = new ParentListAdapter(getActivity(), initData());
//                adapter.setParentClickableViewAnimationDefaultDuration();
//                adapter.setParentAndIconExpandOnClick(true);
//                recyclerView.setAdapter(adapter);
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

    private List<ParentObject> initData() {
        List<ParentObject> parentObjects = new ArrayList<>();
        for (OrderParentlist_Model_DistOrder title : titles) {
            Log.i("title", String.valueOf(title.getTitle()));
            List<Object> childlist = new ArrayList<>();
//            childlist.add(new OrderChildlist_Model());
            for (OrderChildlist_Model_DistOrder product : productList) {
                Log.i("product", String.valueOf(product.getCategoryTitle()));
                if (title.getTitle().equals(product.getCategoryTitle())) {
                    Log.i("productAdded", product.getTitle());
                    childlist.add(product);
                }
            }
            title.setChildObjectList(childlist);
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
                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
                        Context.MODE_PRIVATE);
                object_string = selectedProducts.getString("selected_products", "");
                Gson gson = new Gson();
                Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
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
        }
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
