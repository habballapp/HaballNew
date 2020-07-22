package com.haball.Distributor.ui.Fragment_Notification;

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
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Loader;
import com.haball.Payment.ConsolidatePaymentsModel;
import com.haball.R;
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
import java.util.Timer;
import java.util.TimerTask;

public class FragmentNotification extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter NotificationAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<NotificationModel> notificationLists = new ArrayList<>();
    private String Token, DistributorId, ID;
    static int counter;
    //private String URL_NOTIFICATION = "http://175.107.203.97:4013/api/useralert/ShowAll/";
    private Loader loader;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_notification, container, false);
        loader = new Loader(getContext());

        recyclerView = root.findViewById(R.id.rv_notification);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

        fetchNotificationForItemCount();


//
        return root;
    }

    private void fetchNotification(final int resultLenght) {
        loader.showLoader();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        ID = sharedPreferences1.getString("ID", "");
        String URL_NOTIFICATION = "http://175.107.203.97:4013/api/useralert/ShowAll/";
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        if (!URL_NOTIFICATION.contains("/" + ID))
            URL_NOTIFICATION = URL_NOTIFICATION + ID;
        Log.i("URL_NOTIFICATION", URL_NOTIFICATION);

        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_NOTIFICATION, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                loader.hideLoader();

                if (resultLenght == result.length()) {

                }
                Log.i("RESULT NOTIFICATION", result.toString());
                Gson gson = new Gson();
                Type type = new TypeToken<List<NotificationModel>>() {
                }.getType();
                notificationLists = gson.fromJson(result.toString(), type);
                NotificationAdapter = new NotificationAdapter(getContext(), notificationLists, Token);
                recyclerView.setAdapter(NotificationAdapter);
                setNotificationStatus(DistributorId, ID);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
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
        sr.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 1000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void setNotificationStatus(String DistributorId, String ID) {
        loader.showLoader();
        String URL_NOTIFICATION_SEEN = "http://175.107.203.97:4013/api/useralert/MarkSeen/";
        if (!URL_NOTIFICATION_SEEN.contains("/" + ID))
            URL_NOTIFICATION_SEEN = URL_NOTIFICATION_SEEN + ID;
        Log.i("URL_NOTIFICATION", URL_NOTIFICATION_SEEN);

        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_NOTIFICATION_SEEN, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                loader.hideLoader();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
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
        sr.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 1000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void fetchNotificationForItemCount() {
        loader.showLoader();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        ID = sharedPreferences1.getString("ID", "");
        String URL_NOTIFICATION = "http://175.107.203.97:4013/api/useralert/ShowAll/";
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        URL_NOTIFICATION = URL_NOTIFICATION + ID;
        Log.i("URL_NOTIFICATION", URL_NOTIFICATION);

        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_NOTIFICATION, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(final JSONArray result) {
                loader.hideLoader();

                FragmentNotification.counter = result.length();

                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        fetchNotification(result.length());
                    }
                }, 0, 5000);
                Log.i("ResultLength", "" + result.length());
                Log.i("RESULT NOTIFICATION", result.toString());

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
        sr.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 1000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        Volley.newRequestQueue(getContext()).add(sr);
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
}
