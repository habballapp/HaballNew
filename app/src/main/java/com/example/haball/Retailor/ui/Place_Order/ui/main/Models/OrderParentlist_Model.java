package com.example.haball.Retailor.ui.Place_Order.ui.main.Models;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;
import java.util.UUID;

public class OrderParentlist_Model implements ParentObject {
    private List<Object> myCHildrenList;
    private UUID _id;
    private String CategoryId;
    private String Title;
    private String ParentId;
    public Boolean expanded = false;

    public OrderParentlist_Model(List<Object> myCHildrenList, UUID _id, String categoryId, String title, String parentId, Boolean expanded) {
        this.myCHildrenList = myCHildrenList;
        this._id = _id;
        CategoryId = categoryId;
        Title = title;
        ParentId = parentId;
        this.expanded = expanded;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
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
