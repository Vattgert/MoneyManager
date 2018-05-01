package com.example.productmanagment.groups;

import android.util.Log;

import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.data.source.remote.responses.GroupsResponse;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GroupsPresenter implements GroupsContract.Presenter {
    private RemoteDataRepository repository;
    private GroupsContract.View view;
    private BaseSchedulerProvider provider;
    CompositeDisposable compositeDisposable;

    public GroupsPresenter(RemoteDataRepository repository, GroupsContract.View view, BaseSchedulerProvider provider) {
        this.repository = repository;
        this.view = view;
        this.provider = provider;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadGroupsByOwner("1");
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void loadGroupsByOwner(String groupOwner) {
        Disposable disposable = repository.getGroupsByCreator(groupOwner)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processGroups, throwable -> Log.wtf("MyLog", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void createNewGroup(Group group) {

    }

    @Override
    public void openEditAndDetailGroup(Group group) {
        view.showEditAndDetailGroup(group.getGroupId());
    }

    @Override
    public void openCreateNewGroup() {
        view.showCreateNewGroup();
    }

    private void processGroups(GroupsResponse groupsResponse){
        List<Group> groups = groupsResponse.groupList;
        view.setGroupsData(groups);
    }
}
