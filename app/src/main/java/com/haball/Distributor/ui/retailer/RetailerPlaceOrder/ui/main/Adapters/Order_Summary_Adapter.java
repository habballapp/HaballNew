package com.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Models.OrderChildlist_Model;
import com.haball.R;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Order_Summary_Adapter extends RecyclerView.Adapter<Order_Summary_Adapter.ViewHolder> {

    private Context context;
    private List<OrderChildlist_Model> selectedProductsDataList;
    private List<String> selectedProductsDataListQty;
    private float grossAmount = 0;

    public Order_Summary_Adapter(Context context, List<OrderChildlist_Model> selectedProductsDataList, List<String> selectedProductsDataListQty) {
        this.context = context;
        this.selectedProductsDataList = selectedProductsDataList;
        this.selectedProductsDataListQty = selectedProductsDataListQty;
        Log.i("selectedProducts", String.valueOf(selectedProductsDataList));
    }

    public Order_Summary_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.order_summary_recycler_fragment, parent, false);
        return new Order_Summary_Adapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final Order_Summary_Adapter.ViewHolder holder, int position) {
        final int finalPosition = position;
        Log.i("position", String.valueOf(finalPosition));
        holder.list_numberOFitems.setText(selectedProductsDataListQty.get(position));
        holder.list_product_code_value.setText(selectedProductsDataList.get(position).getProductCode());
        holder.list_txt_products_.setText(selectedProductsDataList.get(position).getTitle());
        holder.list_price_value.setText(selectedProductsDataList.get(position).getProductUnitPrice());
        holder.list_discount_value.setText(selectedProductsDataList.get(position).getDiscountAmount());
        float totalamount = 0;
        if (!selectedProductsDataList.get(position).getProductUnitPrice().equals("") && !selectedProductsDataListQty.get(position).equals(""))
            totalamount = Float.parseFloat(selectedProductsDataListQty.get(position)) * Float.parseFloat(selectedProductsDataList.get(position).getProductUnitPrice());
        holder.totalAmount_value.setText(String.format("%.0f", totalamount));
        holder.list_numberOFitems.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (String.valueOf(s).equals("0")) {
//                    Log.i("position to be removed", String.valueOf(finalPosition));
//                    selectedProductsDataList.remove(finalPosition);
//                    selectedProductsDataListQty.remove(finalPosition);
//                    notifyItemRemoved(finalPosition);
//                    notifyItemRangeChanged(finalPosition, selectedProductsDataList.size());
//
//                    Gson gson = new Gson();
//                    String json = gson.toJson(selectedProductsDataList);
//                    String jsonqty = gson.toJson(selectedProductsDataListQty);
//                    Log.i("jsonqty", jsonqty);
//                    Log.i("json", json);
//
//                    SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_retailer",
//                            Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = selectedProducts.edit();
//                    editor.putString("selected_products", json);
//                    editor.putString("selected_products_qty", jsonqty);
//                    editor.apply();
//                } else {
                checkOutEnabler(holder, finalPosition);
                String quantity = selectedProductsDataListQty.get(finalPosition);
                if(quantity.equals(""))
                    quantity = "0";

                final float finaltotalamount =  Float.parseFloat(quantity) * Float.parseFloat(selectedProductsDataList.get(finalPosition).getProductUnitPrice());
                holder.totalAmount_value.setText(String.valueOf(finaltotalamount));
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return selectedProductsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView list_txt_products_, list_product_code_value, list_price_value, list_discount_value, list_UOM_value, list_pack_size_value, totalAmount_value;
        public EditText list_numberOFitems;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            list_txt_products_ = itemView.findViewById(R.id.list_txt_products_);
            list_product_code_value = itemView.findViewById(R.id.list_product_code_value);
            list_price_value = itemView.findViewById(R.id.list_price_value);
            list_discount_value = itemView.findViewById(R.id.list_discount_value);
            list_UOM_value = itemView.findViewById(R.id.list_UOM_value);
//            list_pack_size_value = itemView.findViewById(R.id.list_pack_size_value);
            list_numberOFitems = itemView.findViewById(R.id.list_numberOFitems);
            totalAmount_value = itemView.findViewById(R.id.totalAmount_value);
        }
    }


    private void checkOutEnabler(Order_Summary_Adapter.ViewHolder holder, int position) {
        if (holder.list_numberOFitems.getText() != null && selectedProductsDataListQty.size() > position) {
            selectedProductsDataListQty.set(position, String.valueOf(holder.list_numberOFitems.getText()));

//            for (int i = 0; i < selectedProductsDataList.size(); i++)
//                Toast.makeText(context, selectedProductsDataList.get(i).getTitle() + " - " + selectedProductsDataListQty.get(i), Toast.LENGTH_LONG).show();

            Gson gson = new Gson();
            String json = gson.toJson(selectedProductsDataList);
            String jsonqty = gson.toJson(selectedProductsDataListQty);
            Log.i("jsonqty", jsonqty);
            Log.i("json", json);
            SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_retailer",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = selectedProducts.edit();
            editor.putString("selected_products", json);
            editor.putString("selected_products_qty", jsonqty);
            editor.apply();

            if (selectedProductsDataList.size() > 0) {
                for (int i = 0; i < selectedProductsDataList.size(); i++) {
                    Log.i("unit price", selectedProductsDataList.get(i).getProductUnitPrice());
                    Log.i("qty", selectedProductsDataListQty.get(i));
                    if (!selectedProductsDataList.get(i).getProductUnitPrice().equals("") && !selectedProductsDataListQty.get(i).equals(""))
                        grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getProductUnitPrice()) * Float.parseFloat(selectedProductsDataListQty.get(i));
                }
                SharedPreferences grossamount = context.getSharedPreferences("grossamount",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_grossamount = grossamount.edit();
                editor_grossamount.putString("grossamount", String.valueOf(grossAmount));
                editor_grossamount.apply();
                grossAmount = 0;
            }
        }
    }
}
