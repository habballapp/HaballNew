package com.example.haball.Distributor.ui.orders;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.ui.home.HomeFragment;
import com.example.haball.Distributor.ui.orders.OrdersTabsNew.Tabs.OrderSummaryDraft;
import com.example.haball.NonSwipeableViewPager;
import com.example.haball.R;
import com.example.haball.Registration.BooleanRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditOrderDraft {
    public String URL_EDIT_ORDER_DRAFT = "http://175.107.203.97:4013/api/Orders/";
    public String DistributorId, Token;
    public Context mContext;


    public EditOrderDraft() {
    }

    public void editDraft(final Context context, String orderId, final String orderNumber) {
        mContext = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        if (!URL_EDIT_ORDER_DRAFT.contains(orderId))
            URL_EDIT_ORDER_DRAFT = URL_EDIT_ORDER_DRAFT + orderId;

//        final Context finalcontext = context;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_EDIT_ORDER_DRAFT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // TODO handle the response
                Log.i("responseDraft", String.valueOf(response));

                try {
                    JSONArray arr = response.getJSONArray("OrderDetails");

//                    Log.i("jsonOrderDetail1", String.valueOf(arr));
//                    Gson gson = new Gson();
//                    String json = null;
//                    json = gson.toJson(arr);
//                    Log.i("jsonOrderDetail", json);
                    SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_distributor_draft",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = selectedProducts.edit();
                    editor.putString("selected_products", String.valueOf(arr));
                    editor.apply();

                    FragmentTransaction fragmentTransaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.main_container, new OrderSummaryDraft());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
        RequestQueue mRequestQueue = Volley.newRequestQueue(context, new HurlStack());
        mRequestQueue.add(request);


    }
}
