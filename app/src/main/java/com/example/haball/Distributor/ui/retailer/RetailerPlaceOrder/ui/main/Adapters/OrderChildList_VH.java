package com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Adapters;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.example.haball.R;

public class OrderChildList_VH extends ChildViewHolder {
     public TextView option1 , option2;
    public OrderChildList_VH(View itemView) {
        super(itemView);
        option1 = itemView.findViewById(R.id.option1);
        option2 = itemView.findViewById(R.id.option2);

    }
}
