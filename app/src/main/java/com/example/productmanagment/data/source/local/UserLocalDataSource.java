package com.example.productmanagment.data.source.local;

import com.example.productmanagment.data.models.User;
import com.example.productmanagment.data.source.users.UserDataSource;

import io.reactivex.Flowable;

public class UserLocalDataSource implements UserDataSource{

    @Override
    public Flowable<User> getCurrentUser(String email, String password) {
        return null;
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public void editUser() {

    }
}
