package com.example.productmanagment.data.source.remote;

import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.source.remote.responses.GroupsResponse;
import com.example.productmanagment.data.source.remote.responses.SuccessResponse;
import com.example.productmanagment.data.source.remote.responses.UsersResponse;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class RemoteDataRepository implements RemoteData {
    private RemoteDataSource remoteDataSource;

    public RemoteDataRepository() {
        this.remoteDataSource = new RemoteDataSource();
    }

    @Override
    public Single<GroupsResponse> getGroupsByCreator(String groupCreator) {
        return remoteDataSource.getGroupsByCreator(groupCreator);
    }

    @Override
    public Single<Group> getGroupById(String groupId) {
        return remoteDataSource.getGroupById(groupId);
    }

    @Override
    public Single<UsersResponse> getUsersByGroup(String groupId) {
        return remoteDataSource.getUsersByGroup(groupId);
    }

    @Override
    public Single<SuccessResponse> addGroup(Group group) {
        return remoteDataSource.addGroup(group);
    }

    @Override
    public Single<SuccessResponse> addUserToGroup(String userEmail, String groupId) {
        return remoteDataSource.addUserToGroup(userEmail, groupId);
    }

    @Override
    public Single<SuccessResponse> renameGroup(String groupTitle, String groupId) {
        return remoteDataSource.renameGroup(groupTitle, groupId);
    }

    @Override
    public Single<SuccessResponse> getUserRights(String groupId, String userId) {
        return remoteDataSource.getUserRights(groupId, userId);
    }

    @Override
    public Single<SuccessResponse> setUserRights(String groupId, String userId, String userRights) {
        return remoteDataSource.setUserRights(groupId, userId, userRights);
    }
}
