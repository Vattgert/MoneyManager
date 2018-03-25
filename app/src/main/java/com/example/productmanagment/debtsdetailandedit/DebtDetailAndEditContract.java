package com.example.productmanagment.debtsdetailandedit;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Debt;

/**
 * Created by Ivan on 25.03.2018.
 */

public interface DebtDetailAndEditContract {
    interface View extends BaseView<Presenter> {
        void showCost(double cost);
        void showDebtType(int debtType);
        void showReceiver(String receiver);
        void showBorrowDate(String date);
        void showRepayDate(String date);
        void showAccount(String account);
        void showDescription(String description);
        void finish();
    }

    interface Presenter extends BasePresenter {
        void openDebt(int debtId);
        void editDebt(Debt debt);
        void deleteDebt();
    }
}
