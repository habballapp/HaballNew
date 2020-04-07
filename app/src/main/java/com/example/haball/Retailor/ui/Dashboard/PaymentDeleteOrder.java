package com.example.haball.Retailor.ui.Dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

class PaymentDeleteOrder {

    public String URL_DELETE_PAYMENT = "http://175.107.203.97:4014/api/prepaidrequests/Delete/";
    public Context context;
    public String invoiceNumber;
    public String RetailerId,Token;



    public PaymentDeleteOrder() {
    }


    public void deleteOrder(final Context context, String invoiceId, final String invoiceNumber) {
        Log.i("paymentLog", "in delete order" );
        Log.i("paymentLog_Error", String.valueOf( invoiceId ) );


        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        RetailerId = sharedPreferences1.getString("Retailer_Id", "");
        if(!URL_DELETE_PAYMENT.contains( "/" + invoiceId ))
            URL_DELETE_PAYMENT = URL_DELETE_PAYMENT + invoiceId;
        Log.i("RetailerId ", RetailerId);
        Log.i("Token Retailer ", Token);


        final Context finalcontext = context;
        JsonObjectRequest request = new JsonObjectRequest( Request.Method.DELETE, URL_DELETE_PAYMENT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // TODO handle the response
                Log.i("paymentLog_Response", String.valueOf( response ) );

                Toast.makeText(context, "Payment # " + invoiceNumber + " deleted.", Toast.LENGTH_LONG).show();

//                fragmentTransaction = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.main_container, new HomeFragment());
//                fragmentTransaction.commit();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("paymentLog_Error", String.valueOf( error ) );
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
