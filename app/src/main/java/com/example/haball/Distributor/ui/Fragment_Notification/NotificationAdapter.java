package com.example.haball.Distributor.ui.Fragment_Notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haball.Distribution_Login.Distribution_Login;
import com.example.haball.Distributor.ui.payments.PaymentRequestDashboard;
import com.example.haball.Distributor.ui.payments.Payments_Fragment;
import com.example.haball.Distributor.ui.payments.ProofOfPaymentForm;
import com.example.haball.Distributor.ui.shipments.DistributorShipment_ViewDashboard;
import com.example.haball.Distributor.ui.shipments.Shipments_Fragments;
import com.example.haball.Payment.Consolidate_Fragment;
import com.example.haball.Payment.Consolidate_Fragment_View_Payment;
import com.example.haball.R;
import com.example.haball.Registration.Registration_Activity;
import com.example.haball.Retailor.ui.Dashboard.DashBoardFragment;

import java.text.DecimalFormat;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context context;
    private List<NotificationModel> notificationLists;
    private String token;

    public NotificationAdapter(Context context, List<NotificationModel> notificationLists, String token) {
        this.context = context;
        this.notificationLists = notificationLists;
        this.token = token;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.notifications_recycler,parent,false);
        return new NotificationAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.notification_message.setText(notificationLists.get(position).getAlertMessage());
        holder.subject.setText(notificationLists.get(position).getSubject());
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

                                Dismiss_Notification dismiss_notification = new Dismiss_Notification();
                                dismiss_notification.requestDismissNotification(notificationLists.get(position).getID(),context, token);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, notificationLists.size());
                                Toast.makeText(context, "Notification Dismissed", Toast.LENGTH_LONG).show();
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
        return notificationLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView notification_message, subject;
        public ImageButton menu_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notification_message = itemView.findViewById(R.id.notification_message);
            subject = itemView.findViewById(R.id.subject);
            menu_btn = itemView.findViewById(R.id.menu_btn);
        }
    }
}