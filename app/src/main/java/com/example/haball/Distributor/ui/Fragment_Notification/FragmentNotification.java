package com.example.haball.Distributor.ui.Fragment_Notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Payment.ConsolidatePaymentsModel;
import com.example.haball.R;
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

public class FragmentNotification extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter NotificationAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<NotificationModel> notificationLists = new ArrayList<>();
    private String Token, DistributorId, ID;
    private String URL_NOTIFICATION = "http://175.107.203.97:4008/api/useralert/ShowAll/";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_notification, container, false);

        recyclerView = root.findViewById(R.id.rv_notification);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

        fetchNotification();

//

        return root;
    }

    private void fetchNotification() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        ID = sharedPreferences1.getString("ID", "");

        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        URL_NOTIFICATION = URL_NOTIFICATION + ID;
        Log.i("URL_NOTIFICATION", URL_NOTIFICATION);

        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_NOTIFICATION, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                Log.i("RESULT NOTIFICATION", result.toString());
                Gson gson = new Gson();
                Type type = new TypeToken<List<NotificationModel>>() {
                }.getType();
                notificationLists = gson.fromJson(result.toString(), type);
                NotificationAdapter = new NotificationAdapter(getContext(), notificationLists, Token);
                recyclerView.setAdapter(NotificationAdapter);
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
