package com.example.productmanagment.groups;

import com.example.productmanagment.data.source.remote.RemoteDataRepository;

public class GroupsPresenter implements GroupsContract.Presenter {
    private RemoteDataRepository repository;
    private GroupsContract.View view;

    public GroupsPresenter(RemoteDataRepository repository, GroupsContract.View view) {
        this.repository = repository;
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        repository.getGroupListByGroupOwner("Ivan");
    }

    @Override
    public void unsubscribe() {

    }
}
