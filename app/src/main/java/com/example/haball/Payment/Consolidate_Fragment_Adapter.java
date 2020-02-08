package com.example.haball.Payment;

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

public class Consolidate_Fragment_Adapter extends RecyclerView.Adapter<Consolidate_Fragment_Adapter.ViewHolder> {

    private Consolidate_Fragment mContext;
    public String consolidate_heading, invoice_no_value,company_name_value,tv_consolidated_date,tv_amount_value, tv_amount_remvalue ,consolidate_status;

    public Consolidate_Fragment_Adapter(Consolidate_Fragment mContext, String consolidate_heading, String invoice_no_value, String company_name_value, String tv_consolidated_date, String tv_amount_value, String tv_amount_remvalue, String consolidate_status) {
        this.mContext = mContext;
        this.consolidate_heading = consolidate_heading;
        this.invoice_no_value = invoice_no_value;
        this.company_name_value = company_name_value;
        this.tv_consolidated_date = tv_consolidated_date;
        this.tv_amount_value = tv_amount_value;
        this.tv_amount_remvalue = tv_amount_remvalue;
        this.consolidate_status = consolidate_status;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view_inflate = LayoutInflater.from(mContext.getContext()).inflate(R.layout.payments_consolidate_recycler,parent,false);
        return new Consolidate_Fragment_Adapter.ViewHolder(view_inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_heading.setText(consolidate_heading);
        holder.invoice_no_value.setText(invoice_no_value);
        holder.company_name_value.setText(company_name_value);
        holder.tv_consolidated_date.setText(tv_consolidated_date);
        holder.tv_amount_value.setText(tv_amount_value);
        holder.tv_amount_remvalue.setText(tv_amount_remvalue);
        holder.consolidate_status.setText(consolidate_status);



    }

    @Override
    public int getItemCount() {
        return 1;
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
