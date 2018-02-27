package com.example.productmanagment.data.models;

/**
 * Created by Ivan on 25.01.2018.
 */

public class Subcategory extends Category{
    private int categoryId;
    private int isFavourite;

    public Subcategory(int id, String name) {
        super(id, name);
    }

    public Subcategory(int id, String name, int categoryId) {
        super(id, name);
        this.categoryId = categoryId;
    }

    public Subcategory(int id, String name, String icon, int categoryId, int isFavourite)  {
        super(id, name, icon);
        this.categoryId = categoryId;
        this.isFavourite = isFavourite;
    }

    public Subcategory(int id, String name, int categoryId, int isFavourite){
        super(id, name);
        this.categoryId = categoryId;
        this.isFavourite = isFavourite;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int isFavourite() {
        return isFavourite;
    }

    public void setFavourite(int favourite) {
        isFavourite = favourite;
    }
}
