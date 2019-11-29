package com.example.productmanagment.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Group {
    @SerializedName("household_id")
    private int groupId;

    @SerializedName("household_title")
    private String title;

    @SerializedName("household_members_count")
    private int membersCount = 0;

    @SerializedName("creator_id")
    private String groupOwner;

    private List<User> groupUsers;

    public Group(){

    }

    public Group(int groupId, String title) {
        this.groupId = groupId;
        this.title = title;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

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

    public String getGroupOwner() {
        return groupOwner;
    }

    public void setGroupOwner(String groupOwner) {
        this.groupOwner = groupOwner;
    }

    public List<User> getGroupUsers() {
        return groupUsers;
    }

    public void setGroupUsers(List<User> groupUsers) {
        this.groupUsers = groupUsers;
    }
}
