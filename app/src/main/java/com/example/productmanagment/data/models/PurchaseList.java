package com.example.productmanagment.data.models;

/**
 * Created by Ivan on 21.03.2018.
 */

public class PurchaseList {
    int id;
    String title;

    public PurchaseList(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public PurchaseList(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
