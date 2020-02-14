package com.example.haball.Retailor.ui.Network.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haball.R;
import com.example.haball.Retailor.ui.Network.Select_Tabs.Sent_Fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Fragment_Sent_Adaptor extends RecyclerView.Adapter<Fragment_Sent_Adaptor.ViewHolder> {

    private Context context;
    private String Number,Address;

    public Fragment_Sent_Adaptor(Context context, String number, String address) {
        this.context = context;
        Number = number;
        Address = address;
    }

    public Fragment_Sent_Adaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.fragment_sent_recycler,parent,false);
        return new Fragment_Sent_Adaptor.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Fragment_Sent_Adaptor.ViewHolder holder, int position) {

        holder.send_no_value.setText(Number);
        holder.send_address_value.setText(Address);

    }

    @Override
    public int getItemCount() {
        return 2;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView send_no_value,send_address_value;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            send_no_value = itemView.findViewById(R.id.sent_no_value);
            send_address_value = itemView.findViewById(R.id.sent_address_value);

        }
    }
}
