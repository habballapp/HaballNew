package com.example.haball.Shipment.Adapters;

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

import com.example.haball.Distributor.ui.shipments.DistributorShipment_ViewDashboard;
import com.example.haball.Distributor.ui.shipments.ShipmentModel;
import com.example.haball.Distributor.ui.shipments.Shipments_Fragments;
import com.example.haball.R;

import java.text.DecimalFormat;
import java.util.List;

public class DistributorShipmentAdapter extends RecyclerView.Adapter<DistributorShipmentAdapter.ViewHolder> {
    //
//    private Shipments_Fragments mContext;
    private  String heading="",shipment_no_value="",tv_date="",tv_recv_date="",tv_quantity_value="",tv_option="";
    Context activity;
    private Context context;
    private List<ShipmentModel> shipmentList;

//
//    public DistributorShipmentAdapter(String heading, String shipment_no_value, String name_value, String tv_date, String tv_price, String tv_option, Context activity) {
//        this.heading = heading;
//        this.shipment_no_value = shipment_no_value;
//        this.name_value = name_value;
//        this.tv_date = tv_date;
//        this.tv_price = tv_price;
//        this.tv_option = tv_option;
//        this.activity = activity;
//    }

    public DistributorShipmentAdapter(Context context, List<ShipmentModel> shipmentList) {
        this.context = context;
        this.shipmentList = shipmentList;
    }

    public DistributorShipmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.distributorshipment_recycler,parent,false);
        return new DistributorShipmentAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DistributorShipmentAdapter.ViewHolder holder, final int position) {
        if(shipmentList.get(position).getCompanyName() != null)
            heading = shipmentList.get(position).getCompanyName();
        if(shipmentList.get(position).getDeliveryNumber() != null)
            shipment_no_value = shipmentList.get(position).getDeliveryNumber();
        if(shipmentList.get(position).getDeliveryDate() != null)
            tv_date = shipmentList.get(position).getDeliveryDate().split("T")[0];
        if(shipmentList.get(position).getReceivingDate() != null)
            tv_recv_date = shipmentList.get(position).getReceivingDate().split("T")[0];
        if(shipmentList.get(position).getShipmentStatusValue() != null)
            tv_option = shipmentList.get(position).getShipmentStatusValue();
        if(shipmentList.get(position).getQuantity() != null)
            tv_quantity_value = shipmentList.get(position).getQuantity();
        holder.tv_heading.setText(heading);
        holder.shipment_no_value.setText(shipment_no_value);
        holder.tv_date.setText(tv_date);

        holder.tv_recv_date.setText(tv_recv_date);
        holder.tv_quantity_value.setText(tv_quantity_value);
        if(tv_option.equals("1"))
            holder.tv_option.setText("Paid");
        else
            holder.tv_option.setText("Unpaid");

        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.cosolidate_payment_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.consiladate_view:
                                //handle menu1 click
                                Toast.makeText(context,"View Clicked",Toast.LENGTH_LONG).show();
                                FragmentTransaction fragmentTransaction= ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.add(R.id.main_container,new DistributorShipment_ViewDashboard());
                                fragmentTransaction.commit();
                                SharedPreferences shipmentid = ((FragmentActivity)context).getSharedPreferences("Shipment_ID",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = shipmentid.edit();
                                editor.putString("ShipmentID", shipmentList.get(position).getID());
                                editor.commit();
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
        return shipmentList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_heading,shipment_no_value,tv_date,tv_recv_date,tv_quantity_value,tv_option;
        public ImageButton menu_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            shipment_no_value = itemView.findViewById(R.id.shipment_no_value);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_recv_date = itemView.findViewById(R.id.tv_recv_date);
            tv_quantity_value = itemView.findViewById(R.id.tv_quantity_value);
            tv_option = itemView.findViewById(R.id.tv_option);
            menu_btn = itemView.findViewById(R.id.menu_btn);

        }
    }
}
