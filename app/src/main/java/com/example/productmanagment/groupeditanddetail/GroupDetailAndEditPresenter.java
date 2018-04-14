package com.example.productmanagment.groupeditanddetail;

import android.util.Log;

import com.example.productmanagment.data.models.User;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GroupDetailAndEditPresenter implements GroupDetailAndEditContract.Presenter {
    private String groupTitle;
    private GroupDetailAndEditContract.View view;
    private RemoteDataRepository repository;
    private CompositeDisposable compositeDisposable;

    public GroupDetailAndEditPresenter(String groupTitle, GroupDetailAndEditContract.View view, RemoteDataRepository repository) {
        this.groupTitle = groupTitle;
        this.view = view;
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
        Log.wtf("MyLog", groupTitle);
    }

    @Override
    public void addUser(String user) {
        repository.addUserToGroup(groupTitle, user);
        loadUsersByGroup(this.groupTitle);
    }

    @Override
    public void openAddUserDialog() {
        view.showNewParticipantDialog(groupTitle);
    }

    @Override
    public void loadUsersByGroup(String groupTitle) {
        Disposable disposable = repository.getUsersListByGroup(groupTitle)
                .flatMap(Flowable::fromIterable)
                .toList()
                .subscribe(users -> {view.setUsersData(users); view.setGroupParticipantCount();});
        compositeDisposable.add(disposable);
    }

    //TODO: Прикинуть что то с редактированием имени группы
    @Override
    public void editGroupTitle(String groupTitle) {

    }

    @Override
    public void addUserToGroup(String group, String user) {
        repository.addUserToGroup(group, user);
    }

    @Override
    public void subscribe() {
        view.setGroupTitleEdit(this.groupTitle);
        loadUsersByGroup(this.groupTitle);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
