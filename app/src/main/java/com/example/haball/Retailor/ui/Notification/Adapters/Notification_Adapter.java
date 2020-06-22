package com.example.haball.Retailor.ui.Notification.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.ui.Fragment_Notification.Dismiss_Notification;
import com.example.haball.Distributor.ui.Fragment_Notification.NotificationAdapter;
import com.example.haball.R;
import com.example.haball.Registration.BooleanRequest;
import com.example.haball.Retailor.ui.Notification.Retailer_Notification_Model;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.ViewHolder> {

    private Context context;
    private  String subject,notification_txt;
    private List<Retailer_Notification_Model> NotificationList = new ArrayList<>();
    private String dismiss_alert = "https://retailer.haball.pk/api/useralert/DismissAlert/";

    public Notification_Adapter(Context context, List<Retailer_Notification_Model> notificationList) {
        this.context = context;
//        this.subject = subject;
//        this.notification_txt = notification_txt;
        NotificationList = notificationList;
    }

    public Notification_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.notification_retailor_recycler,parent,false);
        return new Notification_Adapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Notification_Adapter.ViewHolder holder, final int position) {

        holder.subject.setText(NotificationList.get(position).getSubject());
        holder.notification_message.setText(NotificationList.get(position).getAlertMessage());
        holder.rl_payments_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Details", Toast.LENGTH_SHORT).show();
            }
        });

        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final PopupMenu popup = new PopupMenu(context, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.notification_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.dismiss:
                                Log.i("DISMISS CASE", "HERE");

                                if (dismiss_alert.contains("/" + NotificationList.get(position).getID()))
                                    dismiss_alert = dismiss_alert + NotificationList.get(position).getID();

                                SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                                        Context.MODE_PRIVATE);
                                final String Token = sharedPreferences.getString("Login_Token", "");


                                BooleanRequest sr = new BooleanRequest(Request.Method.POST, dismiss_alert, null, new Response.Listener<Boolean>() {
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
                                Volley.newRequestQueue(context).add(sr);

//                                Toast.makeText(context, "Notification Dismissed", Toast.LENGTH_LONG).show();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return NotificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView notification_message, subject;
        public ImageButton menu_btn;
        public RelativeLayout rl_payments_notification;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notification_message = itemView.findViewById(R.id.notification_message);
            subject = itemView.findViewById(R.id.subject);
            menu_btn = itemView.findViewById(R.id.menu_btn);
            rl_payments_notification = itemView.findViewById(R.id.rl_payments_notification);
        }
    }
}
