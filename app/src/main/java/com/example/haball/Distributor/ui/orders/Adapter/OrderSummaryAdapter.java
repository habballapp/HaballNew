package com.example.haball.Distributor.ui.orders.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haball.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.ViewHolder> {

    private Context context;
    public String txt_count;
    public String txt_products;
    public String price_summary_value;
    public String discount_summary_price;
    public String Rs_summary_value;

    public OrderSummaryAdapter(Context context, String txt_count, String txt_products, String price_summary_value, String discount_summary_price, String rs_summary_value) {
        this.context = context;
        this.txt_count = txt_count;
        this.txt_products = txt_products;
        this.price_summary_value = price_summary_value;
        this.discount_summary_price = discount_summary_price;
        Rs_summary_value = rs_summary_value;
    }

    public OrderSummaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.orders_summary_recycler,parent,false);
        return new OrderSummaryAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSummaryAdapter.ViewHolder holder, int position) {

        holder.txt_count.setText(txt_count);
        holder.txt_products.setText(txt_products);
        holder.price_summary_value.setText(price_summary_value);
        holder.discount_summary_price.setText(discount_summary_price);
        holder.Rs_summary_value.setText(Rs_summary_value);

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_count,txt_products,price_summary_value,discount_summary_price,Rs_summary_value;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_count = itemView.findViewById(R.id.txt_count);
            txt_products = itemView.findViewById(R.id.txt_products);
            price_summary_value = itemView.findViewById(R.id.price_summary_value);
            discount_summary_price = itemView.findViewById(R.id. discount_summary_price);
            Rs_summary_value =  itemView.findViewById(R.id.Rs_summary_value);
        }
    }
}
