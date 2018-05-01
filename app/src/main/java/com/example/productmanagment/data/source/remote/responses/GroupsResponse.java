package com.example.productmanagment.data.source.remote.responses;

import com.example.productmanagment.data.models.Group;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupsResponse {
    @SerializedName("groups")
    public List<Group> groupList;
}
