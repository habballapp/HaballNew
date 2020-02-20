package com.example.haball.Distributor.ui.orders.Adapter;

        import android.content.Context;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.core.view.GravityCompat;
        import androidx.drawerlayout.widget.DrawerLayout;
        import androidx.fragment.app.FragmentActivity;
        import androidx.fragment.app.FragmentTransaction;
        import androidx.recyclerview.widget.RecyclerView;
        import androidx.viewpager.widget.ViewPager;

        import com.example.haball.Distributor.ui.orders.Models.Company_Fragment_Model;
        import com.example.haball.Distributor.ui.orders.Models.OrderFragmentModel;
        import com.example.haball.Distributor.ui.orders.OrdersTabsLayout.Orders_Dashboard;
        import com.example.haball.Distributor.ui.orders.OrdersTabsLayout.Tabs.Order_Summary;
        import com.example.haball.Distributor.ui.orders.OrdersTabsLayout.Tabs.Orders_Items_Fragment;
        import com.example.haball.Invoice.Distributor_Invoice_DashBoard;
        import com.example.haball.R;
        import com.google.android.material.tabs.TabLayout;

        import java.util.List;

public class CompanyFragmentAdapter extends RecyclerView.Adapter<CompanyFragmentAdapter.ViewHolder> {
//    ViewPager mPager;
    //  private Orders_Fragment mContext;
    private  String heading="";
    private DrawerLayout drawer;
    private FragmentTransaction fragmentTransaction;

    private Context context;
    private List<OrderFragmentModel> orderList;
    private ViewPager mPager;
    private ViewGroup mycontainer;
    private LayoutInflater myinflator;
    private Button place_order_button;
    public CompanyFragmentAdapter(Context requestOrder, String heading, ViewPager mPager, ViewGroup container, LayoutInflater inflator){

        this.heading = heading;
        this.context = requestOrder;
        this.mPager = mPager;
        this.mycontainer = container;
        this.myinflator = inflator;
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
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // mPager.setCurrentItem(0);
                   FragmentTransaction fragmentTransaction= ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                   fragmentTransaction.add(R.id.main_container,new Orders_Items_Fragment());
                   fragmentTransaction.commit();
                // View root = myinflator.inflate(R.layout.orders_items_fragments, false);// TabLayout tab = root.findViewById(R.id.tabs5);
               /*   place_order_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPager.setCurrentItem(0);
                    }
                });*/



//                recyclerView = (RecyclerView) root.findViewById(R.id.rv_order_ledger);


                 //drawer.closeDrawer(GravityCompat.START);
                    /*((FragmentActivity) v.getContext()).getFragmentManager().beginTransaction()
                            .replace(R.id.main_container, new Order_Summary())
                            .commit();*/
            }
        });

    }
    @Override
    public int getItemCount() {
//        return orderList.size();
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_heading;
        public LinearLayout ll;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            ll = (LinearLayout) itemView.findViewById(R.id.ll_heading);
            place_order_button = (Button) itemView.findViewById(R.id.place_order_button);



        }

    }
}
