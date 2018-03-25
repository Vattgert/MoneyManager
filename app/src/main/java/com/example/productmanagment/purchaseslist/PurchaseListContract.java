package com.example.productmanagment.purchaseslist;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Purchase;
import com.example.productmanagment.data.models.PurchaseList;

import java.util.List;

/**
 * Created by Ivan on 21.03.2018.
 */

public interface PurchaseListContract {
    public interface View extends BaseView<Presenter> {
        void showPurchasesList(List<PurchaseList> purchaseList);
        void showPurchases(List<Purchase> purchases);
    }

    public interface Presenter extends BasePresenter {
        void loadPurchasesList();
        void loadPurchasesById(int purchaseListId);
        void deletePurchase(int purchaseId);
        void selectPurchases();
        void removePurchasesSelection();
        void deletePurchasesList();
        void sendPurchasesList();
    }
}
