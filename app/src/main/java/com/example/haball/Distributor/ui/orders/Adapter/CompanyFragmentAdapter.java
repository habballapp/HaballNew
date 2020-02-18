package com.example.haball.Distributor.ui.orders.Adapter;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.haball.Distributor.ui.orders.Models.Company_Fragment_Model;
        import com.example.haball.Distributor.ui.orders.Models.OrderFragmentModel;
        import com.example.haball.R;

        import java.util.List;

public class CompanyFragmentAdapter extends RecyclerView.Adapter<CompanyFragmentAdapter.ViewHolder> {

    //  private Orders_Fragment mContext;
    private  String heading="";

    private Context context;
    private List<OrderFragmentModel> orderList;
    public CompanyFragmentAdapter(Context requestOrder, String heading){

        this.heading = heading;
        this.context = requestOrder;
    }
//
//    public DistributorOrderAdapter(Context context, List<OrderFragmentModel> orderList) {
//        this.context = context;
//        this.orderList = orderList;
//    }

    @Override
    public CompanyFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.company_fragment,parent,false);
        return new CompanyFragmentAdapter.ViewHolder(view_inflate);
    }

//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//    }

    @Override
    public void onBindViewHolder(@NonNull CompanyFragmentAdapter.ViewHolder holder, int position) {
//
//        if(orderList.get(position).getCompanyName() != null)
//            heading = orderList.get(position).getCompanyName();
//
//        if(orderList.get(position).getCompanyName()!= null)
//            orderno = orderList.get(position).getOrderNumber();
//        if(orderList.get(position).getTotalPrice()!=null)
//            amount = orderList.get(position).getTotalPrice();
//        if(orderList.get(position).getOrderStatusValue()!= null)
//            status = orderList.get(position).getOrderStatusValue();
//
//        holder.tv_heading.setText(heading);
//        holder.order_no_value.setText(orderno);
//        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
//        String yourFormattedString1 = formatter1.format(Integer.parseInt(amount));
//        holder.amount_value.setText(yourFormattedString1);
//
////        holder.amount_value.setText(amount);
//        holder.status_value.setText(status);
//
//        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PopupMenu popup = new PopupMenu(context, view);
//                MenuInflater inflater = popup.getMenuInflater();
//                inflater.inflate(R.menu.orders_fragment_menu, popup.getMenu());
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.orders_view:
//                                Log.i("text1", "View Item");
//                                Toast.makeText(context,"View Clicked",Toast.LENGTH_LONG).show();
//                                break;
//
//
//                        }
//                        return false;
//                    }
//                });
//                popup.show();
//            }
//        });

//        if(orderList.get(position).getCompanyName() != null)
//            heading = orderList.get(position).getCompanyName();
//
//        if(orderList.get(position).getCompanyName()!= null)
//            orderno = orderList.get(position).getOrderNumber();
//        if(orderList.get(position).getTotalPrice()!=null)
//            amount = orderList.get(position).getTotalPrice();
//        if(orderList.get(position).getOrderStatusValue()!= null)
//            status = orderList.get(position).getOrderStatusValue();

        holder.tv_heading.setText(heading);
    }
    @Override
    public int getItemCount() {
//        return orderList.size();
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_heading;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
        }
    }
}
