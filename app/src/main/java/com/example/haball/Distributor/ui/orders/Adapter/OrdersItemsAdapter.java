package com.example.haball.Distributor.ui.orders.Adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextUtils;
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
import com.example.haball.Distributor.ui.orders.OrdersTabsLayout.Tabs.Orders_Items_Fragment;
import com.example.haball.R;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class OrdersItemsAdapter extends RecyclerView.Adapter<OrdersItemsAdapter.ViewHolder> {

    private Context context;
    private List<OrderItemsModel> productsDataList;
    private List<OrderItemsModel> selectedProductsDataList = new ArrayList<>();
    private List<String> selectedProductsQuantityList = new ArrayList<>();

    public OrdersItemsAdapter(Context context, List<OrderItemsModel> productsDataList) {
        this.context = context;
        this.productsDataList = productsDataList;
    }

    public OrdersItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.orders_items_recycler,parent,false);
        return new OrdersItemsAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrdersItemsAdapter.ViewHolder holder, final int position) {
        holder.txt_products.setText(productsDataList.get(position).getCode() + "  |  " + productsDataList.get(position).getTitle());
        holder.unit_price_value.setText(String.valueOf(productsDataList.get(position).getUnitPrice()));
        holder.discount_price.setText(String.valueOf(productsDataList.get(position).getDiscountAmount()));
        holder.quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                holder.btn_cart.setBackgroundResource(R.drawable.button_grey_round);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (TextUtils.isEmpty(holder.quantity.getText())){
                    holder.btn_cart.setBackgroundResource(R.drawable.button_grey_round);
                    holder.btn_cart.setEnabled(false);
                }
                else{
                    holder.btn_cart.setBackgroundResource(R.drawable.button_round);
                    holder.btn_cart.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!selectedProductsDataList.contains(productsDataList.get(position))){
                    selectedProductsDataList.add(productsDataList.get(position));
                    selectedProductsQuantityList.add(String.valueOf(holder.quantity.getText()));
                } else {
                    int foundIndex = -1;
                    for(int i = 0; i < selectedProductsDataList.size(); i++) {
                        if(selectedProductsDataList.get(i).equals(productsDataList.get(position))) {
                            foundIndex = i;
//                            break;
                        } else{
                            holder.quantity.setText("");
                            holder.btn_cart.setBackgroundResource(R.drawable.button_grey_round);
                            holder.btn_cart.setEnabled(false);
                        }
                    }

                    if(foundIndex != -1)
                        selectedProductsQuantityList.set(foundIndex, String.valueOf(holder.quantity.getText()));
                    Log.i("selected updated qty", String.valueOf(selectedProductsQuantityList));
                }
                for(int i = 0; i < selectedProductsDataList.size(); i++)
                    Toast.makeText(context, selectedProductsDataList.get(i).getTitle() + " - " + selectedProductsQuantityList.get(i), Toast.LENGTH_LONG).show();

                Gson gson = new Gson();
                String json = gson.toJson(selectedProductsDataList);
                String jsonqty = gson.toJson(selectedProductsQuantityList);
                Log.i("jsonqty",jsonqty);
                Log.i("json",json);
                SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = selectedProducts.edit();
                editor.putString("selected_products",json);
                editor.putString("selected_products_qty",jsonqty);
                editor.apply();

//                FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.oif_container, new Orders_Items_Fragment());
//                transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
//                transaction.addToBackStack(null);
//                transaction.commit();
            }
        });


    }
    @Override
    public int getItemCount() {
        return productsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_count,txt_products,unit_price_value,discount_price, btn_cart, quantity;


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
