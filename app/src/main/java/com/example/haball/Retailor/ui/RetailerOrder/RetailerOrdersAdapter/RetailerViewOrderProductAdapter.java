package com.example.haball.Retailor.ui.RetailerOrder.RetailerOrdersAdapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haball.R;
import com.example.haball.Retailor.ui.RetailerOrder.RetailerOrdersModel.RetailerViewOrderProductModel;

import java.text.DecimalFormat;
import java.util.List;

public class RetailerViewOrderProductAdapter extends RecyclerView.Adapter<RetailerViewOrderProductAdapter.ViewHolder> {
    private Context context;
    private List<RetailerViewOrderProductModel> OrdersList;

    public RetailerViewOrderProductAdapter(Context context, List<RetailerViewOrderProductModel> ordersList) {
        this.context = context;
        this.OrdersList = ordersList;
    }

    @NonNull
    @Override
    public RetailerViewOrderProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.order_items_new_recycler, parent, false);
        return new RetailerViewOrderProductAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RetailerViewOrderProductAdapter.ViewHolder holder, int position) {

        SharedPreferences sharedPreferences1 = context.getSharedPreferences("OrderId",
                Context.MODE_PRIVATE);
        String InvoiceStatus = sharedPreferences1.getString("InvoiceStatus", "null");
        Log.i("InvoiceStatus", InvoiceStatus);


        holder.txt_products.setText(OrdersList.get(position).getProductName());
        holder.product_code_value.setText(OrdersList.get(position).getProductCode());
        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
        String yourFormattedString1 = formatter1.format(Double.parseDouble(OrdersList.get(position).getUnitPrice()));
        holder.price_value.setText("Rs. " + yourFormattedString1);
        holder.UOM_value.setText(OrdersList.get(position).getUOMTitle());
        String yourFormattedString2;
        if (OrdersList.get(position).getDiscount().equals("0") || OrdersList.get(position).getDiscount().equals("null")) {
//            holder.discount.setText("Tax: ");
//            String yourFormattedString4 = formatter1.format(Double.parseDouble(OrdersList.get(position).getTaxValue()));
//            holder.discount_value.setText("Rs. " + yourFormattedString4);
//            holder.tv_taxValue.setText("Quantity: ");
//            holder.tax_value.setText(OrdersList.get(position).getOrderQty());
//            holder.Quantity_value.setVisibility(View.GONE);
//            holder.separator_3.setVisibility(View.GONE);
//            holder.Quantity.setVisibility(View.GONE);

            if (!OrdersList.get(position).getTaxValue().equals("0") && !OrdersList.get(position).getTaxValue().equals("") && !OrdersList.get(position).getTaxValue().equals("null")) {
                holder.discount.setText("Tax: ");
                String yourFormattedString4 = formatter1.format(Double.parseDouble(OrdersList.get(position).getTaxValue()));
                holder.discount_value.setText("Rs. " + yourFormattedString4);
                holder.tv_taxValue.setText("Quantity: ");
                holder.tax_value.setText(OrdersList.get(position).getOrderQty());
                holder.Quantity_value.setVisibility(View.GONE);
                holder.separator_3.setVisibility(View.GONE);
                holder.Quantity.setVisibility(View.GONE);
            } else {
                holder.discount.setVisibility(View.GONE);
                holder.discount_value.setVisibility(View.GONE);
                holder.separator_2.setVisibility(View.GONE);
                holder.tax_value.setVisibility(View.GONE);
                holder.tv_taxValue.setVisibility(View.GONE);
                holder.Quantity_value.setText(OrdersList.get(position).getOrderQty());
            }


        } else {
            yourFormattedString2 = formatter1.format(Double.parseDouble(OrdersList.get(position).getDiscount()));
            holder.discount_value.setText("Rs. " + yourFormattedString2);
            holder.tax_value.setText(OrdersList.get(position).getTaxValue());
            if (!OrdersList.get(position).getTaxValue().equals("0") && !OrdersList.get(position).getTaxValue().equals("") && !OrdersList.get(position).getTaxValue().equals("null")) {
                String yourFormattedString4 = formatter1.format(Double.parseDouble(OrdersList.get(position).getTaxValue()));
                holder.tax_value.setText("Rs. " + yourFormattedString4);
//            holder.tax_value.setText(OrdersList.get(position).getTaxValue());
                holder.Quantity_value.setText(OrdersList.get(position).getOrderQty());
            } else {
                holder.tv_taxValue.setText("Quantity: ");
                holder.tax_value.setText(OrdersList.get(position).getOrderQty());
                holder.Quantity_value.setVisibility(View.GONE);
                holder.separator_3.setVisibility(View.GONE);
                holder.Quantity.setVisibility(View.GONE);
            }
        }
//        holder.tax_value.setVisibility(View.GONE);
//        holder.tv_taxValue.setVisibility(View.GONE);
//        holder.separator_2.setVisibility(View.GONE);

        String yourFormattedString3 = formatter1.format(Double.parseDouble(OrdersList.get(position).getTotalPrice()));
        holder.amount_value.setText("Rs. " + yourFormattedString3);
    }

    @Override
    public int getItemCount() {
        return OrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_products, product_code_value, separator_1, discount, separator_2, tv_taxValue, price_value, discount_value, UOM_value, tax_value, Quantity_value, amount_value, Quantity, separator_3;
        public ImageButton menu_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_products = itemView.findViewById(R.id.txt_products);
            product_code_value = itemView.findViewById(R.id.product_code_value);
            price_value = itemView.findViewById(R.id.price_value);
            discount = itemView.findViewById(R.id.discount);
            discount_value = itemView.findViewById(R.id.discount_value);
            UOM_value = itemView.findViewById(R.id.UOM_value);
            tax_value = itemView.findViewById(R.id.tax_value);
            tv_taxValue = itemView.findViewById(R.id.tv_taxValue);
            separator_1 = itemView.findViewById(R.id.separator_1);
            separator_2 = itemView.findViewById(R.id.separator_2);
            Quantity_value = itemView.findViewById(R.id.Quantity_value);
            amount_value = itemView.findViewById(R.id.amount_value);
            separator_2 = itemView.findViewById(R.id.separator_2);
            Quantity = itemView.findViewById(R.id.Quantity);
            separator_3 = itemView.findViewById(R.id.separator_3);
        }
    }
}
