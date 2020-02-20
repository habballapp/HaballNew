package com.example.haball.Distributor.ui.orders.OrdersTabsLayout.Tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.haball.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class Orders_Items_Fragment extends Fragment {


    private ViewPager mPager;
    private Button  place_item_button;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         final View view = inflater.inflate(R.layout.orders_items_fragments, container, false);
      //  View view1 = inflater.inflate(R.layout.fragment_orders__dashboard, container, false);

        place_item_button = (Button) view.findViewById(R.id.place_item_button);
        mPager = view.findViewById(R.id.view_pager5);
//        place_item_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(),"Clicked", Toast.LENGTH_SHORT).show();
//                Holderorders(view);
//
//
//            }
//        });



        return view;
    }}
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
