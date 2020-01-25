package com.example.haball.Distributor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haball.Distributor.ui.main.PlaceholderFragment;
import com.example.haball.R;

public class DistributorPaymentsAdapter extends RecyclerView.Adapter<DistributorPaymentsAdapter.ViewHolder> {
    private PlaceholderFragment mContxt;
    private String heading, paymentid, amount, status;
    public DistributorPaymentsAdapter(PlaceholderFragment placeholderFragment, String heading, String paymentid, String amount, String status) {
        this.mContxt = placeholderFragment;
        this.heading = heading;
        this.paymentid = paymentid;
        this.amount = amount;
        this.status = status;
    }

    @NonNull
    @Override
    public DistributorPaymentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContxt.getContext()).inflate(R.layout.payments_layout,parent,false);
        return new DistributorPaymentsAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DistributorPaymentsAdapter.ViewHolder holder, int position) {
        holder.tv_heading.setText(heading);
        holder.tv_payment_id.setText(paymentid);
        holder.tv_status.setText(status);
        holder.tv_amount.setText(amount);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_heading, tv_payment_id, tv_status, tv_amount;
        public ImageButton menu_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            tv_payment_id = itemView.findViewById(R.id.payment_id_value);
            tv_status = itemView.findViewById(R.id.status_value);
            tv_amount = itemView.findViewById(R.id.amount_value);
            menu_btn = itemView.findViewById(R.id.menu_btn);
        }
    }
}
