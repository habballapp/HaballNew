package com.example.haball.Payment;

import android.app.AlertDialog;
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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haball.Distributor.ui.payments.ProofOfPaymentForm;
import com.example.haball.R;

import java.util.List;


public class ProofOfPaymentAdapter extends RecyclerView.Adapter<ProofOfPaymentAdapter.ViewHolder> {
    private Context mContxt;
    private ProofOfPaymentDashboard proofOfPaymentDashboard;
    private String status, popid, createdDate, mode, payid;
    private List<ProofOfPaymentModel> proofOfPaymentsList;

//    public ProofOfPaymentAdapter(ProofOfPaymentDashboard proofOfPaymentDashboard, String status, String popid, String createdDate, String mode, String payid) {
//        mContxt = proofOfPaymentDashboard;
//        this.status = status;
//        this.popid = popid;
//        this.createdDate = createdDate;
//        this.mode = mode;
//        this.payid = payid;
//
//    }

    public ProofOfPaymentAdapter(Context context, List<ProofOfPaymentModel> proofOfPaymentsList) {
        this.mContxt = context;
        this.proofOfPaymentsList = proofOfPaymentsList;
    }

    @NonNull
    @Override
    public ProofOfPaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContxt).inflate(R.layout.layout_proof_of_payments,parent,false);
        return new ProofOfPaymentAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProofOfPaymentAdapter.ViewHolder holder, int position) {
        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(mContxt, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.proof_of_payment_form_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.proof_view:
                                FragmentTransaction fragmentTransaction= ((FragmentActivity)mContxt).getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.main_container,new ProofOfPaymentForm());
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

        if(proofOfPaymentsList.get(position).getStatus().equals("4")){
            holder.tv_status.setText("Approved");
            holder.tv_status.setTextColor(mContxt.getResources().getColor(R.color.green_color));
        }
        else if(proofOfPaymentsList.get(position).getStatus().equals("3")){
            holder.tv_status.setText("Returned");
            holder.tv_status.setTextColor(mContxt.getResources().getColor(R.color.orange_color));
        }
        String string = proofOfPaymentsList.get(position).getCreatedDate();
        String[] parts = string.split("T");
        String Date = parts[0];

        holder.payment_id_value.setText(proofOfPaymentsList.get(position).getPaymentNumber());
        holder.pop_id_value.setText(proofOfPaymentsList.get(position).getPOPNumber());
        holder.created_date_value.setText(Date);
        if(proofOfPaymentsList.get(position).getPaymentMode().equals("0"))
            holder.payment_mode_value.setText("ATM");
    }

    @Override
    public int getItemCount() {
        return proofOfPaymentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_status, payment_id_value, pop_id_value, created_date_value,payment_mode_value;
        public ImageButton menu_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_status = itemView.findViewById(R.id.tv_status);
            payment_id_value = itemView.findViewById(R.id.payment_id_value);
            pop_id_value = itemView.findViewById(R.id.pop_id_value);
            created_date_value = itemView.findViewById(R.id.created_date_value);
            payment_mode_value = itemView.findViewById(R.id.payment_mode_value);
            menu_btn = itemView.findViewById(R.id.menu_btn);
        }
    }
}
