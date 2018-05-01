package com.example.productmanagment.data.source.remote;

import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.source.remote.responses.GroupsResponse;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface RemoteData {
    Single<GroupsResponse> getGroupsByCreator(String groupCreator);
    Single<Group> getGroupById(String groupId);
}
