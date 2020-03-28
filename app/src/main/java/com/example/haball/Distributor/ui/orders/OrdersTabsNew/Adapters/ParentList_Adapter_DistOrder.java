package com.example.haball.Distributor.ui.orders.OrdersTabsNew.Adapters;

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
import com.example.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderChildlist_Model_DistOrder;
import com.example.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderParentlist_Model_DistOrder;
import com.example.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ParentList_Adapter_DistOrder extends ExpandableRecyclerAdapter<OrderParentList_VH_DistOrder, OrderChildList_VH_DistOrder> {
    LayoutInflater inflater;
    private Context context;
    private List<OrderChildlist_Model_DistOrder> selectedProductsDataList = new ArrayList<>();
    private List<String> selectedProductsQuantityList = new ArrayList<>();
    private String object_string, object_stringqty;
    private int parentPosition = -1;
//    private List<OrderParentlist_Model_DistOrder> orderParentlist_modelList = new ArrayList<>();

    public ParentList_Adapter_DistOrder(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        inflater = LayoutInflater.from(context);
        this.context = context;
        SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_distributor",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        object_stringqty = selectedProducts.getString("selected_products_qty", "");
        object_string = selectedProducts.getString("selected_products", "");
        Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
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
    public OrderParentList_VH_DistOrder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.parentlist_retailer_order, viewGroup, false);
        return new OrderParentList_VH_DistOrder(view);

    }

    @Override
    public OrderChildList_VH_DistOrder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.orderchildlist_expand, viewGroup, false);
        return new OrderChildList_VH_DistOrder(view);

    }


    @Override
    public void onBindParentViewHolder(OrderParentList_VH_DistOrder orderParentLIst_vh, int i, Object o) {
        Log.i("objAdapter", String.valueOf(o));
        OrderParentlist_Model_DistOrder orderParentlist_model = (OrderParentlist_Model_DistOrder) o;
        orderParentLIst_vh._textview.setText(orderParentlist_model.getTitle());

//        if(parentPosition == 2){
//            orderParentLIst_vh._textview.setVisibility(View.GONE);
//            Toast.makeText(context, "parentPosition"+parentPosition +" "+i, Toast.LENGTH_SHORT).show();
//        }else{
//            orderParentLIst_vh.layout_expandable.setVisibility(View.VISIBLE);
//
//            Toast.makeText(context, "parentPosition"+parentPosition +" "+i, Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    public void onBindChildViewHolder(OrderChildList_VH_DistOrder orderChildList_vh, int i, Object o) {


        Log.i("objAdapter", String.valueOf(o));
        OrderChildlist_Model_DistOrder orderChildlist_model = (OrderChildlist_Model_DistOrder) o;

        final OrderChildList_VH_DistOrder temp_orderChildList_vh = orderChildList_vh;
        final int temp_i = i;
        final OrderChildlist_Model_DistOrder temp_orderChildlist_model = orderChildlist_model;


        orderChildList_vh.list_numberOFitems.setText("");
        if (selectedProductsDataList != null) {
            setQuantity(orderChildList_vh, orderChildlist_model);
        }

        orderChildList_vh.list_txt_products.setText(orderChildlist_model.getTitle());
        orderChildList_vh.list_product_code_value.setText(orderChildlist_model.getCode());
        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
        if (orderChildlist_model.getUnitPrice() != null) {
            String yourFormattedString1 = formatter1.format(Double.parseDouble(orderChildlist_model.getUnitPrice()));
            orderChildList_vh.list_price_value.setText("Rs. " + yourFormattedString1);
        }
        String yourFormattedString2 = "0";
        if (orderChildlist_model.getDiscountAmount() != null) {
            yourFormattedString2 = formatter1.format(Double.parseDouble(orderChildlist_model.getDiscountAmount()));
        } else {
            yourFormattedString2 = formatter1.format(Double.parseDouble(orderChildlist_model.getUnitPrice()));
        }
//        if (orderChildlist_model.getUOMTitle() != null)
            orderChildList_vh.list_discount_value.setText("Rs. " + yourFormattedString2);
        if (orderChildlist_model.getUOMTitle() != null)
            orderChildList_vh.list_UOM_value.setText(orderChildlist_model.getUOMTitle());
        if (orderChildlist_model.getPackSize() != null)
            orderChildList_vh.list_pack_size_value.setText(orderChildlist_model.getPackSize());
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

//                Log.i("textChanged12", "check");
//                Log.i("textChanged11", String.valueOf(s));
                if (!String.valueOf(s).equals("")) {
                    if(temp_orderChildList_vh.list_txt_products.getText().equals(temp_orderChildlist_model.getTitle())) {
                        Log.i("textChanged", String.valueOf(temp_orderChildlist_model.getTitle()));
                        Log.i("textChanged11", String.valueOf(temp_orderChildList_vh.list_txt_products.getText()));
                        checkOutEnabler(temp_orderChildList_vh, temp_i, temp_orderChildlist_model, String.valueOf(s));
                    }
                }
            }
        };
        orderChildList_vh.list_numberOFitems.addTextChangedListener(textWatcher);
//        orderChildList_vh.list_numberOFitems.removeTextChangedListener(textWatcher);
    }

    private void setQuantity(OrderChildList_VH_DistOrder orderChildList_vh, OrderChildlist_Model_DistOrder orderChildlist_model) {
        for (int j = 0; j < selectedProductsDataList.size(); j++) {
            if (selectedProductsDataList.get(j).getTitle().equals(orderChildlist_model.getTitle()) && selectedProductsDataList.get(j).getCode().equals(orderChildlist_model.getCode())) {
                Log.i("foundItem", String.valueOf(orderChildlist_model.getTitle()));
                orderChildList_vh.list_numberOFitems.setText(selectedProductsQuantityList.get(j));
            }
        }

    }

    private void checkOutEnabler(OrderChildList_VH_DistOrder holder, int position, OrderChildlist_Model_DistOrder orderChildlist_model, String s) {

        if (selectedProductsDataList != null) {

            if (!selectedProductsDataList.contains(orderChildlist_model)) {
                selectedProductsDataList.add(orderChildlist_model);
                selectedProductsQuantityList.add(String.valueOf(s));
            } else {
                int foundIndex = -1;
                for (int i = 0; i < selectedProductsDataList.size(); i++) {
                    if (selectedProductsDataList.get(i).getTitle().equals(orderChildlist_model.getTitle()) && selectedProductsDataList.get(i).getCode().equals(orderChildlist_model.getCode())) {
                        foundIndex = i;
                        break;
                    }
                }

                if (foundIndex != -1)
                    selectedProductsQuantityList.set(foundIndex, String.valueOf(s));
                Log.i("selected updated qty", String.valueOf(selectedProductsQuantityList));
            }
        } else {
            selectedProductsDataList.add(orderChildlist_model);
            selectedProductsQuantityList.add(String.valueOf(s));
        }

        for (int i = 0; i < selectedProductsDataList.size(); i++)
            Toast.makeText(context, selectedProductsDataList.get(i).getTitle() + " - " + selectedProductsQuantityList.get(i), Toast.LENGTH_LONG).show();

        Gson gson = new Gson();
        String json = gson.toJson(selectedProductsDataList);
        String jsonqty = gson.toJson(selectedProductsQuantityList);
        Log.i("jsonqty", jsonqty);
        Log.i("jsonqtySize", String.valueOf(selectedProductsQuantityList.size()));
        Log.i("json", json);
        Log.i("jsonSize", String.valueOf(selectedProductsDataList.size()));
        SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_distributor",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = selectedProducts.edit();
        editor.putString("selected_products", json);
        editor.putString("selected_products_qty", jsonqty);
        editor.apply();
    }

//    @Override
//    public void onParentItemClickListener(int position) {
//        super.onParentItemClickListener(position);
//        // Open only one parent list at a time
//        Toast.makeText(context, "Clicked"+position,Toast.LENGTH_LONG).show();
//        parentPosition = posit

//    @Override
//    public void onParentItemClickListener(int position) {
//        super.onParentItemClickListener(position);
//        Toast.makeText(context, "position"+position +" parentpostion"+parentPosition, Toast.LENGTH_SHORT).show();
//
//        parentPosition=position;
//    }

}
