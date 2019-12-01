package com.example.productmanagment.data.source.remote.responses;

import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Group;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccountResponse {
    @SerializedName("accounts")
    public List<Account> accounts;
    @SerializedName("success")
    public String success;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
