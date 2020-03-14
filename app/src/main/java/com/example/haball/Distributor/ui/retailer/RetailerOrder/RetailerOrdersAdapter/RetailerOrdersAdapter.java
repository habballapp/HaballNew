package com.example.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersModel.RetailerOrdersModel;
import com.example.haball.R;

import java.util.List;

public class RetailerOrdersAdapter extends RecyclerView.Adapter<RetailerOrdersAdapter.ViewHolder>  {
    private Context context;
    private List<RetailerOrdersModel> OrdersList;

    public RetailerOrdersAdapter(Context context, List<RetailerOrdersModel> ordersList) {
        this.context = context;
        this.OrdersList = ordersList;
    }

    @NonNull
    @Override
    public RetailerOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.layout_retailer_orders_dashboard,parent,false);
        return new RetailerOrdersAdapter.ViewHolder(view_inflate);    }

    @Override
    public void onBindViewHolder(@NonNull RetailerOrdersAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
