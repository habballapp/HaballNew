package com.example.haball.Distributor.ui.support;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResolveTicket {
    private String ID;
    private String URL_RESOLVE_TICKET = "http://175.107.203.97:4008/api/useralert/DismissAlert/";

    public ResolveTicket() {}

    protected void RequestResolveTicket(String id, Context context, final String Token) {

        URL_RESOLVE_TICKET = URL_RESOLVE_TICKET+id;
        Log.i("NOTIFICATION_DISMISS", URL_RESOLVE_TICKET);

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_RESOLVE_TICKET, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.i("DISMISSED ", result.toString());

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
                return params;
            }
        };
        Volley.newRequestQueue(context).add(sr);
    }
}
