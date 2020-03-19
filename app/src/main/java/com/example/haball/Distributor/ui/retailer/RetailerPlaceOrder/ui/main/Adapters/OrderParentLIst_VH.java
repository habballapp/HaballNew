package com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Adapters;


import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.haball.R;

public class OrderParentLIst_VH extends ParentViewHolder {
        public TextView _textview;
        public ImageView imageView;
    public OrderParentLIst_VH(View itemView) {
        super(itemView);
        _textview = (TextView) itemView.findViewById(R.id.orderName_retailer);
        imageView = itemView.findViewById(R.id.plus_icon);
    }
}
