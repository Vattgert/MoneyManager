package com.example.productmanagment.debts;

import android.support.annotation.NonNull;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Debt;

import java.util.List;

/**
 * Created by Ivan on 10.03.2018.
 */

public interface DebtsContract {
    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);
        void showDebts(List<Debt> debts);
        void showAddDebt();
        void showDebtDetailAndEdit(int debtId);
        void showDebtPayments(int debtId);
        void showPayDebt(Debt debt);
        void showEnterDebtSum(Debt debt);
        void showLoadingExpensesError();
        void showNoDebts();
        void showDebtSuccessfullySavedMessage();
        void showDebtIsPayedMessage(String message);
    }

    interface Presenter extends BasePresenter {
        void loadDebts();
        void openDebtDetails(@NonNull Debt debt);
        void addNewDebt();
        void payDebt(Debt clicked);
        void enterDebtPartSum(Debt debt);
        void closeDebtPart(Debt debt, String sum);
        void closeDebt(Debt debt);
        void result(int requestCode, int resultCode);
        void debtIsPayedMessage();
        void openDebtPayments(int debtId);
    }
}
