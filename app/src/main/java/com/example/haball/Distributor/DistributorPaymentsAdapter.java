package com.example.haball.Distributor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.example.haball.Distributor.ui.main.PlaceholderFragment;
import com.example.haball.R;

import java.util.ArrayList;
import java.util.List;

public class DistributorPaymentsAdapter extends RecyclerView.Adapter<DistributorPaymentsAdapter.ViewHolder> {
    private Context mContxt;
    List<DistributorPaymentsModel> paymentsList;

    public DistributorPaymentsAdapter(Context context, List<DistributorPaymentsModel> paymentsList) {
        this.mContxt = context;
        this.paymentsList = paymentsList;
        Log.i("Payments List => ", String.valueOf(paymentsList));
    }

    @NonNull
    @Override
    public DistributorPaymentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContxt).inflate(R.layout.payments_layout,parent,false);
        return new DistributorPaymentsAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DistributorPaymentsAdapter.ViewHolder holder, int position) {
        holder.tv_heading.setText(paymentsList.get(position).getName());
        holder.tv_payment_id.setText(paymentsList.get(position).getPrePaidNumber());
        holder.tv_amount.setText(paymentsList.get(position).getPaidAmount());

        if(paymentsList.get(position).getStatus().equals("1")){
            holder.tv_status.setText("Paid");
        }
        else{
            holder.tv_status.setText("Unpaid");
        }
    }

    @Override
    public int getItemCount() {
        return paymentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_heading, tv_payment_id, tv_status, tv_amount;
        public ImageButton menu_btn;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            tv_payment_id = itemView.findViewById(R.id.payment_id_value);
            tv_status = itemView.findViewById(R.id.status_value);
            tv_amount = itemView.findViewById(R.id.amount_value);
            menu_btn = itemView.findViewById(R.id.menu_btn);
        }
    }
}
