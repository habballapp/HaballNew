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

import java.util.List;

public class SupportDashboardAdapter extends RecyclerView.Adapter<SupportDashboardAdapter.ViewHolder> {

    Context mContxt;
    Context activity;
    String dashboard, id, pending, createdDate;
    List<SupportDashboardModel> supportList;

    public SupportDashboardAdapter(Activity activity, Context applicationContext, String dashboard, String id, String pending, String createdDate) {
//        this.mContxt = applicationContext;
//        this.activity = activity;
//        this.dashboard = dashboard;
//        this.id = id;
//        this.pending = pending;
//        this.createdDate = createdDate;
    }

    public SupportDashboardAdapter(Context context, List<SupportDashboardModel> supportList) {
        this.mContxt = context;
        this.supportList = supportList;
    }

    @Override
    public SupportDashboardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContxt).inflate(R.layout.layout_support_rv,parent,false);
        return new SupportDashboardAdapter.ViewHolder(view_inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull final SupportDashboardAdapter.ViewHolder holder, final int position) {
        holder.heading.setText(supportList.get(position).getIssueType());
        holder.ticket_id_value.setText(supportList.get(position).Id);
        holder.status_value.setText(supportList.get(position).getStatus());
        holder.created_date_value.setText(supportList.get(position).getCreatedDate().split("T")[0]);

        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContxt, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_items, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_view:

                                TextView tv_username, et_email, et_phone, et_issue_type, et_criticality, et_preffered_contact, et_status, et_comments;
                                Toast.makeText(mContxt,"View Clicked",Toast.LENGTH_LONG).show();
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContxt);
                                LayoutInflater inflater = LayoutInflater.from(mContxt);
                                View view_popup = inflater.inflate(R.layout.view_popup, null);
                                alertDialog.setView(view_popup);

                                tv_username = view_popup.findViewById(R.id.tv_username);
                                et_email = view_popup.findViewById(R.id.et_email);
                                et_phone = view_popup.findViewById(R.id.et_phone);
                                et_issue_type = view_popup.findViewById(R.id.et_issue_type);
                                et_criticality = view_popup.findViewById(R.id.et_criticality);
                                et_preffered_contact = view_popup.findViewById(R.id.et_preffered_contact);
                                et_status = view_popup.findViewById(R.id.et_status);
                                et_comments = view_popup.findViewById(R.id.et_comments);

                                tv_username.setText(supportList.get(position).getContactName());
                                et_email.setText("Email Address: "+supportList.get(position).getEmail());
                                et_phone.setText("Phone: "+supportList.get(position).getMobileNumber());
                                et_issue_type.setText("Issue Type: "+supportList.get(position).getIssueType());
                                et_criticality.setText("Criticality: "+supportList.get(position).getCriticality());
                                et_preffered_contact.setText("Preferred Contact Method: "+supportList.get(position).getPreferredContactMethod());
                                et_status.setText("Status: "+supportList.get(position).getStatus());
                                et_comments.setText("Message: "+supportList.get(position).getDescription());

                                alertDialog.show();
                                break;
                            case R.id.menu_edit:
                                //handle menu2 click
                                Toast.makeText(mContxt,"Edit Clicked",Toast.LENGTH_LONG).show();

                                break;
                            case R.id.menu_delete:
                                //handle menu3 click
                                Toast.makeText(mContxt,"Delete Clicked",Toast.LENGTH_LONG).show();
                                final AlertDialog deleteAlert = new AlertDialog.Builder(mContxt).create();
                                LayoutInflater delete_inflater = LayoutInflater.from(mContxt);
                                View delete_alert = delete_inflater.inflate(R.layout.delete_alert, null);
                                deleteAlert.setView(delete_alert);
                                Button btn_delete = (Button) delete_alert.findViewById(R.id.btn_delete);
                                btn_delete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        deleteAlert.dismiss();
                                        AlertDialog.Builder delete_successAlert = new AlertDialog.Builder(mContxt);
                                        LayoutInflater delete_inflater = LayoutInflater.from(mContxt);
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
        return supportList.size();
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
