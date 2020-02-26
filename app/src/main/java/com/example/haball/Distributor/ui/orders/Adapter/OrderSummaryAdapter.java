package com.example.haball.Distributor.ui.orders.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haball.Distributor.ui.orders.Models.OrderItemsModel;
import com.example.haball.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.ViewHolder> {

    private Context context;
    private String txt_count;
    private String txt_products;
    private String price_summary_value;
    private String discount_summary_price;
    private String Rs_summary_value;
    private List<OrderItemsModel> selectedProductsDataList;
    private List<String> selectedProductsDataListQty;

    public OrderSummaryAdapter(Context context, List<OrderItemsModel> selectedProductsDataList, List<String> selectedProductsDataListQty) {
        this.context = context;
        this.selectedProductsDataList = selectedProductsDataList;
        this.selectedProductsDataListQty = selectedProductsDataListQty;
    }

    public OrderSummaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.orders_summary_recycler,parent,false);
        return new OrderSummaryAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSummaryAdapter.ViewHolder holder, int position) {

        holder.show_quantity.setText(selectedProductsDataListQty.get(position));
        holder.txt_count.setText(selectedProductsDataList.get(position).getCode());
        holder.txt_products.setText("| "+selectedProductsDataList.get(position).getTitle());
        holder.price_summary_value.setText(selectedProductsDataList.get(position).getUnitPrice());
        holder.discount_summary_price.setText(selectedProductsDataList.get(position).getDiscountAmount());
        holder.Rs_summary_value.setText("test value to be changed");

    }

    @Override
    public int getItemCount() {
        return selectedProductsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_count,txt_products,price_summary_value,discount_summary_price,Rs_summary_value,show_quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_count = itemView.findViewById(R.id.txt_count);
            txt_products = itemView.findViewById(R.id.txt_products);
            price_summary_value = itemView.findViewById(R.id.price_summary_value);
            discount_summary_price = itemView.findViewById(R.id. discount_summary_price);
            Rs_summary_value =  itemView.findViewById(R.id.Rs_summary_value);
            show_quantity =  itemView.findViewById(R.id.show_quantity);
        }
    }
}
