package com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Models.OrderChildlist_Model;
import com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Models.OrderParentlist_Model;
import com.example.haball.R;

import java.util.List;

public class ParentListAdapter extends ExpandableRecyclerAdapter<OrderParentLIst_VH ,OrderChildList_VH> {
    LayoutInflater inflater;

    public ParentListAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        inflater  = LayoutInflater.from(context);

    }

    @Override
    public OrderParentLIst_VH onCreateParentViewHolder(ViewGroup viewGroup) {
        View view  = inflater.inflate(R.layout.parentlist_retailer_order , viewGroup ,false);
        return  new OrderParentLIst_VH(view);

    }

    @Override
    public OrderChildList_VH onCreateChildViewHolder(ViewGroup viewGroup) {
        View view  = inflater.inflate(R.layout.childlist_retailer_order , viewGroup ,false);
        return  new OrderChildList_VH(view);

    }

    @Override
    public void onBindParentViewHolder(OrderParentLIst_VH orderParentLIst_vh, int i, Object o) {
        OrderParentlist_Model orderParentlist_model = (OrderParentlist_Model) o;
        orderParentLIst_vh._textview.setText(orderParentlist_model.getTitle());

    }

    @Override
    public void onBindChildViewHolder(OrderChildList_VH orderChildList_vh, int i, Object o) {
        OrderChildlist_Model orderChildlist_model = (OrderChildlist_Model) o;
        orderChildList_vh.option1.setText(orderChildlist_model.getOption1());
        orderChildList_vh.option2.setText(orderChildlist_model.getOption2());
    }
}
