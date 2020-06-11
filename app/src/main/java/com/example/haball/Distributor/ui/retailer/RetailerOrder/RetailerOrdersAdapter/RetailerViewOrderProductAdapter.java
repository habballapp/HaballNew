package com.example.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersAdapter;

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageButton;
        import android.widget.PopupMenu;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.fragment.app.FragmentActivity;
        import androidx.fragment.app.FragmentTransaction;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersModel.RetailerOrdersModel;
        import com.example.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersModel.RetailerViewOrderProductModel;
        import com.example.haball.Distributor.ui.retailer.RetailerOrder.RetailerViewOrder;
        import com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.RetailerPlaceOrder;
        import com.example.haball.R;

        import java.text.DecimalFormat;
        import java.util.List;

public class RetailerViewOrderProductAdapter extends RecyclerView.Adapter<RetailerViewOrderProductAdapter.ViewHolder> {
    private Context context;
    private List<RetailerViewOrderProductModel> OrdersList;

    public RetailerViewOrderProductAdapter(Context context, List<RetailerViewOrderProductModel> ordersList) {
        this.context = context;
        this.OrdersList = ordersList;
    }

    @NonNull
    @Override
    public RetailerViewOrderProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.order_items_new_recycler, parent, false);
        return new RetailerViewOrderProductAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RetailerViewOrderProductAdapter.ViewHolder holder, int position) {
        holder.txt_products.setText(OrdersList.get(position).getProductTitle());
        holder.product_code_value.setText(OrdersList.get(position).getProductCode());
        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
        String yourFormattedString1 = formatter1.format(Double.parseDouble(OrdersList.get(position).getProductUnitPrice()));
        holder.price_value.setText("Rs. " + yourFormattedString1);
        String yourFormattedString2;
        if(OrdersList.get(position).getDiscountedAmount() != null)
            yourFormattedString2 = formatter1.format(Double.parseDouble(OrdersList.get(position).getDiscountedAmount()));
        else
            yourFormattedString2 = formatter1.format(Double.parseDouble(OrdersList.get(position).getProductUnitPrice()));
        holder.discount_value.setText("Rs. " + yourFormattedString2);
        holder.UOM_value.setText(OrdersList.get(position).getUOM());
        holder.pack_size_value.setText(OrdersList.get(position).getPackSize());

        holder.Quantity_value.setText(OrdersList.get(position).getOrderedQty());

        String yourFormattedString3 = formatter1.format(Double.parseDouble(OrdersList.get(position).getTotalamount()));
        holder.amount_value.setText("Rs. " + yourFormattedString3);
    }

    @Override
    public int getItemCount() {
        return OrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_products, product_code_value, price_value, discount_value, UOM_value, pack_size_value, Quantity_value, amount_value;
        public ImageButton menu_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_products = itemView.findViewById(R.id.txt_products);
            product_code_value = itemView.findViewById(R.id.product_code_value);
            price_value = itemView.findViewById(R.id.price_value);
            discount_value = itemView.findViewById(R.id.discount_value);
            UOM_value = itemView.findViewById(R.id.UOM_value);
            pack_size_value = itemView.findViewById(R.id.tax_value);
            Quantity_value = itemView.findViewById(R.id.Quantity_value);
            amount_value = itemView.findViewById(R.id.amount_value);
        }
    }
}
