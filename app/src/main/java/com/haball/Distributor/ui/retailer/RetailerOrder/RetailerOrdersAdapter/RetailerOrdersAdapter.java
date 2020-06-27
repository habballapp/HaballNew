package com.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersAdapter;

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


import com.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersModel.RetailerOrdersModel;
import com.haball.Distributor.ui.retailer.RetailerOrder.RetailerViewOrder;
import com.haball.R;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class RetailerOrdersAdapter extends RecyclerView.Adapter<RetailerOrdersAdapter.ViewHolder> {
    private Context context;
    private List<RetailerOrdersModel> OrdersList;
    private HashMap<String, String> OrderStatusKVP;

    public RetailerOrdersAdapter(Context context, List<RetailerOrdersModel> ordersList, HashMap<String, String> orderStatusKVP) {
        this.context = context;
        this.OrdersList = ordersList;
        this.OrderStatusKVP = orderStatusKVP;
    }

    @NonNull
    @Override
    public RetailerOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.layout_retailer_orders_dashboard, parent, false);
        return new RetailerOrdersAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RetailerOrdersAdapter.ViewHolder holder, final int position) {
        holder.tv_heading.setText(OrdersList.get(position).getRetailer());
        holder.tv_order_no_value.setText(OrdersList.get(position).getOrderNumber());

        DecimalFormat formatter1 = new DecimalFormat("#,###,###.00");
        String yourFormattedString1 = formatter1.format(Double.parseDouble(OrdersList.get(position).getTotalAmount()));
        holder.tv_amount.setText(yourFormattedString1);

//        if(paymentsList.get(position).getStatus().equals("0")){
//            holder.tv_status.setText("Paid");
//        }
//        else{
//            holder.tv_status.setText("Unpaid");
//        }
        holder.tv_status.setText(OrderStatusKVP.get(OrdersList.get(position).getOrderStatus()));
        final int finalPosition = position;
        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(context, view);
                MenuInflater inflater = popup.getMenuInflater();
                if (OrderStatusKVP.get(OrdersList.get(position).getOrderStatus()).equals("Approved"))
                    inflater.inflate(R.menu.dist_order_menu, popup.getMenu());
                else if (OrderStatusKVP.get(OrdersList.get(position).getOrderStatus()).equals("Cancelled"))
                    inflater.inflate(R.menu.cosolidate_payment_menu, popup.getMenu());
                else if (OrderStatusKVP.get(OrdersList.get(position).getOrderStatus()).equals("Draft"))
                    inflater.inflate(R.menu.dist_order_draft_menu, popup.getMenu());
                else if (OrderStatusKVP.get(OrdersList.get(position).getOrderStatus()).equals("Pending"))
                    inflater.inflate(R.menu.pending_order_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.consiladate_view:
                            case R.id.view_payment:
                            case R.id.orders_view:
                                viewOrder(position);
                                break;
                            case R.id.view_payment_cancel:
                            case R.id.orders_cancel:
                                String orderID = OrdersList.get(position).getID();
                                cancelOrder(context, OrdersList.get(position).getOrderId(), OrdersList.get(position).getOrderNumber());

                        }
                        return false;
                    }
                });
                popup.show();
            }


        });

    }

    private void viewOrder(int finalPosition) {
        FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, new RetailerViewOrder());
        fragmentTransaction.commit();
        SharedPreferences OrderId = ((FragmentActivity) context).getSharedPreferences("OrderId",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = OrderId.edit();
        editor.putString("OrderId", OrdersList.get(finalPosition).getOrderId());
        editor.putString("Status", OrdersList.get(finalPosition).getOrderStatus());
        editor.commit();

    }

    private void cancelOrder(Context context, String ID, String OrderNumber) {

        RetailerCancelOrder cancelOrder = new RetailerCancelOrder();
        try {
            cancelOrder.cancelOrder(context, ID, OrderNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return OrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_heading, tv_order_no_value, tv_status, tv_amount;
        public ImageButton menu_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            tv_order_no_value = itemView.findViewById(R.id.order_no_value);
            tv_status = itemView.findViewById(R.id.status_value);
            tv_amount = itemView.findViewById(R.id.amount_value);
            menu_btn = itemView.findViewById(R.id.menu_btn_orders);
        }
    }
}
