package com.example.productmanagment.data.source.remote.remotemodels;

import com.google.gson.annotations.SerializedName;

public class Subcategory {
    @SerializedName("subcategory_title")
    String subcategoryTitle;

    @SerializedName("category_title")
    String categoryTitle;

    @SerializedName("subcategory_id")
    String subcategoryId;

    @SerializedName("category_id")
    String categoryId;

    public String getSubcategoryTitle() {
        return subcategoryTitle;
    }

    public void setSubcategoryTitle(String subcategoryTitle) {
        this.subcategoryTitle = subcategoryTitle;
    }

    public String getGetSubcategoryTitle() {
        return categoryTitle;
    }

    public void setGetSubcategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(String subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
