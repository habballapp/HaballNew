package com.example.haball.Retailor.ui.Place_Order.ui.main.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haball.R;
import com.example.haball.Retailor.RetailorDashboard;
import com.example.haball.Retailor.ui.Make_Payment.PaymentScreen3Fragment_Retailer;
import com.example.haball.Retailor.ui.Place_Order.ui.main.Models.OrderChildlist_Model;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class Order_Summary_Adapter extends RecyclerView.Adapter<Order_Summary_Adapter.ViewHolder> {

    private Context context;
    private List<OrderChildlist_Model> selectedProductsDataList;
    private List<String> selectedProductsDataListQty;
    private float grossAmount = 0;

    public Order_Summary_Adapter(Context context, List<OrderChildlist_Model> selectedProductsDataList, List<String> selectedProductsDataListQty) {
        this.context = context;
        this.selectedProductsDataList = selectedProductsDataList;
        this.selectedProductsDataListQty = selectedProductsDataListQty;
        Log.i("selectedProducts", String.valueOf(selectedProductsDataList));
    }

    public Order_Summary_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.order_summary_recycler_fragment, parent, false);
        return new Order_Summary_Adapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final Order_Summary_Adapter.ViewHolder holder, int position) {
        final int finalPosition = position;
        Log.i("position", String.valueOf(finalPosition));
        holder.list_numberOFitems.setText(selectedProductsDataListQty.get(position));
        holder.list_product_code_value.setText(selectedProductsDataList.get(position).getProductCode());
        holder.list_txt_products_.setText(selectedProductsDataList.get(position).getTitle());
        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");

        if (selectedProductsDataList.get(position).getProductUnitPrice() != null) {
            String yourFormattedString1 = formatter1.format(Double.parseDouble(selectedProductsDataList.get(position).getProductUnitPrice()));
            holder.list_price_value.setText("Rs. " + yourFormattedString1);
        }
        String yourFormattedString2;
        Log.i("getDiscountAmount()", "'" + String.valueOf(selectedProductsDataList.get(position).getDiscountAmount()) + "'");
        if (selectedProductsDataList.get(position).getDiscountAmount() != null)
            yourFormattedString2 = formatter1.format(Double.parseDouble(selectedProductsDataList.get(position).getDiscountAmount()));
        else
            yourFormattedString2 = formatter1.format(0);
        holder.list_discount_value.setText("Rs. " + yourFormattedString2);
//        if (orderChildlist_model.getPackSize() != null)
//            orderChildList_vh.list_pack_size_value.setText(orderChildlist_model.getPackSize());
        holder.list_UOM_value.setText(selectedProductsDataList.get(position).getUnitOFMeasure());


        float totalamount = 0;
        if (!selectedProductsDataList.get(position).getProductUnitPrice().equals("") && !selectedProductsDataListQty.get(position).equals(""))
            totalamount = Float.parseFloat(selectedProductsDataListQty.get(position)) * Float.parseFloat(selectedProductsDataList.get(position).getProductUnitPrice());
        holder.totalAmount_value.setText(String.valueOf(totalamount));
        holder.list_numberOFitems.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (String.valueOf(s).equals("0")) {
//                    Log.i("position to be removed", String.valueOf(finalPosition));
//                    selectedProductsDataList.remove(finalPosition);
//                    selectedProductsDataListQty.remove(finalPosition);
//                    notifyItemRemoved(finalPosition);
//                    notifyItemRangeChanged(finalPosition, selectedProductsDataList.size());
//
//                    Gson gson = new Gson();
//                    String json = gson.toJson(selectedProductsDataList);
//                    String jsonqty = gson.toJson(selectedProductsDataListQty);
//                    Log.i("jsonqty", jsonqty);
//                    Log.i("json", json);
//
//                    SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_retailer_own",
//                            Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = selectedProducts.edit();
//                    editor.putString("selected_products", json);
//                    editor.putString("selected_products_qty", jsonqty);
//                    editor.apply();
//                } else {
                checkOutEnabler(holder, finalPosition);
                String quantity = selectedProductsDataListQty.get(finalPosition);
                if (quantity.equals(""))
                    quantity = "0";

                final float finaltotalamount = Float.parseFloat(quantity) * Float.parseFloat(selectedProductsDataList.get(finalPosition).getProductUnitPrice());
                holder.totalAmount_value.setText(String.valueOf(finaltotalamount));
//                }
            }
        });
        holder.btn_delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                LayoutInflater inflater = LayoutInflater.from(context);
                View view_popup = inflater.inflate(R.layout.discard_changes, null);
                TextView tv_discard = view_popup.findViewById(R.id.tv_discard);
                TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
                tv_discard_txt.setText("Are you sure, you want to delete this product?");
                tv_discard.setText("Delete Product");

                alertDialog.setView(view_popup);
                alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
                WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
                layoutParams.y = 200;
                layoutParams.x = -70;// top margin
                alertDialog.getWindow().setAttributes(layoutParams);
                Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
                btn_discard.setText("Delete");
                btn_discard.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Log.i("CreatePayment", "Button Clicked");
                        alertDialog.dismiss();

                        if (selectedProductsDataList.size() > 1) {

                            selectedProductsDataListQty.set(finalPosition, "0");
                            holder.list_numberOFitems.setText("0");
                            checkOutEnabler(holder, finalPosition);

                            grossAmount = 0;

                            if (selectedProductsDataList.size() > 0) {

                                for (int i = 0; i < selectedProductsDataList.size(); i++) {
                                    Log.i("unit price", selectedProductsDataList.get(i).getProductUnitPrice());
                                    Log.i("qty", selectedProductsDataListQty.get(i));
                                    if (!selectedProductsDataList.get(i).getProductUnitPrice().equals("") && !selectedProductsDataListQty.get(i).equals(""))
                                        grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getProductUnitPrice()) * Float.parseFloat(selectedProductsDataListQty.get(i));
                                }
                                SharedPreferences grossamount = context.getSharedPreferences("grossamount",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor_grossamount = grossamount.edit();
                                editor_grossamount.putString("grossamount", String.valueOf(grossAmount));
                                editor_grossamount.apply();
                                grossAmount = 0;
                            }

                            selectedProductsDataList.remove(finalPosition);
                            selectedProductsDataListQty.remove(finalPosition);
                            notifyItemRemoved(finalPosition);
                            notifyItemRangeChanged(finalPosition, selectedProductsDataList.size());

                            Gson gson = new Gson();
                            String json = gson.toJson(selectedProductsDataList);
                            String jsonqty = gson.toJson(selectedProductsDataListQty);
                            Log.i("jsonqty", jsonqty);
                            Log.i("json", json);

                            SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_retailer_own",
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = selectedProducts.edit();
                            editor.putString("selected_products", json);
                            editor.putString("selected_products_qty", jsonqty);
                            editor.apply();

                            final Dialog fbDialogue = new Dialog(context);
                            //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                            fbDialogue.setContentView(R.layout.password_updatepopup);
                            TextView tv_pr1, txt_header1;
                            txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
                            tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
                            tv_pr1.setText("Your product has been deleted successfully.");
                            txt_header1.setText("Product Deleted");
                            fbDialogue.setCancelable(true);
                            fbDialogue.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
                            WindowManager.LayoutParams layoutParams = fbDialogue.getWindow().getAttributes();
                            layoutParams.y = 200;
                            layoutParams.x = -70;// top margin
                            fbDialogue.getWindow().setAttributes(layoutParams);
                            fbDialogue.show();

                            ImageButton close_button = fbDialogue.findViewById(R.id.image_button);
                            close_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    fbDialogue.dismiss();
                                }
                            });
//                checkOutEnabler(holder, finalPosition, false);


                        } else {
                            Toast.makeText(context, "You have only 1 selected product.", Toast.LENGTH_LONG).show();
                        }
//                fm.popBackStack();
                    }
                });

                ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.btn_close);
                img_email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();

                    }
                });

                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return selectedProductsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView list_txt_products_, list_product_code_value, list_price_value, list_discount_value, list_UOM_value, totalAmount_value;
        public EditText list_numberOFitems;
        public TextView btn_delete_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            list_txt_products_ = itemView.findViewById(R.id.list_txt_products_);
            list_product_code_value = itemView.findViewById(R.id.list_product_code_value);
            list_price_value = itemView.findViewById(R.id.list_price_value);
            list_discount_value = itemView.findViewById(R.id.list_discount_value);
            list_UOM_value = itemView.findViewById(R.id.list_UOM_value);
//            list_pack_size_value = itemView.findViewById(R.id.list_pack_size_value);
            list_numberOFitems = itemView.findViewById(R.id.list_numberOFitems);
            totalAmount_value = itemView.findViewById(R.id.totalAmount_value);
            btn_delete_item = itemView.findViewById(R.id.btn_delete_item);

        }
    }


    private void checkOutEnabler(Order_Summary_Adapter.ViewHolder holder, int position) {
        if (holder.list_numberOFitems.getText() != null && selectedProductsDataListQty.size() > position) {
            selectedProductsDataListQty.set(position, String.valueOf(holder.list_numberOFitems.getText()));

//            for (int i = 0; i < selectedProductsDataList.size(); i++)
//                Toast.makeText(context, selectedProductsDataList.get(i).getTitle() + " - " + selectedProductsDataListQty.get(i), Toast.LENGTH_LONG).show();

            Gson gson = new Gson();
            String json = gson.toJson(selectedProductsDataList);
            String jsonqty = gson.toJson(selectedProductsDataListQty);
            Log.i("jsonqty", jsonqty);
            Log.i("json", json);
            SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_retailer_own",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = selectedProducts.edit();
            editor.putString("selected_products", json);
            editor.putString("selected_products_qty", jsonqty);
            editor.apply();

            grossAmount = 0;

            if (selectedProductsDataList.size() > 0) {
                for (int i = 0; i < selectedProductsDataList.size(); i++) {
                    Log.i("unit price", selectedProductsDataList.get(i).getProductUnitPrice());
                    Log.i("qty", selectedProductsDataListQty.get(i));
                    if (!selectedProductsDataList.get(i).getProductUnitPrice().equals("") && !selectedProductsDataListQty.get(i).equals(""))
                        grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getProductUnitPrice()) * Float.parseFloat(selectedProductsDataListQty.get(i));
                }
                SharedPreferences grossamount = context.getSharedPreferences("grossamount",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_grossamount = grossamount.edit();
                editor_grossamount.putString("grossamount", String.valueOf(grossAmount));
                editor_grossamount.apply();
                grossAmount = 0;
            }
        }
    }
}
