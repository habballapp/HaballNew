package com.example.haball.Distributor.ui.orders.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haball.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class OrdersItemsAdapter extends RecyclerView.Adapter<OrdersItemsAdapter.ViewHolder> {

    private Context context;
    public String txt_count,txt_products,unit_price_value,discount_price;

    public OrdersItemsAdapter(Context context, String txt_count, String txt_products, String unit_price_value, String discount_price) {
        this.context = context;
        this.txt_count = txt_count;
        this.txt_products = txt_products;
        this.unit_price_value = unit_price_value;
        this.discount_price = discount_price;
    }

    public OrdersItemsAdapter(Context context, String txt_count, String txt_products, String unit_price_value, String discount_price, ViewPager mPager) {
        this.context = context;
        this.txt_count = txt_count;
        this.txt_products = txt_products;
        this.unit_price_value = unit_price_value;
        this.discount_price = discount_price;
         }

    public OrdersItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.orders_items_recycler,parent,false);
        return new OrdersItemsAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersItemsAdapter.ViewHolder holder, int position) {
        holder.txt_count.setText(txt_count);
        holder.txt_products.setText(txt_products);
        holder.unit_price_value.setText(unit_price_value);
        holder.discount_price.setText(discount_price);
    }
    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_count,txt_products,unit_price_value,discount_price;
        public Button checkout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_count = itemView.findViewById(R.id.txt_count);
            txt_products = itemView.findViewById(R.id.txt_products);
            unit_price_value = itemView.findViewById(R.id.unit_price_value);
            discount_price = itemView.findViewById(R.id.discount_price);


        }
    }
}
