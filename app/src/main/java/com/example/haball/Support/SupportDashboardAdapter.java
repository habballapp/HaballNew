package com.example.haball.Support;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haball.R;

public class SupportDashboardAdapter extends RecyclerView.Adapter<SupportDashboardAdapter.ViewHolder> {

    Context mContxt;
    Context activity;
    String dashboard, id, pending, createdDate;

    public SupportDashboardAdapter(Activity activity, Context applicationContext, String dashboard, String id, String pending, String createdDate) {
        this.mContxt = applicationContext;
        this.activity = activity;
        this.dashboard = dashboard;
        this.id = id;
        this.pending = pending;
        this.createdDate = createdDate;
    }

    @Override
    public SupportDashboardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContxt).inflate(R.layout.layout_support_rv,parent,false);
        return new SupportDashboardAdapter.ViewHolder(view_inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull final SupportDashboardAdapter.ViewHolder holder, int position) {
        holder.heading.setText(dashboard);
        holder.ticket_id_value.setText(id);
        holder.status_value.setText(pending);
        holder.created_date_value.setText(createdDate);
        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(activity, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_items, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_view:
                                //handle menu1 click
                                Toast.makeText(mContxt,"View Clicked",Toast.LENGTH_LONG).show();
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                                LayoutInflater inflater = LayoutInflater.from(activity);
                                View view_popup = inflater.inflate(R.layout.view_popup, null);
                                alertDialog.setView(view_popup);
                                alertDialog.show();
                                break;
                            case R.id.menu_edit:
                                //handle menu2 click
                                Toast.makeText(mContxt,"Edit Clicked",Toast.LENGTH_LONG).show();

                                break;
                            case R.id.menu_delete:
                                //handle menu3 click
                                Toast.makeText(mContxt,"Delete Clicked",Toast.LENGTH_LONG).show();
                                final AlertDialog deleteAlert = new AlertDialog.Builder(activity).create();
                                LayoutInflater delete_inflater = LayoutInflater.from(activity);
                                View delete_alert = delete_inflater.inflate(R.layout.delete_alert, null);
                                deleteAlert.setView(delete_alert);
                                Button btn_delete = (Button) delete_alert.findViewById(R.id.btn_delete);
                                btn_delete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        deleteAlert.dismiss();
                                        AlertDialog.Builder delete_successAlert = new AlertDialog.Builder(activity);
                                        LayoutInflater delete_inflater = LayoutInflater.from(activity);
                                        View delete_success_alert = delete_inflater.inflate(R.layout.delete_success, null);
                                        delete_successAlert.setView(delete_success_alert);
                                        delete_successAlert.show();
                                    }
                                });
                                deleteAlert.show();
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
        return 1;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
        public TextView heading, ticket_id_value, status_value, created_date_value;
        public ImageButton menu_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.heading);
            ticket_id_value = itemView.findViewById(R.id.ticket_id_value);
            status_value = itemView.findViewById(R.id.status_value);
            created_date_value = itemView.findViewById(R.id.created_date_value);
            menu_btn = itemView.findViewById(R.id.menu_btn);
        }
    }
}
