package com.haball.Payment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.haball.Distribution_Login.Distribution_Login;
import com.haball.Distributor.ui.payments.EditPayment;
import com.haball.Distributor.ui.payments.EditPaymentRequestFragment;
import com.haball.Distributor.ui.payments.PaymentScreen3Fragment;
import com.haball.Distributor.ui.payments.Payments_Fragment;
import com.haball.Distributor.ui.payments.ViewVoucherRequest;
import com.haball.R;
import com.haball.Retailor.ui.Dashboard.DashBoardFragment;
import com.haball.Retailor.ui.Make_Payment.Payment_Summary;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class
DistributorPaymentRequestAdaptor extends RecyclerView.Adapter<DistributorPaymentRequestAdaptor.ViewHolder> {
    private Context context;
    private List<DistributorPaymentRequestModel> paymentsRequestList;
    private Button btn_update;
    public ImageButton btn_back;
    private String company_names;
    List<String> CompanyNames = new ArrayList<>();
    ArrayAdapter<String> arrayAdapterPayments;
    private HashMap<String, String> companyNameAndId = new HashMap<>();
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private Activity activity;

    Spinner spinner;
    private FragmentTransaction fragmentTransaction;

    public DistributorPaymentRequestAdaptor(Activity activity, Context context, List<DistributorPaymentRequestModel> paymentsRequestList) {
        this.activity = activity;
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
        if (paymentsRequestList.size() == 3) {
            if (position == (paymentsRequestList.size() - 1)) {
//        if (position == 2) {
                Log.i("DebugSupportFilter_In", paymentsRequestList.get(position).getPrePaidNumber());
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 50, 0, 280);
                holder.main_layout_payment_box.setLayoutParams(params);
            }
        }
        holder.tv_state.setVisibility(View.GONE);
        holder.tv_state_value.setVisibility(View.GONE);
        holder.tv_heading.setText(paymentsRequestList.get(position).getCompanyName());
        holder.payment_id_value.setText(paymentsRequestList.get(position).getPrePaidNumber());
        DecimalFormat formatter1 = new DecimalFormat("#,###,###.00");
        String yourFormattedString1 = formatter1.format(Integer.parseInt(paymentsRequestList.get(position).getPaidAmount()));
        holder.amount_value.setText("Rs. " + yourFormattedString1);
        if (paymentsRequestList.get(position).getStatus().equals("1"))
            holder.status_value.setText("Paid");
        else
            holder.status_value.setText("Unpaid");


        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (paymentsRequestList.get(position).getPrepaidStatusValue().equals("Paid")) {
                    final PopupMenu popup = new PopupMenu(context, view);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.payment_request_menu_items_status, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.payment_request_view:
                                    SharedPreferences paymentsRequestListID = ((FragmentActivity) context).getSharedPreferences("paymentsRequestListID",
                                            Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = paymentsRequestListID.edit();
                                    editor.putString("paymentsRequestListID", paymentsRequestList.get(position).getID());
                                    editor.commit();

                                    fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.add(R.id.main_container, new View_Payment_Fragment()).addToBackStack("tag");
                                    fragmentTransaction.commit();
                                    break;
                                case R.id.payment_request_ebay:
                                    // Toast.makeText(context, "Epay Clicked", Toast.LENGTH_LONG).show();
                                    break;
                            }
                            return false;
                        }
                    });
                    popup.show();

                } else if (paymentsRequestList.get(position).getPrepaidStatusValue().equals("Unpaid")) {
                    final PopupMenu popup = new PopupMenu(context, view);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.payment_request_menu_items, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.payment_request_view:
//                                    View_Payment_Fragment view_Payment_Fragment = new View_Payment_Fragment();
//                                    SharedPreferences paymentsRequestListID = ((FragmentActivity) context).getSharedPreferences("paymentsRequestListID",
//                                            Context.MODE_PRIVATE);
//                                    SharedPreferences.Editor editor = paymentsRequestListID.edit();
//                                    editor.putString("paymentsRequestListID", paymentsRequestList.get(position).getID());
//                                    editor.commit();
//
//                                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                                    fragmentTransaction.add(R.id.main_container, view_Payment_Fragment);
//                                    fragmentTransaction.commit();
                                    SharedPreferences PrePaidNumber = context.getSharedPreferences("PrePaidNumber",
                                            Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = PrePaidNumber.edit();
                                    editor.putString("PrePaidNumber", paymentsRequestList.get(position).getPrePaidNumber());
                                    editor.putString("PrePaidId", paymentsRequestList.get(position).getID());
                                    editor.putString("CompanyId", paymentsRequestList.get(position).getCompanyId());
                                    editor.putString("CompanyName", paymentsRequestList.get(position).getCompanyName());
                                    editor.putString("Amount", paymentsRequestList.get(position).getPaidAmount());
                                    editor.putString("MenuItem", "View");
                                    editor.apply();

                                    fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.add(R.id.main_container, new PaymentScreen3Fragment()).addToBackStack("tag");
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();

                                    break;
                                case R.id.payment_request_edit:

                                    // Toast.makeText(context,"Edit Clicked",Toast.LENGTH_LONG).show();
//                                    final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//                                    LayoutInflater inflater = LayoutInflater.from(context);
//                                    View view_popup = inflater.inflate(R.layout.edit_payment_request, null);
//                                    alertDialog.setView(view_popup);
//                                    spinner = view_popup.findViewById(R.id.spinner_conso);
//                                    Button btn_update = (Button) view_popup.findViewById(R.id.btn_payment_request_update);
//                                    btn_update.setOnClickListener(new View.OnClickListener() {
//                                        public void onClick(View v) {
//                                            alertDialog.dismiss();
//                                            //    Toast.makeText(context,"Update",Toast.LENGTH_LONG).show();
//                                            final AlertDialog alertDialog1 = new AlertDialog.Builder(context).create();
//                                            LayoutInflater inflater = LayoutInflater.from(context);
//                                            View view_popup = inflater.inflate(R.layout.edit_request_payment_success, null);
//                                            alertDialog1.setView(view_popup);
//                                            ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.image_success);
//                                            img_email.setOnClickListener(new View.OnClickListener() {
//                                                @Override
//                                                public void onClick(View v) {
//                                                    alertDialog1.dismiss();
//                                                }
//                                            });
//                                            alertDialog1.show();
//
//                                        }
//                                    });
//
//
//
//                                    CompanyNames.add("Company *");
//                                    CompanyNames.add("ABC");
//                                    company_names = "";
//
//                                    arrayAdapterPayments = new ArrayAdapter<>(view_popup.getContext(),
//                                            android.R.layout.simple_spinner_dropdown_item, CompanyNames);
//
//                                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                        @Override
//                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                            if (i == 0) {
//                                                ((TextView) adapterView.getChildAt(0)).setTextColor(context.getResources().getColor(android.R.color.darker_gray));
//                                            } else {
//                                                company_names = CompanyNames.get(i);
//                                                Log.i("company name and id ", companyNameAndId.get(company_names));
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onNothingSelected(AdapterView<?> adapterView) {
//
//                                        }
//                                    });
//
//
//
//                                    ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.image_payment_request);
//                                    img_email.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            alertDialog.dismiss();
//
//                                        }
//                                    });
//
//                                    alertDialog.show();
//                                    arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                    arrayAdapterPayments.notifyDataSetChanged();
//                                    spinner.setAdapter(arrayAdapterPayments);
                                    SharedPreferences PrePaidNumberEdit = context.getSharedPreferences("PrePaidNumber",
                                            Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editorEdit = PrePaidNumberEdit.edit();
                                    editorEdit.putString("PrePaidNumber", paymentsRequestList.get(position).getPrePaidNumber());
                                    editorEdit.putString("PrePaidId", paymentsRequestList.get(position).getID());
                                    editorEdit.putString("CompanyName", paymentsRequestList.get(position).getCompanyName());
                                    editorEdit.putString("Amount", paymentsRequestList.get(position).getPaidAmount());
                                    editorEdit.putString("MenuItem", "Edit");
                                    editorEdit.apply();

                                    FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.add(R.id.main_container, new PaymentScreen3Fragment()).addToBackStack("Tag");
                                    fragmentTransaction.commit();

                                    break;
                                case R.id.payment_request_bank:
                                    //handle menu2 click
                                    //  Toast.makeText(context,"Bank Clicked",Toast.LENGTH_LONG).show();
                                    final AlertDialog alertDialog2 = new AlertDialog.Builder(context).create();
                                    LayoutInflater inflater2 = LayoutInflater.from(context);
                                    View view_popup2 = inflater2.inflate(R.layout.payment_request_details, null);
                                    alertDialog2.setView(view_popup2);
                                    alertDialog2.show();
                                    Button btn_view_voucher = view_popup2.findViewById(R.id.btn_view_voucher);
                                    btn_view_voucher.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (checkAndRequestPermissions()) {
                                                try {
                                                    viewPDF(context, paymentsRequestList.get(position).getID());
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    });
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

    private boolean checkAndRequestPermissions() {
        int permissionRead = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWrite = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionRead != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
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
        public RelativeLayout main_layout_payment_box;
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
            main_layout_payment_box = itemView.findViewById(R.id.main_layout_payment_box_retailer);
        }
    }

    public void addListItem(List<DistributorPaymentRequestModel> list) {
        for (DistributorPaymentRequestModel plm : list) {
            paymentsRequestList.add(plm);
        }
        notifyDataSetChanged();
    }
}
