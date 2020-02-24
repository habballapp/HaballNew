package com.example.haball.Invoice.Adapters;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.haball.Invoice.Models.ProductDetails_Model;
        import com.example.haball.R;
        import com.example.haball.Shipment.ui.main.Models.Distributor_ProductModel;

        import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ShipmentDetailsVH> {

    private Context context;
    List<ProductDetails_Model> product_list;

    public ProductAdapter(Context context, List<ProductDetails_Model> product_list) {
        this.context = context;
        this.product_list = product_list;
    }

    @NonNull
    @Override
    public ShipmentDetailsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.product_details_shipment,parent,false);
        return new ProductAdapter.ShipmentDetailsVH(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipmentDetailsVH holder, int position) {
        holder.shipment_productCode.setText(product_list.get(position).getProductCode());
        holder.shipment_productName.setText(product_list.get(position).getProductName());
        holder.shipment_quantity.setText(product_list.get(position).getDeliveredQty());
        holder.shipment_unitPrice.setText(product_list.get(position).getUnitPrice());
        holder.shipment_discount.setText(product_list.get(position).getDiscount());
        holder.shipment_amount.setText(product_list.get(position).getTotalPrice());

    }

    @Override
    public int getItemCount() {
        return product_list.size();
    }

    public class ShipmentDetailsVH extends RecyclerView.ViewHolder {
        private TextView shipment_productCode,shipment_productName,shipment_quantity ,shipment_unitPrice ,shipment_discount ,shipment_amount;

        public ShipmentDetailsVH(@NonNull View itemView) {
            super(itemView);
            shipment_productCode = itemView.findViewById(R.id.shipment_productCode);
            shipment_productName = itemView.findViewById(R.id.shipment_productName);
            shipment_productCode = itemView.findViewById(R.id.shipment_productCode);
            shipment_quantity = itemView.findViewById(R.id.shipment_quantity);
            shipment_unitPrice = itemView.findViewById(R.id.shipment_unitPrice);
            shipment_discount = itemView.findViewById(R.id.shipment_discount);
            shipment_amount = itemView.findViewById(R.id.shipment_amount);


        }
    }
}
