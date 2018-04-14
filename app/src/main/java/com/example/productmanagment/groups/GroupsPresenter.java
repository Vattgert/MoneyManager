package com.example.productmanagment.groups;

import android.util.Log;

import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GroupsPresenter implements GroupsContract.Presenter {
    private RemoteDataRepository repository;
    private GroupsContract.View view;
    CompositeDisposable compositeDisposable;

    public GroupsPresenter(RemoteDataRepository repository, GroupsContract.View view) {
        this.repository = repository;
        this.view = view;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        Log.wtf("RemoteDatabaseLog", "get groups by owner group presenter subscribe");
        loadGroupsByOwner("Vattgert");
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void loadGroupsByOwner(String groupOwner) {
        Disposable disposable = repository.getGroupsByGroupOwner(groupOwner)
                .flatMap(Flowable::fromIterable)
                .toList()
                .subscribe(groupList -> view.setGroupsData(groupList));
        compositeDisposable.add(disposable);
    }

    @Override
    public void createNewGroup(Group group) {
        repository.createGroup(group);
        loadGroupsByOwner("Vattgert");
    }

    @Override
    public void openEditAndDetailGroup(Group group) {
        view.showEditAndDetailGroup(group.getTitle());
    }

    @Override
    public void openCreateNewGroup() {
        view.showCreateNewGroup();
    }
}
