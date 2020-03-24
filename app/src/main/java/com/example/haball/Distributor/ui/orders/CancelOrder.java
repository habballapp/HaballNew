package com.example.haball.Distributor.ui.orders;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.haball.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CancelOrder {
    public String URL_CANCEL_ORDER = "http://175.107.203.97:4013/api/orders/cancelorder";
    public String DistributorId, Token;
    public Context mContext;
    private FragmentTransaction fragmentTransaction;

    public CancelOrder() {
    }

    public void cancelOrder(final Context context, String orderId, final String orderNumber) throws JSONException {
        mContext = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        JSONObject map = new JSONObject();
        map.put("ID", orderId);

        final Context finalcontext = context;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_CANCEL_ORDER, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // TODO handle the response
                Toast.makeText(context, "Order # " + orderNumber + " is cancelled", Toast.LENGTH_LONG).show();
                fragmentTransaction = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new HomeFragment());
                fragmentTransaction.commit();

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
