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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
//import com.bignerdranch.expandablerecyclerview.model.SimpleParent;
import com.example.haball.Distributor.ui.orders.OrdersTabsNew.ExpandableRecyclerAdapter;
import com.example.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderChildlist_Model_DistOrder;
import com.example.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderParentlist_Model_DistOrder;
import com.example.haball.Distributor.ui.orders.OrdersTabsNew.ParentViewHolder;
import com.example.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ParentList_Adapter_DistOrder extends ExpandableRecyclerAdapter<OrderParentlist_Model_DistOrder, OrderChildlist_Model_DistOrder, OrderParentList_VH_DistOrder, OrderChildList_VH_DistOrder> {
    LayoutInflater inflater;
    private Context context;
    private List<OrderChildlist_Model_DistOrder> selectedProductsDataList = new ArrayList<>();
    private List<String> selectedProductsQuantityList = new ArrayList<>();
    private String object_string, object_stringqty;
    private int parentPosition = -1;
    private OrderParentList_VH_DistOrder orderParentLIst_vh_main;
    private List<OrderParentlist_Model_DistOrder> parentItemList;
    private List<OrderParentList_VH_DistOrder> OrderParentList = new ArrayList<>();
//    private List<OrderParentlist_Model_DistOrder> orderParentlist_modelList = new ArrayList<>();

    public ParentList_Adapter_DistOrder(Context context, List<OrderParentlist_Model_DistOrder> parentItemList) {
        super(parentItemList);
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.parentItemList = parentItemList;
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
    public OrderParentList_VH_DistOrder onCreateParentViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.parentlist_retailer_order, viewGroup, false);
        OrderParentList_VH_DistOrder orderParentList_VH_DistOrder = new OrderParentList_VH_DistOrder(view);
        OrderParentList.add(orderParentList_VH_DistOrder);
        Log.i("orderParentList_VH", String.valueOf(orderParentList_VH_DistOrder));
        return orderParentList_VH_DistOrder;

    }

    @Override
    public OrderChildList_VH_DistOrder onCreateChildViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.orderchildlist_expand, viewGroup, false);
        return new OrderChildList_VH_DistOrder(view);

    }

    @Override
    public void onBindParentViewHolder(@NonNull final OrderParentList_VH_DistOrder parentViewHolder, int parentPosition, @NonNull OrderParentlist_Model_DistOrder parent) {
        Log.i("objAdapter", String.valueOf(parent));

        OrderParentlist_Model_DistOrder orderParentlist_model = (OrderParentlist_Model_DistOrder) parent;
        parentViewHolder._textview.setText(orderParentlist_model.getTitle());
        orderParentLIst_vh_main = parentViewHolder;

    }

    @Override
    public void onBindChildViewHolder(@NonNull OrderChildList_VH_DistOrder orderChildList_vh, int parentPosition, int i, @NonNull OrderChildlist_Model_DistOrder o) {
        Log.i("objAdapter", String.valueOf(o));
        OrderChildlist_Model_DistOrder orderChildlist_model = (OrderChildlist_Model_DistOrder) o;

        final OrderChildList_VH_DistOrder temp_orderChildList_vh = orderChildList_vh;
        final int temp_i = i;
        final OrderChildlist_Model_DistOrder temp_orderChildlist_model = orderChildlist_model;


        orderChildList_vh.list_numberOFitems.setText("");
        if (selectedProductsDataList != null && selectedProductsQuantityList != null) {
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
//        if (orderChildlist_model.getPackSize() != null)
//            orderChildList_vh.list_pack_size_value.setText(orderChildlist_model.getPackSize());
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
                       // Toast.makeText(context, "Quantity must be greater than 0", Toast.LENGTH_LONG).show();
                    } else {
                        Log.i("textChanged", String.valueOf(temp_orderChildlist_model.getTitle()));
                        Log.i("textChanged11", String.valueOf(temp_orderChildList_vh.list_txt_products.getText()));
                        checkOutEnabler(temp_orderChildList_vh, temp_i, temp_orderChildlist_model, str_quantity);
                    }
                }
            }
        };
        orderChildList_vh.list_numberOFitems.addTextChangedListener(textWatcher);

    }

//
//    @Override
//    public void onBindParentViewHolder(OrderParentList_VH_DistOrder orderParentLIst_vh, int i, Object o) {
//        Log.i("objAdapter", String.valueOf(o));
//        OrderParentlist_Model_DistOrder orderParentlist_model = (OrderParentlist_Model_DistOrder) o;
//        orderParentLIst_vh._textview.setText(orderParentlist_model.getTitle());
//        orderParentLIst_vh_main = orderParentLIst_vh;
////        if(parentPosition == 2){
////            orderParentLIst_vh._textview.setVisibility(View.GONE);
////            Toast.makeText(context, "parentPosition"+parentPosition +" "+i, Toast.LENGTH_SHORT).show();
////        }else{
////            orderParentLIst_vh.layout_expandable.setVisibility(View.VISIBLE);
////
////            Toast.makeText(context, "parentPosition"+parentPosition +" "+i, Toast.LENGTH_SHORT).show();
////        }
//
//    }

//    @Override
//    public void onBindChildViewHolder(OrderChildList_VH_DistOrder orderChildList_vh, int i, Object o) {
//
//
//        Log.i("objAdapter", String.valueOf(o));
//        OrderChildlist_Model_DistOrder orderChildlist_model = (OrderChildlist_Model_DistOrder) o;
//
//        final OrderChildList_VH_DistOrder temp_orderChildList_vh = orderChildList_vh;
//        final int temp_i = i;
//        final OrderChildlist_Model_DistOrder temp_orderChildlist_model = orderChildlist_model;
//
//
//        orderChildList_vh.list_numberOFitems.setText("");
//        if (selectedProductsDataList != null && selectedProductsQuantityList != null) {
//            setQuantity(orderChildList_vh, orderChildlist_model);
//        }
//
//        orderChildList_vh.list_txt_products.setText(orderChildlist_model.getTitle());
//        orderChildList_vh.list_product_code_value.setText(orderChildlist_model.getCode());
//        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
//        if (orderChildlist_model.getUnitPrice() != null) {
//            String yourFormattedString1 = formatter1.format(Double.parseDouble(orderChildlist_model.getUnitPrice()));
//            orderChildList_vh.list_price_value.setText("Rs. " + yourFormattedString1);
//        }
//        String yourFormattedString2 = "0";
//        if (orderChildlist_model.getDiscountAmount() != null) {
//            yourFormattedString2 = formatter1.format(Double.parseDouble(orderChildlist_model.getDiscountAmount()));
//        } else {
//            yourFormattedString2 = formatter1.format(Double.parseDouble(orderChildlist_model.getUnitPrice()));
//        }
////        if (orderChildlist_model.getUOMTitle() != null)
//        orderChildList_vh.list_discount_value.setText("Rs. " + yourFormattedString2);
//        if (orderChildlist_model.getUOMTitle() != null)
//            orderChildList_vh.list_UOM_value.setText(orderChildlist_model.getUOMTitle());
//        if (orderChildlist_model.getPackSize() != null)
//            orderChildList_vh.list_pack_size_value.setText(orderChildlist_model.getPackSize());
//        TextWatcher textWatcher = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String str_quantity = String.valueOf(s);
////                Log.i("textChanged12", "check");
////                Log.i("textChanged11", String.valueOf(s));
//                if (String.valueOf(s).equals(""))
//                    str_quantity = "0";
//
//                if (temp_orderChildList_vh.list_txt_products.getText().equals(temp_orderChildlist_model.getTitle())) {
//                    if (Float.parseFloat(str_quantity) <= 0) {
//                        Toast.makeText(context, "Quantity must be greater than 0", Toast.LENGTH_LONG).show();
//                    }
//                    Log.i("textChanged", String.valueOf(temp_orderChildlist_model.getTitle()));
//                    Log.i("textChanged11", String.valueOf(temp_orderChildList_vh.list_txt_products.getText()));
//                    checkOutEnabler(temp_orderChildList_vh, temp_i, temp_orderChildlist_model, str_quantity);
//                }
//            }
//        };
//        orderChildList_vh.list_numberOFitems.addTextChangedListener(textWatcher);
////        orderChildList_vh.list_numberOFitems.removeTextChangedListener(textWatcher);
//    }

    private void setQuantity(OrderChildList_VH_DistOrder orderChildList_vh, OrderChildlist_Model_DistOrder orderChildlist_model) {
        if (selectedProductsQuantityList != null && selectedProductsDataList != null) {
            for (int j = 0; j < selectedProductsDataList.size(); j++) {
                if (selectedProductsDataList.get(j).getTitle().equals(orderChildlist_model.getTitle()) && selectedProductsDataList.get(j).getCode().equals(orderChildlist_model.getCode())) {
                    Log.i("foundItem", String.valueOf(orderChildlist_model.getTitle()));
                    orderChildList_vh.list_numberOFitems.setText(selectedProductsQuantityList.get(j));
                }
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

        // for (int i = 0; i < selectedProductsDataList.size(); i++)
            // Toast.makeText(context, selectedProductsDataList.get(i).getTitle() + " - " + selectedProductsQuantityList.get(i), Toast.LENGTH_LONG).show();

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
//        parentPosition = position;
//    }

//    @Override
//    public void onParentItemClickListener(int position) {
//        super.onParentItemClickListener(position);
////        Toast.makeText(context, "position: "+position +" parent position: "+parentPosition, Toast.LENGTH_SHORT).show();
//
//        Log.i("textviewparent-position", String.valueOf(position));
//        Log.i("textviewparent", "in parent item listener");
//        Log.i("textviewparent-listsize", String.valueOf(OrderParentList.size()));
////        parentPosition = position;
//        for(OrderParentList_VH_DistOrder orderParentListItem : OrderParentList) {
////            Log.i("textviewparent", String.valueOf(orderParentListItem._textview.getText()));
//            orderParentListItem.setExpanded(false);
////            if(OrderParentListItem._textview.equals())
//        }
//    }


}
