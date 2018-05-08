package com.example.productmanagment.data.source.remote;

import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.source.remote.responses.GroupsResponse;
import com.example.productmanagment.data.source.remote.responses.SuccessResponse;
import com.example.productmanagment.data.source.remote.responses.UsersResponse;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface RemoteData {
    Single<GroupsResponse> getGroupsByCreator(String groupCreator);
    Single<Group> getGroupById(String groupId);
    Single<UsersResponse> getUsersByGroup(String groupId);
    Single<SuccessResponse> addGroup(Group group);
    Single<SuccessResponse> addUserToGroup(String userEmail, String groupId);
    Single<SuccessResponse> renameGroup(String groupTitle, String groupId);
    Single<SuccessResponse> getUserRights(String groupId, String userId);
    Single<SuccessResponse> setUserRights(String groupId, String userId, String userRights);
}
