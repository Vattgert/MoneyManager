package com.example.productmanagment.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Group {
    @SerializedName("id_group")
    private int groupId;

    @SerializedName("group_title")
    private String title;

    @SerializedName("group_member_count")
    private int membersCount;

    @SerializedName("id_group_creator")
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
