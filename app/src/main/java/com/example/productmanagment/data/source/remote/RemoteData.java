package com.example.productmanagment.data.source.remote;

import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.models.MyCurrency;
import com.example.productmanagment.data.models.User;
import com.example.productmanagment.data.source.remote.responses.AccountResponse;
import com.example.productmanagment.data.source.remote.responses.CurrencyResponse;
import com.example.productmanagment.data.source.remote.responses.DiagramResponse;
import com.example.productmanagment.data.source.remote.responses.ExpensesResponse;
import com.example.productmanagment.data.source.remote.responses.GroupsResponse;
import com.example.productmanagment.data.source.remote.responses.ReportResponse;
import com.example.productmanagment.data.source.remote.responses.SuccessResponse;
import com.example.productmanagment.data.source.remote.responses.UsersResponse;

import java.util.Currency;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface RemoteData {
    Single<SuccessResponse> signUpUser(User user);
    Single<User> signInUser(String email, String password);
    Single<GroupsResponse> getGroupsByCreator(String groupCreator);
    Single<GroupsResponse> getGroups(String userId);
    Single<Group> getGroupById(String groupId);
    Single<UsersResponse> getUsersByGroup(String groupId);
    Single<SuccessResponse> addGroup(Group group);
    Single<SuccessResponse> addUserToGroup(String userEmail, String groupId);
    Single<SuccessResponse> renameGroup(String groupTitle, String groupId);
    Single<SuccessResponse> getUserRights(String groupId, String userId);
    Single<SuccessResponse> setUserRights(String groupId, String userId, String userRights);

    //Рахунки
    Single<AccountResponse> getAccountsByGroup(String groupId);
    Single<Account> getAccountById(String accountId);
    Single<SuccessResponse> addAccount(String groupId, Account account);
    Single<SuccessResponse> updateAccount(Account account);
    Single<SuccessResponse> deleteAccount(String accountId);

    //Витрати та доходи
    Single<ExpensesResponse> getExpensesByAccount(String accountId);
    Single<Expense> getExpenseById(String expenseId);
    Single<SuccessResponse> addExpense(Expense expense);
    Single<SuccessResponse> updateExpense(Expense expense);
    Single<SuccessResponse> deleteExpense(String idExpense);
    Single<ExpensesResponse> getExpensesByCategory(String category, String groupId);

    //Діаграми
    Single<DiagramResponse> getExpensesByCategoryDiagram(String groupId);
    Single<DiagramResponse> getIncomesByCategoryDiagram(String groupId);
    Single<DiagramResponse> getExpensesByUserDiagram(String groupId);

    //Валюти
    Single<CurrencyResponse> getCurrencies(String groupId);
    Single<MyCurrency> getCurrencyById(String currencyId);
    Single<SuccessResponse> addCurrency(MyCurrency currency);
    Single<SuccessResponse> updateCurrency(MyCurrency currency);
    Single<SuccessResponse> deleteCurrency(String currencyId);

    //Звіти
    Single<ReportResponse> getReport(String categoryId, String groupId);
}
