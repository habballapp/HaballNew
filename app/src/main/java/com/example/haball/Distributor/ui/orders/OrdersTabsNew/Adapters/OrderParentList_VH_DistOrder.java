package com.example.haball.Distributor.ui.orders.OrdersTabsNew.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.haball.R;

public class OrderParentList_VH_DistOrder extends ParentViewHolder {
    public TextView _textview;
    public ImageView imageView;
    public OrderParentList_VH_DistOrder(View itemView) {
        super(itemView);
        _textview = (TextView) itemView.findViewById(R.id.orderName_retailer);
        imageView = itemView.findViewById(R.id.plus_icon);
    }
}
