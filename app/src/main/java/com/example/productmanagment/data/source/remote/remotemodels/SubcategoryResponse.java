package com.example.productmanagment.data.source.remote.remotemodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubcategoryResponse {
    @SerializedName("subcategory")
    String subcategory;

    @SerializedName("subcategories")
    public List<Subcategory> subcategoriesList;
}
