package com.example.haball.Distributor.ui.orders.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haball.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrdersItemsAdapter extends RecyclerView.Adapter<OrdersItemsAdapter.ViewHolder> {
    @NonNull
    @Override
    public OrdersItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersItemsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_count,txt_products,unit_price_value,discount_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_count = itemView.findViewById(R.id.txt_count);
            txt_products = itemView.findViewById(R.id.txt_products);
            unit_price_value = itemView.findViewById(R.id.unit_price_value);
            discount_price = itemView.findViewById(R.id.discount_price);


        }
    }
}
