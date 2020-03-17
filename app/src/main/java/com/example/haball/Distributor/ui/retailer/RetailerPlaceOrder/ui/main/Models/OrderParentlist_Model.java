package com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Models;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;
import java.util.UUID;

public class OrderParentlist_Model implements ParentObject {
    private List<Object> myCHildrenList;
    private UUID  _id;
    private String title;

    public OrderParentlist_Model(String title) {
        this.title = title;
        _id = UUID.randomUUID();
    }

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<Object> getChildObjectList() {
        return myCHildrenList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        myCHildrenList = list;
    }
}
