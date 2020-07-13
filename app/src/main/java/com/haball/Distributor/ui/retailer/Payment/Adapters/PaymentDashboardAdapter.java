package com.haball.Distributor.ui.retailer.Payment.Adapters;

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

import com.haball.Distributor.ui.retailer.Payment.Models.Dist_Retailer_Dashboard_Model;
import com.haball.Distributor.ui.retailer.Payment.ViewPayment.RetailerPaymentView;
import com.haball.R;

import java.text.DecimalFormat;
import java.util.List;

public class PaymentDashboardAdapter extends RecyclerView.Adapter<PaymentDashboardAdapter.PaymentDashboardVH> {
    private Context context;
    private List<Dist_Retailer_Dashboard_Model> paymentsList;
    private FragmentTransaction fragmentTransaction;

    public PaymentDashboardAdapter(Context context, List<Dist_Retailer_Dashboard_Model> paymentsList) {
        this.context = context;
        this.paymentsList = paymentsList;
    }
    @Override
    public PaymentDashboardVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.dist_retailer_payments,parent,false);
        return new PaymentDashboardAdapter.PaymentDashboardVH(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentDashboardVH holder, final int position) {
        holder.tv_heading.setText(paymentsList.get(position).getCompanyName());
        holder.tv_payment_id.setText(paymentsList.get(position).getInvoiceNumber());

        DecimalFormat formatter1 = new DecimalFormat("#,###,###.00");
        String yourFormattedString1 = formatter1.format(Double.parseDouble(paymentsList.get(position).getTotalPrice()));
        holder.tv_amount.setText("Rs. " +yourFormattedString1);

//        if(paymentsList.get(position).getStatus().equals("0")){
//            holder.tv_status.setText("Paid");
//        }
//        else{
//            holder.tv_status.setText("Unpaid");
//        }
        holder.tv_status.setText(paymentsList.get(position).getInvoiceStatusValue());
        final int finalPosition = position;
        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (paymentsList.get(position).getInvoiceStatusValue().equals("Pending") || paymentsList.get(position).getInvoiceStatusValue().equals("Unpaid")) {
                    final PopupMenu popup = new PopupMenu(context, view);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.payment_dashboard_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.view_payment:
                                    FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.add(R.id.main_container, new RetailerPaymentView()).addToBackStack("Tag");
                                    fragmentTransaction.commit();
                                    SharedPreferences PaymentId = ((FragmentActivity) context).getSharedPreferences("PaymentId",
                                            Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = PaymentId.edit();
                                    editor.putString("PaymentId", paymentsList.get(finalPosition).getRetailerInvoiceId());
                                    editor.putString("Status", paymentsList.get(finalPosition).getInvoiceStatusValue());
                                    editor.putString("IsEditable", paymentsList.get(finalPosition).getIsEditable());
                                    editor.commit();

                                    break;
                                case R.id.view_payment_cancel:
                                    Toast.makeText(context, "Cancel button pressed", Toast.LENGTH_SHORT).show();
                            }
                            return false;
                        }
                    });
                    popup.show();

                }
                else {
                    final PopupMenu popup = new PopupMenu(context, view);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.payment_dashboard_menu2, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.view_payment:
                                    Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();

                                 FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.add(R.id.main_container, new RetailerPaymentView()).addToBackStack("Tag");
                                    fragmentTransaction.commit();
                                    SharedPreferences PaymentId = ((FragmentActivity) context).getSharedPreferences("PaymentId",
                                            Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = PaymentId.edit();
                                    editor.putString("PaymentId", paymentsList.get(finalPosition).getRetailerInvoiceId());
                                    editor.putString("Status", paymentsList.get(finalPosition).getInvoiceStatusValue());
                                    editor.putString("IsEditable", paymentsList.get(finalPosition).getIsEditable());
                                    editor.commit();

                                    break;
                            }
                            return false;
                        }
                    });
                    popup.show();


                }

            }


        });
    }


    @Override
    public int getItemCount() {
        return paymentsList.size();
    }

    public static class  PaymentDashboardVH  extends  RecyclerView.ViewHolder {
         TextView tv_heading, tv_payment_id, tv_status, tv_amount;
        public ImageButton menu_btn;
        public PaymentDashboardVH(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            tv_payment_id = itemView.findViewById(R.id.dist_ret_payment_id_value);
            tv_status = itemView.findViewById(R.id.status_value);
            tv_amount = itemView.findViewById(R.id.dist_ret_amount_value);
            menu_btn = itemView.findViewById(R.id.menu_btn);

        }


    }
}
