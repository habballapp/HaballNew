package com.example.haball.Retailor.ui.Notification;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.ui.Fragment_Notification.NotificationAdapter;
import com.example.haball.Loader;
import com.example.haball.R;
import com.example.haball.Registration.BooleanRequest;
import com.example.haball.Retailer_Login.RetailerLogin;
import com.example.haball.Retailor.Retailer_New_Password;
import com.example.haball.Retailor.RetailorDashboard;
import com.example.haball.Retailor.ui.Notification.Adapters.Notification_Adapter;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.socketio.client.IO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notification_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter NotificationAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Socket iSocket;
    private static final String URL = "https://retailer.haball.pk";
    private String URL_Mark_Seen = "https://retailer.haball.pk/api/useralert/MarkSeen";
    private String UserId, Token;
    private List<Retailer_Notification_Model> NotificationList = new ArrayList<>();
    private TextView tv_notification_no_data;
    private Loader loader;
    private FragmentTransaction fragmentTransaction;

    public Notification_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //     return inflater.inflate(R.layout.fragment_blank, container, false);
        IO.Options opts = new IO.Options();
//            opts.query = "userId=" + UserId;
        try {
            iSocket = IO.socket(URL, opts);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        loader = new Loader(getContext());
        View root = inflater.inflate(R.layout.notification_fragment, container, false);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        UserId = sharedPreferences.getString("UserId", "");
        Token = sharedPreferences.getString("Login_Token", "");
        tv_notification_no_data = root.findViewById(R.id.tv_notification_no_data);
        tv_notification_no_data.setVisibility(View.VISIBLE);
        recyclerView = root.findViewById(R.id.rv_notification_retailor);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
        tv_notification_no_data.setVisibility(View.GONE);
//        NotificationAdapter = new Notification_Adapter(getContext(), "Payment", "Payment ID 345697977970 has been approved successfully");
//        recyclerView.setAdapter(NotificationAdapter);

        getNotifications();
        try {
            markSeenApi();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
    }

    private void markSeenApi() throws JSONException {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");


        BooleanRequest sr = new BooleanRequest(Request.Method.PUT, URL_Mark_Seen, null, new Response.Listener<Boolean>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Boolean result) {
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
                params.put("Content-Type", "application/json");

                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private List<Retailer_Notification_Model> getNotifications() {
        loader.showLoader();
        List<Retailer_Notification_Model> temp_NotificationList = new ArrayList<>();

//        if (iSocket.connected()) {
            iSocket.emit("userId", UserId);
            iSocket.on("userId" + UserId, new Emitter.Listener() {
                @Override
                public void call(final Object... args) {


                    if (getActivity() != null) {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject data = (JSONObject) args[0];
                                //                    Toast.makeText(getContext(), data.toString(), Toast.LENGTH_SHORT).show();
                                Gson gson = new Gson();
                                Type type = new TypeToken<List<Retailer_Notification_Model>>() {
                                }.getType();
                                try {
                                    loader.hideLoader();
                                    NotificationList = gson.fromJson(String.valueOf(data.getJSONArray("data")), type);

                                    if (NotificationList.size() != 0) {
                                        tv_notification_no_data.setVisibility(View.GONE);
                                    } else {
                                        tv_notification_no_data.setVisibility(View.VISIBLE);
                                    }
                                } catch (
                                        JSONException e) {
                                    e.printStackTrace();
                                    loader.hideLoader();
                                }
                                Log.i("notificationTest12", String.valueOf(NotificationList));
                                NotificationAdapter = new Notification_Adapter(getContext(), NotificationList);
                                recyclerView.setAdapter(NotificationAdapter);


                            }
                        });
                    }
                }
            });
//        }
        iSocket.connect();

        return temp_NotificationList;
    }

    //
    private void setNotificationStatus(final int count) {
//        if (getActivity() != null) {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
        if (count != 0) {
            tv_notification_no_data.setVisibility(View.GONE);
        } else {
            tv_notification_no_data.setVisibility(View.VISIBLE);
        }
//                }
//            });
//        }
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

                    SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                    editorOrderTabsFromDraft.putString("TabNo", "0");
                    editorOrderTabsFromDraft.apply();

                    Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                    ((FragmentActivity) getContext()).startActivity(login_intent);
                    ((FragmentActivity) getContext()).finish();
                }
                return false;
            }
        });

    }


}
