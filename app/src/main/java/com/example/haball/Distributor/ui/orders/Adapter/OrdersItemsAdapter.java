package com.example.haball.Distributor.ui.orders.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haball.Distributor.ui.orders.Models.OrderItemsModel;
import com.example.haball.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class OrdersItemsAdapter extends RecyclerView.Adapter<OrdersItemsAdapter.ViewHolder> {

    private Context context;
    private String txt_count,txt_products,unit_price_value,discount_price;
    private EditText quantity;
    private Button btn_cart;
    private List<OrderItemsModel> productsDataList;

    public OrdersItemsAdapter(Context context, List<OrderItemsModel> productsDataList) {
        this.context = context;
        this.productsDataList = productsDataList;
    }

    public OrdersItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.orders_items_recycler,parent,false);
        return new OrdersItemsAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersItemsAdapter.ViewHolder holder, int position) {
        holder.txt_count.setText(productsDataList.get(position).getCode());
        holder.txt_products.setText(productsDataList.get(position).getTitle());
        holder.unit_price_value.setText(String.valueOf(productsDataList.get(position).getUnitPrice()));
        holder.discount_price.setText(String.valueOf(productsDataList.get(position).getDiscountAmount()));

         //final String s1 = quantity.getText().toString();
        Log.i("S1:", String.valueOf(quantity));
        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                btn_cart.setBackgroundResource(R.drawable.button_grey_round);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                double myNum =0;
                try {
                    myNum = Integer.parseInt(quantity.getText().toString());
                } catch(NumberFormatException nfe) {
                   // System.out.println("Please Enter Number Only " + nfe);
                    Toast.makeText(context,"Error",Toast.LENGTH_SHORT);
                }

              //  String s1 = quantity.getText().toString();
                if (myNum == 0)
                {
                    btn_cart.setBackgroundResource(R.drawable.button_grey_round);
                    btn_cart.setEnabled(false);
                }
                else
                    btn_cart.setBackgroundResource(R.drawable.button_round);
                // btn_cart.

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    @Override
    public int getItemCount() {
        return productsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_count,txt_products,unit_price_value,discount_price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_count = itemView.findViewById(R.id.txt_count);
            txt_products = itemView.findViewById(R.id.txt_products);
            unit_price_value = itemView.findViewById(R.id.unit_price_value);
            discount_price = itemView.findViewById(R.id.discount_price);
            quantity =  itemView.findViewById(R.id.quantity);
            btn_cart = itemView.findViewById(R.id.btn_cart);


        }
    }
}
