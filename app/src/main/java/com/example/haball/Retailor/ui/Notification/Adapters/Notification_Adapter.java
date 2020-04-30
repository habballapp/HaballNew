package com.example.haball.Retailor.ui.Notification.Adapters;

import android.content.Context;
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

import com.example.haball.Distributor.ui.Fragment_Notification.Dismiss_Notification;
import com.example.haball.Distributor.ui.Fragment_Notification.NotificationAdapter;
import com.example.haball.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.ViewHolder> {

    private Context context;
    private  String subject,notification_txt;

    public Notification_Adapter(Context context, String subject, String notification_txt) {
        this.context = context;
        this.subject = subject;
        this.notification_txt = notification_txt;
    }

    public Notification_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.notification_retailor_recycler,parent,false);
        return new Notification_Adapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Notification_Adapter.ViewHolder holder, int position) {

        holder.subject.setText(subject);
        holder.notification_message.setText(notification_txt);
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
        return 2;
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
