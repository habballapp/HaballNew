package com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Adapters.ParentListAdapter;
import com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Models.OrderChildlist_Model;
import com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Models.OrderParentlist_Model;
import com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Models.TitleCreator;
import com.example.haball.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderPlace_retailer_dashboarad extends Fragment {
        RecyclerView recyclerView ;

    public OrderPlace_retailer_dashboarad() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_order_place_retailer_dashboarad, container, false);
        recyclerView = view.findViewById(R.id.rv_order_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ParentListAdapter  adapter =  new ParentListAdapter(getActivity() , (List<ParentObject>) initData());
        adapter.setParentClickableViewAnimationDefaultDuration();
        adapter.setParentAndIconExpandOnClick(true);
        recyclerView.setAdapter(adapter);
        return view;

    }

    private List<ParentObject>  initData() {
        TitleCreator titleCreator = TitleCreator.get(getActivity());
        List<OrderParentlist_Model> titles =  titleCreator.getAll();
        List<ParentObject> parentObjects = new ArrayList<>();
        for (OrderParentlist_Model title:titles) {
            List< Object>  childlist = new ArrayList<>();
            childlist.add(new OrderChildlist_Model("Shabbir Abbas","rahawa"));
            title.setChildObjectList( childlist);
            parentObjects.add(title);
        }
        return parentObjects;
    }
}
