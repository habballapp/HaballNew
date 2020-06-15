package com.example.haball.Retailor.ui.Notification;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haball.Distributor.ui.Fragment_Notification.NotificationAdapter;
import com.example.haball.R;
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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notification_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter NotificationAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Socket iSocket;
    private static final String URL = "http://175.107.203.97:4014";
    private String UserId, Token;
    private List<Retailer_Notification_Model> NotificationList = new ArrayList<>();
    private TextView tv_notification_no_data;

    public Notification_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //     return inflater.inflate(R.layout.fragment_blank, container, false);
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
//        NotificationAdapter = new Notification_Adapter(getContext(), "Payment", "Payment ID 345697977970 has been approved successfully");
//        recyclerView.setAdapter(NotificationAdapter);
        try {
            IO.Options opts = new IO.Options();
//            opts.query = "userId=" + UserId;
            iSocket = IO.socket(URL, opts);
            iSocket.connect();
            if (iSocket.connected()) {
                iSocket.emit("userId", UserId);
                iSocket.on("userId" + UserId, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        JSONObject data = (JSONObject) args[0];
//                    Toast.makeText(getContext(), data.toString(), Toast.LENGTH_SHORT).show();
//                    Log.i("notificationTest", String.valueOf(data));
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Retailer_Notification_Model>>() {
                        }.getType();
                        try {
                            NotificationList = gson.fromJson(String.valueOf(data.getJSONArray("data")), type);

                            setNotificationStatus(NotificationList.size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("notificationTest12", String.valueOf(NotificationList));
                        NotificationAdapter = new Notification_Adapter(getContext(), NotificationList);
                        recyclerView.setAdapter(NotificationAdapter);
                    }
                });
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return root;
    }

    private void setNotificationStatus(final int count) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (count != 0) {
                    tv_notification_no_data.setVisibility(View.GONE);
                } else {
                    tv_notification_no_data.setVisibility(View.VISIBLE);
                }
            }
        });

    }

}
