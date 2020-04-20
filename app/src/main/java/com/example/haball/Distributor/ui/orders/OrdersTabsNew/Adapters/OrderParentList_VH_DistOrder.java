package com.example.haball.Distributor.ui.orders.OrdersTabsNew.Adapters;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

//import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;
import com.example.haball.Distributor.ui.orders.OrdersTabsNew.ParentViewHolder;
import com.example.haball.R;

public class OrderParentList_VH_DistOrder extends ParentViewHolder {
    public TextView _textview;
    public ImageView imageView;
    public ImageView minus_icon;
    public RecyclerView subchlid_RV;
    public RelativeLayout layout_expandable, rl_orderName_retailer;
    public OrderParentList_VH_DistOrder(View itemView) {
        super(itemView);
        _textview = (TextView) itemView.findViewById(R.id.orderName_retailer);
        rl_orderName_retailer = itemView.findViewById(R.id.rl_orderName_retailer);
        imageView = itemView.findViewById(R.id.plus_icon);
        minus_icon = itemView.findViewById(R.id.minus_icon);
        subchlid_RV = itemView.findViewById(R.id.subchlid_RV);
        layout_expandable = itemView.findViewById(R.id.layout_expandable);
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
        if(isExpanded()){
            imageView.setVisibility(View.VISIBLE);
            minus_icon.setVisibility(View.GONE);
            collapseView();
        } else {
            imageView.setVisibility(View.GONE);
            minus_icon.setVisibility(View.VISIBLE);
            expandView();
        }

    }

}
