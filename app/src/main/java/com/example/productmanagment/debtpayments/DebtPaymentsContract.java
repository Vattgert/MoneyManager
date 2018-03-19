package com.example.productmanagment.debtpayments;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Expense;

import java.util.List;

/**
 * Created by Ivan on 11.03.2018.
 */

public interface DebtPaymentsContract {
    interface View extends BaseView<Presenter> {
        void showDebtPayments(List<Expense> debtPayments);
    }

    interface Presenter extends BasePresenter {
        void loadDebtPayments(int debtId);
    }
}
