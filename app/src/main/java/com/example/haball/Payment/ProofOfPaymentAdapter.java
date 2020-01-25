package com.example.haball.Payment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haball.R;


public class ProofOfPaymentAdapter extends RecyclerView.Adapter<ProofOfPaymentAdapter.ViewHolder> {
    private Context mContxt;
    private ProofOfPaymentDashboard proofOfPaymentDashboard;
    private String status, popid, createdDate, mode, payid;

    public ProofOfPaymentAdapter(ProofOfPaymentDashboard proofOfPaymentDashboard, String status, String popid, String createdDate, String mode, String payid) {
        mContxt = proofOfPaymentDashboard;
        this.status = status;
        this.popid = popid;
        this.createdDate = createdDate;
        this.mode = mode;
        this.payid = payid;

    }

    @NonNull
    @Override
    public ProofOfPaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContxt).inflate(R.layout.layout_proof_of_payments,parent,false);
        return new ProofOfPaymentAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProofOfPaymentAdapter.ViewHolder holder, int position) {
        holder.tv_status.setText(status);
        holder.payment_id_value.setText(payid);
        holder.pop_id_value.setText(popid);
        holder.created_date_value.setText(createdDate);
        holder.payment_mode_value.setText(mode);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_status, payment_id_value, pop_id_value, created_date_value,payment_mode_value;
        public ImageButton menu_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_status = itemView.findViewById(R.id.tv_status);
            payment_id_value = itemView.findViewById(R.id.payment_id_value);
            pop_id_value = itemView.findViewById(R.id.pop_id_value);
            created_date_value = itemView.findViewById(R.id.created_date_value);
            payment_mode_value = itemView.findViewById(R.id.payment_mode_value);
        }
    }
}
