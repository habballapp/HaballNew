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

public class DistributorOrdersAdapter extends RecyclerView.Adapter<DistributorOrdersAdapter.ViewHolder> {

    private PlaceholderFragment mContxt;
    private String heading, order_no_value, amount, status;

    public DistributorOrdersAdapter(PlaceholderFragment placeholderFragment, String heading, String order_no_value, String amount, String status) {
        this.mContxt = placeholderFragment;
        this.heading = heading;
        this.order_no_value = order_no_value;
        this.amount = amount;
        this.status = status;
    }

    @NonNull
    @Override
    public DistributorOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContxt.getContext()).inflate(R.layout.orders_layout,parent,false);
        return new DistributorOrdersAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DistributorOrdersAdapter.ViewHolder holder, int position) {
        holder.tv_heading.setText(heading);
        holder.order_no_value.setText(order_no_value);
        holder.tv_status.setText(status);
        holder.tv_amount.setText(amount);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_heading, order_no_value, tv_status, tv_amount;
        public ImageButton menu_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            order_no_value = itemView.findViewById(R.id.order_no_value);
            tv_status = itemView.findViewById(R.id.status_value);
            tv_amount = itemView.findViewById(R.id.amount_value);
            menu_btn = itemView.findViewById(R.id.menu_btn);
        }
    }
}
