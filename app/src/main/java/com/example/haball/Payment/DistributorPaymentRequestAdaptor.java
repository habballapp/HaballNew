package com.example.haball.Payment;

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

public class DistributorPaymentRequestAdaptor extends RecyclerView.Adapter<DistributorPaymentRequestAdaptor.ViewHolder> {
    private Payments_Fragment mContext;
    private DashBoardFragment mContext1;
    private String heading, ledgerid, doctype, transaction, balance;
    public DistributorPaymentRequestAdaptor(Payments_Fragment requestPayment, String heading, String ledgerid, String doctype, String transaction, String balance) {
        this.mContext = requestPayment;
        this.heading = heading;
        this.ledgerid = ledgerid;
        this.doctype = doctype;
        this.transaction = transaction;
        this.balance = balance;
    }
//
//    public DistributorPaymentRequestAdaptor(DashBoardFragment dashBoardFragment, String heading, String ledgerid, String invoice, String transaction, String balance) {
//        this.mContext1 = dashBoardFragment;
//        this.heading = heading;
//        this.ledgerid = ledgerid;
//        this.doctype = doctype;
//        this.transaction = transaction;
//        this.balance = balance;
//    }
////
//    public DistributorPaymentRequestAdaptor(Payment_Summary payment_summary, String s, String s1, String invoice, String s2, String s3) {
//    }

    @NonNull
    @Override
    public DistributorPaymentRequestAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view_inflate = LayoutInflater.from(mContxt).inflate(R.layout.distributerorder_shoprecycler,parent,false);
        View view_inflate = LayoutInflater.from(mContext.getContext()).inflate(R.layout.layout_payment_ledger,parent,false);
        return new DistributorPaymentRequestAdaptor.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_heading.setText(heading);
        holder.ledger_id_value.setText(ledgerid);
        holder.document_type_value.setText(doctype);
        holder.transaction_value.setText(transaction);
        holder.balance_value.setText(balance);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_heading, ledger_id_value, document_type_value, transaction_value,balance_value;
        public ImageButton menu_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            ledger_id_value = itemView.findViewById(R.id.ledger_id_value);
            document_type_value = itemView.findViewById(R.id.document_type_value);
            transaction_value = itemView.findViewById(R.id.transaction_value);
            balance_value = itemView.findViewById(R.id.balance_value);
        }
    }
}
