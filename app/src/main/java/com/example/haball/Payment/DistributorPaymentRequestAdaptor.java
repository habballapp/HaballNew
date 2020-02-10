package com.example.haball.Payment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haball.Distributor.ui.payments.Payments_Fragment;
import com.example.haball.R;
import com.example.haball.Retailor.ui.Dashboard.DashBoardFragment;
import com.example.haball.Retailor.ui.Make_Payment.Payment_Summary;

import java.text.DecimalFormat;
import java.util.List;

public class DistributorPaymentRequestAdaptor extends RecyclerView.Adapter<DistributorPaymentRequestAdaptor.ViewHolder> {
    private Context context;
    private List<DistributorPaymentRequestModel> paymentsRequestList;

    public DistributorPaymentRequestAdaptor(Context context, List<DistributorPaymentRequestModel> paymentsRequestList) {
        this.context = context;
        this.paymentsRequestList = paymentsRequestList;
    }

    @NonNull
    @Override
    public DistributorPaymentRequestAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.payments_layout,parent,false);
        return new DistributorPaymentRequestAdaptor.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_heading.setText(paymentsRequestList.get(position).getCompanyName());
        holder.payment_id_value.setText(paymentsRequestList.get(position).getPrePaidNumber());
        DecimalFormat formatter1 = new DecimalFormat("#,###,###.00");
        String yourFormattedString1 = formatter1.format(Integer.parseInt(paymentsRequestList.get(position).getPaidAmount()));
        holder.amount_value.setText(yourFormattedString1);
        if(paymentsRequestList.get(position).getStatus().equals("1"))
            holder.status_value.setText("Paid");
        else
            holder.status_value.setText("Unpaid");
    }

    @Override
    public int getItemCount() {
        return paymentsRequestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_heading, payment_id_value, amount_value,status_value;
        public ImageButton menu_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            payment_id_value = itemView.findViewById(R.id.payment_id_value);
            amount_value = itemView.findViewById(R.id.amount_value);
            status_value = itemView.findViewById(R.id.status_value);
            menu_btn = itemView.findViewById(R.id.menu_btn);
        }
    }
}
