package com.example.haball.Payment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haball.Distributor.ui.payments.Payments_Fragment;
import com.example.haball.Payment.Consolidate_Fragment;
import com.example.haball.R;
import com.example.haball.Retailor.ui.Dashboard.DashBoardFragment;

import java.util.List;

public class Consolidate_Fragment_Adapter extends RecyclerView.Adapter<Consolidate_Fragment_Adapter.ViewHolder> {

    private Context context;
    private List<ConsolidatePaymentsModel> consolidatePaymentsRequestList;

    public Consolidate_Fragment_Adapter(Context context, List<ConsolidatePaymentsModel> consolidatePaymentsRequestList) {
        this.context = context;
        this.consolidatePaymentsRequestList = consolidatePaymentsRequestList;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view_inflate = LayoutInflater.from(context).inflate(R.layout.payments_consolidate_recycler,parent,false);
        return new Consolidate_Fragment_Adapter.ViewHolder(view_inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int Total = Integer.parseInt(consolidatePaymentsRequestList.get(position).getTotalPrice())
                -Integer.parseInt(consolidatePaymentsRequestList.get(position).getPaidAmount());
        String string = consolidatePaymentsRequestList.get(position).getCreatedDate();
        String[] parts = string.split("T");
        String Date = parts[0];
        holder.tv_heading.setText(consolidatePaymentsRequestList.get(position).getCompanyName());
        holder.invoice_no_value.setText(consolidatePaymentsRequestList.get(position).getConsolidatedInvoiceNumber());
        holder.tv_consolidated_date.setText(Date);
        holder.tv_amount_value.setText(consolidatePaymentsRequestList.get(position).getTotalPrice());
        holder.tv_amount_remvalue.setText(String.valueOf(Total));
        if(consolidatePaymentsRequestList.get(position).getStatus().equals("0")) {
            holder.consolidate_status.setText("Pending");
        }
        else if (consolidatePaymentsRequestList.get(position).getStatus().equals("1"))
            holder.consolidate_status.setText("Unpaid");
        else if (consolidatePaymentsRequestList.get(position).getStatus().equals("2"))
            holder.consolidate_status.setText("Partially Paid");
        else if (consolidatePaymentsRequestList.get(position).getStatus().equals("3"))
            holder.consolidate_status.setText("Paid");
        else if (consolidatePaymentsRequestList.get(position).getStatus().equals("-1"))
            holder.consolidate_status.setText("Payment Processing");
    }

    @Override
    public int getItemCount() {
        return consolidatePaymentsRequestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_heading ,invoice_no_value,company_name_value,tv_consolidated_date,tv_amount_value, tv_amount_remvalue , consolidate_status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_heading = itemView.findViewById(R.id.consolidate_heading);
            invoice_no_value = itemView.findViewById(R.id.consolidate_invoice_no);
            company_name_value = itemView.findViewById(R.id.consolidate_company_name);
            tv_consolidated_date= itemView.findViewById(R.id.tv_consolidated_date);
            tv_amount_value = itemView.findViewById(R.id.consolidate_amount_value);
            tv_amount_remvalue = itemView.findViewById(R.id.tv_amount_remvalue);
            consolidate_status = itemView.findViewById(R.id.consolidate_sat);


        }
    }
}
