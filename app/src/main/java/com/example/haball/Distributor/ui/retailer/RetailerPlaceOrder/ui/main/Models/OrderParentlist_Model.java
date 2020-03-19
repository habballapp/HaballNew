package com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Models;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;
import java.util.UUID;

public class OrderParentlist_Model implements ParentObject {
    private List<Object> myCHildrenList;
    private UUID _id;
    private String CategoryId;
    private String Title;
    private String ParentId;

    public OrderParentlist_Model(String categoryId, String title, String parentId) {
        this.CategoryId = categoryId;
        this.Title = title;
        this.ParentId = parentId;
        _id = UUID.randomUUID();
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID _id) {
        this._id = _id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
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
