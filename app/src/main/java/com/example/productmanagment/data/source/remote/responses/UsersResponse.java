package com.example.productmanagment.data.source.remote.responses;

import com.example.productmanagment.data.models.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersResponse {
    @SerializedName("groupUsers")
    public List<User> userList;
}
