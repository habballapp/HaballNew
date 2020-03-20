package com.example.haball.Distributor.ui.retailer.Retailor_Management.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haball.Distributor.DistributorInvoicesAdapter;
import com.example.haball.Distributor.ui.retailer.Retailor_Management.Model.Retailer_Management_Dashboard_Model;
import com.example.haball.Distributor.ui.retailer.Retailor_Management.ViewRetailer;
import com.example.haball.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class Retailer_Management_Dashboard_Adapter extends RecyclerView.Adapter<Retailer_Management_Dashboard_Adapter.ViewHolder> {

    private Context mContext;
    private String heading,retailer_code_no,tv_retailerdate_date_no,retailer_status_value;
    private List<Retailer_Management_Dashboard_Model> retailerList;

    public Retailer_Management_Dashboard_Adapter(Context mContext, List<Retailer_Management_Dashboard_Model> retailerList) {
        this.mContext = mContext;
        this.retailerList = retailerList;
//        this.heading = heading;
//        this.retailer_code_no = retailer_code_no;
//        this.tv_retailerdate_date_no = tv_retailerdate_date_no;
//        this.retailer_status_value = retailer_status_value;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view_inflate = LayoutInflater.from(mContext).inflate(R.layout.retailer_mangement_dashboard_recycler,parent,false);
        return new Retailer_Management_Dashboard_Adapter.ViewHolder(view_inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_heading.setText(retailerList.get(position).getCompanyName());
        holder.retailer_code_no.setText(retailerList.get(position).getRetailerCode());
        if(retailerList.get(position).getCreatedDate() != null)
        holder.tv_retailerdate_date_no.setText(retailerList.get(position).getCreatedDate().split("T")[0]);
        holder.retailer_status_value.setText(retailerList.get(position).getStatus());
        final int finalPosition = position;
        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"View Retailer",Toast.LENGTH_LONG).show();
                final androidx.appcompat.widget.PopupMenu popup = new PopupMenu(mContext, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.fragment_view_management_retailer_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){

                            case R.id.view_retailer:
//                                Toast.makeText(mContext,"Popup",Toast.LENGTH_LONG).show();
                                ViewRetailer viewRetailer = new ViewRetailer();
                                Bundle args = new Bundle();
                                args.putString("RetailerId", retailerList.get(finalPosition).getID());
                                viewRetailer.setArguments(args);


                                FragmentTransaction fragmentTransaction= ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.add(R.id.main_container,viewRetailer);
                                fragmentTransaction.commit();
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
        return retailerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_heading,retailer_code_no,tv_retailerdate_date_no,retailer_status_value;
        public ImageButton menu_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_heading = itemView.findViewById(R.id.retailer_heading_dashboard);
            retailer_code_no = itemView.findViewById(R.id.retailer_code_no);
            tv_retailerdate_date_no = itemView.findViewById(R.id.tv_retailerdate_date_no);
            retailer_status_value = itemView.findViewById(R.id.retailer_status_value);
            menu_btn = itemView.findViewById(R.id.mgretailer_menu_btn);

        }
    }
}
