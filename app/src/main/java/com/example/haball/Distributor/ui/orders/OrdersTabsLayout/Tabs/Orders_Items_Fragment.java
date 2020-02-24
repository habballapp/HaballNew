package com.example.haball.Distributor.ui.orders.OrdersTabsLayout.Tabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.haball.Distributor.ui.orders.Adapter.OrdersItemsAdapter;
import com.example.haball.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class Orders_Items_Fragment extends Fragment {

    private RecyclerView itemsSelect_Rv;
    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.LayoutManager layoutManager1;
    private Button  place_item_button;
    Boolean Checkout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         final View view = inflater.inflate(R.layout.orders_items_fragments, container, false);
      //  View view1 = inflater.inflate(R.layout.fragment_orders__dashboard, container, false);
                //init
        place_item_button = (Button) view.findViewById(R.id.place_item_button);
         place_item_button.setBackgroundResource(R.drawable.button_grey_round);
         place_item_button.setEnabled(false);


        holderitems(view);

        return view;
    }
    private void holderitems(final View root) {
        Log.i("abbasi" ,"abccccccccccccccccc");
        itemsSelect_Rv =(RecyclerView) root.findViewById(R.id.rv_items_orders);
        itemsSelect_Rv.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(getContext());
        itemsSelect_Rv.setLayoutManager(layoutManager1);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Button_Check",
                Context.MODE_PRIVATE);
        Checkout = sharedPreferences.getBoolean("checkout_success", false);
        Log.i("Checkout", String.valueOf(Checkout));

        mAdapter1 = new OrdersItemsAdapter(getContext(),"0","abc","1232","230");
        itemsSelect_Rv.setAdapter(mAdapter1);

         if(Checkout){

//             place_item_button.setBackgroundResource(R.drawable.button_round);
//             place_item_button.setEnabled(true);
//
//             place_item_button.setOnClickListener(new View.OnClickListener() {
//                 @Override
//                 public void onClick(View v) {
//                     ViewPager  viewPager = getActivity().findViewById(R.id.view_pager5);
//                     Toast.makeText(getContext(),"Clicked", Toast.LENGTH_SHORT).show();
//                     viewPager.setCurrentItem(1);
//                     FragmentTransaction fragmentTransaction= ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
//                     fragmentTransaction.add(R.id.main_container,new Order_Summary());
//                     fragmentTransaction.commit();
//                 }
//             });
             Log.i("Checkout", "in true");
         }
         else {
             Log.i("Checkout", "in false");
//
//             place_item_button.setBackgroundResource(R.drawable.button_grey_round);
//             place_item_button.setEnabled(false);


         }

        Log.i("placeHolder12" , String.valueOf(mAdapter1));

    }

}
//    private void Holderorders(final View view){
//
//           mPager.setCurrentItem(1);
//
//
//
//
//
//       /* create_payment = root.findViewById(R.id.place_order_button);
//        create_payment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPager.setCurrentItem(1); // Change to page 1, i.e., FragmentB
//            }
//        });*/
//
//    }}
