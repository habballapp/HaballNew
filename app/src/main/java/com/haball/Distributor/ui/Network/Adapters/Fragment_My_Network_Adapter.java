package com.haball.Distributor.ui.Network.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haball.Distributor.ui.Network.Models.Netwok_Model;
import com.haball.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Fragment_My_Network_Adapter extends RecyclerView.Adapter<Fragment_My_Network_Adapter.ViewHolder> {

    private Context context;
    private List<Netwok_Model> myNetworkData;

    public Fragment_My_Network_Adapter(Context context, List<Netwok_Model> myNetworkData) {
        this.context = context;
        this.myNetworkData = myNetworkData;
    }

    public Fragment_My_Network_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view_inflate = LayoutInflater.from(context).inflate(R.layout.fragment_my_network_recycler,parent,false);
        return new Fragment_My_Network_Adapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Fragment_My_Network_Adapter.ViewHolder holder, int position) {

        holder.my_network_fragment_status_value.setText(myNetworkData.get(position).getKycStatusValue());
        holder.net_company_name.setText(myNetworkData.get(position).getCompanyName());

        holder.my_network_fragment_address.setText("Created Date: ");
        holder.my_network_fragment_cnic_value.setText(myNetworkData.get(position).getCreatedDate().split("T")[0]);

        holder.my_network_fragment.setText("Request No: ");
        holder.my_network_fragment_no_value.setText(myNetworkData.get(position).getKYCNumber());
       // Toast.makeText("Adapter",);

    }

    @Override
    public int getItemCount() {
        return myNetworkData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView my_network_fragment_status_value,my_network_fragment_no_value,my_network_fragment_cnic_value ,net_company_name;
        public TextView my_network_fragment_status,my_network_fragment_address,my_network_fragment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            my_network_fragment_status_value = itemView.findViewById(R.id.my_network_fragment_status_value);
            my_network_fragment_no_value = itemView.findViewById(R.id.my_network_fragment_no_value);
            my_network_fragment_cnic_value = itemView.findViewById(R.id.my_network_fragment_cnic_value);
            my_network_fragment_status = itemView.findViewById(R.id.my_network_fragment_status);
            my_network_fragment_address = itemView.findViewById(R.id.my_network_fragment_address);
            my_network_fragment = itemView.findViewById(R.id.my_network_fragment);
            net_company_name =itemView.findViewById(R.id.net_company_name);

        }
    }
}
