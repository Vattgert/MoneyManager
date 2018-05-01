package com.example.productmanagment.data.models;

/**
 * Created by Ivan on 21.03.2018.
 */

public class Purchase extends PurchaseList{
    private int purchaseListId;
    private boolean isChecked;

    public Purchase(int id, String title, int purchaseListId) {
        super(id, title);
        this.purchaseListId = purchaseListId;
        isChecked = false;
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return title;
    }
}
