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

import java.util.List;

public class PaymentLedgerAdapter extends RecyclerView.Adapter<PaymentLedgerAdapter.ViewHolder> {
    private Context mContxt;
    private List<PaymentLedgerModel> PaymentLedgerList;

    public PaymentLedgerAdapter(Context context, List<PaymentLedgerModel> paymentLedgerList) {
        this.mContxt = context;
        this.PaymentLedgerList = paymentLedgerList;
    }

    @NonNull
    @Override
    public PaymentLedgerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContxt).inflate(R.layout.layout_payment_ledger,parent,false);
        return new PaymentLedgerAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentLedgerAdapter.ViewHolder holder, int position) {
        holder.tv_heading.setText(PaymentLedgerList.get(position).getCompanyName());
        holder.ledger_id_value.setText(PaymentLedgerList.get(position).getDocumentNumber());
        holder.document_type_value.setText(PaymentLedgerList.get(position).getDocumentType());
        if(!PaymentLedgerList.get(position).getDebitAmount().equals("0"))
            holder.transaction_value.setText(PaymentLedgerList.get(position).getDebitAmount());
        else{
            holder.transaction_value.setText(PaymentLedgerList.get(position).getCreditAmount());
            holder.transaction.setText("Credit");
        }

        holder.balance_value.setText(PaymentLedgerList.get(position).getBalanceAmount());
    }

    @Override
    public int getItemCount() {
        return PaymentLedgerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_heading, ledger_id_value, document_type_value, transaction_value,balance_value, transaction;
        public ImageButton menu_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            ledger_id_value = itemView.findViewById(R.id.ledger_id_value);
            document_type_value = itemView.findViewById(R.id.document_type_value);
            transaction_value = itemView.findViewById(R.id.transaction_value);
            balance_value = itemView.findViewById(R.id.balance_value);
            transaction = itemView.findViewById(R.id.transaction);
        }
    }
}
