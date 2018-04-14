package com.example.productmanagment.data.source.remote;

import android.util.Log;

import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.models.User;

import java.util.List;

import io.reactivex.Flowable;

public class RemoteDataRepository implements RemoteData {
    private RemoteDataSource dataSource;

    public RemoteDataRepository() {
        dataSource = new RemoteDataSource();
    }

    @Override
    public void signUpUserToDatabase(String email, String login) {
        dataSource.signUpUserToDatabase(email, login);
    }

    @Override
    public List<Expense> getExpensesByGroup(String group) {
        return null;
    }

    @Override
    public void createGroup(Group group) {
        dataSource.createGroup(group);
    }

    @Override
    public void editGroupTitle(String groupTitle) {

    }

    @Override
    public void addUserToGroup(String group, String user) {
        dataSource.addUserToGroup(group, user);
    }

    @Override
    public Flowable<List<Group>> getGroupsByGroupOwner(String owner){
        Log.wtf("RemoteDatabaseLog", "get groups by owner remote repository");
        return dataSource.getGroupsByGroupOwner(owner);
    }

    @Override
    public Flowable<List<User>> getUsersListByGroup(String group) {
        return dataSource.getUsersListByGroup(group);
    }
}
