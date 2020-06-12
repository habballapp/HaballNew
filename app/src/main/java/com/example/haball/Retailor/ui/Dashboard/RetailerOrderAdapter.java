package com.example.haball.Retailor.ui.Dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haball.R;
import com.example.haball.Retailor.ui.RetailerOrder.RetailerViewOrder;

import org.json.JSONException;

import java.util.List;

public class RetailerOrderAdapter extends RecyclerView.Adapter<RetailerOrderAdapter.ViewHolder> {

    //    private PlaceholderFragment mContxt;
    private Context mContxt;
    private String heading, order_no_value, amount, status;
    private List<RetailerOrderModel> OrderList;

    public RetailerOrderAdapter(Context mContxt, List<RetailerOrderModel> orderList) {
        this.mContxt = mContxt;
        this.OrderList = orderList;
    }

//    public RetailerOrderAdapter(PlaceholderFragment placeholderFragment, String heading, String order_no_value, String amount, String status) {
//        this.mContxt = placeholderFragment;
//        this.heading = heading;
//        this.order_no_value = order_no_value;
//        this.amount = amount;
//        this.status = status;
//    }

    @NonNull
    @Override
    public RetailerOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContxt).inflate(R.layout.orders_layout, parent, false);
        return new RetailerOrderAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RetailerOrderAdapter.ViewHolder holder, final int position) {
//        holder.tv_heading.setText(heading);
//        holder.order_no_value.setText(order_no_value);
//        holder.tv_status.setText(status);
//        holder.tv_amount.setText(amount);

        holder.tv_heading.setText(OrderList.get(position).getCompanyName());
        holder.order_no_value.setText(OrderList.get(position).getOrderNumber());
        if (OrderList.get(position).getOrderStatusValue() != null)
            holder.tv_status.setText(OrderList.get(position).getOrderStatusValue());
        else if (OrderList.get(position).getStatus() != null)
            holder.tv_status.setText(OrderList.get(position).getStatus());
        holder.tv_amount.setText(OrderList.get(position).getTotalPrice());

        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(mContxt, view);
                if (OrderList.get(position).getOrderStatusValue() != null) {
                    if (OrderList.get(position).getOrderStatusValue().equals("Draft"))
                        setMenuDraft(popup, position);
                    else if (OrderList.get(position).getOrderStatusValue().equals("Cancelled"))
                        setMenuCancelled(popup, position);
                    else
                        setMenuAll(popup, position);
                } else if (OrderList.get(position).getStatus() != null) {
                    if (OrderList.get(position).getStatus().equals("Draft"))
                        setMenuDraft(popup, position);
                    else if (OrderList.get(position).getStatus().equals("Cancelled"))
                        setMenuCancelled(popup, position);
                    else
                        setMenuAll(popup, position);
                }
            }
        });
    }

    private void setMenuDraft(PopupMenu popup, final int position) {
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.dist_order_draft_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.orders_edit:
                        try {
                            editOrderDraft(mContxt, OrderList.get(position).getID(), OrderList.get(position).getOrderNumber());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Toast.makeText(mContxt, "View Order ID - " + OrderList.get(position).getOrderNumber(), Toast.LENGTH_LONG).show();
                        break;
                    case R.id.orders_delete:
                        try {
                            deleteOrderDraft(mContxt, OrderList.get(position).getID(), OrderList.get(position).getOrderNumber());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                                Toast.makeText(mContxt, "View Order ID - " + ID, Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });
        popup.show();

    }

    private void setMenuCancelled(PopupMenu popup, final int position) {
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.orders_fragment_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.orders_view:
                        String ID = OrderList.get(position).getID();
                        FragmentTransaction fragmentTransaction= ((FragmentActivity) mContxt).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container_ret, new RetailerViewOrder()).addToBackStack("tag");
                        fragmentTransaction.commit();
                        SharedPreferences OrderId = ((FragmentActivity) mContxt).getSharedPreferences("OrderId",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = OrderId.edit();
                        editor.putString("OrderId", OrderList.get(position).getID());
                        editor.putString("Status", OrderList.get(position).getOrderStatusValue());
                        editor.commit();

                        // Toast.makeText(mContxt, "View Order ID - " + ID, Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });
        popup.show();

    }

    private void setMenuAll(PopupMenu popup, final int position) {
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.dist_order_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.orders_view:
                        String ID = OrderList.get(position).getID();
                        FragmentTransaction fragmentTransaction= ((FragmentActivity)mContxt).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container_ret,new RetailerViewOrder()).addToBackStack("tag");
                        fragmentTransaction.commit();
                        SharedPreferences OrderId = ((FragmentActivity)mContxt).getSharedPreferences("OrderId",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = OrderId.edit();
                        editor.putString("OrderId", OrderList.get(position).getID());
                        editor.putString("Status", OrderList.get(position).getOrderStatusValue());
                        editor.putString("InvoiceStatus", String.valueOf(OrderList.get(position).getInvoiceStatus()));
                        Log.i("InvoiceStatus_Adapter", String.valueOf(OrderList.get(position).getInvoiceStatus()));
                        editor.commit();
                        break;
                    case R.id.orders_cancel:
                        String orderID = OrderList.get(position).getID();
                        try {
                            cancelOrder(mContxt, OrderList.get(position).getID(), OrderList.get(position).getOrderNumber());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                                Toast.makeText(mContxt, "View Order ID - " + ID, Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });
        popup.show();

    }

    private void cancelOrder(Context context, String ID, String OrderNumber) throws JSONException {
        CancelOrder cancelOrder = new CancelOrder();
        cancelOrder.cancelOrder(context, ID, OrderNumber);
    }

    private void deleteOrderDraft(Context context, String ID, String OrderNumber) throws JSONException {
        DeleteOrderDraft deleteDraft = new DeleteOrderDraft();
        deleteDraft.deleteDraft(context, ID, OrderNumber);
    }

    private void editOrderDraft(Context context, String ID, String OrderNumber) throws JSONException {
        EditOrderDraft editDraft = new EditOrderDraft();
        editDraft.editDraft(context, ID, OrderNumber);
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

    public void addListItem(List<RetailerOrderModel> list) {
        for (RetailerOrderModel plm : list) {
            OrderList.add(plm);
        }
        notifyDataSetChanged();
    }
}
