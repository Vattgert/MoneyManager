package com.example.productmanagment.data.source.remote;

import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.source.remote.responses.GroupsResponse;

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
}
