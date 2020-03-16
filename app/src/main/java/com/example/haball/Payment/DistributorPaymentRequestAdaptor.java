package com.example.haball.Payment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haball.Distribution_Login.Distribution_Login;
import com.example.haball.Distributor.ui.payments.Payments_Fragment;
import com.example.haball.Distributor.ui.payments.ViewVoucherRequest;
import com.example.haball.R;
import com.example.haball.Retailor.ui.Dashboard.DashBoardFragment;
import com.example.haball.Retailor.ui.Make_Payment.Payment_Summary;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.List;

public class
DistributorPaymentRequestAdaptor extends RecyclerView.Adapter<DistributorPaymentRequestAdaptor.ViewHolder> {
    private Context context;
    private List<DistributorPaymentRequestModel> paymentsRequestList;
    private Button btn_update;
    public ImageButton btn_back;
    private FragmentTransaction fragmentTransaction;

    public DistributorPaymentRequestAdaptor(Context context, List<DistributorPaymentRequestModel> paymentsRequestList) {
        this.context = context;
        this.paymentsRequestList = paymentsRequestList;
    }

    @NonNull
    @Override
    public DistributorPaymentRequestAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.payments_layout, parent, false);
        return new DistributorPaymentRequestAdaptor.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_state.setVisibility(View.GONE);
        holder.tv_state_value.setVisibility(View.GONE);
        holder.tv_heading.setText(paymentsRequestList.get(position).getCompanyName());
        holder.payment_id_value.setText(paymentsRequestList.get(position).getPrePaidNumber());
        DecimalFormat formatter1 = new DecimalFormat("#,###,###.00");
        String yourFormattedString1 = formatter1.format(Integer.parseInt(paymentsRequestList.get(position).getPaidAmount()));
        holder.amount_value.setText(yourFormattedString1);
        if (paymentsRequestList.get(position).getStatus().equals("1"))
            holder.status_value.setText("Paid");
        else
            holder.status_value.setText("Unpaid");


        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (paymentsRequestList.get(position).getPrepaidStatusValue().equals("Unpaid")) {
                    final PopupMenu popup = new PopupMenu(context, view);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.payment_request_menu_items_status, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.payment_request_view:
//                                    Toast.makeText(context,"View Clicked - " + paymentsRequestList.get(position).getID(),Toast.LENGTH_LONG).show();
//                                fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
//                                fragmentTransaction.replace(R.id.main_container, new View_Payment_Fragment());
//                                fragmentTransaction.commit();

                                    View_Payment_Fragment view_Payment_Fragment = new View_Payment_Fragment();
//                                    Log.i("paymentsRequestList", paymentsRequestList.get(position).getID());
//                                    Bundle args = new Bundle();
//                                    args.putString("PaymentsRequestId", paymentsRequestList.get(position).getID());
//                                    view_Payment_Fragment.setArguments(args);

                                    SharedPreferences paymentsRequestListID = ((FragmentActivity) context).getSharedPreferences("paymentsRequestListID",
                                            Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = paymentsRequestListID.edit();
                                    editor.putString("paymentsRequestListID", paymentsRequestList.get(position).getID());
                                    editor.commit();

                                    fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.main_container, view_Payment_Fragment);
                                    fragmentTransaction.commit();
                                    break;
                                case R.id.payment_request_ebay:
                                    Toast.makeText(context, "Epay Clicked", Toast.LENGTH_LONG).show();
                                    break;
                            }
                            return false;
                        }
                    });
                    popup.show();

                } else if (paymentsRequestList.get(position).getPrepaidStatusValue().equals("Paid")) {
                    final PopupMenu popup = new PopupMenu(context, view);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.payment_request_menu_items, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.payment_request_view:
//                                Toast.makeText(context,"View Clicked - " + paymentsRequestList.get(position).getID(),Toast.LENGTH_LONG).show();
//                                fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
//                                fragmentTransaction.replace(R.id.main_container, new View_Payment_Fragment());
//                                fragmentTransaction.commit();

                                    View_Payment_Fragment view_Payment_Fragment = new View_Payment_Fragment();
                                    SharedPreferences paymentsRequestListID = ((FragmentActivity) context).getSharedPreferences("paymentsRequestListID",
                                            Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = paymentsRequestListID.edit();
                                    editor.putString("paymentsRequestListID", paymentsRequestList.get(position).getID());
                                    editor.commit();

                                    fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.main_container, view_Payment_Fragment);
                                    fragmentTransaction.commit();

                                    break;
                                case R.id.payment_request_edit:
                                    // Toast.makeText(context,"Edit Clicked",Toast.LENGTH_LONG).show();
                                    final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                                    LayoutInflater inflater = LayoutInflater.from(context);
                                    View view_popup = inflater.inflate(R.layout.edit_payment_request, null);
                                    alertDialog.setView(view_popup);
                                    Button btn_update = (Button) view_popup.findViewById(R.id.btn_payment_request_update);
                                    btn_update.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            alertDialog.dismiss();
                                            //    Toast.makeText(context,"Update",Toast.LENGTH_LONG).show();
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
                                    //  Toast.makeText(context,"Bank Clicked",Toast.LENGTH_LONG).show();
                                    final AlertDialog alertDialog2 = new AlertDialog.Builder(context).create();
                                    LayoutInflater inflater2 = LayoutInflater.from(context);
                                    View view_popup2 = inflater2.inflate(R.layout.payment_request_details, null);
                                    alertDialog2.setView(view_popup2);
                                    alertDialog2.show();
                                    ImageButton img_close = (ImageButton) view_popup2.findViewById(R.id.image_button_close);
                                    img_close.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            alertDialog2.dismiss();
                                        }
                                    });
                                    break;
                                case R.id.menu_delete:
                                    //handle menu3 click
                                    Toast.makeText(context, "Bank Clicked", Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(context, "Epay Clicked", Toast.LENGTH_LONG).show();
                                    break;
                                case R.id.payment_request_download:
//                                        Toast.makeText(mContxt, "View PDF", Toast.LENGTH_LONG).show();
                                    try {
                                        viewPDF(context, paymentsRequestList.get(position).getID());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    break;

                            }
                            return false;
                        }
                    });
                    popup.show();
                }
            }
        });
    }

    private void viewPDF(Context context, String ID) throws JSONException {
        ViewVoucherRequest viewPDFRequest = new ViewVoucherRequest();
        viewPDFRequest.viewPDF(context, ID);
    }

    @Override
    public int getItemCount() {
        return paymentsRequestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_heading, payment_id_value, amount_value, status_value, tv_state, tv_state_value;
        public ImageButton menu_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            payment_id_value = itemView.findViewById(R.id.payment_id_value);
            amount_value = itemView.findViewById(R.id.amount_value);
            status_value = itemView.findViewById(R.id.status_value);
            menu_btn = itemView.findViewById(R.id.menu_btn);
            tv_state = itemView.findViewById(R.id.tv_state);
            tv_state_value = itemView.findViewById(R.id.tv_state_value);
        }
    }

    public void addListItem(List<DistributorPaymentRequestModel> list) {
        for (DistributorPaymentRequestModel plm : list) {
            paymentsRequestList.add(plm);
        }
        notifyDataSetChanged();
    }
}
