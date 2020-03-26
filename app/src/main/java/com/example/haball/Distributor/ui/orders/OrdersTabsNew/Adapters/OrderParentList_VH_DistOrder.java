package com.example.haball.Distributor.ui.orders.OrdersTabsNew.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
//import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;
import com.example.haball.R;

public class OrderParentList_VH_DistOrder extends ParentViewHolder {
    public TextView _textview;
    public ImageView imageView;
    public RecyclerView subchlid_RV;
    public RelativeLayout layout_expandable;
    public OrderParentList_VH_DistOrder(View itemView) {
        super(itemView);
        _textview = (TextView) itemView.findViewById(R.id.orderName_retailer);
        imageView = itemView.findViewById(R.id.plus_icon);
        subchlid_RV = itemView.findViewById(R.id.subchlid_RV);
        layout_expandable = itemView.findViewById(R.id.layout_expandable);
    }
}
