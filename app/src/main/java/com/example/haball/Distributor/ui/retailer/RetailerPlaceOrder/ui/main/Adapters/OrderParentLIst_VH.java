package com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Adapters;


import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.example.haball.R;

public class OrderParentLIst_VH extends ParentViewHolder {
        public TextView _textview;
        public ImageView imageView;
        public RelativeLayout expanded_layout;

    public OrderParentLIst_VH(View itemView) {
        super(itemView);
        _textview = (TextView) itemView.findViewById(R.id.orderName_retailer);
        imageView = itemView.findViewById(R.id.plus_icon);
        expanded_layout = itemView.findViewById(R.id.layout_expandable);

    }
}
