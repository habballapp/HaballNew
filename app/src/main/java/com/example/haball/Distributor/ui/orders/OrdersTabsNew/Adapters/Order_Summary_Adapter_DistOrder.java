package com.example.haball.Distributor.ui.orders.OrdersTabsNew.Adapters;

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

import com.example.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderChildlist_Model_DistOrder;
import com.example.haball.R;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class Order_Summary_Adapter_DistOrder extends RecyclerView.Adapter<Order_Summary_Adapter_DistOrder.ViewHolder> {

    private Context context;
    private List<OrderChildlist_Model_DistOrder> selectedProductsDataList;
    private List<String> selectedProductsDataListQty;
    private float grossAmount = 0;

    public Order_Summary_Adapter_DistOrder(Context context, List<OrderChildlist_Model_DistOrder> selectedProductsDataList, List<String> selectedProductsDataListQty) {
        this.context = context;
        this.selectedProductsDataList = selectedProductsDataList;
        this.selectedProductsDataListQty = selectedProductsDataListQty;
        Log.i("selectedProducts", String.valueOf(selectedProductsDataList));
    }

    public Order_Summary_Adapter_DistOrder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.order_summary_recycler_fragment, parent, false);
        return new Order_Summary_Adapter_DistOrder.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final Order_Summary_Adapter_DistOrder.ViewHolder holder, int position) {
        final int finalPosition = position;
        Log.i("position", String.valueOf(finalPosition));
        if (selectedProductsDataListQty.get(position) != null)
            holder.list_numberOFitems.setText(selectedProductsDataListQty.get(position));
        if (selectedProductsDataList.get(position).getCode() != null)
            holder.list_product_code_value.setText(selectedProductsDataList.get(position).getCode());
        if (selectedProductsDataList.get(position).getTitle() != null)
            holder.list_txt_products_.setText(selectedProductsDataList.get(position).getTitle());
        if (selectedProductsDataList.get(position).getUnitPrice() != null)
            holder.list_price_value.setText(selectedProductsDataList.get(position).getUnitPrice());
        if (selectedProductsDataList.get(position).getUOMTitle() != null)
            holder.list_UOM_value.setText(selectedProductsDataList.get(position).getUOMTitle());
        if (selectedProductsDataList.get(position).getPackSize() != null)
            holder.list_pack_size_value.setText(selectedProductsDataList.get(position).getPackSize());
        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
        String yourFormattedString2 = "0";
        if (selectedProductsDataList.get(position).getDiscountAmount() != null) {
            yourFormattedString2 = formatter1.format(Double.parseDouble(selectedProductsDataList.get(position).getDiscountAmount()));
        } else {
            yourFormattedString2 = formatter1.format(Double.parseDouble(selectedProductsDataList.get(position).getUnitPrice()));
        }
//        if (orderChildlist_model.getUOMTitle() != null)
        holder.list_discount_value.setText("Rs. " + yourFormattedString2);
        float totalamount = 0;
        if (!selectedProductsDataList.get(position).getUnitPrice().equals("") && !selectedProductsDataListQty.get(position).equals(""))
            totalamount = Float.parseFloat(selectedProductsDataListQty.get(position)) * Float.parseFloat(selectedProductsDataList.get(position).getUnitPrice());
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
//                    SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_distributor",
//                            Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = selectedProducts.edit();
//                    editor.putString("selected_products", json);
//                    editor.putString("selected_products_qty", jsonqty);
//                    editor.apply();
//                } else {
                checkOutEnabler(holder, finalPosition);
                String quantity = selectedProductsDataListQty.get(finalPosition);
                if (quantity.equals(""))
                    quantity = "0";

                final float finaltotalamount = Float.parseFloat(quantity) * Float.parseFloat(selectedProductsDataList.get(finalPosition).getUnitPrice());
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


    private void checkOutEnabler(Order_Summary_Adapter_DistOrder.ViewHolder holder, int position) {
        if (holder.list_numberOFitems.getText() != null && selectedProductsDataListQty.size() > position) {
            selectedProductsDataListQty.set(position, String.valueOf(holder.list_numberOFitems.getText()));

//            for (int i = 0; i < selectedProductsDataList.size(); i++)
//                Toast.makeText(context, selectedProductsDataList.get(i).getTitle() + " - " + selectedProductsDataListQty.get(i), Toast.LENGTH_LONG).show();

            Gson gson = new Gson();
            String json = gson.toJson(selectedProductsDataList);
            String jsonqty = gson.toJson(selectedProductsDataListQty);
            Log.i("jsonqty", jsonqty);
            Log.i("json", json);
            SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_distributor",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = selectedProducts.edit();
            editor.putString("selected_products", json);
            editor.putString("selected_products_qty", jsonqty);
            editor.apply();

            if (selectedProductsDataList.size() > 0) {
                for (int i = 0; i < selectedProductsDataList.size(); i++) {
                    Log.i("unit price", selectedProductsDataList.get(i).getUnitPrice());
                    Log.i("qty", selectedProductsDataListQty.get(i));
                    if (!selectedProductsDataList.get(i).getUnitPrice().equals("") && !selectedProductsDataListQty.get(i).equals(""))
                        grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice()) * Float.parseFloat(selectedProductsDataListQty.get(i));
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
