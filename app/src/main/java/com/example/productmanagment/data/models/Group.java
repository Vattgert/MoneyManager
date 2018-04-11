package com.example.productmanagment.data.models;

import java.util.List;

public class Group {
    private String title;
    private int membersCount;
    private List<Expense> groupExpenses;
    private List<User> users;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMembersCount() {
        return membersCount;
    }

    public void setMembersCount(int membersCount) {
        this.membersCount = membersCount;
    }

    public List<Expense> getGroupExpenses() {
        return groupExpenses;
    }

    public void setGroupExpenses(List<Expense> groupExpenses) {
        this.groupExpenses = groupExpenses;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
