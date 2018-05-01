package com.example.productmanagment.groupeditanddetail;

import android.util.Log;

import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.models.User;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GroupDetailAndEditPresenter implements GroupDetailAndEditContract.Presenter {
    private int groupId;
    private GroupDetailAndEditContract.View view;
    private RemoteDataRepository repository;
    private BaseSchedulerProvider provider;
    private CompositeDisposable compositeDisposable;

    public GroupDetailAndEditPresenter(int groupId, GroupDetailAndEditContract.View view,
                                       RemoteDataRepository repository, BaseSchedulerProvider provider) {
        this.groupId = groupId;
        this.view = view;
        this.repository = repository;
        this.provider = provider;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void addUser(String user) {

    }

    @Override
    public void openAddUserDialog() {

        //view.showNewParticipantDialog(groupTitle);
    }

    @Override
    public void loadUsersByGroup(String groupTitle) {

    }

    //TODO: Прикинуть что то с редактированием имени группы
    @Override
    public void editGroupTitle(String groupTitle) {

    }

    @Override
    public void addUserToGroup(String group, String user) {

    }

    @Override
    public void subscribe() {
        Disposable disposable = repository.getGroupById(String.valueOf(this.groupId))
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processGroup, throwable -> Log.wtf("MyLog", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    private void processGroup(Group group){
        view.setGroupTitleEdit(group.getTitle());
        view.setUsersData(group.getGroupUsers());
        view.setGroupParticipantCount();
        Log.wtf("GroupLog", group.getGroupId() + "");
        Log.wtf("GroupLog", group.getTitle());
        for(User user : group.getGroupUsers()){
            Log.wtf("GroupLog", user.getEmail());
        }
    }
}
