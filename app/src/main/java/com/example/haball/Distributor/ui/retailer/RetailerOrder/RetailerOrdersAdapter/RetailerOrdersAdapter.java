package com.example.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersAdapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.example.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersModel.RetailerOrdersModel;
import com.example.haball.Distributor.ui.retailer.RetailerOrder.RetailerViewOrder;
import com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.RetailerPlaceOrder;
import com.example.haball.R;

import java.text.DecimalFormat;
import java.util.List;

public class RetailerOrdersAdapter extends RecyclerView.Adapter<RetailerOrdersAdapter.ViewHolder> {
    private Context context;
    private List<RetailerOrdersModel> OrdersList;

    public RetailerOrdersAdapter(Context context, List<RetailerOrdersModel> ordersList) {
        this.context = context;
        this.OrdersList = ordersList;
    }

    @NonNull
    @Override
    public RetailerOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.layout_retailer_orders_dashboard, parent, false);
        return new RetailerOrdersAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RetailerOrdersAdapter.ViewHolder holder, int position) {
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
        holder.tv_status.setText(OrdersList.get(position).getOrderStatus());
        final int finalPosition = position;
        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(context, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.payment_dashboard_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.view_payment:
//                                Toast.makeText(context, "View order - " + OrdersList.get(finalPosition).getOrderId(), Toast.LENGTH_LONG).show();
//                                FragmentTransaction fragmentTransaction= ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
//                                fragmentTransaction.add(R.id.main_container,new Distributor_Invoice_DashBoard());
//                                fragmentTransaction.commit();
//                                Toast.makeText(mContext,"Popup",Toast.LENGTH_LONG).show();
                                FragmentTransaction fragmentTransaction= ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.add(R.id.main_container,new RetailerViewOrder());
                                fragmentTransaction.commit();
                                SharedPreferences OrderId = ((FragmentActivity)context).getSharedPreferences("OrderId",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = OrderId.edit();
                                editor.putString("OrderId", OrdersList.get(finalPosition).getOrderId());
                                editor.putString("Status", OrdersList.get(finalPosition).getOrderStatus());
                                editor.commit();
                                break;

                            case R.id.view_payment_cancel:
                                // Toast.makeText(context,"Cancel",Toast.LENGTH_SHORT).show();
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
