package com.example.productmanagment.addtemplate;

import android.content.Intent;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Template;
import com.google.android.gms.location.places.Place;

import java.util.List;

public interface AddTemplateContract {
    interface Presenter extends BasePresenter {
        Category getChosenCategory();
        Place getChosenPlace();
        void choosePlace();
        void result(int requestCode, int resultCode, Intent data);
        void saveTemplate(Template template);
        void loadAccounts();
    }
    interface View extends BaseView<Presenter> {
        void setChosenCategory(String title);
        void showChoosePlacePicker();
        void setChosenPlace(String place);
        void setAddress(Place place);
        void showAccounts(List<Account> accountList);
        void showMessage(String message);
    }
}
