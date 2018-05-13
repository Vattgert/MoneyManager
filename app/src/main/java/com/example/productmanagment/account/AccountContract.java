package com.example.productmanagment.account;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Account;

import java.util.List;

/**
 * Created by Ivan on 27.03.2018.
 */

public interface AccountContract {
    interface Presenter extends BasePresenter{
        void loadAccount();
        void loadAccountsByGroup(String groupId);
        void openAddAccount();
        void openAccountDetailAndEdit(Account account);
    }

    interface View extends BaseView<Presenter> {
        void showAccounts(List<Account> accountList);
        void showAddAccount(int groupId);
        void showAccountDetailAndEdit(String accountId, int groupId);
    }
}
