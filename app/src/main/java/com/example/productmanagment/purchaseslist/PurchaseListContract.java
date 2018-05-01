package com.example.productmanagment.purchaseslist;

import android.app.Dialog;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.Purchase;
import com.example.productmanagment.data.models.PurchaseList;

import java.util.List;

/**
 * Created by Ivan on 21.03.2018.
 */

public interface PurchaseListContract {
    interface View extends BaseView<Presenter> {
        void showPurchasesList(List<PurchaseList> purchaseList);
        void showPurchases(List<Purchase> purchases);
        void showSendDialog(String text);
        void showAllPurchasesSelection();
        void showAllPurchasesDeselection();
        void showAddExpenseRecord();
        void showAddPurchaseList();
        void showRenamePurchaseList();
        void showAccountList(List<Account> accounts);
    }

    interface Presenter extends BasePresenter {
        void loadPurchasesList();
        void loadPurchasesById(int purchaseListId);
        void loadAccounts();
        void deletePurchase(int purchaseId);
        void selectPurchases();
        void createPurchase(Purchase purchase);
        void removePurchasesSelection();
        void deletePurchasesList(int purchaseListId);
        void sendPurchasesList(String text);
        void openPurchaseRecordDialog();
        void openAddPurchaseListDialog();
        void openRenamePurchaseListDialog();
        void renamePurchaseList(int purchaseListId, String rename);
        void createPurchaseList(String name);
        void createExpenseRecord(Expense expense);
    }
}
