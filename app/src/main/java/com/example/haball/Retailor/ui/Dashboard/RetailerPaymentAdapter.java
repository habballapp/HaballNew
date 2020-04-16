package com.example.haball.Retailor.ui.Dashboard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haball.Distributor.ui.payments.PaymentScreen3Fragment;
import com.example.haball.Distributor.ui.payments.ViewVoucherRequest;
import com.example.haball.R;
import com.example.haball.Retailer_Login.RetailerLogin;
import com.example.haball.Retailor.RetailorDashboard;
import com.example.haball.Retailor.ui.Make_Payment.EditPaymentRequestFragment;
import com.example.haball.Retailor.ui.Make_Payment.PaymentScreen3Fragment_Retailer;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


public class RetailerPaymentAdapter extends RecyclerView.Adapter<RetailerPaymentAdapter.ViewHolder> {
    private Context context;
    private List<RetailerPaymentModel> paymentsList;
    private FragmentTransaction fragmentTransaction;

    public RetailerPaymentAdapter(Context context, List<RetailerPaymentModel> paymentsList) {
        this.context = context;
        this.paymentsList = paymentsList;
    }

    @NonNull
    @Override
    public RetailerPaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.retailer_payments, parent, false);
        return new RetailerPaymentAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RetailerPaymentAdapter.ViewHolder holder, final int position) {
        holder.tv_heading.setText(paymentsList.get(position).getCompanyName());
        holder.tv_payment_id.setText(paymentsList.get(position).getInvoiceNumber());

        DecimalFormat formatter1 = new DecimalFormat("#,###,###.00");
        String yourFormattedString1 = formatter1.format(Double.parseDouble(paymentsList.get(position).getTotalPrice()));
        holder.tv_amount.setText(yourFormattedString1);

//        if(paymentsList.get(position).getStatus().equals("0")){
//            holder.tv_status.setText("Paid");
//        }
//        else{
//            holder.tv_status.setText("Unpaid");
//        }
        holder.tv_status.setText(paymentsList.get(position).getStatus());

        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (paymentsList.get(position).getIsEditable().equals("0")) {
                    final PopupMenu popup = new PopupMenu(context, view);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.payment_invoice_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.view_invoice:
                                    // Toast.makeText(context, "View payments", Toast.LENGTH_LONG).show();
//                                FragmentTransaction fragmentTransaction= ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
//                                fragmentTransaction.add(R.id.main_container,new Distributor_Invoice_DashBoard());
//                                fragmentTransaction.commit();

                                    break;
                                case R.id.view_pdf:
                                    // Toast.makeText(context, "View PDF", Toast.LENGTH_LONG).show();
                                    break;
                            }
                            return false;
                        }
                    });
                    popup.show();
                } else if (paymentsList.get(position).getIsEditable().equals("1")) {
                    if (paymentsList.get(position).getStatus().equals("Un-Paid")) {
                        setUnpaidPaymentMenu(position, view);
                    } else if (paymentsList.get(position).getStatus().equals("Paid")) {
                        setPaidPaymentMenu(position, view);
                    }
                }
            }
        });
    }

    private void setUnpaidPaymentMenu(final int position, View view) {
        final PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.retailer_payment_action_buttons, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.view_retailer_payment:
//                        Toast.makeText(context, "Edit Clicked", Toast.LENGTH_LONG).show();
//                        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//                        LayoutInflater inflater = LayoutInflater.from(context);
//                        View view_popup = inflater.inflate(R.layout.edit_payment_request, null);
//                        alertDialog.setView(view_popup);
//                        Button btn_update = (Button) view_popup.findViewById(R.id.btn_payment_request_update);
//                        btn_update.setOnClickListener(new View.OnClickListener() {
//                            public void onClick(View v) {
//                                alertDialog.dismiss();
//                                //    Toast.makeText(context,"Update",Toast.LENGTH_LONG).show();
//                                final AlertDialog alertDialog1 = new AlertDialog.Builder(context).create();
//                                LayoutInflater inflater = LayoutInflater.from(context);
//                                View view_popup = inflater.inflate(R.layout.edit_request_payment_success, null);
//                                alertDialog1.setView(view_popup);
//                                ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.image_success);
//                                img_email.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        alertDialog1.dismiss();
//                                    }
//                                });
//                                alertDialog1.show();
//
//                            }
//                        });
//                        ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.image_payment_request);
//                        img_email.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                alertDialog.dismiss();
//
//                            }
//                        });
//                        alertDialog.show();
                        SharedPreferences PrePaidNumberEdit = context.getSharedPreferences("PrePaidNumber",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorEdit = PrePaidNumberEdit.edit();
                        editorEdit.putString("PrePaidNumber", paymentsList.get(position).getInvoiceNumber());
                        editorEdit.putString("PrePaidId", paymentsList.get(position).getRetailerInvoiceId());
                        editorEdit.putString("CompanyName", paymentsList.get(position).getCompanyName());
                        editorEdit.putString("Amount", paymentsList.get(position).getTotalPrice());
                        editorEdit.apply();

                        FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container_ret, new PaymentScreen3Fragment_Retailer()).addToBackStack("Tag");
                        fragmentTransaction.commit();

                        break;
                    case R.id.pay_by_retailer:
                        //handle menu3 click
                        Toast.makeText(context, "pay by", Toast.LENGTH_LONG).show();
//                        String paymentId = paymentsList.get(position).getID();
//                        deletePayment(context, paymentsList.get(position).getRetailerInvoiceId(), paymentsList.get(position).getInvoiceNumber());


                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    private void deletePayment(Context context, String retailerInvoiceId, String invoiceNumber) {

        PaymentDeleteOrder delete = new PaymentDeleteOrder();
        delete.deleteOrder(context, retailerInvoiceId, invoiceNumber);
        Intent login_intent = new Intent(context, RetailorDashboard.class);
        context.startActivity(login_intent);
        ((FragmentActivity) context).finish();


    }


    private void setPaidPaymentMenu(final int position, View view) {
        final PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.retailer_dashboard_view_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.view_view:
                        SharedPreferences paymentsRequestListID = ((FragmentActivity) context).getSharedPreferences("paymentsRequestListID",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = paymentsRequestListID.edit();
                        editor.putString("paymentsRequestListID", paymentsList.get(position).getRetailerInvoiceId());
                        editor.commit();

                        fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container_ret, new View_Payment_Fragment());
                        fragmentTransaction.commit();
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    private void viewPDF(Context context, String ID) throws JSONException {
        ViewVoucherRequest viewPDFRequest = new ViewVoucherRequest();
        viewPDFRequest.viewPDF(context, ID);
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
