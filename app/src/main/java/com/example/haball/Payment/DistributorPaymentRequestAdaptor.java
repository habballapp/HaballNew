package com.example.haball.Payment;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haball.Distribution_Login.Distribution_Login;
import com.example.haball.Distributor.ui.payments.Payments_Fragment;
import com.example.haball.R;
import com.example.haball.Retailor.ui.Dashboard.DashBoardFragment;
import com.example.haball.Retailor.ui.Make_Payment.Payment_Summary;

import java.text.DecimalFormat;
import java.util.List;

public class DistributorPaymentRequestAdaptor extends RecyclerView.Adapter<DistributorPaymentRequestAdaptor.ViewHolder> {
    private Context context;
    private List<DistributorPaymentRequestModel> paymentsRequestList;
    private Button btn_update;
    public ImageButton btn_back;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_heading.setText(paymentsRequestList.get(position).getCompanyName());
        holder.payment_id_value.setText(paymentsRequestList.get(position).getPrePaidNumber());
        DecimalFormat formatter1 = new DecimalFormat("#,###,###.00");
        String yourFormattedString1 = formatter1.format(Integer.parseInt(paymentsRequestList.get(position).getPaidAmount()));
        holder.amount_value.setText(yourFormattedString1);
        if(paymentsRequestList.get(position).getStatus().equals("1"))
            holder.status_value.setText("Paid");
        else
            holder.status_value.setText("Unpaid");


        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(context, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.payment_request_menu_items, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.payment_request_edit:
                                Toast.makeText(context,"Edit Clicked",Toast.LENGTH_LONG).show();
                                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                                LayoutInflater inflater = LayoutInflater.from(context);
                                View view_popup = inflater.inflate(R.layout.edit_payment_request,null);
                                alertDialog.setView(view_popup);
                               Button btn_update = (Button) view_popup.findViewById(R.id.btn_payment_request_update);
                                btn_update.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        alertDialog.dismiss();
                                        Toast.makeText(context,"Update",Toast.LENGTH_LONG).show();
                                        final AlertDialog alertDialog1 = new AlertDialog.Builder(context).create();
                                        LayoutInflater inflater = LayoutInflater.from(context);
                                        View view_popup = inflater.inflate(R.layout.edit_request_payment_success, null);
                                         alertDialog1.setView(view_popup);
                                        ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.image_success);
                                        img_email.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                alertDialog1.dismiss();
                                            }
                                        });
                                        alertDialog1.show();

                                    }
                                });
                                ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.image_payment_request);
                                img_email.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dismiss();

                                    }
                                });
                                alertDialog.show();

                                break;
                            case R.id.payment_request_bank:
                                //handle menu2 click
                                Toast.makeText(context,"Bank Clicked",Toast.LENGTH_LONG).show();
                                final AlertDialog alertDialog2 = new AlertDialog.Builder(context).create();
                                LayoutInflater inflater2 = LayoutInflater.from(context);
                                View view_popup2 = inflater2.inflate(R.layout.payment_request_details,null);
                                alertDialog2.setView(view_popup2);
                                alertDialog2.show();


                                break;
                            case R.id.menu_delete:
                                //handle menu3 click
                                Toast.makeText(context,"Bank Clicked",Toast.LENGTH_LONG).show();
                             /*   final AlertDialog deleteAlert = new AlertDialog.Builder(mContxt).create();
                                LayoutInflater delete_inflater = LayoutInflater.from(mContxt);
                                View delete_alert = delete_inflater.inflate(R.layout.delete_alert, null);
                                deleteAlert.setView(delete_alert);
                                Button btn_delete = (Button) delete_alert.findViewById(R.id.btn_delete);
                                btn_delete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        deleteAlert.dismiss();
                                        AlertDialog.Builder delete_successAlert = new AlertDialog.Builder(mContxt);
                                        LayoutInflater delete_inflater = LayoutInflater.from(mContxt);
                                        View delete_success_alert = delete_inflater.inflate(R.layout.delete_success, null);
                                        delete_successAlert.setView(delete_success_alert);
                                        delete_successAlert.show();
                                    }
                                });
                                deleteAlert.show();*/
                                break;


                            case R.id.payment_request_ebay:
                                Toast.makeText(context,"Ebay Clicked",Toast.LENGTH_LONG).show();

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
