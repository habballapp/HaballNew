package com.example.haball.Retailor.ui.Place_Order.ui.main.Adapters;


import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.haball.Distributor.ui.orders.OrdersTabsNew.ParentViewHolder;
import com.example.haball.R;

public class OrderParentLIst_VH extends ParentViewHolder {
    public TextView _textview;
    public ImageView imageView;
    public RelativeLayout layout_expandable, rl_orderName_retailer;
    public ImageView minus_icon;
    public RecyclerView subchlid_RV;
    public RelativeLayout rl_parentList;

    public OrderParentLIst_VH(View itemView) {
        super(itemView);
        _textview = (TextView) itemView.findViewById(R.id.orderName_retailer);
        imageView = itemView.findViewById(R.id.plus_icon);
        minus_icon = itemView.findViewById(R.id.minus_icon);
        subchlid_RV = itemView.findViewById(R.id.subchlid_RV);
        layout_expandable = itemView.findViewById(R.id.layout_expandable);
        rl_orderName_retailer = itemView.findViewById(R.id.rl_orderName_retailer);
        rl_parentList = itemView.findViewById(R.id.rl_parentList);
        minus_icon.setVisibility(View.GONE);
        View.OnClickListener plusMinusOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View itemView) {
                togglePlusMinusIcon();
            }
        };
//        _textview.setOnClickListener(plusMinusOnClick);
//        imageView.setOnClickListener(plusMinusOnClick);
//        minus_icon.setOnClickListener(plusMinusOnClick);
//        imageView.setOnClickListener(plusMinusOnClick);
        rl_orderName_retailer.setOnClickListener(plusMinusOnClick);
    }


    private void togglePlusMinusIcon() {
        if (isExpanded()) {
            collapseView();
            minus_icon.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
//            rl_orderName_retailer.setBackgroundResource(R.drawable.underline);
        } else {
            expandView();
            imageView.setVisibility(View.GONE);
            minus_icon.setVisibility(View.VISIBLE);
//            rl_orderName_retailer.setBackground(null);
        }

    }

}
