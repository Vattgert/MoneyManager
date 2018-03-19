package com.example.productmanagment.adddebts;

import android.content.Intent;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Debt;

/**
 * Created by Ivan on 13.03.2018.
 */

public interface AddDebtContract {
    interface Presenter extends BasePresenter {
        void showEmptyDebtError();
        void showDebts();
        void result(int requestCode, int resultCode, Intent data);
        void saveDebt(Debt debt);
    }
    interface View extends BaseView<Presenter> {
        void showDebts();
    }
}
