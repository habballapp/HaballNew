package com.example.haball.Payment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haball.Distribution_Login.Distribution_Login;
import com.example.haball.Distributor.ui.payments.Payments_Fragment;
import com.example.haball.Distributor.ui.payments.ProofOfPaymentForm;
import com.example.haball.Distributor.ui.shipments.DistributorShipment_ViewDashboard;
import com.example.haball.Distributor.ui.shipments.Shipments_Fragments;
import com.example.haball.Payment.Consolidate_Fragment;
import com.example.haball.R;
import com.example.haball.Registration.Registration_Activity;
import com.example.haball.Retailor.ui.Dashboard.DashBoardFragment;

import java.text.DecimalFormat;
import java.util.List;

public class Consolidate_Fragment_Adapter extends RecyclerView.Adapter<Consolidate_Fragment_Adapter.ViewHolder> {

    private Context context;
    private List<ConsolidatePaymentsModel> consolidatePaymentsRequestList;
    private FragmentTransaction fragmentTransaction;

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

        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
        String yourFormattedString1 = formatter1.format(Integer.parseInt(consolidatePaymentsRequestList.get(position).getTotalPrice()));
        holder.tv_amount_value.setText(yourFormattedString1);

        DecimalFormat formatter2 = new DecimalFormat("#,###,##0.00");
        String yourFormattedString2 = formatter2.format(Integer.parseInt(String.valueOf(Total)));
        holder.tv_amount_remvalue.setText(yourFormattedString2);
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

        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(context, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.cosolidate_payment_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.consiladate_view:
                                Intent intent = new Intent(context, Consolidate_Payment_View.class);
                                context.startActivity(intent);
                                break;
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
        return consolidatePaymentsRequestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_heading ,invoice_no_value,company_name_value,tv_consolidated_date,tv_amount_value, tv_amount_remvalue , consolidate_status;
        public ImageButton menu_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_heading = itemView.findViewById(R.id.consolidate_heading);
            invoice_no_value = itemView.findViewById(R.id.consolidate_invoice_no);
            company_name_value = itemView.findViewById(R.id.consolidate_company_name);
            tv_consolidated_date= itemView.findViewById(R.id.tv_consolidated_date);
            tv_amount_value = itemView.findViewById(R.id.consolidate_amount_value);
            tv_amount_remvalue = itemView.findViewById(R.id.tv_amount_remvalue);
            consolidate_status = itemView.findViewById(R.id.consolidate_sat);
            menu_btn = itemView.findViewById(R.id.consolidate_menu_btn);


        }
    }
}
