package com.example.productmanagment.data.source.remote;

import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.models.User;
import com.example.productmanagment.data.source.remote.responses.AccountResponse;
import com.example.productmanagment.data.source.remote.responses.ExpensesResponse;
import com.example.productmanagment.data.source.remote.responses.GroupsResponse;
import com.example.productmanagment.data.source.remote.responses.SuccessResponse;
import com.example.productmanagment.data.source.remote.responses.UsersResponse;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class RemoteDataRepository implements RemoteData {
    private RemoteDataSource remoteDataSource;

    public RemoteDataRepository() {
        this.remoteDataSource = new RemoteDataSource();
    }

    @Override
    public Single<SuccessResponse> signUpUser(User user) {
        return remoteDataSource.signUpUser(user);
    }

    @Override
    public Single<User> signInUser(String email, String password) {
        return remoteDataSource.signInUser(email, password);
    }

    @Override
    public Single<GroupsResponse> getGroupsByCreator(String groupCreator) {
        return remoteDataSource.getGroupsByCreator(groupCreator);
    }

    @Override
    public Single<GroupsResponse> getGroups(String userId) {
        return remoteDataSource.getGroups(userId);
    }

    @Override
    public Single<Group> getGroupById(String groupId) {
        return remoteDataSource.getGroupById(groupId);
    }

    @Override
    public Single<UsersResponse> getUsersByGroup(String groupId) {
        return remoteDataSource.getUsersByGroup(groupId);
    }

    @Override
    public Single<SuccessResponse> addGroup(Group group) {
        return remoteDataSource.addGroup(group);
    }

    @Override
    public Single<SuccessResponse> addUserToGroup(String userEmail, String groupId) {
        return remoteDataSource.addUserToGroup(userEmail, groupId);
    }

    @Override
    public Single<SuccessResponse> renameGroup(String groupTitle, String groupId) {
        return remoteDataSource.renameGroup(groupTitle, groupId);
    }

    @Override
    public Single<SuccessResponse> getUserRights(String groupId, String userId) {
        return remoteDataSource.getUserRights(groupId, userId);
    }

    @Override
    public Single<SuccessResponse> setUserRights(String groupId, String userId, String userRights) {
        return remoteDataSource.setUserRights(groupId, userId, userRights);
    }

    @Override
    public Single<AccountResponse> getAccountsByGroup(String groupId) {
        return remoteDataSource.getAccountsByGroup(groupId);
    }

    @Override
    public Single<Account> getAccountById(String accountId) {
        return remoteDataSource.getAccountById(accountId);
    }

    @Override
    public Single<SuccessResponse> addAccount(String groupId, Account account) {
        return remoteDataSource.addAccount(groupId, account);
    }

    @Override
    public Single<SuccessResponse> updateAccount(Account account) {
        return remoteDataSource.updateAccount(account);
    }

    @Override
    public Single<SuccessResponse> deleteAccount(String accountId) {
        return remoteDataSource.deleteAccount(accountId);
    }

    @Override
    public Single<ExpensesResponse> getExpensesByAccount(String accountId) {
        return remoteDataSource.getExpensesByAccount(accountId);
    }

    @Override
    public Single<SuccessResponse> addExpense(Expense expense) {
        return remoteDataSource.addExpense(expense);
    }

    @Override
    public Single<SuccessResponse> updateExpense(Expense expense) {
        return null;
    }

    @Override
    public Single<SuccessResponse> deleteExpense(String idExpense) {
        return null;
    }
}
