package com.example.productmanagment.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ivan on 12.11.2017.
 */

public class Category {
    @SerializedName("subcategory_id")
    private int id;
    @SerializedName("subcategory_title")
    private String name;
    @SerializedName("icon")
    private String icon;

    public Category(){
        id = -1;
        name = "";
        icon = "";
    }

    public Category(int id){
        this.id = id;
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public Category(int id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}


