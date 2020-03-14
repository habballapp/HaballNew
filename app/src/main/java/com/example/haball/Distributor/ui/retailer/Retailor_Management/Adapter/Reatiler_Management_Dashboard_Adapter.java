package com.example.haball.Distributor.ui.retailer.Retailor_Management.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haball.Distributor.DistributorInvoicesAdapter;
import com.example.haball.Distributor.ui.retailer.Retailor_Management.Model.Retailer_Management_Dashboard_Model;
import com.example.haball.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class Reatiler_Management_Dashboard_Adapter extends RecyclerView.Adapter<Reatiler_Management_Dashboard_Adapter.ViewHolder> {

    private Context mContext;
    private String heading,retailer_code_no,tv_retailerdate_date_no,retailer_status_value;

    public Reatiler_Management_Dashboard_Adapter(String heading, String retailer_code_no, String tv_retailerdate_date_no, String retailer_status_value) {
        this.mContext = mContext;
        this.heading = heading;
        this.retailer_code_no = retailer_code_no;
        this.tv_retailerdate_date_no = tv_retailerdate_date_no;
        this.retailer_status_value = retailer_status_value;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view_inflate = LayoutInflater.from(mContext).inflate(R.layout.retailer_mangement_dashboard_recycler,parent,false);
        return new Reatiler_Management_Dashboard_Adapter.ViewHolder(view_inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_heading.setText(heading);
        holder.retailer_code_no.setText(retailer_code_no);
        holder.tv_retailerdate_date_no.setText(retailer_code_no);
        holder.retailer_status_value.setText(retailer_status_value);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_heading,retailer_code_no,tv_retailerdate_date_no,retailer_status_value;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_heading = itemView.findViewById(R.id.retailer_heading_dashboard);
            retailer_code_no = itemView.findViewById(R.id.retailer_code_no);
            retailer_status_value = itemView.findViewById(R.id.retailer_status_value);

        }
    }
}
