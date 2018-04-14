package com.example.productmanagment.data.source.users;

import com.example.productmanagment.data.models.User;

import io.reactivex.Flowable;

public interface UserDataSource {
    Flowable<User> getCurrentUser(String email, String password);
    void addUser(User user);
    void deleteUser(User user);
    void editUser();
}
