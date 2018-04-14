package com.example.productmanagment.data.source.remote;

import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.models.User;
import com.google.firebase.database.DataSnapshot;

import java.util.List;
import java.util.Observable;

import io.reactivex.Flowable;

public interface RemoteData {
    void signUpUserToDatabase(String email, String login);
    Flowable<List<Group>> getGroupsByGroupOwner(String owner);
    Flowable<List<User>> getUsersListByGroup(String group);
    List<Expense> getExpensesByGroup(String group);
    void createGroup(Group group);
    void editGroupTitle(String groupTitle);
    void addUserToGroup(String group, String user);
}
