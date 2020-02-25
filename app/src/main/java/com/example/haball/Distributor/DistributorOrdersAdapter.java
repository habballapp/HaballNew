package com.example.haball.Distributor;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haball.Distributor.ui.main.PlaceholderFragment;
import com.example.haball.R;

import java.util.ArrayList;
import java.util.List;

public class DistributorOrdersAdapter extends RecyclerView.Adapter<DistributorOrdersAdapter.ViewHolder> {

//    private PlaceholderFragment mContxt;
    private Context mContxt;
    private String heading, order_no_value, amount, status;
    private List<DistributorOrdersModel> OrderList;

    public DistributorOrdersAdapter(Context mContxt, List<DistributorOrdersModel> orderList) {
        this.mContxt = mContxt;
        this.OrderList = orderList;
    }

//    public DistributorOrdersAdapter(PlaceholderFragment placeholderFragment, String heading, String order_no_value, String amount, String status) {
//        this.mContxt = placeholderFragment;
//        this.heading = heading;
//        this.order_no_value = order_no_value;
//        this.amount = amount;
//        this.status = status;
//    }

    @NonNull
    @Override
    public DistributorOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContxt).inflate(R.layout.orders_layout,parent,false);
        return new DistributorOrdersAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DistributorOrdersAdapter.ViewHolder holder, final int position) {
//        holder.tv_heading.setText(heading);
//        holder.order_no_value.setText(order_no_value);
//        holder.tv_status.setText(status);
//        holder.tv_amount.setText(amount);

        holder.tv_heading.setText(OrderList.get(position).getCompanyName());
        holder.order_no_value.setText(OrderList.get(position).getOrderNumber());
        holder.tv_status.setText(OrderList.get(position).getOrderStatusValue());
        holder.tv_amount.setText(OrderList.get(position).getTotalPrice());

        final int finalPosition = position;
        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(mContxt, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.orders_fragment_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.orders_view:
                                String ID = OrderList.get(position).getID();
                                Toast.makeText(mContxt, "View Order ID - " + ID, Toast.LENGTH_LONG).show();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return OrderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_heading, order_no_value, tv_status, tv_amount;
        public ImageButton menu_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            order_no_value = itemView.findViewById(R.id.order_no_value);
            tv_status = itemView.findViewById(R.id.status_value);
            tv_amount = itemView.findViewById(R.id.amount_value);
            menu_btn = itemView.findViewById(R.id.menu_btn_orders);
        }
    }
}
