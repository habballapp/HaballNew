package com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Adapters;

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
import com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Models.OrderChildlist_Model;
import com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Models.OrderParentlist_Model;
import com.example.haball.R;
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

    public ParentListAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        inflater = LayoutInflater.from(context);
        this.context = context;
        SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_retailer",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        object_stringqty = selectedProducts.getString("selected_products_qty", "");
        object_string = selectedProducts.getString("selected_products", "");
        Type type = new TypeToken<List<OrderChildlist_Model>>() {}.getType();
        Type typeString = new TypeToken<List<String>>() {}.getType();
        if(!object_string.equals("")) {
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
    public void onBindParentViewHolder(OrderParentLIst_VH orderParentLIst_vh, int i, Object o) {
        Log.i("obj", String.valueOf(o));
        OrderParentlist_Model orderParentlist_model = (OrderParentlist_Model) o;
        orderParentLIst_vh._textview.setText(orderParentlist_model.getTitle());

    }

    @Override
    public void onBindChildViewHolder(final OrderChildList_VH orderChildList_vh, final int i, Object o) {

        Log.i("o", String.valueOf(o));
        final OrderChildlist_Model orderChildlist_model = (OrderChildlist_Model) o;


        if (selectedProductsDataList != null) {
            for (int j = 0; j < selectedProductsDataList.size(); j++) {
                Gson gson = new Gson();
                String json = gson.toJson(selectedProductsDataList);
                if (selectedProductsDataList.get(j).getTitle().equals(orderChildlist_model.getTitle())) {
                    Log.i("found", String.valueOf(orderChildlist_model.getTitle()));
                    orderChildList_vh.list_numberOFitems.setText(selectedProductsQuantityList.get(j));
                }
            }
        }

        orderChildList_vh.list_txt_products.setText(orderChildlist_model.getTitle());
        orderChildList_vh.list_product_code_value.setText(orderChildlist_model.getProductCode());
        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
        if (orderChildlist_model.getProductUnitPrice() != null) {
            String yourFormattedString1 = formatter1.format(Double.parseDouble(orderChildlist_model.getProductUnitPrice()));
            orderChildList_vh.list_price_value.setText("Rs. " + yourFormattedString1);
        }
        if (orderChildlist_model.getDiscountAmount() != null) {
            String yourFormattedString2 = formatter1.format(Double.parseDouble(orderChildlist_model.getDiscountAmount()));
            orderChildList_vh.list_discount_value.setText("Rs. " + yourFormattedString2);
        }
        orderChildList_vh.list_UOM_value.setText(orderChildlist_model.getUnitOFMeasure());
        orderChildList_vh.list_numberOFitems.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkOutEnabler(orderChildList_vh, i, orderChildlist_model);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void checkOutEnabler(OrderChildList_VH holder, int position, OrderChildlist_Model orderChildlist_model) {
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
        SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_retailer",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = selectedProducts.edit();
        editor.putString("selected_products", json);
        editor.putString("selected_products_qty", jsonqty);
        editor.apply();
    }
}
