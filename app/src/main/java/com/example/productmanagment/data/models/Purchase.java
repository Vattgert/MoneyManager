package com.example.productmanagment.data.models;

/**
 * Created by Ivan on 21.03.2018.
 */

public class Purchase extends PurchaseList{
    private int purchaseListId;

    public Purchase(int id, String title, int purchaseListId) {
        super(id, title);
        this.purchaseListId = purchaseListId;
    }

    public Purchase(String title, int purchaseListId) {
        super(title);
        this.purchaseListId = purchaseListId;
    }

    public int getPurchaseListId() {
        return purchaseListId;
    }

    public void setPurchaseListId(int purchaseListId) {
        this.purchaseListId = purchaseListId;
    }
}
