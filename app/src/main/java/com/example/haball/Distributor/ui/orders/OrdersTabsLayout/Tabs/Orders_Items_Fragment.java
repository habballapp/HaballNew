package com.example.haball.Distributor.ui.orders.OrdersTabsLayout.Tabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.ui.orders.Adapter.OrdersItemsAdapter;
import com.example.haball.Distributor.ui.orders.Models.OrderItemsModel;
import com.example.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.example.haball.Payment.ConsolidatePaymentsModel;
import com.example.haball.Payment.Consolidate_Fragment_Adapter;
import com.example.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Orders_Items_Fragment extends Fragment {

    private RecyclerView itemsSelect_Rv;
    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.LayoutManager layoutManager1;
    private Button place_item_button;
    String CompanyId = "2";
    private String PRODUCTS_URL = "http://175.107.203.97:4008/api/products/ReadProductsByCategories/";
    private String PRODUCTS_CATEGORY_URL = "http://175.107.203.97:4008/api/products/ReadCategories/0/";

    private String Token, DistributorId;
    private List<OrderItemsModel> ProductsDataList = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         final View view = inflater.inflate(R.layout.orders_items_fragments, container, false);
      //  View view1 = inflater.inflate(R.layout.fragment_orders__dashboard, container, false);
                //init
              place_item_button = (Button) view.findViewById(R.id.place_item_button);
               place_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager  viewPager = getActivity().findViewById(R.id.view_pager5);
                Toast.makeText(getContext(),"Clicked", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(1);
                FragmentTransaction fragmentTransaction= ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container,new Order_Summary());
                fragmentTransaction.commit();
            }
        });
        holderitems(view);

        return view;
    }

    private void fetchProductsData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("CompanyId",
                Context.MODE_PRIVATE);
        CompanyId = sharedPreferences2.getString("CompanyId", "");

        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);
            PRODUCTS_CATEGORY_URL = PRODUCTS_CATEGORY_URL + CompanyId;
        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.GET, PRODUCTS_CATEGORY_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                Log.i("CATEGORY DATA .. ", result.toString());

                for(int i = 0; i < result.length(); i++) {
//                    try {
//                        Log.i("category items", String.valueOf(result.get(i)));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                    try {
                        JSONObject obj = result.getJSONObject(i);
                        PRODUCTS_URL = PRODUCTS_URL + obj.get("ID") + "/" + CompanyId ;
                        Log.i("PRODUCTS_URL", PRODUCTS_URL);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    MyJsonArrayRequest sr1 = new MyJsonArrayRequest(Request.Method.GET, PRODUCTS_URL, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray resultProduct) {
                            Log.i("PRODUCTS DATA .. ", resultProduct.toString());
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<OrderItemsModel>>(){}.getType();
                            ProductsDataList.addAll((Collection<? extends OrderItemsModel>) gson.fromJson(resultProduct.toString(),type));
                            mAdapter1 = new OrdersItemsAdapter(getContext(),ProductsDataList);
                            itemsSelect_Rv.setAdapter(mAdapter1);


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
                            return params;
                        }
                    };
                    Volley.newRequestQueue(getContext()).add(sr1);



                    PRODUCTS_URL = "http://175.107.203.97:4008/api/products/ReadProductsByCategories/";


                }


//                mAdapter = new Consolidate_Fragment_Adapter(getContext(),ConsolidatePaymentsRequestList);
//                recyclerView.setAdapter(mAdapter);
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
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void holderitems(final View root) {
        Log.i("abbasi" ,"abccccccccccccccccc");
        itemsSelect_Rv =(RecyclerView) root.findViewById(R.id.rv_items_orders);

        itemsSelect_Rv.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(getContext());
        itemsSelect_Rv.setLayoutManager(layoutManager1);

        /* ****************************** */
        /* Smooth Scroll in Recycler View */
        itemsSelect_Rv.setNestedScrollingEnabled(false);
        /* ****************************** */

        fetchProductsData();


        Log.i("placeHolder12" , String.valueOf(mAdapter1));

    }

    private void printErrorMessage(VolleyError error) {
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
//    private void Holderorders(final View view){
//
//           mPager.setCurrentItem(1);
//
//
//
//
//
//       /* create_payment = root.findViewById(R.id.place_order_button);
//        create_payment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPager.setCurrentItem(1); // Change to page 1, i.e., FragmentB
//            }
//        });*/
//
//    }}
