package com.example.haball.Retailor.ui.Network.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haball.Retailor.ui.Network.Select_Tabs.My_Network_Fragment;
import com.example.haball.R;
import com.example.haball.Retailor.ui.Network.ui.main.PlaceholderFragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Fragment_My_Network_Adapter extends RecyclerView.Adapter<Fragment_My_Network_Adapter.ViewHolder> {

    private Context context;
    private String Connected,Number,Address;

    public Fragment_My_Network_Adapter(Context context, String connected, String number, String address) {
        this.context = context;
        Connected = connected;
        Number = number;
        Address = address;
    }

    public Fragment_My_Network_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view_inflate = LayoutInflater.from(context).inflate(R.layout.fragment_my_network_recycler,parent,false);
        return new Fragment_My_Network_Adapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Fragment_My_Network_Adapter.ViewHolder holder, int position) {

        holder.my_network_fragment_status_value.setText(Connected);
        holder.my_network_fragment_no_value.setText(Number);
        holder.my_network_fragment_cnic_value.setText(Address);
       // Toast.makeText("Adapter",);

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView my_network_fragment_status_value,my_network_fragment_no_value,my_network_fragment_cnic_value;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            my_network_fragment_status_value = itemView.findViewById(R.id.my_network_fragment_status_value);
            my_network_fragment_no_value = itemView.findViewById(R.id.my_network_fragment_no_value);
            my_network_fragment_cnic_value = itemView.findViewById(R.id.my_network_fragment_cnic_value);

        }
    }
}