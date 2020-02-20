package com.example.haball.Retailor.ui.Dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haball.R;

import java.text.DecimalFormat;
import java.util.List;


public class RetailerPaymentAdapter extends RecyclerView.Adapter<RetailerPaymentAdapter.ViewHolder> {
    private Context context;
    private List<RetailerPaymentModel> paymentsList;

    public RetailerPaymentAdapter(Context context, List<RetailerPaymentModel> paymentsList) {
        this.context = context;
        this.paymentsList = paymentsList;
    }


    @NonNull
    @Override
    public RetailerPaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.retailer_payments,parent,false);
        return new RetailerPaymentAdapter.ViewHolder(view_inflate);    }

    @Override
    public void onBindViewHolder(@NonNull RetailerPaymentAdapter.ViewHolder holder, int position) {
        holder.tv_heading.setText(paymentsList.get(position).getCompanyName());
        holder.tv_payment_id.setText(paymentsList.get(position).getPrePaidNumber());

        DecimalFormat formatter1 = new DecimalFormat("#,###,###.00");
        String yourFormattedString1 = formatter1.format(Integer.parseInt(paymentsList.get(position).getPaidAmount()));
        holder.tv_amount.setText(yourFormattedString1);

        if(paymentsList.get(position).getStatus().equals("0")){
            holder.tv_status.setText("Paid");
        }
        else{
            holder.tv_status.setText("Unpaid");
        }

        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(context, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.payment_invoice_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.view_invoice:
                                Toast.makeText(context,"View payments",Toast.LENGTH_LONG).show();
//                                FragmentTransaction fragmentTransaction= ((FragmentActivity)mContxt).getSupportFragmentManager().beginTransaction();
//                                fragmentTransaction.add(R.id.main_container,new Distributor_Invoice_DashBoard());
//                                fragmentTransaction.commit();

                                break;
                            case R.id.view_pdf:
                                Toast.makeText(context,"View PDF",Toast.LENGTH_LONG).show();
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
        return paymentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_heading, tv_payment_id, tv_status, tv_amount;
        public ImageButton menu_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            tv_payment_id = itemView.findViewById(R.id.payment_id_value);
            tv_status = itemView.findViewById(R.id.status_value);
            tv_amount = itemView.findViewById(R.id.amount_value);
            menu_btn = itemView.findViewById(R.id.menu_btn);

        }
    }
}
