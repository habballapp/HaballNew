package com.example.haball.Retailor.ui.Place_Order.ui.main.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.example.haball.R;
import com.example.haball.Retailor.ui.Place_Order.ui.main.Models.OrderChildlist_Model;
import com.example.haball.Retailor.ui.Place_Order.ui.main.Models.OrderParentlist_Model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ParentListAdapter extends ExpandableRecyclerAdapter<OrderParentLIst_VH, OrderChildList_VH> {
    LayoutInflater inflater;
    private Context context;
    public List<OrderChildlist_Model> selectedProductsDataList = new ArrayList<>();
    private List<String> selectedProductsQuantityList = new ArrayList<>();
    private String object_string, object_stringqty;
    private int pre_expanded = -1;

    public ParentListAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        inflater = LayoutInflater.from(context);
        this.context = context;
        SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_retailer_own",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        object_stringqty = selectedProducts.getString("selected_products_qty", "");
        object_string = selectedProducts.getString("selected_products", "");
        Type type = new TypeToken<List<OrderChildlist_Model>>() {
        }.getType();
        Type typeString = new TypeToken<List<String>>() {
        }.getType();
        if (!object_string.equals("")) {
            selectedProductsDataList = gson.fromJson(object_string, type);
            selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
            Log.i("selectedProductsQty", String.valueOf(object_stringqty));
            Log.i("selectedProducts", String.valueOf(object_string));
        }

    }

    @Override
    public OrderParentLIst_VH onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.parentlist_retailer_order, viewGroup, false);
        return new OrderParentLIst_VH(view);

    }


    @Override
    public OrderChildList_VH onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.orderchildlist_expand, viewGroup, false);

        return new OrderChildList_VH(view);

    }

    @Override
    public void onBindParentViewHolder(final OrderParentLIst_VH orderParentLIst_vh, final int position, Object o) {
        Log.i("obj", String.valueOf(o));
        final OrderParentlist_Model orderParentlist_model = (OrderParentlist_Model) o;
        orderParentLIst_vh._textview.setText(orderParentlist_model.getTitle());

    }


    @Override
    public void onBindChildViewHolder(OrderChildList_VH orderChildList_vh, int i, Object o) {

        Log.i("o", String.valueOf(o));
        OrderChildlist_Model orderChildlist_model = (OrderChildlist_Model) o;
        final OrderChildList_VH temp_orderChildList_vh = orderChildList_vh;
        final int temp_i = i;
        final OrderChildlist_Model temp_orderChildlist_model = orderChildlist_model;


        orderChildList_vh.list_numberOFitems.setText("");
        if (selectedProductsDataList != null && selectedProductsQuantityList != null) {
            setQuantity(orderChildList_vh, orderChildlist_model);
        }

        orderChildList_vh.list_txt_products.setText(orderChildlist_model.getTitle());
        orderChildList_vh.list_product_code_value.setText(orderChildlist_model.getProductCode());
        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
        if (orderChildlist_model.getProductUnitPrice() != null) {
            String yourFormattedString1 = formatter1.format(Double.parseDouble(orderChildlist_model.getProductUnitPrice()));
            orderChildList_vh.list_price_value.setText("Rs. " + yourFormattedString1);
        }
        String yourFormattedString2;
        if (orderChildlist_model.getDiscountAmount() != null)
            yourFormattedString2 = formatter1.format(Double.parseDouble(orderChildlist_model.getDiscountAmount()));
        else
            yourFormattedString2 = formatter1.format(Double.parseDouble(orderChildlist_model.getProductUnitPrice()));
        orderChildList_vh.list_discount_value.setText("Rs. " + yourFormattedString2);
        if (orderChildlist_model.getPackSize() != null)
            orderChildList_vh.list_pack_size_value.setText(orderChildlist_model.getPackSize());
        orderChildList_vh.list_UOM_value.setText(orderChildlist_model.getUnitOFMeasure());
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str_quantity = String.valueOf(s);
//                Log.i("textChanged12", "check");
//                Log.i("textChanged11", String.valueOf(s));
                if (String.valueOf(s).equals(""))
                    str_quantity = "0";

                if (temp_orderChildList_vh.list_txt_products.getText().equals(temp_orderChildlist_model.getTitle())) {
                    if (Float.parseFloat(str_quantity) <= 0) {
                        Toast.makeText(context, "Quantity must be greater than 0", Toast.LENGTH_LONG).show();
                    }
                    Log.i("textChanged", String.valueOf(temp_orderChildlist_model.getTitle()));
                    Log.i("textChanged11", String.valueOf(temp_orderChildList_vh.list_txt_products.getText()));
                    checkOutEnabler(temp_orderChildList_vh, temp_i, temp_orderChildlist_model, str_quantity);
                }
            }
        };
        orderChildList_vh.list_numberOFitems.addTextChangedListener(textWatcher);
//        orderChildList_vh.list_numberOFitems.removeTextChangedListener(textWatcher);    }
    }

    private void setQuantity(OrderChildList_VH orderChildList_vh, OrderChildlist_Model orderChildlist_model) {
        if (selectedProductsQuantityList != null && selectedProductsDataList != null) {
            for (int j = 0; j < selectedProductsDataList.size(); j++) {
                if (selectedProductsDataList.get(j).getTitle().equals(orderChildlist_model.getTitle()) && selectedProductsDataList.get(j).getProductCode().equals(orderChildlist_model.getProductCode())) {
                    Log.i("foundItem", String.valueOf(orderChildlist_model.getTitle()));
                    orderChildList_vh.list_numberOFitems.setText(selectedProductsQuantityList.get(j));
                }
            }
        }
    }

    private void checkOutEnabler(OrderChildList_VH holder, int position, OrderChildlist_Model orderChildlist_model, String s) {
        if (selectedProductsDataList != null) {
            if (!selectedProductsDataList.contains(orderChildlist_model)) {
                selectedProductsDataList.add(orderChildlist_model);
                selectedProductsQuantityList.add(String.valueOf(holder.list_numberOFitems.getText()));
            } else {
                int foundIndex = -1;
                for (int i = 0; i < selectedProductsDataList.size(); i++) {
                    if (selectedProductsDataList.get(i).equals(orderChildlist_model)) {
                        foundIndex = i;
                        break;
                    }
                }

                if (foundIndex != -1)
                    selectedProductsQuantityList.set(foundIndex, String.valueOf(holder.list_numberOFitems.getText()));
                Log.i("selected updated qty", String.valueOf(selectedProductsQuantityList));
            }
        } else {
            selectedProductsDataList.add(orderChildlist_model);
            selectedProductsQuantityList.add(String.valueOf(holder.list_numberOFitems.getText()));
        }

        for (int i = 0; i < selectedProductsDataList.size(); i++)
            Toast.makeText(context, selectedProductsDataList.get(i).getTitle() + " - " + selectedProductsQuantityList.get(i), Toast.LENGTH_LONG).show();

        Gson gson = new Gson();
        String json = gson.toJson(selectedProductsDataList);
        String jsonqty = gson.toJson(selectedProductsQuantityList);
        Log.i("jsonqty", jsonqty);
        Log.i("json", json);
        SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_retailer_own",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = selectedProducts.edit();
        editor.putString("selected_products", json);
        editor.putString("selected_products_qty", jsonqty);
        editor.apply();
    }


}
