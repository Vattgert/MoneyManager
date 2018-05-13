package com.example.productmanagment.data.source.remote.responses;

import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Group;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccountResponse {
    @SerializedName("accounts")
    public List<Account> accounts;
}
