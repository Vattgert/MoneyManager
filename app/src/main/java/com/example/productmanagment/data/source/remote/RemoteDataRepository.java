package com.example.productmanagment.data.source.remote;

import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.models.User;

import java.util.List;

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
    public List<Group> getGroupListByGroupOwner(String groupOwner) {
        return dataSource.getGroupListByGroupOwner(groupOwner);
    }

    @Override
    public List<Expense> getExpensesByGroup(String group) {
        return null;
    }

    @Override
    public void createGroup() {

    }

    @Override
    public void addUserToGroup(Group group, User user) {

    }
}
